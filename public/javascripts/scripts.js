$(function(){
	$('.clearfix').fitText(3, { maxFontSize: '15px' });
	jsRoutes.controllers.Application.feedsHtml().ajax({
		success: function(data) { 
			$('#cbp-vm ul').html(data);
			$('.ajax_loader').hide();
		}
	});
});