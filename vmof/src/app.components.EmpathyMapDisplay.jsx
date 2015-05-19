require('./app.components.EmpathyMapDisplay.styl');
require('../node_modules/dotdotdot/src/js/jquery.dotdotdot.min.js');
var React = require('react/addons');
var io = require('alias_socket_io');
var socket = io.connect();

var IMG_WIDTH = 1024;
var IMG_HEIGHT = 719;

//var ellipsis_flag = true;
var firstMount = true;

var EmpathyMapDisplay = React.createClass({

    getInitialState: function () {
        return { think: ['', '', '', '', '', '', '', ''],
                 hear: ['', '', '', '', '', '', '', ''],
                 see: ['', '', '', '', '', '', '', ''],
                 say: ['', '', '', '', '', '', '', ''],
                 pain: ['', '', '', '', '', '', '', ''],
                 gain: ['', '', '', '', '', '', '', ''],
                 cnt_think: 0,
                 cnt_hear: 0,
                 cnt_see: 0,
                 cnt_say: 0,
                 cnt_pain: 0,
                 cnt_gain: 0,
                 prevWidth: 0,
                 prevHeight: 0 };
    },

    adjustStickyNotes: function (prevWidth, prevHeight) {
        //var ratio = 0.1;
        var ratio = 0.07;
        var width = $('.map img').width();
        var height = $('.map img').height();

        $('.item').width(width * ratio).height(width * ratio);

        $('.item').each(function () {
            //$(this).css('top', ((height * parseFloat($(this).css('top'))) / (prevHeight+140)) + 'px');
            // $(this).css('top', ((height * parseFloat($(this).css('top'))) / prevHeight) + 'px');
            // $(this).css('left', ((width * parseFloat($(this).css('left'))) / prevWidth) + 'px');
            $(this).css('top', ((parseFloat($(this).css('top')) / prevHeight) * height) + 'px');
            $(this).css('left', ((parseFloat($(this).css('left')) / prevWidth) * width) + 'px');
        });
    },

    componentDidMount: function () {
        socket.on('empathy:map:submit:idea', this.revealSticky);
        socket.on('empathy:map:facilitator:next:phase', this.nextPhase);
        var data = 'Empathy Map activity in progress';
        socket.emit('updateMeetingStatus',this.props.room, data);

        // $('.item').each(function () {
        //     $(this).css('top', ((parseFloat($(this).css('top'))) + 140) + 'px');
        // });
        var self = this;
if (firstMount) {
        $(document).ready(function() {
            $('.map img').load(function () {
                $('.item').each(function () {
                    $(this).css('margin-top', ((parseFloat($(this).css('margin-top'))) + 120) + 'px');
                });

                self.adjustStickyNotes(IMG_WIDTH, IMG_HEIGHT);

                // var prevWidth = $('.map img').width();
                // var prevHeight = $('.map img').height();
                window.prevWidth = $('.map img').width();
                window.prevHeight = $('.map img').height();
                

                    // $(window).resize(function() {
                    //     self.adjustStickyNotes(prevWidth, prevHeight);
                        
                    //     prevWidth = $('.map img').width();
                    //     prevHeight = $('.map img').height();
                    // });                
                    $(window).resize(function() {
                        self.adjustStickyNotes(window.prevWidth, window.prevHeight);
                        
                        window.prevWidth = $('.map img').width();
                        window.prevHeight = $('.map img').height();
                    });
                    // firstMount = false;
                //} //else {
                    //self.adjustStickyNotes(IMG_WIDTH, IMG_HEIGHT);
                //}
            });
        });
        firstMount = false;
} else {
    console.log('not first mount');
    $('.map img').load(function () {
        $('.item').each(function () {
           $(this).css('margin-top', ((parseFloat($(this).css('margin-top'))) + 120) + 'px');
        });

        self.adjustStickyNotes(IMG_WIDTH, IMG_HEIGHT);

        window.prevWidth = $('.map img').width();
        window.prevHeight = $('.map img').height();
        //self.adjustStickyNotes(window.prevWidth, window.prevHeight);
    });
}
    },

    componentWillUnmount: function () {
        socket.removeAllListeners('empathy:map:submit:idea');
        socket.removeAllListeners('empathy:map:facilitator:next:phase');
        $('#status-map').removeClass('btn-default').addClass('btn-info');
        $('#status-commentary').removeClass('btn-info').addClass('btn-default');
        $('#status-discussion').removeClass('btn-info').addClass('btn-default');
    },

    ellipsis: function(classname) {
        $(document).ready(function() {
            $('.' + classname).dotdotdot({
                /*  The text to add as ellipsis. */
                ellipsis    : '... ',
         
                /*  How to cut off the text/html: 'word'/'letter'/'children' */
                wrap        : 'letter',
         
                /*  Wrap-option fallback to 'letter' for long words */
                fallbackToLetter: true,
         
                /*  jQuery-selector for the element to keep and put after the ellipsis. */
                //after       : null,
         
                /*  Whether to update the ellipsis: true/'window' */
                //watch       : false,
                watch       : true,
            
                /*  Optionally set a max-height, if null, the height will be measured. */
                height      : null
                //height      : 70
         
                /*  Deviation for the height-option. */
                //tolerance   : 0,
         
                /*  Callback function that is fired after the ellipsis is added,
                    receives two parameters: isTruncated(boolean), orgContent(string). */
                //callback    : function( isTruncated, orgContent ) {},
         
                //lastCharacter   : {
         
                    /*  Remove these characters from the end of the truncated text. */
                //    remove      : [ ' ', ',', ';', '.', '!', '?' ],
         
                    /*  Don't add an ellipsis if this array contains 
                        the last character of the truncated text. */
                //    noEllipsis  : []
                //}
            });
        });
    },

    revealSticky: function (phase, idea, user) {
        console.log('em phase is: ', phase);
        console.log('em in revealSticky', idea);
        console.log('em think1', $('.think1'));
        switch(phase) {
            case 0: this.addThinkIdea(idea, user);
                break;
            case 1: this.addHearIdea(idea, user);
                break;
            case 2: this.addSeeIdea(idea, user);
                break;
            case 3: this.addSayIdea(idea, user);
                break;
            case 4: this.addPainIdea(idea, user);
                break;
            case 5: this.addGainIdea(idea, user);
                break;
        }
        //this.adjustStickykNotes();
        // if (ellipsis_flag) {
        //     ellipsis_flag = false;
        //     this.ellipsis();
        // }
    },

    nextPhase: function () {
        console.log('facilitator next phase');
        if($('#status-map').hasClass('btn-info')) {
            $('#status-map').removeClass('btn-info').addClass('btn-default');
            $('#status-commentary').addClass('btn-info');
        } else if ($('#status-commentary').hasClass('btn-info')) {
            $('#status-commentary').removeClass('btn-info').addClass('btn-default');
            $('#status-discussion').addClass('btn-info');
        }
    },

    addThinkIdea: function (idea, user) {
        console.log('user color:', user.color);
        this.state.think[this.state.cnt_think] = idea;
        $('.think' + this.state.cnt_think).css('background-color', user.color);
        $('.think' + this.state.cnt_think).removeClass('hidden');
        //this.ellipsis('think' + this.state.cnt_think);
        this.setState({ think: this.state.think,
                        cnt_think: this.state.cnt_think+1 });
        var cnt = this.state.cnt_think - 1;
        this.ellipsis('think' + cnt);
    },

    addHearIdea: function (idea, user) {
        this.state.hear[this.state.cnt_hear] = idea;
        $('.hear' + this.state.cnt_hear).css('background-color', user.color);
        $('.hear' + this.state.cnt_hear).removeClass('hidden');
        // this.ellipsis('hear' + this.state.cnt_hear);
        this.setState({ hear: this.state.hear,
                        cnt_hear: this.state.cnt_hear+1 });
        var cnt = this.state.cnt_hear - 1;
        this.ellipsis('hear' + cnt);
    },

    addSeeIdea: function (idea, user) {
        this.state.see[this.state.cnt_see] = idea;
        $('.see' + this.state.cnt_see).css('background-color', user.color);
        $('.see' + this.state.cnt_see).removeClass('hidden');
        // this.ellipsis('see' + this.state.cnt_see);
        this.setState({ see: this.state.see,
                        cnt_see: this.state.cnt_see+1 });
        var cnt = this.state.cnt_see - 1;
        this.ellipsis('see' + cnt);
    },

    addSayIdea: function (idea, user) {
        this.state.say[this.state.cnt_say] = idea;
        $('.say' + this.state.cnt_say).css('background-color', user.color);
        $('.say' + this.state.cnt_say).removeClass('hidden');
        // this.ellipsis('say' + this.state.cnt_say);
        this.setState({ say: this.state.say,
                        cnt_say: this.state.cnt_say+1 });
        var cnt = this.state.cnt_say - 1;
        this.ellipsis('say' + cnt);
    },

    addPainIdea: function (idea, user) {
        this.state.pain[this.state.cnt_pain] = idea;
        $('.pain' + this.state.cnt_pain).css('background-color', user.color);
        $('.pain' + this.state.cnt_pain).removeClass('hidden');
        // this.ellipsis('pain' + this.state.cnt_pain);
        this.setState({ pain: this.state.pain,
                        cnt_pain: this.state.cnt_pain+1 });
        var cnt = this.state.cnt_pain - 1;
        this.ellipsis('pain' + cnt);
    },

    addGainIdea: function (idea, user) {
        this.state.gain[this.state.cnt_gain] = idea;
        $('.gain' + this.state.cnt_gain).css('background-color', user.color);
        $('.gain' + this.state.cnt_gain).removeClass('hidden');
        // this.ellipsis('gain' + this.state.cnt_gain);
        this.setState({ gain: this.state.gain,
                        cnt_gain: this.state.cnt_gain+1 });
        var cnt = this.state.cnt_gain - 1;
        this.ellipsis('gain' + cnt);
    },

    endActivity: function () {
        socket.emit('updateMeetingStatus', this.props.room, "Facilitator is idle");
        socket.emit('empathy:map:end:activity');
    },

    proceed: function () {
        socket.emit('empathy:map:proceed');
    },

    render: function () {
        //this.ellipsis();
        return (
            <div>
                <div id="vf-empathy-map-status">
                    <legend>Activity Progress</legend>
                    <div className="btn-group btn-group-justified">
                        <div className="btn-group vf-btn-group-status">
                            <a id="status-map" className="btn btn-info">Empathy Map</a>
                        </div>
                        <div className="btn-group vf-btn-group-status">
                            <a id="status-commentary" className="btn btn-default">Commentary</a>
                        </div>
                        <div className="btn-group vf-btn-group-status">
                            <a id="status-discussion" className="btn btn-default">Discussion</a>
                        </div>
                    </div>
                </div>
                <div className="map">
                    <img src="/img/map.jpg"></img>
                    <div className="item think0 hidden" title={this.state.think[0]}>{this.state.think[0]}</div>
                    <div className="item think1 hidden" title={this.state.think[1]}>{this.state.think[1]}</div>
                    <div className="item think2 hidden" title={this.state.think[2]}>{this.state.think[2]}</div>
                    <div className="item think3 hidden" title={this.state.think[3]}>{this.state.think[3]}</div>
                    <div className="item think4 hidden" title={this.state.think[4]}>{this.state.think[4]}</div>
                    <div className="item think5 hidden" title={this.state.think[5]}>{this.state.think[5]}</div>
                    <div className="item think6 hidden" title={this.state.think[6]}>{this.state.think[6]}</div>
                    <div className="item think7 hidden" title={this.state.think[7]}>{this.state.think[7]}</div>

                    <div className="item hear0 hidden" title={this.state.hear[0]}>{this.state.hear[0]}</div>
                    <div className="item hear1 hidden" title={this.state.hear[1]}>{this.state.hear[1]}</div>
                    <div className="item hear2 hidden" title={this.state.hear[2]}>{this.state.hear[2]}</div>
                    <div className="item hear3 hidden" title={this.state.hear[3]}>{this.state.hear[3]}</div>
                    <div className="item hear4 hidden" title={this.state.hear[4]}>{this.state.hear[4]}</div>
                    <div className="item hear5 hidden" title={this.state.hear[5]}>{this.state.hear[5]}</div>
                    <div className="item hear6 hidden" title={this.state.hear[6]}>{this.state.hear[6]}</div>
                    <div className="item hear7 hidden" title={this.state.hear[7]}>{this.state.hear[7]}</div>

                    <div className="item see0 hidden" title={this.state.see[0]}>{this.state.see[0]}</div>
                    <div className="item see1 hidden" title={this.state.see[1]}>{this.state.see[1]}</div>
                    <div className="item see2 hidden" title={this.state.see[2]}>{this.state.see[2]}</div>
                    <div className="item see3 hidden" title={this.state.see[3]}>{this.state.see[3]}</div>
                    <div className="item see4 hidden" title={this.state.see[4]}>{this.state.see[4]}</div>
                    <div className="item see5 hidden" title={this.state.see[5]}>{this.state.see[5]}</div>
                    <div className="item see6 hidden" title={this.state.see[6]}>{this.state.see[6]}</div>
                    <div className="item see7 hidden" title={this.state.see[7]}>{this.state.see[7]}</div>

                    <div className="item say0 hidden" title={this.state.say[0]}>{this.state.say[0]}</div>
                    <div className="item say1 hidden" title={this.state.say[1]}>{this.state.say[1]}</div>
                    <div className="item say2 hidden" title={this.state.say[2]}>{this.state.say[2]}</div>
                    <div className="item say3 hidden" title={this.state.say[3]}>{this.state.say[3]}</div>
                    <div className="item say4 hidden" title={this.state.say[4]}>{this.state.say[4]}</div>
                    <div className="item say5 hidden" title={this.state.say[5]}>{this.state.say[5]}</div>
                    <div className="item say6 hidden" title={this.state.say[6]}>{this.state.say[6]}</div>
                    <div className="item say7 hidden" title={this.state.say[7]}>{this.state.say[7]}</div>

                    <div className="item pain0 hidden" title={this.state.pain[0]}>{this.state.pain[0]}</div>
                    <div className="item pain1 hidden" title={this.state.pain[1]}>{this.state.pain[1]}</div>
                    <div className="item pain2 hidden" title={this.state.pain[2]}>{this.state.pain[2]}</div>
                    <div className="item pain3 hidden" title={this.state.pain[3]}>{this.state.pain[3]}</div>
                    <div className="item pain4 hidden" title={this.state.pain[4]}>{this.state.pain[4]}</div>
                    <div className="item pain5 hidden" title={this.state.pain[5]}>{this.state.pain[5]}</div>
                    <div className="item pain6 hidden" title={this.state.pain[6]}>{this.state.pain[6]}</div>
                    <div className="item pain7 hidden" title={this.state.pain[7]}>{this.state.pain[7]}</div>

                    <div className="item gain0 hidden" title={this.state.gain[0]}>{this.state.gain[0]}</div>
                    <div className="item gain1 hidden" title={this.state.gain[1]}>{this.state.gain[1]}</div>
                    <div className="item gain2 hidden" title={this.state.gain[2]}>{this.state.gain[2]}</div>
                    <div className="item gain3 hidden" title={this.state.gain[3]}>{this.state.gain[3]}</div>
                    <div className="item gain4 hidden" title={this.state.gain[4]}>{this.state.gain[4]}</div>
                    <div className="item gain5 hidden" title={this.state.gain[5]}>{this.state.gain[5]}</div>
                    <div className="item gain6 hidden" title={this.state.gain[6]}>{this.state.gain[6]}</div>
                    <div className="item gain7 hidden" title={this.state.gain[7]}>{this.state.gain[7]}</div>
                </div>
                <button id="button-end" className="btn btn-danger vf-button-end-activity-margin" onClick={this.endActivity}>End Activity</button>
                <button id="button-proceed" className="btn btn-success vf-button-proceed-margin" onClick={this.proceed}>Proceed</button>
            </div>
        )
    }
});

module.exports = EmpathyMapDisplay;