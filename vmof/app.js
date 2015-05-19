require('node-jsx').install();
var express = require('express');
var app = express();
var router = require('./src/app.routes.js')(app);
var server = require('http').Server(app);
var io = require('socket.io')(server);
var socket = require('./src/app.sockets.socket.js');
var config = require('./config.js');
// var Brainstorming = require('./src/app.sockets.Brainstorming.js');
// var TwoTruths = require('./src/app.sockets.TwoTruths.js');
// var EmpathyMap = require('./src/app.sockets.EmpathyMap.js');

var host = 'localhost';
var port = 3000;


if(global.process.env.NODE_ENV == 'production') {
	host = config.host;
	port = config.port;
}

app.use('/',  express.static(__dirname + '/public'));
//app.use('/',  express.static(__dirname + '/node_modules/bootstrap-styl/fonts'));
app.use('/',  express.static(__dirname + '/node_modules'));
app.use('/',  express.static(__dirname + '/public/js'));


socket(io);
// TwoTruths(io);
// Brainstorming(io);
// EmpathyMap(io);

server.listen(port, host, function() {
    console.log('Listening on port %d %s', server.address().port, host);
});
