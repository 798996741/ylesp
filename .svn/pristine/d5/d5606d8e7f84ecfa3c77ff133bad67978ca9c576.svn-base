package com.xxgl.system;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import oracle.net.aso.p;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;


//import com.alibaba.fastjson.JSONArray;
import net.sf.json.JSONArray;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.util.AppUtil;
import com.fh.util.DateUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.xxgl.utils.StringUtil;
import com.xxgl.service.system.DynamicManager;
import com.xxgl.service.system.TcolumnManager;
import com.xxgl.service.system.TemplatesManager;
import com.xxgl.utils.RelationUtils;
import com.xxgl.utils.ResponseUtils;

/** 
 * 说明：模板库管理
 * 创建人：351412933
 * 创建时间：2019-06-10
 */
@Controller
@RequestMapping(value="/templates")
public class TemplatesController extends BaseController {
	
	String menuUrl = "templates/list.do"; //菜单地址(权限用)
	@Resource(name="templatesService")
	private TemplatesManager templateService;
	/*@Resource(name="createcodeService")
	private CreateCodeManager createcodeService;*/
	@Resource(name="tcolumnService")
	private TcolumnManager tcolumnService;
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	@Resource(name="dynamicService")
	private DynamicManager dynamicService;
/*	@Resource(name="topicService")
	private TopicService topicService;*/
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Template");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("TEMPLATE_ID", this.get32UUID());	//主键
		pd.put("CREATEMAN", Jurisdiction.getUsername());	//创建人
		if(pd.getString("LEADER") != null && "".equals(pd.getString("LEADER"))){
			pd.put("LEADER", "0");
		}
		templateService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**生成表格
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/proCode")
	public void proCode(HttpServletResponse response,HttpServletRequest req) throws Exception{
		JSONObject object = new JSONObject();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){} 		//校验权限
		logBefore(logger, Jurisdiction.getUsername()+"生成表格");
		PageData pd = new PageData();
		pd = this.getPageData();
		
