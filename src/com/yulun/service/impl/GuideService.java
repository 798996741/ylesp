package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.GuideManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Aliar
 * @Time 2020-05-10 16:08
 **/

@Service("guideService")
public class GuideService implements GuideManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Override
    public List<PageData> findAlllistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("guideMapper.findAlllistPage",page);
    }

    @Override
    public List<PageData> findByUidlistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("guideMapper.findByUidlistPage",page);
    }

    @Override
    public List<PageData> findSummarylistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("guideMapper.findSummarylistPage",page);
    }

}
