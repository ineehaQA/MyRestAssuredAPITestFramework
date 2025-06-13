package Utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

	public class ExtentManager {

	    private static ExtentReports extent;
	    private static ExtentTest test;

	    public static ExtentReports getExtentReports() {
	        if (extent == null) {
	            ExtentSparkReporter reporter = new ExtentSparkReporter("test-output/ExtentReport.html");
	            reporter.config().setDocumentTitle("API Test Report");
	            reporter.config().setReportName("ReqRes API Testing");

	            extent = new ExtentReports();
	            extent.attachReporter(reporter);
	            extent.setSystemInfo("Tester", "Neeharika P");
	        }
	        return extent;
	    }

	    public static ExtentTest createTest(String testName) {
	        test = getExtentReports().createTest(testName);
	        return test;
	    }

	    public static void flushReport() {
	        if (extent != null) {
	            extent.flush();
	        }
	    }

	    public static ExtentTest getTest() {
	        return test;
	    }
	}
