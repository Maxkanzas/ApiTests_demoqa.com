package api;

import io.qameta.allure.Step;
import models.books.AddBookRequestBodyModel;
import models.login.LoginRequestBodyModel;
import models.login.LoginResponseBodyModel;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static specs.BookSpec.addBookResponseSpecification;
import static specs.BookSpec.deleteBookResponseSpecification;
import static specs.LoginSpec.*;

public class ApiSteps {
    @Step("Авторизоваться в книжном магазине")
    public static LoginResponseBodyModel login(LoginRequestBodyModel loginRequestBodyModel) {
        return given(loginRequestSpecification)
                .body(loginRequestBodyModel)
                .when().post("/Account/v1/Login")
                .then().spec(loginResponseSpecification)
                .extract().as(LoginResponseBodyModel.class);
    }
    @Step("Удалить все книги из корзины")
    public void deleteAllBooks(LoginResponseBodyModel loginResponse) {
         given(loginRequestSpecification)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .queryParams("UserId", loginResponse.getUserId())
                .when().delete("/BookStore/v1/Books")
                .then().spec(deleteBookResponseSpecification);
    }
    @Step("Удалить одну книгу из корзины")
    public void deleteOneBook(LoginResponseBodyModel loginResponse) {
         given(loginRequestSpecification)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .queryParams("UserId", loginResponse.getUserId())
                .when().delete("/BookStore/v1/Book")
                .then().spec(deleteBookResponseSpecification)
                .extract().response();
    }
    @Step("Добавить в список книгу по ISBN")
    public void addBooks(LoginResponseBodyModel loginResponse, AddBookRequestBodyModel bookData) {
        given(loginRequestSpecification)
                .header("Authorization", "Bearer " + loginResponse.getToken())
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(addBookResponseSpecification);
    }
    public void setCookies(LoginResponseBodyModel loginResponse) {
        open("/favicon.ico"); // Открытие страницы для установки cookies
        getWebDriver().manage().addCookie(new Cookie("userID", loginResponse.getUserId()));
        getWebDriver().manage().addCookie(new Cookie("expires", loginResponse.getExpires()));
        getWebDriver().manage().addCookie(new Cookie("token", loginResponse.getToken()));
    }
}
