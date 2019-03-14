package com.learn.service.impl;

import com.learn.dao.DataFromAppDao;
import com.learn.dao.DataFromPathDao;
import com.learn.entity.DataFromAppEntity;
import com.learn.entity.DataFromPathEntity;
import com.learn.service.RequestService;
import com.learn.service.ResponseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("responseService")
public class ResponseServiceImpl implements ResponseService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DataFromAppDao dataFromAppDao;
    @Override
    public boolean savePathRequest(DataFromAppEntity dataFromAppEntity) {
        String result = dataFromAppEntity.getReportResult();
        if (result != null && result.length() >= 50 ) {
            //保留前50
            result = result.substring(0,Math.min(50, result.length()));
            dataFromAppEntity.setReportResult(result);
        }
        dataFromAppDao.save(dataFromAppEntity);
        return true;
    }

    @Override
    public boolean updatePathRequest(DataFromAppEntity dataFromAppEntity) {
        dataFromAppDao.update(dataFromAppEntity);
        return true;
    }

    @Override
    public DataFromAppEntity getPathResuestByUID(String uid) {
        return dataFromAppDao.getDataFromPathByUID(uid);
    }
}
