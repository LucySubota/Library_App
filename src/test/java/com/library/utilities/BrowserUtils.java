package com.library.utilities;

import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class BrowserUtils {
    public static void switchWindowAndVerify(String expectedInUrl, String expectedTitle){

        for(String each : Driver.getDriver().getWindowHandles()){
            Driver.getDriver().switchTo().window(each);
            if(Driver.getDriver().getCurrentUrl().contains(expectedInUrl)){
                break;
            }
        }
        Assert.assertTrue(Driver.getDriver().getTitle().contains(expectedTitle));
    }

    // excepts int seconds and executes Thread.sleep() method
    public static void sleep(int second){
        second *=1000;
        try{
            Thread.sleep(second);
        }catch (InterruptedException e){

        }
    }

    public static void verifyTitle(String expectedTitle){
        Assert.assertEquals(Driver.getDriver().getTitle(), expectedTitle);
    }

    public static void verifyTitleContains(String expectedTitleContains){
        Assert.assertTrue(Driver.getDriver().getTitle().contains(expectedTitleContains));
    }

    // gets text of every option in dropdown and returns as a List of String
    public static List<String> dropdownOptions_as_STRING(WebElement dropdown){
        Select allOptions = new Select(dropdown);
        List<WebElement> options = allOptions.getOptions();
        List<String> optionsInText = new ArrayList<>();
        for(WebElement each : options){
            optionsInText.add(each.getText());
        }
        return optionsInText;
    }
}
