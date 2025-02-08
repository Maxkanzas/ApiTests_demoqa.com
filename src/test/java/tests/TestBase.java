package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.WebDriverProvider;
import data.TestData;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import models.login.LoginResponseBodyModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;
import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class TestBase extends TestData {
    public static final String demoqaLogin = System.getProperty("demoqaLogin");
    public static final String demoqaPassword = System.getProperty("demoqaPassword");
    private WebDriver driver;

    @BeforeAll
    static void setupConfiguration() {
        Configuration.baseUrl = System.getProperty("webdriver.baseUrl", "https://demoqa.com");
        RestAssured.baseURI = System.getProperty("api.baseURI", "https://demoqa.com");
        RestAssured.defaultParser = Parser.JSON;
        Configuration.pageLoadStrategy = System.getProperty("webdriver.pageLoadStrategy", "eager");
        Configuration.browserSize = System.getProperty("webdriver.browserSize", "1920x1080");
        Configuration.browser = System.getProperty("webdriver.browser", "chrome");
        Configuration.browserVersion = System.getProperty("webdriver.browserVersion", "100.0");
        Configuration.remote = System.getProperty("webdriver.remoteUrl");

        // Используем W3C-совместимый формат для Selenoid
        DesiredCapabilities capabilities = new DesiredCapabilities();
        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", true);
        selenoidOptions.put("enableVideo", true);
        selenoidOptions.put("screenResolution", Configuration.browserSize);
        capabilities.setCapability("selenoid:options", selenoidOptions);

        Configuration.browserCapabilities = capabilities;
    }

    @BeforeEach
    void preconditionConfiguration() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        if (System.getProperty("REMOTE") != null && System.getProperty("REMOTE").equals("true")) {
            System.setProperty("webdriver.remoteUrl", "https://user1:1234@selenoid.autotests.cloud/wd/hub");
        } else {
            System.clearProperty("webdriver.remoteUrl");
        }
    }

    public void startDriver() {
        driver = new WebDriverProvider().get();
    }

    {
        startDriver();
    }

    @AfterEach
    void tearDown() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        closeWebDriver();
    }
}