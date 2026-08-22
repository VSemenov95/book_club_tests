package tests;

import api.ApiClient;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import models.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static data.TestData.REGISTRATION_IP_REGEXP;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class TestBase {
    public static final ApiClient api = new ApiClient();


    @BeforeAll
    public static void setUp() throws MalformedURLException {
        RestAssured.baseURI = "https://book-club.qa.guru";
        RestAssured.basePath = "/api/v1";
        ChromeOptions options = new ChromeOptions();
        options.setCapability("browserVersion", "149.0");
        options.setCapability("selenoid:options", new HashMap<String, Object>() {{
            put("name", "Manual session");
            put("sessionTimeout", "60m");
            put("screenResolution", "1920x1080x24");
            put("timeZone", "UTC");
            put("labels", new HashMap<String, Object>() {{
                put("manual", "true");
            }});
            put("enableVNC", true);
            put("enableVideo", true);
            put("enableHAR", false);
            put("enableLog", true);
        }});
        RemoteWebDriver driver = new RemoteWebDriver(new URL("https://qa_engineer:-aAb_-4gs53FD@selenoid.qa.guru/wd/hub"), options);

    }

    @BeforeEach
    public void setup() {
        SelenideLogger.addListener("allure", new AllureSelenide());
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