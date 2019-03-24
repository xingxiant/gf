package com.learn.controller;

import com.baidu.aip.nlp.AipNlp;
import com.learn.dao.DataDao;
import com.learn.dao.FromPathDao;
import com.learn.dao.PathDataDao;
import com.learn.entity.*;
import com.learn.factory.ThreadPoolFactory;
import com.learn.service.*;
import com.learn.utils.PageUtils;
import com.learn.utils.Query;
import com.learn.utils.R;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 文本信息
 */
@RestController
@RequestMapping("pathApi")
public class PathApiController extends AbstractController {
    @Autowired
    private DataService dataService;

    @Autowired
    private PathDataService pathDataService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private PathService pathService;
    private Base64 base64 = new Base64();

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * callback方式请求
     */
    @RequestMapping(value="/requesta")
    public R requesta(@RequestParam Map<String, Object> params) {
        try {
            logger.info("pathApi request"+params);
            String idfa= (String)params.get("idfa");
            String ip= (String)params.get("ip");
            String companyKey = (String)params.get("companyKey");
            String appId = (String) params.get("appId");
            String callback = (String) params.get("callback");
            if(!checkParams(idfa,ip,companyKey,appId,callback)){
                return R.error(400,"params error");
            }
            //替换&
            String callbackPath =((String)params.get("callback")).replace("&amp;","&");
            logger.info("加密前=>callbackPath:"+callbackPath);
            //对callback base64加密
            byte[] callbackBytes = callbackPath.getBytes("UTF-8");
            callbackPath = base64.encodeToString(callbackBytes);
            logger.info("加密后=>callbackPath:"+callbackPath);
            //校验pathInfo 通过，则获得pathInfo
            PathEntity pathEntity = pathService.check(companyKey,appId);
            if (pathEntity == null){
                return R.error("companyKey or appId error");
            }
            //获取今天的日期
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String now = df.format(new Date());
            Date endDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(now);
            //增加渠道上报数据
            pathDataService.incrFromPath(pathEntity.getName(),companyKey,pathEntity.getAppName(),appId,endDate);
            //设置app回调我们的接口
            //生成callback  我们自己的appApi/requesta加渠道的
            String mycallback= pathEntity.getCallBackApp();
            //urlCode编码
            String str =java.net.URLEncoder.encode(mycallback+"&callback="+callbackPath,   "utf-8");
            String callbackFin = str;
            //请求App idfa mac ip callback
            String url = pathEntity.getAppHost();
            //对广告主地址进行模板替换 {_idfa_}:idfa {_ip_}:ip {_callback_}:callback
            url=url.replace("{_idfa_}",idfa);
            url=url.replace("{_ip_}",ip);
            url=url.replace("{_callback_}",callbackFin);
            requestaApp(url);
            return R.ok("success");
        } catch (Exception e){
            logger.error("pathApi request fail",e);
            return R.error(500,"pathApi request fail");
        }

    }
    /**
     * 提前给回调地址的方式请求
     */
    @RequestMapping(value="/requestb")
    public R requestb(@RequestParam Map<String, Object> params) {
        try {
            logger.info("pathApi request"+params);
            String idfa= (String)params.get("idfa");
            String ip= (String)params.get("ip");
            String companyKey = (String)params.get("companyKey");
            String appId = (String) params.get("appId");
            String callback = (String) params.get("callback");
            if(!checkParams(idfa,ip,companyKey,appId,callback)){
                return R.error(400,"params error");
            }
            //经过替换&
            String callbackPath =(callback).replace("&amp;","&");
            logger.info("加密前=>callbackPath:"+callbackPath);

            //对callback base64加密
            byte[] callbackBytes = callbackPath.getBytes("UTF-8");
            callbackPath = base64.encodeToString(callbackBytes);
            logger.info("加密后=>callbackPath:"+callbackPath);
            //urlcode编码
            callbackPath = URLEncoder.encode(callbackPath,"utf-8");
            //拼接ai1wan_company_appId_callbackPath
            String ai1wan_company_appId_callbackPath ="ai1wan_"+companyKey+"_"+appId+"_"+callbackPath;

            //校验pathInfo 通过，则获得pathInfo
            PathEntity pathEntity = pathService.check(companyKey,appId);
            if (pathEntity == null){
                return R.error("companyKey or appId error");
            }
            //获取今天的日期
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String now = df.format(new Date());
            Date endDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(now);
            //增加渠道上报数据
            pathDataService.incrFromPath(pathEntity.getName(),companyKey,pathEntity.getAppName(),appId,endDate);


            String url = pathEntity.getAppHost();
            //对广告主地址进行模板替换 {_idfa_}:idfa {_ip_}:ip {_callback_}:callback
            url=url.replace("{_cac_}",ai1wan_company_appId_callbackPath);
            requestaApp(url);
            return R.ok("success");
        } catch (Exception e){
            logger.error("pathApi request fail",e);
            return R.error(500,"pathApi request fail");
        }

    }
    /**
     * 提前给回调地址的方式请求
     */
    @RequestMapping(value="/requestc")
    public R requestc(@RequestParam Map<String, Object> params) {
        try {
            logger.info("pathApi request"+params);
            String idfa= (String)params.get("idfa");
            String ip= (String)params.get("ip");
            String companyKey = (String)params.get("companyKey");
            String appId = (String) params.get("appId");
            String callback = (String) params.get("callback");
            if(!checkParams(idfa,ip,companyKey,appId,callback)){
                return R.error(400,"params error");
            }
            //替换&
            String callbackPath =((String)params.get("callback")).replace("&amp;","&");
            logger.info("加密前=>callbackPath:"+callbackPath);
            //对callback base64加密
            byte[] callbackBytes = callbackPath.getBytes("UTF-8");
            callbackPath = base64.encodeToString(callbackBytes);
            logger.info("加密后=>callbackPath:"+callbackPath);
            //校验pathInfo 通过，则获得pathInfo
            PathEntity pathEntity = pathService.check(companyKey,appId);
            if (pathEntity == null){
                return R.error("companyKey or appId error");
            }
            //获取今天的日期
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String now = df.format(new Date());
            Date endDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(now);
            //增加渠道上报数据
            pathDataService.incrFromPath(pathEntity.getName(),companyKey,pathEntity.getAppName(),appId,endDate);
            //设置app回调我们的接口
            //生成callback  我们自己的appApi/requesta加渠道的
            String mycallback= pathEntity.getCallBackApp();
            //不经过 urlCode编码 tomcat自动编码
            String callbackFin = mycallback+"&callback="+callbackPath;
            logger.info("callbackFin:"+callbackFin);
            //请求App idfa mac ip callback
            String url = pathEntity.getAppHost();
            //对广告主地址进行模板替换 {_idfa_}:idfa {_ip_}:ip {_callback_}:callback
            url=url.replace("{_idfa_}",idfa);
            url=url.replace("{_ip_}",ip);
            String paramName = pathEntity.getCallBackPath();
            requestaAppPro(url,paramName,callbackFin);
            return R.ok("success");
        } catch (Exception e){
            logger.error("pathApi request fail",e);
            return R.error(500,"pathApi request fail");
        }


    }

