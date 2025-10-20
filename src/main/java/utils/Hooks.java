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
        // If DriverUtil stored a LambdaTest session id, print it in the required standardized format
        String sid = DriverUtil.getSessionId();
        if (sid != null && !sid.isEmpty()) {
            System.out.println("LT_SESSION_ID=" + sid);
        } else {
            // fallback: if driver is a RemoteWebDriver try to fetch session id
            WebDriver driver = DriverUtil.getDriver();
            if (driver instanceof RemoteWebDriver) {
                RemoteWebDriver r = (RemoteWebDriver) driver;
                if (r.getSessionId() != null) {
                    System.out.println("LT_SESSION_ID=" + r.getSessionId().toString());
                }
            }
        }

        DriverUtil.quitDriver(); // Quit driver after each scenario
    }
}
