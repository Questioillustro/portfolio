var Activity = require('./app.models.Activity.js');

TwoTruths.prototype = new Activity();
TwoTruths.prototype.constructor = TwoTruths;

function TwoTruths(room) {
	Activity.call(this);
	this.timeLimit;
	this.timeOutID;
	this.room = room;
	this.participates;
	this.readyUsers = 0;
};

TwoTruths.prototype.setTimer = function (params, people) {
	this.timeLimit = params.timeLimit * 60000;
	console.log("setting the timout to be about " + this.timeLimit);
	this.participates=people;
	//this.timeOutID = setTimeout(this.activityTimer(io), this.timeLimit);
	console.log("Set the timeout and continueing " + this.room + " poeple count is " + this.participates);
	
	return this.timeLimit;
};

TwoTruths.prototype.readyUp = function(){
	this.readyUsers+=1;
	if(this.participates >= this.readyUsers){
		return true;
	} else {
		return false
	}
};

TwoTruths.prototype.resetReady = function(){
	this.readyUsers = 0;
};

TwoTruths.prototype.leave = function(){
	this.participates-=1;
};



module.exports = TwoTruths;