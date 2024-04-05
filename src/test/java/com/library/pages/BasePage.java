package com.library.pages;

import com.library.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {

    public BasePage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy (xpath = "//a[@id='navbarDropdown']/span")
    public WebElement accountHolderName;

    @FindBy (xpath = "//a[@href='#books']")
    public WebElement booksButton;


    @FindBy (xpath = "//a[@href='#users']")
    public WebElement usersButton;


    @FindBy (xpath = "//a[@href='#dashboard']")
    public WebElement dashboardButton;

    public void goToPage(String pageName){
        switch (pageName){
            case "books": case "Books":
            booksButton.click();
            break;
            case "users": case "Users":
            usersButton.click();
            break;
            case "dashboard": case "Dashboard":
            dashboardButton.click();
            break;
            default:
            throw new IllegalArgumentException("NO SUCH PAGE FOUND");
        }
    }

}
