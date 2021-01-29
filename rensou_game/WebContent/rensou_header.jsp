<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="topBar">
		<img src="topimg.jpg">
		<!-- 上部タイトル画像 -->
	</div>
	<c:if test="${not empty msg }">
		<p>${msg}</p>
	</c:if>
</body>
</html>