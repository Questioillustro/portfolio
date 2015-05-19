var React = require('react/addons');
var Post = require('./app.models.Post');
// var io;
// var socket;
// if (!(typeof module !== 'undefined' && module.exports)) {
// 	io = require('alias_socket_io');
// 	socket = io.connect();
// }

PostEmpathyMap.prototype = new Post();
PostEmpathyMap.prototype.constructor = PostEmpathyMap;

function PostEmpathyMap(content, section, user) {
	//this.post = new Post(content);
	Post.call(this, content);
	this.section = section;
	this.user = user;
};

PostEmpathyMap.prototype.getSection = function () {
	return this.section;
};

PostEmpathyMap.prototype.getUser = function () {
	return this.user;
};

// PostEmpathyMap.prototype.addComment = function (comment) {
// 	this.post.addComment(comment);
// };

// PostEmpathyMap.prototype.upVote = function () {
// 	this.post.upVote();
// };

// PostEmpathyMap.prototype.downVote = function () {
// 	this.post.downVote();
// };

PostEmpathyMap.prototype.toJSON = function () {
	//return {this.content, this.comments, this.votes, this.section};
	return { content: this.getContent(), 
			 comments: this.getComments(),
			 votes: this.getVotes(),
			 section: this.getSection(),
			 user: this.getUser() };
			 // user: this.getUser().toJSON() };
};

// Note: Never called this method outside this class. Use fromJSON().
PostEmpathyMap.prototype.injectJSON = function (json) {
	this.content = json.content;
	this.comments = json.comments;
	this.votes = json.votes;
	this.section = json.section;
	this.user = json.user;
};

PostEmpathyMap.fromJSON = function (json) {
	var postEmpathyMap = new PostEmpathyMap();
	postEmpathyMap.injectJSON(json);
	return postEmpathyMap;
};

var PostEmpathyMapComponent = React.createClass({

	//getInitialState: function () {
		//var io = require('alias_socket_io');
		//socket = io.connect();
		//return { socket: socket, votes: 0 };
	//	return { votes: 0 };
	//},

	getInitialState: function () {
		return { comment: '' };
	},

	componentWillMount: function () {
		require('./app.models.PostEmpathyMap.styl');
	},

	componentDidMount: function () {
		//this.state.socket.on('empathy:map:vote', this.updateVote);
		var self = this;
		$('#button-ready').click(function () {
			console.log('button-ready pressed');
			if (self.state.comment !== '') {
				self.props.post.addComment(self.state.comment);
			}
		});
	},

	componentWillUnmount: function () {
        //this.state.socket.removeAllListeners('empathy:map:vote');
    },

    // updateVote: function (postJSON) {
    // 	if (postJSON.se)
    // 	this.setState({ votes: postJSON.votes });
    // },

	upVote: function (e) {
		//this.setState({ votes: ++this.state.votes });
		//console.log('e: ', e);
		//console.log('e.target: ', e.target);
		//$(e.target).removeClass('toggled');
		//console.log('e.target.siblings: ', $(e.target).siblings());
		//console.log('e.target.parent.siblings: ', $(e.target).parent().siblings());
		$(e.target).parent().siblings().children('span').removeClass('toggled');
		if ($(e.target).hasClass('toggled')) {
			$(e.target).removeClass('toggled');
			this.props.post.clearVote();
		} else {
			$(e.target).addClass('toggled');
			this.props.post.upVote();
		}

		//this.state.socket.emit('empathy:map:vote', this.props.post.toJSON());
	},

	downVote: function (e) {
		//this.setState({ votes: --this.state.votes });
		//$(e.target).removeClass('toggled');
		// $(e.target).siblings().removeClass('toggled');
		$(e.target).parent().siblings().children('span').removeClass('toggled');
		if ($(e.target).hasClass('toggled')) {
			$(e.target).removeClass('toggled');
			this.props.post.clearVote();
		} else {
			$(e.target).addClass('toggled');
			this.props.post.downVote();
		}
	},

	commentChange: function (e) {
		console.log('commentChange:', $(e.target).val());
		this.setState({ comment: $(e.target).val() });
	},

    render: function () {
    	//console.log('yoooo here');
	    return (
			<li key={this.props.post.content}>
				<div className="vf-post-em-item">
					<div className="vf-post-em-vote">
						<a href="javascript:void(0);" onClick={this.upVote}><span className="octicon octicon-triangle-up vf-icon-custom"></span></a><br/>
						<a href="javascript:void(0);" onClick={this.downVote}><span className="octicon octicon-triangle-down vf-icon-custom"></span></a>
						{/*<a href="javascript:void(0);" onClick={this.upVote}><span className="fa fa-caret-up fa-4x vf-icon-caret"></span></a><br/>
						<a href="javascript:void(0);" onClick={this.downVote}><span className="fa fa-caret-down fa-4x vf-icon-caret"></span></a>*/}
						{/*<a href="javascript:void(0);" onClick={this.upVote}><span className="glyphicon glyphicon-chevron-up vf-glyphicon-large"></span></a><br/>
						<a href="javascript:void(0);" onClick={this.downVote}><span className="glyphicon glyphicon-chevron-down vf-glyphicon-large vf-glyphicon-align"></span></a>*/}
					</div>
					<div className="vf-post-em-margin-bottom"><b style={{color: this.props.post.user.color}}>{this.props.post.user.name}:</b> {this.props.post.content}</div>
					<input className="form-control" placeholder="Write a comment..." maxlength="10000" onChange={this.commentChange}></input>
				</div>					
			</li>
		)
    }
});


