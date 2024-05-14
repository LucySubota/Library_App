package com.library.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(

        plugin = {
                "html:target/cucumber-reports.html",
                "rerun:target/rerun.txt",
                "json:target/cucumber.json",
                "pretty"
        },
        features = "src/test/resources/features",
        glue = "com/library/step_definitions",
        dryRun = false,
        tags = ""
        // publish = true, // Enable publishing of test results
        // publishUrl = "https://reporting-server.example.com" // Specify the URL of the reporting server

)
public class CukesRunner {}
