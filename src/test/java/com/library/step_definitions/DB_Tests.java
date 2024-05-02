package com.library.step_definitions;

import com.library.pages.BooksPage;
import com.library.pages.LoginPage;
import com.library.utilities.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class DB_Tests {

    LoginPage loginPage;
    BooksPage booksPage;

    @Test
    public void test1() {
        loginPage = new LoginPage();
        Driver.getDriver().get(ConfigurationReader.getProperty("qa2_url"));
        loginPage.login("librarian");
        DB_Utils.createConnection();

        ResultSet rs = DB_Utils.runQuery("SELECT COUNT(*) FROM books");
        String expectedBooksCount = DB_Utils.getFirstRowFirstColumn();
        WebElement actualBooksCount = Driver.getDriver().findElement(By.cssSelector("#book_count"));
        Assert.assertEquals(expectedBooksCount, actualBooksCount.getText());
        System.out.println("expectedBooksCount = " + expectedBooksCount);
        System.out.println("actualBooksCount = " + actualBooksCount.getText());

        DB_Utils.destroy();
    }

    @Test
    public void test2() throws SQLException {
        booksPage = new BooksPage();
        DB_Utils.createConnection();

        List<Map<String, String>> expectedListOfBooks = booksPage.getListOfBooksBySearchRequestFromDB("Harry Potter");

        /*for (Map<String, String> each : expectedListOfBooks) {
            System.out.println(each);
            System.out.println("----------------------");
        }*/

        DB_Utils.destroy();

        Driver.getDriver().get(ConfigurationReader.getProperty("qa2_url"));
        loginPage.login("librarian");
        loginPage.booksButton.click();
        booksPage.searchInput.sendKeys("Harry Potter" + Keys.ENTER);
        BrowserUtils.sleep(2);

        List<Map<String, String>> actualListOfBooks = booksPage.getListOfBooksBySearchRequestFromUI();

        /*
        for (Map<String, String> each : listOfBooks) {
            System.out.println(each);
            System.out.println("----------------------");
        }
         */

        Assert.assertEquals(expectedListOfBooks, actualListOfBooks);
    }

    @Test
    public void test3() {

        Driver.getDriver().get(ConfigurationReader.getProperty("qa2_url"));
        loginPage.login("librarian");
        loginPage.booksButton.click();
        booksPage.searchInput.sendKeys("Harry Potter" + Keys.ENTER);
        BrowserUtils.sleep(2);

        List<Map<String, String>> listOfBooks = booksPage.getListOfBooksBySearchRequestFromUI();

        for (Map<String, String> each : listOfBooks) {
            System.out.println(each);
            System.out.println("----------------------");
        }

    }


    @Test
    public void tryingDB_ReaderClass(){

        String query = DB_FileReader.getQuery("get all books");
        System.out.println(query);
    }

    @Test
    public void testAllBrokenLinks(){

        Driver.getDriver().get("http://54.80.233.175:8000/spartans");
        List<WebElement> links = Driver.getDriver().findElements(By.tagName("a"));
        for (WebElement link : links) {
            String url = link.getAttribute("href");
            System.out.println(url);
            if(url!= null && !url.isEmpty()) {
                Response response = RestAssured.get(url);
                Assert.assertEquals(200, response.statusCode());
            }
        }

        Driver.closeDriver();
    }

}
