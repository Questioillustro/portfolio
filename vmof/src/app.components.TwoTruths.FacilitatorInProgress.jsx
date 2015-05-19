require('./app.components.TwoTruthsOptions.styl');
var React = require('react/addons');
var Timer = require('./app.components.Timer.jsx');

var FacDisplayLie = require('./app.components.TwoTruths.FacilitatorLieDisplayer.jsx');
var FacTwoTruthsResults = require('./app.components.TwoTruths.FacilitatorResultsTable.jsx');
var FacControls = require('./app.components.TwoTruths.FacControls.jsx');

var io = require('alias_socket_io');
var socket = io.connect();

var scoreBoard = {};
var lieBoard = {};
var scoreSet = false;
var currfooled = 0;
var mostFooled = 0;
var bestFooler = '';

var TwoTruthsFac = React.createClass({

    getInitialState: function() {
        return { users: [], liers: [], currentLier: 0, scoreBoard: [] };
    },

    componentDidMount: function() {

        socket.on('recieveIndividualRoomList', this.getUsers);
        socket.emit('getIndvidualRoomList', this.props.room);
        socket.on('activityData', this.updateBoard);
        console.log("starting the activity");
        var data;
        data = "Two Truths activity in progress";
        socket.emit('updateMeetingStatus', this.props.room, data);
        mostFooled = 0;
        currfooled = 0;
        bestFooler = '';

    },

    componentWillUnmount: function () {

        socket.removeAllListeners('activityData');
        socket.removeAllListeners('recieveIndividualRoomList');
        React.unmountComponentAtNode(document.getElementById("inProgressArea"));
        document.getElementById("inProgressArea").innerHTML = '';
        scoreSet = false;
    },

    getUsers: function(users) {
        console.log(this);
     //   console.log("usr: " + users);
        this.setState({ users: users });
        if(scoreSet == false){
            this.state.users.map(this.setUpScores);
            scoreSet = true;
        }
    },

    setUpScores: function(user){
        if(user.name != "Facilitator"){
            console.log("this was called, the set scores");
            scoreBoard[user.id] = 0;
            console.log(scoreBoard[user.id] + " is the intial score for " + user.name);
        }
    },

    nextPhase: function(){
        console.log(this);
        React.unmountComponentAtNode(document.getElementById("inProgressArea"));
        document.getElementById("inProgressArea").innerHTML = '';

        if(this.state.currentLier == this.state.liers.length){
            if(currfooled > mostFooled){
                bestFooler = this.state.liers[this.state.currentLier-1].name;
                mostFooled = currfooled;
                console.log("best liar is : " + bestFooler);
            }
            var data = {}
            data['text'] = "scoreBoard";
            data['scores'] = scoreBoard;
            data['lies'] = lieBoard;
            if(bestFooler == ''){
                bestFooler = "No one";
            }
            data['best'] = bestFooler;
            socket.emit('twoTruths:nextPhase', this.props.room, data);
            React.render(<FacTwoTruthsResults best={bestFooler} lies={lieBoard} scores={scoreBoard} room={this.props.room} people={this.state.users}/>, document.getElementById("inProgressArea"));

        } else {

            React.render(<FacDisplayLie users={this.state.users} lieData={this.state.liers[this.state.currentLier]} room={this.props.room} nextPhaseRef={this.nextPhase}/>, document.getElementById("inProgressArea"));
            console.log("Current lies to best lies " + currfooled + " : " + mostFooled);
            if(currfooled > mostFooled){
                bestFooler = this.state.liers[this.state.currentLier-1].name;
                mostFooled = currfooled;
                console.log("best liar is : " + bestFooler);
            }
            currfooled = 0;

            var data = {};

            data['text'] = "nextLier";
            data['lier'] = this.state.liers[this.state.currentLier];

            this.setState({currentLier: this.state.currentLier+=1});

            socket.emit('twoTruths:nextPhase', this.props.room, data);
          //  console.log("The count is " + this.state.currentLier);
        }
    },

    updateBoard: function(data) {
        console.log(this);
        console.log("Socre is what i should be getting");
        if(data.text != "nextLier" && data.text != "skip" && data.text != "attGuess" && data.text != "reveal"  && data.text != "Ready"){
            console.log("the message was " + data.text + " " + data.id);
            if(data.id != "" && data.id != null){
                this.state.liers.push(data);
                console.log("adding more lier data");
                if(data.lie == "1"){
                    lieBoard[data.id] = data.inputOne;
                } else if(data.lie == "2") {
                    lieBoard[data.id] = data.inputTwo;
                } else {
                    lieBoard[data.id] = data.inputThree;
                }

            }
        }
        if(data.text == "attGuess") {
            console.log("I got a guess");
            if(data.correct == "yes"){
                scoreBoard[data.id] = scoreBoard[data.id] +=1;
            } else {
                currfooled+=1;
            }
            console.log("the score for " + data.name + " is " + scoreBoard[data.id] + data.id);
        }
    },

    render: function () {
        var now = new Date();
        var time = this.props.time * 60;
        //<Timer start={now} totalSeconds={time} />
        return(
            <div>
                <div id="inProgressArea">
                    <div className="vf-status">
                        <legend>Activity Progress</legend>
                        <div className="btn-group btn-group-justified">
                            <div className="btn-group vf-btn-group-status">
                                <a className="btn btn-info no-click">Input</a>
                            </div>
                            <div className="btn-group vf-btn-group-status">
                                <a className="btn btn-default no-click">Guessing</a>
                            </div>
                            <div className="btn-group vf-btn-group-status">
                                <a className="btn btn-default no-click">Results</a>
                            </div>
                        </div>
                    </div>
                    <FacControls next_phase={this.nextPhase} room={this.props.room} users={this.state.users}/>
                </div>
            </div>
        )
    }
});

module.exports = TwoTruthsFac;