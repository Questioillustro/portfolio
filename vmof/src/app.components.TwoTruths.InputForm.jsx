require('./app.components.TwoTruthsOptions.styl');
require('../node_modules/bootstrap-styl/js/alert.js');
var React = require('react/addons');

var AutoScroll = require('./app.components.mixin.AutoScroll.js');

var io = require('alias_socket_io');
var socket = io.connect();

var AlertMessageWarn = React.createClass({

    render: function () {
      var divStyle = {
        display: 'block'
      };
        return(
            <div className="alert alert-dismissible alert-danger" style={divStyle}>
              <button type="button" className="close" data-dismiss="alert">×</button>
              <strong>Danger Will Robinson:</strong> {this.props.message}
            </div>
        )
      }
});

var AlertMessageSucc = React.createClass({

    render: function () {
        return(
            <div className="alert-success"><b>You did it!: </b>{this.props.message}</div>

        )
      }
});

var TwoTruthsInForm = React.createClass({

    mixins: [AutoScroll],

    getInitialState: function () {
        console.log("intial state");
        return {
            lie: "",
            inputOne: "",
            inputTwo: "",
            inputThree: "",
        }
    },

    componentDidMount: function(){
        this.initializeAutoScroll();
    },

    componentWillUnmount: function () {
    },

    updateInfo: function(e) {
    //  console.log("UpdateInfo");
      if(e.target.id == "one"){
        this.setState({ inputOne: e.target.value});
      } else if (e.target.id == "two") {
        this.setState({ inputTwo: e.target.value});
      } else {
        this.setState({ inputThree: e.target.value});
      }
    },

    selectLie: function(e) {
      console.log("SelectLie");
      this.setState({ lie: e.target.id });
    },

    submitResponse: function() {
  //    console.log(this.state.lie);
  //    console.log(this.state.inputOne);
  //    console.log(this.state.inputTwo);
  //    console.log(this.state.inputThree);
      console.log("Submit Response");
      if(this.state.lie == ""){
   //     console.log('potato');
        React.unmountComponentAtNode(document.getElementById('mesasgeCenter'));
        React.render(<AlertMessageWarn message="You need to select a lie"/>, document.getElementById('mesasgeCenter'));
      } else if( this.state.inputOne == "" || this.state.inputTwo == "" || this.state.inputThree == "") {
        React.unmountComponentAtNode(document.getElementById('mesasgeCenter'));
        React.render(<AlertMessageWarn message="You have to fill out all input fields"/>, document.getElementById('mesasgeCenter'));
      } else {
        React.unmountComponentAtNode(document.getElementById('mesasgeCenter'));
        React.render(<AlertMessageSucc message="Now sit back and wait till the next round"/>, document.getElementById('mesasgeCenter'));
        document.getElementById('inputButton').disabled = true;
        $('input').each(function () {
          this.disabled = true;

        })
        $('#panel_prim').fadeOut();
        $('#waiting').removeClass('hidden');
        var info = {};

        info['name'] = this.props.name;
        info['id'] = this.props.id;
        info['lie'] = this.state.lie;
        info['inputOne'] = this.state.inputOne;
        info['inputTwo'] = this.state.inputTwo;
        info['inputThree'] = this.state.inputThree;
        info['text'] = "ReadyUp";


       // info['input'] = this.state.input;
  //      console.log('potato');
        console.log(info['name'] + " what the hell");
        socket.emit('broadCastData', this.props.room, info) ;
        socket.emit('readyUp', this.props.room, info);
      }
    },

    render: function () {
 //     console.log("Two in form");
        return(
          <div>
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
          <legend>
            Two Truths and a Lie
          </legend>
          <div className="col-md-offset-3 col-md-6">
            <div className="panel panel-info">
              <div className="panel-heading">
                <h3 className="panel-title">Rules</h3>
              </div>
              <div className="panel-body">
                {this.props.rules}
              </div>
            </div>
          </div>

          <div id="waiting" className="col-md-offset-3 col-md-6 hidden">
            <div className="panel panel-primary">       
              <div className="panel-body ">
                <h3 className="centered">
                  Please wait while others input their two truths and a lie
                </h3>
                <div>
                  <img className="gifDisplayer" src="/img/loading5.gif"/>
                </div>
              </div>
            </div>
          </div>

          <div id="panel_prim" className="col-md-offset-3 col-md-6">
            <div className="panel panel-primary">
                <div id="input_panel" className="panel-heading">
                  <h3 className="panel-title">Input</h3>
                </div>
                <div className="panel-body">
                        <div id="mesasgeCenter" className="form-group optionsPadding">

                        </div>
                  <div className="form-horizontal">
                      <fieldset>
                      <div className="form-group optionsPadding">
                        <span className="help-block"> 
                          Enter two truths and one lie and select which one is your lie
                        </span>
                      </div>
                        <div className="form-group optionsPadding">
                            <div className="input-group">
                              <span className="input-group-addon inp">
                                <input type="radio" id="1" name="lie" onClick={this.selectLie}/>
                              </span>
                              <input type="text" id="one" className="form-control" onChange={this.updateInfo}/>
                            </div>
                        </div>

                        <div className="form-group optionsPadding">
                            <div className="input-group">
                              <span className="input-group-addon inp">
                                <input type="radio" id="2" name="lie" onClick={this.selectLie}/>
                              </span>
                              <input type="text" id="two" className="form-control" onChange={this.updateInfo}/>
                            </div>
                        </div>

                        <div className="form-group optionsPadding">
                            <div className="input-group">
                              <span className="input-group-addon inp">
                                <input type="radio" id="3" name="lie" onClick={this.selectLie}/>
                              </span>
                              <input type="text" id="three" className="form-control" onChange={this.updateInfo}/>
                            </div>
                        </div>

                        <div className="form-group centered">
                            <button id="inputButton" className="btn btn-primary" onClick={this.submitResponse}>Submit</button>
                        </div>
                      </fieldset>
                    </div>
                </div>
            </div>
          </div>
        </div> 
        )
    }
});

module.exports = TwoTruthsInForm;