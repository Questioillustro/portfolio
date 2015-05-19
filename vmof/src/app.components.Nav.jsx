require('./app.components.Nav.styl');
var React = require('react/addons');

var Nav = React.createClass({

	getInitialState: function() {
		window.updateNav = this.updateNav;
		return { context: '' };
	},

	updateNav: function (context) {
		this.setState({ context: context });
	},

	render : function() {
		var handleClick = function() {
			$("#wrapper").toggleClass("toggled");
		}
		if(this.state.context === 'Room') {
			return (
				<div>
					<nav className="vf-navbar navbar-default navbar-fixed-top">
						<div className="container-fluid">
							<div className="navbar-header">
								<button type="button" id="menu-toggle" className="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" onClick={handleClick}>
									<span className="sr-only">Toggle navigation</span>
									<span className="icon-bar"></span>
									<span className="icon-bar"></span>
									<span className="icon-bar"></span>
								</button>
								<a className="navbar-brand" href="#">vMOF</a>
							</div>
						</div>
					</nav>
				</div>
			);
		} else {
			return (
				<div>
					<nav className="vf-navbar navbar-default navbar-fixed-top">
						<div className="container-fluid">
							<div className="navbar-header">
								<a className="navbar-brand" href="#">vMOF</a>
							</div>
						</div>
					</nav>
				</div>
			);
		}
	}

});

module.exports = Nav;