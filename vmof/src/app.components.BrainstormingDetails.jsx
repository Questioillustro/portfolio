var React = require('react/addons');
var Timer = require('./app.components.Timer.jsx');
var io = require('alias_socket_io');
var socket = io.connect();

var BrainstormingDetails = React.createClass({

	componentDidMount: function () {
		this.equalizePanelHeights();
		this.stylePanels();
	},

	equalizePanelHeights: function () {
		var maxheight = 0,
			$panels = this.getAllPanels();

		$panels.each(function () {
			var h = $(this).height();
			maxheight = (h > maxheight) ? h : maxheight;
		});

		$panels.height(maxheight);
	},

	stylePanels: function () {
		var $panels = this.getAllPanels(),
			detailCount = this.getDetailCount(),
			widthStr = 'col-lg-';

		widthStr += 12 / detailCount
		$panels.addClass(widthStr).css({'padding':'0','border':'solid 1px black'});
	},

	getAllPanels: function () {
		return $('.vf-detail-content');
	},

	getDetailCount: function () {
		var count = 0;

		if (!this.noImage())
			count++;
		if (!this.noQuestion())
			count++;
		if (!this.noRules())
			count++;

		return count;
	},

	noDetailsToShow: function () {
		return ( this.noImage() && this.noQuestion() && this.noRules() );
	},

	noImage: function () {
		return this.props.params.image === '#';
	},

	noQuestion: function () {
		return this.props.params.question === '';
	},

	noRules: function () {
		return this.props.params.rules === '';
	},

	render: function () {

		var self = this;

		var renderLabel = function () {
			if (self.noDetailsToShow())
				return;

			return <legend>Activity Details</legend>
		}

		//console.log("details params", this.props);
		var renderImage = function () {
			if (self.noImage()) return;

			return (
				<div className="panel panel-primary vf-detail-content">
					<div className="panel-heading">
                		<h4>Image</h4>
                	</div>
                	<div className="panel-body" style={{'text-align':'center'}}>
                		<img src={self.props.params.image} style={{'height':'180px'}} />    	
                    </div>
				</div>				
			)
		}

		var renderQuestion = function () {
			if (self.noQuestion()) return;

			return (
				<div className="panel panel-success vf-detail-content">
					<div className="panel-heading">
                		<h4>Brainstorming Topic</h4>
                	</div>
                	<div className="panel-body">
                    	<h4>{self.props.params.question}</h4>
                    </div>
                </div>
          	)
		}

		var renderRules = function () {
			if (self.noRules()) return;

			return (
	            <div className="panel panel-info vf-detail-content">
	            	<div className="panel-heading">
	            		<h4>Ground Rules</h4>
	            	</div>
	            	<div className="panel-body">
	            		<h4>{self.props.params.rules}</h4>
	            	</div>
	            </div>
			)
		}

		return (
			<div id="details-panel">
            	{renderLabel()}

				<div id="brainstorming-parameters" className="">
					{renderImage()}
					
					{renderQuestion()}
		            
		            {renderRules()}
	            </div>
	        </div>
		)
	}

});

module.exports = BrainstormingDetails;