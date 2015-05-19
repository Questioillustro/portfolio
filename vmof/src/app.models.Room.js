function Room(title) {
	this.title = title;
	this.users = [];
	this.facOnline = false;
	this.activity;
	this.status = 'Facilitator is idle';
};

Room.prototype.setTitle = function (title) {
	this.title = title;
};

Room.prototype.getTitle = function () {
	return this.title;
};

Room.prototype.addUser = function (user) {
	this.users.push(user);
};

Room.prototype.setFacOnline = function (status) {
	this.facOnline = status;
};

Room.prototype.getFacOnline = function () {
	return this.facOnline;
};

Room.prototype.removeUser = function (user) {
	if (user.name === 'Facilitator') {
		this.setFacOnline(false);
		return;
	}

	var index = this.users.map(function (usr) {
		return usr.id;
	}).indexOf(user.id);

	this.users.splice(index, 1);

	// Remove them from any current activity
	var activity = this.getActivity();
	if (activity !== undefined)
		activity.leaveActivity(user);
};

Room.prototype.getUsers = function () {
	return this.users;
};

Room.prototype.getNumberOfUsers = function () {
	return this.users.length;
};

Room.prototype.isEmpty = function () {
	return this.users.length > 0 ? false : true;
};

Room.prototype.setActivity = function (activity) {
	this.activity = activity;
};

Room.prototype.getActivity = function () {
	return this.activity;
};

Room.prototype.setUsersInActivity = function (inActivity) {
	for (var i = 0; i < this.users.length; i ++) {
		this.users[i].setInActivity(inActivity);
	}
};

Room.prototype.setStatus = function (status) {
	this.status = status;
};

Room.prototype.getStatus = function () {
	return this.status;
};

module.exports = Room;
