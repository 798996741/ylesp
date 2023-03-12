package com.yulun.service.impl;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import com.yulun.service.AgentStateManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("agentStateService")
public class AgentStateService implements AgentStateManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Override
	public List<PageData> getZXByState(PageData pd) throws Exception {
		
		return (List<PageData>) dao.findForList("ZXstateMapper.getNumberByState",pd);
	}
	@Override
	public List<PageData> getZXByStateLike(PageData pd) throws Exception {
		
		return (List<PageData>) dao.findForList("ZXstateMapper.getNumberByStateLike",pd);
	}

}
