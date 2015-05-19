require('./app.components.RoomFacilitator.styl');
require('alias_simple_sidebar');
var React = require('react/addons');
var UserList = require('./app.components.UserList.jsx');
var ActivitiesMenu = require('./app.components.ActivitiesMenu.jsx');
var RoomMixin = require('./app.components.Room.mixin.js');

var RoomFacilitator = React.createClass({

	mixins: [RoomMixin],

	sendAlert: function () {
		this.sendMessage($('#status_message').val());
		$('#status_message').val('');
	},

	render : function() {
		return (
			<div id="wrapper" className="col-md-12">
				<div className="row">
					<div id="left-sidebar-wrapper" className="col-md-2">
						<ActivitiesMenu room={this.props.room}/>
					</div>
					
					<div id="sidebar-wrapper" className="col-md-2">
						<UserList room={this.props.room}/>
					</div>

					<div id="page-content-wrapper" className="col-md-8">
						<div className="container-fluid">
							<div className="row">
								<div className="col-md-12 row">
									<div className="col-md-12 row">
										<div className="col-md-offset-1 col-md-2">
											<button className="btn btn-primary vf-center" id="send" onClick={this.sendAlert}>Send Alert</button>
										</div>
										<div className="col-md-8">
											<input id="status_message" type="text" placeholder="Send an alert to the room" className="form-control"></input>
										</div>
									</div>

									<div className="col-md-12">
										<hr/>
									</div>

									<div id="activity-content-section" className="col-md-12 content-panel"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		);
	}

});

module.exports = RoomFacilitator;