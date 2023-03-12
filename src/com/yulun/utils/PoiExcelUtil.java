package com.yulun.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @description:
 * @author: 19bicheng@gmail.com
 * @time: 2020/6/8 14:22
 */
public class PoiExcelUtil {
    public static void dynamicHeadWebWrite(HttpServletResponse response, String fileName, List<List<String>> titles, List<List<String>> datas) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(fileName);
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        HSSFPalette customPalette = workbook.getCustomPalette();
        customPalette.setColorAtIndex(IndexedColors.BLUE.index, (byte) 0, (byte) 88, (byte) 129);
        titleStyle.setFillForegroundColor(IndexedColors.BLUE.index);
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体"); //字体
        //设置字体大小
        font.setFontHeightInPoints((short) 11);
        font.setColor(IndexedColors.WHITE.index);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setFont(font);
        boolean isSingleRow = true;
        for (List<String> title : titles) {
            if (title.size() > 1) {
                isSingleRow = false;
            }
        }
        short rowNum = 0;
        short totalColumn = 0;
        if (isSingleRow) {
            rowNum = 1;
            HSSFRow row = sheet.createRow(0);
            for (int i = 0; i < titles.size(); i++) {
                List<String> title = titles.get(i);
                HSSFCell cell = row.createCell((short) i);
                cell.setCellValue(title.get(0));
                cell.setCellStyle(titleStyle);
                totalColumn++;
            }
        } else {
            rowNum = 2;
            HSSFRow row0 = sheet.createRow(0);
            HSSFRow row1 = sheet.createRow(1);
            for (int i = 0; i < titles.size(); i++) {
                List<String> title = titles.get(i);
                if (title.size() == 1) {
                    HSSFCell cell = row0.createCell((short) totalColumn);
                    cell.setCellValue(title.get(0));
                    cell.setCellStyle(titleStyle);
                    HSSFCell cell2 = row1.createCell((short) totalColumn);
                    cell2.setCellValue("");
                    cell2.setCellStyle(titleStyle);
                    Region cra = new Region(0, (short) 1, totalColumn, totalColumn);
                    sheet.addMergedRegion(cra);
                    totalColumn++;
                } else {
                    HSSFCell cell0 = row0.createCell((short) totalColumn);
                    cell0.setCellStyle(titleStyle);
                    cell0.setCellValue(title.get(0));
                    for (int j = 1; j < title.size(); j++) {
                        HSSFCell cell = row1.createCell((short) totalColumn);
                        cell.setCellStyle(titleStyle);
                        cell.setCellValue(title.get(j));
                        totalColumn++;
                    }
                    if (title.size() > 2) {
                        Region cra = new Region(0, (short) 0, totalColumn - title.size() + 1, (short) (totalColumn - 1));
                        sheet.addMergedRegion(cra);
                    }
                }

            }

        }
        HSSFCellStyle style = workbook.createCellStyle();
        //设置底边框;
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        //设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.BLACK.index);
        //设置左边框;
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        //设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.BLACK.index);
        //设置右边框;
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //设置右边框颜色;
        style.setRightBorderColor(IndexedColors.BLACK.index);
        //设置顶边框;
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        //设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.BLACK.index);
        HSSFFont columnfont = workbook.createFont();
        columnfont.setFontName("宋体"); //字体
        //设置字体大小
        columnfont.setFontHeightInPoints((short) 11);
        columnfont.setColor(IndexedColors.BLACK.index);
        style.setFont(columnfont);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
        style.setWrapText(true);//自动换行
        for (List<String> dataList : datas) {
            HSSFRow row = sheet.createRow(rowNum);
            int colNum = 0;
            for (String data : dataList) {
                createColumn(row, colNum, data, style);
                colNum++;
            }
            rowNum++;
        }
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xls");
        setSizeColumn(sheet, totalColumn);
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }

    // 自适应宽度(中文支持)
    public static void setSizeColumn(HSSFSheet sheet, int size) {
        for (short columnNum = 0; columnNum < size; columnNum++) {
            short columnWidth = (short)(sheet.getColumnWidth(columnNum) / 256);
            System.out.println(sheet.getLastRowNum());
            for (int rowNum = 0; rowNum <= sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell((short) columnNum) != null) {
                    HSSFCell currentCell = currentRow.getCell((short) columnNum);
                    if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = (short)length;
                        }
                    }
                }
            }
            if (columnWidth > 250) {
                columnWidth = 250;
            }
            sheet.setColumnWidth(columnNum, (short) ((columnWidth + 4) * 256));
        }

    }

    public static void createColumn(HSSFRow row, int index, String value, HSSFCellStyle hssfCellStyle) {
        HSSFCell cell = row.createCell((short) index);
        cell.setCellStyle(hssfCellStyle);
        cell.setCellValue(value);

    }
}
