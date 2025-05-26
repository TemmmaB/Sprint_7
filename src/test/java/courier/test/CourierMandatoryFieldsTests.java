package courier.test;

import clients.CourierClient;
import constants.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.courier.CourierRequest;
import org.apache.http.HttpStatus;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CourierMandatoryFieldsTests {
    private String login;
    private String password;
    private String firstName;
    private int expectedStatusCode;

    public CourierMandatoryFieldsTests(String login, String password, String firstName, int expectedStatusCode) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.expectedStatusCode = expectedStatusCode;
    }

    @Parameterized.Parameters
    public static Object[] getCourierData() {
        return new Object[][]{
                {null, null, null, HttpStatus.SC_BAD_REQUEST},
                {UserData.LOGIN, null, null, HttpStatus.SC_BAD_REQUEST},
                {null, UserData.PASSWORD, null, HttpStatus.SC_BAD_REQUEST},
                {null, null, UserData.FIRST_NAME, HttpStatus.SC_BAD_REQUEST},
                {UserData.LOGIN, null, UserData.FIRST_NAME, HttpStatus.SC_BAD_REQUEST},
                {null, UserData.PASSWORD, UserData.FIRST_NAME, HttpStatus.SC_BAD_REQUEST},
        };
    }

    @Test
    @DisplayName("Проверка создания курьера без обязательных полей")
    @Description("Проверка невозможности создания курьера без обязательных полей")
    public void allFieldsShouldBeFilledInToCreateCourierTest() {
        CourierRequest courier = createCourierRequest();
        Response courierResponse = performCourierCreation(courier);
        verifyResponseStatus(courierResponse);
    }
    @Step("Создание запроса на создание курьера с логином: {0}, паролем: {1} и именем: {2}")
    private CourierRequest createCourierRequest() {
        return new CourierRequest(this.login, this.password, this.firstName);
    }

    @Step("Выполнение запроса на создание курьера")
    private Response performCourierCreation(CourierRequest courier) {
        CourierClient client = new CourierClient();
        return client.create(courier);
    }

    @Step("Проверка статуса ответа")
    private void verifyResponseStatus(Response response) {
        assertEquals("Неверный статус-код", this.expectedStatusCode, response.statusCode());
    }
}
