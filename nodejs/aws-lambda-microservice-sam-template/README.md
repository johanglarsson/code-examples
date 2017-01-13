# Microservice template

This is a template showing deployment of a simple microservice simulating an order store and get. It uses cloudformation SAM to deploy the whole microservice in an isolated way. The deployment uses GNU makefiles but in reality any build can shoot this to AWS.

## Usage guide


### Install NPM packages

First the npm modules need to be installed

npm install in the root directory.

TODO: unfortunately I should handle node_modules in backend-function directory.

### Deploy to AWS

Then , to deploy this issue **make deploy**. This will upload the backend .zip to S3 and use cloudformation to execute the stack.

