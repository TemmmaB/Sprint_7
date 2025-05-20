package clients;

import io.restassured.response.Response;
import models.orders.OrderRequest;

import static io.restassured.RestAssured.given;

public class OrderClient extends ClientBase{
    private static final String API_V1_ORDERS = "/api/v1/orders";

    public Response create(OrderRequest order) {
        return given()
                .baseUri(QA_SCOOTER_PRAKTIKUM_SERVICE)
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(API_V1_ORDERS);
    }

    public Response getList() {
        return given()
                .baseUri(QA_SCOOTER_PRAKTIKUM_SERVICE)
                .header("Content-type", "application/json")
                .when()
                .get(API_V1_ORDERS);
    }
}
