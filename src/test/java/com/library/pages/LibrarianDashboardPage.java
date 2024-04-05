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





}
