var React = require('react/addons');
var PostsEmpathyMap = require('./app.models.PostsEmpathyMap.jsx');
var PostsEmpathyMapComponent2 = PostsEmpathyMap.component2;


function DiscussionEmpathyMap() {};

// function DiscussionEmpathyMap(commentaryJSON) {
// 	console.log('commentaryJSON:', commentaryJSON);
// 	console.log('commentaryJSON2:', JSON.stringify(commentaryJSON));
// 	if (commentaryJSON) {
// 		this.think = PostsEmpathyMap.fromJSON(commentaryJSON['think']);
// 		this.hear = PostsEmpathyMap.fromJSON(commentaryJSON['hear']);
// 		this.see = PostsEmpathyMap.fromJSON(commentaryJSON['see']);
// 		this.say = PostsEmpathyMap.fromJSON(commentaryJSON['say']);
// 		this.pain = PostsEmpathyMap.fromJSON(commentaryJSON['pain']);
// 		this.gain = PostsEmpathyMap.fromJSON(commentaryJSON['gain']);
// 	}
// };

// Note: Never called this method outside this class. Use fromJSON().
DiscussionEmpathyMap.prototype.injectJSON = function (json) {
	this.think = PostsEmpathyMap.fromJSON(json['think']);
	console.log('disc inject this.think:', this.think);
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

DiscussionEmpathyMap.fromJSON = function (json) {
	console.log('DEM formJSON:', json);
	console.log('DEM formJSON2:', JSON.stringify(json));
	var discussionEmpathyMap = new DiscussionEmpathyMap();
	discussionEmpathyMap.injectJSON(json);
	console.log('disc this.think:', this.think);
	return discussionEmpathyMap;
};

var DiscussionEmpathyMapComponent = React.createClass({

	// getInitialState: function () {
	// 	var io = require('alias_socket_io');
	// 	socket = io.connect();
	// 	return { socket: socket };
	// },

	componentWillMount: function () {
		//this.state.socket.on('empathy:map:next:phase', this.nextPhase);
		require('./app.models.DiscussionEmpathyMap.styl');
	},

	componentWillUnmount: function () {
		// this.state.socket.removeAllListeners('empathy:map:next:phase');
		// this.state.socket.removeAllListeners('empathy:map:wait');
	},

	nextPhase: function () {
		React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
	},

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
    	console.log('disc props:', this.props.self);
    	return (
    		<div id="vf-discussion-container">
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
							<a className="btn btn-default">Commentary</a>
						</div>
						<div className="btn-group vf-btn-group-status">
							<a className="btn btn-info">Discussion</a>
						</div>
                	</div>
            	</div>
            	<legend>Discuss the ideas below with your peers</legend>
				<div id="vf-panel-think" className="panel panel-primary">
					<div className="panel-heading">
						<h3 className="panel-title">Think</h3>
					</div>
				</div>
	    		<PostsEmpathyMapComponent2 self={this.props.self.think} />
				<div id="vf-panel-hear" className="panel panel-primary vf-section-margin">
					<div className="panel-heading">
						<h3 className="panel-title">Hear</h3>
					</div>
				</div>
	    		<PostsEmpathyMapComponent2 self={this.props.self.hear} />
				<div id="vf-panel-see" className="panel panel-primary vf-section-margin">
					<div className="panel-heading">
						<h3 className="panel-title">See</h3>
					</div>
				</div>
	    		<PostsEmpathyMapComponent2 self={this.props.self.see} />
				<div id="vf-panel-say" className="panel panel-primary vf-section-margin">
					<div className="panel-heading">
						<h3 className="panel-title">Say</h3>
					</div>
				</div>
	    		<PostsEmpathyMapComponent2 self={this.props.self.say} />
				<div id="vf-panel-pain" className="panel panel-primary vf-section-margin">
					<div className="panel-heading">
						<h3 className="panel-title">Pain</h3>
					</div>
				</div>
	    		<PostsEmpathyMapComponent2 self={this.props.self.pain} />
				<div id="vf-panel-gain" className="panel panel-primary vf-section-margin">
					<div className="panel-heading">
						<h3 className="panel-title">Gain</h3>
					</div>
				</div>
	    		<PostsEmpathyMapComponent2 self={this.props.self.gain} />
    		</div>
    	)

    }

});

				// <ul className="vf-list-group list-group">
				// 	<li key={this.props.post.content} className="vf-post list-group-item">
			 // 			<div className="well">
				// 			<ul className="media-list">
				// 				<li className="media">
				// 					<div className="media-left">
				// 							<div className="media-object">
				// 								<a href="javascript:void(0);" onClick={this.upVote}><span className="glyphicon glyphicon-chevron-up vf-glyphicon-large"></span></a>
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
				// </ul>


DiscussionEmpathyMap.prototype.renderAtId = function (id) {
    React.render(<DiscussionEmpathyMapComponent self={this} />, document.getElementById(id));
};

DiscussionEmpathyMap.prototype.unmountAtId = function (id) {
	React.unmountComponentAtNode(id);
};

module.exports = DiscussionEmpathyMap;
module.exports.component = DiscussionEmpathyMapComponent;