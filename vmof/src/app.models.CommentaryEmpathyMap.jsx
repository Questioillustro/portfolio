var React = require('react/addons');
//var Posts = require('./app.models.Posts');
var PostsEmpathyMap = require('./app.models.PostsEmpathyMap.jsx');
var PostsEmpathyMapComponent = PostsEmpathyMap.component;
//var Waiting = require('./app.components.Waiting.jsx');
var DiscussionEmpathyMap = require('./app.models.DiscussionEmpathyMap.jsx');

function CommentaryEmpathyMap(postsJSON) {
	this.think = PostsEmpathyMap.fromJSON(postsJSON['think']);
	this.hear = PostsEmpathyMap.fromJSON(postsJSON['hear']);
	this.see = PostsEmpathyMap.fromJSON(postsJSON['see']);
	this.say = PostsEmpathyMap.fromJSON(postsJSON['say']);
	this.pain = PostsEmpathyMap.fromJSON(postsJSON['pain']);
	this.gain = PostsEmpathyMap.fromJSON(postsJSON['gain']);
};

// CommentaryEmpathyMap.prototype.addCommentary = function (commentary) {
// 	commentary.think.forEach(function (postEmapth) {

// 	});
// };


CommentaryEmpathyMap.prototype.toJSON = function () {
	return { think: this.think.toJSON(),
			 hear: this.hear.toJSON(),
			 see: this.see.toJSON(),
			 say: this.say.toJSON(),
			 pain: this.pain.toJSON(),
			 gain: this.gain.toJSON() };
};

// Note: Never called this method outside this class. Use fromJSON().
CommentaryEmpathyMap.prototype.injectJSON = function (json) {
	this.think = PostsEmpathyMap.fromJSON(json['think']);
	this.hear = PostsEmpathyMap.fromJSON(json['hear']);
	this.see = PostsEmpathyMap.fromJSON(json['see']);
	this.say = PostsEmpathyMap.fromJSON(json['say']);
	this.pain = PostsEmpathyMap.fromJSON(json['pain']);
	this.gain = PostsEmpathyMap.fromJSON(json['gain']);
	// console.log('json is:', json);
	// console.log('json2 is:', JSON.stringify(json));
	// this.think = json['think'].map(function (postJSON) {
	// 	return new PostEmpathyMap(postJSON.content, postJSON.section);
	// });
	// this.posts = json.map(function (postJSON) {
	// 	return new PostEmpathyMap(postJSON.content, postJSON.section);
	// });
};

CommentaryEmpathyMap.fromJSON = function (json) {
	var commentaryEmpathyMap = new CommentaryEmpathyMap();
	commentaryEmpathyMap.injectJSON(json);
	return commentaryEmpathyMap;
};

// CommentaryEmpathyMap.prototype.add = function () {
// 	this.posts.push(postEmpathyMap);
// };

var Waiting = React.createClass({

	getInitialState: function () {
		var io = require('alias_socket_io');
		var socket = io.connect();
		return { socket: socket };
	},

	// componentWillUnmount: function () {
	// 	$('.main-content').css('padding-top', '20px');
	// },

	componentDidMount: function () {
		this.state.socket.on('empathy:map:next:phase', this.nextPhase);
	},

	componentWillUnmount: function () {
		this.state.socket.removeAllListeners('empathy:map:next:phase');
	},

	nextPhase: function (commentaryResults) {
		console.log('commentaryResults: ', commentaryResults);
		React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
		var discussionEmpathyMap = DiscussionEmpathyMap.fromJSON(commentaryResults);
		discussionEmpathyMap.renderAtId('vf-activity-content');
	},

    render: function () {
        return (
			<div className="well">
				Please wait for the rest of the participants to finish submitting.
			</div>
		)
    }
});

