package tests;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import api.ApiClient;
import models.registration.SuccessfulRegistrationResponseModel;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static data.TestData.REGISTRATION_IP_REGEXP;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class TestBase {
    public static final ApiClient api = new ApiClient();


    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://book-club.qa.guru";
        RestAssured.basePath = "/api/v1";
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.browserVersion = System.getProperty("browserVersion", "127");
        Configuration.remote = System.getProperty("https://user1:1234@selenoid.autotests.cloud/wd/hub");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of("enableVNC", true, "enableVideo", true));
        Configuration.browserCapabilities = capabilities;
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