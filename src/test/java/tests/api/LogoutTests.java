package tests.api;

import data.TestData;
import io.restassured.response.Response;
import models.login.LoginBodyModel;
import models.login.SuccessfulLoginResponseModel;
import models.logout.FieldNullResponseModel;
import models.logout.LogoutBodyModel;
import models.logout.UnauthorizedResponseModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static data.TestData.*;

@DisplayName("Проверка выхода из учетной записи")
public class LogoutTests extends TestBase {
    TestData testData = new TestData();

    @Test
    @DisplayName("Успешный выход из учетной записи")
    public void successfulLogout() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(testData.username, testData.password));

        step("Проверка данных после регистрации", () -> {
            assertThat(registrationResponse.username()).isEqualTo(testData.username);
            assertThat(registrationResponse.firstName()).isEqualTo("");
            assertThat(registrationResponse.lastName()).isEqualTo("");
            assertThat(registrationResponse.email()).isEqualTo("");
            assertThat(registrationResponse.remoteAddr()).matches(REGISTRATION_IP_REGEXP);
        });

        SuccessfulLoginResponseModel loginResponse = api.auth.successUserAuth(new LoginBodyModel
                (testData.username, testData.password));

        String actualTokenRefresh =
                step("Получение refresh-token", () -> {
                    String refreshToken = loginResponse.refresh();
                    assertThat(refreshToken).isNotBlank();
                    return refreshToken;
                });

        Response logoutResponse = api.auth.successUserLogout
                (new LogoutBodyModel(actualTokenRefresh));

        step("Проверка ответа после выхода пользователя", () -> {
            assertThat(logoutResponse.body().asString()).isEqualTo("{}");
        });
    }

    @Test
    @DisplayName("Refresh is null")
    public void transmittingRefreshIsNull() {

        FieldNullResponseModel refreshNullResponse = api.auth.refreshIsNullUser
                (new LogoutBodyModel(REFRESH_NULL));

        step("Проверка текста ошибки", () -> {
            String actualRefreshError = refreshNullResponse.refresh().get(0);
            assertThat(actualRefreshError).isEqualTo(NULL_FIELD_ERROR);
        });
    }

    @Test
    @DisplayName("Выход пользователя с невалидным refresh")
    public void passingInvalidRefresh() {

        UnauthorizedResponseModel logoutUnauthorizedResponse = api.auth.invalidRefreshLogout
                (new LogoutBodyModel(REFRESH_INVALID));

        step("Проверка текста ошибки", () -> {
            String actualDetailError = logoutUnauthorizedResponse.detail();
            String actualCodeError = logoutUnauthorizedResponse.code();
            assertThat(actualDetailError).isEqualTo(TOKEN_INVALID_ERROR);
            assertThat(actualCodeError).isEqualTo(TOKEN_NOT_VALID_ERROR);
        });
    }
}