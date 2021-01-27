
//シート一覧表示
function getMySheet(json) {
	$(window).on("load", function() {
		sheetList = JSON.parse(json).sheet_list
		for (var i = 0; i < sheetList.length; i++) {
			$('#sheet').clone().insertBefore('#sheet').attr('id', 'sheet' + i).removeAttr('hidden');
			$('#sheet' + i).children('a').attr('href', "openSheetGame?sheet_id=" + sheetList[i].sheet_id);
			$('#sheet' + i).children('a').text(sheetList[i].sheet_name);
		}
	});
}



//$(function() {
//	$(window).on("load", function() {
//		$.ajax({
//			url: '/rensougame/getMySheets',
//			type: 'POST',
//			dataType: "json",
//			success: function(data) {
//				for (var i = 0; i < data.length; i++) {
//					$('#sheet').clone().insertBefore('#sheet').attr('id', 'sheet' + i).removeAttr('hidden');
//					$('#sheet' + i).children('a').attr('href', "openSheetGame?sheet_id=" + data[i].sheet_id);
//					$('#sheet' + i).children('a').text(data[i].sheet_name);
//				}
//			}
//		})
//	});
//});