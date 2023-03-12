package com.xxgl.service.system.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.UuidUtil;
import com.xxgl.service.system.TcolumnManager;
import com.xxgl.service.system.TemplatesManager;
import com.xxgl.utils.RelationUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

/** 
 * 说明： 模板库管理
 * 创建人：351412933
 * 创建时间：2019-06-10
 * @version
 */
@Service("templatesService")
public class TemplatesService implements TemplatesManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name="tcolumnService")
	private TcolumnManager tcolumnService;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception{
		dao.save("TemplatesMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception{
		dao.delete("TemplatesMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception{
		dao.update("TemplatesMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page) throws Exception{
		return (List<PageData>)dao.findForList("TemplatesMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("TemplatesMapper.listAll", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> getSetList(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("TemplatesMapper.getSetList", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> getSplitList(PageData pd) throws Exception{
		return (List<PageData>)dao.findForList("TemplatesMapper.getSplitList", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception{
		return (PageData)dao.findForObject("TemplatesMapper.findById", pd);
	}
	
	/**通过表名获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCPhysicsName(PageData pd) throws Exception{
		return (PageData)dao.findForObject("TemplatesMapper.findByCPhysicsName", pd);
	}
	
	/**通过名字获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByName(PageData pd) throws Exception{
		return (PageData)dao.findForObject("TemplatesMapper.findByName", pd);
	}
	
	/**通过类型获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByType(PageData pd) throws Exception{
		return (PageData)dao.findForObject("TemplatesMapper.findByType", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception{
		dao.delete("TemplatesMapper.deleteAll", ArrayDATA_IDS);
	}

	/**
	 * 获取关联表格数量
	 */
	public PageData getRelationTableNum(PageData pd) throws Exception {
		return (PageData)dao.findForObject("TemplatesMapper.getRelationTableNum", pd);
	}


	/**
	 * 动态生成表单
	 * 生成相应template
	 * 生成对应的column
	 * 生成对应的表
	 */
	@Transactional
	public void saveDynamicFrom(PageData pd,List<String[]> fieldList) throws Exception {
		pd.put("CREATEMAN", Jurisdiction.getUsername());
		String TEMPLATE_ID = UuidUtil.get32UUID();
		pd.put("TEMPLATE_ID", TEMPLATE_ID);			//主键
		pd.put("PARENT_ID", "0");
		this.save(pd);//生成模板
		PageData pd_column = new PageData();
		String sql = "";	//创表语句
		//基础字段ID
		pd_column.put("TCOLUMN_ID", UuidUtil.get32UUID());
		pd_column.put("TEMPLATE_ID", TEMPLATE_ID);			//外键
		pd_column.put("C_FIELDNAME", "ID");
		pd_column.put("C_DISPLAYLABEL", "主键");
		pd_column.put("I_FIELDTYPE", 1);
		pd_column.put("I_LEN", 32);
		pd_column.put("I_DEFAULT",  UuidUtil.get32UUID());
		pd_column.put("I_TYPE", 1);//字段分类(1是基础，2是其他)
		pd_column.put("I_DEC", 0);
		pd_column.put("IS_DIC", "否");
		pd_column.put("DIC_TYPE", "");
		pd_column.put("IS_QUOTE", "否");
		pd_column.put("QUOTE_TYPE", "");
		pd_column.put("SHOW_ORDER", 1);
		
		//设置新增的6个字段
		pd_column.put("ISNEW", "0");//是否在编辑页面显示 0否 1是
		pd_column.put("ISMUST", "1");//是否必填
		pd_column.put("ISQY", "0");//是否查询字段
		pd_column.put("ISLIST", "0");//是否在查询列表显示
		pd_column.put("ISFLOW", "0");//是否工作流表单字段
		pd_column.put("ISSELECT", "0");//是否在选择框列表显示
		pd_column.put("LAYOUT", "0");//布局 默认0
		
		tcolumnService.save(pd_column);
		
		//基础字段NAME
		pd_column.put("TCOLUMN_ID", UuidUtil.get32UUID());
		pd_column.put("C_FIELDNAME", "NAME");
		pd_column.put("C_DISPLAYLABEL", "名称");
		pd_column.put("I_FIELDTYPE", 2);
		pd_column.put("I_LEN", 200);
		pd_column.put("I_DEFAULT", "");
		pd_column.put("I_DEC", 0);
		pd_column.put("IS_DIC", "否");
		pd_column.put("DIC_TYPE", "");
		pd_column.put("IS_QUOTE", "否");
		pd_column.put("QUOTE_TYPE", "");
		pd_column.put("SHOW_ORDER", 2);
		pd_column.put("ISLIST", "0");//查询列表显示
		tcolumnService.save(pd_column);
		
		//基础字段CREATEDATE
		pd_column.put("TCOLUMN_ID", UuidUtil.get32UUID());
		pd_column.put("C_FIELDNAME", "CREATEDATE");
		pd_column.put("C_DISPLAYLABEL", "创建时间");
		pd_column.put("I_FIELDTYPE", 2);
		pd_column.put("I_LEN", 32);
		pd_column.put("I_DEFAULT", "now()");
		pd_column.put("I_DEC", 0);
		pd_column.put("IS_DIC", "否");
		pd_column.put("DIC_TYPE", "");
		pd_column.put("IS_QUOTE", "否");
		pd_column.put("QUOTE_TYPE", "");
		pd_column.put("SHOW_ORDER", 100);
		pd_column.put("ISLIST", "0");//查询列表显示
		tcolumnService.save(pd_column);
		
		//基础字段CREATEMAN
		pd_column.put("TCOLUMN_ID", UuidUtil.get32UUID());
		pd_column.put("C_FIELDNAME", "CREATEMAN");
		pd_column.put("C_DISPLAYLABEL", "创建人");
		pd_column.put("I_FIELDTYPE", 1);
		pd_column.put("I_LEN", 40);
		pd_column.put("I_DEFAULT", "");
		pd_column.put("I_DEC", 0);
		pd_column.put("IS_DIC", "否");
		pd_column.put("DIC_TYPE", "");
		pd_column.put("IS_QUOTE", "否");
		pd_column.put("QUOTE_TYPE", "");
		pd_column.put("SHOW_ORDER", 99);
		pd_column.put("ISLIST", "0");//查询列表显示
		tcolumnService.save(pd_column);
		
		//基础字段IS_DEL
		pd_column.put("TCOLUMN_ID", UuidUtil.get32UUID());
		pd_column.put("TEMPLATE_ID", TEMPLATE_ID);			//外键
		pd_column.put("C_FIELDNAME", "IS_DEL");
		pd_column.put("C_DISPLAYLABEL", "是否删除");
		pd_column.put("I_FIELDTYPE", 3);
		pd_column.put("I_LEN", 10);
		pd_column.put("I_DEFAULT", "0");
		pd_column.put("I_TYPE", 1);//字段分类(1是基础，2是其他)
		pd_column.put("I_DEC", 0);
		pd_column.put("IS_DIC", "否");
		pd_column.put("DIC_TYPE", "");
		pd_column.put("IS_QUOTE", "否");
		pd_column.put("QUOTE_TYPE", "");
		pd_column.put("SHOW_ORDER", 101);
		tcolumnService.save(pd_column);
		
		//树结构所必需的字段
		if(pd.getString("TREE_TYPE") != null && "1".equals(pd.getString("TREE_TYPE"))){	
			pd_column.put("TCOLUMN_ID", UuidUtil.get32UUID());
			pd_column.put("C_FIELDNAME", "PARENT_ID");
			pd_column.put("C_DISPLAYLABEL", "父节点");
			pd_column.put("I_FIELDTYPE", 1);
			pd_column.put("I_LEN", 40);
			pd_column.put("I_DEFAULT", "");
			pd_column.put("I_DEC", 0);
			pd_column.put("IS_DIC", "否");
			pd_column.put("DIC_TYPE", "");
			pd_column.put("IS_QUOTE", "否");
			pd_column.put("QUOTE_TYPE", "");
			pd_column.put("SHOW_ORDER", 98);
			pd_column.put("ISLIST", "1");//查询列表不显示
			tcolumnService.save(pd_column);
			pd_column.put("TCOLUMN_ID", UuidUtil.get32UUID());
			pd_column.put("C_FIELDNAME", "CODE");
			pd_column.put("C_DISPLAYLABEL", "编号");
			pd_column.put("I_FIELDTYPE", 1);
			pd_column.put("I_LEN", 100);
			pd_column.put("I_DEFAULT", "");
			pd_column.put("I_DEC", 0);
			pd_column.put("IS_DIC", "否");
			pd_column.put("DIC_TYPE", "");
			pd_column.put("IS_QUOTE", "否");
			pd_column.put("QUOTE_TYPE", "");
			pd_column.put("SHOW_ORDER", 96);
			tcolumnService.save(pd_column);
			pd_column.put("TCOLUMN_ID", UuidUtil.get32UUID());
			pd_column.put("C_FIELDNAME", "I_LEVEL");
			pd_column.put("C_DISPLAYLABEL", "等级");
			pd_column.put("I_FIELDTYPE", 1);
			pd_column.put("I_LEN", 8);
			pd_column.put("I_DEFAULT", "");
			pd_column.put("I_DEC", 0);
			pd_column.put("IS_DIC", "否");
			pd_column.put("DIC_TYPE", "");
			pd_column.put("IS_QUOTE", "否");
			pd_column.put("QUOTE_TYPE", "");
			pd_column.put("SHOW_ORDER", 97);
			tcolumnService.save(pd_column);
		}
		if(pd.getString("RELATION_TABLE")!=null && !pd.getString("RELATION_TABLE").equals("")) {
			pd_column.put("TCOLUMN_ID", UuidUtil.get32UUID());
			pd_column.put("C_FIELDNAME", "RELATION_ID");
			pd_column.put("C_DISPLAYLABEL", "关联ID");
			pd_column.put("I_TYPE", 1);
			pd_column.put("I_FIELDTYPE", 1);
			pd_column.put("I_LEN", 32);
			pd_column.put("I_DEFAULT", "");
			pd_column.put("I_DEC", 0);
			pd_column.put("IS_DIC", "否");
			pd_column.put("DIC_TYPE", "");
			pd_column.put("IS_QUOTE", "否");
			pd_column.put("QUOTE_TYPE", "");
			pd_column.put("SHOW_ORDER", 95);
			pd_column.put("ISLIST", "1");//查询列表不显示
			tcolumnService.save(pd_column);
		}
		sql = "CREATE TABLE " + pd.getString("C_PHYSICSNAME") + "( ID VARCHAR(200)  PRIMARY KEY , NAME VARCHAR(200),"
				+ "CREATEDATE DATE,CREATEMAN VARCHAR(40),PARENT_ID VARCHAR(40),CODE VARCHAR(100),I_LEVEL VARCHAR(8),IS_DEL int(1) DEFAULT 0 not null,";
		
		for(String[] str : fieldList){
			pd_column.put("TCOLUMN_ID", UuidUtil.get32UUID());
			pd_column.put("C_FIELDNAME", str[0]);//0是字段名
			pd_column.put("C_DISPLAYLABEL", str[2]);//2是显示中文名
			String type = "";
			//1是字段类型
			if("String".equals(str[1])){
				pd_column.put("I_FIELDTYPE", 1);
				type = "VARCHAR(" + str[4] + ")";
			}else if("Date".equals(str[1])){
				pd_column.put("I_FIELDTYPE", 2);
				type = "datetime";
			}else if("Integer".equals(str[1])){
				pd_column.put("I_FIELDTYPE", 3);
				type = "int(" + str[4] + ")";
			}else if("Double".equals(str[1])){
				pd_column.put("I_FIELDTYPE", 4);
				type = "int(" + str[4] + "," + str[5] + ")";
			}else if("File".equals(str[1])) {
				pd_column.put("I_FIELDTYPE", 5);
				type = "VARCHAR(" + str[4] + ")";
			}else if("Image".equals(str[1])) {
				pd_column.put("I_FIELDTYPE", 6);
				type = "VARCHAR(" + str[4] + ")";
			}else if("Clob".equals(str[1])){
				pd_column.put("I_FIELDTYPE", 7);
				type = "text";
			}
			//4是字段长度
			pd_column.put("I_LEN", str[4]);
			//3是默认值
			if("无".equals(str[3])){
				pd_column.put("I_DEFAULT", "");
			}else{
				pd_column.put("I_DEFAULT", str[3]);
			}
			//5小数点位数
			pd_column.put("I_DEC", str[5]);
			pd_column.put("I_TYPE", 2);//字段分类(1是基础，2是其他)
			//7是否字典
			pd_column.put("IS_DIC", str[7]);
			//9字典类型
			if("否".equals(str[9])){
				pd_column.put("DIC_TYPE", "");
			}else{
				pd_column.put("DIC_TYPE", str[9]);
			}
			//10是否引用
			pd_column.put("IS_QUOTE", str[10]);
			//12引用表名
			if("无".equals(str[12])){
				pd_column.put("QUOTE_TYPE", "");
			}else{
				pd_column.put("QUOTE_TYPE", str[12]);
			}
			//6列显示序号
			pd_column.put("SHOW_ORDER", str[6]);
			
			//设置新增的6个字段
			pd_column.put("ISNEW", str[13]);
			pd_column.put("ISMUST", str[14]);
			pd_column.put("ISQY",str[15]);
			pd_column.put("ISLIST", str[16]);
			pd_column.put("ISFLOW", str[17]);
			pd_column.put("ISSELECT",str[18]);
			pd_column.put("LAYOUT", str[19]);//布局 默认0
			tcolumnService.save(pd_column);
			sql += str[0] + " " + type + ",";
		}
		if(pd.getString("RELATION_TABLE")!=null && !pd.getString("RELATION_TABLE").equals("")) {
			//设置新增的6个字段
			pd_column.put("ISNEW", "0");//是否在编辑页面显示  0否 1是
			pd_column.put("ISMUST", "1");//是否必填
			pd_column.put("ISQY", "0");//是否查询字段
			pd_column.put("ISLIST", "0");//是否在查询列表显示
			pd_column.put("ISFLOW", "0");//是否工作流表单字段
			pd_column.put("ISSELECT", "0");//是否在选择框列表显示
			pd_column.put("LAYOUT", "0");//布局 默认0
			//是否存在关联表
			String [] relationTable=pd.getString("RELATION_TABLE").split(",");
			for (int i = 0; i < relationTable.length; i++) {
				if(relationTable[i]!=null && !relationTable[i].equals("")) {
					String othersql = "alter table " + relationTable[i] + " add (RELATION_ID VARCHAR(32))";
					PageData pd_sql = new PageData();
					pd_sql.put("sql", othersql);
					tcolumnService.dynamicTable(pd_sql);
					PageData pdData = new PageData();
					pdData.put("C_PHYSICSNAME", relationTable[i]);
					pdData=this.findByCPhysicsName(pdData);
					pd_column.put("TEMPLATE_ID", pdData.getString("TEMPLATE_ID"));	
					pd_column.put("CREATEMAN", Jurisdiction.getUsername());
					pd_column.put("PARENT_ID", "0");		
					pd_column.put("TCOLUMN_ID", UuidUtil.get32UUID());
					pd_column.put("C_FIELDNAME", "RELATION_ID");
					pd_column.put("C_DISPLAYLABEL", "关联ID");
					pd_column.put("I_TYPE", 1);
					pd_column.put("I_FIELDTYPE", 1);
					pd_column.put("I_LEN", 32);
					pd_column.put("I_DEFAULT", "");
					pd_column.put("I_DEC", 0);
					pd_column.put("IS_DIC", "否");
					pd_column.put("DIC_TYPE", "");
					pd_column.put("IS_QUOTE", "否");
					pd_column.put("QUOTE_TYPE", "");
					pd_column.put("SHOW_ORDER", 95);
					tcolumnService.save(pd_column);
				}
			}
			sql += " RELATION_ID VARCHAR(32) ,";
		}
		sql = sql.substring(0, sql.length()-1) + ")";
		PageData pd_sql = new PageData();
		pd_sql.put("sql", sql);
		tcolumnService.dynamicTable(pd_sql);
	}

	@Transactional
	public void editDynamicFrom(PageData pd,List<String[]> fieldList) throws Exception {
		String sql = "";	//创表语句
		PageData pData=new PageData();
		pData.put("TEMPLATE_ID", pd.getString("TEMPLATE_ID"));
		pData=this.findById(pData);
		PageData pd_column = new PageData();
		pd_column.put("TEMPLATE_ID", pd.getString("TEMPLATE_ID"));
		pd_column.put("ISNEW", "0");//是否在编辑页面显示  0否 1是
		pd_column.put("ISMUST", "1");//是否必填
		pd_column.put("ISQY", "0");//是否查询字段
		pd_column.put("ISLIST", "0");//是否在查询列表显示
		pd_column.put("ISFLOW", "0");//是否工作流表单字段
		pd_column.put("ISSELECT", "0");//是否在选择框列表显示
		pd_column.put("LAYOUT", "0");//布局 默认0
		pd_column.put("I_TYPE", 1);
		//原来是否存在关联表
		if(pData.getString("RELATION_TABLE")!=null && !pData.getString("RELATION_TABLE").equals("")) {
			//现在是否存在关联表
			if(pd.getString("RELATION_TABLE")!=null && !pd.getString("RELATION_TABLE").equals("")) {
				String[] relationTable=pd.getString("RELATION_TABLE").split(",");
				String[] before=pData.getString("RELATION_TABLE").split(",");
				List<String> delTable=new ArrayList<String>();
				List<String> addTable=new ArrayList<String>();
				if(RelationUtils.getRelationTable(before, relationTable)!=null) {
					//删除不存在关联表
					delTable=RelationUtils.getRelationTable(before, relationTable);
					for (int i = 0; i < delTable.size(); i++) {
						//删除表中关联id字段
						String sqlstr="";
						sqlstr = "alter table " + delTable.get(i) + "  drop (RELATION_ID)";
						PageData pd_sql = new PageData();
						pd_sql.put("sql", sqlstr);
						tcolumnService.dynamicTable(pd_sql);
						//删除表对应的列(关联id列)
						PageData pdData = new PageData();
						pdData.put("C_PHYSICSNAME", delTable.get(i));
						pdData=this.findByCPhysicsName(pdData);
						sqlstr = "delete from S_TCOLUMN  where TEMPLATE_ID = '"+pdData.getString("TEMPLATE_ID")+"' and C_FIELDNAME= 'RELATION_ID'";
						pd_sql.put("sql", sqlstr);
						tcolumnService.dynamicTable(pd_sql);
					}
				}
				if(RelationUtils.getRelationTable(relationTable, before)!=null) {
					//添加新增关联表
					addTable=RelationUtils.getRelationTable(relationTable, before);
					for (int i = 0; i < addTable.size(); i++) {
						//表中添加关联id字段
						String sqlstr="";
						sqlstr = "alter table " + addTable.get(i) + "  add (RELATION_ID VARCHAR(32))";
						PageData pd_sql = new PageData();
						pd_sql.put("sql", sqlstr);
						tcolumnService.dynamicTable(pd_sql);
						//添加表对应的列(关联id列)
						PageData pdData = new PageData();
						pdData.put("C_PHYSICSNAME", addTable.get(i));
						pdData=this.findByCPhysicsName(pdData);
						pd_column.put("TEMPLATE_ID", pdData.getString("TEMPLATE_ID"));	
						pd_column.put("CREATEMAN", Jurisdiction.getUsername());
						pd_column.put("PARENT_ID", "0");		
						pd_column.put("TCOLUMN_ID", UuidUtil.get32UUID());
						pd_column.put("C_FIELDNAME", "RELATION_ID");
						pd_column.put("C_DISPLAYLABEL", "关联ID");
						pd_column.put("I_FIELDTYPE", 1);
						pd_column.put("I_LEN", 32);
						pd_column.put("I_DEFAULT", "");
						pd_column.put("I_DEC", 0);
						pd_column.put("IS_DIC", "否");
						pd_column.put("DIC_TYPE", "");
						pd_column.put("IS_QUOTE", "否");
						pd_column.put("QUOTE_TYPE", "");
						pd_column.put("SHOW_ORDER", 95);
						tcolumnService.save(pd_column);
					}
				}
			}else {
				String[] before=pData.getString("RELATION_TABLE").split(",");
				for (int i = 0; i < before.length; i++) {
					if(before[i]!=null && !before[i].equals("")) {
						//删除表中关联id字段
						String sqlstr="";
						sqlstr = "alter table " + before[i] + "  drop (RELATION_ID)";
						PageData pd_sql = new PageData();
						pd_sql.put("sql", sqlstr);
						tcolumnService.dynamicTable(pd_sql);
						//删除表对应的列(关联id列)
						PageData pdData = new PageData();
						pdData.put("C_PHYSICSNAME", before[i]);
						pdData=this.findByCPhysicsName(pdData);
						sqlstr = "delete from S_TCOLUMN  where TEMPLATE_ID = '"+pdData.getString("TEMPLATE_ID")+"' and C_FIELDNAME= 'RELATION_ID'";
						pd_sql.put("sql", sqlstr);
						tcolumnService.dynamicTable(pd_sql);
					}
				}
				pData.put("C_FIELDNAME", "RELATION_ID");
				PageData pd_count = tcolumnService.findByCondition(pData);
				int count = Integer.parseInt(String.valueOf(pd_count.get("COUNT")));
				if(count>0) {
					//删除表中关联id字段
					String sqlstr="";
					sqlstr = "alter table " + pData.getString("C_PHYSICSNAME") + "  drop (RELATION_ID)";
					PageData pd_sql = new PageData();
					pd_sql.put("sql", sqlstr);
					tcolumnService.dynamicTable(pd_sql);
					//删除表对应的列(关联id列)
					sqlstr = "delete from S_TCOLUMN  where TEMPLATE_ID = '"+pData.getString("TEMPLATE_ID")+"' and C_FIELDNAME= 'RELATION_ID'";
					pd_sql.put("sql", sqlstr);
					tcolumnService.dynamicTable(pd_sql);
				}
			}
			
		}else {
			//新增关联表
			if(pd.getString("RELATION_TABLE")!=null && !pd.getString("RELATION_TABLE").equals("")) {
				String [] relationTable=pd.getString("RELATION_TABLE").split(",");
				for (int i = 0; i < relationTable.length; i++) {
					if(relationTable[i]!=null && !relationTable[i].equals("")) {
						//表中添加关联id字段
						String sqlstr="";
						sqlstr = "alter table " + relationTable[i] + "  add (RELATION_ID VARCHAR(32))";
						PageData pd_sql = new PageData();
						pd_sql.put("sql", sqlstr);
						tcolumnService.dynamicTable(pd_sql);
						//添加表对应的列(关联id列)
						PageData pdData = new PageData();
						pdData.put("C_PHYSICSNAME", relationTable[i]);
						pdData=this.findByCPhysicsName(pdData);
						pd_column.put("TEMPLATE_ID", pdData.getString("TEMPLATE_ID"));	
						pd_column.put("CREATEMAN", Jurisdiction.getUsername());
						pd_column.put("PARENT_ID", "0");		
						pd_column.put("TCOLUMN_ID", UuidUtil.get32UUID());
						pd_column.put("C_FIELDNAME", "RELATION_ID");
						pd_column.put("C_DISPLAYLABEL", "关联ID");
						pd_column.put("I_FIELDTYPE", 1);
						pd_column.put("I_LEN", 32);
						pd_column.put("I_DEFAULT", "");
						pd_column.put("I_DEC", 0);
						pd_column.put("IS_DIC", "否");
						pd_column.put("DIC_TYPE", "");
						pd_column.put("IS_QUOTE", "否");
						pd_column.put("QUOTE_TYPE", "");
						pd_column.put("SHOW_ORDER", 95);
						tcolumnService.save(pd_column);
					}
				}
				//添加主表中关联id字段
				String sqlstr="";
				sqlstr = "alter table " + pd.getString("C_PHYSICSNAME") + "  add (RELATION_ID VARCHAR(32))";
				PageData pd_sql = new PageData();
				pd_sql.put("sql", sqlstr);
				tcolumnService.dynamicTable(pd_sql);
				//添加主表中对应的列(关联id列)
				PageData pdData = new PageData();
				pdData.put("C_PHYSICSNAME", pd.getString("C_PHYSICSNAME"));
				pdData=this.findByCPhysicsName(pdData);
				pd_column.put("TEMPLATE_ID", pdData.getString("TEMPLATE_ID"));	
				pd_column.put("CREATEMAN", Jurisdiction.getUsername());
				pd_column.put("PARENT_ID", "0");		
				pd_column.put("TCOLUMN_ID", UuidUtil.get32UUID());
				pd_column.put("C_FIELDNAME", "RELATION_ID");
				pd_column.put("C_DISPLAYLABEL", "关联ID");
				pd_column.put("I_FIELDTYPE", 1);
				pd_column.put("I_LEN", 32);
				pd_column.put("I_DEFAULT", "");
				pd_column.put("I_DEC", 0);
				pd_column.put("IS_DIC", "否");
				pd_column.put("DIC_TYPE", "");
				pd_column.put("IS_QUOTE", "否");
				pd_column.put("QUOTE_TYPE", "");
				pd_column.put("SHOW_ORDER", 95);
				tcolumnService.save(pd_column);
			}
		}
		this.edit(pd);
		String columnStr = "";
		String addColumnSql = "";
		for(String[] str : fieldList){
			//0是字段名，1是字段类型，2是显示中文名，3是默认值，4是字段长度
			pd_column.put("C_FIELDNAME", str[0]);
			PageData pd_count = tcolumnService.findByCondition(pd_column);
			int count = Integer.parseInt(String.valueOf(pd_count.get("COUNT")));
			columnStr += "'" + str[0] + "',";
			if(count == 0){	//新增
				pd_column.put("TCOLUMN_ID",  UuidUtil.get32UUID());
				pd_column.put("C_DISPLAYLABEL", str[2]);
				String type = "";
				if("String".equals(str[1])){
					pd_column.put("I_FIELDTYPE", 1);
					type = "VARCHAR(" + str[4] + ")";
				}else if("Date".equals(str[1])){
					pd_column.put("I_FIELDTYPE", 2);
					type = "DATE";
				}else if("Integer".equals(str[1])){
					pd_column.put("I_FIELDTYPE", 3);
					type = "int(" + str[4] + ")";
				}else if("Double".equals(str[1])){
					pd_column.put("I_FIELDTYPE", 4);
					type = "int(" + str[4] + "," + str[5] + ")";
				}else if("File".equals(str[1])) {
					pd_column.put("I_FIELDTYPE", 5);
					type = "VARCHAR(" + str[4] + ")";
				}else if("Image".equals(str[1])) {
					pd_column.put("I_FIELDTYPE", 6);
					type = "VARCHAR(" + str[4] + ")";
				}else if("Clob".equals(str[1])) {
					pd_column.put("I_FIELDTYPE", 7);
					type = "CLOB";
				}
				pd_column.put("I_LEN", str[4]);
				if("无".equals(str[3])){
					pd_column.put("I_DEFAULT", "");
				}else{
					pd_column.put("I_DEFAULT", str[3]);
				}
				pd_column.put("IS_DIC", str[7]);
				if("无".equals(str[9])){
					pd_column.put("DIC_TYPE", "");
				}else{
					pd_column.put("DIC_TYPE", str[9]);
				}
				pd_column.put("IS_QUOTE", str[10]);
				if("无".equals(str[12])){
					pd_column.put("QUOTE_TYPE", "");
				}else{
					pd_column.put("QUOTE_TYPE", str[12]);
				}
				pd_column.put("SHOW_ORDER", str[6]);
				pd_column.put("I_DEC", str[5]);
				pd_column.put("I_TYPE", 2);
				pd_column.put("ISNEW", str[13]);
				pd_column.put("ISMUST", str[14]);
				pd_column.put("ISQY",str[15]);
				pd_column.put("ISLIST", str[16]);
				pd_column.put("ISFLOW", str[17]);
				pd_column.put("ISSELECT",str[18]);
				pd_column.put("LAYOUT", str[19]);//布局 默认0
				tcolumnService.save(pd_column);
				addColumnSql += str[0] + " " + type + ",";
			}else {
				PageData data=new PageData();
				data.put("TEMPLATE_ID", pData.getString("TEMPLATE_ID"));
				data.put("C_FIELDNAME", str[0]);
				data=tcolumnService.findByTemplate(data);
				data.put("C_DISPLAYLABEL", str[2]);
				if("String".equals(str[1])){
					data.put("I_FIELDTYPE", 1);
				}else if("Date".equals(str[1])){
					data.put("I_FIELDTYPE", 2);
				}else if("Integer".equals(str[1])){
					data.put("I_FIELDTYPE", 3);
				}else if("Double".equals(str[1])){
					data.put("I_FIELDTYPE", 4);
				}else if("File".equals(str[1])) {
					data.put("I_FIELDTYPE", 5);
				}else if("Image".equals(str[1])) {
					data.put("I_FIELDTYPE", 6);
				}else if("Clob".equals(str[1])){
					data.put("I_FIELDTYPE", 7);
				}
				data.put("I_LEN", str[4]);
				if("无".equals(str[3])){
					data.put("I_DEFAULT", "");
				}else{
					data.put("I_DEFAULT", str[3]);
				}
				data.put("IS_DIC", str[7]);
				if("无".equals(str[9])){
					data.put("DIC_TYPE", "");
				}else{
					data.put("DIC_TYPE", str[9]);
				}
				data.put("IS_QUOTE", str[10]);
				if("无".equals(str[12])){
					data.put("QUOTE_TYPE", "");
				}else{
					data.put("QUOTE_TYPE", str[12]);
				}
				data.put("SHOW_ORDER", str[6]);
				data.put("I_DEC", str[5]);
				data.put("I_TYPE", 2);
				data.put("ISNEW", str[13]);
				data.put("ISMUST", str[14]);
				data.put("ISQY",str[15]);
				data.put("ISLIST", str[16]);
				data.put("ISFLOW", str[17]);
				data.put("ISSELECT",str[18]);
				data.put("LAYOUT",str[19]);
				tcolumnService.edit(data); 
			}
		}
		if(!"".equals(addColumnSql)){
			sql = "alter table " + pd.getString("C_PHYSICSNAME") + " add (" + addColumnSql.substring(0, addColumnSql.length()-1) + ")";
			PageData pd_sql = new PageData();
			pd_sql.put("sql", sql);
			tcolumnService.dynamicTable(pd_sql);
		}
		//先查询要删除的字段
		if(!"".equals(columnStr)){
			//删除不存在的字段，就是在页面上操作删除
			pd_column.put("columnStr", columnStr.substring(0, columnStr.length()-1));
			List<PageData> varList = tcolumnService.findDeleteColumns(pd_column);
			if(varList.size() > 0){
				tcolumnService.deleteColumn(pd_column);
				String deleteColumn = "";
				for(PageData pda : varList){
					deleteColumn += pda.getString("C_FIELDNAME") + ",";
				}
				sql = "alter table " + pd.getString("C_PHYSICSNAME") + " drop (" + deleteColumn.substring(0, deleteColumn.length()-1) + ")";
				PageData pd_sql = new PageData();
				pd_sql.put("sql", sql);
				tcolumnService.dynamicTable(pd_sql);
			}
		}
		
	}

	public List<PageData> getTemplatesByType(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("TemplatesMapper.getTemplatesByType", pd);
	}
	
}

