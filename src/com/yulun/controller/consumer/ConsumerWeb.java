package com.yulun.controller.consumer;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.ConsumerManager;
import com.yulun.utils.TimeHandle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ConsumerWeb implements CommonIntefate {

    @Resource(name = "consumerService")
    private ConsumerManager consumerManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;


    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject object=new JSONObject();
//        try {
            PageData pd = new PageData();
            Page page = new Page();
            String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
            PageData pd_stoken = new PageData();
            JSONObject json = data.getJSONObject("data");
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                if (cmd.equals("findgraduate")) {
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("jobtype",json.getString("jobtype"));
                    pd.put("starttime",json.getString("startTime"));
                    pd.put("endtime",json.getString("endTime"));
                    pd.put("arrtime",json.getString("arrtime"));
                    pd.put("keywords",json.getString("keywords"));
                    pd.put("result",json.getString("result"));
                    pd.put("id",json.getString("id"));
                    page.setPd(pd);
                    List<PageData> list = consumerManager.findgraduatelistPage(page);
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                    object.put("data",list);
                    object.put("success","true");
                }
                else if(cmd.equals("insertgraduate")){
                    pd.put("name",json.getString("name"));
                    pd.put("major",json.getString("major"));
                    pd.put("sex",json.getString("sex"));
                    pd.put("cardid",json.getString("cardid"));
                    pd.put("school",json.getString("school"));
                    pd.put("gradate",json.getString("gradate"));
                    pd.put("edubg",json.getString("edubg"));
                    pd.put("address",json.getString("address"));
                    pd.put("tel",json.getString("tel"));
                    pd.put("extel",json.getString("extel"));
                    pd.put("jobadd",json.getString("jobadd"));
                    pd.put("jobtype",json.getString("jobtype"));
                    pd.put("jobunit",json.getString("jobunit"));
                    pd.put("remark",json.getString("remark"));
                    pd.put("arrtime",json.getString("arrtime"));
                    pd.put("isinsuran",json.getString("isinsuran"));
                    pd.put("location",json.getString("location"));
                    consumerManager.insertgraduate(pd);
                    object.put("success","true");
                }else if(cmd.equals("updategraduate")){
                    pd.put("id",json.getString("id"));
                    pd.put("uid",json.getString("uid"));
                    pd.put("xl",json.getString("xl"));
                    pd.put("name",json.getString("name"));
                    pd.put("zy",json.getString("major"));
                    pd.put("sex",json.getString("sex"));
                    pd.put("cardid",json.getString("cardid"));
                    pd.put("school",json.getString("school"));
                    pd.put("gradate",json.getString("gradate"));
                    pd.put("edubg",json.getString("edubg"));
                    pd.put("jg",json.getString("address"));
                    pd.put("tel",json.getString("tel"));
                    pd.put("lxtel",json.getString("tel"));
                    pd.put("extel",json.getString("extel"));
                    pd.put("jobadd",json.getString("jobadd"));
                    pd.put("isjob",json.getString("jobtype"));
                    pd.put("jobunit",json.getString("jobunit"));
                    pd.put("case",json.getString("remark"));
                    pd.put("arrtime",json.getString("arrtime"));
                    pd.put("isinsuran",json.getString("isinsuran"));
                    pd.put("location",json.getString("location"));
                    pd.put("qzqd",json.getString("qzqd"));
                    pd.put("bylxtel",json.getString("bylxtel"));
                    pd.put("bylxr",json.getString("bylxr"));
                    consumerManager.editPerson(pd);
                    consumerManager.updategraduate(pd);
                    consumerManager.updatejobunit(pd);

                    object.put("success","true");
                }
                else if(cmd.equals("deletegraduate")){
                    pd.put("id",json.getString("id"));
                    consumerManager.deletegraduate(pd);
                    object.put("success","true");
                }else if (cmd.equals("findpoverty")){
                    Integer pageIndex = json.getInteger("pageIndex");
                    Integer pageSize = json.getInteger("pageSize");
                    if(pageIndex != null && !"".equals(pageIndex) && pageSize != null && !"".equals(pageSize)) {
                        page.setShowCount(pageSize);
                        page.setCurrentPage(pageIndex);
                    }else{
                        json.put("mes","notFindPageSizeOrPageIndex");
                        json.put("success","false");
                        return json;
                    }
                    pd.putAll(json);
                    String endTime = pd.getString("endTime");
                    if(endTime != null && "".equals(endTime)) {
                        pd.put("endTime", TimeHandle.endTimeHeandle(endTime));
                    }
                    pd.put("keywords",json.getString("keywords"));
                    pd.put("result",json.getString("result"));
                    page.setPd(pd);
                    List<PageData> list = consumerManager.findpovertylistPage(page);
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                    object.put("data",list);
                    object.put("success","true");
                }else if (cmd.equals("insertpoverty")){
                    pd.put("cztime",getTime());
                    pd.put("name",json.getString("name"));
                    pd.put("tel",json.getString("tel"));
                    pd.put("remark",json.getString("remark"));
                    consumerManager.insertpoverty(pd);
                    object.put("success","true");
                }else if (cmd.equals("updatepoverty")){
                    pd.put("cztime",getTime());
                    pd.put("id",json.getString("id"));
                    pd.put("name",json.getString("name"));
                    pd.put("tel",json.getString("tel"));
                    pd.put("remark",json.getString("remark"));
                    consumerManager.updatepoverty(pd);
                    object.put("success","true");
                }else if (cmd.equals("deletepoverty")){
                    pd.put("id",json.getString("id"));
                    consumerManager.deletepoverty(pd);
                    object.put("success","true");
                }else if (cmd.equals("findsbbtry")){
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("starttime",json.getString("startTime"));
                    pd.put("endtime",json.getString("endTime"));
                    pd.put("id",json.getString("id"));
                    pd.put("keywords",json.getString("keywords"));
                    pd.put("result",json.getString("result"));
                    page.setPd(pd);
                    List<PageData> list = consumerManager.findsbbtrylistPage(page);
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                    object.put("data",list);
                    object.put("success","true");
                }else if (cmd.equals("insertsbbtry")){
                    pd.put("cztime",getTime());
                    pd.put("name",json.getString("name"));
                    pd.put("tel",json.getString("tel"));
                    pd.put("remark",json.getString("remark"));
                    consumerManager.insertsbbtry(pd);
                    object.put("success","true");
                }else if (cmd.equals("updatesbbtry")){
                    pd.put("cztime",getTime());
                    pd.put("id",json.getString("id"));
                    pd.put("name",json.getString("name"));
                    pd.put("tel",json.getString("tel"));
                    pd.put("remark",json.getString("remark"));
                    consumerManager.updatesbbtry(pd);
                    object.put("success","true");
                }else if (cmd.equals("deletesbbtry")){
                    pd.put("id",json.getString("id"));
                    consumerManager.deletesbbtry(pd);
                    object.put("success","true");
                }else if (cmd.equals("findjzbtry")){
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("starttime",json.getString("startTime"));
                    pd.put("endtime",json.getString("endTime"));
                    String creden = json.getString("creden");
                    if ("cf2009ddc4a04ae5bcf34e531b1b5370".equals(creden)) {
                        creden="";
                    }
                    pd.put("creden",creden);
                    pd.put("id",json.getString("id"));
                    pd.put("keywords",json.getString("keywords"));
                    pd.put("result",json.getString("result"));
                    page.setPd(pd);
                    List<PageData> list = consumerManager.findjzbtrylistPage(page);
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                    object.put("data",list);
                    object.put("success","true");
                }else if (cmd.equals("insertjzbtry")){
                    pd.put("cztime",getTime());
                    pd.put("name",json.getString("name"));
                    pd.put("cardid",json.getString("cardid"));
                    pd.put("jobvar",json.getString("jobvar"));
                    pd.put("level",json.getString("creden"));
                    pd.put("creden",json.getString("creden"));
                    pd.put("tel",json.getString("tel"));
                    pd.put("address",json.getString("address"));
                    pd.put("remark",json.getString("remark"));

                    consumerManager.insertjzbtry(pd);
                    object.put("success","true");
                }else if (cmd.equals("updatejzbtry")){
                    pd.put("cztime",getTime());
                    pd.put("id",json.getString("id"));
                    pd.put("name",json.getString("name"));
                    pd.put("cardid",json.getString("cardid"));
                    pd.put("jobvar",json.getString("jobvar"));
                    pd.put("level",json.getString("creden"));
                    pd.put("creden",json.getString("creden"));
                    pd.put("tel",json.getString("tel"));
                    pd.put("address",json.getString("address"));
                    pd.put("remark",json.getString("remark"));
                    consumerManager.updatejzbtry(pd);
                    object.put("success","true");
                }else if (cmd.equals("deletejzbtry")){
                    pd.put("id",json.getString("id"));
                    consumerManager.deletejzbtry(pd);
                    object.put("success","true");
                }else if (cmd.equals("findcompany")){
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("id",json.getString("id"));
                    pd.put("name",json.getString("name"));
                    pd.put("qytype",json.getString("qytype"));
                    pd.put("lxr",json.getString("lxr"));
                    pd.put("lxtel",json.getString("lxtel"));
                    pd.put("keywords",json.getString("keywords"));
                    pd.put("result",json.getString("result"));
                    page.setPd(pd);
                    List<PageData> list = consumerManager.findcompanylistPage(page);
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                    object.put("data",list);
                    object.put("success","true");
                }else if (cmd.equals("insertcompany")){
                    String uuid32 = getUUID32();
                    pd.put("uid",uuid32);
                    pd.put("qytype",json.getString("qytype"));
                    pd.put("name",json.getString("name"));
                    pd.put("addr",json.getString("addr"));
                    pd.put("email",json.getString("email"));
                    pd.put("lxr",json.getString("lxr"));
                    pd.put("tel",json.getString("lxtel"));
                    pd.put("lxtel",json.getString("lxtel"));
                    pd.put("bylxr",json.getString("bylxr"));
                    pd.put("bylxtel",json.getString("bylxtel"));
                    pd.put("czman",json.getString("czman"));
                    pd.put("isinsuran",json.getString("isinsuran"));
                    pd.put("intro",json.getString("intro"));
                    pd.put("qzqd",json.getString("qzqd"));
                    pd.put("isimpot","2");
                    pd.put("czdate",getTime());
                    consumerManager.insertcompany(pd);
                    object.put("success","true");
                    object.put("uid",uuid32);
                }else if (cmd.equals("updatecompany")){
                    pd.put("id",json.getString("id"));
                    pd.put("uid",json.getString("uid"));
                    pd.put("qytype",json.getString("qytype"));
                    pd.put("name",json.getString("name"));
                    pd.put("addr",json.getString("addr"));
                    pd.put("email",json.getString("email"));
                    pd.put("lxr",json.getString("lxr"));
                    pd.put("tel",json.getString("lxtel"));
                    pd.put("lxtel",json.getString("lxtel"));
                    pd.put("bylxr",json.getString("bylxr"));
                    pd.put("bylxtel",json.getString("bylxtel"));
                    pd.put("czman",json.getString("czman"));
                    pd.put("isinsuran",json.getString("isinsuran"));
                    pd.put("intro",json.getString("intro"));
                    pd.put("qzqd",json.getString("qzqd"));
                    pd.put("isimpot","2");
                    pd.put("czdate",getTime());
                    consumerManager.updatecompany(pd);
                    object.put("success","true");
                }else if (cmd.equals("deletecompany")){
                    pd.put("id",json.getString("id"));
                    consumerManager.deletecompany(pd);
                    object.put("success","true");
                }else if (cmd.equals("findemployreg")){
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("id",json.getString("id"));
                    pd.put("uid",json.getString("uid"));
                    pd.put("keywords",json.getString("keywords"));
                    page.setPd(pd);
                    List<PageData> list = consumerManager.findemployreglistPage(page);
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                    object.put("data",list);
                    object.put("success","true");
                }else if (cmd.equals("insertemployreg")){
                    String jobname = json.getString("jobname");

                    pd.put("jobname",jobname);
                    pd.put("jobtype",json.getString("jobtype"));
                    pd.put("xl",json.getString("xl"));
                    pd.put("zyyq",json.getString("zyyq"));
                    pd.put("remark",json.getString("remark"));
                    pd.put("czman",json.getString("czman"));
                    pd.put("gzaddr",json.getString("gzaddr"));
                    pd.put("specificjobname",json.getString("specificjobname"));
                    pd.put("experience",json.getString("experience"));

                    pd.put("uid",json.getString("uid"));
                    pd.put("czdate",getTime());
                    pd.put("zgrs",json.getString("zgrs"));
                    pd.put("sex",json.getString("sex"));
                    String age = json.getString("age");
                    if (!age.equals("") && age!=null){
                        String[] split = age.split("-");
                        pd.put("agestart",split[0]);
                        pd.put("ageend",split[1]);
                    }
                    pd.put("gzdy",json.getString("gzdy"));
                    pd.put("gwyq",json.getString("gwyq"));
                    pd.put("isimpot","2");
                    object.put("success","true");
                    if (jobname==null || jobname.equals("")){
                        object.put("msg"," 请选择招聘工种");
                        object.put("success","false");
                    }else {
                        consumerManager.insertemployreg(pd);
                    }
                }else if (cmd.equals("updatemployreg")){
                    pd.put("id",json.getString("id"));
                    pd.put("jobname",json.getString("jobname"));
                    pd.put("jobtype",json.getString("jobtype"));
                    pd.put("xl",json.getString("xl"));
                    pd.put("zyyq",json.getString("zyyq"));
                    pd.put("remark",json.getString("remark"));
                    pd.put("czman",json.getString("czman"));
                    pd.put("gzaddr",json.getString("gzaddr"));
                    pd.put("specificjobname",json.getString("specificjobname"));
                    pd.put("experience",json.getString("experience"));
                    pd.put("uid",json.getString("uid"));
                    pd.put("czdate",getTime());
                    pd.put("zgrs",json.getString("zgrs"));
                    pd.put("sex",json.getString("sex"));
                    String age = json.getString("age");
                    if (!age.equals("") && age!=null){
                        String[] split = age.split("-");
                        pd.put("agestart",split[0]);
                        pd.put("ageend",split[1]);
                    }
                    pd.put("gzdy",json.getString("gzdy"));
                    pd.put("gwyq",json.getString("gwyq"));
                    pd.put("isvalid",json.getString("isvalid"));
                    pd.put("isimpot","2");
                    consumerManager.updateemployreg(pd);
                    object.put("success","true");
                }else if (cmd.equals("deleteemployreg")){

                    pd.put("id",json.getString("id"));
                    consumerManager.deleteemployreg(pd);
                    object.put("success","true");
                }else if (cmd.equals("callmsg")){
                    List<PageData> list = consumerManager.getcalltoday(pd);
                    object.put("data",list);
                    object.put("success","true");
                }else if (cmd.equals("findcomreg")){
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("id",json.getString("id"));
                    pd.put("name",json.getString("name"));
                    pd.put("qytype",json.getString("qytype"));
                    pd.put("lxr",json.getString("lxr"));
                    pd.put("lxtel",json.getString("lxtel"));
                    pd.put("jobname",json.getString("jobname"));
                    pd.put("qzqd",json.getString("qzqd"));
                    pd.put("keywords",json.getString("keywords"));
                    pd.put("starttime", json.getString("startTime"));
                    pd.put("endtime", json.getString("endTime"));
                    page.setPd(pd);
                    List<PageData> list = consumerManager.findcomreglistPage(page);
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                    object.put("data",list);
                    object.put("success","true");
                }else if (cmd.equals("updateLoseJob")){
                    pd.put("id",json.getString("id"));
                    pd.put("uid",json.getString("uid"));
                    pd.put("xl",json.getString("xl"));
                    pd.put("name",json.getString("name"));
                    pd.put("addr",json.getString("addr"));
                    pd.put("zy",json.getString("zy"));
                    pd.put("sex",json.getString("sex"));
                    pd.put("cardid",json.getString("cardid"));
                    pd.put("school",json.getString("school"));
                    pd.put("gradate",json.getString("gradate"));
                    pd.put("jg",json.getString("jg"));
                    pd.put("tel",json.getString("tel"));
                    pd.put("lxtel",json.getString("tel"));
                    pd.put("isjob",json.getString("isjob"));
                    pd.put("isinsuran",json.getString("isinsuran"));
                    pd.put("location",json.getString("location"));
                    pd.put("remark",json.getString("remark"));
                    pd.put("cate",json.getString("cate"));
                    pd.put("isimpot",json.getString("isimpot"));
                    pd.put("czman",json.getString("czman"));
                    pd.put("czdate",json.getString("czdate"));
                    pd.put("qzqd",json.getString("qzqd"));
                    pd.put("bylxtel",json.getString("bylxtel"));
                    pd.put("bylxr",json.getString("bylxr"));
                    pd.put("certificate_id",json.getString("certificate_id"));
                    pd.put("certificate_date",json.getString("certificate_date"));
                    pd.put("certificate_unit",json.getString("certificate_unit"));

                    consumerManager.updateLoseJob(pd);
                    consumerManager.editPerson(pd);
                    object.put("success","true");
                }else if (cmd.equals("deteleLoseJob")){
                    pd.put("id",json.getString("id"));
                    pd.put("uid",json.getString("uid"));
                    consumerManager.deteleLoseJob(pd);
                    consumerManager.detelePerson(pd);
                }else if (cmd.equals("findLoseJob")){
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("result",json.getString("result"));
                    pd.put("isjob",json.getString("isjob"));
                    pd.put("certificate_unit",json.getString("certificate_unit"));
                    pd.put("certificate_id",json.getString("certificate_id"));
                    pd.put("name",json.getString("name"));
                    pd.put("starttime",json.getString("startTime"));
                    pd.put("endtime",json.getString("endTime"));
                    pd.put("keywords",json.getString("keywords"));

                    page.setPd(pd);
                    List<PageData> list = consumerManager.findLoseJoblistPage(page);

                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                    object.put("data",list);
                    object.put("success","true");
                }
            } else {
                object.put("success", "false");
                object.put("msg", "登录超时，请重新登录");
            }
//        }catch (SQLException e){
//            object.put("success", "false");
//            object.put("msg", "数据异常");
//        }
//        catch (Exception e) {
//            object.put("success", "false");
//            object.put("msg", "异常");
//        }
        return object;
    }

    public String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

    public String getUUID32() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }
}
