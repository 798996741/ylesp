package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.AddressManager;
import com.yulun.service.CompanyManager;
import com.yulun.service.RecordManager;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

@Service("companyService")
public class CompanyService implements CompanyManager{
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	

	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(PageData pd)throws Exception{
		dao.update("CompanyMapper.deleteAll", pd);
	}

	
	
	/**审核
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("CompanyMapper.edit", pd);
	}
	/**审核
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd)throws Exception{
		dao.save("CompanyMapper.save", pd);
	}
	/*
	 *查询所有列表信息
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CompanyMapper.datalistPage", page);
	}
	
	
	/**通过id获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByUid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CompanyMapper.findByuid", pd);
	}
	
	/**通过手机号码获取数据
	 * @param pd
	 * @throws Exception
	 */
	public PageData findByTel(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CompanyMapper.findByTel", pd);
	}
	
	@SuppressWarnings("unchecked")
	public List<PageData> listGwfl(PageData page)throws Exception{
		return (List<PageData>)dao.findForList("CompanyMapper.listGwfl", page);
	}
	
	
	public void editZczx(PageData pd)throws Exception{
		dao.update("CompanyMapper.editZczx", pd);
	}
	/**政策咨询
	 * @param pd
	 * @throws Exception
	 */
	public void saveZczx(PageData pd)throws Exception{
		dao.save("CompanyMapper.saveZczx", pd);
	}
	
	/**通过uid获取数据政策咨询
	 * @param pd
	 * @throws Exception
	 */
	public  List<PageData> findZczxByUid(Page page)throws Exception{
		return ( List<PageData>)dao.findForList("CompanyMapper.findZczxByuidlistPage", page);
	}
	
	
	public void editYwyd(PageData pd)throws Exception{
		dao.update("CompanyMapper.editYwyd", pd);
	}
	/**业务引导
	 * @param pd
	 * @throws Exception
	 */
	public void saveYwyd(PageData pd)throws Exception{
		dao.save("CompanyMapper.saveYwyd", pd);
	}
	
	/**通过uid获取数据业务引导
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findYwydByUid(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CompanyMapper.findYwydByuidlistPage", page);
	}
	
	
	public void editQtfw(PageData pd)throws Exception{
		dao.update("CompanyMapper.editQtfw", pd);
	}
	/**其他服务
	 * @param pd
	 * @throws Exception
	 */
	public void saveQtfw(PageData pd)throws Exception{
		dao.save("CompanyMapper.saveQtfw", pd);
	}
	
	public void saveTj(PageData pd)throws Exception{
		dao.save("CompanyMapper.saveTj", pd);
	}
	
	/**通过uid获取数据其他服务
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> findQtfwByUid(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CompanyMapper.findQtfwByuidlistPage", page);
	}
	
	/*
	 * 用工统计
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listYgtj(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CompanyMapper.ygtjlistPage", page);
	}
	
	/*
	 * 就业统计
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listJytj(Page page)throws Exception{
		return (List<PageData>)dao.findForList("CompanyMapper.jytjlistPage", page);
	}
	
	public PageData findTjByRegid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CompanyMapper.findTjByRegid", pd);
	}
	
	public void saveRecord(PageData pd)throws Exception{
		if(pd.getString("ucid")!=null&&!pd.getString("ucid").equals("")&&pd.getString("uid")!=null){
			PageData pd_record=this.findRecordByuid(pd);
			if(pd_record==null){
				dao.save("CompanyMapper.saveRecord", pd);
			}	
		}
	}
	
	public PageData findRecordByuid(PageData pd)throws Exception{
		return (PageData)dao.findForObject("CompanyMapper.findRecordByuid", pd);
	}

	@Override
	public List<PageData> tecjytjlistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("CompanyMapper.tecjytjlistPage", page);
	}

	@Override
	public List<PageData> tecygdjlistPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("CompanyMapper.tecygdjlistPage", page);
	}

	@Override
	public void insertJobMatching(PageData pd) throws Exception {
		dao.save("CompanyMapper.insertJobMatching", pd);
	}

	@Override
	public void deleteJobMatching(PageData pd) throws Exception {
		dao.delete("CompanyMapper.deleteJobMatching", pd);
	}

	@Override
	public void deleteEmpMatching(PageData pd) throws Exception {
		dao.delete("CompanyMapper.deleteEmpMatching", pd);
	}

	@Override
	public void insertEmpMatching(PageData pd) throws Exception {
		dao.save("CompanyMapper.insertEmpMatching", pd);
	}

	@Override
	public List<PageData> queryList(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("CompanyMapper.queryList", pd);
	}


}
