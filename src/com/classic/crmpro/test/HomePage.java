package com.classic.crmpro.test;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class HomePage {
	//Global variable
	public WebDriver driver;
	//This be executed before all tests
	@BeforeMethod
	public void setUpAndLogin() {
		System.setProperty("webdriver.gecko.driver", "D:\\Java\\Eclipse\\WorkSpace\\ClassicCRMTesting\\src\\WebDriver\\geckodriver.exe");
		driver = new FirefoxDriver();
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.get("https://classic.crmpro.com/");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("preloader")));
		WebElement usernameTextField =  driver.findElement(By.xpath("//input[@placeholder='Username']"));
		WebElement passwordTextField = driver.findElement(By.xpath("//input[@placeholder='Password']"));
		WebElement loginButton = driver.findElement(By.xpath("//input[@value='Login']"));
		usernameTextField.sendKeys("rahaouitesting");
		passwordTextField.sendKeys("Bmn123456");
		loginButton.click();
	}
	//This be executed after all tests
	@AfterMethod
	public void doAfterTests() {
		driver.quit();
	}
	@Test(priority = 5)
	public void contactTest() {
		driver.switchTo().frame("mainpanel");
		WebElement contact = driver.findElement(By.xpath("//a[@title='Contacts']"));
		contact.click();
		WebElement statusLabel = driver.findElement(By.xpath("//td[contains(text(),'Status')]"));
		boolean statusLabelisDisplayed = statusLabel.isDisplayed();
		Assert.assertTrue(statusLabelisDisplayed,"Status label is not displayed, so contact test failed!");
	}
	@Test(priority = 6)
	public void dealsTest() {
		driver.switchTo().frame("mainpanel");
		WebElement contact = driver.findElement(By.xpath("//a[@title='Contacts']"));
		contact.click();
		WebElement statusLabel = driver.findElement(By.xpath("//a[@title='Deals']"));
		boolean statusLabelisDisplayed = statusLabel.isDisplayed();
		Assert.assertTrue(statusLabelisDisplayed,"keyword label is not displayed, so deals test failed!");
	}
	@Test(priority = 7)
	public void tasksTest() {
		driver.switchTo().frame("mainpanel");
		WebElement contact = driver.findElement(By.xpath("//a[@title='Tasks']"));
		contact.click();
		WebElement statusLabel = driver.findElement(By.xpath("//td[contains(text(),'Keyword')]"));
		boolean statusLabelisDisplayed = statusLabel.isDisplayed();
		Assert.assertTrue(statusLabelisDisplayed,"keyword label is not displayed, so tasks test failed!");
	}
}
