package com.xxgl.service.mng;

import java.util.List;

import com.fh.entity.Page;
import com.fh.util.PageData;


/** 
 * 说明： 参数配置表接口
 * 创建人：351412933
 * 创建时间：2018-07-06
 * @version
 */
public interface TaskuserManager{

	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception;
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	
	/**任务启动
	 * @param pd
	 * @throws Exception
	 */
	public void editStateqd(PageData pd)throws Exception;
	
	
	public PageData findByTablename(PageData pd)throws Exception;
	
	/**任务完成
	 * @param pd
	 * @throws Exception
	 */
	public void editStateCom(PageData pd)throws Exception;
	
	public void editTableName(PageData pd)throws Exception;
	
	public void editFptype(PageData pd)throws Exception;
	
	
	public void dropTable(PageData pd)throws Exception;
	
	public void createNewTable(PageData pd)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page)throws Exception;
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> listPage(Page page)throws Exception;
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	public List<PageData> listAllGroupByField(PageData pd)throws Exception;
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;

	public PageData findByField(PageData pd)throws Exception;
	
	
	public PageData findByCode(PageData pd)throws Exception;
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	public PageData findByAddr(PageData pd)throws Exception;
	
	public Object getTmByNaire(PageData pd,String state) throws Exception;
	
	
	
}

