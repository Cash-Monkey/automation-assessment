package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends HeaderFooterPage {

	/**
	 * Unused page for getting more information about a product
	 */
	public ProductPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * add this item into the cart
	 */
	public void addToCart() {
		driver.findElement(By.id("buyButton")).click();
	}

}
