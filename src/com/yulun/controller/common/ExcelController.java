package com.yulun.controller.common;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.util.*;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.excel.ImportExcelService;
import com.yulun.service.*;
import com.yulun.service.impl.TrainrecoService;
import com.yulun.utils.CardIdJudge;
import com.yulun.utils.ListUtil;
import com.yulun.utils.TimeHandle;


import jxl.Workbook;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(value = "/api")
public class ExcelController extends BaseController {

    @Resource(name = "commnoService")
    private CommnoManager commnoManager;

    @Resource(name = "vipInfoService")
    private VipInfoManager vipInfoManager;

    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;

    @Resource(name = "consumerService")
    private ConsumerManager consumerManager;

    @Resource(name = "jobReferService")
    private JobReferManager jobReferManager;

    @Resource(name = "msgTempService")
    private MsgTempManager msgTempManager;

    @Resource(name = "trainInfoService")
    private TrainInfoManager trainInfoService;

    @Resource(name = "otherServiceService")
    private OtherServiceManager otherServiceService;

    @Resource(name = "otherPersonService")
    private OtherPersonManager otherPersonService;

    @Resource(name = "policyService")
    private PolicyManager policyService;

    @Resource(name = "signupService")
    private SignupManager signupService;

    @Resource(name = "guideService")
    private GuideManager guideService;

    @Resource(name = "emPersonService")
    private EmPersonManager emPersonService;
    @Resource(name = "trainrecoService")
    private TrainrecoService trainrecoService;

    @Resource(name = "revrecordService")
    private RevrecordManager revrecordManager;

    @Resource(name = "personCountService")
    private PersonCountManager personCountService;

    @Resource(name = "companyService")
    private CompanyManager companyService;

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    public String getUUID32() {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replace("-", "");
        return uuid;
    }


    @RequestMapping(value = "/exportgraduateExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportgraduateExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
//        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        if (pd_token != null) {
            PageData pd = new PageData();
            Page page = new Page();
            URLDecoder urlDecoder = new URLDecoder();
            String jobtype = request.getParameter("jobtype");
            String arrtime = request.getParameter("arrtime");
            String result = request.getParameter("result");
            String keywords = request.getParameter("keywords");
            String starttime = request.getParameter("startTime");
            String endtime = request.getParameter("endTime");
            System.out.println(arrtime);
            jobtype = jobtype == null ? "" : urlDecoder.decode(jobtype, "utf-8");
            arrtime = arrtime == null ? "" : urlDecoder.decode(arrtime, "utf-8");
            result = result == null ? "" : urlDecoder.decode(result, "utf-8");
            keywords = keywords == null ? "" : urlDecoder.decode(keywords, "utf-8");
            starttime = starttime == null ? "" : urlDecoder.decode(starttime, "utf-8");
            endtime = endtime == null ? "" : urlDecoder.decode(endtime, "utf-8");
            pd.put("jobtype", jobtype);
            pd.put("result", result);
            pd.put("keywords", keywords);
            pd.put("starttime", starttime);
            pd.put("endtime", endtime);
            if (null != arrtime && !"".equals(arrtime)) {
                pd.put("arrtime", arrtime + "-01 00:00:00");
                System.out.println(arrtime);
            }

            page.setPd(pd);
            page.setShowCount(999999999);
            page.setCurrentPage(1);
            System.out.println(pd);
            List<PageData> clist = consumerManager.findgraduateExport(page);
            System.out.println(clist);

            //创建excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建sheet页
            HSSFSheet sheet = wb.createSheet("高校毕业生");
            //创建一个单元格

            //创建第一行标题行
            HSSFRow titleRow_1 = sheet.createRow((short) 0);
            Region region = new Region((short) 0, (short) 0, (short) 1, (short) 16);
            sheet.addMergedRegion(region);
            //设置首行Style
            HSSFCellStyle titleStyle = wb.createCellStyle();
            titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
            titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
            titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
            titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            HSSFFont titleFont = wb.createFont();
            titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
            titleFont.setFontHeightInPoints((short) 20);
            titleStyle.setFont(titleFont);


            HSSFCell cell_1 = titleRow_1.createCell((short) 0);
            cell_1.setCellValue("高校毕业生就业创业服务台帐");
            cell_1.setCellStyle(titleStyle);
            //第二行
            HSSFRow titleRow_2 = sheet.createRow((short) 2);
            region = new Region((short) 2, (short) 0, (short) 3, (short) 0);
            sheet.addMergedRegion(region);
            HSSFCell cell0 = titleRow_2.createCell((short) 0);
            cell0.setCellValue("序号");
            region = new Region((short) 2, (short) 1, (short) 3, (short) 1);
            sheet.addMergedRegion(region);
            HSSFCell cell1 = titleRow_2.createCell((short) 1);
            cell1.setCellValue("回访时间");
            region = new Region((short) 2, (short) 2, (short) 3, (short) 2);
            sheet.addMergedRegion(region);
            HSSFCell cell2 = titleRow_2.createCell((short) 2);
            cell2.setCellValue("服务次数");
            region = new Region((short) 2, (short) 3, (short) 3, (short) 3);
            sheet.addMergedRegion(region);
            HSSFCell cell3 = titleRow_2.createCell((short) 3);
            cell3.setCellValue("姓名");
            region = new Region((short) 2, (short) 4, (short) 3, (short) 4);
            sheet.addMergedRegion(region);
            HSSFCell cell4 = titleRow_2.createCell((short) 4);
            cell4.setCellValue("性别");
            region = new Region((short) 2, (short) 5, (short) 3, (short) 5);
            sheet.addMergedRegion(region);
            HSSFCell cell5 = titleRow_2.createCell((short) 5);
            cell5.setCellValue("文化程度");
            region = new Region((short) 2, (short) 6, (short) 3, (short) 6);
            sheet.addMergedRegion(region);
            HSSFCell cell6 = titleRow_2.createCell((short) 6);
            cell6.setCellValue("联系电话");
            region = new Region((short) 2, (short) 7, (short) 3, (short) 7);
            sheet.addMergedRegion(region);
            HSSFCell cell7 = titleRow_2.createCell((short) 7);
            cell7.setCellValue("毕业院校及专业");
            region = new Region((short) 2, (short) 8, (short) 3, (short) 8);
            sheet.addMergedRegion(region);
            HSSFCell cell8 = titleRow_2.createCell((short) 8);
            cell8.setCellValue("就业创业状态");
            region = new Region((short) 2, (short) 9, (short) 3, (short) 9);
            sheet.addMergedRegion(region);
            HSSFCell cell9 = titleRow_2.createCell((short) 9);
            cell9.setCellValue("求职意向");
            region = new Region((short) 2, (short) 10, (short) 2, (short) 12);
            sheet.addMergedRegion(region);
            HSSFCell cell10 = titleRow_2.createCell((short) 10);
            cell10.setCellValue("提供就业服务情况");
            region = new Region((short) 2, (short) 13, (short) 2, (short) 14);
            sheet.addMergedRegion(region);
            HSSFCell cell13 = titleRow_2.createCell((short) 13);
            cell13.setCellValue("就业情况");
            //第三行
            HSSFRow titleRow_3 = sheet.createRow(3);
            region = new Region((short) 3, (short) 10, (short) 3, (short) 10);
            sheet.addMergedRegion(region);
            HSSFCell cell10_1 = titleRow_3.createCell((short) 10);
            cell10_1.setCellValue("职业介绍");
            region = new Region((short) 3, (short) 11, (short) 3, (short) 11);
            sheet.addMergedRegion(region);
            HSSFCell cell10_2 = titleRow_3.createCell((short) 11);
            cell10_2.setCellValue("技能培训");
            region = new Region((short) 3, (short) 12, (short) 3, (short) 12);
            sheet.addMergedRegion(region);
            HSSFCell cell10_3 = titleRow_3.createCell((short) 12);
            cell10_3.setCellValue("创业扶持政策");

            region = new Region((short) 3, (short) 13, (short) 3, (short) 13);
            sheet.addMergedRegion(region);
            HSSFCell cell13_1 = titleRow_3.createCell((short) 13);
            cell13_1.setCellValue("就业（创业）单位");

            region = new Region((short) 3, (short) 14, (short) 3, (short) 14);
            sheet.addMergedRegion(region);
            HSSFCell cell13_3 = titleRow_3.createCell((short) 14);
            cell13_3.setCellValue("就业时间");

            region = new Region((short) 2, (short) 15, (short) 3, (short) 15);
            sheet.addMergedRegion(region);
            HSSFCell cell15 = titleRow_2.createCell((short) 15);
            cell15.setCellValue("备注");

            region = new Region((short) 2, (short) 15, (short) 3, (short) 16);
            sheet.addMergedRegion(region);
            HSSFCell cell16 = titleRow_2.createCell((short) 16);
            cell16.setCellValue("推荐企业数量");

            int count = 1;
            if (clist.size() > 0) {
                for (PageData pd1 : clist) {
                    HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    dataRow.createCell((short) 0).setCellValue(count);
                    count++;
                    dataRow.createCell((short) 1).setCellValue(pd1.getString("newServiceTime"));
                    dataRow.createCell((short) 2).setCellValue(String.valueOf(pd1.get("serviceCount")));
                    dataRow.createCell((short) 3).setCellValue(pd1.getString("name"));
                    String sex = pd1.getString("sex");
                    if ("1".equals(sex)) {
                        dataRow.createCell((short) 4).setCellValue("男");
                    } else if ("2".equals(sex)) {
                        dataRow.createCell((short) 4).setCellValue("女");
                    }
                    dataRow.createCell((short) 5).setCellValue(pd1.getString("xlName"));
                    dataRow.createCell((short) 6).setCellValue(pd1.getString("tel"));
                    dataRow.createCell((short) 7).setCellValue(pd1.getString("schoolAndMajor"));
                    String isjob = pd1.getString("isjob");
                    if ("0".equals(isjob)) {
                        dataRow.createCell((short) 8).setCellValue("失业");
                    } else if ("1".equals(isjob)) {
                        dataRow.createCell((short) 8).setCellValue("就业");
                    } else if ("2".equals(isjob)) {
                        dataRow.createCell((short) 8).setCellValue("无意向就业");

                    } else if ("3".equals(isjob)) {
                        dataRow.createCell((short) 8).setCellValue("灵活就业");

                    } else if ("4".equals(isjob)) {
                        dataRow.createCell((short) 8).setCellValue("自主创业");
                    }

                    dataRow.createCell((short) 9).setCellValue(pd1.getString("qzgw"));
                    dataRow.createCell((short) 10).setCellValue(pd1.getString("companyName"));
                    dataRow.createCell((short) 11).setCellValue(pd1.getString("trainReco"));
                    dataRow.createCell((short) 12).setCellValue(pd1.getString("jobHelp"));
                    dataRow.createCell((short) 13).setCellValue(pd1.getString("jobUnit"));
                    dataRow.createCell((short) 14).setCellValue(pd1.getString("jobTime"));
                    dataRow.createCell((short) 15).setCellValue(pd1.getString("remark"));
                    dataRow.createCell((short) 16).setCellValue(pd1.get("companyNum").toString());
                }

            }
            sheet.autoSizeColumn((short) 1);
            sheet.autoSizeColumn((short) 7);


            // 设置下载时客户端Excel的名称
            String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-gxbys.xls";
            //设置下载的文件
            System.out.println(filename);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            OutputStream ouputStream = response.getOutputStream();//打开流
            wb.write(ouputStream); //在excel内写入流
            ouputStream.flush();// 刷新流
            ouputStream.close();// 关闭流

        } else {
            json.put("success", "false");
            json.put("msg", "登录超时，请重新登录");
        }

//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();
    }

    @RequestMapping(value = "/readgraduateExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String readgraduateExcel(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageData pd = new PageData();
        PageData pd_area = new PageData();
        JSONObject json = new JSONObject();
//        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        System.out.println(request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        com.alibaba.fastjson.JSONObject object_verificate = new com.alibaba.fastjson.JSONObject();
        List<PageData> saveList = new ArrayList<PageData>();
        List<PageData> dicAll = consumerManager.getDicAll(pd);

        if (pd_token != null) {

            String ZXID = pd_token.getString("ZXID");
            if (null != file && !file.isEmpty()) {
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String pcbh = sdf.format(d);  //导入批次

                String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
                String fileName = FileUpload.fileUp(file, filePath, "graduateListexcel");                            //执行上传
                ImportExcelService importExcelService = new ImportExcelService();
                String[] importColumns = {"报到时间", "姓名", "性别", "身份证号码", "毕业院校", "毕业日期", "学历", "所学专业", "入学前户口所在地", "联系电话", "应急联系电话", "就业地意向", "就业状态", "就业单位", "备注"};//设置字段名称
                String[] importFields = {"arrtime", "name", "sex", "cardid", "school", "gradate", "xl", "zy", "jg", "lxtel", "extel", "jobadd", "isjob", "jobunit", "remark"};  //字段
                // 1:IdCardVerification 2:DateVerification 3:TelVerification 4:DictVerification 5:ValueVerification 6:SqlVerification

//                String[] importYzColumns = {"2", "", "", "", "", "", "6", "", "", "3", "3", "", "", "", ""};
//                int[] importFiledNums = {30, 30, 30, 100, 0, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100}; //0表示无限制
//                int[] importFiledNull = {1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0}; //验证字段是否为空：0表示无限制，1表示不能为空
//                String[] importTjColumns = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};  //isNumberic 是否数字;isEnglish 是否英文 ；isChinese 是否中文
//                String[] importValueColumns = {"", "", "", "", "", "", "select * from sys_dictionaries where  name = #{name}", "", "", "", "", "", "", ""};

                String[] importYzColumns = {"2", "", "", "", "", "", "6", "", "", "3", "", "", "", "", ""};
                int[] importFiledNums = {30, 30, 30, 100, 0, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100}; //0表示无限制
                int[] importFiledNull = {1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0}; //验证字段是否为空：0表示无限制，1表示不能为空
                String[] importTjColumns = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};  //isNumberic 是否数字;isEnglish 是否英文 ；isChinese 是否中文
                String[] importValueColumns = {"", "", "", "", "", "", "select * from sys_dictionaries where  name = #{name}", "", "", "", "", "", "", ""};


                object_verificate.put("importColumns", importColumns);
                object_verificate.put("importFields", importFields);
                object_verificate.put("importYzColumns", importYzColumns);
                object_verificate.put("importFiledNums", importFiledNums);
                object_verificate.put("importValueColumns", importValueColumns);
                object_verificate.put("importTjColumns", importTjColumns);
                object_verificate.put("importFiledNull", importFiledNull);

                String openFilename = filePath + fileName;
                com.alibaba.fastjson.JSONObject jsonObject = importExcelService.importExcel(openFilename, request, object_verificate);
                List<PageData> findperson = jobReferManager.findperson(pd);
                List<PageData> listPd = ((List<PageData>) jsonObject.get("rightList"));
                System.out.println(listPd);
                for (int i = 0; i < listPd.size(); i++) {
                    String uid = "";
                    pd = new PageData();
                    pd_area = new PageData();
                    for (PageData pageData : findperson) {
                        if ((listPd.get(i).getString("name").equals(pageData.getString("name")) && listPd.get(i).getString("lxtel").equals(pageData.getString("lxtel")))) {
                            uid = pageData.getString("uid");
                            pd = pageData;
                        }
                    }

                    pd.put("arrtime", listPd.get(i).getString("arrtime").trim());
                    pd.put("name", listPd.get(i).getString("name").trim());
                    String var2 = listPd.get(i).getString("sex").trim();
                    if ("男".equals(var2)) {
                        var2 = "1";
                    } else if ("女".equals(var2)) {
                        var2 = "2";
                    } else {
                        var2 = "0";
                    }
                    pd.put("sex", var2);
                    pd.put("cardid", listPd.get(i).getString("cardid").trim());
                    pd.put("school", listPd.get(i).getString("school").trim());
                    String var5 = listPd.get(i).getString("gradate").trim();
                    System.out.println(var5);
                    pd.put("gradate", var5);
                    String var6 = listPd.get(i).getString("name").trim();
                    PageData pd1 = new PageData();
                    pd1.put("name", var6);
                    for (PageData pageData : dicAll) {
                        if (var6.equals(pageData.getString("NAME"))) {
                            pd.put("xl", pageData.getString("DICTIONARIES_ID"));
                        }
                    }

                    pd.put("zy", listPd.get(i).getString("zy").trim());
                    pd.put("addr", listPd.get(i).getString("jg").trim());
                    pd.put("jg", listPd.get(i).getString("jg").trim());
                    pd.put("tel", listPd.get(i).getString("lxtel").trim());
                    pd.put("lxtel", listPd.get(i).getString("lxtel").trim());
                    pd.put("extel", listPd.get(i).getString("extel").trim());
                    pd.put("jobadd", listPd.get(i).getString("jobadd").trim());
                    String var12 = listPd.get(i).getString("isjob").trim();
                    if ("就业".equals(var12)) {
                        pd.put("isjob", "1");
                    } else if ("失业".equals(var12)) {
                        pd.put("isjob", "0");
                    } else if ("无意向就业".equals(var12)) {
                        pd.put("isjob", "2");
                    } else if ("灵活就业".equals(var12)) {
                        pd.put("isjob", "3");
                    } else if ("自主创业".equals(var12)) {
                        pd.put("isjob", "4");
                    }

                    pd.put("jobunit", listPd.get(i).getString("jobunit").trim());
                    pd.put("remark", listPd.get(i).getString("remark").trim());
                    pd.put("isimpot", '1');
                    pd.put("cate", "7c2896ad53df4440b81c251231b196fd");
                    pd.put("czdate", getTime());
                    pd.put("czman", ZXID);
                    pd.put("uid", getUUID32());
                    String cardid = listPd.get(i).getString("cardid").trim();
                    if (StringUtils.isNotEmpty(cardid)) {
                        JSONObject cardidjson = JSON.parseObject(JSON.toJSON(identityCard(cardid)).toString());
                        pd.put("age", cardidjson.getString("age"));
                        pd.put("sex", cardidjson.getString("sex"));
                    }

                    PageData savePd = new PageData();
                    savePd.putAll(pd);
                    saveList.add(savePd);
//                            consumerManager.insertgraduate(pd);
                    if ("".equals(uid) || uid.equals(null)) {
                        pd.put("uid", getUUID32());
                        System.out.println("新增");
                        consumerManager.savegraduate(pd);
                        consumerManager.savePerson(pd);
//                        consumerManager.batchSaveGrad(saveList);
                    } else {
                        pd.put("uid", uid);
                        consumerManager.editPerson(pd);
                        consumerManager.updategraduate(pd);
//                        consumerManager.gradeEditBatch(pd);
                    }

                }

                List<PageData> errorList = ((List<PageData>) jsonObject.get("errorList")); //导入错误的信息
                if (errorList.size() > 0) {
//                    System.out.println(errorList);
                    for (int i = 0; i < errorList.size(); i++) {
                        pd = errorList.get(i);
                        pd.put("pcbh", pcbh);
                        pd.put("czman", pd_token.getString("ID"));
                        //System.out.println(errorList.get(i).getString("ycstr")+"ddd");
                        pd.put("uid", getUUID32());
                        pd.put("cate", "7c2896ad53df4440b81c251231b196fd");
                        pd.put("czdate", getTime());
                        consumerManager.savePerson_error(pd);
                        consumerManager.savegraduatedetai_error(pd);
                    }
                }
                json.put("success", "true");
                if (errorList.size() > 0) {
                    json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条,失败记录：" + errorList.size() + "条");
                    json.put("href", "api/excelgraduate_error?pcbh=" + pcbh + "&tokenid=" + request.getParameter("tokenid"));
                } else {
                    json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条");
                }

            }

        } else {
            json.put("success", "false");
            json.put("msg", "登录超时，请重新登录");
        }
