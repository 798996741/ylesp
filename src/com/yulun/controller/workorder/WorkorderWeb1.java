package com.yulun.controller.workorder;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.fh.controller.activiti.AcStartController;
import com.fh.entity.Page;
import com.fh.service.activiti.hiprocdef.HiprocdefManager;
import com.fh.service.activiti.ruprocdef.RuprocdefManager;
import com.fh.service.system.role.RoleManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.xxgl.service.mng.WorkorderManager;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;


/**
 * 说明：工单接口开发
 * 创建人：351412933，hjl
 * 创建时间：2019-01-03
 */
public class WorkorderWeb1 extends AcStartController  implements CommonIntefate {
	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;
	@Resource(name="workorderService")
	private WorkorderManager workorderService;
	@Resource(name="userService")
	private UserManager userService;
	
	@Resource(name="roleService")
	private RoleManager roleService;
	@Resource(name="ruprocdefService")
	private RuprocdefManager ruprocdefService;
	@Resource(name="hiprocdefService")
	private HiprocdefManager hiprocdefService;
	//@Autowired
	@Resource(name="taskService")
	private TaskService taskService; 			//任务管理 与正在执行的任务管理相关的Service
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
				
				if(cmd.equals("WorkorderAll")){  //根据提交的方法执行相应的操作，获取工单分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
		            String keywords = json.getString("keywords")==null?"":URLDecoder.decode( json.getString("keywords"),"utf-8");				//关键词检索条件
		    		if(null != keywords && !"".equals(keywords)){
		    			pd.put("keywords", keywords.trim());
		    		}	
		    		String doaction = json.getString("doaction")==null?"":json.getString("doaction");
		    		if(doaction.equals("1")){
		    			pd.put("czman",pd_token.getString("ZXYH"));
		    		}
		    		
		    		
		    		
		    		if(null != json.getString("code") ){
		    			pd.put("code", json.getString("code"));
		    		}
		    		pd.put("starttime", json.getString("starttime"));
					if(json.getString("endtime")!=null&& !json.getString("endtime").equals("")){
						pd.put("endtime",Tools.getEndTime(json.getString("endtime"), 1));
					}
		    		if(null != json.getString("tssource") ){
		    			pd.put("tssource", json.getString("tssource"));
		    		}	
		    		if(null != json.getString("tsman") ){
		    			pd.put("tsman", json.getString("tsman"));
		    		}	
		    		if(null != json.getString("tsdept") ){
		    			pd.put("tsdept", json.getString("tsdept"));
		    		}	
		    		
		    		if(null != json.getString("type") ){
		    			pd.put("types", json.getString("type"));
		    		}	
		    		
		    		if(null != json.getString("tstype") ){
		    			pd.put("tstype", json.getString("tstype"));
		    		}
		    		if(null != json.getString("bigtstype") ){
		    			pd.put("bigtstype", json.getString("bigtstype"));
		    		}
		    		
		    		if(null != json.getString("endreason") ){
		    			pd.put("endreason", json.getString("endreason"));
		    		}	
		    		
		    		if(pd_token!=null){
		    			String dwbm= pd_token.getString("dept");
		    			if(dwbm.equals("350101")){
		    				dwbm="3501";
		    			}
		    			pd.put("dwbm", dwbm);
		    			
		    		}
		    		pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		    		list = workorderService.list(page);	//列出doc列表
		            
					if(list.size()>0){
						for(PageData pddoc:list){  
							//System.out.println(list);
							if(pddoc.get("tsdate")!=null){
								pddoc.put("tsdate", String.valueOf(pddoc.get("tsdate")).substring(0, 19));
							}
							if(pddoc.get("czdate")!=null){
								pddoc.put("czdate", String.valueOf(pddoc.get("czdate")).substring(0, 19));
							}
							if(pddoc.get("cldate")!=null){
								pddoc.put("cldate", String.valueOf(pddoc.get("cldate")).substring(0, 19));
							}
							if(pddoc.get("cjdate")!=null){
								//pddoc.put("cjdate", String.valueOf(pddoc.get("cjdate")).substring(0, 10));
							}
							if(pddoc.get("endtime")!=null){
								pddoc.put("endtime", String.valueOf(pddoc.get("endtime")).substring(0, 19));
							}
						}
						object.put("success","true");
						object.put("data",list);
						object.put("pageId",pageIndex);
						object.put("pageCount",page.getTotalPage());
						object.put("recordCount",page.getTotalResult());
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if(cmd.equals("WorkorderAdd")) {
				
					pd.put("workid", this.get32UUID());
					pd.put("id",json.getString("id"));
					pd.put("code",json.getString("code"));
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
					pd.put("clsx",json.getString("clsx"));
					pd.put("uid", json.getString("uid"));	//随机生成
					pd.put("type", "0");
					pd.put("czman",pd_token.getString("ID"));
					PageData pd_workorder =null;
					if(pd!=null&&pd.getString("id")!=null&&!pd.getString("id").equals("")){
						pd_workorder = workorderService.findById(pd);
					}
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
					
					pd.put("code", year+file_code);
					pd.put("czman",  pd_token.getString("ZXYH"));
					
					String pid ="";
					if(request.getParameter("id")!=null&&!request.getParameter("id").equals("")){
						workorderService.edit(pd);
					}else{
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
						pd.put("dealman",  pd_token.getString("zxyh"));
						pd.put("cljd",  pd_token.getString("dept"));
						pd.put("type", "4");
						workorderService.editCL(pd);
					}else{
						if(json.getString("doaction")!=null&&json.getString("doaction").equals("1")){
							//发起流程
							if(pd_workorder==null||(pd_workorder!=null&&pd_workorder.getString("pro_id")==null)){
								Map<String,Object> map = new LinkedHashMap<String, Object>();
								map.put("发起流程人",  pd_token.getString("zxyh"));			//当前用户的姓名
								map.put("投诉人",json.getString("tsman"));
								map.put("投诉人身份证号码", pd.getString("cardid"));
								map.put("投诉内容", pd.getString("tscont"));
								map.put("创建时间", Tools.date2Str(new Date()));
								//
								PageData pd_doc = new PageData();

								pd_doc.put("ROLE_NAME", "工单专员");
								List<PageData> roleList=userService.getUserByRoleName(pd_doc);
								String userstr="";
								for(PageData pd_gd:roleList){
									if(userstr!=""){
										userstr=userstr+",";
									}
									userstr=userstr+pd_gd.getString("USERNAME");
									
								}
								map.put("USERNAME", userstr);
								String key="gdsp";
								
								pid = startProcessInstanceByKeyHasVariables(key,map);	//启动流程实例通过KEY
								pd.put("proc_id", pid);
								if( pd_token.getString("dept")!=null&& pd_token.getString("dept").equals("350101")){
									object.put("data",pid);
								}
								pd.put("proc_id", pid);
								if(pd_workorder==null){
									pd.put("workid", pd.getString("workid"));
								}else{
									pd.put("workid", pd_workorder.getString("workid"));
								}
								
								workorderService.editWorkproc(pd);
							}
						}
					}
			        object.put("success","true");
				    object.put("msg","保存成功");
				}else if(cmd.equals("WorkorderEdit")) {
				
					//pd.put("workid", this.get32UUID());
					pd.put("id",json.getString("id"));
					
					pd.put("tsdate",json.getString("tsdate"));
					pd.put("tssource",json.getString("tssource"));
					pd.put("tsman",json.getString("tsman"));
					pd.put("tstel",json.getString("tstel"));
					pd.put("tscont",json.getString("tscont"));
					pd.put("tslevel",json.getString("tslevel"));
					pd.put("tsdept",json.getString("tsdept"));
					pd.put("tstype",json.getString("tstype"));
					
				
					pd.put("tsclassify",json.getString("tsclassify"));
					pd.put("ishf",json.getString("ishf"));
					pd.put("endreason",json.getString("endreason"));
					pd.put("type",json.getString("type"));
					pd.put("cljd",json.getString("cljd"));
					pd.put("cardid",json.getString("cardid"));
					pd.put("cjdate",json.getString("cjdate"));
					pd.put("hbh",json.getString("hbh"));
					pd.put("clsx",json.getString("clsx"));
					pd.put("uid", json.getString("uid"));	//主键
					pd.put("type", "0");
					
					PageData pd_workorder =null;
					if(json.getString("id")!=null&&!json.getString("id").equals("")){
						pd_workorder = workorderService.findById(pd);
					}
					
					pd.put("czman",  pd_token.getString("ZXYH"));
					
					String pid ="";
					
					if(pd_workorder==null){
						 object.put("success","false");
						 object.put("msg","要修改的信息不存在");
					}else{
						workorderService.edit(pd);
						pd_workorder = workorderService.findById(pd);
						if(json.getString("endreason")!=null&&!json.getString("endreason").equals("")){
							pd.put("dealman",  Jurisdiction.getUsername());
							pd.put("cljd",  pd_token.getString("dept"));
							pd.put("type", "4");
							//System.out.println( user.getDWBM()+"bm");
							workorderService.editCL(pd);
							
						}else{
		
							if(pd_workorder!=null&&pd_workorder.getString("pro_id")==null&&json.getString("doaction")!=null&&json.getString("doaction").equals("1")){
								Map<String,Object> map = new LinkedHashMap<String, Object>();
								map.put("发起流程人", Jurisdiction.getUsername());			//当前用户的姓名
								map.put("投诉人", json.getString("tsman"));
								map.put("投诉人身份证号码", json.getString("cardid"));
								map.put("投诉内容", json.getString("tscont"));
								map.put("创建时间", Tools.date2Str(new Date()));
								map.put("USERNAME", Jurisdiction.getUsername());
								String key="gdsp";
								pid = startProcessInstanceByKeyHasVariables(key,map);	//启动流程实例(公文-收文流程)通过KEY
								//System.out.println(pid+"pid");
								pd.put("proc_id", pid);
								pd.put("workid", pd_workorder.getString("workid"));
								workorderService.editWorkproc(pd);
							}
							
						}
				        object.put("success","true");
					    object.put("msg","保存成功");
					}    
				}else if(cmd.equals("WorkorderDel")) {
			        pd.put("id",json.get("id"));
			    	String DATA_IDS =String.valueOf(json.get("id"));
					String ArrayDATA_IDS[] = DATA_IDS.split(",");
					workorderService.deleteAll(ArrayDATA_IDS);
		        	//docService.delete(pd);
		        	object.put("success","true");
				    object.put("msg","删除成功");
			       
				}else if(cmd.equals("WorkorderFindById")) {
			        pd.put("id",json.get("id"));
			        PageData pdDoc=workorderService.findById(pd);
			        List<JSONObject> clList=new ArrayList();
			        if(pdDoc!=null&&pdDoc.get("id")!=null){
						object.put("success","true");
						pdDoc.put("tsdate", String.valueOf(pdDoc.get("tsdate")).substring(0, 19));
						if(pdDoc.get("czdate")!=null){
							pdDoc.put("czdate", String.valueOf(pdDoc.get("czdate")).substring(0, 19));
							
						}
						if(pdDoc.get("cjdate")!=null){
							//pdDoc.put("cjdate", String.valueOf(pdDoc.get("cjdate")).substring(0, 10));
						}
						if(pdDoc!=null&&pdDoc.getString("proc_id")!=null){
							/*
							 * 获取审批信息
							 */
						    String[] arrcl=null;
						    JSONObject pdcl=new JSONObject();
							PageData pduser=new PageData();
							pd.put("PROC_INST_ID_", pdDoc.getString("proc_id"));
							List<PageData>	varList = ruprocdefService.varList(pd);	
							String lasttime="";
							for(int i=0;i<varList.size();i++){	
								pduser=new PageData();
								String clcont="";
								if(varList.get(i).getString("TEXT_")!=null&&varList.get(i).getString("TEXT_").indexOf(",huangfege,")>=0){
									pdcl=new JSONObject();
									arrcl=varList.get(i).getString("TEXT_").split(",huangfege,");
									pdcl.put("clman", arrcl[0]);
									pdcl.put("USERNAME", arrcl[0]);
									
									clcont=(arrcl[1]==null?"":arrcl[1]);
									if(clcont.equals("null")){
										clcont="";
									}
									pdcl.put("clcont", clcont);
									pdcl.put("cldate", String.valueOf(varList.get(i).get("LAST_UPDATED_TIME_")==null?"":varList.get(i).get("LAST_UPDATED_TIME_")).substring(0, 10));
									pduser.put("clman", arrcl[0]);
									pduser.put("USERNAME", arrcl[0]);
									pduser.put("USER_ID", "");
									pduser=userService.getUserInfoByUsername(pduser);
									if(pduser!=null){
										pdcl.put("areaname", pduser.getString("areaname"));
										pdcl.put("name", pduser.getString("NAME"));
										
									}
									clList.add(pdcl);
									
									lasttime=String.valueOf(varList.get(i).get("LAST_UPDATED_TIME_"));
								}
							}
							pdDoc.put("clList", clList.toString());
						}
						
						object.put("data",pdDoc);
					}
				}else if(cmd.equals("WorkorderDb")){  //待办根据提交的方法执行相应的操作，获取工单分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
		            String keywords = json.getString("keywords")==null?"":URLDecoder.decode( json.getString("keywords"),"utf-8");;				//关键词检索条件
		    		if(null != keywords && !"".equals(keywords)){
		    			pd.put("keywords", keywords.trim());
		    		}	
		    		
		    		if(null != json.getString("code") ){
		    			pd.put("code", json.getString("code"));
		    		}
		    		pd.put("starttime", json.getString("starttime"));
					if(json.getString("endtime")!=null&& !json.getString("endtime").equals("")){
						pd.put("endtime",Tools.getEndTime(json.getString("endtime"), 1));
					}
		    		if(null != json.getString("tssource") ){
		    			pd.put("tssource", json.getString("tssource"));
		    		}	
		    		if(null != json.getString("tsman") ){
		    			pd.put("tsman", json.getString("tsman"));
		    		}	
		    		if(null != json.getString("tsdept") ){
		    			pd.put("tsdept", json.getString("tsdept"));
		    		}	
		    		
		    		if(null != json.getString("type") ){
		    			pd.put("types", json.getString("type"));
		    		}	
		    		
		    		if(null != json.getString("tstype") ){
		    			pd.put("tstype", json.getString("tstype"));
		    		}
		    		if(null != json.getString("bigtstype") ){
		    			pd.put("bigtstype", json.getString("bigtstype"));
		    		}
		    		
		    		if(null != json.getString("endreason") ){
		    			pd.put("endreason", json.getString("endreason"));
		    		}	
		    		
		    		if(pd_token!=null){
		    			String dwbm= pd_token.getString("dept");
		    			String ZXYH= pd_token.getString("ZXYH");
		    			if(dwbm.equals("350101")){
		    				dwbm="3501";
		    			}
		    			pd.put("dwbm", dwbm);
		    			//pd.put("USERNAME", ZXYH);
		    			
		    		}
		    		pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		    		list = ruprocdefService.list(page);	//列出Rutask列表
		            
					if(list.size()>0){
						for(PageData pddoc:list){  
							pddoc.put("tsdate", String.valueOf(pddoc.get("tsdate")).substring(0, 19));
							if(pddoc.get("czdate")!=null){
								pddoc.put("czdate", String.valueOf(pddoc.get("czdate")).substring(0, 19));
							}
							if(pddoc.get("cldate")!=null){
								pddoc.put("cldate", String.valueOf(pddoc.get("cldate")).substring(0, 19));
							}
							if(pddoc.get("endtime")!=null){
								pddoc.put("endtime", String.valueOf(pddoc.get("endtime")).substring(0, 19));
							}
							if(pddoc.get("CREATE_TIME_")!=null){
								pddoc.put("CREATE_TIME_", String.valueOf(pddoc.get("CREATE_TIME_")).substring(0, 19));
							}
						}
						object.put("success","true");
						object.put("data",list);
						object.put("pageId",pageIndex);
						object.put("pageCount",page.getTotalPage());
						object.put("recordCount",page.getTotalResult());
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if(cmd.equals("WorkorderSh")){  //根据提交的方法执行相应的操作，获取工单分页信息 
					pd.put("proc_id", json.getString("PROC_INST_ID_"));
					PageData pd_s =workorderService.findById(pd);
					String taskId ="";
					if(json.getString("doaction")!=null&&json.getString("doaction").equals("azb")){
						taskId = ruprocdefService.getTaskID(json.getString("PROC_INST_ID_"));	//任务ID
						json.put("OPINION","派发工单");
					}else{
						taskId = json.getString("ID_");	//任务ID
						pd_s.put("cfbm", pd.getString("cfbm"));
						workorderService.editCfbm(pd_s);
					}
					
					Map<String,Object> map = new LinkedHashMap<String, Object>();
					String OPINION =   pd_token.getString("ZXYH") + ",huangfege,"+json.getString("OPINION");//审批人的姓名+审批意见
					String msg = json.getString("msg");
					String newtsdept=pd.getString("tsdept")==null?"":pd.getString("tsdept");
					pd_s.put("newtsdept", newtsdept);
					//String[] dept=(pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept")).split(",");
					//System.out.println("tsdept:"+pd_s.getString("tsdept"));
					List<Task> tasks = taskService.createTaskQuery().taskName("多部门工单处理")
			                .processInstanceId(json.getString("PROC_INST_ID_")).list();
					//System.out.println(tasks);
					long min=System.currentTimeMillis();
					Task task=taskService.createTaskQuery() // 创建任务查询
			                .taskId(taskId) // 根据任务id查询
			                .singleResult();
					String taskname=task.getName()==null?"":task.getName();
			        // 如果是会签流程
					boolean boo=false;
			        if (taskname.equals("工单提交"))
			        {
			        	
			        	String tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
			        	String[] dept=tsdept.split(",");
			        	map.put("提交人", pd_token.getString("zxyh"));		//审批结果
			        	map.put("提交内容",OPINION);		//审批结果
			        	if(dept.length==1){								//批准
			        		map.put("BMZR","yes");
			        		
						}else{												//驳回
							
							map.put("BMZR","no");
						}
			        	map.put("depts",pd_s.getString("tsdept"));
			        	
			        	
			        	taskService.complete(taskId, map); //设置会签
			        	pd.put("type", "3");
			        }else if (taskname.equals("单部门工单处理")){
			        	String tslevel=pd_s.getString("tslevel")==null?"":pd_s.getString("tslevel");
			        	String tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");

			        	map.put("单部门处理人", pd_token.getString("zxyh"));		//审批结果
			        	map.put("单部门处理内容",OPINION);		//审批结果
			        	//pd.put("tsdept", tsdept);
			        	pd.put("ROLE_NAME", "部门领导");
						List<PageData> roleList=userService.getUserByRoleName(pd);
						String userstr="";
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
			        	pd.put("tsdept", "350101");
			        	pd.put("ROLE_NAME", "工单专员");
						List<PageData> roleList=userService.getUserByRoleName(pd);
						String userstr="";
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
			        	map.put("部门领导姓名", pd_token.getString("zxyh"));		//审批结果
			        	map.put("部门领导-审批内容",OPINION);		//审批结果
			        	taskService.complete(taskId, map); //设置会签
			        	pd.put("type", "3");
			        }else if (taskname.equals("单部门领导审批")){
			        	
			        	
			        	String tsdept=pd_s.getString("tsdept")==null?"":pd_s.getString("tsdept");
			        	pd.put("tsdept", tsdept);
			        	pd.put("ROLE_NAME", "工单专员");
						List<PageData> roleList=userService.getUserByRoleName(pd);
						String userstr="";
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
								pd_s.put("proc_id", pd.getString("PROC_INST_ID_"));
								pd_s.put("iscl", "1");
								ruprocdefService.delDealDept(pd_s);
				        	}
							map.put("DBMMSG","no");
						}	
			        	map.put("单部门-部门领导姓名", pd_token.getString("zxyh"));		//审批结果
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
			            	pd.put("dealman",   pd_token.getString("zxyh"));
			    			pd.put("cljd",  pd_token.getString("dept"));
			    			pd.put("type", "4");
			    			workorderService.editCL(pd);
			    			
			    			if(pd_s!=null&&pd_s.getString("caseCode")!=null&&!pd_s.getString("caseCode").equals("")){
			    				PageData pd_casecode = workorderService.findDbByCasecode(pd_s);
			    				if(pd_casecode!=null){
			    					Date date=new Date();
			    					long timestamp=date.getTime(); 
			    					workorderService.sendAuditFinish(pd_s.getString("caseCode"), pd.getString("endreason"), pd_casecode.getString("postid"), pd.getString("endreason"), pd_token.getString("zxyh"), "", "", String.valueOf(timestamp), "1");	
			    				}
			    			}
			    			//taskService.complete(taskId, map); //设置会签
			    			
			    			boo=true;
						}else{		
							if(dept.length==1){
			        			map.put("RESULT","no-single"); //单部门审批不通过
			        		}else{
			        			map.put("RESULT","no-mul"); //多部门审批不通过
			        		}
						}	
			        	map.put("工单专员", pd_token.getString("zxyh"));		//审批结果
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
							
							pd_s.put("czman",  pd_token.getString("zxyh"));
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
				    			String userstr="";
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
			         	pd.put("dealman",   pd_token.getString("zxyh"));
			     		pd.put("cljd",  pd_token.getString("dept"));
			     		workorderService.editCLByuid(pd);
			        }
			        object.put("success","true");
			        object.put("msg","审核成功");
				}else if(cmd.equals("WorkorderFilelist")) {
			        pd.put("uid",json.get("uid"));
			        List<PageData> fileList=workorderService.findFileByuid(pd);
			        for(PageData pdServerLog:fileList){  
						pdServerLog.put("createdate", String.valueOf(pdServerLog.get("createdate")).substring(0, 19));
					}
					List<JSONObject> objlist=new ArrayList();
					if(fileList.size()>0){
			        	object.put("success","true");
					    object.put("data",fileList);
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
			       
				}else if(cmd.equals("WorkorderFileDel")) {
					pd.put("id",json.get("id"));
					workorderService.deleteFile(pd);
		        	object.put("success","true");
				    object.put("msg","删除成功");
				}else{
					object.put("success","false");
			        object.put("msg","暂无数据");
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
	
	
}
