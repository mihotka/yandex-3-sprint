package com.ya;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class OrderCreateTest {
        private  final String color;
        Order order = new Order();

    public OrderCreateTest(String color) {
        this.color = color;
    }

    @Parameterized.Parameters
        public static Object[][] OrderCreateTestParamApiTestData() {
            return new Object[][]{
                    {"\"BLACK\""},
                    {""},
                    {"\"GREY\""},
                    {"\"GREY\", \"BLACK\""},
            };
        }

        @Test
        @DisplayName("Создания заказа с цветами серый/черный/серый и черный/без цвета")
        @Step
        public void orderColorSelectTest() {
            order.createOrder(color);
            order.response.then().assertThat().statusCode(201);
            int orderTrack = order.response.then().extract().body().path("track");
            assert orderTrack > 0;

            order.cancelCreatedOrder(orderTrack);
            order.cancelResponse.then().assertThat().statusCode(200);
        }
    }

