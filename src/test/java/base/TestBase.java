package base;

import clients.CourierClient;
import constants.UserData;
import io.restassured.response.Response;
import models.courier.CourierLoginRequest;
import models.courier.CourierLoginResponse;
import models.courier.CourierRequest;
import org.junit.After;
import org.junit.Before;

import static org.apache.http.HttpStatus.SC_OK;

public class TestBase {
    protected CourierClient client;
    protected int courierId;
    protected CourierRequest courier;
    protected CourierLoginRequest courierLogin;

    @Before
    public void setUp() {
        this.client = new CourierClient();
        this.courierId = 0;
        this.courier = new CourierRequest(UserData.LOGIN, UserData.PASSWORD, UserData.FIRST_NAME);
        this.courierLogin = new CourierLoginRequest(UserData.LOGIN, UserData.PASSWORD);
    }

    @After
    public void tearDown() {
        try {
            this.courierId = getExistingCourierId(this.courierLogin);
        } catch (Exception e) {
            System.out.println("Не удалось получить идентификатор курьера: " + e);
        } finally {
            this.client.delete(this.courierId);
        }
    }

    protected int getExistingCourierId(CourierLoginRequest courierLogin) {
        Response courierLoginResponse = this.client.login(courierLogin);
        courierLoginResponse.then().statusCode(SC_OK);

        return courierLoginResponse.as(CourierLoginResponse.class).getId();
    }
}
