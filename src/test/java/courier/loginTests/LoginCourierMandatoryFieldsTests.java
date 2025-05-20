package courier.loginTests;

import base.LoginCourierTestBase;
import constants.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.courier.CourierLoginRequest;
import org.apache.http.HttpStatus;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LoginCourierMandatoryFieldsTests extends LoginCourierTestBase {
    private String login;
    private String password;
    private int expectedStatusCode;

    public LoginCourierMandatoryFieldsTests(String login, String password, int expectedStatusCode) {
        this.login = login;
        this.password = password;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters
    public static Object[] getLoginCourierData() {
        return new Object[][]{
                {null, null, HttpStatus.SC_BAD_REQUEST},
                {UserData.LOGIN, null, HttpStatus.SC_BAD_REQUEST},
                {null, UserData.PASSWORD, HttpStatus.SC_BAD_REQUEST},
                {UserData.LOGIN, UserData.PASSWORD_UPDATED, HttpStatus.SC_NOT_FOUND},
                {UserData.FIRST_NAME, UserData.PASSWORD, HttpStatus.SC_NOT_FOUND},
        };
    }

    @Test
    @DisplayName("Проверка создания логина курьера без обязательных полей")
    @Description("Проверка невозможности логина курьера без обязательных полей")
    public void allFieldsShouldBeFilledInToLoginCourierTest() {
        CourierLoginRequest courierLoginInvalid = new CourierLoginRequest(this.login, this.password);
        Response courierLoginResponse = this.client.login(courierLoginInvalid);
        assertEquals("Неверный статус-код", this.expectedStatusCode, courierLoginResponse.statusCode());
    }
}
