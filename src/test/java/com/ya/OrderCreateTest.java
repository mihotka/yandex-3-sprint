package com.ya;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;


@RunWith(Parameterized.class)
public class OrderCreateTest {
        private  final String color;
        Order order = new Order();
        int orderTrack;
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
            assertThat(order.orderId, greaterThan(0));
        }
        @After
        @Step
        public void cancelOrder() {
                order.cancelCreatedOrder(orderTrack);
        }
    }

