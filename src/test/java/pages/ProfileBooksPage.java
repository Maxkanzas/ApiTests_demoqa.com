package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class ProfileBooksPage {
    private  final SelenideElement tableWithUserBooks = $(".ReactTable");
    private  final SelenideElement searchField = $("#searchBox");
    private  final SelenideElement deleteIcon = $("#delete-record-undefined");
    private  final SelenideElement modalOkButton = $("#closeSmallModal-ok");

    @Step("Открыть список добавленных книг пользователя")
    public  ProfileBooksPage openUserBooksPage(String userId, String expires, String token) {
        open("/favicon.ico");
        getWebDriver().manage().addCookie(new Cookie("userID", userId));
        getWebDriver().manage().addCookie(new Cookie("expires", expires));
        getWebDriver().manage().addCookie(new Cookie("token", token));
        open("/profile");

        return this;
    }

    @Step("Проверить, что книга с названием {bookName} существует в профиле")
    public ProfileBooksPage findBookByName(String bookName) {
        tableWithUserBooks.shouldHave(text(bookName));

        return this;
    }

    @Step("Удалить книгу с названием {bookName} из профиля пользователя")
    public ProfileBooksPage deleteBookByName(String bookName) {
        searchField.setValue(bookName);
        deleteIcon.click();
        modalOkButton.click();

        return this;
    }

    @Step("Проверить, что книга с названием {bookName} отсутствует в профиле")
    public ProfileBooksPage findNotBookByName(String bookName) {
        tableWithUserBooks.shouldNotHave(text(bookName));

        return this;
    }
}
