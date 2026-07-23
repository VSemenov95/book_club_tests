package tests.api;

import data.TestData;
import models.login.LoginBodyModel;
import models.login.RequiredFieldsResponseModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import models.update.SuccessfulUpdateResponseModel;
import models.update.UpdateBodyModel;
import models.update.UpdateEmailBodyModel;
import models.update.UpdateWithoutUsernameBodyModel;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static data.TestData.FIELD_IS_REQUIRED;
import static data.TestData.REGISTRATION_IP_REGEXP;

@DisplayName("Проверка обновления данных пользователя")
public class UpdateUserTests extends TestBase {
    TestData testData = new TestData();

    @Test
    @DisplayName("Успешное обновление данных пользователя")
    public void successfulUpdateUserDataTest() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(testData.username, testData.password));

        checkSuccessfulRegistrationResponse(registrationResponse, testData.username);

        String access = api.auth.successUserAuth(new LoginBodyModel
                (testData.username, testData.password)).access();

        SuccessfulUpdateResponseModel updateResponse = api.user.updateUserData
                (new UpdateBodyModel(testData.username, testData.firstName, testData.lastName, testData.email),access);

        step("Проверка обновления данных пользователя", () -> {
            String registrationIp = registrationResponse.remoteAddr();
            assertThat(updateResponse.id()).isEqualTo(registrationResponse.id());
            assertThat(updateResponse.username()).isEqualTo(testData.username);
            assertThat(updateResponse.firstName()).isEqualTo(testData.firstName);
            assertThat(updateResponse.lastName()).isEqualTo(testData.lastName);
            assertThat(updateResponse.email()).isEqualTo(testData.email);
            assertThat(registrationResponse.remoteAddr()).matches(REGISTRATION_IP_REGEXP);
            assertThat(registrationIp).isEqualTo(updateResponse.remoteAddr());
        });
    }

    @Test
    @DisplayName("Успешное добавление поля \"email\"")
    public void successfulEmailUpdate() {
SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(testData.username, testData.password));

        checkSuccessfulRegistrationResponse(registrationResponse, testData.username);

        String access = api.auth.successUserAuth(new LoginBodyModel
                (testData.username, testData.password)).access();

        SuccessfulUpdateResponseModel updateResponse = api.user.updateEmailData(new UpdateEmailBodyModel(testData.email),access);

        step("Проверка обновления данных пользователя", () -> {
            assertThat(updateResponse.id()).isEqualTo(registrationResponse.id());
            assertThat(updateResponse.username()).isEqualTo(testData.username);
            assertThat(updateResponse.firstName()).isEqualTo("");
            assertThat(updateResponse.lastName()).isEqualTo("");
            assertThat(updateResponse.email()).isEqualTo(testData.email);
            assertThat(registrationResponse.remoteAddr()).matches(REGISTRATION_IP_REGEXP);
            assertThat(registrationResponse.remoteAddr()).isEqualTo(updateResponse.remoteAddr());
        });
    }

    @Test
    @DisplayName("Проверка валидации обязательности поля \"username\"")
    public void usernameFieldRequiredUpdate() {
        SuccessfulRegistrationResponseModel registrationResponse = api.user.userRegistration
                (new RegistrationBodyModel(testData.username, testData.password));

        checkSuccessfulRegistrationResponse(registrationResponse, testData.username);

        String access = api.auth.successUserAuth(new LoginBodyModel
                (testData.username, testData.password)).access();

        RequiredFieldsResponseModel updateWithoutUsernameResponse = api.user.usernameFieldEmptyUpdate
                (new UpdateWithoutUsernameBodyModel(testData.firstName, testData.lastName, testData.email),access);

        step("Проверка текста ошибки", () -> {
            String actualUsernameError = updateWithoutUsernameResponse.username().get(0);
            assertThat(actualUsernameError).isEqualTo(FIELD_IS_REQUIRED);
        });
    }
}