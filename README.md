# Selenium LambdaTest Parallel Execution Assignment

## Overview
This project demonstrates running Selenium tests in parallel on LambdaTest Cloud Selenium Grid using TestNG and Cucumber. The setup is ready for use in Gitpod for a seamless cloud development experience.

## Prerequisites
- LambdaTest account (get your username and access key)
- Gitpod account

## Setup Instructions

### 1. Set LambdaTest Credentials in Gitpod
In your Gitpod terminal, run:
```
export LT_USERNAME=your_lambdatest_username
export LT_ACCESS_KEY=your_lambdatest_access_key
```

### 2. Run the Tests
```
mvn test -DsuiteXmlFile=testng.xml
```

### 3. Parallel Execution
Tests are configured to run in parallel on different browsers/platforms via `testng.xml`.

### 4. LambdaTest Session IDs
After each test, the LambdaTest session ID (Test ID) will be printed in the console output. Use these IDs for reporting and submission.

### 5. Gitpod Configuration
- `.gitpod.yml` and `.gitpod.Dockerfile` are provided for one-click setup.

## Notes
- Update your LambdaTest credentials before running tests.
- For any issues, check the console output for errors and session IDs.

## References
- [LambdaTest Selenium Grid Docs](https://www.lambdatest.com/support/docs/selenium-automation-on-lambdatest/)
- [Gitpod Docs](https://www.gitpod.io/docs)

