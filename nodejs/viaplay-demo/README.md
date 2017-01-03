# Viaplay demo
This demo will query viaplay API for a movie title and get the trailer URL with some help from IMDB Open API.

The structure is as follows:

- routes : Contains the API resource routes
- test: Contains unit test without mock.
- app.js: Server application 

## Usage notes

### Docker
There is a simple docker config that can be built and run. Follow these steps:

1. docker build -t ninja/viaplay-demo-app .
2. docker run -p 49160:8080 -d ninja/viaplay-demo-app

Then navigate to URL http://localhost:49160/trailer?resource=https://content.viaplay.se/pc-se/film/ted-2-2015 or use 

curl -i http://localhost:49160/trailer\?resource\=https://content.viaplay.se/pc-se/film/ted-2-2015

You will receive the URL for the trailer back.

### Locally
Install the package using **npm install**...

.. then just kick **npm start** and open URL curl -i http://localhost:8080/trailer?resource=https://content.viaplay.se/pc-se/film/ted-2-2015

To fire test just hit **npm test**

## Thoughts

Yeah, this one is far from perfect and I have deliberately left URL pattern matching out from the validation of the request. That can be added later on. The timeout settings on the requests are set to 10 sec which has been set to lower the stress on the servers on high loads.

### Improvement suggestions

1. I would rather use the film name as input to the REST resource instead of the full URL to add security to the service.
2. Integration with logging framework
3. Consider using Consul for circuit breaker pattern
4. Put the configuration in a separate config module. 
5. Create a util to support common error handling

### Scaling
I would put the docker container in AWS EC2 or I would use Elastic Beanstalk for deploying the app and use its autoscaling features. 

