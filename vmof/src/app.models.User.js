var Room = require('./app.models.Room');

// var User = function(id, name, email, room) {
function User(id, name, email) {
	this.id = id;
	this.name = name;
	this.email = email;
	this.room;
	this.ready = false;
	this.color;
	this.inActivity = false;
}

User.prototype.toJSON = function () {
	return { id: this.id,
		     name: this.name,
		     email: this.email,
		     room: this.room,
		     ready: this.ready, 
		     color: this.color,
		     inActivity: this.inActivity };
};

User.prototype.joinRoom = function (room, socket) {
	socket.join(room);
	
	if (!rooms.getRoom(room)) {
		rooms.add(room);
	}

	this.room = room;
	
	if (this.name !== 'Facilitator')
		rooms.getRoom(room).addUser(this);
	else
		rooms.getRoom(room).setFacOnline(true);
};

User.prototype.getRoom = function() {
	return rooms.getRoom(this.room);
};

User.prototype.getRoomStr = function() {
	return this.room;
};

User.prototype.getActivity = function () {
	return this.getRoom().getActivity();
};

User.prototype.readyUp = function () {
	var activity = this.getActivity();
	this.ready = true;
	activity.setUserReady(this);
	console.log('empathy map users ready:', JSON.stringify(activity.getUsersReady()));
	if (activity.everyoneReady()) {
		activity.clearUsersReady();
		return true;
	}
	return false;
};

User.prototype.setInActivity = function (inActivity) {
	this.inActivity = inActivity;
};

User.prototype.unReady = function () {
	this.ready = false;
};

User.prototype.isReady = function () {
	return this.ready;
};

module.exports = User;
