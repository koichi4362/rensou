

$(function(){
	$('.add').click(function(){
		$(this).prev().clone().insertBefore('.add');
		$('.sheet').each(function(i){
			$(this).attr('id',(i+1));
		});
	});
});
