package com.learn.dao;

import com.learn.entity.DataFromPathEntity;
import com.learn.entity.PathDataEntity;

import java.util.Map;

public interface DataFromPathDao extends BaseDao<DataFromPathEntity> {
    public DataFromPathEntity getDataFromPathByUID(String uid);
}
