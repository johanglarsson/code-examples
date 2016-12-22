'use strict';

var express = require('express');
var router = express.Router();
var request=require('request');

router.get('/', findTrailer, function(req, res) { });
module.exports = router;

const imdAPIKey = '8c5e88c814cb15cf1c1e732f09c8bf60';
const serviceTimeout = 5000;

// Find trailer operation
function findTrailer(req, res, next) {
  
  validateRequest(req.query.resource)
  	.then(getViaplayContent)
  	.then(getImdbTrailerURL)
  	.then(function(data){
  		res.set({'content-type': 'text/plain'})
  		return res.send(data);
  	})
  	.catch(function(err){
		return res.status(err.code).json(err.error);
  	})

}

// validate request to prevent invalid requests.
function validateRequest(resourceURL) {

	return new Promise(function(fullfill, reject) {

	  if (resourceURL) {
			//check the url format
			return fullfill(resourceURL );
		} else {
			return reject({code:404, message:'Missing resource URL'});
		}
	})
	
}

// get viaplay content info
function getViaplayContent(resourceURL) {

	return new Promise(function(fullfill, reject) {

		request.get(resourceURL,{ timeout: serviceTimeout },function(err,res,body){
		
			if(err) return next({code:500, message:err});
			if (res.statusCode === 404 ) return reject({code:res.statusCode, error:{code:1,message:'Movie ' + resourceURL + ' not found in Viaplay'}});
	  		if(res.statusCode !== 200 ) return reject({code:res.statusCode, error:{code:999,message:res.body}});

	  		let response = JSON.parse(res.body);
	  		
	  		return fullfill(response._embedded['viaplay:blocks'][0]._embedded['viaplay:product'].content.imdb.id); 

		});	

	})
	
}
// get IMDB trailer URL based on a movie id.
function getImdbTrailerURL(movieID ) {
	
	return new Promise(function(fullfill, reject) {

		request.get('https://api.themoviedb.org/3/movie/' + movieID + '/videos?api_key=' + imdAPIKey + '&language=en-US',{ timeout: serviceTimeout },function(err,res,body){
			
			if(err) return next({code:500, message:err});
			if (res.statusCode === 404 ) return reject({code:res.statusCode, error:{code:2,message:'Movie ' + movieID + ' not found in IMDB'}});
	  		if(res.statusCode !== 200 ) return reject({code:res.statusCode, error:{code:999,message:res.body}});
	  		let response = JSON.parse(res.body);
	  		return fullfill('http://www.youtube.com/watch?v=' + response.results[0].key); 

		});
	});
}