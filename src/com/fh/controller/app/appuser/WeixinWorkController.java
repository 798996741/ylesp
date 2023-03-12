package com.fh.controller.app.appuser;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.fh.controller.base.BaseController;
import com.fh.entity.AreaManage;
import com.fh.entity.system.Dictionaries;
import com.fh.service.activiti.hiprocdef.HiprocdefManager;
import com.fh.service.activiti.ruprocdef.RuprocdefManager;
import com.fh.service.areamanage.AreaManageManager;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.appuser.WeiXinUserInfoService;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.PageData;
import com.fh.util.weixin.AccessToken;
import com.fh.util.weixin.WxApiUtil;
import com.xxgl.service.mng.WorkorderManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huangjianling
 * @create 2020-03-17 17:47
 * @desc 获取微信用户的所有信息，这个主要是为了不要用户自己填写个人信息
 **/
@Controller
@RequestMapping(value="/appWeixin")
public class WeixinWorkController extends BaseController {
 
	@Resource(name="appuserService")
	private AppuserManager appuserService;
	@Resource(name="weiXinUserInfoService")
	private WeiXinUserInfoService weiXinUserInfoService;

	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	@Resource(name="areamanageService")
	private AreaManageManager areamanageService;
	
	@Resource(name="workorderService")
	private WorkorderManager workorderService;
	@Resource(name="ruprocdefService")
	private RuprocdefManager ruprocdefService;
	@Resource(name="hiprocdefService")
	private HiprocdefManager hiprocdefService;
	//@Autowired
	@Resource(name="taskService")
	private TaskService taskService; 			//任务管理 与正在执行的任务管理相关的Service
	
