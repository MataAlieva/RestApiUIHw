package helpers;

import io.qameta.allure.Step;
import api.models.AuthResponseModel;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class SessionManager {
    @Step("Установить Cookie")
    public static void setBrowserSessionCookies(AuthResponseModel apiResponse, String username) {
        open("/favicon.png");
        WebDriver.Options options = getWebDriver().manage();
        options.addCookie(new Cookie("userID", apiResponse.getUserId()));
        options.addCookie(new Cookie("expires", apiResponse.getExpires()));
        options.addCookie(new Cookie("token", apiResponse.getToken()));

        getWebDriver().manage().addCookie(new Cookie("userName", username));
    }
}