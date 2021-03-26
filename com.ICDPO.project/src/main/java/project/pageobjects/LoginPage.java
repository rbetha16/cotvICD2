package project.pageobjects;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.FindBy;

import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.core.pages.PageObject;

import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.util.EnvironmentVariables;
import project.utilities.GenericUtils;

import project.utilities.ProjectVariables;

import project.utilities.SeleniumUtils;
import net.thucydides.core.util.EnvironmentVariables;
import project.pageobjects.CommonPage;

public class LoginPage extends PageObject {

	//trinath..
	
	CommonPage oIUHomePage;

	GenericUtils oGenericUtils;

	@FindBy(xpath = "//label[text()='User Name :']/parent::td/following-sibling::td//input")
	public WebElementFacade UserName_Txt;

	@FindBy(xpath = "//label[text()='Password :']/parent::td/following-sibling::td//input")
	public WebElementFacade Password_Txt;

	@FindBy(xpath = "//button[text()='Sign In']")
	public WebElementFacade SignIn_Btn;

	@FindBy(xpath = "//div[@id='centerDeckPanel']//span[text()='Home']")
	public WebElementFacade JBPM_HomePage;
	
	private EnvironmentVariables environmentVariables;

	public boolean IU_SignIn(String sURL, String sUSER_NAME, String sPASSWORD) throws Exception {


		getDriver().close();
		getDriver().close();
		

		String APP_URL = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("APPURL");
           
        ProjectVariables.DB_CONNECTION_URL = EnvironmentSpecificConfiguration.from(environmentVariables).getProperty("DB_CONNECTION_URL");
           
        getDriver().navigate().to(APP_URL);
	
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		getDriver().manage().window().maximize();

		SeleniumUtils oSeleniumUtils = this.switchToPage(SeleniumUtils.class);

		// Enter Username
		oSeleniumUtils.highlightElement(UserName_Txt);
		UserName_Txt.withTimeoutOf(ProjectVariables.MIN_TIME_OUT, TimeUnit.SECONDS).waitUntilVisible().clear();

		UserName_Txt.waitUntilVisible().sendKeys(sUSER_NAME);

		// Enter Password
		oSeleniumUtils.highlightElement(Password_Txt);

		Password_Txt.waitUntilVisible().clear();

		Password_Txt.waitUntilVisible().sendKeys(GenericUtils.decode(sPASSWORD));

		oIUHomePage.GetVersionInfo();

		oSeleniumUtils.Click_given_WebElement(SignIn_Btn);
		
		oIUHomePage.waitUntil_Loading_Dailog_Disappears(oIUHomePage.Loading);

		return true;

	}

}
