var React = require('react/addons');
var Posts = require('./app.models.Posts');
var PostEmpathyMap = require('./app.models.PostEmpathyMap.jsx');
//console.log('PostEmpathyMap', PostEmpathyMap);
var PostEmpathyMapComponent = PostEmpathyMap.component;
var PostEmpathyMapComponent2 = PostEmpathyMap.component2;
//console.log('PostEmpathyMapComponent', PostEmpathyMapComponent);
//var uuid = require('node-uuid');

PostsEmpathyMap.prototype = new Posts();
PostsEmpathyMap.prototype.constructor = PostsEmpathyMap;

function PostsEmpathyMap() {
	//this.posts = new Posts();
	Posts.call(this);
};

// PostsEmpathyMap.prototype.get = function () {
// 	return this.posts.get();
// };

// PostsEmpathyMap.prototype.set = function (posts) {
// 	this.posts.set(posts);
// };

PostsEmpathyMap.prototype.add = function (postEmpathyMap) {
	this.posts.push(postEmpathyMap);
};

// PostsEmpathyMap.prototype.add = function (postEmpathyMap) {
// 	//var posts = this.posts.get();
	
// 	var lastIndex = this.posts.map(function (post) {
// 		return post.section;
// 	}).lastIndexOf(postEmpathyMap.section);

// 	if(lastIndex == -1)
// 	    this.posts.push(postEmpathyMap);
// 	else
// 	    this.posts.splice(lastIndex, 0, postEmpathyMap);
// };


// Note: Never called this method outside this class. Use fromJSON().
PostsEmpathyMap.prototype.injectJSON = function (json) {
	console.log('json is:', json);
	console.log('json2 is:', JSON.stringify(json));
	//console.trace();
	this.posts = json.map(function (postJSON) {
		return PostEmpathyMap.fromJSON(postJSON); //new PostEmpathyMap(postJSON.content, postJSON.section);
	});
};

PostsEmpathyMap.fromJSON = function (json) {
	var postsEmpathyMap = new PostsEmpathyMap();
	postsEmpathyMap.injectJSON(json);
	return postsEmpathyMap;

	//	posts.set(
	//		this.props.posts.posts.posts.map(function (post) {
	//			return new PostEmpathyMap(post.content, post.section);
				//return new PostEmpathyMap(post.post.content, post.section);
			//return PostEmpathyMap.create(content,);
	//		})
	//	);
};

PostsEmpathyMap.prototype.toJSON = function () {
	return this.posts.map(function (post) {
		return post.toJSON();
	});
};

var PostsEmpathyMapComponent = React.createClass({

	getInitialState: function () {
		return { self: this.props.self };
	},

	componentDidMount: function () {
		document.getElementsByClassName('main-content')[0].scrollTop = 0;
	},

    render: function () {
    	console.log('posts:', this.state.self);
    	function renderPost(post, i) {
			return <PostEmpathyMapComponent key={i} post={post} />; //post.toJSX();
    	}
        return (
			<ul className="vf-list-group list-group">
				{this.state.self.posts.map(renderPost)}
			</ul>
		)
    }
});

var PostsEmpathyMapComponent2 = React.createClass({

	getInitialState: function () {
		return { self: this.props.self };
	},

	componentDidMount: function () {
		document.getElementsByClassName('main-content')[0].scrollTop = 0;
	},

    render: function () {
    	console.log('posts:', this.state.self);
    	function renderPost(post, i) {
			return <PostEmpathyMapComponent2 key={i} post={post} />; //post.toJSX();
    	}
        return (
			<ul className="vf-list-group list-group">
				{this.state.self.posts.map(renderPost)}
			</ul>
		)
    }
});

PostsEmpathyMap.prototype.renderAtId = function (id) {
	console.log('trying to render');
    React.render(<PostsEmpathyMapComponent self={this} />, document.getElementById(id));
    console.log('did render');
};

PostsEmpathyMap.prototype.unmountAtId = function (id) {
	React.unmountComponentAtNode(id);
};

module.exports = PostsEmpathyMap;
module.exports.component = PostsEmpathyMapComponent;
module.exports.component2 = PostsEmpathyMapComponent2;