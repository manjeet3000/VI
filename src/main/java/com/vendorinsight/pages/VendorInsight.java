package com.vendorinsight.pages;

import java.io.File;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import com.vendorinsight.utility.ConfigFileReader;
import com.vendorinsight.utility.Login;
import com.vendorinsight.utility.SeleniumUtil;

public class VendorInsight { 

	public static WebDriver driver;
	static Logger log = Logger.getLogger(VendorInsight.class.getName());

	//private static  String CSV_PATH ;
	String fileName;
	ConfigFileReader configFileReader;
	 Login login = new Login();

	private static final By Sign_in = By.id("AADLogin");
	//private static final By Username = By.id("i0116");
	//private static final By Next_button = By.id("idSIButton9");
	//private static final By Password = By.id("i0118");
	//private static final By Signin = By.id("idSIButton9");
	private static final By Go_button = By.xpath("(//button[text()='Go'])[2]");
	private static final By Skip_button = By.xpath("//a[@class=' auth-link']");
	//private static final By TGas_VI = By.linkText("TGaS Vendor Insights Portal");
	private static final By Access_button = By.xpath("//span[@class='label']");

	private static final By Search = By.id("search");
	//private static final By Search_button = By.id("searchButton");
	private static final By Search_result = By.xpath("//div[@class='vendor-list grid lap-two-thirds desk-three-quarters']/div/descendant::div[@class='details']/h3/a");
	private static final By About = By.xpath("//h3[text()='Overview']");
	//private static final By About1 = By.xpath("//a[@href='#details']");
	private static final By Products = By.xpath("//a[text()='Products']");
	private static final By Products_product = By.xpath("//h2[text()='Brand & Launch Planning']");
	private static final By Reviews = By.linkText("Reviews");
	private static final By Reivews_FunctionalArea = By.id("function");
	private static final By Start_Date = By.id("review-start");
	private static final By End_Date = By.id("review-end"); 
	private static final By Update_button = By.id("apply-date-filter");
	private static final By Review_Satisfaction = By.xpath("//h2[text()='Summary for Trinity']");
	private static final By Print_pdf = By.xpath("//a[@class='btn blue-btn btn-export-pdf cf']");
	private static final By Export_pdf = By.xpath("//a[@class='btn export-pdf']");
	private static final By Writereview_button = By.xpath("//a[@class='btn btn-review cf']");
	private static final By BigSmileFace_Button = By.xpath("(//div[@class='smileys']//div)[4]");
	private static final By BigSmileFace_Button_Stg = By.xpath("//div[@data-id='4']");
	private static final By Next_button1 = By.linkText("Next");

	private static final By Select_rating = By.xpath("//div[@id='howLikely']/div[text()='10']");
	private static final By Projectscope_inputbox = By.id("ProjectScope");
	private static final By SelectMain_function = By.xpath("//span[text()='Artificial Intelligence & Machine Learning']");
	private static final By Selectsub_function = By.xpath("//label[text()='Artificial Intelligence & Machine Learning']");
	private static final By dropdown1 = By.id("LastUsedID");
	private static final By dropdown2 = By.id("PeriodID");
	private static final By Comment_experience = By.id("Comment");
	private static final By Verify_Review = By.linkText("Verify Review");
	private static final By Field_Operation = By.xpath("(//a[text()='Field Operations'])[1]");
	private static final By compare1  = By.xpath("(//a[@class='compare-button'])[2]");
	private static final By Overallsatisfaction = By.xpath("(//div[@class='grid row']/div[@class='grid lap-one-third'])[2]");
	private static final By Pricerelative = By.xpath("(//div[@class='grid lap-one-third'])[10]");
	private static final By Valueforcost = By.xpath("(//div[@class='grid lap-one-third'])[11]");
	private static final By Myselection = By.xpath("//a[@class='return-selections']");
	private static final By Allvendors = By.linkText("All Vendors");
	private static final By Admin = By.linkText("Admin");
	private static final By Link = By.xpath("//input[@name='Link']");
	private static final By Get_Links = By.xpath("//input[@value='Get Links']");
	private static final By VI_Link = By.xpath("(//a[@class='btn'])[2]");

