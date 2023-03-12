package com.fh.controller.app.appuser;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yulun.utils.DesCrypt;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.dom4j.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;






import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.fh.controller.activiti.AcStartController;
import com.fh.controller.activiti.rutask.RuTaskController;
import com.fh.controller.base.BaseController;
import com.fh.entity.OAuthInfo;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.User;
import com.fh.service.activiti.hiprocdef.HiprocdefManager;
import com.fh.service.activiti.ruprocdef.RuprocdefManager;
import com.fh.service.areamanage.AreaManageManager;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.menu.MenuManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FileDownloadFromUrl;
import com.fh.util.FileUpload;
import com.fh.util.FilecopyHtml;
import com.fh.util.IDUtils;
import com.fh.util.Jurisdiction;
import com.fh.util.MD5;
import com.fh.util.MyUtils;
import com.fh.util.ObjectExcelRead;
import com.fh.util.PageData;
import com.fh.util.PathUtil;
import com.fh.util.Signwx;
import com.fh.util.StringUtils;
import com.fh.util.Tools;
import com.fh.util.ZipTools;
import com.xxgl.utils.ResponseUtils;
import com.xxgl.service.mng.DocManager;
import com.xxgl.service.mng.TdmMobileManager;
import com.xxgl.service.mng.WorkorderManager;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.excel.ImportExcelService;
import com.yulun.service.AddressManager;
import com.yulun.service.BlacklistManager;
import com.yulun.service.RecordManager;
import com.yulun.service.ServerLogManager;
import com.yulun.utils.BuildTree;





/**@author
  * 会员-接口类 
  * 相关参数协议：
  * 00	请求失败
  * 01	请求成功
  * 02	返回空值
  * 03	请求协议参数不完整    
  * 04  用户名或密码错误
  * 05  FKEY验证失败
 */
@Controller
@RequestMapping(value="/appuser")
public class IntAppuserController extends AcStartController{
    
	@Resource(name="appuserService")
	private AppuserManager appuserService;
	
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	@Resource(name="serverLogService")
	private ServerLogManager serverLogService;
	
	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;
	
	@Resource(name="recordService")
	private RecordManager recordService;

	@Resource(name="tdmMobileService")
	private TdmMobileManager tdmMobileService;
	
	@Resource(name="addressService")
	private AddressManager addressService;
	
	@Resource(name="areamanageService")
	private AreaManageManager areamanageService;
	
	@Resource(name="blacklistService")
	private BlacklistManager blacklistService;
	
	@Resource(name="workorderService")
	private WorkorderManager workorderService;
	
	@Resource(name="userService")
	private UserManager userService;
	
	@Resource(name="ruprocdefService")
	private RuprocdefManager ruprocdefService;
	@Resource(name="hiprocdefService")
	private HiprocdefManager hiprocdefService;
	//@Autowired
	@Resource(name="taskService")
	private TaskService taskService; 			//任务管理 与正在执行的任务管理相关的Service
	
	@Resource(name="docService")
	private DocManager docService;
	
	@Resource(name="menuService")
	private MenuManager menuService;
	
	/**获取TDM_MOOLILE
	 * @return 
	 * @throws Exception 
	 */
	//private static String Key = "d41d8cd98f00b204e980";

	@RequestMapping(value="/getTdmMobile")
	@ResponseBody
	public void getTdmmobile(HttpServletResponse response,HttpServletRequest request) throws Exception{
		/*
		List<PageData> list=new ArrayList();
		PageData mobilePageData=new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String mobile = pd.getString("mobile")==null?"":pd.getString("mobile");
		
		JSONObject object = new JSONObject();
		try{
			if(mobile.length()==11){
				pd.put("MOBILE", mobile.substring(0, 7));
				mobilePageData=tdmMobileService.findtdmMobileByMobile(pd);
				if(mobilePageData!=null){
					object.put("data", mobilePageData.getString("PROVINCE")+"-"+mobilePageData.getString("CITY"));
					object.put("result", true);
				}else{
					object.put("msg", "未查询到记录");
					object.put("result", false);
				}
			}else{
				object.put("msg", "手机号码位数不正确");
				object.put("result", false);
			}
		}catch (Exception e){
			logger.error(e.toString(), e);
			object.put("msg", e.toString());
			object.put("result", false);
		}finally{
			logAfter(logger);
		}
		String desstr=  DesUtil.encrypt(object.toString());
		System.out.println(desstr);
		System.out.println(DesUtil.decrypt(desstr));
		ResponseUtils.renderJson(response,desstr);
		//return AppUtil.returnObject(new PageData(), Map);
*/	}
	
