package com.learn.service;

import com.learn.entity.DataEntity;

import java.util.List;
import java.util.Map;

/**
 * 文本信息
 * 
 */
public interface DataService {
    /**
    * 查询
	* @return
	*/
	DataEntity queryObject(Long id);

    /**
    * 查询列表
    * @return
    */
	List<DataEntity> queryList(Map<String, Object> map);

    /**
    * 查询总数
    * @return
    */
	int queryTotal(Map<String, Object> map);

    /**
    * 保存
    * @return
    */
	void save(DataEntity data);

    /**
    * 修改
    * @return
    */
	void update(DataEntity data);

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
