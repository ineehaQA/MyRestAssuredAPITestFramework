package HttpRequests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

	public class AllHttpRequestsCombinedTest {
	
    RequestSpecification request;
    String basePath = "/api/users";
    String userId;

    ExtentReports extent;
    ExtentTest test;

    @BeforeClass
    public void setup() {
        // Base URL & Authorization Header
        RestAssured.baseURI = "https://reqres.in";
        request = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("x-api-key", "reqres-free-v1");

        // ExtentReports Setup
        ExtentSparkReporter spark = new ExtentSparkReporter("test-output/ExtentReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Tester", "Neeharika P");

        System.out.println("Setup complete.");
    }

    @Test(priority = 1)
    public void UserTest_Post() {
        test = extent.createTest("Create User - POST");

        JSONObject json = new JSONObject();
        json.put("name", "Neeharika P");
        json.put("job", "Automation Tester");

        Response response = request.body(json.toString()).post(basePath);

        System.out.println("POST Request: " + json.toString());
        System.out.println("POST Response: " + response.asPrettyString());

        test.info("POST Request: " + json.toString());
        test.info("POST Response: " + response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 201);
        test.pass("Status code 201 validated");

        userId = response.jsonPath().getString("id");
        Assert.assertNotNull(userId);
        test.pass("User ID generated: " + userId);

        System.out.println("User ID: " + userId);
    }

    @Test(priority = 2, dependsOnMethods = "UserTest_Post")
    public void UserTest_Get() {
        test = extent.createTest("Get User - GET");

        Response response = request.get(basePath + "/" + userId);

        System.out.println("GET Response: " + response.asPrettyString());
        test.info("GET Response: " + response.asPrettyString());

        int code = response.getStatusCode();
        System.out.println("GET Status Code: " + code);
        test.info("Status Code: " + code);

        Assert.assertTrue(code == 200 || code == 404);
        test.pass("Status code is either 200 or 404");
    }

    @Test(priority = 3, dependsOnMethods = "UserTest_Post")
    public void UserTest_Put() {
        test = extent.createTest("Update User - PUT");

        JSONObject json = new JSONObject();
        json.put("name", "Neeharika");
        json.put("job", "Lead Tester");

        Response response = request.body(json.toString()).put(basePath + "/" + userId);

        System.out.println("PUT Request: " + json.toString());
        System.out.println("PUT Response: " + response.asPrettyString());

        test.info("PUT Request: " + json.toString());
        test.info("PUT Response: " + response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 200);
        test.pass("Status code 200 validated");
    }

    @Test(priority = 4, dependsOnMethods = "UserTest_Post")
    public void UserTest_Delete() {
        test = extent.createTest("Delete User - DELETE");

        Response response = request.delete(basePath + "/" + userId);

        System.out.println("DELETE Status Code: " + response.getStatusCode());
        test.info("DELETE Status Code: " + response.getStatusCode());

        Assert.assertEquals(response.getStatusCode(), 204);
        test.pass("Status code 204 validated");
    }

    @Test(priority = 5, dataProvider = "userData")
    public void createUserDataDriven(String name, String job) {
        test = extent.createTest("Data Driven Create User: " + name + " - " + job);

        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("job", job);

        Response response = request.body(json.toString()).post(basePath);

        System.out.println("DataDriven POST Request: " + json.toString());
        System.out.println("DataDriven POST Response: " + response.asPrettyString());

        test.info("Request: " + json.toString());
        test.info("Response: " + response.asPrettyString());

        Assert.assertEquals(response.getStatusCode(), 201);
        Assert.assertEquals(response.jsonPath().getString("name"), name);
        Assert.assertEquals(response.jsonPath().getString("job"), job);
        test.pass("User created with name: " + name + " and job: " + job);
    }

    @DataProvider(name = "userData")
    public Object[][] getUserData() {
        return new Object[][]{
                {"neeha", "engineer"},
                {"harika", "developer"},
                {"aheen", "analyst"}
        };
    }

    @Test(priority = 6)
    public void responseTimeValidation() {
        test = extent.createTest("Response Time Validation");

        JSONObject json = new JSONObject();
        json.put("name", "speedy");
        json.put("job", "runner");

        long time = request.body(json.toString()).post(basePath).time();

        System.out.println("Response Time: " + time + " ms");
        test.info("Response Time: " + time + " ms");

        Assert.assertTrue(time < 2000);
        test.pass("Response time under 2000 ms");
    }

    @AfterClass
    public void tearDown() {
        extent.flush(); // Generate the report
        System.out.println("Test execution completed. Report generated.");
    }
}