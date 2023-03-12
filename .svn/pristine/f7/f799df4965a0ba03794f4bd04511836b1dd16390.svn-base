<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.fh.util.Jurisdiction"%>
<%@ page import="com.fh.entity.system.User"%>
<%
	Jurisdiction jurisdiction=new Jurisdiction();
	User user =Jurisdiction.getLoginUser();
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
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
	div.content_wrap {
		width: 600px;
		height: 380px;
	}
	
	div.content_wrap div.left {
		float: left;
		width: 250px;
	}
	
	div.content_wrap div.right {
		float: right;
		width: 340px;
	}
	
	div.zTreeDemoBackground {
		width: 250px;
		height: 362px;
		text-align: left;
	}
	
	ul.ztree {
		margin-top: 10px;
		border: 1px solid #617775;
		background: #f0f6e4;
		width: 220px;
		height: 360px;
		overflow-y: scroll;
		overflow-x: auto;
	}
	
	ul.log {
		border: 1px solid #617775;
		background: #f0f6e4;
		width: 300px;
		height: 170px;
		overflow: hidden;
	}
	
	ul.log.small {
		height: 45px;
	}
	
	ul.log li {
		color: #666666;
		list-style: none;
		padding-left: 10px;
	}
	
	ul.log li.dark {
		background-color: #E3E3E3;
	}
	
	/* ruler */
	div.ruler {
		height: 20px;
		width: 220px;
		background-color: #f0f6e4;
		border: 1px solid #333;
		margin-bottom: 5px;
		cursor: pointer
	}
	
	div.ruler div.cursor {
		height: 20px;
		width: 30px;
		background-color: #3C6E31;
		color: white;
		text-align: right;
		padding-right: 5px;
		cursor: pointer
	}
	
	#menuTree {
		background: #af0000;
	}
	
	.seat-middle-top {
		margin-top: 0;
	}
	
	.seat-middle-top-left-tp select {
		height: 30px;
		margin-left: 10px;
		width: 150px;
	}
	
	.seat-middle-top-left-tp input {
		height: 30px;
		width: 150px;
	}
	
	.seat-middle-top-left-tp div {
		height: 35px;
	}
	
