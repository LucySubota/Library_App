package com.library.pages;

import com.library.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class UsersPage extends BasePage {

    public UsersPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(css = "input[type='search']")
    public WebElement searchInput;

    @FindBy(xpath = "//table[@id='tbl_users']/tbody//td[1]/a")
    public WebElement editUserButtonForFirstUserInList;

    @FindBy(xpath = "//table[@id='tbl_users']/tbody/tr[1]/td[3]")
    public WebElement fullNameForFirstUserInList;

    @FindBy(xpath = "(//select[@id='status'])[1]")
    public WebElement statusDropdownInForm;

    @FindBy(xpath = "//select[@id='user_status']")
    public WebElement statusDropdownOnMainPage;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement saveChangesButton;

    @FindBy(xpath = "//div[@id='toast-container']/div/div")
    public WebElement theUserUpdatedMessage;


    public void chooseStatusInForm(String status){
            Select dropdown = new Select(statusDropdownInForm);
            if(status.equalsIgnoreCase("active")){
                dropdown.selectByVisibleText("ACTIVE");
            }else if(status.equalsIgnoreCase("inactive")){
                dropdown.selectByVisibleText("INACTIVE");
            }else{
                throw new IllegalArgumentException("NO SUCH STATUS OPTION: MUST BE ACTIVE OR INACTIVE");
            }
    }

    public void chooseStatusOnMainPage(String status){
        Select dropdown = new Select(statusDropdownOnMainPage);
        if(status.equalsIgnoreCase("active")){
            dropdown.selectByVisibleText("ACTIVE");
        }else if(status.equalsIgnoreCase("inactive")){
            dropdown.selectByVisibleText("INACTIVE");
        }else{
            throw new IllegalArgumentException("NO SUCH STATUS OPTION: MUST BE ACTIVE OR INACTIVE");
        }
    }

    public String currentlyChosenStatusInForm(){
        Select dropdown = new Select(statusDropdownInForm);
        if(dropdown.getFirstSelectedOption().getText().equalsIgnoreCase("active")){
            return "ACTIVE";
        }else if(dropdown.getFirstSelectedOption().getText().equalsIgnoreCase("inactive")){
            return "INACTIVE";
        }else{
            throw new RuntimeException("ERROR WITH FIGURING CURRENTLY CHOSEN STATUS");
        }
    }

}
