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
//		$(this).attr('disabled', true);
		$(this).attr('value', 'Loading...');
		$('<div id="ajax-spinner">We\'re processing your request. <br /> Images are being loaded from Flickr.</div>').appendTo("body").css({
			position: "fixed",
			left: "40%",
			top: "50%"
		});
	});

});
