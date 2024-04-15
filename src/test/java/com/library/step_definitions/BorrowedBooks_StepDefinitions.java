package com.library.step_definitions;

import com.library.pages.LibrarianDashboardPage;
import com.library.utilities.DB_Utils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.sql.ResultSet;

public class BorrowedBooks_StepDefinitions {

    LibrarianDashboardPage dashboardPage;
    String borrowedBooksCount;

    @When("the librarian gets borrowed books number")
    public void the_librarian_gets_borrowed_books_number() {
        dashboardPage = new LibrarianDashboardPage();
        borrowedBooksCount = dashboardPage.borrowedBooksIcon.getText();
    }

    @Then("borrowed books number information must match with DB")
    public void borrowed_books_number_information_must_match_with_db() {
        ResultSet rs = DB_Utils.runQuery("SELECT COUNT(*) FROM book_borrow WHERE is_returned = 0");
        String borrowedBooksCountFromDB = DB_Utils.getFirstRowFirstColumn();
        System.out.println("borrowedBooksCountFromDB = " + borrowedBooksCountFromDB);
        System.out.println("borrowedBooksCount = " + borrowedBooksCount);
        Assert.assertEquals(borrowedBooksCountFromDB, borrowedBooksCount);
    }

}
