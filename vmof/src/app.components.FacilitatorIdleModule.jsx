var React = require('react/addons');
var Carousel = require('./app.components.InfoCarousel.jsx');
var React = require('react/addons');
var io = require('alias_socket_io');
var socket = io.connect();

var FacilitatorIdleModule = React.createClass({

	render: function () {
		return (
			<div>
				<div>
					<legend>
					<marquee behavior="scroll" direction="left">
						Welcome to vMOF, the Virtual Meeting Outcome Facilitator!    
						Select an activity from the list on the left to begin.
						Participants will appear in the right-hand panel.
						</marquee>
					</legend>
				</div>

				<Carousel />
			</div>
		)
	}
})

module.exports = FacilitatorIdleModule;
