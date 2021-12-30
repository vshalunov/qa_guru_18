package com.github.zlwqa.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;


public class DemoWebShop extends TestBase {

    @Test
    @DisplayName("Отображение товара в корзине после добавления товара через API")
    void displayItemInShoppingCartAfterAddItemViaAPITest() {

        step("Добавить товар '14.1-inch Laptop'", () ->
                updateTopCartSection = given()
                        .cookie("NOPCOMMERCE.AUTH", authorizationCookie)
                        .when()
                        .post("addproducttocart/catalog/31/1/1")
                        .then().log().body()
                        .statusCode(200)
                        .extract()
                        .path("updatetopcartsectionhtml"));

        step("Открыть главную страницу", () ->
                open(""));


        step("Количество товара = " + updateTopCartSection, () ->
                $(".cart-qty").shouldHave(text(updateTopCartSection)));


    }

}