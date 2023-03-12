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
<link type="text/css" rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css"/>
	<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core.js"></script>
<style>
	ul li{line-height:30px;list-style-type:none}
</style>
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
					<div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>
				</div>
				
				<div class="row">
					<div class="col-xs-12">
					
					<form action="zxlb/${msg }.do" name="Form_add" id="Form_add" method="post">
						<input type="hidden" name="ID" id="ID" value="${pd.ID}"/>
						<div id="zhongxin" style="padding-top: 13px;">
						<div id="table_report" class="table table-striped table-bordered table-hover new-wkj">
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">角色:</div>
									<div class="one-wk-r">
										<select name="ROLE_ID" id="role_id1" data-placeholder="请选择角色" >
											<option value=""></option>
											<c:forEach items="${rolefrontList}" var="role">
												<option value="${role.ROLE_ID }"
													<c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<span> </span>
							</div>
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">所属单位:</div>
									<div class="one-wk-r">
										<input type="hidden" id="dept" name="dept" value="${pd.dept }">
										<input id="citySel" name="" type="text" readonly value="${pd.areaname }"  onclick="showMenu(); return false;"/>
									</div>
								</div>
								<span> </span>
							</div>
								<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">坐席ID:</div>
									
									<div class="one-wk-r">
										<input type="hidden" name="chk_zxid" id="chk_zxid" >
										<input type="text" name="ZXID" id="ZXID" value="${pd.ZXID}" maxlength="30" placeholder="这里输入坐席ID" title="坐席ID" onblur="hasU()" />
									</div>
								</div>
								<span>*</span>
							</div>
							
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">坐席分机:</div>
									<div class="one-wk-r"><input type="text" name="FJHM" id="FJHM" value="${pd.FJHM}" maxlength="30" placeholder="这里输入坐席分机" title="坐席分机" /></div>
								</div>
								<span>*</span>
							</div>
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">坐席角色:</div>
									<div class="one-wk-r">
										<select id="ZXJS" name="ZXJS"  >
											<c:forEach items="${roleList}" var="var" varStatus="vs">
												<option value="${var.DICTIONARIES_ID }" <c:if test="${pd.ZXJS==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<span>*</span>
							</div>
							
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">坐席姓名:</div>
									<div class="one-wk-r"><input type="text" name="ZXXM" id="ZXXM" value="${pd.ZXXM}" maxlength="50" placeholder="这里输入坐席姓名" title="坐席姓名" /></div>
								</div>
								<span>*</span>
							</div>
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">登录密码:</div>
									<div class="one-wk-r"><input type="password" name="PWD" id="PWD" value="" maxlength="50" placeholder="这里输入登录密码" title="登录密码" /></div>
								</div>
								<span>*</span>
							</div>
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">确认密码:</div>
									<div class="one-wk-r"><input type="password" name="CHKPWD" id="CHKPWD" value="" maxlength="50" placeholder="这里输入登录密码" title="登录密码" /></div>
								</div>
								<span>*</span>
							</div>
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">系统用户:</div>
									<div class="one-wk-r">
										<select id="ZXYH" name="ZXYH" >
											<c:forEach items="${userList}" var="var" varStatus="vs">
												<option value="${var.USERNAME }" <c:if test="${pd.ZXYH==var.USERNAME}">selected</c:if>>${var.USERNAME }-${var.NAME }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<span>*</span>
							</div>
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">状态:</div>
									<div class="one-wk-r">
										<select id="ZT" name="ZT" >
											<option value="0" <c:if test="${pd.ZT==0}">selected</c:if>>正常</option>
											<option value="-1" <c:if test="${pd.ZT==-1}">selected</c:if>>冻结</option>
										</select>
									</div>
								</div>
								<span>*</span>
							</div>
							
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">坐席分组:</div>
									<div class="one-wk-r">
										<select id="ZXZ" name="ZXZ"  >
											<c:forEach items="${fzList}" var="var" varStatus="vs">
												<option value="${var.ID }" <c:if test="${pd.ZXZ==var.ID}">selected</c:if>>${var.ZMC }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<span>*</span>
							</div>
							
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">坐席类型:</div>
									<div class="one-wk-r">
										<select id="ZXLX" name="ZXLX"  >
											<c:forEach items="${dictList}" var="var" varStatus="vs">
												<option value="${var.DICTIONARIES_ID }" <c:if test="${pd.ZXLX==var.DICTIONARIES_ID}">selected</c:if>>${var.NAME }</option>
											</c:forEach>
										</select>
									</div>
								</div>
								<span>*</span>
							</div>
							
							
							
							
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">坐席工号:</div>
									<div class="one-wk-r"><input type="text" name="ZXGH" id="ZXGH" value="${pd.ZXGH}" maxlength="50" placeholder="这里输入坐席工号" title="坐席工号" /></div>
								</div>
								<span>*</span>
							</div>
							
							
							<div class="new-bc">
								<a onclick="save();">保存</a>
								<a class="new-qxbut"  data-btn-type="cancel" data-dismiss="modal">取消</a>
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
<div id="menuContent" class="menuContent" style="min-height:250px;display:none; position: absolute;background:rgb(238,242,245);border:1px #ccc solid">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:200px;"></ul>
</div>
<!-- /.main-container -->
<!-- 日期框 -->
<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
<script type="text/javascript" charset="utf-8">window.UEDITOR_HOME_URL = "<%=path%>/plugins/ueditor/";</script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="plugins/ueditor/ueditor.all.js"></script>


	<script type="text/javascript">
	
	var setting = {
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			beforeClick: beforeClick,
			onClick: onClick
		}
	};
	var zn = '${zTreeNodes}';
	var zNodes = eval(zn);

	

	function beforeClick(treeId, treeNode) {
		//var check = (treeNode && !treeNode.isParent);
		//if (!check) alert("只能选择城市...");
		//return check;
	}
	
	function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
		nodes = zTree.getSelectedNodes(),
		v = "";
		nodes.sort(function compare(a,b){return a.id-b.id;});
		for (var i=0, l=nodes.length; i<l; i++) {
			v += nodes[i].name + ",";
		}
		if (v.length > 0 ) v = v.substring(0, v.length-1);
		var cityObj = $("#citySel");
		cityObj.attr("value", v);
		$("#dept").val(treeNode.AREA_CODE);
		hideMenu();
	}

	function showMenu() {
		
		var cityObj = $("#citySel");
		var cityOffset = $("#citySel").offset();
		$("#menuContent").css({left:(cityOffset.left-120) + "px", top:(cityOffset.top-40) + cityObj.outerHeight() + "px"}).slideDown("fast");
		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#menuContent").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
			hideMenu();
		}
	}
	$(document).ready(function(){
		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        treeObj.expandAll(true);
		if('${pd.AREA_ID}'!=""){
			//treeObj.checkNode(treeObj.getNodeByParam("id", '${pd.AREA_ID}'), true);
		}
		
		
	});
		//$(top.hangge());
		//保存
		//var ue = UE.getEditor("DETAIL");
		$('.date-picker').datepicker({
			autoclose: true,
			todayHighlight: true,
			clearBtn: true
		});
		
		
		//判断用户名是否存在
		function hasU(){
			var ZXID = $.trim($("#ZXID").val());
			var ID = $.trim($("#ID").val());
			$.ajax({
				type: "POST",
				url: '<%=basePath%>zxlb/hasU.do',
		    	data: {ZXID:ZXID,ID:ID,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					 if("success" == data.result){
						//$("#userForm_add").submit();
						//$("#zhongxin").hide();
						//$("#zhongxin2").show();
						$("#chk_zxid").val('');
					 }else{
						$("#chk_zxid").val('1');
						$("#ZXID").tips({
							side:3,
				            msg:'您输入的坐席id不正确，坐席id已存在，请重新输入！',
				            bg:'#AE81FF',
				            time:2
				        });
					 }
				}
			});
		}
		
		
		function save(){
			var chk_zxid=$("#chk_zxid").val();
			if($("#ZXID").val()==""){
				$("#ZXID").tips({
					side:3,
		            msg:'请输入坐席ID',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ZXID").focus();
				return false;
			}else{
				 if(isNaN($("#ZXID").val())){
					 $("#ZXID").tips({
						side:3,
			            msg:'您输入的坐席id不正确，坐席id为数字！',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#ZXID").focus();
					return false;
				 }
				 
				 if(chk_zxid=='1'){
					 $("#ZXID").tips({
						side:3,
			            msg:'您输入的坐席id不正确，坐席id已存在，请重新输入！',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#ZXID").focus();
					$("#ZXID").val('');
					return false;
				 }
				
			}
			
			
			if($("#FJHM").val()==""){
				$("#FJHM").tips({
					side:3,
		            msg:'请输入坐席分机',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#FJHM").focus();
				return false;
			}else{
				 if(isNaN($("#FJHM").val())){
					 $("#FJHM").tips({
						side:3,
			            msg:'您输入的分机号码不正确，分机号码数字！',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#FJHM").focus();
					return false;
				 }
			}
			
			if($("#ZXXM").val()==""){
				$("#ZXXM").tips({
					side:3,
		            msg:'请输入坐席姓名',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ZXXM").focus();
				return false;
			}
			
		
			
			var msg='${msg }';
			if(msg=="save"){
				if($("#PWD").val()==""){
					$("#PWD").tips({
						side:3,
			            msg:'请输入密码，密码不能为空',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#PWD").focus();
					return false;
				}
			}
			
			if($("#PWD").val()!=""){
				if($("#PWD").val()!=$("#CHKPWD").val()){
					$("#PWD").tips({
						side:3,
			            msg:'两次输入的密码不一样',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#PWD").focus();
					return false;
				}
				
				
			}
			
			
			if($("#ZXGH").val()==""){
				$("#ZXGH").tips({
					side:3,
		            msg:'请输入坐席工号',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ZXGH").focus();
				return false;
			}else{
				 if(isNaN($("#ZXGH").val())){
					 $("#ZXGH").tips({
						side:3,
			            msg:'您输入的坐席工号不正确，坐席工号应为数字！',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#ZXGH").focus();
					return false;
				 }
			}
			
			if($("#ZXYH").val()==""){
				$("#ZXYH").tips({
					side:3,
		            msg:'请选择系统用户',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#ZXYH").focus();
				return false;
			}
			//$("#zhongxin").hide();
			//$("#zhongxin2").show();
			$.ajax({
            //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "html",//预期服务器返回的数据类型
                url: "zxlb/${msg }.do" ,//url
                data: $('#Form_add').serialize(),
                success: function (result) {
                  // alert(result);//打印服务端返回的数据(调试用)
                    if (result.indexOf("success_add")>=0) {
                    	location.href="<%=basePath%>zxlb/list.do";
                    }else if (result.indexOf("error1")>=0){
                    	modals.info("坐席用户已存在，请重新选择！");
                    	return false;
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