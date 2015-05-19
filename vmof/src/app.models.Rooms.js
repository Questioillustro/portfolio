Room = require('./app.models.Room');

function Rooms() {
	this.rooms = {};
	this.roomList = [
			{id: 'Room 1500', title: 'Room 1500'},
			{id: 'Room 1510', title: 'Room 1510'},
			{id: 'Room 1520', title: 'Room 1520'},
			{id: 'Room 1530', title: 'Room 1530'},
			{id: 'Room 1540', title: 'Room 1540'},
			{id: 'Room 1550', title: 'Room 1550'},
			{id: 'Room 1560', title: 'Room 1560'},
			{id: 'Room 1570', title: 'Room 1570'},
			{id: 'Room 1580', title: 'Room 1580'},
			{id: 'Room 1590', title: 'Room 1590'}
		];
	this.lobby = [];
};

Rooms.prototype.getRoom = function (room) {
	if (this.rooms[room])
		return this.rooms[room];
	else
		return undefined;
};

Rooms.prototype.getRoomList = function () {	
	return this.roomList;
};

Rooms.prototype.addToLobby = function (socketId) {
	this.lobby.push(socketId);
};

Rooms.prototype.removeFromLobby = function (socketId) {
	var delIndex = this.lobby.indexOf(socketId);
	this.lobby.splice(delIndex, 1);
};

Rooms.prototype.getLobby = function () {
	return this.lobby;
};

Rooms.prototype.add = function (room) {
	this.rooms[room] = new Room();
};

Rooms.prototype.setTitle = function (room, title) {
	this.getRoom(room).setTitle(title);

	for(var i = 0; i < this.roomList.length; i++) {
		if (this.roomList[i].id === room) {
			if (title === '')
				this.roomList[i].title = this.roomList[i].id;
			else
				this.roomList[i].title = this.roomList[i].id + ' - ' + title;
		}
	}
};

Rooms.prototype.remove = function (room) {
	
};

module.exports = Rooms;
