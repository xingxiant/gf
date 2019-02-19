package com.learn.service.impl;

import com.learn.dao.DataDao;
import com.learn.dao.DataFromPathDao;
import com.learn.entity.DataEntity;
import com.learn.entity.DataFromPathEntity;
import com.learn.service.DataFromPathService;
import com.learn.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("dataFromPathService")
public class DataFromPathServiceImpl implements DataFromPathService {
	@Autowired
	private DataFromPathDao dataDao;

										

	
	@Override
	public DataFromPathEntity queryObject(Long id){
		DataFromPathEntity entity = dataDao.queryObject(id);

										
		return entity;
	}
	
	@Override
	public List<DataFromPathEntity> queryList(Map<String, Object> map){
        List<DataFromPathEntity> list = dataDao.queryList(map);
        for(DataFromPathEntity entity : list){
																										}
		return list;
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return dataDao.queryTotal(map);
	}
	
	@Override
	public void save(DataFromPathEntity data){
		dataDao.save(data);
	}
	
	@Override
	public void update(DataFromPathEntity data){
		dataDao.update(data);
	}
	
	@Override
	public void delete(Long id){
		dataDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		dataDao.deleteBatch(ids);
	}
	
}
