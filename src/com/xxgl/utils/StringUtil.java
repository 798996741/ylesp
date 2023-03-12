package com.xxgl.utils;

import java.util.*;

import com.fh.util.PageData;
import org.apache.commons.lang.ArrayUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import net.sourceforge.pinyin4j.PinyinHelper;

public class StringUtil {
	
	public static String randomStr(int minlen, int maxlen) {
		int minLen = minlen > maxlen ? maxlen : minlen;
		int maxLen = minlen > maxlen ? minlen : maxlen;
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		Random random = new Random((new Date()).getTime());
		StringBuffer sb = new StringBuffer();
		int length = random.nextInt(maxLen - minLen + 1);
		for (int i = 0; i < length + minLen; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	//数字转字母 1-26 ： A-Z
	public static String numberToLetter(int num) {
	    if (num <= 0) {
	        return null;
	    }
	    String letter = "";
	    num--;
	    do {
	        if (letter.length() > 0) {
	            num--;
	        }
	        letter = ((char) (num % 26 + (int)'A')) + letter;
	        num = (int) ((num - num % 26) / 26);
	    } while (num > 0);
	    return letter;
	}
	
	 /**
	    * 提取每个汉字的首字母
      * 
      * @param str
      * @return String
      */
     public static String getPinYinHeadChar(String str) {
         String convert = "";
         for (int j = 0; j < str.length(); j++) {
             char word = str.charAt(j);
             // 提取汉字的首字母
             String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
             if (pinyinArray != null) {
                 convert += pinyinArray[0].charAt(0);
             } else {
                 convert += word;
             }
         }
         return convert;
    }
     
     //使用空格连接定长字符串
     public static String spliceBySpace(String str1,int length){
    	 //处理半角空格
    	 int delta1 = str1.length()-str1.replaceAll("  ","").length();
    	 int spaceNum = length-(str1.replaceAll(" ","").length()*2)-delta1;
    	 String spaces = "";
    	 if(spaceNum>0){
    		 for(int i=0;i<spaceNum;i++){
    			 spaces = spaces+" ";
    		 }
    	 }
    	 return str1+spaces;
     }
     
     //补齐三位全角
     public static String nameSpace(String str){
    	 if(str==null){
    		 return "";
    	 }
    	 if(str.length()==2){
    		 String[] strs = str.split("");
    		 return strs[0]+"&emsp;"+strs[1];
    	 }else {
    		 return str;
    	 }
     }
     
     //补齐三位全角
     public static String nameBoard(String str){
    	 if(str==null){
    		 return "";
    	 }
    	 if(str.length()==2){
    		 String[] strs = str.split("");
    		 return strs[0]+"  "+strs[1];
    	 }else {
    		 return str;
    	 }
     }
     
     //补齐三位全角(空格数量可输入,字数限制可输入)
     public static String nameBoard(String str, int num, int word_num){
         if(str==null){
             return "";
         }
         if(str.length()==word_num){
             String[] strs = str.split("");
             String spaceStr = "";
             String result = "";
             for(int i=0; i<num; i++){
                 spaceStr += " ";
             }
             for(int i=0; i<word_num; i++){
                 if(i == 0){
                     result = strs[i];
                 }else{
                     result += spaceStr + strs[i];
                 }
             }
             return result;
         }else {
             return str;
         }
     }
     
     //车辆信息处理小轿车*2,面包车*1
     public static String vehicleInfo(String str){
    	 if(str==null){
    		 return "";
    	 }
    	 String result = "";
    	 String countStr = "";	//数量字符串
    	 String[] arr = str.split(",");
    	 for(String veStr : arr){
    		 countStr = veStr.substring(veStr.lastIndexOf("*")+1);
    		 veStr = veStr.substring(0, veStr.lastIndexOf("*"));
    		 result += countStr + "辆" + veStr + "，";
    	 }
    	 return result.substring(0, result.length()-1);
     }
     
     public static String delHtmlTag(String content){
         // 标签过滤"待处理富文本内容"; 
         Document document = Jsoup.parse(content);
         content = document.text();
         // 截取前100位
         //Integer contentLength = content.length();
         //content = content.substring(0, contentLength >= 100 ? 100 : contentLength);
         return content;
     }
     
     //座位号信息处理5排30,5排32,5排34,6排30,6排32,6排34，处理后为5排30号、32号34号，6排32号、34号
     public static String seatInfo(String content){
         if(!"".equals(content)){
             if(content.contains(",")){
                 String newContent = "";
                 String str_ori = "";
                 for(String str : content.split(",")){
                     if("".equals(str_ori)){
                         str_ori = str.substring(0, str.indexOf("排")+1);
                         newContent = str + "号";
                     }else{
                         if(str.contains(str_ori)){
                             newContent += "、" + str.substring(str.indexOf("排")+1) + "号";
                         }else{
                             str_ori = str.substring(0, str.indexOf("排")+1);
                             newContent += "，" + str + "号";
                         }
                     }
                 }
                 content = newContent;
             }
         }
         return content;
     }
     

	/**
	 * 新增元素
	 * @param oldArr
	 * @param newArr
	 * @return
	 */
	public static String[] getNewArr(String[] oldArr, String[] newArr){
		List<String> addList = new ArrayList<>();
		for (String aNewArr : newArr) {
			if (!ArrayUtils.contains(oldArr, aNewArr)) {
				addList.add(aNewArr);
			}
		}
		String[] addArr = addList.toArray(new String[addList.size()]);
     	return addArr;
	}

	/**
	 * 删除元素
	 * @param oldArr
	 * @param newArr
	 * @return
	 */
	public static String[] getDelArr(String[] oldArr, String[] newArr){
		List<String> deleteList = new ArrayList<>();

		for (String anOldArr : oldArr) {
			if (!ArrayUtils.contains(newArr, anOldArr)) {
				deleteList.add(anOldArr);
			}
		}
		String[] deleteArr = deleteList.toArray(new String[deleteList.size()]);
		return deleteArr;
	}

	/**
	 * 未修改元素
	 * @param oldArr
	 * @param newArr
	 * @return
	 */
	public static String[] getEditArr(String[] oldArr, String[] newArr){
		List<String> editList = new ArrayList<>();

		for (String anOldArr : oldArr) {
			if (ArrayUtils.contains(newArr, anOldArr)) {
				editList.add(anOldArr);
			}
		}
		String[] deleteArr = editList.toArray(new String[editList.size()]);
		return deleteArr;
	}

	/**
	 * 判断list 是否存在属性name 值为value的数据
	 * @param results
	 * @param name
	 * @param value
	 * @return
	 */
	public static PageData isExist(List<PageData> results, String name, String value) {
		for (PageData pd : results) {
			if (pd.getString(name).equals(value)) {
				return pd;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		String[] oldArr = {"1","2"};
		String[] newArr = {"1","3","4"};
		System.out.println("deleteArr: "+ Arrays.toString(StringUtil.getDelArr(oldArr, newArr)));
		System.out.println("addArr: "+ Arrays.toString(StringUtil.getNewArr(oldArr, newArr)));
		System.out.println("editArr: "+ Arrays.toString(StringUtil.getEditArr(oldArr, newArr)));
	}
	
}
