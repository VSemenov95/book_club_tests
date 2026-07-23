package tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import api.ApiClient;
import models.registration.SuccessfulRegistrationResponseModel;

import static data.TestData.REGISTRATION_IP_REGEXP;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class TestBase {
    public static final ApiClient api = new ApiClient();


    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://book-club.qa.guru";
        RestAssured.basePath = "/api/v1";
    }

    public static void checkSuccessfulRegistrationResponse(SuccessfulRegistrationResponseModel response,
                                                           String expectedUsername) {
        step("Проверка данных пользователя после регистрации", () -> {
            assertThat(response.username()).isEqualTo(expectedUsername);
            assertThat(response.firstName()).isEqualTo("");
            assertThat(response.lastName()).isEqualTo("");
            assertThat(response.email()).isEqualTo("");
            assertThat(response.remoteAddr()).matches(REGISTRATION_IP_REGEXP);
        });
    }
}