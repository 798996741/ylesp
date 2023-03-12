package com.xxgl.service.system;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.entity.Dynamic;

/** 
 * 说明： 动态表管理接口
 * 创建人：huangjianling
 * 创建时间：2019-06-18
 * @version
 */
public interface DynamicManager{

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
	
	public void deletePerson(PageData pd) throws Exception;
	
	public void deletePersonByid(PageData pd) throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception;

	public void edit(PageData pd, int type) throws Exception;
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception;
	
	/**
	 * 查询会议辅助表详情
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> getMeetDetails(PageData pd) throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> getAllList(PageData pd) throws Exception;
	
	/**根据条件列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> getListByCondition(PageData pd) throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd) throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception;
	
	/**通过TEMPLATE_ID获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByTemId(PageData pd) throws Exception;
	
	/**去重
	 * @param pd
	 * @throws Exception
	 */
	public PageData checkRepeat(PageData pd) throws Exception;
	
	/**查询最大编码
	 * @param pd
	 * @throws Exception
	 */
	public PageData findMaxCode(PageData pd) throws Exception;
	
	/**新增人员表时调用更新v_person视图
	 * @throws Exception
	 */
	public void callUpdate() throws Exception;
	public void callRefreshMV() throws Exception;
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Dynamic> listByParentId(String parentId) throws Exception;

	public PageData listById(PageData pd) throws Exception;
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Dynamic> listTree(String parentId) throws Exception;
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public List<Dynamic> listByParentId_child(PageData pd) throws Exception;
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Dynamic> listTree_child(String parentId, String cPhysicsName, String templateId) throws Exception;
	
	/**删除关联表数据
	 * @param pd
	 * @throws Exception
	 */
	public void deleteRelationData(PageData pd) throws Exception;
	
	
	/**
	 * 公文-收文信息集合
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public  List<PageData> recelistPage(Page page) throws Exception;

	/**新增/修改（简单动态表）
	 * @param pd
	 * @throws Exception
	 */
	public void editDynamic(PageData pd, String type) throws Exception;

	/**
	 * 新增/修改（动态表）
	 * @param pd
	 * @param type
	 * @param tableName 动态表名
	 * @throws Exception
	 */
	public void editDynamic(PageData pd, String type, String tableName) throws Exception;
}

