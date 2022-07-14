package com.vendorinsight.utility;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CypressUtil {

	static Logger log = Logger.getLogger(CypressUtil.class.getName());
	static String projectNameActual;
	

	private static final int WAIT_SECONDS = Integer.parseInt(SeleniumUtil.fetchPropertiesDetails("GLOBAL_WAIT"));
	protected static final String USERNAME = SeleniumUtil.fetchPropertiesDetails("USERNAME");
	protected static final String PASSWORD = SeleniumUtil.fetchPropertiesDetails("PASSWORD");
	private static final By TOC_EDIT_BUTTON = By.xpath("//div[@title='Edit on/off']");

	private static final By CHEMISTRY_UNDER_FORMATTING_TOOLBAR = By
			.xpath("//div[@id='tinymceToolbar']//button[@title='WIRIS EDITOR chemistry']");
	private static final By CHEMISTRY_HEADER = By.xpath(
			"//div[@class='wrs_modal_dialogContainer wrs_modal_desktop wrs_stack']//div[text()='WIRIS EDITOR chemistry']");

	private static final By CARBON_UNDER_CHEMISTRY = By.xpath(
			"(//div[@class='wrs_multipleRowPanel wrs_selected']//table[@class='wrs_layoutFor3Rows'])[1]//button[@title='Carbon']");
	private static final By OXYGEN_UNDER_CHEMISTRY = By.xpath(
			"(//div[@class='wrs_multipleRowPanel wrs_selected']//table[@class='wrs_layoutFor3Rows'])[1]//button[@title='Oxygen']");
	private static final By WIRIS_EDITOR_OK = By.cssSelector("[class=wrs_modal_button_accept]");
	private static final By WIRIS_EDITOR_CLOSE_BUTTON = By.cssSelector("[class=wrs_modal_button_cancel]");
	private static final By MATHML_UNDER_FORMATTING_TOOLBAR = By.cssSelector("[aria-label='WIRIS EDITOR math']");
	private static final By MATHML_HEADER = By.xpath(
			"//div[@class='wrs_modal_dialogContainer wrs_modal_desktop wrs_stack']//div[text()='WIRIS EDITOR math']");
	private static final By UNDERROOT_UNDER_MATHML = By.xpath(
			"//div[@class='wrs_multipleRowPanel wrs_selected']//table[@class='wrs_layoutFor2Rows']/tbody/tr/td[2]/button[@title='Root']");
	private static final By BODY_MATTER_LIST = By.xpath(
			"//div[@class='bodymatter']/div[contains(@data-urn, 'urn:pearson:entity:')]/div[1]//div[@class='descBox']");

	// private static final By INPUT_FIELD_PROJECT_NAME =
	// By.xpath("//input[@placeholder='search projects']");
	private static final By INPUT_FIELD_PROJECT_NAME = By.xpath("//input[@placeholder='Search Projects']");
	private static final By ITALICS_BUTTON = By.cssSelector("button[aria-label=Italic]");
	public boolean result = false;
	public static String MOUSE_HOVER_JS = "var evObj = document.createEvent('MouseEvents');"
			+ "evObj.initMouseEvent(\"mouseover\",true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);"
			+ "arguments[0].dispatchEvent(evObj);";

	private static final By SAVING_CALL_LOADER = By.cssSelector(".loader.center-alignContent");
	private static final By SLATE_REFRESH = By.cssSelector(".tooltip.right span svg");
	private static final By SLATE_PREVIEW_UNCLICKED = By
			.xpath("(//div[@class='header-options ']//span[@class='icon'])[1]");
	private static final By BLOCKER_HIDE = By.xpath("//div[@class='blocker hide']");
	private static final By HEADER_OVERLAY = By.cssSelector("div .header-overlay-div");
	private static final By CLOSE_POPUP = By.xpath("(//button[@class='MuiButtonBase-root MuiIconButton-root btn-close']//span)[1]");
	
	private static final By LINK_PRODUCT_DROPDOWN = By.xpath("//div[@class='ext_ProductLink']/div[2]//input");
	private static final By LINK_PRODUCT_WINDOW = By.xpath("//div[@class='app__plModalTitle___1-zBW']");
	private static final By LINK_PRODUCT_LINK_BUTTON = By
			.xpath("//div[@class='ext_ProductLink']//button[contains(text(),'Link')]");
	
	private static final By INSERT_IMAGE_TABLE = By.xpath("//div[@class='insert-media-icon']//span[text()='Insert']");
	private static final By INSERT_IMAGE_LIST = By.xpath("(//button//span[text()='Insert'])[1]");
	protected static final String USERNAME_EMAIL = SeleniumUtil.fetchPropertiesDetails("USERNAME_EMAIL");
	
	
	
	public boolean checkForNavigationPanelLoading(WebDriver driver) throws InterruptedException {
		DOMConfigurator.configure("log4j.xml");
		boolean checkLoading = false;

		SeleniumUtil.turnOffImplicitWaits(driver);

		try {

			checkLoading = driver
					.findElement(By.xpath(
							"//div[@id='navigation-panel-content-container']//*[contains(text(),'Loading ...')]"))
					.isDisplayed();

			if (checkLoading) {

				for (int i = 1; i <= 60; i++) {

					try {
						checkLoading = driver.findElement(By.xpath(
								"//div[@id='navigation-panel-content-container']//*[contains(text(),'Loading ...')]"))
								.isDisplayed();

						if (!checkLoading) {
							log.info("Navigation LOADER DISAPPEARED");
							break;
						} else {
							log.info("Navigation LOADER APPEARING");
						}

					} catch (Exception e) {
						// TODO: handle exception
						log.info("Navigation LOADER NOT APPEARING");
						break;
					}
					Thread.sleep(1000);
				}

			} else {
				log.info("Navigation LOADER NOT APPEARING");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info("NO Navigation LOADER");
		} finally {
			//
		}
		return checkLoading;
	}

	/**
	 * 
	 * To wait for the page spinner which comes under the main HTML, i.e., outside
	 * any frame
	 * 
	 * @param driver
	 * @return boolean
	 */
	public static boolean checkForPageLoading(WebDriver driver) {
		DOMConfigurator.configure("log4j.xml");
		boolean checkLoading = false;

		SeleniumUtil.turnOffImplicitWaits(driver);
		try {

			checkLoading = driver.findElement(By.xpath("//div[@class='spinner']")).isDisplayed();

			if (checkLoading) {

				for (int i = 1; i <= 60; i++) {

					try {
						checkLoading = driver.findElement(By.xpath("//div[@class='spinner']")).isDisplayed();

						if (!checkLoading) {
							log.info("PAGE LOADER DISAPPEARED");
							break;
						} else {
							log.info("PAGE LOADER APPEARING");
						}

					} catch (Exception e) {
						// TODO: handle exception
						log.info("PAGE LOADER NOT APPEARING");
						break;
					}
					SeleniumUtil.pause(2000);
				}

			} else {
				log.info("PAGE LOADER NOT APPEARING");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info("NO PAGE LOADER");
		} finally {
			//
		}

		return checkLoading;
	}

//	public boolean loginAndProjectSelect(String projectName, WebDriver driver) throws Exception {
//		DOMConfigurator.configure("log4j.xml");
//
//		DashboardPage dashboardPage = login2(driver);
//		// dashboardPage.checkProjectDashboardPresence();
//		Thread.sleep(3000);
//		//driver.findElement(By.xpath("")).click();
//		searchingProjectOnDashboard(driver, projectName);
//		dashboardPage.clickEditProjectButtonByProjectName(projectName);
//
//		SeleniumUtil.pause(10000);
//
//		SeleniumUtil.closeCurrentBrowserTab(driver);
//		SeleniumUtil.switchWindowByIndex(driver, 0);
//		SeleniumUtil.switchToDefaultContent(driver);
//		checkForPageLoading(driver);
//		SeleniumUtil.waitForElementPresence(driver, By.xpath("//div[@class='blocker hide']"), 180);
//		ProjectTopPanelPage projectTopPanelPage = new ProjectTopPanelPage(driver);
//		return projectTopPanelPage.verifyDashboardLogoIcon();
//	}

	public boolean checkForSlateElementLoading(WebDriver driver) throws InterruptedException {
		DOMConfigurator.configure("log4j.xml");
		boolean checkLoading = false;

		try {

			SeleniumUtil.turnOffImplicitWaits(driver);

			checkLoading = driver.findElement(By.xpath("//p[text()='Loading ...']")).isDisplayed();
			checkLoading = driver.findElement(By.xpath("//div[@class='loadingmessage']")).isDisplayed();

			if (checkLoading) {

				for (int i = 1; i <= 60; i++) {

					try {
						checkLoading = driver.findElement(By.xpath("//div[@class='loadingmessage']")).isDisplayed();

						if (!checkLoading) {
							log.info("LOADER DISAPPEARED");
							break;
						} else {
							log.info("LOADER APPEARING");
						}

					} catch (Exception e) {
						// TODO: handle exception
						log.info("LOADER NOT APPEARING");
						break;
					}
					Thread.sleep(1000);
				}

			} else {
				log.info("LOADER NOT APPEARING");
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.info("NO LOADER");
		} finally {
			//
		}

		return checkLoading;
	}

//	public void insertSlateInFMForElementTesting(WebDriver driver) throws Exception {
//		DOMConfigurator.configure("log4j.xml");
//		NavigationPanelPage navigationPanelPage = new NavigationPanelPage(driver);
//		boolean navigationPanelCheck = navigationPanelPage.openNavigationPanel();
//		log.info("Navigation Panel Open status: " + navigationPanelCheck);
//
//		SeleniumUtil.pause(5000);
//
//		ArrayList listOFSlateORASBefore = navigationPanelPage.getListofSlateORASFromFM();
//
//		int countOfSlatesOrASinFMBefore = navigationPanelPage.countOFSlateOrAsInFM();
//
//		SeleniumUtil.switchingIframeToIframe(driver, "canvas-iframe");
//
//		checkForPageLoading(driver);
//
//		SeleniumUtil.switchingIframeToIframe(driver, "toc-iframe");
//		navigationPanelPage.clickFrontMatterByIndex(1);
//
//		checkForPageLoading(driver);
//
//		navigationPanelPage.addTOCStructure("slate");
//
//		for (int i = 1; i <= 120; i++) {
//
//			int countOfSlatesOrASinFMAfter = navigationPanelPage.countOFSlateOrAsInFM();
//
//			if (countOfSlatesOrASinFMAfter == (countOfSlatesOrASinFMBefore + 1)) {
//				log.info("Slate added successfully");
//				break;
//			}
//
//			Thread.sleep(1000);
//		}
//
//		ArrayList listOFSlateORASAfter = navigationPanelPage.getListofSlateORASFromFM();
//
//		// int index = listOFSlateORASAfter.indexOf("");
//
//		log.info("New Slate added at index: " + (2));
//
//		Thread.sleep(2000);
//
//		checkForPageLoading(driver);
//
//		Thread.sleep(1000);
//
//		String textOfSlateSelected = navigationPanelPage.getSlateNameInFMByIndex((2));
//
//		SeleniumUtil.click(driver, TOC_EDIT_BUTTON);
//
//		log.info("Selected slate title: " + textOfSlateSelected);
//
//		Thread.sleep(2000);
//
//		checkForPageLoading(driver);
//		Thread.sleep(2000);
//		checkForPageLoading(driver);
//		navigationPanelPage.clickFrontMatterByIndex((2));
//
//		Thread.sleep(2000);
//
//		checkForSlateElementLoading(driver);
//
//		SlatePage slatePage = new SlatePage(driver);
//
//		SeleniumUtil.switchingIframeToIframe(driver, "canvas-iframe");
//		String textOfSlateOpened = slatePage.getOpenedSlateTitle();
//
//		log.info("Opened slate title: " + textOfSlateOpened);
//		checkForSlateElementLoading(driver);
//
//	}
//
//	public void insertSlateInBMForElementTesting(WebDriver driver) throws Exception {
//		DOMConfigurator.configure("log4j.xml");
//		NavigationPanelPage navigationPanelPage = new NavigationPanelPage(driver);
//		boolean navigationPanelCheck = navigationPanelPage.openNavigationPanel();
//		log.info("Navigation Panel Open status: " + navigationPanelCheck);
//
//		boolean checkExpandCollapseBM = navigationPanelPage.checkTOCExpandCollapseStatus("back matter");
//		log.info("Back Matter expand status: " + checkExpandCollapseBM);
//		if (!checkExpandCollapseBM) {
//			navigationPanelPage.expandBackMatterTOC();
//		}
//
//		Thread.sleep(2000);
//
//		ArrayList listOFSlateORASBefore = navigationPanelPage.getListofSlateORASFromBM();
//
//		int countOfSlatesOrASinBMBefore = navigationPanelPage.countOFSlateOrAsInBM();
//
//		SeleniumUtil.switchingIframeToIframe(driver, "canvas-iframe");
//
//		checkForPageLoading(driver);
//
//		SeleniumUtil.switchingIframeToIframe(driver, "toc-iframe");
//
//		navigationPanelPage.clickBackMatterByIndex(1);
//
//		Thread.sleep(2000);
//
//		checkForPageLoading(driver);
//
//		Thread.sleep(1000);
//
//		navigationPanelPage.addTOCStructure("slate");
//
//		for (int i = 1; i <= 120; i++) {
//
//			int countOfSlatesOrASinBMAfter = navigationPanelPage.countOFSlateOrAsInBM();
//
//			if (countOfSlatesOrASinBMAfter == (countOfSlatesOrASinBMBefore + 1)) {
//				log.info("Slate added successfully");
//				break;
//			}
//
//			Thread.sleep(1000);
//		}
//
//		ArrayList listOFSlateORASAfter = navigationPanelPage.getListofSlateORASFromBM();
//
////		int index = listOFSlateORASAfter.indexOf("Untitled Slate");
//
//		log.info("New Slate added at: " + (1 + 1));
//
//		Thread.sleep(2000);
//
//		checkForPageLoading(driver);
//
//		Thread.sleep(1000);
//
//		String textOfSlateSelected = navigationPanelPage.getSlateNameInBMByIndex((2));
//		SeleniumUtil.click(driver, TOC_EDIT_BUTTON);
//
//		log.info("Selected slate title: " + textOfSlateSelected);
//
//		navigationPanelPage.clickBackMatterByIndex((2));
//
//		Thread.sleep(2000);
//
//		checkForPageLoading(driver);
//
//		Thread.sleep(1000);
//		String selectStateText = navigationPanelPage.getSlateNameInBMByIndex((2));
//		log.info("selectStateText " + selectStateText);
//
//		Thread.sleep(2000);
//
//		checkForPageLoading(driver);
//
//		Thread.sleep(2000);
//
//		checkForSlateElementLoading(driver);
//
//		SlatePage slatePage = new SlatePage(driver);
//		SeleniumUtil.switchingIframeToIframe(driver, "canvas-iframe");
//
//		String textOfSlateOpened = slatePage.getOpenedSlateTitle();
//
//		log.info("Opened slate title: " + textOfSlateOpened);
//
//		checkForSlateElementLoading(driver);
//	}

	/**
	 * @param index
	 * @param type
	 * @param driver
	 * @param elementName
	 * @throws Exception
	 * 
	 *                   created by Harshit
	 */
	public void addElementByType(int index, String type, WebDriver driver, String... elementName) throws Exception {
		String subElementName = "";
		if ((elementName.length > 0) && (!elementName.equals(null))) {
			subElementName = elementName[0];
		}

		By valueInSecondaryDropDown = By.xpath("//li[text()='Add " + subElementName + "']");

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		By webElementIf = By
				.xpath("//div[contains(@class, 'opacityClassOn ignore-for-drag')]//span[contains(@class,'btn-element "
						+ type + "')]");
		By webElementElse = By.xpath(
				"(//div[@id='appender'])[" + index + "]//li[@class='add-object add-media__" + type + " tooltip']");

		if (!(type.contains("metadata"))) {
			try {
				executor.executeScript("arguments[0].click();", driver.findElement(webElementIf));

				if ((type.equals("interactive")) || (type.equals("container"))) {

					SeleniumUtil.click(driver, valueInSecondaryDropDown);

				} else if ((type.equals("block-text"))) {
					By valueInSecondaryDropDownBlock = By.xpath("//li[text()='" + subElementName + "']");
					SeleniumUtil.click(driver, valueInSecondaryDropDownBlock);

				}

			} catch (Exception e) {
				SeleniumUtil.click(driver, webElementIf);
				if ((type.equals("interactive")) || (type.equals("container"))) {

					SeleniumUtil.waitForElementPresence(driver, valueInSecondaryDropDown, 15).click();
				} else if ((type.equals("block-text"))) {
					By valueInSecondaryDropDownBlock = By.xpath("//li[text()='" + subElementName + "']");
					SeleniumUtil.click(driver, valueInSecondaryDropDownBlock);

				}

			}
		} else if (type.contains("metadata")) {
			By webElementMetadata = By.xpath(
					"//div[contains(@class, 'opacityClassOn ignore-for-drag')]//span[@class='btn-element metadata-anchor']");
			executor.executeScript("arguments[0].click();", driver.findElement(webElementMetadata));
		}

		else {
			SeleniumUtil.click(driver, webElementElse);
		}
		CypressUtil.waitForAllPageLoaders(driver);
	}

//	protected DashboardPage login(WebDriver driver) {
//		LoginPage loginPage = new LoginPage(driver);
//		return loginPage.loginUser(USERNAME, PASSWORD);
//	}
//	
//	//New login method after change in login process-Dual authentication login process introduced
//		protected DashboardPage login2(WebDriver driver) {
//			LoginPage loginPage = new LoginPage(driver);
//			return loginPage.loginUser2(USERNAME_EMAIL,USERNAME, PASSWORD);
//		}

	public void waitForElementToDisplay(WebDriver driver, By elementToBeVisible) throws Exception {
		boolean res = false;
		int counter = 0;

		try {
			while (!res) {
				res = SeleniumUtil.waitForElementPresence(driver, elementToBeVisible, 20).isDisplayed();
				counter++;
				if (res || counter == 60) {
					break;
				}
			}
			Thread.sleep(1000);
		} catch (Exception e) {
			log.info("Again trying to find element....");
			SeleniumUtil.waitForElementPresence(driver, elementToBeVisible, 6).isDisplayed();

		} finally {
		}
	}

	public boolean verifyingMultipleFlag(WebDriver driver, boolean checkedResolved, boolean checkedOpen,
			String firstCondition, String secondCondition) {
		boolean res = false;
		if (checkedResolved) {
			res = true;
			if (checkedOpen) {
				res = true;
				log.info(firstCondition + " : Success");
				log.info(secondCondition + " : Success");
			} else {
				log.info(firstCondition + " : Success");
				log.info(secondCondition + " : Failed");
				res = false;
			}
		} else {
			log.info(firstCondition + " : Failed");
			log.info(secondCondition + " : Failed");
			res = false;
		}
		return res;
	}

	public void waitForLockElementKey(WebDriver driver) throws InterruptedException {
		final By elementLockKey = By.xpath("//div[@id='lockblocker tooltip']/i[@class='fa fa-key']");
		SeleniumUtil.waitForElementPresence(driver, elementLockKey, WAIT_SECONDS);
		Thread.sleep(2000);
	}

	public boolean checkArrowCondition(WebDriver driver) {

		SeleniumUtil.turnOffImplicitWaits(driver);
		Boolean checkArrow = false;

		try {
			checkArrow = driver.findElement(By.xpath(
					"(//li[contains(@class, 'selected')]//div[@class='accordian__btn']/*[contains(@class,'u-rotate-90')])[1]"))
					.isDisplayed();
		} catch (Exception e) {
			checkArrow = false;
		}

		return checkArrow;
	}

	public void selectNewAddedTocElement(WebDriver driver, int chapterIndex) {
		int newChapterIndex = chapterIndex + 1;
		final By selectingFirstChapterUnderToc = By.xpath(
				"(//div[@class='bodymatter']/div[contains(@data-urn, 'urn:pearson:entity:')]/div[1]//div[@class='descBox' and text()='Chapter'])["
						+ newChapterIndex + "]");
		SeleniumUtil.waitForElementPresence(driver, selectingFirstChapterUnderToc, WAIT_SECONDS).click();
		SeleniumUtil.switchingIframeToIframe(driver, "canvas-iframe");
		checkForSavingCallLoading(driver);
		checkForWrapperLoading(driver);
		SeleniumUtil.switchingIframeToIframe(driver, "toc-iframe");
		SeleniumUtil.pause(1500);
	}

	public int selectFirstChapterUnderTOC(WebDriver driver) throws Exception {
		int chapterIndex = 0;
		ArrayList<String> tocContent = new ArrayList<String>();
		List<WebElement> bodyMatterListOption = driver.findElements(BODY_MATTER_LIST);
		for (WebElement abc : bodyMatterListOption) {
			tocContent.add(abc.getText());
		}

		for (String abc : tocContent) {
			chapterIndex++;
			if (abc.contains("Chapter")) {
				final By selectingFirstChapterUnderToc = By.xpath(
						"(//div[@class='bodymatter']/div[contains(@data-urn, 'urn:pearson:entity:')]/div[1]//div[@class='descBox' and text()='Chapter'])["
								+ chapterIndex + "]");
				SeleniumUtil.click(driver, selectingFirstChapterUnderToc);
				break;
			}
		}

		if (chapterIndex == tocContent.size())
			log.info("Atleast one Chapter is required to addParts");
		return chapterIndex;
	}

	public void selectTocElementByIndex(WebDriver driver, int selectedChapterIndex) throws InterruptedException {
		final By selectingFirstChapterUnderToc = By
				.xpath("(//div[@class='chapter-list']/ul[2]/li/div[1]/div/span[1])[" + selectedChapterIndex + "]");
		SeleniumUtil.waitForElementPresence(driver, selectingFirstChapterUnderToc, WAIT_SECONDS).click();
		SeleniumUtil.switchingIframeToIframe(driver, "canvas-iframe");
		checkForSavingCallLoading(driver);
		checkForWrapperLoading(driver);
		SeleniumUtil.switchingIframeToIframe(driver, "toc-iframe");
		SeleniumUtil.pause(1000);
	}

	public void addChemistryContent(WebDriver driver) {

		try {

			SeleniumUtil.waitForElementClickable(driver, CHEMISTRY_UNDER_FORMATTING_TOOLBAR, WAIT_SECONDS).click();
			SeleniumUtil.waitForElementPresence(driver, CHEMISTRY_HEADER, 180);
			SeleniumUtil.waitForElementVisibility(driver, CARBON_UNDER_CHEMISTRY, 180);
			SeleniumUtil.waitForElementPresence(driver, CARBON_UNDER_CHEMISTRY, WAIT_SECONDS).click();
			SeleniumUtil.waitForElementPresence(driver, OXYGEN_UNDER_CHEMISTRY, WAIT_SECONDS).click();
			SeleniumUtil.waitForElementPresence(driver, WIRIS_EDITOR_OK, WAIT_SECONDS).click();
			SeleniumUtil.waitForElementInvisibility(driver, WIRIS_EDITOR_OK, WAIT_SECONDS);
			checkForSavingCallLoading(driver);
			SeleniumUtil.pause(2000);
			driver.switchTo().defaultContent();
			SeleniumUtil.pause(1000);
		} catch (Exception e) {
			driver.switchTo().frame("canvas-iframe");
			final By wirisPopupCloseButton = By.xpath("//div[@title='Close']");
			SeleniumUtil.waitForElementVisibility(driver, wirisPopupCloseButton, WAIT_SECONDS).click();
			SeleniumUtil.pause(1000);
		}
	}

//	public void addMathMLContent(WebDriver driver) {
//
//		try {
//			Log.info("Clicking on WIRIS EDITOR math.");
//			SeleniumUtil.waitForElementClickable(driver, MATHML_UNDER_FORMATTING_TOOLBAR, WAIT_SECONDS).click();
//			Log.info("Clicked on WIRIS EDITOR math.");
//			SeleniumUtil.waitForElementPresence(driver, MATHML_HEADER, 180);
//			SeleniumUtil.waitForElementVisibility(driver, UNDERROOT_UNDER_MATHML, 180);
//			SeleniumUtil.waitForElementPresence(driver, UNDERROOT_UNDER_MATHML, WAIT_SECONDS).click();
//			SeleniumUtil.pause(2000);
//
//			Actions act = new Actions(driver);
//			act.sendKeys(Keys.ARROW_UP);
//			act.sendKeys(Keys.NUMPAD3).build().perform();
//			SeleniumUtil.pause(1000);
//			act.sendKeys(Keys.NUMPAD4).build().perform();
//			SeleniumUtil.pause(1000);
//			act.sendKeys(Keys.ARROW_DOWN);
//			SeleniumUtil.pause(1000);
//			act.sendKeys(Keys.NUMPAD1).build().perform();
//			SeleniumUtil.pause(1000);
//			act.sendKeys(Keys.NUMPAD2).build().perform();
//			SeleniumUtil.pause(2000);
//
//			SeleniumUtil.waitForElementPresence(driver, WIRIS_EDITOR_OK, WAIT_SECONDS).click();
//
//			
//
//			
//			//Code added to perform second click on Ok button of WIRIS Editor window 25-04-2021
//			if(SeleniumUtil.isElementPresent(driver, WIRIS_EDITOR_OK))
//			{
//				SeleniumUtil.waitForElementPresence(driver, WIRIS_EDITOR_OK, WAIT_SECONDS).click();
//			}
//			
//			SeleniumUtil.waitForElementInvisibility(driver, WIRIS_EDITOR_OK, WAIT_SECONDS);
//
//			CypressUtil.waitForAllPageLoaders(driver);
//		} catch (Exception e) {
//			SeleniumUtil.waitForElementVisibility(driver, WIRIS_EDITOR_CLOSE_BUTTON, WAIT_SECONDS).click();
//			SeleniumUtil.waitForElementInvisibility(driver, WIRIS_EDITOR_CLOSE_BUTTON, WAIT_SECONDS);
//			checkForSavingCallLoading(driver);
//			SeleniumUtil.pause(2000);
//		}
//	}

	public WebElement scrollToViewElement(WebDriver driver, By findByCondition) {
		WebElement element = driver.findElement(findByCondition);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		return driver.findElement(findByCondition);
	}

	public void selectNewAddedFrontMatterSlate(WebDriver driver, int slateIndex) throws InterruptedException {
		int newSlateIndex = slateIndex + 1;
		final By selectingNewSlateUnderFM = By
				.xpath("(//div[@class='toc-contents']/div[1]/div/div[2]/div)[" + newSlateIndex + "]");

		try {
			SeleniumUtil.click(driver, selectingNewSlateUnderFM);
			log.info("Slate selected number: " + newSlateIndex);
		} catch (Exception e) {
			SeleniumUtil.waitForElementPresence(driver, selectingNewSlateUnderFM, WAIT_SECONDS).click();
			log.info("Slate selected number: " + newSlateIndex);
		}
		SeleniumUtil.switchingIframeToIframe(driver, "canvas-iframe");
		SeleniumUtil.switchingIframeToIframe(driver, "toc-iframe");
	}

//	public boolean insertingNewElementOnSlateAndVerifyingItsTagName(WebDriver driver, String elementType,
//			String tagNameOfAddedElement, String slateName, String... elementName) throws Exception {
//
//		String subElementName = "";
//		if ((elementName.length > 0) && (!elementName.equals(null))) {
//			subElementName = elementName[0];
//		}
//
//		NavigationPanelPage navigationPanel = new NavigationPanelPage(driver);
//		navigationPanel.openNavigationPanel();
//
//		FrontMatterPage frontmatter = new FrontMatterPage(driver);
//		int slateToSelect = frontmatter.insertSlateInFrontMatter(driver);
//		selectNewAddedFrontMatterSlate(driver, slateToSelect);
//		navigationPanel.renameSlate(slateName, slateToSelect + 1);
//		navigationPanel.closeNavigationPanel();
//
//		SeleniumUtil.switchingIframeToIframe(driver, "canvas-iframe");
//		SlatePage slatePage = new SlatePage(driver);
//
//		// Temporary fix starts for July Release regarding no default element on a slate
//		int countOfElementbeforeAddingPara = slatePage.getCountOfElementOnaSlate();
//		log.info("Count of Elements on the slate by Default: " + countOfElementbeforeAddingPara);
//		slatePage.addingElementOnTheSlateAtTheEnd("text", countOfElementbeforeAddingPara);
//		slatePage.waitForElementToGetAddedByItsTag("P", 1);
//
//		// END
//
//		int countOfElement = slatePage.getCountOfElementOnaSlate();
//		log.info("Count of Elements on the slate: " + countOfElement);
//
//		slatePage.addingElementOnTheSlateAtTheEnd(elementType, countOfElement, subElementName);
//		SeleniumUtil.pause(1500);
//		String addedBlockTag = slatePage.waitForElementToAdd(countOfElement);
//		log.info("Block Tag for Element added: " + addedBlockTag);
//
//		if (addedBlockTag.equals(tagNameOfAddedElement)) { 
//			result = true;
//		} else {
//			log.info("Some error. Tag name of the element is not as expected.");
//		}
//		return result; 
//	}

	public void searchingProjectOnDashboard(WebDriver driver, String projectName) throws Exception {
		
		try {
			if(driver.findElement(CLOSE_POPUP).isDisplayed()) {
				SeleniumUtil.waitForElementPresence(driver, CLOSE_POPUP, 120);
				SeleniumUtil.click(driver, CLOSE_POPUP);
			}	
		}catch (Exception e) {
			SeleniumUtil.waitForElementPresence(driver, INPUT_FIELD_PROJECT_NAME, 120);
			SeleniumUtil.enterText(driver, INPUT_FIELD_PROJECT_NAME, projectName);	
		}	
	}

	public void selectingTextToHighlightByElementType(WebDriver driver, String elementName) {
		By element = null;
		String text = "Testing text ";
		if (elementName == "Heading 1") {
			element = By.xpath("//h1[@class='heading1NummerEins']/parent::div");
			SeleniumUtil.waitForElementPresence(driver, element, WAIT_SECONDS).sendKeys(text);
		} else if (elementName == "Paragraph") {
			element = By.xpath("(//p[@class='paragraphNumeroUno']/parent::div)[1]");
			SeleniumUtil.waitForElementPresence(driver, element, WAIT_SECONDS).sendKeys(text);
		} else if (elementName == "Heading 2") {
			element = By.xpath("//h2[@class='heading2NummerEins']/parent::div");
			SeleniumUtil.waitForElementPresence(driver, element, WAIT_SECONDS).sendKeys(text);
		} else if (elementName == "Heading 3") {
			element = By.xpath("//h3[@class='heading3NummerEins']/parent::div");
			SeleniumUtil.waitForElementPresence(driver, element, WAIT_SECONDS).sendKeys(text);
		} else if (elementName == "Heading 4") {
			element = By.xpath("//h4[@class='heading4NummerEins']/parent::div");
			SeleniumUtil.waitForElementPresence(driver, element, WAIT_SECONDS).sendKeys(text);
		} else if (elementName == "Heading 5") {
			element = By.xpath("//h5[@class='heading5NummerEins']/parent::div");
			SeleniumUtil.waitForElementPresence(driver, element, WAIT_SECONDS).sendKeys(text);
		} else if (elementName == "Heading 6") {
			element = By.xpath("//h6[@class='heading6NummerEins']/parent::div");
			SeleniumUtil.waitForElementPresence(driver, element, WAIT_SECONDS).sendKeys(text);
		} else if (elementName == "PullQuote") {
			element = By.xpath("//div[contains(@class,'pullquote-editor mce-edit-focus')]");
			SeleniumUtil.waitForElementPresence(driver, element, WAIT_SECONDS).sendKeys(text);
		} else if (elementName == "Learning Objective") {
			element = By.xpath("//h2[@class='heading2learningObjectiveItem']/parent::div");
			SeleniumUtil.waitForElementPresence(driver, element, WAIT_SECONDS).sendKeys(text);
		} else if (elementName == "MarginiliaQuote") {
			element = By.xpath("(//p[@class='paragraphNummerEins'])[1]");
			SeleniumUtil.waitForElementPresence(driver, element, WAIT_SECONDS).sendKeys(text);
		} else if (elementName == "MarginiliaAttrQuote") {
			element = By.xpath("//blockquote[@class='blockquoteMarginaliaAttr']/p[@class='paragraphNummerEins'][1]");
			SeleniumUtil.waitForElementPresence(driver, element, WAIT_SECONDS).sendKeys(text);
		} else if (elementName == "list") {
			element = By.xpath("//div[contains(@class,'mce-content-body')]");
			SeleniumUtil.waitForElementPresence(driver, element, WAIT_SECONDS).sendKeys(text);
		} else if (elementName == "upperAlphaList") {
			element = By.xpath("(//div[@class='fr-element fr-view'])[4]");
			SeleniumUtil.waitForElementPresence(driver, element, WAIT_SECONDS).sendKeys(text);
		} else if (elementName == "lowerAlphaList") {
			element = By.cssSelector(
					"[class='element-container ol active'] div[class='cypress-editable mce-content-body mce-edit-focus']");
			SeleniumUtil.waitForElementPresence(driver, element, WAIT_SECONDS).sendKeys(text);
		} else if (elementName == "Smartlink title") {
			//element = By.cssSelector("#cypress-1-1"); 
			element = By.cssSelector("#cypress-1-2");
			SeleniumUtil.waitForElementPresence(driver, element, WAIT_SECONDS).sendKeys(text);
		}
		Actions action = new Actions(driver);

		WebElement ele = driver.findElement(element);
		action.doubleClick(ele).build().perform();
		try {
			action.sendKeys(Keys.chord(Keys.CONTROL, "a"));

		} catch (Exception e) {
			ele.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		}

	}

	public void clickingItalicsButton(WebDriver driver) {
		SeleniumUtil.waitForElementClickable(driver, ITALICS_BUTTON, WAIT_SECONDS);
		WebElement ele = driver.findElement(ITALICS_BUTTON);
		Actions action = new Actions(driver);
		action.moveToElement(ele).click().perform();

	}

	public void italicizingEnteredText(WebDriver driver, String elementName) {

		selectingTextToHighlightByElementType(driver, elementName);
		clickingItalicsButton(driver);
	}

//	public void enteringTextWithFootnoteMathMLAndItalics(WebDriver driver, String elementName,
//			boolean... flagForItalics) throws Exception {
//		FootNotePage footNote = new FootNotePage(driver);
//		SeleniumUtil.pause(2000);
//		addMathMLContent(driver);
//		Boolean flag = false;
//		if ((flagForItalics.length == 1) && flagForItalics[0] == true) {
//			flag = flagForItalics[0];
//
//			if (flag == true) {
//				boolean presence = SeleniumUtil.waitForElementPresence(driver, ITALICS_BUTTON, WAIT_SECONDS)
//						.isDisplayed();
//				if (presence) {
//					italicizingEnteredText(driver, elementName);
//				} else {
//					italicizingEnteredText(driver, elementName);
//					checkForSavingCallLoading(driver);
//				}
//			}
//		}
//		footNote.clickingFootNoteButton();
//		footNote.enteringFootNote();
//		SeleniumUtil.pause(3500);
//		footNote.clickingSaveButton();
//		CypressUtil.waitForAllPageLoaders(driver);
//	}
//
//	public void clickingToUnlockElementAndConvertingIntoListType(WebDriver driver, String elementToBeConverted,
//			int index, String elementConvertedInto) throws Exception {
//		SlatePage slatePage = new SlatePage(driver);
//		slatePage.unlockElementByIndex(elementToBeConverted, index);
//
//		SeleniumUtil.click(driver, By.cssSelector("[data-element='primary']"));
//		SeleniumUtil.click(driver, By.xpath("//li[@data-value='primary-list']"));
//		SeleniumUtil.click(driver, By.cssSelector("[data-element='secondary']"));
//		SeleniumUtil.pause(2000);
//		SeleniumUtil.click(driver,
//				By.xpath("//li[contains(@data-value,'secondary-list-') and text()='" + elementConvertedInto + "']"));
//	}
//
	public static boolean waitForElement(WebDriver driver, WebElement element, int maxWait) {
		boolean statusOfElementToBeReturned = false;
		WebDriverWait wait = new WebDriverWait(driver, maxWait);
		try {
			WebElement waitElement = wait.until(ExpectedConditions.visibilityOf(element));
			if (waitElement.isDisplayed() && waitElement.isEnabled()) {
				statusOfElementToBeReturned = true;
				log.info("Element is displayed:: " + element.toString());
			}
		} catch (Exception ex) {
		}
		return statusOfElementToBeReturned;
	}

	public static void scrollIntoView(WebDriver driver, WebElement element) {
		try {
			String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
					+ "var elementTop = arguments[0].getBoundingClientRect().top;"
					+ "window.scrollBy(0, elementTop-(viewPortHeight/2));";
			((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);

		} catch (Exception ex) {
			// Log.event("Moved to element..");
		}
	}

	public static void moveToElementJS(WebDriver driver, WebElement element) {
		((JavascriptExecutor) driver).executeScript(MOUSE_HOVER_JS, element);
	}

//	public boolean clickEnter(WebDriver driver, int index) {
//		Actions action = new Actions(driver);
//		By newParagraphElement = By
//				.xpath("(//div[@class='editor  '])[" + index + "]//div[contains(@class,'element-container p')]");
//		try {
//			action.sendKeys(Keys.ENTER).perform();
//			SeleniumUtil.pause(5000);
//			checkForSavingCallLoading(driver);
//			checkForWrapperLoading(driver);
//			if (SeleniumUtil.waitForElementVisibility(driver, newParagraphElement, WAIT_SECONDS).isDisplayed()) {
//
//				SlatePage slatePage = new SlatePage(driver);
//				int countOfElement = slatePage.getCountOfElementOnaSlate();
//
//				log.info("Count of Elements on the slate: " + countOfElement);
//
//				String blockTag = slatePage.getBlockTagByIndex(countOfElement);
//				log.info(blockTag);
//				if (blockTag.equals("P")) {
//					result = true;
//				}
//			}
//		} catch (Exception e) {
//			result = false;
//			log.info("Some error while adding a new paragraph element via clicking. " + e);
//
//		}
//		return result;
//	}

	public void clickOnElementToPointOnSlateByIndex(WebDriver driver, int countOfElement) throws Exception {

		SeleniumUtil.pause(2000);
		By elementLocated = By.xpath("(//div[@class='editor  '])[" + countOfElement + "]//div/div");
		SeleniumUtil.click(driver, elementLocated);
	}

	public String dateAndTimeStamp() {
		Date now = new Date();
		SimpleDateFormat timeStamp = new SimpleDateFormat("yyyy-MM-dd  HH.mm.ss");
		return timeStamp.format(now);

	}

	public static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public void clickOnElement(WebDriver driver) {

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", driver.findElement(By.cssSelector(
				"[container-type='element-aside'] [class='elementSapratorContainer ignore-for-drag']:first-child [class='elemDiv-hr']")));

	}

	public static void doubleClickOnElement(WebDriver driver, By elementLocator) {
		Actions action = new Actions(driver);
		action.doubleClick(driver.findElement(elementLocator)).perform();
	}

	public static void doubleClickOnElement(WebDriver driver) {
		Actions action = new Actions(driver);
		action.doubleClick().perform();
	}

	/**
	 * 
	 * To wait for the wrapper loader which comes under the main HTML, i.e., outside
	 * any frame
	 * 
	 * @param driver
	 * @return boolean
	 */
	public static boolean checkForWrapperLoading(WebDriver driver) {
		DOMConfigurator.configure("log4j.xml");
		boolean checkLoading = false;

		SeleniumUtil.turnOffImplicitWaits(driver);
		try {

			checkLoading = driver.findElement(By.xpath("//div[@id='wrapperLoader ']")).isDisplayed();

			if (checkLoading) {

				for (int i = 1; i <= 60; i++) {

					try {
						checkLoading = driver.findElement(By.xpath("//div[@id='wrapperLoader ']")).isDisplayed();

						if (!checkLoading) {
							log.info("WRAPPER LOADER DISAPPEARED");
							break;
						} else {
							log.info("WRAPPER LOADER APPEARING");
						}

					} catch (Exception e) {
						log.info("WRAPPER LOADER NOT APPEARING");
						break;
					}
					SeleniumUtil.pause(2000);
				}

			} else {
				log.info("WRAPPER LOADER NOT APPEARING");
			}
		} catch (Exception e) {
			log.info("NO WRAPPER LOADER");
		}

		return checkLoading;
	}

	/**
	 * To wait for the saving call loader which comes under the main HTML, i.e.,
	 * outside any frame
	 * 
	 * @param driver
	 * @return
	 */
	public static boolean checkForSavingCallLoading(WebDriver driver) {
		DOMConfigurator.configure("log4j.xml");
		boolean checkLoading = false;

		SeleniumUtil.turnOffImplicitWaits(driver);
		try {

			checkLoading = driver.findElement(SAVING_CALL_LOADER).isDisplayed();

			if (checkLoading) {

				for (int i = 1; i <= 60; i++) {

					try {
						checkLoading = driver.findElement(SAVING_CALL_LOADER).isDisplayed();

						if (!checkLoading) {
							log.info("SAVING LOADER DISAPPEARED");
							break;
						} else {
							log.info("SAVING LOADER APPEARING");
						}

					} catch (Exception e) {
						log.info("SAVING LOADER NOT APPEARING");
						break;
					}
					SeleniumUtil.pause(1000);
				}

			} else {
				log.info("SAVING LOADER NOT APPEARING");
			}
		} catch (Exception e) {
			log.info("NO SAVING LOADER");
		}
		return checkLoading;
	}

	public static void selectAllTextInAnElement(WebDriver driver, By elementLocator) {
		driver.findElement(elementLocator).sendKeys(Keys.chord(Keys.CONTROL, "a"));
	}

	public static void selectSpecificTextInAnElement(WebDriver driver, By elementLocator) {
		driver.findElement(elementLocator).sendKeys(Keys.chord(Keys.LEFT_CONTROL, Keys.LEFT_SHIFT, Keys.ARROW_LEFT));
	}

	/**
	 * 
	 * @param driver
	 * @throws Exception
	 * 
	 */
	public static void clickOnSlateRefresh(WebDriver driver) throws Exception {
		SeleniumUtil.switchToDefaultContent(driver);
		SeleniumUtil.click(driver, SLATE_REFRESH);
	}

	/**
	 * @param driver
	 * 
	 *               Method to click on Slate Preview Button
	 */
	public static void clickOnSlatePreview(WebDriver driver) {
		SeleniumUtil.switchToDefaultContent(driver);
		SeleniumUtil.waitForElementPresence(driver, SLATE_PREVIEW_UNCLICKED, WAIT_SECONDS).click();
		SeleniumUtil.pause(2000);
		SeleniumUtil.switchWindowByIndex(driver, 1);
	} 

	public static void waitForAllPageLoaders(WebDriver driver) {
		SeleniumUtil.switchToDefaultContent(driver);
		checkForPageLoading(driver);
		SeleniumUtil.waitForElementPresence(driver, BLOCKER_HIDE, 120);
		checkForWrapperLoading(driver);
		checkForSavingCallLoading(driver);
		SeleniumUtil.switchToIframe(driver, "canvas-iframe");
		SeleniumUtil.pause(2000);
	}

	public static void waitForHeaderOverlay(WebDriver driver) {
		SeleniumUtil.waitForElementPresence(driver, HEADER_OVERLAY, WAIT_SECONDS);
	}
	
	
	//Insert Image feature//
	public static boolean insertImageInElement(WebDriver driver) throws Exception {
		boolean result = false;
		
		if (SeleniumUtil.isElementPresent(driver, By.xpath("//span[text()='TABLE INFORMATION']")))
		{
			SeleniumUtil.waitForElementPresence(driver, INSERT_IMAGE_TABLE, 5);
			SeleniumUtil.click(driver, INSERT_IMAGE_TABLE);
			SeleniumUtil.click(driver, By.xpath("//div[@class='tox-collection__item-label']"));
			
		} else {
			SeleniumUtil.waitForElementPresence(driver, INSERT_IMAGE_LIST, 5);
			SeleniumUtil.click(driver, INSERT_IMAGE_LIST);
			SeleniumUtil.click(driver, By.xpath("//div[@class='tox-collection__item-label']"));
		}
		
		//ChapterOpenerSupportingMethods chapterOpenerSupportingMethodsObjects = new ChapterOpenerSupportingMethods(
			//	driver);
		

		try {
			boolean linkWindowPresence = SeleniumUtil.waitForElementPresence(driver, LINK_PRODUCT_WINDOW, 5)
					.isDisplayed();
			if (linkWindowPresence) {
				By linkProductDropDownOption = By.xpath("//ul[@id='react-autowhatever-productLink']/li[2]");
				
				SeleniumUtil.pause(6000);
				SeleniumUtil.waitForElementPresence(driver, LINK_PRODUCT_DROPDOWN, WAIT_SECONDS).click();
				SeleniumUtil.waitForElementPresence(driver, linkProductDropDownOption, WAIT_SECONDS).click();
				SeleniumUtil.waitForElementPresence(driver, LINK_PRODUCT_LINK_BUTTON, WAIT_SECONDS).click();
			}
		} catch (Exception e) {
			log.info("Link window didn't occur.");
		}

	//	result = chapterOpenerSupportingMethodsObjects.addAlfrescoContent("WorldMap.jpg", "");

		if (result)
			log.info("Successfully added image under image element");
		else
			log.info("ERROR in adding image under image element");

		return result;
	}
}