package tests.api;

import data.TestData;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import models.login.InvalidCredentialsResponseModel;
import models.login.LoginBodyModel;
import models.login.RequiredFieldsResponseModel;
import models.login.SuccessfulLoginResponseModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static data.TestData.*;

@Owner("VSSemenov")
@Epic("Разработка ручки для авторизации пользователя")
@DisplayName("Проверка авторизации пользователя")
public class LoginTests extends TestBase {
    TestData testData = new TestData();


    @Test
    @Tag("API")
    @Story("MVP.Ручка логина")
    @DisplayName("Успешная авторизация")
    public void successfulLogin() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(testData.username, testData.password));

        checkSuccessfulRegistrationResponse(registrationResponse, testData.username);

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
    @Tag("API")
    @Story("Ручка логина. Валидации")
    @DisplayName("Авторизация с неверным паролем")
    public void invalidCredentialLogin() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(testData.username, testData.password));

        checkSuccessfulRegistrationResponse(registrationResponse, testData.username);

        InvalidCredentialsResponseModel loginResponse = api.auth.invalidPasswordFieldAuth
                (new LoginBodyModel(testData.username, testData.password + "1"));

        step("Проверка сообщения об ошибке при неверных учетных данных", () -> {
            String actualDetail = loginResponse.detail();
            assertThat(actualDetail).isEqualTo(LOGIN_INVALID_CREDENTIALS_ERROR);
        });
    }

    @Test
    @Tag("API")
    @Story("Ручка логина. Валидации")
    @DisplayName("Авторизация с незаполненным полем \"Username\"")
    public void emptyUsernameFieldLogin() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(testData.username, testData.password));

        checkSuccessfulRegistrationResponse(registrationResponse, testData.username);

        RequiredFieldsResponseModel emptyUsernameLoginResponse = api.auth.emptyUsernameFieldAuth
                (new LoginBodyModel("", testData.password));

        step("Проверка текста ошибки", () -> {
            String actualUsername = emptyUsernameLoginResponse.username().get(0);
            assertThat(actualUsername).isEqualTo(EMPTY_FIELD_ERROR);
        });
    }
}