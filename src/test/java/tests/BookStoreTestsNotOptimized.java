package tests;

import org.openqa.selenium.Cookie;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

@Tag("Smoke")
public class BookStoreTestsNotOptimized extends TestBase {
    @DisplayName("Авторизация, добавление одной книги и удаление всех книг")
    @Test
    void deleteAllBooksFromCollectionTest() {
        step("Авторизация пользователя", () -> {
            String authData = "{\"userName\":\"max\",\"password\":\"mMiu4*3hPK3U*C@\"}";
            Response response = given()
                    .log().uri()
                    .log().method()
                    .contentType(JSON)
                    .body(authData)
                    .when()
                    .post("/Account/v1/Login")
                    .then()
                    .log().status()
                    .log().body()
                    .extract().response();
            String bookData = "{\"userId\": \"95e7f595-9f05-420c-9fd0-436cb2d09cf9\", \"collectionOfIsbns\": [{\"isbn\": \"9781449365035\"}]}";
            step("Добавление книги в корзину корзины", () -> {
                given()
                        .log().uri()
                        .log().method()
                        .log().body()
                        .contentType(JSON)
                        .header("Authorization", "Bearer " + response.path("token"))
                        .body(bookData)
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .log().body()
                        .statusCode(201);
            });
            step("Удаление всех книг из корзины", () -> {
                given()
                        .log().uri()
                        .log().method()
                        .log().body()
                        .contentType(JSON)
                        .header("Authorization", "Bearer " + response.path("token"))
                        .when()
                        .delete("/BookStore/v1/Books?UserId=95e7f595-9f05-420c-9fd0-436cb2d09cf9")
                        .then()
                        .log().body()
                        .statusCode(204);
            });
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", response.path("userId").toString()));
            getWebDriver().manage().addCookie(new Cookie("expires", response.path("expires").toString()));
            getWebDriver().manage().addCookie(new Cookie("token", response.path("token").toString()));

            open("/profile");
            $(".rt-noData").shouldHave(text("No rows found"));
        });
    }
    @DisplayName("Авторизация, добавление двух книг и удаление одной книги")
    @Test
    void deleteOneBookFromCollectionTest() {
        step("Авторизация пользователя", () -> {
            String authData = "{\"userName\":\"max\",\"password\":\"mMiu4*3hPK3U*C@\"}";
            Response response = given()
                    .log().uri()
                    .log().method()
                    .contentType(JSON)
                    .body(authData)
                    .when()
                    .post("/Account/v1/Login")
                    .then()
                    .log().status()
                    .log().body()
                    .extract().response();
            String bookData = "{\"userId\": \"95e7f595-9f05-420c-9fd0-436cb2d09cf9\", \"collectionOfIsbns\": [{\"isbn\": \"9781449365035\"}]}";
            step("Добавление книги в корзину корзины", () -> {
                given()
                        .log().uri()
                        .log().method()
                        .log().body()
                        .contentType(JSON)
                        .header("Authorization", "Bearer " + response.path("token"))
                        .body(bookData)
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .log().body()
                        .statusCode(201);
            });
            String bookDataOther = "{\"userId\": \"95e7f595-9f05-420c-9fd0-436cb2d09cf9\", \"collectionOfIsbns\": [{\"isbn\": \"9781491904244\"}]}";
            step("Добавление книги в корзину корзины", () -> {
                given()
                        .log().uri()
                        .log().method()
                        .log().body()
                        .contentType(JSON)
                        .header("Authorization", "Bearer " + response.path("token"))
                        .body(bookDataOther)
                        .when()
                        .post("/BookStore/v1/Books")
                        .then()
                        .log().body()
                        .statusCode(201);
            });
            String bookDataDelete = "{\"isbn\": \"9781491904244\", \"userId\": \"95e7f595-9f05-420c-9fd0-436cb2d09cf9\"}";
            step("Удаление одной книги из корзины", () -> {
                given()
                        .log().uri()
                        .log().method()
                        .log().body()
                        .contentType(JSON)
                        .header("Authorization", "Bearer " + response.path("token"))
                        .body(bookDataDelete)
                        .when()
                        .delete("/BookStore/v1/Book")
                        .then()
                        .log().body()
                        .statusCode(204);
            });
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", response.path("userId").toString()));
            getWebDriver().manage().addCookie(new Cookie("expires", response.path("expires").toString()));
            getWebDriver().manage().addCookie(new Cookie("token", response.path("token").toString()));

            open("/profile");
            $(".action-buttons").shouldHave(text("Speaking JavaScript"));
        });
    }
    @DisplayName("Авторизация и удаление всех книг")
    @Test
    void deleteAllBooksTest() {
        step("Авторизация пользователя", () -> {
            String authData = "{\"userName\":\"max\",\"password\":\"mMiu4*3hPK3U*C@\"}";
            Response response = given()
                    .log().uri()
                    .log().method()
                    .contentType(JSON)
                    .body(authData)
                    .when()
                    .post("/Account/v1/Login")
                    .then()
                    .log().status()
                    .log().body()
                    .extract().response();
            step("Удаление всех книг из корзины", () -> {
                given()
                        .log().uri()
                        .log().method()
                        .log().body()
                        .contentType(JSON)
                        .header("Authorization", "Bearer " + response.path("token"))
                        .when()
                        .delete("/BookStore/v1/Books?UserId=95e7f595-9f05-420c-9fd0-436cb2d09cf9")
                        .then()
                        .log().body()
                        .statusCode(204);
            });
            open("/favicon.ico");
            getWebDriver().manage().addCookie(new Cookie("userID", response.path("userId").toString()));
            getWebDriver().manage().addCookie(new Cookie("expires", response.path("expires").toString()));
            getWebDriver().manage().addCookie(new Cookie("token", response.path("token").toString()));

            open("/profile");
            $(".rt-noData").shouldHave(text("No rows found"));
        });
    }
}