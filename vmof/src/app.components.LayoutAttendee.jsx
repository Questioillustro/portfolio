require('./app.components.LayoutAttendee.styl');
require('../node_modules/bootstrap-styl/js/modal.js');

var AnonymousBrainstorming = require('./app.components.AnonymousBrainstorming.jsx');
var EmpathyMap = require('./app.components.EmpathyMap.jsx');
var PollingAtt = require('./app.components.Polling.AttendeeInProgress.jsx');
var AnonymousBrainstorming = require('./app.components.AnonymousBrainstorming.jsx');
var EmpathyMap = require('./app.components.EmpathyMap.jsx');
var RoundRobinBrainstorming = require('./app.components.RoundRobinBrainstorming.jsx');
var TwoTruthsAtt = require('./app.components.TwoTruths.AttendeInProgress.jsx');
var TopNav = require('./app.components.TopNav.jsx');
var AttendeeIdleModule = require('./app.components.AttendeeIdleModule.jsx');
var ModalWarningMixin = require('./app.components.mixin.ModalWarning.js');


var React = require('react/addons');
var io = require('alias_socket_io');
var socket = io.connect();

var inprogress = false;

var Layout = React.createClass({

	mixins: [ModalWarningMixin],

	getInitialState: function() {
		return { 
			text: "",
			name: "",
			id: "", 
		};
	},

	componentWillMount: function() {
		socket.on('receiveMessage', this.showMessage);
		socket.on('beginActivity', this.startActivity);
		socket.on('start:anonymous:brainstorming', this.startAnonymousBrainstorming);
		socket.on('end:activity', this.endActivity);
		socket.on('userInfoRequest', this.upDateSelf);
    	socket.on('empathy:map:start', this.startEmpathyMap);
     	socket.on('start:round:robin:brainstorming', this.startRoundRobinBrainstorming);
     	socket.on('start:polling', this.startPolling);
     	socket.on('start:TwoTruths', this.startTwoTruths);
   	
     	socket.on('receiveCommand', this.parseCommand);

	 	$(window).on('beforeunload', function() {
	        return 'Navigation will end your current session!';
	    });
    },

	componentDidMount: function() {
		socket.emit('getSelf');
		
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
		
		React.render(<AttendeeIdleModule room={this.props.room}/>, document.getElementById('vf-activity-content'));
	},

    componentWillUnmount: function () {
    	socket.removeAllListeners('receiveMessage');
    	socket.removeAllListeners('beginActivity');
    	socket.removeAllListeners('start:anonymous:brainstorming');
    	socket.removeAllListeners('end:activity');
    	socket.removeAllListeners('userInfoRequest');
       	socket.removeAllListeners('empathy:map:start');
       	socket.removeAllListeners('start:polling');
    	socket.removeAllListeners('start:round:robin:brainstorming');
    	socket.removeAllListeners('receiveCommand');
    	socket.removeAllListeners('startTwoTruths');
    },

    startEmpathyMap: function (parameters) {
    	inprogress = true;
    	React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
		React.render(<EmpathyMap params={parameters} room={this.props.room} />, document.getElementById('vf-activity-content'));
    },

    startRoundRobinBrainstorming: function (parameters) {
    	React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
		React.render(<RoundRobinBrainstorming params={parameters} room={this.props.room} />, document.getElementById('vf-activity-content'));
    },

    startTwoTruths: function (parameters){
    	inprogress = true;
    	React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
    	React.render(<TwoTruthsAtt name={this.state.name} id={this.state.id} rules={parameters['activity-rules']} room={this.props.room}/>, document.getElementById('vf-activity-content'));
    },

    upDateSelf: function(data){
      this.setState({name: data.name});
      this.setState({id: data.id});
    },

	showMessage: function (message) {
		$('.alert').show();
		this.setState({text: message});
	},

	//TODO: Remove this code duplication
    parseCommand: function(message){
    	if(message.text == "endActivity" && inprogress == true) {
    		inprogress = false;
    		activityunmounted = React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
    		React.render(<AttendeeIdleModule room={this.props.room}/>, document.getElementById('vf-activity-content'));
    	}
    },
    endActivity: function () {
    	if(inprogress = true){
    		inprogress = false;
    		this.alertMessage( "Thank you for participating! If you have supplied an email address the results will be emailed out to you.", "Activity Ended" )
			var activityunmounted = React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
			React.render(<AttendeeIdleModule room={this.props.room}/>, document.getElementById('vf-activity-content'));
		}
	},

	startAnonymousBrainstorming: function (parameters) {
		inprogress = true;
		React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
		React.render(<AnonymousBrainstorming params={parameters} room={this.props.room} />, document.getElementById('vf-activity-content'));
	},

	startActivity: function (parameters) {	
		inprogress = true;
		React.unmountComponentAtNode(document.getElementById('vf-activity-content'));

		if(parameters['activity-selection'] === 'TwoTruths') {
		//	React.render(<TwoTruthsAtt name={this.state.name} id={this.state.id} rules={parameters['activity-rules']} room={this.props.room}/>, document.getElementById('vf-activity-content'));
		} else if (parameters['activity-selection'] === 'Polling') {
			React.render(<PollingAtt params={parameters} room={this.props.room} />, document.getElementById('vf-activity-content'));	
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

	showMessage: function (message) {
		$('.alert').show();
		this.setState({text: message});
	},

	dismissAlert: function (e) {
		$(e.target).closest('.alert').fadeOut(500);
	},

	render: function() {
		
		var self = this;

		return(
			<div>
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
                      	<button id="warning-modal-ok" type="button" className="btn btn-primary" data-dismiss="modal">Ok</button>
                        <button id="warning-modal-cancel" type="button" className="btn btn-primary" data-dismiss="modal">Cancel</button>
                      </div>
                    </div>
                  </div>
                </div>

				<nav className="vf-navbar navbar navbar-default">
					<div>
						<TopNav room={self.props.room} />
					</div>

					<div className="container-fluid">
						<div className="navbar-header">
							<button type="button" id="menu-toggle-right" className="navbar-toggle" onClick={this.pressRight}>
								<span className="icon-bar"></span>
								<span className="icon-bar"></span>
								<span className="icon-bar"></span>
							</button>
						</div>
					</div>
				</nav>
				<div className="main-content">
					<div className="container-fluid">
						<div className="row">
							<div className="alert alert-success alert-dismissible col-lg-12">
								<button type="button" className="dismiss-alert" onClick={self.dismissAlert}>
									<span aria-hidden="true" className="glyphicon glyphicon-remove-sign" style={{'color':'red'}}></span>
								</button>
								{this.state.text}
							</div>
						</div>
					</div>

					<div id="vf-activity-content" className="main-content-inner">

					</div>
				</div>

				<div className="sidebar-two">
					<div className="room vf-panel panel panel-primary">
						<div className="panel-heading">
							<h3 className="panel-title">Meeting Participants</h3>
						</div>
					</div>

					<div id="vf-user-list" className="users"></div>
				</div>
			</div>
		);
	}

});

module.exports = Layout;
