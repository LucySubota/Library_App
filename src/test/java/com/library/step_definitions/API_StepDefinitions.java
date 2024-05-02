package com.library.step_definitions;

import com.library.pages.BooksPage;
import com.library.pages.LibrarianDashboardPage;
import com.library.pages.LoginPage;
import com.library.pages.UsersPage;
import com.library.utilities.API_Utils;
import com.library.utilities.ConfigurationReader;
import com.library.utilities.DB_Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Assert;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class API_StepDefinitions {

    String token;
    String acceptHeader;
    String requestHeaderContentType;
    Response response;
    String pathParam;
    Map<String, Object> newCreation;
    LoginPage loginPage;
    LibrarianDashboardPage librarianDashboardPage;
    BooksPage booksPage;
    UsersPage usersPage;

    @Given("I logged Library api as a {string}")
    public void i_logged_library_api_as_a(String userType) {
       token = API_Utils.getToken(userType);
    }

    @Given("Accept header is {string}")
    public void accept_header_is(String contentType) {
        acceptHeader = contentType;
    }

    @When("I send GET request to {string} endpoint")
    public void i_send_get_request_to_endpoint(String endPoint) {
        if(pathParam == null) {
            response = given().accept(acceptHeader).header("x-library-token", token)
                    .when().get(ConfigurationReader.getProperty("library.baseUri") + endPoint);
        }else{
            response = given().accept(acceptHeader).header("x-library-token", token)
                    .pathParam("id", pathParam)
                    .when().get(ConfigurationReader.getProperty("library.baseUri") + endPoint);
        }
    }

    @Then("status code should be {int}")
    public void status_code_should_be(Integer statusCode) {
        Assert.assertEquals(statusCode, (Integer)response.getStatusCode());
    }

    @Then("Response Content type is {string}")
    public void response_content_type_is(String contentType) {
        Assert.assertEquals(contentType, response.getContentType());
    }

    @Then("Each {string} field should not be null")
    public void each_field_should_not_be_null(String field) {
        List<String> values = response.path(field);
        for (String each : values) {
            Assert.assertFalse(each.isBlank());
        }
    }

    @Given("Path param is {string}")
    public void path_param_is(String pathParam) {
        this.pathParam = pathParam;
    }

    @Then("{string} field should be same with path param")
    public void field_should_be_same_with_path_param(String pathParam) {
        Assert.assertEquals(this.pathParam, response.path(pathParam));
        this.pathParam = null;
    }

    @Then("following fields should not be null")
    public void following_fields_should_not_be_null(List<String> fields) {
        for (String each : fields) {
            Assert.assertFalse(each.isBlank());
        }
    }

    @Given("Request Content Type header is {string}")
    public void request_content_type_header_is(String contentType) {
        requestHeaderContentType = contentType;
    }

    @Given("I create a random {string} as request body")
    public void i_create_a_random_as_request_body(String newUserOrBook) {
        if(newUserOrBook.equalsIgnoreCase("book")){
            this.newCreation = API_Utils.getRandomBookMap();
        }else if(newUserOrBook.equalsIgnoreCase("user")){
            this.newCreation = API_Utils.getRandomUserMap();
        }else{
            throw new IllegalArgumentException("ONLY USER OR BOOK CAN BE CREATED");
        }
        System.out.println("----NEW USER OR BOOK----");
        System.out.println(this.newCreation);
    }

    @When("I send POST request to {string} endpoint")
    public void i_send_post_request_to_endpoint(String endPoint) {
        if(endPoint.equalsIgnoreCase("/decode")){
            Map<String, String> body = new HashMap<>();
            body.put("token", token);
            response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
                    .header("x-library-token", token)
                    .body(body)
                    .when().post(ConfigurationReader.getProperty("library.baseUri") + endPoint);
        }else {
            response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
                    .header("x-library-token", token)
                    .body(newCreation)
                    .when().post(ConfigurationReader.getProperty("library.baseUri") + endPoint);
        }
    }

    @Then("the field value for {string} path should be equal to {string}")
    public void the_field_value_for_path_should_be_equal_to(String path, String value) {

        Assert.assertEquals(value, response.path(path));

    }

    @Then("{string} field should not be null")
    public void field_should_not_be_null(String path) {
        Assert.assertNotNull(response.path(path));
    }

    @Given("I logged in Library UI as {string}")
    public void i_logged_in_library_ui_as(String userType) {
        loginPage = new LoginPage();
        loginPage.login(userType);
    }

    @Given("I navigate to {string} page")
    public void i_navigate_to_page(String page) {
        librarianDashboardPage = new LibrarianDashboardPage();
        librarianDashboardPage.navigateToPage(page);
    }

    @Then("UI, Database and API created book information must match")
    public void ui_database_and_api_created_book_information_must_match() {
        String bookId = response.path("book_id");
        String bookName = (String) newCreation.get("name");
        //System.out.println("=====NEW BOOKS NAME FROM API - " + bookName);

        //UI
        booksPage = new BooksPage();
        List<Map<String, String>> listOfBookFromUI = booksPage.getListOfBooksBySearchRequestFromUI(bookName);
        for (Map<String, String> each : listOfBookFromUI) {
            each.remove("category");
        }
//        System.out.println("BOOKS FROM UI");
//        System.out.println(listOfBookFromUI);

        //DB
        ResultSet rs = DB_Utils.runQuery("SELECT name, isbn, year, author, book_category_id from books where name = '"+bookName+"'");
        List<Map<String, String>> listOfBooksFromDB = DB_Utils.getAllRowAsListOfMap();
//        System.out.println("BOOKS FROM DB");
//        System.out.println(listOfBooksFromDB);

        Map<String, String> expectedFromAPI = new HashMap<>();
        for(Map.Entry<String, Object> each : newCreation.entrySet()){
            if(!each.getKey().equals("description")) {
                expectedFromAPI.put(each.getKey(), each.getValue() + "");
            }
        }
//        System.out.println("BOOK CREATED WITH API");
//        System.out.println(expectedFromAPI);

        Assert.assertTrue(listOfBooksFromDB.contains(expectedFromAPI));
        expectedFromAPI.remove("book_category_id");
        Assert.assertTrue(listOfBookFromUI.contains(expectedFromAPI));

    }

    @Then("created user information should match with Database")
    public void created_user_information_should_match_with_database() {
        String fullName = (String) newCreation.get("full_name");
        ResultSet rs = DB_Utils.runQuery("select full_name, email, user_group_id, status, start_date, end_date, address from users where full_name = '"+fullName+"'");
        Map<String, String> dbUser = DB_Utils.getRowMap(1);
        Map<String, String> expectedFromAPI = new HashMap<>();
        for(Map.Entry<String, Object> each : newCreation.entrySet()){
            if(!each.getKey().equals("password")) {
                expectedFromAPI.put(each.getKey(), each.getValue() + "");
            }
        }
        Assert.assertEquals(expectedFromAPI, dbUser);
    }

    @Then("created user should be able to login Library UI")
    public void created_user_should_be_able_to_login_library_ui() {
        usersPage = new UsersPage();
        loginPage.login((String)newCreation.get("email"), (String)newCreation.get("password"));
        Assert.assertTrue(librarianDashboardPage.borrowedBooksIcon.isDisplayed());
    }

    @Then("created user name should appear in Dashboard Page")
    public void created_user_name_should_appear_in_dashboard_page() {

    }

    @Given("I logged Library api with credentials {string} and {string}")
    public void i_logged_library_api_with_credentials_and(String email, String password) {
        token = API_Utils.getToken(email, password);
    }

    @Given("I send token information as request body")
    public void i_send_token_information_as_request_body() {

    }

}
