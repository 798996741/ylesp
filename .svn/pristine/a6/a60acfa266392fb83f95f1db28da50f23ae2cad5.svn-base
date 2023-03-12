package com.yulun.controller.signup;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.SignupManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author Aliar
 * @Time 2020-05-10 21:45
 **/
public class SignupWeb implements CommonIntefate {
    String SUCCESS = "success";
    String MSG = "msg";
    String FALSE = "false";
    String TURE = "true";
    Integer pageIndex;
    Integer pageSize;

    @Resource(name="zxlbService")
    private ZxlbManager zxlbService;

    @Resource(name="signupService")
    private SignupManager signupService;
    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        JSONObject object = new JSONObject();
        PageData pd = new PageData();
        String cmd = data.getString("cmd") == null ? "" : data.getString("cmd");
        PageData pd_stoken = new PageData();
        JSONObject json = data.getJSONObject("data");
        pd_stoken.put("TOKENID", json.get("tokenid"));
        try {
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (null == pd_token) {
                json.put(SUCCESS, "false");
                json.put(MSG, "请重新登陆");
            }
            if("signupList".equals(cmd)){
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
//            查询条件
                pd.putAll(json);
                page.setPd(pd);
                List<PageData> list = signupService.findAlllistPage(page);
                if(list.size() > 0){
                    object.put(SUCCESS,TURE);
                    object.put("data",list);
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                }else{
                    object.put(SUCCESS,"true");
                    object.put(MSG,"暂无数据");
                }

            }




        } catch (Exception e) {
            object.put("success", "false");
            object.put("msg", "数据异常");

        } finally {
            return object;
        }
    }
}
