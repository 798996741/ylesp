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
<div class="content-wrapper" style="width:100%;margin-left:0px;">
    <section class="container-fluid">
			<div class="seat-middle-top-x"></div>
			<div class="seat-middle-top">
				<div class="seat-middle-top-left">
					<div class="seat-middle-top-left-bt">人员信息配置</div>
					<div class="seat-middle-top-left-tp">
						<%if(jurisdiction.hasQx("31001")){ %>
							
							<a href="javascript:void (0)" onclick="productCode('add');">新增</a>
						<%} %>
					</div>
				</div>
				<div class="seat-middle-top-right">
					 <input placeholder="搜  索" name="keywords" id="keywords"
						value="${pd.keywords }"> <a href="javascript:void(0)">
						<img src="static/login/images/icon-search.png"
						onclick="search_sumbit()">
					</a>
				</div>
			</div>
			 <div class="seat-middle">
                    <div class="xtyh-middle-r zxzgl-middle-r">
					<table id="example2" class="table table-bordered table-hover">
						<thead>
							<tr>

								<th class="center cy_th">编号</th>
								<th id='cy_thk'></th>
								<th class="center">名称</th>
								<th class="center">表名</th>
								<th class="center">类型</th>
								<th class="center">是否字典</th>
								<th class="center">创建人</th>
								<th class="center">创建时间</th>
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
											<td class="center">${var.C_PHYSICSNAME}</td>
											<td class="center">${var.DICTIONARIES_NAME}</td>
											<td class="center"><c:choose>
													<c:when test="${var.TB_TYPE == '1'}">
														是
													</c:when>
													<c:otherwise>
														否
													</c:otherwise>
												</c:choose></td>
											<td class="center">${var.CREATEMAN}</td>
											<td class="center">${var.CREATEDATE}</td>
											<td class="center">
												<%if(jurisdiction.hasQx("31002")){ %>
														<a style="margin-left: 10px"
															onclick="productCode('${var.TEMPLATE_ID}');"> 编辑
														</a>
												<%}%>
												<%if(jurisdiction.hasQx("31003")){ %>
													<a style="margin-left: 10px"
													onclick="del('${var.TEMPLATE_ID}','${var.C_PHYSICSNAME}','${var.TYPE}');">
													刪除 </a>
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
		//$(top.hangge());//关闭加载状态
		function search_sumbit(){
			 $("#search_form").submit();
		}
		function search_cancel(){
			location.href="<%=basePath%>templates/list.do";
		}
		//检索
		function tosearch(){
			//top.jzts();
			location.href="<%=basePath%>templates/list.do";
		}
		
		function home(){
			parent.parent.document.getElementById("mainFrame").src="<%=basePath%>login_default.do";
		}

		
		$(function() {
			$('#example2').DataTable({
		      'paging'      : true,
		      'lengthChange': false,
		      'searching'   : false,
		      'ordering'    : false,
		      'info'        : true,
		      'autoWidth'   : true
		    })
		});
		
		//启动代码生成器

		function productCode(TEMPLATE_ID){
			newmylay = layer.open({
	   		  	type: 2,
	   		  	title: "表单配置",
	   		  	shade: 0.5,
	   		  	skin: 'demo-class',
	   		  	area:  ['100%', '100%'],
	   		  	content: '<%=basePath%>templates/goProduct.do?TEMPLATE_ID='+TEMPLATE_ID+'&BIANMA=005',
	   		  	end: function () {
		             location.reload();
		        }
		    });
			 
			
		}
		
		//新增
		function add(){
			/* var winId = "userWin";
		  	modals.openWin({
	          	winId: winId,
	          	title: '新增',
	          	width: '700px',
	          	height: '550px',
	          	url: 
	          	, hideFunc:function(){
	             	 modals.info("hide me");
	          	},
	          	showFunc:function(){
	              	modals.info("show me");
	          	}
	      	}); */
		  	
		  	layer.open({
		   		  type: 2,
		   	      title: "新增",
		   		  skin: 'demo-class',
		   		  shade: 0.5,
		   		  area:  ['100%', '100%'],
		   		  content: ['<%=basePath%>templates/goAdd.do', 'no'],
		   		  end: function () {
	                location.reload();
	         	   }
			});
			
		}
		
		//删除
		function del(Id, C_PHYSICSNAME, TYPE){
			layer.confirm("确定要删除吗?", function(result) {
				if(result) {
					//top.jzts();
					var url = "<%=basePath%>templates/delete.do?TEMPLATE_ID="+Id+"&C_PHYSICSNAME=" + C_PHYSICSNAME +"&tm="+new Date().getTime()+"&TYPE="+TYPE;
					$.get(url,function(data){
						tosearch();
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			/* var winId = "userWin";
		  	modals.openWin({
	          	winId: winId,
	          	title: '编辑',
	          	width: '580px',
	          	height: '355px',
	          	url: 
	          	, hideFunc:function(){
	             	 modals.info("hide me");
	          	},
	          	showFunc:function(){
	              	modals.info("show me");
	          	} 
	      	}); */
		  	
		  	layer.open({
		   		  type: 2,
		   	      title: "编辑",
		   		  skin: 'demo-class',
		   		  shade: 0.5,
		   		  area:  ['100%', '100%'],
		   		  content: ['<%=basePath%>templates/goEdit.do?TEMPLATE_ID='+Id+'&BIANMA=005', 'no'],
		   		  end: function () {
	                location.reload();
	         	   }
			});
		}
		
		//修改模板字段
		function editTColumn(Id){
			/* var winId = "userWin";
		  	modals.openWin({
	          	winId: winId,
	          	title: '编辑模板字段',
	          	width: '580px',
	          	height: '355px',
	          	url: 
	          	, hideFunc:function(){
	             	 modals.info("hide me");
	          	},
	          	showFunc:function(){
	              	modals.info("show me");
	          	} 
	      	}); */
		  	
			layer.open({
		   		  type: 2,
		   	      title: "编辑模板字段",
		   		  skin: 'demo-class',
		   		  shade: 0.5,
		   		  area:  ['100%', '100%'],
		   		  content: ['<%=basePath%>templates/goEdit.do?TEMPLATE_ID='+Id, 'no'],
		   		  end: function () {
	                location.reload();
	         	   }
			});
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  }
					}
					if(str==''){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择任何内容!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						$("#zcheckbox").tips({
							side:1,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>templates/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											tosearch();
									 });
								}
							});
						}
					}
				}
			});
		};
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>templates/excel.do';
		}
		function set(){
			var dataJson = {};
			dataJson.tab= "data_unit_tmp";
			dataJson.col= "name";
			$.ajax({
		    	type: "POST",//方法类型
		    	dataType: 'json',
		    	contentType: 'application/json',
				type : 'post',
				data: JSON.stringify(dataJson),
		     	url: "templates/doSetPy.do",//url
		        success: function (result) {
		        	if(result.error==0){
		        		
		        	}	
		        },
		        error : function() {
		        	closeLoading();
		        }
		    });
		}
	</script>

</body>
</html>
