'use strict';

// Used modules.
var express = require('express');
var app = express();
var async = require('async');
var request=require('request');


// Set GET router
var trailer = express.Router();
trailer.get('/', findTrailer, function(req, res) { });
app.use('/trailer', trailer);
module.exports = app;

const imdAPIKey = '8c5e88c814cb15cf1c1e732f09c8bf60';
const serviceTimeout = 5000;

// Find trailer operation
function findTrailer(req, res, next) {
  
  async.waterfall( [ async.apply(validateRequest,req.query.resource), getViaplayContent, getImdbTrailerURL ], function(err, data) {
  	
  	if ( err ) {
  		return res.status(err.code).json(err.error);
  	} else {
  		return res.send(data);
	}
  })

}

// validate request to prevent invalid requests.
function validateRequest(resourceURL, next) {
	console.log(resourceURL);
	if (resourceURL) {
		//check the url format
		return next(null, resourceURL );
	} else {
		return next({code:404, message:'Missing resource URL'});
	}
}

// get viaplay content info
function getViaplayContent(resourceURL,next) {


	request.get(resourceURL,{ timeout: serviceTimeout },function(err,res,body){
		
		if(err) return next({code:500, message:err});
		if (res.statusCode === 404 ) return next({code:res.statusCode, error:{code:1,message:'Movie ' + resourceURL + ' not found in Viaplay'}});
  		if(res.statusCode !== 200 ) return next({code:res.statusCode, error:{code:999,message:res.body}});

  		let response = JSON.parse(res.body);
  		
  		return next(null, response._embedded['viaplay:blocks'][0]._embedded['viaplay:product'].content.imdb.id); 

	});	
}
// get IMDB trailer URL based on a movie id.
function getImdbTrailerURL(movieID, next ) {

	request.get('https://api.themoviedb.org/3/movie/' + movieID + '/videos?api_key=' + imdAPIKey + '&language=en-US',{ timeout: serviceTimeout },function(err,res,body){
		
		if(err) return next({code:500, message:err});
		if (res.statusCode === 404 ) return next({code:res.statusCode, error:{code:2,message:'Movie ' + movieID + ' not found in IMDB'}});
  		if(res.statusCode !== 200 ) return next({code:res.statusCode, error:{code:999,message:res.body}});
  		let response = JSON.parse(res.body);
  		return next(null, 'http://www.youtube.com/watch?v=' + response.results[0].key); 

	});
}