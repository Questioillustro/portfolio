require('./app.components.Lobby.styl');
var React = require('react/addons');
var JoinForm = require('./app.components.JoinForm.jsx');
var JoinFormFacilitator = require('./app.components.JoinFormFacilitator.jsx');

var Lobby = React.createClass({

	render: function() {
		//console.log('props: ' + this.props.user);
		if (this.props.user === "facilitator") {
			return(
				<div className="vf-fill vf-center">
					<JoinFormFacilitator />
				</div>
			);
		} else {
			return(
				<div className="vf-fill vf-center">
					<JoinForm />
				</div>
			);
		}
	}

});

module.exports = Lobby;