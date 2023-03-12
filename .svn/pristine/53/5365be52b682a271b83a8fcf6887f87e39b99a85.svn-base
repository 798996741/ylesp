package com.yulun.controller.serverLog;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.CompanyManager;
import com.yulun.service.GuideManager;
import com.yulun.service.PersonManager;
import com.yulun.service.PolicyManager;
import com.yulun.service.impl.PolicyService;
import com.yulun.utils.TimeHandle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author Aliar
 * @Time 2020-05-10 16:04
 **/
public class GuideWeb implements CommonIntefate {
    String SUCCESS = "success";
    String MSG = "msg";
    String FALSE = "false";
    String TURE = "true";
    Integer pageIndex;
    Integer pageSize;

    @Resource(name = "policyService")
    private PolicyManager policyService;
    @Resource(name="zxlbService")
    private ZxlbManager zxlbService;
    @Resource(name="guideService")
    private GuideManager guideService;
    @Resource(name="personService")
    private PersonManager personService;
    @Resource(name="companyService")
    private CompanyManager companyService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject object=new JSONObject();
        PageData pd = new PageData();
        String cmd = data.getString("cmd")==null?"":data.getString("cmd");
        PageData pd_stoken=new PageData();
        JSONObject json = data.getJSONObject("data");
        pd_stoken.put("TOKENID", json.get("tokenid"));
        try {
            PageData pd_token=zxlbService.findByTokenId(pd_stoken);
            if (null == pd_token){
                json.put(SUCCESS,"false");
                json.put(MSG,"请重新登陆");
            }
        if("guideList".equals(cmd)){
            //startTime、endTime、ywname、isjd、type、name检索条件
            Page page = new Page();
            pageIndex = json.getInteger("pageIndex");
            pageSize = json.getInteger("pageSize");
            if(pageSize == null || "".equals(pageSize) || pageIndex == null || "".equals(pageIndex) ){
                object.put(SUCCESS,FALSE);
                object.put(MSG,"pageSizeOrPageIndexNotFind");
                return object;
            }else {
                page.setCurrentPage(pageIndex);
                page.setShowCount(pageSize);
            }
            String endTime = json.getString("endTime");
            if(null != endTime && !"".equals(endTime)) {
                endTime = TimeHandle.endTimeHeandle(endTime);
                json.put("endTime", endTime);
            }
            pd.putAll(json);
            page.setPd(pd);
            List<PageData> list = guideService.findAlllistPage(page);
            if(list.size() > 0){
                object.put(SUCCESS,TURE);
                object.put("data",list);
                object.put("pageId",pageIndex);
                object.put("pageCount",page.getTotalPage());
                object.put("recordCount",page.getTotalResult());
            }else{
                object.put("success","false");
                object.put("msg","暂无数据");
            }
        }

        if("guideCustomer".equals(cmd)){
            pd.putAll(json);
            String uid = pd.getString("uid");
            if(uid == null || "".equals(uid)){
                object.put("success", "false");
                object.put("msg", "入参错误,NotFindUid");
                return object;
            }

            PageData customer = companyService.findByUid(pd);
        if (customer == null) {
          customer = personService.findByUid(pd);
                }

            if(customer == null){
                object.put(SUCCESS, FALSE);
                object.put(MSG, "暂无此人");
            }else{
                object.put("data",customer);
            }
        }
        if("detail".equals(cmd)){
            Page page = new Page();
            pageIndex = json.getInteger("pageIndex");
            pageSize = json.getInteger("pageSize");
            if(pageSize == null || "".equals(pageSize) || pageIndex == null || "".equals(pageIndex) ){
                object.put(SUCCESS,FALSE);
                object.put(MSG,"入参错误");
                return object;
            }else {
                page.setCurrentPage(pageIndex);
                page.setShowCount(pageSize);
            }
            pd.putAll(json);
            page.setPd(pd);
            List<PageData> list = guideService.findByUidlistPage(page);
            if(list.size() > 0){
                object.put(SUCCESS,TURE);
                object.put("data",list);
                object.put("pageId",pageIndex);
                object.put("pageCount",page.getTotalPage());
                object.put("recordCount",page.getTotalResult());
            }else{
                object.put("success","false");
                object.put("msg","暂无数据");
            }
        }

        }catch(Exception e){
            object.put("success", "false");
            object.put("msg", "数据异常");
        }
        finally {
            return object;
        }

    }
}
