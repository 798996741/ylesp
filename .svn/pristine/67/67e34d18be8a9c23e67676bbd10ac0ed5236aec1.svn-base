package com.xxgl.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

/**
 * 操作Excel表格的功能类
 */
public class ExcelReader {
	private POIFSFileSystem fs;
	private HSSFWorkbook wb;
	private HSSFSheet sheet;
	private HSSFRow row;

	/**
	 * 读取Excel表格表头的内容
	 * 
	 * @param InputStream
	 * @return String 表头内容的数组
	 */
	public String[] readExcelTitle(InputStream is,int sheetNum) {
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = wb.getSheetAt(sheetNum);
		row = sheet.getRow(0);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		//System.out.println("colNum:" + colNum);
		String[] title = new String[colNum+1];
		for (int i = 0; i < colNum; i++) {
			// title[i] = getStringCellValue(row.getCell((short) i));
			title[i] = getCellFormatValue(row.getCell((short)i));
		}
		title[colNum] = "辖区政府";
		return title;
	}

	/**
	 * 读取反馈各区数据.xls数据内容
	 * (反馈各区数据)
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public Map<Integer, String[]> readExcelContent(InputStream is,String[] headerModel,int sheetNum) {
		Map<Integer, String[]> content = new HashMap<Integer, String[]>();
		/*try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}*/
		sheet = wb.getSheetAt(sheetNum);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println("总列数："+colNum);
		if(headerModel!=null){
			if(headerModel.length == colNum){
				row = sheet.getRow(0);
				for(int i=0;i<colNum;i++){
					String val = getCellFormatValue(row.getCell((short) i)).trim();
					if(!headerModel[i].equals(val)){
						throw new RuntimeException("EXCEL数据格式不对");
					}
				}
			}else{
				throw new RuntimeException("EXCEL数据格式不对");
			}
		}
		String[] arrVal = new String[colNum+1];
		String city = "";
		//四个区结束标志
		boolean fourAreaEndFlg=false;
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			if(row == null){
				
			}else{
				int j = 0;
				int nullInt = 0;
				while (j < colNum) {
					arrVal[j]=getCellFormatValue(row.getCell((short) j)).trim();
					if(StringUtils.isBlank(arrVal[j])){
						if(j==0){//四个区结束条件为第一个cell没有数据
							fourAreaEndFlg=true;
						}
						nullInt++;
					}
					j++;
				}
				String str = "";
				for (String string : arrVal) {
					if (string != null) {
						str = str + string;
					}
				}
				if(str.isEmpty()){
					
				}else{
					if(sheetNum==1){//四个区处理
						if (fourAreaEndFlg) {
							return content;
						}else{
							arrVal[colNum]="";//辖区政府值置空
							content.put(i, arrVal);
						}
					}else if(sheetNum==0){//七个区处理
						if (nullInt == colNum - 1) {
							int charIndex = str.indexOf("（");
							if (charIndex != -1) {
								city = str.substring(0,charIndex);
							} else {
								city = str;
							}
							if (city.matches(".*\\d-\\d月.*")){
								return content;
							}
						} else if (nullInt < colNum - 1) {
							if (!city.isEmpty()) {
								arrVal[colNum] = city;
							}
							content.put(i, arrVal);
						}
					}
				}
				arrVal = new String[colNum+1];
			}
		}
		return content;
	}
	
	/**
	 * 读取海东房地产投资、施工、销售数据内容
	 * (海东房地产投资、施工、销售)
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public Map<Integer, String[]> readExcelContent1(InputStream is,String[] headerModel,int sheetNum,HSSFWorkbook wb) {
		Map<Integer, String[]> content = new HashMap<Integer, String[]>();
		sheet = wb.getSheetAt(sheetNum);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println("总列数："+colNum);
		if(headerModel!=null){
			if(headerModel.length == colNum){
				row = sheet.getRow(0);
				for(int i=0;i<colNum;i++){
					String val = getCellFormatValue(row.getCell((short)i)).trim();
					if(!headerModel[i].equals(val)){
						throw new RuntimeException("EXCEL数据格式不对");
					}
				}
			}else{
				throw new RuntimeException("EXCEL数据格式不对");
			}
		}
		String[] arrVal = new String[colNum];
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			int j = 0;
			int nullInt = 0;
			while (j < colNum) {
				arrVal[j]=getCellFormatValue(row.getCell((short)j)).trim();
				if(StringUtils.isBlank(arrVal[j])){
					nullInt++;
				}
				j++;
			}
			if(nullInt<colNum){
				content.put(i, arrVal);
			}
			arrVal = new String[colNum];
		}
		return content;
	}

	/**
	 * 读取海东房地产投资、施工、销售数据内容
	 * (海东房地产投资、施工、销售)
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public List<Map<String,String>> readExcelContentHouse(InputStream is,String[] headerModel,int sheetNum,HSSFWorkbook wb) {
		List<Map<String,String>> list = new ArrayList<>();
		Map<Integer, String[]> content = new HashMap<Integer, String[]>();
		sheet = wb.getSheetAt(sheetNum);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		System.out.println("总列数："+colNum);
		if(headerModel!=null){
			if(headerModel.length == colNum){
				row = sheet.getRow(0);
				for(int i=0;i<colNum;i++){
					String val = getCellFormatValue(row.getCell((short)i)).trim();
					if(!headerModel[i].equals(val)){
						throw new RuntimeException("EXCEL数据格式不对");
					}
				}
			}else{
				throw new RuntimeException("EXCEL数据格式不对");
			}
		}
		String[] arrVal = new String[colNum+1];
		String city = "";
		String[] title = null;
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i <= rowNum; i++) {
			row = sheet.getRow(i);
			int j = 0;
			int nullInt = 0;
			while (j < colNum) {
				arrVal[j]=getCellFormatValue(row.getCell((short)j)).trim();
				if(StringUtils.isBlank(arrVal[j])){
					nullInt++;
				}
				j++;
			}
			String str = "";
			for (String string : arrVal) {
				if (string != null) {
					str = str + string;
				}
			}
			if(str == ""){
				
			}else{
				if (nullInt == colNum - 1) {
					city = str.replaceAll(" ", "");
					title = null;
				} else if (nullInt < colNum - 1) {
					if (title == null) { // 城市行下面的表头
						title = arrVal;
						arrVal[colNum] = "辖区政府";
					} else {//表内容
						if (!city.isEmpty()) {
							arrVal[colNum] = city;
						}
						Map<String,String> item = new HashMap<>();
						for(int k=0;k<arrVal.length;k++){
							item.put(title[k], arrVal[k]);
						}
						list.add(item);
					}
				}
			}
			arrVal = new String[colNum+1];
		}
		return list;
	}

	/**
	 * 读取Excel数据内容
	 * 
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public int readExcelContent(InputStream is,String[] headerModel,ExcelReaderCallback callback) {
		Map<Integer, String[]> content = new HashMap<Integer, String[]>();
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		if(headerModel!=null){
			if(headerModel.length == colNum){
				row = sheet.getRow(0);
				for(int i=0;i<colNum;i++){
					String val = getCellFormatValue(row.getCell((short) i)).trim();
					if(!headerModel[i].equals(val)){
						throw new RuntimeException("EXCEL数据格式不对");
					}
				}
			}else{
				throw new RuntimeException("EXCEL数据格式不对");
			}
		}
		String[] arrVal = new String[colNum];
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 1; i < rowNum; i++) {
			row = sheet.getRow(i);
			int j = 0;
			int nullInt = 0;
			while (j < colNum) {
				arrVal[j]=getCellFormatValue(row.getCell((short)j)).trim();
				if(StringUtils.isBlank(arrVal[j])){
					nullInt++;
				}
				j++;
			}
			if(nullInt<colNum){
				content.put(i, arrVal);
			}
			if(content.size() == 5000){
				callback.execute(content);
				content.clear();
			}
			arrVal = new String[colNum];
		}
		if(content.size() > 0){
			callback.execute(content);
			content.clear();
		}
		return rowNum;
	}
    
	/**
	 * 获取单元格数据内容为字符串类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getStringCellValue(HSSFCell cell) {
		if (cell == null) {
			return "";
		}
		String strCell = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:
			strCell = cell.getStringCellValue();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			strCell = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			strCell = String.valueOf(cell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			strCell = "";
			break;
		default:
			strCell = "";
			break;
		}
		if (strCell == null || strCell.equals("") ) {
			return "";
		}
		return strCell;
	}

	/**
	 * 获取单元格数据内容为日期类型的数据
	 * 
	 * @param cell
	 *            Excel单元格
	 * @return String 单元格数据内容
	 */
	private String getDateCellValue(HSSFCell cell) {
		String result = "";
		try {
			int cellType = cell.getCellType();
			if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
				Date date = cell.getDateCellValue();
				result = (date.getYear() + 1900) + "-" + (date.getMonth() + 1)
						+ "-" + date.getDate();
			} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
				String date = getStringCellValue(cell);
				result = date.replaceAll("[年月]", "-").replace("日", "").trim();
			} else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
				result = "";
			}
		} catch (Exception e) {
			System.out.println("日期格式不正确!");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据HSSFCell类型设置数据
	 * 
	 * @param cell
	 * @return
	 */
	private String getCellFormatValue(HSSFCell cell) {
		String cellvalue = "";
		if (cell != null) {
			// 判断当前Cell的Type
			switch (cell.getCellType()) {
			// 如果当前Cell的Type为NUMERIC
			case HSSFCell.CELL_TYPE_NUMERIC:
			case HSSFCell.CELL_TYPE_FORMULA: {
				// 判断当前的cell是否为Date
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					// 如果是Date类型则，转化为Data格式

					// 方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
					// cellvalue = cell.getDateCellValue().toLocaleString();

					// 方法2：这样子的data格式是不带带时分秒的：2011-10-12
					Date date = cell.getDateCellValue();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					cellvalue = sdf.format(date);

				}
				// 如果是纯数字
				else {
					// 取得当前Cell的数值
					try {
						cell.setCellType(Cell.CELL_TYPE_STRING);
					} catch (Exception e) {
						e.printStackTrace();
					}
					cellvalue = cell.getStringCellValue();
				}
				break;
			}
			// 如果当前Cell的Type为STRIN
			case HSSFCell.CELL_TYPE_STRING:
				// 取得当前的Cell字符串
				cellvalue = cell.getRichStringCellValue().getString();
				break;
			// 默认的Cell值
			default:
				cellvalue = " ";
			}
		} else {
			cellvalue = "";
		}
		return cellvalue;

	}
