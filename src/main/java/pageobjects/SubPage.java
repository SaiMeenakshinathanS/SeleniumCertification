package pageobjects;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import utils.DriverUtil;
import utils.ElementUtil;


public class SubPage {
    private WebDriver driver;
    private ElementUtil elementUtil;

    public SubPage(WebDriver driverName) {
        this.driver = driverName;
        this.elementUtil = new ElementUtil(driverName);
    }

    private By enterMessage= By.id("user-message");
    private By checkedValue= By.id("showInput");
    private By validationMessage= By.id("message");
    private By slider= By.cssSelector("input[type='range']");
    private By sliderValue=By.id("rangeSuccess");
    private By submitButton=By.xpath("//button[text()='Submit']");
    private By name=By.id("name");
    private By email=By.id("inputEmail4");
    private By password=By.id("inputPassword4");
    private By company=By.id("company");
    private By website=By.id("websitename");
    private By selectElement=By.name("country");
    private By city=By.id("inputCity");
    private By address1=By.id("inputAddress1");
    private By address2=By.id("inputAddress2");
    private By state=By.id("inputState");
    private By zip=By.id("inputZip");
    private By thanksMessage=By.xpath("//p[contains(text(),'Thanks for contacting us, we will get back to you shortly.')]");

    public void enterMessageFieldAndClickCheckedValue(String message) {
        elementUtil.getElement(enterMessage).sendKeys(message);
        elementUtil.getClickableElement(checkedValue).click();
    }

    public void messageValidation(String expectedMessage) {
        String actualMessage = elementUtil.getElement(validationMessage).getText();
        assert actualMessage.equalsIgnoreCase(expectedMessage);
    }

    // Set the value of a specific slider (by index, e.g., 3 for the 4th slider)
    public void setSliderValue(int targetValue) {
        WebElement sliderElement = driver.findElements(slider).get(2);
        int min = Integer.parseInt(sliderElement.getAttribute("min"));
        int max = Integer.parseInt(sliderElement.getAttribute("max"));
        int sliderWidth = sliderElement.getSize().getWidth();
        double ratio = (double)(targetValue - min) / (max - min);
        int xOffset = (int)(ratio * sliderWidth) - (sliderWidth / 2);
        Actions actions = new Actions(driver);
        actions.moveToElement(sliderElement)
                .clickAndHold()
                .moveByOffset(xOffset, 0)
                .release()
                .perform();
    }

    // Verify the value of a specific slider (by index)
    public void verifySliderValue(int expectedValue) {
        WebElement sliderElement = driver.findElements(slider).get(2);
        String actualValue = sliderElement.getAttribute("value");
        System.out.println("Expected Slider Value: " + expectedValue);
        System.out.println("Actual Slider Value: " + actualValue);
        assert actualValue.equals(String.valueOf(expectedValue));
    }

    public void clickSubmitButton(){
        elementUtil.getClickableElement(submitButton).click();
    }

    public void errorMessagevalidation(String expectedMessage){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String validationMessage = (String) js.executeScript("return arguments[0].validationMessage;", elementUtil.getElement(name));
        System.out.println("Actual validation message: " + validationMessage);
        System.out.println("Expected validation message: " + expectedMessage);
        assert validationMessage.equalsIgnoreCase(expectedMessage);
    }

    public void formSubmission(String personName,String personEmail,String personPassword,String personCompany,String personWebsite,String personCountry,String personCity,String addr1,String addr2,String personState,String zipcode){
        elementUtil.getElement(name).sendKeys(personName);
        elementUtil.getElement(email).sendKeys(personEmail);
        elementUtil.getElement(password).sendKeys(personPassword);
        elementUtil.getElement(company).sendKeys(personCompany);
        elementUtil.getElement(website).sendKeys(personWebsite);
        Select select=new Select(elementUtil.getElement(selectElement));
        select.selectByValue("IN");
        elementUtil.getElement(city).sendKeys(personCity);
        elementUtil.getElement(address1).sendKeys(addr1);
        elementUtil.getElement(address2).sendKeys(addr2);
        elementUtil.getElement(state).sendKeys(personState);
        elementUtil.getElement(zip).sendKeys(zipcode);
    }

    public void validateFormSubmissionMessage(String expectedMessage){
        assert elementUtil.getElement(thanksMessage).getText().equalsIgnoreCase(expectedMessage);
    }
}
