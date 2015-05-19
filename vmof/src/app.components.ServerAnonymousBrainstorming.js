var Activity = require('./app.models.Activity.js');

AnonymousBrainstorming.prototype = new Activity();
AnonymousBrainstorming.prototype.constructor = AnonymousBrainstorming;


function AnonymousBrainstorming (io) {
	this.state = {};
	this.io = io;
	this.image = '#';
}

AnonymousBrainstorming.prototype.setParameters = function (params) {
	var now = new Date();
	params.start_time = now;
	params.ideas = {};
	params.commentIdeas = [];

	params.phase = 0;
	params.usersReady = 0;
	params.numUsers = params.users.length;
	params.time_remaining = parseInt(params.brainstorm_limit) * 60;

	params.renderDiscussion = false; // Sets up the module for discussion
	params.renderCommentary = false; // Renders the commenting UI elements
	params.renderVoting = true; // Renders the voting buttons
	params.renderBrainstorming = true; // Renders the idea input during brainstorming
	params.activityEnding = false;
	params.actionItems = [];

	// Populates the BreadCrumbs Widget
	// Index of the breadcrumb should match the phase of the activity
	params.breadCrumbs = [
		{
			'title' : 'Brainstorming',
			'help'  : 'In the first phase of the activity you will enter ideas for the chosen topic in the spaces provided.',
		},
		{
			'title' : 'Commentary',
			'help'  : 'During commentary you can comment and vote on the ideas generated by the other participants.'
		},
		{
			'title' : 'Discussion',
			'help'  : 'During discussion you will be presented the ideas in order of rank to discuss and add action items as needed.'
		}
	];

	this.state = params;
	//console.log("Anonymous Brainstorming set", params);
};

AnonymousBrainstorming.prototype.startActivity = function (room) {
	console.log("Anonymous brainstorming starting in room", room);
	this.io.to(room).emit('start:anonymous:brainstorming', this.state);
	this.io.to(room).emit('set:anonymous:userlist', true);
	this.state.room = room;

	// Only send out the image when activity starts but save for emailing
	this.image = this.state.image;
	delete this.state.image;
}

// Move to the next Phase of the activity and set the new state variable values
AnonymousBrainstorming.prototype.nextPhase = function () {
	console.log(this.state.room, "moving to next phase", this.state.phase);
	this.state.phase += 1;
		
	var now = new Date();
	this.state.start_time = now;
	
	switch (this.state.phase) {
		case 1: // Commentary Phase
			this.state.renderBrainstorming = false;
			this.state.renderCommentary = true;
			this.state.cmd = "brainstorming:complete";
			this.state.time_remaining = parseInt(this.state.commentary_limit) * 60;
			break;
		case 2: // Discussion Phase
			this.state.time_remaining = parseInt(this.state.discussion_limit) * 60;
			this.state.cmd = "commentary:complete";
			this.state.displayIndex = 0;
			this.state.renderVoting = false;
			this.state.renderCommentary = false;
			this.state.renderDiscussion = true;
			break;
		case 3: // End activity
			this.state.activityEnding = true; // Notify the front end to clean up UI and end
			break;
	}

	this.sendUpdate(); // Send out state through socket
	this.state.cmd = "";
}

AnonymousBrainstorming.prototype.nextIdea = function () {
	this.state.displayIndex++;
	
	if (this.state.displayIndex >= Object.keys(this.state.ideas).length)
		this.state.activityEnding = true;
	
	this.sendUpdate();
}

AnonymousBrainstorming.prototype.next = function () {
	this.state.usersReady = 0;

	if (this.state.phase == 2)
		this.nextIdea();
	else
		this.nextPhase();

	this.io.to(this.state.room).emit('update:users:ready', this.state.usersReady);
}

AnonymousBrainstorming.prototype.addIdea = function (idea) {
	console.log(this.state.room, "idea added:", idea);
	this.state.ideas = this.state.ideas || {};
	this.state.ideas[idea] = {
		'comments' : [],
		'votes'    : 0,
		'actionItems' : []
	}
}

AnonymousBrainstorming.prototype.addIdeas = function (ideas) {
	for (var i = 0; i < ideas.length; i++) {
		if (ideas[i].trim() !== '') // Second layer of protection against empty ideas
			this.addIdea(ideas[i]);
	}
	this.sendUpdate();
}

// Handle COMMENTS coming from the participants
AnonymousBrainstorming.prototype.addComment = function (data) {
	if (data.comment.trim() === '')
		return

	console.log(this.state.room, "comment added:", data);
	this.state.ideas[data.idea].comments.push(data.comment);
	this.sendUpdate();
}

// Handle VOTES coming from the participants
AnonymousBrainstorming.prototype.addVote = function (data) {
	console.log(this.state.room, "vote cast:", data);
	this.state.ideas[data.idea].votes += parseInt(data.vote)
	this.sendUpdate();
}

AnonymousBrainstorming.prototype.addActionItem = function (data) {
	console.log(this.state.room, "action item added", data);
	this.state.ideas[data.idea].actionItems.push(data.actionItem);
	this.io.to(data.actionItem.userObj.id).emit("receiveMessage", "An action item has been assigned to you.");
	this.state.actionItems.push(data);
	this.sendUpdate();
}

