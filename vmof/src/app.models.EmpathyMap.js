var PostsEmpathyMap = require('./app.models.PostsEmpathyMap.jsx');
var PostEmpathyMap = require('./app.models.PostEmpathyMap.jsx');

function EmpathyMap() {
	this.think = [];
	this.hear = [];
	this.see = [];
	this.say = [];
	this.pain = [];
	this.gain = [];
};

var SECTION_THINK = 0;
var SECTION_HEAR = 1;
var SECTION_SEE = 2;
var SECTION_SAY = 3;
var SECTION_PAIN = 4;
var SECTION_GAIN = 5;

EmpathyMap.prototype.addIdea = function (idea, section, user) {
	switch (section) {
		case SECTION_THINK: this.addThinkIdea(idea, user);
			break;
		case SECTION_HEAR: this.addHearIdea(idea, user);
			break;
		case SECTION_SEE: this.addSeeIdea(idea, user);
			break;
		case SECTION_SAY: this.addSayIdea(idea, user);
			break;
		case SECTION_PAIN: this.addPainIdea(idea, user);
			break;
		case SECTION_GAIN: this.addGainIdea(idea, user);
			break;
	}
};

EmpathyMap.prototype.addThinkIdea = function (idea, user) {
	this.think.push({idea: idea, user: user});
};

EmpathyMap.prototype.addHearIdea = function (idea, user) {
	this.hear.push({idea: idea, user: user});
};

EmpathyMap.prototype.addSeeIdea = function (idea, user) {
	this.see.push({idea: idea, user: user});
};

EmpathyMap.prototype.addSayIdea = function (idea, user) {
	this.say.push({idea: idea, user: user});
};

EmpathyMap.prototype.addPainIdea = function (idea, user) {
	this.pain.push({idea: idea, user: user});
};

EmpathyMap.prototype.addGainIdea = function (idea, user) {
	this.gain.push({idea: idea, user: user});
};

EmpathyMap.prototype.toJSON = function () {
	console.log('toPosts() think:', this.think);
	console.log('toPosts() think2:', JSON.stringify(this.think));

	var posts = {};

	var thinkPosts = new PostsEmpathyMap();	
	this.think.forEach(function (item) {
		thinkPosts.add(new PostEmpathyMap(item.idea, SECTION_THINK, item.user));
	});
	posts['think'] = thinkPosts.toJSON();
	//posts.push(thinkPosts.toJSON());

	var hearPosts = new PostsEmpathyMap();	
	this.hear.forEach(function (item) {
		hearPosts.add(new PostEmpathyMap(item.idea, SECTION_HEAR, item.user));
	});
	posts['hear'] = hearPosts.toJSON();
	//posts.push(hearPosts.toJSON());

	var seePosts = new PostsEmpathyMap();	
	this.see.forEach(function (item) {
		seePosts.add(new PostEmpathyMap(item.idea, SECTION_SEE, item.user));
	});
	posts['see'] = seePosts.toJSON();
	//posts.push(seePosts.toJSON());
	
	var sayPosts = new PostsEmpathyMap();	
	this.say.forEach(function (item) {
		sayPosts.add(new PostEmpathyMap(item.idea, SECTION_SAY, item.user));
	});
	posts['say'] = sayPosts.toJSON();
	//posts.push(sayPosts.toJSON());
	
	var painPosts = new PostsEmpathyMap();	
	this.pain.forEach(function (item) {
		painPosts.add(new PostEmpathyMap(item.idea, SECTION_PAIN, item.user));
	});
	posts['pain'] = painPosts.toJSON();
	//posts.push(painPosts.toJSON());
	
	var gainPosts = new PostsEmpathyMap();	
	this.gain.forEach(function (item) {
		gainPosts.add(new PostEmpathyMap(item.idea, SECTION_GAIN, item.user));
	});
	posts['gain'] = gainPosts.toJSON();
	//posts.push(gainPosts.toJSON());

	console.log('toPosts() posts are:', posts);
	console.log('toPosts() posts2 are:', JSON.stringify(posts));

	return posts;
};



// EmpathyMap.prototype.toPostsList = function () {
// 	var postsList = [];
	
// 	var thinkPosts = new PostsEmpathyMap();
// 	this.think.forEach(function (idea) {
// 		thinkPosts.add(new PostEmpathyMap(idea, SECTION_THINK)));
// 	});
// 	postsList.add(thinkPosts);
	
// 	var hearPosts = new PostsEmpathyMap();
// 	this.hear.forEach(function (idea) {
// 		hearPosts.add(new PostEmpathyMap(idea, SECTION_HEAR));
// 	});
// 	postsList.add(thinkPosts);

