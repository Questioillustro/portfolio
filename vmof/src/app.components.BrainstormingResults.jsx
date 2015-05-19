require('./app.components.BrainstormingResults.styl');
var React = require('react/addons');

var HelpTextMixin = require('./app.components.mixin.HelpText.js');
var AutoScroll = require('./app.components.mixin.AutoScroll.js')

var ActionItems = require('./app.components.ActionItems.jsx');

var io = require('alias_socket_io');
var socket = io.connect();

var BrainstormingResults = React.createClass({

    mixins: [HelpTextMixin, AutoScroll],

    getInitialState: function () {
        return {
            displayIndex: parseInt(this.props.params.displayIndex),
            votes: []
        }
    },

    componentWillReceiveProps: function (nextProps) {
        //console.log("Next props", nextProps);
        if (nextProps.params.renderDiscussion)
            this.setState({displayIndex: nextProps.params.displayIndex});
    },

    // Perform any UI cleanup that is impractical to change during render
    componentDidUpdate: function () {
        if (!this.props.params.renderVoting) {
            $('.vf-vote-up,.vf-vote-down').hide();
        }

        this.initializeAutoScroll();
    },

    componentDidMount: function () {
        this.initializeAutoScroll();
    },

    showComments: function (event) {
        var idea = this.getIdea();
        if (this.props.params.ideas[idea]['comments'].length == 0)
            return

        $('.vf-post-comments').slideToggle('fast');
    },

    sortIdeas: function () {
        var ideas = Object.keys(this.props.params.ideas),
            self = this;
      
        var sorted = ideas.sort (function (val1, val2) {
            val1 = parseInt(self.props.params.ideas[val1]['votes']);
            val2 = parseInt(self.props.params.ideas[val2]['votes']);
            return val2-val1;
        });

        return sorted;
    },

    castVote: function (event) {
        var $tar = $(event.target),
            index = this.state.displayIndex,
            votes = this.state.votes,
            currVote = votes[index] || 0,
            idea = this.props.params.commentIdeas[index];

        // the span in the button picks up the click event as well
        if ($tar.hasClass('badge'))
            $tar = $tar.closest('button');

        var val = parseInt($tar.data('value')),
            adjVal = val;

        votes[index] = val;

        // If vote is flopped then compensate for the prior vote
        if (val === 1 && currVote === -1) adjVal = 2;
        if (val === -1 && currVote === 1) adjVal = -2;

        // Remove vote
        if (val === 0 && currVote === -1) adjVal = 1;
        if (val === 0 && currVote === 1) adjVal = -1;

        // Ignore repeated clicks
        if (val === 1 && currVote === 1) return;
        if (val === -1 && currVote === -1) return;

        this.setState({votes: votes});

        // Update interface
        if (val > 0) {
            $tar.addClass('active')
                .attr('disabled', true);

            // Restore the down vote button
            $tar.closest('.vf-voting')
                .find('.vf-vote-down')
                .removeClass('active')
                .attr('disabled', false);

        } else if (val < 0) {
            $tar.addClass('active')
                .attr('disabled', true);

            // Restore the up vote button
            $tar.closest('.vf-voting')
                .find('.vf-vote-up')
                .removeClass('active')
                .attr('disabled', false);
        } else if (val === 0) {
            $tar.closest('.vf-voting')
                .find('.active')
                .removeClass('active')
                .attr('disabled', false);
        }

        // Send the vote
        var data = {
            'idea'   : idea,
            'vote'   : adjVal
        };

        this.props.submitVote(data);
    },

    nextComment: function (newIndex) {
        var newIndex = (this.state.displayIndex + 1) % this.props.params.commentIdeas.length;
        this.setState({displayIndex: newIndex});
        this.resetCommentaryUI(newIndex);
    },

    prevComment: function () {
        var newIndex = (this.state.displayIndex - 1);
        if (newIndex < 0) 
            newIndex = this.props.params.commentIdeas.length-1;

        this.setState({displayIndex: newIndex});

        this.resetCommentaryUI(newIndex);
    },

    resetCommentaryUI: function (index) {
        $('.vf-vote-up.active,.vf-vote-down.active').removeClass('active');
        $('#comment-field').val('');

        if (this.state.votes[index] === 1)
            $('.vf-vote-up').addClass('active');
        else if (this.state.votes[index] === -1) 
            $('.vf-vote-down').addClass('active');
    },

    getIdea: function () {
        if (this.props.params.renderDiscussion)
            return this.sortIdeas()[this.state.displayIndex];
        else if (this.props.params.renderCommentary)
            return this.props.params.commentIdeas[this.state.displayIndex];
    },

    submitComment: function (data) {
        this.props.submitComment(data); // Send idea to server
    },

	render: function () {
		var self = this;

        var renderComment = function (comment, index) {
            return (
                <div className="vf-post-comment">
                    <h4 className="media-heading">
                        {comment}
                    </h4>
                </div>
            )
        }

        var renderCarouselControls = function () {
            if (!self.props.params.renderCommentary) return;

            return (
                <ul className="pager">
                    <li>
                        <a className="vf-page-comment vf-page-previous" onClick={self.prevComment}
                           data-toggle="popover" data-title="Previous Comment" data-content="Click to move to the previous comment in the list"
                           data-placement="top">
                            &#60; Previous
                        </a>
                         |
                    </li>
                    <li>
                        <a className="vf-page-comment vf-page-next" onClick={self.nextComment}
                           data-toggle="popover" data-title="Next Comment" data-content="Click to move to the next comment in the list"
                           data-placement="top">
                            Next &#62;
                        </a>
                    </li>
                </ul>
            )
        }

        var renderVotes = function (vote) {
        	vote = parseInt(vote);
        	if (vote >= 0) {
        		return (
                    <button data-value="0" className="btn btn-success" onClick={self.castVote}>
                        <span className="badge"
                              data-toggle="popover" data-title="View/Cancel Vote" data-content="View current voting results and click to cancel your vote on this idea">
                            {vote}
                        </span>
                    </button>
                )
        	} else {
        		return (
                    <button data-value="0" className="btn btn-danger" onClick={self.castVote}>
                        <span className="badge"
                              data-toggle="popover" data-title="View/Cancel Vote" data-content="View current voting results and click to cancel your vote on this idea">
                            {vote}
                        </span>
                    </button>
                )
        	}
        }

        var renderActionItems = function () {
            var idea = self.getIdea();

            if (self.props.params.renderDiscussion) {
                var actionItems = self.props.params.ideas[idea].actionItems;
                return <ActionItems actionItems={actionItems} submit={self.props.submitActionItem} idea={idea} users={self.props.params.users} />
            }
        }

        var renderCommentary = function (idea) {
            if (self.props.params.renderDiscussion)
                return; // No commentary in discussion phase
            else
                return <Commentary idea={idea} submitComment={self.submitComment} />;
        }

        // Creates idea container including voting and comments
        var renderIdeaContent = function () {
            var idea = self.getIdea();
            return (
                <div className="well">
                    <ul className="media-list">
                        <li className="media">
                            <div className="media-left">
                                <div className="vf-voting media-object">
                                    <span data-value="1" className="vf-vote-up octicon octicon-triangle-up col-xs-12" 
                                          data-toggle="popover" data-title="Up Vote" data-content="Up vote this idea and show your approval!"
                                          onClick={self.castVote}>
                                    </span>
                                    
                                    <div className="vf-vote-count">
                                        {renderVotes(self.props.params.ideas[idea]['votes'])}
                                    </div>
                                    
                                    <span data-value="-1" className="vf-vote-down octicon octicon-triangle-down col-xs-12" 
                                          data-toggle="popover" data-title="Down Vote" data-content="Down vote this idea to say you don't agree"
                                          onClick={self.castVote}></span>
                                </div>
                            </div>

                            <div className="media-body">
                                <div className="vf-post-contents">
                                    <h3 className="vf-post-idea media-heading"
                                             data-toggle="popover" data-title="Brainstorming Idea" data-content="This is an idea generated by one of your fellow participants."
                                             data-placement="top">
                                         {idea}
                                    </h3>

                                    <a className="vf-view-comments" onClick={self.showComments} style={{'cursor':'pointer'}}
                                        data-toggle="popover" data-title="Show Comments" data-content="Click to view any comments posted to this idea">
                                        <legend style={{'margin-bottom':'0'}}>
                                            {self.props.params.ideas[idea]['comments'].length} comments
                                        </legend>
                                    </a>
                                </div>

                                <div className="vf-post-comments">
                                    {self.props.params.ideas[idea]['comments'].map(renderComment)}
                                </div>
                                
                                {renderCommentary(idea)}
                            </div>
                        </li>
                    </ul>
                </div>
            )
        }

        // Generates the top level div
		var renderIdea = function () {
            var idea = self.getIdea();

            //console.log("Idea", idea);

            if (idea == undefined)
                return;

            return (
                <div className="active-idea">
                    {renderIdeaContent()}
                </div>
            )
        }

        return ( 
                <div id="idea-carousel" className="col-xs-12">
                    {renderIdea()}
                    {renderCarouselControls()}
                    {renderActionItems()}
                </div>
            )
	}
});

module.exports = BrainstormingResults;

var Commentary = React.createClass({

    submitComment: function (e) {
        var $field = $('#comment-field'),
            comment = $field.val();
        comment = comment.trim();

        // Ignore empty comments
        if (comment === '')
            return

        var data = {
            'idea'   : this.props.idea,
            'comment': comment,
        };

        // Reset UI
        $field.val('');
        this.props.submitComment(data);
    },

    detectSubmit: function (event) {
        var key = event.which;
        if(key == 13)  // the enter key code
            this.submitComment();
    },

    render: function () {
        var self = this;

        return (
            <div className="vf-commenting">
                    <input type="text" id="comment-field" className="form-control" maxLength="200"
                           placeholder="Please enter a comment... Press the 'Enter' key to submit" onKeyPress={self.detectSubmit} 
                           data-toggle="popover" data-title="Comment on Idea" data-content="Enter a comment on the idea here and press 'Enter' to submit."
                           data-placement="top"/>
            </div>
        )
    }
});