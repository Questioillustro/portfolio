require('./app.components.TwoTruthsOptions.styl');
var React = require('react/addons');


var io = require('alias_socket_io');
var socket = io.connect();

var notReadyTotal = 0;


var FacControls = React.createClass({

    getInitialState: function() {
        return { users: [], liers: [], currentLier: 0, scoreBoard: [] };
    },

    componentDidMount: function() {

    },

    componentWillUnmount: function () {


    },

    moveON: function() {
        this.props.next_phase();
    },

    endActivity: function(){
        var data = {};
        data['text'] = "endActivity";
        socket.emit('updateMeetingStatus', this.props.room, "Facilitator is idle");
        socket.emit('end:TwoTruths', this.props.room);
    },

    render: function () {
        return(
            <div>
                <div className="panel panel-primary">
                    <div className="panel-heading">
                        <h3 className="center-me panel-title"> Facilitator Controls </h3>
                    </div>
                    <div className="panel panel-body">
                        <div className="row">
                            <div className="col-xs-3 col-xs-offset-3">
                                <button id="EndButton" className="btn btn-danger" onClick={this.endActivity}>End Activity</button>
                            </div>
                            <div className="col-xs-3 col-xs-offset-2">
                                <button id="NextButton" className="btn btn-success" onClick={this.moveON}>Proceed</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
});

module.exports = FacControls;