package com.yulun.controller.msgtemp;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.*;
import com.yulun.service.impl.QueryService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
/**
 * @Author Aliar
 * @Time 2020-05-22 11:15
 **/
public class MsgQueryWeb implements CommonIntefate {

        @Resource(name = "zxlbService")
        private ZxlbManager zxlbService;
        @Resource(name="consumerService")
        private ConsumerManager consumerService;
        @Resource(name="queryService")
        private QueryManager queryService;

        String SUCCESS = "success";
        String MSG = "msg";
        String FALSE = "false";
        String TRUE = "true";
        Integer pageIndex;
        Integer pageSize;

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
                      if("query".equals(cmd)){
                                List<PageData> list = new ArrayList<PageData>();
                              pageIndex = json.getInteger("pageIndex");
                              pageSize = json.getInteger("pageSize");
                              if(pageSize == null || "".equals(pageSize) || pageIndex == null || "".equals(pageIndex) ){
                                      object.put(SUCCESS,FALSE);
                                      object.put(MSG,"pageSizeOrPageIndexIsNull");
                                      return object;
                              }else {
                                      page.setCurrentPage(pageIndex);
                                      page.setShowCount(pageSize);
                              }

                              String typeStr = json.getString("type");
                              if(typeStr == null || "".equals(typeStr)){
                                      object.put(SUCCESS,FALSE);
                                      object.put(MSG,"typeStrIsNull");
                                      return object;
                              }
                              if(typeStr.length()!=1){
                                      object.put(SUCCESS,FALSE);
                                      object.put(MSG,"typeStrIsError");
                                      return object;
                              }
                              if(!"1".equals(typeStr) && !"2".equals(typeStr)){
                                      object.put(SUCCESS,FALSE);
                                      object.put(MSG,"typeStrIsError");
                                      return object;
                              }
                              if("2".equals(typeStr)) {
                                      String cate = json.getString("cate");
                                      if (cate == null || "".equals(cate)) {
                                              object.put(SUCCESS, FALSE);
                                              object.put(MSG, "typeIs2AndCateIsNull");
                                              return object;
                                      }
                              }
                              pd.putAll(json);
                              page.setPd(pd);
                                String typeInt = json.getString("type");
                                switch (typeInt){
                                        case "1":
                                                //公司
                                                list = queryService.findcompanylistPage(page);
                                                break;
                                        case "2":
                                                //个人
                                                list = queryService.findCatelistPage(page);
                                                break;
                                }
                                if(list.size() > 0){
                                        object.put("success","true");
                                        object.put("data",list);
                                        object.put("pageId",pageIndex);
                                        object.put("pageCount",page.getTotalPage());
                                        object.put("recordCount",page.getTotalResult());
                                }else{
                                        object.put("success","false");
                                        object.put("msg","暂无数据");
                                }
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

}
