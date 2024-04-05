package com.library.step_definitions;

import com.library.pages.BooksPage;
import com.library.pages.LibrarianDashboardPage;
import com.library.pages.LoginPage;
import com.library.utilities.BrowserUtils;
import com.library.utilities.ConfigurationReader;
import com.library.utilities.DB_Utils;
import com.library.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Books_StepDefinitions {

    LoginPage loginPage = new LoginPage();
    BooksPage booksPage = new BooksPage();
    LibrarianDashboardPage dashboardPage = new LibrarianDashboardPage();

    @Given("the user logged in as {string}")
    public void the_user_logged_in_as(String userType) {
        Driver.getDriver().get(ConfigurationReader.getProperty("qa2_url"));
        loginPage.login(userType);
    }

    @When("the user navigates to {string} page")
    public void the_user_navigates_to_page(String page) {
        dashboardPage.goToPage(page);
    }

    @When("the user gets all book categories in webpage")
    public void the_user_gets_all_book_categories_in_webpage() {
        booksPage.booksCategories.click();
    }

    @Then("user should be able to see correct categories")
    public void user_should_be_able_to_see_correct_categories() {
        DB_Utils.createConnection();
        ResultSet rs = DB_Utils.runQuery("SELECT name FROM book_categories");
        List<String> expectedCategories = DB_Utils.getColumnDataAsList(1);

        Assert.assertEquals(expectedCategories, booksPage.getListOfBooksCategories_String());
        Assert.assertTrue(booksPage.booksCategories.isDisplayed());
    }

    @When("user searches for book {string}")
    public void user_searches_for_book(String searchValue) {
        booksPage.searchInput.sendKeys(searchValue + Keys.ENTER);
    }

    @Then("books information must match the database for {string}")
    public void books_information_must_match_the_database_for(String searchValue) {
        List<Map<String, String>> expectedList = booksPage.getListOfBooksBySearchRequestFromDB(searchValue);
        BrowserUtils.sleep(1);
        System.out.println("expectedList = " + expectedList);
        List<Map<String, String>> actualList = booksPage.getListOfBooksBySearchRequestFromUI();
        BrowserUtils.sleep(1);
        System.out.println("actualList = " + actualList);
        Assert.assertEquals(expectedList, actualList);
    }

}
