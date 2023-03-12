package com.xxgl.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;

/** 
 * 说明： 优惠券
 * 创建人：351412933
 * 创建时间：2018-08-01
 * @version
 */
@Service("zxlbService")
public class ZxlbService implements ZxlbManager{

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("ZxlbMapper.save", pd);
	}
	
	/**删除
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("ZxlbMapper.delete", pd);
	}
	
	/**修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("ZxlbMapper.edit", pd);
	}
	
	
	public void editTokenid(PageData pd)throws Exception{
		dao.update("ZxlbMapper.editTokenid", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("ZxlbMapper.datalist", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("ZxlbMapper.listAll", pd);
	}
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ZxlbMapper.findById", pd);
	}
	
	public PageData findByTokenId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ZxlbMapper.findByTokenId", pd);
	}
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByZxyh(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ZxlbMapper.findByZxyh", pd);
	}
	
	public PageData findByZxid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ZxlbMapper.findByZxid", pd);
	}
	
	public PageData findByParamCode(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ZxlbMapper.findByParamCode", pd);
	}
	
	public PageData findByZxz(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ZxlbMapper.findByZxz", pd);
	}
	
	/**通过usernmad获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByUsername(PageData pd)throws Exception{
		return (PageData)dao.findForObject("ZxlbMapper.findByUsername", pd);
	}
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("ZxlbMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

