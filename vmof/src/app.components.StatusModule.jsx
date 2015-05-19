var React = require('react/addons');

//require('./app.components.StatusModule.styl');

var io = require('alias_socket_io');
var socket = io.connect();

var Carousel = React.createClass({

	getInitialState: function () {
		return { status : "", facOnline : false };
	},

	componentWillMount: function(){
		socket.on('updateStatus', this.updateStatus);
		socket.emit('getStatus', this.props.room);
    },

    componentWillUnmount: function () {
    	socket.removeAllListeners('updateStatus');
    },

    updateStatus: function(data){
    	this.setState( data )
    },

	render: function() {

		var self = this;

		var renderFacOnline = function () {
			if (self.state.facOnline)
				return self.state.status
			else
				return "Facilitator Offline"
		}

		return(
			<div>
				<div>
					<div className="jumbotron">
					  <h2 className="centered noTopPadding">Meeting Status</h2>
					  <h4 className="centered noTopPadding">{renderFacOnline()}</h4>
					</div>
				</div>
			</div>
		);
	}
});

module.exports = Carousel;
