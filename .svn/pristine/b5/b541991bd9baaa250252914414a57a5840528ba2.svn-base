package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.yulun.service.SignupManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Aliar
 * @Time 2020-05-10 21:48
 **/
@Service("signupService")
public class SignupService implements SignupManager {
    @Resource(name = "daoSupport")
    private DaoSupport dao;

    @Override
    public List<PageData> findAlllistPage(Page page) throws Exception {
        System.out.println(page.getPd());
        return (List<PageData>) dao.findForList("signupMapper.findAlllistPage",page);
    }

    @Override
    public List<PageData> findSignuplistPage(Page page) throws Exception {
        return (List<PageData>) dao.findForList("signupMapper.findAlllistPage",page);
    }
}
