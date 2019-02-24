package com.learn.dao;

import com.learn.entity.UserPathEntity;

import java.util.List;

public interface UserPathDao extends BaseDao<UserPathEntity> {
    public List<UserPathEntity> getAllByUserId(String uid);
}