	/**获取用户登录信息
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping(value="/userLogin")
	@ResponseBody
	
	public String userLogin(HttpServletResponse response,HttpServletRequest request) throws Exception{
		response.setHeader("Access-Control-Allow-Origin", "*");
		JSONObject object = new JSONObject();
		JSONObject object_data = new JSONObject();
		DesCrypt desCrypt=new DesCrypt();
		String data = request.getParameter("data");
		String desData= desCrypt.decode(data.replaceAll(" ","+"));
		com.alibaba.fastjson.JSONObject string_to_json = com.alibaba.fastjson.JSONObject.parseObject(desData);
		if(data==null||data.equals("")){
			object.put("success","false");
			object.put("msg","获取信息失败！");
			ResponseUtils.renderJson(response, object.toString());
			return null;
		}
//		JSONObject string_to_json = JSONObject.fromObject(data);
		com.alibaba.fastjson.JSONObject json_to_data = string_to_json.getJSONObject("data");
		String userId =json_to_data.getString("userId"); 
		String userPwd = json_to_data.getString("userPwd");
		
		if(userId!=null&&!userId.equals("")&&userPwd!=null&&!userPwd.equals("")){
			String ip=request.getRemoteAddr();
			HashMap<String, String> data_s = new HashMap<String, String>();
			PageData pd_s=new PageData();
			pd_s.put("ZXID", userId);
			pd_s.put("PWD", MD5.md5(userPwd));
			data_s.put("loginip", ip);
			PageData pd = zxlbService.findByZxyh(pd_s);
			if(pd!=null&&pd.getString("ZT")!=null&&pd.getString("ZT").equals("0")){
				pd_s.put("param_code", "ctisvr");
				PageData pd_param = zxlbService.findByParamCode(pd_s);
				
				pd_s.put("param_code", "ivrvdn");
				PageData pd_param_iv = zxlbService.findByParamCode(pd_s);
				
				
				
				String tokenid=IDUtils.createUUID();
				pd_s.put("TOKENID", tokenid);
				pd_s.put("ZXYH", pd.getString("ZXYH"));
				
				zxlbService.editTokenid(pd_s);
				object_data.put("tokenid", tokenid);
				object_data.put("userId", userId);
				object_data.put("ctisvr", pd_param.getString("param_value"));
				object_data.put("ivrvdn", pd_param_iv.getString("param_value"));
				object_data.put("userName",pd.getString("ZXXM")==null?"":pd.getString("ZXXM"));
				object_data.put("role",pd.getString("BIANMA")==null?"":pd.getString("BIANMA"));
				object_data.put("rolename",pd.getString("ZXJSNAME"));
				
				if(pd!=null&&pd.getString("menu_ids")!=null){
					List<PageData> pdList= menuService.listMenufrontByMenuids(pd);
					List<JSONObject> object_menulist=new ArrayList();
					JSONObject object_menu = new JSONObject();
					for(PageData pd_menu:pdList){
						object_menu = new JSONObject();
						object_menu.put("menu_id", pd_menu.getString("MENU_ID"));
						object_menu.put("menu_name", pd_menu.getString("MENU_NAME"));
						object_menu.put("menu_url", pd_menu.getString("MENU_URL"));
						object_menu.put("menu_icon", pd_menu.getString("MENU_ICON"));
						object_menu.put("parent_id", pd_menu.getString("PARENT_ID"));
						object_menu.put("menu_order", pd_menu.getString("MENU_ORDER"));
						//object_menu.put("menu_state", pd_menu.getString("MENU_STATE"));
						object_menu.put("menu_type", pd_menu.getString("MENU_TYPE"));
						object_menulist.add(object_menu);
					}
					
					object_data.put("menuList",object_menulist.toString());
				}
				
				String zjn="";
				String[] array=null;
				String zjnname="";
				zjn=pd.getString("zjn");
				if(zjn!=null&&!zjn.equals("")){
					zjnname="";
					array=zjn.split(",");
					List<PageData> dict= dictionariesService.listAllDictByDict(array);
					for(PageData pageData1:dict){
						if(!zjnname.equals("")){
							zjnname=zjnname+",";
						}
						zjnname=zjnname+pageData1.getString("BIANMA");
					}
				}
					
				
				object_data.put("zxjn",zjnname);
				//object_data.put("zxjnname",zjnname);
				object_data.put("zxfz",pd.getString("zbh")==null?"":pd.getString("zbh"));
				object_data.put("zxfzname",pd.getString("zmc"));
				
				object_data.put("zxid",pd.getString("ZXID"));
				object_data.put("fjhm",pd.getString("FJHM"));
				object.put("success","true");
				object.put("data",object_data.toString());

                long end = System.currentTimeMillis();
			}else{
				if(pd!=null&&pd.getString("ZT")!=null&&pd.getString("ZT").equals("-1")){
					object.put("success","false");
					object.put("msg","该用户已冻结，请检查！");
				}else{
					object.put("success","false");
					object.put("msg","用户账号或密码错误");
				}
				
			}
		}else{
			object.put("success","false");
			object.put("msg","登录超时，请重新登录");
		}

		ResponseUtils.renderJson(response,desCrypt.encode(object.toString()) );
//		ResponseUtils.renderJson(response,object.toString() );
		return null;
	}
	
	
	
	@RequestMapping(value="/getMenus")
	@ResponseBody
	public String getMenus(HttpServletResponse response,HttpServletRequest request) throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject object = new JSONObject();
		JSONObject object_data = new JSONObject();
        DesCrypt desCrypt=new DesCrypt();
		try{
			String data = request.getParameter("data");
			if(data==null||data.equals("")){
				object.put("success","false");
				object.put("msg","获取信息失败！");
				ResponseUtils.renderJson(response, object.toString());
				return null;
			}
//			JSONObject string_to_json = JSONObject.fromObject(data);
			String desData= desCrypt.decode(data.replaceAll(" ","+"));
			JSONObject string_to_json = JSONObject.fromObject(desData);
			JSONObject json_to_data = string_to_json.getJSONObject("data");
			String tokenid =json_to_data.getString("tokenid"); 
			
			PageData pd_stoken=new PageData();
			
			pd_stoken.put("TOKENID", tokenid);
			PageData pd_token=zxlbService.findByTokenId(pd_stoken);
			
			if(pd_token!=null){
				String ip=request.getRemoteAddr();
				if(pd_token!=null&&pd_token.getString("menu_ids")!=null){
					List<PageData> pdList= menuService.listMenufrontByMenuids(pd_token);
					List<JSONObject> object_menulist=new ArrayList();
					JSONObject object_menu = new JSONObject();
					for(PageData pd_menu:pdList){
						object_menu = new JSONObject();
						
						object_menu.put("menu_id", pd_menu.getString("MENU_ID"));
						object_menu.put("menu_name", pd_menu.getString("MENU_NAME"));
						object_menu.put("menu_url", pd_menu.getString("MENU_URL"));
						object_menu.put("menu_icon", pd_menu.getString("MENU_ICON"));
						object_menu.put("parent_id", pd_menu.getString("PARENT_ID"));
						object_menu.put("menu_order", pd_menu.getString("MENU_ORDER"));
						//object_menu.put("menu_state", pd_menu.getString("MENU_STATE"));
						object_menu.put("menu_type", pd_menu.getString("MENU_TYPE"));
						object_menulist.add(object_menu);
					}
					
					object.put("data",object_menulist.toString());
					object.put("success","true");
				}else{
					object.put("success","false");
					object.put("msg","暂无菜单数据");
				}
				
				
			}else{
				object.put("success","false");
				object.put("msg","登录超时，请重新登录");
			}
		}catch(Exception e){
			e.printStackTrace();
			object.put("success","false");
			object.put("msg","异常");
		}	
		ResponseUtils.renderJson(response, desCrypt.encode(object.toString()));
//		ResponseUtils.renderJson(response, object.toString());
		return null;
	}
	
	
	@RequestMapping(value="/getMenuById")
	@ResponseBody
	public String getMenuById(HttpServletResponse response,HttpServletRequest request) throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject object = new JSONObject();
		JSONObject object_data = new JSONObject();
        DesCrypt desCrypt=new DesCrypt();
		try{
			String data = request.getParameter("data");
			if(data==null||data.equals("")){
				object.put("success","false");
				object.put("msg","获取信息失败！");
				ResponseUtils.renderJson(response, object.toString());
				return null;
			}

//			JSONObject string_to_json = JSONObject.fromObject(data);
			String desData= desCrypt.decode(data.replaceAll(" ","+"));
			JSONObject string_to_json = JSONObject.fromObject(desData);
			JSONObject json_to_data = string_to_json.getJSONObject("data");
			String tokenid =json_to_data.getString("tokenid"); 
			
			PageData pd_stoken=new PageData();
			
			pd_stoken.put("TOKENID", tokenid);
			pd_stoken.put("MENU_ID", json_to_data.getString("menu_id"));
			PageData pd_token=zxlbService.findByTokenId(pd_stoken);
			
			if(pd_token!=null){
				String menu_ids=pd_token.getString("menu_ids")==null?"":pd_token.getString("menu_ids");
				String[] arr=menu_ids.split(",");
				if(!menu_ids.equals("")&&json_to_data.getString("menu_id")!=null&&!json_to_data.getString("menu_id").equals("")){
					
					if(Arrays.asList(arr).contains(json_to_data.getString("menu_id"))){
						PageData pd_menu= menuService.getMenufrontById(pd_stoken);
						JSONObject object_menu = new JSONObject();
							
						object_menu.put("menu_id", pd_menu.getString("MENU_ID"));
						object_menu.put("menu_name", pd_menu.getString("MENU_NAME"));
						object_menu.put("menu_url", pd_menu.getString("MENU_URL"));
						object_menu.put("menu_icon", pd_menu.getString("MENU_ICON"));
						object_menu.put("parent_id", pd_menu.getString("PARENT_ID"));
						object_menu.put("menu_order", pd_menu.getString("MENU_ORDER"));
						//object_menu.put("menu_state", pd_menu.getString("MENU_STATE"));
						object_menu.put("menu_type", pd_menu.getString("MENU_TYPE"));
					
						object.put("menu",object_menu.toString());
						object.put("success","true");
					}else{
						object.put("msg","该用户无改菜单权限");
						object.put("success","false");
					}
				}else{
					object.put("msg","该用户无改菜单权限");
					object.put("success","false");
				}
				
				
			}else{
				object.put("success","false");
				object.put("msg","登录超时，请重新登录");
			}
		}catch(Exception e){
			e.printStackTrace();
			object.put("success","false");
			object.put("msg","异常");
		}	
		ResponseUtils.renderJson(response, desCrypt.encode(object.toString()));
//		ResponseUtils.renderJson(response, object.toString());
		return null;
	}
	
	/**添加操作
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping(value="/addOperation")
	@ResponseBody
	
	public String addOperation(HttpServletResponse response,HttpServletRequest request) throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject object = new JSONObject();
		JSONObject object_data = new JSONObject();
        DesCrypt desCrypt=new DesCrypt();
		try{
			String data = request.getParameter("data");
			if(data==null||data.equals("")){
				object.put("success","false");
				object.put("msg","获取信息失败！");
				ResponseUtils.renderJson(response, object.toString());
				return null;
			}

//			JSONObject string_to_json = JSONObject.fromObject(data);
			String desData= desCrypt.decode(data.replaceAll(" ","+"));
			JSONObject string_to_json = JSONObject.fromObject(desData);
			JSONObject json_to_data = string_to_json.getJSONObject("data");
			String tokenid =json_to_data.getString("tokenid"); 
			PageData pd_stoken=new PageData();
			
			pd_stoken.put("TOKENID", tokenid);
			PageData pd_token=zxlbService.findByTokenId(pd_stoken);
			
			if(pd_token!=null){
				String ip=request.getRemoteAddr();
				
				String field =json_to_data.getString("field")==null?"":json_to_data.getString("field"); 
				String tbname =json_to_data.getString("tbname")==null?"":json_to_data.getString("tbname"); 
				String fieldvalue =json_to_data.getString("fieldvalue")==null?"":json_to_data.getString("fieldvalue"); 
				HashMap<String, String> data_s = new HashMap<String, String>();
				PageData pd_s=new PageData();
				String[] field_arr=field.split("@s");
				String[] fieldvalue_arr=fieldvalue.split("@s");
				//System.out.println(field+"field_arr"+field_arr.length);
				String fieldstr="",valuestr="";
				
				for(int i=0;i<field_arr.length;i++){
					//System.out.println(field_arr[i]+"field_arr[i]");
					if(fieldstr!=""){
						fieldstr=fieldstr+",";
					}
					fieldstr=fieldstr+field_arr[i];
				}
				for(int i=0;i<fieldvalue_arr.length;i++){
					if(valuestr!=""){
						valuestr=valuestr+",";
					}
					if(fieldvalue_arr[i].equals("nonevalue")){
						valuestr=valuestr+"''";
					}else{
						valuestr=valuestr+"'"+fieldvalue_arr[i]+"'";
					}
					
				}
				pd_s.put("tbname", tbname);
				pd_s.put("fieldstr", fieldstr);
				pd_s.put("valuestr", valuestr);
				pd_s.put("loginip", ip);
				pd_s.put("czman", pd_token.getString("ZXYH"));
				appuserService.addOperation(pd_s);
				
				object.put("success","true");
			}else{
				object.put("success","false");
				object.put("msg","登录超时，请重新登录");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			object.put("success","false");
			object.put("msg","异常");
		}	
//		ResponseUtils.renderJson(response, object.toString());
		ResponseUtils.renderJson(response, desCrypt.encode(object.toString()));
		return null;
	}
	
	/**修改操作
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping(value="/modOperation")
	@ResponseBody
	public String modOperation(HttpServletResponse response,HttpServletRequest request) throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject object = new JSONObject();
		JSONObject object_data = new JSONObject();
        DesCrypt desCrypt=new DesCrypt();
		try{
			String data = request.getParameter("data");
			if(data==null||data.equals("")){
				object.put("success","false");
				object.put("msg","获取信息失败！");
				ResponseUtils.renderJson(response, object.toString());
				return null;
			}

//			JSONObject string_to_json = JSONObject.fromObject(data);
			String desData= desCrypt.decode(data.replaceAll(" ","+"));
			JSONObject string_to_json = JSONObject.fromObject(desData);
			JSONObject json_to_data = string_to_json.getJSONObject("data");
			String tokenid =json_to_data.getString("tokenid"); 
			PageData pd_stoken=new PageData();
			
			pd_stoken.put("TOKENID", tokenid);
			PageData pd_token=zxlbService.findByTokenId(pd_stoken);
			
			if(pd_token!=null){
				String ip=request.getRemoteAddr();
				
				String field =json_to_data.getString("field")==null?"":json_to_data.getString("field"); 
				String id =json_to_data.getString("id")==null?"":json_to_data.getString("id"); 
				String tbname =json_to_data.getString("tbname")==null?"":json_to_data.getString("tbname"); 
				String fieldvalue =json_to_data.getString("fieldvalue")==null?"":json_to_data.getString("fieldvalue"); 
				HashMap<String, String> data_s = new HashMap<String, String>();
				PageData pd_s=new PageData();
				String[] field_arr=field.split("@s");
				String[] fieldvalue_arr=fieldvalue.split("@s");
				//System.out.println(field+"field_arr"+field_arr.length);
				String fieldstr="",valuestr="";
				
				for(int i=0;i<field_arr.length;i++){
					//System.out.println(field_arr[i]+"field_arr[i]");
					if(fieldstr!=""){
						fieldstr=fieldstr+",";
					}
					if(fieldvalue_arr[i].equals("nonevalue")){
						fieldstr=fieldstr+field_arr[i]+"="+"''";	
					}else{
						fieldstr=fieldstr+field_arr[i]+"="+"'"+fieldvalue_arr[i]+"'";
					}
					
				}
				
				pd_s.put("tbname", tbname);
				pd_s.put("fieldstr", fieldstr);
				pd_s.put("valuestr", valuestr);
				pd_s.put("loginip", ip);
				pd_s.put("id", id);
				pd_s.put("czman", pd_token.getString("ZXYH"));
				
				PageData pd_search=appuserService.findOperationByid(pd_s);
				if(pd_search!=null){
					appuserService.modOperation(pd_s);	
					object.put("success","true");
				}else{
					object.put("success","false");
					object.put("msg","修改失败，信息不存在");
				}
				
				
			}else{
				object.put("success","false");
				object.put("msg","登录超时，请重新登录");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			object.put("success","false");
			object.put("msg","异常");
		}	
//		ResponseUtils.renderJson(response, object.toString());
		ResponseUtils.renderJson(response, desCrypt.encode(object.toString()));
		return null;
	}
	
	/**删除操作
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping(value="/delOperation")
	@ResponseBody
	public String delOperation(HttpServletResponse response,HttpServletRequest request) throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject object = new JSONObject();
		JSONObject object_data = new JSONObject();
        DesCrypt desCrypt=new DesCrypt();
		try{
			String data = request.getParameter("data");
			if(data==null||data.equals("")){
				object.put("success","false");
				object.put("msg","获取信息失败！");
				ResponseUtils.renderJson(response, object.toString());
				return null;
			}

//			JSONObject string_to_json = JSONObject.fromObject(data);
			String desData= desCrypt.decode(data.replaceAll(" ","+"));
			JSONObject string_to_json = JSONObject.fromObject(desData);
			JSONObject json_to_data = string_to_json.getJSONObject("data");
			String tokenid =json_to_data.getString("tokenid"); 
			
			PageData pd_stoken=new PageData();
			
			pd_stoken.put("TOKENID", tokenid);
			PageData pd_token=zxlbService.findByTokenId(pd_stoken);
			
			if(pd_token!=null){
				String ip=request.getRemoteAddr();
				
				//String field =json_to_data.getString("field")==null?"":json_to_data.getString("field"); 
				String id =json_to_data.getString("id")==null?"":json_to_data.getString("id"); 
				String tbname =json_to_data.getString("tbname")==null?"":json_to_data.getString("tbname"); 
				//String fieldvalue =json_to_data.getString("fieldvalue")==null?"":json_to_data.getString("fieldvalue"); 
				HashMap<String, String> data_s = new HashMap<String, String>();
				PageData pd_s=new PageData();
				//System.out.println(field+"field_arr"+field_arr.length);
				
				pd_s.put("tbname", tbname);
				pd_s.put("id", id);
				pd_s.put("fieldstr", "id");
				pd_s.put("czman", pd_token.getString("ZXYH"));
				
				PageData pd_search=appuserService.findOperationByid(pd_s);
				if(pd_search!=null){
					appuserService.delOperation(pd_s);
					object.put("success","true");
				}else{
					object.put("success","false");
					object.put("msg","修改失败，信息不存在");
				}
				object.put("success","true");
			}else{
				object.put("success","false");
				object.put("msg","登录超时，请重新登录");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			object.put("success","false");
			object.put("msg","异常");
		}	
//		ResponseUtils.renderJson(response, object.toString());
		ResponseUtils.renderJson(response, desCrypt.encode(object.toString()));
		return null;
	}
	
	/**通过id 获取信息
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping(value="/findOperationByid")
	@ResponseBody
	public String findOperationByid(HttpServletResponse response,HttpServletRequest request) throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject object = new JSONObject();
		JSONObject object_data = new JSONObject();
        DesCrypt desCrypt=new DesCrypt();
		try{
			String data = request.getParameter("data");
			if(data==null||data.equals("")){
				object.put("success","false");
				object.put("msg","获取信息失败！");
				ResponseUtils.renderJson(response, object.toString());
				return null;
			}

//			JSONObject string_to_json = JSONObject.fromObject(data);
			String desData= desCrypt.decode(data.replaceAll(" ","+"));
			JSONObject string_to_json = JSONObject.fromObject(desData);
			JSONObject json_to_data = string_to_json.getJSONObject("data");
			String tokenid =json_to_data.getString("tokenid"); 
			
			PageData pd_stoken=new PageData();
			
			pd_stoken.put("TOKENID", tokenid);
			PageData pd_token=zxlbService.findByTokenId(pd_stoken);
			
			if(pd_token!=null){
				String ip=request.getRemoteAddr();
				
				String field =json_to_data.getString("field")==null?"":json_to_data.getString("field"); 
				String id =json_to_data.getString("id")==null?"":json_to_data.getString("id"); 
				String tbname =json_to_data.getString("tbname")==null?"":json_to_data.getString("tbname"); 
				HashMap<String, String> data_s = new HashMap<String, String>();
				PageData pd_s=new PageData();
				String[] field_arr=field.split("@s");
				String fieldstr="",valuestr="";
				
				for(int i=0;i<field_arr.length;i++){
					if(fieldstr!=""){
						fieldstr=fieldstr+",";
					}
					fieldstr=fieldstr+"cast("+field_arr[i]+" as char) as "+field_arr[i];
				}
				pd_s.put("tbname", tbname);
				pd_s.put("fieldstr", fieldstr);
				pd_s.put("id", id);
				pd_s.put("czman", pd_token.getString("ZXYH"));
				PageData pd_search=appuserService.findOperationByid(pd_s);
				if(pd_search!=null){
					for(int i=0;i<field_arr.length;i++){
						object_data.put(field_arr[i], pd_search.get(field_arr[i]));
					}
					object.put("data",object_data.toString());
					object.put("success","true");
				}else{
					object.put("success","false");
					object.put("msg","信息不存在");
				}
				
			}else{
				object.put("success","false");
				object.put("msg","登录超时，请重新登录");
			}
		}catch(Exception e){
			e.printStackTrace();
			object.put("success","false");
			object.put("msg","异常");
		}	
//		ResponseUtils.renderJson(response, object.toString());
		ResponseUtils.renderJson(response, desCrypt.encode(object.toString()));
		return null;
	}
	
	/**获取信息列表
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping(value="/listOperation")
	@ResponseBody
	public String listOperation(HttpServletResponse response,HttpServletRequest request) throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject object = new JSONObject();
		JSONObject object_data = new JSONObject();
        DesCrypt desCrypt=new DesCrypt();
		try{
			String data = request.getParameter("data");
			if(data==null||data.equals("")){
				object.put("success","false");
				object.put("msg","获取信息失败！");
				ResponseUtils.renderJson(response, object.toString());
				return null;
			}

//			JSONObject string_to_json = JSONObject.fromObject(data);
			String desData= desCrypt.decode(data.replaceAll(" ","+"));
			JSONObject string_to_json = JSONObject.fromObject(desData);
			JSONObject json_to_data = string_to_json.getJSONObject("data");
			String tokenid =json_to_data.getString("tokenid"); 
			
			PageData pd_stoken=new PageData();
			
			pd_stoken.put("TOKENID", tokenid);
			PageData pd_token=zxlbService.findByTokenId(pd_stoken);
			
			if(pd_token!=null){
				String ip=request.getRemoteAddr();
				
				String field =json_to_data.getString("field")==null?"":json_to_data.getString("field"); 
				String searchfield ="";
				if(json_to_data.has("searchfield")){
					searchfield =json_to_data.getString("searchfield")==null?"":json_to_data.getString("searchfield"); 
				}
				String searchfieldvalue ="";
				if(json_to_data.has("searchfieldvalue")){
					 searchfieldvalue =json_to_data.getString("searchfieldvalue")==null?"":json_to_data.getString("searchfieldvalue"); 
				}
				String pageIndex =json_to_data.getString("pageIndex")==null?"1":json_to_data.getString("pageIndex"); 
				String pageSize =json_to_data.getString("pageSize")==null?"10":json_to_data.getString("pageSize"); 
				String tbname =json_to_data.getString("tbname")==null?"":json_to_data.getString("tbname"); 
				HashMap<String, String> data_s = new HashMap<String, String>();
				PageData pd_s=new PageData();
				String[] field_arr=field.split("@s");
				String[] searchfield_arr=searchfield.split("@s");
				String[] searchfieldvalue_arr=searchfieldvalue.split("@s");
				
				String fieldstr="",searchstr="";
				
				for(int i=0;i<field_arr.length;i++){
					if(fieldstr!=""){
						fieldstr=fieldstr+",";
					}
					fieldstr=fieldstr+"cast("+field_arr[i]+" as char) as "+field_arr[i];
				}
				
				if(searchfieldvalue!=null&&!searchfieldvalue.equals("")){
					for(int i=0;i<searchfield_arr.length;i++){
						if(searchfield_arr[i]!=null&&searchfield_arr[i]!=""){
							searchstr=searchstr+" and ";
							searchstr=searchstr+searchfield_arr[i]+" like "+"'%"+searchfieldvalue_arr[i]+"%'";	
						}	
					}
				}
				
				
				pd_s.put("tbname", tbname);
				pd_s.put("fieldstr", fieldstr);
				pd_s.put("czman", pd_token.getString("ZXYH"));
				pd_s.put("searchstr", searchstr);
				Page page=new Page();
				page.setPd(pd_s);
				page.setCurrentPage(Integer.parseInt(pageIndex));
				page.setShowCount(Integer.parseInt(pageSize));
				List<JSONObject> object_List = new ArrayList();
				List<PageData>	pageList = appuserService.listOperation(page);
				for(PageData pd_search:pageList){
					object_data=new JSONObject();
					for(int i=0;i<field_arr.length;i++){
						object_data.put(field_arr[i], pd_search.getString(field_arr[i]));
					}
					object_List.add(object_data);
				}
				
				object.put("data",object_List.toString());
				object.put("pageId",page.getCurrentPage());
				object.put("pageCount",page.getTotalPage());
				//System.out.println(page.getTotalResult()+"total");
				object.put("recordCount",page.getTotalResult());
				object.put("success","true");
			}else{
				object.put("success","false");
				object.put("msg","登录超时，请重新登录");
			}
		}catch(Exception e){
			e.printStackTrace();
			object.put("success","false");
			object.put("msg","异常");
		}	
		ResponseUtils.renderJson(response, desCrypt.encode(object.toString()));
//		ResponseUtils.renderJson(response, object.toString());
		return null;
	}
	
	/**根据存储过程名称获取数据
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping(value="/proPage")
	@ResponseBody
	public String proPage(HttpServletResponse response,HttpServletRequest request) throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject object = new JSONObject();
		JSONObject object_data = new JSONObject();
        DesCrypt desCrypt=new DesCrypt();
		try{
			String data = request.getParameter("data");
			if(data==null||data.equals("")){
				object.put("success","false");
				object.put("msg","获取信息失败！");
				ResponseUtils.renderJson(response, object.toString());
				return null;
			}

//			JSONObject string_to_json = JSONObject.fromObject(data);
			String desData= desCrypt.decode(data.replaceAll(" ","+"));
			JSONObject string_to_json = JSONObject.fromObject(desData);
			JSONObject json_to_data = string_to_json.getJSONObject("data");
			String tokenid =json_to_data.getString("tokenid"); 
			
			PageData pd_stoken=new PageData();
			
			pd_stoken.put("TOKENID", tokenid);
			PageData pd_token=zxlbService.findByTokenId(pd_stoken);
			
			if(pd_token!=null){
				String ip=request.getRemoteAddr();
				
				String field =json_to_data.getString("field")==null?"":json_to_data.getString("field"); 
				String searchfield ="";
				if(json_to_data.has("searchfield")){
					searchfield =json_to_data.getString("searchfield")==null?"":json_to_data.getString("searchfield"); 
				}
				String searchfieldvalue ="";
				if(json_to_data.has("searchfieldvalue")){
					 searchfieldvalue =json_to_data.getString("searchfieldvalue")==null?"":json_to_data.getString("searchfieldvalue"); 
				}
				
				
				String pageIndex =json_to_data.getString("pageIndex")==null?"1":json_to_data.getString("pageIndex"); 
				String pageSize =json_to_data.getString("pageSize")==null?"10":json_to_data.getString("pageSize"); 
				String proname =json_to_data.getString("proname")==null?"":json_to_data.getString("proname"); 
				HashMap<String, String> data_s = new HashMap<String, String>();
				PageData pd_s=new PageData();
				String[] field_arr=field.split("@s");
				String[] searchfield_arr=searchfield.split("@s");
				String[] searchfieldvalue_arr=searchfieldvalue.split("@s");
				
				String fieldstr="",searchstr="";
				
				for(int i=0;i<field_arr.length;i++){
					if(fieldstr!=""){
						fieldstr=fieldstr+",";
					}
					fieldstr=fieldstr+"cast("+field_arr[i]+" as char) as "+field_arr[i];
				}
				
				if(searchfieldvalue!=null&&!searchfieldvalue.equals("")){
					for(int i=0;i<searchfield_arr.length;i++){
						if(searchfield_arr[i]!=null&&searchfield_arr[i]!=""){
							searchstr=searchstr+" and ";
							searchstr=searchstr+searchfield_arr[i]+" like "+"'%"+searchfieldvalue_arr[i]+"%'";	
						}
					}
				}
				pd_s.put("proname", proname);
				pd_s.put("_fields", fieldstr);
				pd_s.put("czman", pd_token.getString("ZXYH"));
				pd_s.put("_where", searchfield.replace("@s", ","));
				pd_s.put("_wherevalue", searchfieldvalue.replace("@s", ","));
				Page page=new Page();
				page.setPd(pd_s);
				page.setCurrentPage(Integer.parseInt(pageIndex));
				page.setShowCount(Integer.parseInt(pageSize));
				
				pd_s.put("_pageindex", pageIndex);
				pd_s.put("_pageSize", pageSize);
				List<JSONObject> object_List = new ArrayList();
				List<PageData>	pageList = appuserService.proPage(pd_s);
				for(PageData pd_search:pageList){
					object_data=new JSONObject();
					for(int i=0;i<field_arr.length;i++){
						object_data.put(field_arr[i], pd_search.getString(field_arr[i]));
					}
					object_List.add(object_data);
				}
				
				object.put("data",object_List.toString());
				object.put("pageId",pageIndex);
				object.put("pageCount",pd_s.get("_pagecount"));
				//System.out.println(pd_s.getString("_strsql")+"_strsql");
				object.put("recordCount",pd_s.get("_totalcount"));
				object.put("success","true");
			}else{
				object.put("success","false");
				object.put("msg","登录超时，请重新登录");
			}
		}catch(Exception e){
			e.printStackTrace();
			object.put("success","false");
			object.put("msg","异常");
		}	
//		ResponseUtils.renderJson(response, object.toString());
		ResponseUtils.renderJson(response, desCrypt.encode(object.toString()));
		return null;
	}
	
	
	@RequestMapping(value="/logout")
	@ResponseBody
	public String logout(HttpServletResponse response,HttpServletRequest request) throws Exception {
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject object = new JSONObject();
		JSONObject object_data = new JSONObject();
		String data = request.getParameter("data");
		if(data==null||data.equals("")){
			object.put("success","false");
			object.put("msg","获取信息失败！");
			ResponseUtils.renderJson(response, object.toString());
			return null;
		}
		DesCrypt desCrypt=new DesCrypt();

//		JSONObject string_to_json = JSONObject.fromObject(data);
		String desData= desCrypt.decode(data.replaceAll(" ","+"));
		JSONObject string_to_json = JSONObject.fromObject(desData);
		JSONObject json_to_data = string_to_json.getJSONObject("data");
		String tokenid =json_to_data.getString("tokenid"); 
		
		if(tokenid!=null&&!tokenid.equals("")){
			PageData pd_s=new PageData();
			pd_s.put("TOKENID", tokenid);
			PageData pd = zxlbService.findByTokenId(pd_s);
			if(pd!=null){
				String newtokenid=IDUtils.createUUID();
				tokenid=IDUtils.createUUID();
				pd_s.put("TOKENID", tokenid);
				zxlbService.editTokenid(pd_s);
				object.put("success","true");
			}else{
				object.put("success","true");
			}
		}else{
			object.put("success","false");
			object.put("msg","未获取信息！");
		}
//		ResponseUtils.renderJson(response, object.toString());
		ResponseUtils.renderJson(response, desCrypt.encode(object.toString()));
		return null;
	}
	
	/**获取字典信息
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping(value="/getDict")
	@ResponseBody
	public String getDict(HttpServletResponse response,HttpServletRequest request) throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject object = new JSONObject();
		JSONObject object_data = new JSONObject();
        DesCrypt desCrypt=new DesCrypt();
		try{
			String data = request.getParameter("data");
			if(data==null||data.equals("")){
				object.put("success","false");
				object.put("msg","获取信息失败！");
				ResponseUtils.renderJson(response, object.toString());
				return null;
			}

//			JSONObject string_to_json = JSONObject.fromObject(data);
			String desData= desCrypt.decode(data.replaceAll(" ","+"));
			JSONObject string_to_json = JSONObject.fromObject(desData);
			JSONObject json_to_data = string_to_json.getJSONObject("data");
			String tokenid =json_to_data.getString("tokenid"); 
			
			PageData pd_stoken=new PageData();
			
			pd_stoken.put("TOKENID", tokenid);
			PageData pd_token=zxlbService.findByTokenId(pd_stoken);
			
			if(pd_token!=null){
				String ip=request.getRemoteAddr();
				
				String bianma =json_to_data.getString("bianma")==null?"":json_to_data.getString("bianma"); 
				
				String children=""; 
				if(json_to_data.has("children")){
					children=json_to_data.getString("children")==null?"":json_to_data.getString("children");
				}
				String searchfield ="";
				pd_stoken.put("bianma", bianma);
				
				List<PageData>	pageList = dictionariesService.listAllDictByBM(pd_stoken);
				List<JSONObject> pdList = new ArrayList<JSONObject>();
				
				List<Map<String,Object>> dataList = new ArrayList<>();
		        Map<String,Object> map = new HashMap<>();
		        
				for(PageData d :pageList){
					JSONObject pdf = new JSONObject();
					pdf.put("DICTIONARIES_ID",d.getString("DICTIONARIES_ID"));
					pdf.put("BIANMA", d.getString("BIANMA"));
					pdf.put("NAME", d.getString("NAME"));
					pdf.put("PARENT_ID", d.getString("PARENT_ID"));
					pdList.add(pdf);
				}
				com.alibaba.fastjson.JSONArray result ;
				if(children.equals("1")){
					for(PageData pd_fl:pageList){
			        	map = new HashMap<>();
			        	map.put("id",pd_fl.get("DICTIONARIES_ID"));
			        	map.put("pid",pd_fl.get("PARENT_ID"));
			        	map.put("name",pd_fl.get("NAME"));
			        	map.put("bianma",pd_fl.get("BIANMA"));
			        	dataList.add(map);
			        }
			        //System.out.println(JSON.toJSONString(data));
			        result = BuildTree.listToTree(com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(dataList)),"id","pid","children");
			        object.put("data",result);
					object.put("success","true");
				}else{
					object.put("data",pdList.toString());
					object.put("success","true");
				}
				
		       
			}else{
				object.put("success","false");
				object.put("msg","登录超时，请重新登录");
			}
		}catch(Exception e){
			e.printStackTrace();
			object.put("success","false");
			object.put("msg","异常");
		}	
//		ResponseUtils.renderJson(response, object.toString());
		ResponseUtils.renderJson(response, desCrypt.encode(object.toString()));
		return null;
	}
	
	

	/**获取字典信息
	 * @return 
	 * @throws Exception 
	 */
	@RequestMapping(value="/getArea",produces = "text/html;charset=utf-8")
	@ResponseBody
	public String getArea(HttpServletResponse response,HttpServletRequest request) throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		JSONObject object = new JSONObject();
		JSONObject object_data = new JSONObject();
        DesCrypt desCrypt=new DesCrypt();
		try{
			String data = request.getParameter("data");
			if(data==null||data.equals("")){
				object.put("success","false");
				object.put("msg","获取信息失败！");
				ResponseUtils.renderJson(response, object.toString());
				return null;
			}

//			JSONObject json_to_data = JSONObject.fromObject(data);
			String desData= desCrypt.decode(data.replaceAll(" ","+"));
			JSONObject string_to_json = JSONObject.fromObject(desData);
			JSONObject json_to_data = string_to_json.getJSONObject("data");
			String tokenid =json_to_data.getString("tokenid"); 
			
			PageData pd_stoken=new PageData();
			
			pd_stoken.put("TOKENID", tokenid);
			PageData pd_token=zxlbService.findByTokenId(pd_stoken);
			
			if(pd_token!=null){
				String ip=request.getRemoteAddr();
				
				JSONArray arr = JSONArray.fromObject(areamanageService.listAll(pd_stoken));
				String json_dept = arr.toString();
				json_dept = json_dept.replaceAll("AREA_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subAreaManage", "nodes").replaceAll("hasAreaManage", "checked").replaceAll("treeurl", "url");
				//System.out.println(json);
				object.put("data",json_dept);
				object.put("success","true");
			}else{
				object.put("success","false");
				object.put("msg","登录超时，请重新登录");
			}
		}catch(Exception e){
			e.printStackTrace();
			object.put("success","false");
			object.put("msg","异常");
		}	
		ResponseUtils.renderJson(response, object.toString());
//		ResponseUtils.renderJson(response, desCrypt.encode(object.toString()));
		return null;
	}
	
	
	/*
	 * 文件压缩下载
	 *
	 */
	@RequestMapping(value="/download")
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")  
	public void download(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//设置导出状态，进度条
		response.addHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("GBK");
		response.setCharacterEncoding("GBK");
		PageData pd = new PageData();
		pd = this.getPageData();
		//pd.put("TOKENID", pd.getString("tokenid"));
		PageData pd_token= new PageData();
		//if(pd_token==null){response.getWriter().print("暂无可以下载数据");}
		FilecopyHtml filecopy=new FilecopyHtml();
		String rootPath = request.getRealPath("/")+"uploadFiles\\";
		String fileroot = 	 request.getRealPath("/");
		//System.out.println(fileroot);
		//设置下载父文件名
		String parentPath = StringUtils.getDateTime("yyyyMMdd");
		File file_r = new File(rootPath+parentPath);
		if(file_r.exists()){
			System.gc();
			filecopy.delFile(file_r);
		}else{
			file_r.createNewFile();
		}
		String fileName="",inputPath="",oldPath="",newPath="",url="";
		String id = request.getParameter("id")==null?"": request.getParameter("id");
		String[] ida=id.split(",");
		pd.put("ids", id);
		List<PageData> pdList =recordService.listByids(pd);
		FileDownloadFromUrl fileDownloadFromUrl=new FileDownloadFromUrl();
		String downloadurl="";
		
		pd_token.put("param_code", "recordsvr");
		PageData pdparam=blacklistService.findSysparam(pd_token);
		boolean boo=false;
		for(PageData pdcx:pdList){
			//System.out.println(pdList+"pdList");
			//获取服务的地址
			downloadurl=pdcx.getString("lywj");
			//从服务器下载
			if(downloadurl!=null&&!downloadurl.equals("")){
				//System.out.println(downloadurl);
				////System.out.println("rootPath+parentPath"+rootPath+parentPath);
				downloadurl=pdparam.getString("param_value")+downloadurl;
				fileDownloadFromUrl.downLoadFromUrl(downloadurl, pdcx.getString("lywj"),rootPath+parentPath);
				//设置下载跟路径
				//System.out.println(bdzimgList);
				fileName = parentPath+".zip";
				inputPath = "\\download\\"+fileName;
				String root=rootPath+parentPath;
				File file = new File(rootPath+parentPath);
				if(!file.exists()){
					file.mkdirs();
				}
				boo=true;
			}
			
		}
		if(boo){
			//文件压缩
			ZipTools.compressFloderChangeToZip(rootPath+parentPath, rootPath, fileName);
			File downfile = new File(rootPath+fileName); // 要下载的文件绝对路径 
			InputStream ins; 

			ins = new BufferedInputStream(new FileInputStream(downfile)); 

			byte[] buffer = new byte[ins.available()]; 
			ins.read(buffer); 
			ins.close(); 

			response.reset(); 
			response.addHeader("Content-Disposition", "attachment;filename="+ new String(downfile.getName().getBytes())); 
			response.addHeader("Content-Length", "" + downfile.length()); 
			OutputStream ous = new BufferedOutputStream(response.getOutputStream()); 
			response.setContentType("application/octet-stream"); 
			ous.write(buffer); 
			ous.flush(); 
			ous.close();
			ins.close();
			
		}else{
			response.getWriter().print("暂无可以下载数据");
		}
		
	}
	
	
	
	@RequestMapping(value="/excelAddresslist")
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true") 
	public String excelAddresslist(HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"导出doc到excel");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		//User user=Jurisdiction.getLoginUser();
		response.addHeader("Access-Control-Allow-Origin", "*");
		Page page = new Page();
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		
		pd.put("TOKENID", pd.getString("tokenid"));
		PageData pd_token=zxlbService.findByTokenId(pd);
		if(pd_token==null){return null;}
		
		
		pd.put("isdel", "0"); 
	
        page.setShowCount(9999999);
        page.setPd(pd);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		
		dataMap.put("titles", titles);
		List<PageData> varOList = addressService.listPage(page);
		List<PageData> varList = new ArrayList<PageData>();
		String ishf="",type="";
		
		try
	    {
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
	    
	  

	      
	      wsheet.addCell(new Label(0, 0, "姓名"));
	      wsheet.addCell(new Label(1, 0, "联系电话1"));
	      wsheet.addCell(new Label(2, 0, "联系电话2"));
	      wsheet.addCell(new Label(3, 0, "部门名称"));
	      wsheet.addCell(new Label(4, 0, "审核状态"));
	      wsheet.addCell(new Label(5, 0, "审核人"));
	      wsheet.addCell(new Label(6, 0, "审核时间"));
	      wsheet.addCell(new Label(7, 0, "审核备注"));
	 
	      int num = 1;
	      
	      for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				
				wsheet.addCell(new Label(0, num, varOList.get(i).getString("name")));	    //1
				wsheet.addCell(new Label(1, num, String.valueOf(varOList.get(i).get("tel1"))));	    //2
				wsheet.addCell(new Label(2, num, String.valueOf(varOList.get(i).getString("tel2"))));	    //3
				String issh=String.valueOf(varOList.get(i).get("issh"));
				if(issh.equals("0")){
					issh="待审核";
				}
				if(issh.equals("1")){
					issh="已审核";
				}
				if(issh.equals("2")){
					issh="审核不通过";
				}
				wsheet.addCell(new Label(3, num, varOList.get(i).getString("departmentname")));	    //5
				wsheet.addCell(new Label(4, num, issh));	    //4
				
				wsheet.addCell(new Label(5, num, varOList.get(i).getString("shmanName")));	    //6
				wsheet.addCell(new Label(6, num, varOList.get(i).getString("shdate")));	    //7
				wsheet.addCell(new Label(7, num, varOList.get(i).getString("shremark")));	    //8
				num++;
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
	    return null;
	}
	
	
	@RequestMapping(value="/excelServerinfo",produces = "text/html;charset=utf-8")
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true") 
	public String excelServerinfo(HttpServletResponse response) throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		Page page = new Page();
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("TOKENID", pd.getString("tokenid"));
		PageData pd_token=zxlbService.findByTokenId(pd);
		if(pd_token==null){return null;}
		
		if(pd.getString("usertype")!=null&&pd.getString("usertype").equals("1")){
        	pd.put("usertype", pd.getString("usertype"));
        	pd.put("createman", pd_token.getString("ID"));
        }
		if(pd.getString("endtime")!=null&& !pd.getString("endtime").equals("")){
			pd.put("endtime",Tools.getEndTime(pd.getString("endtime"), 1));
		}
		if(pd.getString("keywords")!=null){
			pd.put("keywords", 	URLEncoder.encode(URLEncoder.encode(pd.getString("keywords"))));
			
		}
		
		
		page.setPd(pd);
		
        page.setShowCount(9999999);
        page.setPd(pd);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		
		dataMap.put("titles", titles);
		List<PageData> varOList = serverLogService.listPage(page);
		
		try
	    {
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
	    
	  

	      
	      wsheet.addCell(new Label(0, 0, "客户姓名"));
	      wsheet.addCell(new Label(1, 0, "来电号码"));
	      wsheet.addCell(new Label(2, 0, "服务人姓名"));
	      wsheet.addCell(new Label(3, 0, "来电时间"));
	      wsheet.addCell(new Label(4, 0, "客户姓名"));
	      wsheet.addCell(new Label(5, 0, "服务类型名称"));
	      wsheet.addCell(new Label(6, 0, "内容"));
	 
	      int num = 1;
	      
	      for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();
				
				wsheet.addCell(new Label(0, num, varOList.get(i).getString("name")));	    //1
				wsheet.addCell(new Label(1, num, String.valueOf(varOList.get(i).get("phone"))));	    //2
				wsheet.addCell(new Label(2, num, String.valueOf(varOList.get(i).getString("createmanName"))));	    //3
				String receivetime=String.valueOf(varOList.get(i).get("receivetime"));
				wsheet.addCell(new Label(3, num, receivetime));	    //4
				wsheet.addCell(new Label(4, num, varOList.get(i).getString("customName")));	    //5
				
				
				wsheet.addCell(new Label(5, num, varOList.get(i).getString("typename")));	    //6
				wsheet.addCell(new Label(6, num, varOList.get(i).getString("content")));	    //
				num++;
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
	    return null;
	}
	
	
	/**文件上传保存
	 * @param
	 * @throws Exception
	 * 验证参数
	 * IdCardVerification:1,-身份证号码验证
	 * DateVerification:2---日期验证
	 * TelVerification:3--电话号码验证
	 * DictVerification:4--字典验证
	 * ValueVerification:5--固定值验证 如（是,否）
	 * SqlVerification:6 --sql语句验证
	 */
	@RequestMapping(value="/uploadAddresslist",produces = "text/html;charset=utf-8")
	@ResponseBody
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true") 
	public String editFile(@RequestParam(value = "file",required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject object=new JSONObject();
		com.alibaba.fastjson.JSONObject object_verificate=new com.alibaba.fastjson.JSONObject();
		PageData pd = new PageData();
		PageData pd_area = new PageData();
		pd = this.getPageData();
		pd.put("TOKENID", request.getParameter("tokenid"));
		PageData pd_token=zxlbService.findByTokenId(pd);
		if(pd_token==null){
			object.put("success","false");
			object.put("msg","登录超时请重新登录");
			return object.toString();
		}
		
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);
		if (null != file && !file.isEmpty()) {
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String pcbh=sdf.format(d);  //导入批次
			
            String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
            String fileName = FileUpload.fileUp(file, filePath, "addressListexcel");                            //执行上传
           // List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 1, 0, 0);        //执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
            String departmentname="",issh="";
            ImportExcelService importExcelService=new ImportExcelService();
            String[] importColumns={"姓名","联系电话1","联系电话2","部门名称","审核状态"}; //设置字段名称
            String[] importFields={"name","tel1","tel2","departmentid","issh"};  //字段

            String[] importYzColumns={"","3","3","6",""};
            int[] importFiledNums={30,30,30,100,0}; //0表示无限制
            int[] importFiledNull={0,1,0,0,0}; //验证字段是否为空：0表示无限制，1表示不能为空
            String[] importTjColumns={"","","","","isChinese"};  //isNumberic 是否数字;isEnglish 是否英文 ；isChinese 是否中文
            String[] importValueColumns={"","","","select * from t_area where  name = #{name}",""};
            
            object_verificate.put("importColumns", importColumns);
            object_verificate.put("importFields", importFields);
            object_verificate.put("importYzColumns", importYzColumns);
            object_verificate.put("importFiledNums", importFiledNums);
            object_verificate.put("importValueColumns", importValueColumns);
            object_verificate.put("importTjColumns", importTjColumns);
            object_verificate.put("importFiledNull", importFiledNull);
            
            String openFilename=filePath+fileName;
            com.alibaba.fastjson.JSONObject jsonObject=importExcelService.importExcel(openFilename, request, object_verificate);
           
            //System.out.println(listPd);
            
            List<PageData> listPd=((List<PageData>) jsonObject.get("rightList"));
            for (int i = 0; i <listPd.size(); i++) {
            	pd = new PageData();
            	pd_area=new PageData();
            	pd.put("czman",pd_token.getString("ID"));
            	pd.put("name",listPd.get(i).getString("var0"));
		        pd.put("tel1",listPd.get(i).getString("var1"));
		        pd.put("tel2",listPd.get(i).getString("var2")); 
		        departmentname=listPd.get(i).getString("var3");
		        issh=listPd.get(i).getString("var4");
		        if(issh!=null&&issh.equals("已审核")){
		        	issh="1";
		        }else if(issh!=null&&issh.equals("审核不通过")){
		        	issh="2";
		        }else{
		        	issh="0";
		        }
		        pd.put("issh", issh);
		        pd_area.put("name", departmentname);
		        pd_area=areamanageService.findByAreaName(pd_area);
		        if(pd_area!=null&&pd_area.getString("AREA_ID")!=null){
		        	pd.put("departmentid",pd_area.getString("AREA_ID")); 
		        }
		        pd.put("pcbh", pcbh);
		        addressService.save(pd);
            }    
            
          
            
            List<PageData> errorList=((List<PageData>) jsonObject.get("errorList")); //导入错误的信息
            for (int i = 0; i <errorList.size(); i++) {
            	pd = errorList.get(i);
            	pd.put("pcbh", pcbh);
            	pd.put("czman",pd_token.getString("ID"));
            	//System.out.println(errorList.get(i).getString("ycstr")+"ddd");
            	addressService.save_error(pd);
            }
            object.put("success","true");

            if(errorList.size()>0){
                object.put("data", "导入总记录："+(listPd.size()+errorList.size())+"条,导入成功记录："+listPd.size()+"条,失败记录："+errorList.size()+"条,<a href='appuser/excelAddresslist_error.do?pcbh="+pcbh+"&tokenid="+request.getParameter("tokenid")+"' style='color:blue;font-weight:bold'>下载失败记录&nbsp;&nbsp;</a>");
            }else{
                object.put("data", "导入总记录："+(listPd.size()+errorList.size())+"条,导入成功记录："+listPd.size()+"条");
            }
            
		} else{
			object.put("success","false");
			object.put("msg","上传文件不存在");
		}
		//System.out.println(object);
		return object.toString();
	}
	
	/*
	 * 异常信息下载
	 */	
	@RequestMapping(value="/excelAddresslist_error")
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true") 
	public String excelAddresslist_error(HttpServletResponse response) throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		Page page = new Page();
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		JSONObject object=new JSONObject();
		pd = this.getPageData();
		
		pd.put("TOKENID", pd.getString("tokenid"));
		PageData pd_token=zxlbService.findByTokenId(pd);
		if(pd_token==null){
			object.put("success","false");
			object.put("msg","登录超时请重新登录");
			return object.toString();
		}
		
		pd.put("pcbh", pd.getString("pcbh")); 
		pd.put("tables","t_addresslist_error"); 
        page.setShowCount(9999999);
        page.setPd(pd);
		
	
		List<PageData> varOList = addressService.listPage(page);
	
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
			wcfFC.setBackground(Colour.YELLOW);
			wsheet.addCell(new Label(0, 0, "姓名"));
			wsheet.addCell(new Label(1, 0, "联系电话1"));
			wsheet.addCell(new Label(2, 0, "联系电话2"));
			wsheet.addCell(new Label(3, 0, "部门名称"));
			wsheet.addCell(new Label(4, 0, "审核状态"));
			wsheet.addCell(new Label(5, 0, "异常日志"));
			int num = 1;
			String ycstr="";
			for(int i=0;i<varOList.size();i++){
				
				ycstr= String.valueOf(varOList.get(i).getString("ycstr"));
				if(ycstr.indexOf("name")>=0){
					wsheet.addCell(new Label(0, num, varOList.get(i).getString("name"),wcfFC));
				}else{
					wsheet.addCell(new Label(0, num, varOList.get(i).getString("name")));
				}
				if(ycstr.indexOf("tel1")>=0){
					wsheet.addCell(new Label(1, num, varOList.get(i).getString("tel1"),wcfFC));
				}else{
					wsheet.addCell(new Label(1, num, varOList.get(i).getString("tel1")));
				}
				if(ycstr.indexOf("tel2")>=0){
					wsheet.addCell(new Label(2, num, varOList.get(i).getString("tel2"),wcfFC));
				}else{
					wsheet.addCell(new Label(2, num, varOList.get(i).getString("tel2")));
				}
	    	 
				if(ycstr.indexOf("departmentid")>=0){
					wsheet.addCell(new Label(3, num, varOList.get(i).getString("departmentid"),wcfFC));
				}else{
					wsheet.addCell(new Label(3, num, varOList.get(i).getString("departmentname")));
				}
				if(ycstr.indexOf("issh")>=0){
					wsheet.addCell(new Label(4, num, varOList.get(i).getString("issh"),wcfFC));
				}else{
					wsheet.addCell(new Label(4, num, varOList.get(i).getString("issh")));
				}
				wsheet.addCell(new Label(5, num, varOList.get(i).getString("ycstrs")));
				num++;
			}
			wbook.write();
			if(wbook!=null){ wbook.close(); wbook=null;}
			if(os!=null){ os.close(); os=null;}
			return null;
	    }
	    catch (Exception ex)
	    {
	    	ex.printStackTrace();
	    	System.out.println(ex);
	    }
	    return null;
	}
	
	
	

	/**工单保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/workorderSave" ,produces = "text/html;charset=utf-8")
	@ResponseBody
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true") 
	public String workorderSave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		PageData pd = new PageData();
		String reqdata=request.getParameter("data");
		com.alibaba.fastjson.JSONObject data =  com.alibaba.fastjson.JSONObject.parseObject(reqdata);
        JSONObject object = new JSONObject();

		String cmd = data.getString("cmd")==null?"":data.getString("cmd");
		PageData pd_stoken=new PageData();
		com.alibaba.fastjson.JSONObject json = data.getJSONObject("data");
		pd_stoken.put("TOKENID", json.get("tokenid"));
		PageData pd_token=zxlbService.findByTokenId(pd_stoken);
		String username=(json.getString("username")==null?"":json.getString("username"));
		if(!username.equals("")){
			pd_stoken.put("USERNAME", username);
			pd_token=userService.findByUsername(pd_stoken);
			if(pd_token!=null){
				pd_token.put("dept", pd_token.getString("DWBM"));
				pd_token.put("ZXYH", username);
			}
		}
		
		if(pd_token==null){
			object.put("success","false");
			object.put("msg","登录超时请重新登录");
			return object.toString();
		}
		pd.put("workid", this.get32UUID());
		pd.put("id",json.getString("id"));
		
		pd.put("tsdate",json.getString("tsdate"));
		pd.put("tssource",json.getString("tssource"));
		pd.put("tsman",json.getString("tsman"));
		pd.put("tstel",json.getString("tstel"));
		pd.put("tscont",json.getString("tscont"));
		pd.put("tslevel",json.getString("tslevel"));
		pd.put("tsdept",json.getString("tsdept"));
		pd.put("tstype",json.getString("tstype"));
		pd.put("cfbm",json.getString("cfbm"));
	
		pd.put("tsclassify",json.getString("tsclassify"));
		pd.put("ishf",json.getString("ishf"));
		pd.put("endreason",json.getString("endreason"));
		pd.put("type",json.getString("type"));
		pd.put("cljd",json.getString("cljd"));
		pd.put("cardid",json.getString("cardid"));
		pd.put("cjdate",json.getString("cjdate"));
		pd.put("hbh",json.getString("hbh"));
		pd.put("ucid",json.getString("ucid"));
		pd.put("clsx",json.getString("clsx"));
		pd.put("uid", json.getString("uid"));	//随机生成
		pd.put("iszx", json.getString("iszx"));	//是否坐席
		String iszx=json.getString("iszx")==null?"":json.getString("iszx");
		pd.put("type", "0");
		pd.put("czman",pd_token.getString("ID"));
		PageData pd_workorder =null;
		if(pd!=null&&json.getString("id")!=null&&!json.getString("id").equals("")){
			pd.put("id", json.getString("id"));
			pd_workorder = workorderService.findById(pd);
			if(json.getString("uid")==null||json.getString("uid").equals("")){
				//uid=;
				pd.put("uid", pd_workorder.getString("uid"));
			}
		}
		pd.put("code",json.getString("code"));
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String year = formatter.format(currentTime);
		PageData pd_year = new PageData();
		pd_year.put("year", year);
		pd_year = getNum(year);
		String code_num = String.valueOf(pd_year.get("code_num"));
		int length = code_num.length();
		String file_code = "";
		if(length == 1){
			file_code = "000" + code_num + "";
		}else if(length == 2){
			file_code = "00" + code_num + "";
		}else if(length == 3){
			file_code = "0" + code_num + "";
		}else{
			file_code = "" + code_num + "";
		}
		
	
		pd.put("czman",  pd_token.getString("ZXYH"));
		
		String pid ="";
		if(pd_workorder!=null){
			workorderService.edit(pd);
		}else{
			pd.put("code", year+file_code);
			workorderService.save(pd);	
			if("1".equals(code_num)){
				workorderService.saveCode(pd_year);
			}else{
				pd_year.put("code_num", Integer.parseInt(code_num));
				workorderService.editCode(pd_year);
			}
			if(!pid.equals("")){
				workorderService.saveWorkProc(pd);
			}
		}
		
		
		if(json.getString("doaction")!=null&&json.getString("doaction").equals("0")){
			pd.put("dealman",  pd_token.getString("ZXYH"));
			pd.put("cljd",  pd_token.getString("dept"));
			pd.put("type", "4");
			workorderService.editCL(pd);
		}else{
			if(json.getString("doaction")!=null&&json.getString("doaction").equals("1")){
				//发起流程
				String tsdept=json.getString("tsdept")==null?"":json.getString("tsdept");
				String[] arr=tsdept.split(",");
				if(iszx.equals("1")&&arr.length!=1){ //坐席派发,多部门或者未知
					workorderService.zxPf(pd);
				}else{
					if(pd_workorder==null||(pd_workorder!=null&&pd_workorder.getString("pro_id")==null)){
						Map<String,Object> map = new LinkedHashMap<String, Object>();
						map.put("发起流程人",  pd_token.getString("ZXYH"));			//当前用户的姓名
						map.put("投诉人",json.getString("tsman"));
						map.put("投诉人身份证号码", pd.getString("cardid"));
						map.put("投诉内容", pd.getString("tscont"));
						map.put("创建时间", Tools.date2Str(new Date()));
						//
						PageData pd_doc = new PageData();
						String sendcont="";
						if(pd!=null){
							sendcont="您有一个投诉工单需要处理：投诉内容："+pd.getString("tscont");
						}
						pd_doc.put("ROLE_NAME", "工单专员");
						List<PageData> roleList=userService.getUserByRoleName(pd_doc);
						String userstr="";
						RuTaskController ruTaskController=new RuTaskController();
						for(PageData pd_gd:roleList){
							if(userstr!=""){
								userstr=userstr+",";
							}
							userstr=userstr+pd_gd.getString("USERNAME");
						
							if(pd_gd.getString("USERNAME")!=null&&!pd_gd.getString("USERNAME").equals("")){
								ruTaskController.sendMsg(pd_gd.getString("USERNAME"), sendcont);
							}
						}
						map.put("USERNAME", userstr);
						String key="gdsp";
						
						pid = startProcessInstanceByKeyHasVariables(key,map);	//启动流程实例通过KEY
						//pd.put("proc_id", pid);
						if(iszx.equals("1")){
							object.put("proc_id",pid);
						}
						//System.out.println(pd_token.getString("dept")+"dept");		
						if( pd_token.getString("dept")!=null&&pd_token.getString("dept").equals("350101")){
							object.put("proc_id",pid);
						}
						pd.put("proc_id", pid);
						if(pd_workorder==null){
							pd.put("workid", pd.getString("workid"));
						}else{
							pd.put("workid", pd_workorder.getString("workid"));
						}
						pd.put("dealman",  pd_token.getString("ZXYH"));
						pd.put("cljd",  pd_token.getString("dept"));
						pd.put("type", "2");
						workorderService.editCL(pd);
						workorderService.editWorkproc(pd);
					}
				}	
			}
		}
		PageData pd_s=workorderService.findById(pd);
	    object.put("success","true");
	    if(pd_s!=null){
	    	object.put("data",pd_s.get("id"));
	    }
	    object.put("msg","保存成功");
	    
	    return object.toString();
	}   
	
	
	
	/**工单保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/workorderSh" ,produces = "text/html;charset=utf-8")
	@ResponseBody
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true") 
	public String workorderSh(HttpServletRequest request,HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		PageData pd = new PageData();
		String reqdata=request.getParameter("data");
		com.alibaba.fastjson.JSONObject data =  com.alibaba.fastjson.JSONObject.parseObject(reqdata);
        JSONObject object = new JSONObject();

		String cmd = data.getString("cmd")==null?"":data.getString("cmd");
		PageData pd_stoken=new PageData();
		com.alibaba.fastjson.JSONObject json = data.getJSONObject("data");
		pd_stoken.put("TOKENID", json.get("tokenid"));
		PageData pd_token=zxlbService.findByTokenId(pd_stoken);
		
		String username=(json.getString("username")==null?"":json.getString("username"));
		if(!username.equals("")){
			pd_stoken.put("USERNAME", username);
			pd_token=userService.findByUsername(pd_stoken);
			if(pd_token!=null){
				pd_token.put("dept", pd_token.getString("DWBM"));
				pd_token.put("ZXYH", username);
			}
		}
		
		if(pd_token==null){ 
			object.put("success","false");
			object.put("msg","登录超时请重新登录");
			return object.toString();
		}
		
		pd.put("id", json.get("id"));
		PageData pd_s =workorderService.findById(pd);
		if(pd_s==null){ 
			object.put("success","false");
			object.put("msg","审核的信息不存在");
			return object.toString();
		}
		String taskId ="";
		taskId = ruprocdefService.getTaskID(pd_s.getString("proc_id"));	//任务ID
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		String OPINION =   pd_token.getString("ZXYH") + ",huangfege,"+json.getString("OPINION");//审批人的姓名+审批意见
		String msg = json.getString("msg");
		//String[] dept=(pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept")).split(",");
		//System.out.println("tsdept:"+pd_s.getString("tsdept"));
		List<Task> tasks = taskService.createTaskQuery().taskName("多部门工单处理")
                .processInstanceId(json.getString("PROC_INST_ID_")).list();
		//System.out.println(tasks);
		
		Task task=taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
		String taskname=task.getName()==null?"":task.getName();
        // 如果是会签流程
		boolean boo=false;
		String sendcont="";
		if(pd_s!=null){
			sendcont="您有一个投诉工单需要处理：投诉内容："+pd_s.getString("tscont");
		}
		String userstr="";
        if (taskname.equals("工单提交"))
        {
        	
        	String tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
        	String[] dept=tsdept.split(",");
        	map.put("提交人", pd_token.getString("ZXYH"));		//审批结果
        	map.put("提交内容",OPINION);		//审批结果
        	if(dept.length==1){								//批准
        		map.put("BMZR","yes");
        		
			}else{												//驳回
				
				map.put("BMZR","no");
			}
        	map.put("depts",pd_s.getString("tsdept"));
        	pd.put("tsdept", pd_s.getString("tsdept"));
        	pd.put("ROLE_NAME", "部门处理人员");
			List<PageData> roleList=userService.getUserByRoleName(pd);
			
			for(PageData pd_gd:roleList){
				if(userstr!=""){
					userstr=userstr+",";
				}
				userstr=userstr+pd_gd.getString("USERNAME");
				
			}
        	taskService.complete(taskId, map); //设置会签
        	pd.put("type", "3");
        }else if (taskname.equals("单部门工单处理")){
        	String tslevel=pd_s.getString("tslevel")==null?"":pd_s.getString("tslevel");
        	String tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");

        	map.put("单部门处理人", pd_token.getString("ZXYH"));		//审批结果
        	map.put("单部门处理内容",OPINION);		//审批结果
        	//pd.put("tsdept", tsdept);
        	pd.put("ROLE_NAME", "部门领导");
			List<PageData> roleList=userService.getUserByRoleName(pd);
			//String userstr="";
			for(PageData pd_gd:roleList){
				if(userstr!=""){
					userstr=userstr+",";
				}
				userstr=userstr+pd_gd.getString("USERNAME");
				
			}
        	map.put("LEADER",userstr);		//审批结果
        	if(tslevel.equals("e1337c48f17042f39ec88b63e3d7def2")){								//批准
        		map.put("DBMDJ","low");
			}else{		
				map.put("DBMDJ","high");
			}
        	//String tkid =ruprocdefService.getTaskID(pd.getString("PROC_INST_ID_"));
			//setAssignee(tkid,pd_s.getString("tsdept"));	
        	taskService.complete(taskId, map); //设置会签
        	pd.put("type", "3");
        }else if (taskname.equals("部门领导审批")){
        	String tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
        	pd.put("tsdept", "");
        	pd.put("ROLE_NAME", "工单专员");
			List<PageData> roleList=userService.getUserByRoleName(pd);
			//String userstr="";
			for(PageData pd_gd:roleList){
				if(userstr!=""){
					userstr=userstr+",";
				}
				userstr=userstr+pd_gd.getString("USERNAME");
				
			}
        	map.put("GDZY",userstr);
        	if(msg.equals("yes")){								//批准
        		map.put("MSG","yes");
			}else{	
	        	String[] dept=tsdept.split(",");
	        	for(int i=0;i<dept.length;i++){
	        		pd_s.put("dept", dept[i]);
					pd_s.put("proc_id", pd.getString("PROC_INST_ID_"));
					pd_s.put("iscl", "1");
					ruprocdefService.delDealDept(pd_s);
	        	}
				map.put("MSG","no");
			}	
        	map.put("部门领导姓名", pd_token.getString("ZXYH"));		//审批结果
        	map.put("部门领导-审批内容",OPINION);		//审批结果
        	taskService.complete(taskId, map); //设置会签
        	pd.put("type", "3");
        }else if (taskname.equals("单部门领导审批")){
        	String tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
        	pd.put("tsdept", "");
        	pd.put("ROLE_NAME", "工单专员");
			List<PageData> roleList=userService.getUserByRoleName(pd);
			//String userstr="";
			for(PageData pd_gd:roleList){
				if(userstr!=""){
					userstr=userstr+",";
				}
				userstr=userstr+pd_gd.getString("USERNAME");
				
			}
        	if(msg.equals("yes")){								//批准
        		map.put("DBMMSG","yes");
			}else{	
	        	String[] dept=tsdept.split(",");
	        	for(int i=0;i<dept.length;i++){
	        		pd_s.put("dept", dept[i]);
					pd_s.put("proc_id", pd.getString("PROC_INST_ID_"));
					pd_s.put("iscl", "1");
					ruprocdefService.delDealDept(pd_s);
	        	}
				map.put("DBMMSG","no");
			}	
        	map.put("单部门-部门领导姓名", pd_token.getString("ZXYH"));		//审批结果
        	map.put("单部门-部门领导-审批内容",OPINION);		//审批结果
        	taskService.complete(taskId, map); //设置会签
        	pd.put("type", "3");
        }else if (taskname.equals("工单专员审批")){
        	
        	
        	String tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
        	
        	String[] dept=tsdept.split(",");
        	if(msg.equals("yes")){
        		//批准
        		map.put("RESULT","yes");
        		pd.put("uid", pd_s.getString("uid"));
            	pd.put("dealman",   pd_token.getString("ZXYH"));
    			pd.put("cljd",  pd_token.getString("dept"));
    			pd.put("type", "4");
    			workorderService.editCL(pd);
    			//taskService.complete(taskId, map); //设置会签
    			
    			boo=true;
			}else{		
				if(dept.length==1){
        			map.put("RESULT","no-single"); //单部门审批不通过
        		}else{
        			map.put("RESULT","no-mul"); //多部门审批不通过
        		}
			}	
        	map.put("工单专员", pd_token.getString("ZXYH"));		//审批结果
        	map.put("工单专员审批内容",OPINION);		//审批结果
        	taskService.complete(taskId, map); 
        	pd.put("type", "3");
        }else if (taskname.equals("多部门工单处理")){
			
			pd_s.put("dept",  pd_token.getString("dept"));
			pd_s.put("proc_id", pd.getString("PROC_INST_ID_"));
			pd_s.put("iscl", "1");

			pd_s.put("remark", OPINION);
			PageData pd_deptdeal =ruprocdefService.getDealByDept(pd_s);
			if(pd_deptdeal==null||pd_deptdeal.get("id")==null){
				
				pd_s.put("czman",  pd_token.getString("ZXYH"));
				ruprocdefService.saveDealDept(pd_s);
				
				String tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
	        	String[] dept=tsdept.split(",");
	        	int num=0;
	        	for(int i=0;i<dept.length;i++){
	        		pd_s.put("dept", dept[i]);
					pd_s.put("proc_id", pd.getString("PROC_INST_ID_"));
					pd_s.put("isdeal", "1");
					PageData pd_deptdeal_s =ruprocdefService.getDealByDept(pd_s);
					if(pd_deptdeal_s!=null&&pd_deptdeal_s.get("id")!=null){
						num=num+1;
						String remark=pd_deptdeal_s.getString("remark");
						String[] arr=remark.split(",");
						if(arr.length==3){
							OPINION = pd_deptdeal_s.getString("czman") + ",huangfege,"+arr[2];//审批人的姓名+审批意见
						}else{
							OPINION = pd_deptdeal_s.getString("czman") + ",huangfege,";//审批人的姓名+审批意见
						}
						map.put("多部门处理内容"+num,OPINION);		//审批结果
					}
	        	}
	        	
	        	if(dept.length>=2&&dept.length==num){
	        		//驳回
	        		String tslevel=pd_s.getString("tslevel")==null?"":pd_s.getString("tslevel");
	            	
	        		
	            	map.put("多部门处理",tsdept);		//审批结果
	            	
	            	pd.put("tsdept", "");
	            	pd.put("ROLE_NAME", "部门领导");
	    			List<PageData> roleList=userService.getUserByRoleName(pd);
	    		//	String userstr="";
	    			for(PageData pd_gd:roleList){
	    				if(userstr!=""){
	    					userstr=userstr+",";
	    				}
	    				userstr=userstr+pd_gd.getString("USERNAME");
	    			}
	    			//userstr="350101";
	            	map.put("LEADER",userstr);		//审批结果果
	            	if(tslevel.equals("e1337c48f17042f39ec88b63e3d7def2")){								//批准
	            		map.put("DJ","low");
	    			}else{		
	    				map.put("DJ","high");
	    			}	
	            	
	            	taskService.complete(taskId, map); //设置会签
	        	}
	        	pd.put("type", "3");
			}else{
				boo=true;
			}	
		}	
        if(!boo){
        	pd.put("uid", pd_s.getString("uid"));
         	pd.put("dealman",   pd_token.getString("ZXYH"));
     		pd.put("cljd",  pd_token.getString("dept"));
     		workorderService.editCLByuid(pd);
        }
        
        String[] arry=userstr.split(",");
        RuTaskController ruTaskController=new RuTaskController();
        for(int i=0;i<arry.length;i++){
        	if(arry[i]!=null&&!arry[i].equals("")){
				ruTaskController.sendMsg(arry[i], sendcont);
			}
        }
        object.put("success","true");
        object.put("msg","审核成功");
	    
	    return object.toString();
	}   
	
	
	/**文件上传保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/workorderFileUpload",produces = "text/html;charset=utf-8")
	@ResponseBody
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true") 
	public String editFile(@RequestParam(value = "file",required = false) MultipartFile[] files,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"新增doc");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		JSONObject object=new JSONObject();
		PageData pd = new PageData();
		String userid=request.getParameter("userid")==null?"":request.getParameter("userid");
		PageData pd_token=new PageData();
		if(userid.equals("")){
			pd.put("TOKENID", request.getParameter("tokenid"));
			pd_token=zxlbService.findByTokenId(pd);
			if(pd_token==null){ 
				object.put("success","false");
				object.put("msg","登录超时请重新登录");
				return object.toString();
			}
		}else{
			pd.put("USERNAME", userid);
			pd_token=userService.findByUsername(pd);
			if(pd_token==null){ 
				object.put("success","false");
				object.put("msg","登录超时请重新登录");
				return object.toString();
			}else{
				pd_token.put("dept", pd_token.getString("DWBM"));
				pd_token.put("ZXYH", userid);
			}
		}
		
		PageData pd_workorder = new PageData();
		pd = this.getPageData();
		pd.put("uid", request.getParameter("uid"));
		pd.put("filename", request.getParameter("filenames"));
		pd.put("createman",pd_token.getString("ID"));
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);
		if (null != files) {
			for (MultipartFile i:files) {
				if(i.getSize()>0) {
					String filename = i.getOriginalFilename();
					//System.out.println(filename+"filename");
					String ext = MyUtils.getFileNameExt(filename);
					String newFileName = dateString+"_"+System.currentTimeMillis() + "." + ext;
					
					String path=request.getServletContext().getRealPath("uploadFiles/uploadFile");
					//String newFilePath = MyUtils.transferToWithHash(i, path, newFileName);
					pd.put("file", newFileName);
					pd.put("name", filename);
					pd.put("ext", ext);
					pd.put("createman",  pd_token.getString("ZXYH"));
					File file = new File(path,newFileName);
					try {
						i.transferTo(file);
						workorderService.savefile(pd);
						object.put("success","true");
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}	
		}else{
			object.put("success","false");
			object.put("data","保存失败");
		}	
		
		//mv.addObject("msg","success_add");
		//mv.setViewName("redirect:/workorder/filelist.do?uid="+request.getParameter("uid")+"");
		return object.toString();
	}
	
	/*
	 * 文件下载
	 */
	@RequestMapping(value="/downLoadWorkorderFile",produces = "text/html;charset=utf-8")
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true") 
	public void downLoadWorkorderFile(HttpServletRequest request,HttpServletResponse response){
		
		PageData pd = new PageData();
		PageData pd_file = new PageData();
		pd = this.getPageData();
		
		
		FileInputStream inputStream = null;
		OutputStream outputStream = null;
		File file = null;
		try {
			pd_file=workorderService.findFileById(pd);
			String downloadFileName = pd_file.getString("name");
			String path=request.getServletContext().getRealPath("uploadFiles/uploadFile/")+"/"+pd_file.getString("file");
			System.out.println(path);
			file = new File(path);
			if (!file.exists()) {
				
				System.out.println("日报文件不存在，请重新导出数据");
			} else {
				inputStream = new FileInputStream(file);
				response.setContentType("application/txt;charset=utf-8");
				response.setHeader("Content-Disposition",
						"attachment;Filename=" + new String(downloadFileName.getBytes("GB2312"), "ISO8859-1"));// 设置下载后文件的名字、防止中文乱码

				outputStream = response.getOutputStream();
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = inputStream.read(bytes)) > 0) {
					outputStream.write(bytes, 0, len);
				}
			}
		} catch (Exception e) {
			//log.error("文件下载异常：", e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (IOException e) {
				//log.error("下载日报中关闭流异常 ：{} ", e);
			}
		}
	}
	
	/*
	 * 获取编码
	 */
	public PageData getNum(String year) throws Exception{
		PageData pd_num = new PageData();
		pd_num.put("year", year);
		pd_num = workorderService.findByYear(pd_num);
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
	
	
	/*
	 * 导出知识库
	 */
	@RequestMapping(value="/excelDoclist",produces = "text/html;charset=utf-8")
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true") 
	public String excelDoclist(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//logBefore(logger, Jurisdiction.getUsername()+"导出doc到excel");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		//User user=Jurisdiction.getLoginUser();
		response.addHeader("Access-Control-Allow-Origin", "*");
		Page page = new Page();
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		JSONObject object=new JSONObject();
		pd.put("TOKENID", request.getParameter("tokenid"));
		PageData pd_token=zxlbService.findByTokenId(pd);
		if(pd_token==null){ 
			object.put("success","false");
			object.put("msg","登录超时请重新登录");
			return object.toString();
		}

		pd.put("isdel", "0"); 

		page.setShowCount(9999999);
		page.setPd(pd);

		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();

		dataMap.put("titles", titles);
		pd.put("ids", request.getParameter("ids"));
		List<PageData> varOList = docService.findByIds(pd);


		try
		{
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


			wsheet.addCell(new Label(0, 0, "知识库类型"));
			wsheet.addCell(new Label(1, 0, "标题"));
			wsheet.addCell(new Label(2, 0, "内容"));
			wsheet.addCell(new Label(3, 0, "来源"));
			wsheet.addCell(new Label(4, 0, "知识状态"));
			wsheet.addCell(new Label(5, 0, "作者"));
			
			wsheet.addCell(new Label(6, 0, "创建人"));
			wsheet.addCell(new Label(7, 0, "创建时间"));

			wsheet.addCell(new Label(8, 0, "部门名称"));

			wsheet.addCell(new Label(9, 0, "审核意见"));
			wsheet.addCell(new Label(10, 0, "审核时间"));
			int num = 1;
			String createmanname="";
			for(int i=0;i<varOList.size();i++){
				PageData vpd = new PageData();

				wsheet.addCell(new Label(0, num, varOList.get(i).getString("doctypename")));	    //1
				wsheet.addCell(new Label(1, num, String.valueOf(varOList.get(i).get("doctitle"))));	    //2
				wsheet.addCell(new Label(2, num, String.valueOf(varOList.get(i).getString("doccont"))));	    //3
				String issh=String.valueOf(varOList.get(i).get("doctype"));
				if(issh.equals("0")){
					issh="待审核";
				}
				if(issh.equals("1")){
					issh="已审核";
				}
				if(issh.equals("2")){
					issh="审核不通过";
				}
				wsheet.addCell(new Label(3, num, varOList.get(i).getString("docsource")));	    //5
				wsheet.addCell(new Label(4, num, issh));	    //4

				wsheet.addCell(new Label(5, num, varOList.get(i).getString("docauthor")));	    //6
				createmanname=varOList.get(i).getString("createmanname");
				if(createmanname==null||createmanname.equals("")){
					createmanname=varOList.get(i).getString("createmanname1");
				}
				wsheet.addCell(new Label(6, num, createmanname));	    //7
				wsheet.addCell(new Label(7, num, varOList.get(i).get("createdate").toString()));	    //8
				wsheet.addCell(new Label(8, num, varOList.get(i).getString("deptName")));	    //5
				wsheet.addCell(new Label(9, num, varOList.get(i).getString("shyj")));	    //5
				if(varOList.get(i).get("shtime")!=null){
					wsheet.addCell(new Label(10, num, varOList.get(i).get("shtime").toString()));	    //8

				}
				
				num++;
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
		return null;
	}
	
	
	
	/**文件上传保存
	 * @param
	 * @throws Exception
     * ,method = RequestMethod.POST
	 */
	@RequestMapping(value="/docSave")
	@ResponseBody
	@CrossOrigin(allowedHeaders = "*", allowCredentials = "true") 
	public String docSave(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.addHeader("Access-Control-Allow-Origin", "*");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.getParameter("tokenid");

		JSONObject object=new JSONObject();
		PageData pd = new PageData();
		PageData pd_area = new PageData();
		pd = this.getPageData();
		pd.put("TOKENID", request.getParameter("tokenid"));

		PageData pd_token=zxlbService.findByTokenId(pd);
		if(pd_token==null){
			object.put("success","false");
			object.put("msg","登录超时请重新登录");
			return object.toString();
		}
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);
		pd = new PageData();
		String type=String.valueOf(request.getParameter("type")==null?"": request.getParameter("type"));
		String cmd=String.valueOf( request.getParameter("cmd")==null?"": request.getParameter("cmd"));
		String dept=String.valueOf( request.getParameter("dept")==null?"": request.getParameter("dept"));
		pd.put("createman",pd_token.getString("ID"));
        pd.put("doctitle", request.getParameter("doctitle"));
        pd.put("doctype_id", request.getParameter("doctype_id"));//父类id
      //  pd.put("sort",pd.getString("sort"));  //排序
        pd.put("doctype", request.getParameter("doctype")); //状态 1、启用 0，禁用
        pd.put("doccont", request.getParameter("doccont"));
        pd.put("ishot", request.getParameter("ishot"));
        pd.put("validate", request.getParameter("validate"));
        pd.put("type",type); //1公共接口，2用户，3部门
        pd.put("id", request.getParameter("id"));


		//System.out.println(pd_token);
        if(type.equals("3")&&dept.equals("")){
        	pd.put("dept",pd_token.getString("dept"));
        }
        if(cmd.equals("docAdd")){
        	docService.save(pd);
        	object.put("success","true");
 			object.put("data","保存成功");
        }
       
        if(cmd.equals("docEdit")){
        	docService.edit(pd);
        	object.put("success","true");
 			object.put("data","修改成功");
        }
		//System.out.println(object);
		return object.toString();
	}
	

}
	
 