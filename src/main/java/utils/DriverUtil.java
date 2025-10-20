package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;
import java.net.MalformedURLException;

public class DriverUtil {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private static final String LT_USERNAME = System.getenv("LT_USERNAME");
    private static final String LT_ACCESS_KEY = System.getenv("LT_ACCESS_KEY");
    private static final String LT_GRID_URL = "https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + "@hub.lambdatest.com/wd/hub";

    private static WebDriver createDriver(String driverName, String platform) {
        try {
            if (System.getenv("USE_LAMBDATEST") != null && System.getenv("USE_LAMBDATEST").equalsIgnoreCase("true")) {
                DesiredCapabilities capabilities = new DesiredCapabilities();
                if (driverName.equalsIgnoreCase("chrome")) {
                    capabilities.setCapability("browserName", "Chrome");
                    capabilities.setCapability("version", "latest");
                    capabilities.setCapability("platform", platform != null ? platform : "Windows 10");
                } else if (driverName.equalsIgnoreCase("firefox")) {
                    capabilities.setCapability("browserName", "Firefox");
                    capabilities.setCapability("version", "latest");
                    capabilities.setCapability("platform", platform != null ? platform : "macOS Catalina");
                } else {
                    capabilities.setCapability("browserName", "Chrome");
                    capabilities.setCapability("version", "latest");
                    capabilities.setCapability("platform", "Windows 10");
                }
                driver.set(new RemoteWebDriver(new URL(LT_GRID_URL), capabilities));
            } else {
                if(driverName.equalsIgnoreCase("firefox")) {
                    WebDriverManager.firefoxdriver().setup();
                    driver.set(new FirefoxDriver());
                } else if(driverName.equalsIgnoreCase("edge")) {
                    WebDriverManager.edgedriver().setup();
                    driver.set(new EdgeDriver());
                } else {
                    WebDriverManager.chromedriver().setup();
                    driver.set(new ChromeDriver());
                }
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid LambdaTest grid URL", e);
        }
        driver.get().manage().window().maximize();
        return driver.get();
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            String browser = System.getProperty("browser", System.getenv("browser"));
            String platform = System.getProperty("platform", System.getenv("platform"));
            if (browser == null || browser.isEmpty()) {
                browser = "chrome";
            }
            if (platform == null || platform.isEmpty()) {
                platform = "Windows 10";
            }
            return createDriver(browser, platform);
        }
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            System.out.println("[DriverUtil] Quitting driver on thread: " + Thread.currentThread().getId());
            driver.get().quit();
            driver.remove();
        } else {
            System.out.println("[DriverUtil] No driver to quit on thread: " + Thread.currentThread().getId());
        }
    }
}
