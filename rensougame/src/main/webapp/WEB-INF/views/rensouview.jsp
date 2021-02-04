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
			<c:choose>
				<c:when test="${not empty sessionScope.loginUser}">
					<a href="mypage">マイページ</a>
				</c:when>
				<c:when test="${not empty sessionScope.loginManager}">
					<a href="kanri_top">管理者TOP</a>
				</c:when>
			</c:choose>
			<c:choose>
				<c:when test="${not empty sessionScope.loginManager}">
					<c:choose>
						<c:when test="${sheet.public_flag == 1}">
							<div>
								<button type="button"
									onclick="location.href='stopOpenPublic?sheet_id=${sheet.sheet_id }'">公開停止する</button>
							</div>
						</c:when>
						<c:otherwise>
							<div>
								<button type="button"
									onclick="location.href='allowOpenPublic?sheet_id=${sheet.sheet_id }'">公開可能にする</button>
							</div>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:when test="${not empty sessionScope.loginUser}">

					<c:choose>
						<c:when test="${good_flag == 0}">
							<div>
								<button type="button"
									onclick="location.href='addGood?sheet_id=${sheet.sheet_id }&user_id=${sessionScope.loginUser.user_id }'">いいね</button>
							</div>
						</c:when>
						<c:otherwise>
							<div>
								<button type="button"
									onclick="location.href='cancelGood?sheet_id=${sheet.sheet_id }&user_id=${sessionScope.loginUser.user_id }'">キャンセル</button>
							</div>
						</c:otherwise>
					</c:choose>

				</c:when>
			</c:choose>
		</div>
		<h2 class="sheetName">${sheet.sheet_name}</h2>
	</div>
	<div class="common" id="box" hidden>
		<div class="textbox common">
			<p class="word"></p>
		</div>
	</div>
</body>
</html>