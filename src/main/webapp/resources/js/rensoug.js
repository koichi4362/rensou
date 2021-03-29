
//シート展開
function openSheet(json) {
	$(window).on("load", function() {
		eNodeList = JSON.parse(json).node_list
		for (var i = 0; i < eNodeList.length; i++) {
			$('#box').clone().insertBefore('#box').attr('id', 'eNode' + i).removeAttr('hidden').append('<div class="kurosenn common"><img src="resources/images/kurosenn.jpg"></div>');
			$('#eNode' + i).children('div').children('input').attr({
				'name': "eWordList[" + eNodeList[i].node_id + "].word", value: eNodeList[i].node_name
			});
		}
	});
}

//シート名編集モードへ
function changeSheetName(changeTo) {
	if (changeTo == '編集') {
		$('#changeSheetNameButton').hide();
		$('#sheetNameInputDiv').show();
	}
	else {
		$('#changeSheetNameButton').show();
		$('#sheetNameInputDiv').hide();
		alert('シート名の変更は連想ボックスを保存を押すと一緒に保存されます。');
	}
}


//シート名変更
function updateSheetName(input) {
	let inputSheetName = input.value;
	document.getElementById("sheetName").text = inputSheetName;
	document.getElementById("sheetNameForm").value = inputSheetName;
}

//ボックス追加
let newNodeCount = 0;
$(function() {
	$('.add').click(function() {
		$('#box').clone().insertBefore('#box').attr('id', 'nNode' + newNodeCount).removeAttr('hidden').append('<div class="kurosenn common"><img src="resources/images/kurosenn.jpg"></div>');
		$('#nNode' + newNodeCount).children('div').children('input').attr(
			'name', "nWordList[" + newNodeCount + "].word");
		newNodeCount++;
	});
});



//$(function() {
//	let sheet_id = $('#sheet_id').attr(name);
//	$(window).on("load", function() {
//		$.ajax({
//			url: '/rensougame/open',
//			type: 'POST',
//			dataType: "json",
//			data: {
//				sheet_id: sheet_id
//			},
//			success: function(data) {
//				for (var i = 0; i < data.length; i++) {
//					$('#box').clone().insertBefore('#box').attr('id', 'eNode' + i).removeAttr('hidden').append('<div class="kurosenn common"><img src="resources/images/kurosenn.jpg"></div>');
//					$('#eNode' + i).children('div').children('input').attr({
//						'name': "eWordList[" + data[i].node_id + "].word", value: data[i].node_name
//					});
//				}
//			}
//		})
//	});
//});
