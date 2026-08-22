package tests;

import api.ApiClient;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import models.registration.SuccessfulRegistrationResponseModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.WebDriverRunner.hasWebDriverStarted;
import static data.TestData.REGISTRATION_IP_REGEXP;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

public class TestBase {
    public static final ApiClient api = new ApiClient();


    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://book-club.qa.guru";
        RestAssured.basePath = "/api/v1";
        Configuration.remote = System.getProperty("https://qa_engineer:-aAb_-4gs53FD@selenoid.qa.guru/wd/hub");
        Configuration.browser = System.getProperty("browser", "chrome").toLowerCase();
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.browserVersion = System.getProperty("browserVersion", "149");
        Configuration.baseUrl = System.getProperty("baseUrl", "https://book-club.qa.guru");
        Configuration.timeout = 15000;
        Configuration.pageLoadTimeout = 60000;
        Configuration.pageLoadStrategy = "eager";

    String browserVersion = System.getProperty("browserVersion");
        if (browserVersion != null && !browserVersion.isBlank()) {
        Configuration.browserVersion = browserVersion;
    }

    String remoteUrl = System.getProperty("remoteUrl");
        if (remoteUrl == null || remoteUrl.isBlank()) {
        String login = System.getProperty("remoteBrowserUrlLogin");
        String password = System.getProperty("remoteBrowserUrlPassword");
        if (login != null && password != null && !login.isBlank() && !password.isBlank()) {
            remoteUrl = "https://" + login + ":" + password + "@" +
                    System.getProperty("remoteBrowserUrl", "selenoid.autotests.cloud/wd/hub");
        }
    }

    boolean remote = remoteUrl != null && !remoteUrl.isBlank();
    Configuration.headless = !remote && Boolean.parseBoolean(System.getProperty("headless", "false"));

        if (remote) {
        Configuration.remote = remoteUrl;
        Configuration.browserCapabilities = selenoidCapabilities();
    }
}

private static MutableCapabilities selenoidCapabilities() {
    Map<String, Object> selenoidOptions = Map.of(
            "enableVNC", true,
            "enableVideo", true
    );
    if ("chrome".equalsIgnoreCase(Configuration.browser)) {
        ChromeOptions options = new ChromeOptions();
        options.setAcceptInsecureCerts(true);
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
        options.setCapability("selenoid:options", selenoidOptions);
        return options;
    }
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("pageLoadStrategy", "eager");
    capabilities.setCapability("selenoid:options", selenoidOptions);
    return capabilities;
}

    @BeforeEach
    public void setup() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterEach
    void addAttachments() {
        if (!hasWebDriverStarted()) {
            return;
        }
        try {
            Attach.screenshotAs("Last screenshot");
            Attach.pageSource();
            Attach.browserConsoleLogs();
            if (Configuration.remote != null) {
                Attach.addVideo();
            }
        } catch (Exception ignored) {
        } finally {
            closeWebDriver();
        }
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