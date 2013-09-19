window.displayOnlyChecked = function() {
	$('input:checkbox').each(function(index, element) {
		var el = $(element);
		var id = el.prop("id");
		var isChecked = el.prop("checked");
		toggleShowingFeeds(id, isChecked);
	});
};

var ajaxSuccess = function(data) {
	$('#cbp-vm ul').html(data);
	displayOnlyChecked();
	$('.ajax_loader').hide();
};

var ajaxError = function() {
	$('.ajax_loader').hide();
};

ajaxCache = {
		success:  function(data) {
			$('#cbp-vm ul').html(data);
			displayOnlyChecked();
			jsRoutes.controllers.Application.feedsHtml().ajax(ajaxFinal)
		},
		error : ajaxError
}

ajaxFinal = {
		success: ajaxSuccess,
		error : ajaxError
}

$(function() {
	$('header').fitText(3, {
		minFontSize : '10px',
		maxFontSize : '20px'
	});
	
	jsRoutes.controllers.Application.feedsFromDBHTML().ajax(ajaxCache);

	$('.codrops-icon-drop').click(function() {
		$('body,html').animate({
			scrollTop : 0
		}, 800);
		return false;
	});
});