$(function() {
	$('#colorpicker-showColor').click(function() {
		$('#colorpicker').toggle('slide', {
			direction: 'left'
		});
	});
	$('#colorpicker').farbtastic(function(color) {
		$('input[name=color]').attr('value', color);
		$('#colorpicker-showColor').attr('style', 'background-color:' + color);
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
	if (!($('input[name=numberOfResults]').attr('value') > 0)) {
		alert('Field number of items must contains value greater than zero.');
		return false;
	}
	return true;
}
