package com.learn.dao;

import com.learn.entity.PathDataEntity;
import com.learn.entity.PathEntity;

import java.util.List;
import java.util.Map;

public interface PathDao extends BaseDao<PathEntity> {
    List<PathEntity> queryByAIdAndCpKey(PathEntity pathEntity);
    List<PathEntity> queryAll();

}
