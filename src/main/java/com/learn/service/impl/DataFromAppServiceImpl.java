package com.learn.service.impl;

import com.learn.dao.DataFromAppDao;
import com.learn.dao.DataFromPathDao;
import com.learn.entity.DataFromAppEntity;
import com.learn.service.DataFromAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("dataFromAppService")
public class DataFromAppServiceImpl implements DataFromAppService {
	@Autowired
	private DataFromAppDao dataDao;

										

	
	@Override
	public DataFromAppEntity queryObject(Long id){
		DataFromAppEntity entity = dataDao.queryObject(id);

										
		return entity;
	}
	
	@Override
	public List<DataFromAppEntity> queryList(Map<String, Object> map){
        List<DataFromAppEntity> list = dataDao.queryList(map);
		return list;
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return dataDao.queryTotal(map);
	}
	
	@Override
	public void save(DataFromAppEntity data){
		dataDao.save(data);
	}
	
	@Override
	public void update(DataFromAppEntity data){
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
