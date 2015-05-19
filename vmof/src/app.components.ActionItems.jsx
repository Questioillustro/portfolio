var React = require('react/addons');
var ReactAutocomplete = require('alias_react_autocomplete');

var ActionItems = React.createClass({
    submitActionItem: function () {
        var text = $('#action-item-text').val(),
            assignedTo = $('#assigned-to').val();

        var user = this.getUserObjectByName(assignedTo);

        // Don't send action items with blank fields
        if (text.trim === '' || user === undefined)
            return;

        var data = {
            'actionItem': {
                'text': text,
                'assignedTo': assignedTo,
                'userObj' : user
            },
            'idea' : this.props.idea
        }

        $('#action-item-text').val('');
        this.props.submit(data);
    },

    getUserObjectByName: function (username) {
        var users = this.props.users;
        
        for (var i = 0; i < users.length; i++) {
            if (users[i].name === username) 
                return users[i];
        }

        return undefined;
    },

    render: function () {
        var self = this;
        var users = [];

        for (var i = 0; i < self.props.users.length; i++)
            users.push({id: self.props.users[i].name, title: self.props.users[i].name})

        var renderActionItem = function (actionItem, index) {
            return (
                <div className="well col-xs-12">
                    <div>
                        <h3>{actionItem.text}</h3>
                        Assigned To: {actionItem.assignedTo}
                    </div>
                </div>
            )
        }

        var renderUserOption = function (user, index) {
            return <option value={user.id}>{user.title}</option>
        }

        return (
            <div id="action-items" className="">
                <div>
                    <legend>Assign an Action Item</legend>

                    {self.props.actionItems.map(renderActionItem)}
                </div>

                <div className="vf-input-actionitem">
                    <div className="form-group">
                        <textarea id="action-item-text" className="form-control" placeholder="Action item description..." maxLength="200"></textarea>
                    </div>

                    <div className="assign-to form-group">
                        <select className="form-control" id="assigned-to" placeholder="Assign to...">
                            {users.map(renderUserOption)}
                        </select>
                    </div>

                    <div id="submit-action-item" className="form-group">
                        <button className="btn btn-primary" onClick={self.submitActionItem} >Submit</button>
                    </div>
                </div>
            </div>
        )
    }
});

module.exports = ActionItems;