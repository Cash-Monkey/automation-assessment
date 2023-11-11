package pages;

import org.openqa.selenium.WebDriver;

public abstract class Page {

	protected WebDriver driver;
	
	/**
	 * Super class for all pages
	 * @param driver abstract the driver
	 */
	public Page(WebDriver driver) {
		this.driver = driver;
	}
	
	public void close() {
		driver.close();
	}
	
	public WebDriver getDriver() {
		return driver;
	}
	
}
