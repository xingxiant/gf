package com.learn.dao;

import com.learn.entity.PathDataEntity;

import java.util.Map;

public interface PathDataDao extends BaseDao<PathDataEntity> {
    public int saveReturn(PathDataEntity pathDataEntity);

    public PathDataEntity queryWithDate(Map<String, Object> map);

    public PathDataEntity queryWithDate1(Map<String, Object> map);

    public  int queryFromPath(Map<String, Object> map);

    public  int queryFromApp(Map<String, Object> map);

    public  int queryToPath(Map<String, Object> map);

    public int incrFromPath(Map<String, Object> map);

    public int incrFromAppWithTrue(Map<String, Object> map);

    public int incrToPathWithTrue(Map<String, Object> map);
}
