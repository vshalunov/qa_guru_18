package com.github.zlwqa.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.zlwqa.config.AppConfig;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class TestBase {

    public static AppConfig webConfig = ConfigFactory.create(AppConfig.class, System.getProperties());
    public static String authorizationCookie;
    public static String updateTopCartSection;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = webConfig.apiUrl();
        Configuration.baseUrl = webConfig.webUrl();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());

        step("Получение cookie через api и установка его в браузере", () -> {
            authorizationCookie =
                    given()
                            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                            .formParam("Email", webConfig.userLogin())
                            .formParam("Password", webConfig.userPassword())
                            .when()
                            .post("login")
                            .then()
                            .statusCode(302)
                            .extract()
                            .cookie("NOPCOMMERCE.AUTH");

            step("Open minimal content, because cookie can be set when site is opened", () ->
                    open("/Themes/DefaultClean/Content/images/logo.png"));

            step("Set cookie to to browser", () ->
                    getWebDriver().manage().addCookie(
                            new Cookie("NOPCOMMERCE.AUTH", authorizationCookie)));
        });
    }
}
