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
 <%@ include file="../../system/include/incJs_mx.jsp"%>
<!-- Bootstrap 3.3.7 -->
<link rel="stylesheet"
	href="static/ace/adminlet/bower_components/bootstrap/dist/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="static/ace/adminlet/bower_components/font-awesome/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet"
	href="static/ace/adminlet/bower_components/Ionicons/css/ionicons.min.css">
<!-- DataTables -->
<link rel="stylesheet"
	href="static/ace/adminlet/bower_components/datatables.net-bs/css/dataTables.bootstrap.min.css">
<link rel="stylesheet" href="static/ace/css/font-awesome.css" />
<script src="static/ace/adminlet/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<link rel="stylesheet" type="text/css" href="static/easyui/css/easyui.css">
<link rel="stylesheet" type="text/css" href="static/easyui/css/icon.css">
<link rel="stylesheet" type="text/css" href="static/easyui/css/demo.css">
<script type="text/javascript" src="static/easyui/js/jquery.min.js"></script>
<script type="text/javascript" src="static/easyui/js/jquery.easyui.min.js"></script>
<script src="static/easyui/js/Convert_Pinyin.js"></script>
<script src="static/easyui/js/easyui-lang-zh_CN.js"></script>
<script src="static/custom/layer/layer.js"></script>
<script type="text/javascript" src="plugins/zTree/2.6/jquery.ztree-2.6.min.js"></script>
<link type="text/css" rel="stylesheet" href="plugins/zTree/2.6/zTreeStyle.css"/>
<style type="text/css">
#dialog-add, #dialog-message, #dialog-comment {
	width: 100%;
	height: 100%;
	position: relative;
	top: 0px;
	z-index: 10000;
	display: none;
}

.commitopacity {
	position: absolute;
	width: 100%;
	height: 620px;
	background: #7f7f7f;
	filter: alpha(opacity = 50);
	-moz-opacity: 0.5;
	-khtml-opacity: 0.5;
	opacity: 0.5;
	top: 0px;
	z-index: 99999;
}

.commitbox {
	width: 100%;
	padding: 20px 10px;
	position: absolute;
	top: 0px;
	z-index: 99999;
}

.commitbox_inner {
	width: 100%;
	height: 557px;
	margin: 6px auto;
	background: #efefef;
	border-radius: 5px;
}

.commitbox_top {
	width: 100%;
	height: 555px;
	margin-bottom: 10px;
	padding-top: 10px;
	background: #FFF;
	border-radius: 5px;
	box-shadow: 1px 1px 3px #e8e8e8;
}

.commitbox_top textarea {
	width: 95%;
	height: 165px;
	display: block;
	margin: 0px auto;
	border: 0px;
}

.commitbox_cen {
	width: 95%;
	height: 40px;
	padding-top: 10px;
}

.commitbox_cen div.left {
	float: left;
	background-size: 15px;
	background-position: 0px 3px;
	padding-left: 18px;
	color: #f77500;
	font-size: 16px;
	line-height: 27px;
}

.commitbox_cen div.left img {
	width: 30px;
}

.commitbox_cen div.right {
	float: right;
	margin-top: 7px;
}

.commitbox_cen div.right span {
	cursor: pointer;
}

.commitbox_cen div.right span.save {
	border: solid 1px #c7c7c7;
	background: #6FB3E0;
	border-radius: 3px;
	color: #FFF;
	padding: 5px 10px;
}

.commitbox_cen div.right span.quxiao {
	border: solid 1px #f77400;
	background: #f77400;
	border-radius: 3px;
	color: #FFF;
	padding: 4px 9px;
}
.disabled-selected{
	background-color: #EEEEEE!important;
	border: 1px solid #EEEEEE!important;
}
.btn-danger {
    color: #fff;
    border-color: #1d9aee; 
    background-color: #1d9aee;
}

.panel {
    overflow: hidden;
    text-align: left;
    margin: 0;
    border: 0;
    -moz-border-radius: 0 0 0 0;
    -webkit-border-radius: 0 0 0 0;
    border-radius: 0 0 0 0;
    margin-top: 10px;
}
.input-class{
	width: 156px;
    height: 26px;
    text-indent: 1em;
    border: 1px solid #95B8E7;
    border-radius: 5px;
    margin-right: 2px;
}
.label-class{
	text-align: right;
}
.tagbox {
    cursor: text;
    width: 160px!important;
    overflow-y: auto;
}
.datagrid-body {
    margin: 0;
    padding: 0;
    overflow: auto;
    zoom: 1;
    min-height: 78px;
    max-height: 250px;
}
.datagrid-header, .datagrid-toolbar, .datagrid-pager, .datagrid-footer-inner {
    border-color: #dddddd;
    height: 30px!important;
}
.datagrid .panel-body {
    overflow: hidden;
    position: relative;
    width: 100%!important;
} 
</style>

