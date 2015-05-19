module.exports = function (app) {

    app.use('/', require('./app.routes.attendee.js'));
    app.use('/facilitator', require('./app.routes.facilitator.js'));

};