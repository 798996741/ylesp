package com.xxgl.service.system.impl;

import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.Jurisdiction;
import com.fh.util.UuidUtil;
import com.xxgl.entity.Dynamic;
import com.xxgl.service.system.TcolumnManager;
import com.xxgl.service.system.TemplatesManager;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.system.DynamicManager;

import org.springframework.web.servlet.ModelAndView;

/** 
 * 说明： 动态表管理
 * 创建人：huangjianling
 * 创建时间：2019-06-18
 * @version
 */
@Service("dynamicService")
public class DynamicService implements DynamicManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Resource(name="tcolumnService")
	private TcolumnManager tcolumnService;

	@Resource(name="templatesService")
	private TemplatesManager templateService;
	
	

	@Override
	public void callRefreshMV() throws Exception {
		dao.findForObject("DynamicMapper.callRefreshMV", "");
	}

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception{
		dao.save("DynamicMapper.save", pd);
	}
	
	public void savePerson(PageData pd) throws Exception{
		dao.save("DynamicMapper.savePerson", pd);
	}
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception{
		dao.delete("DynamicMapper.delete", pd);
	}
	/**删除人员信息
	 * @param pd
	 * @throws Exception
	 */
	
	public void deletePerson(PageData pd) throws Exception{
		dao.update("DynamicMapper.deletePerson", pd);
	}
	
	

	/**修改 type 0为常规表 1为方案表 需要对PLAN_ID特殊处理
	 * @param pd,type
	 * @throws Exception
	 */
	public void edit(PageData pd,int type) throws Exception{
		if(type == 1) {
			dao.update("DynamicMapper.editByPlanID", pd);
		}else{
			dao.update("DynamicMapper.edit", pd);
		}
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>)dao.findForList("DynamicMapper.datalistPage", page);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getAllList(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("DynamicMapper.datalist", pd);
	}
	
	/**根据条件列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getListByCondition(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("DynamicMapper.datalistByCondition", pd);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("DynamicMapper.listAll", pd);
	}
	
	/**
	 * 查询会议辅助表详情
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> getMeetDetails(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("DynamicMapper.getMeetDetails", pd);
	}
	
	/**通过TEMPLATE_ID获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByTemId(PageData pd) throws Exception{
		return (PageData)dao.findForObject("DynamicMapper.findByTemId", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception{
		return (PageData)dao.findForObject("DynamicMapper.findById", pd);
	}
	
	/**去重
	 * @param pd
	 * @throws Exception
	 */
	public PageData checkRepeat(PageData pd) throws Exception{
		return (PageData)dao.findForObject("DynamicMapper.checkRepeat", pd);
	}
	
	/**查询最大编码
	 * @param pd
	 * @throws Exception
	 */
	public PageData findMaxCode(PageData pd) throws Exception{
		return (PageData)dao.findForObject("DynamicMapper.findMaxCode", pd);
	}
	
	/**新增人员表时调用更新v_person视图
	 * @throws Exception
	 */
	public void callUpdate() throws Exception{
		dao.findForObject("DynamicMapper.callUpdate", "");
	}

	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Dynamic> listByParentId(String parentId) throws Exception {
		return (List<Dynamic>) dao.findForList("DynamicMapper.listByParentId", parentId);
	}

	/**
	 * 通过ID获取表数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public PageData listById(PageData pd) throws Exception {
		return (PageData) dao.findForObject("DynamicMapper.listById", pd);
	}
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Dynamic> listTree(String parentId) throws Exception {
		List<Dynamic> valueList = this.listByParentId(parentId);
		for(Dynamic fhentity : valueList){
			fhentity.setTreeurl("dynamic/list.do?ID="+fhentity.getID()+"&C_PHYSICSNAME="+fhentity.getC_PHYSICSNAME()+"&RESOURCE_TYPE="+fhentity.getTYPE());
			fhentity.setSubDynamic(this.listTree(fhentity.getID()));
			fhentity.setTarget("treeFrame");
		}
		return valueList;
	}
	
	/**
	 * 通过ID获取其子级列表
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Dynamic> listByParentId_child(PageData pd) throws Exception {
		return (List<Dynamic>) dao.findForList("DynamicMapper.listByParentId_child", pd);
	}
	
	/**
	 * 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @param MENU_ID
	 * @return
	 * @throws Exception
	 */
	public List<Dynamic> listTree_child(String parentId, String cPhysicsName, String templateId) throws Exception {
		PageData pd = new PageData();
		pd.put("PARENT_ID", parentId);
		pd.put("C_PHYSICSNAME", cPhysicsName);
		List<Dynamic> valueList = this.listByParentId_child(pd);
		for(Dynamic fhentity : valueList){
			fhentity.setTreeurl("dynamic/child_list.do?ID="+fhentity.getID()+"&C_PHYSICSNAME="+cPhysicsName+"&TEMPLATE_ID="+templateId);
			fhentity.setSubDynamic(this.listTree_child(fhentity.getID(), cPhysicsName, templateId));
			fhentity.setTarget("treeFrame_child");
		}
		return valueList;
	}
	
	/**删除关联表数据
	 * @param pd
	 * @throws Exception
	 */
	public void deleteRelationData(PageData pd) throws Exception{
		dao.delete("DynamicMapper.deleteRelationData", pd);
	}


	public List<PageData> recelistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("DynamicMapper.recelistPage", page);
	}

	public void editDynamic(PageData pd,String type) throws Exception{
		Page page = new Page();
		pd.put("I_TYPE", 2);
		page.setPd(pd);
		List<PageData> varList = tcolumnService.list(page);	//列出其他类型的字段，即编辑页面上显示的字段
		PageData tableInfo = templateService.findById(pd);
		int saveType = tableInfo.getString("TYPE").equals("005-10")?1:0;
		String columnStr = tableInfo.getString("TYPE").equals("005-10")?"ID,PLAN_ID,":"ID,";
		String valueStr = tableInfo.getString("TYPE").equals("005-10")?"#{ID},#{PLAN_ID},":"#{ID},";
		String updateSql="";
		for(PageData pda : varList){
			if(pda.getString("ISNEW").equals("1") || (pd.getString("C_PHYSICSNAME").equals("TB_WHOJ3B7Y2K8HEU0M") && pda.getString("C_FIELDNAME").equals("NEWS_REPORT"))) {
				if(pd.containsKey(pda.getString("C_FIELDNAME") )){
					if ("2".equals(String.valueOf(pda.get("I_FIELDTYPE")))) {    //日期
						valueStr += "to_date(#{" + pda.getString("C_FIELDNAME") + "}, 'yyyy-MM-dd'),";
						updateSql += pda.getString("C_FIELDNAME") + "= to_date(#{" + pda.getString("C_FIELDNAME") + "}, 'yyyy-MM-dd'),";
					} else {
						valueStr += "#{" + pda.getString("C_FIELDNAME") + "},";
						updateSql += pda.getString("C_FIELDNAME") + "= #{" + pda.getString("C_FIELDNAME") + "},";
					}
					columnStr += pda.getString("C_FIELDNAME") + ",";
				}
			}
		}
		if(!"".equals(columnStr)){
			columnStr = columnStr.substring(0, columnStr.length() - 1);
			pd.put("columnStr", columnStr);
		}
		if(!"".equals(valueStr)){
			valueStr = valueStr.substring(0, valueStr.length() - 1);
			pd.put("valueStr", valueStr);
		}
		if(!"".equals(updateSql)){
			updateSql = updateSql.substring(0, updateSql.length() - 1);
			pd.put("updateSql", updateSql);
		}
		if(!pd.containsKey("ID")){
			String id = UuidUtil.get32UUID();
			pd.put("ID", id);	//主键
		}
		pd.put("CREATEMAN", Jurisdiction.getUsername());	//创建人

		if(type.equals("save")){
			this.save(pd);
		}else{
			if(!"".equals(updateSql)){
				this.edit(pd,saveType);
			}
		}
	}

	@Override
	public void deletePersonByid(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.update("DynamicMapper.edit", pd);
		
	}

	@Override
	public void editDynamic(PageData pd, String type, String tableName)
			throws Exception {
		// TODO Auto-generated method stub
		
	}


}

