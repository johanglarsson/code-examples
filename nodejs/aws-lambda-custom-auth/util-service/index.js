'use strict';

// sign with default (HMAC SHA256) 
var jwt = require('jsonwebtoken');
var token = jwt.sign({
  data: 'Application X'
}, 'mysecret',{ expiresIn: '1m' });

console.log(token);