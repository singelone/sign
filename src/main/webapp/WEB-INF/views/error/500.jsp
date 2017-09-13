<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
    <!--<![endif]-->
    <!-- BEGIN HEAD -->

    <head>
        <meta charset="utf-8" />
        <title>500 错误</title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta content="width=device-width, initial-scale=1" name="viewport" />
        <meta content="" name="description" />
        <meta content="" name="author" />

		<!-- 开始全局CSS -->
		<link href="<c:url value="/resources/admin/global/plugins/font-awesome/css/font-awesome.min.css" />" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/resources/admin/global/plugins/simple-line-icons/simple-line-icons.min.css" />" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/resources/admin/global/plugins/bootstrap/css/bootstrap.min.css" />" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/resources/admin/global/plugins/uniform/css/uniform.default.css" />" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/resources/admin/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" />" rel="stylesheet" type="text/css" />
		<!-- 结束全局CSS -->

		<!-- 开始主题CSS -->
		<link href="<c:url value="/resources/admin/global/css/components.min.css" />" rel="stylesheet" id="style_components" type="text/css" />
		<link href="<c:url value="/resources/admin/global/css/plugins.min.css" />" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/resources/admin/layouts/layout2/css/layout.min.css" />" rel="stylesheet" type="text/css" />
		<link href="<c:url value="/resources/admin/layouts/layout2/css/themes/blue.min.css" />" rel="stylesheet" type="text/css" id="style_color" />
		<link href="<c:url value="/resources/admin/layouts/layout2/css/custom.min.css" />" rel="stylesheet" type="text/css" />
		<!-- 结束主题CSS -->
		<link href="<c:url value="/resources/admin/pages/css/error.min.css" />" rel="stylesheet" type="text/css" />
		<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
		<link rel="shortcut icon" href="<c:url value="/resources/comm/img/favicon.ico" />"/>
	 </head>
    <!-- END HEAD -->

    <body class=" page-404-full-page">
        <div class="row">
            <div class="col-md-12 page-404">
                <div class="number font-red"> 500 </div>
                <div class="details">
                    <h3>出错了.</h3>
                    <p> 程序出错了，请返回首页.
                        <br/>
                        <a class="btn red btn-outline" href="index.html"> 返回首页 </a>
                </div>
            </div>
            <div class="col-md-12">
				<div class="note note-warning">
				错误状态代码是：${pageContext.errorData.statusCode}<br>
				错误发生页面是：${pageContext.errorData.requestURI} <br>
				错误信息：${pageContext.exception}<br>
				错误堆栈信息：<br/>
				<c:forEach var="trace" items="${pageContext.exception.stackTrace}">
				<p>${trace}</p>
				</c:forEach>
				</div>
			</div>
        </div>
        <!--[if lt IE 9]>
<script src="../assets/global/plugins/respond.min.js"></script>
<script src="../assets/global/plugins/excanvas.min.js"></script>
<![endif]-->
		<!-- BEGIN CORE PLUGINS -->
		<script src="<c:url value="/resources/admin/global/plugins/jquery.min.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/admin/global/plugins/bootstrap/js/bootstrap.min.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/admin/global/plugins/js.cookie.min.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/admin/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/admin/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/admin/global/plugins/jquery.blockui.min.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/admin/global/plugins/uniform/jquery.uniform.min.js" />" type="text/javascript"></script>
		<script src="<c:url value="/resources/admin/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js" />" type="text/javascript"></script>
		<!-- END CORE PLUGINS -->
        <!-- BEGIN THEME GLOBAL SCRIPTS -->
        <script src="<c:url value="/resources/admin/global/scripts/app.min.js" />" type="text/javascript"></script>
        <!-- END THEME GLOBAL SCRIPTS -->
        <!-- BEGIN THEME LAYOUT SCRIPTS -->
        <!-- END THEME LAYOUT SCRIPTS -->
</body>
</html>