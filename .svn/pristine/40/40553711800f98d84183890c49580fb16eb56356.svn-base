package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.AddressManager;
import com.yulun.service.JobregManager;
import com.yulun.service.RecordManager;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

@Service("jobregService")
public class JobregService implements JobregManager{
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(PageData pd)throws Exception{
		dao.update("JobregMapper.deleteAll", pd);
	}

	
	
	/**审核
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("JobregMapper.edit", pd);
	}
	
	
	public void editValid(PageData pd)throws Exception{
		dao.update("JobregMapper.editValid", pd);
	}
	/**审核
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("JobregMapper.save", pd);
	}
	/*
	 *查询所有列表信息
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("JobregMapper.datalistPage", page);
	}
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("JobregMapper.findByid", pd);
	}
	

	
}
