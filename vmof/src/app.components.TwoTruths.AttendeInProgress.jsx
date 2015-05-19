require('./app.components.TwoTruthsOptions.styl');
var React = require('react/addons');
var IndividualScore = require('./app.components.IndividualScore.jsx');
var TwoTruthsInForm = require('./app.components.TwoTruths.InputForm.jsx');
var AttLieGuess = require('./app.components.TwoTruths.AttendeLieGuessing.jsx');
var AttTwoTruthsResults = require('./app.components.TwoTruths.AttResultsTable.jsx');
var ModalWarningMixin = require('./app.components.mixin.ModalWarning.js');
var io = require('alias_socket_io');
var socket = io.connect();

var Becool = React.createClass({
    render: function () {
        return(
            <div>
                <div className="vf-status">
                    <legend>Activity Progress</legend>
                    <div className="btn-group btn-group-justified">
                        <div className="btn-group vf-btn-group-status">
                            <a className="btn btn-default no-click">Input</a>
                        </div>
                        <div className="btn-group vf-btn-group-status">
                            <a className="btn btn-info no-click">Guessing</a>
                        </div>
                        <div className="btn-group vf-btn-group-status">
                            <a className="btn btn-default no-click">Results</a>
                        </div>
                    </div>
                </div>
                    <legend>
                        Two Truths and a Lie
                    </legend>
                <div className="well well-lg col-md-offset-3 col-md-6">
                    <legend className="centered">Be cool, people are figuring out your lie</legend>
                </div> 
            </div>
        )
    }
});

var TwoTruthsAtt = React.createClass({

    mixins: [ModalWarningMixin],

    getInitialState: function() {
        return {people: []};
    },

    componentDidMount: function(){
        socket.on('recieveIndividualRoomList', this.getUsersInRoom);
        socket.emit('getIndvidualRoomList', this.props.room);
        socket.on('countDown:warning', this.showAlert);

        socket.on('activityData', this.messageCheck);
        React.render(<TwoTruthsInForm rules={this.props.rules} name={this.props.name} id={this.props.id} room={this.props.room}/>, document.getElementById('two-truth-activity-area'));
    },

    componentWillUnmount: function () {
        React.unmountComponentAtNode(document.getElementById('two-truth-activity-area'));
        socket.removeAllListeners('activityData');
        socket.removeAllListeners('recieveIndividualRoomList');
    },

    getUsersInRoom: function(users) {
        this.setState({ people: users });
    },

    timeChanged: function () {

    },

    showAlert: function () {
        this.alertMessage( "There are two minutes remaining", "Warning" );
    },

    messageCheck: function (data) {
        if(data.text == "nextLier"){
            React.unmountComponentAtNode(document.getElementById('two-truth-activity-area'));
        
            if(data.lier.id == this.props.id){
                var ready = {};
                ready['text'] = "Ready";
                ready['name'] = this.props.name;
                ready['id'] = this.props.id;

                socket.emit('readyUp', this.props.room, ready);
                React.render(<Becool/>, document.getElementById('two-truth-activity-area'));
                console.log("We ne lied again");
            } else {
                React.render(<AttLieGuess name={this.props.name} id={this.props.id} lieData={data.lier} room={this.props.room}/>, document.getElementById('two-truth-activity-area'));
            }
        } else if(data.text == "scoreBoard"){
            React.unmountComponentAtNode(document.getElementById('two-truth-activity-area'));
            document.getElementById('two-truth-activity-area').innerHTML = "";
            React.render(<AttTwoTruthsResults best={data.best} lies={data.lies} scores={data.scores} room={this.props.room} people={this.state.people}/>, document.getElementById('two-truth-activity-area'));
        }
    },

    render: function () {
        return <div id='two-truth-activity-area'></div>
    }
});

module.exports = TwoTruthsAtt;