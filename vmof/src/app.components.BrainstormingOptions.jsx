var React = require('react/addons');
var MinutesInputWidget = require('./app.components.MinutesInputWidget.jsx');

var ModalWarningMixin = require('./app.components.mixin.ModalWarning.js');

var io = require('alias_socket_io');
var socket = io.connect();

var BrainstormingOptions = React.createClass({

    mixins: [ModalWarningMixin],

    getInitialState: function () {
        return {
            activity_duration: 15,
            advancedVisible: false
        }
    },

    componentDidMount: function () {
        this.initializeImgUpload();
        
        // Initialize modal warning
        var startWarning = "Participants are not permitted to join an activity late. Press OK to start the activity. Press Cancel to wait for everyone to join.";
        this.initializeWarning( startWarning, this.startActivity );
    },

    initializeImgUpload: function () {
        $(":file").change(function () {
            if (this.files && this.files[0]) {
                var reader = new FileReader();
                reader.onload = imageIsLoaded;
                reader.readAsDataURL(this.files[0]);
            }
        });

        function imageIsLoaded(e) {
            $('#myImg').attr('src', e.target.result).show();
        }
    },

    attemptStartActivity: function () {
        this.warningMessage();
    },

    startActivity: function () {
        var parameters = {
            'activity-selection': 'Anonymous Brainstorming'
        };

        $('select,input,textarea').each(function () {
            if(this.id !== '') //Ignore null id elements
                parameters[this.id] = this.value;
        });

        var image = $('#myImg').attr('src');
        parameters.image = image;

        if (image !== '#')
            $('#uploading-display').attr('style', '');
        
        socket.emit('start:anonymous:brainstorming', this.props.room, parameters);
    },

    timeChanged: function () {
        var sum = 0;

        $('.round-time input[type="number"]').each(function () {
            sum += parseInt(this.value);
        });

        this.setState({activity_duration: sum});
    },

    showOptions: function (evt) {
        var $btn = $(evt.target);

        if (!this.state.advancedVisible) {
            $('#advanced-options').fadeIn(500);
            this.setState({advancedVisible: true});
            $btn.addClass('active');
        }
        else {
            $('#advanced-options').fadeOut(500);
            this.setState({advancedVisible: false});
            $btn.removeClass('active');
        }
    },

    render: function () {
        
        var self = this;

        return (
            <div id="anon-brainstorm-opts" className="">
                
                <div className="well well-lg">
                    <legend>Anonymous Brainstorming</legend>

                    <div className="input-div">
                        <label className="control-label">
                            <h4>Summary</h4>
                        </label>
                        <div id="brainstorm_summary">
                            Anonymous brainstorming should allow participants to freely input ideas without judgment. 
                            Our implementation of this supports 3 distinct rounds:
                            <br/>
                            1) Brainstorming allows participants to generate ideas in real time for a specified duration. <br/>
                            2) The commentary round allow participants to build off of each other&apos;s ideas and upvote or downvote ideas to show their opinion. <br/>
                            3) The final phase provides the participants the opportunity to discuss and agree upon the strongest ideas and assign action items for follow up.
                        </div>
                    </div>

                    <div className="input-div" style={{marginBottom: '20px'}}>
                        <label className="control-label">
                            <h4>Anticipated activity length using current settings</h4>
                        </label>
                        <div id="activity-duration">{this.state.activity_duration} minutes</div>
                    </div>

                    <button className="btn btn-info vf-activity-cancel" style={{marginRight: '20px'}} onClick={this.props.cancel}>Cancel</button>
                    <button id="start_activity" className="btn btn-primary" style={{marginRight: '20px'}} onClick={self.attemptStartActivity}>Start</button>
                    <button id="show-advanced-options" className="btn btn-success" style={{'float':'right'}} onClick={self.showOptions}>Advanced</button>

                </div>

                <div id="advanced-options" className="well col-xs-12" style={{'display':'none'}}>
                    <div className="input-div">
                        <h4>Upload an Image</h4>
                        <div>
                            <input type="file" />
                            <img id="myImg" src="#" style={{'width':'400px', 'display':'none'}} />
                        </div>
                    </div>

                    <div id="uploading-image">
                        <div id="uploading-display" style={{'visibility':'hidden'}}>
                            <img className="gifDisplayer" src="/img/loading5.gif" style={{'height':'18px'}}/>
                            Uploading Image
                        </div>
                    </div>

                    <div className="input-div">
                        <h4>Pose a Question</h4>
                        <div>
                            <textarea id="question" className="form-control" placeholder="ex: How can we provide better customer service?"></textarea>
                        </div>
                    </div>

                    <div className="input-div">
                        <h4>Set Ground Rules</h4>
                        <div>
                            <textarea id="rules" className="form-control" placeholder="ex: All ideas count, No arguing"></textarea>
                        </div>
                    </div>
                        
                    <div className="input-div">
                        <MinutesInputWidget title="Ideas per Participant" defaultval="3" maxLimit="5" minLimit="1" id="num_ideas" />
                    </div>

                    <div className="input-div round-time">
                        <MinutesInputWidget title="Brainstorming Time Limit (minutes)" maxLimit="30" defaultval="2" id="brainstorm_limit" changeCallback={this.timeChanged} />
                    </div>

                    <div className="input-div round-time">
                        <MinutesInputWidget title="Commentary Time Limit (minutes)" maxLimit="30" defaultval="3" id="commentary_limit"  changeCallback={this.timeChanged} />
                    </div>

                    <div className="input-div round-time">
                        <MinutesInputWidget title="Discussion Time Limit (minutes)" maxLimit="30" defaultval="10" id="discussion_limit"  changeCallback={this.timeChanged} />
                    </div>
                </div>
            </div>
        )
    }
});

module.exports = BrainstormingOptions;
