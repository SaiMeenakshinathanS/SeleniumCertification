package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import pageobjects.SubPage;
import utils.DriverUtil;

public class TestScenarioThreeStep {
    private SubPage subPage = new SubPage(DriverUtil.getDriver());

    @When("User click the submit button")
    public void clickSubmitButton() {
        subPage.clickSubmitButton();
    }

    @Then("Verify the {string}")
    public void verifyErrorMessage(String expectedMessage) {
        subPage.errorMessagevalidation(expectedMessage);
    }

    @When("User submits the form {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}")
    public void submitForm(String name, String email, String password, String company, String website, String country, String city, String address1, String address2, String state, String zip) {
        subPage.formSubmission(name,email,password,company,website,country,city,address1,address2,state,zip);
        subPage.clickSubmitButton();
    }

    @Then("Verify the submission message {string}")
    public void verifyThanksMessage(String expectedMessage) {
        subPage.validateFormSubmissionMessage(expectedMessage);
    }
}
