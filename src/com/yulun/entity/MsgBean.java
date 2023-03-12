package com.yulun.entity;

public class MsgBean {
	
	//公司名称
	private String ecName; 
	private String apId;
	//秘钥
	private String secretKey;
	//手机号码
	private String mobiles;
	//短信内容
	private String content;
	//签名编码
	private String sign;
	
	public String getEcName() {
		return ecName;
	}
	public void setEcName(String ecName) {
		this.ecName = ecName;
	}
	public String getApId() {
		return apId;
	}
	public void setApId(String apId) {
		this.apId = apId;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getMobiles() {
		return mobiles;
	}
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getAddSerial() {
		return addSerial;
	}
	public void setAddSerial(String addSerial) {
		this.addSerial = addSerial;
	}
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	//扩展码
	private String addSerial;
	//参数校验序列
	private String mac ;

	
	

}
