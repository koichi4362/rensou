<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>連想ゲーム</title>
<link rel="stylesheet" href="rensoug.css"></link>
</head>
<body class="clearfix">
	<div id="topBar">
		<img src="topimg.jpg">
		<!-- 上部タイトル画像 -->
	</div>

	<div class="buttons">
		<div class="button">
			<a href="rensou_select.jsp">シート選択へ戻る</a>
		</div>
		<div class="button">
			<h2 class="sheetName">シート名</h2>
			<a class="henkou">シート名を変更...</a>
		</div>
		<div>
			<button>連想ボックスを保存</button>
		</div>
	</div>
	<div class="common" id="box" hidden>
		<div class="textbox common">
			<form onsubmit="return false;">
				<input type="text" autocomplete="off" name="textbox">
			</form>
		</div>
	</div>
	<div class="common">
		<img src="addbox.jpg" class="add">
	</div>

	<script src="http://code.jquery.com/jquery-1.12.4.min.js">

	</script>
	<script src="rensoug.js"></script>
</body>
</html>