//---------------------------------------------
	public static void main(String[] args) {
		/*try {
			//getExcellData("C:\\Users\\admin\\Desktop\\fgw\\fgw_file\\新系统\\固投工具\\20160726工具报表固投\\2016年6月反馈各区数据.xls");
			//getExcellData1("C:\\Users\\admin\\Desktop\\fgw\\fgw_file\\新系统\\固投工具\\20160726工具报表固投\\aa.xls");
		} catch (FileNotFoundException e) {
			System.out.println("未找到指定路径的文件!");
			e.printStackTrace();
		}*/
	}
	
	public interface ExcelReaderCallback{
		public void execute(Map<Integer, String[]> map);
	}
	// 读取Excel表格内容(2016年6月反馈各区数据)
	public List<List<Map<String,String>>> getExcellData(InputStream is,InputStream is2) throws FileNotFoundException{
		List<List<Map<String,String>>> lists = new ArrayList<List<Map<String,String>>>();
		Map<String,String> map1 = new HashMap<String,String>(); 
		Map<String,String> map3 = new HashMap<String,String>(); 
		List<Map<String,String>> list1 = new ArrayList<Map<String,String>>();
		List<Map<String,String>> list3 = new ArrayList<Map<String,String>>();
		ExcelReader excelReader = new ExcelReader();
		String[] title = excelReader.readExcelTitle(is,1);
		String[] title2 = excelReader.readExcelTitle(is2,0);
		Map<Integer, String[]> map = excelReader.readExcelContent(is,null,1);
		Map<Integer, String[]> map2 = excelReader.readExcelContent(is2,null,0);
		
		for (String[] values:map2.values()) {
			JSONArray s = JSONArray.fromObject(values); //获取内容
			map3 = new HashMap<String, String>();
			for(int j=0;j<s.size();j++){
				String str = String.valueOf(s.get(j));
				map3.put(title2[j], str);
			}
			list3.add(map3);
			map3 = null;
		}
		for (String[] values:map.values()) {
			JSONArray s = JSONArray.fromObject(values); //获取内容
			map1 = new HashMap<String, String>();
			for(int j=0;j<s.size();j++){
				String str = String.valueOf(s.get(j));
				map1.put(title[j], str);
			}
			list1.add(map1);
			map1 = null;
		}
		lists.add(list1); // Excel表格内容(2016年6月反馈各区数据[4个区])
		lists.add(list3); // Excel表格内容(2016年6月反馈各区数据[7个区])
		return lists;
	}
	//读取Excel表格内容(海东房地产投资、施工、销售)
	public List<List<Map<String,String>>> getExcellData1(InputStream is2) throws FileNotFoundException{
		POIFSFileSystem fs;
		HSSFWorkbook wb;
		try {
			fs = new POIFSFileSystem(is2);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		Map<String,String> map1 = new HashMap<String,String>(); 
		List<Map<String,String>> list1 = new ArrayList<Map<String,String>>();
		List<List<Map<String,String>>> lists = new ArrayList<List<Map<String,String>>>();
		ExcelReader excelReader = new ExcelReader();
		Map<Integer, String[]> map = null;
		int tip = 0;
		for(int m=1;m<=1;m++){
		    try{
		    	list1 =  excelReader.readExcelContentHouse(is2,null,m,wb);
				lists.add(list1);
		    }catch(Exception e){
		       tip = 1;
		    }
	   }
		return lists;
	}
	
	//读取Excel表格内容
	public List<List<Map<String,String>>> getExcellData(InputStream is) throws FileNotFoundException{
		Map<String,String> map1 = new HashMap<String,String>(); 
		List<Map<String,String>> list1 = new ArrayList<Map<String,String>>();
		List<List<Map<String,String>>> lists = new ArrayList<List<Map<String,String>>>();
		ExcelReader excelReader = new ExcelReader();
		Map<Integer, String[]> map = null;
		int tip = 0;
		for(int m=1;m<=1;m++){
		    try{
		    	list1 =  excelReader.readExcel(is);
				lists.add(list1);
		    }catch(Exception e){
		       tip = 1;
		    }
	   }
		return lists;
	}
	
	/**
	 * 读取Excel数据内容(包括表头)
	 * 
	 * @param InputStream
	 * @return Map 包含单元格数据内容的Map对象
	 */
	public List<Map<String, String>> readExcel(InputStream is) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<Integer, String[]> content = new HashMap<Integer, String[]>();
		POIFSFileSystem fs;
		HSSFWorkbook wb;
		try {
			fs = new POIFSFileSystem(is);
			wb = new HSSFWorkbook(fs);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		row = sheet.getRow(0);
		int colNum = row.getPhysicalNumberOfCells();
		String[] arrVal = new String[colNum];
		String[] title = null;
		// 正文内容应该从第二行开始,第一行为表头的标题
		for (int i = 0; i <= rowNum; i++) {
			row = sheet.getRow(i);
			int j = 0;
			int nullInt = 0;
			while (j < colNum) {
				System.out.println(row.getCell((short)j)+"999");
				//arrVal[j]=getCellFormatValue(row.getCell((short)j)).trim();
				arrVal[j]=String.valueOf(row.getCell((short)j)).trim();
				if(StringUtils.isBlank(arrVal[j])){
					nullInt++;
				}
				j++;
			}
			if(nullInt<colNum){
				content.put(i, arrVal);
			}
//			if(content.size() == 5000){
//				content.clear();
//			}
			if(i==0){//表头
				title = arrVal;
			}
			LinkedHashMap<String,String> item = new LinkedHashMap<String,String>();
				
				for(int k=0;k<arrVal.length;k++){
				item.put(title[k], arrVal[k]);
			    }
			list.add(item);
			
	
			arrVal = new String[colNum];
		}
		if(content.size() > 0){
			content.clear();
		}
		return list;
	}
}
