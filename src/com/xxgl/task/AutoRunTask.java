package com.xxgl.task;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.yulun.entity.SendRes;
import com.yulun.service.CompanyManager;

import com.yulun.utils.ZpSmsUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.util.PageData;
import com.fh.util.SmsUtil;
import com.fh.util.httpclient;
import com.xxgl.service.mng.WorkorderManager;
import com.yulun.service.MsgTempManager;
import com.yulun.utils.CtSmsUtils;
@Component
public class AutoRunTask {
    
	@Resource(name="workorderService")
	private WorkorderManager workorderService;
	
	@Resource(name = "msgTempService")
	private MsgTempManager msgTempManager;

	@Resource(name="companyService")
	private CompanyManager companyService;
	
    @Scheduled(cron = "0/10 * * * * ? ") // 间隔10秒执行
    public void execute() throws Exception {
    	//漳浦人社
//    	sendMsg();
		sendMsgZp();
    	//长泰人社
//    	sendMsgCt();

	}

	@Scheduled(cron = "0 15 10 ? * 1") // 每天凌晨1点执行
	public void execute2() throws Exception {
		PageData pd = new PageData();
		companyService.deleteJobMatching(pd);
		companyService.deleteEmpMatching(pd);

	}

	@Scheduled(cron = "0 15 15 ? * 1") // 每天凌晨1点执行
	public void execute3() throws Exception {
		PageData pd = new PageData();
		companyService.insertJobMatching(pd);
		companyService.insertEmpMatching(pd);
	}
	public void sendMsgZp(){
		PageData pd=new PageData();
		List<PageData> list=new ArrayList();
		try {
			pd.put("time",getTime());
			list = msgTempManager.findMsgByState(pd);//查询是否需要发送的短信记录
			String tel="";
			String text="",smstype="";
			//String id="";
			JSONObject data =null;
			String indata="",returnmsg="";
			String returnstate="";
			int state=0;

			for(PageData pd_fl:list){
				List<PageData> sendMsgId = msgTempManager.findSendMsgId(pd_fl);
				if (sendMsgId.size()==0){
					//id=String.valueOf(pd_fl.get("id")==null?"":pd_fl.get("id"));
					tel=pd_fl.getString("phone")==null?"":pd_fl.getString("phone");
					text=pd_fl.getString("content")==null?"":pd_fl.getString("content");
					smstype=pd_fl.getString("smstype")==null?"":pd_fl.getString("smstype");


					if(tel.length()==11&&!text.equals("")){
						if(isPhone(tel)){
							SendRes sendRes = ZpSmsUtils.sendMsg(tel, text);


							if(sendRes!=null){

								//if(sendRes.isSuccess() && !"".equals(sendRes.getMsgGroup()) && "success".equals(sendRes.getRspcod())){
								//return 1;
								//}else{
								//return 0;
								//}

								returnstate=sendRes.getRspcod();
								switch (returnstate){
									case "success":
										if(sendRes.isSuccess()&& !"".equals(sendRes.getMsgGroup())){
											state=1;
											returnmsg=sendRes.getMsgGroup();
											break;
										}else{
											returnmsg="其它错误";
											break;
										}
									case "IllegalMac":
										returnmsg="mac校验不通过";
										break;

									case "IllegalSignId":
										returnmsg="无效的签名编码";
										break;
									case "InvalidMessage":
										returnmsg="非法消息，请求数据解析失败";
										break;
									case "InvalidUsrOrPwd":
										returnmsg="非法用户名/密码";
										break;
									case "NoSignId":
										returnmsg="匹配到对应的签名信息";
										break;
									case "TooManyMobiles":
										returnmsg="手机号数量超限（>5000），应≤5000";
										break;
									default:
										returnmsg="其它错误";
										break;
								}
								pd_fl.put("state", state);
								pd_fl.put("returnmsg", returnmsg);
								msgTempManager.updateState(pd_fl); //修改发送状态
								if (1==state) {
									msgTempManager.insertMsgId(pd_fl);
								}
							}

						}else{
							pd_fl.put("state", "-1");
							pd_fl.put("returnmsg", "手机号码格式不正确");
							msgTempManager.updateState(pd_fl); //修改发送状态
						}

					}else{
						pd_fl.put("state", "-1");
						pd_fl.put("returnmsg", "手机号码格式不正确获取内容为空");
						msgTempManager.updateState(pd_fl); //修改发送状态
					}
				}else {
					msgTempManager.updateMsgId(pd_fl);
				}


			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
   
    public void sendMsg(){
    	PageData pd=new PageData();
    	List<PageData> list=new ArrayList();
		try {
			pd.put("time",getTime());
			list = msgTempManager.findMsgByState(pd);//查询是否需要发送的短信记录
			String tel="";
			String text="",smstype="";
			//String id="";
			JSONObject data =null;
			String indata="",returnmsg="";
			int returnstate=0,state=-1;
	        for(PageData pd_fl:list){
	        	//id=String.valueOf(pd_fl.get("id")==null?"":pd_fl.get("id"));	
	        	tel=pd_fl.getString("phone")==null?"":pd_fl.getString("phone");	
	        	text=pd_fl.getString("content")==null?"":pd_fl.getString("content");	
	        	smstype=pd_fl.getString("smstype")==null?"":pd_fl.getString("smstype");	
	        	if(tel.length()==11&&!text.equals("")){
	        		if(isPhone(tel)){
	        			
	        			indata=httpclient.sendMsg(tel,smstype, "【漳浦人社】"+text); //发送短信
		        		if(indata!=null&&!indata.equals("")){
		        			data=JSONObject.parseObject(indata);
			        		returnstate=Integer.parseInt(String.valueOf(data.get("code")));
			        		switch (returnstate){
				                case 0:
				                	state=1;
				                	returnmsg=data.getString("pack_id");
				                    break;
				                case -1:
				                	returnmsg="账号无效";
				                    break;
				                case -2:
				                	returnmsg="参数：无效";
				                    break;
				                case -3:
				                	returnmsg="连接不上服务器";
				                    break;
				                case -6:
				                	returnmsg="用户名密码错误";
				                    break;
				                case -9:
				                	returnmsg="资金账户不存在";
				                    break;
				                case -11:
				                	returnmsg="包号码数量超过最大限制";
				                    break;
				                case -12:
				                	returnmsg="余额不足";
				                    break;
				                case -13:
				                	returnmsg="账号没有发送权限";
				                    break;
				                default:
				                	returnmsg="其它错误";
				                    break;
				            }
			        		pd_fl.put("state", state);
			        		pd_fl.put("returnmsg", returnmsg);
			        		msgTempManager.updateState(pd_fl); //修改发送状态
		        		}
		        		
	        		}else{
	        			pd_fl.put("state", "-1");
		        		pd_fl.put("returnmsg", "手机号码格式不正确");
		        		msgTempManager.updateState(pd_fl); //修改发送状态
	        		}
	        		
	        	}else{
	        		pd_fl.put("state", "-1");
	        		pd_fl.put("returnmsg", "手机号码格式不正确获取内容为空");
	        		msgTempManager.updateState(pd_fl); //修改发送状态
	        	}
	        	
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
    }
    
    
    public void sendMsgCt(){
    	PageData pd=new PageData();
    	List<PageData> list=new ArrayList();
		try {
			pd.put("time",getTime());
			list = msgTempManager.findMsgByState(pd);//查询是否需要发送的短信记录
			String tel="";
			String text="",smstype="";
			//String id="";
			JSONObject data =null;
			String indata="",returnmsg="";
			String returnstate="";
			int state=0;

	        for(PageData pd_fl:list){
				List<PageData> sendMsgId = msgTempManager.findSendMsgId(pd_fl);
				if (sendMsgId.size()==0){
					//id=String.valueOf(pd_fl.get("id")==null?"":pd_fl.get("id"));
					tel=pd_fl.getString("phone")==null?"":pd_fl.getString("phone");
					text=pd_fl.getString("content")==null?"":pd_fl.getString("content");
					smstype=pd_fl.getString("smstype")==null?"":pd_fl.getString("smstype");


					if(tel.length()==11&&!text.equals("")){
						if(isPhone(tel)){
							SendRes sendRes =CtSmsUtils.sendMsg(tel, text);


							if(sendRes!=null){

								//if(sendRes.isSuccess() && !"".equals(sendRes.getMsgGroup()) && "success".equals(sendRes.getRspcod())){
								//return 1;
								//}else{
								//return 0;
								//}

								returnstate=sendRes.getRspcod();
								switch (returnstate){
									case "success":
										if(sendRes.isSuccess()&& !"".equals(sendRes.getMsgGroup())){
											state=1;
											returnmsg=sendRes.getMsgGroup();
											break;
										}else{
											returnmsg="其它错误";
											break;
										}
									case "IllegalMac":
										returnmsg="mac校验不通过";
										break;

									case "IllegalSignId":
										returnmsg="无效的签名编码";
										break;
									case "InvalidMessage":
										returnmsg="非法消息，请求数据解析失败";
										break;
									case "InvalidUsrOrPwd":
										returnmsg="非法用户名/密码";
										break;
									case "NoSignId":
										returnmsg="匹配到对应的签名信息";
										break;
									case "TooManyMobiles":
										returnmsg="手机号数量超限（>5000），应≤5000";
										break;
									default:
										returnmsg="其它错误";
										break;
								}
								pd_fl.put("state", state);
								pd_fl.put("returnmsg", returnmsg);
								msgTempManager.updateState(pd_fl); //修改发送状态
								if (1==state) {
									msgTempManager.insertMsgId(pd_fl);
								}
							}

						}else{
							pd_fl.put("state", "-1");
							pd_fl.put("returnmsg", "手机号码格式不正确");
							msgTempManager.updateState(pd_fl); //修改发送状态
						}

					}else{
						pd_fl.put("state", "-1");
						pd_fl.put("returnmsg", "手机号码格式不正确获取内容为空");
						msgTempManager.updateState(pd_fl); //修改发送状态
					}
				}else {
					msgTempManager.updateMsgId(pd_fl);
				}

	        	
	        }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	        
    }
    
    public static boolean isPhone(String phone) {
    	String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
    	if (phone.length() != 11) {
    		return false;
    	} else {
    		Pattern p = Pattern.compile(regex);
    		Matcher m = p.matcher(phone);
    		boolean isMatch = m.matches();
    		return isMatch;
    	}

    }

	public String getTime() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

}