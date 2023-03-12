package com.xxgl.utils;

import java.util.ArrayList;
import java.util.List;

public class RelationUtils {
	public static List<String> getRelationTable(String[] data1,String[] data2) {
		List<String> relationTable = new ArrayList<String>();
		for (int i = 0; i < data1.length; i++) {
			if(!exist(data2,data1[i])) {
				relationTable.add(data1[i]);
			}
		}
		return relationTable;
	}
	
	public static boolean exist(String[] arrayData,String data) {
		for (int i = 0; i < arrayData.length; i++) {
			if(arrayData[i].equals(data)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 移除某字段
	 * @param arrayData
	 * @param data
	 * @return
	 */
	public static String remove(String[] arrayData,String data) {
		List<String> newArray = new ArrayList<String>();
		for (int i = 0; i < arrayData.length; i++) {
			if(!arrayData[i].equals(data) && !arrayData[i].equals("")) {
				newArray.add(arrayData[i]);
			}
		}
		newArray=distinctData(newArray);
		return transStr(newArray);
	}

	/**
	 * 空值处理（群组问题）
	 * @param dataStr
	 * @return
	 */
	public static List<String> filterEmpty(String dataStr) {
		List<String> newArray = new ArrayList<String>();
		if(!dataStr.replaceAll("\n","").equals("")){
			String []arrayData=dataStr.replaceAll("\n","").split(",");
			for (int i = 0; i < arrayData.length; i++) {
				if(!arrayData[i].equals("")) {
					newArray.add(arrayData[i]);
				}
			}
			newArray=distinctData(newArray);
		}
		return newArray;
	}

	/**
	 * 数组转字符串'，'分割
	 * @param data
	 * @return
	 */
	public static String transStr(List<String> data) {
		String newStr="";
		for (int i = 0; i < data.size(); i++) {
			newStr+=data.get(i)+",";
		}
		if(!newStr.equals("")) {
			newStr=newStr.substring(0, newStr.length()-1);
		}
		return newStr;
	}

	/**
	 * 新增某字段
	 * @param temp
	 * @param data
	 * @return
	 */
	public static String appendData(String temp,String data) {
		List<String> newArray = new ArrayList<String>();
		if(filterEmpty(temp)!=null && filterEmpty(temp).size()>0){
			newArray=filterEmpty(temp);
		}
		if(!data.equals("")){
			newArray.add(data);
		}
		return transStr(newArray);
	}

	/**
	 * 去重
	 * @param list
	 * @return
	 */
	public static List<String> distinctData(List<String> list) {
		List<String> listTemp = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			if(!listTemp.contains(list.get(i))){
				listTemp.add(list.get(i));
			}
		}
		return listTemp;
	}


}
