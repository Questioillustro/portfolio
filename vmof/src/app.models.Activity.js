//Room = require('./app.models.Room');

function Activity() {
	this.status;
	this.timeRemaining;
	this.usersReady = [];
	this.usersInActiviy = [];
};

Activity.prototype.addUserToActivity = function (user) {
	this.usersInActiviy.push(user);
};

Activity.prototype.getStatus = function () {
	return this.status;
};

Activity.prototype.setStatus = function (status) {
	this.status = status;
};

Activity.prototype.getUsers = function () {
	return this.usersInActiviy;
};

Activity.prototype.usersToJSON = function () {
	return this.usersInActiviy.map(function (user) {
		return user.toJSON();
	});
};

Activity.prototype.getUsersReady = function () {
	return this.usersReady;
};

Activity.prototype.everyoneReady = function () {
	var ready = true;
	for(var i = 0; i < this.usersInActiviy.length; i++){
		if(this.usersInActiviy[i].isReady() == false){
			ready = false;
		}
	}
	console.log("We are ready to move on? " + ready);
	return ready;
};

Activity.prototype.setUserReady = function (user) {
	return this.usersReady.push(user);
};

Activity.prototype.clearUsersReady = function (user) {
	for(var i = 0; i < this.usersInActiviy.length; i++){
		this.usersInActiviy[i].unReady();
	}
	return this.usersReady = [];
};

Activity.prototype.leaveActivity = function (user) {
	var position = this.usersInActiviy.indexOf(user);

	if ( ~position ) this.usersInActiviy.splice(position, 1);
};

module.exports = Activity;