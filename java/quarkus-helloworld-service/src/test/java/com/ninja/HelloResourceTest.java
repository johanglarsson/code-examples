package com.ninja;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class HelloResourceTest {

    @Test
    public void testHelloEndpoint() {
        HelloModel helloModel = new HelloModel("HELLO JOHAN");
        given().when().get("/resteasy/hello").then().statusCode(200).extract().as(HelloModel.class).equals(helloModel);
    }

}