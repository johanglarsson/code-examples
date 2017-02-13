var jwt = require('jsonwebtoken');

exports.handler =  (event, context, callback) => {

    // Get token supplied in request.
    var token = event.authorizationToken;
    
    // Crack JWT token with mysecret as secret key 
    jwt.verify(token, 'mysecret', { ignoreExpiration: false }, function(err, decoded) {
      if (err) {
        console.log(err);
        if ( err.name === 'TokenExpiredError') {
            console.log('Calling back token expired');
            callback('Unauthorized');
        } else {
            callback(null, generatePolicy('user', 'Deny', event.methodArn));
        }
      } else {
        console.log(decoded);
        callback(null, generatePolicy('user', 'Allow', event.methodArn));
      }
    });



};

var generatePolicy = function(principalId, effect, resource) {
    var authResponse = {};
    
    authResponse.principalId = principalId;
    if (effect && resource) {
        var policyDocument = {};
        policyDocument.Version = '2012-10-17'; // default version
        policyDocument.Statement = [];
        var statementOne = {};
        statementOne.Action = 'execute-api:Invoke'; // default action
        statementOne.Effect = effect;
        statementOne.Resource = resource;
        policyDocument.Statement[0] = statementOne;
        authResponse.policyDocument = policyDocument;
    }
    
    // Can optionally return a context object of your choosing.
    authResponse.context = {};
    authResponse.context.stringKey = "stringval";
    authResponse.context.numberKey = 123;
    authResponse.context.booleanKey = true;
    return authResponse;
}