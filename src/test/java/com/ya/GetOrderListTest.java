package com.ya;

import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertFalse;

public class GetOrderListTest {

    OrderList orderList = new OrderList();

    @Test
    @DisplayName("Получение списка заказов")
    public void getOrderListTest() {
        orderList.getOrderList();
        List<Object> orders = orderList.response.then().extract().jsonPath().getList("orders");
        assertFalse(orders.isEmpty());
    }
}
