package com.fh.controller.activiti.hiprocdef;

import com.fh.controller.activiti.AcBusinessController;
import com.fh.controller.activiti.rutask.RuTaskController;
import com.fh.entity.Page;
import com.fh.service.activiti.hiprocdef.HiprocdefManager;
import com.fh.service.activiti.ruprocdef.RuprocdefManager;
import com.fh.service.system.user.UserManager;
import com.fh.util.AppUtil;
import com.fh.util.Jurisdiction;
import com.fh.util.PageData;
import com.xxgl.service.mng.WorkorderManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 说明：历史流程任务
 * 作者：FH Admin Q31-35-96790
 * 官网：www.fhadmin.org
 */
@Controller
@RequestMapping(value="/hiprocdef")
public class HiprocdefController extends AcBusinessController {
	
	String menuUrl = "hiprocdef/list.do"; //菜单地址(权限用)
	@Resource(name="hiprocdefService")
	private HiprocdefManager hiprocdefService;
	@Resource(name="ruprocdefService")
	private RuprocdefManager ruprocdefService;
	@Resource(name="workorderService")
	private WorkorderManager workorderService;
	@Resource(name="userService")
	private UserManager userService;

	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Hiprocdef");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		String lastStart = pd.getString("lastStart");			//开始时间
		String lastEnd = pd.getString("lastEnd");				//结束时间
		if(lastStart != null && !"".equals(lastStart)){
			pd.put("lastStart", lastStart+" 00:00:00");
		}
		if(lastEnd != null && !"".equals(lastEnd)){
			pd.put("lastEnd", lastEnd+" 00:00:00");
		}
		page.setPd(pd);
		List<PageData> varList = hiprocdefService.list(page);	//列出Hiprocdef列表
		for(int i=0;i<varList.size();i++){
			Long ztime = Long.parseLong(varList.get(i).get("DURATION_").toString());
			Long tian = ztime / (1000*60*60*24);
			Long shi = (ztime % (1000*60*60*24))/(1000*60*60);
			Long fen = (ztime % (1000*60*60*24))%(1000*60*60)/(1000*60);
			varList.get(i).put("ZTIME", tian+"天"+shi+"时"+fen+"分");
			varList.get(i).put("INITATOR", getInitiator(varList.get(i).getString("PROC_INST_ID_")));//流程申请人
		}
		mv.setViewName("activiti/hiprocdef/hiprocdef_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**查看流程信息页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/view")
	public ModelAndView view()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("proc_id", pd.getString("PROC_INST_ID_"));
		PageData pd_s =workorderService.findById(pd);
		List<PageData>	varList = ruprocdefService.varList(pd);			//列出流程变量列表
		List<PageData>	hitaskList = ruprocdefService.hiTaskList(pd);	//历史任务节点列表
		
		List<PageData>	clList=new ArrayList();
		PageData pdcl=new PageData();
		PageData pduser=new PageData();
		String[] arrcl=null;
		for(int i=0;i<hitaskList.size();i++){							//根据耗时的毫秒数计算天时分秒
			if(null != hitaskList.get(i).get("DURATION_")){
				Long ztime = Long.parseLong(hitaskList.get(i).get("DURATION_").toString());
				Long tian = ztime / (1000*60*60*24);
				Long shi = (ztime % (1000*60*60*24))/(1000*60*60);
				Long fen = (ztime % (1000*60*60*24))%(1000*60*60)/(1000*60);
				Long miao = (ztime % (1000*60*60*24))%(1000*60*60)%(1000*60)/1000;
				hitaskList.get(i).put("ZTIME", tian+"天"+shi+"时"+fen+"分"+miao+"秒");
			}
			
		}
		String lasttime="";
		//System.out.println(varList+"varList===="+hitaskList);
		for(int i=0;i<varList.size();i++){							//根据耗时的毫秒数计算天时分秒
			
			if(varList.get(i).getString("TEXT_")!=null&&varList.get(i).getString("TEXT_").indexOf(",huangfege,")>=0){
				pdcl=new PageData();
				arrcl=varList.get(i).getString("TEXT_").split(",huangfege,");
				pdcl.put("clman", arrcl[0]);
				pdcl.put("USERNAME", arrcl[0]);
				if(arrcl[1]!=null&&!arrcl[1].equals("")&&!arrcl[1].equals("null")){
					pdcl.put("clcont", arrcl[1]==null?"":arrcl[1]);
				}
				
				pdcl.put("cldate", String.valueOf(varList.get(i).get("LAST_UPDATED_TIME_")==null?"":varList.get(i).get("LAST_UPDATED_TIME_")).substring(0, 19));
				pduser=userService.getUserInfoByUsername(pdcl);
				pdcl.put("areaname", pduser.getString("areaname"));
				pdcl.put("name", pduser.getString("NAME"));
				clList.add(pdcl);
				lasttime=String.valueOf(varList.get(i).get("LAST_UPDATED_TIME_"));
			}
			
		}
		//System.out.println(clList+"clList");
		String clsx=pd_s.getString("clsx")==null?"":pd_s.getString("clsx");
		String czdate=String.valueOf(pd_s.get("czdate"));
		int hour=0;
		if(clsx.equals("24H")){
			hour=24;
		}
		if(clsx.equals("48H")){
			hour=48;
		}
		if(clsx.equals("3D")){
			hour=72;
		}
		if(clsx.equals("7D")){
			hour=168;
		}
		if(hour>0){
			String xclsj=RuTaskController.addDateMinut(czdate, hour);
			String cltime=RuTaskController.timesBetween(lasttime, xclsj);
			mv.addObject("cltime", cltime);
			mv.addObject("xclsj", xclsj);
		}
		
		
		String taskId = pd.getString("ID_");	//任务ID
		
		
	/*	String FILENAME = URLDecoder.decode(pd.getString("FILENAME"), "UTF-8");
		createXmlAndPngAtNowTask(pd.getString("PROC_INST_ID_"),FILENAME);//生成当前任务节点的流程图片
		pd.put("FILENAME", FILENAME);
		String imgSrcPath = PathUtil.getClasspath()+Const.FILEACTIVITI+FILENAME;
		
		
		pd.put("imgSrc", "data:image/jpeg;base64,"+ImageAnd64Binary.getImageStr(imgSrcPath)); //解决图片src中文乱码，把图片转成base64格式显示(这样就不用修改tomcat的配置了)
		
		*/
		mv.setViewName("activiti/hiprocdef/hiprocdef_view");
	
		mv.addObject("varList", varList);
		mv.addObject("hitaskList", hitaskList);
		mv.addObject("clList", clList);
		mv.addObject("clCount", clList.size());
		mv.addObject("pd", pd);
		mv.addObject("pd_s", pd_s);
		mv.addObject("pd", pd);
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		deleteHiProcessInstance(pd.getString("PROC_INST_ID_"));
		out.write("success");
		out.close();
	}
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			for(int i=0;i<ArrayDATA_IDS.length;i++){
				deleteHiProcessInstance(ArrayDATA_IDS[i]);
			}
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
}
