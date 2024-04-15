package com.library.step_definitions;

import com.library.utilities.ConfigurationReader;
import com.library.utilities.DB_Utils;
import com.library.utilities.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @Before("@db")
    public void setupDB(){
        // setting up DB connection
        DB_Utils.createConnection();
    }

    @After("@db")
    public void closeDB(){
        // closing DB connection
        DB_Utils.destroy();
        System.out.println("CONNECTION CLOSED");
    }

    @Before ("@ui")
    public void setupUI(){
        System.out.println("set up ui");
        Driver.getDriver().get(ConfigurationReader.getProperty("qa2_url"));
    }

    @After
    public void tearDown(Scenario scenario){
        System.out.println("After method is running");
        if(scenario.isFailed()){
            byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());
        }

        Driver.closeDriver();
    }

}
