$(window).load ->
	$(".checkBoxContainer a").click ->
		link = $(this).attr('href').replace('#', '.')
		firstFeed = $(link + ':visible:first')
		offset = firstFeed.offset()
		if offset && offset.top != 0
			finalOffset = offset.top - 65
			$('html, body').animate({ scrollTop:finalOffset }, 300)