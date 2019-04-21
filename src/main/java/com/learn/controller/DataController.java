package com.learn.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baidu.aip.nlp.AipNlp;
import com.learn.dao.*;
import com.learn.entity.*;
import com.learn.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.utils.PageUtils;
import com.learn.utils.Query;
import com.learn.utils.R;

import javax.servlet.http.HttpServletResponse;


/**
 * 文本信息
 */
@RestController
@RequestMapping("data")
public class DataController extends AbstractController {
    @Autowired
    private DataService dataService;

    @Autowired
    private PathDataService pathDataService;

    @Autowired
    private PathService pathService;
    @Autowired
    private DataFromPathService dataFromPathService;
    @Autowired
    private DataFromAppService dataFromAppService;
    @Autowired
    private FromAppDao fromAppDao;
    @Autowired
    private ToPathDao toPathDao;
    @Autowired
    private PathDao pathDao;
    @Autowired
    private UserPathDao userPathDao;
    private FromPathDao fromPathDao;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");

        //查询列表数据
        Query query = new Query(params);

        List<DataEntity> dataList = dataService.queryList(query);
        int total = dataService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(dataList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }
    /**
     * 渠道信息列表
     */
    @RequestMapping("/pathList")
    public R pathDataList(@RequestParam Map<String, Object> params,HttpServletResponse response) {
        //查询列表数据
        Query query = new Query(params);

        List<PathEntity> dataList = pathService.queryPathData(query);
        int total = pathService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(dataList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }
    /**
     * 渠道请求列表列表
     */
    @RequestMapping("/dataFromPathList")
    public R dataFromPathList(@RequestParam Map<String, Object> params,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");

        //查询列表数据
        Query query = new Query(params);

        List<DataFromPathEntity> dataList = dataFromPathService.queryList(query, false);
        int total = dataFromPathService.queryTotal(query, false);

        PageUtils pageUtil = new PageUtils(dataList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }
    /**
     * 大点击渠道请求列表列表
     */
    @RequestMapping("/dataFromBigPathList")
    public R dataFromBigPathList(@RequestParam Map<String, Object> params,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");

        //查询列表数据
        Query query = new Query(params);

        List<DataFromPathEntity> dataList = dataFromPathService.queryList(query, true);
        int total = dataFromPathService.queryTotal(query, true);

        PageUtils pageUtil = new PageUtils(dataList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }


    /**
     * 广告请求列表列表
     */
    @RequestMapping("/dataFromAppList")
    public R dataFromAppList(@RequestParam Map<String, Object> params,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");

        //查询列表数据
        Query query = new Query(params);

        List<DataFromAppEntity> dataList = dataFromAppService.queryList(query);
        int total = dataFromAppService.queryTotal(query);

        PageUtils pageUtil = new PageUtils(dataList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 渠道下拉
     */
    @RequestMapping("/pathSelect")
    public R pathSelect(@RequestParam Map<String, Object> params,HttpServletResponse response) {

        //查询列表数据
        Query query = new Query(params);

        List<PathEntity> list = pathDao.queryAll();
        Map<String,List<Map<String,String>>> result = new HashMap<>();
        List<Map<String,String>> pathList = new ArrayList<>();
        Set<String> pathSet = new HashSet<>();
        //查出所有渠道
        for(PathEntity pathEntity : list){
            if (!pathSet.contains(pathEntity.getCompanyKey())){
                Map<String,String> pathMap = new HashMap<>();
                pathMap.put("name",pathEntity.getName());
                pathMap.put("value",pathEntity.getCompanyKey());
                pathSet.add(pathEntity.getCompanyKey());
                pathList.add(pathMap);
            }

        }
        //查出所有App
        List<Map<String,String>> appList = new ArrayList<>();
        Set<String> appSet = new HashSet<>();
        for(PathEntity pathEntity : list){
            if (!appSet.contains(pathEntity.getAppId())){
                Map<String,String> appMap = new HashMap<>();
                appMap.put("name",pathEntity.getAppName());
                appMap.put("value",pathEntity.getAppId());
                appSet.add(pathEntity.getAppId());
                appList.add(appMap);
            }

        }

        result.put("path",pathList);
        result.put("app",appList);

        return R.ok().put("result", result);
    }
    /**
     * 渠道数据列表
     */
    @RequestMapping("/pathDataList")
    public R pathList(@RequestParam Map<String, Object> params,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
//            //查询列表数据
//            Date startDate = (new SimpleDateFormat("yyyy-MM-dd")).parse("2018-11-18");
//

//            params.put("startDate",startDate);

//            params.put("appId",1);
//            params.put("companyKey",2);
//            Query query = new Query(params);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

            String now = df.format(new Date());
            String ago = df.format(new Date(0));
            String endDate = (String) params.get("endDate");
            String startDate = (String) params.get("startDate");
            if (StringUtils.isBlank(endDate)){
                params.put("endDate",now);
            }
            if (StringUtils.isBlank(startDate)){
                params.put("startDate",ago);
            }
            logger.info("pathDataList:"+params);
            List<PathDataEntity> dataList = pathDataService.queryPathData(params);
            logger.info("pathDataList:dataList:"+dataList);


            return R.ok().put("result", dataList);
        } catch (Exception e){
            return R.error(500,"get pathList fail");
        }


    }


    /**
     * 列表
     */
    @RequestMapping("/list2")
    public R list2(@RequestParam Map<String, Object> params,HttpServletResponse response) {
        Query query = new Query(params);
        List<DataEntity> dataList = dataService.queryList(query);
        return R.ok().put("list", dataList);
    }


    @Autowired
    WordService wordService;

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        DataEntity data = dataService.queryObject(id);

        return R.ok().put("data", data);
    }
    /**
     * 信息
     */
    @RequestMapping("/pathInfo/{id}")
    public R pathInfo(@PathVariable("id") Long id,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        PathEntity data = pathService.queryObject(id);

        return R.ok().put("data", data);
    }
    /**
     * 信息详情
     */
    @RequestMapping("/dataFromPathInfo/{id}")
    public R dataFromPathInfo(@PathVariable("id") Long id,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        DataFromPathEntity data = dataFromPathService.queryObject(id, false);

        return R.ok().put("data", data);
    }
    /**
     * 信息详情
     */
    @RequestMapping("/dataFromBigPathInfo/{id}")
    public R dataFromBigPathInfo(@PathVariable("id") Long id,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        DataFromPathEntity data = dataFromPathService.queryObject(id, true);

        return R.ok().put("data", data);
    }
    /**
     * 信息详情
     */
    @RequestMapping("/dataFromAppInfo/{id}")
    public R dataFromAppInfo(@PathVariable("id") Long id,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        DataFromAppEntity data = dataFromAppService.queryObject(id);

        return R.ok().put("data", data);
    }
    @RequestMapping("/sf2/{id}")
    public R sf2(@PathVariable("id") Long id) {
        //这种手撸算法佷特殊，主要是支持多个自定义的类别
        Map<String, Integer> r = new HashMap<>();
        Map<String, Integer> r2 = new HashMap<>();
        List<WordEntity> words = this.wordService.queryList(new HashMap<String, Object>());
        DataEntity data = this.dataService.queryObject(id);
        for (WordEntity w : words) {
            if (data.getContent().contains(w.getValue())) {
                Integer i = r.get(w.getType1());
                if (i == null) {
                    i = 1;
                } else {
                    i++;
                }
                r.put(w.getType1(), i);
                r2.put(w.getType(), i);
            }
        }

        return R.ok().put("r", r).put("r2", r2);
    }

    //设置APPID/AK/SK
    public static final String APP_ID = "10984195";
    public static final String API_KEY = "pL6jwmTszKUNm2IaOa4gFG43";
    public static final String SECRET_KEY = "kd2VmFM2U8u4gWGC81UOKWeOEmHc7h1I";

    @RequestMapping("/sf/{id}")
    public R sf(@PathVariable("id") Long id) {

        //NLP 算法

        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);


        // 调用接口
        JSONObject res = client.sentimentClassify(this.dataService.queryObject(id).getContent(), null);
        System.out.println(res.toString(2));

        return R.ok().put("data", res.toString(2));
    }


    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody DataEntity data) {


        //NLP 算法

        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);


        // 调用接口
        JSONObject res = client.sentimentClassify(data.getContent(), null);
        System.out.println(res.toString(2));

        JSONArray array = res.getJSONArray("items");
        JSONObject obj = array.getJSONObject(0);
        data.setFs(obj.getDouble("positive_prob"));

        dataService.save(data);

        return R.ok();
    }
    /**
     * 渠道保存
     */
    @RequestMapping("/pathSave")
    public R save(@RequestBody PathEntity data,HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        data.setCreateTime(new Timestamp(System.currentTimeMillis()));

        //transToUserPathEntity(data);

        pathService.save(data);

        return R.ok();
    }

