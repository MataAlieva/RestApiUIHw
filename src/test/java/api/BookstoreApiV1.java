package api;

import api.models.AddBookRequestModel;
import api.models.AuthRequestModel;
import api.models.AuthResponseModel;
import api.specs.TestSpecs;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BookstoreApiV1 {
    private static final String BASE_ACCOUNT_URL = "/Account/v1";
    private static final String BASE_BOOKSTORE_URL = "/BookStore/v1";

    public AuthResponseModel authenticateUser(String user, String pass) {
        AuthRequestModel credentials = new AuthRequestModel();
        credentials.setUserName(user);
        credentials.setPassword(pass);

        return given()
                .spec(TestSpecs.requestSpecification)
                .body(credentials)
                .when()
                .post(BASE_ACCOUNT_URL + "/Login")
                .then()
                .spec(TestSpecs.statusCodeResponseSpec(200))
                .extract().as(AuthResponseModel.class);
    }

    public void clearAllBooks(AuthResponseModel sessionData) {
        given()
                .spec(TestSpecs.requestSpecification)
                .header("Authorization", "Bearer " + sessionData.getToken())
                .queryParam("UserId", sessionData.getUserId())
                .when()
                .delete(BASE_BOOKSTORE_URL + "/Books")
                .then()
                .spec(TestSpecs.statusCodeResponseSpec(204));
    }

    public void addBookToCollection(AuthResponseModel sessionData, String isbn) {
        AddBookRequestModel requestBody = new AddBookRequestModel();
        requestBody.setUserId(sessionData.getUserId());

        AddBookRequestModel.Isbn singleBook = new AddBookRequestModel.Isbn(isbn);
        requestBody.setCollectionOfIsbns(List.of(singleBook));

        given()
                .spec(TestSpecs.requestSpecification)
                .header("Authorization", "Bearer " + sessionData.getToken())
                .body(requestBody)
                .when()
                .post(BASE_BOOKSTORE_URL + "/Books")
                .then()
                .spec(TestSpecs.statusCodeResponseSpec(201));
    }
}