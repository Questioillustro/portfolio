$("#menu-toggle").click(function(e) {
	console.log('in toggle menu');
    e.preventDefault();
    $("#wrapper").toggleClass("toggled");
});