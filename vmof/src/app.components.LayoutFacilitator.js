// $(document).ready(function() {

	var fullHeightMinusHeader, sideScrollHeight = 0;

	function calcHeights() {		
		fullHeightMinusHeader = $(window).height() - $(".vf-navbar").outerHeight();
		$(".main-content, .sidebar-two, .sidebar-one").height(fullHeightMinusHeader);
		
		sideScrollHeight = fullHeightMinusHeader - $(".nav-menu").outerHeight() - $(".buttons").outerHeight();
		$(".side-scroll").height(sideScrollHeight);

		usersHeight = fullHeightMinusHeader - $(".room").outerHeight();
		$(".users").height(usersHeight);
	}
	
	// run on page load
	// calcHeights();
	
	// run on window resize event
	$(window).resize(function() {
		calcHeights();
	});

	module.exports = calcHeights;
	
//});


// var Rooms = function() {
// 	this.rooms = {};
// }

// Rooms.prototype.get = function (room) {
// 	return this.rooms[room];
// }

// Rooms.prototype.join = function (room, user) {
// 	this.rooms[room] = this.get(room) || [];
// 	this.get(room).push(user);
// }

// Rooms.prototype.leave = function (room, user) {
// 	var index = this.get(room).indexOf(user);
// 	if(index > -1) {
// 		this.get(room).splice(index, 1);
// 	}
// 	if(this.isEmpty(room)) {
// 		this.remove(room);
// 	}
// }

// Rooms.prototype.isEmpty = function (room) {
// 	return this.get(room).length > 0 ? false : true;
// }

// Rooms.prototype.remove = function (room) {
// 	delete this.rooms[room];
// }

// module.exports = Rooms;