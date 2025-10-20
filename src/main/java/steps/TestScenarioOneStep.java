package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import pageobjects.MainPage;
import pageobjects.SubPage;
import utils.DriverUtil;

//First Scenario
public class TestScenarioOneStep {
    private MainPage mainPage = new MainPage(DriverUtil.getDriver());
    private SubPage subPage = new SubPage(DriverUtil.getDriver());

    @Given("User navigate to the {string}")
    public void userNavigation(String url) {
        DriverUtil.getDriver().get(url);
    }

    @When("User click the {string}")
    public void userClick(String xpath) {
        mainPage.clickSimpleFormDemoElement(xpath);
    }

    @Then("Verify the url contains {string}")
    public void linkVerification(String expectedUrl) {
        String actualUrl = DriverUtil.getDriver().getCurrentUrl();
        assert actualUrl.contains(expectedUrl);
    }

    @When("User enter the {string} in the Enter Message field")
    public void userEnterTextTheEnterMessageField(String message) {
        subPage.enterMessageFieldAndClickCheckedValue(message);
    }

    @Then("User validates the same {string}")
    public void validateText(String expectedMessage) {
        subPage.messageValidation(expectedMessage);
    }
}
