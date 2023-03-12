package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import com.yulun.service.ServiceCountManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Aliar
 * @Time 2020-05-25 17:53
 **/
@Service("serviceCountService")
public class ServiceCountService implements ServiceCountManager {
        @Resource(name = "daoSupport")
        private DaoSupport dao;

        @Override
        public PageData serviceCount(PageData pd) throws Exception {
                return (PageData) dao.findForObject("serviceCountMapper.serviceCount",pd);
        }

        @Override
        public List<PageData> policyRank(PageData pd) throws Exception {
                return (List<PageData>) dao.findForList("serviceCountMapper.policyRank",pd);
        }

        @Override
        public List<PageData> policyProportion(PageData pd) throws Exception {
                return (List<PageData>) dao.findForList("serviceCountMapper.policyProportion",pd);
        }

        @Override
        public List<PageData> employProportion(PageData pd) throws Exception {
                return (List<PageData>) dao.findForList("serviceCountMapper.employProportion",pd);
        }

        @Override
        public List<PageData> ywydCount(PageData pd) throws Exception {
                return (List<PageData>) dao.findForList("serviceCountMapper.ywydCount",pd);
        }
}
