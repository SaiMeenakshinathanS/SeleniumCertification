package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Hooks {
    @Before
    public void setUp() {
        DriverUtil.getDriver(); // Initialize driver before each scenario
    }

    @After
    public void tearDown() {
        WebDriver driver = DriverUtil.getDriver();
        if (driver instanceof RemoteWebDriver) {
            String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
            System.out.println("LambdaTest Session ID: " + sessionId);
        }
        DriverUtil.quitDriver(); // Quit driver after each scenario
    }
}
