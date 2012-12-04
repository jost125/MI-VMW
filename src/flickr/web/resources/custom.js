$(function() {
	$('#colorpicker-showColor').click(function() {
		$('#colorpicker').toggle('slide', {
			direction: 'left'
		});
	});
	$('#colorpicker').each(function() {
		$(this).farbtastic(function(color) {
			$('input[name=color]').attr('value', color);
			$('#colorpicker-showColor').attr('style', 'background-color:' + color);
		});
	});

	$('input[name=submit]').click(function() {
		if (isValid()) {
	//		$(this).attr('disabled', true);
			$(this).attr('value', 'Loading...');
			$('#ajax-spinner').appendTo("body").css({
				position: "fixed",
				left: "40%",
				top: "50%"
			}).show();
		} else {
			return false;
		}
	});
});

function isValid() {
	if ($('input[name=term]').attr('value') == "") {
		alert('Field keyword must be filled in.');
		return false;
	}
	if (!(($('input[name=numberOfResults]').attr('value') > 0) && ($('input[name=numberOfResults]').attr('value') <= 1500))) {
		alert('Field number of items must contains value between 1 and 1500.');
		return false;
	}
	return true;
}

$(function() {
	$('.results img').load(function() {
		$(this).fadeTo(2000, 1);
	});
	$('.results a').lightBox()
});
