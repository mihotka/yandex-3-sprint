package com.ya;

import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class InvalidCreateCourierTest {
    private CourierClient courierClient;

    @Before
    public void setUp() {
        courierClient = new CourierClient();

    }
    @Test
    @DisplayName("Отображение ошибки при создании курьера без логина")
    public void createCourierWithNullLogin(){
        Courier courier = new Courier(null, "1234Bb", "Nick");
        courierClient.create(courier).then().assertThat().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Отображение ошибки при создании курьера без пароля")
    public void createCourierWithNullPassword(){
        String login = "xxxxxx" + RandomStringUtils.randomAlphabetic(10);
        Courier courier = new Courier(login, null, "Nick");
        courierClient.create(courier).then().assertThat().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Успешное создание курьера без имени")
    public void createCourierWithNullFirstname(){
        String login = "xxxxxx" + RandomStringUtils.randomAlphabetic(10);
        Courier courier = new Courier(login, "1234Bb", null);
        courierClient.create(courier).then().assertThat().statusCode(201);
    }
}
