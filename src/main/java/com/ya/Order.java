package com.ya;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Order extends RestAssuredClient {
    int orderId;
    Response cancelResponse ;
    private static final String ORDER_PATH = "api/v1/orders";

    @Step
    public int createOrder(String color) {
      return  orderId =
                 given()
                        .spec(getBaseSpec())
                        .body("{   " +
                                "\"firstName\" : \"Miha\", " +
                                "\"lastName\": \"Kek\", " +
                                "\"address\": \"Ko7899\", " +
                                "\"metroStation\": 4," +
                                "\"phone\": \"+7 800 255 35 35\", " +
                                "\"rentTime\": 4," +
                                "\"deliveryDate\": \"2022-01-01\"," +
                                "\"comment\": \"Saske\"," +
                                "\"color\":" +"[" + color + "]}")
                        .when()
                        .post(ORDER_PATH)
                        .then().extract().body().path("track");
    }

    @Step
    public void cancelCreatedOrder(int orderTrack) {
    cancelResponse = (Response) given()
                .spec(getBaseSpec())
                .body("{ \"track\": " + orderTrack + " }")
                .when()
                .put(ORDER_PATH + "/cancel")
                .then()
                .assertThat()
                .statusCode(200);
    }
}
