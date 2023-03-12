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
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<!-- jsp文件头和头部 -->
<%-- 
<%@ include file="../index/top.jsp"%> --%>
<%@ include file="../../system/include/incJs_mx.jsp"%>



<style>
@media screen and (max-width: 1700px) {
	.xtyh-middle-r {
		width: 100% !important;
	}
}

.zxzgl-middle-r {
	width: 100% !important;
}

</style>
	<script src="static/js/echarts.min.js"></script>
</head>
<body class="no-skin">


	<div class="content-wrapper" style="width:100%;margin-left:0px;">


		<section class="container-fluid">
			<div class="seat-middle-top-x"></div>
			<div class="seat-middle-top">
				<div class="seat-middle-top-left">
					<div class="seat-middle-top-left-bt">投诉统计</div>
					<div class="seat-middle-top-left-tp">
						开始日期：
						<input style="margin:0px" class="date-picker" name="starttime" id="starttime" autoComplete="off" title="开始时间"
							   placeholder="开始时间" value="${pd.starttime}" type="text"
							   data-date-format="yyyy-mm-dd"/>
						结束日期：
						<input style="margin:0px" class="date-picker" name="endtime" id="endtime" autoComplete="off" title="开始时间"
							   placeholder="结束时间" value="${pd.endtime}" type="text"
							   data-date-format="yyyy-mm-dd"/>
						投诉部门: <select id="tsdepts" name="tsdepts">
						<option value="">全部</option>
						<c:forEach items="${area}" var="var" varStatus="vs">
							<option value="${var.area_code}"
									<c:if test="${pd.tsdept==var.area_code}">selected</c:if>>${var.NAME }</option>
						</c:forEach>
					</select>
						<input type="button" style="width:60px" class="btn btn-mini btn-success" onclick="search();" value="查询">

					</div>
				</div>
				<div class="seat-middle-top">
					<input type="button" style="width:60px;margin-left:30px " class="btn btn-mini btn-success" onclick="searchmon('1');" value="本周">
					<input type="button" style="width:60px;margin-left:30px " class="btn btn-mini btn-success" onclick="searchmon('2');" value="本月">
					<input type="button" style="width:60px;margin-left:30px " class="btn btn-mini btn-success" onclick="searchmon('3');" value="本季">
					<input type="button" style="width:60px;margin-left:30px " class="btn btn-mini btn-success" onclick="searchmon('4');" value="本年">
				</div>
			</div>
			<div class="seat-middle">
				<!--  <div class="seat-middle-nr"> -->

				<div class="xtyh-middle-r zxzgl-middle-r">
					<!-- <div class="box-body" > -->
					<div>投诉类别（大项）</div>
					<div id="main" style="width: 800px;height:300px;"></div>
                    <table>
                        <thead>
                        <tr>
                            <th class="center">序号</th>
                            <th class="center">名称</th>
                            <th class="center">投诉量</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${list1}" var="var1" varStatus="vs1">
                                    <tr>
                                        <td>${vs1.count}</td>
                                        <td>${var1.key}</td>
                                        <td>${var1.value}</td>
                                    </tr>
                        </c:forEach>
                        </tbody>
                    </table>

				</div>

				<div class="xtyh-middle-r zxzgl-middle-r">
                    <div>投诉类别（细项）</div>
                    <div id="main1" style="width: 800px;height:300px;"></div>
					<table>
						<thead>
						<tr>
						<th class="center">序号</th>
						<th class="center">名称</th>
						<th class="center">投诉量</th>
						</tr>
						</thead>
						<tbody>
						<c:forEach items="${list2}" var="var1" varStatus="vs1">
									<tr>
										<td>${vs1.count}</td>
										<td>${var1.key}</td>
										<td>${var1.value}</td>
									</tr>
						</c:forEach>
						</tbody>
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
	<!-- 获取本周、本月、本季度、本年、时间戳格式化方法 -->
	<script src="static/js/GetTimeUtil.js"></script>

	<script type="text/javascript">
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('main'));
		var myChart2 = echarts.init(document.getElementById('main1'));
		window.onload = function () {
			var array1=[];
            var array2=[];
			<c:forEach items="${data1}" var="var" varStatus="vs">
			    array1.push('${var.key}');
			    array2.push('${var.value}');
			</c:forEach>


			//柱状图
			option = {
				color: ['#3398DB'],
				tooltip: {
					trigger: 'axis'
				},
				grid: {
					left: '3%',
					right: '4%',
					bottom: '3%',
					containLabel: true
				},
				xAxis: [
					{
						name:'投诉类别（大项）',
						type: 'category',
						data:array1,
						axisTick: {
							alignWithLabel: true
						}
					}
				],
				yAxis: [
					{
						name:'投诉量',
						type: 'value'
					}
				],
				series: [
					{
						name: '建议量',
						type: 'bar',
						barWidth: '50%',
						data: array2
					}
				]
			};
			myChart.setOption(option);

            var array3=[];
            var array4=[];
            <c:forEach items="${data2}" var="var" varStatus="vs">
            array3.push('${var.key}');
            array4.push('${var.value}');
            </c:forEach>
            //柱状图
            option2 = {
                color: ['#3398DB'],
                tooltip: {
                    trigger: 'axis'
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis: [
                    {
						name:'投诉类别（细项）',
                        type: 'category',
                        data:array3,
                        axisTick: {
                            alignWithLabel: true
                        }
                    }
                ],
                yAxis: [
                    {
						name:'投诉量',
                        type: 'value'
                    }
                ],
                series: [
                    {
						name:'建议量',
                        type: 'bar',
                        barWidth: '50%',
                        data: array4
                    }
                ]
            };
            myChart2.setOption(option2);
		}

		function search() {
			var starttime=$("#starttime").val();
			var endtime=$("#endtime").val();
			var tsdepts=$("#tsdepts").val();
			location.href="<%=path%>/complain/tstypedata.do?starttime="+starttime+
					"&endtime="+endtime+"&tsdept="+tsdepts;
		}
		function searchmon(mon) {
			var tsdepts=$("#tsdepts").val();
			if (mon=='1'){
				//本周
				location.href="<%=path%>/complain/tstypedata.do?starttime="+getWeekStartDate()+
						"&endtime="+getWeekEndDate()+"&tsdept="+tsdepts;
			}else if (mon=='2'){
				//本月
				location.href="<%=path%>/complain/tstypedata.do?starttime="+getMonthStartDate()+
						"&endtime="+getMonthEndDate()+"&tsdept="+tsdepts;
			}else if (mon=='3'){
				//本季
				location.href="<%=path%>/complain/tstypedata.do?starttime="+getQuarterStartDate()+
						"&endtime="+getQuarterEndDate()+"&tsdept="+tsdepts;
			}else if (mon=='4'){
				//本年
				location.href="<%=path%>/complain/tstypedata.do?starttime="+getYearStartDate()+
						"&endtime="+getYearEndDate()+"&tsdept="+tsdepts;
			}
		}
		$('.date-picker').datepicker({
			autoclose: true,
			todayHighlight: true,
			clearBtn: true
		});
		$(function () {
			$('.seat-middle').find(".row:first").hide();
			$(".xtyh-middle-r").find(".row:last").addClass("row-zg");
			$(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");
			
		});
		
	</script>
</body>
</html>