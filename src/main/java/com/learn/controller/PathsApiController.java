package com.learn.controller;

import com.learn.entity.PathEntity;
import com.learn.service.DataService;
import com.learn.service.PathDataService;
import com.learn.service.PathService;
import com.learn.utils.R;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * 文本信息
 */
@RestController
@RequestMapping("pathsApi")
public class PathsApiController extends AbstractController {
    @Autowired
    private DataService dataService;

    @Autowired
    private PathDataService pathDataService;

    @Autowired
    private PathService pathService;
    private Base64 base64 = new Base64();


    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 荔枝FM请求
     */
    @RequestMapping(value="/requesta")
    public R requesta(@RequestParam Map<String, Object> params) {
        try {
            logger.info("pathsApi request"+params);
            String idfa= (String)params.get("idfa");
            String mac= (String)params.get("mac");
            String ua = (String)params.get("ua");
            if (mac == null){
                mac ="";
            }
            String ip= (String)params.get("ip");
            String companyKey = (String)params.get("companyKey");
            String appId = (String) params.get("appId");
            //获得参数 idfa mac ip companyKey appId callabck
            //校验pathInfo
            //通过，则获得pathInfo
            PathEntity pathEntity = pathService.check(companyKey,appId);
            if (pathEntity == null){
                logger.info("pathsApi request fail parms:"+params);
                return R.error("param error");
            }
            //校验callback
            String ca = (String)params.get("callback");
            if (ca == null || ca == ""){
                return R.error("callback error");
            }
            String callbackPath =ca.replace("&amp;","&");
            logger.info("加密前:"+callbackPath);
            //编码 base64
            byte[] callbackBytes = callbackPath.getBytes("UTF-8");
            callbackPath = base64.encodeToString(callbackBytes);
            logger.info("pathsApi request callbackPath:"+callbackPath);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

            String now = df.format(new Date());

            Date endDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(now);

            //增加上报数据
            pathDataService.incrFromPath(pathEntity.getName(),companyKey,pathEntity.getAppName(),appId,endDate);
            //设置app回调我们的接口
            //生成callback  我们自己的加渠道的
            String callback= pathEntity.getCallBackApp();
            //urlCode编码
            String str =java.net.URLEncoder.encode(callbackPath,   "utf-8");
            callback = str;
            logger.info("pathsApi request callback:"+callback);
            //请求App idfa mac ip callback
            String url = pathEntity.getAppHost();
            //参数使用ifa
            String aff_sub = companyKey+"_"+appId;
            String site_name = "";
            requestApp(url,idfa, ua,ip,site_name,aff_sub,callback);
            return R.ok().put("result","success");
        } catch (Exception e){
            logger.error("pathsApi request fail",e);
            return R.error(500,"pathsApi request fail");
        }

    }

    private void requestApp(String url, String idfa, String ua, String ip,String site_name, String aff_sub, String callback) {
        logger.info(url);
        Connection connection =Jsoup.connect(url).ignoreContentType(true);
        connection.data("aff_sub",aff_sub);
        //先不传ua
        //connection.data("ua",ua);
        connection.data("idfa",idfa);
        //先不传site_name
        //connection.data("site_name","24");
        connection.data("ip",ip);
        connection.data("sub_affiliate_id",callback);
        try {
            Document document = connection.header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                    .timeout(5000).get();
            if (document!= null){
                logger.info("document:"+document);
                logger.info("pathsApi request success!");
            }
        } catch (Exception e){
            logger.error("pathsApi request fail!",e);
        }
    }
    /**
     * 音遇请求
     */
    @RequestMapping(value="/requestb")
    public R requestb(@RequestParam Map<String, Object> params) {
        try {
            logger.info("pathsApi request"+params);
            String idfa= (String)params.get("idfa");
            String mac= (String)params.get("mac");
            String ua = (String)params.get("ua");
            if (mac == null){
                mac ="";
            }
            String ip= (String)params.get("ip");
            String companyKey = (String)params.get("companyKey");
            String appId = (String) params.get("appId");
            //获得参数 idfa mac ip companyKey appId callabck
            //校验pathInfo
            //通过，则获得pathInfo
            PathEntity pathEntity = pathService.check(companyKey,appId);
            if (pathEntity == null){
                logger.info("pathsApi request fail parms:"+params);
                return R.error("param error");
            }
            //校验callback
            String ca = (String)params.get("callback");
            if (ca == null || ca == ""){
                return R.error("callback error");
            }
            String callbackPath =ca.replace("&amp;","&");
            logger.info("加密前:"+callbackPath);
            //编码 base64
            byte[] callbackBytes = callbackPath.getBytes("UTF-8");
            callbackPath = base64.encodeToString(callbackBytes);
            logger.info("pathsApi request callbackPath:"+callbackPath);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

            String now = df.format(new Date());

            Date endDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(now);

            //增加上报数据
            pathDataService.incrFromPath(pathEntity.getName(),companyKey,pathEntity.getAppName(),appId,endDate);
            //设置app回调我们的接口
            //生成callback  我们自己的加渠道的
            String callback= pathEntity.getCallBackApp();
            //urlCode编码
            String str =java.net.URLEncoder.encode(callbackPath,   "utf-8");
            callback = str;
            logger.info("pathsApi request callback:"+callback);
            //请求App idfa mac ip callback
            String url = pathEntity.getAppHost();
            //参数使用ifa
            String aff_sub = companyKey+"_"+appId;
            String site_name = "";
            requestApp1(url,idfa, ua,ip,site_name,aff_sub,callback);
            return R.ok().put("result","success");
        } catch (Exception e){
            logger.error("pathsApi request fail",e);
            return R.error(500,"pathsApi request fail");
        }

    }

    private void requestApp1(String url, String idfa, String ua, String ip,String site_name, String aff_sub, String callback) {
        logger.info(url);
        Connection connection =Jsoup.connect(url).ignoreContentType(true);
        connection.data("cid",aff_sub);
        //先不传ua
        //connection.data("ua",ua);
        connection.data("idfa",idfa);
        //先不传site_name
        //connection.data("site_name","24");
        connection.data("ip",ip);
        connection.data("s1",callback);
        try {
            Document document = connection.header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                    .timeout(5000).get();
            if (document!= null){
                logger.info("document:"+document);
                logger.info("pathsApi request success!");
            }
        } catch (Exception e){
            logger.error("pathsApi request fail!",e);
        }
    }
}
