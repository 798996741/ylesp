package com.yulun.service;

import com.fh.entity.Page;
import com.fh.util.PageData;

import java.util.List;

/**
 * @Author Aliar
 * @Time 2020-05-21 11:47
 **/
public interface PersonCountManager {
    PageData jobregCount(PageData pd) throws Exception;

    PageData recruitCount(PageData pd)  throws Exception;

    List<PageData> sexCount(Page page) throws Exception;

    List<PageData> ageCount(Page page) throws Exception;;

    List<PageData> xlCount(Page page) throws Exception;

    List<PageData> jobCount(Page page) throws Exception;

    List<PageData> getDicName(String parentId) throws Exception;


}
