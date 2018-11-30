package com.learn.controller;

import com.baidu.aip.nlp.AipNlp;
import com.google.gson.Gson;
import com.learn.entity.DataEntity;
import com.learn.service.DataService;
import com.learn.utils.MultipartFileUtil;
import com.learn.utils.R;
import com.learn.utils.RRException;
import com.learn.utils.TXTUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


/**
 * 文件上传
 *
 */
@RestController
@RequestMapping("file")
public class UploadController {

    public static String[] suffixs = {"IMG", "PNG", "JPG", "JPEG", "GIF", "BPM"};

    @Autowired
    private DataService dataService;


    //设置APPID/AK/SK
    public static final String APP_ID = "10984195";
    public static final String API_KEY = "pL6jwmTszKUNm2IaOa4gFG43";
    public static final String SECRET_KEY = "kd2VmFM2U8u4gWGC81UOKWeOEmHc7h1I";

    /**
     * 上传文件
     */
    @RequestMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws Exception {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
        MultipartHttpServletRequest re = (MultipartHttpServletRequest) request;
        for (MultipartFile f : re.getFiles("file")) {

            String url = MultipartFileUtil.uploadFile("/cdn", f, request);

            String content = TXTUtil.read(MultipartFileUtil.getRootPath(request) + url);
            DataEntity entity = new DataEntity();
            entity.setContent(content);


            //NLP 算法

            AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

            // 可选：设置网络连接参数
            client.setConnectionTimeoutInMillis(2000);
            client.setSocketTimeoutInMillis(60000);


            // 调用接口
            JSONObject res = client.sentimentClassify(content, null);
            
            //System.out.println(res.toString(2));

            JSONArray array = res.getJSONArray("items");
            JSONObject obj = array.getJSONObject(0);
            entity.setFs(obj.getDouble("positive_prob"));

            this.dataService.save(entity);

            Thread.sleep(200);


        }

        return R.ok();
    }


}
