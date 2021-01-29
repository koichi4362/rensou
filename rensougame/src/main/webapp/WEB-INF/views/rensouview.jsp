<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>連想ゲーム - 閲覧</title>
<link rel="stylesheet" href="resources/css/rensouview.css"></link>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="resources/js/rensouview.js"></script>
<script>
	var json = '${json}';
	openSheet(json);
</script>
</head>
<body class="clearfix">
	<div id="topBar">
		<img src="resources/images/topimg.jpg">
		<!-- 上部タイトル画像 -->
	</div>
	<div class="buttons">
		<div class="button">
			<a href="top">TOP</a><a href="public_sheets">投稿一覧へ戻る</a>
			<c:if test="${not empty sessionScope.loginUser}">
				<a href="mypage">マイページ</a>
			</c:if>
		</div>
		<h2 class="sheetName">${sheet_name}</h2>
	</div>
	<div class=" common" id="box" hidden>
		<div class="textbox common">
			<p class="word"></p>
		</div>
	</div>
</body>
</html>