# Spring JPA demo service


This service is a learning project for JPA entities. It uses Liquibase for database migration upon startup of the Spring boot application.

## Liquibase migration scripts

I chose to test using XML migration scripts. They are located at classpath com/samples/jpa/recipies/changelog. 

The master script is db.changelog-master.xml which is the one executing the migrations.

## JPA entities

Well, since I like to cook I chose a recipe database. I chose to not use lombok on the equals and hashcode due to it is not optimal for entities where I would like to have more control. 

The package is by feature so everyting, the controller, repositories and entities are in the recipies package.

## API 

Exposed at http://localhost:8080/swagger-ui.html 