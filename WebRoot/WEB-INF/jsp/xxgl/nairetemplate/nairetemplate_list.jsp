<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.fh.util.Jurisdiction"%>
<%
	Jurisdiction jurisdiction=new Jurisdiction();
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<html lang="en">
<head>
    <base href="<%=basePath%>">
    <!-- 下拉框 -->
    <!-- jsp文件头和头部 -->
    <%--
    <%@ include file="../index/top.jsp"%> --%>
    <%@ include file="../../system/include/incJs_mx.jsp" %>
    <style>
        .seat-middle {
            display: block !important;
        }

        #nairetable_filter {
            display: none;
        }


        .content-wrapper .box-body {
            margin: 15px;
            background: #fff;
        }

        .content-wrapper .box-footer {
            margin: 15px;
        }

        .box-footer {
            display: none;
        }

        .table-responsive {
            overflow-x: hidden;
        }

        @media screen and (max-width: 1700px) {
            .seat-middle-top {
                margin: 15px 0 15px 0 !important;
            }
        }
    </style>
</head>
<script>
    function show(ID) {
        //alert(ID);

        document.getElementById("hiddenfullText" + ID + "").value = document.getElementById("fullText" + ID + "").innerHTML;
        var text = document.getElementById("hiddenfullText" + ID + "").value;
        document.getElementById("fullText" + ID + "").innerHTML = "";
        document.getElementById("subText" + ID + "").style.float = "left";
        document.getElementById("btn" + ID + "").style.float = "left";
        if (text.length > 50) {
            document.getElementById("subText" + ID + "").innerHTML = text.substring(0, 50);
            document.getElementById("btn" + ID + "").innerHTML = "...显示全部";
        } else {
            document.getElementById("subText" + ID + "").innerHTML = text;
            document.getElementById("btn" + ID + "").innerHTML = "";
        }
    }

    function change(ID) {
        var text = document.getElementById("hiddenfullText" + ID + "").value;
        var t = document.getElementById("btn" + ID + "");
        var tt = document.getElementById("subText" + ID + "");
        if (t.innerHTML == "...显示全部") {
            tt.innerHTML = text;
            t.innerHTML = "收起"
        } else {
            tt.innerHTML = text.substring(0, 50);
            t.innerHTML = "...显示全部"
        }
    }

</script>
<body class="no-skin">


