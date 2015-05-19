require('./app.components.LayoutHome.styl');
require('./app.components.LayoutHome.js');
var React = require('react/addons');

var Layout = React.createClass({

	render: function() {
		return(
			<div>

				<nav className="vf-navbar navbar navbar-default">
					<div className="container-fluid">
						<div className="navbar-header">
							<a className="navbar-brand" href="#">vMOF</a>
						</div>
					</div>
				</nav>

				<div id="vf-content">
				</div>

			</div>
		);
	}

});

module.exports = Layout;