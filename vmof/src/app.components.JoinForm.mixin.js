var JoinFormMixin = {

	roomChangeHandler: function(value){
		console.log(value);
		this.setState({ room: document.getElementById("room-input").value });
	},

	shouldComponentUpdate: function(nextProps, nextState) {
  		return false;
	},

	getDefaultOptions: function () {
		var options = [
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

		return options;
	},

	updateRoomList: function (roomList) {
		this.setState({ options: roomList });
		this.forceUpdate();
	},

};

module.exports = JoinFormMixin;