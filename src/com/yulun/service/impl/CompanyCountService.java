package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.CompanyCountManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Aliar
 * @Time 2020-05-21 17:15
 **/
@Service("companyCountService")
public class CompanyCountService implements CompanyCountManager {

        @Resource(name = "daoSupport")
        private DaoSupport dao;

        @Override
        public PageData companyCount(PageData pd) throws Exception {
                return (PageData) dao.findForObject("companyCountMapper.companyCount",pd);
        }

        @Override
        public PageData recruitPeopleCount(PageData pd) throws Exception {
                return (PageData) dao.findForObject("companyCountMapper.recruitPeopleCount",pd);
        }

        @Override
        public List<PageData> jobCount(Page page) throws Exception {
                return (List<PageData>) dao.findForList("companyCountMapper.jobCount",page);
        }

        @Override
        public List<PageData> recruitProportion(Page page) throws Exception {
                return (List<PageData>) dao.findForList("companyCountMapper.recruitProportion",page);
        }

        @Override
        public List<PageData> employregCount(List<PageData> list) throws Exception {
                return (List<PageData>) dao.findForList("companyCountMapper.employregCount",list);
        }
}
