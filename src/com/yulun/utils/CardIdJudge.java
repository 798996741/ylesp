package com.yulun.utils;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author Aliar
 * @Time 2020-05-29 0:34
 **/
public class CardIdJudge {
        public static PageData getInfo(String cardId ){
                PageData pd = new PageData();
                try {
                        int length = cardId.length();
                        if(length != 18) {
                                throw new Exception();
                        }
                        int sex = cardId.indexOf(16);
                        if (sex % 2 == 0) {
                                pd.put("sex", "2");
                        } else {
                                pd.put("sex", "1");
                        }
                        String age = cardId.substring(6, 10);
                        SimpleDateFormat df = new SimpleDateFormat("yyyy");
                        String year = df.format(new Date());
                        int ageResult = Integer.parseInt(year) - Integer.parseInt(age);
                        if (ageResult > 0 && ageResult < 200) {
                                pd.put("age", String.valueOf(ageResult));
                        } else {
                                pd.put("age", "");
                        }
                        return pd;
                }catch (Exception e){
                        pd.put("age","");
                        pd.put("sex","");
                        return pd;
                }
        }
}
