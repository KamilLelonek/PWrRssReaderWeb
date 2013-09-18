jQuery.expr[':'].Contains = (a, i, m) ->
    (a.textContent || a.innerText || "").toUpperCase().indexOf(m[3].toUpperCase()) >= 0

filter = (filter) ->
 li_tags = $('#cbp-vm ul li')
 to_filter_li_tags = li_tags.find('.title:Contains(' + filter + ')').parent()
 to_unfilter_li_tags = li_tags.not(to_filter_li_tags)
 hide(to_unfilter_li_tags)
 show(to_filter_li_tags)

show = (elements) -> elements.each ->
  	     $(this).removeClass("hidden")
hide = (elements) -> elements.each ->
	       $(this).addClass("hidden")

$(window).load ->
 $("input[type='search']").
  change ->
   text = $(this).val()

   if text
    filter text
   else
    show($('#cbp-vm ul li'))