package com.learn.controller;

import com.learn.dao.FromAppDao;
import com.learn.dao.ToPathDao;
import com.learn.entity.PathEntity;
import com.learn.service.DataService;
import com.learn.service.PathDataService;
import com.learn.service.PathService;
import com.learn.utils.R;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;


/**
 * 文本信息
 */
@RestController
@RequestMapping("appsApi")
public class AppsApiController extends AbstractController {
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
    public R request(@RequestParam Map<String, Object> params,HttpServletResponse response) {

        logger.info("appsApi request"+params);
        //获得参数 companyKey_appId
        String aff_sub = (String)params.get("aff_sub");
        String companyKey=aff_sub.split("_")[0];

        String appId = aff_sub.split("_")[1];
        //校验pathInfo
        //通过，则获得pathInfo
        PathEntity pathEntity = pathService.check(companyKey,appId);
        if (pathEntity == null){
            logger.info("appsApi request fail parms:"+params);
            return R.error("参数有误!");
        }
        //base64加密的
        //进行解密
        String sub_affiliate_id = (String) params.get("sub_affiliate_id");

        try {
            sub_affiliate_id = URLDecoder.decode(sub_affiliate_id,"utf-8");
            sub_affiliate_id = new String(base64.decode(sub_affiliate_id), "UTF-8");
        } catch (Exception e){
            logger.info("解密失败",e);
        }

        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            String now = df.format(new Date());
            Date endDate = (new SimpleDateFormat("yyyy-MM-dd")).parse(now);

            //app回调数据加1
            //增加上报数据
            pathDataService.incrFromApp(pathEntity.getName(),companyKey,pathEntity.getAppName(),appId,endDate);
            //概率回调渠道
            //生成1-100
            int p = new Random().nextInt(100)+1;
            int weight = pathEntity.getWeight();
            logger.info("appApi p:"+p+" weight:"+weight);
            if (p<=weight){
                //不用编码
                String callback = sub_affiliate_id;
                pathRequest(callback);

                //回传渠道记录加1
                pathDataService.incrToPath(pathEntity.getName(),companyKey,pathEntity.getAppName(),appId,endDate);
            }
            return R.ok();
        } catch (Exception e){
            logger.error("appsApi request fail!",e);
            return R.error("appsApi request fail!");
        }


    }

    private void pathRequest(String callback) {
        logger.info("appsApi request pathRequest:"+callback);
        Connection connection =Jsoup.connect(callback).ignoreContentType(true);
        try {
            Document document = connection.header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Content-Type", "application/json;charset=UTF-8")
                    .header("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                    .timeout(5000).get();
            if (document!= null){
                logger.info("appsApi request document:"+document);
                logger.info("appsApi request success!");
            }
        } catch (Exception e){
            logger.error("appsApi request fail!",e);
        }
    }





}
