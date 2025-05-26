package courier.loginTests;
import io.qameta.allure.Description;
import base.LoginCourierTestBase;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.courier.CourierLoginResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LoginCourierTest extends LoginCourierTestBase {
    @Test
    @DisplayName("Проверка логина курьера")
    @Description("Позитивная проверка возможности курьера залогиниться")
    public void courierCanLoginSuccessfullyTest() {
        Response courierLoginResponse = performLogin();
        verifyResponseStatus(courierLoginResponse);
        verifyCourierId(courierLoginResponse);
    }
    @Step("Выполнение логина курьера")
    private Response performLogin() {
        return this.client.login(courierLogin);
    }

    @Step("Проверка статуса ответа")
    private void verifyResponseStatus(Response response) {
        assertEquals("Неверный статус-код", HttpStatus.SC_OK, response.statusCode());
    }

    @Step("Проверка, что ID курьера не равен 0")
    private void verifyCourierId(Response response) {
        assertNotEquals("ID не должен быть 0", 0, response.as(CourierLoginResponse.class).getId());
    }
}
