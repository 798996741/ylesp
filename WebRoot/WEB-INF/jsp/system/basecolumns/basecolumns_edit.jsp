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

 <style type="text/css">
     #dialog-add, #dialog-message, #dialog-comment {
         display: none;
     }


     .commitbox_top textarea {
         width: 95%;
         height: 165px;
    /*      display: block; */
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
     .btn-danger {
         color: #fff;
         border-color: #1d9aee;
         background-color: #1d9aee;
     }
#dialog-add{
	    padding-top: 15px;
}
.lbl{
	width:60px;
	min-width:60px;
	border:0px;
}

 </style>
<!-- 日期框 -->
</head>
<body class="no-skin">


	<div  style="width:100%;margin-left:0px;">

		<section class="container-fluid">
		
			<div class="seat-middle">
				<div class="xtyh-middle-r zxzgl-middle-r">
					<form action="templates/proCode.do" name="Form_add" id="Form_add" method="post">
                        <input type="hidden" name="zindex" id="zindex" value="0">
                        <input type="hidden" name="FIELDLIST" id="FIELDLIST" value="">
                        <input type="hidden" name="C_PHYSICSNAME" id="C_PHYSICSNAME" value="${pd.C_PHYSICSNAME}"> 
                        <input type="hidden" name="TEMPLATE_ID" id="TEMPLATE_ID" value="${pd.TEMPLATE_ID}" />
                        <input type="hidden" name="msg" id="msg" value="${msg}" /> 
                        <input type="hidden" name="table_type" id="table_type" value="${pd.TYPE}" /> 
                        <input type="hidden" name="dic_type_hid" id="dic_type_hid" value="${pd.TB_TYPE}" />
						<table   id="example2" class="table table-bordered table-hover">
						
	                         <thead>
	                         <tr>
	                             <th class="center" style="width: 40px;">序号</th>
	                             <th class="center" style="width: 60px;">属性名</th>
	                             <th class="center" style="width: 40px;">类型</th>
	                             <th class="center" style="width: 40px;">长度</th>
	                             <th class="center" style="width: 60px;">小数点</th>
	                             <th class="center" style="width: 40px;">备注</th>
	                             <!-- <th class="center" style="width:79px;">前台录入</th> -->
	                             <th class="center" style="width: 60px;">默认值</th>
	                             <th class="center" style="width: 80px;">显示序号</th>
	                             <th class="center" style="width: 80px;">是否字典</th>
	                             <th class="center" style="width: 80px;">字典类型</th>
	                             <th class="center" hidden>字典类型ID</th>
	                             <th class="center" style="width: 80px;">是否引用</th>
	                             <th class="center" style="width: 80px;">引用表名</th>
	                             <th class="center" hidden>表名ID</th>
	                             <th class="center" style="width: 69px;">操作</th>
	                         </tr>
	                         </thead>
	
	                         <tbody id="fields"></tbody>
						</table>
 						
						<div class="step-button">  
		                     <span id="tsTest" style="color: red;display: none;margin-right: 10px">* 无需添加 议题类型、责任单位、责任人、预计时间</span>
		                     <span id="tsTest1" style="color: red;">&nbsp;</span>
		                     
		                     <div class="new_but_xzb " onclick="dialog_open();">新增</div>
		                     <div class="new_but_scs " onclick="save();" id="productc">生成</div>
		                     <div class="new-but-cancel " id="gb" onclick="parent.closelay();">取消</div>
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
			
		</section>

	</div>
		
	
	
	 <!-- 添加属性  -->
	<input type="hidden" name="hcdname" id="hcdname" value="" />
	<input type="hidden" name="msgIndex" id="msgIndex" value="" />
	<input type="hidden" name="dtype" id="dtype" value="String" />
	<!-- <input type="hidden" name="isQian" id="isQian" value="是" /> -->
	<div id="dialog-add">

		<table class="margin main-padding-t-15">
			<tr height="30px;">
				<td style="padding-left: 16px; text-align: right;">属性名：</td>
				<td style="width:40%;">
					<div class="nav-search">
						<input class="nav-search-input" style="width: 80%; text-indent: 1em;" name="dname" id="dname"
							type="text" value="" placeholder="首字母必须为字母或下划线" title="属性名" />
					</div>
				</td>
				<td style="padding-left: 16px; text-align: right;">其备注：</td>
				<td style="width:40%;">
					<div class="nav-search">
						<input class="nav-search-input" style="width: 269px; text-indent: 1em;" name="dbz" id="dbz" type="text" value="" placeholder="例如 name的备注为 '姓名'" title="备注" />
					</div>
				</td>
			</tr>
			<tr height="30px;">
				<td style="padding-left: 16px; text-align: right;">默认值：</td>
				<td><div class="nav-search">
						<input class="nav-search-input"
							style="width: 80%; text-indent: 1em;" name="ddefault"
							id="ddefault" type="text" value="" placeholder="后台附加值时生效"
							title="默认值" />
					</div>
				</td>
				<td style="padding-left: 16px; text-align: right;">是否引用：</td>
				<td>
					<div class="nav-search">
						<select name="is_quote" id="is_quote"
							style="width: 80%; height: 26px; text-indent: 1em;"
							onchange="quote_change();">
							<option value="否">否</option>
							<option value="是">是</option>
						</select>
					</div>
				</td>
			</tr>
			<tr height="30px;">
				<td style="padding-left: 16px; text-align: right;">是否字典：</td>
				<td>
					<div class="nav-search">
						<select name="is_dic" id="is_dic"
							style="width: 80%; height: 26px; text-indent: 1em;"
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
							style="width: 80%; height: 26px; text-indent: 1em;">
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
			<tr height="30px;">
				<td style="padding-left: 16px; text-align: right;">属性类型：</td>
				<td style="padding-bottom: 5px;width:40%;">
					<label style="float: left; padding-left: 6px;">
						<input name="form-field-radiot"  id="form-field-radio1"
						onclick="setType('String');" type="radio" value="icon-edit">
						<span class="lbl" >String</span>
					</label> 
					<label style="float: left; padding-left: 6px;">
						<input name="form-field-radiot" id="form-field-radio3"
						onclick="setType('Date');" type="radio" value="icon-edit">
						<span class="lbl">Date</span>
					</label> 
					<label style="float: left; padding-left: 6px;">
						<input name="form-field-radiot" id="form-field-radio2"
						onclick="setType('Integer');" type="radio" value="icon-edit">
						<span class="lbl">Integer</span>
					</label> 
					
					<label style="float: left; padding-left: 6px;">
						<input
						name="form-field-radiot" id="form-field-radio33"
						onclick="setType('Double');" type="radio" value="icon-edit">
						<span class="lbl">Double</span>
					</label> 
					<label style="float: left; padding-left: 6px;">
						<input
						name="form-field-radiot" id="form-field-radio33"
						onclick="setType('File');" type="radio" value="icon-edit"><span
						class="lbl">File</span>
					</label> 
					<label style="float: left; padding-left: 6px;">
					 	<input
						name="form-field-radiot" id="form-field-radio33"
						onclick="setType('Image');" type="radio" value="icon-edit">
						<span class="lbl">Image</span>
					</label>
				</td>
				<td style="padding-left: 16px; text-align: right;">长度：</td>
				<td>
					<div class="nav-search" style="padding-right: 5px;">
						<input class="nav-search-input"
							style="width: 80%; text-indent: 1em;" name="flength"
							id="flength" type="number" value="" placeholder="长度" title="长度" />
						. <input class="nav-search-input" style="width: 59px;"
							name="decimal" id="decimal" type="number" value=""
							placeholder="小数" title="类型为Double时有效" />
					</div>
				</td>

			</tr>
			<tr height="30px;">
				<td style="padding-left: 16px; text-align: right;">显示序号：</td>
				<td>
					<div class="nav-search">
						<input class="nav-search-input"
							style="width: 80%; text-indent: 1em;" name="show_order"
							id="show_order" type="number" value="" placeholder="请输入显示序号"
							title="显示序号" />
					</div>
				</td>
				<td style="padding-left: 16px; text-align: right;">引用表名：</td>
				<td>
					<div class="nav-search">
						<select name="quote_type" id="quote_type"
							style="width: 80%; height: 26px; text-indent: 1em;">
							<option value="">请选择...</option>
							<c:choose>
								<c:when test="${not empty quoteList}">
									<c:forEach items="${quoteList}" var="var" varStatus="vs">
										<option value="${var.TEMPLATE_ID}">${var.NAME}</option>
									</c:forEach>
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>
						</select>
					</div>
				</td>
			</tr>
			<tr height="30px;">
				<td style="padding-left: 16px; text-align: right;">是否不在编辑页面显示：</td>
				<td>
					<div class="nav-search">
						<select name="ISNEW" id="ISNEW"
							style="width: 80%; height: 26px; text-indent: 1em;">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</td>
				<td style="padding-left: 16px; text-align: right;">是否必填：</td>
				<td>
					<div class="nav-search">
						<select name="ISMUST" id="ISMUST"
							style="width: 80%; height: 26px; text-indent: 1em;">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</td>
			</tr>
			<tr height="30px;">
				<td style="padding-left: 16px; text-align: right;">是否查询字段：</td>
				<td>
					<div class="nav-search">
						<select name="ISQY" id="ISQY"
							style="width: 80%; height: 26px; text-indent: 1em;">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</td>
				<td style="padding-left: 16px; text-align: right;">是否在查询列表显示：</td>
				<td>
					<div class="nav-search">
						<select name="ISLIST" id="ISLIST"
							style="width: 80%; height: 26px; text-indent: 1em;">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</td>
			</tr>
			<!-- <tr height="30px;">
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
							style="width: 100%; height: 26px; text-indent: 1em;">
							<option value="0">否</option>
							<option value="1">是</option>
						</select>
					</div>
				</td>
			</tr> -->
			<tr height="60px;">
				<td colspan="4" style="padding-left: 16px;">
					<input type="hidden" id="ISFLOW" name="ISFLOW" value="0">
					<input type="hidden" id="ISSELECT" name="ISSELECT" value="0">
					<span style="color:red;font-weight: bold; text-align: left;"> 
					
					注意：<br /> 
					1.请不要添加 ID 的主键，系统自动生成一个32位无序不重复字符序列作为主键<br /> 2. 主键为ID
						格式，所有字段的字母均用大写
				</span></td>
			</tr>

		</table>


		<div class="step-button">

			<div class="new-but-q-save" onClick="saveD()">保存</div>
			<div class="new-but-cancel" onclick="closelayA();">取消</div>

		</div>



	</div>


	<!-- /.main-container -->
