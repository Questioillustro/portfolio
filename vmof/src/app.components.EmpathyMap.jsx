require('./app.components.EmpathyMap.styl');
// var Posts = require('./app.models.Posts');
var PostsEmpathyMap = require('./app.models.PostsEmpathyMap.jsx');
//var Post = require('./app.models.Post');
var PostEmpathyMap = require('./app.models.PostEmpathyMap.jsx');
var CommentaryEmpathyMap = require('./app.models.CommentaryEmpathyMap.jsx');
var CommentaryEmpathyMapComponent = CommentaryEmpathyMap.component;
var Waiting = require('./app.components.Waiting.jsx');

var AutoScroll = require('./app.components.mixin.AutoScroll.js');

var React = require('react/addons');
var io = require('alias_socket_io');
var socket = io.connect();

var SECTION_THINK = 0;
var SECTION_HEAR = 1;
var SECTION_SEE = 2;
var SECTION_SAY = 3;
var SECTION_PAIN = 4;
var SECTION_GAIN = 5;

var EmpathyMapPhaseThink = React.createClass({
	mixins: [AutoScroll],

	componentDidMount: function () {
		socket.on('empathy:map:next:phase', this.nextPhase);
		this.initializeAutoScroll();
	},

	componentWillUnmount: function () {
		socket.removeAllListeners('empathy:map:next:phase');
	},

	componentDidUpdate: function () {
		this.initializeAutoScroll();	
	},

	submitIdea: function (e) {
		e.preventDefault();
		var idea = $('#idea-think').val();
		console.log('idea submitted: ', idea);
		socket.emit('empathy:map:submit:idea', this.props.room, idea, SECTION_THINK);
		//$('#idea-think-container').fadeOut(1000);
		$('#idea-think-container').hide();
		$('input:visible:first').focus();
	},

	nextPhase: function () {
        React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
        React.render(<EmpathyMapPhaseHear room={this.props.room} />, document.getElementById('vf-activity-content'));
	},

    render: function () {
        return (
			<div id="idea-think-container" className="well bs-component">
				<form onSubmit={this.submitIdea}>
					<legend>What does the customer think and feel?</legend>
					<div className="form-group">
						<div>
							<input id="idea-think" className="form-control" rows="3" maxlength="10000"></input>
							<span className="help-block">What is important to the customer? What are their hopes, dreams, and fears?</span>
						</div>
					</div>
					<div className="form-group">
						<div>
							<button type="submit" className="btn btn-primary" onClick={this.submitIdea}>Submit</button>
						</div>
					</div>
				</form>
			</div>
		)
    }
});


var EmpathyMapPhaseHear = React.createClass({

	componentDidMount: function () {
		socket.on('empathy:map:next:phase', this.nextPhase);
	},

	componentWillUnmount: function () {
		socket.removeAllListeners('empathy:map:next:phase');
	},

	submitIdea: function (e) {
		e.preventDefault();
		var idea = $('#idea-hear').val();
		console.log('idea submitted: ', idea);
		socket.emit('empathy:map:submit:idea', this.props.room, idea, SECTION_HEAR);
		//$('#idea-hear-container').fadeOut(1000);
		$('#idea-hear-container').hide();
		$('input:visible:first').focus();
	},

	nextPhase: function () {
        React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
        React.render(<EmpathyMapPhaseSee room={this.props.room} />, document.getElementById('vf-activity-content'));
	},

    render: function () {
        return (
	        <div id="idea-hear-container" className="well bs-component">
	      		<form onSubmit={this.submitIdea}>
		            <legend>What does the customer hear?</legend>
		            <div className="form-group">
		                <div>
		                    <input id="idea-hear" className="form-control" rows="3" maxlength="10000"></input>
		                    <span className="help-block">What influences the customer?</span>
		                </div>
		            </div>
		            <div className="form-group">
		                <div>
		                    <button type="submit" className="btn btn-primary" onClick={this.submitIdea}>Submit</button>
		                </div>
		            </div>
		        </form>
	        </div>
		)
    }
});


