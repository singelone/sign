<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>首页</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="这是主页">
  </head>
  
  <body>
    	删除<br>
  <h1>code: ${code}</h1>
  <h1>姓名: ${name}</h1>
  <h1>职位: ${position}</h1>
  <h1>电话: ${mobile}</h1>
        <h1>性别: <c:if test="${gender ==1}">男</c:if><c:if test="${gender ==0}">女</c:if></h1>
        <c:if test="${not empty email}"><h1>邮箱: ${email}</h1></c:if>
  <h1>头像: <a href="${avatar}">点我查看</a></h1>

  </body>
</html>
