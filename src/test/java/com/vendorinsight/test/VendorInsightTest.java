package com.vendorinsight.test;

import org.testng.annotations.Test;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.ITestResult;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.AfterMethod;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.vendorinsight.pages.VendorInsight;
import com.vendorinsight.utility.EmailUtil;

public class VendorInsightTest extends VendorInsight {
	static Logger log = Logger.getLogger(VendorInsightTest.class.getName());
	public ExtentReports extent;
	public ExtentHtmlReporter htmlReporter;
	public ExtentTest test; 

		SoftAssert softAssert = new SoftAssert();
		@BeforeSuite
		public void setup() {
			DOMConfigurator.configure("log4j.xml");
			htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+ "/test-output/insight.html");
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("ExtentReports.html");
			htmlReporter.config().setDocumentTitle("Vendor Insight Automation Report");
			htmlReporter.config().setReportName("Functional Report");
			htmlReporter.config().setTheme(Theme.DARK);
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter); 
			
		}

		
		@Test (priority = 1)
		public void TestVILaunch() throws Exception {

			DOMConfigurator.configure("log4j.xml");
			 test = extent.createTest("TestVILaunch", "Verifying Vendor Insight portal launch").addScreenCaptureFromPath("screenshot.png").assignCategory("Regression Testing");
			 test.log(Status.INFO, "User successfully landed on the Vendor Insight home page");
			boolean a =  launchbrowser();
			try {
			AssertJUnit.assertEquals(a, true);
			 log.info("Browser launch successfully");
			// log(Status, details)
			}catch (Exception e) {
				Assert.fail("Browser failed to launch");
			} 


		        // log with snapshot
		        test.fail("details", MediaEntityBuilder.createScreenCaptureFromPath("screenshot.png").build());

		        // test with snapshot
		       test.addScreenCaptureFromPath("screenshot.png");

		        signin();

		}
		
		@Test (priority = 2)
		public void TestFieldOperation() throws Exception {

			DOMConfigurator.configure("log4j.xml");
			 test = extent.createTest("TestFieldOperation", "Compare the vendors and print the pdf and verify the same");
			boolean a =  Field_Operation();
			 
			AssertJUnit.assertEquals(a, true);
			  test.pass("Vendor compare functionality is working fine");
		        test.log(Status.INFO, "This is the functionality to perform comparison upto 3 Vendors");
		     
		}	
		
		@Test(priority = 3)
		public void TestSearchInVendorInsight() throws Exception {

			DOMConfigurator.configure("log4j.xml");
			 test = extent.createTest("TestSearchInVendorInsight", "Search and filter out all the results in Vendor Insight Portal");
			boolean a =  VI_Search();
			 
			AssertJUnit.assertEquals(a, true);
			  test.pass("Search and Filters are working fine");
			// log(Status, details)
		        test.log(Status.INFO, "This is the functionality to perform search and filter on Vendor insight search box");
			
		        // log with snapshot
		      //  test.fail("details", MediaEntityBuilder.createScreenCaptureFromPath("screenshot.png").build());

		        // test with snapshot
		        test.addScreenCaptureFromPath("screenshot.png");

		}	

		@Test(priority = 4)
		public void TestVITabs() throws Exception {
			DOMConfigurator.configure("log4j.xml");
			 test = extent.createTest("TestVITabs", "Verify all the three tabs for VI working or not");
			boolean a = VI_VerifyTabs();
			AssertJUnit.assertEquals(a, true);
			test.pass("All three tabs are verified and working fine for Vendor Insight and non is broken");

	 
		}
		@Test (priority = 5)
		public void TestVIWritereview() throws Exception {
			DOMConfigurator.configure("log4j.xml");
			 test = extent.createTest("TestVIWritereview", "Verify if user is able to submit reviews");
			boolean a = VI_write_review_stg();
			AssertJUnit.assertEquals(a, true);
			test.pass("User is able to write review successfully for VI");
			test.log(Status.INFO,"Verified the reivew flow till verify the reiview. However, not submitting the review in the backend");
		}
		
		@Test (priority =6)
		public void TestGetLinks() throws Exception {
			DOMConfigurator.configure("log4j.xml");
			 test = extent.createTest("TestGetLinks", "Verify if user is generate review links successfully");
			boolean a = Get_Site_Links();
			AssertJUnit.assertEquals(a, true);
			test.pass("User is able generate survey links successfully");
			test.log(Status.INFO, "In this test we are generating the review links for the multiple vendors and asserting if links generated are unique and are not broken");
			
		}

//		@Test (dependsOnMethods = "TestGetLinks")
//		public void TestQuitBrowser() { 
//			DOMConfigurator.configure("log4j.xml");
//			 test = extent.createTest("TestQuitBrowser", "Verify if user is able to Quit Browser successfully");
//			
//			
//		}  
		@AfterMethod
		public void AfterMethod(ITestResult result) throws Exception {
			String screenshotPath = VendorInsight.getScreenshot(driver, result.getName());
		    if (result.getStatus() == ITestResult.FAILURE) {
		        test.log(Status.FAIL,
		                MarkupHelper.createLabel(result.getName()
		                        + " Test case FAILED due to below issues:",
		                        ExtentColor.RED) );
		        Assert.fail(screenshotPath, result.getThrowable());
		    } else if (result.getStatus() == ITestResult.SUCCESS) { 
		        test.log(
		                Status.PASS,
		                MarkupHelper.createLabel(result.getName()
		                        + " Test Case PASSED", ExtentColor.GREEN));
		    } else {
		        test.log(
		                Status.SKIP,
		                MarkupHelper.createLabel(result.getName()
		                        + " Test Case SKIPPED", ExtentColor.ORANGE));
		        test.skip(result.getThrowable());
		    }
		} 
		@AfterSuite
		public void teardown() throws Exception { 
	      EmailUtil.sendEmail("msingh@trinitypartners.com", "C:\\Users\\msingh\\eclipse-workspace\\VendorInsight\\ExtentReports.html");
	      extent.flush();
	      softAssert.assertAll();
	      
	     Quit_Browser();
		}
		
	
}
