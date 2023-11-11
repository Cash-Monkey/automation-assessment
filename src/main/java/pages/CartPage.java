package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends HeaderFooterPage {
	
	// pop-up modal for empty cart
	private By confirmEmptyBy = By.xpath("//*[@class='ReactModalPortal']//footer/button[1]");
	private By cancelEmptyBy = 	By.xpath("//*[@class='ReactModalPortal']//footer/button[2]");
	
	public CartPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * If there are items in the cart, this clicks the button above the itemized list
	 * @throws IllegalStateException if the cart is empty, this button is not displayed
	 */
	public void emptyCart() throws IllegalStateException {
		WebElement button = driver.findElement(By.cssSelector("button.emptyCartButton"));
		
		if(button.isDisplayed()) {
			button.click();
		} else {
			if(isEmpty()) {
				throw new IllegalStateException("Empty Cart button is not displayed when the cart is empty");
			}		
		}
	}
	
	/**
	 * Accepts the modal to confirm the empty cart decision
	 */
	public void confirmEmptyCart() {
		driver.findElement(confirmEmptyBy).click();
		
		Wait<WebDriver> wait = new WebDriverWait(driver, 3);
		ExpectedCondition<Boolean> emptyCondition = ExpectedConditions.textToBe(getCartCountBy(), "0");
		wait.until(emptyCondition);
	}
	
	/**
	 * Rejects the decision to empty the cart when the modal asks to confirm
	 */
	public void cancelEmptyCart() {
		driver.findElement(cancelEmptyBy).click();
		
		Wait<WebDriver> wait = new WebDriverWait(driver, 3);
		ExpectedCondition<Boolean> emptyCondition = ExpectedConditions.textToBe(getCartCountBy(), "0");
		wait.until(emptyCondition);
	}
	
	public boolean isEmpty() {
		return driver.findElement(By.className("cartEmpty")).isDisplayed();
	}
}
