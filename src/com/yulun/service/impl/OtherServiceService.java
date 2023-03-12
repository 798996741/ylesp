package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.OtherServiceManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.Name;
import java.util.List;

/**
 * @Author Aliar
 * @Time 2020-05-18 16:51
 **/

@Service("otherServiceService")
public class OtherServiceService implements OtherServiceManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public List<PageData> findAlllistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("otherServiceMapper.findAlllistPage",page);
    }

    @Override
    public List<PageData> findSummarylistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("otherServiceMapper.findSummarylistPage",page);
    }
}