// 	var seePosts = new PostsEmpathyMap();
// 	this.see.forEach(function (idea) {
// 		seePosts.add(new PostEmpathyMap(idea, SECTION_SEE));
// 	});
// 	postsList.add(thinkPosts);
	
// 	var sayPosts = new PostsEmpathyMap();
// 	this.say.forEach(function (idea) {
// 		sayPosts.add(new PostEmpathyMap(idea, SECTION_SAY));
// 	});
// 	postsList.add(thinkPosts);
	
// 	var painPosts = new PostsEmpathyMap();
// 	this.pain.forEach(function (idea) {
// 		painPosts.add(new PostEmpathyMap(idea, SECTION_PAIN));
// 	});
// 	postsList.add(thinkPosts);
	
// 	var gainPosts = new PostsEmpathyMap();
// 	this.gain.forEach(function (idea) {
// 		gainPosts.add(new PostEmpathyMap(idea, SECTION_GAIN));
// 	});

// 	return posts;
// };

EmpathyMap.prototype.toPosts = function () {
	console.log('toPosts() think:', this.think);
	console.log('toPosts() think2:', JSON.stringify(this.think));
	var posts = new PostsEmpathyMap();
	
	this.think.forEach(function (idea) {
		posts.add(new PostEmpathyMap(idea, SECTION_THINK));
	});
	
	this.hear.forEach(function (idea) {
		posts.add(new PostEmpathyMap(idea, SECTION_HEAR));
	});
	
	this.see.forEach(function (idea) {
		posts.add(new PostEmpathyMap(idea, SECTION_SEE));
	});
	
	this.say.forEach(function (idea) {
		posts.add(new PostEmpathyMap(idea, SECTION_SAY));
	});
	
	this.pain.forEach(function (idea) {
		posts.add(new PostEmpathyMap(idea, SECTION_PAIN));
	});
	
	this.gain.forEach(function (idea) {
		posts.add(new PostEmpathyMap(idea, SECTION_GAIN));
	});

	console.log('toPosts() posts are:', posts);
	console.log('toPosts() posts2 are:', JSON.stringify(posts));

	return posts;

	// var think = this.think.map(function (idea) {
	// 	return new PostEmpathyMap(idea, SECTION_THINK);
	// })

	// var hear = this.hear.map(function (idea) {
	// 	return new PostEmpathyMap(idea, SECTION_HEAR);
	// });

	// var see = this.see.map(function (idea) {
	// 	return new PostEmpathyMap(idea, SECTION_SEE);
	// });

	// var say = this.say.map(function (idea) {
	// 	return new PostEmpathyMap(idea, SECTION_SAY);
	// });

	// var pain = this.pain.map(function (idea) {
	// 	return new PostEmpathyMap(idea, SECTION_PAIN);
	// });

	// var gain = this.gain.map(function (idea) {
	// 	return new PostEmpathyMap(idea, SECTION_GAIN);
	// });
	
	// var posts = new PostsEmpathyMap();
	// posts.set(
	// 			think
	// 	.concat(hear)
	// 	.concat(see)
	// 	.concat(say)
	// 	.concat(pain)
	// 	.concat(gain)
	// );
	// console.log('empathy map posts:', posts.get());
	//return posts;
	// return new PostsEmpathyMap().set(
	// 		this.think.map(function (idea) {
	// 			return new PostEmpathyMap(idea, SECTION_THINK);
	// 		})
	// 	.concat(
	// 		this.hear.map(function (idea) {
	// 			return new PostEmpathyMap(idea, SECTION_HEAR);
	// 		})
	// 	).concat(
	// 		this.see.map(function (idea) {
	// 			return new PostEmpathyMap(idea, SECTION_SEE);
	// 		})
	// 	).concat(
	// 		this.say.map(function (idea) {
	// 			return new PostEmpathyMap(idea, SECTION_SAY);
	// 		})
	// 	).concat(
	// 		this.pain.map(function (idea) {
	// 			return new PostEmpathyMap(idea, SECTION_PAIN);
	// 		})
	// 	).concat(
	// 		this.gain.map(function (idea) {
	// 			return new PostEmpathyMap(idea, SECTION_GAIN);
	// 		})
	// 	)
	// );
};

EmpathyMap.prototype.toList = function () {
	return 	this.think
	.concat(this.hear)
	.concat(this.see)
	.concat(this.say)
	.concat(this.pain)
	.concat(this.gain);
};

module.exports = EmpathyMap;