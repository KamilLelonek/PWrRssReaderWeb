$(function(){
	$('header').fitText(3, { minFontSize: '10px', maxFontSize: '20px' });
	jsRoutes.controllers.Application.feedsHtml().ajax({
		success: function(data) { 
			$('#cbp-vm ul').html(data);
			$('.ajax_loader').hide();
		},
		error: function() { $('.ajax_loader').hide(); }
	});
});