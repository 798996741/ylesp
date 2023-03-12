package com.yulun.controller.esppoponscreen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.service.system.appuser.AppuserManager;
import com.fh.util.PageData;
import com.fh.util.Tools;
import com.yulun.service.CompanyManager;
import com.yulun.service.PersonManager;
import com.yulun.utils.BuildTree;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import org.apache.commons.lang3.StringUtils;

/**
 * 说明：通来电弹屏
 * 创建人：351412933
 * 创建时间：2020-04-08
 */
public class CompanyWeb implements CommonIntefate {


	@Resource(name="zxlbService")
	private ZxlbManager zxlbService;

	@Resource(name="companyService")
	private CompanyManager companyService;
	
	@Resource(name="personService")
	private PersonManager personService;
	
	@Override
	public JSONObject execute(JSONObject data, HttpServletRequest request)
			throws Exception {
		JSONObject object=new JSONObject();
//		try{
			PageData pd = new PageData();
			String cmd = data.getString("cmd")==null?"":data.getString("cmd");
			PageData pd_stoken=new PageData();
			JSONObject json = data.getJSONObject("data");
			pd_stoken.put("TOKENID", json.get("tokenid"));
			PageData pd_token=zxlbService.findByTokenId(pd_stoken);
			if(pd_token!=null){
				if(cmd.equals("companyAll")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			      
					pd.put("name", json.getString("name")); 
					pd.put("qytype", json.getString("qytype")); 
					String tel = json.getString("tel") == null ? "" : json.getString("tel").replace("-", "");
			        if ((tel.length() == 12) && (tel.startsWith("01"))) {
			            tel = tel.substring(1, 12);
			        }
			        String lxtel = json.getString("lxtel") == null ? "" : json.getString("lxtel").replace("-", "");
			        if ((lxtel.length() == 12) && (lxtel.startsWith("01"))) {
			            lxtel = lxtel.substring(1, 12);
			        }
			        pd.put("tel", tel);
					pd.put("lxr", json.getString("lxr")); 
					
					pd.put("startTime", json.getString("startTime"));
					if(json.getString("endTime")!=null&& !json.getString("endTime").equals("")){
						pd.put("endtime",Tools.getEndTime(json.getString("endTime"), 1));
					}
					
		            pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		            list =companyService.listPage(page);
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
				}else if(cmd.equals("companyDel")) {
				    pd.put("uid",json.get("uid"));
				    pd.put("ucid",json.get("ucid"));
				    companyService.saveRecord(pd);
					companyService.deleteAll(pd);
		        	object.put("success","true");
				    object.put("msg","删除成功");
				}else if(cmd.equals("companyAdd")||cmd.equals("companyEdit")) {
				
					pd.put("czman",pd_token.getString("ZXYH"));
					pd.put("uid",json.get("uid"));
					pd.put("ucid",json.get("ucid"));
					companyService.saveRecord(pd);
					pd.put("qytype",json.get("qytype"));
					String tel = json.getString("tel") == null ? "" : json.getString("tel").replace("-", "");
			        if ((tel.length() == 12) && (tel.startsWith("01"))) {
			            tel = tel.substring(1, 12);
			        }
			        String lxtel = json.getString("lxtel") == null ? "" : json.getString("lxtel").replace("-", "");
			        if ((lxtel.length() == 12) && (lxtel.startsWith("01"))) {
			            lxtel = lxtel.substring(1, 12);
			        }
			        pd.put("tel", lxtel);
			        pd.put("lxtel", lxtel);
			        pd.put("name",json.get("name"));
			        pd.put("email", json.getString("email")); 
			        pd.put("addr",json.getString("addr"));
			        pd.put("lxr",json.getString("lxr"));
			        pd.put("bylxr",json.get("bylxr")); 
			        pd.put("bylxtel",json.get("bylxtel")); 
			        pd.put("isinsuran",json.get("isinsuran"));
					pd.put("qzqd",json.getString("qzqd"));
			        pd.put("intro",json.get("intro"));
					PageData pd_company=companyService.findByUid(pd);
					if(pd_company!=null&&pd_company.getString("uid")!=null){
						companyService.edit(pd);
					}else{
						companyService.save(pd);
						
					}
			        object.put("success","true");
				    object.put("msg","保存成功");
				}else if(cmd.equals("companyFindById")) {
			        pd.put("uid",json.get("uid"));
			        PageData pdcompany=companyService.findByUid(pd);
			        if(pdcompany!=null&&pdcompany.get("id")!=null){
						object.put("success","true");
						object.put("data",pdcompany);
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if(cmd.equals("companyFindByTel")) {
					String tel = json.getString("tel") == null ? "" : json.getString("tel").replace("-", "");
			        if ((tel.length() == 12) && (tel.startsWith("01"))) {
			            tel = tel.substring(1, 12);
			        }
			        pd.put("tel", tel);
			        PageData pdcompany=companyService.findByTel(pd);
			        
			        JSONObject object_com=new JSONObject();
			        PageData pdperson=personService.findByTel(pd);
			        if((pdcompany!=null)||pdperson!=null){
			        	object_com.put("company", pdcompany);
			        	object_com.put("person", pdperson);
						object.put("success","true");
						object.put("data",object_com);
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if(cmd.equals("loadGwfl")) {
			        pd.put("uid",json.get("uid"));
			        List<PageData> list =companyService.listGwfl(pd);
			        List<Map<String,Object>> dataList = new ArrayList<>();
			        Map<String,Object> map = new HashMap<>();
			        
			        for(PageData pd_fl:list){
			        	map = new HashMap<>();
			        	map.put("id",pd_fl.get("code").toString());
			        	map.put("pid",pd_fl.get("parent_code").toString());
			        	map.put("name",pd_fl.get("gwflname"));
			        	dataList.add(map);
			        }
			        //System.out.println(JSON.toJSONString(data));
			        JSONArray result = BuildTree.listToTree(JSONArray.parseArray(JSON.toJSONString(dataList)),"id","pid","children");
			        
			        
			        if(list.size()>0){
						object.put("success","true");
						object.put("data",result);
					}else{
						object.put("success","false");
				        object.put("msg","暂无数据");
					}
				}else if(cmd.equals("zczxAdd")) {
				
					
					//PageData pd_company=companyService.findByUid(pd);
					//if(pd_company!=null&&pd_company.getString("uid")!=null){
						pd.put("czman",pd_token.getString("ZXYH"));
						pd.put("uid",json.get("uid"));
						pd.put("ucid",json.get("ucid"));
						companyService.saveRecord(pd);
						
						pd.put("id",json.get("id"));
						pd.put("type",json.get("type"));
						pd.put("zczxname",json.get("zczxname"));
				        pd.put("zczxtype",json.get("zczxtype"));
				        pd.put("isjd",json.get("isjd"));
				        pd.put("remark",json.get("remark"));
				        companyService.saveZczx(pd);
				        object.put("success","true");
					    object.put("msg","保存成功");
					//}else{
						//object.put("success","false");
					    //object.put("msg","请先保存企业信息或个人信息");
					//}    
				}else if(cmd.equals("zczxEdit")) {
				
					pd.put("czman",pd_token.getString("ZXYH"));
					pd.put("uid",json.get("uid"));
					pd.put("ucid",json.get("ucid"));
					companyService.saveRecord(pd);
					pd.put("id",json.get("id"));
					pd.put("zczxname",json.get("zczxname"));
			        pd.put("zczxtype",json.get("zczxtype"));
			        pd.put("isjd",json.get("isjd"));
			        pd.put("remark",json.get("remark"));
			        companyService.editZczx(pd);
			        object.put("success","true");
				    object.put("msg","保存成功");
				}else if(cmd.equals("findZczxByUid")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			      
					pd.put("uid", json.getString("uid")); 
					pd.put("type",json.get("type"));
		            pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		            list =companyService.findZczxByUid(page);
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
				}else if(cmd.equals("ywydAdd")) {
				
					pd.put("czman",pd_token.getString("ZXYH"));
					pd.put("uid",json.get("uid"));
					pd.put("ucid",json.get("ucid"));
					companyService.saveRecord(pd);
					pd.put("id",json.get("id"));
					pd.put("ywname",json.get("ywname"));
			        pd.put("ydqd",json.get("ydqd"));
			        pd.put("isjd",json.get("isjd"));
			        pd.put("type",json.get("type"));
			        pd.put("remark",json.get("remark"));
			        companyService.saveYwyd(pd);
			        object.put("success","true");
				    object.put("msg","保存成功");
				}else if(cmd.equals("ywydEdit")) {
				
					pd.put("czman",pd_token.getString("ZXYH"));
					pd.put("uid",json.get("uid"));
					pd.put("ucid",json.get("ucid"));
					companyService.saveRecord(pd);
					pd.put("id",json.get("id"));
					pd.put("ywname",json.get("ywname"));
			        pd.put("ydqd",json.get("ydqd"));
			        pd.put("isjd",json.get("isjd"));
			        pd.put("remark",json.get("remark"));
			        companyService.editYwyd(pd);
			        object.put("success","true");
				    object.put("msg","保存成功");
				}else if(cmd.equals("findYwydByUid")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			        pd.put("type",json.get("type"));
					pd.put("uid", json.getString("uid")); 
		            pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		            list =companyService.findYwydByUid(page);
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
				}else if(cmd.equals("qtfwAdd")) {
					pd.put("type",json.get("type"));
					pd.put("czman",pd_token.getString("ZXYH"));
					pd.put("uid",json.get("uid"));
					pd.put("ucid",json.get("ucid"));
					companyService.saveRecord(pd);
					pd.put("id",json.get("id"));
					pd.put("fwname",json.get("fwname"));
			        pd.put("fwtype",json.get("fwtype"));
			        pd.put("cljg",json.get("cljg"));
			        pd.put("remark",json.get("remark"));
			        pd.put("isjd",json.get("isjd"));
			        companyService.saveQtfw(pd);
			        object.put("success","true");
				    object.put("msg","保存成功");
				}else if(cmd.equals("qtfwEdit")) {
				
					pd.put("czman",pd_token.getString("ZXYH"));
					pd.put("uid",json.get("uid"));
					pd.put("ucid",json.get("ucid"));
					companyService.saveRecord(pd);
					pd.put("id",json.get("id"));
					pd.put("fwname",json.get("fwname"));
			        pd.put("fwtype",json.get("fwtype"));
			        pd.put("cljg",json.get("cljg"));
			        pd.put("remark",json.get("remark"));
					pd.put("isjd",json.get("isjd"));
			        companyService.editQtfw(pd);
			        object.put("success","true");
				    object.put("msg","保存成功");
				}else if(cmd.equals("ygtj")) {
					pd.put("czman",pd_token.getString("ZXYH"));
					pd.put("uid",json.get("uid"));
					pd.put("ucid",json.get("ucid"));
					companyService.saveRecord(pd);
					
					pd.put("regid",json.get("regid"));
					pd.put("istj","1");
					PageData pdperson=companyService.findTjByRegid(pd);
					if(pdperson==null){
						companyService.saveTj(pd);
				        object.put("success","true");
					    object.put("msg","保存成功");
					}else{
						object.put("success","false");
					    object.put("msg","推荐失败");
					}
			        
				}else if(cmd.equals("findQtfwByUid")){  //根据提交的方法执行相应的操作，获取知识库分页信息 
					Page page = new Page();
			        Integer pageIndex;
			        Integer pageSize;
			        List<PageData> list;
			        pd.put("type",json.get("type"));
					pd.put("uid", json.getString("uid")); 
		            pageIndex = json.getInteger("pageIndex");
		            page.setCurrentPage(pageIndex);
		            pageSize = json.getInteger("pageSize");
		            page.setShowCount(pageSize);
		            page.setPd(pd);
		            list =companyService.findQtfwByUid(page);
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
				}
//				else if(cmd.equals("listYgtj")){  //根据提交的方法执行相应的操作，获取知识库分页信息
//
//					Page page = new Page();
//			        Integer pageIndex;
//			        Integer pageSize;
//
//			        List<PageData> list;
//					pd.put("uid", json.getString("uid"));
//					pd.put("tel", json.getString("tel"));
//					pd.put("name", json.getString("name"));
//					pd.put("jobname", json.getString("jobname"));
//					pd.put("jobaddr", json.getString("jobaddr"));
//					pd.put("xl", json.getString("xl"));
//					pd.put("qwxz", json.getString("qwxz"));
//					pd.put("sex", json.getString("sex"));
//
//					if(json.get("age")==null||json.get("age").equals("不限")||json.get("age")==""){
//						pd.put("agestart","0");
//						pd.put("ageend","100");
//					}else if(json.getString("age").indexOf("-")>=0){
//						String[] arr=json.getString("age").split("-");
//						pd.put("agestart",arr[0]);
//						pd.put("ageend",arr[1]);
//					}
//
//					pageIndex = json.getInteger("pageIndex");
//		            page.setCurrentPage(pageIndex);
//		            pageSize = json.getInteger("pageSize");
//		            page.setShowCount(pageSize);
//		            page.setPd(pd);
//		            list =companyService.listYgtj(page);
//
//		           // System.out.println(list);
//					if(list.size()>0){
//						object.put("success","true");
//						object.put("data",list);
//						object.put("pageId",pageIndex);
//						object.put("pageCount",page.getTotalPage());
//						object.put("recordCount",page.getTotalResult());
//					}else{
//						object.put("success","false");
//				        object.put("msg","暂无数据");
//					}
//				}else if(cmd.equals("listJytj")){  //根据提交的方法执行相应的操作，获取知识库分页信息
//					Page page = new Page();
//			        Integer pageIndex;
//			        Integer pageSize;
//			        List<PageData> list;
//
//
//					pd.put("uid", json.getString("uid"));
//					pd.put("tel", json.getString("tel"));
//					pd.put("name", json.getString("name"));
//					pd.put("gzaddr", json.getString("gzaddr"));
//					if(json.get("age")==null||json.get("age").equals("不限")){
//						pd.put("agestart","0");
//						pd.put("ageend","100");
//					}else if(json.getString("age").indexOf("-")>=0){
//						String[] arr=json.getString("age").split("-");
//						pd.put("agestart",arr[0]);
//						pd.put("ageend",arr[1]);
//					}
//					pd.put("sex", json.getString("sex"));
//					pd.put("gzdy", json.getString("gzdy"));
//					pd.put("xl", json.getString("xl"));
//					pd.put("jobname", json.getString("jobname"));
//
//					pageIndex = json.getInteger("pageIndex");
//		            page.setCurrentPage(pageIndex);
//		            pageSize = json.getInteger("pageSize");
//		            page.setShowCount(pageSize);
//		            page.setPd(pd);
//
//		            list =companyService.listJytj(page);
//		           // System.out.println(list);
//		           /* for(PageData pdtj:list){
//		            	if(pdtj.get("agestart")!=null&&pdtj.get("agestart").equals("0")){
//		            		pdtj.put("age", "不限");
//		            	}else{
//		            		pdtj.put("age", pdtj.get("agestart")+"-"+pdtj.get("ageend"));
//		            	}
//		            }*/
//					if(list.size()>0){
//						object.put("success","true");
//						object.put("data",list);
//						object.put("pageId",pageIndex);
//						object.put("pageCount",page.getTotalPage());
//						object.put("recordCount",page.getTotalResult());
//					}else{
//						object.put("success","false");
//				        object.put("msg","暂无数据");
//					}
//				}
				else if(cmd.equals("listJytj")){
					Page page = new Page();
					Integer pageIndex;
					Integer pageSize;
					List<PageData> list;

					pd.put("uid", json.getString("uid"));
					pd.put("tel", json.getString("tel"));
					pd.put("name", json.getString("name"));
					pd.put("gzaddr", json.getString("gzaddr"));

					if(json.get("age")==null||json.get("age").equals("不限")){
						pd.put("agestart","0");
						pd.put("ageend","100");
					}else if(json.getString("age").indexOf("-")>=0){
						String[] arr=json.getString("age").split("-");
						pd.put("agestart",arr[0]);
						pd.put("ageend",arr[1]);
					}
					pd.put("sex", json.getString("sex"));
					pd.put("gzdy", json.getString("gzdy"));
					pd.put("xl", json.getString("xl"));
					pd.put("jobname", json.getString("jobname"));
					pd.put("queryname", json.getString("queryname"));
					if (!"".equals(json.getString("queryname")) && null!=json.getString("queryname")) {
						String[] querynames = json.getString("queryname").split(",");
//						String[] queryvalues = json.getString("queryvalue").split(",");
						for (int i = 0; i < querynames.length; i++) {
							pd.put(querynames[i],"0");
						}
					}

					pageIndex = json.getInteger("pageIndex");
					page.setCurrentPage(pageIndex);
					pageSize = json.getInteger("pageSize");
					page.setShowCount(pageSize);
					page.setPd(pd);
					System.out.println(pd);
					list = companyService.tecjytjlistPage(page);
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
				}else if(cmd.equals("listYgtj")){
					Page page = new Page();
					Integer pageIndex;
					Integer pageSize;

					List<PageData> list;
					pd.put("uid", json.getString("uid"));
					pd.put("tel", json.getString("tel"));
					pd.put("name", json.getString("name"));
					pd.put("jobname", json.getString("jobname"));
					pd.put("jobaddr", json.getString("jobaddr"));
					pd.put("xl", json.getString("xl"));
					pd.put("qwxz", json.getString("qwxz"));
					pd.put("sex", json.getString("sex"));

					if(json.get("age")==null||json.get("age").equals("不限")||json.get("age")==""){
						pd.put("agestart","0");
						pd.put("ageend","100");
					}else if(json.getString("age").indexOf("-")>=0){
						String[] arr=json.getString("age").split("-");
						pd.put("agestart",arr[0]);
						pd.put("ageend",arr[1]);
					}

					pageIndex = json.getInteger("pageIndex");
					page.setCurrentPage(pageIndex);
					pageSize = json.getInteger("pageSize");
					page.setShowCount(pageSize);
					page.setPd(pd);
					pd.put("queryname", json.getString("queryname"));
					if (!"".equals(json.getString("queryname")) && null!=json.getString("queryname")) {
						String[] querynames = json.getString("queryname").split(",");
//						String[] queryvalues = json.getString("queryvalue").split(",");
						for (int i = 0; i < querynames.length; i++) {
							pd.put(querynames[i],"0");
						}
					}
					list = companyService.tecygdjlistPage(page);
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
				}else if(cmd.equals("queryList")) {
                    pd.put("condition", json.getString("condition"));
					List<PageData> list = companyService.queryList(pd);
					object.put("success","true");
					object.put("data",list);
				}else{
					object.put("success","false");
			        object.put("msg","访问异常");
				}
			}else{
				object.put("success","false");
				object.put("msg","登录超时，请重新登录");
			}
//		}catch(Exception e){
//			e.printStackTrace();
//			if(e.getMessage().indexOf("Duplicate")>=0){
//				object.put("success","false");
//				object.put("msg","请勿插入重复数据");
//			}else{
//				object.put("success","false");
//				object.put("msg","操作异常");
//			}
//
//		}
        return object;
	}
	
	
	 public String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return dateFormat.format(date);
    }

}
