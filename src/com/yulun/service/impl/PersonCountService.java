package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.PersonCountManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Aliar
 * @Time 2020-05-21 11:47
 **/
@Service("personCountService")
public class PersonCountService implements PersonCountManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public PageData jobregCount(PageData pd) throws Exception {
       return (PageData) dao.findForObject("personCountMapper.jobregCount",pd);
    }

    @Override
    public PageData recruitCount(PageData pd) throws Exception {
        return (PageData) dao.findForObject("personCountMapper.recruitCount",pd);
    }

    @Override
    public List<PageData> sexCount(Page page) throws Exception {
        return (List<PageData>) dao.findForList("personCountMapper.sexCountlistPage",page);
    }

    @Override
    public List<PageData> ageCount(Page page) throws Exception {
        return (List<PageData>) dao.findForList("personCountMapper.ageCountlistPage",page);
    }

    @Override
    public List<PageData> xlCount(Page page) throws Exception {
        return (List<PageData>) dao.findForList("personCountMapper.xlCountlistPage",page);
    }

    @Override
    public List<PageData> jobCount(Page page) throws Exception {
        return (List<PageData>) dao.findForList("personCountMapper.jobCountlistPage",page);
    }

    @Override
    public List<PageData> getDicName(String parentId) throws Exception {
        return (List<PageData>) dao.findForList("personCountMapper.getDicName",parentId);
    }

}
