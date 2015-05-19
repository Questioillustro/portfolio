var React = require('react/addons');
require('../node_modules/bootstrap-styl/js/tooltip.js');
require('../node_modules/bootstrap-styl/js/popover.js');

var HelpTextMixin = require('./app.components.mixin.HelpText.js');

var PhaseBreadCrumbs = React.createClass({

    mixins: [HelpTextMixin],

    componentDidMount: function () {
        this.update();
    },
    
    componentDidUpdate: function () {   
        this.update();
    },

    update: function () {
        var index = this.props.params.phase;
        $('.vmof-breadcrumb .btn-info').removeClass('btn-info').addClass('btn-default');
        $('[data-index="'+index+'"]').find('a').addClass('btn-info').removeClass('btn-default');
    },

    render: function () {

        var self = this;

        var renderBreadCrumbs = function (val, index) {
            return (
                <div className="btn-group vmof-breadcrumb" role="group" data-index={index}>
                    <a className="btn btn-default" data-toggle="popover" data-placement="bottom" data-trigger="focus" role="button"
                            title={self.props.params.breadCrumbs[index].title} 
                            data-content={self.props.params.breadCrumbs[index].help} 
                            style={{'border':'solid 1px black'}}>
                        {self.props.params.breadCrumbs[index].title}
                    </a>
                </div>
            )
        }

        return (
            <div id="vf-brainstorming-breadcrumbs" style={{'margin-bottom':'20px'}}>
                <legend>Activity Progress</legend>
                <div className="btn-group btn-group-justified" role="group">
                    {Object.keys(self.props.params.breadCrumbs).map(renderBreadCrumbs)}
                </div>
            </div>
        )
    }
});

module.exports = PhaseBreadCrumbs;