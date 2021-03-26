package project.utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.time.temporal.ChronoUnit;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.webdriver.SerenityWebdriverManager;

public class SeleniumUtils extends PageObject {
	//trinath
	public static void waitUntillGivenWindowPresent(int Time, int NoofWindows, WebDriver driver) {

		Set<String> windows = driver.getWindowHandles();
		int windowCount = windows.size();

		for (int i = 0; i < Time; i++) {

			if (windowCount > NoofWindows) {
				break;
			} else {
				try {

					defaultWait(ProjectVariables.MIN_THREAD_WAIT);
				} catch (Exception e1) {
					System.out.println("Waiting for webelement in DOM");
				}

			}
		}

	}

	public static void switchWindowUsingWindowCount(int waitTimer, int windowNum, WebDriver driver) {

		waitUntillGivenWindowPresent(waitTimer, windowNum, driver);
		ArrayList<String> windowHandles = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("WINDOWS" + windowHandles.size());
		driver.switchTo().window(windowHandles.get(windowNum - 1).toString());	
		System.out.println(driver.getCurrentUrl());
	}
		
	public static void switchWindowUsingWindowCountClose(int waitTimer, int windowNum, WebDriver driver) {

		waitUntillGivenWindowPresent(waitTimer, windowNum, driver);
		ArrayList<String> windowHandles = new ArrayList<String>(driver.getWindowHandles());
		System.out.println("WINDOWS" + windowHandles.size());
		driver.switchTo().window(windowHandles.get(windowNum - 1).toString()).close();

	}

	public static void refresBrowser(WebDriver driver) {
		driver.navigate().refresh();

	}

