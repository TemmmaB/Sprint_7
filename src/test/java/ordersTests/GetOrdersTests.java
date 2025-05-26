package ordersTests;

import clients.OrderClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;

import static org.junit.Assert.assertEquals;

public class GetOrdersTests {
    @Test
    @DisplayName("Проверка получения списка заказов")
    @Description("Позитивная проверка возможности получить заполненный список заказов")
    public void getOrdersListTest() {
        OrderClient client = new OrderClient();
        Response ordersResponse = performGetOrders(client);
        verifyResponseStatus(ordersResponse);
        verifyOrdersList(ordersResponse);
    }
    @Step("Выполнение запроса на получение списка заказов")
    private Response performGetOrders(OrderClient client) {
        return client.getList();
    }

    @Step("Проверка статуса ответа на запрос списка заказов")
    private void verifyResponseStatus(Response response) {
        assertEquals("Неверный статус-код", HttpStatus.SC_OK, response.statusCode());
    }

    @Step("Проверка, что список заказов не пуст")
    private void verifyOrdersList(Response response) {
        response
                .then()
                .assertThat()
                .body("orders", not(empty()))
                .body("orders.size()", greaterThan(0));
    }
}
