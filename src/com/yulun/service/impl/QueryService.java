package com.yulun.service.impl;

import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.QueryManager;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Aliar
 * @Time 2020-05-22 14:31
 **/
@Service("queryService")
public class QueryService implements QueryManager {

        @Resource(name = "daoSupport")
        private DaoSupport dao;

        @Override
        public List<PageData> findCatelistPage(Page page) throws Exception {
                return (List<PageData>) dao.findForList("queryMapper.catelistPage",page);
        }

        @Override
        public List<PageData> findcompanylistPage(Page page) throws Exception {
                return (List<PageData>) dao.findForList("queryMapper.companylistPage",page);
        }
}
