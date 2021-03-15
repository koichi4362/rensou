


//シート一覧表示
function getMySheet(json) {
	$(window).on("load", function() {
		sheetList = JSON.parse(json).sheet_list
		for (var i = 0; i < sheetList.length; i++) {
			var id = sheetList[i].sheet_id;
			$('#mySheet').clone().attr('id', 'mySheet' + id).insertBefore('#mySheet').removeAttr('hidden');
			var sheetNameForm = $('#mySheet' + id).children('.sheetNameForm');
			sheetNameForm.children('.sheet_id').attr('value', id);
			sheetNameForm.children('.sheet').children('a').attr({ 'class': 'sheetLink', 'href': "openSheetGame?sheet_id=" + id, 'id': 'sheetLinkId' + id }).text(sheetList[i].sheet_name);
			sheetNameForm.children('.sheet').children('input').attr({ class: "updateNameInput", 'value': sheetList[i].sheet_name, 'id': 'inputId' + id });
			sheetNameForm.children('.buttonOfSheet').attr('id', 'buttonOfSheet' + id);
			sheetNameForm.children('.buttonOfSheet').children('.sheetNameBtn').attr('id', 'sheetNameBtn' + id);
			sheetNameForm.children('.buttonOfSheet').children('.switchPublicBtn').attr('id', 'switchPublicBtn' + id);
			sheetNameForm.children('.buttonOfUpdateSheetName').attr('id', 'buttonOfUpdateSheetName' + id);
			if (sheetList[i].public_flag === 1)  {
				$('#switchPublicBtn' + id).text("公開中止");
			}else{
				$('#switchPublicBtn' + id).text("公開する");
			}
		}
	});
}

//シート名変更フォーム表示
function updateSheetName(id) {
	cancelUpdateName();
	id = id.replace(/[^0-9]/g, '');
	$('#inputId' + id).removeAttr('type');
	$('#sheetLinkId' + id).hide();
	$('#buttonOfSheet' + id).hide();
	$('#buttonOfUpdateSheetName' + id).show();
}

//シート名変更キャンセル
function cancelUpdateName() {
	$('.updateNameInput').attr('type', 'hidden');
	$('.sheetLink').show();
	$('.buttonOfSheet').show();
	$('.buttonOfUpdateSheetName').hide();
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