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
import com.fh.controller.activiti.rutask.RuTaskController;
import com.fh.entity.Page;
import com.fh.entity.system.User;
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
public class WorkorderWeb extends AcStartController  implements CommonIntefate {
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
			String username=(json.getString("username")==null?"":json.getString("username"));
			if(!username.equals("")){
				pd_stoken.put("USERNAME", username);
				pd_token=userService.findByUsername(pd_stoken);
				if(pd_token!=null){
					pd_token.put("dept", pd_token.getString("DWBM"));
					pd_token.put("ZXYH", username);
				}
			}
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
		    		String[] arrcl=null;
					JSONObject pdcl=new JSONObject();
					PageData pduser=new PageData();
					List<PageData>	varList =null;
					List<JSONObject> clList=new ArrayList();
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
							
							if(pddoc!=null&&pddoc.getString("proc_id")!=null){
								/*
								 * 获取审批信息
								 */
							    pdcl=new JSONObject();
								pduser=new PageData();
								pd.put("PROC_INST_ID_", pddoc.getString("proc_id"));
								varList = ruprocdefService.varList(pd);	
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
										
									}
								}
								pddoc.put("clList", clList.toString());
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
			        pd.put("proc_id",json.get("proc_id"));
			        PageData pdDoc=workorderService.findById(pd);
			      //  System.out.println(pdDoc.get("proc_id")+"proc_idd");
			       // String taskId = ruprocdefService.getTaskID(String.valueOf(pdDoc.get("proc_id")));	//任务ID	//任务ID
			        String taskname="";
			       /* if(taskId!=null){
			        	Task task=taskService.createTaskQuery() // 创建任务查询
				                .taskId(taskId) // 根据任务id查询
				                .singleResult();
						taskname=task.getName()==null?"":task.getName();	
			        }*/
			        
			        List<JSONObject> clList=new ArrayList();
			        if(pdDoc!=null&&pdDoc.get("id")!=null){
						object.put("success","true");
						pdDoc.put("tsdate", String.valueOf(pdDoc.get("tsdate")).substring(0, 10));
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
									System.out.println(varList.get(i).getString("TEXT_")+"test_");
									arrcl=varList.get(i).getString("TEXT_").split(",huangfege,");
									pdcl.put("clman", arrcl[0]);
									pdcl.put("USERNAME", arrcl[0]);
									
									clcont=(arrcl[1]==null?"":arrcl[1]);
									if(clcont.equals("null")){
										clcont="";
									}
									pdcl.put("clcont", clcont);
									pdcl.put("cldate", String.valueOf(varList.get(i).get("LAST_UPDATED_TIME_")==null?"":varList.get(i).get("LAST_UPDATED_TIME_")).substring(0, 19).replace(" ", "<br/>"));
									pduser.put("clman", arrcl[0]);
									pduser.put("USERNAME", arrcl[0]);
									pduser.put("USER_ID", "");
									pduser=userService.getUserInfoByUsername(pduser);
									if(pduser!=null){
										pdcl.put("areaname", pduser.getString("areaname"));
										pdcl.put("name", pduser.getString("NAME"));
										
									}
									clList.add(pdcl);
									
								}
							}
							
							pdDoc.put("clList", clList.toString());
						}
						System.out.println(pdDoc.toString());
						object.put("data",pdDoc);
						object.put("taskname",taskname);
					}
				}else if(cmd.equals("WorkorderDb")){  //待办根据提交的方法执行相应的操作，获取工单分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			        List<PageData> dblist =new ArrayList();
			        List<PageData> alllist=new ArrayList();
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
		    			
		    			
		    		}
		    		pd_token.put("USERNAME", pd_token.getString("ZXYH"));
		    		PageData user=new PageData();
		    		if(!username.equals("")){
		    			user=pd_token;
		    		}else{
		    			user=userService.getUserInfoByUsername(pd_token);
		    		}
		    		String role_id=user.getString("ROLE_ID");
		    		PageData pd_js = new PageData();
		    		pd_js.put("ROLE_ID", role_id);
		    		PageData role=roleService.findObjectById(pd_js);
		    		
		    		if(role!=null&&(role.getString("ROLE_NAME")!=null&&(role.getString("ROLE_NAME").equals("部门处理人员")))){
		    			if(user.getString("dwbh")!=null){
		    				pd.put("DWBM", user.getString("dwbh")); 	
		    			}else{
		    				pd.put("DWBM", user.getString("DWBM")); 	
		    			}
		    			//||(user.getDWBM().equals("350101")&&role.getString("ROLE_NAME").equals("工单专员"))
		    		}else{
		    			pd.put("USERNAME", pd_token.getString("ZXYH")); 		//查询当前用户的任务(用户名查询)
		    		}
		    		
		    		if((pd_token.getString("dept").equals("350101")&&role.getString("ROLE_NAME").equals("工单专员"))){
		    			pd.put("DWBM", pd_token.getString("dept")); 
		    			pd.put("USERNAME", username);
		    			pd.put("isgdzy","1");
		    		}else{
		    			pd.put("isgdzy","0");
		    		}
		    		String islx="cl";
		    		if(role.getString("ROLE_NAME").equals("部门领导")){
		    			pd.put("DWBM1", pd_token.getString("dept")); 
		    			islx="sh";
		    		}
		    		
		    		
		    		if(role.getString("ROLE_NAME").equals("部门处理人员")){
		    			pd.put("DWBM2", pd_token.getString("dept")); 
		    		}
		    		
		    		pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		           
		            
		            if(pd!=null&&pd.getString("isgdzy")!=null&&pd.getString("isgdzy").equals("1")){ //安质部的工单专员
		            	pd.put("dpf", "1");
		            	
		    			pd.put("iszx","0");
		    			page.setPd(pd);
		            	dblist=workorderService.list(page);	//列出doc列表
		            }else{
		            	page.setPd(pd);
		            }
		            
		    		list = ruprocdefService.listPage(page);	//列出Rutask列表
		            
                	PageData dbpd=new PageData();
					if(list.size()>0||(dblist!=null&&dblist.size()>0)){
						
						for(PageData pddoc:dblist){  
							pddoc.put("tsdate", String.valueOf(pddoc.get("tsdate")).substring(0, 10));
							dbpd=new PageData();
							dbpd.put("tsdate", pddoc.getString("tsdate"));
							dbpd.put("tssourcename", pddoc.getString("tssourcename"));
							dbpd.put("tslevelname", pddoc.getString("tslevelname"));
							dbpd.put("clsx", pddoc.getString("clsx"));
							dbpd.put("proc_id", pddoc.getString("proc_id"));
							dbpd.put("id", pddoc.get("id"));
							dbpd.put("uid", pddoc.getString("uid"));
							dbpd.put("islx", "dpf");
							alllist.add(dbpd);
						}
						
						for(PageData pddoc:list){  
							if((pd_token.getString("dept").equals("350101")&&role.getString("ROLE_NAME").equals("工单专员"))){
								if(pddoc!=null&&pddoc.getString("NAME_").equals("多部门工单处理")){
									PageData db_deal=new PageData();
									db_deal.put("dept", "350101");
									db_deal.put("proc_id", pddoc.getString("proc_id"));
									db_deal.put("iscl", "1");
									db_deal.put("isdel", "0");
			    					PageData pd_deptdeal_s =ruprocdefService.getDealByDept(db_deal);
			    					if(pd_deptdeal_s==null||pd_deptdeal_s.get("id")==null){
			    						pddoc.put("tsdate", String.valueOf(pddoc.get("tsdate")).substring(0, 10));
										dbpd=new PageData();
										dbpd.put("tsdate", pddoc.getString("tsdate"));
										dbpd.put("tssourcename", pddoc.getString("tssourcename"));
										dbpd.put("tslevelname", pddoc.getString("tslevelname"));
										dbpd.put("clsx", pddoc.getString("clsx"));
										dbpd.put("proc_id", pddoc.getString("proc_id"));
										dbpd.put("id", pddoc.getString("id"));
										dbpd.put("uid", pddoc.getString("uid"));
										dbpd.put("ID_", pddoc.getString("ID_"));
										dbpd.put("islx", islx);
										alllist.add(dbpd);
			    					}
									
								}else{
									pddoc.put("tsdate", String.valueOf(pddoc.get("tsdate")).substring(0, 10));
									dbpd=new PageData();
									dbpd.put("tsdate", pddoc.getString("tsdate"));
									dbpd.put("tssourcename", pddoc.getString("tssourcename"));
									dbpd.put("tslevelname", pddoc.getString("tslevelname"));
									dbpd.put("clsx", pddoc.getString("clsx"));
									dbpd.put("proc_id", pddoc.getString("proc_id"));
									dbpd.put("id", pddoc.getString("id"));
									dbpd.put("uid", pddoc.getString("uid"));
									dbpd.put("ID_", pddoc.getString("ID_"));
									dbpd.put("islx", islx);
									alllist.add(dbpd);
								}
							}else{
								pddoc.put("tsdate", String.valueOf(pddoc.get("tsdate")).substring(0, 10));
								dbpd=new PageData();
								dbpd.put("tsdate", pddoc.getString("tsdate"));
								dbpd.put("tssourcename", pddoc.getString("tssourcename"));
								dbpd.put("tslevelname", pddoc.getString("tslevelname"));
								dbpd.put("clsx", pddoc.getString("clsx"));
								dbpd.put("proc_id", pddoc.getString("proc_id"));
								dbpd.put("id", pddoc.getString("id"));
								dbpd.put("uid", pddoc.getString("uid"));
								dbpd.put("ID_", pddoc.getString("ID_"));
								dbpd.put("islx", islx);
								alllist.add(dbpd);
							}
						}
						System.out.println(list.toString());
						object.put("success","true");
						object.put("data",alllist);
						object.put("pageId",pageIndex);
						object.put("pageCount",page.getTotalPage());
						object.put("recordCount",page.getTotalResult());
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if(cmd.equals("WorkorderAzbDb")){  //待办根据提交的方法执行相应的操作，获取工单分页信息 
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
		    		if(null != json.getString("iszx") ){
		    			pd.put("iszx", json.getString("iszx"));
		    		}
		    		if(pd_token!=null){
		    			String dwbm= pd_token.getString("dept");
		    			String ZXYH= pd_token.getString("ZXYH");
		    			if(dwbm.equals("350101")){
		    				dwbm="3501";
		    			}
		    			pd.put("userdwbm", dwbm);
		    			
		    			
		    		}
		    		pd.put("type", "all");
		    		pd.put("dpf", "1");
		    		pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		    		list = workorderService.list(page);	//所有待办
		            
					if(list.size()>0){
						for(PageData pddoc:list){  
							pddoc.put("tsdate", String.valueOf(pddoc.get("tsdate")).substring(0, 19));
							if(pddoc.get("czdate")!=null){
								pddoc.put("czdate", String.valueOf(pddoc.get("czdate")).substring(0, 19));
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
					String sfrom="";
					
					
					Map<String,Object> map = new LinkedHashMap<String, Object>();
				
					String newtsdept=json.getString("tsdept")==null?"":json.getString("tsdept");
				
					
					String PROC_INST_ID_=json.getString("PROC_INST_ID_");
					//String id=pd.getString("id");
					String ID_=json.getString("ID_")==null?"":json.getString("ID_");
					String dwbm=pd_token.getString("dept");
					String userid=pd_token.getString("ZXYH");
					String cfbm=json.getString("cfbm")==null?"":json.getString("cfbm");
					String doaction=json.getString("doaction")==null?"":json.getString("doaction");
					String msg=json.getString("msg")==null?"":json.getString("msg");
					String OPINION=json.getString("OPINION")==null?"派发工单":json.getString("OPINION");
					this.deal("",PROC_INST_ID_, ID_, dwbm, userid, cfbm, doaction, msg, newtsdept, OPINION);
					
					
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
	


	public String deal(String id,String PROC_INST_ID_,String ID_,String dwbm,String userid,String cfbm,String doaction,String msg,String tsdept,String OPINION) throws Exception{
		PageData pd = new PageData();
		String taskId ="";
		
		pd.put("proc_id", PROC_INST_ID_);
		pd.put("id", id);
		pd.put("code", "");
		PageData pd_s =workorderService.findById(pd);
		//System.out.println("pd_s:"+pd_s);
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

        	map.put("单部门处理人",userid);		//审批结果
        	map.put(min+"单部门处理内容",OPINION);		//审批结果
        	pd.put("tsdept", tsdept);
        	nextdept=tsdept;
        	
        	
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
	            	
	            	if(tslevel.equals("e1337c48f17042f39ec88b63e3d7def2")){								//批准
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
		    			//userstr="350101";
		            	map.put("LEADER",userstr);		//审批结果果
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
