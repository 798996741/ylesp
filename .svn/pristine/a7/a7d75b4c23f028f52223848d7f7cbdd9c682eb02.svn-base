<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">

<!-- jsp文件头和头部 -->
<%@ include file="../../system/include/incJs_mx.jsp"%>
<jsp:include page="../../system/include/incJs.jsp"></jsp:include>
<style>
	.col-md-4{
		width:250px
	}
</style>
<script >
	function add(){
		location.href="rutask/list.do?type=1";
	}
</script>
</head>

<body class="no-skin" style="background:rgb(236,240,245);margin:10px;overflow: hidden; ">

	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">

		<section class="content" style="background:rgb(236,240,245);overflow: hidden;">

			<div class="row">

				<section class="content-header padbottom15">
					<h1>
						&nbsp;&nbsp;&nbsp;工单处理状况
					</h1>
					<ol class="breadcrumb">
						<li class="active"></li>
					</ol>
				</section>
				<div class="row card-row" style="padding-left:10px;">
					<div class="col-md-4 col-sm-6 col-xs-12" style="cursor:pointer;">
						<div class="info-box card-box" onclick="dcl('0')">
							<div class="info-box-top">
								<span class="info-box-title">待处理工单</span><span
									class="info-icon"><i
									class="icon icon-chengse font20 icon-guanzhu"></i></span>
							</div>
							<div class="info-box-bottom">
								<span class="info-number">${wclCount}个</span>
							</div>
						</div>
					</div>
					
					<div class="col-md-4 col-sm-6 col-xs-12" style="cursor:pointer">
						<div class="info-box card-box"  onclick="dcl('1')">
							<div class="info-box-top">
								<span class="info-box-title">处理中工单</span><span
									class="info-icon"><i
									class="icon icon-chengse font20 icon-guanzhu"></i></span>
							</div>
							<div class="info-box-bottom">
								<span class="info-number">${clzCount}个</span>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-6 col-xs-12" style="cursor:pointer">
						<div class="info-box card-box" onclick="dcl('3')">
							<div class="info-box-top">
								<span class="info-box-title" >待审核工单</span><span
									class="info-icon"><i
									class="icon icon-chengse font20 icon-guanzhu"></i></span>
							</div>
							<div class="info-box-bottom">
								<span class="info-number">${dshCount}个</span>
							</div>
						</div>
					</div>
					<div class="col-md-4 col-sm-6 col-xs-12" style="cursor:pointer">
						<div class="info-box card-box" onclick="dcl('4')">
							<div class="info-box-top">
								<span class="info-box-title" >已处理工单</span><span
									class="info-icon"><i
									class="icon icon-chengse font20 icon-guanzhu"></i></span>
							</div>
							<div class="info-box-bottom">
								<span class="info-number">${yclCount}个</span>
							</div>
						</div>
					</div>
				</div>
				
				<c:if test="${allruCount>0}">
					<section class="content-header padbottom15" style="margin-top:20px">
						<h1>
							&nbsp;&nbsp;&nbsp;工单列表
						</h1>
						<ol class="breadcrumb">
							<li class="active"></li>
						</ol>
					</section>
					<div class="row card-row" style="padding-left:10px;">
						<div class="col-md-4 col-sm-6 col-xs-12" style="cursor:pointer;width:300px;">
							<div class="info-box card-box" style="height:150px">
								<div class="info-box-top">
									<span class="info-box-title">您有${allruCount}个工单即将超时，请速处理！</span><span
										class="info-icon"><i
										class="icon icon-chengse font20 icon-guanzhu"></i></span>
								</div>
								<div class="info-box-bottom" style="text-align:center">
									<input type="button" style="width:70px" class="btn btn-mini btn-success" onclick="add();" value="去处理">
								</div>
							</div>
						</div>
					</div>	
				</c:if>	
				
			</div>
	

		</section>
	</div>

	<script type="text/javascript" src="static/ace/js/jquery.js"></script>
	<script>
		function dcl(type){
			location.href="<%=path%>/workorder/list.do?type="+type;
		}
		
		function add(type){
			location.href="<%=path%>/rutask/list.do";
		}
	</script>
</body>
</html>