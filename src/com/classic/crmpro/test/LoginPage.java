package com.classic.crmpro.test;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class LoginPage {
	//E2E:3 + 1 => 3
	//Sanity:1 + 2 => 3
	//Regression:2 + 3 => 5
	//Global variable
	public WebDriver driver;
	//Parameters
	@Parameters({"URL","WebDriver"})
	//This be executed before all tests
	
	
	@BeforeMethod(groups= {"E2E","Sanity","Regression"})
	public void setUp(String URL,String WebDriver) {
		System.setProperty("webdriver.gecko.driver", WebDriver);
		driver = new FirefoxDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get(URL);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	//This be executed after all tests
	@AfterMethod(groups= {"E2E","Sanity","Regression"})
	public void doAfterTests() {
		driver.quit();
	}
	//Testing if url is correct with priority 1(To be tested first)
	@Test(priority = 1 , groups= {"E2E"})
	public void urlTest() {
		String expectedResult = "https://classic.crmpro.com/index.html";
		String actualResult = driver.getCurrentUrl();
		Assert.assertEquals(actualResult, expectedResult, "Failed because link is not as expected!");
	}
	//Testing if page title is correct with priority 2
	@Test(priority = 2,groups= {"E2E"})
	public void titleTest() {
		String expectedResult = "CRMPRO - CRM software for customer relationship management, sales, and support.";
		String actualResult = driver.getTitle();
		Assert.assertEquals(actualResult, expectedResult, "Failed because title is not as expected!");
	}
	//Testing if logo is displayed with priority 3
	@Test(priority = 3,groups= {"Regression"})
	public void logoTest() {
		WebElement logo = driver.findElement(By.xpath("//*[@src='https://classic.crmpro.com/img/logo.png']"));
		boolean actualResult = logo.isDisplayed();
		Assert.assertTrue(actualResult,"Failed because logo is not displayed!");
	}
	//Testing login button	
	@Test(priority = 4,groups= {"E2E","Sanity","Regression"},dataProvider="loginData")
	public void homePageLoginButtonTest(String username,String password) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("preloader")));
		WebElement usernameTextField =  driver.findElement(By.xpath("//input[@placeholder='Username']"));
		WebElement passwordTextField = driver.findElement(By.xpath("//input[@placeholder='Password']"));
		WebElement loginButton = driver.findElement(By.xpath("//input[@value='Login']"));
		usernameTextField.sendKeys(username);
		passwordTextField.sendKeys(password);
		loginButton.click();
		SoftAssert soft = new SoftAssert();
		WebElement mainFrame = driver.findElement(By.name("mainpanel"));
		boolean mainFrameIsDisplayed = mainFrame.isDisplayed();
		soft.assertTrue(mainFrameIsDisplayed,"Login unsuccessful!");
		soft.assertAll();
	}
	//DataProvider
	@DataProvider
	public Object[][] loginData() {
		Object[][] myDataTable = new Object[4][2];
		myDataTable[0][0] = "rahaouitesting";
		myDataTable[0][1] = "Bmn123456";
		myDataTable[1][0] = "Incorrectuser";
		myDataTable[1][1] = "Bmn123456";
		myDataTable[2][0] = "rahaouitesting";
		myDataTable[2][1] = "incorrect@pass";
		myDataTable[3][0] = "incorrectuser";
		myDataTable[3][1] = "incorrect@pass";
		return myDataTable;
	}
}
