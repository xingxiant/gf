package com.learn.controller;

import com.baidu.aip.nlp.AipNlp;
import com.learn.dao.DataDao;
import com.learn.dao.FromPathDao;
import com.learn.dao.PathDataDao;
import com.learn.entity.*;
import com.learn.service.DataService;
import com.learn.service.PathDataService;
import com.learn.service.PathService;
import com.learn.service.WordService;
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
    private PathService pathService;
    private Base64 base64 = new Base64();


    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 列表
     */
    @RequestMapping(value="/request")
    public R request(@RequestParam Map<String, Object> params) {
        try {
            logger.info("pathApi request"+params);
            String idfa= (String)params.get("idfa");
            String mac= (String)params.get("mac");
            if (mac == null){
                mac ="";
            }
            String ip= (String)params.get("ip");
            String companyKey = (String)params.get("companyKey");
            String appId = (String) params.get("appId");
            String callbackPath =((String)params.get("callback")).replace("&amp;","&");
            logger.info("加密前:"+callbackPath);
            //编码 base64
            byte[] callbackBytes = callbackPath.getBytes("UTF-8");
            callbackPath = base64.encodeToString(callbackBytes);
            logger.info("pathApi request callbackPath:"+callbackPath);
            //获得参数 idfa mac ip companyKey appId callabck
            //校验pathInfo
            //通过，则获得pathInfo
            PathEntity pathEntity = pathService.check(companyKey,appId);
            if (pathEntity == null){
                logger.info("pathApi request fail parms:"+params);
                return R.error("param error");
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

            String now = df.format(new Date());

            Date endDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(now);


            //增加上报数据
            pathDataService.incrFromPath(pathEntity.getName(),companyKey,pathEntity.getAppName(),appId,endDate);
            //设置app回调我们的接口
            //生成callback  我们自己的加渠道的
            String callback= pathEntity.getCallBackApp();
            //urlCode编码
            String str =java.net.URLEncoder.encode(callback+"&callback="+callbackPath,   "utf-8");
            callback = str;
            logger.info("pathApi request callback:"+callback);
            //请求App idfa mac ip callback
            String url = pathEntity.getAppHost();
            //参数使用ifa
            requestApp(url,idfa, mac,ip,callback);
            return R.ok();
        } catch (Exception e){
            logger.error("pathApi request fail",e);
            return R.error(500,"pathApi request fail");
        }

    }


    private void requestApp1(String url, String idfa, String mac, String ip, String callback) {
        logger.info(url);
        Connection connection =Jsoup.connect(url).ignoreContentType(true);
        connection.data("ifa",idfa);
        connection.data("mac",mac);
        connection.data("ip",ip);
        connection.data("callback",callback);
        try {
            Document document = connection.header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")

                    .timeout(5000).get();
            if (document!= null){
                logger.info("pathApi request success!");
            }
        } catch (Exception e){
            logger.error("pathApi request fail!",e);
        }
    }
    private void requestApp(String url, String idfa, String mac, String ip, String callback) {
        logger.info(url);
        Connection connection =Jsoup.connect(url).ignoreContentType(true);
        connection.data("IDFA",idfa);
        connection.data("chainId","24");
        connection.data("ipaddr",ip);
        connection.data("callback",callback);
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
        }
    }

}
