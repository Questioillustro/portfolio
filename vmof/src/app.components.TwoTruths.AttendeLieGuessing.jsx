require('./app.components.TwoTruthsOptions.styl');
require('../node_modules/bootstrap-styl/js/alert.js');

var React = require('react/addons');
var io = require('alias_socket_io');
var socket = io.connect();
var lieStr = "";

var AlertMessageWarn2 = React.createClass({
    render: function () {
      var divStyle = {
        display: 'block'
      };
        return(
            <div className="alert alert-dismissible alert-danger" style={divStyle}>
              <button type="button" className="close" data-dismiss="alert">×</button>
              <strong>Wait a second</strong> {this.props.message}
            </div>
        )
      }
});

var AlertMessageSucc2 = React.createClass({

    render: function () {
        return(
            <div className="alert-success"><b>Thank you: </b>{this.props.message}</div>

        )
      }
});


var Correct = React.createClass({

    render: function () {
        return(
            <div><h2 className="green"><span className="glyphicon glyphicon-ok"></span> You weren't fooled!</h2></div>
        )
      }
});

var Wrong = React.createClass({

    render: function () {
        return(
            <div><h2 className="red"><span className="glyphicon glyphicon-remove"></span> You got fooled!</h2></div>

        )
      }
});



var AttLieGuess = React.createClass({

    getInitialState: function () {
      
        return {
            name: "",
            id: "",
            guess: ""
        }
    },

    componentDidMount: function(){
        socket.on('showLie', this.checkMessageBox);

    },

    componentWillUnmount: function () {
        socket.removeAllListeners('showLie');
      //  React.unmountComponentAtNode(document.getElementById('mesasgeCenter'));
    },

    checkMessageBox: function(data) {

      console.log(this);

      if(data.text == "reveal") {
        console.log("reveal the answer");
        $('input').each(function () {
          this.disabled = true;

        })
        document.getElementById('inputButton').disabled = true;
        document.getElementById('resultInfo').innerHTML = "";
        if(this.props.lieData.lie == this.state.guess){
          React.render(<Correct/>, document.getElementById('resultInfo'));
        } else {
          React.render(<Wrong/>, document.getElementById('resultInfo'));
        }
        console.log("Showing the correct answer");
        $('#answerInfo').fadeIn();
        console.log("The right answe was revealed");
      }
    },

    selectAnswer: function(e) {
      this.setState({ guess: e.target.id });
    },

    submitAnswer: function (){
      var data = {};

      data['text'] = "attGuess";
      data['guess'] = this.state.guess;
      data['name'] = this.props.name;
      data['id'] = this.props.id;

      console.log("The name is " + this.props.name);

      if(this.state.guess == this.props.lieData.lie){
        data['correct'] = "yes";
        console.log("you guess right");
      } else {
        data['correct'] = "no";
      }

      if(this.state.guess == ""){
        React.unmountComponentAtNode(document.getElementById('mesasgeCenter'));
        React.render(<AlertMessageWarn2 message="Please select a lie"/>, document.getElementById('mesasgeCenter'));
      } else {
        React.unmountComponentAtNode(document.getElementById('mesasgeCenter'));
        React.render(<AlertMessageSucc2 message="Your guess has been noted"/>, document.getElementById('mesasgeCenter'));
        $('input').each(function () {
          this.disabled = true;

        })


        var guess;

        if(this.state.guess == "1"){
          guess = this.props.lieData.inputOne;
          document.getElementById('myGuess').innerHTML = "Your guess: " + guess;
        } else if(this.state.guess == "2"){
          guess = this.props.lieData.inputTwo;
          document.getElementById('myGuess').innerHTML = "Your guess: " + guess;
        } else {
          guess = this.props.lieData.inputThree;
          document.getElementById('myGuess').innerHTML = "Your guess: " + guess;
        }

        $('#answerPanel').fadeIn();
        $('#guessForm').fadeOut();

        document.getElementById('inputButton').disabled = true;
        socket.emit('broadCastData', this.props.room, data);
        console.log("Submitted Guess");
        var ready = {};

        ready['text'] = "Ready";
        ready['name'] = this.props.name;
        ready['id'] = this.props.id;

        socket.emit('readyUp', this.props.room, ready);
      }
    },

    render: function () {
        if(this.props.lieData.lie == "1"){
          lieStr = this.props.lieData.inputOne;
          
        } else if(this.props.lieData.lie == "2"){
          lieStr = this.props.lieData.inputTwo;
          
        } else {
          lieStr = this.props.lieData.inputThree;
         
        }

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
            <div id="answerPanel" className="col-md-offset-3 col-md-6 hideMe">
              <div className="panel panel-info">
                <div className="panel-heading">
                  <h3 className="panel-title">Answer</h3>
                </div>
                <div className="panel-body">
                <div className="row">
                  <div className="centered">
                    <h3 id="myGuess">You think the lie is: </h3>
                  </div>
                </div>
                <div className="centered" id="resultInfo">
                    <img className="gifDisplayer" src="/img/loading5.gif"/>
                    <span className="help-block">Waitng for the big reveal</span>
                </div>

                <div id="answerInfo" className="centered hideMe">
                  <h4>{this.props.lieData.name}'s lie: {lieStr}</h4>
                </div>
                </div>
              </div>
            </div>
            <div id="guessForm">
              <div className="col-md-offset-3 col-md-6">
                <div className="panel panel-primary">
                  <div className="panel-heading">
                    <h3 className="panel-title">{this.props.lieData.name} is lying, but about what?</h3>
                  </div>
                  <div className="panel-body">
                        <div id="mesasgeCenter" className="form-group optionsPadding">

                        </div>
                     <div className="form-horizontal">
                      <fieldset>
                        <div className="form-group optionsPadding">
                          <span className="help-block"> 
                            Select which option you think is the lie
                          </span>
                        </div>
                        <div className="form-group optionsPadding optionsPadding">
                            <div className="input-group">
                              <span className="input-group-addon inp">
                                <input type="radio" id="1" name="lie" onClick={this.selectAnswer}/>
                              </span>
                              <p type="text" id="three" className="form-control"> {this.props.lieData.inputOne} </p>
                            </div>
                        </div>

                        <div className="form-group optionsPadding">
                            <div className="input-group">
                              <span className="input-group-addon inp">
                                <input type="radio" id="2" name="lie" onClick={this.selectAnswer}/>
                              </span>
                              <p type="text" id="three" className="form-control"> {this.props.lieData.inputTwo} </p>
                            </div>
                        </div>

                        <div className="form-group optionsPadding">
                            <div className="input-group">
                              <span className="input-group-addon inp">
                                <input type="radio" id="3" name="lie" onClick={this.selectAnswer}/>
                              </span>
                              <p type="text" id="three" className="form-control"> {this.props.lieData.inputThree} </p>
                            </div>
                        </div>

                        <div className="form-group centered">
                            <button id="inputButton" className="btn btn-primary" onClick={this.submitAnswer}>Submit</button>
                        </div>
                      </fieldset>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        )
    }
});

module.exports = AttLieGuess;