//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();
    }

    @RequestMapping(value = "/downLoadgraduateExcelMode", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String downLoadgraduateExcelMode(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {

                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("高校毕业生");
                //创建标题行

                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("报到时间");
                titleRow.createCell((short) 1).setCellValue("姓名");
                titleRow.createCell((short) 2).setCellValue("性别");
                titleRow.createCell((short) 3).setCellValue("身份证号码");
                titleRow.createCell((short) 4).setCellValue("毕业院校");
                titleRow.createCell((short) 5).setCellValue("毕业日期");
                titleRow.createCell((short) 6).setCellValue("学历");
                titleRow.createCell((short) 7).setCellValue("所学专业");
                titleRow.createCell((short) 8).setCellValue("入学前户口所在地");
                titleRow.createCell((short) 9).setCellValue("联系电话");
                titleRow.createCell((short) 10).setCellValue("应急联系电话");
                titleRow.createCell((short) 11).setCellValue("就业地意向");
                titleRow.createCell((short) 12).setCellValue("就业状态");
                titleRow.createCell((short) 13).setCellValue("就业单位");
                titleRow.createCell((short) 14).setCellValue("备注");


                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-graduate.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    /*
     * 异常信息下载
     */
    @RequestMapping(value = "/excelgraduate_error", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String excelgraduate_error(HttpServletResponse response) throws Exception {
//        response.addHeader("Access-Control-Allow-Origin", "*");
        Page page = new Page();
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        net.sf.json.JSONObject object = new net.sf.json.JSONObject();
        pd = this.getPageData();

        pd.put("TOKENID", pd.getString("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd);
        if (pd_token == null) {
            object.put("success", "false");
            object.put("msg", "登录超时请重新登录");
            return object.toString();
        }

        pd.put("pcbh", pd.getString("pcbh"));
        pd.put("tables", "tesp_person_error");
        pd.put("tables2", "tesp_graduatedetai_error");
        page.setShowCount(9999999);
        page.setPd(pd);


        List<PageData> varOList = consumerManager.findgraduate(pd);

        try {
            Date date = new Date();
            String formatFileName = Tools.date2Str(date, "yyyyMMddHHmmss");
            OutputStream os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + formatFileName + ".xls");
            response.setContentType("application/msexcel");

            WritableWorkbook wbook = Workbook.createWorkbook(os);
            WritableSheet wsheet = wbook.createSheet(formatFileName, 0);

            WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat wcfFC = new WritableCellFormat(wfont);
            wcfFC.setBackground(Colour.YELLOW);
            //                String[] importColumns={"报到时间","姓名","性别","身份证号码","毕业院校","毕业日期","学历","所学专业","入学前户口所在地","联系电话","应急联系电话","就业地意向","就业状态","就业单位","备注"};//设置字段名称
            wsheet.addCell(new Label(0, 0, "报到时间"));
            wsheet.addCell(new Label(1, 0, "姓名"));
            wsheet.addCell(new Label(2, 0, "性别"));
            wsheet.addCell(new Label(3, 0, "身份证号码"));
            wsheet.addCell(new Label(4, 0, "毕业院校"));
            wsheet.addCell(new Label(5, 0, "毕业日期"));
            wsheet.addCell(new Label(6, 0, "学历"));
            wsheet.addCell(new Label(7, 0, "所学专业"));
            wsheet.addCell(new Label(8, 0, "入学前户口所在地"));
            wsheet.addCell(new Label(9, 0, "联系电话"));
            wsheet.addCell(new Label(10, 0, "应急联系电话"));
            wsheet.addCell(new Label(11, 0, "就业地意向"));
            wsheet.addCell(new Label(12, 0, "就业状态"));
            wsheet.addCell(new Label(13, 0, "就业单位"));
            wsheet.addCell(new Label(14, 0, "备注"));
            wsheet.addCell(new Label(15, 0, "异常日志"));
            int num = 1;
            String ycstr = "";
            for (int i = 0; i < varOList.size(); i++) {

                ycstr = String.valueOf(varOList.get(i).getString("ycstr"));
                if (ycstr.indexOf("arrtime") >= 0) {
                    wsheet.addCell(new Label(0, num, varOList.get(i).getString("arrtime"), wcfFC));
                } else {
                    wsheet.addCell(new Label(0, num, varOList.get(i).getString("arrtime")));
                }
                if (ycstr.indexOf("name") >= 0) {
                    wsheet.addCell(new Label(1, num, varOList.get(i).getString("name"), wcfFC));
                } else {
                    wsheet.addCell(new Label(1, num, varOList.get(i).getString("name")));
                }
                if (ycstr.indexOf("sex") >= 0) {
                    wsheet.addCell(new Label(2, num, varOList.get(i).getString("sex"), wcfFC));
                } else {
                    wsheet.addCell(new Label(2, num, varOList.get(i).getString("sex")));
                }

                if (ycstr.indexOf("cardid") >= 0) {
                    wsheet.addCell(new Label(3, num, varOList.get(i).getString("cardid"), wcfFC));
                } else {
                    wsheet.addCell(new Label(3, num, varOList.get(i).getString("cardid")));
                }
                if (ycstr.indexOf("school") >= 0) {
                    wsheet.addCell(new Label(4, num, varOList.get(i).getString("school"), wcfFC));
                } else {
                    wsheet.addCell(new Label(4, num, varOList.get(i).getString("school")));
                }
                if (ycstr.indexOf("gradate") >= 0) {
                    wsheet.addCell(new Label(5, num, varOList.get(i).getString("gradate"), wcfFC));
                } else {
                    wsheet.addCell(new Label(5, num, varOList.get(i).getString("gradate")));
                }
                if (ycstr.indexOf("xl") >= 0) {
                    wsheet.addCell(new Label(6, num, varOList.get(i).getString("xl"), wcfFC));
                } else {
                    wsheet.addCell(new Label(6, num, varOList.get(i).getString("xl")));
                }
                if (ycstr.indexOf("major") >= 0) {
                    wsheet.addCell(new Label(7, num, varOList.get(i).getString("major"), wcfFC));
                } else {
                    wsheet.addCell(new Label(7, num, varOList.get(i).getString("major")));
                }
                if (ycstr.indexOf("address") >= 0) {
                    wsheet.addCell(new Label(8, num, varOList.get(i).getString("address"), wcfFC));
                } else {
                    wsheet.addCell(new Label(8, num, varOList.get(i).getString("address")));
                }
                if (ycstr.indexOf("tel") >= 0) {
                    wsheet.addCell(new Label(9, num, varOList.get(i).getString("tel"), wcfFC));
                } else {
                    wsheet.addCell(new Label(9, num, varOList.get(i).getString("tel")));
                }
                if (ycstr.indexOf("extel") >= 0) {
                    wsheet.addCell(new Label(10, num, varOList.get(i).getString("extel"), wcfFC));
                } else {
                    wsheet.addCell(new Label(10, num, varOList.get(i).getString("extel")));
                }
                if (ycstr.indexOf("jobadd") >= 0) {
                    wsheet.addCell(new Label(11, num, varOList.get(i).getString("jobadd"), wcfFC));
                } else {
                    wsheet.addCell(new Label(11, num, varOList.get(i).getString("jobadd")));
                }
                if (ycstr.indexOf("jobtype") >= 0) {
                    wsheet.addCell(new Label(12, num, varOList.get(i).getString("jobtype"), wcfFC));
                } else {
                    wsheet.addCell(new Label(12, num, varOList.get(i).getString("jobtype")));
                }
                if (ycstr.indexOf("jobunit") >= 0) {
                    wsheet.addCell(new Label(13, num, varOList.get(i).getString("jobunit"), wcfFC));
                } else {
                    wsheet.addCell(new Label(13, num, varOList.get(i).getString("jobunit")));
                }
                if (ycstr.indexOf("remark") >= 0) {
                    wsheet.addCell(new Label(14, num, varOList.get(i).getString("remark"), wcfFC));
                } else {
                    wsheet.addCell(new Label(14, num, varOList.get(i).getString("remark")));
                }

                wsheet.addCell(new Label(15, num, varOList.get(i).getString("ycstrs")));
                num++;
            }
            //                String[] importFields = {"arrtime", "name", "sex", "cardid", "school", "gradate", "xl", "zy", "jg", "tel", "extel", "jobadd", "isjob", "jobunit", "remark"};  //字段
            wbook.write();
            if (wbook != null) {
                wbook.close();
                wbook = null;
            }
            if (os != null) {
                os.close();
                os = null;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        return null;
    }


    @RequestMapping(value = "/exportpovertyExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportpovertyExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
//        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        if (pd_token != null) {
            PageData pd = new PageData();
            Page page = new Page();
            URLDecoder urlDecoder = new URLDecoder();
            String jg = request.getParameter("jg");
            String tel = request.getParameter("tel");
            String isJob = request.getParameter("isJob");
            String cardId = request.getParameter("cardId");
            String result = request.getParameter("result");
            String keywords = request.getParameter("keywords");
            jg = jg == null ? "" : urlDecoder.decode(jg, "utf-8");
            tel = tel == null ? "" : urlDecoder.decode(tel, "utf-8");
            isJob = isJob == null ? "" : urlDecoder.decode(isJob, "utf-8");
            cardId = cardId == null ? "" : urlDecoder.decode(cardId, "utf-8");
            result = result == null ? "" : urlDecoder.decode(result, "utf-8");
            keywords = keywords == null ? "" : urlDecoder.decode(keywords, "utf-8");
            pd.put("jg", jg);
            pd.put("tel", tel);
            pd.put("isJob", isJob);
            pd.put("cardId", cardId);
            pd.put("result", result);
            pd.put("keywords", keywords);
            page.setShowCount(999999999);
            page.setCurrentPage(1);
            page.setPd(pd);
            System.out.println(pd);
            List<PageData> clist = consumerManager.findExportList(page);
            System.out.println(clist);
            //创建excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建sheet页
            HSSFSheet sheet = wb.createSheet("建档立卡贫困人员");
            //创建标题行
            HSSFRow titleRow = sheet.createRow(0);
            titleRow.createCell((short) 0).setCellValue("序号");
            titleRow.createCell((short) 1).setCellValue("最新服务时间");
            titleRow.createCell((short) 2).setCellValue("服务次数");
            titleRow.createCell((short) 3).setCellValue("籍贯");
            titleRow.createCell((short) 4).setCellValue("姓名");
            titleRow.createCell((short) 5).setCellValue("联系电话");
            titleRow.createCell((short) 6).setCellValue("证件号码");
            titleRow.createCell((short) 7).setCellValue("年龄");
            titleRow.createCell((short) 8).setCellValue("性别");
            titleRow.createCell((short) 9).setCellValue("就业状态");
            titleRow.createCell((short) 10).setCellValue("情况记录");
            titleRow.createCell((short) 11).setCellValue("求职岗位");
            titleRow.createCell((short) 12).setCellValue("推荐企业");
            titleRow.createCell((short) 13).setCellValue("就业岗位");
            titleRow.createCell((short) 14).setCellValue("就业单位");
            titleRow.createCell((short) 15).setCellValue("推荐政策");
            titleRow.createCell((short) 16).setCellValue("推荐技能");
            titleRow.createCell((short) 17).setCellValue("通话状态");
            titleRow.createCell((short) 18).setCellValue("未接通原因");
            titleRow.createCell((short) 19).setCellValue("推荐企业数量");
            if (clist.size() > 0) {
                int count = 1;
                for (PageData pd1 : clist) {
                    HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    dataRow.createCell((short) 0).setCellValue(count++);
                    dataRow.createCell((short) 1).setCellValue(pd1.getString("newServiceTime"));
                    dataRow.createCell((short) 2).setCellValue(pd1.get("serviceCount").toString());
                    dataRow.createCell((short) 3).setCellValue(pd1.getString("jgName"));
                    dataRow.createCell((short) 4).setCellValue(pd1.getString("name"));
                    dataRow.createCell((short) 5).setCellValue(pd1.getString("tel"));
                    dataRow.createCell((short) 6).setCellValue(pd1.getString("cardid"));
                    dataRow.createCell((short) 7).setCellValue(pd1.getString("age"));
                    String sex = pd1.getString("sex");
                    if ("1".equals(sex)) {
                        dataRow.createCell((short) 8).setCellValue("男");
                    } else if ("2".equals(sex)) {
                        dataRow.createCell((short) 8).setCellValue("女");
                    }
                    String isjob = pd1.getString("isjob");
                    if ("0".equals(isjob))
                        dataRow.createCell((short) 9).setCellValue("失业");
                    if ("1".equals(isjob))
                        dataRow.createCell((short) 9).setCellValue("就业");
                    if ("2".equals(isjob))
                        dataRow.createCell((short) 9).setCellValue("无意向就业");
                    if ("3".equals(isjob))
                        dataRow.createCell((short) 9).setCellValue("灵活就业");
                    if ("4".equals(isjob))
                        dataRow.createCell((short) 9).setCellValue("自主创业");

                    dataRow.createCell((short) 10).setCellValue(pd1.getString("cate"));
                    dataRow.createCell((short) 11).setCellValue(pd1.getString("qzgw"));
                    dataRow.createCell((short) 12).setCellValue(pd1.getString("companyName"));
                    dataRow.createCell((short) 13).setCellValue(pd1.getString("isjobwork"));
                    dataRow.createCell((short) 14).setCellValue(pd1.getString("jobunit"));
                    dataRow.createCell((short) 15).setCellValue(pd1.getString("zczxname"));
                    dataRow.createCell((short) 16).setCellValue(pd1.getString("trainreco"));
                    String isjt = pd1.getString("isjt");
                    if ("0".equals(isjt)) {
                        dataRow.createCell((short) 17).setCellValue("未接通");
                    } else if ("1".equals(isjt)) {
                        dataRow.createCell((short) 17).setCellValue("已接通");
                    } else {
                        dataRow.createCell((short) 17).setCellValue("未回访");
                    }
                    dataRow.createCell((short) 18).setCellValue(pd1.getString("unconnet"));
                    dataRow.createCell((short) 19).setCellValue(pd1.get("companyNum").toString());
                }

            }
            // 设置下载时客户端Excel的名称
            String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-poverty.xls";
            //设置下载的文件
            System.out.println(filename);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            OutputStream ouputStream = response.getOutputStream();//打开流
            wb.write(ouputStream); //在excel内写入流
            ouputStream.flush();// 刷新流
            ouputStream.close();// 关闭流

        } else {
            json.put("success", "false");
            json.put("msg", "登录超时，请重新登录");
        }

//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();
    }

    @RequestMapping(value = "/readpovertyExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String readpovertyExcel(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        PageData pd_area = new PageData();
        JSONObject json = new JSONObject();
//        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        System.out.println(request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        com.alibaba.fastjson.JSONObject object_verificate = new com.alibaba.fastjson.JSONObject();
        List<PageData> saveList = new ArrayList<PageData>();
        List<PageData> dicAll = consumerManager.getDicAll(pd);

        if (pd_token != null) {

            String ZXID = pd_token.getString("ZXID");
            if (null != file && !file.isEmpty()) {
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String pcbh = sdf.format(d);  //导入批次

                String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
                String fileName = FileUpload.fileUp(file, filePath, "povertyListexcel");                            //执行上传
                ImportExcelService importExcelService = new ImportExcelService();
                String[] importColumns = {"所属乡镇", "姓名", "性别", "年龄", "联系电话", "就业状态", "身份证号码"};//设置字段名称
                String[] importFields = {"jg", "name", "sex", "age", "lxtel", "isjob", "cardid"};  //字段
                // 1:IdCardVerification 2:DateVerification 3:TelVerification 4:DictVerification 5:ValueVerification 6:SqlVerification

                String[] importYzColumns = {"", "", "", "", "", "", "1"};
                int[] importFiledNums = {200, 30, 10, 10, 20, 5, 20}; //0表示无限制
                int[] importFiledNull = {0, 1, 1, 1, 1, 0, 0}; //验证字段是否为空：0表示无限制，1表示不能为空
                String[] importTjColumns = {"", "", "", "isNumberic", "", "", ""};  //isNumberic 是否数字;isEnglish 是否英文 ；isChinese 是否中文
                String[] importValueColumns = {"", "", "", "", "", "", ""};

                object_verificate.put("importColumns", importColumns);
                object_verificate.put("importFields", importFields);
                object_verificate.put("importYzColumns", importYzColumns);
                object_verificate.put("importFiledNums", importFiledNums);
                object_verificate.put("importValueColumns", importValueColumns);
                object_verificate.put("importTjColumns", importTjColumns);
                object_verificate.put("importFiledNull", importFiledNull);

                String openFilename = filePath + fileName;
                com.alibaba.fastjson.JSONObject jsonObject = importExcelService.importExcel(openFilename, request, object_verificate);
                List<PageData> findperson = jobReferManager.findperson(pd);
                List<PageData> listPd = ((List<PageData>) jsonObject.get("rightList"));
                System.out.println(listPd);
                System.out.println(findperson);
                for (int i = 0; i < listPd.size(); i++) {
                    String uid = "";
                    pd = new PageData();
                    pd_area = new PageData();

                    String lxtel = listPd.get(i).getString("lxtel") == null ? "" : listPd.get(i).getString("lxtel").replace("-", "");
                    for (PageData pageData : findperson) {
                        System.out.println(pageData.getString("name"));
                        System.out.println(pageData.getString("lxtel"));
                        System.out.println(pageData.getString("cardid"));
                        System.out.println(listPd.get(i).getString("name"));
                        System.out.println(listPd.get(i).getString("cardid"));
                        if ((listPd.get(i).getString("name").equals(pageData.getString("name")) && lxtel.equals(pageData.getString("lxtel"))) || listPd.get(i).getString("cardid").equals(pageData.getString("cardid"))) {
                            uid = pageData.getString("uid");
                            pd = pageData;
                        }
                    }
//                    String[] importFields = {"jg", "name", "sex", "age", "lxtel", "isjob", "idcard"};  //字段
                    pd.put("jg", listPd.get(i).getString("jg").trim());
                    pd.put("name", listPd.get(i).getString("name").trim());
                    String var2 = listPd.get(i).getString("sex").trim();
                    if ("男".equals(var2)) {
                        var2 = "1";
                    } else if ("女".equals(var2)) {
                        var2 = "2";
                    } else {
                        var2 = "0";
                    }
                    pd.put("sex", var2);
                    pd.put("age", listPd.get(i).getString("age").trim());
                    pd.put("tel", listPd.get(i).getString("lxtel").trim());
                    pd.put("lxtel", listPd.get(i).getString("lxtel").trim());
                    String var12 = listPd.get(i).getString("isjob").trim();
                    if ("就业".equals(var12)) {
                        pd.put("isjob", "1");
                    } else if ("失业".equals(var12)) {
                        pd.put("isjob", "0");
                    } else if ("无意向就业".equals(var12)) {
                        pd.put("isjob", "2");
                    } else if ("灵活就业".equals(var12)) {
                        pd.put("isjob", "3");
                    } else if ("自主创业".equals(var12)) {
                        pd.put("isjob", "4");
                    }
                    pd.put("cardid", listPd.get(i).getString("cardid").trim());
                    pd.put("isimpot", '1');
                    pd.put("cate", "a6d55a9451534e14aefd082634f78ad9");
                    pd.put("czdate", getTime());
                    pd.put("czman", ZXID);
                    pd.put("uid", getUUID32());
                    String cardid = listPd.get(i).getString("cardid").trim();
                    if (StringUtils.isNotEmpty(cardid)) {
                        JSONObject cardidjson = JSON.parseObject(JSON.toJSON(identityCard(cardid)).toString());
                        pd.put("age", cardidjson.getString("age"));
                        pd.put("sex", cardidjson.getString("sex"));
                    }

                    PageData savePd = new PageData();
                    savePd.putAll(pd);
                    saveList.add(savePd);
//                            consumerManager.insertgraduate(pd);
                    if ("".equals(uid) || uid.equals(null)) {
                        pd.put("uid", getUUID32());
                        System.out.println("新增");
                        consumerManager.savePerson(pd);
                    } else {
                        pd.put("uid", uid);
                        consumerManager.editPerson(pd);
                    }

                }

                List<PageData> errorList = ((List<PageData>) jsonObject.get("errorList")); //导入错误的信息
                if (errorList.size() > 0) {
//                    System.out.println(errorList);
                    for (int i = 0; i < errorList.size(); i++) {
                        pd = errorList.get(i);
                        pd.put("pcbh", pcbh);
                        pd.put("czman", pd_token.getString("ID"));
                        //System.out.println(errorList.get(i).getString("ycstr")+"ddd");
                        pd.put("uid", getUUID32());
                        pd.put("cate", "a6d55a9451534e14aefd082634f78ad9");
                        pd.put("czdate", getTime());
                        consumerManager.savePerson_error(pd);
                    }
                }
                json.put("success", "true");
                if (errorList.size() > 0) {
                    json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条,失败记录：" + errorList.size() + "条");
                    json.put("href", "api/excelpoverty_error?pcbh=" + pcbh + "&tokenid=" + request.getParameter("tokenid"));
                } else {
                    json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条");
                }

            }

        } else {
            json.put("success", "false");
            json.put("msg", "登录超时，请重新登录");
        }
//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();

    }

    @RequestMapping(value = "/downLoadpovertyExcelMode", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String downLoadpovertyExcelMode(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {

                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("建档立卡户");
                //创建标题行
//                String[] importColumns = {"所属乡镇", "姓名", "性别", "年龄", "联系电话", "就业状态", "身份证号码"};//设置字段名称
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("所属乡镇");
                titleRow.createCell((short) 1).setCellValue("姓名");
                titleRow.createCell((short) 2).setCellValue("性别");
                titleRow.createCell((short) 3).setCellValue("年龄");
                titleRow.createCell((short) 4).setCellValue("联系电话");
                titleRow.createCell((short) 5).setCellValue("就业状态");
                titleRow.createCell((short) 6).setCellValue("身份证号码");


                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-poverty.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    /*
     * 异常信息下载
     */
    @RequestMapping(value = "/excelpoverty_error", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String excelpoverty_error(HttpServletResponse response) throws Exception {
//        response.addHeader("Access-Control-Allow-Origin", "*");
        Page page = new Page();
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        net.sf.json.JSONObject object = new net.sf.json.JSONObject();
        pd = this.getPageData();

        pd.put("TOKENID", pd.getString("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd);
        if (pd_token == null) {
            object.put("success", "false");
            object.put("msg", "登录超时请重新登录");
            return object.toString();
        }

        pd.put("pcbh", pd.getString("pcbh"));
        pd.put("tables", "tesp_person_error");
        page.setShowCount(9999999);
        page.setPd(pd);


        List<PageData> varOList = consumerManager.findpoverty(pd);

        try {
            Date date = new Date();
            String formatFileName = Tools.date2Str(date, "yyyyMMddHHmmss");
            OutputStream os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + formatFileName + ".xls");
            response.setContentType("application/msexcel");

            WritableWorkbook wbook = Workbook.createWorkbook(os);
            WritableSheet wsheet = wbook.createSheet(formatFileName, 0);

            WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat wcfFC = new WritableCellFormat(wfont);
            wcfFC.setBackground(Colour.YELLOW);
            wsheet.addCell(new Label(0, 0, "所属乡镇"));
            wsheet.addCell(new Label(1, 0, "姓名"));
            wsheet.addCell(new Label(2, 0, "性别"));
            wsheet.addCell(new Label(3, 0, "年龄"));
            wsheet.addCell(new Label(4, 0, "联系电话"));
            wsheet.addCell(new Label(5, 0, "就业状态"));
            wsheet.addCell(new Label(6, 0, "身份证号码"));
            wsheet.addCell(new Label(7, 0, "异常日志"));
//            String[] importColumns = {"所属乡镇", "姓名", "性别", "年龄", "联系电话", "就业状态", "身份证号码"};//设置字段名称
//            String[] importFields = {"jg", "name", "sex", "age", "lxtel", "isjob", "idcard"};  //字段
            int num = 1;
            String ycstr = "";
            for (int i = 0; i < varOList.size(); i++) {

                ycstr = String.valueOf(varOList.get(i).getString("ycstr"));
                if (ycstr.indexOf("jgName") >= 0) {
                    wsheet.addCell(new Label(0, num, varOList.get(i).getString("jgName"), wcfFC));
                } else {
                    wsheet.addCell(new Label(0, num, varOList.get(i).getString("jgName")));
                }
                if (ycstr.indexOf("name") >= 0) {
                    wsheet.addCell(new Label(1, num, varOList.get(i).getString("name"), wcfFC));
                } else {
                    wsheet.addCell(new Label(1, num, varOList.get(i).getString("name")));
                }
                if (ycstr.indexOf("sex") >= 0) {
                    wsheet.addCell(new Label(2, num, varOList.get(i).getString("sex"), wcfFC));
                } else {
                    wsheet.addCell(new Label(2, num, varOList.get(i).getString("sex")));
                }

                if (ycstr.indexOf("age") >= 0) {
                    wsheet.addCell(new Label(3, num, varOList.get(i).getString("age"), wcfFC));
                } else {
                    wsheet.addCell(new Label(3, num, varOList.get(i).getString("age")));
                }
                if (ycstr.indexOf("lxtel") >= 0) {
                    wsheet.addCell(new Label(4, num, varOList.get(i).getString("lxtel"), wcfFC));
                } else {
                    wsheet.addCell(new Label(4, num, varOList.get(i).getString("lxtel")));
                }
                if (ycstr.indexOf("isjob") >= 0) {
                    wsheet.addCell(new Label(5, num, varOList.get(i).getString("isJob"), wcfFC));
                } else {
                    wsheet.addCell(new Label(5, num, varOList.get(i).getString("isJob")));
                }
                if (ycstr.indexOf("cardid") >= 0) {
                    wsheet.addCell(new Label(6, num, varOList.get(i).getString("cardid"), wcfFC));
                } else {
                    wsheet.addCell(new Label(6, num, varOList.get(i).getString("cardid")));
                }
                wsheet.addCell(new Label(7, num, varOList.get(i).getString("ycstrs")));
                num++;
            }
            wbook.write();
            if (wbook != null) {
                wbook.close();
                wbook = null;
            }
            if (os != null) {
                os.close();
                os = null;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        return null;
    }

    @RequestMapping(value = "/exportsbbtryExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportsbbtryExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
//        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                PageData pd = new PageData();
                URLDecoder urlDecoder = new URLDecoder();

                pd.put("starttime", request.getParameter("startTime") == null ? "" : urlDecoder.decode(request.getParameter("startTime"), "utf-8"));
                pd.put("result", request.getParameter("result") == null ? "" : urlDecoder.decode(request.getParameter("result"), "utf-8"));
                pd.put("endtime", request.getParameter("endTime") == null ? "" : urlDecoder.decode(request.getParameter("endTime"), "utf-8"));
                pd.put("keywords", request.getParameter("keywords") == null ? "" : urlDecoder.decode(request.getParameter("keywords"), "utf-8"));

                System.out.println(pd);
                List<PageData> clist = consumerManager.findsbbtry(pd);
                System.out.println(clist);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("社保补贴人员");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("最新服务时间");
                titleRow.createCell((short) 2).setCellValue("服务次数");
                titleRow.createCell((short) 3).setCellValue("籍贯");
                titleRow.createCell((short) 4).setCellValue("姓名");
                titleRow.createCell((short) 5).setCellValue("联系电话");
                titleRow.createCell((short) 6).setCellValue("证件号码");
                titleRow.createCell((short) 7).setCellValue("年龄");
                titleRow.createCell((short) 8).setCellValue("性别");
                titleRow.createCell((short) 9).setCellValue("就业状态");
                titleRow.createCell((short) 10).setCellValue("情况记录");
                titleRow.createCell((short) 11).setCellValue("求职岗位");
                titleRow.createCell((short) 12).setCellValue("推荐企业");
                titleRow.createCell((short) 13).setCellValue("就业岗位");
                titleRow.createCell((short) 14).setCellValue("就业单位");
                titleRow.createCell((short) 15).setCellValue("推荐政策");
                titleRow.createCell((short) 16).setCellValue("推荐技能");
                titleRow.createCell((short) 17).setCellValue("通话状态");
                titleRow.createCell((short) 18).setCellValue("未接通原因");
                titleRow.createCell((short) 19).setCellValue("推荐企业数量");
                if (clist.size() > 0) {

                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count++);
                        dataRow.createCell((short) 1).setCellValue(pd1.getString("newServiceTime"));
                        dataRow.createCell((short) 2).setCellValue(pd1.get("serviceCount").toString());
                        dataRow.createCell((short) 3).setCellValue(pd1.getString("jgName"));
                        dataRow.createCell((short) 4).setCellValue(pd1.getString("name"));
                        dataRow.createCell((short) 5).setCellValue(pd1.getString("lxtel"));
                        dataRow.createCell((short) 6).setCellValue(pd1.getString("cardid"));
                        dataRow.createCell((short) 7).setCellValue(pd1.getString("age"));
                        String sex = pd1.getString("sex");
                        if ("1".equals(sex)) {
                            dataRow.createCell((short) 8).setCellValue("男");
                        } else if ("2".equals(sex)) {
                            dataRow.createCell((short) 8).setCellValue("女");
                        }
                        String isjob = pd1.getString("isjob");
                        if ("0".equals(isjob))
                            dataRow.createCell((short) 9).setCellValue("失业");
                        if ("1".equals(isjob))
                            dataRow.createCell((short) 9).setCellValue("就业");
                        if ("2".equals(isjob))
                            dataRow.createCell((short) 9).setCellValue("无意向就业");
                        if ("3".equals(isjob))
                            dataRow.createCell((short) 9).setCellValue("灵活就业");
                        if ("4".equals(isjob))
                            dataRow.createCell((short) 9).setCellValue("自主创业");

                        dataRow.createCell((short) 10).setCellValue(pd1.getString("cate"));
                        dataRow.createCell((short) 11).setCellValue(pd1.getString("qzgw"));
                        dataRow.createCell((short) 12).setCellValue(pd1.getString("companyName"));
                        dataRow.createCell((short) 13).setCellValue(pd1.getString("isjobwork"));
                        dataRow.createCell((short) 14).setCellValue(pd1.getString("jobunit"));
                        dataRow.createCell((short) 15).setCellValue(pd1.getString("zczxname"));
                        dataRow.createCell((short) 16).setCellValue(pd1.getString("trainreco"));
                        String isjt = pd1.getString("isjt");
                        if ("0".equals(isjt)) {
                            dataRow.createCell((short) 17).setCellValue("未接通");
                        } else if ("1".equals(isjt)) {
                            dataRow.createCell((short) 17).setCellValue("已接通");
                        } else {
                            dataRow.createCell((short) 17).setCellValue("未回访");
                        }
                        dataRow.createCell((short) 18).setCellValue(pd1.getString("unconnet"));
                        dataRow.createCell((short) 19).setCellValue(pd1.get("companyNum").toString());

                    }

                }
                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-sbbtry.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }

//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();
    }

    @RequestMapping(value = "/readsbbtryExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String readsbbtryExcel(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        PageData pd_area = new PageData();
        JSONObject json = new JSONObject();
//        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        System.out.println(request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        com.alibaba.fastjson.JSONObject object_verificate = new com.alibaba.fastjson.JSONObject();
        List<PageData> saveList = new ArrayList<PageData>();
        List<PageData> dicAll = consumerManager.getDicAll(pd);

        if (pd_token != null) {

            String ZXID = pd_token.getString("ZXID");
            if (null != file && !file.isEmpty()) {
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String pcbh = sdf.format(d);  //导入批次

                String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
                String fileName = FileUpload.fileUp(file, filePath, "sbbtryListexcel");                            //执行上传
                ImportExcelService importExcelService = new ImportExcelService();
                String[] importColumns = {"姓名", "联系电话", "备注"};//设置字段名称
                String[] importFields = {"name", "lxtel", "remark"};  //字段
                // 1:IdCardVerification 2:DateVerification 3:TelVerification 4:DictVerification 5:ValueVerification 6:SqlVerification

                String[] importYzColumns = {"", "", ""};
                int[] importFiledNums = {100, 30, 0}; //0表示无限制
                int[] importFiledNull = {1, 1, 0}; //验证字段是否为空：0表示无限制，1表示不能为空
                String[] importTjColumns = {"", "", ""};  //isNumberic 是否数字;isEnglish 是否英文 ；isChinese 是否中文
                String[] importValueColumns = {"", "", ""};

                object_verificate.put("importColumns", importColumns);
                object_verificate.put("importFields", importFields);
                object_verificate.put("importYzColumns", importYzColumns);
                object_verificate.put("importFiledNums", importFiledNums);
                object_verificate.put("importValueColumns", importValueColumns);
                object_verificate.put("importTjColumns", importTjColumns);
                object_verificate.put("importFiledNull", importFiledNull);

                String openFilename = filePath + fileName;
                com.alibaba.fastjson.JSONObject jsonObject = importExcelService.importExcel(openFilename, request, object_verificate);
                List<PageData> findperson = jobReferManager.findperson(pd);
                List<PageData> listPd = ((List<PageData>) jsonObject.get("rightList"));
                System.out.println(listPd);
                for (int i = 0; i < listPd.size(); i++) {
                    String uid = "";
                    pd = new PageData();
                    pd_area = new PageData();
                    for (PageData pageData : findperson) {
                        if (listPd.get(i).getString("name").equals(pageData.getString("name")) && listPd.get(i).getString("lxtel").equals(pageData.getString("lxtel")) ) {
                            uid = pageData.getString("uid");
                            pd = pageData;
                        }
                    }

                    pd.put("name", listPd.get(i).getString("name").trim());
                    pd.put("lxtel", listPd.get(i).getString("lxtel").trim());
                    pd.put("tel", listPd.get(i).getString("lxtel").trim());
                    pd.put("remark", listPd.get(i).getString("remark").trim());

                    pd.put("cate", "54acc890c8bc433ca53dc19b06d71857");
                    pd.put("czman", ZXID);
                    pd.put("uid", getUUID32());
                    pd.put("isimpot", "1");

//                    String cardid = listPd.get(i).getString("cardid").trim();
//                    if (StringUtils.isNotEmpty(cardid)) {
//                        JSONObject cardidjson = JSON.parseObject(JSON.toJSON(identityCard(cardid)).toString());
//                        pd.put("age", cardidjson.getString("age"));
//                        pd.put("sex", cardidjson.getString("sex"));
//                    }

                    PageData savePd = new PageData();
                    savePd.putAll(pd);
                    saveList.add(savePd);
//                            consumerManager.insertgraduate(pd);
                    if ("".equals(uid) || uid.equals(null)) {
                        pd.put("uid", getUUID32());
                        System.out.println("新增");
                        consumerManager.savePerson(pd);
                    } else {
                        pd.put("uid", uid);
                        consumerManager.editPerson(pd);
                    }

                }

                List<PageData> errorList = ((List<PageData>) jsonObject.get("errorList")); //导入错误的信息
                if (errorList.size() > 0) {
//                    System.out.println(errorList);
                    for (int i = 0; i < errorList.size(); i++) {
                        pd = errorList.get(i);
                        pd.put("pcbh", pcbh);
                        pd.put("czman", pd_token.getString("ID"));
                        //System.out.println(errorList.get(i).getString("ycstr")+"ddd");
                        pd.put("uid", getUUID32());
                        pd.put("cate", "54acc890c8bc433ca53dc19b06d71857");
                        pd.put("czdate", getTime());
                        consumerManager.savePerson_error(pd);
                    }
                }
                json.put("success", "true");
                if (errorList.size() > 0) {
                    json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条,失败记录：" + errorList.size() + "条");
                    json.put("href", "api/excelsbbtry_error?pcbh=" + pcbh + "&tokenid=" + request.getParameter("tokenid"));
                } else {
                    json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条");
                }

            }

        } else {
            json.put("success", "false");
            json.put("msg", "登录超时，请重新登录");
        }
//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();
    }

    @RequestMapping(value = "/downLoadsbbtryExcelMode", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String downLoadsbbtryExcelMode(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {

                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("社保补贴人员");
                //创建标题行
//                String[] importColumns = {"姓名", "联系电话", "备注"};
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("姓名");
                titleRow.createCell((short) 1).setCellValue("联系电话");
                titleRow.createCell((short) 2).setCellValue("备注");


                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-sbbtry.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    /*
     * 异常信息下载
     */
    @RequestMapping(value = "/excelsbbtry_error", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String excelsbbtry_error(HttpServletResponse response) throws Exception {
//        response.addHeader("Access-Control-Allow-Origin", "*");
        Page page = new Page();
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        net.sf.json.JSONObject object = new net.sf.json.JSONObject();
        pd = this.getPageData();

        pd.put("TOKENID", pd.getString("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd);
        if (pd_token == null) {
            object.put("success", "false");
            object.put("msg", "登录超时请重新登录");
            return object.toString();
        }

        pd.put("pcbh", pd.getString("pcbh"));
        pd.put("tables", "tesp_person_error");
        page.setShowCount(9999999);
        page.setPd(pd);


        List<PageData> varOList = consumerManager.findsbbtrylistPage(page);

        try {
            Date date = new Date();
            String formatFileName = Tools.date2Str(date, "yyyyMMddHHmmss");
            OutputStream os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + formatFileName + ".xls");
            response.setContentType("application/msexcel");

            WritableWorkbook wbook = Workbook.createWorkbook(os);
            WritableSheet wsheet = wbook.createSheet(formatFileName, 0);

            WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat wcfFC = new WritableCellFormat(wfont);
            wcfFC.setBackground(Colour.YELLOW);

            wsheet.addCell(new Label(0, 0, "姓名"));
            wsheet.addCell(new Label(1, 0, "联系电话"));
            wsheet.addCell(new Label(2, 0, "备注"));
            wsheet.addCell(new Label(3, 0, "异常信息"));


            int num = 1;
            String ycstr = "";
            for (int i = 0; i < varOList.size(); i++) {

                ycstr = String.valueOf(varOList.get(i).getString("ycstr"));
                if (ycstr.indexOf("name") >= 0) {
                    wsheet.addCell(new Label(0, num, varOList.get(i).getString("name"), wcfFC));
                } else {
                    wsheet.addCell(new Label(0, num, varOList.get(i).getString("name")));
                }
                if (ycstr.indexOf("lxtel") >= 0) {
                    wsheet.addCell(new Label(1, num, varOList.get(i).getString("lxtel"), wcfFC));
                } else {
                    wsheet.addCell(new Label(1, num, varOList.get(i).getString("lxtel")));
                }
                if (ycstr.indexOf("remark") >= 0) {
                    wsheet.addCell(new Label(2, num, varOList.get(i).getString("remark"), wcfFC));
                } else {
                    wsheet.addCell(new Label(2, num, varOList.get(i).getString("remark")));
                }

                wsheet.addCell(new Label(3, num, varOList.get(i).getString("ycstrs")));
                num++;
            }
            wbook.write();
            if (wbook != null) {
                wbook.close();
                wbook = null;
            }
            if (os != null) {
                os.close();
                os = null;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        return null;
    }

    @RequestMapping(value = "/exportjzbtryExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportjzbtryExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                PageData pd = new PageData();
                Page page = new Page();
                URLDecoder urlDecoder = new URLDecoder();
                String starttime = request.getParameter("startTime");
                String endtime = request.getParameter("endTime");
                String level = request.getParameter("level");
                String pageIndex = "0";
                String pageSize = "999999999";
                String starttime1 = starttime == null ? "" : urlDecoder.decode(starttime, "utf-8");
                String endtime1 = endtime == null ? "" : urlDecoder.decode(endtime, "utf-8");
                String level1 = level == null ? "" : urlDecoder.decode(level, "utf-8");
                String pageIndex1 = pageIndex == null ? "" : urlDecoder.decode(pageIndex, "utf-8");
                String pageSize1 = pageSize == null ? "" : urlDecoder.decode(pageSize, "utf-8");
                pd.put("starttime", starttime1);
                pd.put("endtime", endtime1);
                pd.put("level", level1);
                pd.put("result", request.getParameter("result") == null ? "" : urlDecoder.decode(request.getParameter("result"), "utf-8"));
                pd.put("keywords", request.getParameter("keywords") == null ? "" : urlDecoder.decode(request.getParameter("keywords"), "utf-8"));
                pd.put("creden", request.getParameter("creden") == null ? "" : urlDecoder.decode(request.getParameter("creden"), "utf-8"));
                pd.put("pageIndex", pageIndex1);
                pd.put("pageSize", pageSize1);
                page.setPd(pd);
                System.out.println(pd);
                List<PageData> clist = consumerManager.findjzbtry(pd);
                System.out.println(clist);

                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("见证补贴人员");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("最新服务时间");
                titleRow.createCell((short) 2).setCellValue("服务次数");
                titleRow.createCell((short) 3).setCellValue("籍贯");
                titleRow.createCell((short) 4).setCellValue("姓名");
                titleRow.createCell((short) 5).setCellValue("联系电话");
                titleRow.createCell((short) 6).setCellValue("证件号码");
                titleRow.createCell((short) 7).setCellValue("年龄");
                titleRow.createCell((short) 8).setCellValue("性别");

                titleRow.createCell((short) 9).setCellValue("证书等级");
                titleRow.createCell((short) 10).setCellValue("工种");

                titleRow.createCell((short) 11).setCellValue("就业状态");
                titleRow.createCell((short) 12).setCellValue("情况记录");
                titleRow.createCell((short) 13).setCellValue("求职岗位");
                titleRow.createCell((short) 14).setCellValue("推荐企业");
                titleRow.createCell((short) 15).setCellValue("就业岗位");
                titleRow.createCell((short) 16).setCellValue("就业单位");
                titleRow.createCell((short) 17).setCellValue("推荐政策");
                titleRow.createCell((short) 18).setCellValue("推荐技能");
                titleRow.createCell((short) 19).setCellValue("通话状态");
                titleRow.createCell((short) 20).setCellValue("未接通原因");
                titleRow.createCell((short) 21).setCellValue("推荐企业数量");
                if (clist.size() > 0) {

                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count++);
                        dataRow.createCell((short) 1).setCellValue(pd1.getString("newServiceTime"));
                        dataRow.createCell((short) 2).setCellValue(pd1.get("serviceCount").toString());
                        dataRow.createCell((short) 3).setCellValue(pd1.getString("jgName"));
                        dataRow.createCell((short) 4).setCellValue(pd1.getString("name"));
                        dataRow.createCell((short) 5).setCellValue(pd1.getString("lxtel"));
                        dataRow.createCell((short) 6).setCellValue(pd1.getString("cardid"));
                        dataRow.createCell((short) 7).setCellValue(pd1.getString("age"));
                        String sex = pd1.getString("sex");
                        if ("1".equals(sex)) {
                            dataRow.createCell((short) 8).setCellValue("男");
                        } else if ("2".equals(sex)) {
                            dataRow.createCell((short) 8).setCellValue("女");
                        }
                        dataRow.createCell((short) 9).setCellValue(pd1.getString("credenname"));
                        dataRow.createCell((short) 10).setCellValue(pd1.getString("jobvarname"));

                        String isjob = pd1.getString("isjob");
                        if ("0".equals(isjob))
                            dataRow.createCell((short) 11).setCellValue("失业");
                        if ("1".equals(isjob))
                            dataRow.createCell((short) 11).setCellValue("就业");
                        if ("2".equals(isjob))
                            dataRow.createCell((short) 11).setCellValue("无意向就业");
                        if ("3".equals(isjob))
                            dataRow.createCell((short) 11).setCellValue("灵活就业");
                        if ("4".equals(isjob))
                            dataRow.createCell((short) 11).setCellValue("自主创业");

                        dataRow.createCell((short) 12).setCellValue(pd1.getString("cate"));
                        dataRow.createCell((short) 13).setCellValue(pd1.getString("qzgw"));
                        dataRow.createCell((short) 14).setCellValue(pd1.getString("companyName"));
                        dataRow.createCell((short) 15).setCellValue(pd1.getString("isjobwork"));
                        dataRow.createCell((short) 16).setCellValue(pd1.getString("jobunit"));
                        dataRow.createCell((short) 17).setCellValue(pd1.getString("zczxname"));
                        dataRow.createCell((short) 18).setCellValue(pd1.getString("trainreco"));
                        String isjt = pd1.getString("isjt");
                        if ("0".equals(isjt)) {
                            dataRow.createCell((short) 19).setCellValue("未接通");
                        } else if ("1".equals(isjt)) {
                            dataRow.createCell((short) 19).setCellValue("已接通");
                        } else {
                            dataRow.createCell((short) 19).setCellValue("未回访");
                        }
                        dataRow.createCell((short) 20).setCellValue(pd1.getString("unconnet"));
                        dataRow.createCell((short) 21).setCellValue(pd1.get("companyNum").toString());

                    }

                }
                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-jzbtry.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    @RequestMapping(value = "/readjzbtryExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String readjzbtryExcel(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        PageData pd_area = new PageData();
        JSONObject json = new JSONObject();
//        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        System.out.println(request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        com.alibaba.fastjson.JSONObject object_verificate = new com.alibaba.fastjson.JSONObject();
        List<PageData> saveList = new ArrayList<PageData>();
        List<PageData> dicAll = consumerManager.getDicAll(pd);

        if (pd_token != null) {

            String ZXID = pd_token.getString("ZXID");
            if (null != file && !file.isEmpty()) {
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String pcbh = sdf.format(d);  //导入批次

                String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
                String fileName = FileUpload.fileUp(file, filePath, "sbbtryListexcel");                            //执行上传
                ImportExcelService importExcelService = new ImportExcelService();
                String[] importColumns = {"姓名", "身份证号码", "工种", "证书等级", "联系电话", "所属地区", "备注"};//设置字段名称
                String[] importFields = {"name", "cardid", "jobvar", "creden", "lxtel", "jg", "remark"};  //字段
                // 1:IdCardVerification 2:DateVerification 3:TelVerification 4:DictVerification 5:ValueVerification 6:SqlVerification

                String[] importYzColumns = {"", "1", "6", "6", "3", "", ""};
                int[] importFiledNums = {100, 30, 0, 0, 20, 300, 500}; //0表示无限制
                int[] importFiledNull = {1, 1, 0, 0, 1, 0, 0}; //验证字段是否为空：0表示无限制，1表示不能为空
                String[] importTjColumns = {"", "", "", "", "isNumberic", "", ""};  //isNumberic 是否数字;isEnglish 是否英文 ；isChinese 是否中文
                String[] importValueColumns = {"", "", "select * from sys_dictionaries where  name = #{name}", "select * from sys_dictionaries where  name = #{name}", "", "", ""};

                object_verificate.put("importColumns", importColumns);
                object_verificate.put("importFields", importFields);
                object_verificate.put("importYzColumns", importYzColumns);
                object_verificate.put("importFiledNums", importFiledNums);
                object_verificate.put("importValueColumns", importValueColumns);
                object_verificate.put("importTjColumns", importTjColumns);
                object_verificate.put("importFiledNull", importFiledNull);

                String openFilename = filePath + fileName;
                com.alibaba.fastjson.JSONObject jsonObject = importExcelService.importExcel(openFilename, request, object_verificate);
                List<PageData> findperson = jobReferManager.findperson(pd);
                List<PageData> listPd = ((List<PageData>) jsonObject.get("rightList"));
                System.out.println(listPd);

                for (int i = 0; i < listPd.size(); i++) {
                    String uid = "";
                    pd = new PageData();
                    pd_area = new PageData();
                    for (PageData pageData : findperson) {
                        if ((listPd.get(i).getString("name").equals(pageData.getString("name")) && listPd.get(i).getString("lxtel").equals(pageData.getString("lxtel"))) || listPd.get(i).getString("cardid").equals(pageData.getString("cardid"))) {
                            uid = pageData.getString("uid");
                            pd = pageData;
                        }
                    }

                    pd.put("name", listPd.get(i).getString("name").trim());
                    pd.put("cardid", listPd.get(i).getString("cardid").trim());


                    String jobvar = listPd.get(i).getString("jobvar").trim();
                    for (PageData pageData : dicAll) {
                        if (jobvar.equals(pageData.getString("NAME"))) {
                            pd.put("jobvar", pageData.getString("DICTIONARIES_ID"));
                        }
                    }

                    String creden = listPd.get(i).getString("creden").trim();
                    for (PageData pageData : dicAll) {
                        if (creden.equals(pageData.getString("NAME"))) {
                            pd.put("creden", pageData.getString("DICTIONARIES_ID"));
                        }
                    }
                    pd.put("lxtel", listPd.get(i).getString("lxtel").trim());
                    pd.put("tel", listPd.get(i).getString("lxtel").trim());
                    pd.put("jg", listPd.get(i).getString("jg").trim());
                    pd.put("remark", listPd.get(i).getString("remark").trim());

                    pd.put("cate", "7b95e900ca3a4eee943cb3ca11e51fd1");
                    pd.put("czman", ZXID);
                    pd.put("uid", getUUID32());
                    pd.put("isimpot", "1");

                    String cardid = listPd.get(i).getString("cardid").trim();
                    if (StringUtils.isNotEmpty(cardid)) {
                        JSONObject cardidjson = JSON.parseObject(JSON.toJSON(identityCard(cardid)).toString());
                        pd.put("age", cardidjson.getString("age"));
                        pd.put("sex", cardidjson.getString("sex"));
                    }

                    PageData savePd = new PageData();
                    savePd.putAll(pd);
                    saveList.add(savePd);
//                            consumerManager.insertgraduate(pd);
                    if ("".equals(uid) || uid.equals(null)) {
                        pd.put("uid", getUUID32());
                        System.out.println("新增");
                        consumerManager.savePerson(pd);
                        trainrecoService.insertTrainreco(pd);
                    } else {
                        pd.put("uid", uid);
                        consumerManager.editPerson(pd);
                        trainrecoService.updateTrainreco(pd);
                    }

                }

                List<PageData> errorList = ((List<PageData>) jsonObject.get("errorList")); //导入错误的信息
                if (errorList.size() > 0) {
//                    System.out.println(errorList);
                    for (int i = 0; i < errorList.size(); i++) {
                        pd = errorList.get(i);
                        pd.put("pcbh", pcbh);
                        pd.put("czman", pd_token.getString("ID"));
                        //System.out.println(errorList.get(i).getString("ycstr")+"ddd");
                        pd.put("uid", getUUID32());
                        pd.put("cate", "7b95e900ca3a4eee943cb3ca11e51fd1");
                        pd.put("czdate", getTime());
                        consumerManager.savePerson_error(pd);
                        trainrecoService.saveTrainreco(pd);
                    }
                }
                json.put("success", "true");
                if (errorList.size() > 0) {
                    json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条,失败记录：" + errorList.size() + "条");
                    json.put("href", "api/exceljzbtry_error?pcbh=" + pcbh + "&tokenid=" + request.getParameter("tokenid"));
                } else {
                    json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条");
                }

            }

        } else {
            json.put("success", "false");
            json.put("msg", "登录超时，请重新登录");
        }
//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();
    }

    @RequestMapping(value = "/downLoadjzbtryExcelMode", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String downLoadjzbtryExcelMode(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {

                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("见证补贴人员");
                //创建标题行

                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("姓名");
                titleRow.createCell((short) 1).setCellValue("身份证号码");
                titleRow.createCell((short) 2).setCellValue("工种");
                titleRow.createCell((short) 3).setCellValue("证书等级");
                titleRow.createCell((short) 4).setCellValue("联系电话");
                titleRow.createCell((short) 5).setCellValue("所属地区");
                titleRow.createCell((short) 6).setCellValue("备注");


                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-jzbtry.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    /*
     * 异常信息下载
     */
    @RequestMapping(value = "/exceljzbtry_error", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exceljzbtry_error(HttpServletResponse response) throws Exception {
//        response.addHeader("Access-Control-Allow-Origin", "*");
        Page page = new Page();
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        net.sf.json.JSONObject object = new net.sf.json.JSONObject();
        pd = this.getPageData();

        pd.put("TOKENID", pd.getString("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd);
        if (pd_token == null) {
            object.put("success", "false");
            object.put("msg", "登录超时请重新登录");
            return object.toString();
        }

        pd.put("pcbh", pd.getString("pcbh"));
        pd.put("tables", "tesp_person_error");
        pd.put("tables2", "tesp_trainreco_error");
        page.setShowCount(9999999);
        page.setPd(pd);


        List<PageData> varOList = consumerManager.findjzbtrylistPage(page);

        try {
            Date date = new Date();
            String formatFileName = Tools.date2Str(date, "yyyyMMddHHmmss");
            OutputStream os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + formatFileName + ".xls");
            response.setContentType("application/msexcel");

            WritableWorkbook wbook = Workbook.createWorkbook(os);
            WritableSheet wsheet = wbook.createSheet(formatFileName, 0);

            WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat wcfFC = new WritableCellFormat(wfont);
            wcfFC.setBackground(Colour.YELLOW);

            wsheet.addCell(new Label(0, 0, "姓名"));
            wsheet.addCell(new Label(1, 0, "身份证号码"));
            wsheet.addCell(new Label(2, 0, "工种"));
            wsheet.addCell(new Label(3, 0, "证书等级"));
            wsheet.addCell(new Label(4, 0, "联系电话"));
            wsheet.addCell(new Label(5, 0, "所属地区"));
            wsheet.addCell(new Label(6, 0, "备注"));
            wsheet.addCell(new Label(7, 0, "异常信息"));


            int num = 1;
            String ycstr = "";
            for (int i = 0; i < varOList.size(); i++) {
//                String[] importColumns = {"姓名", "身份证号码", "工种", "证书等级", "联系电话", "所属地区", "备注"};//设置字段名称
//                String[] importFields = {"name", "cardid", "jobvar","creden","lxtel","jg","remark"};  //字段
                ycstr = String.valueOf(varOList.get(i).getString("ycstr"));
                if (ycstr.indexOf("name") >= 0) {
                    wsheet.addCell(new Label(0, num, varOList.get(i).getString("name"), wcfFC));
                } else {
                    wsheet.addCell(new Label(0, num, varOList.get(i).getString("name")));
                }
                if (ycstr.indexOf("cardid") >= 0) {
                    wsheet.addCell(new Label(1, num, varOList.get(i).getString("cardid"), wcfFC));
                } else {
                    wsheet.addCell(new Label(1, num, varOList.get(i).getString("cardid")));
                }
                if (ycstr.indexOf("jobname") >= 0) {
                    wsheet.addCell(new Label(2, num, varOList.get(i).getString("jobname"), wcfFC));
                } else {
                    wsheet.addCell(new Label(2, num, varOList.get(i).getString("jobname")));
                }
                if (ycstr.indexOf("creden") >= 0) {
                    wsheet.addCell(new Label(3, num, varOList.get(i).getString("credenname"), wcfFC));
                } else {
                    wsheet.addCell(new Label(3, num, varOList.get(i).getString("credenname")));
                }
                if (ycstr.indexOf("tel") >= 0) {
                    wsheet.addCell(new Label(4, num, varOList.get(i).getString("tel"), wcfFC));
                } else {
                    wsheet.addCell(new Label(4, num, varOList.get(i).getString("tel")));
                }
                if (ycstr.indexOf("address") >= 0) {
                    wsheet.addCell(new Label(5, num, varOList.get(i).getString("address"), wcfFC));
                } else {
                    wsheet.addCell(new Label(5, num, varOList.get(i).getString("address")));
                }
                if (ycstr.indexOf("remark") >= 0) {
                    wsheet.addCell(new Label(6, num, varOList.get(i).getString("remark"), wcfFC));
                } else {
                    wsheet.addCell(new Label(6, num, varOList.get(i).getString("remark")));
                }


                wsheet.addCell(new Label(7, num, varOList.get(i).getString("ycstrs")));
                num++;
            }
            wbook.write();
            if (wbook != null) {
                wbook.close();
                wbook = null;
            }
            if (os != null) {
                os.close();
                os = null;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        return null;
    }

    //用工推荐
    @RequestMapping(value = "/exportemployregExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportemployregExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                PageData pd = new PageData();
                URLDecoder urlDecoder = new URLDecoder();
                String name = request.getParameter("name");
                String qytype = request.getParameter("qytype");
                String lxr = request.getParameter("lxr");
                String lxtel = request.getParameter("lxtel");
                String keywords = request.getParameter("keywords");

                String name1 = name == null ? "" : urlDecoder.decode(name, "utf-8");
                String qytype1 = qytype == null ? "" : urlDecoder.decode(qytype, "utf-8");
                String lxr1 = lxr == null ? "" : urlDecoder.decode(lxr, "utf-8");
                String lxtel1 = lxtel == null ? "" : urlDecoder.decode(lxtel, "utf-8");
                String keywords1 = keywords == null ? "" : urlDecoder.decode(keywords, "utf-8");

                pd.put("name", name1);
                pd.put("qytype", qytype1);
                pd.put("lxr", lxr1);
                pd.put("lxtel", lxtel1);
                pd.put("keywords", keywords1);
                pd.put("result", request.getParameter("result") == null ? "" : urlDecoder.decode(request.getParameter("result"), "utf-8"));
                pd.put("starttime", request.getParameter("startTime") == null ? "" : urlDecoder.decode(request.getParameter("startTime"), "utf-8"));
                pd.put("endtime", request.getParameter("endTime") == null ? "" : urlDecoder.decode(request.getParameter("endTime"), "utf-8"));
                pd.put("tjstarttime", request.getParameter("tjstartTime") == null ? "" : urlDecoder.decode(request.getParameter("tjstartTime"), "utf-8"));
                pd.put("tjendtime", request.getParameter("tjendTime") == null ? "" : urlDecoder.decode(request.getParameter("tjendTime"), "utf-8"));
                System.out.println(pd);

//            List<PageData> clist = consumerManager.findemployreg(pd);

//            System.out.println("clist"+clist);
//            System.out.println("company"+company);
                Page page = new Page();
                page.setPd(pd);
                page.setCurrentPage(1);
                page.setShowCount(999999);
//            List<PageData> company = consumerManager.findcomreglistPage(page);
                List<PageData> company = jobReferManager.personreferDatalistPage(page);
                List<PageData> referlist = jobReferManager.Companyrefer(pd);
                ArrayList<PageData> list = new ArrayList<>();
                if (company.size() > 0) {
                    for (PageData pageData : company) {
                        PageData pageData1 = new PageData();

                        int refer = 0;
                        if (referlist.size() > 0) {
                            for (PageData data : referlist) {
                                if (data.getString("uid").equals(pageData.getString("uid"))) {
                                    refer++;
                                }
                            }

                        }
                        pageData.put("refersize", refer);

                        pageData1.put("uid", pageData.getString("uid"));
                        page.setPd(pageData1);
                        List<PageData> listYgtj = companyService.listYgtj(page);
                        ArrayList<String> Ygtj = new ArrayList<>();
                        for (PageData data1 : listYgtj) {
                            Ygtj.add(data1.getString("personname"));
                        }
                        String Ygtjstring = Ygtj.toString().substring(1, Ygtj.toString().length() - 1);
                        pageData.put("Ygtjsize", listYgtj.size());
                        pageData.put("listYgtj", Ygtjstring);
                        list.add(pageData);

                    }
                }


                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("企业招聘信息");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("登记时间");
                titleRow.createCell((short) 2).setCellValue("企业名称");
                titleRow.createCell((short) 3).setCellValue("联系人");
                titleRow.createCell((short) 4).setCellValue("联系电话");
                titleRow.createCell((short) 5).setCellValue("企业地址");
                titleRow.createCell((short) 6).setCellValue("招聘工种");
                titleRow.createCell((short) 7).setCellValue("招工人数");
                titleRow.createCell((short) 8).setCellValue("岗位要求");

                titleRow.createCell((short) 9).setCellValue("工资待遇");
                titleRow.createCell((short) 10).setCellValue("用工推荐");
                titleRow.createCell((short) 11).setCellValue("推荐人员总量");
                titleRow.createCell((short) 12).setCellValue("已推荐人员量");
                System.out.println("list" + list);
                if (company.size() > 0) {

                    for (PageData pd1 : list) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(sheet.getLastRowNum());
                        dataRow.createCell((short) 1).setCellValue(pd1.getString("czdate"));
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("name"));
                        dataRow.createCell((short) 3).setCellValue(pd1.getString("lxr"));
                        dataRow.createCell((short) 4).setCellValue(pd1.getString("lxtel"));
                        dataRow.createCell((short) 5).setCellValue(pd1.getString("addr"));

                        dataRow.createCell((short) 6).setCellValue(pd1.getString("gwflname"));
                        dataRow.createCell((short) 7).setCellValue(pd1.getString("zgrs"));
                        dataRow.createCell((short) 8).setCellValue(pd1.getString("gwyq"));
                        dataRow.createCell((short) 9).setCellValue(pd1.getString("gzdyname"));

                        dataRow.createCell((short) 10).setCellValue(pd1.getString("listYgtj"));
                        dataRow.createCell((short) 11).setCellValue(pd1.get("Ygtjsize").toString());
                        dataRow.createCell((short) 12).setCellValue(pd1.get("refersize").toString());

                    }


                }
                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-company.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    @RequestMapping(value = "/exportcompanyExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportcompanyExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                PageData pd = new PageData();
                Page page = new Page();
                URLDecoder urlDecoder = new URLDecoder();
                String name = request.getParameter("name");
                String qytype = request.getParameter("qytype");
                String lxr = request.getParameter("lxr");
                String lxtel = request.getParameter("lxtel");
                String keywords = request.getParameter("keywords");
                String pageIndex = "0";
                String pageSize = "999999999";
                String name1 = name == null ? "" : urlDecoder.decode(name, "utf-8");
                String qytype1 = qytype == null ? "" : urlDecoder.decode(qytype, "utf-8");
                String lxr1 = lxr == null ? "" : urlDecoder.decode(lxr, "utf-8");
                String lxtel1 = lxtel == null ? "" : urlDecoder.decode(lxtel, "utf-8");
                String keywords1 = keywords == null ? "" : urlDecoder.decode(keywords, "utf-8");

                String pageIndex1 = pageIndex == null ? "" : urlDecoder.decode(pageIndex, "utf-8");
                String pageSize1 = pageSize == null ? "" : urlDecoder.decode(pageSize, "utf-8");

                pd.put("name", name1);
                pd.put("qytype", qytype1);
                pd.put("lxr", lxr1);
                pd.put("lxtel", lxtel1);
                pd.put("keywords", keywords1);
                pd.put("result", request.getParameter("result") == null ? "" : urlDecoder.decode(request.getParameter("result"), "utf-8"));
                pd.put("pageIndex", pageIndex1);
                pd.put("pageSize", pageSize1);
                page.setPd(pd);
                System.out.println(pd);
                List<PageData> clist = consumerManager.findcompany(pd);

                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("企业招聘信息");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("企业编号");
                titleRow.createCell((short) 1).setCellValue("企业名称");
                titleRow.createCell((short) 2).setCellValue("企业类型");
                titleRow.createCell((short) 3).setCellValue("联系人");
                titleRow.createCell((short) 4).setCellValue("联系电话");
                titleRow.createCell((short) 5).setCellValue("备用联系人");
                titleRow.createCell((short) 6).setCellValue("备用联系电话");
                titleRow.createCell((short) 7).setCellValue("企业地址");
                titleRow.createCell((short) 8).setCellValue("企业邮箱");

                titleRow.createCell((short) 9).setCellValue("招聘工种");
                titleRow.createCell((short) 10).setCellValue("岗位性质");
                titleRow.createCell((short) 11).setCellValue("招工人数");
                titleRow.createCell((short) 12).setCellValue("岗位要求");
                titleRow.createCell((short) 13).setCellValue("学历要求");
                titleRow.createCell((short) 14).setCellValue("专业要求");
                titleRow.createCell((short) 15).setCellValue("性别要求");
                titleRow.createCell((short) 16).setCellValue("年龄要求");
                titleRow.createCell((short) 17).setCellValue("工资待遇");
                titleRow.createCell((short) 18).setCellValue("其他说明");
                if (clist.size() > 0) {


                    for (PageData pageData : clist) {
                        String uid = pageData.getString("uid");
                        PageData data = new PageData();
                        data.put("uid", uid);
                        List<PageData> employ = consumerManager.findemployreg(data);
//                        List<PageData> company = consumerManager.findcompany(data);
                        if (employ.size() > 0) {
                            for (PageData pd1 : employ) {
                                HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                                dataRow.createCell((short) 0).setCellValue(sheet.getLastRowNum());
                                dataRow.createCell((short) 1).setCellValue(pageData.getString("name"));
                                dataRow.createCell((short) 2).setCellValue(pageData.getString("qytypename"));
                                dataRow.createCell((short) 3).setCellValue(pageData.getString("lxr"));
                                dataRow.createCell((short) 4).setCellValue(pageData.getString("lxtel"));
                                dataRow.createCell((short) 5).setCellValue(pageData.getString("bylxr"));
                                dataRow.createCell((short) 6).setCellValue(pageData.getString("bylxtel"));
                                dataRow.createCell((short) 7).setCellValue(pageData.getString("addr"));
                                dataRow.createCell((short) 8).setCellValue(pageData.getString("email"));

                                dataRow.createCell((short) 9).setCellValue(pd1.getString("gwflname"));
                                dataRow.createCell((short) 10).setCellValue(pd1.getString("jobtypename"));
                                dataRow.createCell((short) 11).setCellValue(pd1.getString("zgrs"));
                                dataRow.createCell((short) 12).setCellValue(pd1.getString("gwyq"));
                                dataRow.createCell((short) 13).setCellValue(pd1.getString("xlname"));
                                dataRow.createCell((short) 14).setCellValue(pd1.getString("zyyq"));
                                String sex = pd1.getString("sex");
                                if ("1".equals(sex)) {
                                    sex = "男";
                                } else if ("2".equals(sex)) {
                                    sex = "女";
                                } else {
                                    sex = "均可";
                                }
                                dataRow.createCell((short) 15).setCellValue(sex);
                                dataRow.createCell((short) 16).setCellValue(pd1.getString("age"));
                                dataRow.createCell((short) 17).setCellValue(pd1.getString("gzdyname"));
                                dataRow.createCell((short) 18).setCellValue(pd1.getString("remark"));

                            }
                        } else {
                            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                            dataRow.createCell((short) 0).setCellValue(sheet.getLastRowNum());
                            dataRow.createCell((short) 1).setCellValue(pageData.getString("name"));
                            dataRow.createCell((short) 2).setCellValue(pageData.getString("qytypename"));
                            dataRow.createCell((short) 3).setCellValue(pageData.getString("lxr"));
                            dataRow.createCell((short) 4).setCellValue(pageData.getString("lxtel"));
                            dataRow.createCell((short) 5).setCellValue(pageData.getString("bylxr"));
                            dataRow.createCell((short) 6).setCellValue(pageData.getString("bylxtel"));
                            dataRow.createCell((short) 7).setCellValue(pageData.getString("addr"));
                            dataRow.createCell((short) 8).setCellValue(pageData.getString("email"));

                        }

                    }


                }
                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-company.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    @RequestMapping(value = "/readcompanyExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String readcompanyExcel(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest request) throws Exception {

        PageData pd = new PageData();
        PageData pd_area = new PageData();
        JSONObject json = new JSONObject();
//        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        System.out.println(request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        com.alibaba.fastjson.JSONObject object_verificate = new com.alibaba.fastjson.JSONObject();
        List<PageData> saveList = new ArrayList<PageData>();
        List<PageData> dicAll = consumerManager.getDicAll(pd);
        if (pd_token != null) {

            String ZXID = pd_token.getString("ZXID");
            if (null != file && !file.isEmpty()) {
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String pcbh = sdf.format(d);  //导入批次

                String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
                String fileName = FileUpload.fileUp(file, filePath, "graduateListexcel");                            //执行上传
                ImportExcelService importExcelService = new ImportExcelService();
                String[] importColumns = {"企业名称", "企业类型", "联系人", "联系电话", "备用联系人", "备用联系电话", "企业地址", "邮箱", "招聘岗位", "岗位性质", "招工人数", "岗位要求", "学历", "专业要求", "性别要求", "年龄要求", "工资待遇"};//设置字段名称16
                String[] importFields = {"name", "qytype", "lxr", "lxtel", "bylxr", "bylxtel", "addr", "email", "jobname", "jobtype", "zgrs", "gwyq", "xl", "zyyq", "sex", "age", "gzdy"};  //字段
                // 1:IdCardVerification 2:DateVerification 3:TelVerification 4:DictVerification 5:ValueVerification 6:SqlVerification

                String[] importYzColumns = {"", "6", "", "", "", "", "", "", "6", "6", "", "", "6", "", "6", "", "6"};
                int[] importFiledNums = {0, 20, 50, 20, 50, 20, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //0表示无限制
                int[] importFiledNull = {1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //验证字段是否为空：0表示无限制，1表示不能为空
                String[] importTjColumns = {"", "", "", "isNumberic", "", "isNumberic", "", "", "", "", "isNumberic", "", "", "", "", "", ""};  //isNumberic 是否数字;isEnglish 是否英文 ；isChinese 是否中文
                String[] importValueColumns = {"", "select * from sys_dictionaries where  name = #{name}", "", "", "", "", "", "", "select * from tesp_jobtype where  gwflname = #{name}", "select * from sys_dictionaries where  name = #{name}", "", "", "select * from sys_dictionaries where  name = #{name}", "", "select * from sys_dictionaries where  name = #{name}", "select * from sys_dictionaries where  name = #{name}", "select * from sys_dictionaries where  name = #{name}"};

                object_verificate.put("importColumns", importColumns);
                object_verificate.put("importFields", importFields);
                object_verificate.put("importYzColumns", importYzColumns);
                object_verificate.put("importFiledNums", importFiledNums);
                object_verificate.put("importValueColumns", importValueColumns);
                object_verificate.put("importTjColumns", importTjColumns);
                object_verificate.put("importFiledNull", importFiledNull);

                String openFilename = filePath + fileName;
                com.alibaba.fastjson.JSONObject jsonObject = importExcelService.importExcel(openFilename, request, object_verificate);

                List<PageData> findemployreg = consumerManager.findemployreg(pd);

                List<PageData> listPd = ((List<PageData>) jsonObject.get("rightList"));

                System.out.println(jsonObject);
                System.out.println(listPd);
                for (int i = 0; i < listPd.size(); i++) {
                    String uid = "";
                    String juid = "";
                    pd = new PageData();
                    pd_area = new PageData();
                    String lxtel = listPd.get(i).getString("lxtel") == null ? "" : listPd.get(i).getString("lxtel").replace("-", "");
                    List<PageData> findcompany = consumerManager.findcompany(pd);
                    for (PageData pageData : findcompany) {
                        if ((listPd.get(i).getString("name").equals(pageData.getString("name")))) {
                            uid = pageData.getString("uid");
                            pd = pageData;
                        }
                    }

//                    String[] importFields = {"name", "qytype", "lxr", "lxtel", "bylxr", "bylxtel", "addr", "email",
//                            "jobname", "jobtype", "zgrs", "gwyq", "xl", "zyyq", "sex", "age", "gzdy"};  //字段
                    pd.put("name", listPd.get(i).getString("name").trim());
                    for (PageData pageData : dicAll) {
                        if (pageData.getString("NAME").equals(listPd.get(i).getString("qytype").trim())) {
                            pd.put("qytype", pageData.getString("DICTIONARIES_ID"));
                        }
                    }
                    pd.put("lxr", listPd.get(i).getString("lxr").trim());
                    pd.put("lxtel", listPd.get(i).getString("lxtel").trim());
                    pd.put("tel", listPd.get(i).getString("lxtel").trim());
                    pd.put("bylxtel", listPd.get(i).getString("bylxtel").trim());
                    pd.put("addr", listPd.get(i).getString("addr").trim());
                    pd.put("email", listPd.get(i).getString("email").trim());
                    PageData pd2 = new PageData();
                    String jobname = listPd.get(i).getString("jobname").trim();
                    pd2.put("gwflname", jobname);
                    PageData getjobtype = consumerManager.getjobtype(pd2);

                    if (getjobtype != null) {
                        jobname = getjobtype.get("code").toString();
                    } else {
                        jobname = "暂无";
                    }
                    pd.put("jobname", jobname);

                    for (PageData pageData : dicAll) {
                        if (pageData.getString("NAME").equals(listPd.get(i).getString("jobtype").trim())) {
                            pd.put("jobtype", pageData.getString("DICTIONARIES_ID"));
                        }
                    }
                    pd.put("zgrs", listPd.get(i).getString("zgrs").trim());
                    pd.put("gwyq", listPd.get(i).getString("gwyq").trim());

                    for (PageData pageData : dicAll) {
                        if (pageData.getString("NAME").equals(listPd.get(i).getString("xl").trim())) {
                            pd.put("xl", pageData.getString("DICTIONARIES_ID"));
                        }
                    }
                    pd.put("zyyq", listPd.get(i).getString("zyyq").trim());

                    String sex = listPd.get(i).getString("sex").trim();
                    if ("男".equals(sex)) {
                        pd.put("sex", "1");
                    }else if ("女".equals(sex)){
                        pd.put("sex", "2");
                    }else if ("不限".equals(sex)){
                        pd.put("sex", "0");
                    }else {
                        pd.put("sex", "");
                    }

//                    for (PageData pageData : dicAll) {
//                        if (pageData.getString("NAME").equals(listPd.get(i).getString("sex").trim())) {
//                            pd.put("sex", pageData.getString("DICTIONARIES_ID"));
//                        }
//                    }


                    String age = listPd.get(i).getString("age").trim();
                    for (PageData pageData : dicAll) {
                        if (pageData.getString("NAME").equals(listPd.get(i).getString("age").trim())) {
                            if ("不限".equals(listPd.get(i).getString("age").trim())) {
                                pd.put("agestart", 0);
                                pd.put("ageend", 100);
                            }else {
                                String[] split = age.split("-");

                                pd.put("agestart", split[0]);
                                pd.put("ageend", split[1]);
                            }

                        }
                    }
                    pd.put("gzdy", listPd.get(i).getString("gzdy").trim());
                    for (PageData pageData : dicAll) {
                        if (pageData.getString("NAME").equals(listPd.get(i).getString("gzdy").trim())) {
                            pd.put("gzdy", pageData.getString("DICTIONARIES_ID"));
                        }
                    }

                    pd.put("isimpot", '1');
                    pd.put("cate", "1");
                    pd.put("czdate", getTime());
                    pd.put("czman", ZXID);

                    PageData savePd = new PageData();
                    savePd.putAll(pd);
                    saveList.add(savePd);

                    if ("".equals(uid) || uid.equals(null)) {
                        pd.put("uid", getUUID32());
                        System.out.println("新增");
                        consumerManager.insertcompany(pd);
                        consumerManager.insertemployreg(pd);
                    } else {
                        pd.put("uid", uid);
                        consumerManager.updatecompany(pd);
                        consumerManager.insertemployreg(pd);
                    }

                }

                List<PageData> errorList = ((List<PageData>) jsonObject.get("errorList")); //导入错误的信息
                if (errorList.size() > 0) {
                    System.out.println(errorList);
                    for (int i = 0; i < errorList.size(); i++) {
                        pd = errorList.get(i);
                        pd.put("pcbh", pcbh);
                        pd.put("uid", getUUID32());
                        pd.put("cate", "1");
                        pd.put("czman", pd_token.getString("ID"));
                        pd.put("czdate", getTime());
                        //System.out.println(errorList.get(i).getString("ycstr")+"ddd");
                        consumerManager.insertcompany_error(pd);
                        consumerManager.insertemployreg_error(pd);
                    }
                }

                json.put("success", "true");

                if (errorList.size() > 0) {
                    json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条,失败记录：" + errorList.size() + "条");
                    json.put("msg", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条,失败记录：" + errorList.size() + "条");
                    json.put("href", "api/excelLoseJob_error?pcbh=" + pcbh + "&tokenid=" + request.getParameter("tokenid"));
                } else {
                    json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条");
                    json.put("msg", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条");
                }
            }


        } else {
            json.put("success", "false");
            json.put("msg", "登录超时，请重新登录");
        }
//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();
    }

    //其他人员信息导入
    @RequestMapping(value = "/readOtherPersonExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String readOtherPersonExcel(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        PageData pd_area = new PageData();
        JSONObject json = new JSONObject();
//        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        System.out.println(request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        com.alibaba.fastjson.JSONObject object_verificate = new com.alibaba.fastjson.JSONObject();
        List<PageData> saveList = new ArrayList<PageData>();
        List<PageData> dicAll = consumerManager.getDicAll(pd);

        if (pd_token != null) {

            String ZXID = pd_token.getString("ZXID");
            if (null != file && !file.isEmpty()) {
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String pcbh = sdf.format(d);  //导入批次

                String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
                String fileName = FileUpload.fileUp(file, filePath, "sbbtryListexcel");                            //执行上传
                ImportExcelService importExcelService = new ImportExcelService();
                String[] importColumns = {"姓名", "性别", "身份证号码", "联系电话", "年龄", "籍贯", "邮箱", "学历", "专业"};//设置字段名称
                String[] importFields = {"name", "sex", "cardid", "lxtel", "age", "jg", "email", "xl", "zy"};  //字段
                // 1:IdCardVerification 2:DateVerification 3:TelVerification 4:DictVerification 5:ValueVerification 6:SqlVerification

                String[] importYzColumns = {"", "", "1", "", "", "", "", "6", ""};
                int[] importFiledNums = {50, 5, 20, 20, 10, 100, 100, 50, 100}; //0表示无限制
                int[] importFiledNull = {1, 1, 1, 1, 0, 0, 0, 0, 0}; //验证字段是否为空：0表示无限制，1表示不能为空
                String[] importTjColumns = {"", "", "", "isNumberic", "isNumberic", "", "", "", ""};  //isNumberic 是否数字;isEnglish 是否英文 ；isChinese 是否中文
                String[] importValueColumns = {"", "", "", "", "", "", "", "select * from sys_dictionaries where  name = #{name}", ""};

                object_verificate.put("importColumns", importColumns);
                object_verificate.put("importFields", importFields);
                object_verificate.put("importYzColumns", importYzColumns);
                object_verificate.put("importFiledNums", importFiledNums);
                object_verificate.put("importValueColumns", importValueColumns);
                object_verificate.put("importTjColumns", importTjColumns);
                object_verificate.put("importFiledNull", importFiledNull);

                String openFilename = filePath + fileName;
                com.alibaba.fastjson.JSONObject jsonObject = importExcelService.importExcel(openFilename, request, object_verificate);
                List<PageData> findperson = jobReferManager.findperson(pd);
                List<PageData> listPd = ((List<PageData>) jsonObject.get("rightList"));
                System.out.println(listPd);
                for (int i = 0; i < listPd.size(); i++) {
                    String uid = "";
                    pd = new PageData();
                    pd_area = new PageData();
                    for (PageData pageData : findperson) {
                        if ((listPd.get(i).getString("name").equals(pageData.getString("name")) && listPd.get(i).getString("lxtel").equals(pageData.getString("lxtel"))) || listPd.get(i).getString("cardid").equals(pageData.getString("cardid"))) {
                            uid = pageData.getString("uid");
                            pd = pageData;
                        }
                    }

                    pd.put("name", listPd.get(i).getString("name").trim());
                    pd.put("sex", listPd.get(i).getString("sex").trim());
                    pd.put("cardid", listPd.get(i).getString("cardid").trim());
                    pd.put("lxtel", listPd.get(i).getString("lxtel").trim());
                    pd.put("tel", listPd.get(i).getString("lxtel").trim());
                    pd.put("age", listPd.get(i).getString("age").trim());
                    pd.put("jg", listPd.get(i).getString("jg").trim());
//                    String isjob = listPd.get(i).getString("isjob").trim();
//                    if ("0".equals(isjob))
//                        isjob="失业";
//                    if ("1".equals(isjob))
//                        isjob="就业";
//                    if ("2".equals(isjob))
//                        isjob="无意向就业";
//                    if ("3".equals(isjob))
//                        isjob="灵活就业";
//                    if ("4".equals(isjob))
//                        isjob="自主创业";
//                    pd.put("isjob", isjob);
                    pd.put("email", listPd.get(i).getString("email").trim());
                    for (PageData pageData : dicAll) {
                        if (pageData.getString("NAME").equals(listPd.get(i).getString("xl").trim())) {
                            pd.put("xl", pageData.getString("DICTIONARIES_ID"));
                        }
                    }

                    pd.put("zy", listPd.get(i).getString("zy").trim());

                    pd.put("czman", ZXID);
                    pd.put("uid", getUUID32());
                    pd.put("isimpot", "1");

                    String cardid = listPd.get(i).getString("cardid").trim();
                    if (StringUtils.isNotEmpty(cardid)) {
                        JSONObject cardidjson = JSON.parseObject(JSON.toJSON(identityCard(cardid)).toString());
                        pd.put("age", cardidjson.getString("age"));
                        pd.put("sex", cardidjson.getString("sex"));
                    }

                    PageData savePd = new PageData();
                    savePd.putAll(pd);
                    saveList.add(savePd);
//                            consumerManager.insertgraduate(pd);
                    if ("".equals(uid) || uid.equals(null)) {
                        pd.put("uid", getUUID32());
                        System.out.println("新增");
                        consumerManager.savePerson(pd);
                    } else {
                        pd.put("uid", uid);
                        consumerManager.editPerson(pd);
                    }

                }

                List<PageData> errorList = ((List<PageData>) jsonObject.get("errorList")); //导入错误的信息
                if (errorList.size() > 0) {
//                    System.out.println(errorList);
                    for (int i = 0; i < errorList.size(); i++) {
                        pd = errorList.get(i);
                        pd.put("pcbh", pcbh);
                        pd.put("czman", pd_token.getString("ID"));
                        //System.out.println(errorList.get(i).getString("ycstr")+"ddd");
                        pd.put("uid", getUUID32());
                        pd.put("cate", "95df4fa459264d77b508c963b4ebbe21");
                        pd.put("czdate", getTime());
                        consumerManager.savePerson_error(pd);
                    }
                }
                json.put("success", "true");
                if (errorList.size() > 0) {
                    json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条,失败记录：" + errorList.size() + "条");
                    json.put("href", "api/excelOtherPerson_error?pcbh=" + pcbh + "&tokenid=" + request.getParameter("tokenid"));
                } else {
                    json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条");
                }

            }

        } else {
            json.put("success", "false");
            json.put("msg", "登录超时，请重新登录");
        }
//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();

    }

    @RequestMapping(value = "/downLoadOtherPersonExcelMode", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String downLoadOtherPersonExcelMode(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {

                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("其他人员");
                //创建标题行

                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("姓名");
                titleRow.createCell((short) 1).setCellValue("性别");
                titleRow.createCell((short) 2).setCellValue("身份证号码");
                titleRow.createCell((short) 3).setCellValue("联系电话");
                titleRow.createCell((short) 4).setCellValue("年龄");
                titleRow.createCell((short) 5).setCellValue("籍贯");
                titleRow.createCell((short) 6).setCellValue("邮箱");
                titleRow.createCell((short) 7).setCellValue("学历");
                titleRow.createCell((short) 8).setCellValue("专业");


                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-OtherPerson.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    /*
     * 异常信息下载
     */
    @RequestMapping(value = "/excelOtherPerson_error", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String excelOtherPerson_error(HttpServletResponse response) throws Exception {
//        response.addHeader("Access-Control-Allow-Origin", "*");
        Page page = new Page();
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        net.sf.json.JSONObject object = new net.sf.json.JSONObject();
        pd = this.getPageData();

        pd.put("TOKENID", pd.getString("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd);
        if (pd_token == null) {
            object.put("success", "false");
            object.put("msg", "登录超时请重新登录");
            return object.toString();
        }

        pd.put("pcbh", pd.getString("pcbh"));
        pd.put("tables", "tesp_person_error");
        page.setShowCount(9999999);
        page.setPd(pd);


        List<PageData> varOList = otherPersonService.findAlllistPage(page);

        try {
            Date date = new Date();
            String formatFileName = Tools.date2Str(date, "yyyyMMddHHmmss");
            OutputStream os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + formatFileName + ".xls");
            response.setContentType("application/msexcel");

            WritableWorkbook wbook = Workbook.createWorkbook(os);
            WritableSheet wsheet = wbook.createSheet(formatFileName, 0);

            WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat wcfFC = new WritableCellFormat(wfont);
            wcfFC.setBackground(Colour.YELLOW);

            wsheet.addCell(new Label(0, 0, "姓名"));
            wsheet.addCell(new Label(1, 0, "性别"));
            wsheet.addCell(new Label(2, 0, "身份证号码"));
            wsheet.addCell(new Label(3, 0, "联系电话"));
            wsheet.addCell(new Label(4, 0, "年龄"));
            wsheet.addCell(new Label(5, 0, "籍贯"));
            wsheet.addCell(new Label(6, 0, "邮箱"));
            wsheet.addCell(new Label(7, 0, "学历"));
            wsheet.addCell(new Label(8, 0, "专业"));
            wsheet.addCell(new Label(9, 0, "异常信息"));


            int num = 1;
            String ycstr = "";
            for (int i = 0; i < varOList.size(); i++) {
//                    String[] importColumns = {"姓名", "性别", "身份证号码", "联系电话", "年龄", "籍贯", "邮箱", "学历", "专业"};//设置字段名称
//                    String[] importFields = {"name", "sex", "cardid", "lxtel", "age", "jg", "isjob", "email", "xl", "zy"};  //字段
                ycstr = String.valueOf(varOList.get(i).getString("ycstr"));
                if (ycstr.indexOf("name") >= 0) {
                    wsheet.addCell(new Label(0, num, varOList.get(i).getString("name"), wcfFC));
                } else {
                    wsheet.addCell(new Label(0, num, varOList.get(i).getString("name")));
                }
                if (ycstr.indexOf("sex") >= 0) {
                    wsheet.addCell(new Label(1, num, varOList.get(i).getString("sex"), wcfFC));
                } else {
                    wsheet.addCell(new Label(1, num, varOList.get(i).getString("sex")));
                }
                if (ycstr.indexOf("cardid") >= 0) {
                    wsheet.addCell(new Label(2, num, varOList.get(i).getString("cardid"), wcfFC));
                } else {
                    wsheet.addCell(new Label(2, num, varOList.get(i).getString("cardid")));
                }
                if (ycstr.indexOf("lxtel") >= 0) {
                    wsheet.addCell(new Label(3, num, varOList.get(i).getString("lxtel"), wcfFC));
                } else {
                    wsheet.addCell(new Label(3, num, varOList.get(i).getString("lxtel")));
                }
                if (ycstr.indexOf("age") >= 0) {
                    wsheet.addCell(new Label(4, num, varOList.get(i).getString("age"), wcfFC));
                } else {
                    wsheet.addCell(new Label(4, num, varOList.get(i).getString("age")));
                }
                if (ycstr.indexOf("jg") >= 0) {
                    wsheet.addCell(new Label(5, num, varOList.get(i).getString("jg"), wcfFC));
                } else {
                    wsheet.addCell(new Label(5, num, varOList.get(i).getString("jg")));
                }
                if (ycstr.indexOf("email") >= 0) {
                    wsheet.addCell(new Label(6, num, varOList.get(i).getString("email"), wcfFC));
                } else {
                    wsheet.addCell(new Label(6, num, varOList.get(i).getString("email")));
                }
                if (ycstr.indexOf("xl") >= 0) {
                    wsheet.addCell(new Label(7, num, varOList.get(i).getString("xl"), wcfFC));
                } else {
                    wsheet.addCell(new Label(7, num, varOList.get(i).getString("xl")));
                }
                if (ycstr.indexOf("zy") >= 0) {
                    wsheet.addCell(new Label(8, num, varOList.get(i).getString("zy"), wcfFC));
                } else {
                    wsheet.addCell(new Label(8, num, varOList.get(i).getString("zy")));
                }


                wsheet.addCell(new Label(9, num, varOList.get(i).getString("ycstrs")));
                num++;
            }
            wbook.write();
            if (wbook != null) {
                wbook.close();
                wbook = null;
            }
            if (os != null) {
                os.close();
                os = null;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        return null;
    }

    //求职推荐
    @RequestMapping(value = "/exportpersonExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportpersonExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        JSONObject json = new JSONObject();
//        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
//            if (pd_token != null) {
        URLDecoder urlDecoder = new URLDecoder();
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        String sex = request.getParameter("sex");
        String xl = request.getParameter("xl");
        String isjob = request.getParameter("isjob");
        String jobtype = request.getParameter("jobtype");
        String jobname = request.getParameter("jobname");
        String qwxz = request.getParameter("qwxz");
        String lxtel = request.getParameter("lxtel");
        String starttime = request.getParameter("startTime");
        String endtime = request.getParameter("endTime");
        String tjstarttime = request.getParameter("tjstartTime");
        String tjendtime = request.getParameter("tjendTime");
        String name1 = name == null ? "" : urlDecoder.decode(name, "utf-8");
        String age1 = age == null ? "" : urlDecoder.decode(age, "utf-8");
        String sex1 = sex == null ? "" : urlDecoder.decode(sex, "utf-8");
        String xl1 = xl == null ? "" : urlDecoder.decode(xl, "utf-8");
        String isjob1 = isjob == null ? "" : urlDecoder.decode(isjob, "utf-8");
        String jobtype1 = jobtype == null ? "" : urlDecoder.decode(jobtype, "utf-8");
        String jobname1 = jobname == null ? "" : urlDecoder.decode(jobname, "utf-8");
        String qwxz1 = qwxz == null ? "" : urlDecoder.decode(qwxz, "utf-8");
        String lxtel1 = lxtel == null ? "" : urlDecoder.decode(lxtel, "utf-8");
        String starttime1 = starttime == null ? "" : urlDecoder.decode(starttime, "utf-8");
        String endtime1 = endtime == null ? "" : urlDecoder.decode(endtime, "utf-8");
        String tjstarttime1 = tjstarttime == null ? "" : urlDecoder.decode(tjstarttime, "utf-8");
        String tjendtime1 = tjendtime == null ? "" : urlDecoder.decode(tjendtime, "utf-8");
        pd.put("name", name1);
        if (StringUtils.isNotEmpty(age1)) {
            String[] split = age1.split("-");
            pd.put("agestart", split[0]);
            pd.put("ageend", split[1]);
        }
        pd.put("sex", sex1);
        pd.put("xl", xl1);
        pd.put("isjob", isjob1);
        pd.put("jobtype", jobtype1);
        pd.put("jobname", jobname1);
        pd.put("qwxz", qwxz1);
        pd.put("lxtel", lxtel1);
        pd.put("starttime", starttime1);
        pd.put("endtime", endtime1);
        pd.put("tjstarttime", tjstarttime1);
        pd.put("tjendtime", tjendtime1);

        List<PageData> clist = jobReferManager.getjob(pd);
        List<PageData> list = new ArrayList<>();
//        Page Jobpage = new Page();
//        Jobpage.setPd(pd);
//        Jobpage.setCurrentPage(1);
//        Jobpage.setShowCount(999999);
        List<PageData> referlist = jobReferManager.Personrefer(pd);

        System.out.println("clist" + clist);
        if (clist.size() > 0) {
            for (PageData pd1 : clist) {

                Page page = new Page();
                page.setCurrentPage(1);
                page.setShowCount(999999);
                PageData pageData = new PageData();


                if (referlist.size() >= 0) {
                    int size = 0;
                    for (PageData data : referlist) {
                        if (data.getString("uid").equals(pd1.getString("uid"))) {
                            size++;
                        }
                    }
                    pd1.put("referlistsize", size);
                }


                pageData.put("uid", pd1.getString("uid"));
                page.setPd(pageData);
                List<PageData> listJytj = companyService.listJytj(page);
                ArrayList<String> Jytj = new ArrayList<>();
                for (PageData data : listJytj) {
                    Jytj.add(data.getString("companyname"));
                }
                String Jytjstring = Jytj.toString().substring(1, Jytj.toString().length() - 1);
                pd1.put("Jytjstring", Jytjstring);
                pd1.put("listJytjsize", listJytj.size());
                list.add(pd1);
            }
        }


        System.out.println("list" + list);
        //创建excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        //创建sheet页
        HSSFSheet sheet = wb.createSheet("求职登记汇总");
        //创建标题行
        HSSFRow titleRow = sheet.createRow(0);
        titleRow.createCell((short) 0).setCellValue("序号");
        titleRow.createCell((short) 1).setCellValue("求职时间");
        titleRow.createCell((short) 2).setCellValue("人员类型");
        titleRow.createCell((short) 3).setCellValue("姓名");
        titleRow.createCell((short) 4).setCellValue("身份证号");
        titleRow.createCell((short) 5).setCellValue("性别");
        titleRow.createCell((short) 6).setCellValue("年龄");
        titleRow.createCell((short) 7).setCellValue("联系电话");
        titleRow.createCell((short) 8).setCellValue("学历");
        titleRow.createCell((short) 9).setCellValue("专业");
        titleRow.createCell((short) 10).setCellValue("求职岗位");
        titleRow.createCell((short) 11).setCellValue("期望薪资");
        titleRow.createCell((short) 12).setCellValue("推荐企业");
        titleRow.createCell((short) 13).setCellValue("推荐企业总量");
        titleRow.createCell((short) 14).setCellValue("已推荐企业量");

        if (list.size() > 0) {


            for (PageData pd1 : list) {

                HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                dataRow.createCell((short) 0).setCellValue(sheet.getLastRowNum());
                dataRow.createCell((short) 1).setCellValue(pd1.getString("czdate"));
                dataRow.createCell((short) 2).setCellValue(pd1.getString("catename"));
                dataRow.createCell((short) 3).setCellValue(pd1.getString("name"));
                dataRow.createCell((short) 4).setCellValue(pd1.getString("cardid"));
                String sex2 = pd1.getString("sex");
                if ("1".equals(sex2)) {
                    sex2 = "男";
                } else if ("2".equals(sex2)) {
                    sex2 = "女";
                } else {
                    sex2 = "";
                }
                dataRow.createCell((short) 5).setCellValue(sex2);

                dataRow.createCell((short) 6).setCellValue(pd1.getString("age"));
                dataRow.createCell((short) 7).setCellValue(pd1.getString("lxtel"));
                dataRow.createCell((short) 8).setCellValue(pd1.getString("xlname"));
                dataRow.createCell((short) 9).setCellValue(pd1.getString("zy"));
                dataRow.createCell((short) 10).setCellValue(pd1.getString("gwflname"));
                dataRow.createCell((short) 11).setCellValue(pd1.getString("qwxzname"));
                dataRow.createCell((short) 12).setCellValue(pd1.getString("Jytjstring"));
                dataRow.createCell((short) 13).setCellValue(Integer.parseInt(pd1.get("listJytjsize").toString()));
                dataRow.createCell((short) 14).setCellValue(Integer.parseInt(pd1.get("referlistsize").toString()));

            }


        }
        // 设置下载时客户端Excel的名称
        String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-jdlkry.xls";
        //设置下载的文件
        System.out.println(filename);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-disposition", "attachment;filename=" + filename);
        OutputStream ouputStream = response.getOutputStream();//打开流
        wb.write(ouputStream); //在excel内写入流
        ouputStream.flush();// 刷新流
        ouputStream.close();// 关闭流

//            } else {
//                json.put("success", "false");
//                json.put("msg", "登录超时，请重新登录");
//            }
//
//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();
    }

    //求职登记导入
    @RequestMapping(value = "/readpersonExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String readpersonExcel(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        PageData pd_area = new PageData();
        JSONObject json = new JSONObject();
//        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        System.out.println(request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        com.alibaba.fastjson.JSONObject object_verificate = new com.alibaba.fastjson.JSONObject();
        List<PageData> saveList = new ArrayList<PageData>();
        List<PageData> dicAll = consumerManager.getDicAll(pd);

        if (pd_token != null) {

            String ZXID = pd_token.getString("ZXID");
            if (null != file && !file.isEmpty()) {
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String pcbh = sdf.format(d);  //导入批次

                String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
                String fileName = FileUpload.fileUp(file, filePath, "sbbtryListexcel");                            //执行上传
                ImportExcelService importExcelService = new ImportExcelService();
                String[] importColumns = {"姓名", "身份证号码", "性别", "年龄", "联系电话", "学历", "专业", "籍贯", "就业状态", "邮箱", "岗位工种", "岗位性质", "期望薪资", "工作地点", "备注"};//设置字段名称15
                String[] importFields = {"name", "cardid", "sex", "age", "lxtel", "xl", "zy", "jg", "isjob", "email", "jobname", "jobtype", "qwxz", "jobaddr", "remark"};  //字段
                // 1:IdCardVerification 2:DateVerification 3:TelVerification 4:DictVerification 5:ValueVerification 6:SqlVerification

                String[] importYzColumns = {"", "1", "", "", "", "6", "", "", "", "", "6", "6", "6", "", ""};
                int[] importFiledNums = {100, 20, 20, 20, 20, 100, 100, 50, 100, 0, 1, 0, 0, 0, 0}; //0表示无限制
                int[] importFiledNull = {1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0}; //验证字段是否为空：0表示无限制，1表示不能为空
                String[] importTjColumns = {"", "", "isChinese", "isNumberic", "isNumberic", "", "", "", "", "", "", "", "", "", ""};  //isNumberic 是否数字;isEnglish 是否英文 ；isChinese 是否中文
                String[] importValueColumns = {"", "", "", "", "", "select * from sys_dictionaries where  name = #{name}", "", "", "", "", "select * from tesp_jobtype where  gwflname = #{name}", "select * from sys_dictionaries where  name = #{name}", "select * from sys_dictionaries where  name = #{name}", "", ""};

                object_verificate.put("importColumns", importColumns);
                object_verificate.put("importFields", importFields);
                object_verificate.put("importYzColumns", importYzColumns);
                object_verificate.put("importFiledNums", importFiledNums);
                object_verificate.put("importValueColumns", importValueColumns);
                object_verificate.put("importTjColumns", importTjColumns);
                object_verificate.put("importFiledNull", importFiledNull);

                String openFilename = filePath + fileName;
                com.alibaba.fastjson.JSONObject jsonObject = importExcelService.importExcel(openFilename, request, object_verificate);


                List<PageData> listPd = ((List<PageData>) jsonObject.get("rightList"));
                System.out.println(listPd);
                for (int i = 0; i < listPd.size(); i++) {
                    String uid = "";
                    pd = new PageData();
                    pd_area = new PageData();
                    List<PageData> findperson = jobReferManager.findperson(pd);
                    for (PageData pageData : findperson) {
                        if ((listPd.get(i).getString("name").equals(pageData.getString("name")) && listPd.get(i).getString("lxtel").equals(pageData.getString("lxtel"))) || listPd.get(i).getString("cardid").equals(pageData.getString("cardid"))) {
                            uid = pageData.getString("uid");
                            pd = pageData;
                        }
                    }

                    pd.put("name", listPd.get(i).getString("name").trim());
                    pd.put("cardid", listPd.get(i).getString("cardid").trim());
                    pd.put("sex", listPd.get(i).getString("sex").trim());
                    pd.put("lxtel", listPd.get(i).getString("lxtel").trim());
                    pd.put("tel", listPd.get(i).getString("lxtel").trim());
                    for (PageData pageData : dicAll) {
                        if (pageData.getString("NAME").equals(listPd.get(i).getString("xl").trim())) {
                            pd.put("xl", pageData.getString("DICTIONARIES_ID"));
                        }
                    }
                    pd.put("zy", listPd.get(i).getString("zy").trim());
                    pd.put("jg", listPd.get(i).getString("jg").trim());
                    String isjob = listPd.get(i).getString("isjob").trim();
                    if ("0".equals(isjob))
                        isjob = "失业";
                    if ("1".equals(isjob))
                        isjob = "就业";
                    if ("2".equals(isjob))
                        isjob = "无意向就业";
                    if ("3".equals(isjob))
                        isjob = "灵活就业";
                    if ("4".equals(isjob))
                        isjob = "自主创业";
                    pd.put("isjob", isjob);
                    pd.put("email", listPd.get(i).getString("email").trim());
                    PageData pd2 = new PageData();
                    String jobname = listPd.get(i).getString("jobname").trim();
                    pd2.put("gwflname", jobname);
                    PageData getjobtype = consumerManager.getjobtype(pd2);

                    if (getjobtype != null) {
                        jobname = getjobtype.get("code").toString();
                    } else {
                        jobname = "暂无";
                    }
                    pd.put("jobname", jobname);
                    for (PageData pageData : dicAll) {
                        if (pageData.getString("NAME").equals(listPd.get(i).getString("jobtype").trim())) {
                            pd.put("jobtype", pageData.getString("DICTIONARIES_ID"));
                        }
                    }
                    for (PageData pageData : dicAll) {
                        if (pageData.getString("NAME").equals(listPd.get(i).getString("qwxz").trim())) {
                            pd.put("qwxz", pageData.getString("DICTIONARIES_ID"));
                        }
                    }

                    pd.put("jobaddr", listPd.get(i).getString("jobaddr").trim());
                    pd.put("remark", listPd.get(i).getString("remark").trim());

                    pd.put("czman", ZXID);
                    pd.put("uid", getUUID32());
                    pd.put("isimpot", "1");

                    String cardid = listPd.get(i).getString("cardid").trim();
                    if (StringUtils.isNotEmpty(cardid)) {
                        JSONObject cardidjson = JSON.parseObject(JSON.toJSON(identityCard(cardid)).toString());
                        pd.put("age", cardidjson.getString("age"));
                        pd.put("sex", cardidjson.getString("sex"));
                    }


                    PageData savePd = new PageData();
                    savePd.putAll(pd);
                    saveList.add(savePd);

                    if ("".equals(uid) || uid.equals(null)) {
                        pd.put("uid", getUUID32());
                        System.out.println("新增");
                        consumerManager.savePerson(pd);
                        jobReferManager.insertjobreg(pd);
                    } else {
                        pd.put("uid", uid);
                        consumerManager.editPerson(pd);
                        List<PageData> getjob = jobReferManager.getjob(pd);
                        String id = "";
                        for (PageData pageData : getjob) {
                            if (pd.getString("jobname").equals(pageData.getString("jobname")) && uid.equals(pageData.getString("uid"))) {
                                id = pageData.getString("id");
                                pd = pageData;
                            }
                        }
                        if ("".equals(id) || id.equals(null)) {
                            jobReferManager.insertjobreg(pd);
                        } else {
                            jobReferManager.updatejobreg(pd);
                        }
                    }

                }

                List<PageData> errorList = ((List<PageData>) jsonObject.get("errorList")); //导入错误的信息
                if (errorList.size() > 0) {
//                    System.out.println(errorList);
                    for (int i = 0; i < errorList.size(); i++) {
                        pd = errorList.get(i);
                        pd.put("pcbh", pcbh);
                        pd.put("czman", pd_token.getString("ID"));
                        //System.out.println(errorList.get(i).getString("ycstr")+"ddd");
                        pd.put("uid", getUUID32());
                        pd.put("cate", "95df4fa459264d77b508c963b4ebbe21");
                        pd.put("czdate", getTime());
                        consumerManager.savePerson_error(pd);
                        consumerManager.insertjobregerror(pd);
                    }
                }
                json.put("success", "true");
                if (errorList.size() > 0) {
                    json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条,失败记录：" + errorList.size() + "条");
                    json.put("href", "api/excelperson_error?pcbh=" + pcbh + "&tokenid=" + request.getParameter("tokenid"));
                } else {
                    json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条");
                }

            }

        } else {
            json.put("success", "false");
            json.put("msg", "登录超时，请重新登录");
        }
//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();

    }

    /*
     * 异常信息下载
     */
    @RequestMapping(value = "/excelperson_error", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String excelperson_error(HttpServletResponse response) throws Exception {
//        response.addHeader("Access-Control-Allow-Origin", "*");
        Page page = new Page();
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        net.sf.json.JSONObject object = new net.sf.json.JSONObject();
        pd = this.getPageData();

        pd.put("TOKENID", pd.getString("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd);
        if (pd_token == null) {
            object.put("success", "false");
            object.put("msg", "登录超时请重新登录");
            return object.toString();
        }

        pd.put("pcbh", pd.getString("pcbh"));
        pd.put("tables", "tesp_person_error");
        pd.put("tables2", "tesp_jobreg_error");
        page.setShowCount(9999999);
        page.setPd(pd);


        List<PageData> varOList = jobReferManager.findpersonlistPage(page);

        try {
            Date date = new Date();
            String formatFileName = Tools.date2Str(date, "yyyyMMddHHmmss");
            OutputStream os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + formatFileName + ".xls");
            response.setContentType("application/msexcel");

            WritableWorkbook wbook = Workbook.createWorkbook(os);
            WritableSheet wsheet = wbook.createSheet(formatFileName, 0);

            WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat wcfFC = new WritableCellFormat(wfont);
            wcfFC.setBackground(Colour.YELLOW);

            wsheet.addCell(new Label(0, 0, "姓名"));
            wsheet.addCell(new Label(1, 0, "身份证号"));
            wsheet.addCell(new Label(2, 0, "性别"));
            wsheet.addCell(new Label(3, 0, "年龄"));
            wsheet.addCell(new Label(4, 0, "联系电话"));
            wsheet.addCell(new Label(5, 0, "学历"));
            wsheet.addCell(new Label(6, 0, "专业"));
            wsheet.addCell(new Label(7, 0, "籍贯"));
            wsheet.addCell(new Label(8, 0, "就业状态"));
            wsheet.addCell(new Label(9, 0, "邮箱"));
            wsheet.addCell(new Label(10, 0, "岗位工种"));
            wsheet.addCell(new Label(11, 0, "岗位性质"));
            wsheet.addCell(new Label(12, 0, "期望薪资"));
            wsheet.addCell(new Label(13, 0, "工作地点"));
            wsheet.addCell(new Label(14, 0, "备注"));
            wsheet.addCell(new Label(15, 0, "异常日志"));
            int num = 1;
            String ycstr = "";
            for (int i = 0; i < varOList.size(); i++) {

                ycstr = String.valueOf(varOList.get(i).getString("ycstr"));
                if (ycstr.indexOf("name") >= 0) {
                    wsheet.addCell(new Label(0, num, varOList.get(i).getString("name"), wcfFC));
                } else {
                    wsheet.addCell(new Label(0, num, varOList.get(i).getString("name")));
                }
                if (ycstr.indexOf("cardid") >= 0) {
                    wsheet.addCell(new Label(1, num, varOList.get(i).getString("cardid"), wcfFC));
                } else {
                    wsheet.addCell(new Label(1, num, varOList.get(i).getString("cardid")));
                }
                if (ycstr.indexOf("sex") >= 0) {
                    wsheet.addCell(new Label(2, num, varOList.get(i).getString("sex"), wcfFC));
                } else {
                    wsheet.addCell(new Label(2, num, varOList.get(i).getString("sex")));
                }

                if (ycstr.indexOf("age") >= 0) {
                    wsheet.addCell(new Label(3, num, varOList.get(i).getString("age"), wcfFC));
                } else {
                    wsheet.addCell(new Label(3, num, varOList.get(i).getString("age")));
                }
                if (ycstr.indexOf("lxtel") >= 0) {
                    wsheet.addCell(new Label(4, num, varOList.get(i).getString("lxtel"), wcfFC));
                } else {
                    wsheet.addCell(new Label(4, num, varOList.get(i).getString("lxtel")));
                }
                if (ycstr.indexOf("xl") >= 0) {
                    wsheet.addCell(new Label(5, num, varOList.get(i).getString("xl"), wcfFC));
                } else {
                    wsheet.addCell(new Label(5, num, varOList.get(i).getString("xl")));
                }
                if (ycstr.indexOf("zy") >= 0) {
                    wsheet.addCell(new Label(6, num, varOList.get(i).getString("zy"), wcfFC));
                } else {
                    wsheet.addCell(new Label(6, num, varOList.get(i).getString("zy")));
                }
                if (ycstr.indexOf("jg") >= 0) {
                    wsheet.addCell(new Label(7, num, varOList.get(i).getString("jg"), wcfFC));
                } else {
                    wsheet.addCell(new Label(7, num, varOList.get(i).getString("jg")));
                }
                if (ycstr.indexOf("isjob") >= 0) {
                    wsheet.addCell(new Label(8, num, varOList.get(i).getString("isjob"), wcfFC));
                } else {
                    wsheet.addCell(new Label(8, num, varOList.get(i).getString("isjob")));
                }
                if (ycstr.indexOf("email") >= 0) {
                    wsheet.addCell(new Label(9, num, varOList.get(i).getString("email"), wcfFC));
                } else {
                    wsheet.addCell(new Label(9, num, varOList.get(i).getString("email")));
                }
                if (ycstr.indexOf("jobname") >= 0) {
                    wsheet.addCell(new Label(10, num, varOList.get(i).getString("jobname"), wcfFC));
                } else {
                    wsheet.addCell(new Label(10, num, varOList.get(i).getString("jobname")));
                }
                if (ycstr.indexOf("jobtype") >= 0) {
                    wsheet.addCell(new Label(11, num, varOList.get(i).getString("jobtype"), wcfFC));
                } else {
                    wsheet.addCell(new Label(11, num, varOList.get(i).getString("jobtype")));
                }
                if (ycstr.indexOf("qwxz") >= 0) {
                    wsheet.addCell(new Label(12, num, varOList.get(i).getString("qwxz"), wcfFC));
                } else {
                    wsheet.addCell(new Label(12, num, varOList.get(i).getString("qwxz")));
                }
                if (ycstr.indexOf("jobaddr") >= 0) {
                    wsheet.addCell(new Label(13, num, varOList.get(i).getString("jobaddr"), wcfFC));
                } else {
                    wsheet.addCell(new Label(13, num, varOList.get(i).getString("jobaddr")));
                }
                if (ycstr.indexOf("remark") >= 0) {
                    wsheet.addCell(new Label(14, num, varOList.get(i).getString("remark"), wcfFC));
                } else {
                    wsheet.addCell(new Label(14, num, varOList.get(i).getString("remark")));
                }
                wsheet.addCell(new Label(15, num, varOList.get(i).getString("ycstrs")));
                num++;
            }
            wbook.write();
            if (wbook != null) {
                wbook.close();
                wbook = null;
            }
            if (os != null) {
                os.close();
                os = null;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        return null;
    }

    @RequestMapping(value = "/downpersonExcelMode", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String downLoadpersonExcelMode(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {

                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("就业登记");
                //创建标题行

                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("姓名");
                titleRow.createCell((short) 1).setCellValue("身份证号码");
                titleRow.createCell((short) 2).setCellValue("性别");
                titleRow.createCell((short) 3).setCellValue("年龄");
                titleRow.createCell((short) 4).setCellValue("联系电话");
                titleRow.createCell((short) 5).setCellValue("学历");
                titleRow.createCell((short) 6).setCellValue("专业");
                titleRow.createCell((short) 7).setCellValue("籍贯");
                titleRow.createCell((short) 8).setCellValue("就业状态");
                titleRow.createCell((short) 9).setCellValue("邮箱");
                titleRow.createCell((short) 10).setCellValue("岗位工种");
                titleRow.createCell((short) 11).setCellValue("岗位性质");
                titleRow.createCell((short) 12).setCellValue("期望薪资");
                titleRow.createCell((short) 13).setCellValue("工作地点");
                titleRow.createCell((short) 14).setCellValue("备注");


                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-Person.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    //通讯录导入
    @RequestMapping(value = "/readaddrExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String readaddrExcel(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            System.out.println(request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                System.out.println(file);
                if (null != file && !file.isEmpty()) {
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
                    String fileName = FileUpload.fileUp(file, filePath, "Excel");                            //执行上传
                    List<PageData> listPd = (List) ObjectExcelRead2.readExcel(filePath, fileName, 1, 0, 0);        //执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
                    /*存入数据库操作======================================*/
                    for (int i = 0; i < listPd.size(); i++) {
                        pd.put("office", listPd.get(i).getString("var1"));
                        pd.put("duty", listPd.get(i).getString("var2"));
                        pd.put("name", listPd.get(i).getString("var3"));
                        pd.put("tel1", listPd.get(i).getString("var4"));
                        pd.put("tel2", listPd.get(i).getString("var5"));
                        jobReferManager.insertaddr(pd);
                    }
                    json.put("success", "true");
                }

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }
        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    @RequestMapping(value = "/exportaddrExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportaddrExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                PageData pd = new PageData();
                Page page = new Page();
                URLDecoder urlDecoder = new URLDecoder();
                String starttime = request.getParameter("starttime");
                String endtime = request.getParameter("endtime");
                String pageIndex = "0";
                String pageSize = "999999999";
                String starttime1 = starttime == null ? "" : urlDecoder.decode(starttime, "utf-8");
                String endtime1 = endtime == null ? "" : urlDecoder.decode(endtime, "utf-8");
                String pageIndex1 = pageIndex == null ? "" : urlDecoder.decode(pageIndex, "utf-8");
                String pageSize1 = pageSize == null ? "" : urlDecoder.decode(pageSize, "utf-8");
                pd.put("starttime", starttime1);
                pd.put("endtime", endtime1);
                pd.put("pageIndex", pageIndex1);
                pd.put("pageSize", pageSize1);
                page.setPd(pd);
                System.out.println(pd);
                List<PageData> clist = jobReferManager.findaddr(pd);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("通讯录");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("办公室");
                titleRow.createCell((short) 2).setCellValue("职责范围");
                titleRow.createCell((short) 3).setCellValue("姓名");
                titleRow.createCell((short) 4).setCellValue("办公电话1");
                titleRow.createCell((short) 5).setCellValue("办公电话2");
                System.out.println(clist);
                if (clist.size() > 0) {


                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        dataRow.createCell((short) 1).setCellValue(pd1.getString("office"));
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("duty"));
                        dataRow.createCell((short) 3).setCellValue(pd1.getString("name"));
                        dataRow.createCell((short) 4).setCellValue(pd1.getString("tel1"));
                        dataRow.createCell((short) 5).setCellValue(pd1.getString("tel2"));
                    }

                }
                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-txl.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    //知识库导入
    @RequestMapping(value = "/readdocExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String readdocExcel(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            String createman = request.getParameter("czman");
            String doctype_id = request.getParameter("doctype_id");
            System.out.println(request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            System.out.println("执行了");
            if (pd_token != null) {
                System.out.println(file);
                if (null != file && !file.isEmpty()) {
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
                    String fileName = FileUpload.fileUp(file, filePath, "Excel");                            //执行上传
                    List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 1, 0, 0);        //执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet

                    /*检查字段操作======================================*/
                    boolean check = true;
                    String str = "";
                    for (int i = 0; i < listPd.size(); i++) {
                        String var0 = listPd.get(i).getString("var0");
                        String var1 = listPd.get(i).getString("var1");
                        String var2 = listPd.get(i).getString("var2");
                        String var3 = listPd.get(i).getString("var3");
                        if ("".equals(var0) || var0 == null) {
                            check = false;
                            str = str + "知识标题不能为空";
                            break;
                        }
                        if ("".equals(var1) || var1 == null) {
                            check = false;
                            str = str + "知识内容不能为空";
                            break;
                        }
                        if ("".equals(var2) || var2 == null) {
                            check = false;
                            str = str + "有效期不能为空";
                            break;
                        }
                        if ("".equals(var3) || var3 == null) {
                            check = false;
                            str = str + "是否热点不能为空";
                            break;
                        }
                        if (doctype_id == null) {
                            check = false;
                            str = str + "请先选择知识库分类";
                            break;
                        }
                    }

                    if (check == true) {
                        /*存入数据库操作======================================*/
                        for (int i = 0; i < listPd.size(); i++) {
                            pd.put("doctype_id", doctype_id);
                            pd.put("doctitle", listPd.get(i).getString("var0"));
                            pd.put("doccont", listPd.get(i).getString("var1"));
                            pd.put("validate", listPd.get(i).getString("var2"));
                            String var3 = listPd.get(i).getString("var3");
                            String ishot = var3.equals("是") ? "1" : "0";
                            pd.put("ishot", ishot);
                            pd.put("createman", createman);
                            pd.put("createdate", getTime());
                            jobReferManager.importdoc(pd);
                        }
                        json.put("success", "true");
                    } else if (check == false) {
                        json.put("msg", str);
                    }

                }

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }
        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    //短息记录导出
    @RequestMapping(value = "/exportmsglogExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public void exportmsglogExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                PageData pd = new PageData();
                Page page = new Page();
                URLDecoder urlDecoder = new URLDecoder();
                String starttime = request.getParameter("starttime");
                String endtime = request.getParameter("endtime");
                String sendman = request.getParameter("sendman");
                String phone = request.getParameter("phone");
                String acceptman = request.getParameter("acceptman");
                String keywords = request.getParameter("keywords");
                String starttime1 = starttime == null ? "" : urlDecoder.decode(starttime, "utf-8");
                String endtime1 = endtime == null ? "" : urlDecoder.decode(endtime, "utf-8");
                String sendman1 = sendman == null ? "" : urlDecoder.decode(sendman, "utf-8");
                String phone1 = phone == null ? "" : urlDecoder.decode(phone, "utf-8");
                String acceptman1 = acceptman == null ? "" : urlDecoder.decode(acceptman, "utf-8");
                String keywords1 = keywords == null ? "" : urlDecoder.decode(keywords, "utf-8");
                pd.put("starttime", starttime1);
                pd.put("endtime", endtime1);
                pd.put("sendman", sendman1);
                pd.put("phone", phone1);
                pd.put("acceptman", acceptman1);
                pd.put("keywords", keywords1);
                pd.put("pageIndex", 0);
                pd.put("pageSize", 9999);
                page.setPd(pd);
                System.out.println(pd);
                List<PageData> clist = msgTempManager.findMsgLogAll(pd);
                System.out.println(clist);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("短信发送记录");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("发送人");
                titleRow.createCell((short) 2).setCellValue("短信模板");
                titleRow.createCell((short) 3).setCellValue("联系方式");
                titleRow.createCell((short) 4).setCellValue("接受人");
                titleRow.createCell((short) 5).setCellValue("发送时间");
                titleRow.createCell((short) 6).setCellValue("发送方式");
                titleRow.createCell((short) 7).setCellValue("短信内容");
//                titleRow.createCell((short) 5).setCellValue("客户名称");
//                titleRow.createCell((short) 6).setCellValue("客户类别");

                if (clist.size() > 0) {


                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        dataRow.createCell((short) 1).setCellValue(pd1.getString("sendman"));
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("tempname"));
                        dataRow.createCell((short) 3).setCellValue(pd1.getString("phone"));
                        dataRow.createCell((short) 4).setCellValue(pd1.getString("acceptman"));
                        dataRow.createCell((short) 5).setCellValue(pd1.getString("sendtime"));
                        dataRow.createCell((short) 6).setCellValue(pd1.getString("way"));
                        dataRow.createCell((short) 7).setCellValue(pd1.getString("content"));
//                        dataRow.createCell((short) 5).setCellValue(pd1.getString("clientname"));
//                        dataRow.createCell((short) 6).setCellValue(pd1.getString("clientcate"));
                    }

                }
                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-Messagelog.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }


        } catch (Exception e) {
            json.put("success", "false");
        }

    }

    //导出培训信息
    @RequestMapping(value = "/exportTrainExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportTrainExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                PageData pd = new PageData();
                Page page = new Page();
                URLDecoder urlDecoder = new URLDecoder();
                String name = request.getParameter("name");
                System.out.println(name);
                String keywords = request.getParameter("keywords");
                System.out.println(keywords);
                name = name == null ? "" : urlDecoder.decode(name, "utf-8");
                keywords = keywords == null ? "" : urlDecoder.decode(keywords, "utf-8");
                pd.put("name", name);
                pd.put("keywords", keywords);
                page.setCurrentPage(1);
                page.setShowCount(99999999);
                page.setPd(pd);
                System.out.println(pd);
                List<PageData> clist = trainInfoService.findAlllistPage(page);
                HSSFWorkbook wb = new HSSFWorkbook();
                HSSFSheet sheet = wb.createSheet("培训信息表");
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("培训机构");
                titleRow.createCell((short) 2).setCellValue("办学班类型");
                titleRow.createCell((short) 3).setCellValue("地址");
                titleRow.createCell((short) 4).setCellValue("负责人");
                titleRow.createCell((short) 5).setCellValue("联系方式1");
                titleRow.createCell((short) 6).setCellValue("联系方式2");
                titleRow.createCell((short) 7).setCellValue("备注");
                System.out.println(clist);
                if (clist.size() > 0) {
                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        dataRow.createCell((short) 1).setCellValue(pd1.getString("name"));
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("stat"));
                        dataRow.createCell((short) 3).setCellValue(pd1.getString("addr"));
                        dataRow.createCell((short) 4).setCellValue(pd1.getString("principal"));
                        dataRow.createCell((short) 5).setCellValue(pd1.getString("tel"));
                        dataRow.createCell((short) 6).setCellValue(pd1.getString("tel2"));
                        dataRow.createCell((short) 7).setCellValue(pd1.getString("remark"));
                    }

                }
                String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-trainInfo.xls";
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();
                wb.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();
                json.put("success", "true");
                json.put("msg", "导出成功");
            } else {
                json.put("success", "false");
                json.put("msg", "超时，请重新登陆");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }


    @RequestMapping(value = "/exportOtherPersonExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportOtherPersonExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        if (pd_token != null) {
            PageData pd = new PageData();
            Page page = new Page();
            URLDecoder urlDecoder = new URLDecoder();
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            String result = request.getParameter("result");
            String keywords = request.getParameter("keywords");
//            if (endTime != null && !"".equals(endTime)) {
//                endTime = TimeHandle.endTimeHeandle(endTime);
//                json.put("endTime", endTime);
//            }
            String isjob = request.getParameter("isjob");
            startTime = startTime == null ? "" : urlDecoder.decode(startTime, "utf-8");
            endTime = endTime == null ? "" : urlDecoder.decode(endTime, "utf-8");
            isjob = isjob == null ? "" : urlDecoder.decode(isjob, "utf-8");
            result = result == null ? "" : urlDecoder.decode(result, "utf-8");
            keywords = keywords == null ? "" : urlDecoder.decode(keywords, "utf-8");

            page.setShowCount(99999999);
            page.setCurrentPage(1);
            pd.put("startTime", startTime);
            pd.put("endTime", endTime);
            pd.put("isjob", isjob);
            pd.put("result", result);
            pd.put("keywords", keywords);
            page.setPd(pd);
            System.out.println(pd);
            List<PageData> clist = consumerManager.findotherPersonexport(pd);

            System.out.println(clist);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("其他人员信息表");
            HSSFRow titleRow = sheet.createRow(0);
            titleRow.createCell((short) 0).setCellValue("序号");
            titleRow.createCell((short) 1).setCellValue("最新服务时间");
            titleRow.createCell((short) 2).setCellValue("服务次数");
            titleRow.createCell((short) 3).setCellValue("籍贯");
            titleRow.createCell((short) 4).setCellValue("姓名");
            titleRow.createCell((short) 5).setCellValue("联系电话");
            titleRow.createCell((short) 6).setCellValue("证件号码");
            titleRow.createCell((short) 7).setCellValue("年龄");
            titleRow.createCell((short) 8).setCellValue("性别");
            titleRow.createCell((short) 9).setCellValue("就业状态");
            titleRow.createCell((short) 10).setCellValue("情况记录");
            titleRow.createCell((short) 11).setCellValue("求职岗位");
            titleRow.createCell((short) 12).setCellValue("推荐企业");
            titleRow.createCell((short) 13).setCellValue("就业岗位");
            titleRow.createCell((short) 14).setCellValue("就业单位");
            titleRow.createCell((short) 15).setCellValue("推荐政策");
            titleRow.createCell((short) 16).setCellValue("推荐技能");
            titleRow.createCell((short) 17).setCellValue("通话状态");
            titleRow.createCell((short) 18).setCellValue("未接通原因");
            titleRow.createCell((short) 19).setCellValue("推荐企业数量");

            System.out.println(clist);
            if (clist.size() > 0) {


                int count = 1;
                for (PageData pd1 : clist) {
                    HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    dataRow.createCell((short) 0).setCellValue(count++);
                    dataRow.createCell((short) 1).setCellValue(pd1.getString("newServiceTime"));
                    dataRow.createCell((short) 2).setCellValue(pd1.get("serviceCount").toString());
                    dataRow.createCell((short) 3).setCellValue(pd1.getString("jgName"));
                    dataRow.createCell((short) 4).setCellValue(pd1.getString("name"));
                    dataRow.createCell((short) 5).setCellValue(pd1.getString("lxtel"));
                    dataRow.createCell((short) 6).setCellValue(pd1.getString("cardid"));
                    dataRow.createCell((short) 7).setCellValue(pd1.getString("age"));
                    String sex = pd1.getString("sex");
                    if ("1".equals(sex)) {
                        dataRow.createCell((short) 8).setCellValue("男");
                    } else if ("2".equals(sex)) {
                        dataRow.createCell((short) 8).setCellValue("女");
                    }
                    String isjob1 = pd1.getString("isjob");
                    if ("0".equals(isjob1))
                        dataRow.createCell((short) 9).setCellValue("失业");
                    if ("1".equals(isjob1))
                        dataRow.createCell((short) 9).setCellValue("就业");
                    if ("2".equals(isjob1))
                        dataRow.createCell((short) 9).setCellValue("无意向就业");
                    if ("3".equals(isjob1))
                        dataRow.createCell((short) 9).setCellValue("灵活就业");
                    if ("4".equals(isjob1))
                        dataRow.createCell((short) 9).setCellValue("自主创业");

                    dataRow.createCell((short) 10).setCellValue(pd1.getString("cate"));
                    dataRow.createCell((short) 11).setCellValue(pd1.getString("qzgw"));
                    dataRow.createCell((short) 12).setCellValue(pd1.getString("companyName"));
                    dataRow.createCell((short) 13).setCellValue(pd1.getString("isjobwork"));
                    dataRow.createCell((short) 14).setCellValue(pd1.getString("jobunit"));
                    dataRow.createCell((short) 15).setCellValue(pd1.getString("zczxname"));
                    dataRow.createCell((short) 16).setCellValue(pd1.getString("trainreco"));
                    String isjt = pd1.getString("isjt");
                    if ("0".equals(isjt)) {
                        dataRow.createCell((short) 17).setCellValue("未接通");
                    } else if ("1".equals(isjt)) {
                        dataRow.createCell((short) 17).setCellValue("已接通");
                    } else {
                        dataRow.createCell((short) 17).setCellValue("未回访");
                    }
                    dataRow.createCell((short) 18).setCellValue(pd1.getString("unconnet"));
                    dataRow.createCell((short) 19).setCellValue(pd1.get("companyNum").toString());
                }

            }
            String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-other.xls";
            System.out.println(filename);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            OutputStream ouputStream = response.getOutputStream();//???
            wb.write(ouputStream); //?excel????
            ouputStream.flush();// ???
            ouputStream.close();// ???
            json.put("success", "true");
            json.put("msg", "导入成功");
        } else {
            json.put("success", "false");
            json.put("msg", "超时，请重新登陆");
        }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    //政策咨询导出
    @RequestMapping(value = "/exportPolicyExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportPolicyExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
//        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        if (pd_token != null) {
            //clazz区分汇总还是详情，detail为详情，summary为汇总
            String clazz = request.getParameter("clazz");
            if (null == clazz || "".equals(clazz)) {
                json.put("success", "false");
                json.put("msg", "ClazzIsNull");
                return json.toString();

            } else if (!"detail".equals(clazz) && !"summary".equals(clazz)) {
                json.put("success", "false");
                json.put("msg", "ClazzIsError,MustIsDetailOrSummary");
                return json.toString();
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            PageData pd = new PageData();
            Page page = new Page();
            URLDecoder urlDecoder = new URLDecoder();
            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");
            if (endTime != null && !"".equals(endTime)) {
                endTime = TimeHandle.endTimeHeandle(endTime);
                json.put("endTime", endTime);
            }
            String zczxname = request.getParameter("zczxname");
            String isjd = request.getParameter("isjd");
            String name = request.getParameter("name");
            String zczxtype = request.getParameter("zczxtype");
            String type = request.getParameter("type");
            startTime = startTime == null ? "" : urlDecoder.decode(startTime, "utf-8");
            type = type == null ? "" : urlDecoder.decode(type, "utf-8");
            zczxtype = zczxtype == null ? "" : urlDecoder.decode(zczxtype, "utf-8");
            endTime = endTime == null ? "" : urlDecoder.decode(endTime, "utf-8");
            isjd = isjd == null ? "" : urlDecoder.decode(isjd, "utf-8");
            name = name == null ? "" : urlDecoder.decode(name, "utf-8");
            zczxname = zczxname == null ? "" : urlDecoder.decode(zczxname, "utf-8");
            page.setShowCount(99999999);
            page.setCurrentPage(1);
            pd.put("startTime", startTime);
            pd.put("endTime", endTime);
            pd.put("isjd", isjd);
            pd.put("zczxtype", zczxtype);
            pd.put("name", name);
            pd.put("type", type);
            pd.put("zczxname", zczxname);
            page.setPd(pd);
            System.out.println(pd);
            List<PageData> clist = null;
            boolean flag = false;
            if ("detail".equals(clazz)) {
                clist = policyService.findAlllistPage(page);
                flag = true;
            } else {
                clist = policyService.findSummarylistPage(page);
            }

            HSSFWorkbook wb = new HSSFWorkbook();

            HSSFSheet sheet = wb.createSheet("政策咨询记录");
            int i = 0;
            if (flag == false) {
                i = 1;
            }
            HSSFRow titleRow_1 = sheet.createRow((short) 0);
            Region region = new Region((short) 0, (short) 0, (short) 1, (short) (7 + i + i));
            sheet.addMergedRegion(region);
            HSSFCell cell = titleRow_1.createCell((short) 0);
            cell.setCellValue("24小时就业服务平台政策服务报表");
            HSSFCellStyle titleStyle = wb.createCellStyle();
            titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
            titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
            titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
            titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
            titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            HSSFFont titleFont = wb.createFont();
            titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
            titleFont.setFontHeightInPoints((short) 20);
            titleStyle.setFont(titleFont);
            cell.setCellStyle(titleStyle);
            HSSFRow titleRow = sheet.createRow(2);
            titleRow.createCell((short) 0).setCellValue("序号");
            if (flag == false) {
                titleRow.createCell((short) 1).setCellValue("服务次数");
            }
            titleRow.createCell((short) (1 + i)).setCellValue("咨询时间");
            titleRow.createCell((short) (2 + i)).setCellValue("政策名称");
            titleRow.createCell((short) (3 + i)).setCellValue("服务类别");
            titleRow.createCell((short) (4 + i)).setCellValue("咨询结果");
            titleRow.createCell((short) (5 + i)).setCellValue("客户名称");
            titleRow.createCell((short) (6 + i)).setCellValue("客户类型");
            titleRow.createCell((short) (7 + i)).setCellValue("咨询内容");

            if (flag == false) {
                titleRow.createCell((short) (8 + i)).setCellValue("政策推荐数量");
            } else {
                titleRow.createCell((short) (8 + i)).setCellValue("备注");
            }

            if (clist.size() > 0) {
                int count = 1;
                for (PageData pd1 : clist) {
                    HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    dataRow.createCell((short) 0).setCellValue(count);
                    count++;
                    if (flag == false) {
                        dataRow.createCell((short) 1).setCellValue(String.valueOf(pd1.get("zczxCount")));
                    }
                    dataRow.createCell((short) (1 + i)).setCellValue(df.format(df.parse(pd1.getString("czdate"))));
                    //分解政策名称并且编号
                    dataRow.createCell((short) (2 + i)).setCellValue(countHandle(pd1, flag, "zczxname"));
                    //分解政策类型并且编号
                    dataRow.createCell((short) (3 + i)).setCellValue(countHandle(pd1, flag, "zczxtypename"));
                    isjd = pd1.getString("isjd");
                    if ("0".equals(isjd))
                        dataRow.createCell((short) (4 + i)).setCellValue("未解答");
                    if ("1".equals(isjd))
                        dataRow.createCell((short) (4 + i)).setCellValue("已解答");
                    if ("".equals(isjd) || isjd == null)
                        dataRow.createCell((short) (4 + i)).setCellValue("");
                    dataRow.createCell((short) (5 + i)).setCellValue(pd1.getString("name"));
                    dataRow.createCell((short) (6 + i)).setCellValue(pd1.getString("typename"));
                    dataRow.createCell((short) (7 + i)).setCellValue(pd1.getString("fwcont"));
                    if (flag == false) {
                        dataRow.createCell((short) (8 + i)).setCellValue(String.valueOf(pd1.get("zctjCount")));
                    } else {
                        dataRow.createCell((short) (8 + i)).setCellValue(String.valueOf(pd1.get("remark")));
                    }

                }

            }
            String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-policy.xls";
            System.out.println(filename);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            OutputStream ouputStream = response.getOutputStream();
            wb.write(ouputStream);
            ouputStream.flush();
            ouputStream.close();
            json.put("success", "true");
            json.put("msg", "导出成功");
        } else {
            json.put("success", "false");
            json.put("msg", "超时，请重新登陆");
        }

//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();
    }


    @RequestMapping(value = "/exportSignupExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportSignupExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                PageData pd = new PageData();
                Page page = new Page();
                URLDecoder urlDecoder = new URLDecoder();
                String startTime = request.getParameter("startTime");
                String endTime = request.getParameter("endTime");
                if (endTime != null && !"".equals(endTime)) {
                    endTime = TimeHandle.endTimeHeandle(endTime);
                    json.put("endTime", endTime);
                }
                String isjob = request.getParameter("isjob");
                String name = request.getParameter("name");
                startTime = startTime == null ? "" : urlDecoder.decode(startTime, "utf-8");
                endTime = endTime == null ? "" : urlDecoder.decode(endTime, "utf-8");
                isjob = isjob == null ? "" : urlDecoder.decode(isjob, "utf-8");
                name = name == null ? "" : urlDecoder.decode(name, "utf-8");
                pd.put("startTime", startTime);
                pd.put("endTime", endTime);
                pd.put("isjob", isjob);
                pd.put("name", name);
                page.setCurrentPage(1);
                page.setShowCount(99999999);
                page.setPd(pd);
                System.out.println("pd" + pd);
                System.out.println("page" + page.getPd());
                List<PageData> clist = signupService.findSignuplistPage(page);
                //??excel??
                HSSFWorkbook wb = new HSSFWorkbook();
                //??sheet?
                HSSFSheet sheet = wb.createSheet("线上报名就业结果汇总表");
                //?????
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("报名时间");
                titleRow.createCell((short) 2).setCellValue("姓名");
                titleRow.createCell((short) 3).setCellValue("性别");
                titleRow.createCell((short) 4).setCellValue("联系电话");
                titleRow.createCell((short) 5).setCellValue("学历");
                titleRow.createCell((short) 6).setCellValue("报名企业");
                titleRow.createCell((short) 7).setCellValue("报名岗位");
                titleRow.createCell((short) 8).setCellValue("入职情况");
                titleRow.createCell((short) 9).setCellValue("备注");
                titleRow.createCell((short) 10).setCellValue("回访人员");
                titleRow.createCell((short) 11).setCellValue("回访时间");
                System.out.println(clist);
                if (clist.size() > 0) {


                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        if (pd1.get("czdate") != null)
                            dataRow.createCell((short) 1).setCellValue(df.format(df.parse(pd1.getString("czdate"))));
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("pname"));
                        String sex = pd1.getString("sex");
                        if ("1".equals(sex)) {
                            dataRow.createCell((short) 3).setCellValue("男");
                        }
                        if ("2".equals(sex)) {
                            dataRow.createCell((short) 3).setCellValue("女");
                        }
                        dataRow.createCell((short) 4).setCellValue(pd1.getString("tel"));
                        dataRow.createCell((short) 5).setCellValue(pd1.getString("xlname"));
                        dataRow.createCell((short) 6).setCellValue(pd1.getString("cname"));
                        dataRow.createCell((short) 7).setCellValue(pd1.getString("signupjob"));
                        isjob = pd1.getString("isjob");
                        if ("0".equals(isjob))
                            dataRow.createCell((short) 8).setCellValue("未入职");
                        if ("1".equals(isjob))
                            dataRow.createCell((short) 8).setCellValue("已入职");


                        dataRow.createCell((short) 9).setCellValue(pd1.getString("remark"));
                        dataRow.createCell((short) 10).setCellValue(pd1.getString("rczman"));
                        dataRow.createCell((short) 11).setCellValue(df.format(df.parse(pd1.getString("rcztime"))));

                    }

                }
                // ????????Excel???
                String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-signup.xls";
                //???????
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//???
                wb.write(ouputStream); //?excel????
                ouputStream.flush();// ???
                ouputStream.close();// ???
                json.put("success", "true");
                json.put("msg", "导出成功");
            } else {
                json.put("success", "false");
                json.put("msg", "超时，请重新登陆");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    //业务引导导出
    @RequestMapping(value = "/exportGuideExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportGuideExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                //clazz区分汇总还是详情，detail为详情，summary为汇总
                String clazz = request.getParameter("clazz");
                if (null == clazz || "".equals(clazz)) {
                    json.put("success", "false");
                    json.put("msg", "ClazzIsNull");
                    return json.toString();
                } else if (!"detail".equals(clazz) && !"summary".equals(clazz)) {
                    json.put("success", "false");
                    json.put("msg", "ClazzIsError,MustIsDetailOrSummary");
                    return json.toString();
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                PageData pd = new PageData();
                Page page = new Page();
                URLDecoder urlDecoder = new URLDecoder();
                String startTime = request.getParameter("startTime");
                String endTime = request.getParameter("endTime");
                System.out.println(endTime);
                if (endTime != null && !"".equals(endTime)) {
                    endTime = TimeHandle.endTimeHeandle(endTime);
                    json.put("endTime", endTime);
                }
                String isjob = request.getParameter("isjob");
                String name = request.getParameter("name");
                String ywname = request.getParameter("ywname");
                String ydqd = request.getParameter("ydqd");
                String isjd = request.getParameter("isjd");
                String type = request.getParameter("type");
                String uid = request.getParameter("uid");
                startTime = startTime == null ? "" : urlDecoder.decode(startTime, "utf-8");
                endTime = endTime == null ? "" : urlDecoder.decode(endTime, "utf-8");
                isjob = isjob == null ? "" : urlDecoder.decode(isjob, "utf-8");
                name = name == null ? "" : urlDecoder.decode(name, "utf-8");
                ywname = ywname == null ? "" : urlDecoder.decode(ywname, "utf-8");
                ydqd = ydqd == null ? "" : urlDecoder.decode(ydqd, "utf-8");
                isjd = isjd == null ? "" : urlDecoder.decode(isjd, "utf-8");
                type = type == null ? "" : urlDecoder.decode(type, "utf-8");
                uid = uid == null ? "" : urlDecoder.decode(uid, "utf-8");
                pd.put("startTime", startTime);
                pd.put("endTime", endTime);
                pd.put("isjob", isjob);
                pd.put("isjd", isjd);
                pd.put("name", name);
                pd.put("ywname", ywname);
                pd.put("ydqd", ydqd);
                pd.put("type", type);
                pd.put("uid", uid);
                page.setShowCount(99999999);
                page.setCurrentPage(1);
                page.setPd(pd);
                List<PageData> clist = null;
                boolean flag = false;
                if ("detail".equals(clazz)) {
                    clist = guideService.findAlllistPage(page);
                    flag = true;
                } else {
                    clist = guideService.findSummarylistPage(page);
                }
                int i = 0;
                if (flag == false)
                    i = 1;
                HSSFWorkbook wb = new HSSFWorkbook();
                HSSFSheet sheet = wb.createSheet("业务咨询解答表");
                HSSFRow titleRow_1 = sheet.createRow((short) 0);
                Region region = new Region((short) 0, (short) 0, (short) 1, (short) (7 + i));
                sheet.addMergedRegion(region);
                HSSFCell cell = titleRow_1.createCell((short) 0);
                cell.setCellValue("24小时就业服务平台业务引导服务报表");
                HSSFCellStyle titleStyle = wb.createCellStyle();
                titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
                titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
                titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
                titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
                titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                HSSFFont titleFont = wb.createFont();
                titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
                if (flag == false) {
                    titleFont.setFontHeightInPoints((short) 20);
                } else {
                    titleFont.setFontHeightInPoints((short) 16);
                }
                titleStyle.setFont(titleFont);
                cell.setCellStyle(titleStyle);
                HSSFRow titleRow = sheet.createRow(2);
                titleRow.createCell((short) 0).setCellValue("序号");
                if (flag == false) {
                    titleRow.createCell((short) 1).setCellValue("服务次数");
                }
                titleRow.createCell((short) (1 + i)).setCellValue("咨询时间");
                titleRow.createCell((short) (2 + i)).setCellValue("客户类型");
                titleRow.createCell((short) (3 + i)).setCellValue("客户名称");
                titleRow.createCell((short) (4 + i)).setCellValue("业务名称");
                titleRow.createCell((short) (5 + i)).setCellValue("引导渠道");
                titleRow.createCell((short) (6 + i)).setCellValue("咨询结果");
                titleRow.createCell((short) (7 + i)).setCellValue("服务内容");
                if (clist.size() > 0) {
                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        if (flag == false) {
                            dataRow.createCell((short) 1).setCellValue(String.valueOf(pd1.get("fwCount")));
                        }
                        if (pd1.get("czdate") != null)
                            dataRow.createCell((short) (1 + i)).setCellValue(df.format(df.parse(pd1.getString("czdate"))));
                        dataRow.createCell((short) (2 + i)).setCellValue(pd1.getString("typename"));

                        dataRow.createCell((short) (3 + i)).setCellValue(pd1.getString("name"));
                        dataRow.createCell((short) (4 + i)).setCellValue(countHandle(pd1, flag, "ywname"));
                        dataRow.createCell((short) (5 + i)).setCellValue(countHandle(pd1, flag, "ydqdname"));
                        isjd = pd1.getString("isjd");
                        if ("1".equals(isjd))
                            dataRow.createCell((short) (6 + i)).setCellValue("已解答");
                        if ("0".equals(isjd))
                            dataRow.createCell((short) (6 + i)).setCellValue("未解答");
                        dataRow.createCell((short) (7 + i)).setCellValue(pd1.getString("fwcont"));

                    }

                }

                String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-Guide.xls";
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//???
                wb.write(ouputStream); //?excel????
                ouputStream.flush();// ???
                ouputStream.close();// ???
                json.put("success", "true");
                json.put("msg", "导出成功");
            } else {
                json.put("success", "false");
                json.put("msg", "超时，请重新登陆");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    //培训机构信息导入
    @RequestMapping(value = "/readTrainExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String readTrainExcel(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            System.out.println(request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                System.out.println(file);
                if (null != file && !file.isEmpty()) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //??????
                    String fileName = FileUpload.fileUp(file, filePath, "Excel");                            //????
                    List<PageData> listPd = (List) ObjectExcelRead2.readExcel(filePath, fileName, 1, 0, 0);        //???EXCEL??,???????List 2:??3????0:??A????0:?0?sheet
                    /*???????======================================*/
                    for (int i = 0; i < listPd.size(); i++) {
                        String var1 = listPd.get(i).getString("var1");
                        String var2 = listPd.get(i).getString("var2");
                        String var3 = listPd.get(i).getString("var3");
                        String var4 = listPd.get(i).getString("var4");
                        String var5 = listPd.get(i).getString("var5");
                        if (var1 == null || "".equals(var1) ||
                                var2 == null || "".equals(var2) ||
                                var3 == null || "".equals(var3) ||
                                var4 == null || "".equals(var4) ||
                                var5 == null || "".equals(var5)) {
                            json.put("msg", "有无效数据");
                            json.put("success", "false");
                            break;
                        }
                    }
                    List<PageData> saveList = new ArrayList<PageData>();
                    for (int i = 0; i < listPd.size(); i++) {
                        PageData savePd = new PageData();
                        String var1 = listPd.get(i).getString("var1");
                        String var2 = listPd.get(i).getString("var2");
                        String var3 = listPd.get(i).getString("var3");
                        String var4 = listPd.get(i).getString("var4");
                        String var5 = listPd.get(i).getString("var5");
                        String var6 = listPd.get(i).getString("var6");
                        String var7 = listPd.get(i).getString("var7");
                        savePd.put("name", var1);
                        savePd.put("addr", var2);
                        savePd.put("stat", var3);
                        savePd.put("principal", var4);
                        savePd.put("tel", var5);
                        savePd.put("tel2", var6);
                        savePd.put("remark", var7);
                        savePd.put("czman", pd_token.get("ZXYH"));
                        List<PageData> exit = trainInfoService.findExit(savePd);
                        if (exit.size() < 1)
                            saveList.add(savePd);
                    }
                    if (saveList != null && saveList.size() >= 1) {
                        List<List<PageData>> split = ListUtil.split(saveList, 2000);
                        for (List<PageData> pageData : split) {
                            if (pageData != null && pageData.size() >= 1) {
                                trainInfoService.save(pageData);
                            }
                        }
                    }

                    json.put("success", "true");
                }

            } else {
                json.put("success", "false");
                json.put("msg", "导入失败");
            }
        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }


    @RequestMapping(value = "/exportEmPersonExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportEmPersonExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                PageData pd = new PageData();
                Page page = new Page();
                URLDecoder urlDecoder = new URLDecoder();
                String startTime = request.getParameter("startTime");
                String endTime = request.getParameter("endTime");
//                if (endTime != null && !"".equals(endTime)) {
//                    endTime = TimeHandle.endTimeHeandle(endTime);
//                    json.put("endTime", endTime);
//                }
                String isjob = request.getParameter("isjob");
                String name = request.getParameter("name");
                String pageIndex = "0";
                String pageSize = "999999999";
                startTime = startTime == null ? "" : urlDecoder.decode(startTime, "utf-8");
                endTime = endTime == null ? "" : urlDecoder.decode(endTime, "utf-8");
                isjob = isjob == null ? "" : urlDecoder.decode(isjob, "utf-8");
                name = name == null ? "" : urlDecoder.decode(name, "utf-8");
                pd.put("startTime", startTime);
                pd.put("endTime", endTime);
                pd.put("isjob", isjob);
                pd.put("name", name);
                page.setShowCount(999999999);
                page.setCurrentPage(1);
                page.setPd(pd);
                System.out.println(pd);
                List<PageData> clist = emPersonService.findAll(pd);
                for (PageData pageData : clist) {
                    System.out.println(pageData);
                }
                HSSFWorkbook wb = new HSSFWorkbook();
                HSSFSheet sheet = wb.createSheet("就业人员信息汇总表");
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("求职时间");
                titleRow.createCell((short) 2).setCellValue("姓名");
                titleRow.createCell((short) 3).setCellValue("性别");
                titleRow.createCell((short) 4).setCellValue("联系电话");
                titleRow.createCell((short) 5).setCellValue("学历");
                titleRow.createCell((short) 6).setCellValue("岗位性质");
                titleRow.createCell((short) 7).setCellValue("岗位名称");
                titleRow.createCell((short) 8).setCellValue("推荐企业数量");
                titleRow.createCell((short) 9).setCellValue("就职单位名称");
                titleRow.createCell((short) 10).setCellValue("回访结果");
                titleRow.createCell((short) 11).setCellValue("更新时间");
                System.out.println(clist);
                if (clist.size() > 0) {


                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count);
                        count++;
                        if (pd1.get("qzdate") != null)
                            dataRow.createCell((short) 1).setCellValue(df.format(df.parse(pd1.getString("qzdate"))));
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("name"));
                        String sex = pd1.getString("sex");
                        if ("1".equals(sex)) {
                            dataRow.createCell((short) 3).setCellValue("男");
                        }
                        if ("2".equals(sex)) {
                            dataRow.createCell((short) 3).setCellValue("女");
                        }
                        dataRow.createCell((short) 4).setCellValue(pd1.getString("tel"));
                        dataRow.createCell((short) 5).setCellValue(pd1.getString("xlname"));
                        dataRow.createCell((short) 6).setCellValue(pd1.getString("jobtypename"));
                        dataRow.createCell((short) 7).setCellValue(pd1.getString("jobname"));
                        Object tjcount = pd1.get("tjcount");
                        Integer tjcountint = Integer.valueOf(tjcount.toString());
                        dataRow.createCell((short) 8).setCellValue(tjcountint.toString());
                        dataRow.createCell((short) 9).setCellValue(pd1.getString("jobunit"));
                        String result = pd1.getString("result");
                        if ("0".equals(result))
                            dataRow.createCell((short) 10).setCellValue("未回访");
                        if ("1".equals(result))
                            dataRow.createCell((short) 10).setCellValue("已回访");
                        if (pd1.get("cztime") != null)
                            dataRow.createCell((short) 11).setCellValue(df.format(df.parse(pd1.getString("cztime"))));

                    }

                }
                // ????????Excel???
                String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-EmPeople.xls";
                //???????
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//???
                wb.write(ouputStream); //?excel????
                ouputStream.flush();// ???
                ouputStream.close();// ???
                json.put("success", "true");
                json.put("msg", "导出成功");
            } else {
                json.put("success", "false");
                json.put("msg", "超时，请重新登陆");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }


    //线上就业回访导出
    @RequestMapping(value = "/exportsignExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportsignExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
//        try {
        PageData pd_stoken = new PageData();
        URLDecoder urlDecoder = new URLDecoder();

        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        if (pd_token != null) {
            PageData pd = new PageData();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println(pd);
            String result = request.getParameter("result");
            String keywords = request.getParameter("keywords");
            pd.put("result",result == null ? "" : urlDecoder.decode(result, "utf-8"));
            pd.put("keywords",keywords == null ? "" : urlDecoder.decode(keywords, "utf-8"));
            List<PageData> clist = trainrecoService.exportsign(pd);
            System.out.println(clist);
            //创建excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建sheet页
            HSSFSheet sheet = wb.createSheet("线上报名回访");
            //创建标题行
            HSSFRow titleRow = sheet.createRow(0);
            titleRow.createCell((short) 0).setCellValue("提交时间");
            titleRow.createCell((short) 1).setCellValue("姓名");
            titleRow.createCell((short) 2).setCellValue("性别");
            titleRow.createCell((short) 3).setCellValue("联系电话");
            titleRow.createCell((short) 4).setCellValue("学历");
            titleRow.createCell((short) 5).setCellValue("报名企业");
            titleRow.createCell((short) 6).setCellValue("报名岗位(汇总)");
            titleRow.createCell((short) 7).setCellValue("招聘工种");
            titleRow.createCell((short) 8).setCellValue("企业联系人");
            titleRow.createCell((short) 9).setCellValue("企业备用联系人");
            titleRow.createCell((short) 10).setCellValue("联系电话1");
            titleRow.createCell((short) 11).setCellValue("联系电话2");
            if (clist.size() > 0) {

                for (PageData pd1 : clist) {
                    HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    dataRow.createCell((short) 0).setCellValue(df.format(df.parse(pd1.getString("czdate"))));
                    dataRow.createCell((short) 1).setCellValue(pd1.getString("pname"));
                    String sex = pd1.getString("sex");
                    if ("1".equals(sex)) {
                        sex = "男";
                    }
                    if ("2".equals(sex)) {
                        sex = "女";
                    }
                    dataRow.createCell((short) 2).setCellValue(sex);
                    dataRow.createCell((short) 3).setCellValue(pd1.getString("plxtel"));
                    dataRow.createCell((short) 4).setCellValue(pd1.getString("xlname"));
                    dataRow.createCell((short) 5).setCellValue(pd1.getString("cname"));
                    dataRow.createCell((short) 6).setCellValue(pd1.getString("signupjob"));
                    dataRow.createCell((short) 7).setCellValue(pd1.getString("jobnamename"));
                    dataRow.createCell((short) 8).setCellValue(pd1.getString("clxr"));
                    dataRow.createCell((short) 9).setCellValue(pd1.getString("cbylxr"));
                    dataRow.createCell((short) 10).setCellValue(pd1.getString("clxtel"));
                    dataRow.createCell((short) 11).setCellValue(pd1.getString("cbylxtel"));
                }

            }
            // 设置下载时客户端Excel的名称
            String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-xsbmhf.xls";
            //设置下载的文件
            System.out.println(filename);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            OutputStream ouputStream = response.getOutputStream();//打开流
            wb.write(ouputStream); //在excel内写入流
            ouputStream.flush();// 刷新流
            ouputStream.close();// 关闭流

        } else {
            json.put("success", "false");
            json.put("msg", "登录超时，请重新登录");
        }

//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();
    }

    //线上就业回访导人
    @RequestMapping(value = "/readsignExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String readsignExcel(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            System.out.println(request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            String zxyh = pd_stoken.getString("ZXYH");
            if (pd_token != null) {
                if (null != file && !file.isEmpty()) {
                    String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
                    String fileName = FileUpload.fileUp(file, filePath, "ComExcel");                            //执行上传
                    List<PageData> listPd = (List) ObjectExcelRead2.readExcel(filePath, fileName, 1, 0, 0);        //执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
                    /*检查字段操作======================================*/
                    boolean check = true;
                    String str = "";
                    for (int i = 0; i < listPd.size(); i++) {
                        String var1 = listPd.get(i).getString("var1");
                        String var3 = listPd.get(i).getString("var3");
                        String var5 = listPd.get(i).getString("var5");
                        String var7 = listPd.get(i).getString("var7");
                        String var8 = listPd.get(i).getString("var8");
                        String var10 = listPd.get(i).getString("var10");
                        if ("".equals(var1)) {
                            check = false;
                            str = str + "姓名不能为空;";
                            break;
                        } else if ("".equals(var3)) {
                            check = false;
                            //StringUtils.isnu
                            str = str + "联系电话不能为空;";
                            break;
                        } else if ("".equals(var5)) {
                            check = false;
                            str = str + "报名企业不能为空;";
                            break;
                        } else if ("".equals(var7)) {
                            check = false;
                            str = str + "招聘工种不能为空;";
                            break;
                        } else if ("".equals(var8)) {
                            check = false;
                            str = str + "企业联系人不能为空;";
                            break;
                        } else if ("".equals(var10)) {
                            check = false;
                            str = str + "联系电话不能为空;";
                            break;
                        }
                    }
                    System.out.println(listPd);
                    /*存入数据库操作======================================*/
                    if (check == true) {
                        for (int i = 0; i < listPd.size(); i++) {
                            PageData pd4 = new PageData();
                            String var1 = listPd.get(i).getString("var1").trim();
                            String tel = listPd.get(i).getString("var3") == null ? "" : listPd.get(i).getString("var3").replace("-", "");
                            pd4.put("tel", tel);
                            pd4.put("name", var1);
                            List<PageData> findperson = jobReferManager.findperson(pd4);
                            String puuid = "";

                            if (findperson.isEmpty()) {
                                PageData pageData = new PageData();
                                pageData.put("name", listPd.get(i).getString("var1"));
                                String var2 = listPd.get(i).getString("var2");
                                if ("男".equals(var2)) {
                                    var2 = "1";
                                } else if ("女".equals(var2)) {
                                    var2 = "2";
                                }
                                pageData.put("sex", var2);
                                pageData.put("tel", listPd.get(i).getString("var3"));
                                pageData.put("lxtel", listPd.get(i).getString("var3"));
                                String var4 = listPd.get(i).getString("var4").trim();
                                System.out.println(var4 + "var4");
                                PageData pd1 = new PageData();
                                pd1.put("name", var4);
                                PageData dic1 = consumerManager.getDIC(pd1);
                                if (dic1 != null) {
                                    pageData.put("xl", dic1.getString("DICTIONARIES_ID"));
                                } else {
//                                    str = str + "查无此学历;";
//                                    json.put("msg", str);
//                                    break;
                                    pageData.put("xl", "暂无");
                                }
                                puuid = getUUID32();
                                pageData.put("uid", puuid);
                                pageData.put("isimpot", "1");
                                pageData.put("czdate", getTime());
                                pageData.put("czman", zxyh);
                                pageData.put("cate", "4c3ef95ed0c5441db8b75db881ce5c80");
                                jobReferManager.insertperson(pageData);
                            } else {
                                for (PageData pageData : findperson) {
                                    puuid = pageData.getString("uid");
                                    System.out.println(pageData);
                                }
                            }


                            PageData pd3 = new PageData();
                            String var5 = listPd.get(i).getString("var5").trim();
                            String tel2 = listPd.get(i).getString("var10") == null ? "" : listPd.get(i).getString("var10").replace("-", "");
                            pd3.put("tel", tel2);
                            pd3.put("name", var5);
                            List<PageData> findcompany = consumerManager.findcompany(pd3);
                            String cuuid = "";

                            if (findcompany.isEmpty()) {
                                PageData pageData1 = new PageData();
                                pageData1.put("name", listPd.get(i).getString("var5"));
                                pageData1.put("lxr", listPd.get(i).getString("var8"));
                                pageData1.put("bylxr", listPd.get(i).getString("var9"));
                                String tel3 = listPd.get(i).getString("var10") == null ? "" : listPd.get(i).getString("var10").replace("-", "");
                                pageData1.put("tel", tel3);
                                pageData1.put("lxtel", tel3);
                                String bylxtel = listPd.get(i).getString("var11") == null ? "" : listPd.get(i).getString("var11").replace("-", "");
                                pageData1.put("bylxtel", bylxtel);
                                cuuid = getUUID32();
                                pageData1.put("uid", cuuid);
                                pageData1.put("isimpot", "1");
                                pageData1.put("czdate", getTime());
                                pageData1.put("czman", zxyh);
                                consumerManager.insertcompany(pageData1);
                            } else {
                                for (PageData data : findcompany) {
                                    cuuid = data.getString("uid");
                                    System.out.println(data);
                                }
                            }


                            PageData pageData2 = new PageData();
                            pageData2.put("czdate", listPd.get(i).getString("var0"));
                            pageData2.put("signupjob", listPd.get(i).getString("var6"));
                            String var7 = listPd.get(i).getString("var7").trim();
                            PageData pd2 = new PageData();
                            pd2.put("gwflname", var7);
                            PageData getjobtype = consumerManager.getjobtype(pd2);
                            if (getjobtype != null) {
                                pageData2.put("jobname", Integer.parseInt(getjobtype.get("code").toString()));
                            } else {
                                pageData2.put("jobname", "暂无");
                            }
                            pageData2.put("puid", puuid);
                            pageData2.put("cuid", cuuid);
                            pageData2.put("isjob", "0");

                            pageData2.put("uid", getUUID32());

                            trainrecoService.insertsignup(pageData2);

                        }
                        json.put("success", "true");
                    } else if (check == false) {
                        json.put("msg", str);
                    }

                }

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }
        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }


    //回访信息导出
    @RequestMapping(value = "/exportrevrecordExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportrevrecordExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                URLDecoder urlDecoder = new URLDecoder();
                PageData pd = new PageData();
                Page page = new Page();
                String type = request.getParameter("type");
                if (!"".equals(type)&&null!=type&&!"null".equals(type)) {
                    pd.put("type", request.getParameter("type") == null ? "" : urlDecoder.decode(request.getParameter("type"), "utf-8"));
                }
                String isjt = request.getParameter("isjt");
                if (!"".equals(isjt)&&null!=isjt&&!"null".equals(isjt)) {
                    pd.put("isjt", request.getParameter("isjt") == null ? "" : urlDecoder.decode(request.getParameter("isjt"), "utf-8"));
                }

                pd.put("starttime", request.getParameter("startTime") == null ? "" : urlDecoder.decode(request.getParameter("startTime"), "utf-8"));
                pd.put("endtime", request.getParameter("endTime") == null ? "" : urlDecoder.decode(request.getParameter("endTime"), "utf-8"));
                pd.put("keywords", request.getParameter("keywords") == null ? "" : urlDecoder.decode(request.getParameter("keywords"), "utf-8"));

                page.setShowCount(999999999);
                page.setCurrentPage(1);
                page.setPd(pd);
                System.out.println(pd);
                List<PageData> clist = revrecordManager.findrevrecordlistPage(page);
                System.out.println(clist);
                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("回访信息");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("回访时间");
                titleRow.createCell((short) 1).setCellValue("联系电话");
                titleRow.createCell((short) 2).setCellValue("客户名称");
                titleRow.createCell((short) 3).setCellValue("客户类型");
                titleRow.createCell((short) 4).setCellValue("任务类型");
                titleRow.createCell((short) 5).setCellValue("回访结果");
                titleRow.createCell((short) 6).setCellValue("服务坐席");
                titleRow.createCell((short) 7).setCellValue("备注");
                if (clist.size() > 0) {

                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(pd1.getString("kssj"));
                        dataRow.createCell((short) 1).setCellValue(pd1.getString("zjhm"));
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("name"));
                        dataRow.createCell((short) 3).setCellValue(pd1.getString("typename"));
                        String tasktype = StringUtils.isNotEmpty(pd1.getString("tasktype")) ? pd1.get("tasktype").toString() : "";
                        if (tasktype.equals("0")) {
                            tasktype = "就业回访";
                        } else if (tasktype.equals("1")) {
                            tasktype = "线上报名";
                        } else if (tasktype.equals("2")) {
                            tasktype = "未接来电";
                        } else if (tasktype.equals("3")) {
                            tasktype = "留言";
                        } else {
                            tasktype = "暂无";
                        }
                        System.out.println(tasktype);
                        dataRow.createCell((short) 4).setCellValue(tasktype);
                        String result = StringUtils.isNotEmpty(pd1.getString("result")) ? pd1.getString("result") : "";
                        if (result.equals("0")) {
                            result = "未回访";
                        } else if (result.equals("1")) {
                            result = "已回访";
                        } else {
                            result = "未回访";
                        }
                        dataRow.createCell((short) 5).setCellValue(result);
                        dataRow.createCell((short) 6).setCellValue(pd1.getString("zxxm"));
                        dataRow.createCell((short) 7).setCellValue(pd1.getString("remark"));
                    }

                }
                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-xsbmhf.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }


    //回访信息导入
//    @RequestMapping(value = "/readrevrecordExcel", produces = {"application/json;charset=UTF-8"})
//    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
//    @ResponseBody()
//    public String readrevrecordExcel(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest request) throws Exception {
//        PageData pd = new PageData();
//        JSONObject json = new JSONObject();
//        try {
//            PageData pd_stoken = new PageData();
//            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
//            System.out.println(request.getParameter("tokenid"));
//            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
//            System.out.println("执行了");
//            if (pd_token != null) {
//                String zxid = pd_token.getString("ZXID");
//                if (null != file && !file.isEmpty()) {
//                    String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
//                    String fileName = FileUpload.fileUp(file, filePath, "Excel");                            //执行上传
//                    List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 1, 0, 0);        //执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
//
//                    /*检查字段操作======================================*/
//                    boolean check = true;
//                    String str = "";
//                    for (int i = 0; i < listPd.size(); i++) {
//                        String var0 = listPd.get(i).getString("var0");
//                        String var1 = listPd.get(i).getString("var1");
//                        String var2 = listPd.get(i).getString("var2");
//                        String var3 = listPd.get(i).getString("var3");
//                        if ("".equals(var0) || var0 == null) {
//                            check = false;
//                            str = str + "知识标题不能为空";
//                            break;
//                        }
//
//                    }
//
//                    if (check==true){
//                        /*存入数据库操作======================================*/
//                        for (int i = 0; i < listPd.size(); i++) {
//                            pd.put("doctype_id", doctype_id);
//                            pd.put("doctitle", listPd.get(i).getString("var0"));
//                            pd.put("doccont", listPd.get(i).getString("var1"));
//                            pd.put("validate", listPd.get(i).getString("var2"));
//                            String var3 = listPd.get(i).getString("var3");
//                            String ishot = var3.equals("是") ? "1" : "0";
//                            pd.put("ishot", ishot);
//                            pd.put("createman",createman);
//                            pd.put("createdate",getTime());
//                            jobReferManager.importdoc(pd);
//                        }
//                        json.put("success", "true");
//                    } else if (check == false) {
//                        json.put("msg", str);
//                    }
//
//                }
//
//            } else {
//                json.put("success", "false");
//                json.put("msg", "登录超时，请重新登录");
//            }
//        } catch (Exception e) {
//            json.put("success", "false");
//        }
//        return json.toString();
//    }


    //其他服务导出
    @RequestMapping(value = "/exportOtherServiceExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportOtherServiceExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                String clazz = request.getParameter("clazz");
                if (null == clazz || "".equals(clazz)) {
                    json.put("success", "false");
                    json.put("msg", "ClazzIsNull");
                    return json.toString();
                } else if (!"detail".equals(clazz) && !"summary".equals(clazz)) {
                    json.put("success", "false");
                    json.put("msg", "ClazzIsError,MustIsDetailOrSummary");
                    return json.toString();
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                PageData pd = new PageData();
                Page page = new Page();
                URLDecoder urlDecoder = new URLDecoder();
                String startTime = request.getParameter("startTime");
                String endTime = request.getParameter("endTime");
                if (endTime != null && !"".equals(endTime)) {
                    endTime = TimeHandle.endTimeHeandle(endTime);
                    json.put("endTime", endTime);
                }
                String fwname = request.getParameter("fwname");
                String fwtype = request.getParameter("fwtype");
                String name = request.getParameter("name");
                String type = request.getParameter("type");
                String isjd = request.getParameter("isjd");
                startTime = startTime == null ? "" : urlDecoder.decode(startTime, "utf-8");
                type = type == null ? "" : urlDecoder.decode(type, "utf-8");
                endTime = endTime == null ? "" : urlDecoder.decode(endTime, "utf-8");
                name = name == null ? "" : urlDecoder.decode(name, "utf-8");
                fwname = fwname == null ? "" : urlDecoder.decode(fwname, "utf-8");
                fwtype = fwtype == null ? "" : urlDecoder.decode(fwtype, "utf-8");
                isjd = isjd == null ? "" : urlDecoder.decode(isjd, "utf-8");
                page.setShowCount(99999999);
                page.setCurrentPage(1);
                pd.put("startTime", startTime);
                pd.put("endTime", endTime);
                pd.put("fwtype", fwtype);
                pd.put("fwname", fwname);
                pd.put("name", name);
                pd.put("type", type);
                pd.put("isjd", isjd);
                page.setPd(pd);
                boolean flag = false;
                List<PageData> clist = null;
                if ("detail".equals(clazz)) {
                    clist = otherServiceService.findAlllistPage(page);
                    flag = true;
                } else {
                    clist = otherServiceService.findSummarylistPage(page);
                }
                int i = 0;
                if (flag == false)
                    i = 1;
                HSSFWorkbook wb = new HSSFWorkbook();

                HSSFSheet sheet = wb.createSheet("其他服务记录");
                HSSFRow titleRow_1 = sheet.createRow((short) 0);
                Region region = new Region((short) 0, (short) 0, (short) 1, (short) (7 + i));
                sheet.addMergedRegion(region);
                HSSFCell cell = titleRow_1.createCell((short) 0);
                cell.setCellValue("24小时就业服务平台其他服务报表");
                HSSFCellStyle titleStyle = wb.createCellStyle();
                titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
                titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
                titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
                titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
                titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                HSSFFont titleFont = wb.createFont();
                titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);//粗体显示
                if (flag == false) {
                    titleFont.setFontHeightInPoints((short) 20);
                } else {
                    titleFont.setFontHeightInPoints((short) 16);
                }
                titleStyle.setFont(titleFont);
                cell.setCellStyle(titleStyle);
                HSSFRow titleRow = sheet.createRow(2);
                titleRow.createCell((short) 0).setCellValue("序号");
                if (flag == false) {
                    titleRow.createCell((short) 1).setCellValue("服务次数");

                }
                titleRow.createCell((short) (1 + i)).setCellValue("咨询时间");
                titleRow.createCell((short) (2 + i)).setCellValue("客户名称");
                titleRow.createCell((short) (3 + i)).setCellValue("客户类型");
                titleRow.createCell((short) (4 + i)).setCellValue("服务标题");
                titleRow.createCell((short) (5 + i)).setCellValue("咨询类别");
                titleRow.createCell((short) (6 + i)).setCellValue("服务记录");
                titleRow.createCell((short) (7 + i)).setCellValue("咨询结果");
                System.out.println(clist);
                if (clist.size() > 0) {
                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count++);
                        if (flag == false) {
                            dataRow.createCell((short) 1).setCellValue(String.valueOf(pd1.get("serverCount")));
                        }
                        dataRow.createCell((short) (1 + i)).setCellValue(df.format(df.parse(pd1.getString("czdate"))));
                        dataRow.createCell((short) (2 + i)).setCellValue(pd1.getString("name"));
                        dataRow.createCell((short) (3 + i)).setCellValue(pd1.getString("typename"));
                        dataRow.createCell((short) (4 + i)).setCellValue(countHandle(pd1, flag, "fwname"));
                        dataRow.createCell((short) (5 + i)).setCellValue(countHandle(pd1, flag, "fwtypename"));
                        dataRow.createCell((short) (6 + i)).setCellValue(pd1.getString("remark"));
                        isjd = pd1.getString("isjd");
                        if ("0".equals(isjd))
                            dataRow.createCell((short) (7 + i)).setCellValue("未解答");
                        if ("1".equals(isjd))
                            dataRow.createCell((short) (7 + i)).setCellValue("已解答");
                    }

                }
                String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-otherService.xls";
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();
                wb.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();
                json.put("success", "true");
                json.put("msg", "导出成功");
            } else {
                json.put("success", "false");
                json.put("msg", "超时，请重新登陆");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }


    //技能培训导出
    @RequestMapping(value = "/exporttrainrecoExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportTrainRecoExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
//        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                PageData pd = new PageData();
                Page page = new Page();
                URLDecoder urlDecoder = new URLDecoder();
                String starttime = request.getParameter("startTime");
                String endtime = request.getParameter("endTime");
                if (endtime != null && !"".equals(endtime)) {
                    endtime = TimeHandle.endTimeHeandle(endtime);
                    json.put("endtime", endtime);
                }
                String issue = request.getParameter("issue");
                String type = request.getParameter("type");
                String keywords = request.getParameter("keywords");

                starttime = starttime == null ? "" : urlDecoder.decode(starttime, "utf-8");
                endtime = endtime == null ? "" : urlDecoder.decode(endtime, "utf-8");
                issue = issue == null ? "" : urlDecoder.decode(issue, "utf-8");
                keywords = keywords == null ? "" : urlDecoder.decode(keywords, "utf-8");
                type = type == null ? "" : urlDecoder.decode(type, "utf-8");

                page.setShowCount(99999999);
                page.setCurrentPage(1);
                pd.put("starttime", starttime);
                pd.put("endtime", endtime);
                pd.put("issue", issue);
                pd.put("keywords", keywords);
                pd.put("type", type);
                page.setPd(pd);
                System.out.println(pd);
                List<PageData> clist = trainrecoService.findTrainrecolistPage(page);

                HSSFWorkbook wb = new HSSFWorkbook();

                HSSFSheet sheet = wb.createSheet("技能培训");

                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("序号");
                titleRow.createCell((short) 1).setCellValue("通话时间");
                titleRow.createCell((short) 2).setCellValue("人员类型");
                titleRow.createCell((short) 3).setCellValue("客户名称");
                titleRow.createCell((short) 4).setCellValue("联系电话");
                titleRow.createCell((short) 5).setCellValue("工种");
                titleRow.createCell((short) 6).setCellValue("证书等级");
                titleRow.createCell((short) 7).setCellValue("领证状态");
                titleRow.createCell((short) 8).setCellValue("推荐机构");
                titleRow.createCell((short) 9).setCellValue("培训课程");
                titleRow.createCell((short) 10).setCellValue("推荐政策");
                titleRow.createCell((short) 11).setCellValue("备注");
                System.out.println(clist);
                if (clist.size() > 0) {


                    int count = 1;
                    for (PageData pd1 : clist) {
                        HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                        dataRow.createCell((short) 0).setCellValue(count++);
                        String cztime = StringUtils.isNotEmpty(pd1.getString("cztime")) ? df.format(df.parse(pd1.getString("cztime"))) : "";
                        dataRow.createCell((short) 1).setCellValue(cztime);
                        dataRow.createCell((short) 2).setCellValue(pd1.getString("typename"));
                        dataRow.createCell((short) 3).setCellValue(pd1.getString("name"));
                        dataRow.createCell((short) 4).setCellValue(pd1.getString("lxtel"));
                        dataRow.createCell((short) 5).setCellValue(pd1.getString("jobvarname"));
                        dataRow.createCell((short) 6).setCellValue(pd1.getString("credenname"));
                        issue = pd1.getString("issue");
                        if ("0".equals(issue))
                            dataRow.createCell((short) 7).setCellValue("未领证");
                        if ("1".equals(issue))
                            dataRow.createCell((short) 7).setCellValue("已领证");

                        dataRow.createCell((short) 8).setCellValue(pd1.getString("trainname"));
                        pd.put("uid", pd1.getString("uid"));
                        List<PageData> TrainZClist = trainrecoService.TrainZC(pd);
                        ArrayList<String> TrainZC = new ArrayList<>();
                        for (PageData data1 : TrainZClist) {
                            String zczxname = data1.getString("zczxnamename");
                            TrainZC.add(zczxname);
                        }
                        String TrainZCstring = TrainZC.toString().substring(1, TrainZC.toString().length() - 1);
                        dataRow.createCell((short) 9).setCellValue(pd1.getString("course"));
                        dataRow.createCell((short) 10).setCellValue(TrainZCstring);

                        dataRow.createCell((short) 11).setCellValue(pd1.getString("remark"));

                    }

                }
                String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-TrainReco.xls";
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();
                wb.write(ouputStream);
                ouputStream.flush();
                ouputStream.close();
                json.put("success", "true");
                json.put("msg", "导出成功");
            } else {
                json.put("success", "false");
                json.put("msg", "超时，请重新登陆");
            }

//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();
    }


    public String getTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }


    public String countHandle(PageData pd, boolean flag, String name) {
        String resultString = null;
        String HandleString = pd.getString(name);
        if (pd.getString(name) != null && flag == false && HandleString.contains(",")) {
            HandleString = "1." + HandleString;
            char[] chars = HandleString.toCharArray();
            List<String> result = new ArrayList<String>();
            //编号i
            int i = 2;
            for (char aChar : chars) {
                if (aChar == ',') {
                    result.add(i++ + ".");
                } else {
                    result.add(String.valueOf(aChar));
                }
            }
            resultString = String.valueOf(result).replaceAll(",", "")
                    .replaceAll("\\[", "").replaceAll("]", "")
                    .replaceAll(" ", "");


        } else {
            resultString = pd.getString(name);
        }


        return resultString;
    }

    @RequestMapping(value = "/exportLoseJobExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportLoseJobExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
//        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        if (pd_token != null) {
            PageData pd = new PageData();
            Page page = new Page();
            URLDecoder urlDecoder = new URLDecoder();

            pd.put("result", request.getParameter("result") == null ? "" : urlDecoder.decode(request.getParameter("result"), "utf-8"));
            pd.put("isjob", request.getParameter("isjob") == null ? "" : urlDecoder.decode(request.getParameter("isjob"), "utf-8"));
            pd.put("certificate_unit", request.getParameter("certificate_unit") == null ? "" : urlDecoder.decode(request.getParameter("certificate_unit"), "utf-8"));
            pd.put("certificate_id", request.getParameter("certificate_id") == null ? "" : urlDecoder.decode(request.getParameter("certificate_id"), "utf-8"));
            pd.put("name", request.getParameter("name") == null ? "" : urlDecoder.decode(request.getParameter("name"), "utf-8"));
            pd.put("starttime", request.getParameter("startTime") == null ? "" : urlDecoder.decode(request.getParameter("startTime"), "utf-8"));
            pd.put("endtime", request.getParameter("endTime") == null ? "" : urlDecoder.decode(request.getParameter("endTime"), "utf-8"));
            pd.put("keywords", request.getParameter("keywords") == null ? "" : urlDecoder.decode(request.getParameter("keywords"), "utf-8"));
            page.setShowCount(999999999);
            page.setCurrentPage(1);
            page.setPd(pd);
            System.out.println(pd);
            List<PageData> clist = consumerManager.findlostjobexport(pd);
            System.out.println(clist);
            //创建excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建sheet页
            HSSFSheet sheet = wb.createSheet("失业登记人员");
            //创建标题行
            HSSFRow titleRow = sheet.createRow(0);
            titleRow.createCell((short) 0).setCellValue("序号");
            titleRow.createCell((short) 1).setCellValue("最新服务时间");
            titleRow.createCell((short) 2).setCellValue("服务次数");
            titleRow.createCell((short) 3).setCellValue("籍贯");
            titleRow.createCell((short) 4).setCellValue("姓名");
            titleRow.createCell((short) 5).setCellValue("联系电话");
            titleRow.createCell((short) 6).setCellValue("证件号码");
            titleRow.createCell((short) 7).setCellValue("年龄");
            titleRow.createCell((short) 8).setCellValue("性别");

            titleRow.createCell((short) 9).setCellValue("就业失业证书号");
            titleRow.createCell((short) 10).setCellValue("发证日期");
            titleRow.createCell((short) 11).setCellValue("发证单位");

            titleRow.createCell((short) 12).setCellValue("就业状态");
            titleRow.createCell((short) 13).setCellValue("情况记录");
            titleRow.createCell((short) 14).setCellValue("求职岗位");
            titleRow.createCell((short) 15).setCellValue("推荐企业");
            titleRow.createCell((short) 16).setCellValue("就业岗位");
            titleRow.createCell((short) 17).setCellValue("就业单位");
            titleRow.createCell((short) 18).setCellValue("推荐政策");
            titleRow.createCell((short) 19).setCellValue("推荐技能");
            titleRow.createCell((short) 20).setCellValue("通话状态");
            titleRow.createCell((short) 21).setCellValue("未接通原因");
            ;
            titleRow.createCell((short) 22).setCellValue("推荐企业数量");
            if (clist.size() > 0) {
                int count = 1;
                for (PageData pd1 : clist) {
                    HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    dataRow.createCell((short) 0).setCellValue(count++);
                    dataRow.createCell((short) 1).setCellValue(pd1.getString("newServiceTime"));
                    dataRow.createCell((short) 2).setCellValue(pd1.get("serviceCount").toString());
                    dataRow.createCell((short) 3).setCellValue(pd1.getString("jgName"));
                    dataRow.createCell((short) 4).setCellValue(pd1.getString("name"));
                    dataRow.createCell((short) 5).setCellValue(pd1.getString("lxtel"));
                    dataRow.createCell((short) 6).setCellValue(pd1.getString("cardid"));
                    dataRow.createCell((short) 7).setCellValue(pd1.getString("age"));
                    String sex = pd1.getString("sex");
                    if ("1".equals(sex)) {
                        dataRow.createCell((short) 8).setCellValue("男");
                    } else if ("2".equals(sex)) {
                        dataRow.createCell((short) 8).setCellValue("女");
                    }

                    dataRow.createCell((short) 9).setCellValue(pd1.getString("certificate_id"));
                    dataRow.createCell((short) 10).setCellValue(pd1.getString("certificate_date"));
                    dataRow.createCell((short) 11).setCellValue(pd1.getString("certificate_unit"));

                    String isjob = pd1.getString("isjob");
                    if ("0".equals(isjob))
                        dataRow.createCell((short) 12).setCellValue("失业");
                    if ("1".equals(isjob))
                        dataRow.createCell((short) 12).setCellValue("就业");
                    if ("2".equals(isjob))
                        dataRow.createCell((short) 12).setCellValue("无意向就业");
                    if ("3".equals(isjob))
                        dataRow.createCell((short) 12).setCellValue("灵活就业");
                    if ("4".equals(isjob))
                        dataRow.createCell((short) 12).setCellValue("自主创业");

                    dataRow.createCell((short) 13).setCellValue(pd1.getString("cate"));
                    dataRow.createCell((short) 14).setCellValue(pd1.getString("qzgw"));
                    dataRow.createCell((short) 15).setCellValue(pd1.getString("companyName"));
                    dataRow.createCell((short) 16).setCellValue(pd1.getString("isjobwork"));
                    dataRow.createCell((short) 17).setCellValue(pd1.getString("jobunit"));
                    dataRow.createCell((short) 18).setCellValue(pd1.getString("zczxname"));
                    dataRow.createCell((short) 19).setCellValue(pd1.getString("trainreco"));
                    String isjt = pd1.getString("isjt");
                    if ("0".equals(isjt)) {
                        dataRow.createCell((short) 20).setCellValue("未接通");
                    } else if ("1".equals(isjt)) {
                        dataRow.createCell((short) 20).setCellValue("已接通");
                    } else {
                        dataRow.createCell((short) 20).setCellValue("未回访");
                    }
                    dataRow.createCell((short) 21).setCellValue(pd1.getString("unconnet"));
                    dataRow.createCell((short) 22).setCellValue(pd1.get("companyNum").toString());


                }

            }
            // 设置下载时客户端Excel的名称
            String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-losejob.xls";
            //设置下载的文件
            System.out.println(filename);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            OutputStream ouputStream = response.getOutputStream();//打开流
            wb.write(ouputStream); //在excel内写入流
            ouputStream.flush();// 刷新流
            ouputStream.close();// 关闭流

        } else {
            json.put("success", "false");
            json.put("msg", "登录超时，请重新登录");
        }

//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();
    }

    @RequestMapping(value = "/downLoadLoseJobExcelMode", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String downLoadLoseJobExcelMode(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {

                //创建excel文件
                HSSFWorkbook wb = new HSSFWorkbook();
                //创建sheet页
                HSSFSheet sheet = wb.createSheet("失业登记人员");
                //创建标题行
                HSSFRow titleRow = sheet.createRow(0);
                titleRow.createCell((short) 0).setCellValue("姓名");
                titleRow.createCell((short) 1).setCellValue("身份证号码");
                titleRow.createCell((short) 2).setCellValue("就业失业证书号");
                titleRow.createCell((short) 3).setCellValue("发证日期");
                titleRow.createCell((short) 4).setCellValue("发证单位");
                titleRow.createCell((short) 5).setCellValue("联系电话");
                titleRow.createCell((short) 6).setCellValue("就业状态");


                // 设置下载时客户端Excel的名称
                String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-losejob.xls";
                //设置下载的文件
                System.out.println(filename);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-disposition", "attachment;filename=" + filename);
                OutputStream ouputStream = response.getOutputStream();//打开流
                wb.write(ouputStream); //在excel内写入流
                ouputStream.flush();// 刷新流
                ouputStream.close();// 关闭流

            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }

        } catch (Exception e) {
            json.put("success", "false");
        }
        return json.toString();
    }

    @RequestMapping(value = "/readLoseJobExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String readLoseJobExcel(@RequestParam(value = "files", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PageData pd = new PageData();
        PageData pd_area = new PageData();
        JSONObject json = new JSONObject();
//        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        System.out.println(request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        com.alibaba.fastjson.JSONObject object_verificate = new com.alibaba.fastjson.JSONObject();
        List<PageData> saveList = new ArrayList<PageData>();
        List<PageData> dicAll = consumerManager.getDicAll(pd);
        if (pd_token != null) {

            String ZXID = pd_token.getString("ZXID");
            if (null != file && !file.isEmpty()) {
                Date d = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                String pcbh = sdf.format(d);  //导入批次

                String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;                                //文件上传路径
                String fileName = FileUpload.fileUp(file, filePath, "graduateListexcel");                            //执行上传
                ImportExcelService importExcelService = new ImportExcelService();
                String[] importColumns = {"姓名", "身份证号", "就业失业证书号", "发证日期", "发证单位", "联系电话", "就业状态"};//设置字段名称
                String[] importFields = {"name", "cardid", "certificate_id", "certificate_date", "certificate_unit", "lxtel", "isjob"};  //字段
                // 1:IdCardVerification 2:DateVerification 3:TelVerification 4:DictVerification 5:ValueVerification 6:SqlVerification

                String[] importYzColumns = {"", "1", "", "2", "", "", ""};
                int[] importFiledNums = {30, 20, 50, 100, 0, 11, 5}; //0表示无限制
                int[] importFiledNull = {1, 1, 1, 1, 1, 1, 0}; //验证字段是否为空：0表示无限制，1表示不能为空
                String[] importTjColumns = {"", "", "isNumberic", "", "", "", ""};  //isNumberic 是否数字;isEnglish 是否英文 ；isChinese 是否中文
                String[] importValueColumns = {"", "", "", "", "", "", ""};

                object_verificate.put("importColumns", importColumns);
                object_verificate.put("importFields", importFields);
                object_verificate.put("importYzColumns", importYzColumns);
                object_verificate.put("importFiledNums", importFiledNums);
                object_verificate.put("importValueColumns", importValueColumns);
                object_verificate.put("importTjColumns", importTjColumns);
                object_verificate.put("importFiledNull", importFiledNull);

                String openFilename = filePath + fileName;
                com.alibaba.fastjson.JSONObject jsonObject = importExcelService.importExcel(openFilename, request, object_verificate);
                List<PageData> findperson = jobReferManager.findperson(pd);
                List<PageData> listPd = ((List<PageData>) jsonObject.get("rightList"));

                System.out.println(jsonObject);
                System.out.println(listPd);
                for (int i = 0; i < listPd.size(); i++) {
                    String uid = "";
                    pd = new PageData();
                    pd_area = new PageData();
                    String lxtel = listPd.get(i).getString("lxtel") == null ? "" : listPd.get(i).getString("lxtel").replace("-", "");
                    for (PageData pageData : findperson) {
                        if ((listPd.get(i).getString("name").equals(pageData.getString("name")) && lxtel.equals(pageData.getString("lxtel"))) || listPd.get(i).getString("cardid").equals(pageData.getString("cardid"))) {
                            uid = pageData.getString("uid");
                            pd = pageData;
                        }
                    }

                    pd.put("name", listPd.get(i).getString("name").trim());
                    pd.put("cardid", listPd.get(i).getString("cardid").trim());
                    pd.put("certificate_id", listPd.get(i).getString("certificate_id").trim());
                    pd.put("certificate_date", listPd.get(i).getString("certificate_date").trim());
                    pd.put("certificate_unit", listPd.get(i).getString("certificate_unit").trim());
                    pd.put("tel", lxtel);
                    pd.put("lxtel", lxtel);
                    String var12 = listPd.get(i).getString("isjob").trim();
                    if ("就业".equals(var12)) {
                        pd.put("isjob", "1");
                    } else if ("失业".equals(var12)) {
                        pd.put("isjob", "0");
                    } else if ("无意向就业".equals(var12)) {
                        pd.put("isjob", "2");
                    } else if ("灵活就业".equals(var12)) {
                        pd.put("isjob", "3");
                    } else if ("自主创业".equals(var12)) {
                        pd.put("isjob", "4");
                    }
                    pd.put("isimpot", '1');
                    pd.put("cate", "617b2f6e122a43c9a86b5b25129aecc5");
                    pd.put("czdate", getTime());
                    pd.put("czman", ZXID);
                    String cardid = listPd.get(i).getString("cardid").trim();
                    if (StringUtils.isNotEmpty(cardid)) {
                        JSONObject cardidjson = JSON.parseObject(JSON.toJSON(identityCard(cardid)).toString());
                        pd.put("age", cardidjson.getString("age"));
                        pd.put("sex", cardidjson.getString("sex"));
                    }

                    PageData savePd = new PageData();
                    savePd.putAll(pd);
                    saveList.add(savePd);

                    if ("".equals(uid) || uid.equals(null)) {
                        pd.put("uid", getUUID32());
                        System.out.println("新增");
                        consumerManager.savePerson(pd);
                        consumerManager.insertLoseJob(pd);
                    } else {
                        pd.put("uid", uid);
                        consumerManager.editPerson(pd);
                        consumerManager.updateLoseJob(pd);
                    }

                }

                List<PageData> errorList = ((List<PageData>) jsonObject.get("errorList")); //导入错误的信息
                if (errorList.size() > 0) {
                    System.out.println(errorList);
                    for (int i = 0; i < errorList.size(); i++) {
                        pd = errorList.get(i);
                        pd.put("pcbh", pcbh);
                        pd.put("uid", getUUID32());
                        pd.put("cate", "617b2f6e122a43c9a86b5b25129aecc5");
                        pd.put("czman", pd_token.getString("ID"));
                        pd.put("czdate", getTime());
                        //System.out.println(errorList.get(i).getString("ycstr")+"ddd");
                        consumerManager.savePerson_error(pd);
                        consumerManager.insertLoseJob_error(pd);
                    }
                }

                json.put("success", "true");
                if (errorList.size() > 0) {
                    json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条,失败记录：" + errorList.size() + "条");
                    json.put("href", "api/excelLoseJob_error?pcbh=" + pcbh + "&tokenid=" + request.getParameter("tokenid"));
                } else {
                    json.put("data", "导入总记录：" + (listPd.size() + errorList.size()) + "条,导入成功记录：" + listPd.size() + "条");
                }
            }


        } else {
            json.put("success", "false");
            json.put("msg", "登录超时，请重新登录");
        }
//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();
    }

    /*
     * 异常信息下载
     */
    @RequestMapping(value = "/excelLoseJob_error", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String excelLoseJob_error(HttpServletResponse response) throws Exception {
//        response.addHeader("Access-Control-Allow-Origin", "*");
        Page page = new Page();
        ModelAndView mv = new ModelAndView();
        PageData pd = new PageData();
        net.sf.json.JSONObject object = new net.sf.json.JSONObject();
        pd = this.getPageData();

        pd.put("TOKENID", pd.getString("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd);
        if (pd_token == null) {
            object.put("success", "false");
            object.put("msg", "登录超时请重新登录");
            return object.toString();
        }

        pd.put("pcbh", pd.getString("pcbh"));
        pd.put("tables", "tesp_person_error");
        pd.put("tables2", "tesp_losejobdetail_error");
        page.setShowCount(9999999);
        page.setPd(pd);


        List<PageData> varOList = consumerManager.findLoseJoblistPage(page);

        try {
            Date date = new Date();
            String formatFileName = Tools.date2Str(date, "yyyyMMddHHmmss");
            OutputStream os = response.getOutputStream();
            response.reset();
            response.setHeader("Content-disposition", "attachment; filename=" + formatFileName + ".xls");
            response.setContentType("application/msexcel");

            WritableWorkbook wbook = Workbook.createWorkbook(os);
            WritableSheet wsheet = wbook.createSheet(formatFileName, 0);

            WritableFont wfont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
            WritableCellFormat wcfFC = new WritableCellFormat(wfont);
            wcfFC.setBackground(Colour.YELLOW);
            wsheet.addCell(new Label(0, 0, "姓名"));
            wsheet.addCell(new Label(1, 0, "身份证号"));
            wsheet.addCell(new Label(2, 0, "就业失业证书号"));
            wsheet.addCell(new Label(3, 0, "发证日期"));
            wsheet.addCell(new Label(4, 0, "发证单位"));
            wsheet.addCell(new Label(5, 0, "联系电话"));
            wsheet.addCell(new Label(6, 0, "就业状态"));
            wsheet.addCell(new Label(7, 0, "异常日志"));
            int num = 1;
            String ycstr = "";
            for (int i = 0; i < varOList.size(); i++) {

                ycstr = String.valueOf(varOList.get(i).getString("ycstr"));
                if (ycstr.indexOf("name") >= 0) {
                    wsheet.addCell(new Label(0, num, varOList.get(i).getString("name"), wcfFC));
                } else {
                    wsheet.addCell(new Label(0, num, varOList.get(i).getString("name")));
                }
                if (ycstr.indexOf("cardid") >= 0) {
                    wsheet.addCell(new Label(1, num, varOList.get(i).getString("cardid"), wcfFC));
                } else {
                    wsheet.addCell(new Label(1, num, varOList.get(i).getString("cardid")));
                }
                if (ycstr.indexOf("certificate_id") >= 0) {
                    wsheet.addCell(new Label(2, num, varOList.get(i).getString("certificate_id"), wcfFC));
                } else {
                    wsheet.addCell(new Label(2, num, varOList.get(i).getString("certificate_id")));
                }

                if (ycstr.indexOf("certificate_date") >= 0) {
                    wsheet.addCell(new Label(3, num, varOList.get(i).getString("certificate_date"), wcfFC));
                } else {
                    wsheet.addCell(new Label(3, num, varOList.get(i).getString("certificate_date")));
                }
                if (ycstr.indexOf("certificate_unit") >= 0) {
                    wsheet.addCell(new Label(4, num, varOList.get(i).getString("certificate_unit"), wcfFC));
                } else {
                    wsheet.addCell(new Label(4, num, varOList.get(i).getString("certificate_unit")));
                }
                if (ycstr.indexOf("tel") >= 0) {
                    wsheet.addCell(new Label(5, num, varOList.get(i).getString("tel"), wcfFC));
                } else {
                    wsheet.addCell(new Label(5, num, varOList.get(i).getString("tel")));
                }
                if (ycstr.indexOf("isjob") >= 0) {
                    wsheet.addCell(new Label(6, num, varOList.get(i).getString("isjob"), wcfFC));
                } else {
                    wsheet.addCell(new Label(6, num, varOList.get(i).getString("isjob")));
                }
                wsheet.addCell(new Label(7, num, varOList.get(i).getString("ycstrs")));
                num++;
            }
            wbook.write();
            if (wbook != null) {
                wbook.close();
                wbook = null;
            }
            if (os != null) {
                os.close();
                os = null;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex);
        }
        return null;
    }

    /**
     * 18位身份证获取性别和年龄
     *
     * @param CardCode
     * @return
     * @throws Exception
     */
    public static Map<String, Object> identityCard(String CardCode) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        // 得到年份
        String year = CardCode.substring(6).substring(0, 4);
        // 得到月份
        String month = CardCode.substring(10).substring(0, 2);
        //得到日
        //String day=CardCode.substring(12).substring(0,2);
        String sex;
        // 判断性别
        if (Integer.parseInt(CardCode.substring(16).substring(0, 1)) % 2 == 0) {
            sex = "2";
        } else {
            sex = "1";
        }
        // 得到当前的系统时间
        Date date = new Date();
        // 当前年份
        String currentYear = format.format(date).substring(0, 4);
        // 月份
        String currentMonth = format.format(date).substring(5, 7);
        //String currentdDay=format.format(date).substring(8,10);
        int age = 0;
        // 当前月份大于用户出身的月份表示已过生日
        if (Integer.parseInt(month) <= Integer.parseInt(currentMonth)) {
            age = Integer.parseInt(currentYear) - Integer.parseInt(year) + 1;
        } else {
            // 当前用户还没过生日
            age = Integer.parseInt(currentYear) - Integer.parseInt(year);
        }
        map.put("sex", sex);
        map.put("age", age);
        return map;
    }

    //就业推荐统计
    @RequestMapping(value = "/exportjobreferDataExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportjobreferDataExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
//        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        if (pd_token != null) {
            PageData pd = new PageData();
            Page page = new Page();
            URLDecoder urlDecoder = new URLDecoder();

            pd.put("name", request.getParameter("name") == null ? "" : urlDecoder.decode(request.getParameter("name"), "utf-8"));
            pd.put("lxtel", request.getParameter("lxtel") == null ? "" : urlDecoder.decode(request.getParameter("lxtel"), "utf-8"));
            pd.put("sex", request.getParameter("sex") == null ? "" : urlDecoder.decode(request.getParameter("sex"), "utf-8"));
            pd.put("xl", request.getParameter("xl") == null ? "" : urlDecoder.decode(request.getParameter("xl"), "utf-8"));
            pd.put("jobname", request.getParameter("jobname") == null ? "" : urlDecoder.decode(request.getParameter("jobname"), "utf-8"));
            pd.put("isnew", request.getParameter("isnew") == null ? "" : urlDecoder.decode(request.getParameter("isnew"), "utf-8"));
            String keywords = request.getParameter("keywords");

            if (!"".equals(keywords)&&null!=keywords&& !"undefined".equals(keywords)) {
                pd.put("keywords", request.getParameter("keywords") == null ? "" : urlDecoder.decode(request.getParameter("keywords"), "utf-8"));
            }
            String qzqd = request.getParameter("qzqd");
            if (!"".equals(qzqd)&&null!=qzqd&&!"undefined".equals(qzqd)) {
                pd.put("qzqd", request.getParameter("qzqd") == null ? "" : urlDecoder.decode(request.getParameter("qzqd"), "utf-8"));
            }
            page.setShowCount(999999999);
            page.setCurrentPage(1);
            page.setPd(pd);
            System.out.println(pd);
            List<PageData> clist = jobReferManager.jobreferDatalistPage(page);
            Page pageed = new Page();
            PageData pageData1 = new PageData();
            pageData1.put("starttime", request.getParameter("starttime"));
            pageData1.put("endtime", request.getParameter("endtime"));
            pageed.setCurrentPage(1);
            pageed.setShowCount(999999);
            pageed.setPd(pageData1);
            List<PageData> listJytj = companyService.tecjytjlistPage(pageed);
            List<PageData> referlist = jobReferManager.Personrefer(pageData1);
            for (PageData pageData : clist) {
                int countjytj = 0;
                int countrefer = 0;
                String uid = pageData.getString("uid");
                for (PageData data : listJytj) {
                    if (data.getString("uid").equals(uid)) {
                        countjytj++;
                    }
                }
                for (PageData data : referlist) {
                    if (data.getString("uid").equals(uid)) {
                        countrefer++;
                    }
                }
                pageData.put("Jytjnum", countjytj);
                pageData.put("Refernum", countrefer);
            }
            System.out.println(clist);
            //创建excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建sheet页
            HSSFSheet sheet = wb.createSheet("就业推荐统计");
            //创建标题行
            HSSFRow titleRow = sheet.createRow(0);
            titleRow.createCell((short) 0).setCellValue("序号");
            titleRow.createCell((short) 1).setCellValue("姓名");
            titleRow.createCell((short) 2).setCellValue("学历");
            titleRow.createCell((short) 3).setCellValue("联系电话");
            titleRow.createCell((short) 4).setCellValue("招聘工种");
            titleRow.createCell((short) 5).setCellValue("性别");
            titleRow.createCell((short) 6).setCellValue("岗位匹配数");
            titleRow.createCell((short) 7).setCellValue("已推荐量");
            if (clist.size() > 0) {
                int count = 1;
                for (PageData pd1 : clist) {
                    HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    dataRow.createCell((short) 0).setCellValue(count++);
                    dataRow.createCell((short) 1).setCellValue(pd1.getString("name"));
                    dataRow.createCell((short) 2).setCellValue(pd1.getString("xlname"));
                    dataRow.createCell((short) 3).setCellValue(pd1.getString("lxtel"));
                    dataRow.createCell((short) 4).setCellValue(pd1.getString("gwflname"));
                    String sex = pd1.getString("sex");
                    if ("1".equals(sex)) {
                        sex = "男";
                    } else if ("2".equals(sex)) {
                        sex = "女";
                    } else {
                        sex = "均可";
                    }
                    dataRow.createCell((short) 5).setCellValue(sex);

                    dataRow.createCell((short) 6).setCellValue(pd1.get("Jytjnum").toString());
                    dataRow.createCell((short) 7).setCellValue(pd1.get("Refernum").toString());

                }

            }
            // 设置下载时客户端Excel的名称
            String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-poverty.xls";
            //设置下载的文件
            System.out.println(filename);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            OutputStream ouputStream = response.getOutputStream();//打开流
            wb.write(ouputStream); //在excel内写入流
            ouputStream.flush();// 刷新流
            ouputStream.close();// 关闭流

        } else {
            json.put("success", "false");
            json.put("msg", "登录超时，请重新登录");
        }

//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();
    }

    //用工推荐统计
    @RequestMapping(value = "/exportpersonreferDataExcel", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String exportpersonreferDataExcel(HttpServletResponse response, HttpServletRequest request) throws Exception {

        JSONObject json = new JSONObject();
//        try {
        PageData pd_stoken = new PageData();
        pd_stoken.put("TOKENID", request.getParameter("tokenid"));
        PageData pd_token = zxlbService.findByTokenId(pd_stoken);
        if (pd_token != null) {
            PageData pd = new PageData();
            Page page = new Page();
            URLDecoder urlDecoder = new URLDecoder();

            pd.put("name", request.getParameter("name") == null ? "" : urlDecoder.decode(request.getParameter("name"), "utf-8"));
            pd.put("lxtel", request.getParameter("lxtel") == null ? "" : urlDecoder.decode(request.getParameter("lxtel"), "utf-8"));
            String xl = request.getParameter("xl");
            if (!"".equals(xl)&&null!=xl&&!"undefined".equals(xl)) {
                pd.put("xl", request.getParameter("xl") == null ? "" : urlDecoder.decode(request.getParameter("xl"), "utf-8"));
            }
            String qzqd = request.getParameter("qzqd");
            if (!"".equals(qzqd)&&null!=xl&&!"undefined".equals(qzqd)) {
                pd.put("qzqd", request.getParameter("qzqd") == null ? "" : urlDecoder.decode(request.getParameter("qzqd"), "utf-8"));
            }
            String jobname = request.getParameter("jobname");
            if (!"".equals(jobname)&&null!=jobname&&!"undefined".equals(jobname)) {
                pd.put("jobname", request.getParameter("jobname") == null ? "" : urlDecoder.decode(request.getParameter("jobname"), "utf-8"));
            }
            pd.put("isnew", request.getParameter("isnew") == null ? "" : urlDecoder.decode(request.getParameter("isnew"), "utf-8"));
            String keywords = request.getParameter("keywords");
            if (!"".equals(keywords)&&null!=xl&&!"undefined".equals(keywords)) {
                pd.put("keywords", request.getParameter("keywords") == null ? "" : urlDecoder.decode(request.getParameter("keywords"), "utf-8"));
            }



            page.setShowCount(999999999);
            page.setCurrentPage(1);
            page.setPd(pd);
            System.out.println(pd);
            List<PageData> clist = jobReferManager.personreferDatalistPage(page);

            Page pageed = new Page();
            PageData pageData1 = new PageData();
            pageData1.put("starttime", request.getParameter("starttime"));
            pageData1.put("endtime", request.getParameter("endtime"));
            pageed.setCurrentPage(1);
            pageed.setShowCount(999999);
            pageed.setPd(pageData1);
            List<PageData> Ygtj = companyService.tecygdjlistPage(pageed);
            List<PageData> referlist = jobReferManager.Companyrefer(pageData1);

            for (PageData pageData : clist) {

                String uid = pageData.getString("uid");
                int countJytj = 0;
                int countrefer = 0;
                for (PageData data : Ygtj) {
                    if (data.getString("uid").equals(uid)) {
                        countJytj++;
                    }
                }
                for (PageData data : referlist) {
                    if (data.getString("uid").equals(uid)) {
                        countrefer++;
                    }
                }

                pageData.put("Ygtjnum", countJytj);
                pageData.put("Refernum", countrefer);
            }
            System.out.println(clist);
            //创建excel文件
            HSSFWorkbook wb = new HSSFWorkbook();
            //创建sheet页
            HSSFSheet sheet = wb.createSheet("用工推荐统计");
            //创建标题行
            HSSFRow titleRow = sheet.createRow(0);
            titleRow.createCell((short) 0).setCellValue("序号");
            titleRow.createCell((short) 1).setCellValue("企业名称");
            titleRow.createCell((short) 2).setCellValue("学历要求");
            titleRow.createCell((short) 3).setCellValue("联系电话");
            titleRow.createCell((short) 4).setCellValue("招聘工种");
            titleRow.createCell((short) 5).setCellValue("性别");
            titleRow.createCell((short) 6).setCellValue("岗位匹配数");
            titleRow.createCell((short) 7).setCellValue("已推荐量");
            if (clist.size() > 0) {
                int count = 1;
                for (PageData pd1 : clist) {
                    HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
                    dataRow.createCell((short) 0).setCellValue(count++);
                    dataRow.createCell((short) 1).setCellValue(pd1.getString("name"));
                    dataRow.createCell((short) 2).setCellValue(pd1.getString("xlname"));
                    dataRow.createCell((short) 3).setCellValue(pd1.getString("lxtel"));
                    dataRow.createCell((short) 4).setCellValue(pd1.getString("gwflname"));
                    String sex = pd1.getString("sex");
                    if ("1".equals(sex)) {
                        sex = "男";
                    } else if ("2".equals(sex)) {
                        sex = "女";
                    } else {
                        sex = "均可";
                    }
                    dataRow.createCell((short) 5).setCellValue(sex);

                    dataRow.createCell((short) 6).setCellValue(pd1.get("Ygtjnum").toString());
                    dataRow.createCell((short) 7).setCellValue(pd1.get("Refernum").toString());

                }

            }
            // 设置下载时客户端Excel的名称
            String filename = new SimpleDateFormat("yyyyMMdd").format(new Date()) + "-poverty.xls";
            //设置下载的文件
            System.out.println(filename);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment;filename=" + filename);
            OutputStream ouputStream = response.getOutputStream();//打开流
            wb.write(ouputStream); //在excel内写入流
            ouputStream.flush();// 刷新流
            ouputStream.close();// 关闭流

        } else {
            json.put("success", "false");
            json.put("msg", "登录超时，请重新登录");
        }

//        } catch (Exception e) {
//            json.put("success", "false");
//        }
        return json.toString();
    }
}