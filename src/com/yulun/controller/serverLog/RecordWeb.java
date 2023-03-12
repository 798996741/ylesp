package com.yulun.controller.serverLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.yulun.service.BlacklistManager;
import com.yulun.service.RecordManager;
import com.yulun.service.ServerLogManager;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;

/**
 * 说明：我的服务记录-录音
 * 创建人：351412933
 * 创建时间：2019-12-27
 */
public class RecordWeb implements CommonIntefate {


	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;

	@Resource(name="recordService")
	private RecordManager recordService;
	
	@Resource(name="blacklistService")
	private BlacklistManager blacklistService;
	
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
				if(cmd.equals("RecordAll")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			      
					pd.put("phone", json.getString("phone")); 
					pd.put("zxid", json.getString("zxid")); 
					pd.put("thfx", json.getString("thfx")); 
					pd.put("starttime", json.getString("starttime"));
					if(json.getString("endtime")!=null&& !json.getString("endtime").equals("")){
						pd.put("endtime",Tools.getEndTime(json.getString("endtime"), 1));
					}
					
		            pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		            list =recordService.listPage(page);
		           // System.out.println(list);
					if(list.size()>0){
						pd_token.put("param_code", "recordsvr");
						PageData pdparam=blacklistService.findSysparam(pd_token);
					
						for(PageData pdServerLog:list){  
							if(pdServerLog!=null){
								if(pdServerLog.get("lysj")!=null&&String.valueOf(pdServerLog.get("lysj")).length()>10){
									pdServerLog.put("lysj", String.valueOf(pdServerLog.get("lysj")).substring(0, 19));
								}
								if(pdServerLog!=null&&pdServerLog.getString("lywj")!=null){
									pdServerLog.put("lywjurl", pdparam.getString("param_value")+String.valueOf(pdServerLog.get("lysj")).substring(0, 10)+"/"+pdServerLog.getString("lywj"));
								}
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
				}else if(cmd.equals("ComPersonRecord")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			        pd.put("type", json.getString("type")); 
			        pd.put("ComPersonRecord","1"); 
			        pd.put("keywords", json.getString("keywords")); 
					pd.put("phone", json.getString("phone")); 
					pd.put("zxid", json.getString("zxid")); 
					pd.put("thfx", json.getString("thfx")); 
					pd.put("starttime", json.getString("startTime"));
					pd.put("endtime", json.getString("endTime"));
//					if(json.getString("endTime")!=null&& !json.getString("endTime").equals("")){
//						pd.put("endtime",Tools.getEndTime(json.getString("endTime"), 1));
//					}
					
		            pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		            list =recordService.listPage(page);
		           // System.out.println(list);
					if(list.size()>0){
						pd_token.put("param_code", "recordsvr");
						PageData pdparam=blacklistService.findSysparam(pd_token);
					
						for(PageData pdServerLog:list){  
							if(pdServerLog!=null){
								if(pdServerLog.get("lysj")!=null&&String.valueOf(pdServerLog.get("lysj")).length()>10){
									pdServerLog.put("lysj", String.valueOf(pdServerLog.get("lysj")).substring(0, 19));
								}
								if(pdServerLog!=null&&pdServerLog.getString("lywj")!=null){
									pdServerLog.put("lywjurl", pdparam.getString("param_value")+String.valueOf(pdServerLog.get("lysj")).substring(0, 10)+"/"+pdServerLog.getString("lywj"));
								}
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
				}else if(cmd.equals("RecordPf")) {
					pd.put("pfman",pd_token.getString("ID"));
			        pd.put("score",json.get("score"));
			        pd.put("pfremark",json.get("pfremark"));
			        pd.put("id",json.get("id"));
			        recordService.pf(pd);
			        object.put("success","true");
				    object.put("msg","评分成功");
				}else if(cmd.equals("RecordDel")) {
					String DATA_IDS = (String) json.get("id");
					String ArrayDATA_IDS[] = DATA_IDS.split(",");
					recordService.deleteAll(ArrayDATA_IDS);
		        	object.put("success","true");
				    object.put("msg","删除成功");
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
	
	
}

