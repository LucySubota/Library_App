package com.library.step_definitions;

import com.library.utilities.DB_Utils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataVerification_StepDefinitions {

    List<String> dataFromDB;
    String mostPopularGenreFromDB;

    @When("Execute query to get all IDs from users")
    public void execute_query_to_get_all_i_ds_from_users() {
        ResultSet rs = DB_Utils.runQuery("SELECT id FROM users");
        dataFromDB = DB_Utils.getColumnDataAsList(1);
    }

    @Then("verify all users have unique ID")
    public void verify_all_users_have_unique_id() {
        Set<String> uniqueIDs = new HashSet<>(dataFromDB);
        Assert.assertEquals(dataFromDB.size(), uniqueIDs.size());
    }

    @When("Execute query to get all columns")
    public void execute_query_to_get_all_columns() {
        ResultSet rs = DB_Utils.runQuery("SELECT * FROM users");
        dataFromDB = DB_Utils.getAllColumnNamesAsList();
    }

    @Then("verify the below columns are listed in result")
    public void verify_the_below_columns_are_listed_in_result(List<String> columns) {
        Assert.assertEquals(columns, dataFromDB);
    }

    @When("I execute query to find most popular book genre")
    public void i_execute_query_to_find_most_popular_book_genre() {
        ResultSet rs = DB_Utils.runQuery("SELECT book_categories.name, COUNT(*)\n" +
                "FROM books JOIN book_categories\n" +
                "ON books.book_category_id = book_categories.id\n" +
                "JOIN book_borrow ON books.id = book_borrow.book_id\n" +
                "GROUP BY 1\n" +
                "ORDER BY 2 DESC");
        mostPopularGenreFromDB = DB_Utils.getFirstRowFirstColumn();
    }

    @Then("I verify {string} is the most popular book genre.")
    public void i_verify_is_the_most_popular_book_genre(String expectedMostPopularGenre) {
        Assert.assertEquals(expectedMostPopularGenre, mostPopularGenreFromDB);
    }

}
