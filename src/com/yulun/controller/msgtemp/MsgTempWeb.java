package com.yulun.controller.msgtemp;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.entity.MsgTemp;
import com.yulun.service.MsgTempManager;
import com.yulun.service.QueryManager;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MsgTempWeb implements CommonIntefate {
    @Resource(name = "msgTempService")
    private MsgTempManager msgTempManager;
    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;
    @Resource(name="queryService")
    private QueryManager queryService;

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
            String zxid = pd_token.getString("ZXID");
            if (pd_token != null) {
                if (cmd.equals("insertMagTemp")) {
                    pd.put("content", json.getString("content"));
                    pd.put("parentid", json.getString("parentid"));
                    pd.put("tempname", json.getString("tempname"));
                    pd.put("smstype", json.getString("smstype"));
                    msgTempManager.save(pd);
                    object.put("success", "true");
                } else if (cmd.equals("updateMagTemp")) {
                    pd.put("id", json.getString("id"));
                    pd.put("content", json.getString("content"));
                    pd.put("parentid", json.getString("parentid"));
                    pd.put("tempname", json.getString("tempname"));
                    pd.put("smstype", json.getString("smstype"));
                    msgTempManager.edit(pd);
                    object.put("success", "true");
                } else if (cmd.equals("deleteMagTemp")) {
                    String[] ids = json.getString("id").split(",");
                    msgTempManager.deleteAll(ids);
                    object.put("success", "true");
                } else if (cmd.equals("listAllDict")) {
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("keywords", json.get("keywords"));
                    page.setPd(pd);
                    List<PageData> msgAlllistPage = msgTempManager.findMsgAlllistPage(page);
                    object.put("pageId", pageIndex);
                    object.put("pageCount", page.getTotalPage());
                    object.put("recordCount", page.getTotalResult());
                    object.put("data", msgAlllistPage);
                    object.put("success", "true");
                } else if (cmd.equals("listByParentId")) {
                    String parentid = json.getString("parentid");
                    List<MsgTemp> msgTemps = msgTempManager.listSubDictByParentId(parentid);
                    object.put("data", msgTemps);
                    object.put("success", "true");
                } else if (cmd.equals("findMsgLogAll")) {
                    Integer pageIndex = json.getInteger("pageIndex");
                    page.setCurrentPage(pageIndex);
                    Integer pageSize = json.getInteger("pageSize");
                    page.setShowCount(pageSize);
                    pd.put("starttime", json.getString("startTime"));
                    pd.put("endtime", json.getString("endTime"));
                    pd.put("keywords", json.getString("keywords"));
                    pd.put("sendman", json.getString("sendman"));
                    pd.put("acceptman", json.getString("acceptman"));
                    pd.put("phone", json.getString("phone"));
                    pd.put("way", json.getString("way"));
                    pd.put("cate", json.getString("cate"));
                    pd.put("temp", json.getString("temp"));
                    page.setPd(pd);
                    List<PageData> msgLogAll = msgTempManager.findMsgLogAlllistPage(page);
                    object.put("pageId", pageIndex);
                    object.put("pageCount", page.getTotalPage());
                    object.put("recordCount", page.getTotalResult());
                    object.put("data", msgLogAll);
                    object.put("success", "true");

                } else if (cmd.equals("insertMsgLog")) {
//                    String Allcontent = json.getString("content");
//                    int duanshu = 0;//总段数
//                    int duan = 75;//每段字数
//                    int zongchangdu = Allcontent.length();//测试文本长度
//                    int yushu=zongchangdu%duan;//余数
//                    duanshu = zongchangdu/duan+(yushu>0?1:0);
//                    for(int i=0;i<duanshu;i++){
//                        String content = (i == duanshu - 1) ? Allcontent.substring(i * duan) : Allcontent.substring(i * duan, (i + 1) * duan);
                        String content = json.getString("content");
                        String way = json.getString("way");
                        String time = json.getString("time");
                        if ("0".equals(way)){
                            pd.put("time", StringUtils.isNotEmpty(time)?time:getTime());
                            pd.put("way", json.getString("way"));
                            pd.put("temp", json.getString("temp"));
                            pd.put("content", content);
                            pd.put("acceptman", json.getString("acceptmans"));
                            pd.put("phone", json.getString("phone"));
                            pd.put("uid", json.getString("uid"));
                            pd.put("sendman", json.getString("sendman"));
                            pd.put("smstype", json.getString("smstype"));
                            pd.put("czman", zxid);
                            pd.put("czdate", getTime());
                            pd.put("cate", json.getString("cate"));
                            msgTempManager.insertMsgLog(pd);
                        }
                        if ("1".equals(way)){
                            String cate = json.getString("cate");
                            if ("1".equals(cate)){
                                List<PageData> getcompany = msgTempManager.getcompany(pd);
                                for (PageData pageData : getcompany) {
                                    pd.put("time", StringUtils.isNotEmpty(time)?time:getTime());
                                    pd.put("way", json.getString("way"));
                                    pd.put("temp", json.getString("temp"));
                                    pd.put("content", content);
                                    pd.put("acceptman", pageData.getString("name"));
                                    pd.put("phone", pageData.getString("tel"));
                                    pd.put("uid", pageData.getString("uid"));
                                    pd.put("sendman", json.getString("sendman"));
                                    pd.put("smstype", json.getString("smstype"));
                                    pd.put("czman", zxid);
                                    pd.put("czdate", getTime());
                                    pd.put("cate", json.getString("cate"));
                                    msgTempManager.insertMsgLog(pd);
                                }
                            }else {
                                PageData pageData = new PageData();
                                pageData.put("cate",cate);
                                List<PageData> getcate = msgTempManager.getcate(pageData);
                                for (PageData pageData1 : getcate) {
                                    pd.put("time", StringUtils.isNotEmpty(time)?time:getTime());
                                    pd.put("way", json.getString("way"));
                                    pd.put("temp", json.getString("temp"));
                                    pd.put("content", content);
                                    pd.put("acceptman", pageData1.getString("name"));
                                    pd.put("phone", pageData1.getString("tel"));
                                    pd.put("uid", pageData1.getString("uid"));
                                    pd.put("sendman", json.getString("sendman"));
                                    pd.put("smstype", json.getString("smstype"));
                                    pd.put("czman", zxid);
                                    pd.put("czdate", getTime());
                                    pd.put("cate", json.getString("cate"));
                                    msgTempManager.insertMsgLog(pd);
                                }
                            }
                        }
//                    }

                    object.put("success", "true");

                } else if (cmd.equals("updateMsgLog")) {
                    pd.put("id", json.getString("id"));
                    pd.put("time", json.getString("time"));
                    pd.put("way", json.getString("way"));
                    pd.put("temp", json.getString("temp"));
                    pd.put("content", json.getString("content"));
                    pd.put("acceptman", json.getString("acceptman"));
                    pd.put("phone", json.getString("phone"));
                    pd.put("sendman", json.getString("sendman"));
                    pd.put("sendtime", json.getString("sendtime"));
                    pd.put("state", json.getString("state"));
                    pd.put("returnmsg", json.getString("returnmsg"));
                    pd.put("smstype", json.getString("smstype"));

                    msgTempManager.updateMsgLog(pd);
                    object.put("success", "true");
                }  else if (cmd.equals("deleteTempAll")) {
                    String[] ids = json.getString("id").split(",");
                    msgTempManager.deleteAll(ids);
                    object.put("success", "true");
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

    public void test(){
        String testString = "012345678901234567890123456789";//测试文本
        int duanshu = 0;//总段数
        int duan = 4;//每段字数
        int zongchangdu = testString.length();//测试文本长度
        int yushu=zongchangdu%duan;//余数
        duanshu = zongchangdu/duan+(yushu>0?1:0);
        for(int i=0;i<duanshu;i++){
            System.out.println((i==duanshu-1)?testString.substring(i*duan):testString.substring(i*duan,(i+1)*duan));
        }
    }
    public void test1(){
        String s="0596-6986789-1009";
        String tel = s == null ? "" : s.replace("-", "");
        System.out.println(tel);
    }


}