var EmpathyMapPhaseSee = React.createClass({

	componentDidMount: function () {
		socket.on('empathy:map:next:phase', this.nextPhase);
	},

	componentWillUnmount: function () {
		socket.removeAllListeners('empathy:map:next:phase');
	},

	nextPhase: function () {
        React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
        React.render(<EmpathyMapPhaseSay room={this.props.room} />, document.getElementById('vf-activity-content'));
	},

	submitIdea: function (e) {
		e.preventDefault();
		var idea = $('#idea-see').val();
		console.log('idea submitted: ', idea);
		socket.emit('empathy:map:submit:idea', this.props.room, idea, SECTION_SEE);
		// $('#idea-see-container').fadeOut(1000);
		$('#idea-see-container').hide();
		$('input:visible:first').focus();
	},

    render: function () {
        return (
	        <div id="idea-see-container" className="well bs-component">
		        <form onSubmit={this.submitIdea}>
		            <legend>What does the customer see?</legend>
		            <div className="form-group">
		                <div>
		                    <input id="idea-see" className="form-control" rows="3" maxlength="10000"></input>
		                    <span className="help-block">What does the customers environment look like?</span>
		                </div>
		            </div>
		            <div className="form-group">
		                <div>
		                    <button type="submit" className="btn btn-primary" onClick={this.submitIdea}>Submit</button>
		                </div>
		            </div>
		        </form>
	        </div>
		)
    }
});


var EmpathyMapPhaseSay = React.createClass({

	componentDidMount: function () {
		socket.on('empathy:map:next:phase', this.nextPhase);
	},

	componentWillUnmount: function () {
		socket.removeAllListeners('empathy:map:next:phase');
	},

	submitIdea: function (e) {
		e.preventDefault();
		var idea = $('#idea-say').val();
		console.log('idea submitted: ', idea);
		socket.emit('empathy:map:submit:idea', this.props.room, idea, SECTION_SAY);
		// $('#idea-say-container').fadeOut(1000);
		$('#idea-say-container').hide();
		$('input:visible:first').focus();
	},

	nextPhase: function () {
        React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
        React.render(<EmpathyMapPhasePain room={this.props.room} />, document.getElementById('vf-activity-content'));
	},

    render: function () {
        return (
	        <div id="idea-say-container" className="well bs-component">
	        	<form onSubmit={this.submitIdea}>
		            <legend>What does the customer say and do?</legend>
		            <div className="form-group">
		                <div>
		                    <input id="idea-say" className="form-control" rows="3" maxlength="10000"></input>
		                    <span className="help-block">How does the customer act in public? How do they appear? What is their behavior toward others?</span>
		                </div>
		            </div>
		            <div className="form-group">
		                <div>
		                    <button type="submit" className="btn btn-primary" onClick={this.submitIdea}>Submit</button>
		                </div>
		            </div>
		        </form>
	        </div>
		)
    }
});


var EmpathyMapPhasePain = React.createClass({

	componentDidMount: function () {
		socket.on('empathy:map:next:phase', this.nextPhase);
	},

	componentWillUnmount: function () {
		socket.removeAllListeners('empathy:map:next:phase');
	},

	submitIdea: function (e) {
		e.preventDefault();
		var idea = $('#idea-pain').val();
		console.log('idea submitted: ', idea);
		socket.emit('empathy:map:submit:idea', this.props.room, idea, SECTION_PAIN);
		// $('#idea-pain-container').fadeOut(1000);
 		$('#idea-pain-container').hide();
		$('input:visible:first').focus();
	},

	nextPhase: function () {
        React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
        React.render(<EmpathyMapPhaseGain room={this.props.room} />, document.getElementById('vf-activity-content'));
	},

    render: function () {
        return (
	        <div id="idea-pain-container" className="well bs-component">
	        	<form onSubmit={this.submitIdea}>
		            <legend>Pain?</legend>
		            <div className="form-group">
		                <div>
		                    <input id="idea-pain" className="form-control" rows="3" maxlength="10000"></input>
		                    <span className="help-block">What obstacles or challenges does the customer have?</span>
		                </div>
		            </div>
		            <div className="form-group">
		                <div>
		                    <button type="submit" className="btn btn-primary" onClick={this.submitIdea}>Submit</button>
		                </div>
		            </div>
		        </form>
	        </div>
		)
    }
});


