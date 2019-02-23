package com.learn.service;

import com.learn.entity.DataFromAppEntity;

import java.util.List;
import java.util.Map;

/**
 * 文本信息
 * 
 */
public interface DataFromAppService {
    /**
    * 查询
	* @return
	*/
	DataFromAppEntity queryObject(Long id);

    /**
    * 查询列表
    * @return
    */
	List<DataFromAppEntity> queryList(Map<String, Object> map);

    /**
    * 查询总数
    * @return
    */
	int queryTotal(Map<String, Object> map);

    /**
    * 保存
    * @return
    */
	void save(DataFromAppEntity data);

    /**
    * 修改
    * @return
    */
	void update(DataFromAppEntity data);

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
