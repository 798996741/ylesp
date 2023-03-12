package com.yulun.controller.callmsg;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.CallMsgManger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CallMsg implements CommonIntefate {
    @Resource(name = "callMsgService")
    private CallMsgManger callMsgManger;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject json = data.getJSONObject("data");
        JSONObject object = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", json.get("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                PageData pd = new PageData();
                pd.put("ucid", json.getString("ucid"));
                pd.put("zjhm", json.getString("zjhm"));
                List<PageData> findcallmsg = new ArrayList<>();
                findcallmsg = callMsgManger.findcallmsg(pd);
                if (findcallmsg.size()>0) {

                }else {

                    object.put("msg", "查询日志为空，请及时联系开发人员。");
                }
//                if (findcallmsg.size()>0){
//                    object.put("data", findcallmsg);
//                    String zjhm = findcallmsg.get(0).getString("zjhm");
//                }else {
                    PageData pageData = new PageData();
                    pageData.put("zjhm",json.getString("zjhm"));
                    pageData.put("kssj",getTime());
                    findcallmsg.add(pageData);
                    object.put("data", findcallmsg);
//                }
                object.put("success", "true");
//                if ("905".equals(role)) {
//                    List<PageData> vipinfo = callMsgManger.findVipinfo(pd);
//                    PageData findnum = callMsgManger.findnum(pd);
//                    if (vipinfo.size()>0){
//                        for (PageData pageData : vipinfo) {
//                            pageData.put("num",findnum.get("num").toString());
//                        }
//                        object.put("data", vipinfo);
//                    }else if (vipinfo.size()==0){
//                        PageData pageData = new PageData();
//                        pageData.put("name","");
//                        pageData.put("position","");
//                        pageData.put("zjhm",pd.getString("zjhm"));
//                        pageData.put("kssj","");
//                        pageData.put("num","");
//                        object.put("data", pageData);
//                    }
//                    System.out.println(vipinfo);
//                } else {
//                    List<PageData> custinfo = callMsgManger.findCustinfo(pd);
//                    PageData findnum = callMsgManger.findnum(pd);
//                    if (custinfo.size()>0){
//                        for (PageData pageData : custinfo) {
//                            pageData.put("num",findnum.get("num").toString());
//                        }
//                        object.put("data", custinfo);
//                    }else if (custinfo.size()==0){
//                        PageData pageData = new PageData();
//                        pageData.put("name","");
//                        pageData.put("zjhm",pd.getString("zjhm"));
//                        pageData.put("kssj","");
//                        pageData.put("num","");
//                        object.put("data", pageData);
//                    }
//                    System.out.println(custinfo);
//                }

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

    public String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
}
