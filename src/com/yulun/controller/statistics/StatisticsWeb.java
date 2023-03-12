package com.yulun.controller.statistics;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.entity.Statistics;
import com.yulun.service.StatisticsManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StatisticsWeb implements CommonIntefate {

    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;
    @Resource(name = "statisticsService")
    private StatisticsManager statisticsManager;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject object = new JSONObject();
        try {
            PageData pd = new PageData();
            Page page = new Page();
            String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
            PageData pd_stoken = new PageData();
            JSONObject json = data.getJSONObject("data");
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                if (cmd.equals("zxgrbb")) {
                    HashMap<String, Statistics> map = new HashMap<>();
                    PageData pageData1 = new PageData();
                    pageData1.put("zxid", json.getString("zxid"));
                    List<PageData> getxzid = statisticsManager.getxzid(pageData1);
                    for (PageData xzid : getxzid) {
                        map.put(xzid.getString("zxid"), new Statistics());
                        System.out.println(xzid.getString("zxid"));
                    }
                    pd.put("starttime", json.getString("startTime"));
                    pd.put("endtime", json.getString("endTime"));
                    pd.put("thfx", "0");
                    pd.put("zxid", json.getString("zxid"));

                    List<PageData> ldllist = statisticsManager.zxCallNumCount(pd);
                    List<PageData> hrzsclist = statisticsManager.zxCallNumSum(pd);

                    PageData pd2 = new PageData();
                    pd2.put("starttime", json.getString("startTime"));
                    pd2.put("endtime", json.getString("endtime"));
                    pd2.put("thfx", "0");
                    pd2.put("inmark", "0");
                    pd2.put("zxid", json.getString("zxid"));
                    List<PageData> nbhjsllist = statisticsManager.zxCallNumCount(pd2);
                    List<PageData> nbhjzsclist = statisticsManager.zxCallNumSum(pd2);

                    PageData pd3 = new PageData();
                    pd3.put("starttime", json.getString("startTime"));
                    pd3.put("endtime", json.getString("endtime"));
                    pd3.put("thfx", "1");
                    pd3.put("zxid", json.getString("zxid"));
                    List<PageData> wbhjzllist = statisticsManager.zxCallNumCount(pd3);
                    List<PageData> wbhjzsclist = statisticsManager.zxCallNumSum(pd3);

                    PageData pd4 = new PageData();
                    pd4.put("starttime", json.getString("startTime"));
                    pd4.put("endtime", json.getString("endtime"));
                    pd4.put("zxid", json.getString("zxid"));
                    List<PageData> thzsclist = statisticsManager.zxCallNumSum(pd4);

                    List<PageData> getcgjtl = statisticsManager.getcgjtl(pd4);
                    List<PageData> getxlfql = statisticsManager.getxlfql(pd4);

                    for (Map.Entry<String, Statistics> entry : map.entrySet()) {
                        if (ldllist.size() == 1 && null == ldllist.get(0) || ldllist.size() == 0) {
                            entry.getValue().setLdl("0");

                        } else {
                            for (PageData pageDatum : ldllist) {
                                if (entry.getKey().equals(pageDatum.get("zxid").toString())) {
                                    entry.getValue().setLdl(pageDatum.get("callnum").toString() == "" ? "0" : pageDatum.get("callnum").toString());
                                } else if (entry.getValue().getLdl() == null || entry.getValue().getLdl() == "") {
                                    entry.getValue().setLdl("0");
                                }
                            }
                        }

                        if (hrzsclist.size() == 1 && null == hrzsclist.get(0) || hrzsclist.size() == 0) {
                            entry.getValue().setHrzsc("0");

                        } else {
                            for (PageData pageData : hrzsclist) {
                                if (entry.getKey().equals(pageData.get("zxid").toString())) {
                                    entry.getValue().setHrzsc(pageData.get("thnum").toString() == "" ? "0" : pageData.get("thnum").toString());
                                } else if (entry.getValue().getHrzsc() == null || entry.getValue().getHrzsc() == "") {
                                    entry.getValue().setHrzsc("0");
                                }
                            }
                        }

                        if (nbhjsllist.size() == 1 && null == nbhjsllist.get(0) || nbhjsllist.size() == 0) {
                            entry.getValue().setNbhjsl("0");

                        } else {
                            for (PageData pageData : nbhjsllist) {
                                if (entry.getKey().equals(pageData.get("zxid").toString())) {
                                    entry.getValue().setNbhjsl(pageData.get("callnum").toString() == "" ? "0" : pageData.get("callnum").toString());
                                } else if (entry.getValue().getNbhjsl() == null || entry.getValue().getNbhjsl() == "") {
                                    entry.getValue().setNbhjsl("0");
                                }
                            }
                        }
                        if (nbhjzsclist.size() == 1 && null == nbhjzsclist.get(0) || nbhjzsclist.size() == 0) {
                            entry.getValue().setNbhjzsc("0");
                        } else {
                            for (PageData pageData : nbhjzsclist) {
                                if (entry.getKey().equals(pageData.get("zxid").toString())) {
                                    entry.getValue().setNbhjzsc(pageData.get("thnum").toString() == "" ? "0" : pageData.get("thnum").toString());
                                } else if (entry.getValue().getNbhjzsc() == null || entry.getValue().getNbhjzsc() == "") {
                                    entry.getValue().setNbhjzsc("0");
                                }
                            }
                        }

                        if (wbhjzllist.size() == 1 && null == wbhjzllist.get(0) || wbhjzllist.size() == 0) {
                            entry.getValue().setWbhjl("0");

                        } else {
                            for (PageData pageData : wbhjzllist) {
                                if (entry.getKey().equals(pageData.get("zxid").toString())) {
                                    entry.getValue().setWbhjl(pageData.get("callnum").toString() == "" ? "0" : pageData.get("callnum").toString());
                                } else if (entry.getValue().getWbhjl() == null || entry.getValue().getWbhjl() == "") {
                                    entry.getValue().setWbhjl("0");
                                }
                            }
                        }

                        if (wbhjzsclist.size() == 1 && null == wbhjzsclist.get(0) || wbhjzsclist.size() == 0) {
                            entry.getValue().setWbhjzsc("0");

                        } else {
                            for (PageData pageData : wbhjzsclist) {
                                if (entry.getKey().equals(pageData.get("zxid").toString())) {
                                    entry.getValue().setWbhjzsc(pageData.get("thnum").toString() == "" ? "0" : pageData.get("thnum").toString());
                                } else if (entry.getValue().getWbhjzsc() == null || entry.getValue().getWbhjzsc() == "") {
                                    entry.getValue().setWbhjzsc("0");
                                }
                            }
                        }

                        if (thzsclist.size() == 1 && null == thzsclist.get(0) || thzsclist.size() == 0) {
                            entry.getValue().setThzsc("0");

                        } else {
                            for (PageData pageData : thzsclist) {
                                if (entry.getKey().equals(pageData.get("zxid").toString())) {
                                    entry.getValue().setThzsc(pageData.get("thnum").toString() == "" ? "0" : pageData.get("thnum").toString());
                                } else if (entry.getValue().getThzsc() == null || entry.getValue().getThzsc() == "") {
                                    entry.getValue().setThzsc("0");
                                }
                            }
                        }

                        if (getcgjtl.size() == 1 && null == getcgjtl.get(0) || getcgjtl.size() == 0) {
                            entry.getValue().setChjtl("0");
                        } else {
                            for (PageData pageData : getcgjtl) {
                                if (entry.getKey().equals(pageData.get("zxid").toString())) {
                                    entry.getValue().setChjtl(pageData.get("cgjtl").toString());
                                } else {
                                    entry.getValue().setChjtl("0");
                                }
                            }
                        }

                        if (getxlfql.size() == 1 && null == getxlfql.get(0) || getxlfql.size() == 0) {
                            entry.getValue().setXlfql("0");
                        } else {
                            for (PageData pageData : getcgjtl) {
                                for (PageData pageData2 : getxlfql) {
                                    if (entry.getKey().equals(pageData.get("zxid").equals(pageData2.get("zxid").toString()))) {
                                        int i = Integer.parseInt(pageData2.get("xlfql").toString()) - Integer.parseInt(pageData.get("cgjtl").toString());
                                        entry.getValue().setXlfql(i + "");
                                    } else {
                                        entry.getValue().setXlfql("0");
                                    }
                                }
                            }
                        }

                    }
                    String starttime = json.getString("startTime");
                    String endtime = json.getString("endTime");
                    object.put("time", starttime.substring(11, starttime.length()) + " " + endtime.substring(11, endtime.length()));
                    object.put("day", starttime.substring(0, 11) + " " + endtime.substring(0, 11));
                    object.put("data", map);
                    object.put("success", "true");
                    System.out.println(map.toString());
                    System.out.println(map.get("601"));
                } else if (cmd.equals("zxOpeLog")) {
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("starttime", json.getString("startTime"));
                    pd.put("endtime", json.getString("endtime"));
                    pd.put("zxid", json.getString("zxid"));
                    page.setPd(pd);
                    List<PageData> zxopeloglistPage = statisticsManager.zxopeloglistPage(page);
                    object.put("data", zxopeloglistPage);
                    object.put("success", "true");
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                } else if (cmd.equals("zxCallLog")) {
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("starttime", json.getString("startTime"));
                    pd.put("endtime", json.getString("endtime"));
                    pd.put("zxid", json.getString("zxid"));
                    pd.put("zjhm", json.getString("zjhm"));
                    pd.put("thfx", json.getString("thfx"));
                    page.setPd(pd);
                    List<PageData> zxcallloglistPage = statisticsManager.zxcallloglistPage(page);
                    object.put("data", zxcallloglistPage);
                    object.put("success", "true");
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                } else if (cmd.equals("MYDTJ")) {
                    String way = json.getString("way");
                    String starttime = json.getString("startTime");
                    String endtime = json.getString("endTime");
                    if (starttime.equals(null) || starttime.equals("")) {
                        starttime = getTime() + "-01";
                    }
                    if (endtime.equals(null) || endtime.equals("")) {
                        endtime = getTime() + "-31";
                    }
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("starttime", starttime);
                    pd.put("endtime", endtime);
                    pd.put("way", way);
                    page.setPd(pd);
                    if (way.equals("zxid")) {
                        List<PageData> list = statisticsManager.MYDTJlistPage(page);

                        object.put("data", list);
                        object.put("success", "true");
                        object.put("pageId",pageIndex);
                        object.put("pageCount",page.getTotalPage());
                        object.put("recordCount",page.getTotalResult());
                    } else if (way.equals("day") || way.equals("week") || way.equals("month") || way.equals("year")) {
                        List<PageData> list = statisticsManager.timeMYDTJlistPage(page);

                        object.put("data", changetime(starttime, endtime, way, list));
                        object.put("success", "true");
                        object.put("pageId",pageIndex);
                        object.put("pageCount",page.getTotalPage());
                        object.put("recordCount",page.getTotalResult());
                    }
                } else if (cmd.equals("ZXHWTJ")) {

                    String way = json.getString("way");
                    String starttime = json.getString("startTime");
                    String endtime = json.getString("endTime");
                    if (starttime.equals(null) || starttime.equals("")) {
                        starttime = getTime() + "-01";
                    }
                    if (endtime.equals(null) || endtime.equals("")) {
                        endtime = getTime() + "-31";
                    }
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("starttime", starttime);
                    pd.put("endtime", endtime);
                    pd.put("way", way);
                    pd.put("zxid", json.getString("zxid"));
                    page.setPd(pd);
                    System.out.println(way);
                    if (way.equals("zxid")) {
                        List<PageData> list = statisticsManager.ZXHWTJlistPage(page);
                        object.put("data", list);
                        object.put("success", "true");
                        object.put("pageId",pageIndex);
                        object.put("pageCount",page.getTotalPage());
                        object.put("recordCount",page.getTotalResult());
                    } else if (way.equals("day") || way.equals("week") || way.equals("month") || way.equals("year")) {
                        List<PageData> list = statisticsManager.timeZXHWTJlistPage(page);

                        object.put("data", changetime(starttime, endtime, way, list));
                        object.put("success", "true");
                        object.put("pageId",pageIndex);
                        object.put("pageCount",page.getTotalPage());
                        object.put("recordCount",page.getTotalResult());
                    }

                } else if (cmd.equals("XTHWTJ")) {
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    String starttime = json.getString("startTime");
                    String endtime = json.getString("endTime");
                    String way = json.getString("way");
                    if (starttime.equals(null) || starttime.equals("")) {
                        starttime = getTime() + "-01";
                    }
                    if (endtime.equals(null) || endtime.equals("")) {
                        endtime = getTime() + "-31";
                    }
                    pd.put("starttime", starttime);
                    pd.put("endtime", endtime);
                    pd.put("way", json.getString("way"));
                    page.setPd(pd);
                    List<PageData> list = statisticsManager.XTHWTJlistPage(page);
                    object.put("data", changetime(starttime, endtime, way, list));
                    object.put("success", "true");
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                }
            } else {
                object.put("success", "false");
                object.put("msg", "登录超时，请重新登录");
            }
        } catch (Exception e) {
            object.put("success", "false");
            object.put("msg", "异常");
        }
        return object;
    }

    public ArrayList<PageData> judgetime(String starttime, String endtime, String way) throws ParseException {

        Integer startmonth = Integer.parseInt(starttime.substring(5, 7));
        Integer endmonth = Integer.parseInt(endtime.substring(5, 7));
        Integer startyear = Integer.parseInt(starttime.substring(0, 4));
        Integer endyear = Integer.parseInt(endtime.substring(0, 4));
        Integer startday = Integer.parseInt(starttime.substring(8, 10));
        Integer endday = Integer.parseInt(endtime.substring(8, 10));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = format.parse(starttime);
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date1);
        Integer weekNumbe1 = calendar.get(Calendar.WEEK_OF_YEAR);
        Date date2 = format.parse(endtime);
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setFirstDayOfWeek(Calendar.MONDAY);
        calendar1.setTime(date2);
        Integer weekNumbe2 = calendar1.get(Calendar.WEEK_OF_YEAR);
        ArrayList<PageData> strings = new ArrayList<>();
        if (way.equals("day")) {
            Integer day = endday - startday;
            for (int i = startday; i <= endday; i++) {
                PageData pageData = new PageData();
                pageData.put("time", i);
                strings.add(pageData);
            }
        } else if (way.equals("month")) {
            Integer month = endmonth - startmonth;
            for (int i = startmonth; i <= endmonth; i++) {
                PageData pageData = new PageData();
                pageData.put("time", i);
                strings.add(pageData);
            }
        } else if (way.equals("year")) {
            Integer year = endyear - startyear;
            for (int i = startyear; i <= endyear; i++) {
                PageData pageData = new PageData();
                pageData.put("time", i);
                strings.add(pageData);
            }
        } else if (way.equals("week")) {
            int week = weekNumbe2 - 1 - weekNumbe1 + 1;
            System.out.println(weekNumbe2);
            System.out.println(weekNumbe1);
            for (int i = weekNumbe1; i <= weekNumbe2; i++) {
                PageData pageData = new PageData();
                pageData.put("time", i);
                strings.add(pageData);
            }
        }
        return strings;
    }

    public List<PageData> changetime(String starttime, String endtime, String way, List<PageData> list) {

        if (way.equals("day")) {
            for (PageData pageData : list) {
                pageData.put("time", starttime.substring(0, 7) + "-" + Integer.parseInt(pageData.get("time").toString()));
            }
        } else if (way.equals("month")) {
            for (PageData pageData : list) {
                pageData.put("time", starttime.substring(8, 10) + "-" + Integer.parseInt(pageData.get("time").toString()));
            }
        } else if (way.equals("week")) {

        }
        return list;
    }

    public String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        return dateFormat.format(date);
    }
}
