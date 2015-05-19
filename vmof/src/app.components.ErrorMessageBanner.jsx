var React = require('react/addons');

var WarningMessage = React.createClass({

    render: function () {
      var divStyle = {
        display: 'block'
      };
        return(
            <div className="alert alert-dismissible alert-danger" style={divStyle}>
              <button type="button" className="close" data-dismiss="alert">×</button>
              <strong>Error:</strong> {this.props.message}
            </div>
        )
      }
});

module.exports = WarningMessage;