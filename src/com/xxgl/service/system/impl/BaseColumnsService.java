package com.xxgl.service.system.impl;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.system.BaseColumnsManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("baseColumnsService")
public class BaseColumnsService implements BaseColumnsManager {

    @Resource(name = "daoSupport")
    private DaoSupport dao;

    /**修改
     * @param pd
     * @throws Exception
     */
    public void edit(PageData pd)throws Exception{
        dao.update("BaseColumnsMapper.edit", pd);
    }

    /**通过id获取数据
     * @param pd
     * @throws Exception
     */
    public PageData findById(PageData pd)throws Exception{
        return (PageData)dao.findForObject("BaseColumnsMapper.findById", pd);
    }

    /**通过父节点id获取数据
     * @param pid
     * @throws Exception
     */
    public List<PageData> findByParentId(String pid)throws Exception{
        return (List<PageData>)dao.findForList("BaseColumnsMapper.findByParentId", pid);
    };

    public void save(PageData pd) throws Exception{
        dao.update("BaseColumnsMapper.save",pd);
    }
}
