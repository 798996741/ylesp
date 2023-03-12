package com.fh.controller.activiti.rutask;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.apache.http.client.ClientProtocolException;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.activiti.AcBusinessController;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.entity.system.User;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.DateUtil;
import com.fh.util.HttpClientUtil;
import com.fh.util.ImageAnd64Binary;
import com.fh.util.PageData;
import com.fh.util.Jurisdiction;
import com.fh.util.PathUtil;
import com.fh.util.RightsHelper;
import com.fh.util.Tools;
import com.fh.util.weixin.Weixin;
import com.fh.service.activiti.hiprocdef.HiprocdefManager;
import com.fh.service.activiti.ruprocdef.RuprocdefManager;
import com.fh.service.activiti.ruprocdef.impl.RuprocdefService;
import com.fh.service.areamanage.AreaManageManager;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.service.system.fhsms.FhsmsManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.xxgl.service.mng.WorkorderManager;
import com.yulun.utils.Httpclient;

/**
 * 说明：待办任务
 */
@Controller
@RequestMapping(value="/rutask")
public class RuTaskController extends AcBusinessController {
	
	String menuUrl = "rutask/list.do"; 	//菜单地址(权限用)
	@Resource(name="ruprocdefService")
	private RuprocdefManager ruprocdefService;
	@Resource(name="hiprocdefService")
	private HiprocdefManager hiprocdefService;
	@Resource(name="fhsmsService")
	private FhsmsManager fhsmsService;
	@Resource(name="workorderService")
	private WorkorderManager workorderService;

	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	
	@Resource(name="areamanageService")
	private AreaManageManager areamanageService;
	@Autowired
	private TaskService taskService; 			//任务管理 与正在执行的任务管理相关的Service
	@Resource(name="userService")
	private UserManager userService;
	
	@Resource(name="roleService")
	private RoleManager roleService;
	
