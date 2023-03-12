package com.yulun.entity;

import java.util.Arrays;

public class sendDSMS {
    private String[] mobiles;
    private String smsContent;
    private String addSerial; //扩展码 填写空字符串（""）
    private int SMSPriority; //优先级。取值1-5，其它值默认为1。
    private String sign; //签名编码
    private String msgGroup; //消息批次号
    private boolean isMo; //是否需要上行

    public sendDSMS() {
    }
    private static sendDSMS instance= new sendDSMS();
    public static sendDSMS sendDSMS(){
        return instance;
    }

    @Override
    public String toString() {
        return "sendDSMS{" +
                "mobiles=" + Arrays.toString(mobiles) +
                ", smsContent='" + smsContent + '\'' +
                ", addSerial='" + addSerial + '\'' +
                ", SMSPriority=" + SMSPriority +
                ", sign='" + sign + '\'' +
                ", msgGroup='" + msgGroup + '\'' +
                ", isMo=" + isMo +
                '}';
    }

    public sendDSMS(String[] mobiles, String smsContent, String addSerial, int SMSPriority, String sign, String msgGroup, boolean isMo) {
        this.mobiles = mobiles;
        this.smsContent = smsContent;
        this.addSerial = addSerial;
        this.SMSPriority = SMSPriority;
        this.sign = sign;
        this.msgGroup = msgGroup;
        this.isMo = isMo;
    }


    public String[] getMobiles() {
        return mobiles;
    }

    public void setMobiles(String[] mobiles) {
        this.mobiles = mobiles;
    }

    public String getSmsContent() {
        return smsContent;
    }

    public void setSmsContent(String smsContent) {
        this.smsContent = smsContent;
    }

    public String getAddSerial() {
        return addSerial;
    }

    public void setAddSerial(String addSerial) {
        this.addSerial = addSerial;
    }

    public int getSMSPriority() {
        return SMSPriority;
    }

    public void setSMSPriority(int SMSPriority) {
        this.SMSPriority = SMSPriority;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMsgGroup() {
        return msgGroup;
    }

    public void setMsgGroup(String msgGroup) {
        this.msgGroup = msgGroup;
    }

    public boolean isMo() {
        return isMo;
    }

    public void setMo(boolean mo) {
        isMo = mo;
    }
}
