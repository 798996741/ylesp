package com.yulun.controller.esppoponscreen;

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
import com.yulun.service.CompanyManager;
import com.yulun.service.JobregManager;
import com.yulun.service.PersonManager;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;

/**
 * 说明：通来电弹屏
 * 创建人：351412933
 * 创建时间：2020-04-08
 */
public class JobregWeb implements CommonIntefate {


	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;

	@Resource(name="jobregService")
	private JobregManager jobregService;
	
	@Resource(name="companyService")
	private CompanyManager companyService;
	
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
				if(cmd.equals("jobregAll")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			      
					pd.put("uid", json.getString("uid")); 
					
					pd.put("starttime", json.getString("starttime"));
					if(json.getString("endtime")!=null&& !json.getString("endtime").equals("")){
						pd.put("endtime",Tools.getEndTime(json.getString("endtime"), 1));
					}
					
		            pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		            list =jobregService.listPage(page);
		           // System.out.println(list);
					if(list.size()>0){
						object.put("success","true");
						object.put("data",list);
						object.put("pageId",pageIndex);
						object.put("pageCount",page.getTotalPage());
						object.put("recordCount",page.getTotalResult());
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if(cmd.equals("jobregDel")) {
				    pd.put("id",json.get("id"));
				    pd.put("uid",json.get("uid"));
			        pd.put("ucid",json.get("ucid"));
					companyService.saveRecord(pd);
					jobregService.deleteAll(pd);
		        	object.put("success","true");
				    object.put("msg","删除成功");
				}else if(cmd.equals("jobregAdd")||cmd.equals("jobregEdit")) {
				
					pd.put("czman",pd_token.getString("ZXYH"));
					pd.put("id",json.get("id"));
					pd.put("uid",json.get("uid"));
				    pd.put("ucid",json.get("ucid"));
					companyService.saveRecord(pd);
					
					pd.put("jobname",json.get("jobname"));
			        pd.put("jobtype",json.get("jobtype"));
			        
			        pd.put("qwxz",json.get("qwxz"));
			        pd.put("jobaddr",json.get("jobaddr"));
			        pd.put("specificjobname",json.get("specificjobname"));
			        pd.put("experience",json.get("experience"));

			        pd.put("remark",json.get("remark"));
			       
					PageData pd_jobreg=jobregService.findByid(pd);
					if(pd_jobreg!=null&&pd_jobreg.getString("uid")!=null){
						jobregService.edit(pd);
					}else{
						jobregService.save(pd);
						
					}
			        object.put("success","true");
				    object.put("msg","保存成功");
				}else if(cmd.equals("jobregFindById")) {
			        pd.put("id",json.get("id"));
			        PageData pdjobreg=jobregService.findByid(pd);
			        if(pdjobreg!=null&&pdjobreg.get("id")!=null){
						object.put("success","true");
						object.put("data",pdjobreg);
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if(cmd.equals("jobregEditValid")) {//设置有效和失效
			        pd.put("id",json.get("id"));
			        pd.put("uid",json.get("uid"));
			        pd.put("ucid",json.get("ucid"));
					companyService.saveRecord(pd);
			        pd.put("isvalid",json.get("isvalid"));
			        jobregService.editValid(pd);
			        object.put("success","true");
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
	
	
	 public String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }

}

