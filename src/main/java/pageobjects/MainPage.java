package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.DriverUtil;
import utils.ElementUtil;


public class MainPage {
    private WebDriver driver;
    private ElementUtil elementUtil;

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.elementUtil = new ElementUtil(driver);
    }

    public void clickSimpleFormDemoElement(String element) {
        By simpleFormDemoLink = By.partialLinkText(element);
        elementUtil.getElement(simpleFormDemoLink).click();
    }

}
