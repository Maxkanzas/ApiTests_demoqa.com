package tests;

import data.TestData;
import io.restassured.response.Response;
import models.books.AddBookRequestBodyModel;
import models.books.Isbn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import pages.ProfileBooksPage;

import java.util.List;

import static api.ApiSteps.*;

public class BookStoreTests extends TestBase {
    @Tag("Simple")
    @Tag("Smoke")
    @DisplayName("Удаление книги из списка")
    @Test
    void deleteBookFromList() {
        Response responseLogin = login(TestData.bookStoreLogin, TestData.bookStorePassword);
        String token = responseLogin.path("token");
        String userId = responseLogin.path("userId");
        String expires = responseLogin.path("expires");
        clearListOfUserBooks(token, userId);
        Isbn isbn = new Isbn();
        isbn.setIsbn(TestData.isbn);
        List<Isbn> listIsbns = List.of(isbn);
        AddBookRequestBodyModel bookData = new AddBookRequestBodyModel(userId, listIsbns);
        addBooks(token, bookData);
        ProfileBooksPage profileBooksPage = new ProfileBooksPage();
        profileBooksPage.openUserBooksPage(userId, expires, token);
        profileBooksPage.findBookByName(TestData.bookName);
        profileBooksPage.deleteBookByName(TestData.bookName);
        profileBooksPage.findNotBookByName(TestData.bookName);
    }
}