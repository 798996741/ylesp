<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.fh.util.Jurisdiction" %>
<%
	Jurisdiction jurisdiction=new Jurisdiction();
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<%@ include file="../../system/include/incJs_mx.jsp"%>
	<!-- jsp文件头和头部 -->
	<!-- 日期框 (带小时分钟)-->
	<link rel="stylesheet" href="static/ace/css/bootstrap-datetimepicker.css" />
	<link type="text/css" rel="stylesheet" href="plugins/zTree/v3/css/zTreeStyle/zTreeStyle.css" />
	<script type="text/javascript" src="plugins/zTree/v3/js/jquery.ztree.core.js"></script>
	<script type="text/javascript" src="plugins/zTree/v3/js/jquery.ztree.excheck.js"></script>
	<style>
     	.btable,.btable tr th, .btable tr td { border:1px solid #ccc;padding:5px; }
     	.btable { width: 100%; min-height: 25px; line-height: 25px; text-align: left; border-collapse: collapse;}   
     	.tdclass{
     		text-align:right;
     	}
     	.tdtitle{
     		text-align:center;
     		background:#dedede
     	}
	</style>
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container" style="min-height:550px">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
			
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel" style="float: left;">	</h4>
					<div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>
				</div>
				<div class="row">
					<div class="col-xs-12">
					<div class="span6">
						<div class="tabbable">
							
							<div class="tab-content" style="background:#fff;min-height:300px">
								<div id="home" class="tab-pane in active">
									<form action="rutask/handle.do" name="Form_add" id="Form_add" method="post">
										<input type="hidden" name="ID_" id="ID_" value="${pd.ID_}"/>
										<input type="hidden" name="ASSIGNEE_" id="ASSIGNEE_" value=""/>
										<input type="hidden" name="PROC_INST_ID_" id="PROC_INST_ID_" value="${pd.PROC_INST_ID_}"/>
										
										<table  class="btable" style="margin-top: 10px;">
											<tr>
												<td class="tdclass">投诉来源:</td>
												<td>${pd_s.tssourcename}</td>
												<td class="tdclass">投诉时间:</td>
												<td>${pd_s.tsdate}</td>
												<td class="tdclass">投诉编号:</td>
												<td>${pd_s.code}</td>
											</tr>
											<tr>
												<td class="tdclass">投诉内容:</td>
												<td colspan="5">${pd_s.tscont}</td>
											</tr>
											<tr>
												<td class="tdclass">投诉类别(大项):</td>
												<td>
													${pd_s.tsbigtype}
												</td>
												<td class="tdclass">投诉类别(细项):</td>
												<td >
													${pd_s.tsclassifyname}
												</td>
												<td class="tdclass">投诉部门:</td>
												<td>
													<c:if test="${taskname!='工单专员审批'}">
														${pd_s.tsdeptname}
														<input type="hidden" name="tsdept" id="tsdept" value="${pd_s.tsdept}"/>
													</c:if>
													<c:if test="${taskname=='工单专员审批'}">
														<input type="hidden" name="tsdept" id="tsdept" value="${pd_s.tsdept }">
														<input id="tsdeptSel" style="width:200px;" name="" type="text" readonly  onclick="showTsdept(); return false;"/>	
														<div id="menuContent_dept" class="menuContent_dept"
															style="position: absolute;display:none;background:rgb(240,246,228);">
															<div class="new-bc" style=" height:35px;padding-top:5px;border-top-left-radius:5px;border-top-right-radius:5px;border-left:1px #ccc solid;border-right:1px #ccc solid;border-top:1px #ccc solid ">
																<a  style=" width:50px;height:25px;line-height:25px;padding:0px;padding-left:10px;" class="btnbm" onclick="hideDept()" >确定</a>&nbsp;&nbsp;&nbsp;&nbsp;<a  style=" width:50px;height:25px;line-height:25px;padding:0px;padding-left:10px;" onclick="hideDept()" >取消</a>
															</div>
															<ul id="treeDemo_dept" class="ztree" style="margin-top:0; width:250px;border:1px #ccc solid"></ul>
														</div>
														<script type="text/javascript">
															var setting_dept = {
																check: {
																	enable: true
																},
																data: {
																	simpleData: {
																		enable: true
																	}
																},
																callback: {
																	beforeClick: beforeClick_dept,
																	//onClick: onClick_dept,
																	onCheck: onClick_dept
																}
															};
															var zn_dept = '${zTreeNodes_dept}';
															var zNodes_dept = eval(zn_dept);
															//alert('${zTreeNodes_dept}');
													
															var t_dept = $("#treeDemo_dept");
															t_dept = $.fn.zTree.init(t_dept, setting_dept, zNodes_dept);
															t_dept.expandAll(true);
															
															 var pid= '${pd_s.tsdept}'; /**此处数据前后必须拼接;*/
															 //alert(pid);
															 var zTree = t_dept.getCheckedNodes(false);
														     for (var i = 0; i < zTree.length; i++) {
														        if (pid.indexOf(zTree[i].AREA_CODE) != -1) {
														        	//alert(zTree[i]);
														        	t_dept.expandNode(zTree[i], true); //展开选中的
														        	t_dept.checkNode(zTree[i], true);					
														        }
														     }
														     if(pid!=""){
														    	 onClick_dept();
														     }
															 function beforeClick_dept(treeId, treeNode) {
																var check = (treeNode && !treeNode.isParent);
																if (!check) ;
																return check;
															}
															
															function onClick_dept(e, treeId, treeNode) {
																var zTree = $.fn.zTree.getZTreeObj("treeDemo_dept"),
																nodes = zTree.getCheckedNodes(true),
																v = "",vid="";
																for (var i=0, l=nodes.length; i<l; i++) {
																	if(nodes[i].AREA_LEVEL=='3'){
																		if(v!=""){
																			v += ",";
																		}
																		v += nodes[i].name;
																		if(vid!=""){
																			vid += ",";
																		}
																		vid += nodes[i].AREA_CODE;	
																	}
																}
																//if (v.length > 0 ) v = v.substring(0, v.length-1);
																var cityObj = $("#tsdeptSel");
																//alert(v);
																cityObj.attr("value", v);
																
																$("#tsdept").val(vid);
																//hideDept();
															}
													
															
															function showTsdept() {
																var cityObj = $("#tsdept");
																var cityOffset = $("#tsdept").offset();
																$("#menuContent_dept").slideDown("fast");
																$("body").bind("mousedown", onBodyDown);
															}
															
															function hideMenu() {
																$("#menuContent").fadeOut("fast");
																$("body").unbind("mousedown", onBodyDown);
															}
															function hideDept() {
																$("#menuContent_dept").fadeOut("fast");
																$("body").unbind("mousedown", onBodyDown);
															}
															
															function onBodyDown(event) {
																//alert(event);
																if (!(event.target.id == "menuBtn" || event.target.id == "menuContent"|| event.target.id == "menuContent_dept" || $(event.target).parents("#menuContent").length>0)) {
																	hideMenu();
																	//hideDept();
																}
															}
														</script>
														
													</c:if>	
												
												</td>
											</tr>
											<tr>
												<td class="tdclass">投诉分类:</td>
												<td>${pd_s.tstypename}</td>
												<td class="tdclass">投诉等级:</td>
												<td>${pd_s.tslevelname}</td>
												<td  class="tdclass">是否回访:</td>
												<td>
													<c:if test="${pd_s.ishf=='1'}">是</c:if>
													<c:if test="${pd_s.ishf=='0' || empty pd_s.ishf}">否</c:if>
												</td>
											</tr>
											<tr>
												<td class="tdclass">投诉人:</td>
												<td>${pd_s.tsman}</td>
												<td class="tdclass">联系电话:</td>
												<td>${pd_s.tstel}</td>
												<td class="tdclass">证件号码:</td>
												<td>${pd_s.cardid}</td>
											</tr>
											
											<tr>
												<td class="tdclass">乘机时间:</td>
												<td>${pd_s.cjdate}</td>
												<td class="tdclass">航班号/航程:</td>
												<td>${pd_s.hbh}</td>
												<td class="tdclass">受理人:</td>
												<td>${sessionUser.NAME}</td>
											</tr>
											
											<c:forEach items="${clList}" var="varcl" varStatus="vs">
												<c:if test="${vs.index==0}">
													<tr>
														<td style="width:50px;font-weight:bold" rowspan="${clCount+1 }" class="tdclass">处理<br>记录</td>
														<td class="tdtitle">处理部门</td>
														<td class="tdtitle">处理人</td>
														<td class="tdtitle">处理时间</td>
														<td colspan="2" style="width:40%" class="tdtitle">处理记录</td>
													</tr>
												</c:if>
												<tr>
													<td align="center">${varcl.areaname}</td>
													<td align="center">${varcl.clman}(${varcl.name})</td>
													<td align="center">${varcl.cldate}</td>
													<td colspan="2" align="center">
														<c:if test="${not empty varcl.clcont&&varcl.clcont!='null'}">
															${varcl.clcont}
														</c:if>
													</td>
												</tr>
												
											</c:forEach>
											
											<c:if test="${taskname=='单部门领导审批'||taskname=='部门领导审批'||taskname=='工单专员审批'}">
												<tr>	
													<td style="text-align: right;padding-top: 13px;">是否同意:</td>
													<td colspan="5" >
														<input type="radio" name="msg" id="msg" value="yes" checked style="vertical-align:middle"> 同意
														&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="msg" id="msg" value="no" style="vertical-align:middle"> 不同意
													</td>
												</tr>
											</c:if>
											
											
											
											<tr>	
												<c:if test="${taskname!='单部门领导审批'&&taskname!='部门领导审批'&&taskname!='工单专员审批'}">
													<input type="hidden" name="msg" id="msg" value="yes"/>
												</c:if>
												<td style="text-align: right;padding-top: 13px;">处理意见:</td>
												<td colspan="5" id="omsg">
													<textarea  name="OPINION" id="OPINION" style="width:80%" >${pd.DESCRIPTION}</textarea>
												</td>
											</tr>
											
											<tr>
												<td class="tdclass">处理时限:</td>
												<td><span id="xclsj" style="font-weight:bold">${cltime}</span></td>
												<td class="tdclass">附件:</td>
												<td >
													<ul id="fileid"style="display:none">
													</ul>&nbsp;&nbsp;
														<%if(jurisdiction.hasQx("70209")){ %>
															&nbsp;&nbsp;<a onclick="uploadfile('${pd_s.uid}','');" >上传</a>
														<%} %>
														<%if(!jurisdiction.hasQx("70209")){ %>
															&nbsp;&nbsp;<a onclick="uploadfile('${pd_s.uid}','4');" >查看</a>
														<%} %>
													</a>
												</td>
												
												<td class="tdclass">重复投诉:</td>
												<td >
													<input type="text" readonly name="cfbm" id="cfbm" onclick="search_cfbm('${pd_s.cfbm}')" value="${pd_s.cfbm}" style="width:100px;min-width:120px;border:1px red solid" />
													<c:if test="${taskname=='单部门工单处理'||taskname=='多部门工单处理'}">
														
														<a onclick="getCfts('${pd_s.uid}');">选择</a>&nbsp;&nbsp;&nbsp;<a onclick="delCfts('${pd_s.uid}');">删除</a>
													</c:if>
												</td>
											</tr>
											
											
											<tr>	
												<td style="text-align: center;" colspan="6">
													<c:if test="${taskname=='单部门领导审批'||taskname=='部门领导审批'||taskname=='工单专员审批'}">
														<input id="bton" type="button" class="btn btn-mini btn-success"  onclick="handle('2');" value="提  交">
														&nbsp;&nbsp;&nbsp;&nbsp;
													</c:if>		
													<c:if test="${taskname!='单部门领导审批'&&taskname!='部门领导审批'&&taskname!='工单专员审批'}">
														<input id="bton" type="button" class="btn btn-mini btn-success" onclick="handle('1');" value="提  交">
														&nbsp;&nbsp;&nbsp;&nbsp;
													</c:if>			
												</td>
											</tr>	
										</table>
									
										<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
									</form>
									
								</div>
								
							</div>
						</div>
					</div>
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</div>
			<!-- /.page-content -->
		</div>
	</div>
	<!-- /.main-content -->
</div>

<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<!-- 百度富文本编辑框-->
	<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor_full/";</script>
	<script type="text/javascript" charset="utf-8" src="plugins/ueditor_full/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="plugins/ueditor_full/ueditor.all.min.js"> </script>
    <script type="text/javascript" charset="utf-8" src="plugins/ueditor_full/lang/zh-cn/zh-cn.js"></script>
	
	
	<!-- 百度富文本编辑框-->
	<!-- 确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- 日期框(带小时分钟) -->
	<script src="static/ace/js/date-time/moment.js"></script>
	<script src="static/ace/js/date-time/locales.js"></script>
	<script src="static/ace/js/date-time/bootstrap-datetimepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
	

		//文件上传查看
		function uploadfile(uid,type){
			//alert(uid);
			var winId = "userWin";
			//var type='${pd.type}';
			filelayer=layer.open({
	   		  	type: 2,
	   		  	title: "文件上传",
	   		  	shade: 0.5,
	   		  	skin: 'demo-class',
	   			area:  ['90%', '100%'],
	   		  	content: "<%=basePath%>workorder/filelist.do?doaction="+type+"&uid="+uid
		    });
		  	
		}
		
		function search_cfbm(cfbm){
			//alert(uid);
			var cfbm=$("#cfbm").val();
			if(cfbm==""){
				return false;
			}
			var winId = "userWin";
			var type='${pd.type}';
			filelayer=layer.open({
	   		  	type: 2,
	   		  	title: "详情",
	   		  	shade: 0.5,
	   		  	skin: 'demo-class',
	   		  	area:  ['90%', '100%'],
	   		  	content: '<%=basePath%>workorder/goEdit.do?doaction=search&code='+cfbm
		    });
		  	
		}
		var uid='${pd_s.uid}';
		function getFile(){
			//alert(uid);
			if(uid!=""){
				$.ajax({
					//几个参数需要注意一下
					type : "POST",//方法类型
					dataType : "json",//预期服务器返回的数据类型
					url : "workorder/getFile.do?uid="+uid,//url
					success : function(result) {
						
						var str="";
						$.each(result.list, function(i, list){
							str=str+"<li>"+list.name+"&nbsp;&nbsp;</li>";
						});
						$("#fileid").html('');
						//alert(str);
						$("#fileid").append(str);
					}
				 });
			}else{
				
			}
		}
		var timer = setInterval(function(){
			getCltime();
		},1000);
		function getCltime(){
			var xclsj='${xclsj}';
			//alert(xclsj);
			if('${xclsj}'!=""){
				$.ajax({
					//几个参数需要注意一下
					type : "POST",//方法类型
					dataType : "html",//预期服务器返回的数据类型
					url : "rutask/getCltime.do?xclsj="+xclsj,//url
					success : function(result) {
						result=result.replace("day","天").replace("hour","时").replace("min","分").replace("sec","秒");
						$("#xclsj").html(result);
					}
				 });
			}else{
				
			}
		}
		
		var cftslayer;
		function closeCftsLayer(){
			layer.close(cftslayer);//关闭当前页	
		}
		
		function delCfts(){
			$("#cfbm").val('');
		}
		
		//文件上传查看
		function getCfts(id){
			var winId = "userWin";
			cftslayer=layer.open({
	   		  	type: 2,
	   		  	title: "重复投诉",
	   		  	shade: 0.5,
	   		  	skin: 'demo-class',
	   		  	area:  ['90%', '100%'],
	   		  	content: "<%=basePath%>workorder/list.do?id="+id+"&type=all&doaction=cfts"
		    });
		}
		
		//getFile();
		
		//办理任务
		function handle(action){
			$("#bton").attr("disabled","true");
		     var msg=$("input[name='msg']:checked").val();
		     if(msg=="no"&&action=="2"){
		    	var tsdept=$("#tsdept").val();
			    if(tsdept==""){
			    	layer.alert("请选择投诉的部门");
					return false;
				}else{
					 $("#tsdept").val(tsdept);
				}	
		     }
			 
			//$("#msg").val(msg);
			//$("#OPINION").val(getContent());
			if($("#OPINION").val()==""){
				$("#omsg").tips({
					side:3,
		            msg:'请输入审批意见',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#OPINION").focus();
				return false;
			}
			openLoading(9);
			$("#Form_add").submit();
			closeLoading();
		}
		
		//作废
		function del(Id){
			bootbox.prompt("请输入作废缘由?", function(result) {
				if(result != null){
					if('' == result)result = "未写作废缘由";
					top.jzts();
					var url = "<%=basePath%>ruprocdef/delete.do?PROC_INST_ID_="+Id+"&reason="+encodeURI(encodeURI(result))+"&tm="+new Date().getTime();
					$.get(url,function(data){
						$("#zhongxin").hide();
						$("#zhongxin2").show();
						top.Dialog.close();
					});
				}
			});
		}
		
		//查看用户
		function viewUser(USERNAME){
			if('admin' == USERNAME){
				bootbox.dialog({
					message: "<span class='bigger-110'>不能查看admin用户!</span>",
					buttons: 			
					{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
				});
				return;
			}
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Modal = false;				//有无遮罩窗口
			 diag.Drag=true;
			 diag.Title ="资料";
			 diag.URL = '<%=basePath%>user/view.do?USERNAME='+USERNAME;
			 diag.Width = 469;
			 diag.Height = 380;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//审批意见详情页
		function details(htmlId){
			 var content = $("#"+htmlId).val().split(',fh,');
			 top.handleDetails(content[1]);
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Modal = false;			//有无遮罩窗口
			 diag.Drag=true;
			 diag.Title ="审批意见";
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.URL = '<%=basePath%>rutask/details.do';
			 diag.Width = 760;
			 diag.Height = 500;
			 diag.CancelEvent = function(){ //关闭事件
				diag.close();
			 };
			 diag.show();
		}
		
		//选择办理人
		function getUser(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="选择办理人";
			 diag.URL = '<%=basePath%>user/listUsersForWindow.do';
			 diag.Width = 700;
			 diag.Height = 545;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 var USERNAME = diag.innerFrame.contentWindow.document.getElementById('USERNAME').value;
				 if("" != USERNAME){
					 $("#ASSIGNEE_").val(USERNAME);
					 $("#ASSIGNEE_2").val(USERNAME);
				 }
				diag.close();
			 };
			 diag.show();
		}
		
		//选择角色
		function getRole(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="选择角色";
			 diag.URL = '<%=basePath%>role/roleListWindow.do?ROLE_ID=1';
			 diag.Width = 700;
			 diag.Height = 545;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 var RNUMBER = diag.innerFrame.contentWindow.document.getElementById('RNUMBER').value;
				 if("" != RNUMBER){
					 $("#ASSIGNEE_").val(RNUMBER);
					 $("#ASSIGNEE_2").val(RNUMBER);
				 }
				diag.close();
			 };
			 diag.show();
		}
		
		//清空下一任务对象
		function clean(){
		 	$("#ASSIGNEE_").val("");
		 	$("#ASSIGNEE_2").val("");
		}
		
		</script>
		<c:if test="${null == pd.msg or pd.msg != 'admin' }">
		<script type="text/javascript">
		//百度富文本
		setTimeout("ueditor()",500);
		function ueditor(){
			//UE.getEditor('editor');
		}
		
		//ueditor有标签文本
		function getContent() {
		    var arr = [];
		    arr.push(UE.getEditor('editor').getContent());
		    return arr.join("");
		}
		</script>
		</c:if>
</body>
</html>