package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

public interface OuterCallTaskManager {
    List<PageData> findAllDictionariesPage(PageData pd) throws Exception;

    List<PageData> listPage(Page page) throws Exception;

    void deleteTask(PageData pd) throws Exception;

    void pauseTask(PageData pd)throws Exception;

    void startTask(PageData pd)throws Exception;

    List<PageData> findcompanylistPage(PageData pd) throws Exception;

    List<PageData> findCatelistPage(PageData pd) throws Exception;

    void insertTask(PageData pd) throws Exception;

    void insertTaskCallUser(PageData data1) throws Exception;

    PageData selectOneById(PageData pd) throws Exception;

    List<PageData> selectOutboundData(PageData pd) throws Exception;

    void editTask(PageData pd) throws Exception;

    void replayUnCallUser(PageData pd) throws Exception;

    PageData selectOneDetailById(PageData pd) throws Exception;

    List<PageData> taskDetailPage(Page page) throws Exception;

    List<PageData> taskDetailAll(PageData pd) throws Exception;

    PageData taskIntentAnalysisData(PageData pd) throws Exception;

    List<PageData> taskNameAnalysisData(PageData pd) throws Exception;

    List<PageData> intentUserPage(Page page) throws Exception;

    String findcateByid(String id) throws Exception;
}
