require('./app.components.TopNav.styl');
require('../node_modules/bootstrap-styl/js/modal.js');

var React = require('react/addons');
var io = require('alias_socket_io');
var socket = io.connect();

var TopNav = React.createClass({

	componentDidMount: function () {
		socket.on( 'get:room:title', this.setRoomDetails );
		socket.emit( 'request:room:title', this.props.room );
	},

	componentWillUnmount: function () {
		socket.removeAllListeners( 'get:room:title' );
	},

	getInitialState: function () {
		return { title: '' }
	},

	setRoomDetails: function ( title ) {
		this.setState({ title: title });
	},

	setRoomTitle: function () {
		var newTitle = $('#new-title-input').val();

		var data = { 
			'room' : this.props.room,
			'title' : newTitle
		}

		$('#room-title').val('');

		socket.emit("set:room:title", data);
	},

	showModal: function () {
		$('#title-change-modal').modal('show');
	},

	logout: function () {
		location.reload();
	},
	
	render: function ( ) {
		var self = this;

		var renderTitle = function ( ) {
			var title = self.state.title;

			if (title && title.trim() !== '')
				title = ' - ' + title;
			else 
				title = ' - No Title';

			var renderTitleEdit = function () {
				if (self.props.roomTitleEdit)
					return <span className="glyphicon glyphicon-pencil" onClick={self.showModal} style={{'color':'#5cb85c', 'margin-top':'15px', 'cursor':'pointer'}}></span>
			}

			return (
				<div id="nav-content">
					<a className="vf-navbar-brand navbar-brand" href="#">vMOF {self.props.room}{title}</a>
					{renderTitleEdit()}
					<span className="glyphicon glyphicon-log-out vf-top-nav-log-out" onClick={self.logout}> Logout</span>
				</div>
			)	
		}

		return (
			<div>
				{renderTitle()}

				<div id="title-change-modal" className="modal fade">
                	<div className="modal-dialog">
                    	<div className="modal-content">
                      		<div className="modal-header">
                        		<button type="button" className="close" data-dismiss="modal" aria-hidden="true">×</button>
                        		<h4 className="modal-title">Change Room Title</h4>
                      		</div>
                      		
                      		<div className="modal-body">
	                        	<input id="new-title-input" type="text" className="form-control" placeholder="Enter title..." maxLength="16" />
                      		</div>
                      
                      		<div className="modal-footer">
                      			<button id="title-modal-ok" type="button" className="btn btn-success navbar-btn" data-dismiss="modal" onClick={self.setRoomTitle} style={{'margin-top':'1px'}}>Ok</button>
                        		<button id="title-modal-cancel" type="button" className="btn btn-danger navbar-btn" data-dismiss="modal">Cancel</button>
                      		</div>
                    	</div>
                  	</div>
                </div>
			</div>
		)
	}
});

module.exports = TopNav;
