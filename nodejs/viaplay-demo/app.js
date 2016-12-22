'use strict';

// Used modules.
var express = require('express');
var app = express();

app.set('port', process.env.PORT || 8080);
var bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// Start listening for HTTP requests
var server = app.listen(app.get('port'), function() {
  
  var host = server.address().address;
  var port = server.address().port;

});


// Set all microservice endpoints:
var trailerRouter = require('./routes/trailerRouter')
app.use('/trailer', trailerRouter);

module.exports = app