	public static void defaultWait(int i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public static void defaultWait(long i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
	}

	public static String getValueByName(String Name) {

		return Serenity.sessionVariableCalled(Name).toString();
	}

	public String getLastWebelemtFromList(String xpath) {

		List<WebElement> list = getDriver().findElements(By.xpath(xpath));
		int size = list.size();
		System.out.println(size);
		return list.get(size - 1).getText();
	}

	public boolean is_WebElement_Enabled(String Xpath) {
		try {
			highlightElement(Xpath);
			return $(Xpath).withTimeoutOf(ProjectVariables.SECONDS_MAX_COUNT,ChronoUnit.SECONDS).isEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	public void switchToNewWindow() {

		String baseWindowHandle = getDriver().getWindowHandle();
		Set<String> openedWindows = getDriver().getWindowHandles();
		String newWindow = null;
		if (openedWindows.size() > 1 && openedWindows.remove(baseWindowHandle)) {
			Iterator<String> openedWindowsIterator = openedWindows.iterator();
			newWindow = (String) openedWindowsIterator.next();
		}
		getDriver().switchTo().window(newWindow);
	}

	public static boolean waitUntillElementisDisplayed(String xpath, int seconds, WebDriver driver) {
		if (checkElementExsist(xpath, seconds, driver)) {
			for (int i = 0; i < seconds; i++) {
				if (driver.findElement(By.xpath(xpath)).isDisplayed())
					return true;
				else
					defaultWait(ProjectVariables.MIN_THREAD_WAIT);
			}
			return false;
		}

		else {
			return false;
		}

	}

	public static boolean waitUntillElementisNotDisplayed(String xpath, int seconds, WebDriver driver) {
		try {
			if (!checkElementNotExsist(xpath, seconds, driver)) {
				for (int i = 0; i < seconds; i++) {

					if (driver.findElement(By.xpath(xpath)).isDisplayed())
						defaultWait(ProjectVariables.MIN_THREAD_WAIT);

					else
						return true;

				}

			}
		} catch (Exception e) {
			return false;
		}

		return false;

	}

	public static boolean checkElementExsist(String xpath, int seconds, WebDriver driver) {
		boolean flag = false;
		try {

			driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
			driver.findElement(By.xpath(xpath));
			flag = true;
		} catch (NoSuchElementException e) {
			flag = false;
		} finally {
			SerenityWebdriverManager.inThisTestThread().getCurrentDriver().manage().timeouts().implicitlyWait(4000,
					TimeUnit.SECONDS);
		}
		return flag;

	}

	public static boolean checkElementNotExsist(String xpath, int seconds, WebDriver driver) {
		boolean flag = false;
		try {
			driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
			driver.findElement(By.xpath(xpath));

			flag = false;
		} catch (NoSuchElementException e) {
			flag = true;
		} finally {

			driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

		}
		return flag;

	}

	public static ExpectedCondition<Boolean> waitForDomReadyState(WebDriver driver) {

		return new ExpectedCondition<Boolean>() {

			public Boolean apply(WebDriver d) {
				JavascriptExecutor js = (JavascriptExecutor) driver;
				boolean flag = js.executeScript("return document.readyState;").equals("complete");
				System.out.println(flag);
				return (js.executeScript("return document.readyState;").equals("complete"));
			}
		};
	}

	public void highlightElement(WebElement element) {

		for (int i = 0; i < ProjectVariables.HIGHLIGHT_COUNT; i++) {
			WebDriver driver = getDriver();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].style.border='7px solid black'", element);
			js.executeScript("arguments[0].style.border=''", element);

		}

	}

	public void highlightElement(String Xpath) {

		WebElement element = getDriver().findElement(By.xpath(Xpath));
		for (int i = 0; i < ProjectVariables.HIGHLIGHT_COUNT; i++) {
			WebDriver driver = getDriver();
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("arguments[0].style.border='7px solid black'", element);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
		}

	}

	public void highlightElement_CheckBox(String Xpath) {

		WebElement element = getDriver().findElement(By.xpath(Xpath));
		for (int i = 0; i < 2; i++) {
			WebDriver driver = getDriver();
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("arguments[0].style.border='7px solid black'", element);

		}
	}

	public void highlightElement_CheckBox(WebElementFacade element) {

		for (int i = 0; i < 2; i++) {
			WebDriver driver = getDriver();
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("arguments[0].style.border='7px solid black'", element);

		}

	}

	public void Remove_highlightElement(String Xpath) {

		WebElement element = getDriver().findElement(By.xpath(Xpath));
		for (int i = 0; i < 2; i++) {
			WebDriver driver = getDriver();
			JavascriptExecutor js = (JavascriptExecutor) driver;

			js.executeScript("arguments[0].style.border=''", element);

		}

	}

	public boolean Click_given_WebElement(WebElementFacade element) {

		boolean flag = false;
		try {

			element.withTimeoutOf(ProjectVariables.MAX_TIME_OUT, ChronoUnit.SECONDS).waitUntilVisible();
			highlightElement(element);
			element.click();
			flag = true;
		} catch (Exception e) {
			System.out.println(element + "Element is not visible" + e);
			flag = false;
		}

		return flag;

	}

	public boolean Click_given_Locator(String xpath) {
		boolean flag = false;
		try {

			$(xpath).withTimeoutOf(ProjectVariables.SECONDS_MAX_COUNT, ChronoUnit.SECONDS).waitUntilVisible();
//			highlightElement(xpath);
			$(xpath).click();
			flag = true;
		} catch (Exception e) {
			System.out.println(xpath + "Element is not visible" + e);
			flag = false;
		}

		return flag;

	}

	public boolean Enter_given_Text_Element(WebElementFacade element, String text) {

		boolean flag = false;

		try {
			element.withTimeoutOf(ProjectVariables.MAX_TIME_OUT, ChronoUnit.SECONDS).waitUntilVisible();
			highlightElement(element);
			element.clear();
			element.sendKeys(text);
			flag = true;
		} catch (Exception e) {
			System.out.println(element + "Element is not visible" + e);
			flag = false;
		}

		return flag;
	}

	public boolean Enter_given_Text_Element(WebElement element, String text) {

		boolean flag = false;

		try {
			SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);
			element.isDisplayed();
			highlightElement(element);
			element.clear();
			element.sendKeys(text);
			flag = true;
		} catch (Exception e) {
			System.out.println(element + "Element is not visible" + e);
			flag = false;
		}

		return flag;
	}

	public boolean Enter_given_Text_Element(String xpath, String text) {

		boolean flag = false;
		try {
			$(xpath).withTimeoutOf(ProjectVariables.MIN_TIME_OUT, ChronoUnit.SECONDS).waitUntilVisible();
			highlightElement(xpath);
			$(xpath).clear();
			$(xpath).sendKeys(text);
			flag = true;
		} catch (Exception e) {
			System.out.println(xpath + "Element is not visible" + e);
			flag = false;
			//Assert.assertTrue(xpath + "Element is not visible", flag);
		}
		return flag;
	}

	public int get_Matching_WebElement_count(String xpath) {
		return getDriver().findElements(By.xpath(xpath)).size();
	}

	public String[] get_All_Text_from_Locator(String Xpath) {
		List<WebElement> elements = getDriver().findElements(By.xpath(Xpath));
		String[] text = new String[elements.size()];
		for (int i = 0; i < elements.size(); i++) {

			text[i] = elements.get(i).getText();
			System.out.println(text[i]);
		}
		return text;
	}

	public void select_Given_Value_From_DropDown(WebElement element, String sValue) {
		highlightElement(element);
		Select dropDown = new Select(element);
		dropDown.selectByVisibleText(sValue);
	}

	public String get_TextFrom_Locator(String Xpath) {
		highlightElement(Xpath);
		return $(Xpath).withTimeoutOf(ProjectVariables.MIN_TIME_OUT, ChronoUnit.SECONDS).waitUntilVisible().getTextValue();
	}

	public String get_StringTextFrom_Locator(String Xpath) {
		highlightElement(Xpath);
		return $(Xpath).withTimeoutOf(ProjectVariables.MIN_TIME_OUT, ChronoUnit.SECONDS).waitUntilVisible().getText();
	}
	
	public String get_TextBy_Attribute(String Xpath) {
		highlightElement(Xpath);
		return $(Xpath).withTimeoutOf(ProjectVariables.MIN_TIME_OUT, ChronoUnit.SECONDS).waitUntilVisible().getAttribute("value");
	}
	public String get_Text_From_WebElement(WebElementFacade element) {

		highlightElement(element);
		return element.withTimeoutOf(ProjectVariables.MIN_TIME_OUT, ChronoUnit.SECONDS).waitUntilVisible().getTextValue();
	}

	public String get_Text_From_WebElement(WebElement element) {

		highlightElement(element);
		return element.getText();
	}

	public boolean is_WebElement_Displayed(WebElementFacade element) {
		try {

			element.withTimeoutOf(ProjectVariables.MIN_TIME_OUT, ChronoUnit.SECONDS).isDisplayed();
			highlightElement(element);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean is_WebElement_Present(WebElementFacade element) {
		try {
			highlightElement_CheckBox(element);
			return element.withTimeoutOf(ProjectVariables.MIN_TIME_OUT, ChronoUnit.SECONDS).isPresent();
		} catch (Exception e) {
			return false;
		}

	}

	public boolean is_WebElement_Displayed(String Xpath) {
		try {
			highlightElement(Xpath);
			return $(Xpath).withTimeoutOf(ProjectVariables.MIN_TIME_OUT, ChronoUnit.SECONDS).isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean is_WebElement_Present(String Xpath) {
		try {
			highlightElement_CheckBox(Xpath);
			return $(Xpath).withTimeoutOf(ProjectVariables.MIN_TIME_OUT, ChronoUnit.SECONDS).isPresent();
		} catch (Exception e) {
			return false;
		}
	}

	public void scrool_bottom_of_page() {
		JavascriptExecutor jse = ((JavascriptExecutor) getDriver());
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");

	}

	public void SwitchToFrame(String sFrame) {
		WebElement element = getDriver().findElement(By.xpath(sFrame));
		getDriver().switchTo().frame(element);
	}

	public boolean is_WebElement_Selected(String xpath) {
		highlightElement(xpath);
		return $(xpath).withTimeoutOf(ProjectVariables.MAX_TIME_OUT, ChronoUnit.SECONDS).isSelected();

	}

	public void Doubleclick(String xpath) {
		Actions action = new Actions(getDriver());
		action.moveToElement(getDriver().findElement(By.xpath(xpath))).doubleClick().build().perform();
	}

	public void select_Given_Value_From_DropDown_Using_StringLocator(String xpath, String sValue) {
		highlightElement(xpath);

		Select dropDown = new Select($(xpath));
		dropDown.selectByVisibleText(sValue);

	}

	public String get_FirstSelected_Option_In_DropDown(String xpath) {
		highlightElement(xpath);
		Select dropDown = new Select($(xpath));
		return dropDown.getFirstSelectedOption().getText();

	}

	public void moveToElement(String xpath) {
		System.out.println("Testing");
		WebElement element = getDriver().findElement(By.xpath(xpath));
		Actions builder = new Actions(getDriver());
		builder.moveToElement(element).build().perform();
		// builder.click().build().perform();
		System.out.println("Moved");
	}

	public String Get_Value_By_given_attribute(String attribute, String Xpath) {
		try {
			String value = $(Xpath).withTimeoutOf(ProjectVariables.MIN_TIME_OUT, ChronoUnit.SECONDS)
					.getAttribute(attribute);

			return value;
		} catch (Exception e) {
			return null;
		}
	}

	public int get_size_of_the_given_Locator(String Xpath) {
		int size = getDriver().findElements(By.xpath(Xpath)).size();
		return size;

	}

	public static void scrollingToGivenElement(WebDriver driver, String string) {

		WebElement element = driver.findElement(By.xpath(string));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public void Clear_given_Text_Element(String xpath) {
		// highlightElement(xpath);
		$(xpath).withTimeoutOf(ProjectVariables.MIN_TIME_OUT, ChronoUnit.SECONDS).waitUntilVisible().clear();

	}
	public void waitUntil_Loading_Dailog_Disappears(WebElementFacade element) {

		do {
			SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
		} while (element.isVisible());
		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

	}
	
	
}
