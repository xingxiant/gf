package com.learn.service;

import com.learn.entity.DataEntity;
import com.learn.entity.DataFromPathEntity;

import java.util.List;
import java.util.Map;

/**
 * 文本信息
 * 
 */
public interface DataFromPathService {
    /**
    * 查询
	* @return
	*/
	DataFromPathEntity queryObject(Long id);

    /**
    * 查询列表
    * @return
    */
	List<DataFromPathEntity> queryList(Map<String, Object> map);

    /**
    * 查询总数
    * @return
    */
	int queryTotal(Map<String, Object> map);

    /**
    * 保存
    * @return
    */
	void save(DataFromPathEntity data);

    /**
    * 修改
    * @return
    */
	void update(DataFromPathEntity data);

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