	/**待办任务列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表待办任务");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		User user=Jurisdiction.getLoginUser();
		
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		if(null != pd.getString("codes") ){
			pd.put("code", pd.getString("codes"));
		}	
		if(null != pd.getString("tssources") ){
			pd.put("tssource", pd.getString("tssources"));
		}	
		if(null != pd.getString("tsmans") ){
			pd.put("tsman", pd.getString("tsmans"));
		}	
		if(null != pd.getString("tsdepts") ){
			pd.put("tsdept", pd.getString("tsdepts"));
		}	
		
		if(null != pd.getString("types") ){
			pd.put("type", pd.getString("types"));
		}	
		
		if(null != pd.getString("tstypes")&&"" != pd.getString("tstypes") ){
			pd.put("tstype", pd.getString("tstypes"));
		}
		if(null != pd.getString("bigtstypes")&&"" != pd.getString("bigtstypes") ){
			pd.put("bigtstype", pd.getString("bigtstypes"));
		}
			
		
		if(null != pd.getString("endreasons") ){
			pd.put("endreason", pd.getString("endreasons"));
		}	
		
		
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastStart = pd.getString("lastStart");			//开始时间
		String lastEnd = pd.getString("lastEnd");				//结束时间
		if(lastStart != null && !"".equals(lastStart)){
			pd.put("lastStart", lastStart+" 00:00:00");
		}
		if(lastEnd != null && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd+" 00:00:00");
		}
		//pd.put("USERNAME", Jurisdiction.getUsername()); 		//查询当前用户的任务(用户名查询)
		//pd.put("RNUMBERS", Jurisdiction.getRnumbers()); 		//查询当前用户的任务(角色编码查询)
		//pd.put("DWBM", user.getDWBM()); 
		
		String role_id=user.getROLE_ID();
		PageData pd_js = new PageData();
		pd_js.put("ROLE_ID", role_id);
		PageData role=roleService.findObjectById(pd_js);
		
		if(role!=null&&(role.getString("ROLE_NAME")!=null&&(role.getString("ROLE_NAME").equals("部门处理人员")))){
			pd.put("DWBM", user.getDWBM()); 
			//||(user.getDWBM().equals("350101")&&role.getString("ROLE_NAME").equals("工单专员"))
		}else{
			pd.put("USERNAME", Jurisdiction.getUsername()); 		//查询当前用户的任务(用户名查询)
		}
		if((user.getDWBM().equals("350101")&&role.getString("ROLE_NAME").equals("工单专员"))){
			pd.put("DWBM", user.getDWBM()); 
			pd.put("USERNAME", Jurisdiction.getUsername());
			pd.put("isgdzy","1");
		}else{
			pd.put("isgdzy","0");
		}
		if(role.getString("ROLE_NAME").equals("部门领导")){
			pd.put("DWBM1", user.getDWBM()); 
		}
		if(role.getString("ROLE_NAME").equals("部门处理人员")){
			pd.put("DWBM2", user.getDWBM()); 
		}
		
		page.setPd(pd);
		
		boolean boo=roleService.isRole(role_id);
		//System.out.println(boo+"name");
		List<PageData>	varList =new ArrayList();
		List<PageData>	alllist =new ArrayList();
		if(boo){
			varList = ruprocdefService.list(page);	//列出Rutask列表
			for(PageData pddoc:varList){  
				
				if((user.getDWBM().equals("350101")&&role.getString("ROLE_NAME").equals("工单专员"))){
					if(pddoc!=null&&pddoc.getString("NAME_").equals("多部门工单处理")){
						PageData db_deal=new PageData();
						db_deal.put("dept", "350101");
						db_deal.put("proc_id", pddoc.getString("proc_id"));
						db_deal.put("isdeal", "2");
						db_deal.put("isdel", "0");
    					PageData pd_deptdeal_s =ruprocdefService.getDealByDept(db_deal);
    					if(pd_deptdeal_s!=null&&pd_deptdeal_s.get("id")!=null){
							alllist.add(pddoc);
    					}
						
					}else{
						alllist.add(pddoc);
					}
				}else{
					alllist.add(pddoc);
				}
			}
		}
		
		mv.setViewName("activiti/rutask/rutask_list");
		
		
		
		List<Dictionaries> tssourceList=dictionariesService.listAllDict("9ed978b4a4674adb9c08f45d2e2d9813");
	    mv.addObject("tssourceList", tssourceList);
	    if(user!=null&&user.getDWBM()!=null){
			String dwbm= user.getDWBM();
			if(dwbm.equals("350101")){
				dwbm="3501";
			}
			pd.put("dwbm", dwbm);
			
		}
	    pd.put("AREA_LEVEL", "3");
	    List<PageData> tsbmList=areamanageService.listAll(pd);
	    mv.addObject("tsdeptList", tsbmList);
	    pd.put("bianma", "014");
	    List<Dictionaries> tstypeList=dictionariesService.listAllDict("1b303f7026934f68a6bd1ea01433db19"); 
	    mv.addObject("tstypeLists", tstypeList);
		mv.addObject("varList", alllist);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**待办任务列表(只显示5条,用于后台顶部小铃铛左边显示)
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getList")
	@ResponseBody
	public Object getList(Page page) throws Exception{
		PageData pd = new PageData();
		Map<String,Object> map = new HashMap<String,Object>();
		pd.put("USERNAME", Jurisdiction.getUsername()); 		//查询当前用户的任务(用户名查询)
		pd.put("RNUMBERS", Jurisdiction.getRnumbers()); 		//查询当前用户的任务(角色编码查询)
		page.setPd(pd);
		page.setShowCount(5);
		List<PageData>	varList = ruprocdefService.list(page);	//列出Rutask列表
		List<PageData> pdList = new ArrayList<PageData>();
		for(int i=0;i<varList.size();i++){
			PageData tpd = new PageData();
			tpd.put("NAME_", varList.get(i).getString("NAME_"));	//任务名称
			tpd.put("PNAME_", varList.get(i).getString("PNAME_"));	//流程名称
			pdList.add(tpd);
		}
		map.put("list", pdList);
		map.put("taskCount", page.getTotalResult());
		return AppUtil.returnObject(pd, map);
	}
	
	/**去办理任务页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goHandle")
	public ModelAndView goHandle()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("proc_id", pd.getString("PROC_INST_ID_"));
		PageData pd_s =workorderService.findById(pd);
		List<PageData>	varList = ruprocdefService.varList(pd);			//列出流程变量列表
		List<PageData>	hitaskList = ruprocdefService.hiTaskList(pd);	//历史任务节点列表
		
		List<PageData>	clList=new ArrayList();

		PageData pdcl=new PageData();
		PageData pduser=new PageData();
		String[] arrcl=null;
		/*for(int i=0;i<hitaskList.size();i++){							//根据耗时的毫秒数计算天时分秒
			if(null != hitaskList.get(i).get("DURATION_")){
				Long ztime = Long.parseLong(hitaskList.get(i).get("DURATION_").toString());
				Long tian = ztime / (1000*60*60*24);
				Long shi = (ztime % (1000*60*60*24))/(1000*60*60);
				Long fen = (ztime % (1000*60*60*24))%(1000*60*60)/(1000*60);
				Long miao = (ztime % (1000*60*60*24))%(1000*60*60)%(1000*60)/1000;
				hitaskList.get(i).put("ZTIME", tian+"天"+shi+"时"+fen+"分"+miao+"秒");
			}
			
		}*/
		String lasttime="";
		for(int i=0;i<varList.size();i++){							//根据耗时的毫秒数计算天时分秒
			
			if(varList.get(i).getString("TEXT_")!=null&&varList.get(i).getString("TEXT_").indexOf(",huangfege,")>=0){
				pdcl=new PageData();
				arrcl=varList.get(i).getString("TEXT_").split(",huangfege,");
				pdcl.put("clman", arrcl[0]);
				pdcl.put("USERNAME", arrcl[0]);
				pdcl.put("NAME_", varList.get(i).getString("NAME_"));
				pdcl.put("clcont", arrcl[1]==null?"":arrcl[1]);
				pdcl.put("cldate", String.valueOf(varList.get(i).get("LAST_UPDATED_TIME_")==null?"":varList.get(i).get("LAST_UPDATED_TIME_")).substring(0, 19));
				//pdcl.put("cldate1", String.valueOf(varList.get(i).get("czdate")==null?"":varList.get(i).get("czdate")).substring(0, 16));

				pduser=userService.getUserInfoByUsername(pdcl);
				pdcl.put("areaname", pduser.getString("areaname"));
				pdcl.put("name", pduser.getString("NAME"));
				clList.add(pdcl);
				lasttime=String.valueOf(varList.get(i).get("LAST_UPDATED_TIME_"));
			}
			
		}
		
		String clsx=pd_s.getString("clsx")==null?"0":pd_s.getString("clsx");
		String czdate=String.valueOf(pd_s.get("czdate"));
		int hour=0;
		if(clsx.equals("24H")){
			hour=24;
		}else if(clsx.equals("48H")){
			hour=48;
		}else if(clsx.equals("3D")){
			hour=72;
		}else if(clsx.equals("7D")){
			hour=168;
		}else if(!clsx.equals("0")){
			if(clsx.equals("")){
				clsx="0";
			}
			hour=Integer.parseInt(clsx);
		}
		if(hour>0){
			String xclsj=RuTaskController.addDateMinut(czdate, hour);
			String cltime=RuTaskController.timesBetween(lasttime, xclsj);
			mv.addObject("cltime", cltime);
			mv.addObject("xclsj", xclsj);
		}
		
