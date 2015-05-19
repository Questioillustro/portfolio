require('./app.components.TwoTruthsOptions.styl');
var React = require('react/addons');

var io = require('alias_socket_io');
var socket = io.connect();

var scores = {};
var lies ={};

var AttTwoTruthsResults = React.createClass({

    getInitialState: function() {
        return { users: []};
    },

    componentDidMount: function (){
        //socket.on('recieveRoomList', this.getUsersInRoom);
        //socket.emit('getRoomList', this.props.room);
        console.log(this.props.scores + "  are scores read in");
        scores = this.props.scores;
        lies = this.props.lies;
    },

    componentWillUnmount: function () {
        //socket.removeAllListeners('recieveRoomList');
    },

    getUsersInRoom: function(users) {
        console.log(this);
        this.setState({ users: users });

    },


    render: function () {
        //this sorting code is the worst
        scores = this.props.scores;
        lies = this.props.lies;
        console.log(scores);
        var sorted = Object.keys(scores);
        var sortedPeople = new Array();
        var people = this.props.people;
        sorted.sort(function(a,b) { return parseFloat(scores[b]) - parseFloat(scores[a]) } );
        console.log(sorted);

        console.log(people);



        for(var j = 0; j < sorted.length; j++)
        {
            for(var i = 0; i < people.length; i++)
            {
                if(String(people[i].id) == String(sorted[j]))
                {
                    console.log(people[i].name);
                    sortedPeople.push(people[i]);
                }
            }
        }

        var renderUser = function(user) {
            if(user.name != "Facilitator"){
                console.log(scores[user.id] + " trying to get score");
            return (                
                <tr className="graphText" key={user.id}>
                    <td className="center-me">{user.name}</td>
                    <td className="center-me">
                        <div id={user.id}>
                            {scores[user.id]}
                        </div>
                    </td>
                    <td className="center-me">
                        <div id={user.id}>
                            {lies[user.id]}
                        </div>
                    </td>
                </tr>
                )
            }
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
                            <a className="btn btn-default no-click">Guessing</a>
                        </div>
                        <div className="btn-group vf-btn-group-status">
                            <a className="btn btn-info no-click">Results</a>
                        </div>
                    </div>
                </div>
                <legend>
                    Two Truths and a Lie
                </legend>
                <div className="col-md-6 col-md-offset-3 centerd">
                    <div className="bestLier">
                        <h2>{this.props.best} fooled the most people</h2>
                    </div>
                    <div className="mostGullible">
                        <h2>The most gullible is {sortedPeople[sortedPeople.length-1].name}</h2>
                    </div>
                    <div className="panel panel-default">
                        <div className="panel-heading center-me">
                           <h4> The results are in! </h4>
                        </div>
                        <table className="table table-striped"><tbody>
                            <tr>
                                <th className="center-me graphText">Name</th>
                                <th className="center-me graphText">Number Correct</th>
                                <th className="center-me graphText">Their lie</th>
                            </tr>
                            {sortedPeople.map(renderUser)}
                        </tbody></table>
                    </div>
                </div>
            </div>
        )
    }
});

module.exports = AttTwoTruthsResults
;