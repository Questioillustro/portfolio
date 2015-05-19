require('./app.components.TwoTruthsOptions.styl');
var React = require('react/addons');

var FacControls = require('./app.components.TwoTruths.FacControls.jsx');

var io = require('alias_socket_io');
var socket = io.connect();

var lie = "";
var status = "Reveal"

var FacDisplayLie = React.createClass({

    getInitialState: function() {
        return {};
    },

    componentWillUnmount: function () {
         React.unmountComponentAtNode(document.getElementById('lieTable'));
    },

    revealTruth: function (){
        console.log("revealing Truth");
        if( status == "Next"){
            var data = {};
            data['text'] = "skip";
         //   document.getElementById('inputButton').innerHTML = "Reveal";
            status = "Reveal";
            this.props.nextPhaseRef();

        } else {
            var data = {};
            console.log("sending the reveal message");
            data['text'] = "reveal";
            socket.emit('revealLie', this.props.room, data);
            status = "Next";
            console.log("CLICK");
            document.querySelector('.card').classList.toggle("flip");
          //  document.getElementById('inputButton').innerHTML = "Next";
        }

    },

    clickMe: function (){
          console.log("CLICK");
          document.querySelector('.card').classList.toggle("flip");
    },

    render: function () {
        //TODO create new way to show the answer
        var liers = this.props.users;
        var lierID = this.props.lieData.id;
        
        console.log("hello " +this.props.users);
        var guessers = liers.filter(function (user) {
            if(user.id != lierID){
                return user;
            }
        });
        console.log("the guessers are: " + guessers);

        if(this.props.lieData.lie == "1"){
            lie = this.props.lieData.inputOne;
        } else if( this.props.lieData.lie == "2"){
            lie = this.props.lieData.inputTwo;
        } else {
            lie = this.props.lieData.inputThree;
        }
        console.log("li is " + lie);
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
                <div id="theGrid">
                   <div id="card" className="card"> 
                      <div className="front"> 
                        The liar is {this.props.lieData.name}...
                      </div> 
                      <div className="back well">
                        {lie}
                    </div> 
                  </div>
                    <div id="lieTable">
                        <FacControls next_phase={this.revealTruth} room={this.props.room} users={guessers}/>
                    </div>
                </div>
            </div>
        )
    }
});

module.exports = FacDisplayLie;