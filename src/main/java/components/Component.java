package components;

import org.openqa.selenium.WebElement;

public class Component {

	protected WebElement root;
	
	public Component(WebElement root) {
		this.root = root;
	}
	
	public WebElement getElement() {
		return root;
	}
}
