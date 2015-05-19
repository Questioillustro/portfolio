require('./app.components.PollingOptions.styl');
require('../node_modules/bootstrap-styl/js/alert.js');
var React = require('react/addons');

var PhaseBreadCrumbs = require('./app.components.PhaseBreadCrumbs.jsx');
var PollingResults = require('./app.components.Polling.Results.jsx');

var io = require('alias_socket_io');
var socket = io.connect();

var WarningMessage = React.createClass({

    render: function () {
      var divStyle = {
        display: 'block'
      };
        return(
            <div className="alert alert-dismissible alert-danger" style={divStyle}>
              <button type="button" className="close" data-dismiss="alert">×</button>
              <strong>Error:</strong> {this.props.message}
            </div>
        )
      }
});

var SuccessMessage = React.createClass({

    render: function () {
      var divStyle = {
        display: 'block'
      };
        return(
            <div className="alert alert-success" style={divStyle}>
              <strong>Success:</strong> {this.props.message}
            </div>
        )
      }
});

var PollingAtt = React.createClass({

    getInitialState: function () {
        return { vote: "", user : ""};
    },

    componentDidMount: function(){
        socket.emit('getIndvidualRoomList', this.props.room);
        socket.emit('getSelf');
        socket.on('userInfoRequest', this.getSelf);
        socket.on('nextPhase', this.nextPhase);
    },

    startPolling: function (params) {
        this.setState({params : params});
    },

    componentWillUnmount: function () {
        socket.removeAllListeners('nextPhase');
    },

    getSelf: function(user){
        this.setState({ user: user });
    },

    checkVote: function() {
        if (this.state.vote != "") {
            this.submitResponse();
        } else {
            React.render(<WarningMessage message="Please check your vote and try again."/>, document.getElementById('mesasgeCenter'));
        }
    },

    disableButtons: function() {
        var ideas = document.getElementsByName('choices');
        var ideasSize = ideas.length;

        for (var index = 0; index < ideasSize; index++){
            ideas[index].disabled = true;
        }

        document.getElementById('submitButton').style.visibility = "hidden";
    },

    selectVote: function(e) {
        this.setState({ vote: e.target.value });
    },

    submitResponse: function() {

        var ready = {};

        ready["text"] = "Ready";
        ready["name"] = this.state.user.name;
        ready["id"] = this.state.user.id;

        socket.emit('broadCastData', this.props.room, this.state.vote);
        socket.emit('readyUp', this.props.room, ready);
        this.disableButtons();
        React.render(<SuccessMessage message="Your vote has been submitted. Please sit back and wait."/>, document.getElementById('mesasgeCenter'));
    },

    nextPhase: function(params) {
        React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
        React.render(<PollingResults room={this.props.room} params={params}/>, document.getElementById("vf-activity-content"));
    },

    render: function () {

        var self = this;

        var renderVotingChoice = function(object, index) {
            return(
                <div className="idea">
                    <div className="polling-topic col-sm-5">
                        <h5>{object[0]}</h5>
                        <input type="radio" className="btn btn-primary selectButtons" name="choices" onChange={self.selectVote} value={object[0]}></input>
                    </div>
                </div>
            )
        }

        return(
            <div className="well well-lg">
                <div className="form-horizontal">
                    <fieldset>

                        <PhaseBreadCrumbs params={this.props.params} />

                        <div id="inProgressArea">
                            <div claaName="form-group">
                                <div className="panel panel-default">
                                    
                                    <div id="details-panel">
                                        <div className="form-group optionsPadding">
                                            <div id="mesasgeCenter" className="form-group optionsPadding"></div>
                                        </div>

                                        <div id="polling-parameters">
                                            <div className="question-posed">
                                                <div className="polling-topic well">
                                                    <p className="help-text"> Select what you would like to vote on and click on the vote button to lock in your vote. </p>
                                                    <h2>{this.props.params.question}</h2>
                                                </div>
                                            </div>

                                            {this.props.params.options.map(renderVotingChoice)}

                                        </div>
                                    </div>

                                    <div className="centered proceed-button col-sm-12">
                                        <button id="submitButton" className="btn btn-primary vote" onClick={this.checkVote}>Vote</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                </div>
            </div>
        )
    }
});

module.exports = PollingAtt;