jQuery.expr[':'].Contains = (a, i, m) ->
    (a.textContent || a.innerText || "").toUpperCase().indexOf(m[3].toUpperCase()) >= 0

filter = (filter) ->
 li_tags = $('#cbp-vm ul li')
 to_filter_li_tags = li_tags.find('.title:Contains(' + filter + ')').parent()
 to_unfilter_li_tags = li_tags.not(to_filter_li_tags)
 to_unfilter_li_tags.hide()
 to_filter_li_tags.show()

showProgress =-> $('#circularG').show()
hideProgress =-> $('#circularG').hide()

$(window).load ->
 $('#circularG').hide()

 $("input[type='search']").
  change ->
   showProgress()

   text = $(this).val()

   if text
    filter text
   else
    $('#cbp-vm ul li').show()

   hideProgress()