<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
	<link type="text/css" rel="stylesheet" href="plugins/zTree/3.5/zTreeStyle.css"/>
	<script type="text/javascript" src="plugins/zTree/3.5/jquery.ztree.core.js"></script>
<style>
	ul li{line-height:30px;list-style-type:none}
</style>
</head>
<body class="no-skin">
<div class="main-container" id="main-container">
	<!-- Horizontal Form -->
	<!-- <div class="box box-info">
		<div class="box-header with-border">
			<h3 class="box-title">用户添加</h3>
		</div> -->
		<!-- /.box-header -->
		<!-- form start -->
	<div class="main-content">
		<div class="main-content-inner">
		<div class="page-content">
		<div class="modal-header">
			<h4 class="modal-title" id="myModalLabel" style="float: left;">	</h4>
			<div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>
		</div>
		<div class="row">
			<div class="col-xs-12">
				<form action="user/${msg }.do" name="userForm" id="userForm_add"
					method="post">
					<input type="hidden" name="USER_ID" id="user_id" value="${pd.USER_ID }" />
					<div id="zhongxin" >
						<div id="table_report" class="table table-striped table-bordered table-hover new-wkj">
						
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">角色:</div>
									<div class="one-wk-r">
										<select name="ROLE_ID" id="role_id1" data-placeholder="请选择角色" >
											<option value=""></option>
											<c:forEach items="${roleList}" var="role">
												<option value="${role.ROLE_ID }"
													<c:if test="${role.ROLE_ID == pd.ROLE_ID }">selected</c:if>>${role.ROLE_NAME }</option>
												
											</c:forEach>
										</select>
										<input type="hidden" id="ROLE_NAME" name="ROLE_NAME" value="${role.ROLE_NAME }">
									</div>
								</div>
								<span> </span>
							</div>
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">所属单位:</div>
									<div class="one-wk-r">
										<input type="hidden" id="DWBM" name="DWBM" value="${pd.DWBM }">
										<input id="citySel" name="" type="text" readonly value="${pd.AREA_NAME }"  onclick="showMenu(); return false;"/>
									</div>
								</div>
								<span> </span>
							</div>
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">用户名:</div>
									<div class="one-wk-r"><input type="text" name="USERNAME" id="loginname" value="${pd.USERNAME }" maxlength="32" placeholder="这里输入用户名" title="用户名" /></div>
								</div>
								<span> </span>
							</div>
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">编号:</div>
									<div class="one-wk-r"><input type="text" name="NUM" id="NUM" value="${pd.NUM }" maxlength="32" placeholder="这里输入编号" title="编号" onblur="hasN('${pd.USERNAME }')" /></div>
								</div>
								<span> </span>
							</div>
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">密码:</div>
									<div class="one-wk-r"><input type="password" name="PASSWORD" id="password" maxlength="32" placeholder="输入密码" title="密码" /></div>
								</div>
								<span> </span>
							</div>
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">确认密码:</div>
									<div class="one-wk-r"><input type="password" name="chkpwd" id="chkpwd" maxlength="32" placeholder="确认密码" title="确认密码" /></div>
								</div>
								<span> </span>
							</div>
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">姓名:</div>
									<div class="one-wk-r"><input type="text" name="NAME" id="name" value="${pd.NAME }" maxlength="32" placeholder="这里输入姓名" title="姓名" /></div>
								</div>
								<span> </span>
							</div>
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">手机号:</div>
									<div class="one-wk-r"><input type="NUMBER" name="PHONE" id="PHONE" value="${pd.PHONE }" maxlength="32" placeholder="这里输入手机号" title="手机号" /></div>
								</div>
								<span> </span>
							</div>
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">邮箱:</div>
									<div class="one-wk-r"><input type="email" name="EMAIL" id="EMAIL" value="${pd.EMAIL }" maxlength="32" placeholder="这里输入邮箱" title="邮箱" onblur="hasE('${pd.USERNAME }')"  /></div>
								</div>
								<span> </span>
							</div>
							
							<div class="new-tk-body-one">
								<div class="new-tk-body-one-wk">
									<div class="one-wk-l">备注:</div>
									<div class="one-wk-r"><input type="text" name="BZ" id="BZ" value="${pd.BZ }" placeholder="这里输入备注" maxlength="64" title="备注"  /></div>
								</div>
								<span> </span>
							</div>
							
							
							<div class="new-left " style="width:100%">
								
									<div class="one-wk-l">所管理坐席组:</div>
									<div class="new-table-nr" style="width:100%">
										<input type="hidden" name="ZXZ" id="ZXZ" value="${pd.ZXZ }">
										<c:forEach items="${zxzList}" var="var" varStatus="vs">
											<li style="float:left;margin-left:10px;">
												<table><tr><td style="padding-top:5px"><input name="ZXZCHECK"  value="${var.ID}" ${var.checked} type="checkbox" id="ZXZCHECK"></td><td style="padding-left:5px;">${var.ZMC}</td></tr></table>
		
											</li>
										</c:forEach>
									</div>
								
								
							</div>
							
							<%-- <tr>
								<td style="width:120px;text-align: right;padding-top: 13px;">所管理坐席组:</td>
								<td colspan="3">
									<input type="hidden" name="ZXZ" id="ZXZ" value="${pd.ZXZ }">
									<ul>
										<c:forEach items="${zxzList}" var="var" varStatus="vs">
											<li style="width:25%;float:left">
												<table><tr><td style="padding-top:5px"><input name="ZXZCHECK"  value="${var.ID}" ${var.checked} type="checkbox" id="ZXZCHECK"></td><td style="padding-left:5px;">${var.ZMC}</td></tr></table>
		
											</li>
										</c:forEach>
									</ul>	
								</td>
							</tr> --%>
							
							<div class="new-bc">
								<a  onclick="save();">保存</a>
								&nbsp;&nbsp;&nbsp;&nbsp;<a  onclick="saveweixin();">保存微信</a>
								<a class="new-qxbut"  data-btn-type="cancel" data-dismiss="modal">取消</a>
							</div>
							
						</div>
					</div>
					
				</form>
			</div>
		</div>
		</div>
		</div>
	</div>
	<!-- </div> -->