	private static final int WAIT_SECONDS = Integer.parseInt(SeleniumUtil.fetchPropertiesDetails("GLOBAL_WAIT"));
	
	
	public boolean launchbrowser() throws InterruptedException {
		log.info("TGas Vendor Insight Portal Launched sucessfully");
      System.setProperty("webdriver.chrome.driver", "C:\\Users\\msingh\\eclipse-workspace\\chromebeta\\chromedriver.exe");
     //   System.setProperty("webdriver.chrome.driver", "C:\\Users\\msingh\\Downloads\\chrome-win\\chrome-win\\chrome.exe");
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\Google\\Chrome Beta\\Application\\chrome.exe");
      LoggingPreferences logPrefs = new LoggingPreferences();
     logPrefs.enable(LogType.BROWSER, Level.SEVERE);
        options.setCapability("goog:loggingPrefs", logPrefs);
        //options.addArguments("--headless", "--window-size=1368,768");
        //options.addArguments("--incognito");
        
        
     options.addArguments("user-data-dir=C:\\Users\\msingh\\AppData\\Local\\Google\\Chrome Beta\\User Data");
    // options.setHeadless(true);   
     driver= new ChromeDriver(options);
        driver.manage().window().maximize();


			    	
		return true;
	}

	public boolean signin() throws Exception {
		 login.Login(driver);
		
		 Thread.sleep(3000);
		 SeleniumUtil.waitForElementVisibility(driver, Sign_in, WAIT_SECONDS);
		 
		 SeleniumUtil.click(driver, Sign_in);

		//SeleniumUtil.waitForElement(driver, Username, WAIT_SECONDS);
		//SeleniumUtil.enterText(driver, Username, "msingh@trinitypartners.com");
		//SeleniumUtil.click(driver, Next_button);
		//SeleniumUtil.enterText(driver, Password, "Trinity@2020");
		//SeleniumUtil.click(driver, Signin);
		//SeleniumUtil.waitForElementVisibility(driver, Next_button, WAIT_SECONDS);
		//SeleniumUtil.click(driver, Next_button);


		SeleniumUtil.scrollToViewElement(driver, Go_button);
		SeleniumUtil.waitForElementVisibility(driver, Go_button, WAIT_SECONDS);
		SeleniumUtil.click(driver, Go_button);		
		SeleniumUtil.waitForElementVisibility(driver, Skip_button, WAIT_SECONDS);
		SeleniumUtil.click(driver, Skip_button);
		//SeleniumUtil.waitForElementVisibility(driver, TGas_VI, WAIT_SECONDS);
		//SeleniumUtil.click(driver, TGas_VI);
		//driver.get("https://www.tgasinsights.com/reportaction/VITESTSTAGING01/Toc");
		
		
		
		
		SeleniumUtil.waitForElement(driver, VI_Link, WAIT_SECONDS);
		WebElement VI = driver.findElement(VI_Link);
		SeleniumUtil.clickelementByJS(VI, driver);
		SeleniumUtil.waitForElement(driver, Access_button, WAIT_SECONDS);
		SeleniumUtil.click(driver, Access_button);
		SeleniumUtil.switchWindowByIndex(driver, 1);
		return true;
	}

