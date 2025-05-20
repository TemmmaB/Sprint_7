package clients;

import io.restassured.response.Response;
import models.courier.CourierLoginRequest;
import models.courier.CourierRequest;

import static io.restassured.RestAssured.given;

public class CourierClient extends ClientBase{
    private static final String API_V1_COURIER = "/api/v1/courier";
    private static final String API_V1_COURIER_LOGIN = "/api/v1/courier/login";

    public Response create(CourierRequest courier) {
        return given()
                .baseUri(QA_SCOOTER_PRAKTIKUM_SERVICE)
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(API_V1_COURIER);
    }

    public Response login(CourierLoginRequest courierLogin) {
        return given()
                .baseUri(QA_SCOOTER_PRAKTIKUM_SERVICE)
                .header("Content-type", "application/json")
                .body(courierLogin)
                .when()
                .post(API_V1_COURIER_LOGIN);
    }

    public Response delete(int id) {
        return given()
                .baseUri(QA_SCOOTER_PRAKTIKUM_SERVICE)
                .header("Content-type", "application/json")
                .when()
                .delete(API_V1_COURIER + "/" + id);
    }
}
