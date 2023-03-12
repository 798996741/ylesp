package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import com.yulun.service.DataStatManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("dataStatService")
public class DataStatService implements DataStatManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public PageData usertotal(PageData pd) throws Exception {
        return (PageData)dao.findForObject("DataStatMapper.usertotal", pd);
    }

    @Override
    public PageData servtotal(PageData pd) throws Exception {
        return (PageData)dao.findForObject("DataStatMapper.servtotal", pd);
    }

    @Override
    public List<PageData> getjob(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("DataStatMapper.getjob", pd);
    }

    @Override
    public List<PageData> getemploy(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("DataStatMapper.getemploy", pd);
    }

    @Override
    public PageData getskill(PageData pd) throws Exception {
        return (PageData) dao.findForObject("DataStatMapper.getskill",pd);
    }

    @Override
    public PageData quarterList(PageData pd) throws Exception {
        return (PageData) dao.findForObject("DataStatMapper.quarterList",pd);
    }

    @Override
    public List<PageData> isjobToage(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("DataStatMapper.isjobToage", pd);
    }

    @Override
    public List<PageData> isjobToxl(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("DataStatMapper.isjobToxl", pd);
    }

    @Override
    public List<PageData> isjobTosex(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("DataStatMapper.isjobTosex", pd);
    }
}
