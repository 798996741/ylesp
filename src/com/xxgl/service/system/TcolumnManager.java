package com.xxgl.service.system;

import java.util.List;
import java.util.Map;

import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 字段管理接口
 * 创建人：FH Q351412933
 * 创建时间：2019-06-13
 * @version
 */
public interface TcolumnManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**删除在界面上面被删除的字段
	 * @param pd
	 * @throws Exception
	 */
	public void deleteColumn(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**列出要删除的字段
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findDeleteColumns(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**创建表格/添加或删除表格中的列
	 * @param pd
	 * @throws Exception
	 */
	public PageData dynamicTable(PageData pd)throws Exception;
	
	/**通过条件获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCondition(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * 根据模板id获取数据
	 */
	public List<PageData> findByTemplateId(PageData pd) throws Exception;
	
	/**
	 * 根据模板id和字段名称查找
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByTemplate(PageData pd) throws Exception;
	
	
	/**根据模板id集合查找模板列信息
	 * @param TEMPLATEIDS
	 * @throws Exception
	 */
	public List<PageData> getTemplateColumns(String[] TEMPLATEIDS)throws Exception;

	/**
	 * 根据模板获取字段信息集合
	 * @param TEMPLATE_ID
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getTemplateColSet(String TEMPLATE_ID)throws Exception;

	/**
	 * 获取模板查询字段str
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String getSelectStr(PageData pd)throws Exception;

	/**
	 * 获取字段详情
	 */
	public PageData getColResult(PageData pd) throws Exception;

	/**
	 * 根据模板表 获取列字段信息
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getTemplateColumns(PageData pd)throws Exception;
}

