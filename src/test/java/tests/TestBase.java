package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.logevents.SelenideLogger;
import extensions.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

import static com.codeborne.selenide.Configuration.*;
import static com.codeborne.selenide.Selenide.closeWebDriver;


public class TestBase {
    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://demoqa.com";
        Configuration.baseUrl =  "https://demoqa.com";
        Configuration.pageLoadStrategy = "eager";

        String remoteUrlFromCli = System.getProperty("remote");
        if (remoteUrlFromCli != null && !remoteUrlFromCli.isEmpty()) {
            remote = remoteUrlFromCli;

        browser = System.getProperty("browser", "chrome");
        browserSize = System.getProperty("browser_size", "1920x1080");
        browserVersion = System.getProperty("browser_version", "128.0");

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.of(
                "enableVNC", true,
                "enableVideo", true
        ));
        browserCapabilities = capabilities;
        }
        else {
            browser = "chrome";
            browserSize = "1920x1080";
        }
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void shutDown(){
        if (WebDriverRunner.hasWebDriverStarted()) {
            Attach.screenshotAs("Last screenshot");
            Attach.pageSource();
            Attach.browserConsoleLogs();
            Attach.addVideo();
            closeWebDriver();
        }
    }
}