</div>
<div id="menuContent" class="menuContent" style="min-height:250px;display:none; position: absolute;background:rgb(238,242,245);border:1px #ccc solid">
	<ul id="treeDemo" class="ztree" style="margin-top:0; width:200px;"></ul>
</div>
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
		$("#DWBM").val(treeNode.AREA_CODE);
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
		
		if($("#user_id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	//保存
	function save(){
		//alert($("#role_id1").val());
		if($("#role_id1").val()==""){
			$("#juese").tips({
				side:3,
	            msg:'选择角色',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#role_id1").focus();
			return false;
		}
		if($("#DWBM").val()==""){
			$("#citySel").tips({
				side:3,
	            msg:'请选择单位编码',
	            bg:'#AE81FF',
	            time:0
	        });
			$("#citySel").focus();
			return false;
		}
		if($("#loginname").val()=="" || $("#loginname").val()=="此用户名已存在!"){
			$("#loginname").tips({
				side:3,
	            msg:'输入用户名',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#loginname").focus();
			$("#loginname").val('');
			$("#loginname").css("background-color","white");
			return false;
		}else{
			$("#loginname").val(jQuery.trim($('#loginname').val()));
		}
		
		if($("#NUM").val()==""){
			$("#NUM").tips({
				side:3,
	            msg:'输入编号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#NUM").focus();
			return false;
		}else{
			$("#NUM").val($.trim($("#NUM").val()));
		}
		if($("#user_id").val()=="" && $("#password").val()==""){
			$("#password").tips({
				side:3,
	            msg:'输入密码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#password").focus();
			return false;
		}
		if($("#password").val()!=$("#chkpwd").val()){
			
			$("#chkpwd").tips({
				side:3,
	            msg:'两次密码不相同',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#chkpwd").focus();
			return false;
		}
		if($("#name").val()==""){
			$("#name").tips({
				side:3,
	            msg:'输入姓名',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#name").focus();
			return false;
		}
		var myreg = /^(((13[0-9]{1})|159)+\d{8})$/;
		if($("#PHONE").val()==""){
			
			$("#PHONE").tips({
				side:3,
	            msg:'输入手机号',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#PHONE").focus();
			return false;
		}else if($("#PHONE").val().length != 11 && !myreg.test($("#PHONE").val())){
			$("#PHONE").tips({
				side:3,
	            msg:'手机号格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#PHONE").focus();
			return false;
		}
		if($("#EMAIL").val()==""){
			
			$("#EMAIL").tips({
				side:3,
	            msg:'输入邮箱',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}else if(!ismail($("#EMAIL").val())){
			$("#EMAIL").tips({
				side:3,
	            msg:'邮箱格式不正确',
	            bg:'#AE81FF',
	            time:3
	        });
			$("#EMAIL").focus();
			return false;
		}
		var ZXZ="";
		var conditions=document.getElementsByName("ZXZCHECK");
		//alert(conditions);
		
		for(var i=0;i<conditions.length;i++){
		    if(conditions[i].checked==true){
            	if(ZXZ!=""){
            		ZXZ=ZXZ+",";
            	}
            	ZXZ=ZXZ+conditions[i].value;
            }
        }
       // $("#ZXZ").val(ZXZ);
        //alert(ZXZ);
        if(ZXZ==""){
			alert("请选择坐席组");
			return false;
		}else{
			 $("#ZXZ").val(ZXZ);
		}
       // return false;
		
		if($("#user_id").val()==""){
			hasU();
		}else{
			$("#userForm_add").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
		}
	}

	function ismail(mail){
		return(new RegExp(/^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/).test(mail));
		}
    function saveweixin(){


		var USER_ID=$("#loginname").val();
		var NAME=$("#name").val()
		var AREA_ID=$("#DWBM").val();
		var PHONE=$("#PHONE").val();
		var EMAIL=$("#EMAIL").val();
		var POSITION=$("#ROLE_NAME").val();
		var GENDER="";

		var url="";
		if ("${msg}"=="saveU"){
			// url='http://luyin.ada-robotics.com/weixin/app_department/save?AREA_ID='+AREA_CODE+'&PARENT_ID=1&NAME='+name;
			url='http://luyin.ada-robotics.com/weixin/app_user/save?USER_ID='+USER_ID+'&NAME='+NAME+'&AREA_ID='+AREA_ID+'&PHONE='+PHONE+'&EMAIL='+EMAIL+'&POSITION='+POSITION+'&GENDER='+GENDER;
		}else if ("${msg}"=="editU"){
			url='http://luyin.ada-robotics.com/weixin/app_user/update?USER_ID='+USER_ID+'&NAME='+NAME+'&AREA_ID='+AREA_ID+'&PHONE='+PHONE+'&EMAIL='+EMAIL+'&POSITION='+POSITION+'&GENDER='+GENDER;
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
		alert("同步微信成功，请进行保存")
    }
	//判断用户名是否存在
	function hasU(){
		var USERNAME = $.trim($("#loginname").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasU.do',
	    	data: {USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.result){
					$("#userForm_add").submit();
					//$("#zhongxin").hide();
					//$("#zhongxin2").show();
				 }else{
					$("#loginname").css("background-color","#D16E6C");
					setTimeout("$('#loginname').val('此用户名已存在!')",500);
				 }
			}
		});
	}
	
	//判断邮箱是否存在
	function hasE(USERNAME){
		var EMAIL = $.trim($("#EMAIL").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasE.do',
	    	data: {EMAIL:EMAIL,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#EMAIL").tips({
							side:3,
				            msg:'邮箱 '+EMAIL+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#EMAIL").val('');
				 }
			}
		});
	}
	
	//判断编码是否存在
	function hasN(USERNAME){
		var NUM = $.trim($("#NUM").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/hasN.do',
	    	data: {NUM:NUM,USERNAME:USERNAME,tm:new Date().getTime()},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" != data.result){
					 $("#NUM").tips({
							side:3,
				            msg:'编号 '+NUM+' 已存在',
				            bg:'#AE81FF',
				            time:3
				        });
					 $("#NUM").val('');
				 }
			}
		});
	}
	
		</script>
</body>
</html>
