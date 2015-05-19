var Activity = require('./app.models.Activity.js');

Polling.prototype = new Activity();
Polling.prototype.constructor = Polling;

function Polling(room) {
	Activity.call(this);
	this.room = room;
	this.participates;
	this.usersVoted = 0;
	this.ideas;
	this.inAct;
	this.numUsers;
	this.activityEnding;
	this.breadCrumbs;
};

// Sets up the basic parameters for polling
Polling.prototype.setParameters = function (params) {
	params.ideas = {};

	params.inAct = params.users;
	params.numUsers = params.users.length;

	params.activityEnding = false;

	params.breadCrumbs = [
		{
			'title' : 'Voting',
			'help'  : 'During this phase select the option that you would like to choose.',
		},
		{
			'title' : 'Results',
			'help'  : 'During this phase you will see the results of the poll.'
		}
	];

	this.state = params;

};

// Begins the Polling activity
Polling.prototype.startActivity = function (room) {
	this.io.to(room).emit('start:polling', this.state.room, this.state);
};

// Adds ideas to the array so that they can be voted on later on.
Polling.prototype.addIdea = function(room, idea) {
		this.state.ideas[idea] = {
		'votes'    : 0,
	}
};

Polling.prototype.readyUp = function() {
	this.usersVoted+= 1;
};

Polling.prototype.resetReadyUsers = function() {
	this.usersVoted = 0;
};

// END the activity
Polling.prototype.end = function () {
	this.io.to(this.state.room).emit('end:activity'); // Notify layout manager to unmount activity
};

Polling.prototype.getPollingFormattedHTML = function (results) {
	console.log("Formatting polling results", results);

	var sorted = results['results'].sort (function (val1, val2) {
        return val1[2] < val2[2];
	});
	
	var returnHTML = '<div style="background-color: #2b3e50; color: white; padding: 10px;">';
	returnHTML += '<h1 style="background-color: #4e5d6c; height: 50px; padding: 10px;">vMOF - Polling</h1>';

	returnHTML += '<h2 style="background-color: #df691a; padding: 10px; height: 40px;">Polling Topic: ' + results['question'] + '</h2>'
	
	returnHTML += '<h2>Polling Results</h2> <hr/>';
	
	for (var i = 0; i < sorted.length; i++) {
		returnHTML += '<h3 style="background-color: #4e5d6c; height: 30px; padding: 10px;"> Vote Percentage: ';
		returnHTML += '<span style="color: ' + sorted[i][3] + '">' + sorted[i][2] + '%</span><br/>'; 
		returnHTML += (sorted[i][0] + '</h3>');
	}
	
	returnHTML += '</div>';

	return returnHTML;
}

module.exports = Polling;