var Activity = require('./app.models.Activity.js');
var EmpathyMap = require('./app.models.EmpathyMap.js');

ActivityEmpathyMap.prototype = new Activity();
ActivityEmpathyMap.prototype.constructor = ActivityEmpathyMap;

function ActivityEmpathyMap() {
	Activity.call(this);
	//this.usersReady = 0;
	this.empathyMap = new EmpathyMap();
	this.commentary;
	// this.commentary = { think: [],
	// 					hear: [],
	// 					see: [],
	// 					say: [],
	// 					pain: [],
	// 					gain: [] };
};

ActivityEmpathyMap.prototype.submitIdea = function (idea, section, user) {
	console.log('empathy map submit idea:', idea, section, user);
	this.empathyMap.addIdea(idea, section, user);
};

ActivityEmpathyMap.prototype.addCommentary = function (commentaryJSON) {
	console.log('commentaryJSON***', commentaryJSON);
	//{think: [{content, comments, votes, section}]}
	if (this.commentary == undefined) {
		this.commentary = commentaryJSON;
	} else {
		console.log('commentary is: ', this.commentary);
		console.log('commentary2 is: ', JSON.stringify(this.commentary));
		var self = this;
		commentaryJSON['think'].forEach(function (postJSON, i) {
			self.commentary['think'][i].comments = self.commentary['think'][i].comments.concat(postJSON.comments);
			self.commentary['think'][i].votes += postJSON.votes;
			//self.commentary['think'][i].user = postJSON.votes;
		});
		commentaryJSON['hear'].forEach(function (postJSON, i) {
			self.commentary['hear'][i].comments = self.commentary['hear'][i].comments.concat(postJSON.comments);
			self.commentary['hear'][i].votes += postJSON.votes;
		});
		commentaryJSON['see'].forEach(function (postJSON, i) {
			self.commentary['see'][i].comments = self.commentary['see'][i].comments.concat(postJSON.comments);
			self.commentary['see'][i].votes += postJSON.votes;
		});
		commentaryJSON['say'].forEach(function (postJSON, i) {
			self.commentary['say'][i].comments = self.commentary['say'][i].comments.concat(postJSON.comments);
			self.commentary['say'][i].votes += postJSON.votes;
		});
		commentaryJSON['pain'].forEach(function (postJSON, i) {
			self.commentary['pain'][i].comments = self.commentary['pain'][i].comments.concat(postJSON.comments);
			self.commentary['pain'][i].votes += postJSON.votes;
		});
		commentaryJSON['gain'].forEach(function (postJSON, i) {
			self.commentary['gain'][i].comments = self.commentary['gain'][i].comments.concat(postJSON.comments);
			self.commentary['gain'][i].votes += postJSON.votes;
		});
	}
	console.log('empathy map add commentary: ', this.commentary);
};

ActivityEmpathyMap.prototype.sortCommentaryByVotes = function () {

	this.commentary['think'].sort(function (post1, post2) {
		return post1.votes < post2.votes;
	});

	this.commentary['hear'].sort(function (post1, post2) {
		return post1.votes < post2.votes;
	});

	this.commentary['see'].sort(function (post1, post2) {
		return post1.votes < post2.votes;
	});

	this.commentary['say'].sort(function (post1, post2) {
		return post1.votes < post2.votes;
	});

	this.commentary['pain'].sort(function (post1, post2) {
		return post1.votes < post2.votes;
	});

	this.commentary['gain'].sort(function (post1, post2) {
		return post1.votes < post2.votes;
	});

};

ActivityEmpathyMap.prototype.getCommentary = function () {
	return this.commentary;
}

// ActivityEmpathyMap.prototype.addCommentary = function (commentary) {
// 	//{think: [{content, comments, votes, section}]}
// 	this.commentary.addCommentary(commentary);

// };

// ActivityEmpathyMap.prototype.readyUp = function (userID) {
// 	this.usersReady++;
// 	console.log('empathy map users ready:', this.usersReady);
// 	if (this.usersReady >= this.state.users.length - 1) {
// 		this.io.to(this.state.room).emit('empathy:map:next:phase', this.empathyMap.toPosts().toJSON());
// 		//this.io.to(this.state.room).emit('empathy:map:next:phase', this.posts);
// 	} else {
// 		this.io.to(userID).emit('empathy:map:wait');
// 	}
// };

module.exports = ActivityEmpathyMap;