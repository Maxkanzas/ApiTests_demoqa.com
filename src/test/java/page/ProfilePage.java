package page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import tests.TestBase;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ProfilePage extends TestBase {
    private static SelenideElement checkBookFirst = $(".action-buttons");
    private static SelenideElement checkBookSecond = $("[id=\"see-book-You Don't Know JS\"]");
    private static SelenideElement checkAbsenceBook = $(".rt-noData");

    @Step("Проверяем наличие книги (Speaking JavaScript) в корзине")
    public void checkBookInCollection() {
        open("/profile");
        checkBookFirst.shouldBe(visible).shouldHave(text("Speaking JavaScript"));
    }
    @Step("Проверяем наличие двух книг в корзине")
    public void checkBooksInCollection() {
        open("/profile");
        checkBookFirst.shouldBe(visible).shouldHave(text("Speaking JavaScript"));
        checkBookSecond.shouldBe(visible).shouldHave(text("You Don't Know JS"));
    }
    @Step("Проверяем отсутствие книг в корзине")
    public void checkEmptyBookInCollection() {
        open("/profile");
        checkAbsenceBook.shouldBe(visible).shouldHave(text("No rows found"));
    }
}