var PostEmpathyMapComponent2 = React.createClass({

    render: function () {
    	function renderComments(comment, i) {
    		return <li key={i} className="vf-post-em-comment"><b style={{color: comment.user.color}}>{comment.user.name}:</b> {comment.content}</li>;
    	}
	    return (
			<li key={this.props.post.content}>	
				{/*<div className="vf-post-em-item" style={{backgroundColor: this.props.post.user.color}}>*/}
				<div className="vf-post-em-item">
					<div className="vf-post-em-vote-count">{this.props.post.votes}</div>
					<div className="vf-post-em-contents"><b style={{color: this.props.post.user.color}}>{this.props.post.user.name}:</b> {this.props.post.content}</div>
					<ul className="vf-post-em-comments-list">
						{this.props.post.comments.map(renderComments)}
					</ul>
				</div>
			</li>
		)
    }

});



// var PostEmpathyMapComponent = React.createClass({

// 	//getInitialState: function () {
// 		//var io = require('alias_socket_io');
// 		//socket = io.connect();
// 		//return { socket: socket, votes: 0 };
// 	//	return { votes: 0 };
// 	//},

// 	componentWillMount: function () {
// 		require('./app.models.PostEmpathyMap.styl');
// 	},

// 	componentDidMount: function () {
// 		//this.state.socket.on('empathy:map:vote', this.updateVote);
// 	},

// 	componentWillUnmount: function () {
//         //this.state.socket.removeAllListeners('empathy:map:vote');
//     },

//     // updateVote: function (postJSON) {
//     // 	if (postJSON.se)
//     // 	this.setState({ votes: postJSON.votes });
//     // },

// 	upVote: function (e) {
// 		//this.setState({ votes: ++this.state.votes });
// 		//console.log('e: ', e);
// 		//console.log('e.target: ', e.target);
// 		//$(e.target).removeClass('toggled');
// 		//console.log('e.target.siblings: ', $(e.target).siblings());
// 		//console.log('e.target.parent.siblings: ', $(e.target).parent().siblings());
// 		$(e.target).parent().siblings().children('span').removeClass('toggled');
// 		if ($(e.target).hasClass('toggled')) {
// 			$(e.target).removeClass('toggled');
// 			this.props.post.clearVote();
// 		} else {
// 			$(e.target).addClass('toggled');
// 			this.props.post.upVote();
// 		}

// 		//this.state.socket.emit('empathy:map:vote', this.props.post.toJSON());
// 	},

// 	downVote: function (e) {
// 		//this.setState({ votes: --this.state.votes });
// 		//$(e.target).removeClass('toggled');
// 		// $(e.target).siblings().removeClass('toggled');
// 		$(e.target).parent().siblings().children('span').removeClass('toggled');
// 		if ($(e.target).hasClass('toggled')) {
// 			$(e.target).removeClass('toggled');
// 			this.props.post.clearVote();
// 		} else {
// 			$(e.target).addClass('toggled');
// 			this.props.post.downVote();
// 		}
// 	},

//     render: function () {
//     	//console.log('yoooo here');
// 	    return (
// 				<li key={this.props.post.content} className="vf-post list-group-item">
// 	 			<div className="well">
// 					<ul className="media-list">
// 						<li className="media">
// 							<div className="media-left">
// 									<div className="media-object">
// 										<a href="javascript:void(0);" onClick={this.upVote}><span className="glyphicon glyphicon-chevron-up vf-glyphicon-large"></span></a>
// 										<a href="javascript:void(0);" onClick={this.downVote}><span className="glyphicon glyphicon-chevron-down vf-glyphicon-large"></span></a>
// 									</div>
// 							</div>
// 							<div className="media-body">
// 								<div className="vf-post-contents">
// 									{this.props.post.content}
// 								</div>
// 								<input className="form-control vf-margin-top" placeholder="Write a comment..."></input>
// 							</div>
// 						</li>
// 					</ul>
// 				</div>
// 			</li>
// 		)
//     }
// });

