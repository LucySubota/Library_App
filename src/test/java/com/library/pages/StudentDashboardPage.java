package com.library.pages;

import com.library.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class StudentDashboardPage extends BasePage{

    public StudentDashboardPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy (id = "tbl_books")
    public WebElement booksTable;

}