<script type="text/javascript">
    var path='<%=path%>';
</script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript">
    //$("#table_report").DataTable();
    var path='<%=path%>';

	var arField = new Array();
	var index = 0;
	var newmylayA;
	//追加属性列表
	function appendC(value) {
		var fieldarray = value.split('#');
		var str = "";
		str = '<tr>' + '<td class="center">'
				+ Number(index + 1)
				+ '</td>'
				+ '<td class="center">'
				+ fieldarray[0]
				+ '<input type="hidden" name="field0'+index+'" value="'+fieldarray[0]+'"></td>'
				+ '<td class="center">'
				+ fieldarray[1]
				+ '<input type="hidden" name="field1'+index+'" value="'+fieldarray[1]+'"></td>'
				+ '<td class="center">'
				+ fieldarray[4]
				+ '<input type="hidden" name="field4'+index+'" value="'+fieldarray[4]+'"></td>'
				+
				/*'<td class="center">'+fieldarray[6]+'<input type="hidden" name="field6'+index+'" value="'+fieldarray[6]+'"></td>'+*/
				'<td class="center">'
				+ fieldarray[5]
				+ '<input type="hidden" name="field5'+index+'" value="'+fieldarray[5]+'"></td>'
				+ '<td class="center">'
				+ fieldarray[2]
				+ '<input type="hidden" name="field2'+index+'" value="'+fieldarray[2]+'"></td>'
				+ '<td class="center">'
				+ fieldarray[3]
				+ '<input type="hidden" name="field3'+index+'" value="'+fieldarray[3]+'"></td>'
				+ '<td class="center">'
				+ fieldarray[6]
				+ '<input type="hidden" name="field6'+index+'" value="'+fieldarray[6]+'"></td>'
				+ '<td class="center">'
				+ fieldarray[7]
				+ '<input type="hidden" name="field7'+index+'" value="'+fieldarray[7]+'"></td>'
				+ '<td class="center">'
				+ fieldarray[8]
				+ '<input type="hidden" name="field8'+index+'" value="'+fieldarray[8]+'"></td>'
				+ '<td class="center" hidden>'
				+ fieldarray[9]
				+ '<input type="hidden" name="field9'+index+'" value="'+fieldarray[9]+'"></td>'
				+ '<td class="center">'
				+ fieldarray[10]
				+ '<input type="hidden" name="field10'+index+'" value="'+fieldarray[10]+'"></td>'
				+ '<td class="center">'
				+ fieldarray[11]
				+ '<input type="hidden" name="field11'+index+'" value="'+fieldarray[11]+'"></td>'
				+ '<td class="center" hidden>'
				+ fieldarray[12]
				+ '<input type="hidden" name="field12'+index+'" value="'+fieldarray[12]+'"></td>'
				+ '<td class="center" style="width:100px;">'
				+ '<input type="hidden" name="field'+index+'" value="'+value+'">'
				+ '<a class="" title="编辑" onclick="editField(\'' + value
				+ '\',\'' + index + '\')">编辑</a>&nbsp;'
				+ '<a class="" title="删除" onclick="removeField(\'' + index
				+ '\')">删除</a>' + '</td>' + '</tr>';

		$("#fields").append(str);
		index++;
		$("#zindex").val(index);
	}

	//$(top.hangge());//关闭加载状态
	$(reductionFields());
	//修改时还原属性列表
	function reductionFields() {
		$('#TREE_TYPE').val('${pd.TREE_TYPE}');
		$('#DISPLAY_TYPE').val('${pd.DISPLAY_TYPE}');
		if ('${pd.TYPE}' == '005-1') {
			$('#td_leader_name').show();
			$('#td_leader').show();
			$('#LEADER').val('${pd.LEADER}');
		} else {
			$('#LEADER').val('0');
			$('#td_leader_name').hide();
			$('#td_leader').hide();
		}
		$('#TREE_TYPE').attr("disabled", "disabled");
		var nowarField = '${pd.FIELDLIST}';
		var fieldarray = nowarField.split('Q351412933');
		for (var i = 0; i < fieldarray.length; i++) {
			if (fieldarray[i] != '') {
				appendC(fieldarray[i]);
				arField[i] = fieldarray[i];
			}
		}
	}

	//打开编辑属性(新增)
	function dialog_open() {

		newmylayA = layer.open({
			type : 1,
			title : "表单配置",
			shade : 0.5,
			skin : 'demo-class',
			area : [ '1000px', '450px' ],
			content : $('#dialog-add'),
			cancel : function() {
				closelayA();
			}
		});
		/* $("#dialog-add").css("display","block"); */
		$("#dname").val('');
		$("#dbz").val('');
		$("#ddefault").val('');
		$("#msgIndex").val('');
		$("#dtype").val('String');
		/*$("#isQian").val('是');*/
		$("#form-field-radio1").attr("checked", true);
		$("#form-field-radio1").click();
		/*$("#form-field-radio4").attr("checked",true);
		$("#form-field-radio4").click();*/
		$("#flength").val(255);
		$("#show_order").val('');
		$("#is_dic").val('否');
		$("#dic_type").val('');
		$("#is_quote").val('否');
		$("#quote_type").val('');
		$("#ISNEW").val(0);
		$("#ISMUST").val(0);
		$("#ISQY").val(0);
		$("#ISLIST").val(0);
		$("#ISFLOW").val(0);
		$("#ISSELECT").val(0);
	}

	function editField(value, msgIndex) {
		newmylayA = layer.open({
			type : 1,
			title : "表单配置",
			shade : 0.5,
			skin : 'demo-class',
			area : [ '1000px', '450px' ],
			content : $('#dialog-add'),
			cancel : function() {
				closelayA();
			}
		});

		var efieldarray = value.split('#');
		$("#dname").val(efieldarray[0]); //属性名
		$("#hcdname").val(efieldarray[0]); //属性名 备份一份
		$("#dbz").val(efieldarray[2]); //备注
		$("#ddefault").val(efieldarray[3]); //默认值
		$("#msgIndex").val(msgIndex); //数组ID
		if (efieldarray[1] == 'String') { //类型
			$("#form-field-radio1").attr("checked", true);
			$("#form-field-radio1").click();
			$("#dtype").val('String');
		} else if (efieldarray[1] == 'Integer') {
			$("#form-field-radio2").attr("checked", true);
			$("#form-field-radio2").click();
			$("#dtype").val('Integer');
		} else if (efieldarray[1] == 'Double') {
			$("#form-field-radio33").attr("checked", true);
			$("#form-field-radio33").click();
			$("#dtype").val('Double');
		} else {
			$("#form-field-radio3").attr("checked", true);
			$("#form-field-radio3").click();
			$("#dtype").val('Date');
		}
		/*if(efieldarray[3] == '是'){
		    $("#form-field-radio4").attr("checked",true);
		    $("#form-field-radio4").click();
		    $("#isQian").val('是');
		}else{
		    $("#form-field-radio5").attr("checked",true);
		    $("#form-field-radio5").click();
		    $("#isQian").val('否');
		}*/
		$("#flength").val(efieldarray[4]); //长度
		$("#decimal").val(efieldarray[5]); //小数点
		$("#show_order").val(efieldarray[6]); //显示序号
		$("#is_dic").val(efieldarray[7]); //是否字典
		$("#dic_type").val(efieldarray[9]); //字典类型
		$("#is_quote").val(efieldarray[10]); //是否引用动态表
		$("#quote_type").val(efieldarray[12]); //动态表名
		$("#ISNEW").val(efieldarray[13]);
		$("#ISMUST").val(efieldarray[14]);
		$("#ISQY").val(efieldarray[15]);
		$("#ISLIST").val(efieldarray[16]);
		$("#ISFLOW").val(efieldarray[17]);
		$("#ISSELECT").val(efieldarray[18]);
	}

	/*     //关闭编辑属性
	 function cancel_pl(){
	 $("#dialog-add").css("display","none");
	 } */

	//保存编辑属性
	function saveD() {

		//var dname = $("#dtype").val().substr(0,1)+"_"+$("#dname").val(); 	 		 //属性名
		var dname = $("#dname").val(); //属性名

		var hcdname = $('#hcdname').val(); //备份的属性名
		var dtype = $("#dtype").val(); //类型
		var dbz = $("#dbz").val(); //备注
		/*var isQian = $("#isQian").val();*///是否前台录入
		var ddefault = $("#ddefault").val(); //默认值
		var msgIndex = $("#msgIndex").val(); //msgIndex不为空时是修改
		var flength = $("#flength").val(); //长度
		var decimal = $("#decimal").val(); //小数
		var show_order = $("#show_order").val(); //显示序号
		var is_dic = $("#is_dic").val(); //是否字典
		var dic_type = ""; //字典名称
		var dic_type_id = $("#dic_type").val();
		if (dic_type_id != '') {
			dic_type = $('#dic_type').find(
					"option[value='" + dic_type_id + "']").text();
		}
		var is_quote = $("#is_quote").val(); //是否引用
		var quote_type = ""; //字典名称
		var quote_type_id = $("#quote_type").val();
		if (quote_type_id != '') {
			quote_type = $('#quote_type').find(
					"option[value='" + quote_type_id + "']").text();
		}

		if (is_dic == "是") {
			if (dic_type == "") {
				$("#dic_type").tips({
					side : 3,
					msg : '请选择数据字典',
					bg : '#AE81FF',
					time : 2
				});
				$("#dic_type").focus();
				return false;
			}
		}
		if (dname == "") {
			$("#dname").tips({
				side : 3,
				msg : '输入属性名',
				bg : '#AE81FF',
				time : 2
			});
			$("#dname").focus();
			return false;
		} else {
			dname = dname.toUpperCase(); //转化为大写
			if (isSame(dname)) {
				var headstr = dname.substring(0, 1);
				var pat = new RegExp("^[0-9]+$");
				if (pat.test(headstr)) {
					$("#dname").tips({
						side : 3,
						msg : '属性名首字母必须为字母或下划线',
						bg : '#AE81FF',
						time : 2
					});
					$("#dname").focus();
					return false;
				}
			} else {
				if (msgIndex != '') {
					var hcdname = $("#hcdname").val();
					if (hcdname != dname) {
						if (!isSame(dname)) {
							$("#dname").tips({
								side : 3,
								msg : '属性名重复',
								bg : '#AE81FF',
								time : 2
							});
							$("#dname").focus();
							return false;
						}
						;
					}
					;
				} else {
					$("#dname").tips({
						side : 3,
						msg : '属性名重复',
						bg : '#AE81FF',
						time : 2
					});
					$("#dname").focus();
					return false;
				}
			}
		}

		if (dbz == "") {
			$("#dbz").tips({
				side : 3,
				msg : '输入备注',
				bg : '#AE81FF',
				time : 2
			});
			$("#dbz").focus();
			return false;
		}

		if ((0 - flength >= 0) || flength == "") {
			$("#flength").tips({
				side : 3,
				msg : '输入长度',
				bg : '#AE81FF',
				time : 2
			});
			$("#flength").focus();
			return false;
		}

		if ((0 - show_order >= 0) || show_order == "") {
			$("#show_order").tips({
				side : 3,
				msg : '输入显示序号',
				bg : '#AE81FF',
				time : 2
			});
			$("#show_order").focus();
			return false;
		}

		if ('' == decimal)
			decimal = 0;

		dbz = dbz == '' ? '无' : dbz;
		if (is_dic == "否") {
			dic_type_id = dic_type = '无';
		}
		if (is_quote == "否") {
			quote_type_id = quote_type = '无';
		}
		var newVal = $("#ISNEW").val();
		var mustVal = $("#ISMUST").val();
		var qyVal = $("#ISQY").val();
		var listVal = $("#ISLIST").val();
		var flowVal = $("#ISFLOW").val();
		var selectVal = $("#ISSELECT").val();
		var fields = dname + '#' + dtype + '#' + dbz + '#' + ddefault
				+ '#' + flength + '#' + decimal + '#' + show_order
				+ '#' + is_dic + '#' + dic_type + '#' + dic_type_id
				+ "#" + is_quote + '#' + quote_type + '#'
				+ quote_type_id + '#' + newVal + '#' + mustVal + '#'
				+ qyVal + '#' + listVal + '#' + flowVal + '#'
				+ selectVal;
		if (msgIndex == '') {
			arrayField(fields);
		} else {
			editArrayField(fields, msgIndex);
		}
		closelayA();
	}

	//保存属性后往数组添加元素
	function arrayField(value) {
		arField[index] = value;
		appendC(value);
	}

	//修改属性
	function editArrayField(value, msgIndex) {
		arField[msgIndex] = value;
		index = 0;
		$("#fields").html('');
		for (var i = 0; i < arField.length; i++) {
			appendC(arField[i]);
		}
	}

	//判断属性名是否重复
	function isSame(value) {
		for (var i = 0; i < arField.length; i++) {
			var array0 = arField[i].split('#')[0];
			if (array0 == value) {
				return false;
			}
		}
		return true;
	}

	//赋值类型
	function setType(value) {
		$("#dtype").val(value);
		$("#decimal").val('');
		$("#decimal").attr("disabled", true);
		if (value == 'Integer') {
			if (Number($("#flength").val()) - 0 > 11) {
				$("#flength").val(11);
			}
		} else if (value == 'Date') {
			$("#flength").val(32);
		} else if (value == 'Double') {
			if (Number($("#flength").val()) - 0 > 11) {
				$("#flength").val(11);
			}
			$("#decimal").val(2);
			$("#decimal").attr("disabled", false);
		} else {
			$("#flength").val(255);
		}
	}

	function quote_change() {
		if ($('#is_quote').val() == '是') {
			$('#is_dic').val('否');
			$('#dic_type').val('');
		}
	}

	function dic_change() {
		if ($('#is_dic').val() == '是') {
			$('#is_quote').val('否');
			$('#quote_type').val('');
		}
	}

	//生成
	function save() {

		if (!confirm("确定要生成吗?")) {
			return false;
		}
		var strArField = '';
		for (var i = 0; i < arField.length; i++) {
			strArField = strArField + arField[i] + "Q351412933";
		}
		$("#TREE_TYPE").removeAttr("disabled");
		$.ajax({
			//几个参数需要注意一下
			type : "POST",//方法类型
			dataType : "text",//预期服务器返回的数据类型
			url : path + "/basecolumns/save.do",//url
			data : {
				TEMPLATE_ID : $("#TEMPLATE_ID").val(),
				FIELDLIST : strArField
			},
			success : function(result) {
				if (result == "success") {
					$("#userWin").hide();
					$("#zhongxin").hide();
					layer.alert("生成成功");
					//$("#fields").html('');
					//reductionFields();
					//$("#zhongxin2").show();
					parent.window.location.href =  path +"/basecolumns/list.do";
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				// 状态码
				console.log(XMLHttpRequest.status);
				// 状态
				console.log(XMLHttpRequest.readyState);
				// 错误信息
				console.log(textStatus);

				alert("异常");
			}

		});
	}

	//删除数组添加元素并重组列表
	function removeField(value) {
		layer.confirm("确认要删除吗，删除后不能恢复", { title: "删除确认" }, function (index) {
			index = 0;
			$("#fields").html('');
			arField.splice(value, 1);
			for (var i = 0; i < arField.length; i++) {
				appendC(arField[i]);
			}
			layer.close(index);
			layer.closeAll();
		}); 	
	}

	function closelayA() {
		layer.close(newmylayA);
		$("#dialog-add").css("display", "none");
	}
</script>
</body>
</html>
