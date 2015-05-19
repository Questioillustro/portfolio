require('./app.components.PollingOptions.styl');
require('../node_modules/bootstrap-styl/js/alert.js');
var React = require('react/addons');
var PollingFac = require('./app.components.Polling.FacilitatorInProgress.jsx');

var ModalWarningMixin = require('./app.components.mixin.ModalWarning.js');
var WarningMessage = require('./app.components.ErrorMessageBanner.jsx');

var io = require('alias_socket_io');
var socket = io.connect();

var PollingOptions = React.createClass({

    mixins: [ModalWarningMixin],

    getInitialState: function () {
      var num_opts = 2,
          ideas = [];

      for (var i = 0; i < num_opts; i++) {
          ideas.push("");
      }

    	return {
          num_of_opts: num_opts, 
          ideas: ideas,
    	}
    },

    componentDidMount: function() {
      var updateTitle = "Facilitator is setting up a Poll";
      socket.emit('updateMeetingStatus', this.props.room, updateTitle);

      // Initialize modal warning
      var startWarning = "Participants are not permitted to join an activity late. Press OK to start the activity. Press Cancel to wait for everyone to join.";
      this.initializeWarning( startWarning, this.startActivity );
    },

    componentDidUpdate: function() {
      this.checkSize();
    },

    startActivity: function () {

        var color = [ "#FF3030", "#0066FF", "#FF3300", "#FF8330", "#009900",
                      "#FF66FF", "#9966FF", "#835930", "#4D9B9B", "#33AD5C"];

        var parameters ={
          'activity-selection': 'Polling',
          options: [],
          question: "",
          breadCrumbs: [
            {
              'title' : 'Voting',
              'help'  : 'During this phase select the option that you would like to choose.',
            },
            {
              'title' : 'Results',
              'help'  : 'During this phase you will see the results of the poll.'
            }
          ],
          phase: 0,
        };

        var counter = 0;
        var floatValue = (0).toFixed(2);
        $('input').each(function () {
            if (this.name === 'option' && this.value !== "") {
              parameters.options.push([this.value, 0, floatValue, color[counter]]);
              counter++;
            } else if (this.id === 'question') {
              parameters.question = this.value;
            }
        });

        if(parameters.options.length > 1 && parameters.question !== ""){
            socket.emit('startActivity', this.props.room, parameters);
            React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
            React.render(<PollingFac room={this.props.room} params={parameters} />, document.getElementById('vf-activity-content'));
            $('.vf-nav button').attr('disabled',true);
        } else {
            React.render(<WarningMessage message="You need two options and a question before you can start voting."/>, document.getElementById('mesasgeCenter'));
        }
    },

    attemptStartActivity: function() {
      this.warningMessage();
    },

    addIdea: function(){
      if (this.state.num_of_opts < 10){
        var newIdea = this.state.ideas;
        newIdea.push("");
        this.setState({ ideas: newIdea});
        var newTotal = (this.state.num_of_opts + 1);
        this.setState({ num_of_opts: newTotal});
      }
    },

    checkSize: function(){
      if (this.state.num_of_opts > 9){
        document.getElementById("addIdea").style.visibility = 'hidden';
      }
    },

    render: function () {

      var renderIdea = function(idea, index){
        return(
          <div className="form-group col-sm-6">
            <label className="option-label">Option #{index + 1}</label>
            <div>
              <input className="form-control" name="option" placeholder="An option that you would like to vote on."></input>
            </div>
          </div>
        )
      }

        return(
              <div className="well">
                <div className="row">
                  <div className="legend col-sm-12">
                    <legend>Polling</legend>
                  </div>
                  <div className="optionsPadding col-sm-12">
                    <div id="mesasgeCenter" className="optionsPadding"></div>
                  </div>
                  <div className="heading col-sm-12">
                    <h4 className="control-label">Summmary</h4> 
                    <div className="helpText">
                      <p>
                        Multiple ideas are proposed and the attendees vote on which one they would like. This requires two options and a question to be presented.
                      </p>          
                    </div>
                  </div>
                  <div className="col-sm-12">
                    <label className="question-label">Question</label>
                    <input className="form-control" id="question" placeholder="What would you like to poll the room on?" onChange={this.setQuestion}></input>
                  </div>

                  {this.state.ideas.map(renderIdea)}

                  <div className="buttonContainer col-sm-12">
                      <button className="btn btn-info vf-activity-cancel cancel" onClick={this.props.cancel}>Cancel</button>
                      <button type="submit" className="btn btn-primary start" onClick={this.attemptStartActivity}>Start</button>
                      <button id="addIdea" className="addIdea btn btn-success" onClick={this.addIdea}>Add Option</button>
                  </div>
                </div>
              </div>
        )
    }
});

module.exports = PollingOptions;