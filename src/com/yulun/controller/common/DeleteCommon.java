package com.yulun.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.CommnoManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class DeleteCommon implements CommonIntefate {
    @Resource(name = "commnoService")
    private CommnoManager commnoManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Override
    public JSONObject execute(JSONObject data, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        JSONObject json = data.getJSONObject("data");
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", json.get("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        if (pd_token != null) {
            try {
                pd.put("id", json.get("id"));
                commnoManager.deleteCommon(pd);
            } catch (Exception e) {
                data.put("success", "false");
                data.put("msg", "异常");
            }
            data.put("success", "true");
            data.put("msg", "删除成功");
        } else {
            data.put("success", "false");
            data.put("msg", "登录超时，请重新登录");
        }

        return data;
    }
}
