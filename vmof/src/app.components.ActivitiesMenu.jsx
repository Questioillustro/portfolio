require('./app.components.ActivitiesMenu.styl');

var BrainstormingOptions = require('./app.components.BrainstormingOptions.jsx');
var TwoTruthsOptions = require('./app.components.TwoTruthsOptions.jsx');
var EmpathyMapOptions = require('./app.components.EmpathyMapOptions.jsx');
var RoundRobinOptions = require('./app.components.RoundRobinOptions.jsx');
var PollingOptions = require('./app.components.PollingOptions.jsx');
var TiebreakerOptions = require('./app.components.TiebreakerOptions.jsx');

var React = require('react/addons');

var io = require('alias_socket_io');
var socket = io.connect();

var ActivitiesMenu = React.createClass({
	
	renderBrainstormingOptions: function (evt) {
		this.setUI(evt);
	
	    this.updateStatus("Facilitator is setting up an Anonymous Brainstorming Activity");
      
		React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
	   	React.render(<BrainstormingOptions cancel={this.cancel} room={this.props.room} />, document.getElementById('vf-activity-content'));
	},

	renderTwoTruthsOptions: function (evt) {
		this.setUI(evt);

		this.updateStatus("Facilitator is setting up Two Truths and a Lie Activity");

		React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
    	React.render(<TwoTruthsOptions cancel={this.cancel} room={this.props.room}/>, document.getElementById('vf-activity-content'));
	},

	renderEmpathyMapOptions: function (evt) {
		this.setUI(evt);

		this.updateStatus("Facilitator is setting up an Empathy Map Activity")

		React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
    	React.render(<EmpathyMapOptions cancel={this.cancel} room={this.props.room}/>, document.getElementById('vf-activity-content'));
	},	

	renderPollingOptions: function (evt) {
		this.setUI(evt);
		
		this.updateStatus("Facilitator is setting up a Poll");

		React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
    	React.render(<PollingOptions cancel={this.cancel} room={this.props.room}/>, document.getElementById('vf-activity-content'));
	},	

	renderTiebreakerOptions: function (evt) {
		this.setUI(evt);
		
		this.updateStatus("Facilitator is setting up a Tiebreaker");

		React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
    	React.render(<TiebreakerOptions cancel={this.cancel} room={this.props.room}/>, document.getElementById('vf-activity-content'));
	},	

	cancel: function () {
		$('.vf-nav button').attr('disabled', false);
		this.updateStatus("Facilitator is idle");
		this.props.mountIdle();
	},

	updateStatus: function (status) {
        socket.emit('updateMeetingStatus', this.props.room, status);
	},

	setUI: function (evt) {
		$('.vf-nav').find('.active').removeClass('active');
		$(evt.target).addClass('active');
	},

	render: function () {
		return (
			<ul className="nav nav-pills nav-stacked vf-nav">
				<li style={{marginTop: '0px'}}><button style={{textAlign: 'left', outlineOffset: 0, outline: 'none'}} className="btn btn-default navbar-btn col-xs-12" type="button" onClick={this.renderTwoTruthsOptions}>Two Truths and a Lie</button></li>
				<li><button style={{textAlign: 'left', outlineOffset: 0, outline: 'none'}} className="btn btn-default navbar-btn col-xs-12" type="button" onClick={this.renderBrainstormingOptions}>Brainstorming</button></li>	
				<li><button style={{textAlign: 'left', outlineOffset: 0, outline: 'none'}} className="btn btn-default navbar-btn col-xs-12" type="button" onClick={this.renderEmpathyMapOptions}>Empathy Map</button></li>
				<li><button style={{textAlign: 'left', outlineOffset: 0, outline: 'none'}} className="btn btn-default navbar-btn col-xs-12" type="button" onClick={this.renderPollingOptions}>Polling</button></li>
				<li><button style={{textAlign: 'left', outlineOffset: 0, outline: 'none'}} className="btn btn-default navbar-btn col-xs-12" type="button" onClick={this.renderTiebreakerOptions}>Tiebreaker</button></li>
			</ul> 
		)
	}
});

module.exports = ActivitiesMenu;
