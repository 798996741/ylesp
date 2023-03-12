package com.fh.controller.system.areamanage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.Tools;
import com.fh.service.areamanage.AreaManageManager;
import com.xxgl.utils.ResponseUtils;

/** 
 * 说明：地区管理
 * 创建人：351412933
 * 创建时间：2018-10-09
 */
@Controller
@RequestMapping(value="/areamanage")
public class AreaManageController extends BaseController {
	
	String menuUrl = "areamanage/list.do"; //菜单地址(权限用)
	@Resource(name="areamanageService")
	private AreaManageManager areamanageService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增AreaManage");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("AREA_ID", this.get32UUID());	//主键
		areamanageService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	@ResponseBody
	public Object delete(@RequestParam String AREA_ID) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除AreaManage");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} 					//校验权限
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd.put("AREA_ID", AREA_ID);
		String errInfo = "success";
		if(areamanageService.listByParentId(AREA_ID).size() > 0){		//判断是否有子级，是：不允许删除
			errInfo = "false";
		}else{
			areamanageService.delete(pd);	//执行删除
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改AreaManage");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		areamanageService.edit(pd);
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
		logBefore(logger, Jurisdiction.getUsername()+"列表AreaManage");
		//User user=Jurisdiction.getLoginUser();
		//System.out.println(Jurisdiction.getLoginUser().getDWBM()+"dwbm");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} 	//校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");								//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String AREA_ID = null == pd.get("AREA_ID")?"":pd.get("AREA_ID").toString();
		if(null != pd.get("id") && !"".equals(pd.get("id").toString())){
			AREA_ID = pd.get("id").toString();
		}
		pd.put("AREA_ID", AREA_ID);					//上级ID
		page.setPd(pd);
		List<PageData>	varList = areamanageService.list(page);			//列出AreaManage列表
		mv.setViewName("areamanage/areamanage_list");
		mv.addObject("pd", areamanageService.findById(pd));				//传入上级所有信息
		mv.addObject("AREA_ID", AREA_ID);			//上级ID
		mv.addObject("varList", varList);
		mv.addObject("QX",Jurisdiction.getHC());								//按钮权限
		return mv;
	}

	/**
	 * 显示列表ztree
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/listTree")
	public ModelAndView listTree(Model model,String AREA_ID)throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			
			JSONArray arr = JSONArray.fromObject(areamanageService.listTree("0", ""));
			String json = arr.toString();
			json = json.replaceAll("AREA_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subAreaManage", "nodes").replaceAll("hasAreaManage", "checked").replaceAll("treeurl", "url");
			model.addAttribute("zTreeNodes", json);
			mv.addObject("AREA_ID",AREA_ID);
			mv.addObject("pd", pd);	
			mv.setViewName("areamanage/areamanage_tree");
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
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
	
		String AREA_ID =pd.get("AREA_ID")==null?"0":pd.get("AREA_ID").toString();
		if(AREA_ID.equals("")){
			AREA_ID="0";
		}
		//System.out.println(AREA_ID+"AREA_ID");
		pd.put("AREA_ID", AREA_ID);					//上级ID
		PageData pds = areamanageService.findById(pd); //传入上级所有信息
		if("0".equals(AREA_ID)){
			pd.put("AREA_LEVEL", 1);
		}else{
			if(pds!=null){
				String AREA_LEVEL = pds.getString("AREA_LEVEL");
				pd.put("AREA_LEVEL", Integer.parseInt(AREA_LEVEL) + 1);
			}else{
				pd.put("AREA_LEVEL", 1);
			}
			
		}
		mv.addObject("pds", pds);				//传入上级所有信息
		mv.addObject("pd", pd);				//传入上级所有信息
		mv.addObject("AREA_ID", AREA_ID);			//传入ID，作为子级ID用
		mv.setViewName("areamanage/areamanage_edit");
		mv.addObject("msg", "save");
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
		String AREA_ID = pd.getString("AREA_ID");
		pd = areamanageService.findById(pd);							//根据ID读取		
		mv.addObject("pd", pd);													//放入视图容器
		pd.put("AREA_ID",pd.get("PARENT_ID").toString());			//用作上级信息
		mv.addObject("pds",areamanageService.findById(pd));				//传入上级所有信息
		mv.addObject("AREA_ID", pd.get("PARENT_ID").toString());	//传入上级ID，作为子ID用
		pd.put("AREA_ID",AREA_ID);					//复原本ID
		pd = areamanageService.findById(pd);							//根据ID读取
		mv.setViewName("areamanage/areamanage_edit");
		mv.addObject("msg", "edit");
		return mv;
	}
	
	/**检验地区编码是否已存在
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/checkCode")
	public void checkCode(HttpServletResponse response) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"检验地区编码是否已存在");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		JSONObject object = new JSONObject();
		PageData pd = new PageData();
		pd = this.getPageData();
		String action = pd.getString("action");
		if("save".equals(action)){
			pd.remove("AREA_ID");
			PageData pd_check = new PageData();
			pd_check = areamanageService.findByAreaCode(pd);
			if(pd_check != null){
				object.put("success", false);
			}else{
				object.put("success", true);
			}
		}else{
			PageData pd_check1 = new PageData();
			pd_check1 = areamanageService.findByAreaCode(pd);	//area_code没改
			pd.remove("AREA_ID");
			PageData pd_check2 = areamanageService.findByAreaCode(pd);	//area_code改了
			if(pd_check1 != null){
				object.put("success", true);
			}else if(pd_check2 == null){
				object.put("success", true);
			}else{
				object.put("success", false);
			}
		}
		ResponseUtils.renderJson(response, object.toString());
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出AreaManage到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("地区编码");	//1
		titles.add("地区级别");	//2
		dataMap.put("titles", titles);
		List<PageData> varOList = areamanageService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("AREA_CODE"));	    //1
			vpd.put("var2", varOList.get(i).getString("AREA_LEVEL"));	    //2
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
}
