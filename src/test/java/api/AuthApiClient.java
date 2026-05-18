package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.login.InvalidCredentialsResponseModel;
import models.login.LoginBodyModel;
import models.login.RequiredFieldsResponseModel;
import models.login.SuccessfulLoginResponseModel;
import models.logout.FieldNullResponseModel;
import models.logout.LogoutBodyModel;
import models.logout.UnauthorizedResponseModel;

import static io.restassured.RestAssured.given;
import static specs.BaseSpec.requestSpec;
import static specs.login.LoginSpec.*;
import static specs.logout.LogoutSpec.*;

public class AuthApiClient {
    @Step("Успешная авторизация пользователя")
    public SuccessfulLoginResponseModel successUserAuth(LoginBodyModel loginData) {
        return given(requestSpec)
                .body(loginData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(successLoginResponseSpec)
                .extract()
                .as(SuccessfulLoginResponseModel.class);
    }

    @Step("Авторизация с неверным паролем")
    public InvalidCredentialsResponseModel invalidPasswordFieldAuth(LoginBodyModel loginData) {
        return given(requestSpec)
                .body(loginData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(invalidCredentialLoginResponseSpec)
                .extract()
                .as(InvalidCredentialsResponseModel.class);
    }

    @Step("Авторизация с незаполненным полем username")
    public RequiredFieldsResponseModel emptyUsernameFieldAuth(LoginBodyModel loginData) {
        return given(requestSpec)
                .body(loginData)
                .when()
                .post("/auth/token/")
                .then()
                .spec(emptyFieldLoginResponseSpec)
                .extract()
                .as(RequiredFieldsResponseModel.class);
    }

    @Step("Успешный выход пользователя")
    public Response successUserLogout(LogoutBodyModel logoutData) {
        return given(requestSpec)
                .body(logoutData)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(successLogoutResponseSpec)
                .extract()
                .response();
    }

    @Step("Выход пользователя с пустым refresh")
    public FieldNullResponseModel refreshIsNullUser(LogoutBodyModel logoutData) {
        return given(requestSpec)
                .body(logoutData)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(nullFieldLogoutResponseSpec)
                .extract()
                .as(FieldNullResponseModel.class);
    }

    @Step("Выход пользователя с невалидным refresh")
    public UnauthorizedResponseModel invalidRefreshLogout(LogoutBodyModel logoutData) {
        return given(requestSpec)
                .body(logoutData)
                .when()
                .post("/auth/logout/")
                .then()
                .spec(unauthorizedLogoutResponseSpec)
                .extract().as(UnauthorizedResponseModel.class);
    }
}