    /**
     * 提前给回调地址的方式请求
     */
    @RequestMapping(value = "/req")
    public R req(@RequestParam Map<String, Object> params) {

        logger.info("pathApi req" + params);
        String idfa = (String) params.get("idfa");
        String ip = (String) params.get("ip");
        final String companyKey = (String) params.get("companyKey");
        final String appId = (String) params.get("appId");
        String callback = (String) params.get("callback");
        if (!checkParams(idfa, ip, companyKey, appId, callback)) {
            return R.error(400, "params error");
        }
        //替换&
        String callbackPath = ((String) params.get("callback")).replace("&amp;", "&");
        final DataFromPathEntity dataFromPathEntity = new DataFromPathEntity();
        try {
            //校验pathInfo 通过，则获得pathInfo
            final PathEntity pathEntity = pathService.check(companyKey, appId);
            if (pathEntity == null) {
                return R.error("companyKey or appId error");
            }
            try {
                transformToDataFromPathEntity(dataFromPathEntity, idfa, companyKey, pathEntity.getName(), appId, pathEntity.getAppName(), callbackPath);
                //保存请求记录
                requestService.savePathRequest(dataFromPathEntity);
            } catch (Exception e) {
                logger.error("save dataFromPath error dataFromPathEntity:" + dataFromPathEntity.toString(), e);
            }
            try {
                ThreadPoolFactory.executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            //获取今天的日期
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
                            String now = df.format(new Date());
                            Date endDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(now);
                            //增加渠道上报数据
                            pathDataService.incrFromPath(pathEntity.getName(), companyKey, pathEntity.getAppName(), appId, endDate);
                        } catch (Exception e) {
                            logger.error("incrFromPath error info:" + dataFromPathEntity.toString(), e);
                        }
                    }
                });
            } catch (Exception e){
                logger.error("executorPool is full!", e);
            }

            //设置app回调我们的接口
            //生成callback  我们自己的appApi/requesta加渠道的
            String mycallback = pathEntity.getCallBackApp();
            //不经过 urlCode编码 tomcat自动编码
            String callbackFin = mycallback + "&identif=" + dataFromPathEntity.getUniqueId();
            //logger.info("callbackFin:" + callbackFin);
            //请求App idfa mac ip callback
            String url = pathEntity.getAppHost();
            //对广告主地址进行模板替换 {_idfa_}:idfa {_ip_}:ip {_callback_}:callback
            url = url.replace("{_idfa_}", idfa);
            url = url.replace("{_ip_}", ip);
            String paramName = pathEntity.getCallBackPath();
            String result = requestaAppPro(url, paramName, callbackFin);
            if (result != null){
                dataFromPathEntity.setReportResult(result);
//                requestService.updatePathRequest(dataFromPathEntity);
            }
            requestService.savePathRequest(dataFromPathEntity);
            return R.ok("success");
        } catch (Exception e) {
            logger.error("pathApi request fail", e);
            //请求记录设置为失败
            dataFromPathEntity.setIsReportSuccess(0);
//            requestService.updatePathRequest(dataFromPathEntity);
            requestService.savePathRequest(dataFromPathEntity);
            return R.error(500, "pathApi request fail");
        }
    }

    private void transformToDataFromPathEntity(DataFromPathEntity dataFromPathEntity,String idfa, String companyKey, String pathName, String appId, String appName, String callback) {
        dataFromPathEntity.setAppId(appId);
        dataFromPathEntity.setAppName(appName);
        dataFromPathEntity.setCallback(callback);
        //默认为上报成功
        dataFromPathEntity.setIsReportSuccess(1);
        dataFromPathEntity.setCompanyKey(companyKey);
        dataFromPathEntity.setPathName(pathName);
        dataFromPathEntity.setIdfa(idfa);
        Long time = System.currentTimeMillis()/1000;
        dataFromPathEntity.setTime(time);
        //产生三位随机数
        int random=(int)(Math.random()*900)+100;
        String uniqueId = companyKey+"_"+appId+String.valueOf(random)+time;
        dataFromPathEntity.setUniqueId(uniqueId);
    }

    private boolean checkParams(String idfa, String ip, String companyKey, String appId, String callback) {
        if (idfa == null || idfa==""){
            return false;
        }
        if (ip == null || ip == ""){
            return false;
        }
        if (companyKey == null || companyKey == ""){
            return false;
        }
        if (appId == null || appId == ""){
            return false;
        }
        if (callback == null || callback == ""){
            return false;
        }
        return true;
    }

    private void requestaApp(String url) throws Exception{
        logger.info(url);
        Connection connection =Jsoup.connect(url).ignoreContentType(true);
        try {
            Document document = connection.header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                    .timeout(5000).get();
            if (document!= null){
                logger.info("document:"+document);
                logger.info("pathApi request success!");
            }
        } catch (Exception e){
            logger.error("pathApi request fail!",e);
            throw e;
        }
    }
    private String requestaAppPro(String url,String paramName,String param) throws Exception{
        logger.info("requestaAppPro url:"+url+" paramName:"+paramName+" param"+param);
        Connection connection =Jsoup.connect(url).ignoreContentType(true);
        connection.data(paramName,param);
        try {
            Document document = connection.header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                    .timeout(5000).get();
            if (document!= null){
                logger.info("document:"+document.text());
                logger.info("pathApi request success!");
                return document.text();
            } else {
                return null;
            }
        } catch (Exception e){
            logger.error("pathApi request fail!",e);
            throw e;
        }
    }

}
