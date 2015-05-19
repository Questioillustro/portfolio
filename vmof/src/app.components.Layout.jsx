require('./app.components.Layout.styl');
require('./app.components.Layout.js');
var React = require('react/addons');

var Layout = React.createClass({

	pressLeft: function() {
		if($('.sidebar-one').hasClass('toggled')) {
			$('.sidebar-one').removeClass('toggled');
		} else {
			$('.sidebar-one').addClass('toggled');
			$('.sidebar-two').removeClass('toggled');
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

	render: function() {
		return(
			<div>
				<nav className="vf-navbar navbar navbar-default">
					<div className="container-fluid">
						<div className="navbar-header">
							<button type="button" id="menu-toggle-left" className="navbar-toggle" onClick={this.pressLeft}>
								<span className="icon-bar"></span>
								<span className="icon-bar"></span>
								<span className="icon-bar"></span>
							</button>
							<a className="navbar-brand" href="#">vMOF</a>
							<button type="button" id="menu-toggle-right" className="navbar-toggle" onClick={this.pressRight}>
								<span className="icon-bar"></span>
								<span className="icon-bar"></span>
								<span className="icon-bar"></span>
							</button>
						</div>
					</div>
				</nav>
				
				<div className="sidebar-one">
					<div className="nav-menu">
						<ul>
							<li><a href="full-height-width.html#">Brainstorming</a></li>
							<li><a href="full-height-width.html#">Icebreakers</a></li>
							<li><a href="full-height-width.html#">Other Activity 1</a></li>
							<li><a href="full-height-width.html#">Other Activity 2</a></li>
							<li><a href="full-height-width.html#">Other Activity 3</a></li>
						</ul>	
					</div>

					<div className="side-scroll">
						<div className="side-scroll-inner">
							<h4>Scrollable Content</h4>
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
							<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
						</div>
					</div>
				</div>

				<div className="main-content">
					<div className="main-content-inner">
						<h2>Main Content</h2>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
					</div>
				</div>

				<div className="sidebar-two">
					<div className="room vf-panel panel panel-primary">
						<div className="panel-heading">
							<h3 className="panel-title">Room 1500</h3>
						</div>
					</div>

					<div className="users">
						<ul className="vf-list-group list-group">
							<li className="vf-list-group-item list-group-item"><div className="vf-list-group-item-inner"><span className="glyphicon"><img src="img/user4.png" /></span><span className="vf-list-group-item-label" title="Doug">Doug</span></div></li>
							<li className="vf-list-group-item list-group-item"><div className="vf-list-group-item-inner"><span className="glyphicon"><img src="img/user4.png" /></span><span className="vf-list-group-item-label" title="Marc">Marc</span></div></li>
							<li className="vf-list-group-item list-group-item"><div className="vf-list-group-item-inner"><span className="glyphicon"><img src="img/user4.png" /></span><span className="vf-list-group-item-label" title="Lauren">Lauren</span></div></li>
							<li className="vf-list-group-item list-group-item"><div className="vf-list-group-item-inner"><span className="glyphicon"><img src="img/user4.png" /></span><span className="vf-list-group-item-label" title="Ken">Ken</span></div></li>
							<li className="vf-list-group-item list-group-item"><div className="vf-list-group-item-inner"><span className="glyphicon"><img src="img/user4.png" /></span><span className="vf-list-group-item-label" title="Andrew">Andrew</span></div></li>
							<li className="vf-list-group-item list-group-item"><div className="vf-list-group-item-inner"><span className="glyphicon"><img src="img/user4.png" /></span><span className="vf-list-group-item-label" title="Steve">Steve</span></div></li>
							<li className="vf-list-group-item list-group-item"><div className="vf-list-group-item-inner"><span className="glyphicon"><img src="img/user4.png" /></span><span className="vf-list-group-item-label" title="Evan">Evan</span></div></li>
							<li className="vf-list-group-item list-group-item"><div className="vf-list-group-item-inner"><span className="glyphicon"><img src="img/user4.png" /></span><span className="vf-list-group-item-label" title="Chris">Chris</span></div></li>
							<li className="vf-list-group-item list-group-item"><div className="vf-list-group-item-inner"><span className="glyphicon"><img src="img/user4.png" /></span><span className="vf-list-group-item-label" title="Generic Mitre Employee 1">Generic Mitre Employee 1</span></div></li>
							<li className="vf-list-group-item list-group-item"><div className="vf-list-group-item-inner"><span className="glyphicon"><img src="img/user4.png" /></span><span className="vf-list-group-item-label" title="Generic Mitre Employee 2">Generic Mitre Employee 2</span></div></li>
							<li className="vf-list-group-item list-group-item"><div className="vf-list-group-item-inner"><span className="glyphicon"><img src="img/user4.png" /></span><span className="vf-list-group-item-label" title="Generic Mitre Employee 3">Generic Mitre Employee 3</span></div></li>
							<li className="vf-list-group-item list-group-item"><div className="vf-list-group-item-inner"><span className="glyphicon"><img src="img/user4.png" /></span><span className="vf-list-group-item-label" title="Generic Mitre Employee 4">Generic Mitre Employee 4</span></div></li>
						</ul>
					</div>
				</div>
			</div>
		);
	}

	// render: function() {
	// 	return(
	// 		<div id="vf-layout">
	// 			<div id="vf-nav"></div>
	// 			<div id="vf-content"></div>
	// 		</div>
	// 	);
	// }

});

module.exports = Layout;