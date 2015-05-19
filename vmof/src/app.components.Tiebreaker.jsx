require('../node_modules/bootstrap-styl/js/modal.js');
var React = require('react/addons');
var ModalWarningMixin = require('./app.components.mixin.ModalWarning.js');

var io = require('alias_socket_io');
var socket = io.connect();

var Tiebreaker = React.createClass({

	mixins: [ModalWarningMixin],

	getInitialState: function () {
		var optCount = Object.keys(this.props.options).length,
			values = [];

		for (var i = 0; i < optCount; i++)
			values[i] = 0;

		return { values: values, optCount: optCount };
	},

	componentDidMount: function () {
		this.breakTie();
		$('#end-activity, #restart').attr('disabled', true);

		var statusTxt = "A Tiebreaker is in progress"
		socket.emit('updateMeetingStatus', this.props.room, statusTxt);
	},

	componentDidUpdate: function () {
		if (this.noWinner())
			setTimeout(this.breakTie, 50);
		else {
			this.alertMessage( this.getWinner(), "Winner!" );
			$('#end-activity, #restart').attr('disabled', false);
		}
	},

	breakTie: function () {
		var optCount = this.state.optCount,
			values = this.state.values;

		var val = Math.floor( Math.random() * (optCount) ) + 1
		values[val-1]++;
		this.setState({ values: values });
	},

	noWinner: function () {
		var target = 100,
			values = this.state.values;

		for (var i = 0; i < values.length; i++) {
			if (values[i] >= target) {
				return false;
			}
		}

		return true;
	},

	getWinner: function () {
		var target = 100,
			values = this.state.values;

		for (var i = 0; i < values.length; i++) {
			if (values[i] >= target) {
				return this.props.options[i];
			}
		}
	},

	restart: function () {
		var optCount = this.state.optCount,
			values = [],
			self = this;

		for (var i = 0; i < optCount; i++)
			values[i] = 0;
		
		$('.progress-bar').css('width', '0').attr('aria-valuenow', '0');
		$('#end-activity, #restart').attr('disabled', true);

		setTimeout(function () {
			self.setState({ values: values })
		}, 2000);
	},

	endActivity: function () {
		this.props.end();
	},

	render: function () {

		var self = this;

		var renderOption = function ( option, index ) {
			return (
				<div className="well">
					{self.props.options[option]} <br/>
					<div className="progress">
						<div className="progress-bar progress-bar-danger" role="progressbar" 
		                     aria-valuenow={self.state.values[index]} aria-valuemin="0" 
		                     aria-valuemax="100" style={{'width': self.state.values[index]+'%'}}>
		                </div>
		            </div>
				</div>
			)
		}

		return (
			<div>
				<legend>Tiebreaker</legend>
				{Object.keys(this.props.options).map(renderOption)}

                <div id="facilitator-controls" className="row col-xs-12">
                    <div className="col-xs-6">
                        <button id="end-activity" type="button" className="btn btn-danger" onClick={self.endActivity}>End Activity</button>
                    </div>

                    <div className="col-xs-6">
                        <button id="restart" className="btn btn-success" onClick={self.restart}>Restart</button>
                    </div>
                </div>
			</div>
		)
	}

});

module.exports = Tiebreaker;