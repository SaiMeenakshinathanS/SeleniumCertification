Feature: Test Scenarios for Selenium 101 Assignment

  Scenario Outline: 1. Validate the url and the text message
    Given User navigate to the "<url>"
    When User click the "<subUrl>"
    Then Verify the url contains "<expectedUrlPart>"
    When User enter the "<text>" in the Enter Message field
    Then User validates the same "<text>"

    Examples:
      | url                                             | subUrl           | expectedUrlPart  | text                  |
      | https://www.lambdatest.com/selenium-playground/ | Simple Form Demo | simple-form-demo | Welcome to LambdaTest |


  Scenario Outline: 2. Set the slider to some value and verify the value
    Given User navigate to the "<url>"
    When User click the "<subUrl>"
    When User set the slider to <value>
    Then Verify the slider is set to <targetValue>

    Examples:
      | url                                             | subUrl              | value | targetValue |
      | https://www.lambdatest.com/selenium-playground/ | Drag & Drop Sliders | 93    | 95          |


  Scenario Outline: 3. Fill the input form and verify the submission message
    Given User navigate to the "<url>"
    When User click the "<subUrl>"
    When User click the submit button
    Then Verify the "<errorMessage>"
    And User submits the form "<name>", "<email>", "<password>", "<company>", "<website>", "<country>", "<city>", "<address1>", "<address2>", "<state>", "<zip>"
    Then Verify the submission message "<submissionMessage>"

    Examples:
      | url                                             | subUrl            | errorMessage                | name | email             | password | company | website         | country | city | address1 | address2 | state | zip    | submissionMessage                                          |
      | https://www.lambdatest.com/selenium-playground/ | Input Form Submit | Please fill out this field. | Sai  | Saitest@gmail.com | Test123  | Test    | https://sai.com | India   | Tvl  | Street 1 | Street 2 | TN    | 500081 | Thanks for contacting us, we will get back to you shortly. |