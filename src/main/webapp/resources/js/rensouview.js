/**
 *
 */


//シート展開
function openSheet(json) {
	$(window).on("load", function() {
		NodeList = JSON.parse(json).node_list
		for (var i = 0; i < NodeList.length; i++) {
			var id = NodeList[i].node_id;
			$('#box').clone().insertBefore('#box').attr('id', 'Node' + id).removeAttr('hidden').append('<div class="kurosenn common"><img src="resources/images/kurosenn.jpg"></div>');
			$('#Node' + id).children('.textbox').children('.word').text(NodeList[i].node_name);
		}
	});
}



