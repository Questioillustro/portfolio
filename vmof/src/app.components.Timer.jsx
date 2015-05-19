var React = require('react/addons');
require('./app.components.Timer.styl');

var HelpTextMixin = require('./app.components.mixin.HelpText.js');

var Timer = React.createClass({

    mixins: [HelpTextMixin],

    getInitialState: function () {
        //console.log("timer props", this.props);

        var myNow = new Date(),
            serverNow = new Date(this.props.start),
            timeDiff = (serverNow - myNow);

        //console.log(myNow, serverNow, timeDiff);

        return { remainingMilli: 0, 
            minutes: 0, 
            seconds: 0, 
            waiting: false, 
            percentage: 0, 
            timeDiff: timeDiff,
            waiting: false
        };
    },

    componentDidMount: function(){
        var ticker = setInterval(this.tick, 100)
        this.setState({ticker: ticker});
        //console.log("Did mount ticker", ticker);
    },

    componentWillReceiveProps: function () {
        //console.log("Clearing", this.state.ticker);
        clearInterval(this.state.ticker);

        var ticker = setInterval(this.tick, 100);
        this.setState({waiting: false, ticker: ticker}); // New props arrived so go back to active
        //console.log("Received props ticker", ticker);
    },

    componentWillUnmount: function() {
        clearInterval(this.state.ticker);
    },

    // Called periodically to update the state of the clock
    tick: function(){
        var self = this;

        var myNow = new Date(),
            serverNow = new Date(this.props.start),
            remainingMilli = (this.props.totalSeconds * 1000) - (myNow - serverNow) + this.state.timeDiff,
            percentage = 100 - (remainingMilli / (this.props.totalSeconds * 1000)) * 100,
            x = remainingMilli / 1000,
            seconds = Math.round(x % 60),
            x = x / 60,
            minutes = Math.floor(x);

        if (seconds < 10) {
            seconds = "0"+seconds;

            if (minutes == 0 && this.state.seconds != seconds && this.props.playAudio)
                $('#tickAudio')[0].play();
        }

        if (remainingMilli <= 0) {
            //console.log("Time expired ticker", ticker)
            clearInterval(self.state.ticker);

            // Facilitator calls the callback function
            if (!!this.props.callback && !this.state.waiting) {
                this.props.callback();
                this.setState({waiting: true});
            }
        } 

        this.setState({remainingMilli: remainingMilli, minutes: minutes, seconds: seconds, percentage: percentage});
    },

    render: function() {

        var self = this;

        var renderMinutes = function () {
            if (self.state.minutes == 0) 
                return;

            return <span>{self.state.minutes}:</span>
        }

        var renderSeconds = function () {
            if (self.state.seconds == 0) 
                return;

            return <span>{self.state.seconds}</span>
        }

        return (
            <div className="progress input-group">
                <span className="input-group-addon glyphicon glyphicon-time" />

                <div className="progress-bar progress-bar-danger" role="progressbar" 
                     aria-valuenow={this.state.percentage} aria-valuemin="0" 
                     aria-valuemax="100" style={{'width': this.state.percentage+'%'}}>
                </div>
                
                <div className="progress-bar progress-bar-success" role="progressbar" 
                     aria-valuenow={100-this.state.percentage} aria-valuemin="0" 
                     aria-valuemax="100" style={{'width': (100-this.state.percentage)+'%'}}>
                </div>

                <audio id="tickAudio"><source src="http://soundbible.com/grab.php?id=2044&type=mp3" type="audio/wav"></source></audio>
            </div>
        )
    }
});

module.exports = Timer;