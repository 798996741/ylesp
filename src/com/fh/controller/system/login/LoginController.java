package com.fh.controller.system.login;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.service.system.fhlog.FHlogManager;
import com.fh.service.system.menu.MenuManager;
import com.fh.entity.Page;
import com.fh.entity.system.Menu;
import com.fh.entity.system.Role;
import com.fh.entity.system.User;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;
import com.xxgl.service.mng.ExetaskManager;
import com.xxgl.service.mng.TaskcustomManager;
import com.xxgl.service.mng.TaskuserManager;
import com.xxgl.service.mng.ZxlbManager;
/**
 * 总入口
 * @author fh QQ 3 1 3 5 9 6 7 9 0[青苔]
 * 修改日期：2015/11/2
 */
/**
 * @author Administrator
 *
 */
@Controller
public class LoginController extends BaseController {

	@Resource(name="userService")
	private UserManager userService;
	@Resource(name="menuService")
	private MenuManager menuService;
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="appuserService")
	private AppuserManager appuserService;
	@Resource(name="fhlogService")
	private FHlogManager FHLOG;
	
	@Resource(name="taskuserService")
	private TaskuserManager taskuserService;
	
	@Resource(name="taskcustomService")
	private TaskcustomManager taskcustomService;
	
	

	@Resource(name="exetaskService")
	private ExetaskManager exetaskService;
	
	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;
	
	
	/**访问登录页
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login_toLogin")
	public ModelAndView toLogin()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = this.setLoginPd(pd);	//设置登录页面的配置参数
		mv.setViewName("system/index/login");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**请求登录，验证用户
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/login_login")
	@ResponseBody
	public Object login()throws Exception{
		Map<String,String> map = new HashMap<String,String>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String errInfo = "";
		String KEYDATA[] = pd.getString("KEYDATA").replaceAll("qq351412933fh", "").replaceAll("QQ351412933fh", "").split(",fh,");
		if(null != KEYDATA && KEYDATA.length == 3){
			Session session = Jurisdiction.getSession();
			String sessionCode = (String)session.getAttribute(Const.SESSION_SECURITY_CODE);		//获取session中的验证码
			String code = KEYDATA[2];
			if(null == code || "".equals(code)){//判断效验码
				errInfo = "nullcode"; 			//效验码为空
			}else{
				String USERNAME = KEYDATA[0];	//登录过来的用户名
				String PASSWORD  = KEYDATA[1];	//登录过来的密码
				pd.put("USERNAME", USERNAME);
				if(Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)){		//判断登录验证码
					String passwd = new SimpleHash("SHA-1", USERNAME, PASSWORD).toString();	//密码加密
					pd.put("PASSWORD", passwd);
					////System.out.println(passwd);
					pd = userService.getUserByNameAndPwd(pd);	//根据用户名和密码去读取用户信息
					if(pd != null){
						this.removeSession(USERNAME);//请缓存
						pd.put("LAST_LOGIN",DateUtil.getTime().toString());
						userService.updateLastLogin(pd);
						
						User user = new User();
						user.setDWBM(pd.getString("DWBM"));
						//System.out.println(pd.getString("DWBM")+"dwbm");
						user.setZXZ(pd.getString("ZXZ"));
						user.setUSER_ID(pd.getString("USER_ID"));
						user.setUSERNAME(pd.getString("USERNAME"));
						user.setPASSWORD(pd.getString("PASSWORD"));
						user.setNAME(pd.getString("NAME"));
						user.setRIGHTS(pd.getString("RIGHTS"));
						user.setROLE_ID(pd.getString("ROLE_ID"));
						user.setLAST_LOGIN(pd.getString("LAST_LOGIN"));
						user.setIP(pd.getString("IP"));
						user.setSTATUS(pd.getString("STATUS"));
						User user_role = userService.getUserAndRoleById(user.getUSER_ID());	//根据用户名和密码去读取用户信息
									//把用户信息放session中
						String menu_ids=user_role.getMENU_IDS();
						session.setAttribute("menu_ids", menu_ids); 
						////System.out.println(menu_ids+"menu_ids:");
						////System.out.println(user_role.getPRO_IDS()+"user_role.getPRO_IDS():");
						//session.setAttribute("pro_ids_session", user_role.get);
						//session.setAttribute(Const.SESSION_USER, user.get);			//把用户信息放session中
						if(null == session.getAttribute(USERNAME + Const.SESSION_QX)){
							session.setAttribute(USERNAME + Const.SESSION_QX, this.getUQX(USERNAME));//按钮权限放到session中
						}
						
						
						session.setAttribute(Const.SESSION_USER, user);			//把用户信息放session中
						//session.setAttribute(Const.SESSION_USER, user);			//把用户信息放session中
						session.removeAttribute(Const.SESSION_SECURITY_CODE);	//清除登录验证码的session
						//shiro加入身份验证
						Subject subject = SecurityUtils.getSubject(); 
					    UsernamePasswordToken token = new UsernamePasswordToken(USERNAME, PASSWORD); 
					    try { 
					        subject.login(token); 
					    } catch (AuthenticationException e) { 
					    	errInfo = "身份验证失败！";
					    }
					}else{
						errInfo = "usererror"; 				//用户名或密码有误
						logBefore(logger, USERNAME+"登录系统密码或用户名错误");
						FHLOG.save(USERNAME, "登录系统密码或用户名错误");
					}
				}else{
					errInfo = "codeerror";				 	//验证码输入有误
				}
				if(Tools.isEmpty(errInfo)){
					errInfo = "success";					//验证成功
					logBefore(logger, USERNAME+"登录系统");
					FHLOG.save(USERNAME, "登录系统");
				}
			}
		}else{
			errInfo = "error";	//缺少参数
		}
		map.put("result", errInfo);
		return AppUtil.returnObject(new PageData(), map);
	}
	
	/**访问系统首页
	 * @param changeMenu：切换菜单参数
	 * @return
	 */
	@RequestMapping(value="/main/{changeMenu}")
	public ModelAndView login_index(@PathVariable("changeMenu") String changeMenu){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
			if (user != null) {
				User userr = (User)session.getAttribute(Const.SESSION_USERROL);				//读取session中的用户信息(含角色信息)
				if(null == userr){
					user = userService.getUserAndRoleById(user.getUSER_ID());				//通过用户ID读取用户信息和角色信息
					session.setAttribute(Const.SESSION_USERROL, user);			
				}else{
					user = userr;
				}
				String USERNAME = user.getUSERNAME();
				Role role = user.getRole();													//获取用户角色
				String roleRights = role!=null ? role.getRIGHTS() : "";						//角色权限(菜单权限)
				String menu_ids=user.getMENU_IDS();
				session.setAttribute("menu_ids", menu_ids); 
				
				//System.out.println("menu_ids:"+ menu_ids);
				session.setAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS, roleRights); 	//将角色权限存入session
				session.setAttribute(Const.SESSION_USERNAME, USERNAME);						//放入用户名到session
				this.setAttributeToAllDEPARTMENT_ID(session, USERNAME);						//把用户的组织机构权限放到session里面
				
				PageData pd_par = new PageData();
				List pro_ids=new ArrayList();;
				if(menu_ids!=null&&!menu_ids.equals("")){
					String[] pro=menu_ids.split(",");
					for(int i=0;i<pro.length;i++){
						pro_ids.add(pro[i]);
					}
				}
				pd_par.put("MENU_IDS", pro_ids);
				
				List<Menu> menuList = new ArrayList<Menu>();
				menuList = this.getAttributeMenu(session, USERNAME, menu_ids,(String) pd.getString("action"));			//菜单缓存
				//System.out.println(menuList+"menuList");
				//List<Menu> menuList = new ArrayList<Menu>();
				//menuList = this.changeMenuF(allmenuList, session, USERNAME, changeMenu);	//切换菜单
				if(null == session.getAttribute(USERNAME + Const.SESSION_QX)){
					session.setAttribute(USERNAME + Const.SESSION_QX, this.getUQX(USERNAME));//按钮权限放到session中
				}
				this.getRemortIP(USERNAME);	//更新登录IP
				
				//String username=Jurisdiction.getUsername();
				pd.put("USERNAME", USERNAME);
				List<PageData>	varList =null;
				PageData pd_user=zxlbService.findByUsername(pd);
				String rwstr="";
				if(pd_user!=null){
					mv.addObject("iszx", "1");
				}
				
				mv.setViewName("system/index/main");
				mv.addObject("user", user);
				mv.addObject("action", pd.get("action"));
				mv.addObject("menuList", menuList);
			}else {
				mv.setViewName("system/index/login");//session失效后跳转登录页面
			}
		} catch(Exception e){
			mv.setViewName("system/index/login");
			logger.error(e.getMessage(), e);
		}
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		mv.addObject("pd",pd);
		return mv;
	}
	
	
	@RequestMapping(value="/welcome")
	public ModelAndView welcome(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			Session session = Jurisdiction.getSession();
			User user = (User)session.getAttribute(Const.SESSION_USER);						//读取session中的用户信息(单独用户信息)
			if (user != null) {
				User userr = (User)session.getAttribute(Const.SESSION_USERROL);				//读取session中的用户信息(含角色信息)
				if(null == userr){
					user = userService.getUserAndRoleById(user.getUSER_ID());				//通过用户ID读取用户信息和角色信息
					session.setAttribute(Const.SESSION_USERROL, user);			
				}else{
					user = userr;
				}
				String USERNAME = user.getUSERNAME();
				Role role = user.getRole();													//获取用户角色
				String roleRights = role!=null ? role.getRIGHTS() : "";						//角色权限(菜单权限)
				String menu_ids=user.getMENU_IDS();
				session.setAttribute("menu_ids", menu_ids); 
				
				////System.out.println("menu_ids:"+ menu_ids);
				session.setAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS, roleRights); 	//将角色权限存入session
				session.setAttribute(Const.SESSION_USERNAME, USERNAME);						//放入用户名到session
				this.setAttributeToAllDEPARTMENT_ID(session, USERNAME);						//把用户的组织机构权限放到session里面
				
				PageData pd_par = new PageData();
			
				
				List<Menu> menuList = new ArrayList<Menu>();
				menuList = this.getAttributeMenu_byids(session, USERNAME, menu_ids,(String) pd.getString("action"),menu_ids);			//菜单缓存
	
				//List<Menu> menuList = new ArrayList<Menu>();
				//menuList = this.changeMenuF(allmenuList, session, USERNAME, changeMenu);	//切换菜单
				if(null == session.getAttribute(USERNAME + Const.SESSION_QX)){
					
					session.setAttribute(USERNAME + Const.SESSION_QX, this.getUQX(USERNAME));//按钮权限放到session中
				}
				this.getRemortIP(USERNAME);	//更新登录IP
				mv.setViewName("system/index/welcome");
				mv.addObject("user", user);
				mv.addObject("action", pd.get("action"));
				mv.addObject("menuList", menuList);
			}else {
				mv.setViewName("system/index/login");//session失效后跳转登录页面
			}
		} catch(Exception e){
			mv.setViewName("system/index/login");
			logger.error(e.getMessage(), e);
		}
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); //读取系统名称
		mv.addObject("pd",pd);
		return mv;
	}
	
	
	/**菜单缓存
	 * @param session
	 * @param USERNAME
	 * @param roleRights
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> getAttributeMenu(Session session, String USERNAME, String roleRights,String menu_id) throws Exception{
		List<Menu> allmenuList = new ArrayList<Menu>();
		//if(null == session.getAttribute(USERNAME + Const.SESSION_allmenuList)){
			if(menu_id==null){menu_id="0";}
			allmenuList = menuService.listAllMenuQxcd(menu_id,"1");							//获取所有菜单
			if(Tools.notEmpty(roleRights)){
				allmenuList = this.readMenu(allmenuList, roleRights);				//根据角色权限获取本权限的菜单列表
			}
			session.setAttribute(USERNAME + Const.SESSION_allmenuList, allmenuList);//菜单权限放入session中
		/*}else{
			allmenuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_allmenuList);
		}*/
		return allmenuList;
	}
	
	
	/**根据角色权限获取本权限的菜单列表(递归处理)
	 * @param menuList：传入的总菜单
	 * @param roleRights：加密的权限字符串
	 * @return
	 */
	public List<Menu> readMenu(List<Menu> menuList,String roleRights){
		List<Menu> menuList2=new ArrayList();
		Menu menu=null;
		if(menuList!=null){
			String[] arr = roleRights.split(",");
			Set<String> sets = new HashSet<String>(Arrays.asList(arr));
			for(int i=0;i<menuList.size();i++){
				if( sets.contains(menuList.get(i).getMENU_ID())){
					menuList.get(i).setHasMenu(true);
				}
				//menuList.get(i).setHasMenu(RightsHelper.testRights(roleRights, menuList.get(i).getMENU_ID()));
				if(menuList.get(i).isHasMenu()){		//判断是否有此菜单权限
					menu=new Menu();
					menu=menuList.get(i);
					//System.out.println(menuList.get(i).getMENU_URL()+":t");
					//if(!menuList.get(i).getMENU_TYPE().equals("3")){
						this.readMenu(menuList.get(i).getSubMenu(), roleRights);//是：继续排查其子菜单
						menuList2.add(menu);
					//}
				}
			}
		}
		return menuList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Menu> getAttributeMenu_byids(Session session, String USERNAME, String roleRights,String menu_id,String menu_ids ) throws Exception{
		List<Menu> allmenuList = new ArrayList<Menu>();
		
		//if(null == session.getAttribute(USERNAME + Const.SESSION_allmenuList)){	
			allmenuList = menuService.listAllMenuQxcd(menu_id,"1");							//获取所有菜单
			//System.out.print(allmenuList+"allmenuList:");
			if(Tools.notEmpty(roleRights)){
				allmenuList = this.readMenu_bymenuid(allmenuList, menu_ids);				//根据角色权限获取本权限的菜单列表
			}
			session.setAttribute(USERNAME + Const.SESSION_allmenuList, allmenuList);//菜单权限放入session中
		//}else{
			//allmenuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_allmenuList);
		//}
			//System.out.println("id:"+allmenuList.size()+":t");
			for(int i=0;i<allmenuList.size();i++){
				System.out.println("id:"+allmenuList.get(i).getMENU_ID()+":t");
			}
		return allmenuList;
	}

	public List<Menu> readMenu_bymenuid(List<Menu> menuList,String menu_ids){
		List<Menu> menuList2=new ArrayList();
		Menu menu=null;
		if(menuList!=null){
			for(int i=0;i<menuList.size();i++){
				
				menuList.get(i).setHasMenu(this.is_menu(menuList.get(i).getMENU_ID(), menu_ids));
				if(menuList.get(i).isHasMenu()){		//判断是否有此菜单权限
					menu=new Menu();
					menu=menuList.get(i);
					//System.out.println(menuList.get(i).getMENU_URL()+":t");
					//if(!menuList.get(i).getMENU_TYPE().equals("3")){
						this.readMenu_bymenuid(menuList.get(i).getSubMenu(), menu_ids);//是：继续排查其子菜单
						menuList2.add(menu);
					//}
				}
			}
		}
		
		return menuList2;
	}
	
	public boolean  is_menu(String menu_id,String menu_ids){
		boolean boo=false;
		String[] arr = menu_ids.split(",");
		Set<String> sets = new HashSet<String>(Arrays.asList(arr));
		if( sets.contains(menu_id)){
			boo=true;
		}
	/*		
		String ids[]=menu_ids.split(",");
		for(int i=0;i<ids.length;i++){
			
			if(menu_id.equals(ids[i])){
				//System.out.println(menu_id);
				boo=true;
			}
		}*/
		return boo;
	}
	
	/**切换菜单处理
	 * @param allmenuList
	 * @param session
	 * @param USERNAME
	 * @param changeMenu
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Menu> changeMenuF(List<Menu> allmenuList, Session session, String USERNAME, String changeMenu){
		List<Menu> menuList = new ArrayList<Menu>();
		if(null == session.getAttribute(USERNAME + Const.SESSION_menuList) || ("yes".equals(changeMenu))){
			List<Menu> menuList1 = new ArrayList<Menu>();
			List<Menu> menuList2 = new ArrayList<Menu>();
			for(int i=0;i<allmenuList.size();i++){//拆分菜单
				Menu menu = allmenuList.get(i);
				if("1".equals(menu.getMENU_TYPE())){
					menuList1.add(menu);
				}else if("2".equals(menu.getMENU_TYPE())){
					menuList2.add(menu);
				}
				
				////System.out.println(menu.getMENU_TYPE()+"menuList2");
			}
			session.removeAttribute(USERNAME + Const.SESSION_menuList);
			if("2".equals(session.getAttribute("changeMenu"))){
				session.setAttribute(USERNAME + Const.SESSION_menuList, menuList1);
				session.removeAttribute("changeMenu");
				session.setAttribute("changeMenu", "1");
				menuList = menuList1;
			}else{
				session.setAttribute(USERNAME + Const.SESSION_menuList, menuList2);
				session.removeAttribute("changeMenu");
				session.setAttribute("changeMenu", "2");
				menuList = menuList2;
			}
		}else{
			menuList = (List<Menu>)session.getAttribute(USERNAME + Const.SESSION_menuList);
		}
		return menuList;
	}
	
	/**把用户的组织机构权限放到session里面
	 * @param session
	 * @param USERNAME
	 * @return
	 * @throws Exception 
	 */
	public void setAttributeToAllDEPARTMENT_ID(Session session, String USERNAME) throws Exception{
		String DEPARTMENT_IDS = "0",DEPARTMENT_ID = "0";
		if(!"admin".equals(USERNAME)){
			//PageData pd = datajurService.getDEPARTMENT_IDS(USERNAME);
			//DEPARTMENT_IDS = null == pd?"无权":pd.getString("DEPARTMENT_IDS");
			//DEPARTMENT_ID = null == pd?"无权":pd.getString("DEPARTMENT_ID");
		}
		session.setAttribute(Const.DEPARTMENT_IDS, DEPARTMENT_IDS);	//把用户的组织机构权限集合放到session里面
		session.setAttribute(Const.DEPARTMENT_ID, DEPARTMENT_ID);	//把用户的最高组织机构权限放到session里面
	}
	
	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping(value="/tab")
	public String tab(){
		return "system/index/tab";
	}
	
	/**
	 * 进入首页后的默认页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/login_default")
	public ModelAndView defaultPage(Page page) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		PageData pd_zxyh = new PageData();
		logBefore(logger, Jurisdiction.getUsername()+"新增naire");
		String username=Jurisdiction.getUsername();
		pd.put("USERNAME", username);
		
		
		mv.setViewName("system/index/default");
		return mv;
	}
	
	/**
	 * 用户注销
	 * @param session
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/logout")
	public ModelAndView logout() throws Exception{
		String USERNAME = Jurisdiction.getUsername();	//当前登录的用户名
		logBefore(logger, USERNAME+"退出系统");
		FHLOG.save(USERNAME, "退出");
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		this.removeSession(USERNAME);//请缓存
		//shiro销毁登录
		Subject subject = SecurityUtils.getSubject(); 
		subject.logout();
		pd = this.getPageData();
		pd.put("msg", pd.getString("msg"));
		pd = this.setLoginPd(pd);	//设置登录页面的配置参数
		mv.setViewName("system/index/login");
		mv.addObject("pd",pd);
		return mv;
	}
	
	/**
	 * 清理session
	 */
	public void removeSession(String USERNAME){
		Session session = Jurisdiction.getSession();	//以下清除session缓存
		session.removeAttribute(Const.SESSION_USER);
		session.removeAttribute(USERNAME + Const.SESSION_ROLE_RIGHTS);
		session.removeAttribute(USERNAME + Const.SESSION_allmenuList);
		session.removeAttribute(USERNAME + Const.SESSION_menuList);
		session.removeAttribute(USERNAME + Const.SESSION_QX);
		session.removeAttribute(Const.SESSION_userpds);
		session.removeAttribute(Const.SESSION_USERNAME);
		session.removeAttribute(Const.SESSION_USERROL);
		session.removeAttribute("changeMenu");
		session.removeAttribute("DEPARTMENT_IDS");
		session.removeAttribute("DEPARTMENT_ID");
	}
	
	/**设置登录页面的配置参数
	 * @param pd
	 * @return
	 */
	public PageData setLoginPd(PageData pd){
		pd.put("SYSNAME", Tools.readTxtFile(Const.SYSNAME)); 		//读取系统名称
		String strLOGINEDIT = Tools.readTxtFile(Const.LOGINEDIT);	//读取登录页面配置
		if(null != strLOGINEDIT && !"".equals(strLOGINEDIT)){
			String strLo[] = strLOGINEDIT.split(",fh,");
			if(strLo.length == 2){
				pd.put("isZhuce", strLo[0]);
				pd.put("isMusic", strLo[1]);
			}
		}
		try {
			//List<PageData> listImg = loginimgService.listAll(pd);	//登录背景图片
			//pd.put("listImg", listImg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pd;
	}
	
	/**获取用户权限
	 * @param session
	 * @return
	 */
	public Map<String, String> getUQX(String USERNAME){
		PageData pd = new PageData();
		Map<String, String> map = new HashMap<String, String>();
		try {
			pd.put(Const.SESSION_USERNAME, USERNAME);
			pd.put("ROLE_ID", userService.findByUsername(pd).get("ROLE_ID").toString());//获取角色ID
			pd = roleService.findObjectById(pd);										//获取角色信息														
			map.put("add", pd.getString("ADD_QX"));	//增
			map.put("del", pd.getString("DEL_QX"));	//删
			map.put("edit", pd.getString("EDIT_QX"));	//改
			map.put("cha", pd.getString("CHA_QX"));	//查
			List<PageData> buttonQXnamelist = new ArrayList<PageData>();
			if("admin".equals(USERNAME)){
			//	buttonQXnamelist = fhbuttonService.listAll(pd);					//admin用户拥有所有按钮权限
			}else{
				//buttonQXnamelist = buttonrightsService.listAllBrAndQxname(pd);	//此角色拥有的按钮权限标识列表
			}
			for(int i=0;i<buttonQXnamelist.size();i++){
				map.put(buttonQXnamelist.get(i).getString("QX_NAME"),"1");		//按钮权限
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}	
		return map;
	}
	
	/** 更新登录用户的IP
	 * @param USERNAME
	 * @throws Exception
	 */
	public void getRemortIP(String USERNAME) throws Exception {  
		PageData pd = new PageData();
		HttpServletRequest request = this.getRequest();
		String ip = "";
		if (request.getHeader("x-forwarded-for") == null) {  
			ip = request.getRemoteAddr();  
	    }else{
	    	ip = request.getHeader("x-forwarded-for");  
	    }
		pd.put("USERNAME", USERNAME);
		pd.put("IP", ip);
		userService.saveIP(pd);
	}  
	
}
