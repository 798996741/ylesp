package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.PolicyManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Aliar
 * @Time 2020-05-07 11:04
 **/
@Service("policyService")
@Transactional
public class PolicyService implements PolicyManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Override
    public List<PageData> findAlllistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("policyMapper.findAlllistPage",page);
    }

    @Override
    public List<PageData> findCompanyByUid(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("policyMapper.findCompanyByUid",pd);
    }

    @Override
    public List<PageData> findPersonByUid(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("policyMapper.findPersonByUid",pd);
    }

    @Override
    public List<PageData> findByUidlistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("policyMapper.findByUidlistPage",page);
    }

    @Override
    public List<PageData> findSummarylistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("policyMapper.findSummarylistPage",page);
    }


}
