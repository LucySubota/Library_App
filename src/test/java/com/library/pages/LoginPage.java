package com.library.pages;

import com.library.utilities.ConfigurationReader;
import com.library.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage{

    public LoginPage(){
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy (id = "inputEmail")
    public WebElement usernameBox;

    @FindBy (id = "inputPassword")
    public WebElement passwordBox;

    @FindBy (xpath = "//button[.='Sign in']")
    public WebElement signInButton;

    public void login(String username, String password){
        usernameBox.sendKeys(username);
        passwordBox.sendKeys(password);
        signInButton.click();
    }

    public void login(String userType){
        String username;
        String password;
        switch (userType) {
            case "librarian":
            case "Librarian":
           username  = ConfigurationReader.getProperty("lib22_user");
           password  = ConfigurationReader.getProperty("lib22_pass");
           break;
            case "student":
            case "Student":
           username  = ConfigurationReader.getProperty("student55_user");
           password  = ConfigurationReader.getProperty("student55_pass");
           break;
            default:
            throw new IllegalArgumentException("NO SUCH USER TYPE FOUND");
        }
        login(username, password);
    }

}
