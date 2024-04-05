package com.library.pages;

import com.library.utilities.DB_Utils;
import com.library.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BooksPage extends BasePage {

    public BooksPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(css = "#book_categories")
    public WebElement booksCategories;

    @FindBy(xpath = "//input[@type='search']")
    public WebElement searchInput;


    public List<String> getListOfBooksCategories_String() {
        Select categories = new Select(booksCategories);
        List<String> booksCategories = new ArrayList<>();
        for (WebElement each : categories.getOptions()) {
            booksCategories.add(each.getText());
        }
        booksCategories.remove(0);
        return booksCategories;
    }

    /*
        returns a list of all books (as Maps) in format isbn/name/author/category/year
        that are matching a search request from Library WebSite
     */
    public List<Map<String, String>> getListOfBooksBySearchRequestFromDB(String searchRequest) {
        List<Map<String, String>> listOfBooks = new ArrayList<>();
        try {
            ResultSet rs = DB_Utils.runQuery("SELECT isbn, books.name as Name, author, book_categories.name as 'Category', year FROM books join book_categories ON books.book_category_id = book_categories.id WHERE books.name LIKE '%" + searchRequest + "%' ORDER BY isbn DESC");
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.beforeFirst();
            while (rs.next()) {
                Map<String, String> book = new LinkedHashMap<>();

                for (int j = 1; j <= rsmd.getColumnCount(); j++) {

                    if (rsmd.getColumnName(j).equalsIgnoreCase("name") && !rs.getString(j).contains(searchRequest)) {

                        book.put("category", rs.getString(j));
                    } else {
                        book.put(rsmd.getColumnName(j), rs.getString(j));
                    }
                }
                listOfBooks.add(book);
            }
            DB_Utils.destroy();
        } catch (SQLException e) {
            DB_Utils.destroy();
            throw new RuntimeException("LIST OF BOOKS WAS NOT CREATED");
        }
        return listOfBooks;
    }

    /*
       returns a list of all books (as Maps) in format isbn/name/author/category/year
       that are matching a search request from Database
    */
    public List<Map<String, String>> getListOfBooksBySearchRequestFromUI() {
        List<Map<String, String>> listOfBooks = new ArrayList<>();

        try {
            int bookMatchesFound = Driver.getDriver().findElements(By.xpath("//table[@id='tbl_books']/tbody/tr")).size();
            List<WebElement> bookColumns = Driver.getDriver().findElements(By.xpath("//table[@id='tbl_books']/tbody/tr[1]/td"));
            List<WebElement> listOfHeaders = Driver.getDriver().findElements(By.xpath("//table[@id='tbl_books']/thead//th"));
            int columnsForBook = Driver.getDriver().findElements(By.xpath("//table[@id='tbl_books']/tbody/tr[1]/td")).size();


            for (int i = 1; i <= bookMatchesFound; i++) {
                Map<String, String> book = new LinkedHashMap<>();

                for (int j = 2; j < columnsForBook; j++) {

                    WebElement currentBooksColumn = Driver.getDriver().findElement(By.xpath("//table[@id='tbl_books']/tbody/tr[" + i + "]/td[" + j + "]"));

                    book.put(listOfHeaders.get(j - 1).getText().toLowerCase(), currentBooksColumn.getText());
                }
                listOfBooks.add(book);
            }

        } catch (Exception e) {
            throw new RuntimeException("LIST OF BOOKS WAS NOT CREATED");
        }
        return listOfBooks;
    }


    public List<Map<String, String>> getListOfBooksBySearchRequestFromUI(String searchValue) {

        searchInput.sendKeys(searchValue + Keys.ENTER);

        List<Map<String, String>> listOfBooks = new ArrayList<>();

        try {
            int bookMatchesFound = Driver.getDriver().findElements(By.xpath("//table[@id='tbl_books']/tbody/tr")).size();
            List<WebElement> bookColumns = Driver.getDriver().findElements(By.xpath("//table[@id='tbl_books']/tbody/tr[1]/td"));
            List<WebElement> listOfHeaders = Driver.getDriver().findElements(By.xpath("//table[@id='tbl_books']/thead//th"));
            int columnsForBook = Driver.getDriver().findElements(By.xpath("//table[@id='tbl_books']/tbody/tr[1]/td")).size();


            for (int i = 1; i <= bookMatchesFound; i++) {
                Map<String, String> book = new LinkedHashMap<>();

                for (int j = 2; j < columnsForBook; j++) {

                    WebElement currentBooksColumn = Driver.getDriver().findElement(By.xpath("//table[@id='tbl_books']/tbody/tr[" + i + "]/td[" + j + "]"));

                    book.put(listOfHeaders.get(j - 1).getText().toLowerCase(), currentBooksColumn.getText());
                }
                listOfBooks.add(book);
            }

        } catch (Exception e) {
            throw new RuntimeException("LIST OF BOOKS WAS NOT CREATED");
        }
        return listOfBooks;
    }

}
