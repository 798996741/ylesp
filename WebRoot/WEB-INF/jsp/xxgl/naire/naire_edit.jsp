<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
	
	<!-- 日期框 -->
<link rel="stylesheet" href="static/ace/css/datepicker.css" />
<!-- 日期框 -->
<link rel="stylesheet" href="plugins/ueditor/themes/default/css/ueditor.css"/>
<script>
	 
	</script>
</head>
<body class="no-skin">
<!-- /section:basics/navbar.layout -->
<div class="main-container" id="main-container">
	<!-- /section:basics/sidebar -->
	<div class="main-content">
		<div class="main-content-inner">
			<div class="page-content">
				<div class="modal-header">
					<h4 class="modal-title" id="myModalLabel" style="float: left;">	</h4>
                 	<div  class="new-tb" style="float: right;"   data-dismiss="modal" title="关闭"></div>
				</div>
				<div class="row">
					<div class="col-xs-12">
					
					<form action="naire/${msg }.do" name="Form_add" id="Form_add" method="post">
						<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
						<input type="hidden" name="NAIRE_TEMPLATE_ID" id="NAIRE_TEMPLATE_ID" value="${pd.NAIRE_TEMPLATE_ID}"/>
						<div id="zhongxin" >
						<div id="table_report" class="table table-striped table-bordered table-hover new-wkj">
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">题目类型:</div>
									<div class="one-wk-r">
										<select id="TYPE" name="TYPE"  >
											<c:forEach items="${dictList}" var="var" varStatus="vs">
												<option value="${var.DICTIONARIES_ID }" <c:if test="${pd.TYPE==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<span>*</span>
							</div>
							
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">编号:</div>
									<div class="one-wk-r"><input type="number" name="CODE" id="CODE" value="${pd.CODE}" maxlength="30" placeholder="这里输入编号" title="编号" /></div>
								</div>
								<span>*</span>
							</div>
							
							
					
							
							<div class="new-tk-body-one new-tx">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">题目:</div>
									<div class="one-wk-r">
										<textarea  id="SUBJECT" name="SUBJECT" cols="5">${pd.SUBJECT}</textarea>
									</div>
								</div>
								<span>*</span>
							</div>
							
							
							<div class="new-tk-body-one new-tx">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">答案:</div>
									<div class="one-wk-r">
										<textarea  id="ANSWER" name="ANSWER" cols="5">${pd.ANSWER}</textarea>
									</div>
								</div>
								<span>*</span>
							</div>
							
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">提示信息:</div>
									<div class="one-wk-r">
										<input type="text" name="REMARK" id="REMARK" value="${pd.REMARK}" maxlength="500" placeholder="这里输入提示信息" title="提示信息" />
									</div>
								</div>
								<span>*</span>
							</div>
							
							
							<div class="new-bc">
								<c:if test="${empty action}">
									<a  onclick="save();">保存</a>
								</c:if>
							</div>
							
						</div>
						</div>
						<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
					</form>
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
<!-- 日期框 -->
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script>


		<script type="text/javascript">
		//$(top.hangge());
		//保存
		//var ue = UE.getEditor("SUBJECT");
		$('.date-picker').datepicker({
			autoclose: true,
			todayHighlight: true,
			clearBtn: true
		});
		$(function() {
			
			
		});
		
		
		function save(){
			if($("#CODE").val()==""){
				$("#CODE").tips({
					side:3,
		            msg:'请输入编号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#CODE").focus();
				return false;
			}
			if($("#ANSWER").val()==""){
				$("#ANSWER").tips({
					side:3,
		            msg:'请输入答案',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ANSWER").focus();
				return false;
			}
			
			
			//$("#zhongxin").hide();
			//$("#zhongxin2").show();
			$.ajax({
            //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "html",//预期服务器返回的数据类型
                url: "naire/${msg }.do" ,//url
                data: $('#Form_add').serialize(),
                success: function (result) {
                  // alert(result);//打印服务端返回的数据(调试用)
                	if (result.indexOf("success_")>=0) {
                		$("#naireWin_childs").modal('hide');
                		$('#naireWin_childs').on('hide.bs.modal', function () {
	                         //$("#reason").val("");
	                    });
	                  	
	                  	setmb('${pd.NAIRE_TEMPLATE_ID}');
	                  	
                  	}else{
                  		modals.info("修改失败请检查编码是否已经存在");
                  	} 
                	//$("#zhongxin").hide();
        			//$("#zhongxin2").hide();
                },
                error : function() {
                    alert("异常！");
                }
            });
		}
		</script>
</body>
</html>