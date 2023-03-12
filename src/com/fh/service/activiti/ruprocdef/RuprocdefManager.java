package com.fh.service.activiti.ruprocdef;

import java.util.List;
import java.util.Map;

import com.fh.entity.Page;
import com.fh.util.PageData;

/** 
 * 说明： 正在运行的流程接口
 * 创建人：FH Q313596790
 * @version
 */
public interface RuprocdefManager{

	/**待办任务 or正在运行任务列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	public List<PageData> listPage(Page page)throws Exception;
	
	/**流程变量列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> varList(PageData pd)throws Exception;
	
	/**历史任务节点列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> hiTaskList(PageData pd)throws Exception;
	
	/**已办任务列表列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> hitasklist(Page page)throws Exception;
	
	/**激活or挂起任务(指定某个任务)
	 * @param pd
	 * @throws Exception
	 */
	public void onoffTask(PageData pd)throws Exception;
	
	/**激活or挂起任务(指定某个流程的所有任务)
	 * @param pd
	 * @throws Exception
	 */
	public void onoffAllTask(PageData pd)throws Exception;

	public Map<String,String> getTaskMapByID(String taskId)throws Exception;
	
	public String getTaskID(String INSTID)throws Exception;
	
	public void saveDealDept(PageData pd)throws Exception;
	
	public void delDealDept(PageData pd)throws Exception;
	
	public PageData getDealByDept(PageData pd)throws Exception;
	
	public List<PageData> getDealByDeptList(PageData pd)throws Exception;
	
	public List<PageData> getYj(PageData pd)throws Exception;
	
}

