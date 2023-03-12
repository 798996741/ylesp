package com.xxgl.service.impl;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.util.PageData;
import com.xxgl.service.mng.ExetaskManager;
import com.xxgl.service.mng.NaireManager;
import com.xxgl.service.mng.NairetemplateManager;
import com.xxgl.service.mng.TaskuserManager;
import com.xxgl.utils.ResponseUtils;

/** 
 * 说明： 参数配置表
 * 创建人：351412933
 * 创建时间：2018-07-06
 * @version
 */
@Service("taskuserService")
public class TaskuserService implements TaskuserManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	

	@Resource(name="naireService")
	private NaireManager naireService;
	
	@Resource(name="exetaskService")
	private ExetaskManager exetaskService;

	
	@Resource(name="nairetemplateService")
	private NairetemplateManager nairetemplateService;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TaskuserMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TaskuserMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TaskuserMapper.edit", pd);
	}
	
	public void editStateqd(PageData pd)throws Exception{
		dao.update("TaskuserMapper.editStateqd", pd);
	}
	
	public void editStateCom(PageData pd)throws Exception{
		dao.update("TaskuserMapper.editStateCom", pd);
	}
	
	
	public void editTableName(PageData pd)throws Exception{
		dao.update("TaskuserMapper.editTableName", pd);
	}
	
	public void dropTable(PageData pd)throws Exception{
		dao.update("TaskuserMapper.dropTable", pd);
	}
	
	public void createNewTable(PageData pd)throws Exception{
		dao.update("TaskuserMapper.createNewTable", pd);
	}
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TaskuserMapper.datalist", page);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TaskuserMapper.datalistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TaskuserMapper.listAll", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listAllGroupByField(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TaskuserMapper.listAllGroupByField", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TaskuserMapper.findById", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByTablename(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TaskuserMapper.findByTablename", pd);
	}
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TaskuserMapper.findByCode", pd);
	}
	
	/**通过起始地址获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByAddr(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TaskuserMapper.findByAddr", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TaskuserMapper.deleteAll", ArrayDATA_IDS);
	}

	@Override
	public PageData findByField(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("TaskuserMapper.findByField", pd);
	}

	@Override
	public void editFptype(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.update("TaskuserMapper.editFptype", pd);
	}
	
	
	/**
	 * 获取题目信息及答题信息
	 */
	public Object getTmByNaire(PageData pd,String state) throws Exception{
		
		PageData pd_new = new PageData();
		PageData  pd_naire=null;
		List<PageData>	varList =null;		
		JSONObject object_new = new JSONObject();
		pd_new.put("NAIRE_TEMPLATE_ID", pd.getString("NAIRE_TEMPLATE_ID"));
		pd_new.put("CUS_ID", pd.getString("CUS_ID"));
		
		pd.put("CUS_ID","");
		
		List<PageData>	codeList =naireService.listStartAndEndConde(pd_new);
		String nextCodeStr="";
		String rownum=pd.getString("NUM")==null?"1":pd.getString("NUM");
		for(int i=0;i<codeList.size();i++){
			
			//if(!nextCodeStr.equals("")){
				nextCodeStr=nextCodeStr+" and ";
			//}
			if(Integer.parseInt(String.valueOf(codeList.get(i).get("startcode")))>=Integer.parseInt(rownum)||Integer.parseInt(String.valueOf(codeList.get(i).get("endcode")))<=Integer.parseInt(rownum)){
				nextCodeStr=nextCodeStr+"("+codeList.get(i).get("startcode")+">=code or";
				nextCodeStr=nextCodeStr+" code>="+codeList.get(i).get("endcode")+")";	
			}
			
		}	
		
		if("1".equals(state)){
			
			
			varList = naireService.listAll(pd_new);	 //所有获取题目列表
			pd_new.put("ID", pd.getString("NAIRE_TEMPLATE_ID"));
			PageData  pd_naire_template=nairetemplateService.findById(pd_new);
			
			int ROWNO=Integer.parseInt(rownum);
			
			//是否有下一题
			PageData  pd_naire_next= new PageData();
			String NEXT_ID="";
			String ANSWER="";
			if(pd.getString("ANSWER")!=null&&!pd.getString("ANSWER").equals("")){
				pd_naire_next=naireService.findByNextAnswer(pd);
				if(pd_naire_next!=null){
					NEXT_ID=pd_naire_next.getString("NEXT_ID");
				}
			}
			if(ROWNO==1){
				String kcb=pd_naire_template.getString("OPENINGREMARKS")==null?"":pd_naire_template.getString("OPENINGREMARKS");
				object_new.put("kcb", kcb);
			}
			
			String isjs="0";
			PageData pd_answer_s = new PageData();
			if((ROWNO-1)==varList.size()){  //最后一题
				object_new.put("jsy", pd_naire_template.getString("CONCLUDINGREMARKS"));
				isjs="1";
			}
			//PageData  pd_custom=null;
			if((ROWNO-1)!=varList.size()){
				if(NEXT_ID.equals("")){
					if(pd.getString("ANSWER")!=null&&!pd.getString("ANSWER").equals("")){ //判断是否答题
						pd.put("CODE", ROWNO+1);
						pd_naire=naireService.findByCode(pd);
					}else{
						pd.put("CODE", ROWNO);
						pd_naire=naireService.findByCode(pd);
					}
				}else{
					PageData pd_next = new PageData();
					PageData pd_naire_var = new PageData();
					pd_next.put("ID",NEXT_ID);
					//查找下一题的题目内容
					pd_naire=naireService.findByNextId(pd_next);
					for(int i=0;i<varList.size();i++){
						pd_naire_var=varList.get(i);
						if(pd_naire_var!=null&&pd_naire!=null&&pd_naire.getString("id").equals(pd_naire_var.getString("ID"))){
							ROWNO=Integer.parseInt((String.valueOf(pd_naire_var.get("code"))));
						}
					}
				}
				
				List<PageData> pdList=new ArrayList();
				if(pd_naire!=null){
					String answer=pd_naire.getString("answer")==null?"":pd_naire.getString("answer");
					//System.out.println("answer:"+answer);
					String[] arr=answer.split(";");
					String[] arr1=null;
					int num=0;
					for(int i=0;i<arr.length;i++){
						arr1=arr[i].split(":");
						if(arr1.length>=1){
							num++;
						}
					}
					String[] check=new String[num];
					
					PageData pd_answer=new PageData();
					for(int i=0;i<arr.length;i++){
						pd_answer=new PageData();
						arr1=arr[i].split(":");
						if(arr1.length>=1){
							check[i]= arr1[0];
						}
						
						pdList.add(pd_answer);
					}
					pd_naire.put("checks", check);
				}
				
				//System.out.println(pd_naire);
				object_new.put("naire", pd_naire);
				//获取所有已回答的提交
			}
			pd.put("NEXT_ID", "");
			pd.put("NUM", "");
			pd.put("CUS_ID", pd.getString("uid"));
			pd.put("nextCodeStr", nextCodeStr);
			
			object_new.put("isjs", isjs);
		}else{
			object_new.put("naire", pd_naire);
			pd.put("nextCodeStr", nextCodeStr);
			pd.put("CUS_ID", pd.getString("uid"));
			pd.put("allUse", "1");
		}
		List<PageData> naireUse=naireService.listAll(pd);
		List<PageData> pdList=new ArrayList();
		for(PageData pd_naires:naireUse){
			if(pd_naires!=null){
				pdList=new ArrayList();
				String answer=pd_naires.getString("answer")==null?"":pd_naires.getString("answer");
				//System.out.println("answer:"+answer);
				String[] arr=answer.split(";");
				String[] arr1=null;
				int num=0;
				for(int i=0;i<arr.length;i++){
					arr1=arr[i].split(":");
					if(arr1.length>=1){
						num++;
					}
				}
				String[] check=new String[num];
				
				PageData pd_answer=new PageData();
				for(int i=0;i<arr.length;i++){
					pd_answer=new PageData();
					arr1=arr[i].split(":");
					if(arr1.length>=1){
						check[i]= arr1[0];
					}
					
					pdList.add(pd_answer);
				}
				pd_naires.put("checks", check);
			}
		}
		
		
		object_new.put("naireUse", naireUse);
		
		
		return object_new;
	}

	
	
}

