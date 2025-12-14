package tests;

import helpers.SessionManager;
import api.models.AuthResponseModel;
import org.junit.jupiter.api.Test;
import pages.UserProfilePage;
import api.BookstoreApiV1;

import static tests.TestData.*;

public class BookstoreSmokeTests extends TestBase {

    private final BookstoreApiV1 apiService = new BookstoreApiV1();
    private final UserProfilePage userProfilePage = new UserProfilePage();

    @Test
    void verifyBookDeletionAfterApiAdditionTest() {
        AuthResponseModel sessionData = apiService.authenticateUser(TestData.username, TestData.password);
        apiService.clearAllBooks(sessionData);
        apiService.addBookToCollection(sessionData, TestData.isbn);

        SessionManager.setBrowserSessionCookies(sessionData, username);

        userProfilePage.openPage()
                .checkUserName(TestData.username)
                .checkBookName(TestData.title)
                .clickOnDeleteButton()
                .closeModalOk()
                .confirmModalWindow()
                .checkList(TestData.title);
    }
}