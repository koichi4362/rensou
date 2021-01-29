<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録-連想ゲーム</title>
<link rel="stylesheet" href="rensoug-page.css"></link>
<link rel="stylesheet" href="rensoug-login.css"></link>
</head>
<body>
	<%@include file="rensou_header.jsp"%>
	<%@include file="rensou_sidemenu.jsp"%>
	<main>
		<div>
			<h1>ユーザー新規登録</h1>
			<c:if test="${not empty msg}">
				<p>${msg}</p>
			</c:if>
			<div class="form">
				<form action="newaccount" method="POST">
					<p class="koumoku">
						ユーザー名 <input type="text" name="userName">
					</p>
					<p class="koumoku">
						メールアドレス <input type="email" name="email">
					</p>
					<p class="koumoku">
						パスワード <input type="password" name="passwd">
					</p>
					<p>
						<button>新規登録</button>
				</form>
			</div>
		</div>
	</main>
</body>
</html>