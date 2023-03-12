package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

/**
 * @Author Aliar
 * @Time 2020-05-13 17:10
 **/
public interface EmPersonManager {
    List<PageData> findAlllistPage(Page page) throws Exception;
    List<PageData> findAll(PageData page) throws Exception;


    List<PageData> findEmCaseByUidlistPage(Page page) throws Exception;
}
