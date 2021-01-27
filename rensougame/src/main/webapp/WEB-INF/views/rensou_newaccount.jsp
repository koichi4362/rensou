<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規登録-連想ゲーム</title>
<link rel="stylesheet" href="resources/css/rensoug-page.css"></link>
<link rel="stylesheet" href="resources/css/rensoug-form.css"></link>
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
				<form:form action="doCreateAccount" modelAttribute="user"
					method="POST">
					<p class="koumoku">
						ユーザー名
						<form:input path="user_name" />
					</p>
					<p class="koumoku">
						メールアドレス
						<form:input path="e_mail" />
					</p>
					<p class="koumoku">
						パスワード
						<form:password path="passwd" />
					</p>
					<p class="koumoku">
						パスワード(確認) <input type="password" name="passwd2">
					</p>
					<!--javascriptで再入力のチェック-->
					<p>
						<button>新規登録</button>
					</p>
				</form:form>
			</div>
		</div>
	</main>
</body>
</html>