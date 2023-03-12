<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.fh.util.Jurisdiction"%>
<%
	Jurisdiction jurisdiction=new Jurisdiction();
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<!-- jsp文件头和头部 -->
<%-- 
<%@ include file="../index/top.jsp"%> --%>
<%@ include file="../../system/include/incJs_mx.jsp"%>
<link type="text/css" rel="stylesheet" href="plugins/zTree/v3/css/zTreeStyle/zTreeStyle.css" />
<script type="text/javascript" src="plugins/zTree/v3/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="plugins/zTree/v3/js/jquery.ztree.excheck.js"></script>




<style>


.zxzgl-middle-r {
	width: 100% !important;
}
.seat-middle-top {
	margin-top: 0;
}
.seat-middle-top-left-tp select{
	height:30px;
	margin-left:10px;
	width:150px;
	
}
.seat-middle-top-left-tp input{
	height:30px;
	width:150px;
}
.seat-middle-top-left-tp div{
	height:35px;
}
</style>
</head>
<body class="no-skin"  style="overflow-x:scroll;" id="mr-background">
	<div class="content-wrapper" style="width:180%;margin-left:0px;">
		<section class="container-fluid">
			<div class="seat-middle-top-x"></div>
			<div class="seat-middle-top" style="height:120px;line-height:25px">
				<div class="seat-middle-top-left">
					<div class="seat-middle-top-left-bt">工单待办</div>
					<div class="seat-middle-top-left-tp" style="width:1200px">
						<form action="rutask/list.do" method="post" name="Form" id="Form">
	                    	<div>
		                    	工单来源:
		                    	<select id="tssources" name="tssources"  >
		                    		<option value="" >全部</option>
									<c:forEach items="${tssourceList}" var="var" varStatus="vs">
										<option value="${var.DICTIONARIES_ID }" <c:if test="${pd.tssource==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
									</c:forEach>
								</select>
								开始日期：
								<input style="margin:0px" class="date-picker" name="starttime" id="starttime" autoComplete="off" title="开始时间"
		                                   placeholder="开始时间" value="${pd.starttime}" type="text"
		                                   data-date-format="yyyy-mm-dd"/>
		                                                            结束日期：
								<input style="margin:0px" class="date-picker" name="endtime" id="endtime" autoComplete="off" title="开始时间"
		                                   placeholder="结束时间" value="${pd.endtime}" type="text"
		                                   data-date-format="yyyy-mm-dd"/>
		                                                            投&nbsp;诉&nbsp;人：
								&nbsp;<input placeholder="投诉人" name="tsmans" id="tsmans" value="${pd.tsman }"> 
							</div>
							<div>
								投诉部门:
		                    	<select id="tsdepts" name="tsdepts">
									<option value="">全部</option>
									<c:forEach items="${tsdeptList}" var="var" varStatus="vs">
										<option value="${var.AREA_CODE }"
											<c:if test="${pd.tsdept==var.AREA_CODE}">selected</c:if>>${var.NAME }</option>
									</c:forEach>
								</select> 
								投诉类别(大项)：
								<select id="bigtstypes" name="bigtstypes"  onchange="tstypechanges()"  style="width:330px;background:url('')">
									<option value="">请选择投诉类别</option>
									<c:forEach items="${tstypeLists}" var="var" varStatus="vs">
										<option value="${var.DICTIONARIES_ID }" <c:if test="${pd.bigtstypes==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
									</c:forEach>
								</select>
								投诉类别(细项):
								<span id="tstypespans"></span>
								
							</div>
							<div>
								工单状态:
		                    	<select id="types" name="types"  >
		                    		<option value="" >全部</option>
									<option value="0" <c:if test="${pd.type=='0'}">selected</c:if>>待反馈</option>
									<option value="1" <c:if test="${pd.type=='1'}">selected</c:if>>待确认</option>
									<option value="2" <c:if test="${pd.type=='2'}">selected</c:if>>工单已分派</option>
									<option value="3" <c:if test="${pd.type=='3'}">selected</c:if>>正常为您处理</option>
									<option value="4" <c:if test="${pd.type=='4'}">selected</c:if>>工单已关闭</option>
									
								</select>
								
								<input type="button" style="width:60px" class="btn btn-mini btn-success" onclick="tosearch();" value="查询">
							</div>
							
		                  </form>  
					</div>
				</div>
				
			</div>
			<div class="seat-middle">
				<!--  <div class="seat-middle-nr"> -->

				<div class="xtyh-middle-r zxzgl-middle-r">
					<!-- <div class="box-body" > -->
						<table id="example2" class="table table-striped table-bordered table-hover" style="margin-top:5px;">	
							<thead>
								<tr>
									<th class="center cy_th" style="width:30px;">编号</th>
									<th id='cy_thk'></th>
									<th class="center">日期</th>
									<th class="center">投诉来源</th>
									
									<th class="center">投诉人</th>
									<th class="center">联系方式</th>
									<th class="center">投诉内容</th>
									<th class="center">投诉等级</th>
									<th class="center">投诉部门</th>
									<!-- <th class="center">投诉类别（大项）</th> -->
									<th class="center">投诉类别（细项）</th>
									<th class="center">投诉分类</th>
									<th class="center">受理人</th>
									<th class="center">顾客回访情况</th>
									<th class="center">结束时间</th>
									<th class="center">处理节点</th>
									<th class="center">工单状态</th>
									<th class="center"> 结束原因</th>
									
									
									<th class="center">操作</th>
								</tr>
							</thead>
													
							<tbody>
							<!-- 开始循环 -->	
							<c:choose>
								<c:when test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">
										<tr>
											<td class='center cy_td' style="width: 30px;">${var.code}</td>
											<td id='cy_thk'></td>
											<td class='center'>${var.czdate}</td>
											<td class='center'>${var.tssourcename}</td>
											<td class='center'>${var.tsman}</td>
											<td class='center'>${var.tstel}</td>
											
											<td class='center'>
												<span class="td-content" title="${var.tscont}">${var.tscont}</span>
											</td>
											<td class='center'>${var.tslevelname}</td>
											<td class='center'>${var.tsdeptname}</td>
											<%-- <td class='center'>${var.tsbigtype}</td> --%>
											<td class='center'>${var.tstypename}</td>
											
											<td class='center'>${var.tsclassifyname}</td>
											<td class='center'>${var.dealman}</td>
											<td class='center'>
												<c:if test="${var.ishf == '0' ||empty var.ishf}">否</c:if>
												<c:if test="${var.ishf == '1' }">是</c:if> 
											</td>
											<td class='center'>${var.endtime}</td>
											<td class='center'>${var.cljdname}</td>
											<td class='center'>
												<c:if test="${var.type == 0 }">待反馈</c:if>
												<c:if test="${var.type == 1 }">工单已分派</c:if>
												<c:if test="${var.type == 3 }">正常为您处理</c:if>
												<c:if test="${var.type == 4 }">工单已关闭</c:if>
												<c:if test="${var.type == 5 }">工单退回</c:if>
											</td>
											
											<td class='center'>
												<span class="td-content" title="${var.endreason}">${var.endreason}</span>
											</td>
											<td class="center">
											<c:if test="${var.SUSPENSION_STATE_ == 1 }">
												
												<div class="hidden-sm hidden-xs btn-group">
													<%if(jurisdiction.hasQx("70401")){ %>
													<c:if test="${QX.Delegate == 1 }">
													<a class="btn btn-xs btn-purple" title="委派" onclick="delegate('${var.ID_}');">
														<i class="ace-icon glyphicon glyphicon-user bigger-120" title="委派" style="float: left;"></i><div style="float: right;">委派</div>
													</a>
													</c:if>
													<a class="cy_bj" title="办理" onclick="handle_add('${var.PROC_INST_ID_}','${var.ID_}','${var.DGRM_RESOURCE_NAME_}');">
														<i title="办理"></i>
													</a>
													<%} %>
												</div>
												
											</c:if>
											<c:if test="${var.SUSPENSION_STATE_ == 2 }">
												<h7 class="red">已挂起</h7>
											</c:if>
											</td>
										</tr>
									</c:forEach>
									
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center" >没有需要办理的任务</td>
									</tr>
								</c:otherwise>
							</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
						<table style="width:100%;">
							<tr>
								<td style="vertical-align:top;">
								</td>
								<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
							</tr>
						</table>
						
				</div>

			</div>
		</section>
	</div>

	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<script type="text/javascript">
		//检索
		function tosearch(){
			$("#Form").submit();
		}
		
		$('.date-picker').datepicker({
			autoclose: true,
			todayHighlight: true,
			clearBtn: true
		});
		function tstypechanges(){
			var bigtstypes=$("#bigtstypes").val();
			if(bigtstypes==""){
				$("#tstypespans").html('');
				return false;
			}
			var tstypes='${pd.tstypes}';
			//alert(bigtstype);
			$.ajax({
				//几个参数需要注意一下
				type : "POST",//方法类型
				dataType : "json",//预期服务器返回的数据类型
				url : "workorder/getTstype.do?bigtstype="+bigtstypes,//url
				success : function(result) {
					
					var str="<select id=\"tstypes\" name=\"tstypes\" >";
					
					$.each(result.list, function(i, list){
						str=str+"<option value=\"\">请选择投诉分类细项</option>";	
						if(tstypes==list.DICTIONARIES_ID){
							str=str+"<option value=\""+list.DICTIONARIES_ID+"\" selected>"+list.NAME+"</option>";	
						}else{
							str=str+"<option value=\""+list.DICTIONARIES_ID+"\">"+list.NAME+"</option>";
						}
						
					});
					str=str+"</select>";
					$("#tstypespans").html('');
					$("#tstypespans").html(str);
				}
			 });
		}
		tstypechanges();
		$(function() {
			$('#example2').DataTable({
				'paging'      : true,
			     'lengthChange': false,
			     'searching'   : false,
			     'ordering'    : false,
			     'info'        : true,
			     'autoWidth'   : true
		    })
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
			
		});
		
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
		
		//委派他人办理
		function delegate(ID_){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="委派对象";
			 diag.URL = '<%=basePath%>ruprocdef/goDelegate.do?ID_='+ID_;
			 diag.Width = 500;
			 diag.Height = 130;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 tosearch();
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//办理任务
		function handle_add(PROC_INST_ID_,ID_,FILENAME){
			//alert("d");
			var winId = "userWin";
			//alert($("#mr-background").height());
		  	modals.openWin({
	          	winId: winId,
	          	title: '办理任务',
	          	width: $("#mr-background").width(),
	          	height: $("#mr-background").height()+"px",
	          	url: '<%=basePath%>rutask/goHandle.do?PROC_INST_ID_='+PROC_INST_ID_+"&ID_="+ID_+'&FILENAME='+encodeURI(encodeURI(FILENAME))
	      	});
			
			<%--  top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="办理任务";
			 diag.URL = '<%=basePath%>rutask/goHandle.do?PROC_INST_ID_='+PROC_INST_ID_+"&ID_="+ID_+'&FILENAME='+encodeURI(encodeURI(FILENAME));
			 diag.Width = 1100;
			 diag.Height = 750;
			 diag.Modal = true;				//有无遮罩窗口
			 diag. ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 $("#simple-table").tips({
							side:3,
				            msg:'审批完毕',
				            bg:'#AE81FF',
				            time:3
				     });
					 top.topTask();//刷新顶部任务列表
					 setTimeout('tosearch()',1000);//3秒后刷新当前任务列表
				}
				diag.close();
			 };
			 diag.show(); --%>
		}
	</script>


</body>
</html>