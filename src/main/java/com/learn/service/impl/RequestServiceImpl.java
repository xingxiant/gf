package com.learn.service.impl;

import com.learn.dao.DataFromPathBigDao;
import com.learn.dao.DataFromPathDao;
import com.learn.dao.PathDataDao;
import com.learn.entity.DataFromPathEntity;
import com.learn.service.RequestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("requestService")
public class RequestServiceImpl implements RequestService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private DataFromPathDao dataFromPathDao;
    @Autowired
    private DataFromPathBigDao dataFromPathBigDao;
    @Override
    public boolean savePathRequest(DataFromPathEntity dataFromPathEntity) {
        String result = dataFromPathEntity.getReportResult();
        if (result != null && result.length() >= 50 ) {
            //保留前50
            result = result.substring(0,Math.min(50, result.length()));
            dataFromPathEntity.setReportResult(result);
        }
        dataFromPathDao.save(dataFromPathEntity);
        return true;
    }

    @Override
    public boolean savePathBigRequest(DataFromPathEntity dataFromPathEntity) {
        String result = dataFromPathEntity.getReportResult();
        if (result != null && result.length() >= 50 ) {
            //保留前50
            result = result.substring(0,Math.min(50, result.length()));
            dataFromPathEntity.setReportResult(result);
        }
        dataFromPathBigDao.save(dataFromPathEntity);
        return true;
    }

    @Override
    public boolean updatePathRequest(DataFromPathEntity dataFromPathEntity) {
        String result = dataFromPathEntity.getReportResult();
        if (result != null && result.length() >= 50 ) {
            //保留前50
            result = result.substring(0,Math.min(50, result.length()));
            dataFromPathEntity.setReportResult(result);
        }
        dataFromPathDao.update(dataFromPathEntity);
        return true;
    }

    @Override
    public DataFromPathEntity getPathResuestByUID(String uid) {
        return dataFromPathDao.getDataFromPathByUID(uid);
    }

    @Override
    public DataFromPathEntity getPathBigResuestByUID(String uid) {
        return dataFromPathBigDao.getDataFromPathByUID(uid);
    }
}
