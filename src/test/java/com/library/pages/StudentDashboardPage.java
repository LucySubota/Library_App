package com.library.pages;

import com.library.utilities.Driver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class StudentDashboardPage extends BasePage{

    Actions actions = new Actions(Driver.getDriver());

    public StudentDashboardPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy (id = "tbl_books")
    public WebElement booksTable;

    @FindBy (css = "input[type='search']")
    public WebElement searchBox;

    @FindBy (xpath = "//table[@id='tbl_books']/tbody/tr[1]/td[1]/a")
    public WebElement borrowBookButtonForFirstBookInList;

    @FindBy (xpath = "//table[@id='borrowed_list']/tbody/tr[1]/td[2]")
    public WebElement nameOfFirstBorrowedBookInList;

    public void returnBook(String bookName){
        List<WebElement> booksNames = Driver.getDriver().findElements(By.xpath("//table[@id='borrowed_list']/tbody/tr/td[2]"));
        for (int i = 0; i < booksNames.size(); i++) {
            if(booksNames.get(i).getText().equalsIgnoreCase(bookName)){
                WebElement returnButton = Driver.getDriver().findElement( By.xpath("//table[@id='borrowed_list']/tbody/tr["+(i+1)+"]/td[1]/a"));
                actions.moveToElement(returnButton).click().perform();
            }
        }
    }


}
