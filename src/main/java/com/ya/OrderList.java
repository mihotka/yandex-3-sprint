package com.ya;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderList extends RestAssuredClient {
    Response response;
    private static final String ORDERLIST_PATH = "api/v1/orders";

    public void getOrderList() {
        response =
                (Response) given()
                        .spec(getBaseSpec())
                        .when()
                        .get(ORDERLIST_PATH)
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .extract();
    }
}
