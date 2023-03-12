package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface JobregManager {

	
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
	
	public void editValid(PageData pd)throws Exception;
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
	public PageData findByid(PageData pd)throws Exception;
	
	
}