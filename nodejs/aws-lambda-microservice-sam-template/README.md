# Microservice template

This is a template showing deployment of a simple microservice simulating an order store and get. It uses cloudformation SAM to deploy the whole microservice in an isolated way. The deployment uses GNU makefiles but in reality any build can shoot this to AWS.

## Usage guide

The microservice is defined in service.yaml (Cloudformation template) and the backend-function code is in index.js (backend-function folder)


### Install NPM packages

First the npm modules need to be installed

npm install in the root directory and in backend-function directory

### Deploy to AWS

Then , to deploy this issue **make deploy**. This will upload the backend .zip to S3 and use cloudformation to execute the stack.

### Run locally

1. Make sure you have deployed the functions in AWS (We need the table). 
2. Set the create table name in environment variable TABLE_NAME. Exmaple: export TABLE_NAME=xxxxx
3. Run the functions using grunt

Create an entry in dynamodb:

* grunt lambda_invoke:create

Get the above entry from dynamobdb:

* grunt lambda_invoke:get


