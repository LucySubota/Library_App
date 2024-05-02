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
        // These are environment variables for security, username for librarian is a PC variable and the rest are IntelliJ variables (being autocommited to GitHub)
        String username = System.getenv(userType+"_username");
        String password = System.getenv(userType+"_password");
        login(username, password);
    }

}
