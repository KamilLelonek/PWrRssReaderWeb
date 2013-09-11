$ = jQuery
$ ->
	createCookie = (name, value) ->
	 	document.cookie = name + "=" + value

	getCookie = (name) ->
		allCookies = '; ' + document.cookie + ';'
		index = allCookies.indexOf '; '+ escape(name) + '='
		if index != -1
			index += name.length + 3
			value = allCookies.slice(index, allCookies.indexOf(';', index))
			unescape value

	$.fn.saveState = ->
		element = this

		name = element.prop("id");
		isChecked = "true" == getCookie name
		$(element).parent().parent().addClass("gn-menu-hidden") if !isChecked
		element.prop('checked', isChecked)

		element.click ->
			name = element.prop("id");
			isChecked = element.prop('checked')
			$(element).parent().parent().toggleClass("gn-menu-hidden")
			createCookie(name, isChecked)

	return