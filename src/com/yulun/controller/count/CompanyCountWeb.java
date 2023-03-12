package com.yulun.controller.count;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.CompanyCountManager;
import com.yulun.service.PersonCountManager;
import com.yulun.utils.ListUtil;
import com.yulun.utils.TimeHandle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

/**
 * @Author Aliar
 * @Time 2020-05-21 17:12
 **/
public class CompanyCountWeb implements CommonIntefate {
        static final String SUCCESS = "success";
        static final String MSG = "msg";
        static final String FALSE = "false";
        static final String TRUE = "true";
        Integer pageIndex;
        Integer pageSize;

        @Resource(name = "zxlbService")
        private ZxlbManager zxlbService;


        @Resource(name = "companyCountService")
        private CompanyCountManager companyCountService;

        @Override
        public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
                JSONObject object = new JSONObject();
                PageData pd = new PageData();
                String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
                JSONObject json = data.getJSONObject("data");
                try {
                        if ("companyCount".equals(cmd)) {
                                String startTime = json.getString("startTime");
                                String endTime = json.getString("endTime");
                                String timeSlot = json.getString("timeSlot");
                                // 判空  等于1才能正常运行
                                double isNull = 1d;
//                                if (timeSlot != null && !"".equals(timeSlot)) {
//                                        isNull += 1d;
//                                }
//                                if (startTime != null && !"".equals(startTime)) {
//                                        isNull += 0.5d;
//                                }
//                                if (endTime != null && !"".equals(endTime)) {
//                                        isNull += 0.5d;
//                                }
                                Page page = new Page();
                                page.setShowCount(5);
                                page.setCurrentPage(1);
                                if (isNull < 1d) {
                                        object.put(SUCCESS, FALSE);
                                        object.put(MSG, "StartTime and endTime or timeSolit is NULL");
                                        return object;
                                } else if (isNull == 2d || isNull == 1.5d) {
                                        object.put(SUCCESS, FALSE);
                                        object.put(MSG, "StartTime and endTime and timeSolit Can't together");
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
                                PageData nullPagedate = new PageData();
                                //企业总数
                                PageData companyCount = companyCountService.companyCount(pd);
                                pd.putAll(companyCount);
                                //用工推荐总数
                                PageData recruitPeopleCount = companyCountService.recruitPeopleCount(pd);
                                pd.putAll(recruitPeopleCount);
                                //企业总数新增
                                PageData todayTime = new PageData();
                                PageData yesterdayTime = new PageData();
                                todayTime.put("endTime", TimeHandle.getTodayTime());
                                yesterdayTime.put("endTime", TimeHandle.getYesterdayTime());
                                PageData todayCompanCount = companyCountService.companyCount(todayTime);
                                PageData yesterdayCompanCount = companyCountService.companyCount(yesterdayTime);
                                String companyCountNew = todayCompanCount.get("companyCount").toString();
                                String companyCountOld = yesterdayCompanCount.get("companyCount").toString();
                                Integer companyCountChange = 0;
                                if (companyCountNew != null && !"".equals(companyCountNew) && companyCountOld != null && !"".equals(companyCountOld)) {
                                        companyCountChange = Integer.valueOf(companyCountNew) -
                                                Integer.valueOf(companyCountOld);
                                }
                                pd.put("companyCountChange", companyCountChange);
                                //用工推荐新增
                                PageData todayRecruitPeopleCount = companyCountService.recruitPeopleCount(todayTime);
                                PageData YesterdayRecruitPeopleCount = companyCountService.recruitPeopleCount(yesterdayTime);
                                String recruitPeopleCountNew = todayRecruitPeopleCount.get("recruitPeopleCount").toString();
                                String recruitPeopleCountOld = YesterdayRecruitPeopleCount.get("recruitPeopleCount").toString();
                                Integer recruitPeopleChange = 0;
                                if (recruitPeopleCountNew != null && recruitPeopleCountOld != null && !"".equals(recruitPeopleCountNew) && !"".equals(recruitPeopleCountOld)) {
                                        recruitPeopleChange =
                                                Integer.valueOf(recruitPeopleCountNew) -
                                                        Integer.valueOf(recruitPeopleCountOld);
                                }
                                pd.put("recruitPeopleCountChange", recruitPeopleChange);
                                //岗位分布
                                List<PageData> jobCount = companyCountService.jobCount(page);
                                Map jobMap = new HashMap();
                                List<String> jobList = new ArrayList<String>();
                                List<PageData> jobPd = new ArrayList<PageData>();
                                for (PageData pageData : jobCount) {
                                        String name = pageData.getString("name");
                                        String value = pageData.get("value").toString();
                                        if (name != null && !"".equals(name) && value != null && !"".equals(value)) {
                                                jobList.add(name);
                                                jobPd.add(pageData);
                                        }
                                }
                                jobMap.put("dataText", jobList);
                                jobMap.put("data", jobPd);
                                pd.put("jobCount", jobMap);
                                //招工群体占比
                                List<PageData> recruitProportion = companyCountService.recruitProportion(page);
                                Map recruitMap = new HashMap();
                                List<String> recruitList = new ArrayList<String>();
                                List<PageData> recruitPd = new ArrayList<PageData>();
                                recruitList.add("不限学历");
                                recruitList.add("高校毕业生");
                                recruitList.add("低学历群体");
                                PageData otherPd = new PageData();
                                PageData gradePd = new PageData();
                                PageData lowPd = new PageData();
                                int otherCount = 0;
                                int gradeCount = 0;
                                int lowCount = 0;
                                for (PageData pageData : recruitProportion) {
                                        String name = pageData.getString("name");
                                        String value = pageData.get("value").toString();
                                        if (name != null && !"".equals(name) && value != null && !"".equals(value)) {
                                                if (name.equals("初中及以下") || name.equals("高中")) {
                                                        lowCount += Integer.valueOf(value);
                                                } else if (name.equals("大中专及以上") || name.equals("本科及以上") || name.equals("研究生及以上")){
                                                        gradeCount += Integer.valueOf(value);
                                                }else {
                                                        otherCount+=Integer.valueOf(value);
                                                }
                                        }
                                }
                                otherPd.put("name", "不限学历");
                                otherPd.put("value", otherCount);
                                gradePd.put("name", "高校毕业生");
                                gradePd.put("value", gradeCount);
                                lowPd.put("name", "低学历群体");
                                lowPd.put("value", lowCount);
                                recruitPd.add(otherPd);
                                recruitPd.add(gradePd);
                                recruitPd.add(lowPd);

                                recruitMap.put("dataText", recruitList);
                                recruitMap.put("data", recruitPd);
                                pd.put("recruitProportionCount", recruitMap);

                                //用工缺口人数
                                List<PageData> timeSoltAllDate = TimeHandle.getTimeSoltAllDate(startTime, endTime);
//                        List<List<PageData>> split = ListUtil.split(timeSoltAllDate, 2000);
//                        List<PageData> employregCount = new ArrayList<PageData>();
//                        for (List<PageData> pageData : split) {
//                                        if (pageData.size() >= 1) {
//                                                employregCount.addAll(companyCountService.employregCount(pageData));
//                                        }
//                        }
                                int size = timeSoltAllDate.size();
                                List<PageData> timeCount = new ArrayList<PageData>();
                                List<PageData> employregResult = new ArrayList<PageData>();
                                if (size > 30) {
                                        for (int i = (size - 30); i <= (size - 1); i++) {
                                                if (timeSoltAllDate.get(i) != null) {
                                                        timeCount.add(timeSoltAllDate.get(i));
                                                }
                                        }
                                        employregResult = companyCountService.employregCount(timeCount);
                                } else {
                                        employregResult = companyCountService.employregCount(timeSoltAllDate);
                                }

                                pd.put("emplyregCount", employregResult);

                                object.put("data", pd);
                        }
                } catch (Exception e) {
                        object.put(SUCCESS, FALSE);
                        object.put(MSG, "数据异常");
                } finally {
                        return object;
                }
        }
}
