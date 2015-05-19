var React = require('react/addons');
var Timer = require('./app.components.Timer.jsx');
var io = require('alias_socket_io');
var socket = io.connect();

var PollingDetails = React.createClass({

	getInitialState: function(){
		console.log('params', this.props.params.question);
        return { user: []}
	},

	componentDidMount: function() {
		socket.on('userInfoRequest', this.getUser);
        socket.emit('getSelf');
        console.log('state', this.state);
    },	

	getUser: function(user) {
        this.setState({ user: user });
    },

	render: function () {

		var self = this;

		var renderQuestion = function () {
			// Don't render if no question is provided
			if (self.props.params.question === '')
				return;

			return (
				<div className="question-posed">
	                <div className="polling-topic well">
	                    <h4>{self.props.params.question}</h4>
	                </div>
	            </div>
          	)
		}

		var renderIdea1 = function (user) {
			// Don't render if no idea is provided
			if (self.props.params.idea1 === '')
				return;

			if (user.name === "Facilitator"){
				return (
				<div className="idea">
	                <div className="polling-topic well">
	                    <h4>{self.state.idea1}</h4>
	                </div>
	            </div>
			)} else {
				return (
					<div className="idea">
		                <div className="polling-topic well">
		                    <h4>{self.props.params.idea1}</h4>
		                    <input type="radio" className="btn btn-primary" name="choices" id="ideaButton1"></input>
		                </div>
		            </div>
          	)}
		}

		var renderIdea2 = function (user) {
			// Don't render if no question is provided
			if (self.props.params.idea2 === '')
				return;

			if (user.name === "Facilitator"){
				return (
				<div className="idea">
	                <div className="polling-topic well">
	                    <h4>{self.props.params.idea2}</h4>
	                </div>
	            </div>
			)} else {
				return (
					<div className="idea">
		                <div className="polling-topic well">
		                    <h4>{self.props.params.idea2}</h4>
		                    <input type="radio" className="btn btn-primary" name="choices" id="ideaButton2"></input>
		                </div>
		            </div>
          	)}
		}

		return (
			<div id="details-panel">
				<div id="polling-parameters">
					{renderQuestion}

					{renderIdea1(this.state.user)}
					{renderIdea2(this.state.user)}
	            </div>
	            
	            <div className="phase-progress" style={{'textAlign':'center'}}>

	                <div>
	                    <div className="timer-div">
	                        <Timer start={this.props.params.start_time} 
	                        	   totalSeconds={this.props.params.time_remaining} 
	                        	   callback={self.props.callback} />
	                    </div>
	                    <small>The activity will move to the next phase when all users are ready or time expires</small>
	                </div>
	            </div>
	        </div>
		)
	}

});

module.exports = PollingDetails;