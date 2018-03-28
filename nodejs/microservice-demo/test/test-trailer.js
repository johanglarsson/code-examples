'use strict';

process.env.NODE_ENV = 'test';

let chai = require('chai');
let chaiHttp = require('chai-http');
let server = require('../app');
let should = chai.should();
let expect = require('chai').expect;
let assert = require('chai').assert;


chai.use(chaiHttp);

describe('Trailer API', () => {
  
  it('GET trailer should return correct trailer URL', (done) => {
    chai.request(server)
      .get('/trailer?resource=https://content.viaplay.se/pc-se/film/ted-2-2015')
      .end(function(err, res) {
        expect(res).to.have.status(200);
        expect(res).to.have.header('content-type', 'text/plain; charset=utf-8');
        expect(res).to.be.text;
        assert.equal(res.text,'http://www.youtube.com/watch?v=S3AVcCggRnU','Returned URL is not a trailer');
        done();  
      })
  });
  it('GET trailer should not found when the URL is invalid', (done) => {
    chai.request(server)
      .get('/trailer?resource=https://content.viaplay.se/pc-se/film/ted-2-2015NOTFOUND')
      .end(function(err, res) {
        expect(res).to.have.status(404);
        expect(res).to.have.header('content-type', 'text/plain; charset=utf-8');
        expect(res).to.be.text;
        done();  
      })
  });

  it('GET trailer should not found when the URL resource query parameter is missing', (done) => {
    chai.request(server)
      .get('/trailer')
      .end(function(err, res) {
        expect(res).to.have.status(404);
        expect(res).to.have.header('content-type', 'text/plain; charset=utf-8');
        expect(res).to.be.text;
        assert.equal(res.text,'Missing resource query parameter','Returned message is not correct');
        done();  
      })
  });


});
