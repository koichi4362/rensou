<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン-連想ゲーム</title>
<link rel="stylesheet" href="resources/css/rensoug-page.css"></link>
<link rel="stylesheet" href="resources/css/rensoug-form.css"></link>
</head>
<body>
	<%@include file="rensou_header.jsp"%>
	<%@include file="rensou_sidemenu.jsp"%>
	<main>
		<div>
			<h1>ログイン</h1>
			<div class="form">
				<form:form action="doLogin" modelAttribute="user" method="POST">
					<p class="koumoku">
						メールアドレス
						<form:input type="email" path="e_mail" />
					</p>
					<p class="koumoku">
						パスワード
						<form:password path="passwd" />
					</p>
					<p>
						<button>ログイン</button>
					</p>
					<p>
						または... <a href="createAccount">新規登録</a>
					</p>
				</form:form>
			</div>
		</div>
	</main>
</body>
</html>