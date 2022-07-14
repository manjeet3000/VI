package com.vendorinsight.utility;



import java.awt.AWTException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.net.URL;


import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.base.Stopwatch;

public class SeleniumUtil {
	public static String latestdownloadedfile;
	public static String URL1;
	static Logger log = Logger.getLogger(SeleniumUtil.class.getName());
	private static final int WAIT_SECONDS = Integer.parseInt(SeleniumUtil.fetchPropertiesDetails("GLOBAL_WAIT"));
//manjeet code
	public static final void selectdropdownvalue(WebDriver driver, By findByCondition, String expectedtext, int waitInSeconds) {
		Select s = new Select(driver.findElement(findByCondition));
		s.selectByVisibleText(expectedtext);
	}
	
	public static final String entercurrentdate(WebDriver driver) {
		 // Create object of SimpleDateFormat class and decide the format
		 DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		 
		 //get current date time with Date()
		 Date date = new Date();
		 
		 // Now format the date
		 String date1= dateFormat.format(date);
		 return date1;
	}
	public static void clickelementByJS(WebElement element, WebDriver driver) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("arguments[0].click();", element);
	}
	public static void turnOffImplicitWaits(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}

	public static void turnOnImplicitWaits(WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public static final WebElement waitForElementVisibility(WebDriver driver, By findByCondition, int waitInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, waitInSeconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(findByCondition));
		return driver.findElement(findByCondition);
	}

	public static final void waitForElementInvisibility(WebDriver driver, By findByCondition, int waitInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, waitInSeconds);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(findByCondition));
	}

	public static final WebElement waitForElementPresence(WebDriver driver, By findByCondition, int waitInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, waitInSeconds);
		wait.until(ExpectedConditions.presenceOfElementLocated(findByCondition));
		return driver.findElement(findByCondition);
	}

	public static final WebElement waitForElementClickable(WebDriver driver, By findByCondition, int waitInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, waitInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(findByCondition));
		return driver.findElement(findByCondition);
	}

	public static int countWindow(WebDriver driver) {
		Set<String> windowHandle = driver.getWindowHandles();
		return windowHandle.size();
	}

	public static void switchWindowByIndex(WebDriver driver, int windowno) {
		Set<String> window = driver.getWindowHandles();
		driver.switchTo().window(window.toArray()[windowno].toString());
	}

	public static void openNewTab(WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("window.open('about:blank','_blank');");
	}

	public static void closeCurrentBrowserTab(WebDriver driver) {
		driver.close();
	}

	public static String fetchPropertiesDetails(String key) {

		FileInputStream file = null;
		try {
			file = new FileInputStream("./Resources/config.properties");
		} catch (FileNotFoundException e) {
			log.error("Error - ", e);
		}
		Properties property = new Properties();
		try {
			property.load(file);
		} catch (IOException e) {
			log.error("Error - ", e);
		}
		return property.getProperty(key);
	}

	public static long getLoadTimeInSeconds(WebDriver driver, By waitForLoadElement, int waitInSeconds) {

		StopWatch pageLoad = new StopWatch();
		pageLoad.start();
		waitForElementVisibility(driver, waitForLoadElement, waitInSeconds);
		pageLoad.stop();

		long pageLoadTimeMS = pageLoad.getTime();
		long pageLoadTimeSeconds = pageLoadTimeMS / 1000;
		log.info("Total Loading Time in milliseconds: " + pageLoadTimeMS);
		log.info("Total Loading Time in seconds: " + pageLoadTimeSeconds);

		return pageLoadTimeSeconds;
	}

	public static WebElement scrollToViewElement(WebDriver driver, By findByCondition) {
		WebElement element = driver.findElement(findByCondition);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		return driver.findElement(findByCondition);
	}

	public static void scrollUp(WebDriver driver) throws InterruptedException, AWTException {

		log.info("Try Scroll");
		pause(2000);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,-250)", "");
	}

	public static boolean isElementPresent(WebDriver driver, By locatedElement) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 2) {
			try {
				result = waitForElementPresence(driver, locatedElement, 8).isDisplayed();
				log.info("Element is displayed: " + locatedElement);
				break;
			} catch (Exception e) {
				log.info("Got Element Exception in " + (attempts + 1) + " attempt. Trying to find element again...!! "
						+ e);
				pause(1000);
			}
			attempts++;
		}
		return result;
	}

	public static void hoverElement(WebDriver driver, By locator) {
		Actions action = new Actions(driver);
		WebElement we = driver.findElement(locator);
		action.moveToElement(we).build().perform();
	}

	public static String getScreenshot(WebDriver driver, String screenshotName) throws Exception {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/FailedTestsScreenshots/" + screenshotName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}

	public static void actionScrollToBottom(WebDriver driver) {

		Actions actions = new Actions(driver);

		for (int i = 1; i <= 100; i++) {

			actions.sendKeys(Keys.PAGE_DOWN).perform();
		}

	}

	public static void actionScrollToTop(WebDriver driver) {

		Actions actions = new Actions(driver);

		for (int i = 1; i <= 100; i++) {

			actions.sendKeys(Keys.PAGE_UP).perform();
		}

	}
	
	public static void actionScrollToMiddle(WebDriver driver) {

		Actions actions = new Actions(driver);

		for (int i = 1; i <= 1; i++) {

			actions.sendKeys(Keys.PAGE_DOWN).perform();
		}

	}

	public static void selectDropDownByValue(WebDriver driver, By locator, String value) {

		Select dropdown = new Select(driver.findElement(locator));
		dropdown.selectByValue(value);
		
	}

	public static void refreshPage(WebDriver driver) {
		SeleniumUtil.pause(2000);
		driver.navigate().refresh();
	}

	public static ArrayList getfileNamesFromFolder(String path) {

		ArrayList listOfFilesArray = new ArrayList();

		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				log.info("File " + listOfFiles[i].getName());
				listOfFilesArray.add(listOfFiles[i].getName());
			} else if (listOfFiles[i].isDirectory()) {
				log.info("Directory " + listOfFiles[i].getName());
			}
		}

		log.info(listOfFilesArray);

		return listOfFilesArray;

	}

	public static void switchToIframe(WebDriver driver, String iFrameValue) {
		pause(1500);
		driver.switchTo().frame(driver.findElement(By.id(iFrameValue)));
		pause(1500);
	}

	public static void switchToIframeUsingCssSelector(WebDriver driver, String iFrameValue) {
		pause(1500);
		driver.switchTo().frame(driver.findElement(By.cssSelector(iFrameValue)));
		pause(1500);
	}

	public static void switchToDefaultContent(WebDriver driver) {
		pause(1500);
		driver.switchTo().defaultContent();
		pause(1500);
	}

	public static void switchingIframeToIframe(WebDriver driver, String iFrameValue) {
		pause(1000);
		driver.switchTo().defaultContent();
		pause(1000);
		driver.switchTo().frame(driver.findElement(By.id(iFrameValue)));
		pause(1000);
	}

	public static final void pause(int milliseconds) throws RuntimeException {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			log.info("ERROR: ", e);
		}
	}

	public static void selectAndClickElement(WebDriver driver, By xPath) {

		int attempts = 0;
		while (attempts < 10) {
			try {
				waitForElementPresence(driver, xPath, 10).click();
				break;
			} catch (Exception e) {
				log.info("Got Element Exception in " + (attempts + 1) + " attempt. Trying again...!!");
				pause(1000);
			}
			attempts++;
		}
	}

	/**
	 * To wait for the specific element on the page to become clickable
	 * 
	 * @param driver  -
	 * @param element - webelement to wait for to appear
	 * @param maxWait - how long to wait for in seconds
	 * @return boolean - return true if element is present else return false
	 */
	public static boolean waitForElementToBeClickable(WebDriver driver, WebElement element, int maxWait) {
		boolean statusOfElementToBeReturned = false;
		Stopwatch stopwatch = Stopwatch.createStarted();
		WebDriverWait wait = new WebDriverWait(driver, maxWait);
		try {
			WebElement waitElement = wait.until(ExpectedConditions.elementToBeClickable(element));
			if (waitElement.isDisplayed() && waitElement.isEnabled()) {
				statusOfElementToBeReturned = true;
				log.info("Element: " + element + ", is displayed and clickable");
			}
		} catch (Exception ex) {
			statusOfElementToBeReturned = false;
			stopwatch.stop(); // optional

			// get elapsed time, expressed in milliseconds
			double timeElapsed = (stopwatch.elapsed(TimeUnit.MILLISECONDS)) / 1000;
			log.info("Element not clickable after " + timeElapsed + "seconds...");
		}
		return statusOfElementToBeReturned;
	}

	/**
	 * To wait for the specific element on the page
	 * 
	 * @param driver  -
	 * @param element - webelement to wait for to appear
	 * @param maxWait - how long to wait for
	 * @return boolean - return true if element is present else return false
	 */
	public static boolean waitForElement(WebDriver driver, By elementLocator, int maxWait) {
		boolean statusOfElementToBeReturned = false;
		Stopwatch stopwatch = Stopwatch.createStarted();
		WebDriverWait wait = new WebDriverWait(driver, maxWait);
		try {
			WebElement waitElement = wait.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
			if (waitElement.isDisplayed() && waitElement.isEnabled()) {
				statusOfElementToBeReturned = true;
				log.info("Element is displayed");
			}
		} catch (Exception ex) {
			statusOfElementToBeReturned = false;
			stopwatch.stop(); // optional
			// get elapsed time, expressed in milliseconds
			double timeElapsed = (stopwatch.elapsed(TimeUnit.MILLISECONDS)) / 1000;
			log.info("Unable to find Element after " + timeElapsed + "sec...");
		}
		return statusOfElementToBeReturned;
	}

	public static void enterText(WebDriver driver, By locatedElement, String enteredText) throws Exception {
		turnOffImplicitWaits(driver);
		int attempts = 0; 
		while (attempts < 4) {
			try {
				if ((new WebDriverWait(driver, 13).pollingEvery(Duration.ofMillis(200))
						.ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
						.withMessage("Unable to find element " + locatedElement))
								.until(ExpectedConditions.visibilityOfElementLocated(locatedElement)).isDisplayed()) {
					clickOnElementUsingJavaScript(driver, locatedElement);
					CypressUtil.doubleClickOnElement(driver, locatedElement);
					driver.findElement(locatedElement).sendKeys(enteredText);
					log.info("Text entered in the located element: " + locatedElement + " successfully.");
					break;
				}
			} catch (Exception e) {
				log.error(
						"Got Element Exception in " + (attempts + 1) + " attempt. Trying to send text again...!! " + e);
				pause(1000);
			} finally {
				attempts++;
			}

		}
		if (attempts == 4) {
			throw new Exception("Error:- Unable to send text in the element: " + locatedElement
					+ " , after trying to send the text 4 times.");
		}
	}

	public static void clickOnElementUsingJavaScript(WebDriver driver, By findByCondition) {

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", driver.findElement(findByCondition));
	}

	public static void enterTextInElementUsingJavaScript(WebDriver driver, By findByCondition, int waitInSeconds,
			String text) {
		waitForElementPresence(driver, findByCondition, waitInSeconds);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].value=arguments[1];", driver.findElement(findByCondition), text);
	}

	public static void click(WebDriver driver, By locatedElement) throws Exception {
		turnOffImplicitWaits(driver);
		int attempts = 0;
		while (attempts < 4) {
			try {
				(new WebDriverWait(driver, 30).pollingEvery(Duration.ofMillis(200))
						.ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
						.withMessage("Unable to click on button " + locatedElement))
								.until(ExpectedConditions.elementToBeClickable(locatedElement)).click();
				log.info("Element: " + locatedElement + " clicked successfully.");
				break;

			} catch (Exception e) {
				log.error("Got Element Exception in " + (attempts + 1) + " attempt. Trying to click again...!!" + e);
				pause(1000);
			} finally {
				attempts++;
			}

		}
		if (attempts == 4) {
			throw new Exception("Error:- Unable to click on element: " + locatedElement
					+ " , after trying to click on element 4 times.");
		}

	}

	public static boolean getTextAndVerifyWithExpectedText(WebDriver driver, By locatedElement, String expectedText) 
			throws Exception {
		turnOffImplicitWaits(driver);
		String actualText = null;
		int attempts = 0;
		boolean textFlag = false;
		while (attempts < 2) {
			try {
				if ((new WebDriverWait(driver, 13).pollingEvery(Duration.ofSeconds(2))
						.ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
						.withMessage("Unable to find element " + locatedElement))
								.until(ExpectedConditions.presenceOfElementLocated(locatedElement)).isDisplayed()) {

					actualText = driver.findElement(locatedElement).getText();
					log.info("Text fetched from the element: " + locatedElement + " successfully.");
					log.info("Actual Text fetched from the element is: " + actualText + ".");
					log.info("Expected Text is: " + expectedText + ".");
					break;
				}

			} catch (Exception e) {
				log.error("Got Element Exception in " + (attempts + 1) + " attempt. Trying to get text again...!!" + e);
				pause(1000);
			} finally {
				attempts++;
			}

		}
		if (attempts == 2) {
			throw new Exception(
					"Error:- Unable to get text of element: " + locatedElement + " , after trying 2 times.");
		}

		if (actualText.equals(expectedText)) {
			textFlag = true;
		}
		return textFlag;
	}

	public static String getElementAttributeValue(WebDriver driver, String elementLocator, String attributeType) {

		return SeleniumUtil.waitForElementPresence(driver, By.cssSelector(elementLocator), WAIT_SECONDS)
				.getAttribute(attributeType);
	}

	public static void openWebPage(WebDriver driver, String webUrl) {
		driver.get(webUrl);
	}

	public static String getAttributeTextOfElement(WebDriver driver, By locatedElement, String attributeValue)
			throws Exception {

		String text = "";
		int attempts = 0;
		while (attempts < 4) {
			try {
				text = (new WebDriverWait(driver, 30).pollingEvery(Duration.ofMillis(200))
						.ignoring(NoSuchElementException.class, StaleElementReferenceException.class)
						.withMessage("Unable to click on button " + locatedElement))
								.until(ExpectedConditions.presenceOfElementLocated(locatedElement))
								.getAttribute(attributeValue);
				log.info("Element: " + locatedElement + " clicked successfully.");
				break;

			} catch (Exception e) {
				log.error("Got Element Exception in " + (attempts + 1) + " attempt. Trying to click again...!!" + e);
				pause(1000);
			} finally {
				attempts++;
			}

		}
		if (attempts == 4) {
			throw new Exception("Error:- Unable to click on element: " + locatedElement
					+ " , after trying to click on element 4 times.");
		}
		return text;
	}
	
	public static Boolean isFileDownloaded(String fileName) {
        boolean flag = false;
        //paste your directory path below
        //eg: C:\\Users\\username\\Downloads
        String dirPath = "C:\\Users\\msingh\\Downloads"; 
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        if (files.length == 0 || files == null) {
            System.out.println("The directory is empty");
            flag = false;
        } else {
            for (File listFile : files) {
                if (listFile.getName().contains(fileName)) {
                    System.out.println(fileName + " is present");
                    break;
                }
                flag = true;
            }
        }
        return flag;
    }
	
	 public static String getLatestFilefromDir(){
		 String dirPath = "C:\\Users\\msingh\\Downloads";   
		 File dir = new File(dirPath);
		    File[] files = dir.listFiles();
		    if (files == null || files.length == 0) {
		        return null;
		    }

		    File lastModifiedFile = files[0];
		    for (int i = 1; i < files.length; i++) {
		       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
		           lastModifiedFile = files[i];
		       } 
		    }
		     latestdownloadedfile = lastModifiedFile.toString(); 
		  System.out.println(lastModifiedFile.getName());
		 //  a= lastModifiedFile.getAbsolutePath();
		    return lastModifiedFile.getName(); 
		    
		    } 
	 
	 
	public static   boolean verifyPDFContent(String reqTextInPDF) throws IOException {
		boolean flag = false;
			
			URL url = new URL ("file:///C:/Users/msingh/Downloads/"+getLatestFilefromDir());
			InputStream is = url.openStream();
			BufferedInputStream fileParse = new BufferedInputStream(is);
			PDDocument pdDoc = null;
			
			pdDoc = PDDocument.load(fileParse);
			String pdfContent = new PDFTextStripper().getText(pdDoc);
			System.out.println(pdfContent);
			
			
			if(pdfContent.contains(reqTextInPDF)) {
					flag=true;
					System.out.println(reqTextInPDF+ ":- is present in the downloaded PDF");
				}
			else {
				pdDoc.close();
			}
			pdDoc.close();
			return flag = true;
			
			}
	 public static final String delimiter = ",";
	   public static void read(String csvFile) {
	      try {
	         File file = new File(csvFile);
	         FileReader fr = new FileReader(file);
	         BufferedReader br = new BufferedReader(fr);
	         String line = "";
	         String[] tempArr;
	         int counter=0;
	         List<String> temp = new ArrayList<String>();
	         while((line = br.readLine()) != null) {
	            tempArr = line.split(delimiter);
	            String str = Arrays.toString(tempArr);
	            String [] split = str.split(","); 
	           
	        
	            for(String tempStr : tempArr) {
	            	
	            	if(counter>=3 && counter%2!=0) {
	            //   System.out.print(tempStr + " ");
	            	temp.add(tempStr);
	            	}
	              
	            	
	            	counter++;
	            }
	            System.out.println();
	         }
	         URL1 = temp.get(0);
	         System.out.println("First url is:- "+temp.get(0));
	         System.out.println("Second url is:- "+temp.get(1));
	         if(temp.get(0) != temp.get(1)) {
	        	 System.out.println("both URL's are unique");
	         }else {
				System.out.println("URL's are not unique");
			}
	         
	         
	         br.close();
	         } catch(IOException ioe) {
	            ioe.printStackTrace();
	         }
	   
	         }
	
}