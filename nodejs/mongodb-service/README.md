# Mongodb service

Testing to create a node.js service to store recipies in a mongodb datastore.

Mostly playing around with promises (async, await) and also generating the Swagger API based on the express routes. 

I have tried to divide it into a feature based package structure so,
* src/recipes - Contains the logic for handling the recipes. 

## Pre-requisites

Install pm2 package globally:

npm install pm2 -g

## Generate swagger docs

The command below generates/updates the swagger file based on the controller routes. 
* npm run swagger-autogen
Look in /swagger/swagger_output.json for the generated swagger file.

## Run the application

1. Start mongodb docker container docker run --name my_mongo -p 27017:27017 -d mongo:4.0.21
2. Install the project using npm install
3. pm2 start app.js for background start, otherwise just use npm start
 
Starts the application in background listening to port 8080.

You can access API on http://localhost:8080/doc

## Stop the application
Easy, just hit pm2 stop app.js