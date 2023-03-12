package com.yulun.utils;

import java.math.BigDecimal;

/**
 * @Auther: Administrator
 * @Date: 2019/9/5 11:46
 * @Description:
 */
public class FloatUtil {

    public  static  float interceptFloatValue(float value,int scale){
        return new BigDecimal(value).setScale(scale,BigDecimal.ROUND_HALF_UP ).floatValue();
    }

}
