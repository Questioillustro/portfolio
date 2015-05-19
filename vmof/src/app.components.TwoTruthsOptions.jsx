require('./app.components.TwoTruthsOptions.styl');

var React = require('react/addons');
var MinutesInputWidget = require('./app.components.MinutesInputWidget.jsx');
var IndividualScore = require('./app.components.IndividualScore.jsx');
var TwoTruthsFac = require('./app.components.TwoTruths.FacilitatorInProgress.jsx')
var ModalWarningMixin = require('./app.components.mixin.ModalWarning.js');

var io = require('alias_socket_io');
var socket = io.connect();


var TwoTruths = React.createClass({

    mixins: [ModalWarningMixin],

    getInitialState: function () {
        return {rules: 'There are no rules',
                timeL: 10}
    },

    componentDidMount: function (){
            // Initialize modal warning
        var startWarning = "Participants are not permitted to join an activity late. Press OK to start the activity. Press Cancel to wait for everyone to join.";
        this.initializeWarning( startWarning, this.startActivity );
    },

    attemptActivity: function() {
      this.warningMessage();
    },

    startActivity: function () {
        var paramaters ={}
        if(this.state.rules.trim() == ""){
          paramaters['activity-rules'] = "There are no rules";
        } else {
          paramaters['activity-rules'] = this.state.rules;
        }
        paramaters['activity-selection'] = "TwoTruths";
        paramaters['timeLimit'] = this.state.timeL;

        //Disable fac activity buttons
        $('.vf-nav button').attr('disabled',true);

        socket.emit('start:TwoTruths', this.props.room, paramaters) ;
        React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
        React.render(<TwoTruthsFac room={this.props.room} time={this.state.time}/>, document.getElementById('vf-activity-content'));
    },

    setRules: function(e) {
        this.setState({ rules: e.target.value });
    },

    timeChanged: function () {
        var timeSelected = document.getElementById('time').value;
        this.setState({timeL: parseInt(timeSelected)})
    },

    clickMe: function (){

      $(document.body).on('click', '.card', function() {
          document.querySelector('.card').classList.toggle("flip");
      });

    },

    render: function () {
        return(
            <div>
                <div id="two-truths-options" className="well well-lg bs-component">
                    <legend>Two Truths and a Lie</legend>
                    
                    <div className="form-group">
                      {/*<label htmlFor="textArea" className="control-label">Summmary</label>*/}
                      <h4>Summmary</h4>
                      <div className="vf-form-field-padding">
                        <p>
                            Two Truths and Lie is a game all about fooling one another with random facts about yourself.  It is a great way to introduce one another and learn something new.
                        </p>
                      </div>
                    </div>
                    
                    <div className="form-group">
                      {/*<label htmlFor="textArea" className="control-label">Rules</label>*/}
                      <h4>Rules</h4>
                      <div className="vf-form-field-padding">
                        <textarea className="form-control" rows="3" id="textArea" placeholder="ex: Facts can't contain swear words." onChange={this.setRules}></textarea>
                      </div>
                    </div>
                    
                    <div className="form-group">
                      {/*<label className="control-label">Time Limit</label>*/}
                      <h4>Time Limit</h4>
                      <div id="timeWidget" className="vf-form-field-padding">
                        <MinutesInputWidget id="time"  maxLimit="15" defaultval="10" changeCallback={this.timeChanged}/>
                      </div>
                    </div>
                    
                    <div className="form-group">
                      <div className="vf-form-field-padding">
                        <button className="btn btn-info vf-activity-cancel" style={{marginRight: '20px'}}onClick={this.props.cancel}>Cancel</button>
                        <button className="btn btn-primary" onClick={this.attemptActivity}>Start</button>
                      </div>
                    </div>
                </div>
            </div>

        )
    }
});

module.exports = TwoTruths;
