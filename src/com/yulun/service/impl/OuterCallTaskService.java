package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.OuterCallTaskManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("outerCallTaskService")
public class OuterCallTaskService implements OuterCallTaskManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public List<PageData> findAllDictionariesPage(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("outerCallTaskService.findAllDictionariesPage",pd);
    }

    @Override
    public List<PageData> listPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("outerCallTaskService.listPage",page);
    }

    @Override
    public void deleteTask(PageData pd) throws Exception {
         dao.delete("outerCallTaskService.deleteTask", pd);;
    }

    @Override
    public void pauseTask(PageData pd) throws Exception {
        dao.update("outerCallTaskService.pauseTask", pd);;
    }

    @Override
    public void startTask(PageData pd) throws Exception {
        dao.update("outerCallTaskService.startTask", pd);;
    }

    @Override
    public List<PageData> findcompanylistPage(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("outerCallTaskService.findcompanylist",pd);
    }

    @Override
    public List<PageData> findCatelistPage(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("outerCallTaskService.findCatelist",pd);
    }

    @Override
    public void insertTask(PageData pd) throws Exception {
        dao.save("outerCallTaskService.insertTask", pd);;
    }

    @Override
    public void insertTaskCallUser(PageData pd) throws Exception {
        dao.save("outerCallTaskService.insertTaskCallUser", pd);;
    }

    @Override
    public PageData selectOneById(PageData pd) throws Exception {
        return (PageData)dao.findForObject("outerCallTaskService.selectOneById", pd);
    }

    @Override
    public List<PageData> selectOutboundData(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("outerCallTaskService.selectOutboundData",pd);
    }

    @Override
    public void editTask(PageData pd) throws Exception {
        dao.update("outerCallTaskService.editTask", pd);;
    }

    @Override
    public void replayUnCallUser(PageData pd) throws Exception {
        dao.update("outerCallTaskService.replayUnCallUser", pd);;
    }

    @Override
    public PageData selectOneDetailById(PageData pd) throws Exception {
        return (PageData)dao.findForObject("outerCallTaskService.selectOneDetailById", pd);
    }

    @Override
    public List<PageData> taskDetailPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("outerCallTaskService.taskDetaillistPage",page);
    }

    @Override
    public List<PageData> taskDetailAll(PageData pd) throws Exception {
        return (List<PageData>) dao.findForList("outerCallTaskService.taskDetailAll",pd);
    }

    @Override
    public PageData taskIntentAnalysisData(PageData pd) throws Exception {
        return (PageData)dao.findForObject("outerCallTaskService.taskIntentAnalysisData", pd);
    }

    @Override
    public List<PageData> taskNameAnalysisData(PageData pd) throws Exception {
        return (List<PageData>)dao.findForList("outerCallTaskService.taskNameAnalysisData", pd);
    }

    @Override
    public List<PageData> intentUserPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("outerCallTaskService.intentUserlistPage",page);
    }

    @Override
    public String findcateByid(String id) throws Exception {
        return (String) dao.findForObject("outerCallTaskService.findcateByid", id);
    }
}
