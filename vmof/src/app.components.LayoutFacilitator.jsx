require('./app.components.LayoutFacilitator.styl');
require('../node_modules/bootstrap-styl/js/modal.js');

var AnonymousBrainstormingFacilitator = require('./app.components.AnonymousBrainstormingFacilitator.jsx');
var ActivitiesMenu = require('./app.components.ActivitiesMenu.jsx');
var BrainstormingOptions = require('./app.components.BrainstormingOptions.jsx');
var TwoTruthsOptions = require('./app.components.TwoTruthsOptions.jsx');
var PollingOptions = require('./app.components.PollingOptions.jsx');
var PollingFac = require('./app.components.Polling.FacilitatorInProgress.jsx');
var TopNav = require('./app.components.TopNav.jsx');
var FacilitatorIdleModule = require('./app.components.FacilitatorIdleModule.jsx');

var React = require('react/addons');
var io = require('alias_socket_io');
var socket = io.connect();

var Layout = React.createClass({

	componentDidMount: function() {
		socket.on('start:anonymous:brainstorming', this.startAnonymousBrainstorming);
		//socket.on('start:polling', this.startPolling);
		socket.on('end:activity', this.endActivity);
		socket.on('receiveCommand', this.parseCommand);

		var fullHeightMinusHeader, sideScrollHeight = 0;

		function calcHeights() {		
			fullHeightMinusHeader = $(window).height() - $(".vf-navbar").outerHeight();
			$(".main-content, .sidebar-two, .sidebar-one").height(fullHeightMinusHeader);
			
			sideScrollHeight = fullHeightMinusHeader - $(".nav-menu").outerHeight() - $(".buttons").outerHeight();
			$(".side-scroll").height(sideScrollHeight);

			usersHeight = fullHeightMinusHeader - $(".room").outerHeight();
			$(".users").height(usersHeight);
		}

		calcHeights();
		
		$(window).resize(function() {
			calcHeights();
		});

		$(window).on('beforeunload', function(evt) {
	        return 'Navigation will end your current session!';
	    });

		React.render(<FacilitatorIdleModule key="Idle Module" room={this.props.room} />, document.getElementById('vf-activity-content'));
	},

	componentWillUnmount: function () {
		socket.removeAllListeners('start:anonymous:brainstorming');
		//socket.removeAllListeners('start:polling');
		socket.removeAllListeners('end:activity');
		socket.removeAllListeners('receiveCommand');
	},

	parseCommand: function(message){
		if(message.text == "endActivity")
			this.mountIdleModule();
    },

	pressLeft: function() {
		if($('.sidebar-one').hasClass('toggled')) {
			$('.sidebar-one').removeClass('toggled');
		} else {
			$('.sidebar-one').addClass('toggled');
			$('.sidebar-two').removeClass('toggled');
		}
	},

	pressRight: function() {
		if($('.sidebar-two').hasClass('toggled')) {
			$('.sidebar-two').removeClass('toggled');
		} else {
			$('.sidebar-two').addClass('toggled');
			$('.sidebar-one').removeClass('toggled');
		}
	},

	sendAlert: function () {
		var message = $('#status_message').val()
		$('#status_message').val('');
		socket.emit('sendToRoom', this.props.room, message);
	},

	endActivity: function () {
		this.mountIdleModule();
    },

	startAnonymousBrainstorming: function (parameters) {
		$('.vf-nav button').attr('disabled',true);
		React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
		React.render(<AnonymousBrainstormingFacilitator room={this.props.room} params={parameters} />, document.getElementById('vf-activity-content'));
	},

	startPolling: function() {
		$('.vf-nav button').attr('disabled',true);
		React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
		React.render(<PollingFac room={this.props.room} params={parameters}/>, document.getElementById('vf-activity-content'));	
	},

	mountIdleModule: function () {
		$('.vf-nav button').attr('disabled',false);
		$('.vf-nav').find('.active').removeClass('active');
		var unmounted = React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
		React.render(<FacilitatorIdleModule key="Idle Module" room={this.props.room} />, document.getElementById('vf-activity-content'));
	},

	render: function() {
		var self = this;

		return(
			<div className="vmof">
				<nav className="vf-navbar navbar navbar-default">
					<div>
						<TopNav room={self.props.room} roomTitleEdit={true} />
					</div>
					
					<div className="container-fluid">
						<div className="navbar-header">
							<button type="button" id="menu-toggle-left" className="navbar-toggle" onClick={this.pressLeft}>
								<span className="icon-bar"></span>
								<span className="icon-bar"></span>
								<span className="icon-bar"></span>
							</button>
				
							<button type="button" id="menu-toggle-right" className="navbar-toggle" onClick={this.pressRight}>
								<span className="icon-bar"></span>
								<span className="icon-bar"></span>
								<span className="icon-bar"></span>
							</button>
						</div>
					</div>
				</nav>

				<div id="alert-modal" className="modal fade">
                  <div className="modal-dialog">
                    <div className="modal-content">
                      <div className="modal-header">
                        <button type="button" className="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 id="alert-modal-title" className="modal-title">Warning</h4>
                      </div>
                      <div className="modal-body">
                        <p id="alert-modal-text"></p>
                      </div>
                      <div className="modal-footer">
                        <button type="button" className="btn btn-primary" data-dismiss="modal">Dismiss</button>
                      </div>
                    </div>
                  </div>
                </div>

				<div id="warning-modal" className="modal fade">
                  <div className="modal-dialog">
                    <div className="modal-content">
                      <div className="modal-header">
                        <button type="button" className="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 className="modal-title">Warning</h4>
                      </div>
                      <div className="modal-body">
                        <p id="warning-modal-text"></p>
                      </div>
                      <div className="modal-footer">
                      	<button id="warning-modal-ok" type="button" className="btn btn-success navbar-btn" data-dismiss="modal" style={{'margin-top':'1px'}}>Ok</button>
                        <button id="warning-modal-cancel" type="button" className="btn btn-danger navbar-btn" data-dismiss="modal">Cancel</button>
                      </div>
                    </div>
                  </div>
                </div>

				<div className="sidebar-one">
					{/*<span className="col-xs-12 vf-activity-list-label" style={{ 'background-color':'#2b3e50', 'text-align':'center', 'border' :'inset 1px black' }}>*/}
					<span className="col-xs-12 vf-activity-list-label" style={{ 'background-color':'#df691a' }}>
						<h4>Activity List</h4>
					</span>
					
					<div className="nav-menu">
						<ActivitiesMenu room={this.props.room} mountIdle={this.mountIdleModule} />
					</div>

					<div className="side-scroll">
						<div className="side-scroll-inner"></div>
					</div>
				</div>

				{/*
				<div id="send-alert-div" className="container col-sm-8 col-md-8 col-lg-8 row">
					<div className="col-xs-8 col-sm-7 col-md-9 col-lg-9">
						<input id="status_message" type="text" placeholder="Send an alert to the room" className="form-control"></input>
					</div>

					<div className="col-xs-3 col-sm-2 col-md-3 col-lg-3">
						<button className="btn btn-primary" id="send_alert" onClick={this.sendAlert}>Send Alert</button>
					</div>
				</div>
				*/}

				<div className="main-content">
					<div id="vf-activity-content" className="main-content-inner"></div>
				</div>

				<div className="sidebar-two">
					<div className="room vf-panel panel panel-primary">
						<div className="panel-heading">
							<h3 className="panel-title">Meeting Participants</h3>
						</div>
					</div>

					<div id="vf-user-list" className="users">
					</div>
				</div>
			</div>
		);
	}

});

module.exports = Layout;