package lineair;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase {
	protected WebDriver driver;

	@BeforeMethod(alwaysRun = true)
	public void setUp() {
		// Create a new instance of the Firefox driver
		driver = new FirefoxDriver();

		// Open the website
		driver.get("http://selenium.polteq.com/testshop/index.php");
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		// Close the browser
		// driver.quit();
	}
}
