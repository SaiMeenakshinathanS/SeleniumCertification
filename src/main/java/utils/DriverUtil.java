package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.SessionId;

import java.net.URL;
import java.net.MalformedURLException;

public class DriverUtil {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    // store LambdaTest session id per thread for parallel runs
    private static ThreadLocal<String> ltSessionId = new ThreadLocal<>();

    private static WebDriver createDriver(String driverName, String platform) {
        try {
            // Resolve credentials from environment variables or system properties (support multiple common names)
            String ltUser = System.getenv("LT_USERNAME");
            if (ltUser == null || ltUser.isEmpty()) {
                ltUser = System.getenv("LAMBDATEST_USERNAME");
            }
            if (ltUser == null || ltUser.isEmpty()) {
                ltUser = System.getProperty("LT_USERNAME");
            }
            if (ltUser == null || ltUser.isEmpty()) {
                ltUser = System.getProperty("lambdatest.username");
            }

            String ltKey = System.getenv("LT_ACCESS_KEY");
            if (ltKey == null || ltKey.isEmpty()) {
                ltKey = System.getenv("LAMBDATEST_ACCESS_KEY");
            }
            if (ltKey == null || ltKey.isEmpty()) {
                ltKey = System.getProperty("LT_ACCESS_KEY");
            }
            if (ltKey == null || ltKey.isEmpty()) {
                ltKey = System.getProperty("lambdatest.accesskey");
            }

            String useLT = System.getenv("USE_LAMBDATEST");
            if (useLT == null || useLT.isEmpty()) {
                useLT = System.getProperty("USE_LAMBDATEST", "false");
            }

            if (useLT != null && useLT.equalsIgnoreCase("true") && ltUser != null && ltKey != null) {
                String ltGridUrl = "https://" + ltUser + ":" + ltKey + "@hub.lambdatest.com/wd/hub";

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

                RemoteWebDriver remote = new RemoteWebDriver(new URL(ltGridUrl), capabilities);
                driver.set(remote);

                // fetch and store session id for this thread and print it (explicit format expected for submission)
                SessionId sid = remote.getSessionId();
                if (sid != null) {
                    ltSessionId.set(sid.toString());
                    System.out.println("LT_SESSION_ID=" + sid.toString());
                }

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

    public static String getSessionId() {
        return ltSessionId.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            System.out.println("[DriverUtil] Quitting driver on thread: " + Thread.currentThread().getId());
            driver.get().quit();
            driver.remove();
            ltSessionId.remove();
        } else {
            System.out.println("[DriverUtil] No driver to quit on thread: " + Thread.currentThread().getId());
        }
    }
}
