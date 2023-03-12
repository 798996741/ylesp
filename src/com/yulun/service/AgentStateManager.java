package com.yulun.service;

import com.fh.util.PageData;

import java.util.List;

public interface AgentStateManager {

	List<PageData> getZXByState(PageData pd) throws Exception;
	
	List<PageData> getZXByStateLike(PageData pd) throws Exception;
}
