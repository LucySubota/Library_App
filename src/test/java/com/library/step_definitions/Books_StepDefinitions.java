package com.library.step_definitions;

import com.library.pages.BooksPage;
import com.library.pages.LibrarianDashboardPage;
import com.library.pages.LoginPage;
import com.library.pages.StudentDashboardPage;
import com.library.utilities.BrowserUtils;
import com.library.utilities.ConfigurationReader;
import com.library.utilities.DB_Utils;
import com.library.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.ResultSet;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Books_StepDefinitions {

    LoginPage loginPage = new LoginPage();
    BooksPage booksPage = new BooksPage();
    LibrarianDashboardPage dashboardPage = new LibrarianDashboardPage();
    StudentDashboardPage studentPage = new StudentDashboardPage();
    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));
    String bookISBN;
    String bookYear;
    String bookAuthor;
    String bookCategory;
    String borrowedBookName;


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

    @When("the librarian click to add book")
    public void the_librarian_click_to_add_book() {
        booksPage.addBookButton.click();
    }

    @When("the librarian enter book name {string}")
    public void the_librarian_enter_book_name(String bookName) {
        wait.until(ExpectedConditions.visibilityOf(booksPage.bookNameInput));
        booksPage.bookNameInput.sendKeys(bookName);
    }

    @When("the librarian enter ISBN {string}")
    public void the_librarian_enter_isbn(String isbn) {
        bookISBN = isbn;
        booksPage.bookISBNinput.sendKeys(isbn);
    }

    @When("the librarian enter year {string}")
    public void the_librarian_enter_year(String year) {
        bookYear = year;
        booksPage.bookYearInput.sendKeys(year);
    }

    @When("the librarian enter author {string}")
    public void the_librarian_enter_author(String author) {
        bookAuthor = author;
        booksPage.bookAuthorInput.sendKeys(author);
    }

    @When("the librarian choose the book category {string}")
    public void the_librarian_choose_the_book_category(String category) {
        bookCategory = category;
        booksPage.selectCategoryForNewBook(category);
        BrowserUtils.sleep(1);
    }

    @When("the librarian click to save changes")
    public void the_librarian_click_to_save_changes() {
        booksPage.saveChangesButton.click();
    }

    @Then("verify {string} message is displayed")
    public void verifyMessageIsDisplayed(String message) {
        wait.until(ExpectedConditions.visibilityOf(booksPage.popUpMessage));
        Assert.assertEquals(message, booksPage.popUpMessage.getText());
    }

    @Then("verify {string} information must match with DB")
    public void verify_information_must_match_with_db(String bookName) {
        ResultSet rs = DB_Utils.runQuery("SELECT books.name as Name, isbn, year, author, book_categories.name as Category from books join book_categories on books.book_category_id = book_categories.id where books.name = 'Clean Code'");
        List<Map<String,String>> booksFromSearch = booksPage.getListOfBooksBySearchRequestFromDB(bookName);
        Map<String, String> newBook = new LinkedHashMap<>();
        newBook.put("isbn", bookISBN);
        newBook.put("name", bookName);
        newBook.put("author", bookAuthor);
        newBook.put("category", bookCategory);
        newBook.put("year", bookYear);
        Assert.assertTrue(booksFromSearch.contains(newBook));
    }

    @When("the user searches book name called {string}")
    public void the_user_searches_book_name_called(String bookName) {
        borrowedBookName = bookName;
        studentPage.searchBox.sendKeys(bookName + Keys.ENTER);
    }

    @When("the user clicks Borrow Book")
    public void the_user_clicks_borrow_book() {
        BrowserUtils.sleep(1);
        studentPage.borrowBookButtonForFirstBookInList.click();
    }

    @Then("verify that book is shown in \"Borrowing Books” page")
    public void verify_that_book_is_shown_in_borrowing_books_page() {
        studentPage.borrowingBooksButton.click();
    }

    @Then("verify logged student has same book in database")
    public void verify_logged_student_has_same_book_in_database() {
        Assert.assertEquals(borrowedBookName, studentPage.nameOfFirstBorrowedBookInList.getText());
        studentPage.returnBook(borrowedBookName);
    }

}
