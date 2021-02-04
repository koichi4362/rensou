/**
 *
 */

//シート一覧表示
function getPublicSheet(json, user_id) {
	$(window).on("load", function() {
		sheetList = JSON.parse(json).sheet_list
		for (var i = 0; i < sheetList.length; i++) {
			var id = sheetList[i].sheet_id;
			$('#publicSheet').clone().attr('id', 'publicSheet' + id).insertBefore('#publicSheet').removeAttr('hidden');
			var sheet = $('#publicSheet' + id);
			sheet.children('.sheetLink').attr('href', 'viewSheet?sheet_id=' + sheetList[i].sheet_id + '&user_id=' + user_id).text(sheetList[i].sheet_name);
			sheet.children('.madeBy').text("作ったひと:" + sheetList[i].user_name + " さん");
			sheet.children('.goodCount').text("いいね:" + sheetList[i].good_count);
		}
	});
}

function getPublicSheetNoLogin(json) {
	$(window).on("load", function() {
		sheetList = JSON.parse(json).sheet_list
		for (var i = 0; i < sheetList.length; i++) {
			var id = sheetList[i].sheet_id;
			$('#publicSheet').clone().attr('id', 'publicSheet' + id).insertBefore('#publicSheet').removeAttr('hidden');
			var sheet = $('#publicSheet' + id);
			sheet.children('.sheetLink').attr('href', 'viewSheetNoLogin?sheet_id=' + id).text(sheetList[i].sheet_name);
			sheet.children('.madeBy').text("作ったひと:" + sheetList[i].user_name + " さん");
			sheet.children('.goodCount').text("いいね:" + sheetList[i].good_count);
		}
	});
}

