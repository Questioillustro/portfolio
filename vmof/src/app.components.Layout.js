$(document).ready(function() {

	var fullHeightMinusHeader, sideScrollHeight = 0;

	function calcHeights() {		
		fullHeightMinusHeader = $(window).height() - $(".vf-navbar").outerHeight();
		$(".main-content, .sidebar-two, .sidebar-one").height(fullHeightMinusHeader);
		
		sideScrollHeight = fullHeightMinusHeader - $(".nav-menu").outerHeight() - $(".buttons").outerHeight();
		$(".side-scroll").height(sideScrollHeight);

		usersHeight = fullHeightMinusHeader - $(".room").outerHeight();
		$(".users").height(usersHeight);
	}
	
	// run on page load
	calcHeights();
	
	// run on window resize event
	$(window).resize(function() {
		calcHeights();
	});
	
});