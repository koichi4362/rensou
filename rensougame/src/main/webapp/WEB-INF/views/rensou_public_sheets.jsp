<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>投稿一覧 - 連想ゲーム</title>
<link rel="stylesheet" href="resources/css/rensoug-page.css"></link>
<link rel="stylesheet" href="resources/css/rensoug-public.css"></link>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="resources/js/public_sheets.js"></script>
<c:choose>
	<c:when test="${not empty sessionScope.loginUser}">
		<script>
			let json = '${json}';
			let user_id = '${sessionScope.loginUser.user_id}';
			getPublicSheet(json, user_id);
		</script>
	</c:when>
	<c:otherwise>
		<script>
			var json = '${json}';
			getPublicSheetNoLogin(json);
		</script>
	</c:otherwise>
</c:choose>
</head>
<body>
	<c:choose>
		<c:when test="${not empty sessionScope.loginManager}">
			<%@include file="kanrisha_page.jsp"%></c:when>
		<c:otherwise>
			<%@include file="rensou_header.jsp"%>
			<%@include file="rensou_sidemenu.jsp"%>
		</c:otherwise>
	</c:choose>
	<main>
		<c:choose>
			<c:when test="${not empty sessionScope.loginManager}">
				<div>
					<a href="kanri_top">管理者TOP</a>
				</div>
			</c:when>
			<c:otherwise>
				<div>
					<a href="top">TOPページ</a>
				</div>
			</c:otherwise>
		</c:choose>
		<h2>投稿シート一覧（新着順）</h2>
		<div class="publicSheet" id="publicSheet" hidden>
			<a class="sheetLink"></a>
			<p class="madeBy"></p>
			<p class="goodCount"></p>
		</div>
	</main>
</body>
</html>