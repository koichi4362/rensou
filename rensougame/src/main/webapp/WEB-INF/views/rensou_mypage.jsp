<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>マイページ - 連想ゲーム</title>
<link rel="stylesheet" href="resources/css/rensoug-page.css"></link>
<link rel="stylesheet" href="resources/css/rensoug-mypage.css"></link>
<script src="http://code.jquery.com/jquery-1.12.4.min.js"></script>
<script src="resources/js/mypage.js"></script>
<script>
	var json = '${json}';
	getMySheet(json);
</script>
</head>
<body>
	<%@include file="rensou_header.jsp"%>
	<%@include file="rensou_sidemenu.jsp"%>
	<main>
		<div id="upperBlock" class="clearfix">
			<div class="floatleft">
				<h2 id="userNameBlock">
					<strong>${sessionScope.loginUser.user_name}</strong> さんのマイページ
				</h2>
			</div>
			<div id="buttonsBlock" class="floatright">
				<div class="button">
					<a href="editAccount">アカウントの編集</a>
				</div>
				<div class="button">
					<a href="logout">ログアウト</a>
				</div>
				<br />
			</div>
		</div>
		<div id="userSheets">
			<h2>シート一覧</h2>
			<span><a href="openSheetGame?sheet_id=0">新しいシートを作る</a></span>
			<div id="mySheet" hidden>
				<form:form class="sheetNameForm" action="updateSheetName"
					modelAttribute="sheet">
					<form:input class="sheet_id" type="hidden" path="sheet_id" />
					<div class="sheet floatleft">
						<a href="openSheetGame"></a>
						<form:input type="hidden" path="sheet_name" />
					</div>
					<div class="buttonOfSheet sheetButtonBlock floatleft clearfix ">
						<button class="sheetNameBtn" type="button"
							onClick="updateSheetName(this.id)">シート名変更</button>
						<br />
						<button formaction="switchPublicFlag" class="switchPublicBtn"></button>
					</div>
					<div
						class="buttonOfUpdateSheetName sheetButtonBlock floatleft clearfix"
						style="display: none;">
						<button type="submit">決定</button>
						<br />
						<button type="button" onClick="cancelUpdateName()">キャンセル</button>
					</div>
				</form:form>
			</div>
		</div>
	</main>
</body>
</html>