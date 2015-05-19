require('./app.styles.main.styl');
var React = require('react');
var LayoutHome = require('./app.components.LayoutHome.jsx');
// var Nav = require('./app.components.Nav.jsx');
// var Lobby = require('./app.components.Lobby.jsx');
var JoinForm = require('./app.components.JoinForm.jsx');

React.render(<LayoutHome />, document.body);
React.render(<JoinForm />, document.getElementById('vf-content'));

// React.render(<Layout />, document.body);
// React.render(<Nav />, document.getElementById('vf-nav'));
// React.render(<Lobby />, document.getElementById('vf-content'));