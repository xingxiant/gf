package com.learn.service;

import com.learn.entity.DataEntity;
import com.learn.entity.PathDataEntity;
import com.learn.entity.PathEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PathService {

    public List<PathEntity> queryPathData(Map<String, Object> map);

    int queryTotal(Map<String, Object> query);

    PathEntity queryObject(Long id);

    void save(PathEntity data);

    void update(PathEntity data);

    PathEntity check(String companyKey, String appId);

    Map<PathEntity,List<PathEntity>> twoDo();


}
