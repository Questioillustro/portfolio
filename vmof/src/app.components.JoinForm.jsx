require('./app.components.JoinForm.styl');
var React = require('react/addons');
var io = require('alias_socket_io');
var LayoutAttendee = require('./app.components.LayoutAttendee.jsx');
var UserList = require('./app.components.UserList.jsx');
var ReactAutocomplete = require('alias_react_autocomplete');

var JoinFormMixin = require('./app.components.JoinForm.mixin.js');
var AutoScroll = require('./app.components.mixin.AutoScroll.js');

var socket = io.connect();

var JoinForm = React.createClass({

	mixins: [JoinFormMixin, AutoScroll],

	componentDidMount: function () {
		socket.on('user:join:room', this.updateView);
		socket.on('get:room:list', this.updateRoomList);
		socket.emit('request:room:list');

		this.initializeAutoScroll();
	},

	componentWillUnmount: function () {
		socket.removeAllListeners('user:join:room');
		socket.removeAllListeners('get:room:list');
	},

	getInitialState: function () {
		var options = this.getDefaultOptions();

		return { name: '', email: '', room: options[0].id, options: options };
	},

	nameChangeHandler: function(e){
		this.setState({ name: e.target.value });
	},

	emailChangeHandler: function(e){
		this.setState({ email: e.target.value });
	},

	joinRoom: function() {
		if (this.state.name === '') {
			$('#name-error').show();
			return;
		}

		$('#name-error').hide();

		socket.emit('user:join:room', {name: this.state.name, email: this.state.email, room: this.state.room });
	},

	updateView: function() {
		React.unmountComponentAtNode(document.body);
		React.render(<LayoutAttendee room={this.state.room} />, document.body);
		React.render(<UserList room={this.state.room} />, document.getElementById('vf-user-list'));
	},

	render: function() {
		var self = this;

		var renderRoomOptions = function (room) {
			return <option value={room.id}>{room.title}</option>
		}

		return(
			<div className="col-md-4">
				<div className='vf-welcome'>
					Welcome to vMOF, The Virtual Meeting Outcome Facilitator! To begin please enter your name as you would like it to appear, your email address and select a room to join (typically the room number you are physically meeting in).
				</div>
				
				<div className="vf-well vf-item">
					<label className="control-label" htmlFor="name-input">Username</label>
					<div className="form-group has-error">
						<input id="name-input" className="form-control" placeholder="Name" onChange={this.nameChangeHandler} />
						<label id="name-error" className="control-label vf-input-error" htmlFor="name-input">Name filed is required</label>
					</div>
					
					<div className="form-group">
						<label className="control-label" htmlFor="email-input">Email Address (optional)</label>
						<input id="email-input" className="form-control" placeholder="Email" onChange={this.emailChangeHandler} />
					</div>
					
					<label className="control-label" htmlFor="room-error">Join Room</label>
					<div className="form-group has-error">
						<select id="room-input" className="form-control" onChange={this.roomChangeHandler} >
							{self.state.options.map(renderRoomOptions)}
						</select>
					</div>

					<button className="btn btn-primary vf-center-block" onClick={this.joinRoom}>Join Meeting</button>
				</div>
			</div>
		);
	}

});

module.exports = JoinForm;
