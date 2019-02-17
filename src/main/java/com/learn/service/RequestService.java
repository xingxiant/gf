package com.learn.service;

import com.learn.entity.DataFromPathEntity;

public interface RequestService {
    /**
     * 保存渠道请求
     *
     * @param dataFromPathEntity
     * @return
     */
    public boolean savePathRequest(DataFromPathEntity dataFromPathEntity);

    /**
     * 修改渠道请求
     *
     * @param dataFromPathEntity
     * @return
     */
    public boolean updatePathRequest(DataFromPathEntity dataFromPathEntity);

    /**
     * 获得请求记录通过uid
     * @param uid
     * @return
     */
    public DataFromPathEntity getPathResuestByUID(String uid);
}