		String zindext = pd.getString("zindex");//属性总数
		int zindex = 0;
		if(null != zindext && !"".equals(zindext)){
			zindex = Integer.parseInt(zindext);
		}
		List<String[]> fieldList = new ArrayList<String[]>();   	//属性集合
		for(int i=0; i< zindex; i++){
			fieldList.add(pd.getString("field"+i).split("#"));	//属性放到集合里面
		}
		try {
			if("add".equals(pd.getString("msg"))){
				templateService.saveDynamicFrom(pd, fieldList);
			}else{
				templateService.editDynamicFrom(pd, fieldList);
			}
			if("005-1".equals(pd.getString("TYPE"))){	
				//dynamicService.callUpdate();
			}
			object.put("success", true);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		ResponseUtils.renderJson(response, object.toString());
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Template");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			//先删除表
			pd.put("sql", "drop table " + pd.getString("C_PHYSICSNAME"));
			tcolumnService.dynamicTable(pd);
			//再删除字段表的字段
			tcolumnService.deleteColumn(pd);
			//最后删除模板
			templateService.delete(pd);
			if("005-1".equals(pd.getString("TYPE"))){	
				dynamicService.callUpdate();
			}
		}catch (Exception e) {
			logger.error(e.toString(), e);
		}
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Template");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		templateService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Template");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		page.setShowCount(9999);
		List<PageData>	varList = templateService.list(page);	//列出Template列表
		mv.setViewName("system/template/template_list_new");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("system/template/template_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	/**去代码生成器页面(进入弹窗)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goProduct")
	public ModelAndView goProductTB() throws Exception{
		System.out.println("ddd");
		ModelAndView mv = this.getModelAndView();
		try{
			
			PageData pd = new PageData();
			pd = this.getPageData();
			Page page = new Page();
			Page page_qoute = new Page();
			PageData pd_quote = new PageData();
			String TEMPLATE_ID = pd.getString("TEMPLATE_ID");
			List<Dictionaries> dicList = dictionariesService.listSubDictByParentId("6e8b7fdf5c3e4566a3ea30b301bd5bd4");
			mv.addObject("dicList", dicList);
			if(!"add".equals(TEMPLATE_ID)){
				pd = templateService.findById(pd);
				mv.addObject("msg", "edit");
			}else{
				pd.put("C_PHYSICSNAME", "TB_" + StringUtil.randomStr(11, 16));
				mv.addObject("msg", "add");
			}
			pd.put("DICTIONARIES_ID", "0");
			page.setPd(pd);
			page.setShowCount(999);
			List<PageData> varList = dictionariesService.list(page);	//列出数据字典列表
			PageData pageData = new PageData();
			pageData = this.getPageData();
			page.setPd(pageData);
			List<PageData>	templateList = templateService.list(page);	//列出Template列表
			pd_quote.put("TB_TYPE", "1");
			page_qoute.setPd(pd_quote);
			page_qoute.setShowCount(999);
			List<PageData> quoteList = templateService.list(page_qoute);
			PageData data = new PageData();
			data.put("DICTIONARIES_ID", "PLANTYPE0");//方案类型ID
			/*data=dictionariesService.findById(data);
			List<Dictionaries> dictionaries=dictionariesService.listAllDictByPlanType(data.getString("DICTIONARIES_ID"), "leader");
			Dictionaries dictionary =new Dictionaries();
			dictionary.setPARENT_ID("0");
			dictionary.setDICTIONARIES_ID(pd.getString("DICTIONARIES_ID"));
			dictionary.setNAME(data.getString("NAME"));
			dictionary.setSubDict(dictionaries);
			dictionary.setTarget("treeFrame");
			JSONArray arr = JSONArray.fromObject(dictionaries);
			String json = arr.toString();
			json = json.replaceAll("DICTIONARIES_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subDict", "nodes").replaceAll("hasDict", "checked").replaceAll("treeurl", "url");
			mv.addObject("zTreeNodes", json);*/
			mv.addObject("quoteList", quoteList);
			mv.addObject("varList", varList);
			mv.addObject("templateList", templateList);
			mv.addObject("pd", pd);
			mv.setViewName("system/template/productCode");
		}catch(Exception e){
			e.printStackTrace();
		}	
		return mv;
	}
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = templateService.findById(pd);	//根据ID读取
		mv.setViewName("system/template/template_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Template");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			templateService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出Template到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("名称");	//1
		titles.add("创建人");	//2
		titles.add("创建时间");	//3
		dataMap.put("titles", titles);
		List<PageData> varOList = templateService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("NAME"));	    //1
			vpd.put("var2", varOList.get(i).getString("CREATEMAN"));	    //2
			vpd.put("var3", varOList.get(i).getString("CREATEDATE"));	    //3
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
	
	/*@RequestMapping(value="/doSetPy")
	public void doSaveTopic(@RequestBody Map<String,String> map,HttpServletResponse resp) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"设置空的拼音");
		PageData pd = new PageData();
		pd = this.getPageData();
		if(null!=map.get("tab") && map.get("tab").length()>0 && null!=map.get("col") && map.get("col").length()>0){
			pd.put("tab", map.get("tab"));
			pd.put("col", map.get("col"));
			List<PageData> varOList = templateService.getSetList(pd);
			for(PageData det:varOList){
				String upSql = "update "+map.get("tab")+" set py='"+PinYinUtils.getHanziPinYin(det.getString("NAME"))
					+"',py_f='"+PinYinUtils.getHanziInitials(det.getString("NAME"))+"' where no='"+det.getString("NO")+"'";
				pd.put("sql",upSql);
				topicService.autoSave(pd);
			}
		}
		com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
		obj.put("error",0);
		resp.setContentType("text/plain; charset=utf-8");
		resp.getWriter().println(obj.toJSONString());
	}*/
	
	@RequestMapping(value="/setSplit")
	public void setSplit(@RequestBody Map<String,String> map,HttpServletResponse resp) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"设置分隔");
		PageData pd = new PageData();
		pd = this.getPageData();
		if(null!=map.get("tab") && map.get("tab").length()>0 && null!=map.get("col") && map.get("col").length()>0){
			pd.put("tab", map.get("tab"));
			pd.put("col", map.get("col"));
			List<PageData> varOList = templateService.getSplitList(pd);
			for(PageData det:varOList){
				if(null!=det.getString(map.get("col"))){
					String zw = det.getString(map.get("col")).toString().trim();
					if(zw.indexOf("、")==-1&&zw.indexOf(" ")==-1){
						String tmp="";
						String upSql = "insert into "+map.get("tab")+"_all values (sys_guid(),'"+zw+"','"+det.getString("ID")+"')";
						pd.put("sql",upSql);
						//topicService.autoSave(pd);
					}else{
						List<Map<String,String>> add = new ArrayList<Map<String,String>>();
						String[] dsz = zw.split("、");
						for(String ds:dsz){
							String[] z=ds.split(" ");
							for(String z1:z){
								if(z1.trim().length()>0){
									String upSql = "insert into "+map.get("tab")+"_all values (sys_guid(),'"+z1.trim()+"','"+det.getString("ID")+"')";
									pd.put("sql",upSql);
									//topicService.autoSave(pd);
								}
							}
						}
					}
				}
			}
		}
		com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
		obj.put("error",0);
		resp.setContentType("text/plain; charset=utf-8");
		resp.getWriter().println(obj.toJSONString());
	}
}