</head>
<body class="no-skin main-bg-w">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container" style="height: 550px;">
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="page-content">
				
				<div class="row">
					<div class="col-xs-12 main-padding-t-15">
						<!-- 添加属性  -->
						<input type="hidden" name="hcdname" id="hcdname" value="" /> <input
							type="hidden" name="msgIndex" id="msgIndex" value="" /> <input
							type="hidden" name="dtype" id="dtype" value="String" />
						<!-- <input type="hidden" name="isQian" id="isQian" value="是" /> -->
						<div id="dialog-add">
							<div class="commitopacity"></div>
							<div class="commitbox">
								<div class="commitbox_inner">
									<div class="commitbox_top">
										<br />
										<table style="margin: 50px auto 30px auto;">
											<tr height="42px;">
												<td style="padding-left: 16px; text-align: right;">属性名：</td>
												<td><div class="nav-search">
														<input class="nav-search-input"
															style="width: 100%; text-indent: 1em;" name="dname"
															id="dname" type="text" value=""
															placeholder="首字母必须为字母或下划线" title="属性名" />
													</div>
													<input type="hidden" value="1" name="ISSELECT" id="ISSELECT">
													<input type="hidden" value="0" name="ISFLOW" id="ISFLOW">
													<input type="hidden" value="" name="quote_type" id="quote_type">
												</td>
												<td style="padding-left: 16px; text-align: right;">其备注：</td>
												<td>
													<div class="nav-search">
														<input class="nav-search-input"
															style="width: 269px; text-indent: 1em;" name="dbz"
															id="dbz" type="text" value=""
															placeholder="例如 name的备注为 '姓名'" title="备注" />
													</div>
												</td>
											</tr>
											<tr height="42px;">
												<td style="padding-left: 16px; text-align: right;">默认值：</td>
												<td><div class="nav-search">
														<input class="nav-search-input"
															style="width: 100%; text-indent: 1em;" name="ddefault"
															id="ddefault" type="text" value="" placeholder="后台附加值时生效"
															title="默认值" />
													</div></td>
												<td style="padding-left: 16px; text-align: right;">是否引用：</td>
												<td>
													<div class="nav-search">
														<select name="is_quote" id="is_quote"
															style="width: 269px; height: 26px; text-indent: 1em;"
															onchange="quote_change();">
															<option value="否">否</option>
															<option value="是">是</option>
														</select>
													</div>
												</td>
											</tr>
											<tr height="42px;">
												<td style="padding-left: 16px; text-align: right;">是否字典：</td>
												<td>
													<div class="nav-search">
														<select name="is_dic" id="is_dic"
															style="width: 100%; height: 26px; text-indent: 1em;"
															onchange="dic_change();">
															<option value="否">否</option>
															<option value="是">是</option>
														</select>
													</div>
												</td>
												<td style="padding-left: 16px; text-align: right;">字典类型：</td>
												<td>
													<div class="nav-search">
														<select name="dic_type" id="dic_type"
															style="width: 269px; height: 26px; text-indent: 1em;">
															<option value="">请选择...</option>
															<c:choose>
																<c:when test="${not empty varList}">
																	<c:forEach items="${varList}" var="var" varStatus="vs">
																		<option value="${var.DICTIONARIES_ID}">${var.NAME}</option>
																	</c:forEach>
																</c:when>
																<c:otherwise></c:otherwise>
															</c:choose>
														</select>
													</div>
												</td>
											</tr>
											<tr height="42px;">
												<td style="padding-left: 16px; text-align: right;">属性类型：</td>
												<td style="padding-bottom: 5px;"><label
													style="float: left; padding-left: 6px;"><input
														name="form-field-radiot" id="form-field-radio1"
														onclick="setType('String');" type="radio"
														value="icon-edit"><span class="lbl">String</span></label>
													<label style="float: left; padding-left: 6px;"><input
														name="form-field-radiot" id="form-field-radio3"
														onclick="setType('Date');" type="radio" value="icon-edit"><span
														class="lbl">Date</span></label> <label
													style="float: left; padding-left: 6px;"><input
														name="form-field-radiot" id="form-field-radio2"
														onclick="setType('Integer');" type="radio"
														value="icon-edit"><span class="lbl">Integer</span></label>
													<label style="float: left; padding-left: 6px;"><input
														name="form-field-radiot" id="form-field-radio33"
														onclick="setType('Double');" type="radio"
														value="icon-edit"><span class="lbl">Double</span></label>
												</td>
												<td style="padding-left: 16px; text-align: right;">长度：</td>
												<td>
													<div class="nav-search" style="padding-right: 5px;">
														<input class="nav-search-input"
															style="width: 200px; text-indent: 1em;" name="flength"
															id="flength" type="number" value="" placeholder="长度"
															title="长度" /> . <input class="nav-search-input"
															style="width: 55px;" name="decimal" id="decimal"
															type="number" value="" placeholder="小数"
															title="类型为Double时有效" />
													</div>
												</td>

											</tr>
											<tr height="42px;">
												<td style="padding-left: 16px; text-align: right;">显示序号：</td>
												<td colspan="3">
													<div class="nav-search">
														<input class="nav-search-input"
															style="width: 100%; text-indent: 1em;" name="show_order"
															id="show_order" type="number" value=""
															placeholder="请输入显示序号" title="显示序号" />
													</div>
												</td>
												<%-- <td style="padding-left: 16px; text-align: right;">引用表名：</td>
												<td>
													<div class="nav-search">
														<select name="quote_type" id="quote_type"
															style="width: 269px; height: 26px; text-indent: 1em;">
															<option value="">请选择...</option>
															<c:choose>
																<c:when test="${not empty quoteList}">
																	<c:forEach items="${quoteList}" var="var"
																		varStatus="vs">
																		<option value="${var.TEMPLATE_ID}">${var.NAME}</option>
																	</c:forEach>
																</c:when>
																<c:otherwise></c:otherwise>
															</c:choose>
														</select>
													</div>
													
												</td> --%>
											</tr>
											<tr height="42px;">
												<td style="padding-left: 16px; text-align: right;">是否不在编辑页面显示：</td>
												<td>
													<div class="nav-search">
														<select name="ISNEW" id="ISNEW"
															style="width: 100%; height: 26px; text-indent: 1em;">
															<option value="0">否</option>
															<option value="1">是</option>
														</select>
													</div>
												</td>
												<td style="padding-left: 16px; text-align: right;">是否必填：</td>
												<td>
													<div class="nav-search">
														<select name="ISMUST" id="ISMUST"
															style="width: 269px; height: 26px; text-indent: 1em;">
															<option value="0">否</option>
															<option value="1">是</option>
														</select>
													</div>
													
												</td>
											</tr>
											<tr height="42px;">
												<td style="padding-left: 16px; text-align: right;">是否查询字段：</td>
												<td>
													<div class="nav-search">
														<select name="ISQY" id="ISQY"
															style="width: 100%; height: 26px; text-indent: 1em;">
															<option value="0">否</option>
															<option value="1">是</option>
														</select>
														
													</div>
												</td>
												<td style="padding-left: 16px; text-align: right;">是否在查询列表显示：</td>
												<td>
													<div class="nav-search">
														<select name="ISLIST" id="ISLIST"
															style="width: 269px; height: 26px; text-indent: 1em;">
															<option value="0">否</option>
															<option value="1">是</option>
														</select>
													</div>
												</td>
											</tr>
											<!-- <tr height="42px;">
												<td style="padding-left: 16px; text-align: right;">是否工作流表单字段：</td>
												<td>
													<div class="nav-search">
														<select name="ISFLOW" id="ISFLOW"
															style="width: 100%; height: 26px; text-indent: 1em;">
															<option value="0">否</option>
															<option value="1">是</option>
														</select>
													</div>
													
												</td>
												<td style="padding-left: 16px; text-align: right;">是否在选择框列表显示：</td>
												<td>
													<div class="nav-search">
														<select name="ISSELECT" id="ISSELECT"
															style="width: 269px; height: 26px; text-indent: 1em;">
															<option value="0">否</option>
															<option value="1">是</option>
														</select>
													</div>
													
													
												</td>
											</tr> -->
											<tr height="80px;">
												<td colspan="4" style="padding-left: 16px;"><font
													color="red" style="font-weight: bold; text-align: left;">
														注意：<br /> 1. 请不要添加 ID 的主键，系统自动生成一个32位无序不重复字符序列作为主键<br />
														2. 主键为ID 格式，所有字段的字母均用大写<br /> 3. 显示序号将影响列表中的字段显示
												</font></td>
											</tr>
											<tr height="42px;">
												<td colspan="4">
													<div style="width: 30%; margin: 0 auto;">
														<div class="cbk-middle-one-bg-top-but">
															<div class="new-but-q-save" onClick="saveD()">保存
															</div>
															<div class="new-but-cancel" onClick="cancel_pl()">取消
															</div>
														</div>
													</div>
												</td>
											</tr>
										</table>
									</div>
								</div>
							</div>
						</div>

						<form action="templates/proCode.do" name="Form_add" id="Form_add"
							method="post">
							<input type="hidden" name="zindex" id="zindex" value="0">
							<input type="hidden" name="FIELDLIST" id="FIELDLIST" value="">
							<input type="hidden" name="C_PHYSICSNAME" id="C_PHYSICSNAME"
								value="${pd.C_PHYSICSNAME}"> <input type="hidden"
								name="TEMPLATE_ID" id="TEMPLATE_ID" value="${pd.TEMPLATE_ID}" />
							<input type="hidden" name="msg" id="msg" value="${msg}" /> <input
								type="hidden" name="table_type" id="table_type"
								value="${pd.TYPE}" /> <input type="hidden" name="dic_type_hid"
								id="dic_type_hid" value="${pd.TB_TYPE}" />
								<input type="hidden" value="0" name="DISPLAY_TYPE" id="DISPLAY_TYPE">
								<input type="hidden" value="1" name="SORT_ORDER" id="SORT_ORDER">
								<input type="hidden" value="0" name="SELECT_TYPE" id="SELECT_TYPE">
								<input type="hidden" value="0" name="TREE_TYPE" id="TREE_TYPE">
								<input type="hidden" value="0" name="TB_TYPE" id="TB_TYPE">
								<input name="RELATION_TABLE" id="RELATION_TABLE" type="hidden" >
							<div id="zhongxin" >
								<table class="bdpz-lb">
									<tr>
										<td class="label-class">名称：</td>
										<td><input type="text" name="NAME" id="NAME"
											value="${pd.NAME}" placeholder="这里输入名称" class="input-class" 
											title="名称" /></td>
										<!-- <td style="width:76px;text-align: right;">表名：</td>
							                <td><input type="text" name="C_PHYSICSNAME" id="C_PHYSICSNAME" value="${pd.C_PHYSICSNAME}" placeholder="这里输入表名" style="width:320px" readonly="readonly" title="表名"/></td>
							                <td>&nbsp;&nbsp;<font color="red" style="font-weight: bold;">表名必须为字母或下划线</font></td> -->
										<td class="label-class">类型：</td>
										<td>
											<select name="TYPE" id="TYPE" <c:if test="${msg!='add'}"> class="disabled-selected"</c:if>
												 style="width: 156px; height: 26px;margin-right: 2px;text-indent: 1em;border: 1px solid #95B8E7;border-radius: 5px;margin-top: 5px;" onchange="typeChage();">
												<option value="">请选择...</option>
												<c:choose>
													<c:when test="${not empty dicList}">
														<c:forEach items="${dicList}" var="var" varStatus="vs">
															<option <c:if test="${pd.TYPE == var.BIANMA}">selected</c:if> value="${var.BIANMA}" data-extend1="${var.EXTEND1}">${var.NAME}</option>
														</c:forEach>
													</c:when>
													<c:otherwise></c:otherwise>
												</c:choose>
											</select>
										</td>
										<td class="label-class" style="margin-top: 5px; ">排序号：</td>
										<td><input type="text" name="ORDER_ID" id="ORDER_ID"
											value="${pd.ORDER_ID}" placeholder="排序号" class="input-class" 
											title="排序号" /></td>
										
										<%-- 
											<td class="label-class" style="margin-top: 5px; ">关联表：</td>
											<td>
											<input name="CONNECTION_TABLE" id="CONNECTION_TABLE" value="${pd.RELATION_TABLE}" multiple="multiple" class="easyui-tagbox"  data-options="data:connectionTableData,
											    valueField: 'id',
											    textField: 'text',
											    hasDownArrow: true,
											    panelHeight:'200'">
											 <input name="RELATION_TABLE" id="RELATION_TABLE" type="hidden" >
										</td> --%>
										<%-- <td class="label-class">是否字典：</td>
										<td>
											<select name="TB_TYPE" id="TB_TYPE" class="input-class" >
												<option value="">请选择...</option>
												<option <c:if test="${pd.TB_TYPE == 0}">selected</c:if> value="0">否</option>
												<option <c:if test="${pd.TB_TYPE == 1}">selected</c:if> value="1">是</option>
											</select>
										</td>
										<td class="label-class">是否树形：</td>
										<td>
											<select name="TREE_TYPE" id="TREE_TYPE" class="input-class" >
												<option value="">请选择...</option>
												<option <c:if test="${pd.TREE_TYPE == 0}">selected</c:if> value="0">否</option>
												<option <c:if test="${pd.TREE_TYPE == 1}">selected</c:if> value="1">是</option>
											</select>
										</td> --%>
										
									</tr>
									<%-- <tr>
										<td class="label-class">是否菜单：</td>
										<td>
											<select name="DISPLAY_TYPE" id="DISPLAY_TYPE" class="input-class" >
												<option value="">请选择...</option>
												<option <c:if test="${pd.DISPLAY_TYPE == 0}">selected</c:if> value="0">否</option>
												<option <c:if test="${pd.DISPLAY_TYPE == 1}">selected</c:if> value="1">是</option>
											</select>
											
											
											
										</td>
										
										<td class="label-class" style="margin-top: 5px; ">排列顺序:</td>
										<td>
											<select name="SORT_ORDER" id="SORT_ORDER"
											class="input-class" >
												<option value="">请选择...</option>
												<option <c:if test="${pd.SORT_ORDER == 1}">selected</c:if> value="1">一行一列</option>
												<option <c:if test="${pd.SORT_ORDER == 2}">selected</c:if> value="2">一行二列</option>
												<option <c:if test="${pd.SORT_ORDER == 3}">selected</c:if> value="3">一行三列</option>
										</select>
										
										</td>
									</tr> 
									<tr>
										<td class="label-class" id="td_leader_name" hidden>是否领导：</td>
										<td id="td_leader" hidden>
											<select name="LEADER" id="LEADER" class="input-class" >
												<option value="">请选择...</option>
												<option <c:if test="${pd.LEADER == 0}">selected</c:if> value="0">否</option>
												<option <c:if test="${pd.LEADER == 1}">selected</c:if> value="1">是</option>
											</select>
										</td>
										<td class="label-class">是否多选：</td>
										<td>
											<select name="SELECT_TYPE" id="SELECT_TYPE" class="input-class" >
												<option value="">请选择...</option>
												<option <c:if test="${pd.SELECT_TYPE == 0}">selected</c:if> value="0">否</option>
												<option <c:if test="${pd.SELECT_TYPE == 1}">selected</c:if> value="1">是</option>
											</select>
											
										</td>
										<td class="label-class" id="td_fa_type_name" hidden>会议类型：</td>
										<td id="td_fa_type" hidden>
											<div style="position:relative;">
												<input type="text" class="input-class" name="FA_TYPE_TMP" id="FA_TYPE_TMP" 
													placeholder="这里选择会议类型" title="这里选择会议类型" readonly="true"/>
												<input id="FA_TYPE" name="FA_TYPE" type="hidden"/>
												<ul id="typeTree" class="tree" 
													style="position:absolute;left:100px;width:98%;display:none;height:200px;z-index:1;
													border:dashed 1px #000000; background-color:#FFFFFF; overflow:auto;top:32px!important;">
												</ul>
											</div>
										</td>
									</tr>--%>
								</table>
									 
									
									<div id="tb" style="height:auto;">
										<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="append()">添加</a>
										<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeit()">删除</a>
									    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="accept()">保存</a>
									    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="reject()">撤回</a>
									</div>
									<table id="dg" class="easyui-datagrid"
											data-options="singleSelect: true,toolbar: '#tb',onClickRow: onClickRow, autoRowHeight:false">
										<thead frozen="true">
											<tr>
												<th data-options="field:'index',align:'center',width:60">序号</th>
												<th data-options="field:'remark',align:'center',width:120,editor:{type: 'textbox',options:{required:true,missingMessage:'不能为空' }}">字段名称</th><!-- ,validType:'chineseValid' -->
												<th data-options="field:'name',align:'center',width:150,editor:{type: 'textbox',options:{required:true,missingMessage:'不能为空' ,validType:['nameValid','snameValid']}}">字段</th>
											</tr>
										</thead>
										<thead>
											<tr>
											    <th data-options="field:'type',align:'center',width:120,editor: {type: 'combobox', options:{data: typeData, valueField: 'value', textField: 'text',panelHeight:'auto',required:true,missingMessage:'不能为空'}}">类型</th>
												<th data-options="field:'length',align:'center',width:120,editor:{type: 'textbox',options:{required:true,missingMessage:'不能为空',validType: 'numberValid'}}">长度</th>
												<th data-options="field:'points',align:'center',width:120,editor:{type: 'textbox',options:{validType: 'nameValid'}}">小数点</th>
												<th data-options="field:'defaults',align:'center',width:120,editor:{type: 'textbox'}">默认值</th>
												<th data-options="field:'sortNum',align:'center',width:120,editor:{type: 'numberspinner',options:{min: 1,max: 100,required:true,missingMessage:'不能为空'}}">显示序号</th>
												<th data-options="field:'isDic',align:'center',width:120,editor: {type: 'combobox', options:{data: isDicData, valueField: 'value', textField: 'text',panelHeight:'auto',required:true,missingMessage:'不能为空'}}">是否字典</th>
												<th data-options="field:'dicType',align:'center',width:120,editor: {type: 'combobox', options:{data: dicTypeData, valueField: 'value', textField: 'text',panelHeight:'200px',required:true,missingMessage:'不能为空'}}">字典类型</th>
												<th data-options="field:'dicId',align:'center',width:120,hidden:true,editor: {type: 'combobox', options:{data: dicTypeDataHidden, valueField: 'value', textField: 'text'}}">字典类型ID</th>
												<th data-options="field:'isQuote',align:'center',width:120, editor: {type: 'combobox', options:{data: isQuoteData, valueField: 'value', textField: 'text',panelHeight:'auto',required:true,missingMessage:'不能为空'}}">是否引用</th>
												<th data-options="field:'tableName',align:'center',width:120,editor: {type: 'combobox', options:{data: tableNameData, valueField: 'value', textField: 'text',panelHeight:'200px',required:true,missingMessage:'不能为空'}}">引用表名</th>
												<th data-options="field:'tableId',align:'center',width:120,hidden:true,editor: {type: 'combobox', options:{data: tableNameDataHidden, valueField: 'value', textField: 'text'}}">表名ID</th>
												<th data-options="field:'isNew',align:'center',width:180,editor: {type: 'combobox', options:{data: isNewData, valueField: 'value', textField: 'text',panelHeight:'auto',required:true,missingMessage:'不能为空'}}">是否在编辑页面显示</th>
												<th data-options="field:'isMust',align:'center',width:120,editor: {type: 'combobox', options:{data: isMustData, valueField: 'value', textField: 'text',panelHeight:'auto',required:true,missingMessage:'不能为空'}}">是否必填</th>
										     	<th data-options="field:'isQy',align:'center',width:120,editor: {type: 'combobox', options:{data: isQyData, valueField: 'value', textField: 'text',panelHeight:'auto',required:true,missingMessage:'不能为空'}}">是否查询字段</th>
												<th data-options="field:'isList',align:'center',width:180,editor: {type: 'combobox', options:{data: isListData, valueField: 'value', textField: 'text',panelHeight:'auto',required:true,missingMessage:'不能为空'}}">是否在列表显示</th>
												<th data-options="field:'isFlow',align:'center',width:180,editor: {type: 'combobox', options:{data: isFlowData, valueField: 'value', textField: 'text',panelHeight:'auto',required:true,missingMessage:'不能为空'}}">是否工作流表单字段</th>
												<th data-options="field:'isSelect',align:'center',width:180,editor: {type: 'combobox', options:{data: isSelectData, valueField: 'value', textField: 'text',panelHeight:'auto',required:true,missingMessage:'不能为空'}}">是否在选择框列表显示</th>
												<th data-options="field:'layout',align:'center',width:180,editor: {type: 'combobox', options:{data: layoutData, valueField: 'value', textField: 'text',panelHeight:'auto'}}">布局</th>
											</tr>
										</thead>
									</table>
									
								</div>

								

								<table id="table_report" style="width: 100%;margin: 0 auto;" border="0">
									<tr>
										  <td style="text-align: center;" colspan="2">
								            <div style="width: 40%; margin: 0 auto;">
								          		<div class="cbk-middle-one-bg-top-but" align="center" style="text-align: center;">
													<span id="tsTest" style="color: red;display: none;margin-right: 10px">* 无需添加 议题类型、责任单位、责任人、预计时间</span>
													<span id="tsTest1" style="color: red;margin-left: 250px">&nbsp;</span>
												</div>
											</div>
										</td>
									</tr>
									<tr>
										  <td style="text-align: center;" colspan="2">
								            <div style="width: 20%; margin: 0 auto;">
								          		<div class="cbk-middle-one-bg-top-but" align="center" style="text-align: center;">
												
													
													<div class="new-but-q-save" onclick="generate();" id="productc">保存</div>
					    					<div class="new-but-cancel" onclick="parent.closelay();">取消</div>
												</div>
											</div>
										</td>
									</tr>
								</table>
								<table id="temp"></table>
							</div>

							<div id="zhongxin2" class="center" style="display: none">
								<br />
								<br />
								<br />
								<br />
								<img src="static/images/jiazai.gif" /><br />
								<h4 class="lighter block green">
									<strong id="second_show">10秒</strong>
								</h4>
							</div>

						</form>

					</div>

				</div>
				<!-- /.col -->
			</div>
		</div>
		<!-- /.main-content -->
	<!-- /.main-container -->
	<script type="text/javascript">
			var path='<%=path%>';
		</script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script src="static/js/myjs/productCodeTB.js"></script>
	<script type="text/javascript">
			var path='<%=path%>';
		  /*   $(function (){
				 reductionFields()
			});   */
			//修改时还原属性列表
			function reductionFields(){
				$('#TREE_TYPE').val('${pd.TREE_TYPE}');
				$('#DISPLAY_TYPE').val('${pd.DISPLAY_TYPE}');
				if('${pd.TYPE}' == '005-1'){
					$('#td_leader_name').show();
					$('#td_leader').show();
					$('#LEADER').val('${pd.LEADER}');
				}else{
					$('#LEADER').val('0');
					$('#td_leader_name').hide();
					$('#td_leader').hide();
				}
				var msg = '${msg}';
				if('edit' == msg){
					$('#TREE_TYPE').attr("disabled", "disabled");
					var nowarField = '${pd.FIELDLIST}';
					if(nowarField!=""){
						var fieldarray = nowarField.split('Q351412933');
						for(var i=0;i<fieldarray.length;i++){
							if(fieldarray[i] != ''){
								appendC(fieldarray[i]);
								arField[i] = fieldarray[i];
							}
						}	
					}
					
				}
			}

			function quote_change(){
				if($('#is_quote').val() == '是'){
					$('#is_dic').val('否');
					$('#dic_type').val('');
				}
			}
			
			function dic_change(){
				if($('#is_dic').val() == '是'){
					$('#is_quote').val('否');
					$('#quote_type').val('');
				}
			}
			
			//获取子节点，所有父节点的name的拼接字符串
			function getFilePath(treeObj){
				if(treeObj==null)return "";
				var filename = treeObj.name;
				var pNode = treeObj.parentNode;
				if(pNode!=null){
					filename = getFilePath(pNode) +">"+ filename;
				}
				return filename;
			}
			
			var divHot = $("#typeTree");
			function zTreeBeforeClick(treeId, treeNode, clickFlag) {
				if(!treeNode.isParent){
					$("#FA_TYPE").val(treeNode.id);
					var filename = getFilePath(treeNode);
					$("#FA_TYPE_TMP").val(filename);
					divHot.slideUp("slow");
				}
			}

			//更换类型
			function typeChage(){
				var typeVal = $('#TYPE').val();
				if(typeVal == '005-1'){
					$('#td_leader_name').show();
					$('#td_leader').show();
					$('#FA_TYPE').val('');
					$('#FA_TYPE_TMP').val('');
					$('#td_fa_type_name').hide();
					$('#td_fa_type').hide();
				}else{
					if(typeVal == '005-10'){
						$('#td_fa_type_name').show();
						$('#td_fa_type').show();
					}else{
						$('#FA_TYPE').val('');
						$('#FA_TYPE_TMP').val('');
						$('#td_fa_type_name').hide();
						$('#td_fa_type').hide();
					}
					$('#LEADER').val('0');
					$('#td_leader_name').hide();
					$('#td_leader').hide();
				}
				var jsonData="{";
				var items='"rows":[';
				var nowarField = $("#TYPE option:selected").data("extend1");
				
				var fieldarray = nowarField.split('Q351412933'); 
				var rows=$('#dg').datagrid('getRows');
				var itemTotal=0;
				for (var i = 0; i < fieldarray.length; i++) {
					if(fieldarray[i] != ''){
						var tempStr=fieldarray[i].split('#');
						var itemDetail="{";
					    var itemIndex=i+1;
					    itemDetail+=
							'"index":"'+itemIndex+'",'+
							'"remark":"'+tempStr[2]+'",'+
							'"name":"'+tempStr[0]+'",'+
							'"type":"'+tempStr[1]+'",'+
							'"length":"'+tempStr[4]+'",'+
							'"points":"'+tempStr[5]+'",'+
							'"defaults":"'+tempStr[3]+'",'+
							'"sortNum":"'+tempStr[6]+'",'+
							'"isDic":"'+tempStr[7]+'",'+
							'"dicType":"'+tempStr[8]+'",'+
							'"dicId":"'+tempStr[9]+'",'+
							'"isQuote":"'+tempStr[10]+'",'+
							'"tableName":"'+tempStr[11]+'",'+
							'"tableId":"'+tempStr[12]+'",'+
							'"isNew":"'+tempStr[13]+'",'+
							'"isMust":"'+tempStr[14]+'",'+
							'"isQy":"'+tempStr[15]+'",'+
							'"isList":"'+tempStr[16]+'",'+
							'"isFlow":"'+tempStr[17]+'",'+
                            '"isSelect":"'+tempStr[18]+'",';
							if(tempStr[19]!=undefined){
								itemDetail+= '"layout":"'+tempStr[19]+'"},';
							}else{
								itemDetail+= '"layout":"0"},';
							}
						items+=itemDetail;
						itemTotal=itemIndex;
					}
				}
				items=items.substring(0,items.lastIndexOf(","));
				if(items!=""){
					jsonData+='"total":'+itemTotal+',';
					items+=']';
					jsonData+=items;
					jsonData+='}';
					jsonData = JSON.parse(jsonData);//转换成json对象  
					$('#dg').datagrid('loadData', jsonData); 
				}else{
					jsonData="{}";
				}
				
				
			 	/* arField = [];index=0;
				var extendField = [];
				$("#fields").children().each(function () {
					if(!$(this).hasClass("defaultCol")){
						extendField.push($(this).data("fieldList"));
					};
				});
				$("#fields").children().remove();
				var nowarField = $("#TYPE option:selected").data("extend1");
				var fieldarray = nowarField.split('Q351412933');
				fieldarray = fieldarray.concat(extendField);
				//debugger
				for(var i=0;i<fieldarray.length;i++){
					if(fieldarray[i] != ''){
						appendC(fieldarray[i]);
						arField[i] = fieldarray[i];
					}
				}
				$("#tsTest").hide();
				$("#tsTest1").show();
				if(undefined!=$('#fields').find("tr").children('td').eq(1).html() && $('#fields').find("tr").children('td').eq(1).html().indexOf("TOPIC_INFO")>-1){
					$("#fields").empty();
				}
				
				if($('#TYPE').val() == '005-7'){$("#tsTest").show();$("#tsTest1").hide();
					index=0;$("#fields").empty();
					var tr1='TOPIC_INFO#String#研究事项内容##200#0#1#否#无#无#否#无#无#0#1#1#1#1#1Q351412933';
					arField[0] = tr1;
					var fieldarray = tr1.split('#');
					var tbstr = '<tr>'+
					'<td class="center">'+Number(index+1)+'</td>'+
					'<td class="center">'+fieldarray[0]+'<input type="hidden" name="field0'+index+'" value="'+fieldarray[0]+'"></td>'+
					'<td class="center">'+fieldarray[1]+'<input type="hidden" name="field1'+index+'" value="'+fieldarray[1]+'"></td>'+
					'<td class="center">'+fieldarray[4]+'<input type="hidden" name="field4'+index+'" value="'+fieldarray[4]+'"></td>'+
					'<td class="center">'+fieldarray[5]+'<input type="hidden" name="field5'+index+'" value="'+fieldarray[5]+'"></td>'+
					'<td class="center">'+fieldarray[2]+'<input type="hidden" name="field2'+index+'" value="'+fieldarray[2]+'"></td>'+
					'<td class="center">'+fieldarray[3]+'<input type="hidden" name="field3'+index+'" value="'+fieldarray[3]+'"></td>'+
					'<td class="center">'+fieldarray[6]+'<input type="hidden" name="field6'+index+'" value="'+fieldarray[6]+'"></td>'+
					'<td class="center">'+fieldarray[7]+'<input type="hidden" name="field7'+index+'" value="'+fieldarray[7]+'"></td>'+
					'<td class="center">'+fieldarray[8]+'<input type="hidden" name="field8'+index+'" value="'+fieldarray[8]+'"></td>'+
					'<td class="center" hidden>'+fieldarray[9]+'<input type="hidden" name="field9'+index+'" value="'+fieldarray[9]+'"></td>'+
					'<td class="center">'+fieldarray[10]+'<input type="hidden" name="field10'+index+'" value="'+fieldarray[10]+'"></td>'+
					'<td class="center">'+fieldarray[11]+'<input type="hidden" name="field11'+index+'" value="'+fieldarray[11]+'"></td>'+
					'<td class="center" hidden>'+fieldarray[12]+'<input type="hidden" name="field12'+index+'" value="'+fieldarray[12]+'"></td>'+
					'<td class="center" style="width:100px;">'+
						'<input type="hidden" name="field'+index+'" value="'+tr1+'">'+
						'<a class="btn btn-mini btn-info" title="编辑" onclick="editField(\''+tr1+'\',\''+index+'\')"><i class="ace-icon fa fa-pencil-square-o bigger-120"></i></a>&nbsp;'+
					'</td>'+
					'</tr>';
					$("#fields").append(tbstr);
					index=1;
				} */
			}
			var connectionTableData="[";
			<c:forEach items="${templateList}" var= "item" varStatus="status">
				var it = '{"id":"${item.C_PHYSICSNAME}","text":"${item.NAME}"';
			    if('${status.index+1}' == '${templateList.size()}'){
			    	it+="}";
			    }else{
			    	it+="},";
			    }
			    connectionTableData+=it;
			</c:forEach>
			connectionTableData+=']';
			connectionTableData= JSON.parse(connectionTableData);//转换成json对象
			var arrayData = []; 
			var typeData=[{"value":"String","text":"String"},{"value":"Date","text":"Date"},{"value":"Integer","text":"Integer"},{"value":"Double","text":"Double"},{"value":"File","text":"File"},{"value":"Image","text":"Image"},{"value":"Clob","text":"Clob"}];
		 	var isDicData=[{"value":"否","text":"否"},{"value":"是","text":"是"}];
			var isQuoteData=[{"value":"否","text":"否"},{"value":"是","text":"是"}];
			var dicTypeData="[";
			var dicTypeDataHidden="[";
			dicTypeData+='{"value":"无","text":"无"},';
			dicTypeDataHidden+='{"value":"无","text":"无"},';
			<c:forEach items="${varList}" var= "item" varStatus="status">
				var it = '{"value":"${item.NAME}","text":"${item.NAME}"';
				var its = '{"value":"${item.DICTIONARIES_ID}","text":"${item.NAME}"';
			    if('${status.index+1}' == '${varList.size()}'){
			    	it+="}";
			    	its+="}";
			    }else{
			    	it+="},";
			    	its+="},";
			    }
			    dicTypeData+=it;
			    dicTypeDataHidden+=its;
			</c:forEach>
			dicTypeData+=']';
			dicTypeDataHidden+=']';
			dicTypeData= JSON.parse(dicTypeData);//转换成json对象
			dicTypeDataHidden= JSON.parse(dicTypeDataHidden);//转换成json对象
			var tableNameData="[";
			var tableNameDataHidden="[";
			tableNameData+='{"value":"无","text":"无"}';
			tableNameDataHidden+='{"value":"无","text":"无"}';
			
			tableNameData+=']';
			tableNameDataHidden+=']';
			tableNameData= JSON.parse(tableNameData);//转换成json对象
			tableNameDataHidden= JSON.parse(tableNameDataHidden);//转换成json对象
			var isNewData=[{"value":"0","text":"否"},{"value":"1","text":"是"}];
			var isMustData=[{"value":"0","text":"否"},{"value":"1","text":"是"}];
			var isQyData=[{"value":"0","text":"否"},{"value":"1","text":"是"}];
			var isListData=[{"value":"0","text":"否"},{"value":"1","text":"是"}];
			var isFlowData=[{"value":"0","text":"否"},{"value":"1","text":"是"}];
			var isSelectData=[{"value":"0","text":"否"},{"value":"1","text":"是"}];
            var layoutData=[{"value":"0","text":"默认","selected":true},{"value":"1","text":"一列"},{"value":"1","text":"二列"},{"value":"1","text":"三列"}];
			var fieldCollection = '${pd.FIELDLIST}';
			var fields = fieldCollection.split('Q351412933');
			var total=fields.length-1;
			var data="{";
			data+='"total":'+total+',';
			var rows='"rows":[';
			for(var i=0;i<fields.length;i++){
				if(fields[i] != ''){
					var detail="{";
					var fieldDetail = fields[i].split('#');
				    var index=i+1;
					detail+=
						'"index":"'+index+'",'+
						'"remark":"'+fieldDetail[2]+'",'+
						'"name":"'+fieldDetail[0]+'",'+
						'"type":"'+fieldDetail[1]+'",'+
						'"length":"'+fieldDetail[4]+'",'+
						'"points":"'+fieldDetail[5]+'",'+
						'"defaults":"'+fieldDetail[3]+'",'+
						'"sortNum":"'+fieldDetail[6]+'",'+
						'"isDic":"'+fieldDetail[7]+'",'+
						'"dicType":"'+fieldDetail[8]+'",'+
						'"dicId":"'+fieldDetail[9]+'",'+
						'"isQuote":"'+fieldDetail[10]+'",'+
						'"tableName":"'+fieldDetail[11]+'",'+
						'"tableId":"'+fieldDetail[12]+'",'+
						'"isNew":"'+fieldDetail[13]+'",'+
						'"isMust":"'+fieldDetail[14]+'",'+
						'"isQy":"'+fieldDetail[15]+'",'+
						'"isList":"'+fieldDetail[16]+'",'+
						'"isFlow":"'+fieldDetail[17]+'",'+
						'"isSelect":"'+fieldDetail[18]+'",';
						if(fieldDetail[19]!=undefined){
                            detail+= '"layout":"'+fieldDetail[19]+'"';
						}else{
                            detail+= '"layout":"0"';
						}
					if(index==total){
						detail+="}";
					}else{
						detail+="},";
					}
					rows+=detail;
				}
			}
			rows+=']';
			data+=rows;
			data+='}';
			data = JSON.parse(data);//转换成json对象  
			$(document).ready(function(){ 
				$('#dg').datagrid('loadData', data);
				var setting = {
				    showLine: true,
				    checkable: false,
				    callback: {
				        beforeClick: zTreeBeforeClick
				    },
				};
				var zn = '${zTreeNodes}';
				var zTreeNodes = eval(zn);
				zTree = $("#typeTree").zTree(setting, zTreeNodes);
				var fa_type = '${pd.FA_TYPE}';
				if(fa_type != ""){
					var node = zTree.getNodeByParam('id', ''+fa_type);//获取id为1的点
					$("#FA_TYPE").val(fa_type);
					var filename = getFilePath(node);
					$("#FA_TYPE_TMP").val(filename);
					zTree.selectNode(node);//选择点
					zTree.expandNode(node, true, false, true);
				}else{
					zTree.expandNode(zTreeNodes[0], true);
				}
			  	
			  	$("#FA_TYPE_TMP").click(function() {  //树形事件
		            var x =  0;  //$(this).offset().left
		            divHot.css({ top: 28 + "px"});
		        	divHot.css({ left: x + "px"});
		            divHot.show(); 
		        }); 
			  	divHot.hover(function () {  
		            //鼠标按上事件  
		        }, function () {  
		            //鼠标经过事件  
		            divHot.slideUp("slow");  
		        });
			  	
				if('${pd.TYPE}' == '005-10'){
					$('#td_fa_type_name').show();
					$('#td_fa_type').show();
				}else{
					$('#td_fa_type_name').hide();
					$('#td_fa_type').hide();
				}
			}); 
			var editIndex = undefined;
			function endEditing(){
				if (editIndex == undefined){return true}
				if ($('#dg').datagrid('validateRow', editIndex)){
					$('#dg').datagrid('endEdit', editIndex);
					editIndex = undefined;
					return true;
				} else {
					return false;
				}
			}
			function onClickRow(index){
				if (editIndex != index){
					if (endEditing()){
						$('#dg').datagrid('selectRow', index)
								.datagrid('beginEdit', index);
						editIndex = index;
						itemChange(index);
					} else {
						$('#dg').datagrid('selectRow', editIndex);
					}
				}
			}
			function append(){
				if (endEditing()){
					/*var flag=$('#dg').datagrid('getRows');
					if(flag[0].index == '没有相关记录！'){
						$('#dg').datagrid('cancelEdit', 0).datagrid('deleteRow', 0);
					}*/
					var max = getMaxSortNum();
					max++;
					var index=$('#dg').datagrid('getRows').length+1;
					console.log($('#dg').datagrid('getRows').length);
					$('#dg').datagrid('appendRow',{index:index,remark:"",name:"",type:'String',length:'255',points:'0',defaults:"",sortNum:max,isDic:'否',dicType:'无',dicId:'无',isQuote:'否',tableName:'无',tableId:'无',isNew:'1',isMust:'1',isQy:'0',isList:'1',isFlow:'0',isSelect:'0',layout:'0'});
					editIndex = $('#dg').datagrid('getRows').length-1;
					$('#dg').datagrid('selectRow', editIndex)
							.datagrid('beginEdit', editIndex);
					itemChange(editIndex);
				}
			}
			function removeit(){
				if (editIndex == undefined){return}
				$('#dg').datagrid('cancelEdit', editIndex)
						.datagrid('deleteRow', editIndex);
				editIndex = undefined;
				
			}
			function accept(){
				if (endEditing()){
					$('#dg').datagrid('acceptChanges');
				}
			}
			function reject(){
				$('#dg').datagrid('rejectChanges');
				editIndex = undefined;
			}
			function itemChange(index) {
			   var editors = $('#dg').datagrid('getEditors', index);    
			   var remarkEditor = editors[0];
			   var nameEditor = editors[1];
			   var typeEditor = editors[2];
              /*  remarkEditor.target.textbox({ onChange: function (newValue, oldValue) {
                   if(newValue != undefined){
		                    var full = pinyin.getFullChars(newValue).toUpperCase(); 
		                    var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'name'});
							$(ed.target).textbox('setValue',full);
		                 }
	                }
               }); */
               nameEditor.target.textbox({ onChange: function (newValue, oldValue) {
                   if(newValue != undefined){
		                    var name = newValue.toUpperCase();
		                    var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'name'});
							$(ed.target).textbox('setValue',name);
		                 }
	                }
               });
               var typeFlag=false;
               var type=$(typeEditor.target).combobox('getValue');
               typeEditor.target.combobox({ onSelect: function (record) {
            	   		typeFlag=true;
	              	    var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'dicType'});
	              	    var length = $('#dg').datagrid('getEditor', {index:editIndex,field:'length'});
	              	    var points = $('#dg').datagrid('getEditor', {index:editIndex,field:'points'});
				  	    if(record.text == 'Integer'){
				  	    	$(length.target).textbox('setValue', 11);
				  	    	$(points.target).textbox('setValue', 0);
						}else if(record.text == 'Date'){
							$(length.target).textbox('setValue', 32);
							$(points.target).textbox('setValue', 0);
						}else if(record.text == 'Double'){
							$(length.target).textbox('setValue', 11);
							$(points.target).textbox('setValue', 2);
						}else{
							$(length.target).textbox('setValue', 255);
							$(points.target).textbox('setValue', 0);
						}
				    }
               });
               if(!typeFlag){
            	   $(typeEditor.target).combobox('setValue',type);
               }
               
               var editor1 = editors[7];
               var editor2 = editors[9];//隐藏字典ID
               var editor3 = editors[10];
               var editor4 = editors[12];//隐藏引用表ID
               var editor5 = editors[8];
               var editor6 = editors[11];
               var dicIdFlag=false;
               var dicId=$(editor5.target).combobox('getText');
               console.log(dicId);
               editor5.target.combobox({ onSelect: function (record) {
            	    dicIdFlag = true;
            	    var dicType=$(editor2.target).combobox('getValue');
              	    var dicIdObject = $('#dg').datagrid('getEditor', {index:editIndex,field:'dicId'});
               		for (var i = 0; i < dicTypeDataHidden.length; i++) {
               			if(dicTypeDataHidden[i].text == record.text){
               				$(dicIdObject.target).combobox('setValue', dicTypeDataHidden[i].value);
               			}
					}
			  	  }
        	   });
              var tableIdFlag=false;
              var tableId=$(editor6.target).combobox('getText');
              console.log(tableId);
              editor6.target.combobox({ onSelect: function (record) {
            	  dicIdFlag = true;
            	  var tableName=$(editor4.target).combobox('getValue');
              	  var tableIdObject = $('#dg').datagrid('getEditor', {index:editIndex,field:'tableId'});
                  for (var i = 0; i < tableNameDataHidden.length; i++) {
              		if(tableNameDataHidden[i].text == record.text){
              			$(tableIdObject.target).combobox('setValue', tableNameDataHidden[i].value);
              		}
				  }
			    }
        	  });
               var isDicFlag=false;
               var isDic=$(editor1.target).combobox('getValue');
               editor1.target.combobox({ onSelect: function (record) {
            	   isDicFlag=true;
               	    var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'dicType'});
				  	    if(record.text== '否'){
							$(ed.target).combobox('setValue', '无');
	                	}else{
	                		$(ed.target).combobox('setValue', '');
	                	}
				    }
               });
               if(!isDicFlag){
            	   $(editor1.target).combobox('setValue',isDic);
               }
               var dicTypeFlag=false;
               var dicType=$(editor2.target).combobox('getValue');
               console.log(dicType);
               editor2.target.combobox({ onSelect: function (record) {
            	   		dicTypeFlag=true;
	               	    var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'isDic'});
	               	    var dicIdObject = $('#dg').datagrid('getEditor', {index:editIndex,field:'dicType'});
                  	    if(record.text== '无'){
							$(ed.target).combobox('setValue', '否');
							$(dicIdObject.target).combobox('setValue', '无');
	                	}else{
	                		$(ed.target).combobox('setValue', '是');
	                		for (var i = 0; i < dicTypeDataHidden.length; i++) {
	                			if(dicTypeDataHidden[i].text == record.text){
	                				$(dicIdObject.target).combobox('setValue', dicTypeDataHidden[i].text);
	                			}
							}
	                	}
				    }
               });
               if(!dicTypeFlag){
            	   $(editor2.target).combobox('setValue',dicType);
               }
               var isQuoteFlag=false;
               var isQuote=$(editor3.target).combobox('getValue');
               editor3.target.combobox({ onSelect: function (record) {
            	         isQuoteFlag=true;
	               		 var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'tableName'});
	               		 if(record.text== '否'){
							$(ed.target).combobox('setValue', '无');
	                	}else{
	                		$(ed.target).combobox('setValue', '');
	                	}
				    }
               });
               if(!isQuoteFlag){
            	   $(editor3.target).combobox('setValue',isQuote);
               }
               var tableNameFlag=false;
               var tableName=$(editor4.target).combobox('getValue');
               console.log(tableName);
               editor4.target.combobox({ onSelect: function (record) {
            	        tableNameFlag=true;
	               		var ed = $('#dg').datagrid('getEditor', {index:editIndex,field:'isQuote'});
	               	    var tableIdObject = $('#dg').datagrid('getEditor', {index:editIndex,field:'tableName'});
	                	if(record.text== '无'){
							$(ed.target).combobox('setValue', '否');
							$(tableIdObject.target).combobox('setValue', '无');
	                	}else{
	                		$(ed.target).combobox('setValue', '是');
	                		for (var i = 0; i < tableNameDataHidden.length; i++) {
	                			if(tableNameDataHidden[i].text == record.text){
	                				$(tableIdObject.target).combobox('setValue', tableNameDataHidden[i].text);
	                			}
							}
	                	}
				    }
               });
               if(!tableNameFlag){
            	   $(editor4.target).combobox('setValue',tableName);
               } 
			}
			//生成
			function generate(){
				var arrayData = []; 
				$('#dg').datagrid('acceptChanges');
				var rows=$('#dg').datagrid('getRows');
				$.each(rows,function (i) { 
					var data=rows[i].name + '#' + rows[i].type + '#' + rows[i].remark + '#' + rows[i].defaults + '#' 
					+ rows[i].length + '#' + rows[i].points + '#' + rows[i].sortNum + '#' + rows[i].isDic + '#' + rows[i].dicType + '#' + rows[i].dicId + "#"
					+ rows[i].isQuote + '#' + rows[i].tableName + '#' + rows[i].tableId
					+ '#' + rows[i].isNew+ '#' + rows[i].isMust+ '#' + rows[i].isQy+ '#' + rows[i].isList+ '#' + rows[i].isFlow+ '#' + rows[i].isSelect+ '#' + rows[i].layout;
					arrayData.push(data);
					var tr = $('<tr><td><input type="hidden" name="field'+i+'" value="'+data+'">' +'</td></tr>');
					$("#temp").append(tr);
				}); 
				$("#zindex").val(rows.length);
				if($("#NAME").val()==""){
					$("#NAME").tips({
						side:3,
			            msg:'输入名称',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#NAME").focus();
					return false;
				}
				if($("#TYPE").val()==""){
					$("#TYPE").tips({
						side:3,
			            msg:'请选择类型',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#TYPE").focus();
					return false;
				}
				if($("#TYPE").val()=='005-7'&&$("#ORDER_ID").val()==""){
					$("#ORDER_ID").tips({
						side:3,
			            msg:'排序号必填',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#ORDER_ID").focus();
					return false;
				}
				if($("#TB_TYPE").val()==""){
					$("#TB_TYPE").tips({
						side:3,
			            msg:'请选择是否字典表',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#TB_TYPE").focus();
					return false;
				}
			
				if($("#fields").html() == ''){
					$("#table_report").tips({
						side:3,
			            msg:'请添加属性',
			            bg:'#AE81FF',
			            time:2
			        });
					return false;
				}

				if(!confirm("确定要生成吗?")){
					return false;
				}
				var strArField = '';
				for(var i=0;i<arrayData.length;i++){
					strArField = strArField + arrayData[i] + "Q351412933";
				}
				$("#FIELDLIST").val(strArField); 	//属性集合
				$("#TREE_TYPE").removeAttr("disabled");
				$("#RELATION_TABLE").val($("#CONNECTION_TABLE").val());
			 	$.ajax({
		            //几个参数需要注意一下
		            type: "POST",//方法类型
		            dataType: "json",//预期服务器返回的数据类型
		            url: path+"/templates/proCode.do" ,//url
		            data: $('#Form_add').serialize(),
		            success: function (result) {
		                 if (result.success) {
		                	 var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	                    	 parent.layer.close(index); //再执行关闭    
		                }
		            },
		            error : function() {
		            	layer.alert("异常！");
		            }
		        }); 
			}
			function getMaxSortNum() {
				var rows=$('#dg').datagrid('getRows');
				var max = 0;
				if(rows.length == 0){
					return max;
				}else{
					 max = rows[0].sortNum;
				}
				for (var i = 1; i < rows.length; i++) {
					if(rows[i].sortNum > max){
						max=rows[i].sortNum;
					}
				}
				return max;
			}
			$.extend($.fn.validatebox.defaults.rules, {
				nameValid: {
		            validator: function (value) {
		                return /^[A-Za-z0-9\_]+$/i.test(value);
		            },
		            message: '属性名首字母必须为字母或下划线'
		        },
		        snameValid:{
		        	validator: function(value){
		        		var valid=false;
		        		var rows=$('#dg').datagrid('getRows');
						for (var i = 0; i < rows.length; i++) {
							if(editIndex!=undefined && editIndex!=i && rows[i].name == value.toUpperCase()){
								valid = true;
								break;
							}
						}
						if(!valid){
							return true;
						}else{
							return false;
						}
		            },
			        message: '属性名重复'
		        },
		        numberValid:{
		        	 validator: function (value) {
			                return /^[0-9]+$/i.test(value);
		            },
		            message: '请输入数字'
		        }/* ,
		        chineseValid:{
		        	 validator: function (value) {
			                return /^[\u0391-\uFFE5]+$/i.test(value);
		            },
		            message: '请输入汉字'
		        },
		        numberEqualsValid:{
		        	validator: function(value){
		        		var valid=false;
		        		var rows=$('#dg').datagrid('getRows');
						for (var i = 0; i < rows.length; i++) {
							if(editIndex!=undefined && editIndex!=i && rows[i].sortNum == value){
								valid = true;
								break;
							}
						}
						if(!valid){
							return true;
						}else{
							return false;
						}
		            },
			        message: '显示序号重复'
		        } */
		    });
			
		
			
		</script>

</body>
</html> 