		String taskId = pd.getString("ID_");	//任务ID
		Task task=taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
		String taskname=task.getName()==null?"":task.getName();
		System.out.println("taskname:"+taskname);
		mv.addObject("taskname", taskname);
		if(taskname.equals("多部门工单处理")||taskname.equals("部门领导审批")){
			
			
			pd.put("isdel","0");
			if(taskname.equals("多部门工单处理")){
				pd.put("iscl","1");
			}
			if(taskname.equals("部门领导审批")){
				pd.put("iscl","2");
			}
			List<PageData>	clList1=clList;
			List<PageData>	dealList = ruprocdefService.getDealByDeptList(pd);
			for(int i=0;i<dealList.size();i++){				
				if(dealList.get(i).getString("remark")!=null&&dealList.get(i).getString("remark").indexOf(",huangfege,")>=0){
					pdcl=new PageData();
					arrcl=dealList.get(i).getString("remark").split(",huangfege,");
					pdcl.put("clman", arrcl[0]);
					pdcl.put("USERNAME", arrcl[0]);
					pdcl.put("clcont", arrcl[1]==null?"":arrcl[1]);
					pdcl.put("cldate", String.valueOf(dealList.get(i).get("czdate")==null?"":dealList.get(i).get("czdate")).substring(0, 19));
					//pdcl.put("cldate1", String.valueOf(dealList.get(i).get("czdate")==null?"":dealList.get(i).get("czdate")).substring(0, 16));
					pduser=userService.getUserInfoByUsername(pdcl);
					pdcl.put("areaname", pduser.getString("areaname"));
					pdcl.put("name", pduser.getString("NAME"));
					
					for(int k=0;k<clList1.size();k++){
						if(pdcl.getString("clcont").equals(clList1.get(k).getString("clcont"))&&pdcl.getString("areaname").equals(clList1.get(k).getString("areaname"))&&pdcl.getString("cldate").substring(0, 16).equals(clList1.get(k).getString("cldate").substring(0, 16))){
							
						}else{
							clList.add(pdcl);
						}
					}
					
					
				}
			}
		}
		
		
		List<PageData> listTemp = new ArrayList();
		
		
		for(int i=0;i<clList.size();i++){
			if(!listTemp.contains(clList.get(i))){
				listTemp.add(clList.get(i));
			}
		}
	
