<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>公開禁止シート一覧 - 連想ゲーム(管理)</title>
<link rel="stylesheet" href="resources/css/rensoug-page.css"></link>
<link rel="stylesheet" href="resources/css/rensoug-public.css"></link>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="resources/js/public_sheets.js"></script>
<script>
	var json = '${json}';
	getPublicSheetNoLogin(json);
</script>
</head>
<body>
	<%@include file="kanrisha_page.jsp"%>
	<main>
		<div>
			<a href="kanri_top">管理者TOP</a>
		</div>

		<h2>公開禁止したシート一覧</h2>
		<div class="publicSheet" id="publicSheet" hidden>
			<a class="sheetLink"></a>
			<p class="madeBy"></p>
		</div>
	</main>
</body>
</html>