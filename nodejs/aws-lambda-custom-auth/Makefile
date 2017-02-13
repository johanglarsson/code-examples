# Context for resources, e.g demo_v1, test_v1 and so on
RESOURCE_CONTEXT=dev
APPLICATION_NAME=custauth
DEPLOY_BUCKET_NAME=dwtech-samples
#############
# BUILD LAMBDA
#############

.PHONY: build-no-swagger
build:
	@echo "-----Building Lambda Backend function with API defined in Cloudformation------"
	mkdir -p tmp
	aws cloudformation package --template-file cloudformation.yaml --output-template-file tmp/cloudformation.yaml --s3-bucket $(DEPLOY_BUCKET_NAME) --region us-east-1
	@echo "----Build Lambda Backend function done ----"

#############
# TEST
#############

.PHONY: test
test:
	@echo "Do your unit test step here...."

#############
# DEPLOY
#############

.PHONY: deploy
deploy: build
	@echo "Deploy microservice template"
	date
	aws cloudformation deploy --template-file tmp/cloudformation.yaml --stack-name $(APPLICATION_NAME)-$(RESOURCE_CONTEXT) --capabilities CAPABILITY_NAMED_IAM --region us-east-1 --parameter-overrides  ResourceContext=$(RESOURCE_CONTEXT) ApplicationName=$(APPLICATION_NAME)
	date
	@echo "Deploy done"


#############
# CLEAN
#############
.PHONY: clean
clean:
	@echo "Clean"
	rm -Rf tmp
	@echo "Clean done"
