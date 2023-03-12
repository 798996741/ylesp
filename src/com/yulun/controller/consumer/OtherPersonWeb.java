package com.yulun.controller.consumer;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.OtherPersonManager;
import com.yulun.service.PersonManager;
import com.yulun.utils.TimeHandle;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author Aliar
 * @Time 2020-05-08 16:12
 **/
public class OtherPersonWeb implements CommonIntefate {
    String SUCCESS = "success";
    String MSG = "msg";
    String FALSE = "false";
    String TRUE = "true";
    Integer pageIndex;
    Integer pageSize;

    @Resource(name="zxlbService")
    private ZxlbManager zxlbService;

    @Resource(name="otherPersonService")
    private OtherPersonManager otherPersonService;

    @Resource(name="personService")
    private PersonManager personService;

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
                json.put(SUCCESS, FALSE);
                json.put(MSG, "请重新登陆");
            }
            //查询方法
            if("otherPersonList".equals(cmd)){
                Page page = new Page();
                pageIndex = json.getInteger("pageIndex");
                pageSize = json.getInteger("pageSize");
                if(pageSize == null || "".equals(pageSize) || pageIndex == null || "".equals(pageIndex) ){
                    object.put(SUCCESS,FALSE);
                    object.put(MSG,"入参错误,NotFindPageSizeOrPageIndex");
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
//            查询条件
                page.setPd(pd);
                List<PageData> list = otherPersonService.findAlllistPage(page);
                if(list.size() > 0){
                    object.put(SUCCESS,TRUE);
                    object.put("data",list);
                    object.put("pageId",pageIndex);
                    object.put("pageCount",page.getTotalPage());
                    object.put("recordCount",page.getTotalResult());
                }else{
                    object.put(SUCCESS,TRUE);
                    object.put(MSG,"暂无数据");
                }
            }

            if("save".equals(cmd)){
                pd.putAll(json);
                otherPersonService.save(pd);
            }

            if("updatePerson".equals(cmd)){
                String uid = json.getString("uid");
                if(uid == null || "".equals(uid)){
                    object.put(SUCCESS, "false");
                    object.put(MSG, "入参错误,NotFindIdOrUid");
                    return object;
                }
                pd.putAll(json);
                if(json.size() < 13){
                    object.put(SUCCESS, "false");
                    object.put(MSG, "参数数量不足13");
                    return object;
                }
                List<PageData> train = otherPersonService.findById(pd);
                if(train.size() > 0){
                    int result = (int)otherPersonService.update(pd);
                    if(result > 0 ){
                        object.put(SUCCESS, "true");
                        object.put(MSG, "修改成功");
                    }else{
                        object.put(SUCCESS, "false");
                        object.put(MSG, "修改失败,请检查idOrUid");
                    }
                }else{
                    object.put(SUCCESS, "true");
                    object.put(MSG, "暂无此人");
                }
            }
            if("otherPersonInfo".equals(cmd)){
                pd.putAll(json);
                String uid = pd.getString("uid");
                if(uid == null || "".equals(uid)){
                    object.put(SUCCESS, FALSE);
                    object.put(MSG, "入参错误");
                    return object;
                }
                Page page = new Page();
                page.setPd(pd);
                List<PageData> customer = otherPersonService.findAlllistPage(page);
                if(customer.size() < 1){
                    object.put(SUCCESS, FALSE);
                    object.put(MSG, "暂无此人");
                }else{
                    object.put("data",customer.get(0));
                }
            }
        }catch(Exception e){
            object.put(SUCCESS, FALSE);
            object.put(MSG, "数据异常");

            }finally{
                return object;
            }
    }
}