package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.AddressManager;
import com.yulun.service.CompanyManager;
import com.yulun.service.PersonManager;
import com.yulun.service.RecordManager;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

@Service("personService")
public class PersonService implements PersonManager{
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(PageData pd)throws Exception{
		dao.update("PersonMapper.deleteAll", pd);
	}

	
	
	/**审核
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("PersonMapper.edit", pd);
	}
	/**审核
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("PersonMapper.save", pd);
	}
	/*
	 *查询所有列表信息
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("PersonMapper.datalistPage", page);
	}
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByUid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PersonMapper.findByuid", pd);
	}
	
	/**通过手机号码获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByTel(PageData pd)throws Exception{
		return (PageData)dao.findForObject("PersonMapper.findByTel", pd);
	}

	@Override
	public List<PageData> findDicByParentidlistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList("PersonMapper.findDicByParentidlistPage",page);
	}


}
