package com.learn.service.impl;

import com.learn.dao.PathDao;
import com.learn.dao.PathDataDao;
import com.learn.entity.PathDataEntity;
import com.learn.entity.PathEntity;
import com.learn.service.PathService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("pathService")
public class PathServiceImpl implements PathService {

    @Autowired
    private PathDao pathDao;
    @Autowired
    private PathDataDao pathDataDao;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public List<PathEntity> queryPathData(Map<String, Object> map) {
        List<PathEntity> list = pathDao.queryList(map);
        logger.info("PathServiceImpl queryPathData:"+(list));
        return list;
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return pathDao.queryTotal(map);
    }

    @Override
    public PathEntity queryObject(Long id) {
        PathEntity pathEntity;
        pathEntity = pathDao.queryObject(id);
        logger.info("PathServiceImpl queryObject:"+(id));
        return pathEntity;
    }


    @Override
    public void save(PathEntity data) {
        logger.info("PathServiceImpl save:"+data.toString());
        try {
            pathDao.save(data);
            //TODO 创建pathData 所有值都是0
            PathDataEntity pathDataEntity = new PathDataEntity();
            pathDataEntity.setAppId(data.getAppId());
            pathDataEntity.setApp(data.getAppName());
            pathDataEntity.setPath(data.getName());
            pathDataEntity.setCompanyKey(data.getCompanyKey());
            pathDataEntity.setFromPathCount(0L);
            pathDataEntity.setFromAppCount(0L);
            pathDataEntity.setFromAppTrueCount(0L);
            pathDataEntity.setToPathCount(0L);
            pathDataEntity.setToPathTrueCount(0L);
            pathDataEntity.setDaytime((new SimpleDateFormat("yyyy-MM-dd")).parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

            pathDataDao.save(pathDataEntity);
        } catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void update(PathEntity data) {
        logger.info("PathServiceImpl update:"+data.toString());
        pathDao.update(data);
    }

    @Override
    public PathEntity check(String companyKey, String appId) {
        PathEntity pathEntity = new PathEntity();
        pathEntity.setCompanyKey(companyKey);
        pathEntity.setAppId(appId);
        List<PathEntity> list = pathDao.queryByAIdAndCpKey(pathEntity);
        logger.info("list:"+list);
        if (list != null && list.size()==1){
            return list.get(0);
        }
        return null;
    }

    @Override
    public Map<PathEntity, List<PathEntity>> twoDo() {
        Map<PathEntity, List<PathEntity>> result = new HashMap<>();
        //查询所有信息
        List<PathEntity> list = pathDao.queryAll();
        List<PathEntity> list1 = pathDao.queryAll();
        logger.info("twoDo:"+list);
        for (PathEntity pathEntity : list){
            for (PathEntity in: list1){
                List<PathEntity> qudao = new ArrayList<>();

                if(pathEntity.getCompanyKey().equals(in.getCompanyKey())){
                    PathEntity temp= new PathEntity();
                    temp.setAppId(in.getAppId());
                    temp.setAppName(in.getAppName());
                    qudao.add(temp);
                }
                result.put(pathEntity,qudao);
            }
        }
        return result;
    }


}
