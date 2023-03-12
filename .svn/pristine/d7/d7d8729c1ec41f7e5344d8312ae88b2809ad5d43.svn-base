<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<!-- 下拉框 -->
<jsp:include page="../include/incJs_mx.jsp"></jsp:include>

</head>
<body class="no-skin">
	<div class="row">
			<div class="col-xs-12">
				<form action="user/UpdatePwd.do" name="userForm" id="userForm_add" method="post">
				
				<input type="hidden" name="USER_ID" id="user_id" value="${pd.USER_ID }" />
				<div id="zhongxin" style="padding: 5px;border-radius: 5px">
						
					<table class="table" >	
						<tr>
							<td style="width:100px;min-width:100px;text-align:right">用 户 名 </td>
							<td><input
								type="text" name="USERNAME" id="loginname"
								value="${pd.USERNAME }" maxlength="32" placeholder="这里输入用户名"
								title="用户名" class="detail-seat-input auto-width-medium"
								style="width: 170px" /></td>
						</tr>	
						<tr>
							<td style="width:100px;min-width:100px;text-align:right">密            码</td>
							<td><input
								type="password" name="PASSWORD" id="password" maxlength="32"
								placeholder="输入密码" title="密码"
								class="detail-seat-input auto-width-medium"
								style="width: 170px" /></td>
						</tr>	
						<tr>
							<td style="width:100px;min-width:100px;text-align:right">确认密码</td>
							<td><input
								type="password" name="chkpwd" id="chkpwd" maxlength="32"
								placeholder="确认密码" title="确认密码"
								style="width: 170px" /></td>
						</tr>	
					</table>	
				</div>
				<div class="new-bc">
					<a onclick="save();">保存</a> <a class="new-qxbut" onclick="cancel();"
						data-btn-type="cancel" data-dismiss="modal">取消</a>
				</div>
				</form>
			</div>
		</div>
	
	<%@ include file="../../system/include/incJs_foot.jsp" %>
	<script type="text/javascript">

    //保存
    function save() {
      
        if ($("#password").val() == "") {
            layer.alert("请输入输入密码");
            $("#password").focus();
            return false;
        }
        if ($("#password").val() != $("#chkpwd").val()) {
        	layer.alert("两次密码不相同");
            
            $("#chkpwd").focus();
            return false;
        }
        
        if ($("#user_id").val() == "") {
            
        } else {
        	$.ajax({
                //几个参数需要注意一下
                type: "POST",//方法类型
                url: "user/UpdatePwd.do",//url
                data: $('#userForm_add').serialize(),
                success: function (result) {
                	layer.alert("修改成功");
                	parent.closeLayer();
                },
                error: function () {
                	layer.alert("修改成功");
                	parent.closeLayer();
                }
            });
        	
        }
    }

    function cancel(){
    	parent.closeLayer();
    }
   
    
</script>
</body>
</html>
