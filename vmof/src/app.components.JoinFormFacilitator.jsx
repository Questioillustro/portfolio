require('./app.components.JoinForm.styl');
var React = require('react/addons');
var io = require('alias_socket_io');
var LayoutFacilitator = require('./app.components.LayoutFacilitator.jsx');
var UserList = require('./app.components.UserList.jsx');
// var RoomFacilitator = require('./app.components.RoomFacilitator.jsx');
var ActivitiesMenu = require('./app.components.ActivitiesMenu.jsx');
var ReactAutocomplete = require('alias_react_autocomplete');
var JoinFormMixin = require('./app.components.JoinForm.mixin.js');

var socket = io.connect();

var JoinFormFacilitator = React.createClass({

	mixins: [JoinFormMixin],

	componentDidMount: function () {
		socket.on('user:join:room', this.updateView);
		socket.on('get:room:list', this.updateRoomList);
		socket.emit('request:room:list');
	},

	componentWillUnmount: function () {
		socket.removeAllListeners('user:join:room');
		socket.removeAllListeners('get:room:list');
	},

	getInitialState: function() {
		var options = this.getDefaultOptions();

		return { room: options[0].id, options : options };
	},

	joinRoom: function() {
		var room = this.state.room,
			title = $('#vf-room-title').val();

		socket.emit('user:join:room', {name: "Facilitator", room: room, title: title});
	},

	updateView: function() {
		React.unmountComponentAtNode(document.body);
		React.render(<LayoutFacilitator room={this.state.room} />, document.body);
		React.render(<UserList room={this.state.room} />, document.getElementById('vf-user-list'));
	},

	render: function() {

		var renderRoomOptions = function (room) {
			return <option value={room.id}>{room.title}</option>
		}

		return(
			<div className="col-md-4">
				<div className='vf-welcome'>
					Welcome to vMOF, The Virtual Meeting Outcome Facilitator! To begin please enter the room you are hosting the meeting in and a title for the meeting if desired.
				</div>
				
				<div className="vf-well vf-item">
					<label className="control-label" htmlFor="room-input">Join Room</label>
					<div className="form-group has-error">
						<select id="room-input" className="form-control" onChange={this.roomChangeHandler} >
							{this.state.options.map(renderRoomOptions)}
						</select>
					</div>
					
					<div className="form-group">
						<label className="control-label" htmlFor="vf-room-title">Room Title (optional)</label>
						<input id="vf-room-title" type="text" className="form-control" placeholder="Enter an optional room title..." maxLength="16" />
					</div>
					
					<button className="btn btn-primary vf-center-block" onClick={this.joinRoom}>Join Meeting</button>
				</div>
			</div>
		);
	}

});

module.exports = JoinFormFacilitator;