package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.StatisticsManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service("statisticsService")
public class StatisticsService implements StatisticsManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    @Override
    public List<PageData> zxCallNumCount(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("StatisticsMapper.zxCallNumCount",pd);
    }

    @Override
    public List<PageData> zxCallNumSum(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("StatisticsMapper.zxCallNumSum",pd);
    }

    @Override
    public List<PageData> getxzid(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("StatisticsMapper.getxzid",pd);
    }

    @Override
    public List<PageData> getcgjtl(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("StatisticsMapper.getcgjtl",pd);
    }

    @Override
    public List<PageData> getxlfql(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("StatisticsMapper.getxlfql",pd);
    }

    @Override
    public List<PageData> getuser(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("StatisticsMapper.getuser",pd);
    }

    @Override
    public List<PageData> zxopeloglistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("StatisticsMapper.zxopeloglistPage",pd);
    }

    @Override
    public List<PageData> zxcallloglistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("StatisticsMapper.zxcallloglistPage",pd);
    }

    @Override
    public List<PageData> ZXHWTJlistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("StatisticsMapper.ZXHWTJlistPage",pd);
    }

    @Override
    public List<PageData> timeZXHWTJlistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("StatisticsMapper.timeZXHWTJlistPage",pd);
    }

    @Override
    public List<PageData> MYDTJlistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("StatisticsMapper.MYDTJlistPage",pd);
    }

    @Override
    public List<PageData> timeMYDTJlistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("StatisticsMapper.timeMYDTJlistPage",pd);
    }

    @Override
    public List<PageData> XTHWTJlistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("StatisticsMapper.XTHWTJlistPage",pd);
    }


}
