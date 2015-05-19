require('./app.components.UserList.styl');
var React = require('react/addons');
var io = require('alias_socket_io');
var socket = io.connect();

var UserList = React.createClass({

	componentDidMount: function () {
		socket.on('get:users:in:room', this.getUsersInRoom);
		socket.on('update:users:ready', this.updateUsersReady);
		socket.on('set:anonymous:userlist', this.setAnonymous);
		socket.on('empathy:map:ready:up', this.updateUsersReady2);
		socket.on('empathy:map:unready:up', this.unreadyUp);
		socket.on('empathy:map:set:color', this.setColor);
		socket.on('empathy:map:unset:color', this.unsetColor);
		socket.on('twoTruths:ready:up', this.updateUsersReady2);
		socket.on('update:inprogress', this.inProgressUpdate);

		socket.emit('get:users:in:room', this.props.room);
		socket.emit('get:inprogress', this.props.room);
	},

	componentWillUnmount: function () {
		socket.removeAllListeners('get:users:in:room');
		socket.removeAllListeners('update:users:ready');
		socket.removeAllListeners('set:anonymous:userlist');
		socket.removeAllListeners('empathy:map:ready:up');
		socket.removeAllListeners('empathy:map:unready:up');
		socket.removeAllListeners('empathy:map:set:color');
		socket.removeAllListeners('empathy:map:unset:color');
		socket.removeAllListeners('twoTruths:ready:up');
		socket.removeAllListeners('update:inprogress');
	},

	inProgressUpdate: function (inProgress) {
		this.setState({ activityInProgress: inProgress });
	},

	setColor: function (usersJSON) {
		usersJSON.forEach(function (user) {
			console.log('setColor:', user.color);
			// $('#' + user.id + ' img').css('border-left', '5px solid ' + user.color);
			$('#' + user.id + ' img').css('display', 'none');
			$('#' + user.id + ' .vf-user-icon').css('background', user.color);
			$('#' + user.id + ' .vf-user-icon').css('display', 'inline-block');
		});
		//$('#' + user.id + ' img').css('border-left', '5px solid ' + user.color);
	},

	unsetColor: function (usersJSON) {
		usersJSON.forEach(function (user) {
			//$('#' + user.id + ' img').css('border-left', 'none');
			$('#' + user.id + ' .vf-user-icon').css('display', 'none');
			$('#' + user.id + ' img').css('display', 'inline-block');
			//$('#' + user.id + ' .vf-user-icon').css('background', "url('img/user4.png') no-repeat");
		});
		//$('#' + user.id + ' img').css('border-left', 'none');
	},

	updateUsersReady: function (usersReady) {
		this.setState({ usersReady: usersReady });
	},

	updateUsersReady2: function (user) {
		$('#' + user.id).find('div#checkMark').append('<span class="glyphicon glyphicon-ok vf-user-ready" />');
	},

	unreadyUp: function (user) {
		$('#' + user.id + ' span.vf-user-ready').remove();
	},

	setAnonymous: function (val) {
		this.setState({ anonymousList: val });
	},

	getUsersInRoom: function(users) {
		this.setState({ users: users });
	},

	getInitialState: function() {
		return { users: [], usersReady: 0, anonymousList: false };
	},

	logout: function () {
		location.reload();
	},

	render: function () {
		
		var self = this;

		var renderName = function (user, index) {
			if (self.state.anonymousList) 
				return <span className="vf-list-group-item-label" title={index+1}>User {index+1}</span>
			else
				return <span className="vf-list-group-item-label" title={user.name}>{user.name}</span>
		}

		var renderReady = function (index) {
			if (index < self.state.usersReady) 
				return <span className="glyphicon glyphicon-ok vf-user-ready" />
		}

		var renderIcon = function (user) {
			if (user.inActivity || !self.state.activityInProgress)
				return <span className="glyphicon"><div className="vf-user-icon"></div><img src="img/user4.png" /></span>
			else if (!user.inActivity && self.state.activityInProgress)
				return <span className="glyphicon glyphicon-ban-circle vf-user-late-join navbar-btn" />
		}

		var renderUser = function(user, index) {
	 		return ( 
	 			<li id={user.id} key={user.id} className="vf-list-group-item list-group-item vf-user-panel">
		 			<div className="row">
		 				<div className="col-xs-8">
			 				<div className="vf-list-group-item-inner">
			 					{renderIcon(user)}
			 					{renderName(user, index)}
		 					</div>
		 				</div>
		 				<div className="col-xs-4">
		 					<div id="checkMark">
		 						{renderReady(index)}
		 					</div>
		 				</div>
	 				</div>
				</li>
			)
		}

		return (
			<ul className="vf-list-group list-group">
				<li id="logout" key="logout" className="vf-list-group-item list-group-item vf-user-panel vf-log-out" onClick={self.logout}>
					<span className="glyphicon glyphicon-log-out"> Logout</span>
				</li>
				{this.state.users.map(renderUser)}
			</ul>
		)		
	}	

});

module.exports = UserList;
