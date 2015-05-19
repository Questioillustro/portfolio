require('./app.components.AnonymousBrainstorming.styl');
var React = require('react/addons');

var HelpTextMixin = require('./app.components.mixin.HelpText.js');
var ModalWarningMixin = require('./app.components.mixin.ModalWarning.js');

var BrainstormingDetails = require('./app.components.BrainstormingDetails.jsx');
var BrainstormingResults = require('./app.components.BrainstormingResults.jsx');
var PhaseBreadCrumbs = require('./app.components.PhaseBreadCrumbs.jsx');
var Timer = require('./app.components.Timer.jsx');

var io = require('alias_socket_io');
var socket = io.connect();

var AnonymousBrainstormingFacilitator = React.createClass({

    mixins: [HelpTextMixin, ModalWarningMixin],

    getInitialState: function () {
        return this.props.params;
    }, 

    componentDidMount: function () {
        this.bindSocketListeners();
        this.setActivityStatus("Anonymous Brainstorming activity in progress");

        // Initalize warning modal
        var endWarning = "Are you sure you want to end the activity early?";
        this.initializeWarning( endWarning, this.endActivity );
    }, 

    componentWillUnmount: function () {
        this.unbindSocketListeners();
    },

    bindSocketListeners: function () {
        socket.on('receive:data', this.receiveData);
    },

    unbindSocketListeners: function () {
        socket.removeAllListeners('receive:data');
    },

    setActivityStatus: function (statusTxt) { 
        socket.emit('updateMeetingStatus', this.props.room, statusTxt);
    },

    endActivityEarly: function (e) {
        this.warningMessage();
    },

    endActivity: function () {
        this.initializeWarning( "", undefined);
        socket.emit('send:anonymous:cmd:end', this.props.room);
    },

    receiveData: function (data) {
        if (data.activityEnding)
            this.endActivity();
        else {
            $('#nextPhase').attr('disabled', false);
            this.setState(data);
        }
    },

    next: function () {
        $('#nextPhase').attr('disabled', true);
        socket.emit('send:anonymous:cmd:next', this.props.room)
    }, 

    render: function () {

        var self = this;

        return (
            <div className="anonymous-brainstorming">
                <legend>Anonymous Brainstorming</legend>
                
                <div className="timer-div">
                    <Timer start={this.state.start_time} 
                           totalSeconds={this.state.time_remaining} 
                           callback={self.next}
                           playAudio={true} />
                </div>

                <PhaseBreadCrumbs params={self.state} />

                <BrainstormingDetails params={this.state} 
                                      room={this.props.room} />

                <div id="facilitator-controls" className="row col-xs-12">
                    <div className="col-xs-6">
                        <button type="button" className="btn btn-danger" onClick={self.endActivityEarly}>End Activity</button>
                    </div>

                    <div className="col-xs-6">
                        <button id="nextPhase" className="btn btn-success" onClick={self.next}>Go to Next Round</button>
                    </div>
                </div>
            </div>
        )
    }
});

module.exports = AnonymousBrainstormingFacilitator;
