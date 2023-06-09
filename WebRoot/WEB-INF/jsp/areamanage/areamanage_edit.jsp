<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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


</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="modal-header">
						<h4 class="modal-title" id="myModalLabel" style="float: left;"></h4>
						<div class="new-tb" style="float: right;" data-dismiss="modal"
							title="关闭"></div>
					</div>
					<div class="row">
						<div class="col-xs-12">

							<form action="areamanage/${msg }.do" name="Form_add"
								id="Form_add" method="post">
								<input type="hidden" name="AREA_ID" id="AREA_ID" value="${pd.AREA_ID}" /> 
								<input type="hidden" name="PARENT_ID" id="PARENT_ID" value="${null == pd.PARENT_ID ? AREA_ID:pd.PARENT_ID}" />
								<div id="zhongxin">
									<div id="table_report" class="table table-striped table-bordered table-hover new-wkj">

										<div class="new-block">
											上级:<b>${null == pds.NAME ?'(无) 此为顶级':pds.NAME}（${pds.AREA_CODE}）</b>
										</div>

										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">名称:</div>
												<div class="one-wk-r">
													<input type="text" name="NAME" id="NAME" value="${pd.NAME}" maxlength="40" placeholder="这里输入名称" title="名称" />
												</div>
											</div>
											<span> </span>
										</div>


										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">地区编码：</div>
												<div class="one-wk-r">
													<c:if test="${msg=='save'}">
														<input type="text" name="AREA_CODE" id="AREA_CODE" value="${pds.AREA_CODE}" maxlength="40" placeholder="这里输入地区编码" title="地区编码" />
													</c:if>
													<c:if test="${msg!='save' }">
														<input type="text" name="AREA_CODE" id="AREA_CODE" value="${pd.AREA_CODE}" maxlength="40" placeholder="这里输入地区编码" title="地区编码" />
													</c:if>
												</div>
											</div>
											<span>输入的编码开头为上一级的编码</span>
										</div>

										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">地区级别:</div>
												<div class="one-wk-r">
													<input type="text" name="AREA_LEVEL" id="AREA_LEVEL"
														value="${pd.AREA_LEVEL}" maxlength="2"
														placeholder="这里输入地区级别" title="地区级别" style="width:98%;"
														readonly="readonly" />
												</div>
											</div>
											<span> </span>
										</div>

										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">联系人:</div>
												<div class="one-wk-r">
													<input type="text" name="linkman" id="linkman"
														value="${pd.linkman}" maxlength="32" placeholder="这里输入联系人"
														title="联系人" />
												</div>
											</div>
											<span> </span>
										</div>



										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">联系电话:</div>
												<div class="one-wk-r">
													<input type="text" name="linktel" id="linktel"
														placeholder="这里输入联系电话" title="联系电话" value="${pd.linktel}">
												</div>
											</div>
											<span> </span>
										</div>




										<div class="new-tk-body-one">
											<div class="new-tk-body-one-wk">
												<div class="one-wk-l">地址:</div>
												<div class="one-wk-r">
													<input type="text" name="address" id="address"
														value="${pd.address}" maxlength="100" placeholder="这里输入地址"
														title="地址" />
												</div>
											</div>
											<span> </span>
										</div>



										<div class="new-bc">
											<a onclick="save();">保存</a> <a onclick="saveweixin();">保存微信</a> <a class="new-qxbut"
												data-btn-type="cancel" data-dismiss="modal">取消</a>
										</div>

									</div>
								</div>

								<div id="zhongxin2" class="center" style="display:none">
									<br />
									<br />
									<br />
									<br />
									<br />
									<img src="static/images/jiazai.gif" /><br />
									<h4 class="lighter block green">提交中...</h4>
								</div>

							</form>

							<div id="zhongxin2" class="center" style="display:none">
								<img src="static/images/jzx.gif" style="width: 50px;" /><br />
								<h4 class="lighter block green"></h4>
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


	<!--提示框-->
	<script type="text/javascript">


		function saveweixin(){
			var name=$("#NAME").val();
			var AREA_CODE=$("#AREA_CODE").val();
			var url="";
			if ("${msg }"=="save"){
				 // url='http://luyin.ada-robotics.com/weixin/app_department/save?AREA_ID='+AREA_CODE+'&PARENT_ID=1&NAME='+name;
				 url='http://luyin.ada-robotics.com/weixin/app_department/save?AREA_ID='+AREA_CODE+'&PARENT_ID=1&NAME='+name;
			}else if ("${msg }"=="edit"){
				 url='http://luyin.ada-robotics.com/weixin/app_department/update?AREA_ID='+AREA_CODE+'&PARENT_ID=1&NAME='+name;
			}

			$.ajax({
				// type: "POST",
				url: url ,
				// data: {AREA_ID:AREA_CODE,PARENT_ID:'1',NAME:name},
				dataType:'jsonp',
				crossDomain: true,
				cache: false,
				success: function(data){
					console.log(data);
					if("success" == data){
						console.log("成功");
						//$("#zhongxin").hide();
						//$("#zhongxin2").show();
					}else{
						console.log("失败");
					}
				}
			});
			alert("同步微信成功，请进行保存");
		}


		//$(top.hangge());
		//保存
		function save(){
			if($("#NAME").val()==""){
				$("#NAME").tips({
					side:3,
		            msg:'请输入名称',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#NAME").focus();
				return false;
			}
			if($("#AREA_CODE").val()==""){
				$("#AREA_CODE").tips({
					side:3,
		            msg:'请输入地区编码',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#AREA_CODE").focus();
			return false;
			}
			if($("#AREA_LEVEL").val()==""){
				$("#AREA_LEVEL").tips({
					side:3,
		            msg:'请输入地区级别',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#AREA_LEVEL").focus();
			return false;
			}
			var formData = $('#Form_add').serialize();
			formData += "&action=" + '${msg}';
			$.ajax({
	            //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "json",//预期服务器返回的数据类型
                async: false,
                url: "<%=basePath%>areamanage/checkCode.do" ,//url
                data: formData,
                success: function (data) {
					console.log(data);//打印服务端返回的数据(调试用)
					if(data.success){
						$("#zhongxin").hide();
						$("#zhongxin2").show();
						$.ajax({
				            //几个参数需要注意一下
			                type: "POST",//方法类型
			                dataType: "html",//预期服务器返回的数据类型
			                url: "areamanage/${msg}.do" ,//url
			                data: $('#Form_add').serialize(),
			                success: function (result) {
			                  //  console.log(result.);//打印服务端返回的数据(调试用)
			                    if (result.indexOf("success")>=0) {
			                    	parent.location.href="<%=basePath%>areamanage/listTree.do?AREA_ID=${AREA_ID}";
									}
								},
								error : function() {
									alert("异常！");
								}
							});
							} else {
								$("#AREA_CODE").tips({
									side : 3,
									msg : '你输入的编号已存在,请重新输入',
									bg : '#AE81FF',
									time : 2
								});
								$("#AREA_CODE").focus();
							}
						},
						error : function() {
							alert("异常！");
						}
					});
		}
	</script>
</body>
</html>