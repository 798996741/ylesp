package com.yulun.controller.consumer;

import com.alibaba.fastjson.JSONObject;
import com.fh.entity.Page;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.controller.api.CommonIntefate;
import com.yulun.service.DataStatManager;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataStatWeb implements CommonIntefate {

    @Resource(name = "dataStatService")
    private DataStatManager dataStatManager;
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
//            pd_stoken.put("TOKENID", json.get("tokenid"));
//            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
//            if (pd_token != null) {
                if (cmd.equals("usertotal")) {

//                    pd.put("starttime",json.getString("startTime"));
//                    pd.put("endtime",json.getString("endTime"));
                    PageData list = dataStatManager.usertotal(pd);

                    ArrayList<HashMap<String,String>> hashMaps = new ArrayList<>();
                    HashMap<String, String> map1 = new HashMap<>();
                    map1.put("name","用户总数");
                    int total = Integer.parseInt(list.get("total").toString());
                    map1.put("value",total+"");
                    hashMaps.add(map1);

                    HashMap<String, String> map2 = new HashMap<>();
                    map2.put("name","个人总数");
                    int person = Integer.parseInt(list.get("person").toString());
                    map2.put("value",person+"");
                    hashMaps.add(map2);

                    HashMap<String, String> map4 = new HashMap<>();
                    map4.put("name","企业总数");
                    int company = Integer.parseInt(list.get("company").toString());
                    map4.put("value",company+"");
                    hashMaps.add(map4);

                    HashMap<String, String> map5 = new HashMap<>();
                    map5.put("name","求职人数");
                    int job = Integer.parseInt(list.get("job").toString());
                    map5.put("value",job+"");
                    hashMaps.add(map5);

                    HashMap<String, String> map3 = new HashMap<>();
                    map3.put("name","用工企业数");
                    int ygdj  = Integer.parseInt(list.get("employe").toString());
                    map3.put("value",ygdj+"");
                    hashMaps.add(map3);



                    object.put("data",hashMaps);
                    object.put("success","true");
                }else if (cmd.equals("servtotal")){
//                    pd.put("starttime",json.getString("startTime"));
//                    pd.put("endtime",json.getString("endTime"));
                    PageData list = dataStatManager.servtotal(pd);
                    ArrayList<HashMap<String,String>> hashMaps = new ArrayList<>();

                    HashMap<String, String> map3 = new HashMap<>();
                    map3.put("name","就业服务量");
                    int job = Integer.parseInt(list.get("job").toString());
                    map3.put("value",job+"");
                    hashMaps.add(map3);

                    HashMap<String, String> map6 = new HashMap<>();
                    map6.put("name","用工服务量");
                    int employ = Integer.parseInt(list.get("employ").toString());
                    map6.put("value",1733+"");
                    hashMaps.add(map6);

                    HashMap<String, String> map2 = new HashMap<>();
                    map2.put("name","政策服务量");
                    int zczx = Integer.parseInt(list.get("zczx").toString());
                    map2.put("value",zczx+"");
                    hashMaps.add(map2);

                    HashMap<String, String> map7 = new HashMap<>();
                    map7.put("name","技能培训量");
                    int jnpx = Integer.parseInt(list.get("jnpx").toString());
                    map7.put("value",jnpx+"");
                    hashMaps.add(map7);

                    HashMap<String, String> map5 = new HashMap<>();
                    map5.put("name","业务引导量");
                    int ywyd = Integer.parseInt(list.get("ywyd").toString());
                    map5.put("value",ywyd+"");
                    hashMaps.add(map5);

                    HashMap<String, String> map4 = new HashMap<>();
                    map4.put("name","其他服务量");
                    int qtfw = Integer.parseInt(list.get("qtfw").toString());
                    map4.put("value",qtfw+"");
                    hashMaps.add(map4);

//                    HashMap<String, String> map1 = new HashMap<>();
//                    map1.put("name","服务总量");
//                    int fwzl = Integer.parseInt(list.get("total").toString());
//                    map1.put("value",fwzl+"");
//                    hashMaps.add(map1);


                    System.out.println(hashMaps);
                    object.put("data",hashMaps);
                    object.put("success","true");
                }else if (cmd.equals("getjob")){
//                    pd.put("starttime",json.getString("startTime"));
//                    pd.put("endtime",json.getString("endTime"));
                    List<PageData> list = dataStatManager.getjob(pd);
                    object.put("data",list);
                    object.put("success","true");
                }else if (cmd.equals("getemploy")){
//                    pd.put("starttime",json.getString("startTime"));
//                    pd.put("endtime",json.getString("endTime"));
                    List<PageData> list = dataStatManager.getemploy(pd);
                    object.put("data",list);
                    object.put("success","true");
                }else if (cmd.equals("getskill")){
//                    pd.put("starttime",json.getString("startTime"));
//                    pd.put("endtime",json.getString("endTime"));
                    PageData list = dataStatManager.getskill(pd);
                    ArrayList<HashMap<String,String>> hashMaps = new ArrayList<>();

                    HashMap<String, String> map1 = new HashMap<>();
                    map1.put("name","招聘服务");
                    int zpfw = Integer.parseInt(list.get("zpfw").toString());
                    map1.put("value",zpfw+"");
                    hashMaps.add(map1);

                    HashMap<String, String> map2 = new HashMap<>();
                    map2.put("name","就业服务");
                    map2.put("value",list.get("jyfw").toString());
                    hashMaps.add(map2);

                    HashMap<String, String> map3 = new HashMap<>();
                    map3.put("name","回访服务");
                    map3.put("value",list.get("hffw").toString());
                    hashMaps.add(map3);

                    HashMap<String, String> map4 = new HashMap<>();
                    map4.put("name","咨询服务");
                    int zxfw = Integer.parseInt(list.get("zxfw").toString());
                    map4.put("value",zxfw+"");
                    hashMaps.add(map4);

                    HashMap<String, String> map5 = new HashMap<>();
                    map5.put("name","政策服务");
                    map5.put("value",list.get("zcfw").toString());
                    hashMaps.add(map5);

                    HashMap<String, String> map6 = new HashMap<>();
                    map6.put("name","服务总量");
                    int fwzl = Integer.parseInt(list.get("zpfw").toString())+
                            Integer.parseInt(list.get("jyfw").toString())+
                            Integer.parseInt(list.get("hffw").toString())+
                            Integer.parseInt(list.get("zxfw").toString())+
                            Integer.parseInt(list.get("zcfw").toString());
                    map6.put("value",fwzl+"");
                    hashMaps.add(map6);


                    object.put("data",hashMaps);
                    object.put("success","true");
                }else if (cmd.equals("getquarter")){
                    PageData pageData = dataStatManager.quarterList(pd);
                    object.put("data",pageData);
                    object.put("success","true");
                }else if (cmd.equals("isjobdata")){
                    List<PageData> agelist = dataStatManager.isjobToage(pd);
                    List<PageData> xllist = dataStatManager.isjobToxl(pd);
                    List<PageData> sexlist = dataStatManager.isjobTosex(pd);
                    object.put("age",agelist);
                    object.put("xl",xllist);
                    object.put("sex",sexlist);
                    object.put("success","true");
                }
//            } else {
//                object.put("success", "false");
//                object.put("msg", "登录超时，请重新登录");
//            }
//        }catch (Exception e) {
//        object.put("success", "false");
//        object.put("msg", "异常");
//    }
        return object;
    }
}
