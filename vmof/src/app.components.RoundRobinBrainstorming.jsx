//require('./app.components.RoundRobinBrainstorming.styl');
var React = require('react/addons');
var io = require('alias_socket_io');
var socket = io.connect();

var RoundRobinBrainstorming = React.createClass({

	// componentDidMount: function () {
	// 	socket.on('',);
	// }

	submitIdea: function (e) {
		e.preventDefault();
		var idea = $('#idea').val();
		socket.emit('round:robin:submit:idea', this.props.room, idea);
	},

    render: function () {
        return (
			<div className="well bs-component">
				<form>
					<fieldset>
						<legend>{this.props.params.topic}</legend>
						<div className="form-group">
							<div>
								<textarea className="form-control" rows="3" id="idea" placeholder="Write a response..."></textarea>
							</div>
						</div>
						<div className="form-group">
							<div>
								<button type="submit" className="btn btn-primary" onClick={this.submitIdea}>Submit</button>
							</div>
						</div>
					</fieldset>
				</form>
			</div>
        )
    }
});

module.exports = RoundRobinBrainstorming;

			// <div className="col-lg-6">
			// 	<div className="well bs-component">
			// 		<form className="form-horizontal">
			// 			<fieldset>
			// 			<legend>Where should we eat lunch today?</legend>
			// 				<div className="form-group">
			// 					<div className="col-lg-10">
			// 						<textarea className="form-control" rows="3" id="textArea" placeholder="Write a response..."></textarea>
			// 					</div>
			// 				</div>
			// 				<div className="form-group">
			// 					<div className="col-lg-10">
			// 						<button type="submit" className="btn btn-primary">Submit</button>
			// 					</div>
			// 				</div>
			// 			</fieldset>
			// 		</form>
			// 	</div>
			// </div>