</style>
<body class="no-skin" style="overflow-x:scroll;">
	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper"  <c:if test="${empty pd.doaction }">style="width:180%;margin-left:0px;"</c:if> <c:if test="${not empty pd.doaction }">style="width:100%;margin-left:0px;"</c:if>>
		<section class="container-fluid">
			<div class="seat-middle-top-x"></div>
			<div class="seat-middle-top" style="height:120px;line-height:25px">
				<div class="seat-middle-top-left">
					
					<div class="seat-middle-top-left-tp" <c:if test="${empty pd.doaction }">style="width:1200px"</c:if>>
						<form action="workorder/list.do?doaction=${pd.doaction}&type=${pd.type}" method="post" name="Form"
							id="Form">
							
							<div>
								投诉来源: 
								<select id="tssources" name="tssources">
									<option value="">全部</option>
									<c:forEach items="${tssourceList}" var="var" varStatus="vs">
										<option value="${var.DICTIONARIES_ID }" <c:if test="${pd.tssource==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
									</c:forEach>
								</select> 开始日期： 
								<input style="margin:0px" class="date-picker"
									name="starttime" id="starttime" autoComplete="off" title="开始时间"
									placeholder="开始时间" value="${pd.starttime}" type="text"
									data-date-format="yyyy-mm-dd" /> 
									结束日期： <input style="margin:0px" class="date-picker" name="endtime"
									id="endtime" autoComplete="off" title="开始时间" placeholder="结束时间"
									value="${pd.endtime}" type="text" data-date-format="yyyy-mm-dd" />
								投&nbsp;诉&nbsp;人： &nbsp;<input placeholder="投诉人" name="tsmans"
									id="tsmans" value="${pd.tsman }">
							</div>
							<div>
								投诉部门: <select id="tsdepts" name="tsdepts">
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
								<select id="types" name="types">
									<option value="">全部</option>
									<option value="0" <c:if test="${pd.type=='0'}">selected</c:if>>待反馈</option>
									<option value="1" <c:if test="${pd.type=='1'}">selected</c:if>>待确认</option>
									<option value="2" <c:if test="${pd.type=='2'}">selected</c:if>>工单已分派</option>
									<option value="3" <c:if test="${pd.type=='3'}">selected</c:if>>正常为您处理</option>
									<option value="4" <c:if test="${pd.type=='4'}">selected</c:if>>工单已关闭</option>
									<option value="5" <c:if test="${pd.type=='5'}">selected</c:if>>工单退回</option>
								</select> 
								投诉编号： 
								<input placeholder="投诉编号" name="codes" id="codes" value="${pd.code }">
								<input type="hidden" name="type" id="type" value="${pd.type }">
								<input type="hidden" name="dpf" id="dpf" value="${pd.dpf }">
								结束原因：
								 <input placeholder="结束原因" name="endreasons" id="endreasons" value="${pd.endreason }"> 
								 <input type="button" style="width:60px"
									class="btn btn-mini btn-success" onclick="search();" value="查询">
								 <c:if test="${empty pd.doaction }">
									 <%if(jurisdiction.hasQx("70201")){ %>
										 <input type="button" style="width:60px" class="btn btn-mini btn-success" onclick="add();" value="新增">
									 <%} %>
									 <%if(jurisdiction.hasQx("70204")){ %>
										 <input type="button" style="width:60px" class="btn btn-mini btn-success" onclick="toExcel();" value="导出">
									 <%} %>
								 </c:if>
							</div>
						</form>
					</div>
				</div>
				<!--  <div class="seat-middle-top-right">
                	
				</div> -->
			</div>
			<div class="seat-middle">
				<div class="xtyh-middle-r zxzgl-middle-r">
					<table id="example2" class="table table-striped table-bordered table-hover" style="margin-top:5px;">
						<thead>
							<tr>


								<th class="center cy_th" style="width:30px;">投诉编号</th>
								<th id='cy_thk'></th>
								<th class="center">日期</th>
								<th class="center">投诉来源</th>

								<th class="center">投诉人</th>
								<c:if test="${empty pd.doaction }">
									<th class="center">联系方式</th>
								</c:if>
								<th class="center">投诉内容</th>
								<c:if test="${empty pd.doaction }">
									<th class="center">投诉等级</th>
								</c:if>
								<th class="center">投诉部门</th>
								<!-- <th class="center">投诉类别（大项）</th> -->
								<th class="center">投诉类别（细项）</th>
								<c:if test="${empty pd.doaction }">
									<th class="center">投诉分类</th>
									<th class="center">受理人</th>
									<th class="center">顾客回访情况</th>
									<th class="center">结束时间</th>
									<th class="center">处理节点</th>
									<th class="center">工单状态</th>
									<th class="center">结束原因</th>
								</c:if>

								<th class="center" style="width:50px;">操作</th>


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
											<td class='center'>${fn:substring(var.tsdate,0,10)}</td>
											<td class='center'>${var.tssourcename}</td>
											<td class='center'>${var.tsman}</td>
											<c:if test="${empty pd.doaction }">
												<td class='center'>${var.tstel}</td>
											</c:if>
											<td class='center'>
												<span class="td-content" title="${var.tscont}">${var.tscont}</span>
											</td>
											<c:if test="${empty pd.doaction }">
												<td class='center'>${var.tslevelname}</td>
											</c:if>
											<td class='center'>${var.tsdeptname}</td>
											<%-- <td class='center'>${var.tsbigtype}</td> --%>
											<td class='center'>${var.tstypename}</td>
											<c:if test="${empty pd.doaction }">
												<td class='center'>${var.tsclassifyname}</td>
												<td class='center'>${var.dealman}</td>
												<td class='center'>
													<c:if test="${var.ishf == '0' ||empty var.ishf}">否</c:if>
													<c:if test="${var.ishf == '1' }">是</c:if> 
												</td>
												<td class='center'>${var.endtime}</td>
												<td class='center'>${var.cljdname}</td>
												<td class='center'><c:if test="${var.type == 0 }">待反馈</c:if>
													<c:if test="${var.type == 1 }">工单已分派</c:if> <c:if
														test="${var.type == 3 }">正常为您处理</c:if> <c:if
														test="${var.type == 4 }">工单已关闭</c:if><c:if
														test="${var.type == 5 }">工单退回</c:if></td>
	
												<td class='center'>
													<c:if test="${not empty var.endreason}">
														${var.endreason}
													</c:if> 
													<c:if test="${empty var.endreason&&var.type == 4}">
														闭环
													</c:if> 
													
												</td>
											</c:if>
											<td class="center">

												<div>
													<c:if test="${empty pd.doaction }">
														<%if(jurisdiction.hasQx("70202")){ %>
														<a class="cy_bj" title="编辑" onclick="edit('${var.id}','');">
															<i title="编辑"></i>
														</a>
														<%} %>
														<%if(jurisdiction.hasQx("70208")){ %>
														<a class="cy_bj" title="查看" onclick="edit('${var.id}','search');">
															<i title="查看"></i>
														</a>
														<%} %>
														<%if(jurisdiction.hasQx("70203")){ %>
														<a style="margin-left:10px;" class="cy_sc"
															onclick="del('${var.id}');"> <i title="删除"></i>
														</a>
														<%} %>
													</c:if>
													<c:if test="${not empty pd.doaction }">
														<a style="margin-left:10px;" class="cy_bj"
															onclick="choicecode('${var.code}');"> <i title="选择"></i>
														</a>
													
													</c:if>


												</div>
											</td>
										</tr>

									</c:forEach>
									
								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="100" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>
						</tbody>
					</table>


				</div>

			</div>
		</section>

		<!-- /.content -->
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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<script type="text/javascript">
		//$(top.hangge());//关闭加载状态
		//检索
		var tables;
		function search(){
			//alert(tables.page.info().page);
			$("#Form").submit();
		}
		
		function choicecode(code){
			parent.document.getElementById("cfbm").value=code;
			parent.closeCftsLayer();
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
		
		function tosearch(){
			
			var keywords=$("#keywords").val();
			location.href="<%=path%>/workorder/list.do?action=${pd.action}&doctype_id=${pd.doctype_id}&keywords="+encodeURI(encodeURI(keywords));
			//$("#Form_s").submit();
		}
		$(function() {
			tables=$('#example2').DataTable({
				'paging'      : true,
			     'lengthChange': false,
			     'searching'   : true,
			     'ordering'    : false,
			     'info'        : true,
			     'autoWidth'   : true,
			     "iDisplayStart" :0*10,  //用于指定从哪一条数据开始显示到表格中去 num为20的时候，第一页是0，第二页是20.....
			     "iDisplayLength" :10, 
		    });
			
		    
		});
		
		
		
		var gdlayer;
		function closeLayer(){
			layer.close(gdlayer);//关闭当前页	
		}
		
		//新增
		function add(){
			var winId = "userWin";
			var type='${pd.type}';
			gdlayer=layer.open({
	   		  	type: 2,
	   		  	title: "工单新增",
	   		  	shade: 0.5,
	   		  	skin: 'demo-class',
	   		  	area:  ['100%', '100%'],
	   		  	content: '<%=basePath%>workorder/goAdd.do?type='+type
		    });
		  	<%-- modals.openWin({
	          	winId: winId,
	          	title: '新增',
	          	width: '1000px',
	          	height: '400px',
	          	url: '<%=basePath%>workorder/goAdd.do'
	          
	      	}); --%>
		}
		
		//删除
		function del(Id,Uid){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>workorder/delete.do?id="+Id+"&UID="+Uid+"&tm="+new Date().getTime();
					$.get(url,function(data){
						search();
					});
				}
			});
		}
		
		//修改
		function edit(Id,action){
			
			var winId = "userWin";
			var type='${pd.type}';
			var title="";
			if(action!=""){
				title="工单查看";
				
			}else{
				title="工单修改";
				action="";
			}
			gdlayer=layer.open({
	   		  	type: 2,
	   		  	title: title,
	   		  	shade: 0.5,
	   		  	skin: 'demo-class',
	   		  	area:  ['100%', '100%'],
	   		  	content: "<%=basePath%>workorder/goEdit.do?search="+action+"&type="+type+"&id="+Id
		    });
		}
		
		function docsearch(id){
			
			var winId = "userWin";
		  	modals.openWin({
	          	winId: winId,
	          	title: '查看',
	          	width: '900px',
	          	height: '400px',
	          	url: '<%=basePath%>workorder/goEdit.do?action=search&id='+id
	          	, hideFunc:function(){
	          		search();
	          	},
	      	});
			
		}
		
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>workorder/excel.do';
		}
		$(function () {
		
			$('.seat-middle').find(".row:first").hide();
			
			$(".xtyh-middle-r").find(".row:last").addClass("row-zg");
			$(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");
		});
	</script>
</body>
</html>