package com.yulun.controller.dynamic;

import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import net.sf.json.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.fh.util.UuidUtil;
import com.yulun.service.ConsultManager;
import com.xxgl.service.mng.TemplateManager;
import com.xxgl.service.mng.ZxlbManager;
import com.xxgl.service.system.DynamicManager;
import com.xxgl.service.system.TcolumnManager;
import com.xxgl.service.system.TemplatesManager;
import com.xxgl.utils.ExcelReader;
import com.yulun.controller.api.CommonIntefate;

/**
 * 说明：动态表接口
 * 创建人：huangjianling-351412933
 * 创建时间：2020-11-12
 */
@Controller
@RequestMapping(value="/appDynamic")
public class DynamicWeb  extends BaseController  implements CommonIntefate  {


	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;

	@Resource(name="consultService")
	private ConsultManager consultService;
	
	@Resource(name="dynamicService")
	private DynamicManager dynamicService;
	@Resource(name="tcolumnService")
	private TcolumnManager tcolumnService;
	@Resource(name="templatesService")
	private TemplatesManager templateService;
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	@Override
	public JSONObject execute(JSONObject data, HttpServletRequest request)
			throws Exception {
		JSONObject object=new JSONObject();
		try{
			PageData pd = new PageData();
			String cmd = data.getString("cmd")==null?"":data.getString("cmd");
			PageData pd_stoken=new PageData();
			JSONObject json = data.getJSONObject("data");
			pd_stoken.put("TOKENID", json.get("tokenid"));
			PageData pd_token=zxlbService.findByTokenId(pd_stoken);
			if(pd_token!=null){
				if(cmd.equals("list")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			        
			        JSONObject objectDynamic=new JSONObject();

			        pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		            
			        String RESOURCE_TYPE=json.getString("resource_type")==null?"":json.getString("resource_type");
					String TEMPLATE_ID = json.getString("template_id")==null?"":json.getString("template_id");
					
					if(null != json.get("id") && !"".equals(json.get("id").toString())){
						TEMPLATE_ID = json.get("id").toString();
					}
					objectDynamic.put("resource_type", RESOURCE_TYPE);
					objectDynamic.put("template_id", TEMPLATE_ID);
					pd.put("TEMPLATE_ID", TEMPLATE_ID);	//模板ID
					PageData pd_tem_all = templateService.findById(pd);	//根据id查找模板表类型
					if(pd_tem_all != null){
						String condition="";
						if(pd_tem_all.get("SORT_ORDER")!=null) {
							pd.put("SORT_ORDER", pd_tem_all.get("SORT_ORDER"));
						}
						PageData pageData=new PageData();
						pageData.put("C_PHYSICSNAME", pd_tem_all.getString("C_PHYSICSNAME"));
						
						PageData pd_tem = dynamicService.findByTemId(pd);
						objectDynamic.put("c_physicsname",  pd_tem_all.getString("C_PHYSICSNAME"));
						
						if(pd_tem != null){
							objectDynamic.put("columnTypes",pd_tem.getString("typeStr").split(","));
							objectDynamic.put("coumnnStr",pd_tem.getString("columnStr").split(","));
							objectDynamic.put("displayLables",pd_tem.getString("nameStr").split(","));
							objectDynamic.put("dicStr",pd_tem.getString("dicStr").split(","));
							objectDynamic.put("searchStr",pd_tem.getString("searchStr").split(","));
						}
						pd.put("I_TYPE", 2);
						page.setPd(pd);
						
						List<PageData> varList = tcolumnService.list(page);	//列出其他类型的字段，即编辑页面上显示的字段
						
						String columnStr = "";
						String keywords = json.getString("keywords");								//关键词检索条件
						String keywordsStr = "";
						String searchStr="";
						boolean flag = false;
						if(null != keywords && !"".equals(keywords)){
							flag = true;
						}
						for(PageData pda : varList){
							String is_dic = pda.getString("IS_DIC");
							String is_quote = pda.getString("IS_QUOTE");
							if(json.get("START"+pda.getString("C_FIELDNAME"))!=null&&!"".equals(json.get("START"+pda.getString("C_FIELDNAME")))){
								if(!"".equals(searchStr)){
									searchStr=searchStr+" and ";
								}
								pd.put("START"+pda.getString("C_FIELDNAME"), json.get("START"+pda.getString("C_FIELDNAME")));
								searchStr=searchStr+pda.getString("C_FIELDNAME")+" >= #{pd.START"+pda.getString("C_FIELDNAME")+"}";
							}	
							if(json.get("END"+pda.getString("C_FIELDNAME"))!=null&&!"".equals(json.get("END"+pda.getString("C_FIELDNAME")))){
								if(!"".equals(searchStr)){
									searchStr=searchStr+" and ";
								}
								pd.put("END"+pda.getString("C_FIELDNAME"), json.get("END"+pda.getString("C_FIELDNAME"))+" 23:59:59");
								searchStr=searchStr+pda.getString("C_FIELDNAME")+" <= #{pd.END"+pda.getString("C_FIELDNAME")+"}";
							}
							
							if(pda.getString("ISLIST").equals("1")) {
								if("是".equals(is_dic)){
									columnStr += "(select NAME from SYS_DICTIONARIES sd where sd.DICTIONARIES_ID = tc." + pda.getString("C_FIELDNAME") + ") " + pda.getString("C_FIELDNAME") + "NAME,"+pda.getString("C_FIELDNAME")+",";
									if(json.get(pda.getString("C_FIELDNAME"))!=null&&!"".equals(json.get(pda.getString("C_FIELDNAME")))){
										if(!"".equals(searchStr)){
											searchStr=searchStr+" and ";
										}
										pd.put(pda.getString("C_FIELDNAME"), json.get(pda.getString("C_FIELDNAME")));
										searchStr=searchStr+pda.getString("C_FIELDNAME")+"=#{pd."+pda.getString("C_FIELDNAME")+"}";
									}
								}else if("是".equals(is_quote)){
									PageData pd_quote = new PageData();
									pd_quote.put("TEMPLATE_ID", pda.getString("QUOTE_TYPE"));
									pd_quote = templateService.findById(pd_quote);
									if(pd_quote != null){
										columnStr += "(select NAME from " + pd_quote.getString("C_PHYSICSNAME") + " t where t.ID = tc." + pda.getString("C_FIELDNAME") + ") " + pda.getString("C_FIELDNAME") + ",";
										
									}
								}else{
									if("2".equals(String.valueOf(pda.get("I_FIELDTYPE")))){	//日期
										columnStr += "DATE_FORMAT(" + pda.getString("C_FIELDNAME") + ", '%Y-%m-%d %H:%i:%s') " + pda.getString("C_FIELDNAME") + ",";
									}else if("5".equals(String.valueOf(pda.get("I_FIELDTYPE")))){	//文件名称读取
										
									}else{
										if(json.get(pda.getString("C_FIELDNAME"))!=null&&!"".equals(json.get(pda.getString("C_FIELDNAME")))){
											if(!"".equals(searchStr)){
												searchStr=searchStr+" and ";
											}
											pd.put(pda.getString("C_FIELDNAME"), json.get(pda.getString("C_FIELDNAME")));
											searchStr=searchStr+pda.getString("C_FIELDNAME")+" like CONCAT(CONCAT('%', #{pd."+pda.getString("C_FIELDNAME")+"}),'%')";
										}
										columnStr += pda.getString("C_FIELDNAME") + ",";
									}
								}
								if(flag){
									keywordsStr += pda.getString("C_FIELDNAME") + " LIKE CONCAT(CONCAT('%', '" + keywords.trim() + "'),'%') or ";
								}
							}
						}
						
						if("005-1".equals(RESOURCE_TYPE)) {
							columnStr += "IS_DEL,";
						}
						
						
						if(!"".equals(columnStr)){
							columnStr = columnStr.substring(0, columnStr.length() - 1);
							pd.put("columnStr", columnStr);
						}
						if(!"".equals(keywordsStr)){
							keywordsStr = keywordsStr.substring(0, keywordsStr.length() - 3);
							pd.put("keywordsStr", keywordsStr);
						}
						if(!"".equals(searchStr)){
							pd.put("searchStr", searchStr);
						}
						pd.put("C_PHYSICSNAME", pd_tem_all.getString("C_PHYSICSNAME"));
						
						pd.put("condition", condition);
						page.setShowCount(10);
						
						List<PageData> dynamicList = dynamicService.list(page);	//列出Dynamic列表
						objectDynamic.put("dynamicList", dynamicList);
						if(dynamicList.size()>0){
							
							object.put("success","true");
							object.put("data",objectDynamic);
							object.put("pageId",pageIndex);
							object.put("pageCount",page.getTotalPage());
							object.put("recordCount",page.getTotalResult());
						}else{
							object.put("data",objectDynamic);
							object.put("pageId",pageIndex);
							object.put("pageCount",page.getTotalPage());
							object.put("recordCount",page.getTotalResult());
							object.put("success","false");
					        object.put("msg","暂无数据");
						}
					}else{
						object.put("success","false");
				        object.put("msg","未找到模板：模板已删除");
					}
			        
		           // System.out.println(list);
					
				}else if(cmd.equals("goAdd")) { //跳转到新增页面
					
					String ID=json.getString("id")==null?"":json.getString("id");  
			        String RESOURCE_TYPE=json.getString("resource_type")==null?"":json.getString("resource_type");
					String TEMPLATE_ID = json.getString("template_id")==null?"":json.getString("template_id");
					String C_PHYSICSNAME = json.getString("c_physicsname")==null?"":json.getString("c_physicsname");
					pd.put("C_PHYSICSNAME", C_PHYSICSNAME);
					pd.put("TEMPLATE_ID", TEMPLATE_ID);
					pd.put("RESOURCE_TYPE", RESOURCE_TYPE);
					if(pd.get("SORT_ORDER")!=null) {
						pd.put("SORT_ORDER", pd.get("SORT_ORDER"));
					}else{
						PageData pddata=templateService.findByCPhysicsName(pd);
						if(pddata!=null){
							pd.put("SORT_ORDER", pddata.get("SORT_ORDER"));
						}
					}
					Page page = new Page();
					Page page_dic = new Page();
					PageData pd_type = new PageData();
					pd_type = templateService.findById(pd);
					pd.put("I_TYPE", 2);
					page.setPd(pd);
					List<PageData> varList = tcolumnService.list(page);	//列出其他类型的字段，即编辑页面上显示的字段
					String columnStr = "";
					PageData pd_value =new PageData();
					for(PageData pda : varList){
						String is_dic = pda.getString("IS_DIC");
						String is_quote = pda.getString("IS_QUOTE");
						if("是".equals(is_dic)){
							PageData pd_dic = new PageData();
							pd_dic.put("DICTIONARIES_ID", pda.getString("DIC_TYPE"));
							page_dic.setPd(pd_dic);
							page_dic.setShowCount(999);
							List<PageData> dicList = dictionariesService.list(page_dic);
							pda.put(pda.getString("C_FIELDNAME") + "LIST", dicList);
						
						}
						
						if("2".equals(String.valueOf(pda.get("I_FIELDTYPE")))){	//日期
							columnStr += "DATE_FORMAT(" + pda.getString("C_FIELDNAME") + ", '%Y-%m-%d %H:%i:%s') " + pda.getString("C_FIELDNAME") + ",";
						}else{
							columnStr += pda.getString("C_FIELDNAME") + ",";
						}
						pd_value.put(pda.getString("C_FIELDNAME"), "");
					}
					JSONObject fieldObject = data.getJSONObject("data");
					if(!"".equals(columnStr)){
						columnStr = columnStr.substring(0, columnStr.length() - 1);
						pd.put("columnStr", columnStr);
						if(!"".equals(ID)){
							pd.put("ID", ID);
							pd_value = dynamicService.findById(pd);
							for(PageData pda : varList){
								if(pd_value.get((pda.getString("C_FIELDNAME")))==null||"".equals(pd_value.get((pda.getString("C_FIELDNAME"))))){
									pd_value.put(pda.getString("C_FIELDNAME"), "");
								}
							}
							fieldObject.put("fieldValue", pd_value);
						}
					}
					
					fieldObject.put("resource_type", RESOURCE_TYPE);	
					fieldObject.put("c_physicsname", C_PHYSICSNAME);	
					fieldObject.put("template_id", TEMPLATE_ID);			//传入表的ID，作为参照
					if(pd_type != null){
						fieldObject.put("type", pd_type.get("TYPE"));
					}
					fieldObject.put("uuid", UuidUtil.get32UUID());
					fieldObject.put("fields", varList);
					object.put("data", fieldObject);
				    object.put("success","true");
			       
				}else if(cmd.equals("del")) {
					String ID=json.getString("ID")==null?"":json.getString("ID");  
			        String RESOURCE_TYPE=json.getString("resource_type")==null?"":json.getString("resource_type");
					String TEMPLATE_ID = json.getString("template_id")==null?"":json.getString("template_id");
					String C_PHYSICSNAME = json.getString("c_physicsname")==null?"":json.getString("c_physicsname");
					pd.put("C_PHYSICSNAME", C_PHYSICSNAME);
					pd.put("TEMPLATE_ID", TEMPLATE_ID);
					pd.put("RESOURCE_TYPE", RESOURCE_TYPE);
					pd.put("ID", ID);
					//根据ID删除信息
					dynamicService.delete(pd);	
					object.put("success","true");
				    object.put("msg","删除成功");
				}else if(cmd.equals("save")) {
					Page page = new Page(); 
					String RESOURCE_TYPE=json.getString("resource_type")==null?"":json.getString("resource_type");
					String TEMPLATE_ID = json.getString("template_id")==null?"":json.getString("template_id");
					String C_PHYSICSNAME = json.getString("c_physicsname")==null?"":json.getString("c_physicsname");
					pd.put("C_PHYSICSNAME", C_PHYSICSNAME);
					pd.put("TEMPLATE_ID", TEMPLATE_ID);
					pd.put("RESOURCE_TYPE", RESOURCE_TYPE);
			        pd.put("I_TYPE", 2);
			        
					page.setPd(pd);
					List<PageData> varList = tcolumnService.list(page);	//列出其他类型的字段，即编辑页面上显示的字段
					
			        String columnStr = "ID,";
					String valueStr = "#{ID},";
					if(pd.getString("UUID")!=null && !pd.getString("UUID").equals("") ) {
						//columnStr += "RELATION_ID,";
						//valueStr += "#{RELATION_ID},";
					}
					for(PageData pda : varList){
						if(pda.getString("ISNEW").equals("1")) {
							String is_dic = pda.getString("IS_DIC");
							/*if ("是".equals(is_dic)) {
								PageData pd_dic = new PageData();
								pd_dic.put("DICTIONARIES_ID", pda.getString("DIC_TYPE"));
								page_dic.setPd(pd_dic);
								List<PageData> dicList = dictionariesService.list(page_dic);
								mv.addObject(pda.getString("C_FIELDNAME") + "LIST", dicList);
							}*/
							if ("2".equals(String.valueOf(pda.get("I_FIELDTYPE")))) {    //日期
								valueStr += "#{" + pda.getString("C_FIELDNAME") + "},";
							} else {
								valueStr += "#{" + pda.getString("C_FIELDNAME") + "},";
							}
							if ("5".equals(String.valueOf(pda.get("I_FIELDTYPE")))) {    //文件
								/*List<PageData> datas = UploadUtils.newUploadFiles("/upload/dynamic/file/", files, request);//文件上传
								String ANNEX = filesService.save(datas);
								if (!ANNEX.equals("")) {
									pd.put(pda.getString("C_FIELDNAME"), ANNEX);//文件
								} else {
									pd.put(pda.getString("C_FIELDNAME"), "");//文件
								}*/
								pd.put(pda.getString("C_FIELDNAME"), "");//文件
							}
							if ("6".equals(String.valueOf(pda.get("I_FIELDTYPE")))) {    //图片
								/*String PIC = UploadUtils.uploadPictures("/upload/dynamic/pic/", pictures, request);//图片上传
								if (!PIC.equals("")) {
									pd.put(pda.getString("C_FIELDNAME"), PIC);//图片
								} else {
									pd.put(pda.getString("C_FIELDNAME"), "");//图片
								}*/
							}
							pd.put(pda.getString("C_FIELDNAME"), json.get(pda.getString("C_FIELDNAME")));
							columnStr += pda.getString("C_FIELDNAME") + ",";
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
					String id = pd.getString("ID")!=null && !pd.getString("ID").equals("")?pd.getString("ID"): UuidUtil.get32UUID();
					pd.put("ID", id);	//主键
					
					pd.put("CREATEMAN",pd_token.getString("ID"));	//创建人
					dynamicService.save(pd);
					object.put("success","true");
			        object.put("msg","保存成功");
			        
				}else if(cmd.equals("edit")) {
					Page page = new Page(); 
					Page page_dic = new Page();
					String RESOURCE_TYPE=json.getString("resource_type")==null?"":json.getString("resource_type");
					String TEMPLATE_ID = json.getString("template_id")==null?"":json.getString("template_id");
					String C_PHYSICSNAME = json.getString("c_physicsname")==null?"":json.getString("c_physicsname");
					String id = json.getString("ID");
					if(id==null||"".equals("id")){
						object.put("success","false");
				        object.put("msg","ID不能为空");
				        return object;
					}
					pd.put("C_PHYSICSNAME", C_PHYSICSNAME);
					pd.put("TEMPLATE_ID", TEMPLATE_ID);
					pd.put("RESOURCE_TYPE", RESOURCE_TYPE);
			        pd.put("I_TYPE", 2);
			        
					page.setPd(pd);
					List<PageData> varList = tcolumnService.list(page);	//列出其他类型的字段，即编辑页面上显示的字段
					
			        String columnStr = "ID,";
					String valueStr = "#{ID},";
					String updateSql = "";
					if(pd.getString("UUID")!=null && !pd.getString("UUID").equals("") ) {
						//columnStr += "RELATION_ID,";
						//valueStr += "#{RELATION_ID},";
					}
					for(PageData pda : varList){
						if(pda.getString("ISNEW").equals("1")) {
							String is_dic = pda.getString("IS_DIC");
							/*if ("是".equals(is_dic)) {
								PageData pd_dic = new PageData();
								pd_dic.put("DICTIONARIES_ID", pda.getString("DIC_TYPE"));
								page_dic.setPd(pd_dic);
								List<PageData> dicList = dictionariesService.list(page_dic);
								mv.addObject(pda.getString("C_FIELDNAME") + "LIST", dicList);
							}*/
							if ("2".equals(String.valueOf(pda.get("I_FIELDTYPE")))) {    //日期
								valueStr += "#{" + pda.getString("C_FIELDNAME") + "},";
							} else {
								valueStr += "#{" + pda.getString("C_FIELDNAME") + "},";
							}
							
							if ("5".equals(String.valueOf(pda.get("I_FIELDTYPE")))) {    //文件
								/*List<PageData> datas = UploadUtils.newUploadFiles("/upload/dynamic/file/", files, request);//文件上传
								String ANNEX = filesService.save(datas);
								if (!ANNEX.equals("")) {
									pd.put(pda.getString("C_FIELDNAME"), ANNEX);//文件
								} else {
									pd.put(pda.getString("C_FIELDNAME"), "");//文件
								}*/
								pd.put(pda.getString("C_FIELDNAME"), "");//文件
							}
							if ("6".equals(String.valueOf(pda.get("I_FIELDTYPE")))) {    //图片
								/*String PIC = UploadUtils.uploadPictures("/upload/dynamic/pic/", pictures, request);//图片上传
								if (!PIC.equals("")) {
									pd.put(pda.getString("C_FIELDNAME"), PIC);//图片
								} else {
									pd.put(pda.getString("C_FIELDNAME"), "");//图片
								}*/
							}
							updateSql += pda.getString("C_FIELDNAME") + "= #{" + pda.getString("C_FIELDNAME") + "},";
							pd.put(pda.getString("C_FIELDNAME"), json.get(pda.getString("C_FIELDNAME"))==null?"": json.get(pda.getString("C_FIELDNAME")));
							columnStr += pda.getString("C_FIELDNAME") + ",";
						}
					}
					
					if(!"".equals(columnStr)){
						columnStr = columnStr.substring(0, columnStr.length() - 1);
						pd.put("columnStr", columnStr);
					}
					
					if(!"".equals(updateSql)){
						updateSql = updateSql.substring(0, updateSql.length() - 1);
						pd.put("updateSql", updateSql);
					}
					if(!"".equals(valueStr)){
						valueStr = valueStr.substring(0, valueStr.length() - 1);
						pd.put("valueStr", valueStr);
					}
					pd.put("ID", id);	//主键
					
					pd.put("CREATEMAN",pd_token.getString("ID"));	//创建人
					dynamicService.edit(pd);
					object.put("success","true");
			        object.put("msg","修改成功");
			        
				}else{
					object.put("success","false");
			        object.put("msg","访问异常");
				}
			}else{
				object.put("success","false");
				object.put("msg","登录超时，请重新登录");
			}
		}catch(Exception e){
			e.printStackTrace();
			object.put("success","false");
			object.put("msg","操作异常");
		}	
        return object;
	}
	
	
	/**
	 * 下载导入模板
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/downloadMB")
	@ResponseBody
	public String downloadMB(HttpServletResponse response) throws Exception{
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		Page page = new Page();
		pd = this.getPageData();
		String TEMPLATE_ID = (null == pd.getString("template_id"))?"":pd.getString("template_id");
		pd.put("TEMPLATE_ID", TEMPLATE_ID);
		pd.put("I_TYPE", 2);
		page.setPd(pd);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		List<PageData> varList = new ArrayList<PageData>();
		List<PageData> varList_column = tcolumnService.list(page);	//列出其他类型的字段，即编辑页面上显示的字段
		for(PageData pda : varList_column){
			titles.add(pda.getString("C_DISPLAYLABEL"));
		}
		dataMap.put("titles", titles);
		dataMap.put("varList", varList);
		try{
			Date date = new Date();
			String formatFileName = Tools.date2Str(date, "yyyyMMddHHmmss");
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=" + formatFileName + ".xls");
			response.setContentType("application/msexcel");

			WritableWorkbook wbook = Workbook.createWorkbook(os);
			WritableSheet wsheet = wbook.createSheet(formatFileName, 0);


			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat wcfFC = new WritableCellFormat(wfont);
			wcfFC.setBackground(Colour.AQUA);
			wfont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			wcfFC = new WritableCellFormat(wfont);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
			cellFormat.setFont(wfont);
			
			for(int i=0; i<titles.size(); i++){ //设置标题
				String title = titles.get(i);
				Label label = new Label(i, 0, title,wcfFC);
				label.setCellFormat(cellFormat);
				wsheet.addCell(label);
				wsheet.setColumnView(i, 15);
				
			}

			int num = 1;
			/*
			for(int i=0; i<varList.size(); i++){
				PageData vpd = varList.get(i);
				for(int j=0;j<titles.size();j++){
					String varstr = vpd.getString("var"+(j+1)) != null ? vpd.getString("var"+(j+1)) : "";
					wsheet.addCell(new Label(j, i+1, varstr));	    //1
				}
			}*/
			
			wbook.write();
			wbook.close();
			os.close();

			return null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println(ex);
		}
		return null;
	}
	
	
	
