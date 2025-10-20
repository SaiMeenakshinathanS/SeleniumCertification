package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import pageobjects.SubPage;
import utils.DriverUtil;

public class TestScenarioTwoStep {
    private SubPage subPage = new SubPage(DriverUtil.getDriver());

    @When("User set the slider to {int}")
    public void userSetTheSliderTo(int value) {
        subPage.setSliderValue(value);
    }

    @Then("Verify the slider is set to {int}")
    public void verifySliderValue(int expectedValue) {
        subPage.verifySliderValue(expectedValue);
    }
}