        if (listTemp.size() > 1) {
            //list 集合倒叙排序
        	Collections.sort(listTemp, new Comparator<PageData>() {
                 @Override
                 public int compare(PageData o1, PageData o2) {
                     SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                     try {
                         Date dt1 = format.parse(o1.getString("cldate"));
                         Date dt2 = format.parse(o2.getString("cldate"));
                         if (dt1.getTime() > dt2.getTime()) {
                             return 1;
                         } else if (dt1.getTime() < dt2.getTime()) {
                             return -1;
                         } else {
                             return 0;
                         }
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                     return 0;
                 }
             });
        }
		
		
		
		
		/*String FILENAME = URLDecoder.decode(pd.getString("FILENAME"), "UTF-8");
		createXmlAndPngAtNowTask(pd.getString("PROC_INST_ID_"),FILENAME);//生成当前任务节点的流程图片
		pd.put("FILENAME", FILENAME);
		String imgSrcPath = PathUtil.getClasspath()+Const.FILEACTIVITI+FILENAME;
		
		
		pd.put("imgSrc", "data:image/jpeg;base64,"+ImageAnd64Binary.getImageStr(imgSrcPath)); //解决图片src中文乱码，把图片转成base64格式显示(这样就不用修改tomcat的配置了)
		*/
		
	    pd.put("bianma", "014");
	    List<PageData> tstypeList=dictionariesService.listAllDictByBM(pd);
	    JSONObject object = new JSONObject();
		List<JSONObject> objlist=new ArrayList();
		for(PageData pd_obj:tstypeList){
			object = new JSONObject();
			object.put("id", pd_obj.get("DICTIONARIES_ID"));
			object.put("pId", pd_obj.getString("PARENT_ID"));
			object.put("name",  pd_obj.getString("NAME"));
			//object.put("name",  pd_obj.get("NAME"));
			objlist.add(object);
		}
	    String json = objlist.toString();
		json = json.replaceAll("id", "id").replaceAll("parentid", "pId").replaceAll("NAME", "name").replaceAll("subDict", "nodes").replaceAll("hasDict", "checked");
		//System.out.println(json);
		mv.addObject("zTreeNodes", json);
		
		JSONArray arr = JSONArray.fromObject(areamanageService.listAll(pd));
		String json_dept = arr.toString();
		json_dept = json_dept.replaceAll("AREA_ID", "id").replaceAll("PARENT_ID", "pId").replaceAll("NAME", "name").replaceAll("subAreaManage", "nodes").replaceAll("hasAreaManage", "checked").replaceAll("treeurl", "url");
		//System.out.println(json);
		mv.addObject("zTreeNodes_dept", json_dept);
	    
		
		mv.setViewName("activiti/rutask/rutask_handle");
		mv.addObject("varList", varList);
		mv.addObject("hitaskList", hitaskList);
		mv.addObject("clList", listTemp);
		mv.addObject("clCount", listTemp.size());
		mv.addObject("pd", pd);
		mv.addObject("pd_s", pd_s);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	

	public static String addDateMinut(String day, int hour){   
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;   
        try {   
            date = format.parse(day);   
        } catch (Exception ex) {   
            ex.printStackTrace();   
        }   
        if (date == null)   
            return "";   
        System.out.println("front:" + format.format(date)); //显示输入的日期  
        Calendar cal = Calendar.getInstance();   
        cal.setTime(date);   
        cal.add(Calendar.HOUR, hour);// 24小时制   
        date = cal.getTime();   
        System.out.println("after:" + format.format(date));  //显示更新后的日期 
	    cal = null;   
	    return format.format(date);   
 
	}
	
	public static String timesBetween(String sdate,String bdate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//System.out.println("sdate:"+sdate);
		//System.out.println("bdate:"+bdate);
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		long diff = 0;
		try {
			Date startDate= new Date();
			Date bindDate= df.parse(bdate);
			long stime = startDate.getTime();
			long btime = bindDate.getTime();
			if(stime>btime){
				diff = stime - btime;
			}else{
				diff = btime - stime;
			}
			day = diff/(24*60*60*1000);
			hour = diff/(60*60*1000) - day*24;
			min = diff/(60*1000) - day*24*60 - hour*60;
			sec = diff/1000-day*24*60*60-hour*60*60-min*60;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String hours="",mins="",secs="";
		if(hour<10){
			hours="0"+hour;
		}else{
			hours=hour+"";
		}
		if(min<10){
			mins="0"+min;
		}else{
			mins=min+"";
		}
		if(sec<10){
			secs="0"+sec;
		}else{
			secs=sec+"";
		}
		return  day+"天"+hours+"时"+mins+"分"+secs+"秒";

	}
	
	/**办理任务
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/handle")
	public ModelAndView handle() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"办理任务");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		Session session = Jurisdiction.getSession();
		User user=Jurisdiction.getLoginUser();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String taskId ="";
		
		String PROC_INST_ID_=pd.getString("PROC_INST_ID_");
		String id=pd.getString("id");
		String ID_=pd.getString("ID_")==null?"":pd.getString("ID_");
		String dwbm=user.getDWBM();
		String userid=user.getUSERNAME();
		String cfbm=pd.getString("cfbm")==null?"":pd.getString("cfbm");
		String doaction=pd.getString("doaction")==null?"":pd.getString("doaction");
		String msg=pd.getString("msg")==null?"":pd.getString("msg");
		String tsdept=pd.getString("tsdept")==null?"":pd.getString("tsdept");
		String OPINION=pd.getString("OPINION")==null?"派发工单":pd.getString("OPINION");
		this.deal(id,PROC_INST_ID_, ID_, dwbm, userid, cfbm, doaction, msg, tsdept, OPINION);
		
		mv.addObject("msg","success");
		mv.setViewName("redirect:/rutask/list.do");
		
		return mv;
	}
	
	
	public String deal(String id,String PROC_INST_ID_,String ID_,String dwbm,String userid,String cfbm,String doaction,String msg,String tsdept,String OPINION) throws Exception{
		PageData pd = new PageData();
		String taskId ="";
		
		pd.put("proc_id", PROC_INST_ID_);
		pd.put("id", id);
		pd.put("code", "");
		PageData pd_s =workorderService.findById(pd);
		System.out.println("pd_s:"+pd_s);
		if(doaction!=null&&doaction.equals("azb")){
			taskId = ruprocdefService.getTaskID(PROC_INST_ID_);	//任务ID
			pd.put("OPINION","派发工单");
		}else{
			taskId = ID_;	//任务ID
			pd_s.put("cfbm",cfbm);
			workorderService.editCfbm(pd_s);
			
		}
		
		String sfrom = "";
		//Object ofrom = getVariablesByTaskIdAsMap(taskId,"审批结果");
		//if(null != ofrom){
			//sfrom = ofrom.toString();
		//}
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		OPINION = sfrom + userid + ",huangfege,"+OPINION;//审批人的姓名+审批意见
		
		String newtsdept=tsdept;
		pd_s.put("newtsdept", newtsdept);
		String[] ndept=(pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept")).split(",");
		//System.out.println("tsdept:"+pd_s.getString("tsdept"));
		
		//System.out.println(tasks);
		long min=System.currentTimeMillis();
		
		Task task=taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
		String taskname=task.getName()==null?"":task.getName();
		//System.out.println(taskname+"taskname");
		if(taskname.equals("多部门工单处理")&&ndept.length==1){
			taskname="单部门工单处理";
		}
		
		if(taskname.equals("部门领导审批")&&ndept.length==1){
			taskname="单部门领导审批";
		}
        // 如果是会签流程
		boolean boo=false;
		String sendcont="",userstr="";
		if(pd_s!=null){
			sendcont="您有一个投诉工单需要处理：投诉内容："+pd_s.getString("tscont");
		}
		String nextdept="";
        if (taskname.equals("工单提交"))
        {
        	
        	tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
        	String[] dept=tsdept.split(",");
        	map.put("提交人",userid);		//审批结果
        	map.put("提交内容",OPINION);		//审批结果
        	if(dept.length==1){								//批准
        		map.put("BMZR","yes");
			}else{			
				map.put("BMZR","no");
			}
        	map.put("depts",pd_s.getString("tsdept"));
        	pd.put("tsdept", pd_s.getString("tsdept"));
        	nextdept= pd_s.getString("tsdept");
        	if(pd_s.getString("tsdept").equals("350101")){
        		pd.put("ROLE_NAME", "工单专员");
        	}else{
        		pd.put("ROLE_NAME", "部门处理人员");
        	}
        	
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
        	tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");

        	map.put("单部门处理人",Jurisdiction.getUsername());		//审批结果
        	map.put(min+"单部门处理内容",OPINION);		//审批结果
        	pd.put("tsdept", tsdept);
        	nextdept=tsdept;
        	
        	
        	if(tslevel.equals("e1337c48f17042f39ec88b63e3d7def2")){	
        		pd.put("ROLE_NAME", "工单专员");
    			List<PageData> roleList=userService.getUserByRoleName(pd);
    			
    			for(PageData pd_gd:roleList){
    				if(userstr!=""){
    					userstr=userstr+",";
    				}
    				userstr=userstr+pd_gd.getString("USERNAME");
    				
    			}
            	map.put("GDZY",userstr);
        		map.put("DBMDJ","low");
			}else{		
				pd.put("ROLE_NAME", "部门领导");
				List<PageData> roleList=userService.getUserByRoleName(pd);
				
				for(PageData pd_gd:roleList){
					if(userstr!=""){
						userstr=userstr+",";
					}
					userstr=userstr+pd_gd.getString("USERNAME");
					
				}
				map.put("LEADER",userstr);		//审批结果
				map.put("DBMDJ","high");
			}
        	//String tkid =ruprocdefService.getTaskID(pd.getString("PROC_INST_ID_"));
			//setAssignee(tkid,pd_s.getString("tsdept"));	
        	taskService.complete(taskId, map); //设置会签
        	pd.put("type", "3");
        }else if (taskname.equals("部门领导审批")){
        	
        	if(msg.equals("yes")){ //批准
	        	pd_s.put("dept",dwbm);
				pd_s.put("proc_id",PROC_INST_ID_);
				pd_s.put("iscl", "2");
				pd_s.put("isdel", "0");
				pd_s.put("remark", OPINION);
				PageData pd_deptdeal =ruprocdefService.getDealByDept(pd_s);
				if(pd_deptdeal==null||pd_deptdeal.get("id")==null){				
            		map.put("MSG","yes");
            		pd_s.put("czman", userid);
    				ruprocdefService.saveDealDept(pd_s);
    				tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
    	        	String[] dept=tsdept.split(",");
    	        	int num=0;
    	        	for(int i=0;i<dept.length;i++){
    	        		pd_s.put("dept", dept[i]);
    					pd_s.put("proc_id", PROC_INST_ID_);
    					pd_s.put("isdeal", "2");
    					pd_s.put("isdel", "0");
    					PageData pd_deptdeal_s =ruprocdefService.getDealByDept(pd_s);
    					if(pd_deptdeal_s!=null&&pd_deptdeal_s.get("id")!=null){
    						num=num+1;
    						String remark=pd_deptdeal_s.getString("remark");
    						String[] arr=remark.split(",");
    						if(arr.length==3){
    							OPINION = sfrom +pd_deptdeal_s.getString("czman") + ",huangfege,"+arr[2];//审批人的姓名+审批意见
    						}else{
    							OPINION = sfrom +pd_deptdeal_s.getString("czman") + ",huangfege,";//审批人的姓名+审批意见
    						}
    						map.put(min+"多部门领导审核"+num,OPINION);		//审批结果
    					}
    	        	}
    	        	
    	        	if(dept.length>=2&&dept.length==num){
    	        		//驳回
    	        		String tslevel=pd_s.getString("tslevel")==null?"":pd_s.getString("tslevel");

    	        		tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
    	            	pd.put("tsdept", "350101");
    	            	nextdept="350101";
    	            	pd.put("ROLE_NAME", "工单专员");
    	    			List<PageData> roleList=userService.getUserByRoleName(pd);
    	    			
    	    			for(PageData pd_gd:roleList){
    	    				if(userstr!=""){
    	    					userstr=userstr+",";
    	    				}
    	    				userstr=userstr+pd_gd.getString("USERNAME");
    	    				
    	    			}
    	            	map.put("GDZY",userstr);
    	            	map.put("部门领导姓名",Jurisdiction.getUsername());		//审批结果
    	            	//map.put(min+"部门领导-审批内容",OPINION);		//审批结果
    	            	taskService.complete(taskId, map); //设置会签
    	            	pd.put("type", "3");

    	        	}
    			}
			}else{	
	        	String[] dept=tsdept.split(",");
	        	//for(int i=0;i<dept.length;i++){
	        		pd_s.put("dept", dwbm);
					pd_s.put("proc_id", PROC_INST_ID_);
					pd_s.put("iscl", "1");
					ruprocdefService.delDealDept(pd_s);
					pd_s.put("iscl", "2");
					ruprocdefService.delDealDept(pd_s);
	        	//}
				map.put("MSG","no");
				if(pd_s!=null&&pd_s.getString("tsdept")!=null&&!pd_s.getString("tsdept").equals(pd_s.getString("newtsdept"))){
					map.put("depts",newtsdept);
					nextdept=newtsdept;
					workorderService.editTsdept(pd_s);
					workorderService.saveTsdept(pd_s);
				}else{
					nextdept=pd_s.getString("tsdept");
				}
				map.put("部门领导姓名",Jurisdiction.getUsername());		//审批结果
	        	map.put(min+"部门领导-审批内容",OPINION);		//审批结果
	        	taskService.complete(taskId, map); //设置会签
	        	
			}	
        	pd.put("type", "3");	
        	
        }else if (taskname.equals("单部门领导审批")){
        	
        	
        	tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
        	
        	nextdept="350101";
        	pd.put("ROLE_NAME", "工单专员");
        	pd.put("tsdept", "350101");
			List<PageData> roleList=userService.getUserByRoleName(pd);
			
			for(PageData pd_gd:roleList){
				if(userstr!=""){
					userstr=userstr+",";
				}
				userstr=userstr+pd_gd.getString("USERNAME");
				
			}
			map.put("GDZY",userstr);
        	if(msg.equals("yes")){								//批准
        		map.put("DBMMSG","yes");
			}else{	
	        	String[] dept=tsdept.split(",");
	        	for(int i=0;i<dept.length;i++){
	        		pd_s.put("dept", dept[i]);
					pd_s.put("proc_id",PROC_INST_ID_);
					pd_s.put("iscl", "1");
					ruprocdefService.delDealDept(pd_s);
	        	}
	        	
	        	String[] newdept=newtsdept.split(",");
	        	if(newdept.length>=2){
	        		map.put("DBMMSG","no-duble");
	        	}else{
	        		map.put("DBMMSG","no");
	        	}
	        	if(pd_s!=null&&pd_s.getString("tsdept")!=null&&!pd_s.getString("tsdept").equals(pd_s.getString("newtsdept"))){
					map.put("depts",newtsdept);
					nextdept=newtsdept;
					workorderService.editTsdept(pd_s);
					workorderService.saveTsdept(pd_s);
				}else{
					nextdept=pd_s.getString("tsdept");
				}
				//map.put("depts","350101");
			}	
        	map.put("单部门-部门领导姓名",Jurisdiction.getUsername());		//审批结果
        	map.put(min+"单部门-部门领导-审批内容",OPINION);		//审批结果
        	taskService.complete(taskId, map); //设置会签
        	pd.put("type", "3");
        }else if (taskname.equals("工单专员审批")){
        	
        	
        	tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
        	
        	String[] dept=tsdept.split(",");
        	if(msg.equals("yes")){
        		//批准
        		map.put("RESULT","yes");
        		pd.put("uid", pd_s.getString("uid"));
            	pd.put("dealman",  userid);
    			pd.put("cljd",  dwbm);
    			pd.put("type", "4");
    			workorderService.editCL(pd);
    			//taskService.complete(taskId, map); //设置会签
    			
    			boo=true;
			}else{		
				if(dept.length==1){
        			map.put("RESULT","no-single"); //单部门审批不通过
        		}else{
        			map.put("RESULT","no-mul"); //多部门审批不通过
        			for(int i=0;i<dept.length;i++){
    	        		pd_s.put("dept", dept[i]);
    					pd_s.put("proc_id", PROC_INST_ID_);
    					pd_s.put("iscl", "1");
    					ruprocdefService.delDealDept(pd_s);
    					pd_s.put("iscl", "2");
    					ruprocdefService.delDealDept(pd_s);
    	        	}
        			
        		}
				if(pd_s!=null&&pd_s.getString("tsdept")!=null&&!pd_s.getString("tsdept").equals(pd_s.getString("newtsdept"))){
					map.put("depts",newtsdept);
					nextdept=newtsdept;
					
					workorderService.editTsdept(pd_s);
					workorderService.saveTsdept(pd_s);
				}else{
					nextdept=pd_s.getString("tsdept");
				}
			}	
        	map.put("工单专员",userid);		//审批结果
        	map.put(min+"工单专员审批内容",OPINION);		//审批结果
        	taskService.complete(taskId, map); 
        	pd.put("type", "3");
        }else if (taskname.equals("多部门工单处理")){
			
			pd_s.put("dept",dwbm);
			pd_s.put("proc_id",PROC_INST_ID_);
			pd_s.put("iscl", "1");
			pd_s.put("isdel", "0");

			pd_s.put("remark", OPINION);
			PageData pd_deptdeal =ruprocdefService.getDealByDept(pd_s);
			if(pd_deptdeal==null||pd_deptdeal.get("id")==null){
				
				pd_s.put("czman", userid);
				
				ruprocdefService.saveDealDept(pd_s);
				
				tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
	        	String[] dept=tsdept.split(",");
	        	int num=0;
	        	for(int i=0;i<dept.length;i++){
	        		pd_s.put("dept", dept[i]);
					pd_s.put("proc_id", PROC_INST_ID_);
					pd_s.put("isdeal", "1");
					pd_s.put("isdel", "0");
					PageData pd_deptdeal_s =ruprocdefService.getDealByDept(pd_s);
					if(pd_deptdeal_s!=null&&pd_deptdeal_s.get("id")!=null){
						num=num+1;
						String remark=pd_deptdeal_s.getString("remark");
						String[] arr=remark.split(",");
						if(arr.length==3){
							OPINION = sfrom +pd_deptdeal_s.getString("czman") + ",huangfege,"+arr[2];//审批人的姓名+审批意见
						}else{
							OPINION = sfrom +pd_deptdeal_s.getString("czman") + ",huangfege,";//审批人的姓名+审批意见
						}
						map.put(min+"多部门处理内容"+num,OPINION);		//审批结果
					}
	        	}
	        	
	        	if(dept.length>=2&&dept.length==num){
	        		//驳回
	        		String tslevel=pd_s.getString("tslevel")==null?"":pd_s.getString("tslevel");
	            	
	        		
	            	map.put("多部门处理",tsdept);		//审批结果
	            	
	            	pd.put("tsdept", tsdept);
	            	
	    			//userstr="350101";
	            	map.put("LEADER",userstr);		//审批结果果
	            	if(tslevel.equals("e1337c48f17042f39ec88b63e3d7def2")){	
	            		//批准
	            		
	            		pd.put("ROLE_NAME", "工单专员");
	        			List<PageData> roleList=userService.getUserByRoleName(pd);
	        			
	        			for(PageData pd_gd:roleList){
	        				if(userstr!=""){
	        					userstr=userstr+",";
	        				}
	        				userstr=userstr+pd_gd.getString("USERNAME");
	        			}
	                	map.put("GDZY",userstr);
	            		map.put("DJ","low");
	    			}else{	
	    				pd.put("ROLE_NAME", "部门领导");
		    			List<PageData> roleList=userService.getUserByRoleName(pd);
		    			
		    			for(PageData pd_gd:roleList){
		    				if(userstr!=""){
		    					userstr=userstr+",";
		    				}
		    				userstr=userstr+pd_gd.getString("USERNAME");
		    			}
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
         	pd.put("dealman",  userid);
     		pd.put("cljd", nextdept);
     		workorderService.editCLByuid(pd);
        }
        
        String[] arry=userstr.split(",");
        RuTaskController ruTaskController=new RuTaskController();
        for(int i=0;i<arry.length;i++){
        	if(arry[i]!=null&&!arry[i].equals("")){
				ruTaskController.sendMsg(arry[i], sendcont);
			}
        }
		return "";
	}
	
	public String sendMsg(String USER_ID,String sendcont) throws Exception{
		try{
			sendcont=URLEncoder.encode("请登录：http://webchat.ada-robotics.com:8090/ccweb/appWeixin/getUserid","utf-8");
			sendcont=URLEncoder.encode("您有一条待处理工单，请<a href=\"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wwe1de579339958660&redirect_uri=http://webchat.ada-robotics.com:8090/ccweb/appWeixin/getUserid&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect\">登录操作！</a>","utf-8");
	
			//HttpClientUtil util=new HttpClientUtil("http://luyin.ada-robotics.com/weixin/app_message/sendtext?USER_ID="+USER_ID+"&content="+sendcont+"");
			String requestUrl="http://luyin.ada-robotics.com/weixin/app_message/sendtext?USER_ID="+USER_ID+"&content="+sendcont+"";
			
			Httpclient httpclient=new Httpclient(); 
			String content=Httpclient.doGet2(requestUrl, "GBK");
			//JSONObject jsonobj ect=Weixin.httpRequst(requestUrl, "get", null);
			//设置请求方式
			//util.get();
			//获取返回内容
			//String content=util.getContent();
			//打印结果
			System.out.println(content+"content11");
		}catch(Exception e){
			e.getMessage();
		}	
		return "";
		
	}
	
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		RuTaskController ruTaskController=new RuTaskController();
		System.out.println(ruTaskController.sendMsg("HuangJianLing", "内容"));
	}
	
	@RequestMapping(value="/getCltime")
	public void getCltime(PrintWriter out) throws Exception{
		PageData pd = new PageData();
		pd = this.getPageData();
		try{
			//public static String timesBetween(String sdate,String bdate) ;
			String cltime=RuTaskController.timesBetween("", pd.getString("xclsj"));
			//System.out.println();
			//day+"天"+hour+"时"+min+"分"+sec+"秒";
			out.write(cltime.replace("天", "day").replace("时", "hour").replace("分", "min").replace("秒", "sec"));
			out.close();
		} catch(Exception e){
			logger.error(e.toString(), e);
		}
	}
	
	
	
	
	
	
	/**办理任务
	 * @param
	 * @throws Exception
	 */
	/*@RequestMapping(value="/handle")
	public ModelAndView handle() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"办理任务");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		Session session = Jurisdiction.getSession();
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String taskId = pd.getString("ID_");	//任务ID
		
		pd.put("proc_id", pd.getString("PROC_INST_ID_"));
		PageData pd_s =workorderService.findById(pd);
		
		String sfrom = "";
		Object ofrom = getVariablesByTaskIdAsMap(taskId,"审批结果");
		if(null != ofrom){
			sfrom = ofrom.toString();
		}
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		String OPINION = sfrom + Jurisdiction.getUsername() + ",fh,"+pd.getString("OPINION");//审批人的姓名+审批意见
		String msg = pd.getString("msg");
		//String[] dept=(pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept")).split(",");
		//System.out.println("tsdept:"+pd_s.getString("tsdept"));
		List<Task> tasks = taskService.createTaskQuery().taskName("多部门工单处理")
                .processInstanceId(pd.getString("PROC_INST_ID_")).list();
		System.out.println(tasks);
		
		Task task=taskService.createTaskQuery() // 创建任务查询
                .taskId(taskId) // 根据任务id查询
                .singleResult();
		System.out.println(task+"tasks:::::");
        // 如果是会签流程
        if ( tasks != null && tasks.size() > 0 )
        {
            
            // 当前executionId
            String currentExecutionId = task.getId();
            // 当前签署总数
            String currentSignCount =String.valueOf(getVariablesByTaskIdAsMap(currentExecutionId , "nrOfInstances")) ;
            System.out.println(currentSignCount+"currentSignCount");
            if (msg.equals( "yes" ))
            {
                // 签署数+1
                setVariablesByTaskId(currentExecutionId , "nrOfInstances" , String.valueOf((Integer.parseInt(currentSignCount ) + 1)) );
            }

        }else{
		
        	List<Task> tasks_d = taskService.createTaskQuery().taskName("单部门工单处理")
                    .processInstanceId(pd.getString("PROC_INST_ID_")).list();
        	Task task_d=taskService.createTaskQuery() // 创建任务查询
                    .taskId(taskId) // 根据任务id查询
                    .singleResult();
    		System.out.println(task+"tasks:::::");
            // 如果是会签流程
            if ( tasks != null && tasks.size() > 0 ){
            	
            	
            }else{	
	
				if(msg.equals("yes")){								//批准
					map.put("单部门","单部门审批"+OPINION);		//审批结果
					setVariablesByTaskIdAsMap(taskId,map);			//设置流程变量
					setVariablesByTaskId(taskId,"RESULT","yes");
					completeMyPersonalTask(taskId);
				}else{												//驳回
					map.put("多部门","多部门审批");		//审批结果
					map.put("MULTIPLE",pd.getString("tsdept"));		//审批结果
					map.put("MULTIPLE", Jurisdiction.getUsername());
					setVariablesByTaskIdAsMap(taskId,map);			//设置流程变量
					setVariablesByTaskId(taskId,"RESULT","no");
					//completeMyPersonalTask(taskId);
					
					Map<String, Object> vMap = new HashMap<String, Object>();
					vMap.put("多部门","多部门审批");
					vMap.put("RESULT","no");
					
					List<String> assigneeList=new ArrayList<String>(); //分配任务的人员
					
					String[] dept=(pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept")).split(",");
					for(int i=0;i<dept.length;i++){
						assigneeList.add(dept[i]);
					}
					vMap.put("deptList", assigneeList);
					taskService.complete(taskId, vMap); //设置会签
					
					//setVariablesByTaskIdAsMap(taskId,vMap);
					//completeMyPersonalTask_Map(taskId,vMap);
					
					//map.put("审批结果", "【批准】" + OPINION);		//审批结果
					//setVariablesByTaskIdAsMap(taskId,map);			//设置流程变量
					//setVariablesByTaskId(taskId,"RESULT","批准");
					//completeMyPersonalTask(taskId);
				//}
            //}
				try{
					removeVariablesByPROC_INST_ID_(pd.getString("PROC_INST_ID_"),"RESULT");			//移除流程变量(从正在运行中)
				}catch(Exception e){
				}
				try{
					System.out.println(pd.getString("tsdept")+"tsdept:::::");
					String ASSIGNEE_ =pd.getString("tsdept");							//下一待办对象
					if(Tools.notEmpty(ASSIGNEE_)){
						String tkid =ruprocdefService.getTaskID(pd.getString("PROC_INST_ID_"));
						setAssignee(tkid,ASSIGNEE_);	//指定下一任务待办对象
					}else{
						Object os = session.getAttribute("YAssignee");
						if(null != os && !"".equals(os.toString())){
							ASSIGNEE_ = os.toString();										//没有指定就是默认流程的待办人
						}else{
							//trySendSms(mv,pd); //没有任务监听时，默认流程结束，发送站内信给任务发起人
						}
					}
					//mv.addObject("ASSIGNEE_",ASSIGNEE_);	//用于给待办人发送新任务消息
				//}catch(Exception e){
					//trySendSms(mv,pd);
				}
		}	
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}*/
	
	
	public static void startTask(TaskService taskService, String proId){
		Task task = taskService.createTaskQuery().processInstanceId(proId).singleResult();
		System.out.println("当前任务编码：" + task.getId() + "，当前任务名称：" + task.getName());
		//设置会签人员
		Map<String, Object> var = new HashMap<String, Object>();
		List<String> signList = new ArrayList<String>();
		signList.add("zhangSan");
		signList.add("liSi");
		var.put("signList", signList);
		taskService.complete(task.getId(), var);
	}

	

	public void Cl(String taskId,String tsdept) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"办理任务");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		Session session = Jurisdiction.getSession();
		RuprocdefService  ruprocdefService=new RuprocdefService();
		Map<String,String> remap= ruprocdefService.getTaskMapByID(taskId);
   	
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		//String OPINION =  Jurisdiction.getU_name() + ","+pd.getString("OPINION");//审批人的姓名+审批意见
		//String msg = pd.getString("msg");
		String dept[]=tsdept.split(",");
		if(dept.length==1){								//批准
			//map.put("审批结果", "【批准】" + OPINION);		//审批结果
			setVariablesByTaskIdAsMap(taskId,map);			//设置流程变量
			setVariablesByTaskId(taskId,"RESULT","single");
			completeMyPersonalTask(taskId);
		}else{												//驳回
			//map.put("审批结果", "【驳回】" + OPINION);		//审批结果
			setVariablesByTaskIdAsMap(taskId,map);			//设置流程变量
			setVariablesByTaskId(taskId,"RESULT","multiple");
			completeMyPersonalTask(taskId);
		}
		try{
			removeVariablesByPROC_INST_ID_(remap.get("PROC_INST_ID_"),"RESULT");			//移除流程变量(从正在运行中)
		}catch(Exception e){
			/*此流程变量在历史中**/
		}
		try{
			String ASSIGNEE_ =tsdept;							//下一待办对象
			if(Tools.notEmpty(ASSIGNEE_)){
				String tkid = null;
				if(null!=session.getAttribute("TASKID")){
					tkid=session.getAttribute("TASKID").toString();
				}else{
					//tkid =ruprocdefService.getTaskID(remap.get("PROC_INST_ID_"));
				}
				setAssignee(tkid,ASSIGNEE_);	//指定下一任务待办对象
			}else{
				Object os = session.getAttribute("YAssignee");
				if(null != os && !"".equals(os.toString())){
					ASSIGNEE_ = os.toString();										//没有指定就是默认流程的待办人
				}else{
					//trySendSms(mv,pd); //没有任务监听时，默认流程结束，发送站内信给任务发起人
				}
			}
			//mv.addObject("ASSIGNEE_",ASSIGNEE_);	//用于给待办人发送新任务消息
		}catch(Exception e){
			/*手动指定下一待办人，才会触发此异常。
			 * 任务结束不需要指定下一步办理人了,发送站内信通知任务发起人**/
			//trySendSms(mv,pd);
		}
		
	}
	
