package pages;

import com.codeborne.selenide.ModalOptions;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class UserProfilePage {
    private final SelenideElement reactTable = $(".ReactTable"),
            deleteButton = $("#delete-record-undefined"),
            closeSmallModalOk = $("#closeSmallModal-ok"),
            userName = $("#userName-value");

    @Step("Открыть профиль")
    public UserProfilePage openPage() {
        open("/profile");
        return this;
    }

    @Step("Проверить имя юзера в профиле")
    public UserProfilePage checkUserName(String username) {
        userName.shouldHave(text(username));
        return this;
    }
    @Step("Проверить наличие добавленной книги в списке")
    public UserProfilePage checkBookName(String bookName) {
        reactTable.shouldHave(text(bookName));
        return this;
    }

    @Step("Кликнуть на кнопку \"удалить\"")
    public UserProfilePage clickOnDeleteButton() {
        deleteButton.click();
        return this;
    }

    @Step("Нажать на \"да\" в модальном окне")
    public UserProfilePage closeModalOk() {
        closeSmallModalOk.click();
        return this;
    }

    @Step("Закрыть модальное окно подтверждения")
    public UserProfilePage confirmModalWindow() {
        Selenide.confirm(ModalOptions.withExpectedText("Book deleted."));
        return this;
    }

    @Step("Проверить наличие добавленной книги в списке")
    public void checkList(String bookName) {
        reactTable.shouldNotHave(text(bookName));
    }
}