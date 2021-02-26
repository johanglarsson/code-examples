# Project

This project demonstrates some cloud capabilites using kafka streams and spring cloud task.

## The scenario...

...is as follows...

We have a spring cloud task ***stream-task*** which takes a number of parameters and just outputs it to console. An idea is to trigger something else or store something in database but for now, just testing the flow.

The ***stream-task-source*** is the producer of the task message which says: "Hey, execute THIS task on my behalf with these parameters". It publishes a TaskLauncher message to a kafka stream topic called ***tasktopic***

This message is picked up by ***stream-task-sink*** with a simple annoation @EnableTaskLauncher with the correct maven dependencies so the classpath is correct. The important part is that the stream binder and task is on the classpath. The sink executes the task using maven lookup.
In my example it is a local maven repo but could be a remote one.

### How to initiate the task?

Simple issue the command:

```curl --location --request POST 'http://localhost:8080/tasks' --header 'Content-Type: text/plain' --data-raw '1, 2323, 3554d'```

## What infrastructure components are required

In short, a message bus (Kafka or RabbitMQ or whatever) and a database for the task itself. I have used MySQL.


# MySQL in docker...

...Follow these steps which are a bit cumbersome.

1. Create the volume
docker volume create mysql-volume

2. Start MySQL using the volume
docker run --name=my-mysql -p3306:3306 -v mysql-volume:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -d mysql/mysql-server:8.0.20

If you want to check out the logs then just use:
```docker logs my-mysql```

3. Fix the database and make sure you can access MySQL from local connection.
```docker exec -it my-mysql bash```

Inside the bash, shoot:
```mysql -u root -p```

When you have the db prompt then use this:
```
CREATE DATABASE TASKTEST;

update mysql.user set host = ‘%’ where user=‘root’;

```

4. Finally restart the conainer
```docker restart mk-mysql```
