# MyRestAssuredAPITestFramework

This repository contains a comprehensive API test automation framework built using Rest Assured, TestNG, and Java. It is designed to test RESTful web services by validating HTTP methods like POST, GET, PUT, and DELETE with full assertion coverage and schema validation.

🚀 Features

✔️ End-to-End test coverage for all major HTTP methods

✔️ Modular design using TestNG and DataProviders

✔️ Request and Response validation with assertions

✔️ JSON schema validation for response structure

✔️ Extent Reports for detailed HTML test reporting

✔️ Easy integration with CI/CD tools like Jenkins or GitHub Actions

🔧 Technologies Used
Java

Rest Assured

TestNG

Maven

ExtentReports

GitHub Actions / Jenkins (for CI/CD)

📁 Project Structure
bash
Copy
Edit
src/
 └── test/
     └── java/
         └── tests/
             ├── AllHttpRequestsCombinedTest.java
             └── ... (additional test classes)
utils/
 └── TestDataProvider.java
 └── JsonSchemaValidatorUtil.java
reports/
 └── ExtentReport.html
🔄 CI/CD Integration
To run tests automatically on push or pull request:

GitHub Actions: Use .github/workflows/api-tests.yml

Jenkins: Trigger build via webhook with Maven command

bash
Copy
Edit
mvn clean test
🧪 How to Run
bash
Copy
Edit
# Clone the repository
git clone https://github.com/your-username/restassured-api-tests.git

# Navigate to the project
cd restassured-api-tests

# Run the test suite using Maven
mvn clean test
