package com.learn.service;

import com.learn.entity.DataFromAppEntity;

public interface ResponseService {
    /**
     * 保存渠道请求
     *
     * @param dataFromAppEntity
     * @return
     */
    public boolean savePathRequest(DataFromAppEntity dataFromAppEntity);

    /**
     * 修改渠道请求
     *
     * @param dataFromAppEntity
     * @return
     */
    public boolean updatePathRequest(DataFromAppEntity dataFromAppEntity);

    /**
     * 获得请求记录通过uid
     * @param uid
     * @return
     */
    public DataFromAppEntity getPathResuestByUID(String uid);
}
