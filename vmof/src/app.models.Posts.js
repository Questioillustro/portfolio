function Posts() {
	this.posts = [];
};

// Posts.prototype.get = function () {
// 	return this.posts;
// };

// Posts.prototype.set = function (posts) {
// 	this.posts = posts;
// };

Posts.prototype.add = function (post) {
	this.posts.push(post);
};

Posts.prototype.prnt = function () {
	console.log('post seeing if methods transfer');
};

module.exports = Posts;