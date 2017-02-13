'use strict';

exports.handler =  (event, context, callback) => {
	console.log('Pass to lambda function');
	//Return HTTP 200 success.
	var response = {
        statusCode: 200,
        body: JSON.stringify('{}',null,2)
    };
	callback(null, response);

}