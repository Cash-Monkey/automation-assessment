package components;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class Product extends Component {

	private String name;
	
	private By nameBy = By.cssSelector("*[data-testid=itemDescription]");
	private By addBy = By.name("addToCartButton");
	private By typeBy = By.cssSelector("select[data-testid='itemAddCartGrouping'");
	
	/**
	 * A single result cell on the SearchPage
	 */
	public Product(WebElement root) {
		super(root);
	}

	/**
	 * When you don't care what is selected, just that it is not 'Type'
	 */
	public String selectRandomType() {
		Select select = new Select(root.findElement(typeBy));
		
		// exclude option 1 (index = 0) because it is "Type"
		int index = ThreadLocalRandom.current().nextInt(1, select.getOptions().size() - 1);
		select.selectByIndex(index);
		
		return select.getOptions().get(index).getText();
	}
	
	public void addToCart() throws IllegalStateException {
		List<WebElement> button = root.findElements(addBy);
		
		if(button.isEmpty()) {
			throw new IllegalStateException(getName() + " is out of stock. Failed to add to cart");
		} else {
			button.get(0).click();
		}
	}
	
	/**
	 * Does this product have a drop-down above the 'Add to Cart' button?
	 * @return true if it does
	 */
	public boolean hasType() {
		return root.findElements(typeBy).size() > 0;
	}
	
	public String getName() {
		if(name == null) {
			name = root.findElement(nameBy).getText();
		}
		return name;
	}
	
	public String toString() {
		return getName();
	}
}
