package com.learn.service;

import com.learn.entity.WordEntity;

import java.util.List;
import java.util.Map;

/**
 * 情感词语
 * 
 */
public interface WordService {
    /**
    * 查询
	* @return
	*/
	WordEntity queryObject(Long id);

    /**
    * 查询列表
    * @return
    */
	List<WordEntity> queryList(Map<String, Object> map);

    /**
    * 查询总数
    * @return
    */
	int queryTotal(Map<String, Object> map);

    /**
    * 保存
    * @return
    */
	void save(WordEntity word);

    /**
    * 修改
    * @return
    */
	void update(WordEntity word);

    /**
    * 删除
    * @return
    */
	void delete(Long id);

    /**
    * 批量删除
    * @return
    */
	void deleteBatch(Long[] ids);
}
