package com.learn.dao;

import com.learn.entity.DataFromAppEntity;


public interface DataFromAppDao extends BaseDao<DataFromAppEntity> {
    public DataFromAppEntity getDataFromPathByUID(String uid);
}