	/**
	 * 导入保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/saveImport",produces = "text/html;charset=utf-8")
	@ResponseBody
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true") 
	public String editFile(@RequestParam(value = "fileName",required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response){
		Page page = new Page();
		PageData pd = new PageData();
		PageData pd_save = new PageData();
		JSONObject object=new JSONObject();
		
		pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		PageData pd_stoken = new PageData();
		
		pd_stoken.put("TOKENID", pd.get("tokenid"));
		PageData pd_token=new PageData();;
		try {
			pd_token = zxlbService.findByTokenId(pd_stoken);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(pd_token==null){
			object.put("success","false");
		    object.put("msg","导入失败，还未登录成功");
		    return object.toString();
		}
		
		
		String type = pd.getString("TYPE");
		List<List<Map<String,String>>> impList=null;
		ExcelReader reader=new ExcelReader();
		Map<String,String> titleMap = new HashMap<String,String>();
		Map<String,String> bodyMap = new HashMap<String,String>();
		String operator = pd_token.getString("ID");	//操作人的登录名
		try {
			impList=reader.getExcellData(file.getInputStream());
	        if(impList!=null && !impList.isEmpty() && impList.get(0)!=null && !impList.get(0).isEmpty()){
	        	int successCount = 0;
	        	int repeatCount = 0;
	        	int failCount = 0;
	        	String repeatMsg = "";
	        	String cPhysicsName = pd.getString("c_physicsname");
	        	String TEMPLATE_ID = (null == pd.getString("template_id"))?"":pd.getString("template_id");
	    		pd.put("TEMPLATE_ID", TEMPLATE_ID);
	    		pd.put("I_TYPE", 2);
	    		page.setPd(pd);
	    		List<PageData> varList_column = tcolumnService.list(page);	//列出其他类型的字段，即编辑页面上显示的字段
	    		PageData pd_template = templateService.findByCPhysicsName(pd);
	    		String columnStr = "ID,";
	    		String valueStr = "#{ID},";
	    		String clumnStr_select = "";
	    		String dicStr = "";
	    		String quoteStr = "";
	    		Map<String, String> map = new HashMap<String, String>();
	    		Map<String, String> map_quote = new HashMap<String, String>();
	    		for(PageData pda : varList_column){
	    			String is_dic = pda.getString("IS_DIC");
	    			String is_quote = pda.getString("IS_QUOTE");
	    			if("是".equals(is_dic)){
	    				PageData pd_dic = new PageData();
	    				pd_dic.put("DICTIONARIES_ID", pda.getString("DIC_TYPE"));
	    				dicStr += pda.getString("C_FIELDNAME") + ",";
	    				map.put(pda.getString("C_FIELDNAME"), pda.getString("DIC_TYPE"));
	    				clumnStr_select += "(select NAME from SYS_DICTIONARIES sd where sd.DICTIONARIES_ID =" + pda.getString("C_FIELDNAME") + ") " + pda.getString("C_FIELDNAME") + ",";
	    				valueStr += "#{" + pda.getString("C_FIELDNAME") + "},";
	    			}else{
	    				clumnStr_select += pda.getString("C_FIELDNAME") + ",";
	    				valueStr += "#{" + pda.getString("C_FIELDNAME") + "},";
		    			
	    			}
	    			columnStr += pda.getString("C_FIELDNAME") + ",";
	    			
	    		}
	    		if(!"".equals(columnStr)){
	    			columnStr = columnStr.substring(0, columnStr.length() - 1);
	    			clumnStr_select = clumnStr_select.substring(0, clumnStr_select.length() - 1);
	    			pd.put("columnStr", clumnStr_select);
	    		}
	    		if(!"".equals(valueStr)){
	    			valueStr = valueStr.substring(0, valueStr.length() - 1);
	    		}
	    		if(!"".equals(dicStr)){
	    			dicStr = dicStr.substring(0, dicStr.length() - 1);
	    		}
	    		if(!"".equals(quoteStr)){
	    			quoteStr = quoteStr.substring(0, quoteStr.length() - 1);
	    		}
				for(int i=0; i<impList.get(0).size();i++){//表头不导入
					pd_save = new PageData();
					pd_save.put("columnStr", columnStr);
					String valueStr_tmp = valueStr;
					pd_save.put("C_PHYSICSNAME", cPhysicsName);
					if(i==0){
						titleMap = impList.get(0).get(0);//表头不导入
						if(titleMap!=null && !titleMap.isEmpty()){
							for (Entry<String, String> m : titleMap.entrySet()){
								for(PageData pda : varList_column){
									if(m.getKey().equals(pda.getString("C_DISPLAYLABEL"))){
										titleMap.put(pda.getString("C_DISPLAYLABEL"), pda.getString("C_FIELDNAME"));
										break;
									}
								}
							}
						}
					}else{
						bodyMap = impList.get(0).get(i);
						boolean save_flag = true;
						PageData pd_check = new PageData();
						if(bodyMap!=null && !bodyMap.isEmpty()){
							for (Entry<String, String> m1 :bodyMap.entrySet())  {
								if(!"".equals(m1.getValue().toString().trim())){
						            for (Entry<String, String> m2 :titleMap.entrySet()){
						            	if(m1.getKey().equals(m2.getKey())){
						            		pd_check.put(m2.getValue().toUpperCase(), m1.getValue().toString().trim());
						            		if(dicStr.contains(m2.getValue())){
						            			String dic_id = "(select DICTIONARIES_ID from SYS_DICTIONARIES sd where sd.NAME = '" + m1.getValue().toString().trim() + "' and sd.PARENT_ID = '" + map.get(m2.getValue()) + "')";
						            			valueStr_tmp = valueStr_tmp.replace("#{"+m2.getValue().toUpperCase()+"}", dic_id);
							            		break;
						            		}else{
						            			valueStr_tmp = valueStr_tmp.replace("#{"+m2.getValue().toUpperCase()+"}", "'"+m1.getValue().toString().trim()+"'");
							            		break;
						            		}
						            	}
						            }
								}else{
									save_flag = false;
			            			break;
			            		}
							}
						}else{
							System.out.println("无数据");
						}
						if(save_flag){
							if("1".equals(type)){
								pd_check.put("C_PHYSICSNAME", cPhysicsName);
								pd_check = dynamicService.checkRepeat(pd_check);
								if(pd_check != null){
									int count = Integer.parseInt(String.valueOf(pd_check.get("COUNT")));
									if(count > 0){
										repeatCount++;
									}else{
										pd_save.put("valueStr", valueStr_tmp);
										String id = this.get32UUID();
										try {
											pd_save.put("ID", id);
											pd_save.put("CREATEMAN", operator);
											dynamicService.save(pd_save);
											
											successCount++;
										} catch (Exception e) {
											failCount++;
											logger.error(e.toString(), e);
										}
									}
								}else{
									System.out.println("未查到");
								}
							}else{
								pd_save.put("valueStr", valueStr_tmp);
								String id = this.get32UUID();
								try {
									pd_save.put("ID", id);
									pd_save.put("CREATEMAN", operator);
									pd_save.put("ORDER_ID", 100);
									dynamicService.save(pd_save);
									PageData pd_temp = dynamicService.findById(pd_save);
									
									successCount++;
								} catch (Exception e) {
									failCount++;
									logger.error(e.toString(), e);
								}
							}
						}
					}
				}
				//mv.addObject("successCount", successCount);
				//mv.addObject("repeatCount", repeatCount);
				//mv.addObject("failCount", failCount);
				PageData pd_log = new PageData();
				if(repeatCount > 0){
					repeatMsg = "重复" + repeatCount + "条数据,";
				}
				//pd_log.put("REMARK", "总条数为" + (successCount+failCount) + "条，成功导入" + successCount + "条数据," + repeatMsg + "导入失败" + failCount + "条");
				//mv.addObject("totalCount", successCount+repeatCount+failCount);
				object.put("success", "true");
				object.put("msg", "总条数为" + (successCount+failCount) + "条，成功导入" + successCount + "条数据," + repeatMsg + "导入失败" + failCount + "条");
				
	        }else{
	        	object.put("success", "false");
				object.put("msg", "读取excel失败");
				
	        	//System.out.println("读取excel失败");
	        }
		} catch (Exception e) {
			object.put("success", "false");
			object.put("msg", e.toString());
			logger.error(e.toString(), e);
		}
		/*String resource_type = pd.getString("RESOUCE_TYPE")==null?"":pd.getString("RESOUCE_TYPE");
		if("005-1".equals(resource_type)){	
			dynamicService.callUpdate();
		}*/
		
