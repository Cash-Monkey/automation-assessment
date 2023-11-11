package pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import components.Product;

public class SearchPage extends HeaderFooterPage {

	private List<Product> products;
	private int currentPage;
	private int lastPage;

	public SearchPage(WebDriver driver) {
		super(driver);
		setCurrentPage(1);
		setLastPage(countPagination());
		setProducts();
	}

	public SearchPage(WebDriver driver, SearchPage previous) {
		super(driver);
		setCurrentPage(previous.getPageNumber());
		setLastPage(previous.getLastPageNumber());
	}

	/**
	 * get more information about an individual item
	 * 
	 * @return a new page with all of the details about a product
	 */
	public ProductPage viewProduct(int index) {
		getProducts().get(index).getElement().click();
		return new ProductPage(driver);
	}

	/**
	 * Add a product into the cart
	 * 
	 * @return the popup message if one exists
	 */
	public CartPopup addToCart(int index) throws IllegalStateException {
		getProducts().get(index).addToCart();
		return new CartPopup(driver);
	}

	/**
	 * Filter results by page
	 * 
	 * @param targetPage the number displayed below all the results
	 * @return A copy of the current object with references update
	 * @throws IndexOutOfBoundsException when the targetPage does not exist
	 */
	public SearchPage setPage(int targetPage) throws IndexOutOfBoundsException {
		// nothing to do cases
		if (targetPage <= 0 || targetPage > lastPage) {
			String msg = String.format("Page range for this search was 1-%d, but %d was requested", lastPage,
					targetPage);
			throw new IndexOutOfBoundsException(msg);
		} else if (targetPage == currentPage || lastPage == 1) {
			// no work to do; break
			return this;
		}

		return goToPage(targetPage);
	}

	SearchPage goToPage(int target) {
		List<WebElement> pages = getPageSelectors();
		boolean onFirstPage = (currentPage == 1);

		if (lastPage < 8) {
			int index = onFirstPage ? target - 1 : target;
			pages.get(index).click();
		} else if (target == 1) {
			pages.get(target).click();
		} else if (target == lastPage) {
			pages.get(pages.size() - 2).click();
		} else {
			// 2 clicks to reach targets when the list is long
			if (target > 5) {
				int index = target - 1;
				pages.get(lastPage - 2).click();

				pages = getPageSelectors();
				pages.get(index).click();
			} else {
				int index = onFirstPage ? 0 : 1;
				pages.get(index).click();

				pages = getPageSelectors();
				pages.get(target).click();
			}
		}

		return new SearchPage(driver, this);
	}

	public List<Product> getProducts() {
		if (products == null) {
			setProducts();
		}

		return products;
	}

	private void setProducts() {
		List<Product> ret = new ArrayList<>();
		List<WebElement> items = driver.findElements(By.id("ProductBoxContainer"));
		for (WebElement product : items) {
			ret.add(new Product(product));
		}
		this.products = ret;
	}

	public List<WebElement> getPageSelectors() {
		List<WebElement> pagination = driver.findElements(By.id("paging"));
		if (pagination.isEmpty()) {
			return new ArrayList<>();
		} else {
			return pagination.get(0).findElements(By.cssSelector("li"));
		}
	}

	// When a search has more than 60 results, the results are paginated
	// capped at a maximum of 9 pages (for some reason)
	private int countPagination() {
		List<WebElement> pages = getPageSelectors();
		if (pages.isEmpty()) {
			// element not found, results don't need multiple pages
			return 1;
		} else {
			WebElement lastPaginationOption = pages.get(pages.size() - 2);
			return Integer.parseInt(lastPaginationOption.getText());
		}
	}

	public Integer getPageNumber() {
		return Integer.valueOf(currentPage);
	}

	protected void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getLastPageNumber() {
		return Integer.valueOf(lastPage);
	}

	protected void setLastPage(int maxPage) {
		this.lastPage = maxPage;
	}
}