AnonymousBrainstorming.prototype.getActionItems = function (data) {
	return this.state.actionItems;
}

// A User is ready - called when sendAck is called by participant
AnonymousBrainstorming.prototype.userReady = function () {
	console.log(this.state.room, "a user is ready");
	this.state.usersReady++;

	// Handle all users ready transitions
	if (this.state.usersReady === this.state.numUsers)
		this.next();

	this.io.to(this.state.room).emit('update:users:ready', this.state.usersReady);
}

// Broadcasts the state of the activity to everyone in the room
AnonymousBrainstorming.prototype.sendUpdate = function () {
	//console.log("State update", this.state);
	this.io.to(this.state.room).emit('receive:data', this.state);
}

// END the activity
AnonymousBrainstorming.prototype.end = function () {
	console.log(this.state.room, "ending activity");
	this.io.to(this.state.room).emit('end:activity'); // Notify layout manager to unmount activity
	this.io.to(this.state.room).emit('set:anonymous:userlist', false);
	this.io.to(this.state.room).emit('update:users:ready', 0);
}

// Return results for mailing/storage
AnonymousBrainstorming.prototype.getJSONResults = function () {
	return this.state.ideas;
}

// Sort the ideas
AnonymousBrainstorming.prototype.sortIdeas = function () {
	var ideas = Object.keys(this.state.ideas),
		ideaMap = this.state.ideas;
      
    var sorted = ideas.sort (function (val1, val2) {
        val1 = parseInt(ideaMap[val1]['votes']);
        val2 = parseInt(ideaMap[val2]['votes']);
        return val2-val1;
    });

    return sorted;
}

AnonymousBrainstorming.prototype.getImage = function () {
	return this.image || "";
}

AnonymousBrainstorming.prototype.getActionItemHTML = function (actionItem) {
	var returnHTML = '<div style="background-color: #2b3e50; color: white; padding: 10px;">';
	returnHTML += '<h1 style="background-color: #4e5d6c; height: 50px; padding: 10px;">vMOF - Anonymous Brainstorming</h1>';

	returnHTML += '<h2>Brainstormed Idea</h2> <hr/>';
	returnHTML += '<h3 style="background-color: #4e5d6c; height: 30px; padding: 10px;">' + actionItem.idea + '</h3>'
	returnHTML += '<h2>Assignment</h2> <hr/>';
	returnHTML += '<h3 style="background-color: #4e5d6c; height: 30px; padding: 10px;">' + actionItem.actionItem.text + '</h3>';

	returnHTML += '</div>';

	return returnHTML;
}

AnonymousBrainstorming.prototype.getFormattedResults = function () {
	var returnHTML = '<div style="background-color: #2b3e50; color: white; padding: 10px;">';
	
	returnHTML += '<h1 style="background-color: #4e5d6c; height: 50px; padding: 10px;">vMOF - Anonymous Brainstorming</h1>';

	if (this.image !== '#') {
		returnHTML += '<h2 style="background-color: #df691a; padding: 10px; height: 40px;">Image</h2>'
		returnHTML += '<img src="cid:imageTopic@kreata.ee" width="200px"/>';
	}

	// Add in the topic question
	if (this.state.question && this.state.question.trim() !== '')
		returnHTML += '<div><h2 style="background-color: #5cb85c; height: 40px; padding: 10px;">Topic: '+this.state.question+'</h2></div>';
	
	// Add in the ideas and comments
	var sortedIdeas = this.sortIdeas();

	returnHTML += '<h2 style="">Ideas Generated</h2><hr/>';

	for (var i = 0; i < sortedIdeas.length; i++) {
		var idea = sortedIdeas[i],
			comments = this.state.ideas[idea].comments,
			actionItems = this.state.ideas[idea].actionItems,
			votes = this.state.ideas[idea].votes;

		returnHTML += '<div>';
		returnHTML += '<h3 style="background-color: #4e5d6c; height: 30px; padding: 10px;">' 

		if (votes >= 0)
			returnHTML += '<span style="color: #5cb85c">' + votes + '</span>' 
		else
			returnHTML += '<span style="color: #d9534f">' + votes + '</span>' 

		returnHTML += ' - ' + idea + '</h3>';
		returnHTML += '</div>';
		
		// Add comments
		returnHTML += '<ul>'
		for (var j = 0; j < comments.length; j++)
			returnHTML += '<li>'+comments[j]+'</li>';
		returnHTML += '</ul>'

		// Add Action Items
		if (actionItems.length > 0)
			returnHTML += '<h4>Action Items</h4>';

		returnHTML += '<ul>'
		for (var j = 0; j < actionItems.length; j++) 
			returnHTML += '<li>'+actionItems[j].text+'<br/>Assigned to: '+actionItems[j].assignedTo+'</li>'
		returnHTML += '</ul>'
	}

	returnHTML += '</div>';

	return returnHTML;
}

module.exports = AnonymousBrainstorming;