package com.vendorinsight.utility;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class Login {

	private Properties properties;
	private final String propertyFilePath= "C:\\Users\\msingh\\trinityworkspace\\VendorInsightAutomation\\Resources\\config.properties";


		private WebDriver driver;
		private static final int WAIT_SECONDS = Integer.parseInt(SeleniumUtil.fetchPropertiesDetails("GLOBAL_WAIT"));
		private static final String CYPRESS_URL_STAGE = SeleniumUtil.fetchPropertiesDetails("CYPRESS_URL_STAGE");
		private static final String CYPRESS_URL_DEV = SeleniumUtil.fetchPropertiesDetails("CYPRESS_URL_DEV");
		private static final String CYPRESS_URL_QA = SeleniumUtil.fetchPropertiesDetails("CYPRESS_URL_QA");
		private static final String CYPRESS_URL_INTG = SeleniumUtil.fetchPropertiesDetails("CYPRESS_URL_INTG");
		private static final String CYPRESS_URL_TEST = SeleniumUtil.fetchPropertiesDetails("CYPRESS_URL_TEST");
		
		private static final String TEST_ENVIRONMENT = SeleniumUtil.fetchPropertiesDetails("CYPRESS_TEST_ENVIROMENT");
		private static final By USERNAME_TEXT_FIELD = By.id("user-name-txt");
		private static final By PASSWORD_TEXT_FIELD = By.id("pwd-txt");
		private static final By LOGIN_BUTTON = By.id("signin-button");
		private static final By USERNAME_EMAIL_TEXT_FIELD = By.xpath("//input[@type='email']");	
		private static final By NEXT_BUTTON = By.xpath("//input[@type='submit']");
		private static final By STAY_SIGNED_IN_POPUP = By.xpath("//div[@class='pagination-view has-identity-banner animate slide-in-next']//div[text()='Stay signed in?']");
		private static final By YES_BUTTON_SIGNIN_PAGE = By.xpath("//input[@value='Yes']");
		
		private static final String VI_URL_STAGE = SeleniumUtil.fetchPropertiesDetails("VI_URL_STAGE");
		
	//	static Logger log = Logger.getLogger(Login.class.getName());
	static Logger log = LogManager.getLogger(Login.class.getName());
		/**
		 * constructor of the class
		 * @param driver 
		 * 
		 * @param driver
		 */
		public void Login (WebDriver driver) {
			this.driver = driver;

			String environment = System.getProperty("environment", TEST_ENVIRONMENT);
			log.info("Automation Tests/Scripts executing on: " + TEST_ENVIRONMENT + " environment.");

			if (environment.equalsIgnoreCase("staging")) {
				driver.get(VI_URL_STAGE);
				log.info("Test environment Url: " + VI_URL_STAGE);

			} else if (environment.equalsIgnoreCase("qa")) {
				driver.get(CYPRESS_URL_QA);
				log.info("Test environment Url: " + CYPRESS_URL_QA);

			} else if (environment.equalsIgnoreCase("intg")) {
				driver.get(CYPRESS_URL_INTG);
				log.info("Test environment Url: " + CYPRESS_URL_INTG);

			} else if (environment.equalsIgnoreCase("test")) {
				driver.get(CYPRESS_URL_TEST);
				log.info("Test environment Url: " + CYPRESS_URL_TEST);

			} else if (environment.equalsIgnoreCase("dev")) {
				driver.get(CYPRESS_URL_DEV);
				log.info("Test environment Url: " + CYPRESS_URL_DEV);
			}
		}

		public void clickLogin() {
			driver.findElement(LOGIN_BUTTON).click();
		}
		
		
		public void typeUserName(String username) {
			SeleniumUtil.waitForElementPresence(driver, USERNAME_TEXT_FIELD, 120).sendKeys(username);
		}

		public void typePassword(String password) {
			SeleniumUtil.waitForElementPresence(driver, PASSWORD_TEXT_FIELD, 120).sendKeys(password);
		}

		public void loginUser(String username, String password) {
			typeUserName(username);
			typePassword(password);
		}
		
		public void typeUsername_email(String typeUsername_email)
		{
			SeleniumUtil.waitForElementPresence(driver, USERNAME_EMAIL_TEXT_FIELD, 120).sendKeys(typeUsername_email);
		}
		
		public void clickNextBtn()
		{
			SeleniumUtil.waitForElementPresence(driver, NEXT_BUTTON, 120).click();
		}
		
		public void enterDetailsInFirstAuthenticationPage(String username_email)
		{
			typeUsername_email(username_email);
			clickNextBtn();
			//SeleniumUtil.waitForElementInvisibility(driver, findByCondition, 120);
		}
		
		public Login(){
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(propertyFilePath));
				properties = new Properties();
				try {
					properties.load(reader);
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
			}		
		}
		
		public String getDriverPath(){
		
			String driverPath = properties.getProperty("DRIVER_PATH");
			if(driverPath!= null) return driverPath;
			else throw new RuntimeException("driverPath not specified in the Configuration.properties file.");		
		}
		
		
	}


