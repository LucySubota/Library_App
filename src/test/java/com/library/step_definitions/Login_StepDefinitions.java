package com.library.step_definitions;

import com.library.pages.LibrarianDashboardPage;
import com.library.pages.LoginPage;
import com.library.pages.StudentDashboardPage;
import com.library.utilities.ConfigurationReader;
import com.library.utilities.DB_Utils;
import com.library.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Login_StepDefinitions {

    LoginPage loginPage;
    LibrarianDashboardPage dashboardPage;
    StudentDashboardPage studentDashboardPage;
    String actualUsername;
    String email;
    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        Driver.getDriver().get(ConfigurationReader.getProperty("qa2_url"));
    }

    @When("I login as a librarian")
    public void i_login_as_a_librarian() {
        loginPage = new LoginPage();
        loginPage.login("librarian");
    }

    @When("I login as a student")
    public void i_login_as_a_student() {
        loginPage.login("student");
    }

    @Then("books should be displayed")
    public void books_should_be_displayed() {
        studentDashboardPage = new StudentDashboardPage();
        Assert.assertTrue(studentDashboardPage.booksTable.isDisplayed());
    }

    @When("I enter username {string}")
    public void i_enter_username(String username) {
        loginPage.usernameBox.sendKeys(username);
    }

    @When("I enter password {string}")
    public void i_enter_password(String password) {
        loginPage.passwordBox.sendKeys(password);
    }

    @When("click the sign in button")
    public void click_the_sign_in_button() {
        loginPage.signInButton.click();
    }

    @When("there should be {int} users")
    public void there_should_be_users(Integer numOfUsers) {
        dashboardPage = new LibrarianDashboardPage();
        Assert.assertEquals(String.valueOf(numOfUsers), dashboardPage.usersIcon.getText());
    }

    @Then("dashboard should be displayed")
    public void dashboard_should_be_displayed() {
        System.out.println(Driver.getDriver().getCurrentUrl());
        Assert.assertTrue(Driver.getDriver().getCurrentUrl().endsWith("dashboard"));
    }

    @Given("the user logged in  {string} and {string}")
    public void the_user_logged_in_and(String username, String password) {
        email = username;
        loginPage = new LoginPage();
        loginPage.login(username, password);
    }

    @When("user gets username from user fields")
    public void user_gets_username_from_user_fields() {
        dashboardPage = new LibrarianDashboardPage();
        wait.until(ExpectedConditions.visibilityOf(dashboardPage.userIcon));
        actualUsername = dashboardPage.userIcon.getText();
    }

    @Then("the username should be same with database")
    public void the_username_should_be_same_with_database() {
        DB_Utils.runQuery("SELECT full_name FROM users WHERE EMAIL = '"+email+"'");
        String expectedUserName = DB_Utils.getFirstRowFirstColumn();
        Assert.assertEquals(expectedUserName, actualUsername);
    }



}
