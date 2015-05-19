var React = require('react/addons');

var MinutesInputWidget = React.createClass({

	getDefaultProps: function () {
		return {
			value: 5,
			changeCallback: function () { }
		}
	},

	updateTime: function (event) {
		$(event.target).closest('.row').find('input').val(event.target.value);
		this.props.changeCallback();
	},

	render: function () {
		
		return (
			<div className="minutes-input-widget">
		        {/*<label className="control-label" data-toggle="popover" data-placement="top"
		               data-content={this.props.title}>
			    	{this.props.title}
		        </label>*/}
		        <h4>{this.props.title}</h4>

		        <div className="row">
		        	<div className="col-md-10 col-xs-9">
		            	<input type="range" min="1" max={this.props.maxLimit} defaultValue={this.props.defaultval} onChange={this.updateTime} />
		            </div>
		            <div className="col-md-2 col-xs-3">
		            	<input type="number" id={this.props.id} className="form-control" min="1" max={this.props.maxLimit} defaultValue={this.props.defaultval} onChange={this.updateTime} />
		            </div>
		        </div>
	       	</div>
	    )
	}
});

module.exports = MinutesInputWidget;