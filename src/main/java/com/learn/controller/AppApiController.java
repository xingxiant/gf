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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @RequestMapping("/request")
    public R request(@RequestParam Map<String, Object> params,HttpServletResponse response) {

        logger.info("appApi request"+params);
        //获得参数 companyKey appId
        String companyKey = (String)params.get("companyKey");
        String appId = (String) params.get("appId");
        //base64加密的
        //进行解密
        String callBackPath = (String) params.get("callback");
        try {
            callBackPath = new String(base64.decode(callBackPath), "UTF-8");
        } catch (Exception e){
            logger.info("解密失败",e);
        }

        //校验pathInfo
        //通过，则获得pathInfo
        PathEntity pathEntity = pathService.check(companyKey,appId);
        if (pathEntity == null){
            logger.info("appApi request fail parms:"+params);
            return R.error("参数有误!");
        }
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String now = df.format(new Date());
            Date endDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(now);

            //app回调数据加1
            //增加上报数据
            pathDataService.incrFromApp(pathEntity.getName(),companyKey,pathEntity.getAppName(),appId,endDate);
            //概率回调渠道
            int p = (int)(1+Math.random()*(100-1+1));
            p=89;
            if (p<90){
                //不用编码
                String callback = callBackPath;
                pathRequest(callback);

                //回传渠道记录加1
                pathDataService.incrToPath(pathEntity.getName(),companyKey,pathEntity.getAppName(),appId,endDate);
            }
            return R.ok();
        } catch (Exception e){
            logger.error("appApi request fail!",e);
            return R.error("appApi request fail!");
        }


    }

    private void pathRequest(String callback) {
        logger.info("appApi request pathRequest:"+callback);
        Connection connection =Jsoup.connect(callback).ignoreContentType(true);
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

    /**
     * 请求
     */
    @RequestMapping("/testApp")
    public R testApp(@RequestParam Map<String, Object> params,HttpServletResponse response) {

        logger.info("appApi testApp"+params);

        return R.ok();

    }
    /**
     * 请求
     */
    @RequestMapping("/testPath")
    public R testPath(@RequestParam Map<String, Object> params,HttpServletResponse response) {

        logger.info("appApi testPath"+params);
        return R.ok();

    }

    @RequestMapping("/testRequestApp")
    public R testRequestApp(@RequestParam Map<String, Object> params,HttpServletResponse response) {

        requestApp("http://rongkeweb.com/downloadReport","B4446091-5665-4FE3-9E2E-C097E7A9B385","","117.136.87.34","http%3A%2F%2F39.96.13.4%3A8080%2Fgf%2FappApi%2Frequest%3FcompanyKey%3DYeahmobi1%26appId%3D942443472%26callback%3DaHR0cDovL2dsb2JhbC55bXRyYWNraW5nLmNvbS9jb252P3RyYW5zYWN0aW9uX2lkPWY4MzA0ZWFjZC02ZTY3LTNmM2ItY2MwMzFhODk3Zjg2ZmE4YjdhZTM5YzMxYTBlNWM5MWUzMTE4NTUwN2Q4MzAwMTkmYWZmaWxpYXRlX2lkPTE%3D");

        return R.ok();

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
