package com.learn.utils;

import com.baidu.aip.nlp.AipNlp;
import org.json.JSONObject;

public class Sample {
    //设置APPID/AK/SK
    public static final String APP_ID = "10984195";
    public static final String API_KEY = "pL6jwmTszKUNm2IaOa4gFG43";
    public static final String SECRET_KEY = "kd2VmFM2U8u4gWGC81UOKWeOEmHc7h1I";

    public static void main(String[] args) {
        // 初始化一个AipNlp
        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);


        // 调用接口
        String text = "百度是一家高科技公司";
        JSONObject res = client.sentimentClassify(text, null);
        System.out.println(res.toString(2));

    }
}