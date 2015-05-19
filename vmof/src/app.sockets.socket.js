var nodemailer = require('nodemailer');
var AnonymousBrainstorming = require('./app.components.ServerAnonymousBrainstorming');
var RoundRobinBrainstorming = require('./app.activities.RoundRobinBrainstorming');
var EmpathyMap = require('./app.activities.EmpathyMap');
var Polling = require('./app.components.ServerPolling');
var TwoTruths = require('./app.models.TwoTruthsActivity');
var User = require('./app.models.User.js');
var Rooms = require('./app.models.Rooms.js');
var Room = require('./app.models.Room.js');

global.rooms = new Rooms();
var anonymousBrainstorming;
var empathyMapSocket = require('./app.sockets.EmpathyMap.js');

var transporter = nodemailer.createTransport({
	service: 'gmail',
	auth: {
		user: 'vmof.email@gmail.com',
		pass: 'mofv-8072'
	}
});

module.exports = function (io) {

	io.on('connection', function(socket) {

		var user;
		var TwoTruthsTimeout;
		var TwoMinuteWaring;
		rooms.addToLobby(socket.id);

		var setRoomStatus = function (roomStr, status) {
			console.log("Setting room status for room:", roomStr, status);

			var room = rooms.getRoom(roomStr);
			room.setStatus(status);
			sendStatus(roomStr, status);
		}

		// Broadcast the Facilitator status to a room
		var sendStatus = function (roomStr, status) {
			var status = status || rooms.getRoom(roomStr).getStatus(),
				facOnline = rooms.getRoom(roomStr).getFacOnline();

			var data = {
				'status' : status,
				'facOnline' : facOnline
			}

			console.log('Sending status to room:', roomStr, 'status: ', data.status,  'facOnline', data.facOnline);

			io.to(roomStr).emit('updateStatus', data);
		}

		// Send Room List to users not yet logged in
		var broadCastRoomList = function ( ) {
			var roomList = rooms.getRoomList(),
				lobby = rooms.getLobby();

			for (var i = 0; i < lobby.length; i++) {
				io.to(lobby[i]).emit("get:room:list", roomList);
			}
		}

		var setRoomTitle = function ( roomStr, title ) {
			console.log("Setting new room title", roomStr, title);
			
			rooms.setTitle(roomStr, title);
			
			sendRoomTitle(roomStr, title);
			
			broadCastRoomList();
		}

		// Send title update to users logged in
		var sendRoomTitle = function (roomStr, title) {
			var title = title || rooms.getRoom(roomStr).getTitle(); // If title argument is undefined, default to whatever is on the room
			
			console.log("Broadcast room title:",title,"to room:",roomStr);

			io.to(roomStr).emit('get:room:title', title);
		}

		var facilitatorLogoutCleanup = function (user) {
			console.log("Facilitator logged out of room", roomStr)

			var roomStr = user.room;

			clearTimeout(TwoTruthsTimeout);
			clearTimeout(TwoMinuteWaring);
			
			user.getRoom().getUsers().forEach(function (usr) {
				console.log('usr:', usr);
				io.to(roomStr).emit('empathy:map:unready:up', usr.toJSON());
			});
			
			rooms.getRoom(roomStr).setActivity(undefined);
			setRoomStatus(roomStr, "");
			setRoomTitle(roomStr, "");
			sendStatus(roomStr, "Facilitator is idle");

			io.to(user.room).emit('end:activity'); 
			io.to(user.room).emit('set:anonymous:userlist', false);
		}

		var startingActivity = function (room) {
			rooms.getRoom(room).setUsersInActivity(true);
		}

		var endingActivity = function (room) {
			rooms.getRoom(room).setUsersInActivity(false);

			io.to(room).emit('update:inprogress', false);
		}

		socket.on('user:join:room', function(data) {
			try {
				console.log('socket://user:join:room ' + JSON.stringify(data));
				
				var roomStr = data.room,
					email = data.email,
					username = data.name,
					title = data.title;
				
				// Create the new user and add them to the room
				user = new User(socket.id, username, email);
				user.joinRoom(roomStr, socket);

				// Remove the user from lobby once they login
				rooms.removeFromLobby(user.id);

				// Refresh User Lists
				socket.emit('user:join:room');

				// Send status update if fac comes online
				if (username === 'Facilitator') {
					// Set title if provided
					if (title !== undefined && title !== '')
						setRoomTitle( roomStr, title );

					setRoomStatus(roomStr, "Facilitator is idle");
				}

				empathyMapSocket(io, socket, user);
			} catch (err) {
				console.log(err);
			}					
		});

		socket.on('disconnect', function() {
			try {
				// If a user on the login page disconnects do nothing, otherwise perfrom cleanup
				if (user) {
					console.log('socket://disconnect ' + user.name);
					
					var roomStr = user.room,
						room = rooms.getRoom(roomStr),
						username = 	user.name;
						
					room.removeUser(user);
					
					io.to(roomStr).emit('get:users:in:room', room.getUsers());
					
					if (username === 'Facilitator') {
						facilitatorLogoutCleanup(user);
					}
				}
			} catch (err) {
				console.log(err);
			}					
		});

		socket.on('set:room:title', function (data) {
			try {			
				console.log("Setting title to", data.title, "in room", data.room);
				setRoomTitle( data.room, data.title );
			} catch (err) {
				console.log(err);
			}				
		});

		socket.on('getSelf', function() {
			try {
				console.log('user has requested their information' + user);
				socket.emit('userInfoRequest', user);
			} catch (err) {
				console.log(err);
			}				
		});

		socket.on('get:users:in:room', function(room) {
			try {
				console.log('socket://get:users:in:room ' + JSON.stringify(room));
				io.to(room).emit('get:users:in:room', rooms.getRoom(room).getUsers());
			} catch (err) {
				console.log(err);
			}				
		});

		socket.on('get:inprogress', function (room) {
			try {
				var inProgress = (rooms.getRoom(room).getActivity() !== undefined);
				io.to(room).emit('update:inprogress', inProgress);
			} catch (err) {
				console.log(err);
			}				
		});

		socket.on('getIndvidualRoomList', function(room){
			try {			
				console.log('getIndvidualRoomList' + JSON.stringify(room));
				socket.emit('recieveIndividualRoomList', rooms.getRoom(room).getUsers());
			} catch (err) {
				console.log(err);
			}				
		});

		// Sends out the room attributes like 'title'
		socket.on('request:room:title', function (room) {
			try {
				sendRoomTitle(room);
			} catch (err) {
				console.log(err);
			}				
		});

		// Sends out data for populating the room selection drop down 
		socket.on('request:room:list', function () {
			try {
				var roomList = rooms.getRoomList();
				socket.emit('get:room:list', roomList);
			} catch (err) {
				console.log(err);
			}				
		});		

		socket.on('sendToRoom', function(room, message) {
			try {
				console.log("Message sent to room", "Room:"+room, "Message:"+message);
				io.to(room).emit('receiveMessage', message);
			} catch (err) {
				console.log(err);
			}				
		});

		socket.on('startActivity', function(room, parameters) {
			try {
				var now = new Date();

				if (parameters['activity-selection'] === "Polling"){
					var activity = new Polling(room);
					rooms.getRoom(room).setActivity(activity)
					startingActivity(room);
				}

				parameters['start_time'] = now;
				console.log("Room",room,"starting activity",parameters, now);
				io.to(room).emit('beginActivity', parameters, now);
			} catch (err) {
				console.log(err);
			}				
		});

		socket.on('broadCastData', function(room, data) {
			try {
				console.log("Activity data broadcast", room, data);
				io.to(room).emit('activityData', data);
			} catch (err) {
				console.log(err);
			}				
		});

		socket.on('activityControl', function(room, commandData) {
			try {
				console.log('Activity command room:' + room, 'Data:' + commandData);
				io.to(room).emit('receiveCommand', commandData);
			} catch (err) {
				console.log(err);
			}				
		});

		socket.on('sendAck', function(room, user) {
			try {
				console.log('Ack', 'room:'+room, 'user:'+user);
				io.to(room).emit('receiveAck', user);
			} catch (err) {
				console.log(err);
			}				
		});

		/* 
		* Polling
		*/
		socket.on('start:polling', function (room, params) {
			try {
				var activity = new Polling(io, room);

				// Set the starting user list
				var users = rooms.getUsers(room);
				params.users = users;

				activity.setParameters(params);
				rooms.setActivity(room, activity); // Set the current activity in the room

				activity.startActivity(room); // Start the activity
			} catch (err) {
				console.log(err);
			}				
		});		

		socket.on('sendVotes', function(room, params) {
			io.to(room).emit('votesSent', params);
		});

		socket.on('clear:readyup:polling', function(room) {	
			try {
				user.getRoom().getUsers().forEach(function (usr) {
					io.to(user.room).emit('empathy:map:unready:up', usr.toJSON());
				});
			} catch (err) {
				console.log(err);
			}				
		});

		socket.on('end:polling', function(room, results) {
			try {
				console.log("Ending polling in room:", room, "Results:", results);
				
				var activity = rooms.getRoom(room).getActivity();
					resultsHTML = activity.getPollingFormattedHTML(results);

				var users = rooms.getRoom(room).getUsers();
				
				var emails = users.filter(function (usr) {
				    return usr.email != undefined && usr.email != '';
				}).map(function (usr) {
				    return usr.email;
				}).join(',');
				
				transporter.sendMail({
					from: 'vmof.email@gmail.com',
					to: emails,
					subject: 'Meeting Results', //TODO: make this the meeting title - activity
					html: resultsHTML,
				});

				endingActivity(room);
				rooms.getRoom(room).setActivity(undefined);
				io.to(room).emit('end:activity');
			} catch (err) {
				console.log(err);
			}					
		});

		socket.on('pollingNextPhase', function(room, params) {
			try {
				io.to(room).emit('nextPhase', params);
			} catch (err) {
				console.log(err);
			}				
		});

		/*
		*  Meeting Status
		*/
		socket.on('updateMeetingStatus', function(roomStr, status) {
			try {
				setRoomStatus(roomStr, status);
			} catch (err) {
				console.log(err);
			}				
		});

		socket.on('getStatus', function(roomStr) {
			try {
				sendStatus(roomStr);
			} catch (err) {
				console.log(err);
			}				
		});

		/*
		*  TwoTruths
		*/

		socket.on('start:TwoTruths', function(room, params) {
			try {				
				var activity = new TwoTruths(room);
				console.log('params:' + JSON.stringify(params));
				console.log('start:TwoTruths');
				var peopleNum = rooms.getRoom(room).getUsers().length-1;
				var timeLimit = activity.setTimer(params, peopleNum);
				var timeToWarn;

				rooms.getRoom(room).setActivity(activity);
				startingActivity(room);

				TwoTruthsTimeout = setTimeout(function(){
					console.log("ending stuff"  + room); 
					setRoomStatus(room, "Facilitator is idle");
					rooms.getRoom(room).setActivity(undefined);
					user.getRoom().getUsers().forEach(function (usr) {
						usr.unReady();
						console.log('usr:', usr);
						io.to(user.room).emit('empathy:map:unready:up', usr.toJSON());
					});
					io.to(room).emit('end:activity'); 
				}, timeLimit);

				//Two minute warning logic, if less the 2 minutes the warning is sent out
				// after 30s
				if(timeLimit <= 120000){
					timeToWarn = 30000;
				} else {
					timeToWarn = timeLimit - 120000;
				}

				TwoMinuteWaring = setTimeout(function(){
					console.log("Two Minute Waring");
					io.to(room).emit('countDown:warning');
				}, timeToWarn);
				console.log("The two truths activity is " + JSON.stringify(rooms.getRoom(room).getActivity()));
				io.to(room).emit('start:TwoTruths', params);
			} catch (err) {
				console.log(err);
			}					
		});

		socket.on('end:TwoTruths', function(room) {	
			try {							
				console.log("end:TwoTruths " + room);
				clearTimeout(TwoTruthsTimeout);
				clearTimeout(TwoMinuteWaring);
				//rooms.getRoom(room).getActivity().endActivity();
				rooms.getRoom(room).setActivity(undefined);

				io.to(room).emit('end:activity');

				user.getRoom().getUsers().forEach(function (usr) {
					usr.unReady();
					io.to(user.room).emit('empathy:map:unready:up', usr.toJSON());
					console.log('usr:', usr);
				});

				endingActivity(room);
			} catch (err) {
				console.log(err);
			}			
		});

		socket.on('revealLie', function(room, data) {
			try {							
				console.log('Ack', 'room:'+room, data);
				io.to(room).emit('showLie', data);
			} catch (err) {
				console.log(err);
			}
		});

		socket.on('readyUp', function(room, data) {
			try {							
				console.log("Send readyUP status", room, data);
				console.log(rooms.getRoom(room).getActivity());
				rooms.getRoom(room).getActivity().readyUp();

				console.log(user);

				user.readyUp();
				console.log("sending out two truth read up");
				console.log("The two truths activity is " + JSON.stringify(rooms.getRoom(room).getActivity()));
				io.to(room).emit('twoTruths:ready:up', user.toJSON());
			} catch (err) {
				console.log(err);
			}			
		});

		socket.on('twoTruths:nextPhase', function(room, data) {
			try {							
				console.log("Broadcasting stuff for twotruths", room, data);
				user.getRoom().getUsers().forEach(function (usr) {
					usr.unReady();
					console.log('usr:', usr);
					io.to(user.room).emit('empathy:map:unready:up', usr.toJSON());
				});
				console.log("The two truths activity is " + JSON.stringify(rooms.getRoom(room).getActivity()));
				io.to(room).emit('activityData', data);
			} catch (err) {
				console.log(err);
			}				
		});

		/* 
		* Anonymous Brainstorming
		*/
		socket.on('start:anonymous:brainstorming', function (room, params) {
			try {							
				var activity = new AnonymousBrainstorming(io, room);
				
				// Set the starting user list
				var users = rooms.getRoom(room).getUsers();
				
				params.users = users;

				activity.setParameters(params);
				rooms.getRoom(room).setActivity(activity); // Set the current activity in the room

				activity.startActivity(room); // Start the activity

				startingActivity(room);
			} catch (err) {
				console.log(err);
			}
		});

		// Get one idea
		socket.on('send:anonymous:idea', function (room, idea) {
			try {				
				var activity = rooms.getRoom(room).getActivity();
				//var activity = rooms.getActivity(room);
				activity.addIdea(idea);
			} catch (err) {
				console.log(err);
			}
		});

		// Get all ideas at once
		socket.on('send:anonymous:ideas', function (room, ideas) {
			try {			
				var activity = rooms.getRoom(room).getActivity();
				//var activity = rooms.getActivity(room);
				activity.addIdeas(ideas);
			} catch (err) {
				console.log(err);
			}
		});

		socket.on('send:anonymous:actionitem', function (room, data) {
			try {	
				var activity = rooms.getRoom(room).getActivity();
				//var activity = rooms.getActivity(room);
				activity.addActionItem(data);
			} catch (err) {
				console.log(err);
			}
		});

		socket.on('send:anonymous:cmd:next', function (room) {
			try {
				var activity = rooms.getRoom(room).getActivity();
				//var activity = rooms.getActivity(room);
				activity.next();
			} catch (err) {
				console.log(err);
			}
		});

		// End Brainstorming acitivity
		socket.on('send:anonymous:cmd:end', function (room) {
			try {
				console.log("Ending Anonymous Brainstorming in room", room)

				var activity = rooms.getRoom(room).getActivity();

				users = rooms.getRoom(room).getUsers();
				
				endingActivity(room);
				
				var emails = users.filter(function (usr) {
				    return usr.email != undefined && usr.email != '';
				}).map(function (usr) {
				    return usr.email;
				}).join(',');
				
				var image = activity.getImage(),
					attachments = [];

				if ( image !== undefined && image !== "") {
					attachments = [{
						filename: 'topicImage.jpg',
						content: activity.getImage().split('base64,')[1],
						encoding: 'base64',
						cid: 'imageTopic@kreata.ee'
					}]
				}
				
				transporter.sendMail({
					from: 'vmof.email@gmail.com',
					to: emails,
					subject: 'Meeting Results', //TODO: make this the meeting title - activity
					html: activity.getFormattedResults(),
					attachments: attachments
				});

				// Email action items to the assigned 
				var actionItems = activity.getActionItems();
				for (var i = 0; i < actionItems.length; i++) {
					var emailAddr = actionItems[i].actionItem.userObj.email;
					if ( emailAddr !== '' ) {
						transporter.sendMail({
							from: 'vmof.email@gmail.com',
							to: emailAddr,
							subject: 'Action item assigned to you', 
							html: activity.getActionItemHTML(actionItems[i])
						});
					}
				}

				activity.end();
				setRoomStatus(room, "Facilitator is idle");
				rooms.getRoom(room).setActivity(undefined);
			} catch (err) {
				console.log(err);
			}
		});

		socket.on('send:anonymous:ack', function (room) {
			try {			
				var activity = rooms.getRoom(room).getActivity();
				//var activity = rooms.getActivity(room);
				activity.userReady();
			} catch (err) {
				console.log(err);
			}			
		});

		socket.on('send:anonymous:vote', function (room, data) {
			try {			
				var activity = rooms.getRoom(room).getActivity();
				//var activity = rooms.getActivity(room);
				activity.addVote(data);
			} catch (err) {
				console.log(err);
			}
		});

		socket.on('send:anonymous:comment', function (room, data) {
			try {			
				var activity = rooms.getRoom(room).getActivity();
				//var activity = rooms.getActivity(room);
				activity.addComment(data);
			} catch (err) {
				console.log(err);
			}				
		});
	});
}
