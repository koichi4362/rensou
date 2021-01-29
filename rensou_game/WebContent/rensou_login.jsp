<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン-連想ゲーム</title>
<link rel="stylesheet" href="rensoug-page.css"></link>
<link rel="stylesheet" href="rensoug-login.css"></link>
</head>
<body>
	<%@include file="rensou_header.jsp"%>
	<%@include file="rensou_sidemenu.jsp"%>
	<main>
		<div>
			<h1>ログイン</h1>
			<div class="form">
				<form action="login" method="POST">
					<p class="koumoku">
						メールアドレス <input type="email" name="email">
					</p>
					<p class="koumoku">
						パスワード <input type="password" name="passwd">
					</p>
					<p>
						<button>ログイン</button>
					</p>
					<p>
						または... <a href="rensou_newaccount.jsp">新規登録</a>
					</p>
				</form>
			</div>
		</div>
	</main>
</body>
</html>