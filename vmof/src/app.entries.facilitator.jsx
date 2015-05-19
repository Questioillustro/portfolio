require('./app.styles.main.styl');
var React = require('react');
var LayoutHome = require('./app.components.LayoutHome.jsx');
// var Nav = require('./app.components.Nav.jsx');
// var Lobby = require('./app.components.Lobby.jsx');
var JoinFormFacilitator = require('./app.components.JoinFormFacilitator.jsx');

React.render(<LayoutHome />, document.body);
React.render(<JoinFormFacilitator />, document.getElementById('vf-content'));
// React.render(<Nav />, document.getElementById('vf-nav'));
// React.render(<Lobby user={"facilitator"} />, document.getElementById('vf-content'));