package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

/**
 * @Author Aliar
 * @Time 2020-05-22 14:30
 **/
public interface QueryManager {
        List<PageData> findCatelistPage(Page page) throws Exception;

        List<PageData> findcompanylistPage(Page page) throws Exception;
}
