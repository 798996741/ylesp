package com.fh.service.activiti.ruprocdef.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.fh.service.activiti.ruprocdef.RuprocdefManager;

/** 
 * 说明： 正在运行的流程
 * 创建人：FH Q313596790
 * @version
 */
@Service("ruprocdefService")
public class RuprocdefService implements RuprocdefManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**待办任务 or正在运行任务列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("RuprocdefMapper.datalist", page);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("RuprocdefMapper.datalistPage", page);
	}
	
	@SuppressWarnings("unchecked")
	public String getTaskID(String INSTID)throws Exception{
		return (String) dao.findForObject("RuprocdefMapper.getTaskID", INSTID);
	}
	
	/**流程变量列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> varList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RuprocdefMapper.varList", pd);
	}
	
	/**历史任务节点列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> hiTaskList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RuprocdefMapper.hiTaskList", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> getDealByDeptList(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RuprocdefMapper.getDealByDeptList", pd);
	}
	
	/**已办任务列表列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> hitasklist(Page page)throws Exception{
		return (List<PageData>)dao.findForList("RuprocdefMapper.hitaskdatalist", page);
	}
	
	/**激活or挂起任务(指定某个任务)
	 * @param pd
	 * @throws Exception
	 */
	public void onoffTask(PageData pd)throws Exception{
		dao.update("RuprocdefMapper.onoffTask", pd);
	}
	
	/**激活or挂起任务(指定某个流程的所有任务)
	 * @param pd
	 * @throws Exception
	 */
	public void onoffAllTask(PageData pd)throws Exception{
		dao.update("RuprocdefMapper.onoffAllTask", pd);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String,String> getTaskMapByID(String taskId)throws Exception{
		return (Map<String,String>)dao.findForObject("RuprocdefMapper.getTaskMapByID", taskId);
	}
	
	public PageData getDealByDept(PageData pd)throws Exception{
		return (PageData)dao.findForObject("RuprocdefMapper.getDealByDept", pd);
	}
	
	public void saveDealDept(PageData pd)throws Exception{
		dao.save("RuprocdefMapper.saveDealDept", pd);
	}
	
	public void delDealDept(PageData pd)throws Exception{
		dao.save("RuprocdefMapper.delDealDept", pd);
	}
	
	public List<PageData> getYj(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("RuprocdefMapper.getYj", pd);
	}
	
}

