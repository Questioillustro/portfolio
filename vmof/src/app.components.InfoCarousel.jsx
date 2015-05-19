var React = require('react/addons');

require('./app.components.InfoCarousel.styl');
require('../node_modules/bootstrap-styl/js/carousel.js');
require('../node_modules/bootstrap-styl/js/transition.js');
require('./app.components.InfoCarousel.js');
require('../node_modules/bootstrap-styl/js/modal.js');

var io = require('alias_socket_io');
var socket = io.connect();

var Carousel = React.createClass({

	componentWillMount: function(){
		
    },

    componentWillUnmount: function () {
    	
    },

    updateStatus: function(data){
    	
    },


	render: function() {

		return(
			<div>
				<div id="Tutorial_Modal" className="modal fade">
                  <div className="modal-dialog">
                    <div className="modal-content">
                      <div className="modal-header">
                        <button type="button" className="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 className="modal-title">Tutorial</h4>
                      </div>
                      <div className="modal-body">
                        <video id="tutorial" width="420" controls>
						    <source src="/videos/tutorial.mp4" type="video/mp4"></source>
						    Your browser does not support HTML5 video.
						</video>
                      </div>
                      <div className="modal-footer">
                      	<button id="warning-modal-ok" type="button" className="btn btn-danger" data-dismiss="modal">Close</button>
                      </div>
                    </div>
                  </div>
                </div>
				<div>
				    <div id="myCarousel" className="carousel slide" data-ride="carousel">
				      <ol className="carousel-indicators">
				        <li data-target="#myCarousel" data-slide-to="0" className="active"></li>
				        <li data-target="#myCarousel" data-slide-to="1"></li>
				        <li data-target="#myCarousel" data-slide-to="2"></li>
				        <li data-target="#myCarousel" data-slide-to="3"></li>
				        <li data-target="#myCarousel" data-slide-to="4"></li>
				        <li data-target="#myCarousel" data-slide-to="5"></li>
				      </ol>
				      <div className="carousel-inner" role="listbox">
				        <div className="item active icebreakImg">
				          <div className="container">
				            <div className="carousel-caption">
				              <h1>Two Truths and a Lie</h1>
				              <p>Two truths and a lie is all about getting to know each other by trying to guess what everyone is lying about.</p>
				              <p>This activity is best played with three or more people.</p>
				              <div className="tutorialBtn">
				              	<button className="btn btn-primary" data-toggle="modal" data-target="#Tutorial_Modal">View Tutorial</button>
				              </div>
				            </div>
				          </div>
				        </div>
				        <div className="item">
				          <div className="container">
				            <div className="carousel-caption">
				              <h1>Anonymous Brainstorming</h1>
				              <p>Great way to brainstorm while not having to worry about the general in the room shooting them down.</p>
				              <p>This actvity is best when there are more than two people.</p>
				              <div className="tutorialBtn">
				              	<button className="btn btn-primary" data-toggle="modal" data-target="#Tutorial_Modal">View Tutorial</button>
				              </div>					          
				            </div>
				          </div>
				        </div>
				        <div className="item">
				          <div className="container">
				            <div className="carousel-caption">
				              <h1>Empathy Map</h1>
				              <p>Great way to get into the shoes of the customer, everyone will put up virtual post-it notes about the project relating to the customer's five senses.</p>
				              <p>This actvity is best when there are more than two people.</p>
				          	  <div className="tutorialBtn">
				              	<button className="btn btn-primary" data-toggle="modal" data-target="#Tutorial_Modal">View Tutorial</button>
				              </div>
				            </div>
				          </div>
				        </div>
				        <div className="item">
				          <div className="container">
				            <div className="carousel-caption">
				              <h1>Polling</h1>
				              <p>Struggling to figure out what the people in the room want to do? This application allows you to have everyone vote on a variety of ideas and then shows you what the choice was.</p>
				              <p>This actvity is best when there are two or more people.</p>
				              <div className="tutorialBtn">
				              	<button className="btn btn-primary" data-toggle="modal" data-target="#Tutorial_Modal">View Tutorial</button>
				              </div>				       
				            </div>
				          </div>
				        </div>
				       	<div className="item">
				          <div className="container">
				            <div className="carousel-caption">
				              <h1>Tiebreaker</h1>
				              <p>Can't decide on what to do next?  Is there a stalemate on picking what course of action to take?</p>
				              <p>Run a tiebreaker to take the stress out of deciding and relax as vMOF picks for you.</p>
				              <div className="tutorialBtn">
				              	<button className="btn btn-primary" data-toggle="modal" data-target="#Tutorial_Modal">View Tutorial</button>
				              </div>				            
				            </div>
				          </div>
				        </div>
				   		<div className="item">
				   			<div className="item rit">
				          		<div className="container">
				            <div className="carousel-caption">
				              <h1>About</h1>
				              <p>VMOF was created by the senior project Team Arbiter from the Software Engineering department at R.I.T in collaboration with MITRE.</p>
				              <p>Dev team: Andrew Landman, Christopher Farrell ,Evan O'Malley, Steven Brewster</p>

				            </div>
				          </div>
				        </div>
				        </div>
				      </div>
				      <a className="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
				        <span className="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				        <span className="sr-only">Previous</span>
				      </a>
				      <a className="right carousel-control" href="#myCarousel" role="button" data-slide="next">
				        <span className="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				        <span className="sr-only">Next</span>
				      </a>
				    </div>
				</div>
			</div>
		);
	}
});

module.exports = Carousel;
