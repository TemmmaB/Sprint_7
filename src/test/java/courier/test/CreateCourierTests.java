package courier.test;

import base.TestBase;
import constants.ErrorMessages;
import constants.UserData;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
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
        Response courierResponse = performCourierCreation(this.courier);
        verifyCreationResponse(courierResponse);
    }

    @Test
    @DisplayName("Проверка невозможности создания 2х одинаковых курьеров")
    @Description("Проверка невозможности создания 2х курьеров с одинаковыми данными")
    public void unableToCreateTwoSameCouriersTest() {
        Response courierResponse = performCourierCreation(this.courier);
        verifyCreationResponse(courierResponse);

        Response sameCourierResponse = performCourierCreation(this.courier);
        verifyConflictResponse(sameCourierResponse);
    }

    @Test
    @DisplayName("Проверка невозможности создания курьеров с одинаковым логином")
    @Description("Проверка невозможности создания 2х курьеров с одинаковым логином")
    public void unableToCreateCourierWithSameLoginTest() {
        Response courierResponse = performCourierCreation(this.courier);
        verifyCreationResponse(courierResponse);

        CourierRequest sameLoginCourier = new CourierRequest(UserData.LOGIN, UserData.PASSWORD_UPDATED, UserData.FIRST_NAME_UPDATED);
        Response sameLoginCourierResponse = performCourierCreation(sameLoginCourier);
        verifyConflictResponse(sameLoginCourierResponse);
    }
    @Step("Выполнение запроса на создание курьера")
    private Response performCourierCreation(CourierRequest courier) {
        return this.client.create(courier);
    }

    @Step("Проверка ответа на успешное создание курьера")
    private void verifyCreationResponse(Response response) {
        assertEquals("Неверный статус-код", HttpStatus.SC_CREATED, response.statusCode());
        assertTrue("Неверное значение поля 'ok'", response.as(CourierResponse.class).isOk());
    }

    @Step("Проверка ответа на конфликт при создании курьера")
    private void verifyConflictResponse(Response response) {
        assertEquals("Неверный статус-код", HttpStatus.SC_CONFLICT, response.statusCode());
        assertEquals("Неверное значение поля 'message'", ErrorMessages.CREATE_ACCOUNT_ALREADY_USED, response.as(ErrorResponse.class).getMessage());
    }
}
