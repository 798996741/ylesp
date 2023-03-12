package com.yulun.service;

import com.fh.util.PageData;

import java.util.List;

public interface ComplainManager {
    public List<PageData> getData(PageData pd) throws Exception;
    public List<PageData> getArea(PageData pd) throws Exception;
    public List<PageData> getDictionaries(PageData pd) throws Exception;
    public List<PageData> getDictionaries2(PageData pd) throws Exception;
    public List<PageData> getgdzlmonth(PageData pd) throws Exception;
    public List<PageData> getgdzlday(PageData pd) throws Exception;
    public List<PageData> getgdzlyear(PageData pd) throws Exception;
    public PageData getcfts(PageData pd) throws Exception;
    public PageData getzgds(PageData pd) throws Exception;
    public List<PageData> getkscl(PageData pd) throws Exception;
    public List<PageData> getpjsx(PageData pd) throws Exception;
    public List<PageData> getmaxclsj(PageData pd) throws Exception;
    public List<PageData> getminclsj(PageData pd) throws Exception;
    public List<PageData> getzcll(PageData pd) throws Exception;
    public List<PageData> getcsgd(PageData pd) throws Exception;
    public List<PageData> getdcll(PageData pd) throws Exception;
    public List<PageData> getcftsmonth(PageData pd) throws Exception;
    public List<PageData> getcftsday(PageData pd) throws Exception;
    public List<PageData> getcftsyear(PageData pd) throws Exception;
    public List<PageData> getksclmonth(PageData pd) throws Exception;
    public List<PageData> getksclday(PageData pd) throws Exception;
    public List<PageData> getksclyear(PageData pd) throws Exception;
}
