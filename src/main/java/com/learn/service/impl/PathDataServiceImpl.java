package com.learn.service.impl;

import com.learn.dao.PathDataDao;
import com.learn.entity.PathDataEntity;
import com.learn.entity.PathEntity;
import com.learn.service.PathDataService;
import com.learn.utils.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("pathDataService")
public class PathDataServiceImpl implements PathDataService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private PathDataDao pathDataDao;
    @Override
    public List<PathDataEntity> queryPathData(Map<String, Object> map) {
        PathDataEntity pathDataEntity = new PathDataEntity();
        pathDataEntity = pathDataDao.queryWithDate1(map);
        //查询pathData的基本信息
        //分成多个请求  先查fromPathCount  再查 fromAppCount 以此类推
        int fromPathCount = pathDataDao.queryFromPath(map);
        int fromAppCount = pathDataDao.queryFromApp(map);
        int fromAppCountTrue = fromAppCount;
        int toPathCount = pathDataDao.queryToPath(map);
        int toPathCountTrue = toPathCount;

        pathDataEntity.setFromPathCount(Long.valueOf(fromPathCount));
        pathDataEntity.setFromAppCount(Long.valueOf(fromAppCount));
        pathDataEntity.setFromAppTrueCount(Long.valueOf(fromAppCountTrue));
        pathDataEntity.setToPathCount(Long.valueOf(toPathCount));
        pathDataEntity.setToPathTrueCount(Long.valueOf(toPathCountTrue));
        List<PathDataEntity> list = new ArrayList<>();
        list.add(pathDataEntity);
        return list;
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return 1;
    }

    @Override
    public boolean incrFromPath(String path, String companyKey,String app, String appId, Date endDate) {
        Map<String, Object> map =new HashMap<>();
        map.put("companyKey",companyKey);
        map.put("appId",appId);
        map.put("endDate",endDate);
        PathDataEntity temp = pathDataDao.queryWithDate(map);
        if (temp==null){
            //判断 有没有今天的数据 否则加不上 没有则创建再加
            PathDataEntity pathDataEntity = new PathDataEntity();
            pathDataEntity.setAppId(appId);
            pathDataEntity.setApp(app);
            pathDataEntity.setPath(path);
            pathDataEntity.setCompanyKey(companyKey);
            pathDataEntity.setFromPathCount(0L);
            pathDataEntity.setFromAppCount(0L);
            pathDataEntity.setFromAppTrueCount(0L);
            pathDataEntity.setToPathCount(0L);
            pathDataEntity.setToPathTrueCount(0L);
            try {
                pathDataEntity.setDaytime(endDate);

            } catch (Exception e){
                throw new RuntimeException(e);
            }
            pathDataDao.save(pathDataEntity);
        }
        pathDataDao.incrFromPath(map);
        return true;
    }
    @Override
    public boolean incrFromApp(String path, String companyKey,String app, String appId, Date endDate) {
        Map<String, Object> map =new HashMap<>();
        map.put("companyKey",companyKey);
        map.put("appId",appId);
        map.put("endDate",endDate);
        PathDataEntity temp = pathDataDao.queryWithDate(map);
        if (temp==null){
            //判断 有没有今天的数据 否则加不上 没有则创建再加
            PathDataEntity pathDataEntity = new PathDataEntity();
            pathDataEntity.setAppId(appId);
            pathDataEntity.setApp(path);
            pathDataEntity.setPath(path);
            pathDataEntity.setCompanyKey(companyKey);
            pathDataEntity.setFromPathCount(0L);
            pathDataEntity.setFromAppCount(0L);
            pathDataEntity.setFromAppTrueCount(0L);
            pathDataEntity.setToPathCount(0L);
            pathDataEntity.setToPathTrueCount(0L);
            try {
                pathDataEntity.setDaytime(endDate);

            } catch (Exception e){
                throw new RuntimeException(e);
            }
            pathDataDao.save(pathDataEntity);
        }
        pathDataDao.incrFromAppWithTrue(map);
        return true;
    }

    @Override
    public boolean incrToPath(String path, String companyKey,String app, String appId, Date endDate) {
        Map<String, Object> map =new HashMap<>();
        map.put("companyKey",companyKey);
        map.put("appId",appId);
        map.put("endDate",endDate);
        //不用判断 上层已经判断
        pathDataDao.incrToPathWithTrue(map);
        return true;
    }
}
