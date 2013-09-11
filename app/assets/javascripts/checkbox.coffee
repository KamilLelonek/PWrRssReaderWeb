$(window).load ->
	$('input:checkbox').each (index, element) =>
		$(element).saveState()

	return