	public boolean VI_Search() throws Exception {
		String searchTerm = "Trinity";

		SeleniumUtil.waitForElementClickable(driver, Search, WAIT_SECONDS);
		SeleniumUtil.click(driver, Search);
		SeleniumUtil.enterText(driver, Search, "Trinity");
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("document.getElementById('searchButton').click();");
		// Now, let's gather our search results
		SeleniumUtil.waitForElementPresence(driver, Search_result, WAIT_SECONDS);

		List<WebElement> results = driver.findElements(By.xpath("//div[@class='vendor-list grid lap-two-thirds desk-three-quarters']/div/descendant::div[@class='details']/h3/a"));
		// Finally, we'll loop over the list to verify each result link contains our term

		System.out.println(results.size());

		for (int i = 0; i < results.size(); i++) {
			Assert.assertTrue(results.get(i).getText().contains(searchTerm), "Search result validation failed at instance [ + i + ].");
			String listitem = results.get(i).getText();  
			System.out.println("Searched results are:- " +listitem);
			if(listitem.contains(searchTerm))
			{
				results.get(i).click();
				log.info("Result found and clicked successfully");
				System.out.println("Result found and clicked successfully");
				break;
			}


		}




		return true;
	}

	public boolean VI_VerifyTabs() throws Exception {
	JavascriptExecutor jse = (JavascriptExecutor)driver;
//		WebElement Abt = driver.findElement(By.linkText("About"));
//		jse.executeAsyncScript("arguments[0].click()", Abt);
//		//SeleniumUtil.click(driver, About1);
//		Thread.sleep(1000);
		SeleniumUtil.getTextAndVerifyWithExpectedText(driver, About, "Overview");
		//SeleniumUtil.waitForElementVisibility(driver, Products, WAIT_SECONDS);
		//WebElement prod = driver.findElement(Products);
		//JavascriptExecutor executor = (JavascriptExecutor)driver;
		//executor.executeScript("arguments[0].click();", prod);
//		for(int i=0; i<=12; i++) {
//			act.sendKeys(Keys.TAB).build().perform();
//			
//		}
//		act.sendKeys(Keys.ENTER).build().perform();
		//act.doubleClick(prod).build().perform();
		
		WebElement product = driver.findElement(Products);
		jse.executeScript("arguments[0].click()", product);
		//SeleniumUtil.click(driver, Products);
	SeleniumUtil.getTextAndVerifyWithExpectedText(driver, Products_product, "Brand & Launch Planning");
		
//	for(int i=0; i<=12; i++) {
//		act.sendKeys(Keys.TAB).build().perform();
//		
//	}
//	act.sendKeys(Keys.ENTER).build().perform();
//	SeleniumUtil.click(driver, About1);
//	SeleniumUtil.getTextAndVerifyWithExpectedText(driver, About, "Overview");
		//SeleniumUtil.waitForElementPresence(driver, Reviews, WAIT_SECONDS);
	WebElement Review = driver.findElement(Reviews);
	jse.executeScript("arguments[0].click()", Review);
	//SeleniumUtil.click(driver, Reviews);
		SeleniumUtil.click(driver, Reivews_FunctionalArea);
		SeleniumUtil.selectdropdownvalue(driver, Reivews_FunctionalArea, "   Advanced Analytics/Multi-Channel/Forecasting", WAIT_SECONDS);
		SeleniumUtil.click(driver, Start_Date);
		SeleniumUtil.enterText(driver, Start_Date, "01/01/2022");
		Actions action = new Actions(driver);
		action.sendKeys(Keys.TAB).click().build().perform();

		SeleniumUtil.click(driver, End_Date); 
		SeleniumUtil.enterText(driver, End_Date, SeleniumUtil.entercurrentdate(driver));
		action.sendKeys(Keys.TAB).click().build().perform();
		WebElement update = driver.findElement(Update_button);
		jse.executeScript("arguments[0].click()", update);
		SeleniumUtil.waitForElementVisibility(driver, Review_Satisfaction, WAIT_SECONDS);
		SeleniumUtil.getTextAndVerifyWithExpectedText(driver, Review_Satisfaction, "Summary for Trinity");
		
		
		WebElement Print = driver.findElement(Print_pdf);
		jse.executeScript("arguments[0].click();", Print);
		
		SeleniumUtil.getLatestFilefromDir();
		SeleniumUtil a = new SeleniumUtil();
		waitForFile(a.latestdownloadedfile);
	    
		SeleniumUtil.switchWindowByIndex(driver, 1);
		
		log.info("PDF printed successfully");
		Thread.sleep(15000);
		
		//********PDF Validation ***********//
		
		
		SeleniumUtil.verifyPDFContent("Trinity");
		 
		
		return true;

 
	}
	
	

