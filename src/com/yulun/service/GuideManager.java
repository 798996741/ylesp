package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

/**
 * @Author Aliar
 * @Time 2020-05-10 16:08
 **/
public interface GuideManager {
    List<PageData> findAlllistPage(Page page) throws Exception;


    List<PageData> findByUidlistPage(Page page) throws Exception;

    List<PageData> findSummarylistPage(Page page) throws Exception;
}
