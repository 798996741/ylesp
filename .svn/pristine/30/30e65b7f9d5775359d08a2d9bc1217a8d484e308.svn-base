package com.yulun.controller.count;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.PersonCountManager;
import com.yulun.service.ServiceCountManager;
import com.yulun.utils.TimeHandle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Aliar
 * @Time 2020-05-25 17:51
 **/
public class ServiceCountWeb implements CommonIntefate {
        static final String SUCCESS = "success";
        static final String MSG = "msg";
        static final String FALSE = "false";
        static final String TRUE = "true";
        Integer pageIndex;
        Integer pageSize;

        @Resource(name = "zxlbService")
        private ZxlbManager zxlbService;

        @Resource(name = "serviceCountService")
        private ServiceCountManager serviceCountService;


        @Override
        public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
                JSONObject object = new JSONObject();
                PageData pd = new PageData();
                String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
                JSONObject json = data.getJSONObject("data");
                try {

                        if ("serviceCount".equals(cmd)) {
                                String startTime = json.getString("startTime");
                                String endTime = json.getString("endTime");
                                String timeSlot = json.getString("timeSlot");
                                // 判空  等于1才能正常运行
                                double isNull = 0d;
                                if (timeSlot != null && !"".equals(timeSlot)) {
                                        isNull += 1d;
                                }
                                if (startTime != null && !"".equals(startTime)) {
                                        isNull += 0.5d;
                                }
                                if (endTime != null && !"".equals(endTime)) {
                                        isNull += 0.5d;
                                }
                                Page page = new Page();
                                page.setShowCount(50);
                                page.setCurrentPage(1);
                                if (isNull < 1d) {
                                        object.put(SUCCESS, FALSE);
                                        object.put(MSG, "StartTime and endTime or timeSlot is NULL");
                                        return object;
                                } else if (isNull == 2d || isNull == 1.5d) {
                                        object.put(SUCCESS, FALSE);
                                        object.put(MSG, "StartTime and endTime and timeSlot Can't together");
                                        return object;
                                }
                                // 传值
                                if (timeSlot == null || "".equals(timeSlot)) {
                                        pd.put("startTime", startTime);
                                        pd.put("endTime", TimeHandle.endTimeHeandle(endTime));
                                } else {
                                        Map<String, String> time = TimeHandle.getCountTime(timeSlot);
                                        String msg = time.get("msg");
                                        if (msg != null && !"".equals(msg)) {
                                                object.put(MSG, msg);
                                                object.put(SUCCESS, FALSE);
                                                return object;
                                        }
                                        startTime = time.get("startTime");
                                        endTime = time.get("endTime");
                                        pd.put("startTime", startTime);
                                        pd.put("endTime", endTime);
                                }
                                page.setPd(pd);
                                PageData todayPage = new PageData();
                                PageData yesterdayPage = new PageData();
                                todayPage.put("endTime",TimeHandle.getTodayTime());
                                yesterdayPage.put("endTime",TimeHandle.getYesterdayTime());
                              //业务总量
                               PageData serviceCount =  serviceCountService.serviceCount(pd);
                               pd.putAll(serviceCount);
                               //业务总量新增数
                                PageData todayPageDataNum = serviceCountService.serviceCount(todayPage);
                                PageData yesterdayPageNum = serviceCountService.serviceCount(yesterdayPage);
                                String serviceCountNew = todayPageDataNum.get("serviceCount").toString();
                                String serviceCountOld = yesterdayPageNum.get("serviceCount").toString();
                                int serviceCountChange = 0;
                                if(serviceCountNew != null && !"".equals(serviceCountNew) && serviceCountOld!=null && !"".equals(serviceCountOld))
                                        serviceCountChange = Integer.parseInt(serviceCountNew) - Integer.parseInt(serviceCountOld);
                                pd.put("serviceCountChange",serviceCountChange);
                                //政策热度排行
                                List<PageData> policyRank =  serviceCountService.policyRank(pd);
                                Map policyRankMap = new HashMap();
                                List<String> policyRankList = new ArrayList<String>();
                                List<PageData> policyRankPd = new ArrayList<PageData>();
                                for (PageData pageData : policyRank) {
                                        String name = pageData.getString("name");
                                        String value = pageData.get("value").toString();
                                        if (name != null && !"".equals(name) && value != null && !"".equals(value)) {
                                                policyRankList.add(name);
                                                policyRankPd.add(pageData);
                                        }
                                }
                                policyRankMap.put("dataText", policyRankList);
                                policyRankMap.put("data", policyRankPd);
                                pd.put("policyRank", policyRankMap);
                                //政策热度饼图：

                                List<PageData> policyProportion =  serviceCountService.policyProportion(pd);
                                Map policyProportionMap = new HashMap();
                                List<String> policyProportionList = new ArrayList<String>();
                                List<PageData> policyProportionPd = new ArrayList<PageData>();
                                for (PageData pageData : policyProportion) {
                                        String name = pageData.getString("name");
                                        String value = pageData.get("value").toString();
                                        if (name != null && !"".equals(name) && value != null && !"".equals(value)) {
                                                policyProportionList.add(name);
                                                policyProportionPd.add(pageData);
                                        }
                                }
                                policyProportionMap.put("dataText", policyProportionList);
                                policyProportionMap.put("data", policyProportionPd);
                                pd.put("policyProportion", policyProportionMap);
                                //就业相关服务占比
                                List<PageData> employProportion =  serviceCountService.employProportion(pd);
                                Map employProportionMap = new HashMap();
                                List<String> employProportionList = new ArrayList<String>();
                                List<PageData> employProportionPd = new ArrayList<PageData>();
                                for (PageData pageData : employProportion) {
                                        String name = pageData.getString("name");
                                        String value = pageData.get("value").toString();
                                        if (name != null && !"".equals(name) && value != null && !"".equals(value)) {
                                                employProportionList.add(name);
                                                employProportionPd.add(pageData);
                                        }
                                }
                                employProportionMap.put("dataText", employProportionList);
                                employProportionMap.put("data",employProportionPd);
                                pd.put("employProportion", employProportionMap);
                                //业务引导
                                List<PageData> ywydCount =  serviceCountService.ywydCount(pd);
                                Map ywydMap = new HashMap();
                                List<String> ywydList = new ArrayList<String>();
                                List<PageData> ywydPd = new ArrayList<PageData>();
                                for (PageData pageData : ywydCount) {
                                        String name = pageData.getString("name");
                                        String value = pageData.get("value").toString();
                                        if (name != null && !"".equals(name) && value != null && !"".equals(value)) {
                                                ywydList.add(name);
                                                ywydPd.add(pageData);
                                        }
                                }
                                ywydMap.put("dataText", ywydList);
                                ywydMap.put("data",ywydPd);
                                pd.put("ywydCount", ywydMap);


                                object.put("data",pd);

                        }

                } catch (Exception e) {
                        object.put(SUCCESS, FALSE);
                        object.put(MSG, "数据异常");

                } finally {
                        return object;
                }
        }
}
