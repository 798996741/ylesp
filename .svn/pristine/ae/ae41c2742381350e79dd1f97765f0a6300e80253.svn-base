
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8" />
    <meta name="App-Config" content="fullscreen=yes,useHistoryState=yes,transition=yes" />
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="yes" name="apple-touch-fullscreen" />
    <meta content="telephone=no,email=no" name="format-detection" />
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no" />
    <script src="http://g.tbcdn.cn/mtb/lib-flexible/0.3.4/??flexible_css.js,flexible.js"></script>
    <script src="<%=path%>/static/weixin/js/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/weixin/css/mui.min.css" />
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/weixin/css/mui.picker.min.css" />
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/weixin/css/mui.poppicker.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="<%=path%>/static/weixin/css/common.css" />
</head>

<script type="text/javascript">
	function loadWork(){
		var userid='${userid}';
		var data="{\"api\":\"com.yulun.WorkorderWeb\",\"data\":{\"tokenid\":\"3d0fd800244f48fd9d648724c099528\",\"code\":\"\",\"starttime\":\"\",\"endtime\":\"\",\"tsman\":\"\",\"tssource\":\"\",\"tsdept\":\"\",\"tstype\":\"\",\"pageIndex\":\"1\",\"pageSize\":\"10\",\"doaction\":\"0\",\"username\":\""+userid+"\"},\"cmd\":\"WorkorderDb\"}";
   		//alert(data);
		$.ajax({
           	type: 'post',
          	// url: "http://127.0.0.1:8080/ylxxxtnew/sys/sypzpreAction!getPz.xhtml?endpoint_id=1023&module=home&r="+Math.random(),
			url:"<%=path%>/api",
            dataType: 'json',
            data:{"data":data}, //参数值
            cache: false,
            success: function (obj) {
               
               //	$("#yc").val(obj);
               //	obj='{"home":{"迎宾讲解欢迎语":[{"cont":"欢迎各位领导光临我公司参观指导","descript":"开始进行迎宾讲解欢迎语","lx":"2","remark":"开始进行迎宾讲解欢迎语"}],"首页视频":[{"cont":"/6/11/1515061089456.mp4","descript":"首页视频","lx":"3","remark":"首页视频"}],"首页顶部条":[{"cont":"/5/13/1514556553439.png","descript":"首页顶部条","lx":"1","remark":"首页顶部条"}]}}';
               //	var zobj=JSON.parse(obj);
               //	alert(obj.data);
               	var str="";
                var data =eval(obj.data);
                //alert(obj.taskname);
                //alert(data.length);
                //alert(data);
                for(var o in data){ 
                	var tslevelname="";
                	if(typeof(data[o].tslevelname)=="undefined"){
                		tslevelname="";
                	}else{
                		tslevelname=data[o].tslevelname;
                	}
                	str=str+"<tr>";
                	str=str+"<td><div class=\"cell\">"+data[o].tsdate+"</div></td>";
                	str=str+"<td><div class=\"cell\">"+data[o].tssourcename+"</div></td>";
                	str=str+"<td><div class=\"cell\">"+tslevelname+"</div></td>";
                	str=str+"<td><div class=\"cell\">"+data[o].clsx+"</div></td>";
                	
                	if(data[o].islx=="dpf"){
                		str=str+"<td><div class=\"cell\"><span class=\"btn-opt bg-green\" onclick=\"pf("+data[o].id+","+data[o].uid+")\">派发</span></div></td>";
                	}else if(data[o].islx=="cl"){
                		str=str+"<td><div class=\"cell\"><span class=\"btn-opt bg-blue\" onclick=\"cl("+data[o].proc_id+","+data[o].ID_+")\">处理</span></div></td>";	
                	}else if(data[o].islx=="sh"){
                		str=str+"<td><div class=\"cell\"><span class=\"btn-opt bg-orange\">审核</span></div></td>";
                	}
                	str=str+"</tr>";	
                }
                $("#dbtable tbody").html("");
                if(str==""){
                	str="<tr><td colspan=\"5\">暂无数据</td></tr>";
                	$("#dbtable").find('tbody').append(str);
                }else{
                	$("#dbtable").find('tbody').append(str);
                }
                
            },error: function (jqXHR, textStatus, errorThrown) {
               	alert(jqXHR.responseText);
                return false;
           	}
		});
   	}
	
	function cl(proc_id,ID_){
		var userid='${userid}';
		location.href="<%=path%>/appWeixin/complandetail_sh?ID_="+ID_+"&proc_id="+proc_id+"&userid="+userid;
	}
	function pf(id,uid){
		var userid='${userid}';
		location.href="<%=path%>/appWeixin/distribute?id="+id+"&uid="+uid+"&userid="+userid;
	}
	</script>

<body onload="loadWork()">
    <div>
        <div class="ada-table ada-table-bluebg">
            <table id="dbtable" cellspacing="0" cellpadding="0" border="0" class="ada-table__header">
                <colgroup>
                    <col width="300" />
                    <col width="180" />
                    <col width="180" />
                    <col width="180" />
                    <col width="180" />
                    <col name="gutter" width="0" />
                </colgroup>
                <thead class="has-gutter">
                    <tr>
                        <th>
                            <div class="cell">日期</div>
                        </th>
                        <th>
                            <div class="cell">投诉来源</div>
                        </th>
                        <th>
                            <div class="cell">投诉等级</div>
                        </th>
                        <th>
                            <div class="cell">处理时限</div>
                        </th>
                        <th>
                            <div class="cell">操作</div>
                        </th>
                        <th class="gutter" style="width: 0px; display: none;"></th>
                    </tr>
                </thead>
                <tbody>
                    
                </tbody>
            </table>
        </div>
    </div>
</body>

</html>