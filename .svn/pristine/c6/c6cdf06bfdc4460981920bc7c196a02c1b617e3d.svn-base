package com.xxgl.system;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.entity.system.Dictionaries;
import com.fh.service.system.dictionaries.DictionariesManager;
import com.fh.util.PageData;
import com.xxgl.service.system.BaseColumnsManager;
import com.xxgl.service.system.TemplatesManager;
import com.xxgl.utils.ResponseUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/basecolumns")
public class BaseColumnsController extends BaseController {

    @Resource(name = "baseColumnsService")
    private BaseColumnsManager baseColumnsService;

    @Resource(name = "dictionariesService")
    private DictionariesManager dictionariesService;

    @Resource(name = "templatesService")
    private TemplatesManager templateService;


    /**
     * 列表
     *
     * @throws Exception
     */
    @RequestMapping(value = "/list")
    public ModelAndView list() throws Exception {
        ModelAndView mv = this.getModelAndView();

        List<Map> result = new ArrayList<Map>();
        List<?> dicList = (List<?>) baseColumnsService.findByParentId("6e8b7fdf5c3e4566a3ea30b301bd5bd4");

        for (Object dic : dicList) {
            Map<String, String> map = new HashMap<String, String>();
            PageData dic1 = (PageData) dic;
            map.put("NAME", dic1.getString("NAME"));
            map.put("TEMPLATE_ID", dic1.getString("DICTIONARIES_ID"));
            result.add(map);
        }
        mv.setViewName("system/basecolumns/basecolumns_list");
        mv.addObject("varList", result);
        return mv;
    }

    @RequestMapping(value = "/goEdit")
    public ModelAndView goEdit() throws Exception {
        ModelAndView mv = this.getModelAndView();
        PageData pd = this.getPageData();
        pd = baseColumnsService.findById(pd);
        Page page = new Page();
        Page page_qoute = new Page();
        PageData pd_quote = new PageData();
        String TEMPLATE_ID = pd.getString("DICTIONARIES_ID");
        pd.put("DICTIONARIES_ID", "0");
        //PageData pg_dic = dictionariesService.findByBianma(pd);
        if (pd != null) {
            List<Dictionaries> dicList = dictionariesService.listSubDictByParentId(pd.getString("DICTIONARIES_ID"));
            mv.addObject("dicList", dicList);
        }
        page.setPd(pd);
        page.setShowCount(999);
        List<PageData> varList = dictionariesService.list(page);    //列出数据字典列表
        pd_quote.put("TB_TYPE", "1");
        page_qoute.setPd(pd_quote);
        page_qoute.setShowCount(999);
        List<PageData> quoteList = templateService.list(page_qoute);
        mv.addObject("quoteList", quoteList);
        mv.addObject("varList", varList);
        pd.put("FIELDLIST", pd.get("EXTEND1"));
        pd.put("TEMPLATE_ID", TEMPLATE_ID);
        mv.addObject("pd", pd);
        mv.setViewName("system/basecolumns/basecolumns_edit");
        return mv;
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public Object save() throws Exception {
        String result = "";
        PageData pd = this.getPageData();
        baseColumnsService.save(pd);
        result = "success";
        return result;
    }
}
