package courier.loginTests;
import io.qameta.allure.Description;
import base.LoginCourierTestBase;
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
        Response courierLoginResponse = this.client.login(courierLogin);
        assertEquals("Неверный статус-код", HttpStatus.SC_OK, courierLoginResponse.statusCode());
        assertNotEquals("ID не должен быть 0", 0, courierLoginResponse.as(CourierLoginResponse.class).getId());
    }
}
