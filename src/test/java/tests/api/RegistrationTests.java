package tests.api;

import data.TestData;
import models.login.RequiredFieldsResponseModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static data.TestData.*;

@DisplayName("Проверка регистрации пользователя")
public class RegistrationTests extends TestBase {
    TestData testData = new TestData();

    @Test
    @DisplayName("Успешная регистрация")
    public void successfulRegistration() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(testData.username, testData.password));

        step("Проверка данных пользователя после регистрации", () -> {
            assertThat(registrationResponse.username()).isEqualTo(testData.username);
            assertThat(registrationResponse.firstName()).isEqualTo("");
            assertThat(registrationResponse.lastName()).isEqualTo("");
            assertThat(registrationResponse.email()).isEqualTo("");
            assertThat(registrationResponse.remoteAddr()).matches(REGISTRATION_IP_REGEXP);
        });
    }

    @Test
    @DisplayName("Регистрация существующего пользователя")
    public void existingUserInvalidRegistration() {
        SuccessfulRegistrationResponseModel firstRegistrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(testData.username, testData.password));

        step("Проверка данных пользователя после регистрации", () -> {
            assertThat(firstRegistrationResponse.username()).isEqualTo(testData.username);
        });

        RequiredFieldsResponseModel secondRegistrationResponse = api.user.existingUserInvalidRegistration(
                (new RegistrationBodyModel(testData.username, testData.password)));

        step("Проверка текста ошибки", () -> {
            String actualError = secondRegistrationResponse.username().get(0);
            assertThat(actualError).isEqualTo(REGISTRATION_EXISTING_USER_ERROR);
        });
    }

    @Test
    @DisplayName("Регистрация с незаполненным полем \"Username\"")
    public void emptyUsernameFieldRegistration() {
        RequiredFieldsResponseModel emptyUserResponseModel = api.user.emptyUsernameFieldRegistration
                (new RegistrationBodyModel("", testData.password));
        step("Проверка текста ошибки", () -> {
            String actualError = emptyUserResponseModel.username().get(0);
            assertThat(actualError).isEqualTo(EMPTY_FIELD_ERROR);
        });
    }

    @Test
    @DisplayName("Успешная валидация поля \"Username\" с вводом 151 символа")
    public void validationLengthCharactersRegistrationInUsername() {
        RequiredFieldsResponseModel longUserResponseModel = api.user.validationLengthCharactersRegistrationInUsername
                (new RegistrationBodyModel(testData.longUsername, testData.password));
        step("Проверка текста ошибки", () -> {
            String actualError = longUserResponseModel.username().get(0);
            assertThat(actualError).isEqualTo(MORE_THAN_150_CHARACTERS_ERROR);
        });
    }

    @Test
    @DisplayName("Проверка граничного значения поля \"Username\" с вводом 150 символов при регистрации")
    public void maxCharactersRegistrationInUsername() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.maxCharactersRegistrationInUsername
                (new RegistrationBodyModel(testData.usernameIsMaxCharacters, testData.password));

        step("Проверка данных пользователя после регистрации", () -> {
            assertThat(registrationResponse.username()).isEqualTo(testData.usernameIsMaxCharacters);
            assertThat(registrationResponse.firstName()).isEqualTo("");
            assertThat(registrationResponse.lastName()).isEqualTo("");
            assertThat(registrationResponse.email()).isEqualTo("");
            assertThat(registrationResponse.remoteAddr()).matches(REGISTRATION_IP_REGEXP);
        });
    }
}