		return object.toString();
	}
	
	
	/**
	 * 导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	@ResponseBody
	public String exportExcel(HttpServletResponse response) throws Exception{
		PageData pd = new PageData();
		Page page = new Page();
		JSONObject object=new JSONObject();
		pd = this.getPageData();
		
		PageData pd_stoken = new PageData();
		pd_stoken.put("TOKENID", pd.get("tokenid"));
		PageData pd_token=zxlbService.findByTokenId(pd_stoken);
		if(pd_token==null){
			object.put("success","false");
		    object.put("msg","导入失败，还未登录成功");
		    return object.toString();
			
		}
		String RESOURCE_TYPE=pd.getString("resource_type")==null?"":pd.getString("resource_type");
		String TEMPLATE_ID = pd.getString("template_id")==null?"":pd.getString("template_id");
		String C_PHYSICSNAME = pd.getString("c_physicsname")==null?"":pd.getString("c_physicsname");
		pd.put("C_PHYSICSNAME", C_PHYSICSNAME);
		pd.put("TEMPLATE_ID", TEMPLATE_ID);
		pd.put("RESOURCE_TYPE", RESOURCE_TYPE);
		pd.put("TEMPLATE_ID", TEMPLATE_ID);
		pd.put("I_TYPE", 2);
		page.setPd(pd);
		Map<String,Object> dataMap = new HashMap<String,Object>();
		Map<String,Object> dataFieldMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		List<String> fields = new ArrayList<String>();
		List<PageData> varList_column = tcolumnService.list(page);	//列出其他类型的字段，即编辑页面上显示的字段
		String columnStr = "";
		String columns = "";
		String searchStr="";
		for(PageData pda : varList_column){
			String is_dic = pda.getString("IS_DIC");
			if("是".equals(is_dic)){
				columnStr += "(select NAME from SYS_DICTIONARIES sd where sd.DICTIONARIES_ID = " + pda.getString("C_FIELDNAME") + ") " + pda.getString("C_FIELDNAME") + ",";
				columns += pda.getString("C_FIELDNAME") + ",";
				if(pd.get(pda.getString("C_FIELDNAME"))!=null&&!"".equals(pd.get(pda.getString("C_FIELDNAME")))){
					if(!"".equals(searchStr)){
						searchStr=searchStr+" and ";
					}
					searchStr=searchStr+pda.getString("C_FIELDNAME")+"=#{"+pda.getString("C_FIELDNAME")+"}";
				}
			}else{
				columnStr += pda.getString("C_FIELDNAME") + ",";
				if(pd.get(pda.getString("C_FIELDNAME"))!=null&&!"".equals(pd.get(pda.getString("C_FIELDNAME")))){
					if(!"".equals(searchStr)){
						searchStr=searchStr+" and ";
					}
					searchStr=searchStr+pda.getString("C_FIELDNAME")+" like CONCAT(CONCAT('%', #{"+pda.getString("C_FIELDNAME")+"}),'%')";
				}
			}
			if(pd.get("START"+pda.getString("C_FIELDNAME"))!=null&&!"".equals(pd.get("START"+pda.getString("C_FIELDNAME")))){
				if(!"".equals(searchStr)){
					searchStr=searchStr+" and ";
				}
				searchStr=searchStr+pda.getString("C_FIELDNAME")+" >= #{START"+pda.getString("C_FIELDNAME")+"}";
			}	
			if(pd.get("END"+pda.getString("C_FIELDNAME"))!=null&&!"".equals(pd.get("END"+pda.getString("C_FIELDNAME")))){
				if(!"".equals(searchStr)){
					searchStr=searchStr+" and ";
				}
				pd.put("END"+pda.getString("C_FIELDNAME"),pd.get("END"+pda.getString("C_FIELDNAME"))+" 23:59:59");
				searchStr=searchStr+pda.getString("C_FIELDNAME")+" <= #{END"+pda.getString("C_FIELDNAME")+"}";
			}	
			
			titles.add(pda.getString("C_DISPLAYLABEL"));
			fields.add(pda.getString("C_FIELDNAME"));
		}
		int size = columns.split(",").length + 2;	//列的长度
		String[] column = new String[size];
		if(!"".equals(columnStr)){
			columns = columns.substring(0, columns.length() - 1);
			int count = 0;
			for(String str : columns.split(",")){
				column[count] = str;
				count++;
			}
			columnStr = columnStr.substring(0, columnStr.length() - 1);
			pd.put("columnStr", columnStr);
		}
		if(!"".equals(searchStr)){
			pd.put("searchStr", searchStr);
		}
		
		titles.add("创建人");
		titles.add("创建时间");
		fields.add("CREATEMAN");
		fields.add("CREATEDATE");
		column[size-2] = "CREATEMAN";
		column[size-1] = "CREATEDATE";
		dataMap.put("titles", titles);
		
		List<PageData> varOList = dynamicService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		/*for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			for(int j=1;j<column.length+1;j++){
				vpd.put("var"+j, String.valueOf(null==varOList.get(i).get(column[j-1])?"":varOList.get(i).get(column[j-1])));
			}
			varList.add(vpd);
		}*/
		dataMap.put("varList", varOList);
		
		try{
			Date date = new Date();
			String formatFileName = Tools.date2Str(date, "yyyyMMddHHmmss");
			OutputStream os = response.getOutputStream();
			response.reset();
			response.setHeader("Content-disposition", "attachment; filename=" + formatFileName + ".xls");
			response.setContentType("application/msexcel");

			WritableWorkbook wbook = Workbook.createWorkbook(os);
			WritableSheet wsheet = wbook.createSheet(formatFileName, 0);


			WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			WritableCellFormat wcfFC = new WritableCellFormat(wfont);
			wcfFC.setBackground(Colour.AQUA);
			wfont = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
			wcfFC = new WritableCellFormat(wfont);
			WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
			cellFormat.setFont(wfont);
			
			for(int i=0; i<titles.size(); i++){ //设置标题
				String title = titles.get(i);
				Label label = new Label(i, 0, title,wcfFC);
				label.setCellFormat(cellFormat);
				wsheet.addCell(label);
				wsheet.setColumnView(i, 15);
				
			}

			int num = 1;
			String varstr ="";
			System.out.println(titles);
			System.out.println(varOList);
			for(int i=0; i<varOList.size(); i++){
				PageData vpd = varOList.get(i);
				for(int j=0;j<titles.size();j++){
					varstr = vpd.getString(fields.get(j)) != null ? vpd.getString(fields.get(j)) : "";
					wsheet.addCell(new Label(j, i+1, varstr));	    //1
				}
			}
			
			wbook.write();
			wbook.close();
			os.close();

			return null;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			System.out.println(ex);
		}
		
		
		return object.toString();
	}
		
	
	/*
	 * 获取编码
	 */
	public PageData getNum(String year) throws Exception{
		PageData pd_num = new PageData();
		pd_num.put("year", year);
		pd_num = consultService.findByYear(pd_num);
		if(pd_num == null){
			pd_num = new PageData();
			pd_num.put("year", year);
			pd_num.put("code_num", "1");
		}else{
			int code_num = Integer.parseInt(String.valueOf(pd_num.get("code_num")))+1;
			pd_num.put("code_num", code_num);
		}
		return pd_num;
	}
	
	
	
	
}

