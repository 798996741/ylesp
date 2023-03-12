package com.xxgl.service.system.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import com.fh.service.system.dictionaries.DictionariesManager;
import com.xxgl.service.system.DynamicManager;
import com.xxgl.service.system.TemplatesManager;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.system.TcolumnManager;

/** 
 * 说明： 字段管理
 * 创建人：Q351412933
 * 创建时间：2019-06-13
 * @version
 */
@Service("tcolumnService")
public class TcolumnService implements TcolumnManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Resource(name="templatesService")
	private TemplatesService templateService;

	@Resource(name="dynamicService")
	private DynamicManager dynamicService;

	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TcolumnMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TcolumnMapper.delete", pd);
	}
	
	/**删除在界面上面被删除的字段
	 * @param pd
	 * @throws Exception
	 */
	public void deleteColumn(PageData pd)throws Exception{
		dao.delete("TcolumnMapper.deleteColumn", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TcolumnMapper.edit", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TcolumnMapper.datalist", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TcolumnMapper.listAll", pd);
	}
	
	/**列出要删除的字段
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> findDeleteColumns(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TcolumnMapper.findDeleteColumns", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TcolumnMapper.findById", pd);
	}
	
	/**创建表格/添加或删除表格中的列
	 * @param pd
	 * @throws Exception
	 */
	public PageData dynamicTable(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TcolumnMapper.dynamicTable", pd);
	}
	
	/**通过条件获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCondition(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TcolumnMapper.findByCondition", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TcolumnMapper.deleteAll", ArrayDATA_IDS);
	}

	public List<PageData> findByTemplateId(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("TcolumnMapper.findByTemplateId", pd);
	}

	public PageData findByTemplate(PageData pd) throws Exception {
		return (PageData)dao.findForObject("TcolumnMapper.findByTemplate", pd);
	}
	
	public List<PageData> getTemplateColumns(String[] TEMPLATEIDS) throws Exception {
		return (List<PageData>)dao.findForList("TcolumnMapper.getTemplateColumns", TEMPLATEIDS);
	}

	public List<Map<String, Object>> getTemplateColSet(String TEMPLATE_ID) throws Exception {
		return (List<Map<String,Object>>)dao.findForList("TcolumnMapper.getTopicTmpColSet", TEMPLATE_ID);
	}

	public String getSelectStr(PageData pd) throws Exception {
		PageData template=new PageData();
		template.put("C_PHYSICSNAME",pd.getString("nowTB"));
		template=templateService.findByCPhysicsName(template);
		String columnStr="";
		List<Map<String,Object>> colList = this.getTemplateColSet(template.getString("TEMPLATE_ID"));
		for(Map<String,Object> col:colList){
			if(!col.get("C_FIELDNAME").toString().equals("BASIC_DETAILS")){//项目储备中特殊字段单独处理
				if("2".equals(col.get("I_FIELDTYPE").toString())){
					columnStr += "to_char(" + col.get("C_FIELDNAME") + ", 'yyyy-MM-dd') " + col.get("C_FIELDNAME") + ",";
				}else{
					columnStr += col.get("C_FIELDNAME") + ",";
				}
			}
		}
		return columnStr.substring(0,columnStr.length()-1);
	}

	public PageData getColResult(PageData pd) throws Exception{
		return (PageData)dao.findForObject("TcolumnMapper.getColResult", pd);
	}

	public List<PageData> getTemplateColumns(PageData pd) throws Exception {
		Page page=new Page();
		pd.put("I_TYPE", 2);
		page.setPd(pd);
		List<PageData> varList = this.list(page);	//列出其他类型的字段，即编辑页面上显示的字段
		for(PageData pda : varList){
			String is_dic = pda.getString("IS_DIC");
			String is_quote = pda.getString("IS_QUOTE");
			if("是".equals(is_dic)){
				PageData pd_dic = new PageData();
				pd_dic.put("DICTIONARIES_ID", pda.getString("DIC_TYPE"));
				page.setPd(pd_dic);
				page.setShowCount(999);
				List<PageData> dicList = dictionariesService.list(page);
				pda.put(pda.getString("C_FIELDNAME") + "LIST", dicList);
			}
			if("是".equals(is_quote)){
				PageData pd_quote = new PageData();
				pd_quote.put("TEMPLATE_ID", pda.getString("QUOTE_TYPE"));
				pd_quote = templateService.findById(pd_quote);
				if(pd_quote != null){
					if(pd_quote.get("TREE_TYPE") != null && "1".equals(String.valueOf(pd_quote.get("TREE_TYPE")))){
						JSONArray arr = JSONArray.fromObject(dynamicService.listTree_child("0", pd_quote.getString("C_PHYSICSNAME"), ""));
						String json = arr.toString();
						json = json.replaceAll("ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name")
								.replaceAll("C_PHYSICSname", "C_PHYSICSNAME").replaceAll("TEMPLATE_id", "TEMPLATE_ID")
								.replaceAll("subDynamic", "nodes").replaceAll("hasDynamic", "checked");	//.replaceAll("treeurl", "url")
						//mv.addObject("zTreeNodes", json);
						pda.put(pda.getString("C_FIELDNAME") + "zTreeNodes", json);
						pda.put("TREE_TYPE", "1");
					}else if(pd_quote.get("SELECT_TYPE") != null && "1".equals(String.valueOf(pd_quote.get("SELECT_TYPE")))){
						if(pd_quote.containsKey("C_PHYSICSNAME") && pd_quote.getString("C_PHYSICSNAME").equals("TB_HX7WFXAUG3JX0TU")){
							pd_quote.put("condition"," order by SERIAL_NUMBER asc");
						}
						List<PageData> dicList = dynamicService.getAllList(pd_quote);
						pda.put(pda.getString("C_FIELDNAME") + "LIST", dicList);
						pda.put("SELECT_TYPE", "1");
					}else{
						List<PageData> dicList = dynamicService.getAllList(pd_quote);
						pda.put(pda.getString("C_FIELDNAME") + "LIST", dicList);
						pda.put("TREE_TYPE", "0");
					}
				}
			}
		}
		return varList;
	}


}

