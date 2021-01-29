<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>シート選択-連想ゲーム</title>
<link rel="stylesheet" href="rensoug-select.css"></link>
<link rel="stylesheet" href="rensoug-page.css"></link>
</head>
<body>
	<%@include file="rensou_header.jsp"%>
	<%@include file="rensou_sidemenu.jsp"%>
	<main>
		<div class="contents">
			<div class="userName">
				<h1>ユーザー名</h1>
			</div>
			<div>
				<div class="sheet">
					<a href="rensougame.jsp">シート</a>
				</div>
				<!--
				<div class="add">
					<img src="addbox.jpg">
				</div>
				-->

			</div>
			<div class="create_sheet">
				<strong>新しいシートを作る</strong>
				<form action="newSheet" method="post">
					<a>新しいシートの名前：</a> <input type="text" name="newSheetName">
					<p>
						<input type="submit" value="シート作成">
					</p>
				</form>
			</div>
		</div>
		<!--
		<script src="http://code.jquery.com/jquery-1.12.4.min.js">

		</script>
		<script src="rensoug-select.js"></script>
		-->
	</main>
</body>
</html>