package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.RevrecordManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("revrecordService")
public class RevrecordService implements RevrecordManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public List<PageData> findrevrecordlistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("TespRevrecordMapper.findrevrecordlistPage",page);
    }

    @Override
    public List<PageData> findCallrevrecordlistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("TespRevrecordMapper.findCallrevrecordlistPage",page);
    }

    @Override
    public List<PageData> findrevrecord(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("TespRevrecordMapper.findrevrecord",pd);
    }

    @Override
    public void insertRevrecord(PageData pd) throws Exception {
        dao.save("TespRevrecordMapper.insertRevrecord",pd);
    }

    @Override
    public void updateRevrecord(PageData pd) throws Exception {
        dao.update("TespRevrecordMapper.updateRevrecord",pd);
    }

    @Override
    public List<PageData> tesprecordlistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("TespRevrecordMapper.tesprecordlistPage",page);
    }

    @Override
    public List<PageData> downloadRecord(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("TespRevrecordMapper.downloadRecord",pd);
    }

    @Override
    public List<PageData> HomeMisscalllistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("TespRevrecordMapper.HomeMisscalllistPage",pd);
    }

    @Override
    public List<PageData> HomeLeavemsglistPage(Page pd) throws Exception {
        return (List<PageData>) dao.findForList("TespRevrecordMapper.HomeLeavemsglistPage",pd);
    }

    @Override
    public List<PageData> HaveUcid(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("TespRevrecordMapper.HaveUcid",pd);
    }

    @Override
    public List<PageData> PersonBycardid(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("TespRevrecordMapper.PersonBycardid",pd);
    }

    @Override
    public void updateRevrecordByUcid(PageData pd) throws Exception {
        dao.update("TespRevrecordMapper.updateRevrecordByUcid",pd);
    }
}