// var PostEmpathyMapComponent2 = React.createClass({

//     render: function () {
// 	    return (
// 			<li key={this.props.post.content} className="vf-post list-group-item">
// 	 			<div className="well">
// 					<div className="vf-post-vote-count">{this.props.post.votes}</div>
// 					<div className="vf-post-contents">
// 						{this.props.post.content}
// 					</div>
// 				</div>
// 			</li>
// 		)
//     }

// });


// var PostEmpathyMapComponent2 = React.createClass({

//     render: function () {
// 	    return (
// 			<li key={this.props.post.content} className="vf-post list-group-item">
// 	 			<div className="well">
// 					<ul className="media-list">
// 						<li className="media">
// 							<div className="media-left">
// 								<div className="media-object">
// 									<div className="vf-post-vote-count">{this.props.post.votes}</div>
// 								</div>
// 							</div>
// 							<div className="media-body">
// 								<div className="vf-post-contents">
// 									{this.props.post.content}
// 								</div>
// 							</div>
// 						</li>
// 					</ul>
// 				</div>
// 			</li>
// 		)
//     }

// });


	// downVote: function (e) {
	// 	//this.setState({ votes: --this.state.votes });
	// 	$('#vf-upvote').removeClass('toggled');
	// 	if ($('#vf-downvote').hasClass('toggled')) {
	// 		$('#vf-downvote').removeClass('toggled');
	// 		this.props.post.upVote();
	// 	} else {
	// 		$('#vf-downvote').addClass('toggled');
	// 		this.props.post.downVote();
	// 	}
	// },

  //   render: function () {
  //   	//console.log('yoooo here');
	 //    return (
		// 		<li key={this.props.post.content} className="vf-post list-group-item">
	 // 			<div className="well">
		// 			<ul className="media-list">
		// 				<li className="media">
		// 					<div className="media-left">
		// 							<div className="media-object">
		// 								<a href="javascript:void(0);" onClick={this.upVote}><span className="glyphicon glyphicon-chevron-up vf-glyphicon-large"></span></a>
		// 								<div className="vf-post-vote-count">{this.state.votes}</div>
		// 								<a href="javascript:void(0);" onClick={this.downVote}><span className="glyphicon glyphicon-chevron-down vf-glyphicon-large"></span></a>
		// 							</div>
		// 					</div>
		// 					<div className="media-body">
		// 						<div className="vf-post-contents">
		// 							{this.props.post.content}
		// 						</div>
		// 						<input className="form-control vf-margin-top" placeholder="Write a comment..."></input>
		// 					</div>
		// 				</li>
		// 			</ul>
		// 		</div>
		// 	</li>
		// )
  //   }

// PostEmpathyMap.prototype.toJSX = function () {
//     return (
// 			<li key={this.content} className="vf-post list-group-item">
//  			<div className="well">
// 				{/*<h3>Where should we eat lunch today?</h3> post.phase*/}
// 				<h3>{this.section}</h3>
// 				<ul className="media-list">
// 					<li className="media">
// 						<div className="media-left">
// 								<div className="media-object">
// 									<a href="#"><span className="glyphicon glyphicon-chevron-up vf-glyphicon-large"></span></a>
// 									<div className="vf-post-vote-count">{this.votes}</div>
// 									<a href="#"><span className="glyphicon glyphicon-chevron-down vf-glyphicon-large"></span></a>
// 								</div>
// 						</div>
// 						<div className="media-body">
// 							<div className="vf-post-contents">
// 								<h4 className="media-heading">Andrew Landman</h4>
// 								{this.content}
// 							</div>
// 							{/*<div className="vf-post-comment">
// 								<h5 className="media-heading">Steve Brewster</h5>
// 								Yeahhh
// 							</div>*/}
// 							<input className="form-control vf-margin-top" placeholder="Write a comment..."></input>
// 						</div>
// 					</li>
// 				</ul>
// 			</div>
// 		</li>
// 	)
// };


//PostEmpathyMap.prototype.render = function (location) {
	// React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
    // React.render(<PostEmpathyMapComponent self={this} />, document.getElementById('vf-activity-content'));
//    React.render(<PostEmpathyMapComponent self={this} />, location);
//};

//PostEmpathyMap.prototype.unmount = function (location) {
	// React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
//	React.unmountComponentAtNode(location);
//};

module.exports = PostEmpathyMap;
module.exports.component = PostEmpathyMapComponent;
module.exports.component2 = PostEmpathyMapComponent2;