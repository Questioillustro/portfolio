var React = require('react/addons');


var Waiting = React.createClass({

	getInitialState: function () {
		var io = require('alias_socket_io');
		var socket = io.connect();
		return { socket: socket };
	},

	componentDidMount: function () {
		this.state.socket.on('empathy:map:next:phase', this.nextPhase);
	},

	componentWillUnmount: function () {
		this.state.socket.removeAllListeners('empathy:map:next:phase');
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

module.exports = Waiting;