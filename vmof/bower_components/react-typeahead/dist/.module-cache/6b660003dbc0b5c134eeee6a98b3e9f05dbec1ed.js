/**
 * @jsx React.DOM
 */

var React = window.React || require('react');

/**
 * Encapsulates the rendering of an option that has been "selected" in a
 * TypeaheadTokenizer
 */
var Token = React.createClass({displayName: 'Token',
  propTypes: {
    children: React.PropTypes.string,
    onRemove: React.PropTypes.func
  },

  render: function() {
    return this.transferPropsTo(
      React.DOM.div({className: "typeahead-token"}, 
        this.props.children, 
        this._makeCloseButton()
      )
    );
  },

  _makeCloseButton: function() {
    if (!this.props.onRemove) {
      return "";
    }
    return (
      React.DOM.a({className: "typeahead-token-close", href: "#", onClick: function() {
          this.props.onRemove(this.props.children);
          return false;
        }.bind(this)}, "×")
    );
  }
});

module.exports = Token;
