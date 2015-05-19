// var EmpathyMap = require('./app.activities.EmpathyMap');
//var EmpathyMap = require('./app.models.EmpathyMapActivity.js');
var nodemailer = require('nodemailer');
var ActivityEmpathyMap = require('./app.models.ActivityEmpathyMap.js');
var CommentaryEmpathyMap = require('./app.models.CommentaryEmpathyMap.jsx');

var transporter = nodemailer.createTransport({
	service: 'gmail',
	auth: {
		user: 'vmof.email@gmail.com',
		pass: 'mofv-8072'
	}
});

// var EmpathyMapSocket = function (io, socket, rooms, usr) {
function EmpathyMapSocket(io, socket, usr) {

	var user = usr;

	//this.colorMap = {};
	//this.colorIndex = 4;
	this.colorList = [ '#fbb4ae',
		           	   '#b3cde3',
			    	   '#ccebc5',
				       '#decbe4',
				       '#fed9a6',
				       '#ffffcc',
				       '#e5d8bd',
				       '#fddaec' ];
	var self = this;

	socket.on('empathy:map:validate', function () {
		// io.to(user.room).emit('empathy:map:validate', !(rooms.getRoom(user.room).getNumberOfUsers() <= 8));
		io.to(user.room).emit('empathy:map:validate', !(rooms.getRoom(user.room).getNumberOfUsers() > 0 &&
														rooms.getRoom(user.room).getNumberOfUsers() <= 8));
	});

	socket.on('empathy:map:start', function (room, params) {
		//self.colorMap = {};
		// self.colorIndex = 0;
		// if (self.colorIndex < 7) {
		// 	user.color = self.colorList[self.colorIndex];
		// 	self.colorIndex++;
		// }

		console.log("empathy map starting in room", room);
		console.log('user is:', user, '---', JSON.stringify(user));
		var activityEmpathyMap = new ActivityEmpathyMap();
		activityEmpathyMap.setStatus('empathymap');
		rooms.getRoom(room).setActivity(activityEmpathyMap);
		rooms.getRoom(room).getUsers().forEach(function (usr, i) {
			if (i <= 7) {
				usr.color = self.colorList[i];
			}
			if (user.id === usr.id) {
				user.color = usr.color;
			}
			rooms.getRoom(room).getActivity().addUserToActivity(usr);
		});

		//self.colorMap[user.id] = self.colorMap[user.id] || self.colorList[self.colorIndex];

		//io.to(room).emit('empathy:map:start', this.state);
		rooms.getRoom(room).setUsersInActivity(true);
		io.to(room).emit('empathy:map:set:color', rooms.getRoom(room).getActivity().usersToJSON());
		io.to(room).emit('empathy:map:start');
	});

	socket.on('empathy:map:proceed', function () {
		var activityEmpathyMap = rooms.getRoom(user.room).getActivity();
		activityEmpathyMap.clearUsersReady(user);
		var status = activityEmpathyMap.getStatus();
		console.log('empathy map proceed:', status);
		switch(status) {
			case 'empathymap':
				activityEmpathyMap.setStatus('commentary');
				user.getRoom().getUsers().forEach(function (usr) {
					console.log('usr:', usr);
					io.to(user.room).emit('empathy:map:unready:up', usr.toJSON());
				});
				console.log('facilitator next phase proceed empathymap');
				io.to(user.room).emit('empathy:map:facilitator:next:phase');
				io.to(user.room).emit('empathy:map:next:phase', user.getActivity().empathyMap.toJSON());
				break;
			case 'commentary':
				activityEmpathyMap.setStatus('discussion');
				user.getRoom().getUsers().forEach(function (usr) {
					console.log('usr:', usr);
					io.to(user.room).emit('empathy:map:unready:up', usr.toJSON());
				});
				if(activityEmpathyMap.commentary === undefined) {
					activityEmpathyMap.commentary = {
						think: [],
						hear: [],
						see: [],
						say: [],
						pain: [],
						gain: []
					};
				}
				console.log('facilitator next phase proceed commentary');
				io.to(user.room).emit('empathy:map:facilitator:next:phase');
				io.to(user.room).emit('empathy:map:next:phase', activityEmpathyMap.commentary);
				break;
			case 'discussion':
				emailResults();
				io.to(user.room).emit('empathy:map:unset:color', rooms.getRoom(user.room).getActivity().usersToJSON());
				rooms.getRoom(user.room).setActivity(undefined);
				rooms.getRoom(user.room).setUsersInActivity(false);
				// user.getRoom().getUsers().forEach(function (usr) {
				// 	io.to(user.room).emit('empathy:map:unset:color', usr.toJSON());
				// });
				io.to(user.room).emit('end:activity');
				break;
			default:
				break;
		}
	});

	socket.on('empathy:map:submit:idea', function (room, idea, section) {
		var activityEmpathyMap = rooms.getRoom(user.room).getActivity();
		activityEmpathyMap.submitIdea(idea, section, user);
		
		console.log('user color:', user.color);
		io.to(room).emit('empathy:map:submit:idea', section, idea, user.toJSON());
		// console.log('colorMap:', self.colorMap, ' colorList:', self.colorList, ' colorIndex:', self.colorIndex);
		// self.colorMap[user.id] = self.colorMap[user.id] || self.colorList[self.colorIndex];
		// if (self.colorIndex < 7) {
		// 	self.colorIndex++;
		// }
		// io.to(room).emit('empathy:map:submit:idea', section, idea, { color: self.colorMap[user.id] });
	});

	socket.on('empathy:map:ready:up', function (room) {
		if (user.readyUp()) {
			//io.to(user.room).emit('empathy:map:next:phase', user.getActivity().empathyMap.toPosts().toJSON());
			var activityEmpathyMap = rooms.getRoom(user.room).getActivity();
			activityEmpathyMap.setStatus('commentary');
			user.getRoom().getUsers().forEach(function (usr) {
				console.log('usr:', usr);
				io.to(user.room).emit('empathy:map:unready:up', usr.toJSON());
			});
			console.log('facilitator next phase ready up');
			io.to(user.room).emit('empathy:map:facilitator:next:phase');
			io.to(user.room).emit('empathy:map:next:phase', user.getActivity().empathyMap.toJSON());
			//io.to(user.room).emit('empathy:map:unready:up', user.toJSON());
		} else {
			io.to(user.id).emit('empathy:map:wait');
			io.to(user.room).emit('empathy:map:ready:up', user.toJSON());
		}
	});

	socket.on('empathy:map:submit:commentary', function (commentaryJSON) {
		var activityEmpathyMap = rooms.getRoom(user.room).getActivity();
		//var commentaryEmpathyMap = CommentaryEmpathyMap.fromJSON(commentaryJSON);
		console.log('commentaryJSON:', JSON.stringify(commentaryJSON));
		commentaryJSON['think'].forEach(function (postJSON) {
			postJSON.comments = postJSON.comments.map(function (comment) {
				return {content: comment, user: user};
			});
		});
		commentaryJSON['hear'].forEach(function (postJSON) {
			postJSON.comments = postJSON.comments.map(function (comment) {
				return {content: comment, user: user};
			});
		});
		commentaryJSON['see'].forEach(function (postJSON) {
			postJSON.comments = postJSON.comments.map(function (comment) {
				return {content: comment, user: user};
			});
		});
		commentaryJSON['say'].forEach(function (postJSON) {
			postJSON.comments = postJSON.comments.map(function (comment) {
				return {content: comment, user: user};
			});
		});
		commentaryJSON['pain'].forEach(function (postJSON) {
			postJSON.comments = postJSON.comments.map(function (comment) {
				return {content: comment, user: user};
			});
		});
		commentaryJSON['gain'].forEach(function (postJSON) {
			postJSON.comments = postJSON.comments.map(function (comment) {
				return {content: comment, user: user};
			});
		});
		activityEmpathyMap.addCommentary(commentaryJSON, user);
		if (user.readyUp()) {
			//io.to(user.room).emit('empathy:map:next:phase', user.getActivity().empathyMap.toPosts().toJSON());
			var activityEmpathyMap = rooms.getRoom(user.room).getActivity();
			activityEmpathyMap.setStatus('discussion');
			user.getRoom().getUsers().forEach(function (usr) {
				io.to(user.room).emit('empathy:map:unready:up', usr.toJSON());
			});
			if(activityEmpathyMap.commentary === undefined) {
				activityEmpathyMap.commentary = {
					think: [],
					hear: [],
					see: [],
					say: [],
					pain: [],
					gain: []
				};
			}
			activityEmpathyMap.sortCommentaryByVotes();
			console.log('facilitator next phase submit commentary');
			io.to(user.room).emit('empathy:map:facilitator:next:phase');
			io.to(user.room).emit('empathy:map:next:phase', activityEmpathyMap.commentary);
			//io.to(user.room).emit('empathy:map:unready:up', user.toJSON());
		} else {
			io.to(user.id).emit('empathy:map:wait');
			io.to(user.room).emit('empathy:map:ready:up', user.toJSON());
		}
	});

	socket.on('empathy:map:end:activity', function () {
		emailResults();

		rooms.getRoom(user.room).setUsersInActivity(false);
		
		user.getRoom().getUsers().forEach(function (usr) {
			io.to(user.room).emit('empathy:map:unready:up', usr.toJSON());
		});
		
		io.to(user.room).emit('empathy:map:unset:color', rooms.getRoom(user.room).getActivity().usersToJSON());
		
		rooms.getRoom(user.room).setActivity(undefined);
		//console.log('user.room:', user.room);
		// socket.emit('updateMeetingStatus', user.room, "Facilitator is idle");
		//socket.emit('updateStatus', user.room, "Facilitator is idle");
		// user.getRoom().getUsers().forEach(function (usr) {
		// 	io.to(user.room).emit('empathy:map:unset:color', usr.toJSON());
		// });
		io.to(user.room).emit('end:activity');
		// user.room.getUsers().forEach(function (usr) {
		// 	io.to(usr.id).emit('empathy:map:unready:up', usr.toJSON());
		// });
	});

	// socket.on('empathy:map:vote', function (postJSON) {
	// 	io.to(user.room).emit('empathy:map:vote', postJSON);
	// });

	var emailResults = function () {
		var results = rooms.getRoom(user.room).getActivity().getCommentary();
		
		if (results === undefined)
			return;

		var formattedHTML = formatResultsHTML(results);
		
		console.log('empathy map data to be used for email', results);

		users = rooms.getRoom(user.room).getUsers();
			
		var emails = users.filter(function (usr) {
		    return usr.email != undefined && usr.email != '';
		}).map(function (usr) {
		    return usr.email;
		}).join(',');

		transporter.sendMail({
			from: 'vmof.email@gmail.com',
			to: emails,
			subject: 'Meeting Results', //TODO: make this the meeting title - activity
			html: formattedHTML,
		});
	}

	var formatResultsHTML = function (results) {
		var categories = Object.keys(results),
			returnHTML = '<div style="background-color: #2b3e50; color: white; padding: 10px;">';
	
		returnHTML += '<h1 style="background-color: #4e5d6c; height: 50px; padding: 10px; color:white;">vMOF - Empathy Map</h1>';

		returnHTML += '<h2 style="color:white;">Ideas Generated</h2><hr/>';


		// Itergate categories
		for (var i = 0; i < categories.length; i++) {
			var cat = categories[i];

			if (results[cat].length === 0)
				continue;
			
			// Category heading
			returnHTML += '<h3 style="background-color: #df691a; height: 40px; padding: 10px;">' + cat + '</h3>';

			// Iterate ideas per category
			for (var j = 0; j < results[cat].length; j++) {
				var post = results[cat][j];
				returnHTML += '<h4 style="background-color: #4e5d6c; height: 30px; padding: 10px;">';
				returnHTML += '<span style="color: #5bc0de; font-size: 20px;">' + post['votes'] + '</span> - ' + post['user']['name'] + ': ' + post['content'] + '</h4>'

				// Iterate comments per idea
				for (var x = 0; x < post['comments'].length; x++) {
					returnHTML += '<h5 style="padding: 10px;">' + post['comments'][x]['user']['name'] + ': ' + post['comments'][x]['content'] + '</h5>';
				}
			}
		}

		returnHTML += '</div>';

		return returnHTML;
	}
};

// EmpathyMapSocket.prototype.setUser = function (user) {

// };

module.exports = EmpathyMapSocket;


// module.exports = function (io, socket, rooms) {

// 	socket.on('empathy:map:start', function (room, params) {
// 		// var activity = new EmpathyMap(io, room);
// 		var activity = new EmpathyMap(io);
// 		console.log("empathy map params: " + JSON.stringify(params));
// 		// Set the starting user list
// 		var users = rooms.getUsers(room);
// 		params.users = users;
		
// 		activity.setParameters(params);
// 		rooms.setActivity(room, activity); // Set the current activity in the room

// 		activity.start(room); // Start the activity
// 	});

// 	socket.on('empathy:map:submit:idea', function (room, idea, section) {
// 		console.log('a');
// 		var activity = rooms.getActivity(room);
// 		console.log('b');
// 		activity.submitIdea(idea, section);
// 		console.log('c');
// 		console.log(idea);
// 	});

// 	socket.on('empathy:map:ready:up', function (room) {
// 		var activity = rooms.getActivity(room);
// 		activity.readyUp(user.id);
// 	});

// }