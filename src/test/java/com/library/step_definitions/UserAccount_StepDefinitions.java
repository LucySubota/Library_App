package com.library.step_definitions;

import com.library.pages.LoginPage;
import com.library.pages.UsersPage;
import com.library.utilities.BrowserUtils;
import com.library.utilities.DB_Utils;
import com.library.utilities.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.ResultSet;
import java.time.Duration;

public class UserAccount_StepDefinitions {

    LoginPage loginPage;
    UsersPage usersPage;
    WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(5));
    String fullName;

    @When("I login using {string} and {string}")
    public void i_login_using_and(String email, String password) {
        loginPage = new LoginPage();
        loginPage.login(email, password);
    }

    @Then("account holder name should be {string}")
    public void account_holder_name_should_be(String name) {
        wait.until(ExpectedConditions.titleIs("Library"));
        Assert.assertEquals(name, loginPage.accountHolderName.getText());
    }

    @When("the user clicks Edit User button")
    public void the_user_clicks_edit_user_button() {
        //usersPage.searchInput.sendKeys("Simonne Wolff" + Keys.ENTER);
        usersPage = new UsersPage();
        fullName = usersPage.fullNameForFirstUserInList.getText();
        usersPage.editUserButtonForFirstUserInList.click();
    }

    @When("the user changes user status {string} to {string}")
    public void the_user_changes_user_status_to(String currentStatus, String changeStatus) {
        wait.until(ExpectedConditions.visibilityOf(usersPage.statusDropdownInForm));
        Assert.assertEquals(currentStatus, usersPage.currentlyChosenStatusInForm());
        wait.until(ExpectedConditions.elementToBeClickable(usersPage.statusDropdownInForm));
        usersPage.chooseStatusInForm(changeStatus);
    }

    @When("the user clicks save changes button")
    public void the_user_clicks_save_changes_button() {
        BrowserUtils.sleep(2);
        usersPage.saveChangesButton.click();
        BrowserUtils.sleep(2);
    }

    @Then("{string} message should appear")
    public void message_should_appear(String message) {
        wait.until(ExpectedConditions.visibilityOf(usersPage.theUserUpdatedMessage));
        Assert.assertTrue(usersPage.theUserUpdatedMessage.isDisplayed() && usersPage.theUserUpdatedMessage.getText().equals(message));
    }

    @Then("the users should see same status {string} for related user in database")
    public void the_users_should_see_same_status_for_related_user_in_database(String status) {
        ResultSet rs = DB_Utils.runQuery("SELECT status FROM users where full_name = '"+fullName+"'");
        String expectedStatus = DB_Utils.getFirstRowFirstColumn();
        Assert.assertEquals(expectedStatus, status);
    }

    @Then("the user changes current user status {string} to {string}")
    public void the_user_changes_current_user_status_to(String currentStatus, String changeStatus) {
        wait.until(ExpectedConditions.visibilityOf(usersPage.statusDropdownOnMainPage));
        usersPage.chooseStatusOnMainPage(currentStatus);
        usersPage.searchInput.sendKeys(fullName + Keys.ENTER);
        BrowserUtils.sleep(2);
        usersPage.editUserButtonForFirstUserInList.click();
        wait.until(ExpectedConditions.elementToBeClickable(usersPage.statusDropdownInForm));
        usersPage.chooseStatusInForm(changeStatus);
        BrowserUtils.sleep(1);
        usersPage.saveChangesButton.click();
        ResultSet rs = DB_Utils.runQuery("SELECT status FROM users where full_name = '"+fullName+"'");
        String expectedStatus = DB_Utils.getFirstRowFirstColumn();
        BrowserUtils.sleep(2);
        Assert.assertEquals(expectedStatus, changeStatus);
    }


}
