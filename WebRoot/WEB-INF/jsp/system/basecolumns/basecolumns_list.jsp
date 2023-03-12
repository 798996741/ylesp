<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="com.fh.util.Jurisdiction"%>
<%
	Jurisdiction jurisdiction=new Jurisdiction();
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<%@ include file="../../system/include/incJs_mx.jsp"%>


<!-- 日期框 -->
</head>
<body class="no-skin">


	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper" style="width:100%;margin-left:0px;">

		<section class="container-fluid">
			<div class="seat-middle-top-x"></div>
			<div class="seat-middle-top">
				<div class="seat-middle-top-left">
					<div class="seat-middle-top-left-bt">基础字段配置</div>
					<div class="seat-middle-top-left-tp">
						
					</div>
				</div>
				<%--<div class="seat-middle-top-right">
					 <input placeholder="搜  索" name="keywords" id="keywords"
						value="${pd.keywords }"> <a href="javascript:void(0)">
						<img src="static/login/images/icon-search.png"
						onclick="tosearch()">
					</a>
				</div> --%>
			</div>
			<div class="seat-middle">
				<div class="xtyh-middle-r zxzgl-middle-r">

					<table id="example2" class="table table-bordered table-hover">
						<thead>
							<tr>

								<th class="center cy_th">编号</th>
								<th id='cy_thk'></th>
								<th class="center">名称</th>
								<th class="center">操作</th>
							</tr>
						</thead>
						<tbody>

							<c:choose>
								<c:when test="${not empty varList}">
									<c:forEach items="${varList}" var="var" varStatus="vs">

										<tr>
											<td class='center cy_td'>${vs.index+1}</td>
											<td id='cy_thk'></td>

											<td class="center">${var.NAME}</td>
											<td class="center" style="width:10%">
												<%if(jurisdiction.hasQx("30901")){ %> 
												<div class="button-edit" title="编辑"
													onclick="goEdit('${var.TEMPLATE_ID}');" title="编辑">
													<font class="button-content">编辑</font>
												</div>
												<%}%>
											</td>
										</tr>

									</c:forEach>

								</c:when>
								<c:otherwise>
									<tr class="main_info">
										<td colspan="10" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>

						</tbody>

					</table>


				</div>
			</div>
		</section>
		
	</div>
	
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
	
	  	$(function () {
	  
		    $('#example2').DataTable({
		      'paging'      : true,
		      'lengthChange': false,
		      'searching'   : false,
		      'ordering'    : true,
		      'info'        : true,
		      'autoWidth'   : true
		
		    })
	  	})
	
		
		function  goEdit(TEMPLATE_ID){
			newmylay = layer.open({
				  type: 2,
				  title: "表单配置",
				  shade: 0.5,
				  skin: 'demo-class',
				  area:  ['100%', '100%'],
				  content: '<%=basePath%>basecolumns/goEdit.do?TEMPLATE_ID='+TEMPLATE_ID,
		
		    });
			
		}
	</script>
</body>
</html>
