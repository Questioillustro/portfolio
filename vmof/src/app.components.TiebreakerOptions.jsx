require('./app.components.PollingOptions.styl');

var React = require('react/addons');
var Tiebreaker = require('./app.components.Tiebreaker.jsx');
var WarningMessage = require('./app.components.ErrorMessageBanner.jsx');

var io = require('alias_socket_io');
var socket = io.connect();

var TiebreakerOptions = React.createClass({

    getInitialState: function ( ) {
      var num_opts = 2,
          options = [],
          maxOpts = 5;

      for (var i = 0; i < num_opts; i++)
          options.push("");

      return {
    		  options: options, maxOpts: maxOpts
    	}
    },

    startActivity: function ( ) {
        var now = new Date(),
            options = {};


        $('#tiebreaker-options input').each( function () {
            if(this.id !== '' && this.value.trim() !== '') //Ignore null elements
                options[this.id] = this.value;
        });

        if (Object.keys(options).length < 2) {
          React.render(<WarningMessage message="There must be at least two valid options to begin."/>, document.getElementById('messageCenter'));
          return;
        }
        
        $('.vf-nav button').attr('disabled',true);

        React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
        React.render(<Tiebreaker room={this.props.room} end={this.props.cancel} options={options} />, document.getElementById('vf-activity-content'));
    },

    addIdea: function(){
        var options = this.state.options;

        if (options.length === this.state.maxOpts) {
          React.render(<WarningMessage message="Maximum number of options reached."/>, document.getElementById('messageCenter'));
          return;
        }

        options.push("");
        this.setState({ options: options });
    },

    render: function () {

        var renderIdea = function(idea, index){
          return (
              <div className="form-group">
                <h4>Option #{index+1}</h4>
                <div>
                  <input className="form-control" id={index} placeholder="First idea that you would like to vote on."></input>
                </div>
              </div>
          )
        }

        return (
            <div>
              <div id="tiebreaker-options" className="well">
                    <legend>Tiebreaker</legend>

                    <div id="messageCenter"></div>
                    
                    <div className="form-group">
                      <h4>Summmary</h4>
                      <p>
                        Enter in a set of options and the system will randomly choose a winner.
                      </p>
                    </div>

                    {this.state.options.map(renderIdea)}
                                      
                    <div className="form-group has-error">
                      <div>
                        <button className="btn btn-info vf-activity-cancel" style={{marginRight: '20px'}} onClick={this.props.cancel}>Cancel</button>
                        <button className="btn btn-primary" onClick={this.startActivity}>Start</button>
                        <button type="button" className="btn btn-success" onClick={this.addIdea} style={{'float':'right'}}>Add Option</button>
                      </div>
                    </div>
              </div>
            </div>
        )
    }
});

module.exports = TiebreakerOptions;