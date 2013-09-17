check = (element) -> $('#' + element).click() if !cookieExists(element)
cookieExists = (cookie_name) ->
	cookie = getCookie(cookie_name)
	cookie != undefined && cookie != ""

$(window).load ->
	$('input:checkbox').each (index, element) =>
		$(element).saveState()

	check('channel_pwr')
	check('channel_samorzad')
	check('channel_napwr')
	check('channel_estudent')

	return