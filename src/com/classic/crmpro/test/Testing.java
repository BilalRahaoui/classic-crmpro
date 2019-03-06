package com.classic.crmpro.test;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.BeforeMethod;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class Testing {
	//Global variable
	public WebDriver driver;
	//This be executed before all tests
	@BeforeMethod
	public void setUp() {
		System.setProperty("webdriver.gecko.driver", "D:\\Java\\Eclipse\\WorkSpace\\ClassicCRMTesting\\src\\WebDriver\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get("https://classic.crmpro.com/");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	//This be executed after all tests
	@AfterMethod
	public void doAfterTests() {
		driver.quit();
	}
	//Testing if url is correct with priority 1(To be tested first)
	@Test(priority = 1)
	public void urlTest() {
		String expectedResult = "https://classic.crmpro.com/index.html";
		String actualResult = driver.getCurrentUrl();
		Assert.assertEquals(actualResult, expectedResult, "Failed because link is not as expected!");
	}
	//Testing if page title is correct with priority 2
	@Test(priority = 2)
	public void titleTest() {
		String expectedResult = "CRMPRO - CRM software for customer relationship management, sales, and support.";
		String actualResult = driver.getTitle();
		Assert.assertEquals(actualResult, expectedResult, "Failed because title is not as expected!");
	}
	//Testing if logo is displayed with priority 3
	@Test(priority = 3)
	public void logoTest() {
		WebElement logo = driver.findElement(By.xpath("//*[@src='https://classic.crmpro.com/img/logo.png']"));
		boolean actualResult = logo.isDisplayed();
		Assert.assertTrue(actualResult,"Failed because logo is not displayed!");
	}
	//Testing login button	
	@Test(priority = 4)
	public void homePageLoginButtonTest() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("preloader")));
		WebElement usernameTextField =  driver.findElement(By.xpath("//input[@placeholder='Username']"));
		WebElement passwordTextField = driver.findElement(By.xpath("//input[@placeholder='Password']"));
		WebElement loginButton = driver.findElement(By.xpath("//input[@value='Login']"));
		usernameTextField.sendKeys("rahaouitesting");
		passwordTextField.sendKeys("Bmn123456");
		loginButton.click();
		SoftAssert soft = new SoftAssert();
		WebElement mainFrame = driver.findElement(By.name("mainpanel"));
		boolean mainFrameIsDisplayed = mainFrame.isDisplayed();
		soft.assertTrue(mainFrameIsDisplayed,"Login unsuccessful!");
		soft.assertAll();
	}
}
