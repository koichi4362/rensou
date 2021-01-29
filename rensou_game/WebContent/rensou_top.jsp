<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TOP-連想ゲーム-top</title>
<link rel="stylesheet" href="rensoug-top.css"></link>
<link rel="stylesheet" href="rensoug-page.css"></link>
</head>
<body>
	<%@include file="rensou_header.jsp"%>
	<%@include file="rensou_sidemenu.jsp"%>
	<main>
		<div id="contents">
			<div id="topInfo">連想ゲームへようこそ！！</div>
			<div id="topMenu">
				<ul>
					<li><a href="rensou_select.jsp" class="contentsList">スタート</a></li>
					<li><a href="rensou_howto.jsp" class="contentsList">遊び方</a></li>
					<li><a href="rensou_option.jsp" class="contentsList">設定</a></li>
					<li><a href="rensou_login.jsp" class="contentsList">ログイン</a></li>
				</ul>
			</div>
		</div>
	</main>
</body>
</html>