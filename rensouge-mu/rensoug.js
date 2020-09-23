$(function(){
	$('.add').click(function(){
		$('#box').clone().insertBefore('#box').attr('id', 'box-clone').removeAttr('hidden').append('<div class="kurosenn common"><img src="kurosenn.jpg"></div>');
	});
});
