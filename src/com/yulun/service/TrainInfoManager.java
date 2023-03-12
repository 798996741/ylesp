package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

/**
 * @Author Aliar
 * @Time 2020-05-08 10:39
 **/
public interface TrainInfoManager {
    List<PageData> findAlllistPage(Page page) throws Exception;


    void deleteById(PageData pageData) throws Exception;

    List<PageData> findById(PageData pageData) throws Exception;
    List<PageData> findExit(PageData pageData) throws Exception;

    Object save(List<PageData> pd) throws Exception;

    Object update(PageData pd) throws Exception;


}
