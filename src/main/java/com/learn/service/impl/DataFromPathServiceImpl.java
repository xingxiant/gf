package com.learn.service.impl;

import com.learn.dao.DataDao;
import com.learn.dao.DataFromPathBigDao;
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

	@Autowired
	private DataFromPathBigDao dataBigDao;
	
	@Override
	public DataFromPathEntity queryObject(Long id, boolean isBig){
		if (isBig){
			return dataBigDao.queryObject(id);
		} else {
			return dataDao.queryObject(id);
		}
	}
	
	@Override
	public List<DataFromPathEntity> queryList(Map<String, Object> map, boolean isBig){
		if (isBig) {
			return dataBigDao.queryList(map);
		} else {
			return dataDao.queryList(map);
		}
	}
	
	@Override
	public int queryTotal(Map<String, Object> map, boolean isBig){
		if (isBig){
			return dataBigDao.queryTotal(map);
		}
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
    public void deleteBig(Long id) {
        dataBigDao.delete(id);
    }

    @Override
	public void deleteBatch(Long[] ids){
		dataDao.deleteBatch(ids);
	}
	
}
