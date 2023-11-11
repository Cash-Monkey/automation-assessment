package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPopup extends Page {
	private By rootBy = By.cssSelector("#watnotif-wrapper > *[data-role='notification']");
	
	/**
	 * This pop-up appears when adding to the cart from a SearchPage; expires after a short delay
	 * @throws NoSuchElementException when the pop-up was never opened or already closed
	 */
	public CartPopup(WebDriver driver) throws NoSuchElementException {
		super(driver);
		
		Wait<WebDriver> wait = new WebDriverWait(driver, 3);
		ExpectedCondition<WebElement> visibleCondition = ExpectedConditions.visibilityOf(driver.findElement(rootBy));
		wait.until(visibleCondition);
	}
	
	/**
	 * Closes this pop-up window, by waiting if necessary
	 */
	public void closePopup() {		
		try {
			// the easy way
			WebElement closeButton = driver.findElement(new ByChained(rootBy, By.xpath("//button[contains(@class, 'close')]")));
			closeButton.click();

			Wait<WebDriver> wait = new WebDriverWait(driver, 1);
			ExpectedCondition<Boolean> closeCondition = ExpectedConditions.invisibilityOf(driver.findElement(rootBy));
			wait.until(closeCondition);
		} catch (NoSuchElementException e) {
			// already closed
		} catch (StaleElementReferenceException e) {
			// the hard way
			closePopup();
		}
	}

}