    private void transToUserPathEntity(PathEntity data) {
        if (data.getUserIds() != null && !data.getUserIds().equals("")){
            //逗号分隔
            String userIds = data.getUserIds();
            String[] ids = userIds.split(",");
            for (String id : ids){
                UserPathEntity userPathEntity = new UserPathEntity();
                userPathEntity.setAppId(data.getAppId());
                userPathEntity.setAppName(data.getAppName());
                userPathEntity.setPathName(data.getName());
                userPathEntity.setCompanyKey(data.getCompanyKey());
                userPathEntity.setUserId(Long.parseLong(id));
                userPathDao.save(userPathEntity);
            }
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody DataEntity data) {

        //NLP 算法

        AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);


        // 调用接口
        JSONObject res = client.sentimentClassify(data.getContent(), null);
        System.out.println(res.toString(2));

        JSONArray array = res.getJSONArray("items");
        JSONObject obj = array.getJSONObject(0);
        data.setFs(obj.getDouble("positive_prob"));


        dataService.update(data);

        return R.ok();
    }

    /**
     * 渠道修改
     */
    @RequestMapping("/pathUpdate")
    public R update(@RequestBody PathEntity data,HttpServletResponse response) {

        response.setHeader("Access-Control-Allow-Origin", "*");

        pathService.update(data);

        return R.ok();
    }
    /**
     * 二级联动
     */
    @RequestMapping("/pathDataTwo")
    public R pathDataTwo( ) {
        List<Map<String, Object>> options = new ArrayList<>();
        Map<PathEntity, List<PathEntity>> map = pathService.twoDo();
        for(PathEntity  pathEntity : map.keySet()){
            Map<String, Object> option = new HashMap<>();
            option.put("lable",pathEntity.getName());
            option.put("value",pathEntity.getCompanyKey());
            Map<String,Object> cc = new HashMap<>();
            List<Map<String,String>> ll = new ArrayList<>();
            for (PathEntity aa: map.get(pathEntity)){
                Map<String,String> mm = new HashMap<>();
                mm.put("lable",aa.getAppName());
                mm.put("value",aa.getAppId());
                ll.add(mm);
            }
            option.put("children",cc);
            options.add(option);
        }
        return R.ok().put("result",options);
    }



}
