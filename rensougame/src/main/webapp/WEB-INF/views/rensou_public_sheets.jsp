<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>投稿一覧 - 連想ゲーム</title>
<link rel="stylesheet" href="resources/css/rensoug-page.css"></link>
<link rel="stylesheet" href="resources/css/rensoug-public.css"></link>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="resources/js/public_sheets.js"></script>
<script>
	var json = '${json}';
	getPublicSheet(json);
</script>
</head>
<body>
	<%@include file="rensou_header.jsp"%>
	<%@include file="rensou_sidemenu.jsp"%>
	<main>
		<h2>投稿シート一覧（新着順）</h2>

		<div class="publicSheet" id="publicSheet" hidden>
			<a class="sheetLink"></a>
			<p class="madeBy"></p>
		</div>
	</main>
</body>
</html>