var CommentaryEmpathyMapComponent = React.createClass({

	getInitialState: function () {
		var io = require('alias_socket_io');
		socket = io.connect();
		return { socket: socket };
	},

	componentWillMount: function () {
		this.state.socket.on('empathy:map:next:phase', this.nextPhase);
		this.state.socket.on('empathy:map:wait', this.wait);
		require('./app.models.CommentaryEmpathyMap.styl');
	},

	componentWillUnmount: function () {
		this.state.socket.removeAllListeners('empathy:map:next:phase');
		this.state.socket.removeAllListeners('empathy:map:wait');
	},

	nextPhase: function (commentaryResults) {
		console.log('commentaryResults: ', commentaryResults);
		React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
		var discussionEmpathyMap = DiscussionEmpathyMap.fromJSON(commentaryResults);
		discussionEmpathyMap.renderAtId('vf-activity-content');
	},

	wait: function () {
		React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
		React.render(<Waiting />, document.getElementById('vf-activity-content'));
	},

	submitCommentary: function () {
		//console.log('commentary think: ', JSON.stringify(this.props.self.think));
		console.log('submitting commentary:', this.props.self.toJSON());
		this.state.socket.emit('empathy:map:submit:commentary', this.props.self.toJSON());
	},

	// scrollToId: function (id) {
	// 	console.log('scroll to:', id, document.getElementById(id));
	// 	document.getElementById(id).scrollIntoView();
	// },

	scrollToThink: function () {
		document.getElementById('vf-panel-think').scrollIntoView();
		document.getElementsByClassName('main-content')[0].scrollTop -= 40;
	},

	scrollToHear: function () {
		document.getElementById('vf-panel-hear').scrollIntoView();
		document.getElementsByClassName('main-content')[0].scrollTop -= 40;
	},

	scrollToSee: function () {
		document.getElementById('vf-panel-see').scrollIntoView();
		document.getElementsByClassName('main-content')[0].scrollTop -= 40;
	},

	scrollToSay: function () {
		document.getElementById('vf-panel-say').scrollIntoView();
		document.getElementsByClassName('main-content')[0].scrollTop -= 40;
	},

	scrollToPain: function () {
		document.getElementById('vf-panel-pain').scrollIntoView();
		document.getElementsByClassName('main-content')[0].scrollTop -= 40;
	},

	scrollToGain: function () {
		document.getElementById('vf-panel-gain').scrollIntoView();
		document.getElementsByClassName('main-content')[0].scrollTop -= 40;
	},

    render: function () {
    	return (
    		<div id="vf-commentary-container">
    			<div id="vf-section-menu">
	    			<ul className="vf-section-menu-list">
	    				<li><a className="vf-section-link" href="javascript:void(0);" onClick={this.scrollToThink}>Think</a></li>
	    				<li><a className="vf-section-link" href="javascript:void(0);" onClick={this.scrollToHear}>Hear</a></li>
	    				<li><a className="vf-section-link" href="javascript:void(0);" onClick={this.scrollToSee}>See</a></li>
	    				<li><a className="vf-section-link" href="javascript:void(0);" onClick={this.scrollToSay}>Say</a></li>
	    				<li><a className="vf-section-link" href="javascript:void(0);" onClick={this.scrollToPain}>Pain</a></li>
	    				<li><a className="vf-section-link" href="javascript:void(0);" onClick={this.scrollToGain}>Gain</a></li>
    				</ul>
    			</div>
    			<div id="vf-empathy-map-status">
                	<legend>Activity Progress</legend>
                	<div className="btn-group btn-group-justified">
						<div className="btn-group vf-btn-group-status">
							<a className="btn btn-default">Empathy Map</a>
						</div>
						<div className="btn-group vf-btn-group-status">
							<a className="btn btn-info">Commentary</a>
						</div>
						<div className="btn-group vf-btn-group-status">
							<a className="btn btn-default">Discussion</a>
						</div>
                	</div>
            	</div>
            	<legend>Comment on and up/down vote the ideas below</legend>
				<div id="vf-panel-think" className="panel panel-primary">
					<div className="panel-heading">
						<h3 className="panel-title">Think</h3>
					</div>
				</div>
	    		<PostsEmpathyMapComponent self={this.props.self.think} />
				<div id="vf-panel-hear" className="panel panel-primary vf-section-margin">
					<div className="panel-heading">
						<h3 className="panel-title">Hear</h3>
					</div>
				</div>
	    		<PostsEmpathyMapComponent self={this.props.self.hear} />
				<div id="vf-panel-see" className="panel panel-primary vf-section-margin">
					<div className="panel-heading">
						<h3 className="panel-title">See</h3>
					</div>
				</div>
	    		<PostsEmpathyMapComponent self={this.props.self.see} />
				<div id="vf-panel-say" className="panel panel-primary vf-section-margin">
					<div className="panel-heading">
						<h3 className="panel-title">Say</h3>
					</div>
				</div>
	    		<PostsEmpathyMapComponent self={this.props.self.say} />
				<div id="vf-panel-pain" className="panel panel-primary vf-section-margin">
					<div className="panel-heading">
						<h3 className="panel-title">Pain</h3>
					</div>
				</div>
	    		<PostsEmpathyMapComponent self={this.props.self.pain} />
				<div id="vf-panel-gain" className="panel panel-primary vf-section-margin">
					<div className="panel-heading">
						<h3 className="panel-title">Gain</h3>
					</div>
				</div>
	    		<PostsEmpathyMapComponent self={this.props.self.gain} />
	    		<button type="submit" id="button-ready" className="btn btn-success vf-submit-margin" onClick={this.submitCommentary}>Submit</button>
    		</div>
    	)
  //   	console.log('posts:', this.state.self);
  //   	function renderPost(post, i) {
		// 	return <PostEmpathyMapComponent key={i} post={post} />; //post.toJSX();
  //   	}
  //       return (
		// 	<ul className="vf-list-group list-group">
		// 		{this.state.self.posts.map(renderPost)}
		// 	</ul>
		// )
    }

});

	    		// <div className="panel panel-primary">
	    		// 	Think
	    		// </div>
	    		// <PostsEmpathyMapComponent self={this.props.self.think} />
	    		// <div className="panel panel-primary">
	    		// 	Hear
	    		// </div>
	    		// <PostsEmpathyMapComponent self={this.props.self.hear} />
	    		// <div className="panel panel-primary">
	    		// 	See
	    		// </div>
	    		// <PostsEmpathyMapComponent self={this.props.self.see} />
	     	// 	<div className="panel panel-primary">
	     	// 		Say
	    		// </div>
	    		// <PostsEmpathyMapComponent self={this.props.self.say} />
	     	// 	<div className="panel panel-primary">
	     	// 		Pain
	    		// </div>
	    		// <PostsEmpathyMapComponent self={this.props.self.pain} />
	     	// 	<div className="panel panel-primary">
	     	// 		Gain
	    		// </div>
	    		// <PostsEmpathyMapComponent self={this.props.self.gain} />

CommentaryEmpathyMap.prototype.renderAtId = function (id) {
    React.render(<CommentaryEmpathyMapComponent self={this} />, document.getElementById(id));
};

CommentaryEmpathyMap.prototype.unmountAtId = function (id) {
	React.unmountComponentAtNode(id);
};

module.exports = CommentaryEmpathyMap;
//module.exports.component = CommentaryEmpathyMapComponent;