<div class="content-wrapper" style="width:100%;margin-left:0px;">
    <section class="container-fluid">
        <div class="seat-middle-top-x"></div>
        <div class="seat-middle-top">
            <div class="seat-middle-top-left">
                <div class="seat-middle-top-left-bt">问卷模板设置</div>
                <div class="seat-middle-top-left-tp">
                    <%if(jurisdiction.hasQx("30301")){ %>
                        <a href="javascript:void (0)" onclick="add_t();">新增</a>
                    <%} %>	
                    
                </div>
            </div>
            <div class="seat-middle-top-right">
                <input placeholder="搜  索" name="keywords" id="keywords" value="${pd.keywords }">
                <a href="javascript:void(0)">
                    <img src="static/login/images/icon-search.png" onclick="tosearch()"></a>
            </div>
        </div>
        <div class="seat-middle">
            <!--  <div class="seat-middle-nr"> -->

            <div class="xtyh-middle-r zxzgl-middle-r">
                <!-- <div class="box-body" > -->

                <table id="example2"
                       class="table table-striped table-bordered table-hover"
                       style="margin-top:5px;">
                    <thead>
                    <tr>

                        <th class="center cy_th" style="width:30px;">序号</th>
                        <th id='cy_thk'></th>
                        <th class="center">模板名称</th>
                        <th class="center" style="width:200px">开场白</th>
                        <th class="center" style="width:200px">结束语</th>
                        <th class="center" style="min-width: 80px;">是否启用</th>
                        <th class="center" style="min-width: 60px;">创建人</th>
                        <th class="center" style="min-width: 80px;">创建时间</th>
                        <th class="center" style="min-width: 100px;">操作</th>
                    </tr>
                    </thead>

                    <tbody>
                    <!-- 开始循环 -->
                    <c:choose>
                        <c:when test="${not empty varList}">
                            <c:if test="${QX.cha == 1 }">
                                <c:forEach items="${varList}" var="var" varStatus="vs">
                                    <tr  style="cursor:pointer">

                                        <td class='center cy_td' style="width: 30px;">${vs.index+1}</td>
                                        <td id='cy_thk'></td>
                                        <td class='center'>${var.NAME}</td>
                                        <td class='center' style="max-width:250px"><input
                                                type="hidden" id="hiddenfullText${var.ID}">

                                            <div id="fullText${var.ID}">${var.OPENINGREMARKS}</div>
                                            <div id="subText${var.ID}"></div>
                                            <a id="btn${var.ID}"
                                               onclick="change('${var.ID}')"></a>
                                            <script>
                                                show('${var.ID}');
                                            </script>
                                        </td>
                                        <td class='center' style="max-width:250px">${var.CONCLUDINGREMARKS}</td>
                                        <td class='center'><c:if test="${var.ISUSE==0}">否</c:if>
                                            <c:if test="${var.ISUSE==1}">是</c:if>
                                        	<c:if test="${var.ISUSE==-1}"><span style="color:red">已删除</span></c:if>    
                                        </td>
                                        <td class='center' style="width: 40px;">${var.CREATEMAN}</td>
                                        <td class='center'>${var.CREATEDATE}</td>
                                        <td class="center">
                                            <div class="flex-position">
                                                <div class="flex-row">
                                                    <%if(jurisdiction.hasQx("30302")){ %>
														<div class="button-edit" title="编辑"
															 onclick="edit_nairete('${var.ID}');" title="编辑">
															<font class="button-content">编辑</font>
														</div>
														<%if(jurisdiction.hasQx("30304")){ %>
															<c:if test="${var.ISUSE!=-1}">
		                                                        <div class="button-setting" style="margin-left:10px;"
		                                                             title="模板设置"
		                                                             onclick="setmb('${var.ID}');" title="模板设置">
		                                                            <font class="button-content">配置</font>
		                                                        </div>
	                                                        </c:if>
                                                        <%} %>
                                                    <% }%>
                                                    <%if(jurisdiction.hasQx("30303")){ %>
	                                                    <c:if test="${var.ISUSE!=-1 }">
															<div class="button-delete" style="margin-left:10px;"
																 title="删除"
																 onclick="del_t('${var.ID}');" title="删除">
																<font class="button-content">删除</font>
															</div>                                                     
	                                                    </c:if>
                                                    <%} %>
                                                </div>
                                            </div>
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
                            <tr class="main_info">
                                <td colspan="100" class="center">没有相关数据</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>


            </div>
            <div class="modal " id="tmmodal">
                <div class="main-content newwksz">
                    <div class="main-content-inner">
                        <div class="page-content">
                            <div class="modal-header">
                                <h4 class="modal-title" id="myModalLabel" style="float: left;"> 题目信息</h4>
                                <div class="new-tb" style="float: right;" data-dismiss="modal" title="关闭"></div>
                            </div>

                            <div class="smt-wk">
                                <div class="seat-middle-top">
                                    <div class="seat-middle-top-left">
                                        <div class="seat-middle-top-left-tp smtl">
                                            <div class="" id="addtm">
                                            	<%if(jurisdiction.hasQx("30305")){ %>
                                                	<a onclick="add_naire();">新增题目</a>
                                                <%} %>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="seat-middle-top-right">
                                        <input placeholder="搜  索" name="keywords" id="keywords" value="${pd.keywords }">
                                        <a href="javascript:void(0)">
                                            <img src="static/login/images/icon-search.png" onclick="tosearch()"></a>
                                    </div>
                                </div>


                                <div class="xtyh-middle-r">
                                    <input type="hidden" id="NAIRE_TEMPLATE_ID"
                                           name="NAIRE_TEMPLATE_ID">
                                    <div class="table-responsive" style="min-height:300px">
                                        <div id="nairetdiv" class="table table-bordered table-hover">

                                        </div>
                                    </div>
                                    <!-- /.table-responsive -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- /.box-body -->
                <div class="box-footer clearfix"></div>
                <!-- /.box-footer -->
            </div>
            <!-- /.box-header -->

        </div>
    </section>

</div>

