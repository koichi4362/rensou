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
	<!-- <div>${pageContext.request.contextPath}</div>
	<c:if test="${pageContext.request.requestURL != \"/rensou_top.jsp\"}">
		</c:if>
	-->

	<div id="sideMenu">
		<c:if test="${not empty sessionScope.loginUser}">
			<p>
				<strong>${loginUser.user_name}</strong>さん<br>がログインしています
			</p>
		</c:if>
		<ul>
			<li><p class="menyu-">
					<strong>メニュー</strong>
				</p></li>
			<li><a href="top" class="contentsList">TOP</a></li>
			<li><a href="howto" class="contentsList">遊び方</a></li>
			<li><a href="public_bord" class="contentsList">投稿一覧</a></li>
			<c:choose>
				<c:when test="${not empty sessionScope.loginUser}">
					<li><a href="mypage" class="contentsList">マイページ</a></li>
					<li><a href="doLogout" class="contentsList">ログアウト</a></li>
				</c:when>
				<c:otherwise>
					<li><a href="login" class="contentsList">ログイン</a></li>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
</body>
</html>