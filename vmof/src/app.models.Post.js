function Post(content) {
	this.content = content;
	this.comments = [];
	this.votes = 0;
};

Post.prototype.getContent = function () {
	return this.content;
};

Post.prototype.getComments = function () {
	return this.comments;
};

Post.prototype.getVotes = function () {
	return this.votes;
};

Post.prototype.addComment = function (comment) {
	this.comments.push(comment);
};

Post.prototype.upVote = function () {
	//this.votes++;
	this.votes = 1;
};

Post.prototype.downVote = function () {
	//this.votes--;
	this.votes = -1;
};

Post.prototype.clearVote = function () {
	this.votes = 0;
};

module.exports = Post;