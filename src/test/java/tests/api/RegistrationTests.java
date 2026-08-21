package tests.api;

import data.TestData;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
import models.login.RequiredFieldsResponseModel;
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
@Epic("Разработка ручки для регистрации пользователя")
@DisplayName("Проверка регистрации пользователя")
public class RegistrationTests extends TestBase {
    TestData testData = new TestData();

    @Test
    @Story("MVP. Ручка регистрации")
    @DisplayName("Успешная регистрация")
    @Tag("API")
    public void successfulRegistration() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(testData.username, testData.password));

        checkSuccessfulRegistrationResponse(registrationResponse, testData.username);
    }

    @Test
    @Story("Ручка регистрации. Доработка валидаций")
    @DisplayName("Регистрация существующего пользователя")
    @Tag("API")
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
    @Story("Ручка регистрации. Доработка валидаций")
    @DisplayName("Регистрация с незаполненным полем \"Username\"")
    @Tag("API")
    public void emptyUsernameFieldRegistration() {
        RequiredFieldsResponseModel emptyUserResponseModel = api.user.emptyUsernameFieldRegistration
                (new RegistrationBodyModel("", testData.password));
        step("Проверка текста ошибки", () -> {
            String actualError = emptyUserResponseModel.username().get(0);
            assertThat(actualError).isEqualTo(EMPTY_FIELD_ERROR);
        });
    }

    @Test
    @Story("Ручка регистрации. Доработка валидаций")
    @DisplayName("Ошибка при регистрации с именем длиной 151 символ")
    @Tag("API")
    public void validationLengthCharactersRegistrationInUsername() {
        RequiredFieldsResponseModel longUserResponseModel = api.user.validationLengthCharactersRegistrationInUsername
                (new RegistrationBodyModel(testData.longUsername, testData.password));
        step("Проверка текста ошибки", () -> {
            String actualError = longUserResponseModel.username().get(0);
            assertThat(actualError).isEqualTo(MORE_THAN_150_CHARACTERS_ERROR);
        });
    }

    @Test
    @Story("Ручка регистрации. Доработка валидаций")
    @DisplayName("Проверка граничного значения поля \"Username\" с вводом 150 символов при регистрации")
    @Tag("API")
    public void maxCharactersRegistrationInUsername() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.maxCharactersRegistrationInUsername
                (new RegistrationBodyModel(testData.usernameIsMaxCharacters, testData.password));

        checkSuccessfulRegistrationResponse(registrationResponse, testData.usernameIsMaxCharacters);
    }
}