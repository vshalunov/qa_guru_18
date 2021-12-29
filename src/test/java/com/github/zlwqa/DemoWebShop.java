package com.github.zlwqa;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class DemoWebShop {

    @BeforeAll
    static void configureBaseUrl() {
        RestAssured.baseURI = "http://demowebshop.tricentis.com/";
        Configuration.baseUrl = "http://demowebshop.tricentis.com/";
    }


}
