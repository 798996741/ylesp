package com.yulun.controller.statistics;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.BlacklistManager;
import com.yulun.service.DataAnalyzeManager;
import com.yulun.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DataAnalyzeWeb  implements CommonIntefate {

    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;
    @Resource(name = "dataAnalyzeService")
    private DataAnalyzeManager dataAnalyzeService;
    @Resource(name="blacklistService")
    private BlacklistManager blacklistService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject object = new JSONObject();
//        try {
            PageData pd = new PageData();
            Page page = new Page();
            String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
            PageData pd_stoken = new PageData();
            JSONObject json = data.getJSONObject("data");
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                String zxid = pd_token.getString("ZXID");
                if ("DataTraffic".equals(cmd)){
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    String zx = json.getString("zxid");
                    if (!"".equals(zx) && null!=zx){
                        pd.put("zxid",zx);
                    }
                    String startTime = json.getString("startTime");
                    String endTime = json.getString("endTime");
                    if (StringUtils.isNotEmpty(json.getString("startTime"))&&null!=json.getString("startTime")&&StringUtils.isNotEmpty(json.getString("endTime"))&&null!=json.getString("endTime")) {

                        if (startTime.equals(endTime)){
                            startTime=startTime+" 00:00:00";
                            endTime=endTime+" 23:59:59";
                        }
                    }
                    pd.put("starttime",startTime);
                    pd.put("endtime",endTime);
                    System.out.println(pd);
                    page.setPd(pd);
                    List<PageData> TrafficList = dataAnalyzeService.TrafficlistPage(page);
                    List<PageData> TrafficTitle = dataAnalyzeService.TrafficTitle(pd);
                    object.put("data",TrafficList);
                    object.put("dataTitle",TrafficTitle);
                    object.put("success", "true");
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                }else if ("DataRevrecord".equals(cmd)){
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("zxid",json.getString("zxid"));
                    String startTime = json.getString("startTime");
                    String endTime = json.getString("endTime");
                    if (StringUtils.isNotEmpty(json.getString("startTime"))&&null!=json.getString("startTime")&&StringUtils.isNotEmpty(json.getString("endTime"))&&null!=json.getString("endTime")) {

                        if (startTime.equals(endTime)){
                            startTime=startTime+" 00:00:00";
                            endTime=endTime+" 23:59:59";
                        }
                    }
                    pd.put("starttime",startTime);
                    pd.put("endtime",endTime);
                    page.setPd(pd);
                    List<PageData> RevrecordList = dataAnalyzeService.RevrecordlistPage(page);
                    List<PageData> RevrecordTitle = dataAnalyzeService.RevrecordTitle(pd);
                    System.out.println("RevrecordTitle"+RevrecordTitle);
                    object.put("data",RevrecordList);
                    object.put("dataTitle",RevrecordTitle);
                    object.put("success", "true");
                }else if ("DataRecruit".equals(cmd)){
                    pd.put("starttime",json.getString("startTime"));
                    pd.put("endtime",json.getString("endTime"));
                    List<PageData> recruitlist = dataAnalyzeService.xlrecruitlist(pd);
                    List<PageData> sexrecruitlist = dataAnalyzeService.sexrecruitlist(pd);
                    PageData agerecruitlist = dataAnalyzeService.agerecruitlist(pd);
                    PageData totalrecruit = dataAnalyzeService.totalrecruit(pd);
                    object.put("xldata",recruitlist);
                    object.put("sexdata",sexrecruitlist);
                    object.put("agedata",agerecruitlist);
                    object.put("totaldata",totalrecruit);

                    object.put("success", "true");
                }else if ("DataMonthLine".equals(cmd)){
                    PageData MonthCompanyLine = dataAnalyzeService.MonthCompanyLine(pd);
                    PageData MonthPersonLine = dataAnalyzeService.MonthPersonLine(pd);
                    object.put("CompanyLine",MonthCompanyLine);
                    object.put("PersonLine",MonthPersonLine);
                    object.put("success", "true");
                }else if ("DataTrack".equals(cmd)){
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("starttime",json.getString("startTime"));
                    pd.put("endtime",json.getString("endTime"));
                    pd.put("khpj",json.getString("khpj"));
                    pd.put("khpjname",json.getString("khpjname"));
                    page.setPd(pd);
                    List<PageData> pageData = dataAnalyzeService.DataTrack(pd);
                    List<PageData> list = dataAnalyzeService.TracklistPage(page);
                    if(list.size()>0) {
                        pd_token.put("param_code", "recordsvr");
                        PageData pdparam = blacklistService.findSysparam(pd_token);

                        for (PageData pdServerLog : list) {
                            if (pdServerLog != null) {
                                if (pdServerLog.get("lysj") != null && String.valueOf(pdServerLog.get("lysj")).length() > 10) {
                                    pdServerLog.put("lysj", String.valueOf(pdServerLog.get("lysj")).substring(0, 19));
                                }
                                if (pdServerLog != null && pdServerLog.getString("lywj") != null) {
                                    pdServerLog.put("lywjurl", pdparam.getString("param_value") + String.valueOf(pdServerLog.get("lysj")).substring(0, 10) + "/" + pdServerLog.getString("lywj"));
                                }
                            }
                        }
                    }
                    object.put("data", list);
                    object.put("DataTrac", pageData);
                    object.put("success", "true");
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                }else if ("PersonServer".equals(cmd)){
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("starttime",json.getString("startTime"));
                    pd.put("endtime",json.getString("endTime"));
                    pd.put("zxid",json.getString("zxid"));
                    page.setPd(pd);
                    List<PageData> list = dataAnalyzeService.PersonServerlistPage(page);
                    object.put("data", list);
                    object.put("success", "true");
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                }else if ("locallist".equals(cmd)){
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("starttime",json.getString("startTime"));
                    pd.put("endtime",json.getString("endTime"));
                    pd.put("city",json.getString("city"));
                    page.setPd(pd);
                    List<PageData> list = dataAnalyzeService.LocallistPage(page);
                    object.put("data", list);
                    object.put("success", "true");
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                }else if ("holidaylist".equals(cmd)){
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    page.setPd(pd);
                    List<PageData> list = dataAnalyzeService.findholiday(page);
                    object.put("data", list);
                    object.put("success", "true");
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                }else if ("iuholiday".equals(cmd)){
                    String id = json.getString("id");
                    pd.put("type",json.getString("type"));
                    pd.put("starttime",json.getString("startTime"));
                    pd.put("endtime",json.getString("endTime"));
                    pd.put("czdate",getTime());
                    pd.put("czman",zxid);
                    if (StringUtils.isNotEmpty(id)) {
                        pd.put("id",id);
                        dataAnalyzeService.updateholiday(pd);
                    }else {
                        pd.put("id",getUUID32());
                        dataAnalyzeService.insertholiday(pd);
                    }
                    object.put("success", "true");
                }else if ("deleteholiday".equals(cmd)){
                    pd.put("id",json.getString("id"));
                    dataAnalyzeService.deleteholiday(pd);
                    object.put("success", "true");
                }else if ("consult".equals(cmd)){
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("starttime",json.getString("startTime"));
                    pd.put("endtime",json.getString("endTime"));
                    pd.put("keywords",json.getString("keywords"));
                    page.setPd(pd);
                    List<PageData> ConsultTitle = dataAnalyzeService.ConsultTitle(pd);
                    List<PageData> Consultlist = dataAnalyzeService.ConsultlistPage(page);
                    object.put("data",Consultlist);
                    object.put("dataTitle",ConsultTitle);
                    object.put("success", "true");
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                }else if ("serviceresult".equals(cmd)){

//                    String start_time="";
//                    String end_time="";
//                    String laststart_time="";
//                    String lastend_time="";
//                    DateUtil dateUtil = new DateUtil();
//                    Date nowDate = new Date();
//
//                    if (StringUtils.isNotEmpty(json.getString("date"))||null!=json.getString("date")){
//                        String date = json.getString("date");
//                        if ("week".equals(date)){
//                            String yz_time=dateUtil.getTimeInterval(nowDate);//获取本周时间
//                            String last_time=dateUtil.getLastTimeInterval();//获取上周时间
//                            String array[]=yz_time.split(",");
//                            String lastarray[]=last_time.split(",");
//                            start_time=array[0]+" 00:00:00";//本周第一天
//                            end_time=array[1]+" 23:59:59";  //本周最后一天
//                            laststart_time=lastarray[0]+" 00:00:00";//上周第一天
//                            lastend_time=lastarray[1]+" 23:59:59";  //上周最后一天
////                            start_time="2020-08-03"+" 00:00:00";//本周第一天
////                            end_time="2020-08-08"+" 23:59:59";  //本周最后一天
////                            laststart_time="2020-07-27"+" 00:00:00";//上周第一天
////                            lastend_time="2020-08-02"+" 23:59:59";  //上周最后一天
//                        }else if ("month".equals(date)){
//                            start_time = dateUtil.formatDate(dateUtil.getFirstDayOfMonth(nowDate))+" 00:00:00";//本月第一天
//                            end_time = dateUtil.formatDate(dateUtil.getLastDayOfMonth(nowDate))+" 23:59:59";//本月最后一天
//                            laststart_time=dateUtil.formatDate(dateUtil.getFirstDayOfLastMonth(nowDate))+" 00:00:00";//上月第一天
//                            lastend_time=dateUtil.formatDate(dateUtil.getLastDayOfLastMonth(nowDate))+" 23:59:59";//上月最后一天
//
//                        }
//                    }

                    String startTime = json.getString("startTime");
                    String endTime = json.getString("endTime");
                    if ("".equals(endTime)||null==endTime || "".equals(startTime)||null==startTime){
                        pd.put("starttime","2000-01-01 00:00:00");
                        pd.put("endtime","2077-12-31 23:59:59");
                    }else {
                        pd.put("starttime",json.getString("startTime")+" 00:00:00");
                        pd.put("endtime",json.getString("endTime")+" 23:59:59");
                    }



                    PageData thisdata = dataAnalyzeService.ServiceResult(pd);

//                    pd.put("starttime",laststart_time);
//                    pd.put("endtime",lastend_time);
//                    PageData lastdata = dataAnalyzeService.ServiceResult(pd);
                    //服务人数
                    int fwrs = Integer.parseInt(thisdata.get("fwrs").toString());
                    thisdata.put("fwrs", fwrs);

                    //总来电次数
                    int ldcs = Integer.parseInt(thisdata.get("ldcs").toString());
//                    int lastldcs = Integer.parseInt(lastdata.get("jqrjd").toString()) + Integer.parseInt(lastdata.get("rgjd").toString());
                    thisdata.put("ldcs", ldcs);

                    //总呼叫次数
                    int hjcs = Integer.parseInt(thisdata.get("hjcs").toString());
//                    int lasthjcs = Integer.parseInt(lastdata.get("jqrhf").toString()) + Integer.parseInt(lastdata.get("rghf").toString());
                    thisdata.put("hjcs", hjcs);
                    //来单增长
//                    thisdata.put("ldzz", ldcs - lastldcs);
                    //主动服务增长
//                    int zdfwzz = (hjcs + Integer.parseInt(thisdata.get("dxfs").toString())) - (lasthjcs - Integer.parseInt(lastdata.get("dxfs").toString()));
//                    thisdata.put("zdfwzz", zdfwzz);
                    //个人用户增长
//                    int yhzz = Integer.parseInt(thisdata.get("zzgrsl").toString()) - Integer.parseInt(lastdata.get("zzgrsl").toString());
//                    thisdata.put("yhzz", yhzz);
                    //企业用户增长
//                    int qyzz = Integer.parseInt(thisdata.get("zzqysl").toString()) - Integer.parseInt(lastdata.get("zzqysl").toString());
//                    thisdata.put("qyzz", qyzz);
                    //就业服务
                    int jyfw = Integer.parseInt(thisdata.get("qzrs").toString()) + Integer.parseInt(thisdata.get("qztj").toString());
//                    int lastjyfw = Integer.parseInt(lastdata.get("qzrsjs").toString()) + Integer.parseInt(lastdata.get("qztj").toString());
                    int qzrc = Integer.parseInt(thisdata.get("qzrs").toString()) + Integer.parseInt(thisdata.get("qztj").toString());
//                    int lastqzrc = Integer.parseInt(lastdata.get("qzrcjs").toString()) + Integer.parseInt(lastdata.get("qztj").toString());
                    thisdata.put("jyfw", jyfw);
                    //就业同比增长
//                    thisdata.put("jytbzz", (jyfw - lastjyfw)+"/"+(qzrc-lastqzrc));
                    //用工服务
                    int ygfw = Integer.parseInt(thisdata.get("ygrs").toString()) + Integer.parseInt(thisdata.get("ygtj").toString());
//                    int lastygfw = Integer.parseInt(lastdata.get("ygrsjs").toString()) + Integer.parseInt(lastdata.get("ygtj").toString());
                    int ygrc = Integer.parseInt(thisdata.get("ygrs").toString()) + Integer.parseInt(thisdata.get("ygtj").toString());
//                    int lastygrc = Integer.parseInt(lastdata.get("ygrcjs").toString()) + Integer.parseInt(lastdata.get("ygtj").toString());
                    thisdata.put("ygfw", ygfw);
                    //用工服务同比增长人次
//                    thisdata.put("ygtbzz", (ygfw - lastygfw)+"/"+(ygrc-lastygrc));
                    //职业培训
                    int zypx = Integer.parseInt(thisdata.get("jnts").toString()) + Integer.parseInt(thisdata.get("pxtj").toString());
//                    int lastzypx = Integer.parseInt(lastdata.get("jnts").toString()) + Integer.parseInt(lastdata.get("pxtj").toString());
                    thisdata.put("zypx", zypx);
                    //职业培训同比增长人次
//                    thisdata.put("zypxtbzz", zypx - lastzypx);
                    //政策服务
                    int zczx = Integer.parseInt(thisdata.get("zctj").toString()) + Integer.parseInt(thisdata.get("ywzx").toString());
//                    int lastzczx = Integer.parseInt(lastdata.get("zctj").toString()) + Integer.parseInt(lastdata.get("ywzx").toString());
                    thisdata.put("zczx", zczx);
                    //政策咨询同比增长人次
//                    thisdata.put("zczxtbzz", zczx - lastzczx);
                    //服务反馈
                    int fwfk = Integer.parseInt(thisdata.get("zxyd").toString()) + Integer.parseInt(thisdata.get("fk").toString());
//                    int lastfwfk = Integer.parseInt(lastdata.get("zxyd").toString()) + Integer.parseInt(lastdata.get("fk").toString());
                    thisdata.put("fwfk", fwfk);
                    //服务反馈同比增长人次
//                    thisdata.put("fwfktbzz", fwfk - lastfwfk);
                    //服务总数
                    int fwzs = Integer.parseInt(thisdata.get("qzrs").toString()) + Integer.parseInt(thisdata.get("qztj").toString())+
                            Integer.parseInt(thisdata.get("ygrs").toString()) + Integer.parseInt(thisdata.get("ygtj").toString())+
                            Integer.parseInt(thisdata.get("jnts").toString()) + Integer.parseInt(thisdata.get("pxtj").toString())+
                            Integer.parseInt(thisdata.get("zctj").toString()) + Integer.parseInt(thisdata.get("ywzx").toString())+
                            Integer.parseInt(thisdata.get("zxyd").toString()) + Integer.parseInt(thisdata.get("fk").toString());
//                    int lastfwzs = lastldcs + lasthjcs + lastjyfw + lastygfw + lastzypx + lastzczx + lastfwfk + Integer.parseInt(lastdata.get("dxfs").toString());

                    thisdata.put("fwzs",fwzs+fwrs);
                    //服务次数
                    thisdata.put("fwcs", fwzs);
                    //服务增长
//                    thisdata.put("fwzstbzz", fwzs-lastfwzs);
                    //服务时间
                    thisdata.put("fwsj", json.getString("startTime")+"——"+json.getString("endTime"));

                    ArrayList<PageData> objects = new ArrayList<>();
                    objects.add(thisdata);
                    object.put("data",objects);
                    object.put("success", "true");
                    object.put("pageId",1);
                    object.put("pageCount",1);
                    object.put("recordCount",1);

                }
            } else {
                object.put("success", "false");
                object.put("msg", "登录超时，请重新登录");
            }
//        } catch (Exception e) {
//            object.put("success", "false");
//            object.put("msg", "异常");
//        }
        return object;
    }

    public String getUUID32() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }
    public String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