	/**尝试站内信
	 * @param mv
	 * @param pd
	 * @throws Exception
	 */
	public void trySendSms(ModelAndView mv,PageData pd)throws Exception{
		List<PageData>	hivarList = hiprocdefService.hivarList(pd);			//列出历史流程变量列表
		for(int i=0;i<hivarList.size();i++){
			if("USERNAME".equals(hivarList.get(i).getString("NAME_"))){
				sendSms(hivarList.get(i).getString("TEXT_"));
				mv.addObject("FHSMS",hivarList.get(i).getString("TEXT_"));
				break;
			}
		}
	}
	
	/**发站内信通知审批结束
	 * @param USERNAME
	 * @throws Exception
	 */
	public void sendSms(String USERNAME) throws Exception{
		PageData pd = new PageData();
		pd.put("SANME_ID", this.get32UUID());			//ID
		pd.put("SEND_TIME", DateUtil.getTime());		//发送时间
		pd.put("FHSMS_ID", this.get32UUID());			//主键
		pd.put("TYPE", "1");							//类型1：收信
		pd.put("FROM_USERNAME", USERNAME);				//收信人
		pd.put("TO_USERNAME", "系统消息");
		pd.put("CONTENT", "您申请的任务已经审批完毕,请到已办任务列表查看");
		pd.put("STATUS", "2");
		fhsmsService.save(pd);
	}
	
	/**去审批详情页面
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/details")
	public ModelAndView details(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("activiti/rutask/handle_details");
		mv.addObject("pd", pd);
		return mv;
	}
	
	
}
