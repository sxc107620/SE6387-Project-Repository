$('#colorpalette1').colorPalette().on('selectColor', function(e) {
	$('#selected-color').val(e.color);
});