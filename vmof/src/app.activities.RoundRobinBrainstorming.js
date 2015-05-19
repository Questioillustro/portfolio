var  RoundRobinBrainstorming = function (io) {
	this.state = {};
	this.io = io;
}

RoundRobinBrainstorming.prototype.setParameters = function (params) {
	this.state = params;
	console.log("round robin brainstorming params: " + params);
};

RoundRobinBrainstorming.prototype.start = function (room) {
	console.log("round robin brainstorming starting in room", room);
	this.io.to(room).emit('start:round:robin:brainstorming', this.state);
	this.state.room = room;
}

RoundRobinBrainstorming.prototype.submitIdea = function (idea) {
	console.log('rr state:', JSON.stringify(this.state));
}

module.exports = RoundRobinBrainstorming;