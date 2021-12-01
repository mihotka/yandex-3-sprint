package com.ya;

import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest {

    private CourierClient courierClient;
    private int courierId;
    Courier courier = Courier.getRandom();
    @Before
    public void setUp() {
        courierClient = new CourierClient();

    }

/*  @After
    public void tearDown() {
        courierId = courierClient.login(new CourierCredentials(courier.login, courier.password));
        courierClient.delete(courierId);
    }*/


    @Test
    @DisplayName("Создание курьера с валидными данными")
    public void courierCanBeCreatedWithValidDataTest() {
        courierClient.create(courier).then().assertThat().statusCode(201);
        boolean actual = courierClient.response.body().path("ok");
        assertTrue(actual);
        courierId = courierClient.login(new CourierCredentials(courier.login, courier.password));
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Отображение ошибки при создании курьера с уже существующим логином")
    public void courierCanNotBeCreatedWithSimilarLoginTest() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier).then().assertThat().statusCode(201);
        courierClient.create(courier).then().assertThat().statusCode(409);
        courierId = courierClient.login(new CourierCredentials(courier.login, courier.password));
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Отображение сообщения ошибки при создании курьера с уже существующим логином")
    public void courierErrorMessageWhenCreatingSimilarCourierTest() {
        Courier courier = Courier.getRandom();
        courierClient.create(courier).then().assertThat().statusCode(201);
        courierClient.create(courier).then().assertThat().statusCode(409).body("message", equalTo("Этот логин уже используется"));
        courierId = courierClient.login(new CourierCredentials(courier.login, courier.password));
        courierClient.delete(courierId);
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
    @DisplayName("Отображение ошибки при создании курьера без имени юзера")
    public void createCourierWithNullFirstname(){
        String login = "xxxxxx" + RandomStringUtils.randomAlphabetic(10);
        Courier courier = new Courier(login, "1234Bb", null);
        courierClient.create(courier).then().assertThat().statusCode(400).body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }


}
