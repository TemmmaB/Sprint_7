package courier.Test;

import clients.CourierClient;
import constants.UserData;
import io.qameta.allure.Description;
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
        CourierRequest courier = new CourierRequest(this.login, this.password, this.firstName);
        CourierClient client = new CourierClient();

        Response courierResponse = client.create(courier);
        assertEquals("Неверный статус-код", this.expectedStatusCode, courierResponse.statusCode());
    }
}