	 /**
     * 进行网页授权，便于获取到用户的绑定的内容
     * @param request
     * @param session
     * @param map
     * @return
     * @throws Exception
     * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb24a2fd8af029cd6&redirect_uri=http://webchat.ada-robotics.com/recordpro/appbystu/getarea&response_type=code&scope=snsapi_userinfo&state=1&connect_redirect=1#wechat_redirect
     */
    @RequestMapping("/getUserid")
    public ModelAndView getUserid(HttpServletRequest request , HttpSession session, Map<String, Object> map) throws Exception {
    	//首先判断一下session中，是否有保存着的当前用户的信息，有的话，就不需要进行重复请求信息
    
    	ModelAndView mv = this.getModelAndView();
    	PageData pd = new PageData();
    	String userid="";
    	if(session.getAttribute("currentUser") != null){
    		userid = (String) session.getAttribute("userid");
    	}else {
    		/**
    		 * 进行获取openId，必须的一个参数，这个是当进行了授权页面的时候，再重定向了我们自己的一个页面的时候，
    		 * 会在request页面中，新增这个字段信息，要结合这个ProjectConst.Get_WEIXINPAGE_Code这个常量思考
    		 */
    		String code = request.getParameter("code");
    		try {
    			//得到当前用户的信息(具体信息就看weixinUser这个javabean)
    			//userid=this.getUserid(code);
    			//将获取到的用户信息，放入到session中
    			session.setAttribute("userid", userid);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    	//测试
    	//weiXinUser=new WeiXinUser();
    	//weiXinUser.setOpenId("983WXXDewojdd");
    	userid="350111-clry";
    	//userid="350111-clry";
    	//userid="350101-bmld";		//安质部领导
    	//userid="00000";
    	System.out.println(userid+"userid");
    	mv.addObject("userid", userid);
    	mv.setViewName("wxorder/todolist");
    	return mv;
    }
    
    /*
     * 获取userid
     */
    private String getUserid( String code) {
        
    	AccessToken access_token =  WxApiUtil.getAccessToken();
        String UserID = WxApiUtil.getUserId(access_token.getAccess_token(), code); //第3步
        return UserID;
    }
    
    
    /**
     * 进行网页授权，便于获取到用户的绑定的内容
     * @param request
     * @param session
     * @param map
     * @return
     * @throws Exception
     * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxb24a2fd8af029cd6&redirect_uri=http://webchat.ada-robotics.com/recordpro/appbystu/getarea&response_type=code&scope=snsapi_userinfo&state=1&connect_redirect=1#wechat_redirect
     */
    @RequestMapping("/complandetail_sh")
    public ModelAndView complandetail_sh(HttpServletRequest request , HttpSession session, Map<String, Object> map) throws Exception {
    	//首先判断一下session中，是否有保存着的当前用户的信息，有的话，就不需要进行重复请求信息
    
    	ModelAndView mv = this.getModelAndView();
    	PageData pd = new PageData();
    	pd=this.getPageData();
    	List<AreaManage> areaList=areamanageService.listByParentId("1c60efa9749946b8b82b7322f9e65ac7");
		mv.addObject("areaList", areaList);
        PageData pdDoc=workorderService.findById(pd);
      //  System.out.println(pdDoc.get("proc_id")+"proc_idd");
        String taskId = ruprocdefService.getTaskID(String.valueOf(pdDoc.get("proc_id")));	//任务ID	//任务ID
        String taskname="";
        if(taskId!=null){
        	Task task=taskService.createTaskQuery() // 创建任务查询
	                .taskId(taskId) // 根据任务id查询
	                .singleResult();
			taskname=task.getName()==null?"":task.getName();	
        }
	        
        mv.addObject("taskname", taskname);
    	mv.addObject("pd", pd);
    	mv.setViewName("wxorder/complandetail_sh");
    	return mv;
    }
    @RequestMapping("/attlist")
    public ModelAndView attlist(HttpServletRequest request , HttpSession session, Map<String, Object> map) throws Exception {
    	
    	ModelAndView mv = this.getModelAndView();
    	PageData pd = new PageData();
    	pd=this.getPageData();
    	mv.addObject("pd", pd);
    	mv.setViewName("wxorder/attlist");
    	return mv;
    }
    
    /*
     * 文件上传
     */
    @RequestMapping("/attupload")
    public ModelAndView attupload(HttpServletRequest request , HttpSession session, Map<String, Object> map) throws Exception {
    	
    	ModelAndView mv = this.getModelAndView();
    	PageData pd = new PageData();
    	pd=this.getPageData();
    	mv.addObject("pd", pd);
    	mv.setViewName("wxorder/attupload");
    	return mv;
    }
    
    /*
     * 处理记录
     */
    @RequestMapping("/deallist")
    public ModelAndView deallist(HttpServletRequest request , HttpSession session, Map<String, Object> map) throws Exception {
    	
    	ModelAndView mv = this.getModelAndView();
    	PageData pd = new PageData();
    	pd=this.getPageData();
    	mv.addObject("pd", pd);
    	mv.setViewName("wxorder/deallist");
    	return mv;
    }
    
    
    @RequestMapping("/complainlist")
    public ModelAndView complainlist(HttpServletRequest request , HttpSession session, Map<String, Object> map) throws Exception {
    	ModelAndView mv = this.getModelAndView();
    	PageData pd = new PageData();
    	pd=this.getPageData();
    	mv.addObject("pd", pd);
    	mv.setViewName("wxorder/complainlist");
    	return mv;
    }
    
    @RequestMapping("/distribute")
    public ModelAndView distribute(HttpServletRequest request , HttpSession session, Map<String, Object> map) throws Exception {
    	ModelAndView mv = this.getModelAndView();
    	PageData pd = new PageData();
    	pd=this.getPageData();
    	pd.put("parentId", "9ed978b4a4674adb9c08f45d2e2d9813");
  		List<PageData> tssourceList =dictionariesService.listAllDictByParentId(pd);
	    
    	//List<Dictionaries> tssourceList=dictionariesService.listAllDict("9ed978b4a4674adb9c08f45d2e2d9813");
    	mv.addObject("tssourcejson", JSON.toJSONString(tssourceList).replace("DICTIONARIES_ID", "value").replace("NAME", "text"));
    	
    	//mv.addObject("tssourceList", tssourceList);
    	pd.put("parentId", "2b0ee94d0db04ba6b244cedf436ab57b");
  		List<PageData> tslevelList =dictionariesService.listAllDictByParentId(pd);
	    //List<Dictionaries> tslevelList=dictionariesService.listAllDict("2b0ee94d0db04ba6b244cedf436ab57b");
	    mv.addObject("tsleveljson", JSON.toJSONString(tslevelList).replace("DICTIONARIES_ID", "value").replace("NAME", "text"));
    	
	    pd.put("parentId", "1b303f7026934f68a6bd1ea01433db19");
		List<PageData> tstypeList =dictionariesService.listAllDictByParentId(pd);
		mv.addObject("tstypejson", JSON.toJSONString(tstypeList).replace("DICTIONARIES_ID", "value").replace("NAME", "text"));
    	
		/*
		 * 地点
		 */
		List<AreaManage> areaList=areamanageService.listByParentId("1c60efa9749946b8b82b7322f9e65ac7");
		mv.addObject("areaList", areaList);
		
		
		pd.put("parentId", "55b155b57041478c9c3c15848e8d0225");
		List<PageData> tsclassifyList =dictionariesService.listAllDictByParentId(pd);
		
	   // List<Dictionaries> tsclassifyList=dictionariesService.listAllDict("55b155b57041478c9c3c15848e8d0225");
	    mv.addObject("tsclassifyjson", JSON.toJSONString(tsclassifyList).replace("DICTIONARIES_ID", "value").replace("NAME", "text"));
    	
    	mv.addObject("pd", pd);
    	mv.setViewName("wxorder/distribute");
    	return mv;
    }
    
    @RequestMapping(value="/getTstype")
	@ResponseBody
	public Object getTstype() throws Exception{
		PageData pd = new PageData();	
		pd=this.getPageData();
		Map<String,Object> map = new HashMap<String,Object>();
		List<Dictionaries> tstypeList=dictionariesService.listAllDict(pd.getString("bigtstype"));
	    //mv.addObject("tssourceList", tssourceList);
		JSONObject object = new JSONObject();
		List<JSONObject> objlist=new ArrayList();
		for(Dictionaries pd_obj:tstypeList){
			object = new JSONObject();
			object.put("value", pd_obj.getDICTIONARIES_ID());
			object.put("text", pd_obj.getNAME());
			objlist.add(object);
		}
		
		map.put("list", objlist);
		return AppUtil.returnObject(pd, map);
	}
	
    
}
