package com.learn.service;

import com.learn.entity.PathDataEntity;
import com.learn.utils.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PathDataService {

    public List<PathDataEntity> queryPathData(Map<String, Object> map);

    int queryTotal(Map<String, Object> query);

    public boolean incrFromPath(String path,String companyKey,String app,String appId,Date endDate);

    public boolean incrFromApp(String path,String companyKey,String app,String appId,Date endDate);
    public boolean incrToPath(String path,String companyKey,String app,String appId,Date endDate);
}
