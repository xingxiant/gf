package com.learn.dao;

import com.learn.entity.DataFromPathEntity;

public interface DataFromPathBigDao extends BaseDao<DataFromPathEntity> {
    public DataFromPathEntity getDataFromPathByUID(String uid);
}
