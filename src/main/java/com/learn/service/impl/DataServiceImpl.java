package com.learn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

			

import com.learn.dao.DataDao;
import com.learn.entity.DataEntity;
import com.learn.service.DataService;



@Service("dataService")
public class DataServiceImpl implements DataService {
	@Autowired
	private DataDao dataDao;

										

	
	@Override
	public DataEntity queryObject(Long id){
			DataEntity entity = dataDao.queryObject(id);

										
		return entity;
	}
	
	@Override
	public List<DataEntity> queryList(Map<String, Object> map){
        List<DataEntity> list = dataDao.queryList(map);
		return list;
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return dataDao.queryTotal(map);
	}
	
	@Override
	public void save(DataEntity data){
		dataDao.save(data);
	}
	
	@Override
	public void update(DataEntity data){
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
