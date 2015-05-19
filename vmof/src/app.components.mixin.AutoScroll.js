var AutoScroll = {
	
	initializeAutoScroll: function () {
		// Auto scroll inputs to the top
		$('input').focus( function (evt) {
			$('html, body').animate({
		    	scrollTop: $(evt.target).offset().top
		    }, 1000);    
		});
	}
}

module.exports = AutoScroll;