package com.learn.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

			

import com.learn.dao.WordDao;
import com.learn.entity.WordEntity;
import com.learn.service.WordService;



@Service("wordService")
public class WordServiceImpl implements WordService {
	@Autowired
	private WordDao wordDao;

										

	
	@Override
	public WordEntity queryObject(Long id){
			WordEntity entity = wordDao.queryObject(id);

										
		return entity;
	}
	
	@Override
	public List<WordEntity> queryList(Map<String, Object> map){
        List<WordEntity> list = wordDao.queryList(map);
        for(WordEntity entity : list){
																										}
		return list;
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return wordDao.queryTotal(map);
	}
	
	@Override
	public void save(WordEntity word){
		wordDao.save(word);
	}
	
	@Override
	public void update(WordEntity word){
		wordDao.update(word);
	}
	
	@Override
	public void delete(Long id){
		wordDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		wordDao.deleteBatch(ids);
	}
	
}
