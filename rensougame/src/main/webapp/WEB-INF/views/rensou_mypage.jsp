<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>マイページ - 連想ゲーム</title>
<link rel="stylesheet" href="resources/css/rensoug-page.css"></link>
<link rel="stylesheet" href="resources/css/rensoug-mypage.css"></link>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="resources/js/mypage.js"></script>
<script>
	var json = '${json}';
	getMySheet(json);
</script>
</head>
<body>
	<%@include file="rensou_header.jsp"%>
	<%@include file="rensou_sidemenu.jsp"%>
	<main>
		<div id="upperBlock" class="clearfix">
			<div class="floatleft">
				<h2 id="userNameBlock">
					<strong>${sessionScope.loginUser.user_name}</strong> さんのマイページ
				</h2>
			</div>
			<div id="buttonsBlock" class="floatright">
				<div class="button">
					<a href="editAccount">アカウントの編集</a>
				</div>
				<div class="button">
					<a href="logout">ログアウト</a>
				</div>
				<br />
			</div>
		</div>
		<div id="userSheets">
			<h2>シート一覧</h2>
			<div class="sheet" id="sheet" hidden>
				<!-- シートがないとき枠だけ出るの直す -->
				<a href="openSheetGame"></a>
			</div>
			<!-- シートを展開するリンク。ループ処理でシートの数だけ表示 -->
		</div>
	</main>
</body>
</html>