package courier.Test;

import base.TestBase;
import constants.ErrorMessages;
import constants.UserData;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import models.courier.CourierRequest;
import models.courier.CourierResponse;
import models.errors.ErrorResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierTests extends TestBase {
    @Test
    @DisplayName("Проверка создания курьера")
    @Description("Позитивная проверка возможности создания курьера")
    public void courierCanBeCreatedSuccessfullyTest() {
        Response courierResponse = this.client.create(this.courier);
        assertEquals("Неверный статус-код", HttpStatus.SC_CREATED, courierResponse.statusCode());
        assertTrue("Неверное значение поля 'ok'", courierResponse.as(CourierResponse.class).isOk());
    }

    @Test
    @DisplayName("Проверка невозможности создания 2х одинаковых курьеров")
    @Description("Проверка невозможности создания 2х курьеров с одинаковыми данными")
    public void unableToCreateTwoSameCouriersTest() {
        Response courierResponse = this.client.create(this.courier);
        courierResponse.then().statusCode(HttpStatus.SC_CREATED);

        Response sameCourierResponse = this.client.create(this.courier);
        assertEquals("Неверный статус-код", HttpStatus.SC_CONFLICT, sameCourierResponse.statusCode());
        assertEquals("Неверное значение поля 'message'", ErrorMessages.CREATE_ACCOUNT_ALREADY_USED, sameCourierResponse.as(ErrorResponse.class).getMessage());
    }

    @Test
    @DisplayName("Проверка невозможности создания курьеров с одинаковым логином")
    @Description("Проверка невозможности создания 2х курьеров с одинаковым логином")
    public void unableToCreateCourierWithSameLoginTest() {
        Response courierResponse = this.client.create(this.courier);
        courierResponse.then().statusCode(HttpStatus.SC_CREATED);

        CourierRequest sameLoginCourier = new CourierRequest(UserData.LOGIN, UserData.PASSWORD_UPDATED, UserData.FIRST_NAME_UPDATED);
        Response sameLoginCourierResponse = this.client.create(sameLoginCourier);
        assertEquals("Неверный статус-код", HttpStatus.SC_CONFLICT, sameLoginCourierResponse.statusCode());
        assertEquals("Неверное значение поля 'message'", ErrorMessages.CREATE_ACCOUNT_ALREADY_USED, sameLoginCourierResponse.as(ErrorResponse.class).getMessage());
    }
}
