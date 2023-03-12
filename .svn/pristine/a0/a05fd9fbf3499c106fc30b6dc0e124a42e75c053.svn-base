<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.fh.util.Jurisdiction"%>
<%
	Jurisdiction jurisdiction=new Jurisdiction();
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<%@ include file="../../system/include/incJs_mx.jsp"%>
<style>
.pagination input {
	height: 33px;
}

.pagination select {
	height: 32px;
}
</style>
</head>
<body class="no-skin">


	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper" style="width:100%;margin-left:0px;">
		<section class="container-fluid">
			<div class="seat-middle-top-x"></div>
			<div class="seat-middle-top">
				<div class="seat-middle-top-left">
					<div class="seat-middle-top-left-bt">坐席管理员</div>
					<div class="seat-middle-top-left-tp" style="width:300px">
						<%if(jurisdiction.hasQx("990101")){ %>
						<a href="javascript:void (0)" style="float:left"
							onclick="addRole();">新增</a>
						<%} %>
						<c:choose>
							<c:when test="${pd.ROLE_ID == '99'}">
							</c:when>
							<c:otherwise>

								<span style="float:left" onclick="editRights('${pd.ROLE_ID }');">
									<i class="icon-pencil"></i> &nbsp;&nbsp;&nbsp;<c:if
										test="${pd.ROLE_ID == '1'}">Admin权限</c:if> <c:if
										test="${pd.ROLE_ID != '1'}">组菜单权限</c:if>
								</span>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
				<div class="seat-middle-top-right">
					<input placeholder="搜  索" name="keywords" id="keywords"
						value="${pd.keywords }"> <a href="javascript:void(0)">
						<img src="static/login/images/icon-search.png"
						onclick="tosearch()">
					</a>
				</div>
			</div>
			<div class="seat-middle">
				<!--  <div class="seat-middle-nr"> -->

				<div class="xtyh-middle-r zxzgl-middle-r">
					<!-- <div class="box-body" > -->

					<table id="example2" class="table table-bordered table-hover">
						<thead>
							<tr>
								<!-- <th class="center" style="width:35px;">
                      <label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
                      </th> -->
								<th class="center" style="width: 50px;">序号</th>
								<th class='center' style="width: 150px;">角色</th>

								<th style="width:155px;" class="center">操作</th>
							</tr>
						</thead>
						<tbody>

							<c:choose>
								<c:when test="${not empty roleList_z}">
									<c:forEach items="${roleList_z}" var="var" varStatus="vs">

										<tr>
											<td class='center' style="width:255px;" style="width:30px;">${vs.index+1}</td>
											<td id="ROLE_NAMETd${var.ROLE_ID }">${var.ROLE_NAME }</td>

											<td style="width:255px;">
												<div class="flex-position">
													<div class="flex-row">

														<%if(jurisdiction.hasQx("990204")){ %>
															<div class="button-edit" title="菜单权限"
																onclick="editRights('${var.ROLE_ID }');" title="菜单权限">
																<font class="button-content">权限</font>
															</div>
														<%}%>
														<%if (jurisdiction.hasQx("990202")) {%>
															<div class="button-edit" title="编辑"
																onclick="editRole('${var.ROLE_ID }');" title="编辑">
																<font class="button-content">编辑</font>
															</div>
														<%}%>


														<c:choose>
															<c:when test="${var.ROLE_ID == '2' or var.ROLE_ID == '1'}"></c:when>
															<c:otherwise>
																<%if (jurisdiction.hasQx("990203")) {%>
																	<div class="button-delete" style="margin-left:10px;"
																		title="删除"
																		onclick="delRole('${var.ROLE_ID }','c','${var.ROLE_NAME }');"
																		title="删除">
																		<font class="button-content">删除</font>
																	</div>
																<%} %>
															</c:otherwise>
														</c:choose>
													</div>
												</div>
											</td>
										</tr>
									</c:forEach>

								</c:when>
								<c:otherwise>
									<tr>
										<td colspan="100" class="center">没有相关数据</td>
									</tr>
								</c:otherwise>
							</c:choose>

						</tbody>

					</table>


				</div>

			</div>
		</section>
		<!-- Content Header (Page header) -->
		<%-- <section class="content-header">
      <h1>
        	系统管理
        <small>角色管理</small>
      </h1>
      <ol class="breadcrumb">
        <li>
			<a href="javascript:;" onclick="home()">
				<i class="fa fa-dashboard"></i> 主页
			</a>
		</li>
		<li class="active">角色管理</li>
      </ol>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="row">
        <div class="col-xs-12">
          <div class="box">
            <div class="box-header">
              	<table style="margin-top: 8px;">
					<tr height="35">
						<c:if test="${QX.add == 1 }">
						<td style="width:69px;"><a href="javascript:addRole(0);" class="btn btn-sm btn-success">新增组</a></td>
						</c:if>
							<c:choose>
							<c:when test="${not empty roleList}">
							<c:forEach items="${roleList}" var="role" varStatus="vs">
								<td style="width:100px;" class="center" <c:choose><c:when test="${pd.ROLE_ID == role.ROLE_ID}">bgcolor="#FFC926" onMouseOut="javascript:this.bgColor='#FFC926';"</c:when><c:otherwise>bgcolor="#E5E5E5" onMouseOut="javascript:this.bgColor='#E5E5E5';"</c:otherwise></c:choose>  onMouseMove="javascript:this.bgColor='#FFC926';" >
									<a href="role.do?ROLE_ID=${role.ROLE_ID }" style="text-decoration:none; display:block;"><i class="menu-icon fa fa-users"></i><font color="#666666">${role.ROLE_NAME }</font></a>
								</td>
								<td style="width:5px;"></td>
							</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
								<td colspan="100">没有相关数据</td>
								</tr>
							</c:otherwise>
							</c:choose>
						<td></td>
					</tr>
				</table>
				<table>
					<tr height="7px;"><td colspan="100"></td></tr>
					<tr>
					<td><font color="#808080">本组：</font></td>
					<td>
					<c:if test="${QX.edit == 1 }">
					<a class="btn btn-mini btn-info" onclick="editRole('${pd.ROLE_ID }');">修改组名称<i class="icon-arrow-right  icon-on-right"></i></a>
					</c:if>
						<c:choose>
							<c:when test="${pd.ROLE_ID == '99'}">
							</c:when>
							<c:otherwise>
							<c:if test="${QX.edit == 1 }">
							<a class="btn btn-mini btn-purple" onclick="editRights('${pd.ROLE_ID }');">
								<i class="icon-pencil"></i>
								<c:if test="${pd.ROLE_ID == '1'}">Admin 菜单权限</c:if>
								<c:if test="${pd.ROLE_ID != '1'}">组菜单权限</c:if>
							</a>
							</c:if>
							</c:otherwise>
						</c:choose>
						<c:choose> 
							<c:when test="${pd.ROLE_ID == '1' or pd.ROLE_ID == '2'}">
							</c:when>
							<c:otherwise>
							 <c:if test="${QX.del == 1 }">
							 <a class='btn btn-mini btn-danger' title="删除" onclick="delRole('${pd.ROLE_ID }','z','${pd.ROLE_NAME }');"><i class='ace-icon fa fa-trash-o bigger-130'></i></a>
							 </c:if>
							</c:otherwise>
						</c:choose>
					</td>
					</tr>
				</table>
            </div>
            <!-- /.box-header -->
            <div class="box-body">
              <table id="example2" class="table table-bordered table-hover">
                <thead>
                <tr>
                  	<!-- <th class="center" style="width:35px;">
					<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
					</th> -->
					<th class="center" style="width: 50px;">序号</th>
					<th class='center'>角色</th>
					<c:if test="${QX.edit == 1 }">
					<th class="center">增</th>
					<th class="center">删</th>
					<th class="center">改</th>
					<th class="center">查</th>
					</c:if>
					<th style="width:155px;"  class="center">操作</th>
                </tr>
                </thead>
                <tbody>
                
               <c:choose>
									<c:when test="${not empty roleList_z}">
										<c:if test="${QX.cha == 1 }">
										<c:forEach items="${roleList_z}" var="var" varStatus="vs">
										
										<tr>
										<td class='center' style="width:30px;">${vs.index+1}</td>
										<td id="ROLE_NAMETd${var.ROLE_ID }">${var.ROLE_NAME }</td>
										<c:if test="${QX.edit == 1 }">
										<td style="width:30px;"><a onclick="roleButton('${var.ROLE_ID }','add_qx');" class="btn btn-warning btn-mini" title="分配新增权限"><i class="ace-icon fa fa-wrench bigger-110 icon-only"></i></a></td>
										<td style="width:30px;"><a onclick="roleButton('${var.ROLE_ID }','del_qx');" class="btn btn-warning btn-mini" title="分配删除权限"><i class="ace-icon fa fa-wrench bigger-110 icon-only"></i></a></td>
										<td style="width:30px;"><a onclick="roleButton('${var.ROLE_ID }','edit_qx');" class="btn btn-warning btn-mini" title="分配修改权限"><i class="ace-icon fa fa-wrench bigger-110 icon-only"></i></a></td>
										<td style="width:30px;"><a onclick="roleButton('${var.ROLE_ID }','cha_qx');" class="btn btn-warning btn-mini" title="分配查看权限"><i class="ace-icon fa fa-wrench bigger-110 icon-only"></i></a></td>
										</c:if>
										<td style="width:155px;">
										<c:if test="${QX.edit != 1 && QX.del != 1 }">
										<div style="width:100%;" class="center">
										<span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
										</div>
										</c:if>
										<c:if test="${QX.edit == 1 }">
										<a class="btn btn-mini btn-danger" onclick="editRights('${var.ROLE_ID }');"><i class="icon-pencil"></i>菜单权限</a>
										<a class='btn btn-mini btn-info' title="编辑" onclick="editRole('${var.ROLE_ID }');"><i class='ace-icon fa fa-pencil-square-o bigger-130'></i></a>
										</c:if>
										<c:choose> 
											<c:when test="${var.ROLE_ID == '2' or var.ROLE_ID == '1'}">
											</c:when>
											<c:otherwise>
											 <c:if test="${QX.del == 1 }">
											 <a class='btn btn-mini btn-danger' title="删除" onclick="delRole('${var.ROLE_ID }','c','${var.ROLE_NAME }');"><i class='ace-icon fa fa-trash-o bigger-130'></i></a>
											 </c:if>
											</c:otherwise>
										</c:choose>
										</td>
										</tr>
										</c:forEach>
										</c:if>
										<c:if test="${QX.cha == 0 }">
											<tr>
												<td colspan="100" class="center">您无权查看</td>
											</tr>
										</c:if>
									</c:when>
									<c:otherwise>
										<tr>
										<td colspan="100" class="center" >没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
                
                </tbody>
               
              </table>
              <div>
				<c:if test="${QX.add == 1 }">
					&nbsp;&nbsp;<a class="btn btn-sm btn-success" onclick="addRole('${pd.ROLE_ID }');">新增角色</a>
				</c:if>
				</div>
           
            </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->

        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
    </section> --%>
		<!-- /.content -->
	</div>
	<!-- /.content-wrapper -->

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
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		//$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			//top.jzts();
			
			var keywords=$("#keywords").val();
			location.href="<%=path%>/role/listRoles.do?keywords="+encodeURI(encodeURI(keywords));
			//$("#Form_s").submit();
		}
		function addRole(pid){
			
			var winId = "userWin";
			  modals.openWin({
		          winId: winId,
		          title: '新增',
		          width: '450px',
		          height: '250',
		          url: '<%=basePath%>role/toAdd.do?parent_id='+pid
		          /*, hideFunc:function(){
		              modals.info("hide me");
		          },
		          showFunc:function(){
		              modals.info("show me");
		          } */
		      });
		
		}
		
		//修改
		function editRole(ROLE_ID){
			var winId = "userWin";
			  modals.openWin({
		          winId: winId,
		          title: '新增',
		          width: '450px',
		          height: '250',
		          url: '<%=basePath%>role/toEdit.do?ROLE_ID='+ROLE_ID
		          /*, hideFunc:function(){
		              modals.info("hide me");
		          },
		          showFunc:function(){
		              modals.info("show me");
		          } */
		      });
			
		}
		
		//删除
		function delRole(ROLE_ID,msg,ROLE_NAME){
			bootbox.confirm("确定要删除["+ROLE_NAME+"]吗?", function(result) {
				if(result) {
					var url = "<%=basePath%>role/delete.do?ROLE_ID="+ROLE_ID+"&guid="+new Date().getTime();
					
					$.get(url,function(data){
						if("success" == data.result){
							if(msg == 'c'){
								//top.jzts();
								document.location.reload();
							}else{
								//top.jzts();
								window.location.href="role.do";
							}
							
						}else if("false" == data.result){
							bootbox.dialog({
								message: "<span class='bigger-110'>删除失败，请先删除下级角色!</span>",
								buttons: 			
								{
									"button" :
									{
										"label" : "确定",
										"className" : "btn-sm btn-success"
									}
								}
							});
						}else if("false2" == data.result){
							bootbox.dialog({
								message: "<span class='bigger-110'>删除失败，此角色已被使用!</span>",
								buttons: 			
								{
									"button" :
									{
										"label" : "确定",
										"className" : "btn-sm btn-success"
									}
								}
							});
						}
					});
				}
			});
		}
		
		//菜单权限
		function editRights(ROLE_ID){
			var winId = "userWin";
			  modals.openWin({
		          winId: winId,
		          title: '菜单权限',
		          width: '320px',
		          height: '450px',
		          url: '<%=basePath%>role/menuqx.do?ROLE_ID='+ROLE_ID
		      });
		}
		
		//按钮权限(增删改查)
		function roleButton(ROLE_ID,msg){
			top.jzts();
			if(msg == 'add_qx'){
				var Title = "授权新增权限(此角色下打勾的菜单拥有新增权限)";
			}else if(msg == 'del_qx'){
				Title = "授权删除权限(此角色下打勾的菜单拥有删除权限)";
			}else if(msg == 'edit_qx'){
				Title = "授权修改权限(此角色下打勾的菜单拥有修改权限)";
			}else if(msg == 'cha_qx'){
				Title = "授权查看权限(此角色下打勾的菜单拥有查看权限)";
			}
			var winId = "userWin";
			  modals.openWin({
		          winId: winId,
		          title: Title,
		          width: '330px',
		          height: '450px',
		          url: '<%=basePath%>role/b4Button.do?ROLE_ID='+ROLE_ID+'&msg='+msg
		          /*, hideFunc:function(){
		              modals.info("hide me");
		          },
		          showFunc:function(){
		              modals.info("show me");
		          } */
		      });
			
		}
	</script>

	<script>
    $(function () {

        $('#example2').DataTable({
            'paging': true,
            'lengthChange': false,
            'searching': true,
            'ordering': true,
            'info': true,
            'autoWidth': true
        });
        $(".row thead").find("th:first").addClass("cy_th");
        $(".row tbody").find("tr").find("td:first").addClass("cy_td");
        $(".row thead").find("th:first").after("<th id='cy_thk'></th>");
        $(".row tbody").find("tr").find("td:first").after("<td id='cy_thk'></td>");
        $('.seat-middle').find(".row:first").hide();

        $(".xtyh-middle-r").find(".row:last").addClass("row-zg");
        $(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");
    });

</script>

</body>
</html>