<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="resources/css/kanrisha-page.css"></link>
</head>
<body>
	<div>
		<c:choose>
			<c:when test="${not empty sessionScope.loginManager}">
				<a href="kanri_top"><img src="resources/images/kanrisha.jpg"
					class="headerimg"></a>
			</c:when>
			<c:otherwise>
				<a href="kanri"><img src="resources/images/kanrisha.jpg"
					class="headerimg"></a>
			</c:otherwise>
		</c:choose>
	</div>
	<c:if test="${not empty sessionScope.loginManager}">
		<div>
			<h2>管理者 ${sessionScope.loginManager.user_name} さんとしてログインしています</h2>
		</div>
	</c:if>
	<c:if test="${not empty msg }">
		<p>${msg}</p>
	</c:if>
</body>
</html>