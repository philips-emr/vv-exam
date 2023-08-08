package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.DataTableType;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BookSteps {
    private Response response;
    private String baseUrl = "https://fakerestapi.azurewebsites.net/api/v1/Books";
    private String description = "Lorem lorem lorem. Lorem lorem lorem. Lorem lorem lorem.\n";
    private String excerpt = "Lorem lorem lorem. Lorem lorem lorem. Lorem lorem lorem.\n" +
            "Lorem lorem lorem. Lorem lorem lorem. Lorem lorem lorem.\nLorem lorem lorem. Lorem lorem lorem. Lorem lorem lorem.\n" +
            "Lorem lorem lorem. Lorem lorem lorem. Lorem lorem lorem.\nLorem lorem lorem. Lorem lorem lorem. Lorem lorem lorem.\n";
    private String newBookTitle;
    private String newBookDescription;
    private int newBookPageCount;
    private String newBookExcerpt;
    private String newBookPublishDate;

    //variables for research test
    private int bookId;
    private String expectedTitle = "Book 42";
    private int expectedPageCount = 4200;

    //variables for PUT test
    private String updatedTitle;
    private String updatedDescription;
    private int updatedPageCount;
    private String updatedExcerpt;
    private String updatedPublishDate;

    //variables for invalid search test
    private int invalidBookId;
    private String errorMessage;

    private String createNewBookJson() {
        return String.format(
                "{ \"title\": \"%s\", \"description\": \"%s\", \"pageCount\": %d, \"excerpt\": \"%s\", \"publishDate\": \"%s\" }",
                newBookTitle, newBookDescription, newBookPageCount, newBookExcerpt, newBookPublishDate
        );
    }

    //********** GIVEN **********
    @Given("^that I have the Books API URI$")
    public void thatIHaveTheBooksAPIURI() {
    }

    @Given("^a new book with the necessary information$")
    public void aNewBookWithTheNecessaryInformation() {
        newBookTitle = "New Book";
        newBookDescription = "This is a new book.";
        newBookPageCount = 255;
        newBookExcerpt = "This is an excerpt from the new book.";
        newBookPublishDate = "2023-08-04T12:00:00.000Z";
    }

    @Given("an existing book ID {int}")
    public void anExistingBookID(int id) {
        bookId = id;
    }

    @Given("a new book with updated information")
    public void aNewBookWithUpdatedInformation() {
        updatedTitle = "New Book";
        updatedDescription = "This is a new book.";
        updatedPageCount = 175;
        updatedExcerpt = "This is an excerpt from the new book.";
        updatedPublishDate = "2023-08-08T12:00:00.000Z";
    }

    @DataTableType(replaceWithEmptyString = "<empty>")
    public String emptyStringType(String value) {
        if ("<empty>".equals(value)) {
            return "";
        } else {
            return value;
        }
    }

    //********** WHEN **********
    @When("^I make a GET request to list the books$")
    public void IMakeAGETRequestToListTheBooks() {
        response = given().get(baseUrl);
    }

    @When("^I make a POST request to add the book$")
    public void iMakeAPOSTRequestToAddTheBook() {
        response = given()
                .contentType(ContentType.JSON)
                .body(createNewBookJson())
                .post(baseUrl);
    }

    @When("^I make a GET request to fetch the book by ID$")
    public void iMakeAGETRequestToFetchTheBookByID() {
        response = given()
                .contentType(ContentType.JSON)
                .get(baseUrl + "/" + bookId);
    }

    @When("I make a PUT request to update the book")
    public void iMakeAPUTRequestToUpdateTheBook() {
        String requestBody = String.format(
                "{ \"id\": %d, \"title\": \"%s\", \"description\": \"%s\", \"pageCount\": %d, \"excerpt\": \"%s\", \"publishDate\": \"%s\" }",
                bookId, updatedTitle, updatedDescription, updatedPageCount, updatedExcerpt, updatedPublishDate
        );

        response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .put(baseUrl + "/" + bookId);
    }

    @When("I make a DELETE request to delete the book")
    public void iMakeADELETERequestToDeleteTheBook() {
        response = given()
                .contentType(ContentType.JSON)
                .delete(baseUrl + "/" + bookId);
    }

    @When("I make a GET request to fetch the book with invalid ID")
    public void iMakeAGETRequestToFetchTheBookWithInvalidID() {
        response = given()
                .contentType(ContentType.JSON)
                .get(baseUrl + "/" + invalidBookId);
    }

    @When("I add a new book with invalid publication date")
    public void iAddANewBookWithInvalidPublicationDate() {
        newBookTitle = "New book";
        newBookDescription = "This is a new book.";
        newBookPageCount = 0;
        newBookExcerpt = "This is an excerpt from the new book.";
        newBookPublishDate = "08/08/2023";

        response = given()
                .contentType(ContentType.JSON)
                .body(createNewBookJson())
                .post(baseUrl);
    }

    //********** THEN **********
    @Then("^the answer must contain a list of books$")
    public void theAnswerMustContainAListOfBooks() {
        response.then().statusCode(200);
        response.then().body("size()", greaterThan(0)); // Check that the list is not empty

        // Check specific values for the first book in the list (index 0)
        response.then().body("[0].id", equalTo(1));
        response.then().body("[0].title", equalTo("Book 1"));
        response.then().body("[0].description", equalTo(description));
        response.then().body("[0].pageCount", equalTo(100));
        response.then().body("[0].excerpt", equalTo(excerpt));

        // Check specific values for the book's half list (index 99)
        response.then().body("[99].id", equalTo(100));
        response.then().body("[99].title", equalTo("Book 100"));
        response.then().body("[99].description", equalTo(description));
        response.then().body("[99].pageCount", equalTo(10000));
        response.then().body("[99].excerpt", equalTo(excerpt));

        // Check specific values for the last book in the list (index 199)
        response.then().body("[199].id", equalTo(200));
        response.then().body("[199].title", equalTo("Book 200"));
        response.then().body("[199].description", equalTo(description));
        response.then().body("[199].pageCount", equalTo(20000));
        response.then().body("[199].excerpt", equalTo(excerpt));
    }

    @Then("^the answer must contain the corresponding book information$")
    public void theAnswerMustContainTheCorrespondingBookInformation() {
        response.then().statusCode(200);
        response.then().body("id", equalTo(bookId));
        response.then().body("title", equalTo(expectedTitle));
        response.then().body("description", equalTo(description));
        response.then().body("pageCount", equalTo(expectedPageCount));
        response.then().body("excerpt", equalTo(excerpt));
    }

    @Then("the response must return the code {int}")
    public void theResponseMustReturnTheCode(int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    @Then("the response must contain the updated book information")
    public void theResponseMustContainTheUpdatedBookInformation() {
        response.then().body("title", equalTo(updatedTitle));
        response.then().body("description", equalTo(updatedDescription));
        response.then().body("pageCount", equalTo(updatedPageCount));
        response.then().body("excerpt", equalTo(updatedExcerpt));
    }

    @Then("the response must contain an error message {int}, {string}")
    public void theResponseMustContainAnErrorMessage(int expectedStatus, String errorMessage) {
        response.then()
                .body("title", equalTo(errorMessage))
                .body("status", equalTo(expectedStatus));
    }

}