var EmpathyMapDisplay = require('./app.components.EmpathyMapDisplay.jsx');
var ModalWarningMixin = require('./app.components.mixin.ModalWarning.js');
//require('../node_modules/bootstrap-styl/js/alert.js');
var React = require('react/addons');
var io = require('alias_socket_io');
var socket = io.connect();

var EmpathyMapOptions = React.createClass({

    mixins: [ModalWarningMixin],

    componentDidMount: function () {
        socket.on('empathy:map:validate', this.validated);
        socket.on('empathy:map:start', this.started);
        var data;
        data =  'Facilitator is setting up Empathy Map';
        socket.emit('updateMeetingStatus',this.props.room, data);

        // Initialize modal warning
        var startWarning = "Participants are not permitted to join an activity late. Press OK to start the activity. Press Cancel to wait for everyone to join.";
        this.initializeWarning(startWarning, this.startActivity);
    },

    componentWillUnmount: function () {
        socket.removeAllListeners('empathy:map:start');
        socket.removeAllListeners('empathy:map:validate');
    },


    started: function (state) {
        
    },

    validated: function (error) {
        console.log('empathy map error', error);
        if(error) {
            $('#vf-empathy-map-error').css('display', 'block');
            // if($('#vf-empathy-map-error').hasClass('hidden')) {
            //     $('#vf-empathy-map-error').removeClass('hidden');
            // }
            //$('#vf-empathy-map-error').innerHTML = '<div class="alert alert-dismissible alert-danger"><button type="button" class="close" data-dismiss="alert">×</button>This activity is restricted to a <strong>maximum of eight</strong> participants.</div>';
        } else {
            var parameters = {
                topic: "",
                comment_time: 10
            };
            
            $('#topic').each(function () {
                parameters[this.id] = this.value;
            });
            
            //Disable fac activity buttons
            $('.vf-nav button').attr('disabled',true);
            
            socket.emit('empathy:map:start', this.props.room, parameters);
            React.unmountComponentAtNode(document.getElementById('vf-activity-content'));
            React.render(<EmpathyMapDisplay room={this.props.room} />, document.getElementById('vf-activity-content'));
        }
    },

    attemptStartActivity: function() {
      this.warningMessage();
    },

    startActivity: function () {
        socket.emit('empathy:map:validate');
    },

    dismissError: function () {
        // if (!$('#vf-empathy-map-error').hasClass('hidden')) {
        //     $('#vf-empathy-map-error').addClass('hidden');
        // }
        $('#vf-empathy-map-error').css('display', 'none');
    },

    render: function () {
        return (
            <div>
                <div className="well bs-component">
                    <legend>Empathy Map</legend>
                    
                    {/*<div id="vf-empathy-map-error" className="hidden">*/}
                    <div id="vf-empathy-map-error" style={{display: 'none'}}>
                        <div className="alert alert-dismissible alert-danger">
                             {/*data-dismiss="alert"*/}
                            <button type="button" className="close" onClick={this.dismissError}>×</button>
                            This activity is restricted to a <strong>minimum of one</strong> and a <strong>maximum of eight</strong> participants.
                        </div>
                    </div>
                    
                    <div className="form-group">
                        {/*<label className="control-label">Summary</label>*/}
                        <h4>Summary</h4>
                        <div className="vf-form-field-padding">
                            An emapthy map is a tool to help you better understand the customer. In this activity each participant will contribute an idea to each of the following six sections of an empathy map: Think & Feel, Hear, See, Say & Do, Pain, and Gain. Participants will then comment on and discuss these ideas. This activity is restricted to a maximum of eight participants.
                        </div>
                    </div>

                    {/*<div className="form-group">
                        <label className="control-label">Phase Time</label>
                        <div className="vf-form-field-padding">
                            <input type="range" />
                        </div>
                        <span className="help-block">The time that participants have to submit ideas for each part of the empathy map.</span>
                    </div>*/}
                    
                    <div className="form-group">
                        <div>
                            <button className="btn btn-info vf-activity-cancel" style={{marginRight: '20px'}} onClick={this.props.cancel}>Cancel</button>
                            {/*<button type="submit" className="btn btn-primary" onClick={this.startActivity}>Start</button>*/}
                            <button type="submit" className="btn btn-primary" onClick={this.attemptStartActivity}>Start</button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
});

module.exports = EmpathyMapOptions;