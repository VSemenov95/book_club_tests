package api;

import io.qameta.allure.Step;
import models.login.RequiredFieldsResponseModel;
import models.registration.RegistrationBodyModel;
import models.registration.SuccessfulRegistrationResponseModel;
import models.update.SuccessfulUpdateResponseModel;
import models.update.UpdateBodyModel;
import models.update.UpdateEmailBodyModel;
import models.update.UpdateWithoutUsernameBodyModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.requestSpec;
import static specs.registration.RegistrationSpec.negativeRegistrationResponseSpec;
import static specs.registration.RegistrationSpec.successRegistrationResponseSpec;
import static specs.update.UpdateSpec.fieldRequiredResponseSpec;
import static specs.update.UpdateSpec.successUpdateResponseSpec;

public class UserApiClient {
    @Step("Регистрация пользователя")
    public SuccessfulRegistrationResponseModel userRegistration(RegistrationBodyModel registrationData) {
        return given(requestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(successRegistrationResponseSpec)
                .extract()
                .as(SuccessfulRegistrationResponseModel.class);
    }

    @Step("Регистрация существующего пользователя")
    public RequiredFieldsResponseModel existingUserInvalidRegistration(RegistrationBodyModel registrationData) {
        return given(requestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(negativeRegistrationResponseSpec)
                .extract()
                .as(RequiredFieldsResponseModel.class);
    }

    @Step("Регистрация пользователя с заполненным полем \"Username\" длиной 151 символ")
    public RequiredFieldsResponseModel validationLengthCharactersRegistrationInUsername(RegistrationBodyModel registrationData) {
        return given(requestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(negativeRegistrationResponseSpec)
                .extract()
                .as(RequiredFieldsResponseModel.class);
    }

    @Step("Успешная регистрация пользователя с длиной поля \"Username\" длиной 150 символов")
    public SuccessfulRegistrationResponseModel maxCharactersRegistrationInUsername(RegistrationBodyModel registrationData) {
        return given(requestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(successRegistrationResponseSpec)
                .extract()
                .as(SuccessfulRegistrationResponseModel.class);
    }

    @Step("Регистрация с незаполненным полем \"Username\"")
    public RequiredFieldsResponseModel emptyUsernameFieldRegistration(RegistrationBodyModel registrationData) {
        return given(requestSpec)
                .body(registrationData)
                .when()
                .post("/users/register/")
                .then()
                .spec(negativeRegistrationResponseSpec)
                .extract()
                .as(RequiredFieldsResponseModel.class);
    }

    @Step("Успешное обновление данных пользователя")
    public SuccessfulUpdateResponseModel updateUserData(UpdateBodyModel updateData, String actualAccess) {
        return given(requestSpec)
                .header("Authorization", "Bearer " + actualAccess)
                .body(updateData)
                .when()
                .put("/users/me/")
                .then()
                .spec(successUpdateResponseSpec)
                .extract()
                .as(SuccessfulUpdateResponseModel.class);
    }

    @Step("Добавление email пользователя")
    public SuccessfulUpdateResponseModel updateEmailData(UpdateEmailBodyModel updateData, String actualAccess) {
        return given(requestSpec)
                .header("Authorization", "Bearer " + actualAccess)
                .body(updateData)
                .when()
                .patch("/users/me/")
                .then()
                .spec(successUpdateResponseSpec)
                .extract()
                .as(SuccessfulUpdateResponseModel.class);
    }

    @Step("Обновление данных с незаполненным полем \"Username\"")
    public RequiredFieldsResponseModel usernameFieldEmptyUpdate(UpdateWithoutUsernameBodyModel updateWithoutUsernameData, String actualAccess) {
        return given(requestSpec)
                .header("Authorization", "Bearer " + actualAccess)
                .body(updateWithoutUsernameData)
                .when()
                .put("/users/me/")
                .then()
                .spec(fieldRequiredResponseSpec)
                .extract()
                .as(RequiredFieldsResponseModel.class);
    }
}
