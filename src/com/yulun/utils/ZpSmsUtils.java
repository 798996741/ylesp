package com.yulun.utils;

import com.alibaba.fastjson.JSON;
import com.fh.util.MD5;
import com.yulun.entity.MsgBean;
import com.yulun.entity.SendRes;
import org.apache.commons.codec.binary.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/*
 * @author huangjianling
 * @date 2020-11-27
 * 云SMS  漳浦短信发送
 */
public class ZpSmsUtils {

	private static String apId="yulunZ";
	private static String secretKey="Ylzprs@2020";
	//集团名称
	private static String ecName = "漳浦县劳动就业中心";
	//网关签名编码
	private static String sign = "fJdxpGgTm";
	//拓展码 填空
	private static String addSerial = "";	
	public static String url = "http://112.35.1.155:1992/sms/norsubmit";//请求url

	/**
	 * 多用户发送短信信息
	 * @param mobiles 手机号码逗号分隔
	 * @param content 短信内容
	 * @return 返回1表示成功，0表示失败
	 * @throws IOException
	 */
	public static SendRes sendMsg(String mobiles,String content) throws IOException{
		Calendar calendar = Calendar.getInstance();		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowDatestr = sdf.format(calendar.getTimeInMillis()); 
		content += nowDatestr; //短信内容后跟个日期时间（可有可无），需求要求

		MsgBean sendReq = new MsgBean();
		sendReq.setApId(apId);
		sendReq.setEcName(ecName);
		sendReq.setSecretKey(secretKey);
		sendReq.setContent(content);
		sendReq.setMobiles(mobiles);
		sendReq.setAddSerial(addSerial);
		sendReq.setSign(sign);
        System.out.println(ecName+"发送");
        StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(sendReq.getEcName());
		stringBuffer.append(sendReq.getApId());
		stringBuffer.append(sendReq.getSecretKey());
		stringBuffer.append(sendReq.getMobiles());
		stringBuffer.append(sendReq.getContent());
		stringBuffer.append(sendReq.getSign());
		stringBuffer.append(sendReq.getAddSerial());

		sendReq.setMac(MD5.md5(stringBuffer.toString()).toLowerCase());
		System.out.println(sendReq.getMac());

		String reqText = JSON.toJSONString(sendReq);

		String encode = Base64.encodeBase64String(reqText.getBytes("UTF-8"));
		//System.out.println(encode);

		String resStr = sendPost(url,encode);

		System.out.println("发送短信结果："+resStr);

		SendRes sendRes = JSON.parseObject(resStr,SendRes.class);
		
		return sendRes;
		/*if(sendRes.isSuccess() && !"".equals(sendRes.getMsgGroup()) && "success".equals(sendRes.getRspcod())){
			return 1;
		}else{
			return 0;
		}*/
	}

	
    //main方法测试发送短信，返回1表示成功，0表示失败  
   	public static void main(String[] args) throws IOException{
    	String msg = "这是发送短信的内容！";
    	SendRes result = sendMsg("15759651985",msg);
        System.out.println("==="+result);
    }

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url 发送请求的 URL
	 * @param param 请求参数
	 * @return 所代表远程资源的响应结果
	 */
	private static String sendPost(String url, String param) {
		OutputStreamWriter out = null;

		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("contentType","utf-8");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			conn.setDoOutput(true);
			conn.setDoInput(true);

			out = new OutputStreamWriter(conn.getOutputStream());
			out.write(param);
			out.flush();


			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

}
