<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>設定-連想ゲーム</title>
<link rel="stylesheet" href="rensoug-page.css"></link>
<link rel="stylesheet" href="rensoug-option.css"></link>
</head>
<body>
	<%@include file="rensou_header.jsp"%>
	<%@include file="rensou_sidemenu.jsp"%>
	<main>
		<h1>設定</h1>
		<div class="contents clearfix">
			<form>
				<div class="koumoku">
					背景色： <select name="backgroundColor">
						<option>ホワイト</option>
						<option>グレー</option>
						<option>ピンク</option>
						<option>ライトグリーン</option>
						<option>ライトブルー</option>
					</select>
				</div>
				<div class="koumoku">
					文字フォント： <select>
						<option>ゴシック体</option>
						<option>明朝体</option>
					</select>
				</div>
				<div class="hozon">
					<button type="submit">保存</button>
				</div>
			</form>
		</div>
	</main>
</body>
</html>