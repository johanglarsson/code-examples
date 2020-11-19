# Spring JPA demo service


This service is a learning project for JPA entities. It uses Liquibase for database migration upon startup of the Spring boot application.

## Liquibase migration scripts

I chose to test using XML migration scripts. They are located at classpath changelog. 

The master script is db.changelog-master.xml which is the one executing the migrations.

The only thing needed to make this work is the liquibase dependency and the configuration located at RecipeConfig.java.

## JPA entities

Well, since I like to cook I chose a recipe database. I chose to not use lombok on the equals and hashcode due to it is not optimal for entities where I would like to have more control. 

The package is by feature so everyting, the controller, repositories and entities are in the recipies package.

## API 

Exposed at http://localhost:8080/swagger-ui.html (OpenAPI)

## H2 in-memory database

Access at http://localhost:8080/h2-console

Use jdbc:h2:mem:recipedb with pwd sa and you are a happy camper.

If you want to try out the migration part of liquibase then just switch to a file based db to get the data persisted in application.properties. Eg. jdbc:h2:file:~/recipedb

## Docker

Docker image can be created in two ways. both using layering to improve performance.

* Manually:
  docker build -f src/main/docker/Dockerfile -t mytest:v1 .

* Using build-pack
  mvn spring-boot:build-image

