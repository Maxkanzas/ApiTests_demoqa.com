package tests;

import api.ApiSteps;
import data.TestData;
import io.restassured.response.Response;
import models.books.AddBookRequestBodyModel;
import models.books.Isbn;
import models.login.LoginRequestBodyModel;
import models.login.LoginResponseBodyModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import page.ProfilePage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.openqa.selenium.devtools.v85.network.Network.setCookies;

@Tag("Smoke")
public class BookStoreTestsOptimized extends TestBase {
    ApiSteps apiSteps = new ApiSteps();
    ProfilePage profilePage = new ProfilePage();
    LoginResponseBodyModel loginResponse = new LoginResponseBodyModel();

    @DisplayName("Авторизация, добавление одной книги и удаление всех книг")
    @Test
    public void addBookAndDeleteAllBooksTest() {
        step("Авторизация пользователя", () -> {
            loginResponse = apiSteps.login(loginRequestBodyModel);
        });
        step("Удаление всех книг перед началом теста", () -> {
            apiSteps.deleteAllBooks(loginResponse);
        });
        step("Добавление книги в корзину", () -> {
            Isbn isbn = new Isbn("9781449365035");
            AddBookRequestBodyModel bookData = new AddBookRequestBodyModel(loginResponse.getUserId(), List.of(isbn));
            apiSteps.addBooks(loginResponse, bookData);
        });
        step("Проверка книги в корзине", () -> {
            apiSteps.setCookies(loginResponse);
            profilePage.checkBookInCollection();
        });
        step("Удаление всех книг из корзины", () -> {
            apiSteps.deleteAllBooks(loginResponse);
            profilePage.checkEmptyBookInCollection();
        });
    }
    @DisplayName("Авторизация, добавление двух книг и удаление одной книги")
    @Test
    void addTwoBooksAndDeleteOneBookTest() {
        step("Авторизация пользователя", () -> {
            loginResponse = apiSteps.login(loginRequestBodyModel);
        });
        step("Удаление всех книг перед началом теста", () -> {
            apiSteps.deleteAllBooks(loginResponse);
        });
        step("Добавление книги в корзину", () -> {
            Isbn firstBook = new Isbn("9781449365035");
            Isbn secondBook = new Isbn("9781491904244");
            AddBookRequestBodyModel bookData = new AddBookRequestBodyModel(loginResponse.getUserId(), Arrays.asList(firstBook, secondBook));
            apiSteps.addBooks(loginResponse, bookData);
        });
        step("Проверка книг в корзине", () -> {
            apiSteps.setCookies(loginResponse);
            profilePage.checkBooksInCollection();
        });
        step("Удаление одной книги из корзины", () -> {
            apiSteps.deleteOneBook(loginResponse, "9781491904244");
            profilePage.checkBookInCollection();
        });
        step("Проверка книги в корзине", () -> {
            apiSteps.setCookies(loginResponse);
            profilePage.checkBookInCollection();
        });
    }
    @DisplayName("Авторизация и удаление всех книг")
    @Test
    void deleteAllBooksTest() {
        step("Авторизация пользователя", () -> {
            loginResponse = apiSteps.login(loginRequestBodyModel);
        });
        step("Удаление всех книг перед началом теста", () -> {
            apiSteps.deleteAllBooks(loginResponse);
        });
        step("Проверка книг в корзине", () -> {
            apiSteps.setCookies(loginResponse);
            profilePage.checkEmptyBookInCollection();
        });
    }
}