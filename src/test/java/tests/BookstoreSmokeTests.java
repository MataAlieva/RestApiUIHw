package tests;

import helpers.SessionManager;
import api.models.AuthResponseModel;
import org.junit.jupiter.api.Test;
import pages.UserProfilePage;
import api.BookstoreApiV1;

public class BookstoreSmokeTests extends TestData{

    private final BookstoreApiV1 apiService = new BookstoreApiV1();
    private final UserProfilePage userProfilePage = new UserProfilePage();

    @Test
    void verifyBookDeletionAfterApiAdditionTest() {
        AuthResponseModel sessionData = apiService.authenticateUser(username, password);
        apiService.clearAllBooks(sessionData);
        apiService.addBookToCollection(sessionData, isbn);

        SessionManager.setBrowserSessionCookies(sessionData, username);

        userProfilePage.openPage()
                .checkUserName(username)
                .checkBookName(title)
                .clickOnDeleteButton()
                .closeModalOk()
                .confirmModalWindow()
                .checkList(title);
    }
}