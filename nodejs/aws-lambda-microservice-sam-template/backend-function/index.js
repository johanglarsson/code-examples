'use strict';

var AWS = require('aws-sdk');
var async = require('async');
AWS.config.apiVersions = {
	dynamodb : "2012-08-10"
}
AWS.config.region = "eu-west-1";

var dynamodb = new AWS.DynamoDB.DocumentClient()
var firehose = new AWS.Firehose()
var kinesis = new AWS.Kinesis()
var lambda = new AWS.Lambda()

exports.create = (event, context, cb) => {
	
	log(event, 'audit', 'Processing request')

	//****************************
	// Microservice steps:
	// 1. Validate
	// 2. Get/Store resource data (Business logic)
	// 3. Log metrics
	// Return data
	//****************************
	async.waterfall( [ async.apply(validateRequest, event), storeSampleDataToDynamoDB, logResourceAccessMetric ],
	//  Constuct HTTP response
	function (err, data) {
		
		let status;
		let body;
		
		if ( err ) {
			console.log("TODO!!!! Error, log full request " + JSON.stringify(event, null,2));
			status=500;
			body = {
				errorCode : "Unknown Error",
				message : err
			};
		} else {
			// Set HTTP response
			status = 200;
			body = "";
			
		}
		let response = {
			statusCode: status,
			body: JSON.stringify(body,null,2),
			headers: {
				'Content-Type': 'application/json',
			}
		};
		log(event, 'audit', 'Returning response '+ JSON.stringify(response));
		context.succeed(response);
	})
	
};

// MAIN LOGIC
exports.get = (event, context, cb) => {
	log(event, 'audit', 'Processing GET /orders/' + event.pathParameters.orderid + ' request')
	//****************************
	// Microservice steps:
	// 1. Validate
	// 2. Get/Store resource data (Business logic)
	// 3. Log metrics
	// Return data
	//****************************
	async.waterfall( [ async.apply(validateRequest, event), getSampleDataFromDynamoDB, logResourceAccessMetric ], 
	// Finally clause
	function (err, data) {

		let status;
		let body;
		console.log(data);
		if ( err ) {
			console.log("TODO!!!! Error, log full request " + JSON.stringify(event, null,2));
			status=500;
			body = {
				errorCode : "Unknown Error",
				message : err
			};
		} else if ( data.Item ) {
			// Set HTTP response
			status = 200;
			body = data.Item.order
		} else {
			// Empty
			status = 404;
			body = {
				errorCode : "Not found",
				message : "Order not found"
			};
		}
		let response = {
			statusCode: status,
			body: JSON.stringify(body),
			headers: {
				'Content-Type': 'application/json',
			}
		};
		//http://docs.aws.amazon.com/apigateway/latest/developerguide/api-gateway-set-up-simple-proxy.html
		log(event,'audit','Returning response '+ JSON.stringify(response));
		context.succeed(response);
	} 
	);

};

//***************************
// ASYNC FUNCTIONS
//***************************

// Initial validation logic
var validateRequest = function(event, next) {
	next(null, event);
}

// Store data in dynamodb
var storeSampleDataToDynamoDB = function(data, next){
	let body = JSON.parse(data.body);
	var params = {
		Item: {
			orderId : body.order.orderid,
			order: body
		},
		TableName: process.env.TABLE_NAME,
		ReturnValues: "ALL_OLD"
	}

	// Store data
	let metricStart = new Date();
	dynamodb.put( params, function(err, data)  {
		let execTime = new Date() - metricStart;
		next(err, execTime, data);
	});

}


// Retrieve sample data from table
var getSampleDataFromDynamoDB = function (data, next ) {
	// Set retry attempts. 1s 2s 4s 8s 16s
	AWS.config.maxRetries = 5;
	var params = {
		TableName : process.env.TABLE_NAME,
		Key: {
			orderId: data.pathParameters.orderid
		},
		AttributesToGet: [
		"order"
		]
	};
	let metricStart = new Date();

	// GET data from dynamodb.
	dynamodb.get( params, function(err,data) {
		let execTime = new Date()-metricStart;
		next(err, execTime, data);
	});

}

// Log metric
var logResourceAccessMetric = function(execTime, data, next) {
	console.log('DynamoDB execution time: ' + execTime);
	next(null, data);
	
}

var log = (event, type, message) => {
	let log = {
		'level' : 'INFO',
		'type' : type,
		'timestamp':new Date(),
		'x-dw-session-id': event.headers['x-dw-session-id'],
		'x-dw-correlation-id': event.headers['x-dw-correlation-id'],
		'x-dw-source': event.headers['x-dw-source'],
		'x-dw-user-id': event.headers['x-dw-user-id'],
		'message' : message
	}

	console.log(JSON.stringify(log, null, 2))
}