	public boolean VI_write_review_stg() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		Thread.sleep(8000);
		WebElement Review = driver.findElement(Writereview_button);
//		jse.executeScript("arguments[0].click();", Print);
//		SeleniumUtil.waitForElementVisibility(driver, Writereview_button, WAIT_SECONDS);
		jse.executeScript("arguments[0].click()", Review);
	//	SeleniumUtil.click(driver, Writereview_button);
		WebElement Bigsmile = driver.findElement(BigSmileFace_Button_Stg);
		jse.executeScript("arguments[0].click();", Bigsmile);
		WebElement Next = driver.findElement(Next_button1);
		jse.executeScript("arguments[0].click();", Next);
		WebElement Selectrating = driver.findElement(Select_rating);
		jse.executeScript("arguments[0].click();", Selectrating);
		SeleniumUtil.actionScrollToBottom(driver);
		Thread.sleep(500);
		WebElement next = driver.findElement(By.xpath("(//a[text()='Next'])[2]"));
		jse.executeScript("arguments[0].click();", next);
		
		List<WebElement> rank = driver.findElements(By.xpath("//ul[@class='select-rank unstyle-list']//li"));
		for (int i = 25; i > 5; i--) {
		
			System.out.println("Minimum 5 areas where vendor has to rate:- " +rank.size());
			WebElement rank1 = rank.get(i);
			jse.executeScript("arguments[0].click()", rank1);
			log.info("Ranking marked successfully");

		}
		Thread.sleep(1000);
		WebElement next1 = driver.findElement(By.xpath("(//a[text()='Next'])[3]"));
		jse.executeScript("arguments[0].click()", next1);
		
		//SeleniumUtil.actionScrollToBottom(driver);
	//	WebElement projectid = driver.findElement(Projectscope_inputbox);
		
		SeleniumUtil.clickOnElementUsingJavaScript(driver, Projectscope_inputbox);
		SeleniumUtil.click(driver, Projectscope_inputbox);
		SeleniumUtil.enterText(driver, Projectscope_inputbox, "Please briefly describe the scope of the project this vendor supported");
		Thread.sleep(500);
		WebElement next2 = driver.findElement(By.xpath("(//a[text()='Next'])[4]"));
		jse.executeScript("arguments[0].click()", next2);
		
		WebElement Selectmain = driver.findElement(SelectMain_function);
		jse.executeScript("arguments[0].click()", Selectmain);
		
		
		SeleniumUtil.waitForElementClickable(driver, Selectsub_function, WAIT_SECONDS);
		WebElement Selectsub = driver.findElement(Selectsub_function);
		jse.executeScript("arguments[0].click()", Selectsub);
		Thread.sleep(500);
		WebElement next3 = driver.findElement(By.xpath("(//a[text()='Next'])[5]"));
		jse.executeScript("arguments[0].click()", next3);
		//SeleniumUtil.actionScrollToBottom(driver);
	