var EmpathyMapPhaseGain = React.createClass({

	componentDidMount: function () {
		socket.on('empathy:map:next:phase', this.nextPhase);
	},

	componentWillUnmount: function () {
		socket.removeAllListeners('empathy:map:next:phase');
	},

	submitIdea: function (e) {
		e.preventDefault();
		var idea = $('#idea-gain').val();
		console.log('idea submitted: ', idea);
		socket.emit('empathy:map:submit:idea', this.props.room, idea, SECTION_GAIN);
		// $('#idea-gain-container').fadeOut(1000);
		$('#idea-gain-container').hide();
		$('input:visible:first').focus();
	},

	nextPhase: function (postsJSON) {
        React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
        //React.render(<EmpathyMapPhaseResults posts={posts} />, document.getElementById('vf-activity-content'));
        //var postsEmpathyMap = PostsEmpathyMap.fromJSON(postsJSON);
        //postsEmpathyMap.renderAtId('vf-activity-content');
        var commentaryEmpathyMap = new CommentaryEmpathyMap(postsJSON);
        commentaryEmpathyMap.renderAtId('vf-activity-content');

	},

    render: function () {
        return (
	        <div id="idea-gain-container" className="well bs-component">
	        	<form onSubmit={this.submitIdea}>
		            <legend>Gain?</legend>
		            <div className="form-group">
		                <div>
		                    <input id="idea-gain" className="form-control" rows="3" maxlength="10000"></input>
		                    <span className="help-block">What does the customer hope to achieve and how might they measure success?</span>
		                </div>
		            </div>
		            <div className="form-group">
		                <div>
		                    <button type="submit" className="btn btn-primary" onClick={this.submitIdea}>Submit</button>
		                </div>
		            </div>
		        </form>
	        </div>
		)
    }
});


// --------------------------------

var EmpathyMapWaiting = React.createClass({

	// getInitialState: function () {
	// 	var io = require('alias_socket_io');
	// 	var socket = io.connect();
	// 	return { socket: socket };
	// },

	componentDidMount: function () {
		//this.state.socket.on('empathy:map:next:phase', this.nextPhase);
		socket.on('empathy:map:next:phase', this.nextPhase);
	},

	componentWillUnmount: function () {
		socket.removeAllListeners('empathy:map:next:phase');
	},

	nextPhase: function (postsJSON) {
        React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
        //React.render(<EmpathyMapPhaseResults posts={posts} />, document.getElementById('vf-activity-content'));
        // var postsEmpathyMap = PostsEmpathyMap.fromJSON(postsJSON);
        // postsEmpathyMap.renderAtId('vf-activity-content');
        var commentaryEmpathyMap = new CommentaryEmpathyMap(postsJSON);
        commentaryEmpathyMap.renderAtId('vf-activity-content');
	},

    render: function () {
        return (
			<div className="well">
				Please wait for the rest of the participants to finish submitting.
			</div>
		)
    }
});


var EmpathyMap = React.createClass({

	componentDidMount: function () {
		socket.on('empathy:map:next:phase', this.nextPhase);
		socket.on('empathy:map:wait', this.wait);
	},

	componentWillUnmount: function () {
		socket.removeAllListeners('empathy:map:next:phase');
		socket.removeAllListeners('empathy:map:wait');
	},

	readyUp: function () {
		socket.emit('empathy:map:ready:up', this.props.room);
		//$('.main-content').scrollTop = 0;
	},

	wait: function () {
		React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
		React.render(<EmpathyMapWaiting />, document.getElementById('vf-activity-content'));
	},

	nextPhase: function (postsJSON) {
        React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
        //React.render(<EmpathyMapPhaseResults posts={posts} />, document.getElementById('vf-activity-content'));
        // var postsEmpathyMap = PostsEmpathyMap.fromJSON(postsJSON);
        // postsEmpathyMap.renderAtId('vf-activity-content');
        var commentaryEmpathyMap = new CommentaryEmpathyMap(postsJSON);
        commentaryEmpathyMap.renderAtId('vf-activity-content');
	},

    render: function () {
        return (
        	<div>
    			<div id="vf-empathy-map-status">
                	<legend>Activity Progress</legend>
                	<div className="btn-group btn-group-justified">
						<div className="btn-group vf-btn-group-status">
							<a className="btn btn-info">Empathy Map</a>
						</div>
						<div className="btn-group vf-btn-group-status">
							<a className="btn btn-default">Commentary</a>
						</div>
						<div className="btn-group vf-btn-group-status">
							<a className="btn btn-default">Discussion</a>
						</div>
                	</div>
            	</div>
            	<legend>Add your ideas to the Empathy Map to better understand the customer</legend>
				<EmpathyMapPhaseThink room={this.props.room} />
	        	<EmpathyMapPhaseHear room={this.props.room} />
				<EmpathyMapPhaseSee room={this.props.room} />
				<EmpathyMapPhaseSay room={this.props.room} />
				<EmpathyMapPhasePain room={this.props.room} />
	        	<EmpathyMapPhaseGain room={this.props.room} />
	        	<button type="submit" className="btn btn-success" onClick={this.readyUp}>Ready</button>
        	</div>
		)
    }
});

module.exports = EmpathyMap;