<!-- /.main-container -->

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
    function tosearch() {
        //top.jzts();
        var keywords = $("#keywords").val();
        location.href = "<%=path%>/nairetemplate/list.do?keywords=" + encodeURI(encodeURI(keywords));
        //$("#Form_s").submit();
    }

    var text;

    $(function () {
        $('#example2').DataTable({
            'paging': true,
            'lengthChange': false,
            'searching': true,
            'ordering': false,
            'info': true,
            'autoWidth': true
        });


    });

    //新增
    function add_t() {
        var winId = "nairetemplateWin_add";
        modals.openWin({
            winId: winId,
            title: '问卷新增',
            width: '900px',
            height: '400px',
            url: '<%=basePath%>nairetemplate/goAdd.do'

        });
    }

    function setnext(ID, CODE) {
        var NAIRE_TEMPLATE_ID = $("#NAIRE_TEMPLATE_ID").val();

        var winId = "userWin_next";
        modals.openWin({
            winId: winId,
            title: '设置下一题',
            width: '800px',
            height: '400px',
            url: '<%=basePath%>naire/goNext.do?CODE=' + CODE + '&NAIRE_TEMPLATE_ID=' + NAIRE_TEMPLATE_ID + '&ID=' + ID
        });
    }

    //删除
    function del_t(Id, Uid) {
        bootbox.confirm("确定要删除吗?", function (result) {
            if (result) {
                top.jzts();
                var url = "<%=basePath%>nairetemplate/delete.do?ID=" + Id + "&UID=" + Uid + "&tm=" + new Date().getTime();
                $.get(url, function (data) {
                    if (data == "success") {
                        location.href = "<%=path%>/nairetemplate/list.do";
                    } else if (data == "error") {
                        modals.info("模板在使用中，不能删除");
                    }

                });
            }
        });
    }

    //修改
    function edit_nairete(Id, Uid) {

        var winId = "nairetemplateWin_edit";
        modals.openWin({
            winId: winId,
            title: '问卷修改',
            width: '900px',
            height: '400px',
            url: '<%=basePath%>nairetemplate/goEdit.do?UID="+Uid+"&ID=' + Id
            /*, hideFunc:function(){
                modals.info("hide me");
            },
            showFunc:function(){
                modals.info("show me");
            } */
        });


    }


    //修改
    function setmb(Id) {
        $("#NAIRE_TEMPLATE_ID").val(Id);

        var url = "<%=basePath%>naire/getNaireList.do?NAIRE_TEMPLATE_ID=" + Id + "&tm=" + new Date().getTime();
        $.get(url, function (data) {
            //alert(data);
            var obj = JSON.parse(data);
            //alert(obj.naireString);
            $("#nairetdiv").html("");
            $("#nairetdiv").append(obj.naireString);
            $("#tmmodal").modal("show");
        });


    }


    function add_naire() {
        var NAIRE_TEMPLATE_ID = $("#NAIRE_TEMPLATE_ID").val();
        if (NAIRE_TEMPLATE_ID == "") {
            modals.info("请先选择问卷模板");
            return false;
        }
        var winId = "naireWin_childs";
        modals.openWin({
            winId: winId,
            title: '问卷题目新增',
            width: '900px',
            height: '400px',
            url: '<%=basePath%>naire/goAdd.do?NAIRE_TEMPLATE_ID=' + NAIRE_TEMPLATE_ID

        });
        
       <%--  newmylay = layer.open({
			  type: 2,
			  title: "卷题目新增",
			  shade: 0.5,
			  skin: 'demo-class',
			  area:  ['100%', '100%'],
			  content: '<%=basePath%>naire/goAdd.do?NAIRE_TEMPLATE_ID=' + NAIRE_TEMPLATE_ID
	
	    }); --%>

    }

    //删除
    function del(Id, Uid) {
        bootbox.confirm("确定要删除吗?", function (result) {
            if (result) {
                top.jzts();
                var url = "<%=basePath%>naire/delete.do?ID=" + Id + "&NAIRE_TEMPLATE_ID=${pd.NAIRE_TEMPLATE_ID}&tm=" + new Date().getTime();
                $.get(url, function (data) {
                    var NAIRE_TEMPLATE_ID = $("#NAIRE_TEMPLATE_ID").val();
                    setmb(NAIRE_TEMPLATE_ID);
                });
            }
        });
    }

    //修改
    function edit(Id, Uid) {
        //alert('${pd.action}');
        
        var winId = "naireWin_childs";
        modals.openWin({
            winId: winId,
            title: '问卷题目修改',
            width: '700px',
            height: '400px',
            url: '<%=basePath%>naire/goEdit.do?action=${pd.action}&NAIRE_TEMPLATE_ID=${pd.NAIRE_TEMPLATE_ID}&ID=' + Id
            /*, hideFunc:function(){
                modals.info("hide me");
            },
            showFunc:function(){
                modals.info("show me");
            } */
        });


    }

    $(function () {

        $('.seat-middle').find(".row:first").hide();

        $(".xtyh-middle-r").find(".row:last").addClass("row-zg");
        $(".xtyh-middle-r").find(".row:eq(1)").addClass("row-two");
    });


    //导出excel
    function toExcel() {
        window.location.href = '<%=basePath%>nairetemplate/excel.do';
    }


</script>
</body>
</html>