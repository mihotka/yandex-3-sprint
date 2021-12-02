package com.ya;
//
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

import static org.hamcrest.Matchers.equalTo;

public class CourierLoginTest {
    private CourierClient courierClient;
    private int courierId;
    Courier courier = Courier.getRandom();
    CourierCredentials courierCredentials = new CourierCredentials(courier.login, courier.password);

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierId = courierClient.login(courierCredentials);
        courierClient.delete(courierId);
    }

   @Test
   @DisplayName("Успешная авторизация курьера в системе")
   public void courierCanNotBeCreatedWithSimilarLoginTest() {
       courierClient.create(courier);
       courierId = courierClient.login(new CourierCredentials(courier.login, courier.password));
       assert courierId > 0;
   }

    @Test
    @DisplayName("Ошибка при попытке автоиизации без логина")
    public void courierCanNotLoginWithoutLoginTest() {
        courierClient.create(courier);
        courierClient.loginBodyResponse(new CourierCredentials(null, courier.password));
        courierClient.response.then().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Ошибка при попытке автоиизации без пароля")
    public void courierCanNotLoginWithoutPasswordTest() {
        courierClient.create(courier);
        courierClient.loginBodyResponse(new CourierCredentials(courier.login , null));
        courierClient.response.then().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Ошибка при попытке автоиизации c неверным логином")
    public void courierCanNotLoginWithInvalidLoginTest() {
        courierClient.create(courier);
        String innvalidLogin = "qwqdld";
        courierClient.loginBodyResponse(new CourierCredentials(innvalidLogin, courier.password));
        courierClient.response.then().statusCode(404).assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Ошибка при попытке автоиизации c неверным паролем")
    public void courierCanNotLoginWithInvalidPasswordTest() {
        courierClient.create(courier);
        String innvalidPassword = "qwqdld";
        courierClient.loginBodyResponse(new CourierCredentials(courier.login, innvalidPassword));
        courierClient.response.then().statusCode(404).assertThat().body("message", equalTo("Учетная запись не найдена"));
    }

}
