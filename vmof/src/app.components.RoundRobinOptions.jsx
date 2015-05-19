var React = require('react/addons');
var io = require('alias_socket_io');
var socket = io.connect();

var RoundRobinOptions = React.createClass({

    componentDidMount: function () {
        socket.on('start:round:robin:brainstorming', this.started);
    },

    started: function (state) {
        console.log('rr state:', state);
    },

    startActivity: function () {
        var parameters = {
            topic: "",
            comment_time: 10
        };
        console.log('here');
        $('#topic').each(function () {
            parameters[this.id] = this.value;
        });
        console.log('rr params:', parameters);
        socket.emit('start:round:robin:brainstorming', this.props.room, parameters);
    },

    render: function () {
        return (
            <div>
                <div className="well bs-component">
                    <legend>Round Robin Brainstorming</legend>
                    <div className="form-group">
                        <label className="control-label">Description</label>
                        <div className="vf-form-field-padding">
                            Each participant will come up with an answer to a question. Participants will then respond to each other person's answer, one at a time.
                        </div>
                    </div>
                    <div className="form-group">
                        <label className="control-label">Topic</label>
                        <div>
                            <textarea className="form-control" rows="3" id="topic"></textarea>
                            <span className="help-block">Enter the topic that you would like the group to brainstorm.</span>
                        </div>
                    </div>
                    <div className="form-group">
                        <label className="control-label">Comment Time</label>
                        <div className="vf-form-field-padding">
                            <input type="range" />
                        </div>
                    </div>
                    <div className="form-group">
                        <div>
                            <button type="submit" className="btn btn-primary" onClick={this.startActivity}>Start</button>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
});

module.exports = RoundRobinOptions;


            // <div className="col-lg-6">
            //     <div className="well bs-component">
            //         <form className="form-horizontal">
            //             <fieldset>
            //             <legend>Round Robin Brainstroming</legend>
            //                 <div className="form-group">
            //                     <label className="col-lg-2 control-label">Description</label>
            //                     <div className="col-lg-10 vf-form-field-padding">
            //                         Each participant will come up with an answer to a question. Participants will then respond to each other person's answer, one at a time.
            //                     </div>
            //                 </div>
            //                 <div className="form-group">
            //                     <label for="textArea" className="col-lg-2 control-label">Topic</label>
            //                     <div className="col-lg-10">
            //                         <textarea className="form-control" rows="3" id="textArea"></textarea>
            //                         <span className="help-block">Enter the topic that you would like the group to brainstorm.</span>
            //                     </div>
            //                 </div>
            //                 <div className="form-group">
            //                     <label className="col-lg-2 control-label">Comment Time</label>
            //                     <div className="col-lg-10 vf-form-field-padding">
            //                         <input type="range" />
            //                     </div>
            //                 </div>
            //                 <div className="form-group">
            //                     <div className="col-lg-10 col-lg-offset-2">
            //                         <button type="submit" className="btn btn-primary">Start</button>
            //                     </div>
            //                 </div>
            //             </fieldset>
            //         </form>
            //     </div>
            // </div>
