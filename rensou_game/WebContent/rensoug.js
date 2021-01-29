

let i = 1;
//ボックスを生成する関数。変数iをインクリメントしながらidにセットしてボックスを生成。
$(function() {
	$('.add').click(function() {
		$('#box').clone().insertBefore('#box').attr('id', i++).removeAttr('hidden').append('<div class="kurosenn common"><img src="kurosenn.jpg"></div>');
	});
});

//シートを展開する関数。
//(ループ処理で)ノードの数だけボックスを生成し各ボックスにデータベースから取得したノードを入れる。
//変数iも最終ノードのidと同値にする。
$(function() {
	document.getElementById('?').querySelector('input').value = "？？？";
});
