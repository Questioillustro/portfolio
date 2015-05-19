require('./app.components.Room.styl');
require('alias_simple_sidebar');
var React = require('react/addons');
var RoomMixin = require('./app.components.Room.mixin.js');
var UserList = require('./app.components.UserList.jsx');
var AnonymousBrainstorming = require('./app.components.AnonymousBrainstorming.jsx');
var TwoTruthsAtt = require('./app.components.TwoTruths.AttendeInProgress.jsx');
var io = require('alias_socket_io');
var socket = io.connect();

var Room = React.createClass({

	mixins: [RoomMixin],

	getInitialState: function() {
		socket.on('recieveMessage', this.showMessage);
		socket.on('beginActivity', this.startActivity);
		return { text: "" };
	},

	showMessage: function (message) {
		$('.alert').show();
		this.setState({text: message});
	},

	endActivity: function () {
		var activityunmounted = React.unmountComponentAtNode(document.getElementById('activity-panel'));
	},

	startActivity: function (parameters) {
		if (parameters['activity-selection'] === 'Anonymous Brainstorming') {
			React.render(<AnonymousBrainstorming params={parameters} room={this.props.room} user={this.props.name} />, document.getElementById('activity-panel'));
		} else if(parameters['activity-selection'] === 'TwoTruths') {
			React.render(<TwoTruthsAtt rules={parameters['activity-rules']} room={this.props.room}/>, document.getElementById('activity-panel'));
		}
	},

	dismissAlert: function (e) {
		$(e.target).closest('.alert').hide();
	},

	render : function() {
		var self = this;

		var renderUser = function(user) {
		 	return <li key={user.id}> <a href="#">{user.name}</a> </li>
		}
		
		return (
			<div id="wrapper">
				<div id="sidebar-wrapper">
					<UserList room={this.props.room}/>
				</div>

				<div id="page-content-wrapper">
					<div className="container-fluid">
						<div className="row">
							<div className="alert alert-success alert-dismissible col-lg-12">
								<button type="button" className="dismiss-alert" onClick={self.dismissAlert}><span aria-hidden="true">&times;</span></button>
								{this.state.text}
							</div>
						</div>
					</div>

					<div id="activity-panel" className="activity-panel col-md-12"></div>
				</div>
			</div>
		);
	}

});

module.exports = Room;
