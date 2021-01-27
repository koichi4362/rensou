<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>連想ゲーム</title>
<link rel="stylesheet" href="resources/css/rensoug.css"></link>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="resources/js/rensoug.js"></script>
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
			<a href="top">TOP</a>
			<c:if test="${not empty sessionScope.loginUser}">
				<a href="mypage">マイページ</a>
			</c:if>
		</div>
		<c:if test="${not empty sessionScope.loginUser}">
			<div class="button">
				<!-- ajaxでシート名変更したい -->
				<h2 class="sheetName">シート名</h2>
				<a class="henkou">${rensou.sheet_name}</a>
			</div>
		</c:if>
	</div>
	<form:form action="saveSheet" modelAttribute="rensou" method="POST">
		<c:if test="${not empty sessionScope.loginUser}">
			<!-- シートテーブルからユーザーidとシートidをhiddenで持たせる。
		セキュリティ的によくないが個人開発でこれでやる。本来はcookie -->
			<form:input type="hidden" id="user_id" name="user_id" path="user_id" />
			<form:input type="hidden" id="sheet_id" name="sheet_id"
				path="sheet_id" />
			<div>
				<button>連想ボックスを保存</button>
			</div>
		</c:if>

		<!-- jsでdivとdivの間に要素を追加するため二重にしてる -->
		<div class="common" id="box" hidden>
			<div class="textbox common">
				<input type="text" autocomplete="off">
			</div>
		</div>
		<div class="common">
			<img src="resources/images/addbox.jpg" class="add">
		</div>
	</form:form>
</body>
</html>