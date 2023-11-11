package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public abstract class HeaderFooterPage extends Page {

	private By cartCountBy = By.id("cartItemCountSpan");
	
	/**
	 * Base class for all pages that have a header and footer (most of them)
	 */
	public HeaderFooterPage(WebDriver driver) {
		super(driver);
	}
	
	/**
	 * Use the search bar at the top of the page
	 * @param query the String term(s) to search for
	 * @return a page with the table of search results
	 */
 	public SearchPage search(String query) {
		WebElement search = driver.findElement(By.id("searchval"));
		
		// I don't know why there are 2 search bars
		// I would ask about this, but I can't
		// technically not valid HTML
		if(!search.isDisplayed()) {
			search = driver.findElement(By.xpath("(//input[@id='searchval'])[2]"));
		}
		
		search.sendKeys(query);
		search.submit();
		
		return new SearchPage(driver);
	}
	
 	/**
 	 * Go to the page where the cart's contents are displayed 
 	 * @return the page with all the items in the cart
 	 */
	public CartPage openCart() {
		WebElement cartButton = driver.findElement(By.cssSelector("*[data-testid='cart-button']"));
		cartButton.click();		
		return new CartPage(driver);
	}
	
	/**
	 * @return the number of items displayed next to the cart
	 */
	public int getCartCount() {
		WebElement cartCountSpan = driver.findElement(cartCountBy);
		return Integer.parseInt(cartCountSpan.getText());
	}
	
	protected By getCartCountBy() {
		return cartCountBy;
	}
}
