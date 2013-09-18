$(function() {
	var displayOnlyChecked = function() {
		$('input:checkbox').each(function(index, element) {
			var el = $(element);
			var id = el.prop("id");
			var isChecked = el.prop("checked");
			toggleShowingFeeds(id, isChecked);
		});
	};

	$('header').fitText(3, {
		minFontSize : '10px',
		maxFontSize : '20px'
	});
	jsRoutes.controllers.Application.feedsHtml().ajax({
		success : function(data) {
			$('#cbp-vm ul').html(data);
			displayOnlyChecked();
			$('.ajax_loader').hide();
		},
		error : function() {
			$('.ajax_loader').hide();
		}
	});

	$('.codrops-icon-drop').click(function() {
		$('body,html').animate({
			scrollTop : 0
		}, 800);
		return false;
	});
});