package com.library.pages;

import com.library.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LibrarianDashboardPage extends BasePage{

    public LibrarianDashboardPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy (id = "user_count")
    public WebElement usersIcon;

    @FindBy (id = "book_count")
    public WebElement booksIcon;

    @FindBy (id = "borrowed_books")
    public WebElement borrowedBooksIcon;

    @FindBy (xpath = "//a[@id='navbarDropdown']/span")
    public WebElement userIcon;

    @FindBy (css = "a[href='#books']")
    public WebElement booksButton;

    @FindBy (css = "a[href='#users']")
    public WebElement usersButton;

    @FindBy (css = "a[href='#dashboard']")
    public WebElement dashboardButton;

    public void navigateToPage(String page){
        page = page.toLowerCase();
        switch (page){
            case "books":
            booksButton.click();
            break;
             case "users":
            usersButton.click();
            break;
             case "dashboard":
            dashboardButton.click();
            break;
            default:
            throw new IllegalArgumentException("NO SUCH BUTTON TO PRESS");
        }
    }



}
