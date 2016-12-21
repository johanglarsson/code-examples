#!/usr/bin/env node

var app = require('./index');

app.set('port', process.env.PORT || 8080);

// Start listening for HTTP requests
var server = app.listen(app.get('port'), function() {
  
  var host = server.address().address;
  var port = server.address().port;
  console.log('Example app listening at http://%s:%s', host, port);

});