		SeleniumUtil.waitForElement(driver, dropdown1, WAIT_SECONDS);
		SeleniumUtil.selectDropDownByValue(driver, dropdown1, "1");
		SeleniumUtil.actionScrollToMiddle(driver);
		SeleniumUtil.selectDropDownByValue(driver, dropdown2, "1");
		SeleniumUtil.enterText(driver, Comment_experience, "this is automation");
		Thread.sleep(500);
		WebElement next4 = driver.findElement(By.xpath("(//a[text()='Next'])[6]"));
		jse.executeScript("arguments[0].click()", next4);
		Thread.sleep(500);
		WebElement next5 = driver.findElement(Verify_Review);
		jse.executeScript("arguments[0].click()", next5);
		return true;

	}
	public void Quit_Browser() {
		driver.quit();
		log.info("Browser quite successfully");
	}
	
	public boolean Field_Operation() throws Exception {
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		SeleniumUtil.waitForElement(driver, By.xpath("(//a[text()='Field Operations'])[1]"), WAIT_SECONDS);
		WebElement field = driver.findElement(Field_Operation);
		jse.executeScript("arguments[0].click()", field);
		
		
		SeleniumUtil.waitForElementVisibility(driver, compare1, WAIT_SECONDS);
		WebElement compare1 = driver.findElement(By.xpath("(//i[@class='fa fa-square-o'])[2]"));
		jse.executeScript("arguments[0].click()", compare1);
		WebElement compare2 = driver.findElement(By.xpath("(//i[@class='fa fa-square-o'])[3]"));
		jse.executeScript("arguments[0].click()", compare2);
		WebElement compare3 = driver.findElement(By.xpath("(//i[@class='fa fa-square-o'])[4]"));
		jse.executeScript("arguments[0].click()", compare3);
		Thread.sleep(500);
		WebElement compare4 = driver.findElement(By.xpath("//a[@class='btn compare-vendors']"));
		jse.executeScript("arguments[0].click()", compare4);
		
		SeleniumUtil.waitForElementPresence(driver, Overallsatisfaction, WAIT_SECONDS);
		SeleniumUtil.getTextAndVerifyWithExpectedText(driver, Overallsatisfaction, "Overall Satisfaction");
		
		SeleniumUtil.getTextAndVerifyWithExpectedText(driver, Pricerelative, "Pricing relative to other vendors *");
		SeleniumUtil.getTextAndVerifyWithExpectedText(driver, Valueforcost, "Value for the cost");
//		 Export ratings in PDF
		
		SeleniumUtil.click(driver, Export_pdf);
		
		
		SeleniumUtil.getLatestFilefromDir();
		SeleniumUtil a = new SeleniumUtil();
		waitForFile(a.latestdownloadedfile);
		
	
	    

		
		SeleniumUtil.switchWindowByIndex(driver, 1);
		
		log.info("PDF exported successfully");
		Thread.sleep(15000);
		
		//********PDF Validation ***********//
		
		
		SeleniumUtil.verifyPDFContent("Trinity");
	
		
		
		
		SeleniumUtil.click(driver, Myselection);
		SeleniumUtil.waitForElementVisibility(driver, Allvendors, WAIT_SECONDS);
		SeleniumUtil.click(driver, Allvendors);
		
		return true;
	}
	
	public boolean Get_Site_Links() throws Exception {
		
		SeleniumUtil.click(driver, Admin);
		SeleniumUtil.enterText(driver, Link, "/23402");
		WebElement browse = driver.findElement(By.xpath("//input[@class='file-upload']"));
		   //click on ‘Choose file’ to upload the desired file
		   browse.sendKeys("C:\\Users\\msingh\\OneDrive - TRINITY PARTNERS LLC\\Documents\\Book1.xlsx"); //Uploading the file using sendKeys
		   System.out.println("File is Uploaded Successfully");
		   
		WebElement getlinks = driver.findElement(Get_Links);
		SeleniumUtil.clickelementByJS(getlinks, driver);
		Thread.sleep(500);
		driver.get("chrome://downloads");
		 JavascriptExecutor js1 = (JavascriptExecutor)driver;
	          
	        	  fileName = (String) js1.executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList downloads-item').shadowRoot.querySelector('div#content #file-link').text");
	        	  System.out.println("Download File Name is :-" + fileName);
	        	  String CSV_PATH = "C:/Users/msingh/Downloads/"+fileName;
	        	  waitForFile(CSV_PATH);
	        	  
	        	  SeleniumUtil.read(CSV_PATH);  
        	    String url = SeleniumUtil.URL1;
	        	    HttpURLConnection huc = null; 
	        	    int respCode = 200;
	        	    CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
	        	    java.net.CookieManager cm = new java.net.CookieManager();
	        	    java.net.CookieHandler.setDefault(cm);
	        	   
	        	    if(url == null || url.isEmpty()){
	        	    	System.out.println("URL is either not configured for anchor tag or it is empty");
	        	    	
	        	    	}
	        	    
	        	    try {
	        	    	
	        	    	huc = (HttpURLConnection)(new URL(url).openConnection());

	        	    	huc.setRequestMethod("GET");

	        	    	huc.connect();

	        	    	respCode = huc.getResponseCode();
	        	    	System.out.println("Status code for the URL is:- "+respCode);
	        	    	if(respCode == 200){
	        	    	System.out.println(url+" is a valid link");
	        	    	}
	        	    	else{
	        	    	System.out.println(url+" is a broken link");
	        	    	
	        	    	}
	        	    } catch (MalformedURLException e) {
	        	    	// TODO Auto-generated catch block
	        	    	e.printStackTrace();
	        	    	} catch (IOException e) {
	        	    	// TODO Auto-generated catch block
	        	    	e.printStackTrace();
	        	    	}
	        	    	
	        	    
	        	    return true;
	        
	
	     
		
	}
	public static void waitForFile(String Filename) {
		final File file = new File(Filename);
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofMillis(200)).pollingEvery(Duration.ofMillis(100));
		
		//wait.until((WebDriver wd) -> file.exists()); 
	}
	

	
	public boolean VI_write_review() throws Exception {

		SeleniumUtil.waitForElementVisibility(driver, Writereview_button, WAIT_SECONDS);
		SeleniumUtil.click(driver, Writereview_button);
		SeleniumUtil.click(driver, BigSmileFace_Button);
		SeleniumUtil.click(driver, Next_button1);
		SeleniumUtil.waitForElementVisibility(driver, Select_rating, WAIT_SECONDS);
		SeleniumUtil.click(driver, Select_rating);
		SeleniumUtil.click(driver, Next_button1);

		List<WebElement> rank = driver.findElements(By.xpath("//ul[@class='select-rank unstyle-list']//li"));
		for (int i = 25; i > 5; i--) {
			System.out.println("Minimum 5 areas where vendor has to rate:- " +rank.size());
			rank.get(i).click();
			log.info("Ranking marked successfully");

		}
		SeleniumUtil.waitForElementPresence(driver, Next_button1, WAIT_SECONDS);
		SeleniumUtil.click(driver, Next_button1);
		SeleniumUtil.click(driver, Projectscope_inputbox);
		SeleniumUtil.enterText(driver, Projectscope_inputbox, "Please briefly describe the scope of the project this vendor supported");
		SeleniumUtil.click(driver, Next_button1);

		SeleniumUtil.click(driver, SelectMain_function);
		SeleniumUtil.waitForElementClickable(driver, Selectsub_function, WAIT_SECONDS);
		SeleniumUtil.click(driver, Selectsub_function);
		SeleniumUtil.click(driver, Next_button1);
		
		SeleniumUtil.click(driver, dropdown1);
		SeleniumUtil.selectDropDownByValue(driver, dropdown1, "1");
		SeleniumUtil.click(driver, dropdown2);
		SeleniumUtil.selectDropDownByValue(driver, dropdown2, "1");
		SeleniumUtil.enterText(driver, Comment_experience, "this is automation");
		SeleniumUtil.click(driver, Next_button1);
		SeleniumUtil.click(driver, Verify_Review);
		
		
		
		


		return true;

	}
	
	//Creating a method getScreenshot and passing two parameters
	//driver and screenshotName
	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
	                //below line is just to append the date format with the screenshot name to avoid duplicate names
	                String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	TakesScreenshot ts = (TakesScreenshot) driver;
	File source = ts.getScreenshotAs(OutputType.FILE);
	                //after execution, you could see a folder "FailedTestsScreenshots" under src folder
	String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/"+screenshotName+dateName+".png";
	File finalDestination = new File(destination);
	FileUtils.copyFile(source, finalDestination);
	                //Returns the captured file path
	return destination;
	}
	
	
}
