package com.xxgl.service.system;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 模板库管理接口
 * 创建人：351412933
 * 创建时间：2019-06-10
 * @version
 */
public interface TemplatesManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd) throws Exception;
	
	public List<PageData> getSetList(PageData pd) throws Exception;
	
	public List<PageData> getSplitList(PageData pd) throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception;
	
	/**通过表名获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCPhysicsName(PageData pd) throws Exception;
	
	/**通过名字获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByName(PageData pd) throws Exception;
	
	/**通过类型获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByType(PageData pd) throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception;
	
	/**
	 * 获取关联表格数量
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getRelationTableNum(PageData pd)throws Exception;
	
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void saveDynamicFrom(PageData pd, List<String[]> fieldList) throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void editDynamicFrom(PageData pd, List<String[]> fieldList) throws Exception;
	
	/**
	 * 根据模板类型查询数据  '${TYPE}%'
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getTemplatesByType(PageData pd)throws Exception;
	
}

