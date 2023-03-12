package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface CompanyManager {

	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(PageData pd)throws Exception;
	
	
	/**审核
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception;
	/**审核
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception;
	/*
	 *查询所有列表信息
	 *
	 */
	public List<PageData> listPage(Page page)throws Exception;
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByUid(PageData pd)throws Exception;
	
	public PageData findByTel(PageData pd)throws Exception;
	
	public List<PageData> listGwfl(PageData page)throws Exception;
	
	
	public void editZczx(PageData pd)throws Exception;
	/**政策咨询
	 * @param pd
	 * @throws Exception
	 */
	public void saveZczx(PageData pd)throws Exception;
	
	/**通过uid获取数据政策咨询
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findZczxByUid(Page page)throws Exception;
	
	public void editYwyd(PageData pd)throws Exception;
	/**业务引导
	 * @param pd
	 * @throws Exception
	 */
	public void saveYwyd(PageData pd)throws Exception;
	/**通过uid获取数据业务引导
	 * @param pd
	 * @throws Exception
	 */
	public  List<PageData> findYwydByUid(Page page)throws Exception;
	
	public void editQtfw(PageData pd)throws Exception;
	/**其他服务
	 * @param pd
	 * @throws Exception
	 */
	public void saveQtfw(PageData pd)throws Exception;
	/**通过uid获取数据其他服务
	 * @param pd
	 * @throws Exception
	 */
	public  List<PageData> findQtfwByUid(Page page)throws Exception;
	
	
	 
	public List<PageData> listYgtj(Page page)throws Exception;
	/*
	 * 就业统计
	 */
	public List<PageData> listJytj(Page page)throws Exception;
	
	public void saveTj(PageData pd)throws Exception;
	
	public PageData findTjByRegid(PageData pd)throws Exception;
	
	public void saveRecord(PageData pd)throws Exception;
	
	public PageData findRecordByuid(PageData pd)throws Exception;

	public List<PageData> tecjytjlistPage(Page page)throws Exception;

	public List<PageData> tecygdjlistPage(Page page)throws Exception;

	public void insertJobMatching(PageData pd)throws Exception;

	public void deleteJobMatching(PageData pd)throws Exception;

	public void deleteEmpMatching(PageData pd)throws Exception;

	public void insertEmpMatching(PageData pd)throws Exception;

	public List<PageData> queryList(PageData pd)throws Exception;
}