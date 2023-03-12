package com.xxgl.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.fh.controller.activiti.rutask.RuTaskController;
import com.fh.controller.base.BaseController;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.util.HttpClientUtil;
import com.fh.util.PageData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xxgl.controller.WorkorderController;
import com.xxgl.service.mng.WorkorderManager;
import com.xxgl.utils.Constants;
import com.xxgl.utils.DateUtils;

/** 
 * 说明： 工单管理
 * 创建人：351412933
 * 创建时间：2019-11-24
 * @version
 */
@Service("workorderService")
public class WorkorderService implements WorkorderManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	@Resource(name="dictionariesService")
	private DictionariesManager dictionariesService;
	@Resource(name="workorderService")
	private WorkorderManager workorderService;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void saveWorkProc(PageData pd)throws Exception{
		dao.save("workorderMapper.saveWorkProc", pd);
	}
	
	public void save(PageData pd)throws Exception{
		dao.save("workorderMapper.save", pd);
	}
	
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("workorderMapper.delete", pd);
	}
	
	
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("workorderMapper.edit", pd);
	}
	
	public void editWorkproc(PageData pd)throws Exception{
		dao.update("workorderMapper.editWorkproc", pd);
	}
	
	public void editCL(PageData pd)throws Exception{
		dao.update("workorderMapper.editCL", pd);
	}
	
	public void zxPf(PageData pd)throws Exception{
		dao.update("workorderMapper.zxPf", pd);
	}
	
	public void editTsdept(PageData pd)throws Exception{
		dao.update("workorderMapper.editTsdept", pd);
	}
	
	public void saveTsdept(PageData pd)throws Exception{
		dao.update("workorderMapper.saveTsdept", pd);
	}
	
	public void editCfbm(PageData pd)throws Exception{
		dao.update("workorderMapper.editCfbm", pd);
	}

	public void editCLByuid(PageData pd)throws Exception{
		dao.update("workorderMapper.editCLByuid", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("workorderMapper.datalistPage", page);
	}
	
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("workorderMapper.listAll", pd);
	}

	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("workorderMapper.findByid", pd);
	}
	
	
	public PageData findFileById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("workorderMapper.findFileById", pd);
	}
	
	public List<PageData> findFileByuid(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("workorderMapper.findFileByuid", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("workorderMapper.deleteAll", ArrayDATA_IDS);
	}
	
	public void deleteFile(PageData pd)throws Exception{
		dao.delete("workorderMapper.deleteFile", pd);
	}
	
	public void deleteAllFile(PageData pd)throws Exception{
		dao.delete("workorderMapper.deleteAllFile", pd);
	}
	
	public void savefile(PageData pd)throws Exception{
		dao.save("workorderMapper.saveFile", pd);
	}
	
	public PageData gdCount(PageData pd)throws Exception{
		return (PageData)dao.findForObject("workorderMapper.gdCount", pd);
	}
	
	public PageData findByYear(PageData pd) throws Exception{
		return (PageData)dao.findForObject("workorderMapper.findByYear", pd);
	}

	public void editCode(PageData pd) throws Exception{
		dao.update("workorderMapper.editCode", pd);
	}

	public void saveCode(PageData pd) throws Exception{
		dao.save("workorderMapper.saveCode", pd);
	}
	
	
	
	public String getWorkorderList(String USER_ID,String sendcont) throws Exception{
		
		HttpClientUtil util=new HttpClientUtil(Constants.DBURL);
		//设置请求方式
		String content="";
		Map<String, String> param=new HashMap();
		String[] USERID=Constants.USERID;
		String[] POSTID=Constants.POSTID;
		for(int i=0;i<USERID.length;i++){
			param.put("userId", USERID[i]);
			param.put("postId", POSTID[i]);
			param.put("orderCol", "");
			param.put("orderDir", "asc");
			param.put("pageNum", "1");
			param.put("pageSize", "10000");
			param.put("keyWord", "");
			param.put("questionType", "");
			param.put("deptId", "");
			util.setParameter(param);
			util.post();
			//获取返回内容
			content=util.getContent();
			//打印结果
			//System.out.println(content+"content");
			PageData pd=new PageData();
			PageData pd_dict=new PageData();
			org.json.JSONObject json = new org.json.JSONObject(content);   
			List<Map<String,String>> listObjectFir = (List<Map<String,String>>) JSONArray.parse(json.getString("data"));
	        System.out.println("利用JSONArray中的parse方法来解析json数组字符串");
	        String value="";
	        PageData pddb=null;
	        
	        for(Map<String,String> mapList : listObjectFir){
	        	pd=new PageData();
	        	pddb=null;
	            for (Map.Entry entry : mapList.entrySet()){
	            	value=(entry.getValue()==null?"":entry.getValue()).toString();
	               if(entry.getKey()!=null&&(entry.getKey().equals("updatetime")||entry.getKey().equals("repTime")||entry.getKey().equals("dueTime")||entry.getKey().equals("dispatchDate")||entry.getKey().equals("replyTime"))){
	            	   if(!value.equals("")){
	            		   value=DateUtils.timeStamp2Date(value,"");
	            	   }
	               }
	               
	               System.out.println( "#{"+entry.getKey()+"},"+value);
	               if(value.equals("null")){
	            	   value="";
	               }
	               pd.put(entry.getKey(), value);
	            }
	            pd.put("userid", USERID[i]);
				pd.put("postid", POSTID[i]);
	            pddb= workorderService.findDbByid(pd);
	            if(pddb==null){
	            	workorderService.saveDb(pd);
	            	//发送接收信息
	            	this.sendNextPerson(pd.getString("caseCode"), POSTID[i], sendcont);
	            	BaseController baseController=new BaseController();
	            	WorkorderController workorderController=new WorkorderController();
	            	pd.put("workid", baseController.get32UUID());
	        		pd.put("tsdate",pd.getString("repTime"));
	        		pd.put("tssource","183f8c3ffd654a6aa307aa14c3fbe033");
	        		pd.put("tsman",pd.getString("repMan"));
	        		pd.put("tstel",pd.getString("repTel"));
	        		pd.put("tscont",pd.getString("matterDescription"));
	        		pd.put("tslevel",pd.getString("tslevel"));
	        		pd.put("NAME", pd.getString("matterType"));
	        		
	        		pd.put("tsdept","");
	        		pd_dict=dictionariesService.findByName(pd);
	        		if(pd_dict!=null){
	        			pd.put("tsclassify",pd_dict.getString("DICTIONARIES_ID"));
	        		}
	        		
	        		pd.put("tstype","");
	        		pd.put("ishf","");
	        		pd.put("type","0");
	        		pd.put("cljd","");
	        		pd.put("cardid","");
	        		pd.put("cjdate","");
	        		pd.put("czman","350111-zy");  //默认呼叫中心插入用户
	        		if(pd.getString("completeTimeAsk")!=null){
	        			pd.put("clsx",Integer.parseInt(pd.getString("completeTimeAsk"))/60);
	        		}
	        		
	        		pd.put("uid", baseController.get32UUID());	//随机生成
	        		pd.put("type", "0");
	        		
	        		Date currentTime = new Date();
	        		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	        		String year = formatter.format(currentTime);
	        		PageData pd_year = new PageData();
	        		pd_year.put("year", year);
	        		pd_year = this.getNum(year);
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
        			workorderService.save(pd);	
        			if("1".equals(code_num)){
        				workorderService.saveCode(pd_year);
        			}else{
        				pd_year.put("code_num", Integer.parseInt(code_num));
        				workorderService.editCode(pd_year);
        			}
	            }
	            
	        }
		}
		
        return content;
		
	}
	
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

	
	public PageData findDbByCasecode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("workorderMapper.findDbByCasecode", pd);
	}
	
	public PageData findDbByid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("workorderMapper.findDbByid", pd);
	}
	
	public void saveDb(PageData pd)throws Exception{
		dao.save("workorderMapper.saveDb", pd);
	}
	
	/*
	 * 申请退单接口
	 */
	public String sendReturn(String caseCode,String userId,String postId,String opinion,String deptId,String taskId,String unitType) throws Exception{
		
		HttpClientUtil util=new HttpClientUtil(Constants.DBRETURN);
		//设置请求方式
		
		Map<String, String> param=new HashMap();

		param.put("caseCode",caseCode);
		param.put("postId", postId);
		param.put("userId", userId);
		param.put("deptId", deptId);
		param.put("opinion", opinion);
		param.put("taskId", taskId);
		param.put("unitType", unitType);
		util.setParameter(param);
		util.post();
		//获取返回内容
		String content=util.getContent();
		//打印结果
		System.out.println(content+"content");
		//org.json.JSONObject json = new org.json.JSONObject(content);   
       // System.out.println(json.getString("responseCode"));
		return content;
		
	}

	/*
	 * 1.责任单位签收员或处理人发送下一处理人
	 */
	public String sendNextPerson(String caseCode,String postId,String sendcont) throws Exception{
		
		HttpClientUtil util=new HttpClientUtil(Constants.DBNEXTURL);
		//设置请求方式
		
		Map<String, String> param=new HashMap();
		
		param.put("caseCode",caseCode);
		param.put("postId", postId);
		param.put("recvDeptId", "");
		param.put("recvPosId", "");
		param.put("opinion", "");
		param.put("taskId", "");
		util.setParameter(param);
		util.post();
		//获取返回内容
		String content=util.getContent();
		//打印结果
		System.out.println(content+"content");
		//org.json.JSONObject json = new org.json.JSONObject(content);   
       // System.out.println(json.getString("responseCode"));
		return content;
		
	}
	
	
	/*
	 * 责任单位处理人申请审结
	 */
	public String sendAuditFinish(String caseCode,String replyPoint,String postId,String opinion,String replyPerson,String taskId,String replyPhoneNo,String replyTime,String solved) throws Exception{
		
		HttpClientUtil util=new HttpClientUtil(Constants.DBAuditFinish);
		//设置请求方式
	
		
		Map<String, String> param=new HashMap();

		param.put("caseCode",caseCode);
		param.put("postId", postId);
		param.put("replyPoint", replyPoint);
		
		param.put("opinion", opinion);
		param.put("taskId", taskId);
		param.put("replyPerson", replyPerson);
		param.put("replyPhoneNo", replyPhoneNo);
		
		param.put("replyTime", replyTime);
		param.put("solved", solved);
		util.setParameter(param);
		util.post();
		//获取返回内容
		String content=util.getContent();
		//打印结果
		System.out.println(content+"content");
		//org.json.JSONObject json = new org.json.JSONObject(content);   
       // System.out.println(json.getString("responseCode"));
		return content;
		
	}

	
	
	/*
	 * 责任单位分管领导审核审结
	 */
	public String sendFinishByLeader(String caseCode,String postId,String opinion,String doType,String taskId) throws Exception{
		
		HttpClientUtil util=new HttpClientUtil(Constants.DBFinishByLeader);
		//设置请求方式
		Map<String, String> param=new HashMap();

		param.put("caseCode",caseCode);
		param.put("postId", postId);
		
		param.put("opinion", opinion);
		param.put("taskId", taskId);
		param.put("doType", doType);
		util.setParameter(param);
		util.post();
		//获取返回内容
		String content=util.getContent();
		//打印结果
		System.out.println(content+"content");
		//org.json.JSONObject json = new org.json.JSONObject(content);   
       // System.out.println(json.getString("responseCode"));
		return content;
		
	}

	



	
	public static void main(String[] args) throws Exception {
		WorkorderService ruTaskController=new WorkorderService();
		System.out.println(ruTaskController.sendNextPerson("20200119033924", "159581", "内容"));
	}
	
	
	
	
}

