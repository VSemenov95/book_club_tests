package tests.api;

import data.TestData;
import models.login.InvalidCredentialsResponseModel;
import models.login.LoginBodyModel;
import models.login.RequiredFieldsResponseModel;
import models.login.SuccessfulLoginResponseModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static data.TestData.*;

@DisplayName("Проверка авторизации пользователя")
public class LoginTests extends TestBase {
    TestData testData = new TestData();

    @Test
    @DisplayName("Успешная авторизация")
    public void successfulLogin() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(testData.username, testData.password));

        step("Проверка ответа на регистрацию пользователя", () -> {
            assertThat(registrationResponse.username()).isEqualTo(testData.username);
            assertThat(registrationResponse.firstName()).isEqualTo("");
            assertThat(registrationResponse.lastName()).isEqualTo("");
            assertThat(registrationResponse.email()).isEqualTo("");
            assertThat(registrationResponse.remoteAddr()).matches(REGISTRATION_IP_REGEXP);
        });

        SuccessfulLoginResponseModel loginResponse = api.auth.successUserAuth(new LoginBodyModel
                (testData.username, testData.password));

        step("Проверка refresh и access", () -> {
            String expectedTokenPath = LOGIN_TOKEN_PREFIX;
            String actualAccess = loginResponse.access();
            String actualRefresh = loginResponse.refresh();

            assertThat(actualAccess).startsWith(expectedTokenPath);
            assertThat(actualRefresh).startsWith(expectedTokenPath);
            assertThat(actualAccess).isNotEqualTo(actualRefresh);
        });
    }

    @Test
    @DisplayName("Авторизация с неверным паролем")
    public void invalidCredentialLogin() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(testData.username, testData.password));

        step("Проверка данных пользователя после регистрации", () -> {
            assertThat(registrationResponse.username()).isEqualTo(testData.username);
            assertThat(registrationResponse.firstName()).isEqualTo("");
            assertThat(registrationResponse.lastName()).isEqualTo("");
            assertThat(registrationResponse.email()).isEqualTo("");
            assertThat(registrationResponse.remoteAddr()).matches(REGISTRATION_IP_REGEXP);
        });

        InvalidCredentialsResponseModel loginResponse = api.auth.invalidPasswordFieldAuth
                (new LoginBodyModel(testData.username, testData.password + "1"));

        step("Проверка сообщения об ошибке при неверных учетных данных", () -> {
            String actualDetail = loginResponse.detail();
            assertThat(actualDetail).isEqualTo(LOGIN_INVALID_CREDENTIALS_ERROR);
        });
    }

    @Test
    @DisplayName("Авторизация с незаполненным полем \"Username\"")
    public void emptyUsernameFieldLogin() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(testData.username, testData.password));

        step("Проверка данных после регистрации", () -> {
            assertThat(registrationResponse.username()).isEqualTo(testData.username);
            assertThat(registrationResponse.firstName()).isEqualTo("");
            assertThat(registrationResponse.lastName()).isEqualTo("");
            assertThat(registrationResponse.email()).isEqualTo("");
            assertThat(registrationResponse.remoteAddr()).matches(REGISTRATION_IP_REGEXP);
        });

        RequiredFieldsResponseModel emptyUsernameLoginResponse = api.auth.emptyUsernameFieldAuth
                (new LoginBodyModel("", testData.password));

        step("Проверка текста ошибки", () -> {
            String actualUsername = emptyUsernameLoginResponse.username().get(0);
            assertThat(actualUsername).isEqualTo(EMPTY_FIELD_ERROR);
        });
    }
}