<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン - 連想ゲーム(管理)</title>
<link rel="stylesheet" href="resources/css/rensoug-form.css"></link>
</head>
<body>
	<%@include file="kanrisha_page.jsp"%>
	<main>
		<div>
			<h1>ログイン</h1>
			<div class="form">
				<form:form action="kanri_doLogin" modelAttribute="user"
					method="POST">
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
				</form:form>
			</div>
		</div>
	</main>
</body>
</html>