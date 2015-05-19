var EmpathyMap = require('./app.models.EmpathyMap');
//var PostEmpathyMap = require('./app.models.PostEmpathyMap');
//var Posts = require('./app.models.Posts');

var EmpathyMapActivity = function (io) {
	this.state = {};
	this.io = io;
	this.interval;
	this.phase = 0;
	//this.posts = new Posts();
	this.usersReady = 0;
	this.empathyMap = new EmpathyMap();
};

var NUM_PHASES = 6;
//var NUM_PHASES = 7;

EmpathyMapActivity.prototype.setParameters = function (params) {
	this.state = params;
	console.log("Empathy map params: " + params);
};

EmpathyMapActivity.prototype.start = function (room) {
	console.log("empathy map starting in room", room);
	this.io.to(room).emit('empathy:map:start', this.state);
	this.state.room = room;
	//this.phase = 0;
	console.log('em start room: ', this.state.room);
	//var io = this.io;
	//var self = this;
	//this.interval = setInterval(function () {
		//console.log('10s passed');
	//	console.log('em start room interval: ', room);
	//	if (self.phase >= NUM_PHASES-1) {
	//		clearInterval(self.interval);
	//		console.log('posts:', self.posts);
	//		console.log('posts2:', JSON.stringify(self.posts));
	//		self.posts.prnt();
	//		self.io.to(room).emit('empathy:map:next:phase', self.posts);
	//	} /*else if (self.phase >= NUM_PHASES) {
	//		console.log('posts:', self.posts);
	//		console.log('posts2:', JSON.stringify(self.posts));
	//		self.io.to(room).emit('empathy:map:next:phase', self.posts);
	//	}*/ else {
	//		self.phase += 1;
	//		self.io.to(room).emit('empathy:map:next:phase');
	//	}
	//}, this.state['comment_time'] * 1000);
};

EmpathyMapActivity.prototype.submitIdea = function (idea, section) {
	console.log('empathy map state:', JSON.stringify(this.state));
	this.empathyMap.addIdea(idea, section);
	//this.posts.add(new PostEmpathyMap(idea, section));
	//this.io.to(this.state.room).emit('empathy:map:submit:idea', this.phase, idea);
	this.io.to(this.state.room).emit('empathy:map:submit:idea', section, idea);
};

EmpathyMapActivity.prototype.readyUp = function (userID) {
	this.usersReady++;
	console.log('empathy map users ready:', this.usersReady);
	if (this.usersReady >= this.state.users.length - 1) {
		this.io.to(this.state.room).emit('empathy:map:next:phase', this.empathyMap.toPosts().toJSON());
		//this.io.to(this.state.room).emit('empathy:map:next:phase', this.posts);
	} else {
		this.io.to(userID).emit('empathy:map:wait');
	}
};

module.exports = EmpathyMapActivity;