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
		<a href="top"><img src="resources/images/topimg.jpg"></a>
	</div>
	<c:if test="${not empty msg }">
		<p>${msg}</p>
	</c:if>
</body>
</html>