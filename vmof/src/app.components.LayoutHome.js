$(document).ready(function() {

	var fullHeightMinusHeader;

	function calcHeights() {		
		fullHeightMinusHeader = $(window).height() - $(".vf-navbar").outerHeight();
		$("#vf-content").height(fullHeightMinusHeader);
	}
	
	// run on page load
	calcHeights();
	
	// run on window resize event
	$(window).resize(function() {
		calcHeights();
	});
	
});