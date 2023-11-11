package assessment;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import components.Product;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.CartPage;
import pages.CartPopup;
import pages.HomePage;
import pages.SearchPage;

public class CartTest extends assessment.Test {

	private static final String QUERY = "stainless work table";

	private ChromeDriver driver;
	private HomePage home;

	@BeforeClass
	public void configureDriver() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeMethod
	public void loadPage(Method method) {
		driver = new ChromeDriver();
		driver.get(HomePage.URL);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		home = new HomePage(driver);

		LOG.info("Starting test: " + method.getName());
	}

	@AfterMethod
	public void closeDriver() {
		driver.close();
	}

	@Test(description = "Check all items contain matching word")
	public void checkAllNames() {
		// establish search parameters
		String matchWord = "table";
		Pattern tablePattern = Pattern.compile(matchWord, Pattern.CASE_INSENSITIVE);

		// navigate through every result page
		SearchPage search = home.search(QUERY);
		for (int i = 1; i <= search.getLastPageNumber(); i++) {
			search = search.setPage(i);
			List<Product> items = search.getProducts();
			LOG.info(String.format("searching through %d products on page %d", items.size(), i));

			// check each product
			for (Product item : items) {
				Matcher tableMatcher = tablePattern.matcher(item.getName());
				Assert.assertTrue(tableMatcher.find(), item.getName()
						+ " does not contain the String: '" + matchWord + "' (case insensitive)\n");
				LOG.trace(item.getName() + " has valid name");
			}
		}

	}

	@Test(description = "Add/Remove items from cart")
	public void cartAddRemove() {
		SearchPage search = home.search(QUERY);
		search = search.setPage(search.getLastPageNumber());

		// try to add all items on the last page into the cart
		List<Product> items = search.getProducts();
		int cartSize = items.size();
		LOG.info("Attempting to add " + cartSize + " items to cart");

		for (int i = 0; i < items.size(); i++) {
			Product item = items.get(i);
			
			if(item.hasType()) {
				String selection = item.selectRandomType();
				LOG.debug("selected type: " + selection + " for product: " + item);
			}
			
			try {
				CartPopup popup = search.addToCart(i);
				popup.closePopup();
				LOG.trace("Added " + item + " to cart");
			} catch (IllegalStateException e) {
				// out of stock
				cartSize--;
				LOG.warn("skipped item :: " + e.getMessage());
			} 
		}

		LOG.info("Actually added " + cartSize + " items to cart");
		validateEmptyCart(search.openCart());
	}

	@Test(description = "Add/Remove the last item from the cart")
	public void cartAddRemoveOne() {
		// get the very last item
		SearchPage search = home.search(QUERY);
		search = search.setPage(search.getLastPageNumber());

		// Add item to cart
		int target = search.getProducts().size() - 1;
		CartPopup popup = search.addToCart(target);
		popup.closePopup();
		LOG.info("Added " + search.getProducts().get(target).getName() + " to cart");
		
		validateEmptyCart(search.openCart());
	}
	
	private void validateEmptyCart(CartPage cart) {
		cart.emptyCart();
		cart.confirmEmptyCart();
		Assert.assertEquals(cart.getCartCount(), 0, "Cart counter not set to 0");
		Assert.assertTrue(cart.isEmpty(), "Empty message not displayed");
	}
}
