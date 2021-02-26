# Kafka stream application

This illustrates what is needed to use Spring Cloud Stream to connect to Apache kafka. This simulates a simple consuming
use case where we process the stream and stores it. It does not use any of the complex use cases like grouping into
KTabe.

## Tech stack

* Lombok
* Mapstruct (DTO > Model mapping)
* Spring Cloud Stream

## Spring Cloud Stream

When you create a Bean which returns a Consumer or Function with KStream class then spring cloud stream binder kicks in
and creates inbound and outbound topics with the same name as the bean. This can be overridden (Which I have done in
application.properties)

In this example we have overridden process to a destination called ***my.topic***

## Application flow

OrderDTO published to Kafka topic > Consuming stream > OrderDto mapped to model > Order stored in local db.

Start the application and then publish message using Kafka Cli. See example below:

Produce an order message to the Kafka topic **my.topic**

``` echo '{"id":"123", "name":"Testing this", "quantity": 2, "price":2345678}' | kafka-console-producer --broker-list localhost:9092 --topic my.inbound```

The stream client will process the message directly and store it in an embedded H2 database.

<pre>
There is no UI connected to this. It is a pure backend service.
</pre>
