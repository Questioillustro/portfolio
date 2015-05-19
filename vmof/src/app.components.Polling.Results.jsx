require('./app.components.PollingOptions.styl');
var React = require('react/addons');

var PhaseBreadCrumbs = require('./app.components.PhaseBreadCrumbs.jsx');


var io = require('alias_socket_io');
var socket = io.connect();

var PollingResults = React.createClass({

	getInitialState: function() {
		return { 
			user: "",
		}
 	},

	componentWillMount: function() {
		socket.emit('getSelf');
		socket.on('userInfoRequest', this.setSelf);
	},

	componentWillUnmount: function() {
		socket.removeAllListeners('userInfoRequest');
	},

	setSelf: function(self) {
		this.setState({ user : self });
	},

	orderVotes: function(votes) {
		var votesSorted = Object.keys(votes);
	},

    endActivity: function() {
        var data = {},
        	resultsData = {};;

        resultsData['question'] = this.props.params.question;
        resultsData['results'] = this.props.params.results;

        data['text'] = "endActivity";
        socket.emit('clear:readyup:polling', this.props.room);
        socket.emit('end:polling', this.props.room, resultsData);
        socket.emit('updateMeetingStatus', this.props.room, "Facilitator is idle");
        socket.emit('activityControl', this.props.room, data);
    },

	render: function() {

		var renderIdea = function(idea, index){
			return (
				<div className="col-xs-12">
                    <div>
                        <h3>{idea}</h3>
                    </div>
            	</div>
            )
		}

		var renderEndActivity = function(user) {
			if(user.name === 'Facilitator') {
				return (
				    <div className="centered proceed-button">
                        <button id="inputButton" className="btn btn-primary" onClick={this.endActivity}>End Activity</button>
                    </div>
                )
			}
		}

		var renderVotes = function(object) {
			return(
                <div className="idea">
                    <div className="polling-topic col-sm-5">
                        <div className="ideaColor" style={{ 'backgroundColor': object[3] }}>
                            <h5 className="innerIdea">{object[0]}</h5>
                        </div>
                        <div className="ideaResult" style={{'width': object[2]+'%', 'backgroundColor': object[3]}}>
                            <h5>{object[2]}% </h5>
                        </div>
                    </div>
                </div>
			)
		}


		if (this.state.user.name === 'Facilitator'){
		    return(
	        	<div className="well well-lg">
	                <div className="form-horizontal">
	                  	<fieldset>

	                  		<PhaseBreadCrumbs params={this.props.params} />
	                  		
	                  		<h2 className="headingText">The result of the poll was:</h2>

	                  		<h3 className="decidedIdea"> {this.props.params.decision} </h3>

	                  		{this.props.params.results.map(renderVotes)}

							<div className="centered proceed-button col-sm-12">
	                        	<button id="endButton" className="btn btn-danger endPolling" onClick={this.endActivity}>End Activity</button>
	                    	</div>

						</fieldset>
					</div>
				</div>
		)} else {
			return(
	        	<div className="well well-lg">
	                <div className="form-horizontal">
	                  	<fieldset>
	                  		
							<PhaseBreadCrumbs params={this.props.params} />
	                  		
	                  		<h2 className="headingText">The result of the poll was:</h2>

	                  		<h3 className="decidedIdea"> {this.props.params.decision} </h3>

	                  		{this.props.params.results.map(renderVotes)}

						</fieldset>
					</div>
				</div>
		)}
	}
});

module.exports = PollingResults;