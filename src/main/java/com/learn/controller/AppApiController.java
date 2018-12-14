package com.learn.controller;

import com.baidu.aip.nlp.AipNlp;
import com.learn.dao.DataDao;
import com.learn.dao.FromAppDao;
import com.learn.dao.FromPathDao;
import com.learn.dao.ToPathDao;
import com.learn.entity.*;
import com.learn.service.DataService;
import com.learn.service.PathDataService;
import com.learn.service.PathService;
import com.learn.service.WordService;
import com.learn.utils.PageUtils;
import com.learn.utils.Query;
import com.learn.utils.R;
import com.mysql.jdbc.log.LogUtils;
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
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 文本信息
 */
@RestController
@RequestMapping("appApi")
public class AppApiController extends AbstractController {
    @Autowired
    private DataService dataService;

    @Autowired
    private PathDataService pathDataService;

    @Autowired
    private PathService pathService;
    @Autowired
    private FromAppDao fromAppDao;
    @Autowired
    private ToPathDao toPathDao;
    private Base64 base64 = new Base64();

    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 请求
     */
    @RequestMapping("/requesta")
    public R requesta(@RequestParam Map<String, Object> params,HttpServletResponse response) {

        logger.info("appApi requesta"+params);
        //获得参数 companyKey appId
        String companyKey = (String)params.get("companyKey");
        String appId = (String) params.get("appId");
        String callback=(String) params.get("callback");
        if(!checkParams(companyKey,appId,callback)){
            return R.error(400,"params callback error");
        }
        //base64加密的
        //进行解密
        String callBackPath;
        try {
            callBackPath = new String(base64.decode(callback), "UTF-8");
        } catch (Exception e){
            logger.info("解密callback失败:",e);
            return R.error(400,"params error");
        }
        //校验pathInfo 通过，则获得pathInfo
        PathEntity pathEntity = pathService.check(companyKey,appId);
        if (pathEntity == null){
            logger.info("appApi request fail parms:"+params);
            return R.error(400,"params companyKey or appId error");
        }
        try {
            //获取今天日期
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String now = df.format(new Date());
            Date endDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(now);

            //app回调数据加1
            pathDataService.incrFromApp(pathEntity.getName(),companyKey,pathEntity.getAppName(),appId,endDate);
            //生成1-100
            int p = new Random().nextInt(100)+1;
            int weight = pathEntity.getWeight();
            logger.info("appApi p:"+p+" weight:"+weight);
            if (p<=weight){
                pathRequest(callBackPath);
                //回传渠道记录加1
                pathDataService.incrToPath(pathEntity.getName(),companyKey,pathEntity.getAppName(),appId,endDate);
            }
            return R.ok("success");
        } catch (Exception e){
            logger.error("appApi request fail!",e);
            return R.error(500,"appApi request fail!");
        }

    }

    /**
     * 请求
     */
    @RequestMapping("/requestb")
    public R requestb(@RequestParam Map<String, Object> params,HttpServletResponse response) {

        logger.info("appApi requestb"+params);
        //解析cac
        String cac ="";
        for (String key : params.keySet()){
            String value = (String)params.get(key);
            if (value !=null && value != ""){
                if (value.startsWith("ai1wan_")){
                    cac = value;
                    break;
                }
            }
        }
        logger.info("cac:"+cac);
        if (cac == ""){
            return R.error(400,"params cac error");
        }
        //获得参数 companyKey appId
        String[] values = cac.split("_");
        String companyKey = values[1];
        String appId = values[2];
        String callback= values[3];

        if(!checkParams(companyKey,appId,callback)){
            return R.error(400,"params error");
        }
        //base64加密的
        //进行解密
        String callBackPath;
        try {
            //urlcode解码
            callback=URLDecoder.decode(callback,"utf-8");
            callBackPath = new String(base64.decode(callback), "UTF-8");
        } catch (Exception e){
            logger.info("解密callback失败:",e);
            return R.error(400,"params error");
        }
        //校验pathInfo 通过，则获得pathInfo
        PathEntity pathEntity = pathService.check(companyKey,appId);
        if (pathEntity == null){
            logger.info("appApi request fail parms:"+params);
            return R.error(400,"params companyKey or appId error");
        }
        try {
            //获取今天日期
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String now = df.format(new Date());
            Date endDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(now);

            //app回调数据加1
            pathDataService.incrFromApp(pathEntity.getName(),companyKey,pathEntity.getAppName(),appId,endDate);
            //生成1-100
            int p = new Random().nextInt(100)+1;
            int weight = pathEntity.getWeight();
            logger.info("appApi p:"+p+" weight:"+weight);
            if (p<=weight){
                pathRequest(callBackPath);
                //回传渠道记录加1
                pathDataService.incrToPath(pathEntity.getName(),companyKey,pathEntity.getAppName(),appId,endDate);
            }
            return R.ok("success");
        } catch (Exception e){
            logger.error("appApi request fail!",e);
            return R.error(500,"appApi request fail!");
        }

    }

    private boolean checkParams(String companyKey, String appId, String callback) {
        if (appId == null || appId==""){
            return false;
        }
        if (callback == null || callback == ""){
            return false;
        }
        if (companyKey == null || companyKey == ""){
            return false;
        }
        return true;
    }

    private void pathRequest(String callback) throws Exception{
        logger.info("appApi pathRequest url:"+callback);
        Connection connection =Jsoup.connect(callback).ignoreContentType(true);
        try {
            Document document = connection.header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                    .timeout(5000).get();
            if (document!= null){
                logger.info("pathApi request document:"+document);
                logger.info("pathApi request success!");
            }
        } catch (Exception e){
            logger.error("appApi request fail!",e);
            throw e;
        }
    }
    private void pathRequest1(String url,String callback) throws Exception{
        logger.info("appApi pathRequest url:"+url);
        Connection connection =Jsoup.connect(url).ignoreContentType(true);
        connection.data("callback",callback);
        try {
            Document document = connection.header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                    .timeout(5000).get();
            if (document!= null){
                logger.info("pathApi request document:"+document);
                logger.info("pathApi request success!");
            }
        } catch (Exception e){
            logger.error("appApi request fail!",e);
            throw e;
        }
    }
    /**
     * 请求
     */
    @RequestMapping("/testApp")
    public R testApp(@RequestParam Map<String, Object> params,HttpServletResponse response) {
        try {
            pathRequest1("http://39.96.13.4:8080/gf/appApi/testApp?idfa=D6C71C4F-513C-4574-9C5B-8DA597AEF59C&ip=223.72.86.46","http://39.96.13.4:8080/gf/appApi/requesta?appId=1361571064&companyKey=daiding&callback=aHR0cHM6Ly93d3cuYmFpZHUuY29tL2E/YT0x");

        } catch (Exception e){
            logger.error("error",e);
        }
        logger.info("appApi testApp"+params);

        return R.ok();

    }
    /**
     * 请求
     */
    @RequestMapping("/testPath")
    public R testPath(@RequestParam Map<String, Object> params,HttpServletResponse response) {
        try {
            pathRequest("http://39.96.13.4:8080/gf/appApi/testApp?idfa=D6C71C4F-513C-4574-9C5B-8DA597AEF59C&ip=223.72.86.46&callback=http%3a%2f%2f39.96.13.4%3a8080%2fgf%2fappApi%2frequesta%3fappId%3d1361571064%26companyKey%3ddaiding%26callback%3daHR0cHM6Ly93d3cuYmFpZHUuY29tL2E%2fYT0x");

        } catch (Exception e){
            logger.error("error",e);
        }
        logger.info("appApi testPath"+params);
        return R.ok();

    }
}
