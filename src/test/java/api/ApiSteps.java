package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.books.AddBookRequestBodyModel;
import models.login.LoginRequestBodyModel;

import static io.restassured.RestAssured.given;
import static specs.LoginSpec.*;

public class ApiSteps {
    @Step("Авторизоваться в книжном магазине")
    public static Response login(String userName, String password) {
        LoginRequestBodyModel authData = new LoginRequestBodyModel(userName, password);
        Response authResponse = given(loginRequestSpecification)
                .body(authData)
                .when().post("/Account/v1/Login")
                .then().spec(loginResponseSpecification)
                .extract().response();
        return authResponse;
    }


    @Step("Очистить список книг пользователя")
    public static Response clearListOfUserBooks(String token, String userId) {
        Response deletionResponse = given(loginRequestSpecification)
                .header("Authorization", "Bearer " + token)
                .queryParams("UserId", userId)
                .when().delete("/BookStore/v1/Books")
                .then().spec(deleteBookResponseSpecification)
                .extract().response();
        return deletionResponse;
    }

    @Step("Добавить список книг по ISBN")
    public static void addBooks(String token, AddBookRequestBodyModel bookData) {
        given(loginRequestSpecification)
                .header("Authorization", "Bearer " + token)
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(addBookResponseSpecification);
    }
}
