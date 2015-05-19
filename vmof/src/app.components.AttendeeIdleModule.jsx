var React = require('react/addons');
var Carousel = require('./app.components.InfoCarousel.jsx');
var StatusModule = require('./app.components.StatusModule.jsx');

var AttendeeIdleModule = React.createClass({

	render: function () {
		return (
			<div>
				<StatusModule room={this.props.room} />
				<Carousel />
			</div>
		)
	}
})

module.exports = AttendeeIdleModule;