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
		<c:if test="${not empty loginAccount}">
			<p>
				<strong>${loginAccount.userName}</strong>さん<br>がログインしています
			</p>
		</c:if>
		<ul>
			<li><p class="menyu-">
					<strong>メニュー</strong>
				</p></li>
			<li><a href="rensou_top.jsp" class="contentsList">TOP</a></li>
			<li><a href="rensou_select.jsp" class="contentsList">スタート</a></li>
			<li><a href="rensou_howto.jsp" class="contentsList">遊び方</a></li>
			<li><a href="rensou_option.jsp" class="contentsList">設定</a></li>
			<li><a href="rensou_login.jsp" class="contentsList">ログイン</a></li>

			<c:if test="${not empty loginAccount}">
				<li><a href="logout" class="contentsList">ログアウト</a></li>

			</c:if>
		</ul>
	</div>

</body>
</html>