var HelpMixin = {

	runTutorial: function () {

	},

	showHelp: function (event) {
		console.log("Showing help", event)
		$(event.target).popover('show')
	},

	hideHelp: function (event) {
		console.log("Showing help", event)
	 	$(event.target).popover('hide')	
	},

};

module.exports = HelpMixin;