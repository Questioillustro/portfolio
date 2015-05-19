require('./app.components.PollingOptions.styl');
var React = require('react/addons');
var Timer = require('./app.components.Timer.jsx');

var PhaseBreadCrumbs = require('./app.components.PhaseBreadCrumbs.jsx');
var PollingDetails = require('./app.components.Polling.Details.jsx');
var PollingResults = require('./app.components.Polling.Results.jsx');

var io = require('alias_socket_io');
var socket = io.connect();

var PollingFac = React.createClass({

    getInitialState: function() {

        var temp_array = [];

        for (x = 0; x < this.props.params.options.length; x++) {
            temp_array[x] = this.props.params.options[x];
        }      

        return { users: [],  
                 vote_map: {},
                 update_array: temp_array,
                 totalVotes: 0, 
                 colors: [ "#FF3030", "#0066FF", "#66FF66", "#FF8330", "#FFFF66",
                           "#FF66FF", "#FF75D1", "#835930", "#4D9B9B", "#33AD5C"],
             };
    },

    componentWillMount: function () {
        this.setMaps();
        var updateTitle = "In a Polling Activity";
        socket.emit('updateMeetingStatus', this.props.room, updateTitle);
        socket.on('activityData', this.updateVotes);
        socket.on('recieveIndividualRoomList', this.getUsersInRoom);
        socket.emit('getIndvidualRoomList', this.props.room);
    },

    componentDidMount: function() {
        document.getElementById("inputButton").disabled = true;  
    },

    componentWillUnmount: function () {
        socket.removeAllListeners('activityData');
        socket.removeAllListeners('recieveIndividualRoomList');
    },

    getUsersInRoom: function(users) {
        this.setState({ users: users });
    },

    setMaps: function() {
        for (x = 0; x < this.props.params.options.length; x++) {
            this.state.vote_map[this.props.params.options[x][0]] = this.props.params.options[x];
        }   
    },

    updatePercents: function() {

        var self = this.state;
        var param = this.props.params;

        for (x = 0; x < param.options.length; x++) {
            self.vote_map[param.options[x][0]][2] = ((self.vote_map[param.options[x][0]][1]/self.totalVotes) * 100).toFixed(2);
        }

        var updatedPercents = self.vote_map;
        this.setState({vote_map : updatedPercents});
    },

    updateVotes: function(data) {   

        var self = this.state;
        var params = this.props.params;

        self.vote_map[data][1] = self.vote_map[data][1] + 1;
        self.totalVotes += 1;

        document.getElementById('inputButton').disabled = false;

        this.updatePercents();
        this.setDecision();

        var new_array = [];

        for (x = 0; x < params.options.length; x++){
            new_array[x] = this.state.vote_map[this.props.params.options[x][0]];
        }

        this.setState({ update_array : new_array});

        if(self.totalVotes >= (self.users.length)){
            this.nextPhase();
        }

    },

    setDecision: function() {

        var self = this.state;
        var highestVotes = 0;

        for (x = 0; x < this.props.params.options.length; x++) {
            if (self.vote_map[this.props.params.options[x][0]][1] > highestVotes){
                highestVotes = self.vote_map[this.props.params.options[x][0]][1];
                self.decision = self.vote_map[this.props.params.options[x][0]][0];
            } else if (self.vote_map[this.props.params.options[x][0]][1] === highestVotes){
                self.decision = "It was a tie.";
            }
        }
    },

    nextPhase: function() {

        var self = this.state;
        var param = this.props.params;
        var results = [];

        for (x = 0; x < param.options.length; x++) {
            results.push(self.vote_map[param.options[x][0]]);
        }

        var parameters = {
            results: results,
            decision: self.decision,
            breadCrumbs: param.breadCrumbs,
            phase: (param.phase + 1),
            question: param.question
        };

        socket.emit('sendVotes', this.props.room, this.state.votes);
        socket.emit('pollingNextPhase', this.props.room, parameters);
        React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
        React.render(<PollingResults room={this.props.room} params={parameters}/>, document.getElementById("vf-activity-content"));
    },

    endActivity: function() {
        
        var results = [];

        for (x = 0; x < this.props.params.options.length; x++) {
            results.push(this.state.vote_map[this.props.params.options[x][0]]);
        }

        var data = {};
            
        data['text'] = "endActivity";

        socket.emit('clear:readyup:polling', this.props.room);
        socket.emit('updateMeetingStatus', this.props.room, "Facilitator is idle");
        socket.emit('activityControl', this.props.room, data);
    },

    render: function () {

        var renderChoice = function(object, index) {
            return(
                <div className="idea">
                    <div className="polling-topic col-sm-5">
                        <div className="ideaColor" style={{ 'backgroundColor': object[3] }}>
                            <h5 className="innerIdea">{object[0]}</h5>
                        </div>
                        <div className="ideaResult" style={{'width': object[2]+'%', 'backgroundColor': object[3]}}>
                            <h5>{object[2]}% </h5>
                        </div>
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
                                        <div id="polling-parameters">
                                            <div className="question-posed">
                                                <div className="polling-topic well">
                                                    <p> Welcome to the Polling Activity. The screen will advance to the results once all votes are in or the facilitator presses the 'Proceed Button' </p>
                                                    <h2>{this.props.params.question}</h2>
                                                </div>
                                            </div>

                                            {this.state.update_array.map(renderChoice)}

                                        </div>
                                    </div>

                                    <div id="facilitator-controls" className="col-xs-12">
                                        <div className="well col-xs-6">
                                            <button type="end button" className="btn btn-danger endPolling" onClick={this.endActivity}>End Activity</button>
                                        </div>
                                        <div className="well col-xs-6">
                                            <button id="inputButton" className="btn btn-success proceed" onClick={this.nextPhase}>Proceed</button>
                                        </div>
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

module.exports = PollingFac;