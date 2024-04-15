package com.library.step_definitions;

import com.library.pages.LibrarianDashboardPage;
import com.library.utilities.DB_Utils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.sql.ResultSet;

public class Dashboard_StepDefinitions {

    LibrarianDashboardPage dashboardPage;
    String usersCount;
    String booksCount;
    String borrowedBooksCount;

    @When("user gets all information from modules")
    public void user_gets_all_information_from_modules() {
        dashboardPage = new LibrarianDashboardPage();
        usersCount = dashboardPage.usersIcon.getText();
        booksCount = dashboardPage.booksIcon.getText();
        borrowedBooksCount = dashboardPage.borrowedBooksIcon.getText();
    }

    @Then("the information should be same with database")
    public void the_information_should_be_same_with_database() {
        DB_Utils.createConnection();
        ResultSet rs = DB_Utils.runQuery("SELECT COUNT(is_returned) FROM book_borrow WHERE is_returned = 0");
        String expectedResult = DB_Utils.getFirstRowFirstColumn();
        Assert.assertEquals(expectedResult, borrowedBooksCount);
        rs = DB_Utils.runQuery("SELECT COUNT(*) FROM books");
        expectedResult = DB_Utils.getFirstRowFirstColumn();
        Assert.assertEquals(expectedResult, booksCount);
        rs = DB_Utils.runQuery("SELECT COUNT(*) FROM users");
        expectedResult = DB_Utils.getFirstRowFirstColumn();
        Assert.assertEquals(expectedResult, usersCount);
    }

}
