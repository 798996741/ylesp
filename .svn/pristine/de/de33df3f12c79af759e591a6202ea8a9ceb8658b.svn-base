package com.yulun.controller.trainreco;

import com.alibaba.fastjson.JSONObject;
import com.fh.util.PageData;
import com.xxgl.service.mng.ZxlbManager;
import com.yulun.service.RevrecordManager;
import com.yulun.utils.UrlFilesToZip;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
@CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
@RequestMapping(value = "/api")
public class RecoedDownController {

    @Resource(name = "zxlbService")
    private ZxlbManager zxlbService;
    @Resource(name = "revrecordService")
    private RevrecordManager revrecordManager;

    @RequestMapping(value = "/filesdown", produces = {"application/json;charset=UTF-8"})
    @CrossOrigin(allowedHeaders = "*", allowCredentials = "true")
    @ResponseBody()
    public String filesdown(HttpServletResponse response, HttpServletRequest request) throws Exception {
        PageData pd = new PageData();
        JSONObject json = new JSONObject();
        try {
            PageData pd_stoken = new PageData();
            pd_stoken.put("TOKENID", request.getParameter("tokenid"));
            PageData pd_token = zxlbService.findByTokenId(pd_stoken);
            if (pd_token != null) {
                String ids = request.getParameter("ids");
                pd.put("id",ids);
                List<PageData> list = revrecordManager.downloadRecord(pd);

                ArrayList<String> urls = new ArrayList<>();
                for (PageData pageData : list) {
                    String lywj = pageData.getString("lywj");
                    urls.add(lywj);
                }
//                urls.add("http://114.115.151.239:9999/2020-04-18/013705934512_200418_192926_o.wav");

                String filename = new String((new Date().getTime()+"wav.zip").getBytes("UTF-8"), "ISO8859-1");//控制文件名编码
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ZipOutputStream zos = new ZipOutputStream(bos);
                UrlFilesToZip s = new UrlFilesToZip();
                int idx = 1;
                for (PageData pageData : list) {
                    String lywj = pageData.getString("lywj");
                    String lysj = pageData.getString("lysj");
                    zos.putNextEntry(new ZipEntry(lysj+"profile" + idx+".wav"));
                    byte[] bytes = s.getImageFromURL(lywj);
                    System.out.println(lywj);
                    System.out.println(bytes);
                    zos.write(bytes, 0, bytes.length);
                    zos.closeEntry();
                    idx++;
                }
//                for (String oneFile : urls) {
//                    zos.putNextEntry(new ZipEntry("profile" + idx+".wav"));
//                    byte[] bytes = s.getImageFromURL(oneFile);
//                    System.out.println(oneFile);
//                    System.out.println(bytes);
//                    zos.write(bytes, 0, bytes.length);
//                    zos.closeEntry();
//                    idx++;
//                }
                zos.close();
                response.setContentType("application/force-download");// 设置强制下载不打开
                response.addHeader("Content-Disposition", "attachment;fileName=" + filename);// 设置文件名
                OutputStream os = response.getOutputStream();
                os.write(bos.toByteArray());
                os.close();
                json.put("success", "true");
                json.put("msg", "下载成功");
            } else {
                json.put("success", "false");
                json.put("msg", "登录超时，请重新登录");
            }

        } catch (FileNotFoundException ex) {
            json.put("success", "false");
            json.put("msg", "文件不存在");
        } catch (Exception ex) {
            json.put("success", "false");
            json.put("msg", "文件不存在，下载失败");
        }
        return json.toString();
    }


}
