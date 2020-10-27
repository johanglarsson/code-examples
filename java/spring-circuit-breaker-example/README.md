# Read Me First
This is a simple sample project to demonstrate Circuit Breaker pattern using WebClient for once. I have included hystrix dashboard which is accessible from URL http://localhost:8080/hystrix/dashboard. The hystrix stream can be accessible from http://localhost:8080/actuator/hystrix.stream

The sample also is a gradle project for my own benefit and learnings.

## Usage

The Rest API gets the quote from an external service (Taken from a spring boot guide) and returns the message. 
http://localhost:8080/quote?useCorrectUri=false

THe parameter useCorrectUri provokes the fallback method to be invoked if it is set to true.

## Unit tests
I've tried to use MockWebServer to hide away real endpoint on the test of QuoteService. 

### Additional notes

The webclient has been created as a bean in QuoteConfig class.