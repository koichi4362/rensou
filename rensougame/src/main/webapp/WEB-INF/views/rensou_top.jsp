<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>TOP-連想ゲーム</title>
<link rel="stylesheet" href="resources/css/rensoug-top.css"></link>
<link rel="stylesheet" href="resources/css/rensoug-page.css"></link>
</head>
<body>
	<%@include file="rensou_header.jsp"%>
	<%@include file="rensou_sidemenu.jsp"%>
	<main>
		<div id="contents">
			<div id="topInfo">連想ゲームへようこそ！！</div>
			<div id="topMenu">
				<ul>
					<li><a href="noSheetGame" class="contentsList">スタート</a></li>
					<li><a href="howto" class="contentsList">遊び方</a></li>
					<li><a href="public_bord" class="contentsList">投稿一覧</a></li>
					<li><a href="createAccount" class="contentsList">アカウント新規作成</a></li>
					<c:if test="${not empty sessionScope.loginUser}">
						<li><a href="mypage" class="contentsList">マイページ</a></li>
					</c:if>
				</ul>
			</div>
		</div>
	</main>
</body>
</html>