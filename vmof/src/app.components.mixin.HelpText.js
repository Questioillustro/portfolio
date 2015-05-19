
var HelpMixin = {

    toggleHelp: function () {
        var help = !this.state.showHelp;

        if (help)
            this.turnOnHelp();
        else
            this.turnOffHelp();

        this.setState({ showHelp: help });
    },

    turnOnHelp: function () {
    	var self = this;

    	$('#help-toggle').css({'color':'green'});
        $('#help-status').html('Help on');

		// Bind mouseover events
        $('[data-toggle="popover"]').mouseover(function (event) {
            self.showHelp(event);
        }).mouseleave(function (event) {
            self.hideHelp(event);
        });
    },

    turnOffHelp: function () {
		$('#help-toggle').css({'color':'red'});
        $('#help-status').html('Help off');

        // Unbind the mouseover events
        $('[data-toggle="popover"]').popover('hide').unbind('mouseover').unbind('mouseleave');
    },

    // Shows help text for the Brainstorming phase of Brainstorming
	runTutorial: function (helpTextList) {
		// Unbind the mouseover events durint tutorial
        $('[data-toggle="popover"]').not('#help-toggle').popover('hide').unbind('mouseover').unbind('mouseleave');
        $('#help-toggle').popover('hide');

        // Clear any existing interval
        clearInterval(this.state.helpInterval);

		var displayIndex = 1,
			self = this;

		// Show first help text element
		helpTextList[0].popover('show');

		// Display the help text for the jQuery items in helpTe
		var interval = setInterval(function () {
			displayIndex = self.showNext(helpTextList, displayIndex, interval);
		}, 5000);

		this.setState({helpInterval: interval});
	},

	showNext: function (helpTextList, index, interval) {
		if ( !this.state.showHelp ) {
			clearInterval(interval);

			if (index > 0)
				helpTextList[index].popover('hide');

			return 0;
		}

		if (index < helpTextList.length)
			helpTextList[index].popover('show');
		
		if (index > 0) 
			helpTextList[index-1].popover('hide');
		
		index++;
		if (index > helpTextList.length) {
			clearInterval(interval);
			this.turnOnHelp();
		}

		return index;
	},

	showHelp: function (event) {
		$(event.target).popover('show');
	},

	hideHelp: function (event) {
		$(event.target).popover('hide');
	},

	togglePopover: function (event) {
	    $tar = $(event.target);

	    ret = $tar.closest('div').find('div.popover').detach();
	    if (ret.length == 0)
	        $tar.popover('show');
    },

};

module.exports = HelpMixin;