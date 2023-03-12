package com.yulun.utils;



import com.fh.entity.Page;
import com.fh.util.PageData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author Aliar
 * @Time 2020-05-15 15:59
 **/
public class TimeHandle {
    private static java.util.Date Date;


    public static String endTimeHeandle(String time) {
        time += " 00:00:00";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = df.parse(time);
            date.setHours(23);
            date.setMinutes(59);
            date.setSeconds(59);
            String endTime = df.format(date);
            return endTime;
        } catch (Exception e) {
            return time;
        }
    }

    //根据时间段获取年、月、周的第一天
    public  static Map<String,String> getCountTime(String timeSolt){
        Map<String,String> map = new HashMap<String,String>();
        try{
        if(timeSolt == null || "".equals(timeSolt)){
            map.put("msg","timeSoltIsNull");
            return map;
        }

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        map.put("endTime", df.format(cal.getTime()) + " 23:59:59");
        if("week".equals(timeSolt)){
            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
            if (1 == dayWeek) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            Date time = cal.getTime();
            String weekhand = df.format(time);
            map.put("startTime",weekhand + " 00:00:00");
        }
        else if("month".equals(timeSolt)){
            cal.set(Calendar.DAY_OF_MONTH,1);
            String monthStart = df.format(cal.getTime());
            map.put("startTime",monthStart + " 00:00:00");
        }else if("year".equals(timeSolt)){
            cal.set(Calendar.DAY_OF_YEAR,1);
            String yearStart = df.format(cal.getTime());
            map.put("startTime",yearStart + " 00:00:00");
        }else{
            map.put("msg","timeSoltIsError");
        }
        return map;
        }catch (Exception e){
            map.put("msg","TimeHandleError");
            return map;
        }

    }


    public static String getTodayTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date time = cal.getTime();
        String today = df.format(time);
        return today  + " 23:59:59";
    }

    public static String getYesterdayTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,   -1);
        Date time = cal.getTime();
        String yesterday = df.format(time);
        return yesterday + " 23:59:59";
    }


    public static List<PageData> getTimeSoltAllDate(String startTime,String endTime) throws ParseException {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate  = sdf.parse(startTime);
        Date endDate  = sdf.parse(endTime);
        sdf.parse(endTime);
        List allDate = new ArrayList();
        allDate.add(startDate);
        allDate.add(endDate);
        Calendar calStart = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(endDate);
        calStart.setTime(startDate);
        List newAllDate = new ArrayList();
        newAllDate.add(startDate);
        while (endDate.after(calStart.getTime())) {
            calStart.add(Calendar.DAY_OF_MONTH, 1);
            newAllDate.add(calStart.getTime());
        }
        newAllDate.add(endDate);

        List<PageData> list = new ArrayList<PageData>();
        for (Object o : newAllDate) {
            PageData emp = new PageData();
            emp.put("endTime",sdf.format(o) + " 23:59:59");
            list.add(emp);
        }
        return list;



    }




}