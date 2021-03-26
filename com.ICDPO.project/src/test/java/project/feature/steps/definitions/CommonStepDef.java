package project.feature.steps.definitions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import project.utilities.ExcelUtils;
import freemarker.template.utility.StringUtil;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.By;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import project.pageobjects.AdminPage;
import project.pageobjects.AdminScrubPage;
import project.pageobjects.CommonPage;
import project.pageobjects.InstanceCreation;
import project.pageobjects.LoginPage;
import project.utilities.DBQueries;
import project.utilities.DBUtils;
import project.utilities.GenericUtils;
import project.utilities.ProjectVariables;
import project.utilities.SeleniumUtils;

public class CommonStepDef extends ScenarioSteps {

	private static final long serialVersionUID = 1L;

	LoginPage ologinPage;
	CommonPage oHomePage;
	SeleniumUtils oSeleniumUtils;
	GenericUtils oGenericUtils;
	MyTasksStepdef oMyTasksStepdef;
	InstanceCreation oInstanceCreation;
	AdminPage oAdminPage;
	CommonPage oCommonPage;
	String sValueList;
	AdminScrubPage oAdminScrubPage;

	@Step
	public void userLogsInToInterpretiveUpdateApplication(String sEnvironmentType, String sUserID) throws Exception {

		switch (sEnvironmentType) {

		case "QA":

			ologinPage.IU_SignIn(ProjectVariables.sQA_URL, sUserID, ProjectVariables.PASSWORD);

			verify("IU " + Serenity.sessionVariableCalled("IUVersion").toString(), true);

			break;

		case "Production":

			Serenity.setSessionVariable("Production").to(sEnvironmentType);

			ologinPage.IU_SignIn(ProjectVariables.sPRODUCTION_URL, ProjectVariables.PROD_USER_NAME,
					ProjectVariables.PROD_PASSWORD);

			break;

		case "Dev":

			ologinPage.IU_SignIn(ProjectVariables.sDev_URL, ProjectVariables.USER_NAME, ProjectVariables.PASSWORD);

			break;

		default:
			Assert.assertTrue(
					"Environment Type is not matched for function user_Logs_In_To_Interpretive_Update_Application",
					false);

		}

	}

	@Step
	public void userNavigateToIUSmokeInstancesScreen(String sInstanceType) throws InterruptedException {

		int i = getDriver().findElements(By.xpath(oHomePage.NavigationComponentAdminScrub)).size();

		switch (sInstanceType) {

		case "Admin":

			if (i > 0) {

				oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.NavigationComponentAdminScrub);

			} else if (!oHomePage.AdminInterpretiveUpdateInstanceLink.isVisible()) {

				oGenericUtils.gfn_Click_On_Object("span", "Administration");

				oGenericUtils.gfn_Click_On_Object("a", "Interpretive Update Instances");

				oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			}

			oSeleniumUtils.is_WebElement_Displayed(oHomePage.IUInstanceGrid);

			/*
			 * verify("Add button is displayed",
			 * oGenericUtils.gfn_Verify_Object_Exist("div", "Add"));
			 * 
			 * verify("Add button is clicked",
			 * oGenericUtils.gfn_Click_On_Object("div", "Add"));
			 * 
			 * SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			 * 
			 * oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.
			 * New_UpdateInstance_Screen);
			 * 
			 * verify("Cancel button is Displayed",
			 * oGenericUtils.gfn_Verify_Object_Exist("div", "Cancel"));
			 * 
			 * verify("Cancel button is clicked",
			 * oGenericUtils.gfn_Click_On_Object("div", "Cancel"));
			 * 
			 * verify(
			 * "Are you sure you want to cancel the current ADD process? message popup"
			 * , oGenericUtils.gfn_Verify_Object_Exist("div",
			 * "Are you sure you want to cancel the current ADD process?"));
			 * 
			 * verify("Yes button is clicked",
			 * oGenericUtils.gfn_Click_On_Object("div", "Yes"));
			 * 
			 * SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			 * 
			 * verify("Interpretive Update Instances link is clicked",
			 * oGenericUtils.gfn_Click_On_Object("a",
			 * "Interpretive Update Instances"));
			 * 
			 * SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			 */
			break;

		case "My Tasks":

			oGenericUtils.gfn_Click_On_Object("span", "My Tasks");

			oGenericUtils.gfn_Click_On_Object("a", "Interpretive Rule Update");

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oSeleniumUtils.is_WebElement_Displayed(oHomePage.MyTasksIUInstanceGrid);

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TasksTabs, "sValue", "Group Tasks"));

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			oSeleniumUtils.is_WebElement_Displayed(oHomePage.GroupTasksGrid);

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TasksTabs, "sValue", "Individual Tasks"));

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			oSeleniumUtils.is_WebElement_Displayed(oHomePage.MyTasksIUInstanceGrid);

			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			break;

		case "ProductionTestMyTasks":

			oGenericUtils.gfn_Click_On_Object("span", "My Tasks");

			oGenericUtils.gfn_Click_On_Object("a", "Interpretive Rule Update");

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oSeleniumUtils.is_WebElement_Displayed(oHomePage.MyTasksIUInstanceGrid);

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TasksTabs, "sValue", "Group Tasks"));

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			oSeleniumUtils.is_WebElement_Displayed(oHomePage.GroupTasksGrid);

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TasksTabs, "sValue", "Individual Tasks"));

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			oSeleniumUtils.is_WebElement_Displayed(oHomePage.MyTasksIUInstanceGrid);

			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			break;

		default:
			Assert.assertTrue("Instance Type is not matched for function user_Navigate_To_IU_Smoke_Instances_Screen",
					false);
		}

	}

	@Step
	public void userNavigateToIUInstancesScreen(String sInstanceType) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		boolean blnFlg = false;

		int k = 0;

		switch (sInstanceType) {

		case "MyTasks":

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag, "sValue", "Group Tasks"));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Click_On_Object("span", "My Tasks");

			oGenericUtils.gfn_Click_On_Object("a", "Interpretive Rule Update");

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			break;

		case "Admin":

			oGenericUtils.gfn_Click_On_Object("span", "Administration");

			oGenericUtils.gfn_Click_On_Object("a", "Interpretive Update Instances");

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			break;

		case "GroupTasks":

			oHomePage.closeAllTabs();

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag, "sValue", "Group Tasks"));

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oGenericUtils.gfn_Click_On_Object("span", "My Tasks");

			oGenericUtils.gfn_Click_On_Object("a", "Interpretive Rule Update");

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.GroupTaskTab);

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			break;

		case "Admin PO Scrub":

			oSeleniumUtils.SwitchToFrame(oHomePage.frame);

			oGenericUtils.gfn_Verify_Object_Exist("a", "Process Candidates");

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.AllRulesRadioBtn);

			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			oGenericUtils.gfn_Verify_String_Object_Exist(oHomePage.SelectAllCheckBoxAdminMDScrub);

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.SelectAllCheckBoxAdminMDScrub);

			oGenericUtils.gfn_Click_On_Object("a", "Process Candidates");

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oHomePage.closeAllTabs();

			break;

		case "Admin PO Scrub Release":

			oSeleniumUtils.SwitchToFrame(oHomePage.frame);

			oGenericUtils.gfn_Click_On_Object("a", "Release");

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			getDriver().switchTo().defaultContent();

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			oHomePage.closeAllTabs();

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			break;

		case "View All Tasks":

			blnFlg = oGenericUtils.gfn_Click_String_object_Xpath("//button[contains(text(),'Actions')]");

			verify("Actions menu is clicked in Adminstration", blnFlg);

			blnFlg = oGenericUtils.gfn_Click_String_object_Xpath("//button[contains(text(),'View All Tasks')]");

			verify(sInstanceType + "is clicked under actions in Adminstration", blnFlg);

			oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			break;

		case "Same/Sim Mapping":

			blnFlg = oGenericUtils.gfn_Click_String_object_Xpath("//button[contains(text(),'Actions')]");

			verify("Actions menu is clicked in Adminstration", blnFlg);

			blnFlg = oGenericUtils.gfn_Click_String_object_Xpath("//button[contains(text(),'" + sInstanceType + "')]");

			verify(sInstanceType + "is clicked under actions in Adminstration", blnFlg);

			break;

		case "Rule Review":

			blnFlg = oGenericUtils.gfn_Click_String_object_Xpath("//button[contains(text(),'Actions')]");

			verify("Actions menu is clicked in Adminstration", blnFlg);

			blnFlg = oGenericUtils.gfn_Click_String_object_Xpath("//button[contains(text(),'Rule Review')]");

			verify(sInstanceType + "is clicked under actions in Adminstration", blnFlg);

			oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			blnFlg = oGenericUtils.gfn_Verify_Object_Exist("button", "Go");

			verify("Rule Review is displayed in Admin Page", blnFlg);

			break;

		case "Update Instance Assignments":

			blnFlg = oGenericUtils.gfn_Click_String_object_Xpath("//button[contains(text(),'Actions')]");

			verify("Actions menu is clicked in Adminstration", blnFlg);

			blnFlg = oGenericUtils
					.gfn_Click_String_object_Xpath("//button[contains(text(),'Update Instance Assignments')]");

			verify(sInstanceType + "is clicked under actions in Adminstration", blnFlg);

			oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			break;

		case "Assignment Configuration":

			// SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			// oHomePage.closeAllTabs();

			oGenericUtils.gfn_Click_On_Object("span", "Administration");

			oGenericUtils.gfn_Click_On_Object("a", "Assignment Configuration");

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			boolean bln = oSeleniumUtils.is_WebElement_Displayed("//div[text()='Review Tasks']");

			if (bln == true) {

				oGenericUtils.gfn_Click_On_Object("div", "OK");
			}

			/*
			 * do { SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT); }
			 * while (bln==false);
			 */

			break;

		case "Admin PO Scrub Candidates":

			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			oSeleniumUtils.SwitchToFrame(oHomePage.frame);

			oGenericUtils.gfn_Verify_Object_Exist("a", "Process Candidates");

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.AllRulesRadioBtn);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oGenericUtils.gfn_Verify_String_Object_Exist(oHomePage.AdminMDMidRule);

			oGenericUtils.gfn_Verify_String_Object_Exist(oHomePage.AdminMDRuleVersion);

			ArrayList<String> sMidRuleList = new ArrayList<String>();

			List<WebElement> iMidRuleCount = getDriver().findElements(By.xpath(oHomePage.AdminMDMidRule));

			for (int i = 0; i < iMidRuleCount.size(); i++) {

				sMidRuleList.add(iMidRuleCount.get(i).getText());
			}

			ArrayList<String> sRuleVersionList = new ArrayList<String>();

			List<WebElement> iRuleVersionCount = getDriver().findElements(By.xpath(oHomePage.AdminMDRuleVersion));

			for (int i = 0; i < iRuleVersionCount.size(); i++) {

				sRuleVersionList.add(iRuleVersionCount.get(i).getText());

			}

			for (int j = 0; j < sMidRuleList.size(); j++) {

				String sMidRule = sMidRuleList.get(j);

				String sVersion = sRuleVersionList.get(j);

				String sXpath = oHomePage.GetDynamicXPathForAdminMDRuleVersion(sMidRule, sVersion);

				oGenericUtils.gfn_Verify_String_Object_Exist(sXpath);
			}

			oHomePage.closeAllTabs();

			break;

		case "ICD-Interpretive Rule Update":

			SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

			blnFlg = oGenericUtils.gfn_Click_On_Object("span", "Group Tasks");

			SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

			verify("Group Tasks is Clicked under Navigations in JBPM Page", blnFlg);

			blnFlg = oGenericUtils.gfn_Click_On_Object("span", "My Tasks");

			verify("My Tasks is Clicked under Navigations in JBPM Page", blnFlg);

			blnFlg = oGenericUtils.gfn_Click_On_Object("a", sInstanceType);

			SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

			verify(sInstanceType + " link is clicked under MyTasks in JBPM Page", blnFlg);

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

			SeleniumUtils.switchWindowUsingWindowCount(1, 2, getDriver());

			break;
		case "ICD10-Admin":

//			oCommonPage.closeAllOpendTabs();
			oCommonPage.closeAllOpendTabs2();

			blnFlg = oGenericUtils.gfn_Click_On_Object("span", "My Tasks");

			verify("My Tasks is Clicked in TaskManagement screen", blnFlg);

			blnFlg = oGenericUtils.gfn_Click_On_Object("span", "Administration");

			verify("Administration is Clicked in TaskManagement screen", blnFlg);

			blnFlg = waitingForObject("Interpretive Update Instances", "Administration");

			SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

			blnFlg = oGenericUtils.gfn_Click_On_Object("span", "Interpretive Update Instances");

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

			blnFlg = waitingForObject("Description", "Interpretive Update Instances");

			verify("Interpretive Rule Update link is clicked in under Administration", blnFlg);

			break;

		case "ICD10-MyTasks":

			oCommonPage.closeAllOpendTabs();
//			oCommonPage.closeAllOpendTabs2();
			
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			blnFlg = oGenericUtils.gfn_Click_On_Object("span", "Administration");

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			verify("Administration is Clicked in TaskManagement screen", blnFlg);

			blnFlg = oGenericUtils.gfn_Click_On_Object("span", "My Tasks");

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			verify("My Tasks is Clicked in TaskManagement screen", blnFlg);

			blnFlg = waitingForObject("Interpretive Rule Update", "My Tasks");

			blnFlg = oGenericUtils.gfn_Click_On_Object("span", "Interpretive Rule Update");

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			blnFlg = waitingForObject("Group Tasks", "Interpretive Rule Update");

			verify("Interpretive Rule Update link is clicked in under MyTasks", blnFlg);

			break;
			
		case "ICD10-Reports":
		case "ICD10-ScrubReport":
			
//			oCommonPage.closeAllOpendTabs();
//			if (sInstanceType.equalsIgnoreCase("ICD10-Report")) {
//
//				SeleniumUtils.switchWindowUsingWindowCount(4 ,3, getDriver());
//
//			}	

			SeleniumUtils.defaultWait(3000);
			if(sInstanceType.equalsIgnoreCase("ICD10-ScrubReport")){
				EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
				String sInstance = variables.getProperty("IU.AdminScrubInstance");
				Serenity.setSessionVariable("IUInstanceName").to(sInstance);
			}else{
				EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
				String sInstance = variables.getProperty("IU.FinalMDInstance");
				Serenity.setSessionVariable("IUInstanceName").to(sInstance);
			}
			
			blnFlg = oGenericUtils.gfn_Click_On_Object("span", "My Tasks");

			verify("My Tasks is Clicked in TaskManagement screen", blnFlg);

			blnFlg = oGenericUtils.gfn_Click_On_Object("span", "Administration");

			verify("Administration is Clicked in TaskManagement screen", blnFlg);

			blnFlg = waitingForObject("Interpretive Update Instances", "Administration");

			SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
		
			blnFlg = oGenericUtils.gfn_Click_On_Object("span", "Interpretive Update Instances");

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			verify("Entered Instance Name ",oSeleniumUtils.Enter_given_Text_Element("(//input[@class='ag-floating-filter-input'])[1]", Serenity.sessionVariableCalled("IUInstanceName").toString()));
			verify("Clicked Instance from Admin Tab", oGenericUtils.gfn_Click_String_object_Xpath("//a[text()='"+Serenity.sessionVariableCalled("IUInstanceName").toString()+"']"));
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			verify("Dashboard button is clicked " ,oGenericUtils.gfn_Click_String_object_Xpath("//button[text()='Dashboard']"));
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			
			SeleniumUtils.switchWindowUsingWindowCount(1, 3, getDriver());
			oCommonPage.closeAllOpendTabs();
			
			break;

		default:
			verify("Instance Type is not matched for function user_Navigate_To_IU_Instances_Screen", false);
		}

	}

	public boolean waitingForObject(String sObjectNotDisplaying, String sObjectToClick) throws InterruptedException {

		boolean blnFlg = false;

		int k = 0;

		while (oSeleniumUtils.is_WebElement_Displayed("//span[text()='" + sObjectNotDisplaying + "']") == false
				&& k != 5) {

			blnFlg = oGenericUtils.gfn_Click_On_Object("span", sObjectToClick);

			k = k + 1;

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);
		}

		if (oGenericUtils.gfn_Verify_Object_Exist("span", sObjectNotDisplaying)) {

			blnFlg = true;
		}

		return blnFlg;

	}

	@Step
	public void clickOnInstanceNamefromGridTable(String sInstanceName) {

		if (sInstanceName.equalsIgnoreCase("PO Review Work Queue")) {

			List<WebElement> MDTasklist = getDriver().findElements(By.xpath(oHomePage.MD_WorkQueue_CellData));

			for (int i = 0; i < MDTasklist.size(); i++) {

				if (MDTasklist.get(i).getText().equalsIgnoreCase(sInstanceName)) {

					MDTasklist.get(i).click();

					SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

					oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

					break;

				}

			}

		} else {

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.InstanceName, "sValue", sInstanceName));

			Assert.assertTrue("Actions is not Displayed", oSeleniumUtils.is_WebElement_Displayed(oHomePage.Actions));

			oSeleniumUtils.highlightElement(oHomePage.Actions_Instance_Grid);

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.Actions);

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag, "sValue", "Rule Review"));

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		}

	}

	@Step
	public void userShouldAbleToViewWorkQueue(String sTaskType) {

		switch (sTaskType) {

		case "Review Work Queue":

			oSeleniumUtils.is_WebElement_Displayed(oHomePage.Rule_Review_WorkQueue);

			Serenity.setSessionVariable("Rule Version")
					.to(oSeleniumUtils.get_TextFrom_Locator(oHomePage.InstanceVersion));

			System.out.println("Rule Version: " + Serenity.sessionVariableCalled("Rule Version").toString());

			oSeleniumUtils.Click_given_Locator(oHomePage.InstanceVersion);

			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			oSeleniumUtils.SwitchToFrame(oHomePage.frame);

			System.out.println("Admin Final Rule Version under stubrmr id : "
					+ oSeleniumUtils.get_TextFrom_Locator(oHomePage.FinalRule_Version));

			Assert.assertTrue("Rule Version is not Displayed under stubrmr id : ",
					oSeleniumUtils.is_WebElement_Displayed(oHomePage.FinalRule_Version));

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oHomePage.closeAllTabs();

			break;
		case "PO Review Work Queue":

			oSeleniumUtils.is_WebElement_Displayed(oHomePage.MD_Review_WorkQueue);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			Serenity.setSessionVariable("Rule Version")
					.to(oSeleniumUtils.get_TextFrom_Locator(oHomePage.MD_RuleReview_Version));

			System.out.println("Rule Version: " + Serenity.sessionVariableCalled("Rule Version").toString());

			oSeleniumUtils.Click_given_Locator(oHomePage.MD_RuleReview_Version);

			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			oSeleniumUtils.SwitchToFrame(oHomePage.frame);

			System.out.println("My Task Final Rule Version under stubrmr id : "
					+ oSeleniumUtils.get_TextFrom_Locator(oHomePage.FinalRule_Version));

			Assert.assertTrue("My Task Rule Version is not Displayed under stubrmr id:  ",
					oSeleniumUtils.is_WebElement_Displayed(oHomePage.FinalRule_Version));

			getDriver().switchTo().defaultContent();

			break;
		default:
			Assert.assertTrue("TaskType cases are not matched for function user_Should_Able_To_View_Work_Queue", false);

		}

	}

	@Step
	public void userShouldbeLoggedout() throws InterruptedException {
		Assert.assertTrue("Sign out of IU Application", oHomePage.fn_JBPM_Sign_Out());

	}

	@Step
	public void applyActiveFilterMyTaskRuleWorkQueue(String sFilterName, String sFilterValue)
			throws InterruptedException {

		// String sFilterNames="Rules;Library
		// Status;MedicalPolicies;Topics;Decision Points;Assignees;Proposal
		// Types;Codes;Category Codes;Tasks;Task Status;Payers;Priority;Rules
		// With ARDs;Impact Run";

		String sText = oSeleniumUtils.get_StringTextFrom_Locator("//span[contains(text(),'Showing')]");

		String[] sShowTextAll = sText.trim().split("of");
		String[] sShowText = sShowTextAll[0].trim().split("to");
		// System.out.println("vALUE " +sShowText[0]);
		System.out.println("vALUE " + sShowText[1]);

		List<String> sFilterNameListGherkin = Arrays.asList(sFilterName.split(";"));

		List<String> sFilterValueList = Arrays.asList(sFilterValue.split(";"));

		Serenity.setSessionVariable("FilterValues").to(sFilterValue);

		System.out.println(Serenity.sessionVariableCalled("FilterValues").toString());

		for (int i = 0; i < sFilterValueList.size(); i++) {

			String xpath = "//mat-label[text()='" + sFilterNameListGherkin.get(i).trim() + "']/../../..";

			System.out.println(xpath);

			WebElement e = getDriver().findElement(By.xpath(xpath));

			(new Actions(getDriver())).moveToElement(e).click().build().perform();

			boolean bln = oSeleniumUtils.is_WebElement_Displayed(
					"//span[text()='" + sFilterValueList.get(i).trim() + "']/../mat-pseudo-checkbox");

			if (bln == false) {

				WebElement el = getDriver().findElement(By.xpath(xpath));

				(new Actions(getDriver())).moveToElement(el).click().build().perform();

			}

			oGenericUtils.gfn_Click_String_object_Xpath(
					"//span[text()='" + sFilterValueList.get(i).trim() + "']/../mat-pseudo-checkbox");

			String xpath1 = "//button[text()='ApplyFilter']";

			WebElement el = getDriver().findElement(By.xpath(xpath1));

			(new Actions(getDriver())).moveToElement(el).click().build().perform();

			// oGenericUtils.gfn_Click_On_Object("button", "ApplyFilter");

			oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			oGenericUtils.gfn_Verify_Object_Exist("span", sFilterValueList.get(i).trim());

			oGenericUtils.gfn_Verify_Object_Exist("div", sFilterValueList.get(i).trim());

		}

		oGenericUtils.gfn_Click_On_Object("button", "ApplyFilter");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		/*
		 * oGenericUtils.gfn_Click_String_object_Xpath(
		 * "(//span[contains(@class,'ui-chkbox-icon')])[2]/..");
		 * 
		 * oGenericUtils.gfn_Click_String_object_Xpath(
		 * "(//span[contains(@class,'ui-chkbox-icon')])[3]/..");
		 * 
		 * String sPropCount1=oSeleniumUtils.get_StringTextFrom_Locator(
		 * "(//span[contains(@class,'ui-chkbox-icon')])[2]/../../../../following-sibling::td[3]//span"
		 * );
		 * 
		 * String sPropCount2=oSeleniumUtils.get_StringTextFrom_Locator(
		 * "(//span[contains(@class,'ui-chkbox-icon')])[3]/../../../../following-sibling::td[3]//span"
		 * );
		 * 
		 * int iPropCount1=Integer.parseInt(sPropCount1);
		 * 
		 * int iPropCount2=Integer.parseInt(sPropCount2);
		 * 
		 * oGenericUtils.gfn_Click_On_Object("button", " Bulk Decision");
		 * 
		 * int iPropCountinPopup=Integer.parseInt(oSeleniumUtils.
		 * get_TextFrom_Locator(
		 * "//label[text()='Proposal Count to be Impacted:']/../following-sibling::td"
		 * ).trim());
		 * 
		 * boolean bln= iPropCountinPopup==iPropCount1+iPropCount2;
		 * 
		 * System.out.println("Sum of Proposals"+bln);
		 * 
		 * String sImpactedCodes=oSeleniumUtils.get_StringTextFrom_Locator(
		 * "//label[text()='Rule Count to be Impacted:']/../following-sibling::td"
		 * );
		 * 
		 * boolean bln1=sImpactedCodes.trim().equalsIgnoreCase("2");
		 * 
		 * System.out.println("No fo Codes"+bln1);
		 * 
		 * oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
		 * "//label[text()='Decision for these Proposals:']/../following-sibling::td//select"
		 * , "Add Code");
		 * 
		 * oSeleniumUtils.Enter_given_Text_Element(
		 * "//label[text()='Enter New Rationale Comment']/../following-sibling::td//textarea"
		 * , "Bulk Decisions");
		 */
		/*
		 * oGenericUtils.gfn_Click_On_Object("button", "Apply Decision");
		 * 
		 * oGenericUtils.gfn_Verify_Object_Exist("div",
		 * "Are you sure you want to apply the Bulk Decision to the selected rule proposals?"
		 * );
		 * 
		 * oGenericUtils.gfn_Click_On_Object("button", "Yes");
		 * 
		 * oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		 * 
		 * oGenericUtils.gfn_Verify_Object_Exist("div", "Saved Successfully!");
		 * 
		 * oGenericUtils.gfn_Click_On_Object("button", "Ok");
		 * 
		 * oGenericUtils.gfn_Click_String_object_Xpath(
		 * "(//span[contains(@class,'ui-chkbox-icon')])[2]/../../../../following-sibling::td//a"
		 * );
		 * 
		 * oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		 * 
		 * SeleniumUtils.defaultWait(3000);
		 */

		/*
		 * oGenericUtils.gfn_Verify_String_Object_Exist(
		 * "//th[@ng-reflect-ng-switch='interpRuleCpt.cptCode']//input");
		 * 
		 * oSeleniumUtils.Enter_given_Text_Element(
		 * "//th[@ng-reflect-ng-switch='interpRuleCpt.cptCode']//input",
		 * "I48.11");
		 * 
		 * System.out.println(oGenericUtils.gfn_Click_On_Object("td",
		 * " BULK DEC "));
		 */

	}

	@Step
	public void clickOnWorkQueue(String arg1) throws InterruptedException {
		boolean blnFlg = false;
		switch (arg1) {

		case "Start Review":

			SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

			blnFlg = oGenericUtils.gfn_Click_On_Object("button", "Start Review");

			verify("Clicked on Start Review", blnFlg);

			oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
/*
			String sTaskTypeDBValue = DBUtils.executeSQLQuery(DBQueries.sQueryTaskTypeAndStatus("TTL.TASK_TYPE_DESC",
					Serenity.sessionVariableCalled("IUInstanceName").toString(),
					Serenity.sessionVariableCalled("MidRuleVersion").toString()));

			System.out.println("print::" + "Please confirm you wish to start " + sTaskTypeDBValue + ".");

			blnFlg = oGenericUtils.gfn_Verify_Object_Exist("div",
					"Please confirm you wish to start " + sTaskTypeDBValue + ".");

			verify("Confirmation is displayed. Messege :: "+"Please confirm  you wish to start "+sTaskTypeDBValue+".",blnFlg); */

			blnFlg = oGenericUtils.gfn_Click_On_Object("button", "Yes");

			verify("Yes button is clicked", blnFlg);

			oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			break;

		case "Generate Summaries":

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			if ((oSeleniumUtils.is_WebElement_Present(oHomePage.Generate_Summaries))) {

				oSeleniumUtils.Click_given_Locator(oHomePage.Generate_Summaries);
			} else {
				oGenericUtils.gfn_Click_On_Object("button", "Generate Summaries");
			}

			oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			// boolean bl=oGenericUtils.gfn_Verify_Object_Exist("h4",
			// "Success");
			//
			// verify("Generate Summaries Message is Displayed as expected",bl);

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			break;

		case "Generate Change Log":
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oSeleniumUtils.SwitchToFrame(oHomePage.frame);

			oGenericUtils.gfn_Click_On_Object("tab-heading", "Decisions");

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oSeleniumUtils.Click_given_Locator(oHomePage.GenerateChangeLog);

			verify("Change Log Popup is Displayed", oSeleniumUtils
					.is_WebElement_Displayed(StringUtil.replace(oHomePage.h3Tag, "sValue", "Change Log")));

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Ok"));

			getDriver().switchTo().defaultContent();

			break;

		case "Authorize Decisions":

			String SelCase = null;

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag, "sValue", "Summaries"));

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oGenericUtils.gfn_Click_String_object_Xpath(("//button[contains(text(),'Authorize Decisions')]"));

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			int iEditorialWarning = getDriver()
					.findElements(
							By.xpath(StringUtils.replace(oHomePage.h3Tag, "sValue", "Editorial changes observed")))
					.size();

			if (iEditorialWarning > 0) {

				oGenericUtils.gfn_Click_On_Object("button", "No");
				oGenericUtils.gfn_Click_String_object_Xpath(("//label[contains(text(),'Authorize Decisions')]"));

			}

			List<WebElement> sList = getDriver().findElements(By.xpath(oHomePage.RetireRule_Error));

			if (sList.size() > 0) {

				SelCase = "Retire";
			}

			switch (SelCase) {
			case "Retire":

				oGenericUtils.gfn_Verify_Object_Exist("div", "Please Review Retire Rule before Authorizing DecisionS");
				oGenericUtils.gfn_Click_On_Object("button", "Ok");
				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
				oGenericUtils.gfn_Verify_Object_Exist("span", "Retire Rule");
				oGenericUtils.gfn_Click_On_Object("span", "Retire Rule");

				oSeleniumUtils.select_Given_Value_From_DropDown(oHomePage.Retirerule_MDecision, "Do Not Retire Rule");
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				oHomePage.MD_Comments.click();

				oSeleniumUtils.Enter_given_Text_Element(oHomePage.MD_Comments, ProjectVariables.TestComments);
				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
				oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Save"));
				oGenericUtils.gfn_Verify_Object_Exist("button", "Ok");
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Ok"));
				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
				oGenericUtils.gfn_Click_String_object_Xpath(("//label[contains(text(),'Authorize Decisions')]"));

				break;
			}

			oGenericUtils.gfn_Verify_Object_Exist("button", "Yes");
			// oHomePage.wait_Untill_ElementExists(oHomePage.Authorize_Decisions);

			Assert.assertTrue("Confirmation Warning should be displayed",
					oSeleniumUtils.is_WebElement_Displayed(oHomePage.Authorize_Decisions));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Yes"));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Verify_Object_Exist("h3", "Authorize Decisions");

			Assert.assertTrue("Confirmation Warning should be displayed",
					oSeleniumUtils.is_WebElement_Displayed(StringUtil.replace(oHomePage.pTag, "sValue",
							" Rule moved to next step in rule review process successfully.")));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Ok"));

			getDriver().switchTo().defaultContent();

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			break;

		case "Require Presentation":
			requires_Presentation();
			break;

		case "Return Rule CPM":

			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
			oSeleniumUtils.SwitchToFrame(oHomePage.frame);
			oSeleniumUtils.Click_given_Locator(oHomePage.ReturnRule);
			oGenericUtils.gfn_Verify_Object_Exist("h3", "Editorial Return Rule - Custom Rule");
			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(oHomePage.EditorialReturnSelect, "CPM");
			oSeleniumUtils.Enter_given_Text_Element(oHomePage.EditorialReturnReason, "Return to CPM");
			oGenericUtils.gfn_Click_On_Object("button", "Return Rule");
			oGenericUtils.gfn_Verify_Object_Exist("p",
					"Rule is being returned to CPM for clarification of Editorial concern. Continue?");
			oGenericUtils.gfn_Click_On_Object("button", "Yes");
			oGenericUtils.gfn_Verify_Object_Exist("p", " Rule Returned successfully.");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			getDriver().switchTo().defaultContent();
			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);
			break;

		case "Return Rule Response":

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oSeleniumUtils.SwitchToFrame(oHomePage.frame);

			oSeleniumUtils.Click_given_Locator(oHomePage.ReturnRuleResponse);

			oGenericUtils.gfn_Verify_Object_Exist("h3", "Return Rule Response");

			oSeleniumUtils.Enter_given_Text_Element(oHomePage.CPMResponse, ProjectVariables.ReturnRuleComments);

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.SaveReturnRuleResponse);

			oGenericUtils.gfn_Verify_Object_Exist("h3", "WARNING");

			oSeleniumUtils.Enter_given_Text_Element(oHomePage.CPMResponse, ProjectVariables.ReturnRuleComments);

			oGenericUtils.gfn_Click_On_Object("button", "Return Response To Editorial");

			oGenericUtils.gfn_Verify_Object_Exist("h3", "WARNING");

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.StartReviewPoPYesBtn);

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			getDriver().switchTo().defaultContent();

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			break;

		case "Return Rule Response To Testing":

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oSeleniumUtils.SwitchToFrame(oHomePage.frame);

			oSeleniumUtils.Click_given_Locator(oHomePage.ReturnRuleResponse);

			oGenericUtils.gfn_Verify_Object_Exist("h3", "Return Rule Response");

			oSeleniumUtils.Enter_given_Text_Element(oHomePage.CPMResponse, ProjectVariables.ReturnRuleComments);

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.SaveReturnRuleResponse);

			oGenericUtils.gfn_Verify_Object_Exist("h3", "WARNING");

			oSeleniumUtils.Enter_given_Text_Element(oHomePage.CPMResponse, ProjectVariables.ReturnRuleComments);

			oGenericUtils.gfn_Click_On_Object("button", "Return Response To Testing");

			oGenericUtils.gfn_Verify_Object_Exist("h3", "WARNING");

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.StartReviewPoPYesBtn);

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			getDriver().switchTo().defaultContent();

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			break;

		case "Return Rule Response Testing":
			oSeleniumUtils.SwitchToFrame(oHomePage.frame);

			oSeleniumUtils.Click_given_Locator(oHomePage.ReturnRuleResponse);

			oGenericUtils.gfn_Verify_Object_Exist("h3", "Return Rule Response");

			oSeleniumUtils.Enter_given_Text_Element(oHomePage.CPMResponse, ProjectVariables.ReturnRuleComments);

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.SaveReturnRuleResponse);

			oGenericUtils.gfn_Verify_Object_Exist("h3", "WARNING");

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.StartReviewPoPYesBtn);

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			getDriver().switchTo().defaultContent();

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			break;

		case "CPM Decesion Complete":

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			oSeleniumUtils.SwitchToFrame(oHomePage.frame);

			oSeleniumUtils
					.Click_given_Locator((StringUtil.replace(oHomePage.LabelTag, "sValue", "CPM Decision Complete")));

			oGenericUtils.gfn_Click_On_Object("span", "OK");
			oGenericUtils.gfn_Verify_Object_Exist("p", " CPM Decisions completed successfully");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			getDriver().switchTo().defaultContent();
			break;

		case "CPM Authorize Decisions":

			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			oSeleniumUtils.SwitchToFrame(oHomePage.frame);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			verify("clicked on CPM Summaries button", oSeleniumUtils
					.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "CPM Summaries")));

			verify("Clicked on Authorize Decisions button",
					oGenericUtils.gfn_Click_String_object_Xpath(("//label[contains(text(),'Authorize Decisions')]")));

			verify("clicked on Yes button ", oGenericUtils.gfn_Click_On_Object("button", "Yes"));

			getDriver().switchTo().defaultContent();

			break;
		case "Modify Decisions":

			blnFlg = oGenericUtils.gfn_Click_String_object_Xpath("//button[contains(text(),'" + arg1 + "')]");

			verify(arg1 + " is clicked", blnFlg);

			break;

		case "Peer Review":
			oSeleniumUtils.SwitchToFrame(oHomePage.frame);
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			oGenericUtils.gfn_Click_String_object_Xpath(
					oHomePage.GetDynamicXPathWithString("CONTAINS LABEL", "Peer Agree", ""));
			oGenericUtils.gfn_Verify_Object_Exist("h3", "Peer Review Information");
			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.StartReviewPoPYesBtn);

			getDriver().switchTo().defaultContent();
			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			break;
		case "Set Require Presentation":

			setRequirePresentation();

			break;

		case "Return Require Present Rule":

			returnRequiresPresentationRule();

			break;
		case "Reset":

			oGenericUtils.gfn_Click_On_Object("div", arg1);

			verify("Data which we selected is empty", !oSeleniumUtils.is_WebElement_Displayed(
					"//span[text()='Type']/../../../../../../../..//div[@class='GEFT4QHBE3C']"));

			break;

		case "Clear":

			oGenericUtils.gfn_Click_On_Object("div", arg1);

			// verify("Data which we selected is empty",
			// !oSeleniumUtils.is_WebElement_Displayed("//span[text()='Type']/../../../../../../../..//div[@class='GEFT4QHBE3C']"));

			break;
		case "Interpretive RuleUpdate":
			oGenericUtils.gfn_Click_On_Object("span", "Interpretive Rule Update Tasks");

			int i = getDriver().findElements(By.xpath("//div[@style='background-color: rgb(0, 100, 0);']")).size();

			if (i > 0) {
				oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
			}
			break;

		default:
			Assert.assertTrue("Case values are not matched for function click on", false);

		}

	}

	@Step
	public void selectSystemProposal(String sProposalType, String sDecision) throws InterruptedException {

		oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		if (sProposalType.equalsIgnoreCase("Modify Decisions")) {

			oGenericUtils.gfn_Click_On_Object("button", " Modify Decisions ");

		}

		String[] sDropDownList = oSeleniumUtils
				.get_All_Text_from_Locator("(//h5[.=' System Proposals ']/../../..//select)//option");

		ArrayList<String> oList = new ArrayList<String>();

		for (int i = 0; i < sDropDownList.length; i++) {

			oList.add(sDropDownList[i]);
		}

		switch (sDecision) {

		case "No Decision-Not Displayed":

			oSeleniumUtils.Click_given_Locator(oHomePage.SystemProposalDecision);

			boolean bln = !oList.contains("No Decision");

			verify("No Decision Value is displayed", bln);

			break;
		case "No Decision-Displayed":

			oSeleniumUtils.Click_given_Locator(oHomePage.SystemProposalDecision);

			boolean bln1 = oList.contains("No Decision");

			verify("No Decision Value is  displayed", bln1);

			break;
		case "No Action":
		case "Remove":
		case "No Decision":

			List<WebElement> iMidRuleCount = getDriver()
					.findElements(By.xpath("//h5[.=' System Proposals ']/../../..//select"));

			System.out.println(iMidRuleCount.size());

			for (int i = 1; i <= iMidRuleCount.size(); i++) {
				// int j=i-1;
				int j = i;
				String sXpath = oHomePage.SystemProposalDecision + "[" + i + "]";
				System.out.println(sXpath);
				oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(sXpath, sDecision);
				oSeleniumUtils.Enter_given_Text_Element(oHomePage.SystemProposalCommments + "[" + j + "]",
						ProjectVariables.SystemProposalComments);

			}

			break;

		case "Modify":

			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(oHomePage.SystemProposalDecision,
					sDecision);

			oSeleniumUtils.Enter_given_Text_Element(oHomePage.SystemProposalCommments,
					ProjectVariables.SystemProposalComments);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			/*
			 * oGenericUtils.gfn_Click_String_object_Xpath(sXpath)
			 * oSeleniumUtils.Click_given_Locator(oHomePage.System_Proposal_DOS)
			 * ;
			 * 
			 * SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			 */

			oSeleniumUtils.Enter_given_Text_Element(oHomePage.System_Proposal_DOS, ProjectVariables.SystemProposalDOS);

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag, "sValue", "Ok"));

			break;

		case "ModifywithoutDate":

			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(oHomePage.SystemProposalDecision,
					ProjectVariables.Decision);

			oSeleniumUtils.Enter_given_Text_Element("//textarea[@id='1_interpComment.comments']",
					ProjectVariables.SystemProposalComments);

			break;

		default:

		}

		oGenericUtils.gfn_Click_On_Object("button", "Save");

		// oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.AnchorTag,
		// "sValue", "oHomePage.System_Proposal_DOS"));

		/*
		 * boolean bln = oSeleniumUtils.is_WebElement_Displayed(
		 * "//p[contains(text(),' Please Modify the DOS value for Review Code')]).gfn_Verify_String_Object_Exist("
		 * );
		 * 
		 * if (bln == true) {
		 * 
		 * oGenericUtils.gfn_Click_On_Object("button", "Ok");
		 * 
		 * getDriver().switchTo().defaultContent();
		 * 
		 * selectSystemProposal(sProposalType, "Modify");
		 * 
		 * }
		 * 
		 * oGenericUtils.gfn_Click_On_Object("button", "Ok");
		 * 
		 * getDriver().switchTo().defaultContent();
		 */

	}

	@Step
	public void addCodeFunction(String sCategory, String sCPTCode) {

		Serenity.setSessionVariable("CPTCode").to(sCPTCode);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.LabelTag, "sValue", "Add Code"));

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(oHomePage.StartNewRadio_Btn);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.AnchorTag, "sValue", "Start New"));

		oSeleniumUtils.select_Given_Value_From_DropDown(oHomePage.ManualProposalUpdateInstanceDropdwn, "Yes");

		oSeleniumUtils.Enter_given_Text_Element(oHomePage.ManualProposalsRationaleComments,
				ProjectVariables.ManualProposalsComments);

		oSeleniumUtils.Enter_given_Text_Element(oHomePage.ManualProposalsCptCode, sCPTCode);

		oSeleniumUtils.select_Given_Value_From_DropDown(oHomePage.ManualProposalCatgoryDropdwn, sCategory);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.LabelTag, "sValue", "Save"));

		Assert.assertTrue("Manual Proposal message no displayed", oSeleniumUtils.is_WebElement_Displayed(
				StringUtil.replace(oHomePage.pTag, "sValue", ProjectVariables.ManualProposalsDailogMsg)));

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Ok"));

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void completeEditorialInFinalMDReview(String sUpdateType) throws InterruptedException {

		oHomePage.Click_on_Editorial();

		switch (sUpdateType) {

		case "No Editorial Changes":

			// SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oSeleniumUtils.Click_given_Locator(
					StringUtil.replace(oHomePage.No_Edit_Required, "sValue", "No Editorial Changes Required"));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			verify("Confirmation Warning should be displayed", oSeleniumUtils.is_WebElement_Displayed(
					StringUtil.replace(oHomePage.pTag, "sValue", ProjectVariables.Editorial_MSG_NC_Required)));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oSeleniumUtils.Click_given_WebElement(oHomePage.Confirm_Yes);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			if (Serenity.sessionVariableCalled("TaskType") != null) {

				if (Serenity.sessionVariableCalled("TaskType").toString().equalsIgnoreCase("Preliminary PO Review")) {

					verify("Confirmation Warning should be displayed", oSeleniumUtils.is_WebElement_Displayed(StringUtil
							.replace(oHomePage.pTag, "sValue", ProjectVariables.Editorial_MSG_Editorial_Saved_Prilim)));

				} else {

					verify("Confirmation Warning should be displayed", oSeleniumUtils.is_WebElement_Displayed(StringUtil
							.replace(oHomePage.pTag, "sValue", ProjectVariables.Editorial_MSG_Editorial_Saved)));
				}
			}

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			break;

		case "Editorial Changes":

			oSeleniumUtils.Enter_given_Text_Element(oHomePage.EditNewRuleDesc,
					ProjectVariables.EditorialChangesDescripiton);

			oGenericUtils.gfn_Click_On_Object("label", "Save");

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Click_On_Object("label", "Complete Editorials");

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Verify_Object_Exist("h3", "Success");

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			break;

		case "Edit Decription":

			// Edit Description
			oSeleniumUtils.Enter_given_Text_Element(
					oHomePage.GetDynamicXPathWithString("EDITORIAL TABS", "editables.newRuleDescription", ""),
					ProjectVariables.EditorialChangesDescripiton);
			oGenericUtils.gfn_Click_On_Object("label", "Save");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			break;

		case "Update Tabs":

			// Edit Description
			oSeleniumUtils.Enter_given_Text_Element(
					oHomePage.GetDynamicXPathWithString("EDITORIAL TABS", "editables.newRuleDescription", ""),
					ProjectVariables.EditorialChangesDescripiton);
			oGenericUtils.gfn_Click_On_Object("label", "Save");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			// Edit Notes
			oGenericUtils.gfn_Click_On_Object("span", "Notes");
			oSeleniumUtils.Enter_given_Text_Element(
					oHomePage.GetDynamicXPathWithString("EDITORIAL TABS", "editables.updatedNotes", ""),
					ProjectVariables.EditorialChangesNotes);
			oGenericUtils.gfn_Click_On_Object("label", "Save");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			// Edit Script
			oGenericUtils.gfn_Click_On_Object("span", "Script");
			oSeleniumUtils.Enter_given_Text_Element(
					oHomePage.GetDynamicXPathWithString("EDITORIAL TABS", "editables.updatedScript", ""),
					ProjectVariables.EditorialChangesScript);
			oGenericUtils.gfn_Click_On_Object("label", "Save");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			// Edit Rationale
			oGenericUtils.gfn_Click_On_Object("span", "Rationale");
			oSeleniumUtils.Enter_given_Text_Element(
					oHomePage.GetDynamicXPathWithString("EDITORIAL TABS", "editables.updatedRationale", ""),
					ProjectVariables.EditorialChangesRationale);
			oGenericUtils.gfn_Click_On_Object("label", "Save");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			// Edit Reference
			oGenericUtils.gfn_Click_On_Object("span", "Reference");
			oSeleniumUtils.Enter_given_Text_Element(
					oHomePage.GetDynamicXPathWithString("EDITORIAL TABS", "editables.updatedReference", ""),
					ProjectVariables.EditorialChangesReference);
			oGenericUtils.gfn_Click_On_Object("label", "Save");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Click_On_Object("label", "Complete Editorials");

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Verify_Object_Exist("h3", "Success");

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			break;

		case "Approve Editorials":

			oGenericUtils.gfn_Click_On_Object("label", sUpdateType);

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			break;

		case "Complete Editorials":
			/*
			 * oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage
			 * .No_Edit_Required, "sValue", "Approve Editorials"));
			 * 
			 * SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			 * 
			 * oGenericUtils.gfn_Click_On_Object("button", "Ok");
			 */

			// oHomePage.Click_on_Editorial();

			oSeleniumUtils.Click_given_Locator(
					StringUtil.replace(oHomePage.CompleteEditorials_Approve, "sValue", "Complete Editorials"));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			break;

		default:
			Assert.assertTrue("completeEditorialInFinalMDReview", false);

		}

		getDriver().close();

		SeleniumUtils.switchWindowUsingWindowCount(3, 1, getDriver());

		getDriver().switchTo().defaultContent();
	}

	@Step
	public void validateBOBWGridData() throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag, "sValue", "Configurations Summary"));

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		if (!oHomePage.NoConfigSummaryContent.isVisible()) {

			Assert.assertTrue("Confirmation Warning should be displayed",
					oSeleniumUtils.is_WebElement_Displayed(oHomePage.Configuration_Summary_Content));

		} else {
			oSeleniumUtils.Enter_given_Text_Element(oHomePage.ConfigSummaryInstruction, ProjectVariables.TestComments);

			oSeleniumUtils.Click_given_WebElement(oHomePage.ConfigSummarySaveButton);

			oGenericUtils.gfn_Verify_Object_Exist("h3", "Success");

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Ok"));

		}

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void retireRuleCheck(String sCreateRuleVersion) throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Summaries"));

		oGenericUtils.gfn_Verify_Object_Exist("span", "Configurations Summary");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		if (!(oSeleniumUtils.is_WebElement_Displayed(StringUtil.replace(oHomePage.SpanTag, "sValue", "Retire Rule")))) {

			oSeleniumUtils.Click_given_WebElement(oHomePage.RetireRuleBtn);

			oGenericUtils.gfn_Verify_Object_Exist("span", "Retire Rule");

		}

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag, "sValue", "Retire Rule"));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		if (sCreateRuleVersion.equalsIgnoreCase("Yes")) {

			if (oSeleniumUtils.is_WebElement_Present(oHomePage.Retirerule_MDecision)) {

				oSeleniumUtils.select_Given_Value_From_DropDown(oHomePage.Retirerule_MDecision, "Retire Rule");

				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

				Serenity.setSessionVariable("RetireRule-Yes").to(sCreateRuleVersion);
			}

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.CreateNewRuleVersion, "sValue", "-1"));
		}

		if (sCreateRuleVersion.equalsIgnoreCase("No")) {

			if (oSeleniumUtils.is_WebElement_Present(oHomePage.Retirerule_MDecision)) {

				oSeleniumUtils.select_Given_Value_From_DropDown(oHomePage.Retirerule_MDecision, "Do Not Retire Rule");

				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			}

			// oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.CreateNewRuleVersion,
			// "sValue", "0"));
		}

		if (sCreateRuleVersion.equalsIgnoreCase("RetireRuleWithNoVersion")) {

			if (oSeleniumUtils.is_WebElement_Present(oHomePage.Retirerule_MDecision)) {

				oSeleniumUtils.select_Given_Value_From_DropDown(oHomePage.Retirerule_MDecision, "Retire Rule");

				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			}
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.CreateNewRuleVersion, "sValue", "0"));
		}

		oHomePage.MD_Comments.click();

		verify("Comments were entered",
				oSeleniumUtils.Enter_given_Text_Element(oHomePage.MD_Comments, ProjectVariables.TestComments));

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		verify("Save button is clicked",
				oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Save")));

		oGenericUtils.gfn_Verify_Object_Exist("button", "Ok");

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		verify("Confirmation Warning should be displayed", oSeleniumUtils
				.is_WebElement_Displayed(StringUtil.replace(oHomePage.pTag, "sValue", ProjectVariables.Message_Saved)));

		verify("Ok button is clicked",
				oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Ok")));

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void authorizeDecisions() {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		verify("Summaries clicked",
				oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Summaries")));

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		verify("Authorize Decisions is clicked",
				oSeleniumUtils.Click_given_Locator("//label[contains(text(),'Authorize Decisions')]"));

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		oHomePage.wait_Untill_ElementExists(oHomePage.Authorize_Decisions);

		verify("Confirmation Warning should be displayed",
				oSeleniumUtils.is_WebElement_Displayed(oHomePage.Authorize_Decisions));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		verify("Yes button is cliked",
				oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Yes")));

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		verify("Confirmation Warning should be displayed", oSeleniumUtils.is_WebElement_Displayed(StringUtil
				.replace(oHomePage.pTag, "sValue", " Rule moved to next step in rule review process successfully.")));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		verify("Ok button is clicked",
				oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Ok")));

		getDriver().switchTo().defaultContent();

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

	}

	@Step
	public void validateBOBWConfigLinkSubmit(String sCategoryType) {

		switch (sCategoryType) {

		case "Billed With And":
		case "Billed With (Or)":

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oSeleniumUtils.SwitchToFrame(oHomePage.frame);

			verify("BW And/BWO or Config Logic Link is clicked", oSeleniumUtils.Click_given_Locator(
					StringUtil.replace(oHomePage.AnchorTag, "sValue", "BW And/BWO or Config Logic")));

			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			verify("Billed With And/Billed Without Code displayed", oSeleniumUtils.is_WebElement_Displayed(StringUtil
					.replace(oHomePage.h3Tag, "sValue", "Billed With And/Billed Without Or Code Clarification")));

			List<WebElement> BWRadioBtn = getDriver().findElements(By.xpath("//label[@class='radio-inline']/input"));

			if (BWRadioBtn.size() > 1) {

				verify("Billed With Radio Button is Clicked",
						oSeleniumUtils.Click_given_WebElement(oHomePage.BW_Radio_button));

			} else {

				verify("New Billed With Radio Button is Clicked",
						oSeleniumUtils.Click_given_WebElement(oHomePage.NewBilled_Radio_button));

			}

			verify("Submit Button is Clicked",
					oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Submit")));

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			verify("Billed With And/Billed Without Code displayed", oSeleniumUtils
					.is_WebElement_Displayed(StringUtil.replace(oHomePage.pTag, "sValue", " Saved Successfully")));

			verify("Ok Button is Clicked",
					oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Ok")));

			getDriver().switchTo().defaultContent();

			break;

		case "BWOLinkAvail":

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			oSeleniumUtils.SwitchToFrame(oHomePage.frame);

			if (!oHomePage.BWOConfigLink.isVisible()) {

				verify("BOBW Config Link is NOT Exist ", false);

			} else {
				verify("BOBW Config Link is Exist ", true);

			}

			getDriver().switchTo().defaultContent();

			break;

		case "BWOLinkNotAvail":

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			oSeleniumUtils.SwitchToFrame(oHomePage.frame);

			/*
			 * int iBOBWConfigLink2 = getDriver() .findElements(
			 * By.xpath(StringUtil.replace(oHomePage.AnchorTag, "sValue",
			 * "BW And/BWO or Config Logic"))) .size();
			 * 
			 * if (iBOBWConfigLink2 > 0) { verify("BOBW Config Link is Exist ",
			 * false); } else { verify("BOBW Config Link is NOT Exist ", true);
			 * 
			 * }
			 */

			if (!oHomePage.BWOConfigLink.isVisible()) {

				verify("BOBW Config Link is NOT Exist ", true);

			} else {
				verify("BOBW Config Link is Exist ", false);

			}

			getDriver().switchTo().defaultContent();

			break;

		default:
			Assert.assertTrue("Case values are not matched for function validate_BOBW_Config_Link_Submit on", false);

		}

	}

	@Step
	public void selectRuleWithTask(String sTaskValue) {

		if (sTaskValue.equalsIgnoreCase("Final PO Review")) {

			Serenity.setSessionVariable("TaskType").to(sTaskValue);

			oSeleniumUtils.is_WebElement_Displayed(oHomePage.MD_Review_WorkQueue);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			boolean sFlag = false;

			List<WebElement> RuleVersionlist = getDriver().findElements(By.xpath(oHomePage.MyTaskRuleVersions));

			for (int i = 0; i < RuleVersionlist.size(); i++) {

				String[] sRuleVersion = RuleVersionlist.get(i).getText().split("\\.");

				// Execute Query Before selecting Rule from My Tasks
				String sQuery = " DEACTIVATED_10 from rules.sub_rules where MID_RULE_KEY = '" + sRuleVersion[0]
						+ "' and RULE_VERSION = '" + sRuleVersion[1] + "'";

				System.out.println("Query: " + sQuery);

				String sDeactivateStatus = DBUtils.executeSQLQuery(sQuery);

				System.out.println("Query Status: " + sDeactivateStatus);

				if (sDeactivateStatus.equalsIgnoreCase("0")) {

					Serenity.setSessionVariable("MidRuleVersion").to(RuleVersionlist.get(i).getText());
					// Serenity.setSessionVariable("MidRuleVersion").to("12063.8");

					System.out.println(Serenity.sessionVariableCalled("MidRuleVersion").toString());
					verify("Selected Rule is :" + Serenity.sessionVariableCalled("MidRuleVersion").toString(), true);

					validateColumnStatus("Task", sTaskValue);

					SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
					RuleVersionlist.get(i).click();

					SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
					sFlag = true;
					break;

				}
			}

			if (sFlag == false) {

				verify("Selected Rules are Deactivated", false);
			}
		} else {

		}

	}

	@Step
	public void reassignTask(String sRole, String sReassignUserID) throws InterruptedException {

		oSeleniumUtils.Click_given_WebElement(oHomePage.Col_Rule_Grid);

		oSeleniumUtils.Click_given_WebElement(oHomePage.ReAssignNavigationAdmin);

		int i = getDriver().findElements(By.xpath(StringUtil.replace(oHomePage.SpanTag, "sValue", "Reassign"))).size();

		if (i > 0) {
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag, "sValue", "Reassign"));
		} else {
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.DivTag, "sValue", "Reassign"));
		}

		if (sRole.equalsIgnoreCase("CPM")) {

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.ReassignCPM);

			oSeleniumUtils.Click_given_WebElement(oHomePage.CPM_Reassgin_ListNavigaion);

			oGenericUtils.gfn_Click_On_Object("div", sReassignUserID);

			oGenericUtils.gfn_Click_On_Object("div", "OK");

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		} else {

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			oSeleniumUtils.Click_given_Locator(oHomePage.ReAssignToListBoxIcon);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oGenericUtils.gfn_Click_String_object_Xpath(
					StringUtils.replace(oHomePage.QAReassign, "sValue", sReassignUserID));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Click_On_Object("div", "Submit Reassignments");

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Click_String_object_Xpath(
					"//div[text()='Rules will be reassigned based on select options. Continue?']/parent::div/following-sibling::div//div[text()='Yes']");

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);
		}

	}

	@Step
	public void validateSeletedActiveFilterValues() {

		Assert.assertTrue("Library Status value is not displayed",
				Serenity.sessionVariableCalled("FiterValue").toString()
						.equalsIgnoreCase(oSeleniumUtils
								.get_TextFrom_Locator((StringUtil.replace(oHomePage.FilterValuesValidation, "sValue",
										Serenity.sessionVariableCalled("FiterValue").toString())))));

		Assert.assertTrue("Proposal Types value is not displayed",
				Serenity.sessionVariableCalled("FiterValue").toString()
						.equalsIgnoreCase(oSeleniumUtils
								.get_TextFrom_Locator((StringUtil.replace(oHomePage.FilterValuesValidation, "sValue",
										Serenity.sessionVariableCalled("FiterValue").toString())))));

		Assert.assertTrue("Filter Task Status value is not displayed",
				Serenity.sessionVariableCalled("FiterValue").toString()
						.equalsIgnoreCase(oSeleniumUtils
								.get_TextFrom_Locator((StringUtil.replace(oHomePage.FilterValuesValidation, "sValue",
										Serenity.sessionVariableCalled("FiterValue").toString())))));

		Assert.assertTrue("Task value is not displayed",
				Serenity.sessionVariableCalled("FiterValue").toString()
						.equalsIgnoreCase(oSeleniumUtils
								.get_TextFrom_Locator((StringUtil.replace(oHomePage.FilterValuesValidation, "sValue",
										Serenity.sessionVariableCalled("FiterValue").toString())))));

		Assert.assertTrue("Rules With ARDs value is not displayed",
				Serenity.sessionVariableCalled("FiterValue").toString()
						.equalsIgnoreCase(oSeleniumUtils
								.get_TextFrom_Locator((StringUtil.replace(oHomePage.FilterValuesValidation, "sValue",
										Serenity.sessionVariableCalled("FiterValue").toString())))));

	}

	@Step
	public void validateRemoveCode(String sReviewCode) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.LabelTag, "sValue", "Remove Code"));

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		// Check Generate Remove window
		verify("Generate Remove Code Proposal PopUp should Display", oSeleniumUtils.is_WebElement_Displayed(
				StringUtil.replace(oHomePage.h4Tag, "sValue", "Generate Remove Code Proposal")));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		// Enter value in Code to be removed and click search
		oSeleniumUtils.Enter_given_Text_Element(oHomePage.Code_Removed, sReviewCode);

		oGenericUtils.gfn_Click_On_Object("button", " Search ");

		oGenericUtils.gfn_Click_String_object_Xpath("//b[text()='  " + sReviewCode + "  ']/preceding-sibling::input");

		oGenericUtils.gfn_Click_String_object_Xpath("//input[@ng-model='$parent.relatedToUpdate']");

		// oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag,
		// "sValue", " Search "));

		// Enter comments in Rationale Comment
		oSeleniumUtils.Enter_given_Text_Element(oHomePage.Rational_Comment, "Autotext");

		// Click on Generate REmove Code
		oGenericUtils.gfn_Click_On_Object("button", "Generate Remove Code Proposals");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		boolean bln = oSeleniumUtils.is_WebElement_Displayed(
				(StringUtil.replace(oHomePage.pTag, "sValue", " Manual proposal is created successfully")));

		if (bln == true) {

			// Verify Manual Proposal Created success message
			verify("Manual proposal is created successfully message should be display",
					oGenericUtils.gfn_Verify_String_Object_Exist((StringUtil.replace(oHomePage.pTag, "sValue",
							" Manual proposal is created successfully"))));

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

		} else {

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			boolean blnError = oSeleniumUtils
					.is_WebElement_Displayed((StringUtil.replace(oHomePage.h3Tag, "sValue", "Error")));

			if (blnError == true) {

				oGenericUtils.gfn_Click_On_Object("button", "Ok");
			}

			String[] sCodes = { "01991", "0010M" };

			for (int i = 0; i < sCodes.length; i++) {

				// Enter value in Code to be removed and click search
				oSeleniumUtils.Enter_given_Text_Element(oHomePage.Code_Removed, sCodes[i]);

				oGenericUtils.gfn_Click_On_Object("button", " Search ");

				oGenericUtils
						.gfn_Click_String_object_Xpath("//b[text()='  " + sCodes[i] + "  ']/preceding-sibling::input");

				// Enter comments in Rationale Comment
				oSeleniumUtils.Enter_given_Text_Element(oHomePage.Rational_Comment, "Autotext");

				// Click on Generate REmove Code
				oGenericUtils.gfn_Click_On_Object("button", "Generate Remove Code Proposals");

				boolean blnMessage = oSeleniumUtils.is_WebElement_Displayed(
						(StringUtil.replace(oHomePage.pTag, "sValue", " Manual proposal is created successfully")));

				if (blnMessage == true) {

					// Verify Manual Proposal Created success message
					verify("Manual proposal is created successfully message should be display",
							oGenericUtils.gfn_Verify_String_Object_Exist((StringUtil.replace(oHomePage.pTag, "sValue",
									" Manual proposal is created successfully"))));

					oGenericUtils.gfn_Click_On_Object("button", "Ok");

					break;
				}

				if (blnError == true) {

					oGenericUtils.gfn_Click_On_Object("button", "Ok");
				}
			}

		}

		oGenericUtils.gfn_Click_On_Object("button", "x");

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void columnStatus(String sCloumnName, String sColumnValue) {
		switch (sCloumnName) {
		case "Status":
			oSeleniumUtils.get_TextFrom_Locator(StringUtil.replace(oHomePage.Column_Status, "sValue", "5"));
			break;
		case "Task":
			Assert.assertTrue("Task status is not valid", sColumnValue.equalsIgnoreCase(
					oSeleniumUtils.get_TextFrom_Locator(StringUtil.replace(oHomePage.Column_Status, "sValue", "4"))));
			break;
		case "Assignee":
			oSeleniumUtils.get_All_Text_from_Locator(StringUtil.replace(oHomePage.Assignee_Status, "sValue", "7"));
			break;
		}

	}

	@Step
	public void userShouldBeAbleSeeSelectedRuleVersion() {

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		System.out.println("Get text from Locator" + oSeleniumUtils.get_TextFrom_Locator(oHomePage.FinalRule_Version));

		Assert.assertTrue("Rule Version is not Displayed",
				oSeleniumUtils.is_WebElement_Displayed(oHomePage.FinalRule_Version));

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void navigateToMyTasksAndGoToRuleInInstance(String sRole, String sTaskName, String sInstancetype)
			throws InterruptedException {

		oHomePage.closeAllTabs();

		userNavigateToIUInstancesScreen("MyTasks");

		switch (sRole) {
		case "Admin PO Scrub":

			Assert.assertTrue("Work Queue Validation in My Task is not Displayed as expected", oSeleniumUtils
					.is_WebElement_Displayed(Serenity.sessionVariableCalled("TaskandInstnaceName").toString()));

			break;

		default:

			oMyTasksStepdef.clickTaskByInstanceName(sTaskName, sInstancetype);

			oHomePage.go_To_Rule(Serenity.sessionVariableCalled("MidRuleVersion").toString());

		}

	}

	@Step
	public void completeAllQAReviewsAndUpdateQARule() throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "QA"));

		oGenericUtils.gfn_Verify_String_Object_Exist(oHomePage.QAReviewSegment);

		List<WebElement> rows = getDriver().findElements(By.xpath(oHomePage.QA_Review_Table));

		String sReviewSegValue = null;

		for (int i = 2; i <= rows.size(); i++) {

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "QA"));

			String sVal = Integer.toString(i);

			sReviewSegValue = oSeleniumUtils
					.get_TextFrom_Locator(StringUtil.replace(oHomePage.QA_Review_Table_Item, "sValue", sVal));

			// if (!(sReviewSegValue == "Editorials")) {
			if (!(sReviewSegValue.equalsIgnoreCase("Editorials"))) {

				oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.QA_Review_Table_Item, "sValue", sVal));

				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			}

			switch (sReviewSegValue) {
			case "BW And/BWO Or Logic":
				oGenericUtils.gfn_Verify_Object_Exist("h3", "Billed With And/Billed Without Or Code Clarification");

				if (!(oHomePage.NewBilled_Radio_button.isSelected())) {

					oSeleniumUtils.Click_given_WebElement(oHomePage.NewBilled_Radio_button);
				}
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				oSeleniumUtils.Click_given_Locator(oHomePage.Complete_QA_Review_Sub);
				userClickOnReviewSuccessMsg(sReviewSegValue);
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

				break;

			case "Impact Code List":

				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

				int icount = getDriver().findElements(By.xpath(oHomePage.ImpactReviewCodeList)).size();

				if (icount > 0) {

					if (!(oHomePage.ImpactChkName.isSelected())) {
						oSeleniumUtils.highlightElement(oHomePage.ImpactReviewCodeAll);
						oSeleniumUtils.Click_given_WebElement(oHomePage.ImpactReviewCodeAll);
						SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
					} else {
						System.out.println("Radio button is not availble");
					}
				}

				oSeleniumUtils.highlightElement(oHomePage.Complete_QA_Review);
				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
				if (icount > 0) {
					oSeleniumUtils.Click_given_Locator(oHomePage.QAReview_Category);
				}
				oSeleniumUtils.highlightElement(oHomePage.Complete_QA_Review);
				oSeleniumUtils.highlightElement(oHomePage.Complete_QA_Review);
				oSeleniumUtils.Click_given_Locator(oHomePage.Complete_QA_Review);
				userClickOnReviewSuccessMsg(sReviewSegValue);

				oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "QA"));

				break;

			case "Potential Conflicts":
			case "Retire Rule - System Determined":

				oSeleniumUtils.Click_given_Locator(oHomePage.Complete_QA_Review);
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				int iMsg = getDriver()
						.findElements(By
								.xpath(StringUtil.replace(oHomePage.pTag, "sValue", " Review completed successfully.")))
						.size();

				if (iMsg > 0) {

					userClickOnReviewSuccessMsg(sReviewSegValue);
				}

				break;

			default:

				if (!(sReviewSegValue.equalsIgnoreCase("Editorials"))) {
					oSeleniumUtils.Click_given_Locator(oHomePage.Complete_QA_Review);
					userClickOnReviewSuccessMsg(sReviewSegValue);
					oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "QA"));
					break;
				}

			}

		}

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "QA"));

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.AnchorTag, "sValue", "Editorials"));

		SeleniumUtils.switchWindowUsingWindowCount(5, 2, getDriver());

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(oHomePage.Complete_QA_Review_Editorial);

		userClickOnReviewSuccessMsg(sReviewSegValue);

		// getDriver().close(); // Close current page

		SeleniumUtils.switchWindowUsingWindowCount(3, 1, getDriver());

		getDriver().switchTo().defaultContent();

		// update_QA_Rule();

	}

	@Step
	public void userClickOnReviewSuccessMsg(String sReviewSegValue) throws InterruptedException {

		oGenericUtils.gfn_Verify_Object_Exist("p", " Review completed successfully.");

		Assert.assertTrue("Review completed Successfully", oSeleniumUtils.is_WebElement_Displayed(
				StringUtil.replace(oHomePage.pTag, "sValue", ProjectVariables.Review_Success)));

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		verify("Review Completed and Segement Name is  :" + sReviewSegValue, true);

	}

	@Step
	public void completeAllTestingReviews() throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oGenericUtils.gfn_Verify_Object_Exist("span", "BRAT Test Results");

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Test"));

		String sReviewSegValue = null;

		List<WebElement> rows = getDriver().findElements(By.xpath(oHomePage.Testing_Review_Table));

		for (int i = 2; i <= rows.size(); i++) {

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Test"));

			// String sVal = Integer.toString(i);

			sReviewSegValue = oSeleniumUtils
					.get_TextFrom_Locator(oHomePage.GetDynamicXPath("TESTINGREVIEW TABLEITEM", i));
			System.out.println(sReviewSegValue);
			// if (!(sReviewSegValue == "Editorials")) {
			if (!(sReviewSegValue.equalsIgnoreCase("Editorials"))) {

				oSeleniumUtils.Click_given_Locator(oHomePage.GetDynamicXPath("TESTINGREVIEW TABLEITEM", i));
				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			}

			switch (sReviewSegValue) {
			case "BW And/BWO Or Logic":
				oGenericUtils.gfn_Verify_Object_Exist("h3", "Billed With And/Billed Without Or Code Clarification");

				if (!(oHomePage.NewBilled_Radio_button.isSelected())) {

					oSeleniumUtils.Click_given_WebElement(oHomePage.NewBilled_Radio_button);
				}
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				oGenericUtils.gfn_Click_On_Object("label", "Complete Testing Review");

				userClickOnReviewSuccessMsg(sReviewSegValue);
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

				break;

			case "Impact Code List":

				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

				int icount = getDriver()
						.findElements(By
								.xpath("//div[@id='jqgh_impactCodeListGrid_testingReview10']//input[@id='impactSelectAll']"))
						.size();

				if (icount > 0) {

					if (!(oHomePage.ImpactChkboxTesting.isSelected())) {
						oSeleniumUtils.highlightElement(oHomePage.TestingImpactReviewCodeAll);
						oSeleniumUtils.Click_given_WebElement(oHomePage.TestingImpactReviewCodeAll);
						SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
					} else {
						System.out.println("Radio button is not availble");
					}
				}

				oSeleniumUtils.highlightElement(oHomePage.Complete_Test_Review);
				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
				oSeleniumUtils.Click_given_Locator(oHomePage.QAReview_Category);
				oSeleniumUtils.highlightElement(oHomePage.Complete_Test_Review);
				oSeleniumUtils.Click_given_Locator(oHomePage.Complete_Test_Review);
				userClickOnReviewSuccessMsg(sReviewSegValue);
				oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Test"));

				break;

			default:

				if (!(sReviewSegValue.equalsIgnoreCase("Editorials"))) {
					oSeleniumUtils.Click_given_Locator(oHomePage.Complete_Test_Review);
					userClickOnReviewSuccessMsg(sReviewSegValue);
					oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Test"));
					break;
				}

			}

		}

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Test"));

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.AnchorTag, "sValue", "Editorials"));

		SeleniumUtils.switchWindowUsingWindowCount(5, 2, getDriver());

		// oHomePage.wait_Untill_ElementExists(oHomePage.Complete_QA_Review);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oGenericUtils.gfn_Click_String_object_Xpath(
				oHomePage.GetDynamicXPathWithString("CONTAINS LABEL", "Complete Testing Review", ""));

		userClickOnReviewSuccessMsg(sReviewSegValue);

		getDriver().close(); // Close current page

		SeleniumUtils.switchWindowUsingWindowCount(3, 1, getDriver());

		getDriver().switchTo().defaultContent();

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);
		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "QA"));
		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Test"));

		oGenericUtils.gfn_Click_String_object_Xpath(
				oHomePage.GetDynamicXPathWithString("CONTAINS LABEL", "Testing Complete", ""));
		oGenericUtils.gfn_Verify_Object_Exist("h3", "Testing Completed");
		oSeleniumUtils.Enter_given_Text_Element(oHomePage.TestingCompleteComments, "Test Reviews");
		oGenericUtils.gfn_Click_On_Object("button", "Testing Completed");
		oGenericUtils.gfn_Verify_Object_Exist("h3", "WARNING");
		oGenericUtils.gfn_Verify_Object_Exist("p", "Are you sure this RMR for the rule should be Closed? Continue?");
		oGenericUtils.gfn_Click_On_Object("button", "Yes");
		oGenericUtils.gfn_Verify_Object_Exist("h3", "Testing Complete");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		getDriver().switchTo().defaultContent();

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

	}

	@Step
	public void completeCPMReview(String sAgreePayer) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		// Click on Config is not disabled
		String status_Config_Btn = getDriver().findElement(By.xpath(oHomePage.Config)).getAttribute("disabled");
		System.out.println(status_Config_Btn);

		// if (!((status_Config_Btn).equalsIgnoreCase("true"))) {
		if (status_Config_Btn == null) {
			oSeleniumUtils.Click_given_Locator(oHomePage.Config);
			oGenericUtils.gfn_Verify_Object_Exist("h4", "CPM Additional Configuration Approval");
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			List<WebElement> CPM_Decesion_List = getDriver()
					.findElements(By.xpath(StringUtil.replace(oHomePage.tdTag, "sValue", "Select")));
			System.out.println(CPM_Decesion_List.size());

			if (CPM_Decesion_List.size() <= 4) {
				for (int i = 0; i < CPM_Decesion_List.size(); i++) {
					CPM_Decesion_List.get(i).click();
					oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
							oHomePage.GetDynamicXPath("CPM_PAYERS_DECESION", i + 1), sAgreePayer);
					oSeleniumUtils.Enter_given_Text_Element(oHomePage.GetDynamicXPath("CPM_DECESION_COMMENTS", i + 1),
							"CPM Config Comments");
				}
			} else {
				oSeleniumUtils.Click_given_Locator(oHomePage.CPMPayerTreePlus);

				List<WebElement> CPM_Decesion = getDriver()
						.findElements(By.xpath(StringUtil.replace(oHomePage.tdTag, "sValue", "Select")));
				for (int j = 1; j < CPM_Decesion.size(); j++) {

					System.out.println(CPM_Decesion.size());
					CPM_Decesion.get(j).click();

				}

				List<WebElement> CPM_Decesion_Dropdown = getDriver()
						.findElements(By.xpath(("//select[@name='agree10']")));
				for (int k = 1; k <= CPM_Decesion_Dropdown.size(); k++) {

					oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
							oHomePage.GetDynamicXPath("CPM_PAYERS_DECESION", k), sAgreePayer);
					oSeleniumUtils.Enter_given_Text_Element(oHomePage.GetDynamicXPath("CPM_DECESION_COMMENTS", k),
							"CPM Config Comments");

				}
			}

			oGenericUtils.gfn_Click_On_Object("button", "CPM Decision Complete");
			oGenericUtils.gfn_Verify_Object_Exist("p", " Successfully saved additional config approvals.");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			getDriver().switchTo().defaultContent();
			oSeleniumUtils.SwitchToFrame(oHomePage.frame);
		}

		getDriver().switchTo().defaultContent();

		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
		oSeleniumUtils.SwitchToFrame(oHomePage.frame);
		oSeleniumUtils.Click_given_Locator((StringUtil.replace(oHomePage.LabelTag, "sValue", "CPM Decision Complete")));

		oGenericUtils.gfn_Click_On_Object("span", "OK");
		oGenericUtils.gfn_Verify_Object_Exist("p", " CPM Decisions completed successfully");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		if (sAgreePayer.equalsIgnoreCase("RetireRule")) {
			// String status_CPM_Decision_Btn =
			// getDriver().findElement(By.xpath(StringUtil.replace(oHomePage.LabelTag,
			// "sValue", "Retire Rule"))).getAttribute("disabled");

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.LabelTag, "sValue", "Retire Rule"));
			oGenericUtils.gfn_Click_On_Object("button", "CPM Decision Complete");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");

		}

		getDriver().switchTo().defaultContent();
		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
	}

	@Step
	public void retireRuleCPM() {

		String status_CPM_Decision_Btn = getDriver()
				.findElement(By.xpath(StringUtil.replace(oHomePage.LabelTag, "sValue", "Retire Rule")))
				.getAttribute("disabled");
		if (((status_CPM_Decision_Btn) == "true")) {

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.LabelTag, "sValue", "Retire Rule"));
			oSeleniumUtils
					.Click_given_Locator(StringUtil.replace(oHomePage.LabelTag, "sValue", "CPM Decision Complete"));

		}

	}

	@Step
	public void authorize_CPM_Decisions() {

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.AnchorTag, "sValue", "CPM Summaries"));

		authorizeDecisions();

	}

	@Step
	public void update_QA_Rule() throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Summaries"));
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "QA"));
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.highlightElement(oHomePage.Update_Rule);
		oSeleniumUtils.Click_given_Locator(oHomePage.Update_Rule);

		oGenericUtils.gfn_Verify_String_Object_Exist(oHomePage.Msg_Rule_Update);
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		Assert.assertTrue("Rule updated Successfully",
				oSeleniumUtils.is_WebElement_Displayed(oHomePage.Msg_Rule_Update));
		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Ok"));

		oSeleniumUtils.highlightElement(oHomePage.Stub_RMR_ID);
		Serenity.setSessionVariable("Stub_RMR_ID").to(oHomePage.Stub_RMR_ID.getText());
		System.out.println(Serenity.sessionVariableCalled("Stub_RMR_ID").toString());

		oSeleniumUtils.highlightElement(oHomePage.QA_Review_Completed);
		oSeleniumUtils.Click_given_Locator(oHomePage.QA_Review_Completed);

		oGenericUtils.gfn_Verify_Object_Exist("p", " QA Review Completed");
		Assert.assertTrue("Rule updated Successfully", oSeleniumUtils
				.is_WebElement_Displayed(StringUtil.replace(oHomePage.pTag, "sValue", " QA Review Completed")));
		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Ok"));

		getDriver().switchTo().defaultContent();

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

	}

	@Step
	public void selectRule(String arg1) throws Exception {


		Serenity.setSessionVariable("MidRuleVersion").to("9392.31");
		
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		boolean blnFlg = false;

		switch (arg1) {

		case "RuleID":

			verify("MidRule Version is :" + Serenity.sessionVariableCalled("MidRuleVersion").toString(), true);

//			blnFlg = oSeleniumUtils.is_WebElement_Displayed(
//					"//a[text()='" + Serenity.sessionVariableCalled("MidRuleVersion").toString() + "']");
//
//			if (blnFlg == false) {
//
//				oHomePage.go_To_Rule(Serenity.sessionVariableCalled("MidRuleVersion").toString());
//
//			}
			
			oHomePage.go_To_Rule(Serenity.sessionVariableCalled("MidRuleVersion").toString());
			
			if (getDriver().findElements(By.xpath("//a[text()='"+Serenity.sessionVariableCalled("MidRuleVersion").toString()+"']")).size() > 0) {
				verify("Rule is displayed", true);
			}else{
				verify("Rule is displayed in Review Screen" +Serenity.sessionVariableCalled("MidRuleVersion").toString(), false);
			}

//			verify("Rule is displayed", oGenericUtils.gfn_Verify_Object_Exist("a",
//					Serenity.sessionVariableCalled("MidRuleVersion").toString()));

			blnFlg = oGenericUtils.gfn_Click_On_Object("a",Serenity.sessionVariableCalled("MidRuleVersion").toString());

			verify("Rule is clicked" + Serenity.sessionVariableCalled("MidRuleVersion").toString(), blnFlg);

			oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			blnFlg = oGenericUtils.gfn_Verify_String_Object_Exist(
					"//label[text()='Rule Version: ']/../../following::td//b[contains(text(),'"
							+ Serenity.sessionVariableCalled("MidRuleVersion").toString() + "')]");

			verify("Rule Version is displayed above Start Review", blnFlg);

			break;

		case "RuleID-Admin":

			verify("MidRule Version is :" + Serenity.sessionVariableCalled("MidRuleVersion").toString(), true);

			oHomePage.go_To_Rule(Serenity.sessionVariableCalled("MidRuleVersion").toString());

			break;
		case "First-RuleID":

			verify("MidRule Version is :" + Serenity.sessionVariableCalled("MidRuleVersion").toString(), true);

			oHomePage.go_To_Rule(Serenity.sessionVariableCalled("MidRuleVersion").toString());

			oGenericUtils
					.gfn_Click_String_object_Xpath("(//div[@class='ui-chkbox-box ui-widget ui-state-default'])[1]");

			break;
		case "RuleID-Not Displayed":

			verify("MidRule Version is :" + Serenity.sessionVariableCalled("MidRuleVersion").toString(), true);

			oHomePage.go_To_Rule(Serenity.sessionVariableCalled("MidRuleVersion").toString());

			blnFlg = !oSeleniumUtils.is_WebElement_Displayed(
					"//a[text()='" + Serenity.sessionVariableCalled("MidRuleVersion").toString() + "']");

			verify("Rule id is not displayed", blnFlg);
			break;
		case "RandomRule-AllWorkQueue":

			ArrayList<String> oRules = new ArrayList<String>();

			String[] oRuleslist = oSeleniumUtils
					.get_All_Text_from_Locator("//div[@class='ui-table-scrollable-view']//a");

			for (int i = 0; i < oRuleslist.length; i++) {

				oRules.add(oRuleslist[i]);

			}

			Serenity.setSessionVariable("iRandomRule")
					.to(GenericUtils.generate_Random_Number_for_Given_Range(oRules.size()));

			String sMidRule = oRules.get(Serenity.sessionVariableCalled("iRandomRule"));

			oHomePage.go_To_Rule(sMidRule);

			verify("Rule is displayed", oGenericUtils.gfn_Verify_Object_Exist("a", sMidRule));

			blnFlg = oGenericUtils.gfn_Click_On_Object("a", sMidRule);

			verify("Rule is clicked" + sMidRule, blnFlg);

			oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			blnFlg = oGenericUtils.gfn_Verify_String_Object_Exist(
					"//label[text()='Rule Version: ']/../../following::td//b[contains(text(),'" + sMidRule + "')]");

			verify("Rule Version is displayed above Start Review", blnFlg);

			break;
		case "RuleID-Random":

			String sRuleVersion = null;

			sRuleVersion = Serenity.sessionVariableCalled("NonCandidateRule").toString() + "."
					+ Serenity.sessionVariableCalled("NonCandidateRuleVersion").toString();

			// sRuleVersion="7832.37";

			oHomePage.go_To_Rule(sRuleVersion);

			verify("Rule is displayed", oGenericUtils.gfn_Verify_Object_Exist("a", sRuleVersion));

			blnFlg = oGenericUtils.gfn_Click_On_Object("a", sRuleVersion);

			verify("Rule is clicked" + sRuleVersion, blnFlg);

			oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			break;
		case "Rule Review Request":
		case "Rule Review Request-Non Candidates":

			oGenericUtils.gfn_Click_On_Object("button", " Rule Review Request");

			oGenericUtils.gfn_Verify_Object_Exist("h2", StringUtils.substringBefore(arg1, "-").trim());

			oSeleniumUtils.Enter_given_Text_Element("//label[text()='Enter rule for proposal assesment:']/../..//input",
					Serenity.sessionVariableCalled("MidRuleVersion").toString());

			oGenericUtils.gfn_Click_On_Object("button", "Request Rule Review");

			oGenericUtils.gfn_Verify_Object_Exist("h4", "Request Rule Review");

			String sInstance = Serenity.sessionVariableCalled("IUInstanceName").toString();

			String sRule = Serenity.sessionVariableCalled("MidRuleVersion").toString();

			String sTask = null;

			String sStatus = null;

			String sAssignee = null;

			String sType = null;

			if (arg1.equalsIgnoreCase("Rule Review Request-Non Candidates")) {

				sType = "0";

			} else {

				sType = "-1";
			}

			String sb = "SELECT U.USER_ID,TTL.TASK_TYPE_DESC,TSL.TASK_STATUS_DESC\r\n" + "FROM IRDM.INTERP_RULES IR\r\n"
					+ "JOIN IRDM.INTERP_RULE_DETAILS IRD\r\n" + "ON IR.INTERP_RULE_KEY = IRD.INTERP_RULE_KEY\r\n"
					+ "JOIN IPDE.TASK_DETAILS TD ON TD.REFERENCE_RULE_ID = IR.INTERP_RULE_KEY\r\n"
					+ "JOIN IPDE.TASK_TYPE_LKP TTL ON TTL.TASK_TYPE_KEY = TD.TASK_TYPE_KEY\r\n"
					+ "JOIN IPDE.TASK_STATUS_LKP TSL\r\n" + "ON TSL.TASK_STATUS_KEY = TD.TASK_STATUS_KEY\r\n"
					+ "JOIN IPDE.USERS U ON U.USER_KEY = TD.TASK_USER_KEY\r\n"
					+ "WHERE IR.IMPACT_KEY IN (SELECT IMPACT_KEY\r\n" + "FROM IRDM.INTERP_IMPACTS II\r\n"
					+ "JOIN IRDM.UPDATE_INSTANCES I\r\n" + "ON I.UPDATE_INSTANCE_KEY =\r\n"
					+ "II.UPDATE_INSTANCE_KEY\r\n" + "WHERE UPDATE_INSTANCE_NAME = '" + sInstance + "')\r\n"
					+ "AND IR.CANDIDATE_10 = '" + sType + "'\r\n" +
					// "AND LIBRARY_STATUS_DESC ='Custom'\r\n" +
					"AND IRD.MID_RULE_DOT_VERSION ='" + sRule + "'\r\n";

			System.out.println(sb);

			sTask = DBUtils.db_GetFirstValueforColumn(sb, "TASK_TYPE_DESC");

			sStatus = DBUtils.db_GetFirstValueforColumn(sb, "TASK_STATUS_DESC");

			sAssignee = DBUtils.db_GetFirstValueforColumn(sb, "USER_ID");

			String sText = oSeleniumUtils.get_TextFrom_Locator("//span[contains(text(),'(" + sRule + ")')]");

			boolean bln;

			String sMsg;

			if (arg1.equalsIgnoreCase("Rule Review Request-Non Candidates")) {

				sMsg = "The selected rule (" + sRule
						+ ") is already part of the current update instance, however it has been designated as a non-candidate. Please use the 'Retrieve Non-Candidate Rules' action to include this rule in the review.";

				bln = sMsg.equalsIgnoreCase(sText);

				verify("Non Candidate msg is not matched", bln);

			} else {

				sMsg = "The selected rule (" + sRule + ") is already part of the current update instance";

				bln = sMsg.equalsIgnoreCase(sText);

				verify("Candidate msg is not matched", bln);

				oGenericUtils.gfn_Verify_Object_Exist("span", "Task: " + sTask + "");

				oGenericUtils.gfn_Verify_Object_Exist("span", "Status: " + sStatus + "");

				oGenericUtils.gfn_Verify_Object_Exist("span", "Assignee: " + sAssignee + "");
			}

			System.out.println(sText);

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			break;
		case "Rule Review Request-InvalidInput":

			oGenericUtils.gfn_Click_On_Object("button", " Rule Review Request");

			oGenericUtils.gfn_Verify_Object_Exist("h2", StringUtils.substringBefore(arg1, "-").trim());

			oSeleniumUtils.Enter_given_Text_Element("//label[text()='Enter rule for proposal assesment:']/../..//input",
					"asdfasdf");

			oGenericUtils.gfn_Click_On_Object("button", "Request Rule Review");

			String sErrorText = "Please enter a valid rule, e.g.\"1234.5\"";

			verify("Error Prompt after entering invalid data",
					oGenericUtils.gfn_Verify_Object_Exist("div", sErrorText));
			;

			oGenericUtils.gfn_Click_On_Object("button", "Cancel");

			break;

		}

	}

	@Step
	public void ruleIDSelected(String arg1) {

	}

	@Step
	public void enterRuleNo(String sRuleNo) throws InterruptedException {

		oHomePage.go_To_Rule(sRuleNo);

		oGenericUtils.gfn_Click_String_object_Xpath(StringUtils.replace(oHomePage.AnchorTag, "sValue", sRuleNo));

		verify("MidRule Version is :" + sRuleNo, true);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
	}

	@Step
	public void verify(String sDescription, boolean blnStatus) {

		GenericUtils.Verify(sDescription, blnStatus);
	}

	public void validateColumnStatus(String sColumnName, String sColumnValue) {

		String sValue = oHomePage.get_Column_Value(sColumnName);

		boolean sStatus = sColumnValue.equalsIgnoreCase(sValue);

		verify("Column Value Expected :" + sColumnValue + " and Actual is " + sValue, sStatus);
	}

	@Step
	public void clickInstanceinAdmin(String sInstancetype) throws InterruptedException {

		Serenity.setSessionVariable("AdminInstance").to(sInstancetype);

		String sInstance = null;

		switch (sInstancetype.trim()) {
		case "FINAL PO DEL":
			sInstance = ProjectVariables.FinalMDInstance;
			verify("Final MD Del Instance Name: " + sInstance, true);
			Serenity.setSessionVariable("AdminInstance").to(sInstance);
			break;

		case "FINAL MD SIM":
			sInstance = ProjectVariables.FinalMDInstance;
			break;

		case "PRELIM PO DEL":
			sInstance = ProjectVariables.PrelimMDInstance;
			verify("Prelim MD Del Instance Name: " + sInstance, true);
			break;

		case "FINAL MD DEL-Subsequent":
			sInstance = ProjectVariables.FinalMDInstanceSubsequent;
			verify("Prelim MD Del Instance Name: " + sInstance, true);
			break;

		case "AE PRELIM PO DEL":
			sInstance = ProjectVariables.AEPrelimInstance;
			break;
		}

		oSeleniumUtils.Click_given_Locator(StringUtils.replace(oHomePage.AdminInstance, "sValue", sInstance));

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.Actions);

		// oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag,
		// "sValue", "Rule Review"));

		oGenericUtils.gfn_Click_On_Object("span", "Rule Review");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		// verify("Rules count is displaying at the side of pagination in
		// Admin",
		// oGenericUtils.gfn_Verify_String_Object_Exist("(//div[contains(text(),'Displaying
		// 1')])[2]"));

	}

	@Step
	public void getRuleFromWorkQueueForInstance(String sWorkQueue, String sInstance) throws InterruptedException {

		switch (sWorkQueue) {

		case "EditorialWorkQueue":

			Serenity.setSessionVariable("EditorialRuleVersion")
					.to(oSeleniumUtils.get_TextFrom_Locator(oHomePage.RuleVersionTxt));

			System.out.println(
					"Editorial Rule Version: " + Serenity.sessionVariableCalled("EditorialRuleVersion").toString());

			ruleIDSelected(Serenity.sessionVariableCalled("EditorialRuleVersion").toString());

			break;

		case "QAWorkQueue":

			Serenity.setSessionVariable("QARuleVersion")
					.to(oSeleniumUtils.get_TextFrom_Locator(oHomePage.RuleVersionTxt));

			System.out.println("QA Rule Version: " + Serenity.sessionVariableCalled("QARuleVersion").toString());

			ruleIDSelected(Serenity.sessionVariableCalled("QARuleVersion").toString());

			break;

		case "CPMWorkQueue":

			Serenity.setSessionVariable("CPMRuleVersion")
					.to(oSeleniumUtils.get_TextFrom_Locator(oHomePage.RuleVersionTxt));

			System.out.println("CPM Rule Version: " + Serenity.sessionVariableCalled("CPMRuleVersion").toString());

			ruleIDSelected(Serenity.sessionVariableCalled("CPMRuleVersion").toString());

			break;

		case "TestingWorkQueue":

			Serenity.setSessionVariable("TestingRuleVersion")
					.to(oSeleniumUtils.get_TextFrom_Locator(oHomePage.RuleVersionTxt));

			System.out.println(
					"Testing Rule Version: " + Serenity.sessionVariableCalled("TestingRuleVersion").toString());

			ruleIDSelected(Serenity.sessionVariableCalled("TestingRuleVersion").toString());

			break;

		default:
			Assert.assertTrue("Case Value is not matched for function getRuleFromWorkQueueForInstance", false);

		}

		oHomePage.closeAllTabs();

		userNavigateToIUInstancesScreen("MyTasks");

	}

	@Step
	public void veifyRuleInWorkQueueForInstance(String sWorkQueue, String sInstance) throws InterruptedException {

		oHomePage.closeAllTabs();

		switch (sWorkQueue) {

		case "EditorialWorkQueue":

			oMyTasksStepdef.clickTaskByInstanceName("Editorial Review Work Queue", sInstance);

			oHomePage.go_To_Rule(Serenity.sessionVariableCalled("EditorialRuleVersion").toString());

			oGenericUtils.gfn_Verify_Object_Exist("div",
					Serenity.sessionVariableCalled("EditorialRuleVersion").toString());

			ruleIDSelected(Serenity.sessionVariableCalled("EditorialRuleVersion").toString());

			Assert.assertTrue("EditorialWorkQueue Rule Version is as expected after releasing the rules",
					Serenity.sessionVariableCalled("EditorialRuleVersion").toString()
							.equalsIgnoreCase(oSeleniumUtils.get_TextFrom_Locator(StringUtils.replace(oHomePage.DivTag,
									"sValue", Serenity.sessionVariableCalled("EditorialRuleVersion").toString()))));

			oGenericUtils.gfn_Click_On_Object("div", Serenity.sessionVariableCalled("EditorialRuleVersion").toString());

			break;

		case "QAWorkQueue":

			oMyTasksStepdef.clickTaskByInstanceName("QA Review Work Queue", sInstance);

			oHomePage.go_To_Rule(Serenity.sessionVariableCalled("QARuleVersion").toString());

			oGenericUtils.gfn_Verify_Object_Exist("div", Serenity.sessionVariableCalled("QARuleVersion").toString());

			ruleIDSelected(Serenity.sessionVariableCalled("QARuleVersion").toString());

			Assert.assertTrue("QAWorkQueue Rule Version is as expected after releasing the rules",
					Serenity.sessionVariableCalled("QARuleVersion").toString()
							.equalsIgnoreCase(oSeleniumUtils.get_TextFrom_Locator(StringUtils.replace(oHomePage.DivTag,
									"sValue", Serenity.sessionVariableCalled("QARuleVersion").toString()))));

			oGenericUtils.gfn_Click_On_Object("div", Serenity.sessionVariableCalled("QARuleVersion").toString());
			break;

		case "CPMWorkQueue":

			oMyTasksStepdef.clickTaskByInstanceName("CPM Review Work Queue", sInstance);

			oHomePage.go_To_Rule(Serenity.sessionVariableCalled("CPMRuleVersion").toString());

			oGenericUtils.gfn_Verify_Object_Exist("div", Serenity.sessionVariableCalled("CPMRuleVersion").toString());

			ruleIDSelected(Serenity.sessionVariableCalled("CPMRuleVersion").toString());

			Assert.assertTrue("CPMorkQueue Rule Version is as expected after releasing the rules",
					Serenity.sessionVariableCalled("CPMRuleVersion").toString()
							.equalsIgnoreCase(oSeleniumUtils.get_TextFrom_Locator(StringUtils.replace(oHomePage.DivTag,
									"sValue", Serenity.sessionVariableCalled("CPMRuleVersion").toString()))));

			oGenericUtils.gfn_Click_On_Object("div", Serenity.sessionVariableCalled("CPMRuleVersion").toString());
			break;

		case "TestingWorkQueue":

			oMyTasksStepdef.clickTaskByInstanceName("Testing Review Work Queue", sInstance);

			oHomePage.go_To_Rule(Serenity.sessionVariableCalled("TestingRuleVersion").toString());

			oGenericUtils.gfn_Verify_Object_Exist("div",
					Serenity.sessionVariableCalled("TestingRuleVersion").toString());

			ruleIDSelected(Serenity.sessionVariableCalled("TestingRuleVersion").toString());

			Assert.assertTrue("TestingWorkQueue Rule Version is as expected after releasing the rules",
					Serenity.sessionVariableCalled("TestingRuleVersion").toString()
							.equalsIgnoreCase(oSeleniumUtils.get_TextFrom_Locator(StringUtils.replace(oHomePage.DivTag,
									"sValue", Serenity.sessionVariableCalled("TestingRuleVersion").toString()))));

			oGenericUtils.gfn_Click_On_Object("div", Serenity.sessionVariableCalled("TestingRuleVersion").toString());

			break;

		default:
			Assert.assertTrue("Case Value is not matched for function veifyRuleInWorkQueueForInstance", false);

		}

	}

	@Step
	public void selectRuleWithMultiPayer(String sPayerQuantity) throws InterruptedException {

		ArrayList<String> sArrPayers = new ArrayList<String>();

		Serenity.setSessionVariable("PayerShots").to(sArrPayers);

		int sPayersQuantity = 0;

		System.out
				.println("Rule Version no in Multipayer" + Serenity.sessionVariableCalled("MidRuleVersion").toString());

		switch (sPayerQuantity) {

		case "TWOCPMPAYERSDB":
		case "ONECPMPAYERSDB":
		case "ONECPMOBSOLETE":
		case "MULTIPAYERSINGLECPM":

			/*
			 * String sQuery =
			 * "select PAYERS_4_RULE from IRDM.interp_rule_details where   MID_RULE_DOT_VERSION='"
			 * + Serenity.sessionVariableCalled("MidRuleVersion").toString() +
			 * "' and INTERP_RULE_KEY in" +
			 * "(select interp_rule_key from IRDM.interp_rules where impact_key in "
			 * +
			 * "(select impact_key from irdm.interp_impacts where update_instance_key ="
			 * +
			 * "(select update_instance_key from IRDM.update_instances where update_instance_name ='"
			 * + Serenity.sessionVariableCalled("IUInstanceName").toString() +
			 * "')))";
			 */

			String sQuery = "WITH CTE  AS (  SELECT IR.SUB_RULE_KEY,  IRD.MID_RULE_DOT_VERSION,   IRD.LIBRARY_STATUS_DESC, PAYERS_4_RULE,IRD.ARD_EXISTS_YN, TTL.TASK_TYPE_DESC, TSL.TASK_STATUS_DESC, U.USER_ID,  IRDM.CLIST (UGL.UPDATE_GROUP_NAME) PROPOSAL_TYPE                          FROM IRDM.INTERP_RULES IR                   JOIN IRDM.INTERP_RULE_DETAILS IRD                      ON IR.INTERP_RULE_KEY = IRD.INTERP_RULE_KEY                   JOIN IPDE.TASK_DETAILS TD                      ON TD.REFERENCE_RULE_ID = IR.INTERP_RULE_KEY                   JOIN IPDE.TASK_TYPE_LKP TTL                      ON TTL.TASK_TYPE_KEY = TD.TASK_TYPE_KEY                   JOIN IPDE.TASK_STATUS_LKP TSL                      ON TSL.TASK_STATUS_KEY = TD.TASK_STATUS_KEY                   JOIN IPDE.USERS U ON U.USER_KEY = TD.TASK_USER_KEY                   JOIN IRDM.INTERP_RULE_ICD IRI                      ON IRI.INTERP_RULE_KEY = IR.INTERP_RULE_KEY                   JOIN IRDM.INTERP_ICD_SOURCES IIS                      ON IIS.INTERP_RULE_ICD_KEY = IRI.INTERP_RULE_ICD_KEY                   JOIN IRDM.UPDATE_GROUP_LKP UGL                      ON UGL.UPDATE_GROUP_KEY = IRI.UPDATE_GROUP_KEY             WHERE     IR.IMPACT_KEY IN (SELECT IMPACT_KEY                                           FROM IRDM.INTERP_IMPACTS II                                                JOIN IRDM.UPDATE_INSTANCES I                                                   ON I.UPDATE_INSTANCE_KEY =                                                         II.UPDATE_INSTANCE_KEY                                          \r\n"
					+ "WHERE UPDATE_INSTANCE_NAME = '" + Serenity.sessionVariableCalled("IUInstanceName").toString()
					+ "') GROUP BY IR.SUB_RULE_KEY,   IRD.MID_RULE_DOT_VERSION,   IRD.LIBRARY_STATUS_DESC, IRD.ARD_EXISTS_YN,  TTL.TASK_TYPE_DESC, PAYERS_4_RULE,               \r\n"
					+ "             TSL.TASK_STATUS_DESC,  U.USER_ID) SELECT PAYERS_4_RULE FROM CTE    where MID_RULE_DOT_VERSION ='"
					+ Serenity.sessionVariableCalled("MidRuleVersion").toString() + "'\r\n";

			System.out.println("Query is : " + sQuery);

			String sPayerList = DBUtils.executeSQLQuery(sQuery);
			System.out.println("Query Status: " + sPayerList);

			String[] ArrPayers = sPayerList.split(",");

			for (int i = 0; i < ArrPayers.length; i++) {
				String sAllPayers = ArrPayers[i].trim().split(" ")[0];
				System.out.println(sAllPayers);

				sArrPayers.add(sAllPayers);
			}

			if (sPayerQuantity.equalsIgnoreCase("TWOCPMPAYERSDB")) {

				sPayersQuantity = 2;
			}

			if (sPayerQuantity.equalsIgnoreCase("ONECPMPAYERSDB")) {

				sPayersQuantity = 1;
			}

			if (sPayerQuantity.equalsIgnoreCase("ONECPMOBSOLETE")) {

				sPayersQuantity = 1;
			}

			if (sPayerQuantity.equalsIgnoreCase("MULTIPAYERSFIRSTCPM")) {

				sPayersQuantity = 1;
			}

			if (sPayerQuantity.equalsIgnoreCase("MULTIPAYERSINGLECPM")) {

				sPayersQuantity = 11;
			}

			break;

		case "MULTIPAYERSFIRSTCPM":
			break;

		default:

			if (sPayerQuantity.equalsIgnoreCase("2")) {

				sPayersQuantity = 2;
			}

			if (sPayerQuantity.equalsIgnoreCase("1")) {

				sPayersQuantity = 1;
			}

			if (sPayerQuantity.equalsIgnoreCase("11")) {

				sPayersQuantity = 11;
			}

			sPayersQuantity = Integer.parseInt(sPayerQuantity);
			System.out.println(sPayersQuantity);

			List<WebElement> sRowCount = getDriver()
					.findElements(By.xpath("(//table[@class = 'GEFT4QHBI3C'])[last()]/tbody[2]/tr"));

			for (int i = 0; i < sRowCount.size(); i++) {

				String sXPayer = oHomePage.GetDynamicXPath("RULE PAYERS", i + 1);
				String sPayer = getDriver().findElement(By.xpath(sXPayer)).getText();
				String[] ArrPayers2 = sPayer.split(",");

				if (ArrPayers2.length > sPayersQuantity) {

					String sXRule = oHomePage.GetDynamicXPath("RULE VERSION", i + 1);
					String sRule = getDriver().findElement(By.xpath(sXRule)).getText();
					Serenity.setSessionVariable("MidRuleVersion").to(sRule);

					verify("MidRule Version is :" + Serenity.sessionVariableCalled("MidRuleVersion").toString(), true);
					System.out.println("Rule No : " + Serenity.sessionVariableCalled("MidRuleVersion").toString());

					for (int j = 0; j < ArrPayers2.length; j++) {
						String sAllPayers2 = ArrPayers2[j].trim().split(" ")[0];
						System.out.println(sAllPayers2);

						sArrPayers.add(sAllPayers2);

					}
					break;

				}
			}

			break;
		}

		oHomePage.userNavigateToAdministrationTab();

		oSeleniumUtils
				.Click_given_Locator(StringUtil.replace(oHomePage.AnchorTag, "sValue", "Assignment Configuration"));
		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		if ((oSeleniumUtils.is_WebElement_Present(StringUtil.replace(oHomePage.DivTag, "sValue", "OK")))) {
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.DivTag, "sValue", "OK"));
		}

		oGenericUtils.gfn_Click_On_Object("span", "CPM Payer Assignments");
		// oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag,
		// "sValue", "CPM Payer Assignments"));
		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		switch (sPayersQuantity) {

		case 2:
			for (int k = 0; k < sArrPayers.size(); k++) {
				System.out.println(sArrPayers.get(k));

				if (k == 0) {
					oHomePage.assignDifferentUserToPayer("", sArrPayers.get(k), "iht_ittest03");
				} else if (k == 1) {
					oHomePage.assignDifferentUserToPayer("", sArrPayers.get(k), "iht_ittest02");
				} else {
					oHomePage.assignDifferentUserToPayer("", sArrPayers.get(k), "iht_ittest01");
				}
			}
			break;

		case 11:
			for (int k = 0; k < sArrPayers.size(); k++) {
				if (k == 0) {
					oHomePage.assignDifferentUserToPayer("", sArrPayers.get(k), "iht_ittest01");
				} else {
					oHomePage.assignDifferentUserToPayer("", sArrPayers.get(k), "iht_ittest01");
				}
			}
			break;

		default:
			for (int k = 0; k < sArrPayers.size(); k++) {
				if (k == 0) {
					oHomePage.assignDifferentUserToPayer("", sArrPayers.get(k), "iht_ittest01");
				} else {
					oHomePage.assignDifferentUserToPayer("", sArrPayers.get(k), "iht_ittest02");
				}
			}
			break;
		}

		System.out.println("sPayerQuantity value :" + sPayerQuantity);

		if (sPayerQuantity.equalsIgnoreCase("ONECPMOBSOLETE")) {

			// for (int l = 0; l < sArrPayers.size(); l++) {

			oHomePage.checkObsoletePayer("", sArrPayers.get(0));

			// }

		}

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.CPM_Payer_Save);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		if ((oSeleniumUtils.is_WebElement_Present(oHomePage.CPM_Payer_Yes))) {
			oSeleniumUtils.Click_given_Locator(oHomePage.CPM_Payer_Yes);
			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.CPM_Saving_Load);
		}

		oHomePage.closeAllTabs();

	}

	@Step
	public void requires_Presentation() throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.LabelTag, "sValue", "Requires Presentation"));

		oGenericUtils.gfn_Verify_Object_Exist("h4", "CPM: Change Requires Presentation Status");

		oSeleniumUtils.Click_given_WebElement(oHomePage.RequirePresentationChkbox);

		oSeleniumUtils.Click_given_Locator(oHomePage.Save_Button);

		oGenericUtils.gfn_Verify_Object_Exist("p",
				"All the decisions recorded for the payer will be lost and sensitivities will be refreshed once a payer is set to Requires Presentation.");

		oSeleniumUtils.Click_given_Locator(oHomePage.Continue_Button);

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
	}

	@Step
	public void userAssignPayerToCPMUser(String arg1) {

		oHomePage.assignDifferentUserToPayer("", "wwww", "iht_ittest01");
		oHomePage.assignDifferentUserToPayer("", "abcd1234", "iht_ittest02");
	}

	@Step
	public void click_on_AdminScrubTaskType(String sUser, String sReAssignee, String arg3) throws InterruptedException {

		oGenericUtils.gfn_Click_String_object_Xpath(
				StringUtils.replace(oHomePage.ViewAllAdminScrubCheckBox, "sValue", sUser));

		oGenericUtils.gfn_Click_On_Object("div", "Reassign");

		oGenericUtils.gfn_Verify_Object_Exist("div", "Selecte User to ReAssign");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		oSeleniumUtils.Click_given_Locator((StringUtils.replace(oHomePage.ReassignUseridChek, "sValue", sReAssignee)));

		oGenericUtils.gfn_Click_On_Object("div", "OK");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

	}

	@Step
	public void enterFinalMDConfigComments() throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oSeleniumUtils.SwitchToFrame(oHomePage.frame);
		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Summaries"));
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oGenericUtils.gfn_Click_On_Object("span", "Configurations Summary");
		oSeleniumUtils.Enter_given_Text_Element(oHomePage.ConfigSummaryInstruction,
				ProjectVariables.ConfigSummaryComments);
		oSeleniumUtils.Click_given_WebElement(oHomePage.ConfigSummarySaveButton);
		oGenericUtils.gfn_Verify_Object_Exist("h3", "Success");
		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Ok"));
		getDriver().switchTo().defaultContent();

		// }

	}

	@Step
	public void verifyColumnValueAsPerDBInAdminMD(String sColumnName) throws Exception {

		List<String> sColumnNameList = Arrays.asList(sColumnName.split(";"));

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oGenericUtils.gfn_Verify_Object_Exist("a", "Process Candidates");

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.AllRulesRadioBtn);

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		List<WebElement> iAdminScrubRowcount = getDriver().findElements(By.xpath(oHomePage.AdminMDScrubRows));

		System.out.println("AdminRowCount : " + iAdminScrubRowcount.size());

		for (int i = 2; i <= 3; i++) {

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			String sAdminMDScrubRule = getDriver().findElement(By.xpath(oHomePage.getDynamicXPathAdminMD("MidRule", i)))
					.getText();

			String sAdminMDScrubRuleVersion = getDriver()
					.findElement(By.xpath(oHomePage.getDynamicXPathAdminMD("RuleVersion", i))).getText();

			Serenity.setSessionVariable("MidRuleVersion").to(sAdminMDScrubRule + "." + sAdminMDScrubRuleVersion);

			System.out.println(Serenity.sessionVariableCalled("MidRuleVersion").toString());

			ruleIDSelected(Serenity.sessionVariableCalled("MidRuleVersion"));

			for (int j = 0; j < sColumnNameList.size(); j++) {

				String sDBValue = DBUtils.executeSQLQuery(oHomePage
						.getDynamicQuery(sColumnNameList.get(j), sAdminMDScrubRule, sAdminMDScrubRuleVersion).trim());

				String sDBValueText = StringUtils.replace(sDBValue, "  ", " ");

				String[] sAdimMDScrubColumns = { "Group", "From-To", "Override", "Sim Mappings",
						"Sub Rule Description - Resolved", "Sub Rule Description - Resolved",
						"Sub Rule Description - Unresolved", "Sub Rule Notes", "Sub Rule Script",
						"Sub Rule Rationale" };

				oHomePage.scrollToColumn("span", sAdimMDScrubColumns);

				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

				String sColumnData = get_CellText_from_given_Column(sColumnNameList.get(j), i);

				System.out.println("Pop Data :" + sColumnData);

				verifyAdminScrubCellData(sColumnData, sDBValueText);

			}

			String[] sColoumns = { "Sub Rule Description - Resolved", "Sim Mappings", "Override", "From-To", "Group",
					"Topic", "ARD", "Version" };

			oHomePage.scrollToMidRuleColumn("span", sColoumns);

		}

	}

	@Step
	public void navigateToPageOnAdminMDScrubScreen(String sUser, String sPage) throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		switch (sPage) {

		case "All rules":

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.AllRulesRadioBtn);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			break;
		case "Only unscrubbed rules":

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.UnSubscribedRulesRadioBtn);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			break;
		case "Retrieve Candidates":

			oGenericUtils.gfn_Verify_Object_Exist("a", sPage);

			oGenericUtils.gfn_Click_On_Object("a", sPage);

			break;
		case "Retrieve Non Candidates":

			oGenericUtils.gfn_Verify_Object_Exist("a", sPage);

			oGenericUtils.gfn_Click_On_Object("a", sPage);

			break;

		case "Admin View":

			oGenericUtils.gfn_Verify_Object_Exist("a", sPage);

			oGenericUtils.gfn_Click_On_Object("a", sPage);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			break;

		default:
		}
		getDriver().switchTo().defaultContent();

	}

	@Step
	public void clickOnOneOfTheCellWhichContainsFieldText(String sColumn, String sFieldText) throws Exception {

		verifyColumnValueAsPerDBInAdminMD(sColumn);

	}

	@Step
	public void verifyAdminScrubCellData(String sCellPopText, String sColumnDBValue) {

		boolean sStatus = sCellPopText.equalsIgnoreCase(sColumnDBValue);

		verify("Column Value Expected : " + sColumnDBValue + "\n" + "Actual Value is : " + sCellPopText, sStatus);

	}

	@Step
	public String get_CellText_from_given_Column(String sColumn, int iRowno) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		String sXColVal = oHomePage.getDynamicXPathAdminMD(sColumn, iRowno);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.Doubleclick(sXColVal);

		oGenericUtils
				.gfn_Verify_String_Object_Exist(StringUtils.replace(oHomePage.CellPopTextHeader, "sValue", sColumn));

		oGenericUtils.gfn_Verify_String_Object_Exist(StringUtils.replace(oHomePage.CellPopText, "sValue", sColumn));

		String sVal = oSeleniumUtils
				.get_TextFrom_Locator(StringUtils.replace(oHomePage.CellPopText, "sValue", sColumn.trim()));

		oSeleniumUtils.Enter_given_Text_Element(oHomePage.AdminPopCellSearchBox, sVal);

		oGenericUtils.gfn_Verify_String_Object_Exist(oHomePage.HighlightColor);

		oGenericUtils
				.gfn_Click_String_object_Xpath(StringUtils.replace(oHomePage.CloseCellPopup, "sValue", "Sub Rule"));

		return sVal;

	}

	@Step
	public void rulesWhichWereMarkedAsEitherCandidatesOrNonCandidatesByShouldNotBeReleased(String arg1)
			throws InterruptedException {

		userNavigateToIUInstancesScreen("Admin PO Scrub Candidates");

	}

	@Step
	public void get_Admin_Column_Value(String sColumn, String sStatus) {

		String sActualColValue = null;
		boolean sFlag = false;
		List<WebElement> AdminStatus = getDriver().findElements(By.xpath(oHomePage.Admin_Col_Status));
		for (int i = 0; i < AdminStatus.size(); i++) {
			sActualColValue = AdminStatus.get(i).getText().toString();
			System.out.println("Rule Review Status: " + AdminStatus.get(i).getText().toString());
			if (AdminStatus.get(i).getText().equals(sStatus)) {
				verify("Column Value Expected :" + sStatus + " and Actual is " + sActualColValue, true);
				System.out.println("Acutal Column Value: " + AdminStatus.get(i).getText().toString());
				sFlag = true;
				break;
			}
		}

		if (sFlag == false) {

			verify("Column Value Expected :" + sStatus + " and Actual is " + sActualColValue, false);
		}

	}

	@Step
	public void validateSplitpayerInstruction(String sInstruction, String sManualRMRID) {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oSeleniumUtils.highlightElement(oHomePage.Manual_RMR_ID);

		Serenity.setSessionVariable("Work_Order_Code")
				.to(oSeleniumUtils.get_TextFrom_Locator(oHomePage.Manual_RMR_ID).toString());

		getDriver().switchTo().defaultContent();

		System.out.println("Manual RMR ID: " + Serenity.sessionVariableCalled("Work_Order_Code").toString());

		String sQuery = "Select INSTRUCTIONS from AUTH_MASTER.WORK_ORDER_LOG where WORK_ORDER_CODE = '"
				+ Serenity.sessionVariableCalled("Work_Order_Code").toString() + "'";

		System.out.println("Query: " + sQuery);

		String sManualRMRInstr = DBUtils.executeSQLQuery(sQuery);

		boolean sStatus = sManualRMRInstr.equalsIgnoreCase(sInstruction);

		verify("Column Value Expected :" + sInstruction + " and Actual is " + sInstruction, sStatus);

		System.out.println(sManualRMRInstr);

	}

	@Step
	public void cpmSystemProposal(String sProposalType, String sDecision, String sCPMModify)
			throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oGenericUtils.gfn_Verify_Object_Exist("label", "CPM System Proposals:");

		// SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		if (sCPMModify.equals("Modify Decision")) {

			oGenericUtils.gfn_Click_On_Object("label", "Modify Decisions");

		}
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		// oSeleniumUtils.Enter_given_Text_Element(oHomePage.SystemProposalType,
		// sProposalType);

		// SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(oHomePage.CPMDecision_Select, sDecision);

		if (sDecision.equals("Modify")) {

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.System_Proposal_DOS, "sValue", "1_dos"));

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oSeleniumUtils.Enter_given_Text_Element(
					StringUtil.replace(oHomePage.System_Proposal_DOS, "sValue", "toDOSDate"),
					ProjectVariables.SystemProposalDOS);

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag, "sValue", "Ok"));

		}

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.Enter_given_Text_Element(oHomePage.SystemProposalCPM, ProjectVariables.SystemProposalComments);

		if (sCPMModify.equals("NotSaveProposals")) {
			System.out.println("User selected not to save proposals");
		} else {
			oSeleniumUtils.Click_given_Locator(oHomePage.SystemProposalSave);
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
		}

		getDriver().switchTo().defaultContent();
	}

	@Step
	public void completeGroupTaskSegmentsInMyTask(String sNewInstance) throws InterruptedException {

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.GroupTaskTab);

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		int iInstanceCount = getDriver()
				.findElements(By.xpath(StringUtils.replace(oHomePage.InstanceCount, "sValue", sNewInstance))).size();

		for (int i = 1; i <= iInstanceCount; i++) {

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.getDynamicXpathCheckBoxInGroupTask(sNewInstance, i));

		}

		oGenericUtils.gfn_Verify_Object_Exist("div", "Claim Task");

		oGenericUtils.gfn_Click_On_Object("div", "Claim Task");

		oGenericUtils.gfn_Verify_Object_Exist("div", "Claim Tasks");

		oGenericUtils.gfn_Click_On_Object("div", "Claim Tasks");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oGenericUtils.gfn_Click_On_Object("div", "OK");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		oGenericUtils.gfn_Click_On_Object("span", "Individual Tasks - iht_ittest01");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

	}

	@Step
	public void returnRule(String sCurrentStatus, String sReturnTo) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oSeleniumUtils.Click_given_Locator(oHomePage.ReturnRule);

		System.out.println("" + sCurrentStatus + "Return To:");

		oGenericUtils.gfn_Verify_Object_Exist("label", "" + sCurrentStatus + " Return To:");

		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(oHomePage.EditorialReturnSelect, sReturnTo);

		oSeleniumUtils.Enter_given_Text_Element(oHomePage.EditorialReturnReason,
				StringUtils.replace(ProjectVariables.ReturnComments, "sValue", sCurrentStatus));

		oGenericUtils.gfn_Click_On_Object("button", "Return Rule");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		String sActualPopText = oSeleniumUtils.get_TextFrom_Locator("//div[@id='sameSimCommentsConfirmation']//p");

		String sActualText = sActualPopText.replaceAll("\\s{2,}", " ");

		System.out.println("ActualText" + sActualText);

		String sExpected = "Rule is being returned to " + sReturnTo + " for clarification";

		// String sExpected="Rule is being returned to "+sReturnTo+" for
		// clarification of " + sCurrentStatus + " concern. Continue?";

		System.out.println("Expected" + sExpected);

		verify("Rule is being returned to " + sReturnTo + " for clarification" + "Expected Text:" + sExpected
				+ "Actual Text:" + sActualText, sActualText.contains(sExpected));

		oGenericUtils.gfn_Click_On_Object("button", "Yes");

		oGenericUtils.gfn_Verify_Object_Exist("p", " Rule Returned successfully.");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		getDriver().switchTo().defaultContent();

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

	}

	@Step
	public void insert_rules_of_with_Query(String arg1) throws InterruptedException {

		oGenericUtils.gfn_Click_On_Object("div", "Refresh");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		oGenericUtils
				.gfn_Verify_String_Object_Exist(StringUtils.replace(oHomePage.AdminInstanceSamSim, "sValue", arg1));

		DBUtils.executeSQLQuery(StringUtils.replace(ProjectVariables.AdminScrubQuery, "sValue", arg1));

		DBUtils.insertSQLQuery(StringUtils.replace(ProjectVariables.AdminScrubQuery, "sValue", arg1));

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		oGenericUtils.gfn_Click_String_object_Xpath(StringUtils.replace(oHomePage.AdminInstance, "sValue", arg1));

		oGenericUtils.gfn_Verify_Object_Exist("div", "Request Impact Analysis");

		oGenericUtils.gfn_Click_On_Object("div", "Request Impact Analysis");

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.RequesImpactAnalysis);

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		oGenericUtils.gfn_Verify_Object_Exist("div", "OK");

		oGenericUtils.gfn_Click_On_Object("div", "OK");

	}

	@Step
	public void clickTaskWithNewInstanceName(String sTaskName, String sInstance) throws InterruptedException {

		if (sInstance.equalsIgnoreCase("Random")) {

			sInstance = Serenity.sessionVariableCalled("NewInstanceName");

		}

		String sTaskType = StringUtil.replace(oHomePage.TaskandInstance, "sValue", sTaskName);

		String strInstance = StringUtil.replace(sTaskType, "sInstance", sInstance);

		oGenericUtils.gfn_Verify_String_Object_Exist(strInstance);

		oGenericUtils.gfn_Click_String_object_Xpath(strInstance);

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);
	}

	@Step
	public void navigateToAdminRuleReviewValidateColValues(String sRole, String sInstanceType, String sColoumnName,
			String sColumnValue) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oHomePage.closeAllTabs();

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oGenericUtils.gfn_Click_On_Object("span", "Administration");

		oGenericUtils.gfn_Click_On_Object("a", "Interpretive Update Instances");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		clickInstanceinAdmin(sInstanceType);

		applyAdminActiveFilters(sColoumnName, sColumnValue);

		if (sRole.contains("iht_ittest")) {

			sColoumnName = "Assignee";
			sColumnValue = sRole;

		}

		validateRuleReviewColumnValues(sColoumnName, sColumnValue);

	}

	@Step
	public void validateRuleReviewColumnValues(String SColName, String sColVal) {

		int rowVal = 0;
		String vFlag = null;
		String sColValue = null;
		if (SColName.equalsIgnoreCase("Assignee")) {
			rowVal = 8;
		}

		if (SColName.equalsIgnoreCase("Task")) {
			rowVal = 5;
		}

		if (SColName.equalsIgnoreCase("Status")) {
			rowVal = 6;
		}

		List<WebElement> ColStatus = getDriver()
				.findElements(By.xpath(oCommonPage.GetDynamicXPath("RULEREVIEW COLVALUE", rowVal)));

		for (int i = 0; i < ColStatus.size(); i++) {

			sColValue = ColStatus.get(i).getText().toString();

			if (ColStatus.get(i).getText().equals(sColVal)) {
				verify("Column Value Expected :" + sColVal + " and Actual is " + sColValue, true);
				System.out.println("Acutal Column Value: " + ColStatus.get(i).getText().toString());
				vFlag = "Y";
			}

		}

		if (vFlag.equalsIgnoreCase("Y")) {
			verify("Column Value Expected :" + sColVal + "  and Actual is :" + sColValue, true);
		} else {
			verify("Column Value Expected :" + sColVal + "  and Actual is :" + sColValue, false);

		}

	}

	@Step
	public void validateRuleReviewColRowValues(String sRowNo, String sTask, String sStatus, String Assignee) {

		String vFlag = "N";
		String sAssigneeColVal = null;

		List<WebElement> ColStatus = getDriver()
				.findElements(By.xpath(oHomePage.GetDynamicXPath("RULEREVIEW COLVALUE", 8)));
		List<WebElement> RuleStatus = getDriver()
				.findElements(By.xpath(oHomePage.GetDynamicXPath("RULEREVIEW COLVALUE", 6)));

		List<WebElement> TaskStatus = getDriver()
				.findElements(By.xpath(oHomePage.GetDynamicXPath("RULEREVIEW COLVALUE", 5)));

		for (int i = 0; i < ColStatus.size(); i++) {
			sAssigneeColVal = ColStatus.get(i).getText().toString();

			if (ColStatus.get(i).getText().equals(Assignee)) {
				verify("Column Value Expected :" + Assignee + " and Actual is " + sAssigneeColVal, true);
				System.out.println("Acutal Column Value: " + ColStatus.get(i).getText().toString());

				if (RuleStatus.get(i).getText().equals(sStatus)) {
					verify("Column Value Expected :" + sStatus + " and Actual is "
							+ RuleStatus.get(i).getText().toString(), true);
				}

				// else {
				// verify("Column Value Expected :" + sStatus + " and Actual is
				// "
				// + RuleStatus.get(i).getText().toString(), false);
				// }

				if (TaskStatus.get(i).getText().equals(sTask)) {
					verify("Column Value Expected :" + sTask + " and Actual is "
							+ TaskStatus.get(i).getText().toString(), true);
				}

				// else {
				// verify("Column Value Expected :" + sTask + " and Actual is "
				// + TaskStatus.get(i).getText().toString(), false);
				// }

				vFlag = "Y";
			}
		}

		if (vFlag.equalsIgnoreCase("Y")) {
			verify("Column Value Expected :" + Assignee + "and Actual is :" + sAssigneeColVal, true);
		} else {
			verify("Column Value Expected :" + Assignee + "and Actual is :" + sAssigneeColVal, false);

		}

	}

	@Step
	public void applyAdminActiveFilters(String sFilterName, String sFilterValue) throws InterruptedException {

		List<String> sFilterNameList = Arrays.asList(sFilterName.split(";"));

		List<String> sFilterValueList = Arrays.asList(sFilterValue.split(";"));

		for (int i = 0; i < sFilterNameList.size(); i++) {

			switch (sFilterNameList.get(i)) {
			case "Library Status":

				oSeleniumUtils.Click_given_Locator(
						StringUtil.replace(oHomePage.ActiveFiterDropDwnBtn, "sValue", "Library Status:"));

				System.out.println(sFilterValueList.get(i));

				oGenericUtils.gfn_Click_String_object_Xpath(
						StringUtil.replace(oHomePage.LibraryAdminFilter, "sValue", sFilterValueList.get(i)));

				oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

				break;

			case "ProposalTypes":

				oSeleniumUtils.Click_given_Locator(
						StringUtil.replace(oHomePage.ActiveFiterDropDwnBtn, "sValue", "Proposal Types:"));

				oGenericUtils.gfn_Click_String_object_Xpath(
						StringUtil.replace(oHomePage.ApplyAdminFilterValues, "sValue", sFilterValueList.get(i)));

				oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

				break;

			case "FilterTaskStatus":
				oSeleniumUtils.Click_given_Locator(
						StringUtil.replace(oHomePage.ActiveFiterDropDwnBtn, "sValue", "Task Status:"));

				oGenericUtils.gfn_Click_String_object_Xpath(
						StringUtil.replace(oHomePage.ApplyAdminFilterValues, "sValue", sFilterValueList.get(i)));

				oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

				break;

			case "Task":
				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
				oSeleniumUtils
						.Click_given_Locator(StringUtil.replace(oHomePage.ActiveFiterDropDwnBtn, "sValue", "Tasks:"));

				oGenericUtils.gfn_Click_String_object_Xpath(
						StringUtil.replace(oHomePage.ApplyAdminFilterValues, "sValue", sFilterValueList.get(i)));

				oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);
				break;

			case "Assignee":
				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
				oSeleniumUtils.Click_given_Locator(
						StringUtil.replace(oHomePage.ActiveFiterDropDwnBtn, "sValue", "Assignees:"));

				oGenericUtils.gfn_Click_String_object_Xpath(
						StringUtil.replace(oHomePage.ApplyFilterValues, "sValue", sFilterValueList.get(i)));

				oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

				break;

			case "MedicalPolicies":

				System.out.println(sFilterValueList.get(i));
				oSeleniumUtils.Enter_given_Text_Element(
						"//div[@class='GEFT4QHBCW']//label[text()='Medical Policies:']/following-sibling::div[1]//div/div//table//tr//td//input",
						sFilterValueList.get(i));
				oGenericUtils.gfn_Click_On_Object("div", sFilterValueList.get(i));
				oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

				break;

			case "Topics":

				System.out.println(sFilterValueList.get(i));
				oSeleniumUtils.Enter_given_Text_Element(
						"//div[@class='GEFT4QHBCW']//label[text()='Topics:']/following-sibling::div[1]//div/div//table//tr//td//input",
						sFilterValueList.get(i));
				oGenericUtils.gfn_Click_On_Object("div", sFilterValueList.get(i));
				oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

				break;

			case "Decision Points":

				System.out.println(sFilterValueList.get(i));
				oSeleniumUtils.Enter_given_Text_Element(
						"//div[@class='GEFT4QHBCW']//label[text()='Decision Points:']/following-sibling::div[1]//div/div//table//tr//td//input",
						sFilterValueList.get(i));
				oGenericUtils.gfn_Click_On_Object("div", sFilterValueList.get(i));
				oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

				break;

			default:
				Assert.assertTrue("Active Filter cases are not matched for function applyAdminActiveFilters()", false);

			}
		}
	}

	@Step
	public void clickOnInstanceInAdminAndNavigateToRuleReviewPage(String arg1) throws InterruptedException {

		// clickInstanceinAdmin(arg1);
		navigateRuleReviewPage(arg1);

	}

	@Step
	public void clickOnReportHyperLinkInRuleReviewPage() throws InterruptedException {

		oGenericUtils.gfn_Click_On_Object("div", "Report");

		SeleniumUtils.switchWindowUsingWindowCount(5, 2, getDriver());

		System.out.println(getDriver().getTitle());

	}

	@Step
	public void verifyAdminScrubReportHyperlinkIsDisplayed(String arg1) throws InterruptedException {

		oGenericUtils.gfn_Click_On_Object("div", "Admin PO Scrub Report");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		// getDriver().switchTo().defaultContent();

		oSeleniumUtils.SwitchToFrame(oHomePage.IUReportFrame);

		oGenericUtils.gfn_Click_On_Object("span", "Review");

		oGenericUtils.gfn_Click_String_object_Xpath(
				"(//span[text()='Review'])[2]/ancestor::div//div[@ng-if='grid.options.enableSelectAll']");

		oGenericUtils.gfn_Click_On_Object("button", "Apply Filter");

		oGenericUtils.gfn_Verify_String_Object_Exist("//div[.='A']//span//img");

		/*
		 * oGenericUtils.gfn_Click_On_Object("a", "Filters");
		 * 
		 * oGenericUtils.gfn_Verify_String_Object_Exist(
		 * "//div[.='A']//span//img");
		 * 
		 */

	}

	@Step
	public void completeAllEditorialReviews(String sOperation) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oGenericUtils.gfn_Verify_String_Object_Exist(oHomePage.EditorialSegment);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Editorial"));

		String sReviewSegValue = null;
		List<WebElement> rows = getDriver().findElements(By.xpath(oHomePage.Editorial_Review_Table));
		// List<WebElement> rows =
		// getDriver().findElements(By.xpath(oHomePage.QA_Review_Table));

		System.out.println(rows);

		for (int i = 2; i <= rows.size(); i++) {

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Editorial"));

			String sVal = Integer.toString(i);

			sReviewSegValue = oSeleniumUtils
					.get_TextFrom_Locator(StringUtil.replace(oHomePage.Editorial_Review_Table_Item, "sValue", sVal));

			// if (!(sReviewSegValue == "Editorials")) {
			if (!(sReviewSegValue.equalsIgnoreCase("Editorials"))) {

				oSeleniumUtils
						.Click_given_Locator(StringUtil.replace(oHomePage.Editorial_Review_Table_Item, "sValue", sVal));

			}

			switch (sReviewSegValue) {
			case "BW And/BWO Or Logic":
				oGenericUtils.gfn_Verify_Object_Exist("h3", "Billed With And/Billed Without Or Code Clarification");

				if (!(oHomePage.NewBilled_Radio_button.isSelected())) {

					oSeleniumUtils.Click_given_WebElement(oHomePage.NewBilled_Radio_button);
				}
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

				oGenericUtils.gfn_Click_On_Object("Label", "Complete Editorial Review");
				userClickOnReviewSuccessMsg(sReviewSegValue);
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

				break;

			case "Impact Code List":

				SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

				int icount = getDriver()
						.findElements(By
								.xpath("//div[@id='jqgh_impactCodeListGrid_editorialReview10']//input[@id='impactSelectAll']"))
						.size();

				if (icount > 0) {

					/////// trinat;h///////
					if (!(oHomePage.ImpactChkNameEditorial.isSelected())) {
						oSeleniumUtils.highlightElement(oHomePage.EditorialImpactReviewCodeAll);
						oSeleniumUtils.Click_given_WebElement(oHomePage.EditorialImpactReviewCodeAll);
						SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
					} else {
						System.out.println("Radio button is not availble");
					}
				}

				oSeleniumUtils.highlightElement(oHomePage.Complete_Editorial_Review);
				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
				if (icount > 0) {
					oSeleniumUtils.Click_given_Locator(oHomePage.QAReview_Category);
				}
				oSeleniumUtils.highlightElement(oHomePage.Complete_Editorial_Review);
				oSeleniumUtils.Click_given_Locator(oHomePage.Complete_Editorial_Review);
				userClickOnReviewSuccessMsg(sReviewSegValue);
				oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Editorial"));

				break;

			case "Potential Conflicts":

				SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
				oSeleniumUtils.Click_given_Locator(oHomePage.Complete_Editorial_Review);
				SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

				oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Editorial"));

				break;

			default:

				if (!(sReviewSegValue.equalsIgnoreCase("Editorials"))) {
					oSeleniumUtils.Click_given_Locator(oHomePage.Complete_Editorial_Review);
					userClickOnReviewSuccessMsg(sReviewSegValue);
					oSeleniumUtils
							.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Editorial"));
					break;
				}

			}

		}

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Editorial"));

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.AnchorTag, "sValue", "Editorials"));

		SeleniumUtils.switchWindowUsingWindowCount(5, 2, getDriver());

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		if (sOperation.equalsIgnoreCase("No Editorial Changes Required")) {

			oSeleniumUtils.Click_given_Locator(
					StringUtil.replace(oHomePage.No_Edit_Required, "sValue", "No Editorial Changes Required"));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			verify("Confirmation Warning should be displayed",
					oSeleniumUtils.is_WebElement_Displayed(StringUtil.replace(oHomePage.pTag, "sValue",
							"Rule is being set to No Editorial Changes Required. No changes to the Editorial fields will be allowed Continue?")));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oSeleniumUtils.Click_given_WebElement(oHomePage.Confirm_Yes);

			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			oGenericUtils.gfn_Click_On_Object("button", "Ok");
		}

		// Complete editorial

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oGenericUtils.gfn_Click_On_Object("label", "Complete Editorials");

		// oSeleniumUtils.Click_given_Locator(oHomePage.Complete_QA_Review_Editorial);

		oSeleniumUtils.Click_given_WebElement(oHomePage.Confirm_Yes);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		// getDriver().close(); // Close current page

		SeleniumUtils.switchWindowUsingWindowCount(3, 1, getDriver());

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void reAssignToCPMUser(String sRole, String sCPMFrom, String sCPMTo) throws InterruptedException {

		// oSeleniumUtils.Click_given_WebElement(oHomePage.Column_GridCheck);

		List<WebElement> ColStatus = getDriver()
				.findElements(By.xpath(oHomePage.GetDynamicXPath("RULEREVIEW COLVALUE", 8)));
		for (int i = 0; i < ColStatus.size(); i++) {
			String sAssigneeColVal = ColStatus.get(i).getText().toString();

			if (ColStatus.get(i).getText().equals(sCPMFrom)) {
				verify("Column Value Expected :" + sCPMFrom + " and Actual is " + sAssigneeColVal, true);
				List<WebElement> ChkStatus = getDriver()
						.findElements(By.xpath(oHomePage.GetDynamicXPath("RULEREVIEW COLVALUE", 1)));

				System.out.println("Acutal Column Value: " + ChkStatus.get(i));
				oSeleniumUtils.clickOn(ChkStatus.get(i));
			}

		}

		oSeleniumUtils.Click_given_WebElement(oHomePage.ReAssignNavigationAdmin);

		int i = getDriver().findElements(By.xpath(StringUtil.replace(oHomePage.SpanTag, "sValue", "Reassign"))).size();

		if (i > 0) {
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag, "sValue", "Reassign"));
		} else {
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.DivTag, "sValue", "Reassign"));
		}

		switch (sRole) {

		case "CPM":

			oGenericUtils.gfn_Verify_Object_Exist("div", "Reassign 1 CPM Review Rule");

			oSeleniumUtils.Click_given_WebElement(oHomePage.CPM_Reassgin_ListNavigaion);

			oGenericUtils.gfn_Click_On_Object("div", sCPMTo);

			oGenericUtils.gfn_Click_On_Object("div", "OK");

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			break;

		default:

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			oSeleniumUtils.Click_given_Locator(oHomePage.ReAssignToListBoxIcon);

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Click_String_object_Xpath(StringUtils.replace(oHomePage.QAReassign, "sValue", sCPMTo));

			oGenericUtils.gfn_Click_On_Object("div", "Submit Reassignments");

			oGenericUtils.gfn_Click_On_Object("div", "Yes");

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			break;

		}

	}

	@Step
	public void navigateToOverviewLinkAndValidateOptionsAvailableForMD() throws InterruptedException {

		oGenericUtils.gfn_Click_On_Object("div", "Overview");

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		String[] str = { "Not Started", "Started", "Completed" };

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		for (int i = 0; i < str.length; i++) {

			verify("" + str[i] + " text is displayed under view by", oGenericUtils
					.gfn_Verify_String_Object_Exist((StringUtils.replace(oHomePage.AnchorTag, "sValue", str[i]))));

			oSeleniumUtils.Click_given_Locator(StringUtils.replace(oHomePage.AnchorTag, "sValue", str[i]));

			verify("Collapse all button is displayed under view by", oGenericUtils.gfn_Verify_String_Object_Exist(
					StringUtils.replace(oHomePage.AnchorTag, "sValue", "Collapse all")));

			verify("Expand all button is displayed under view by", oGenericUtils
					.gfn_Verify_String_Object_Exist(StringUtils.replace(oHomePage.AnchorTag, "sValue", "Expand all")));
		}

		/*
		 * verify("Started text is displayed under view by"
		 * ,oSeleniumUtils.is_WebElement_Displayed(StringUtils.replace(
		 * oHomePage.AnchorTag, "sValue", "Started")));
		 * 
		 * oSeleniumUtils.Click_given_Locator(StringUtils.replace(oHomePage.
		 * AnchorTag, "sValue", "Started"));
		 * 
		 * verify("Collapse all button is displayed under view by"
		 * ,oSeleniumUtils.is_WebElement_Displayed(StringUtils.replace(
		 * oHomePage.AnchorTag, "sValue", "Collapse all")));
		 * 
		 * verify("Expand all button is displayed under view by"
		 * ,oSeleniumUtils.is_WebElement_Displayed(StringUtils.replace(
		 * oHomePage.AnchorTag, "sValue", "Expand all")));
		 * 
		 * verify("Completed text is displayed under view by"
		 * ,oSeleniumUtils.is_WebElement_Displayed(StringUtils.replace(
		 * oHomePage.AnchorTag, "sValue", "Completed")));
		 * 
		 * oSeleniumUtils.Click_given_Locator(StringUtils.replace(oHomePage.
		 * AnchorTag, "sValue", "Completed"));
		 * 
		 * verify("Collapse all button is displayed under view by"
		 * ,oSeleniumUtils.is_WebElement_Displayed(StringUtils.replace(
		 * oHomePage.AnchorTag, "sValue", "Collapse all")));
		 * 
		 * verify("Expand all button is displayed under view by"
		 * ,oSeleniumUtils.is_WebElement_Displayed(StringUtils.replace(
		 * oHomePage.AnchorTag, "sValue", "Expand all")));
		 */

	}

	@Step
	public void verifyExcelGUIInAdminScrub() throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		String[] sAdminScrubTabs = { "Export", "Process Candidates", "Process Non Candidates", "Filters", "Hide/Unhide",
				"Retrieve Non Candidates", "Retrieve Candidates", "Night View", "Dashboard" };

		for (int i = 0; i < sAdminScrubTabs.length; i++) {

			System.out.println(sAdminScrubTabs[i]);

			oSeleniumUtils.highlightElement("//a[text()='" + sAdminScrubTabs[i] + "']");

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Verify_Object_Exist("a", sAdminScrubTabs[i]);

		}

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void validateReassignFunctionalityInAdminMD() throws InterruptedException {

		/*
		 * //oSeleniumUtils.SwitchToFrame(oHomePage.frame);
		 * 
		 * String sFiltertxt = null;
		 * 
		 * String[] sAdminMDColumnNames = { "Medical Policy", "Topic" };
		 * 
		 * for (int j = 0; j < sAdminMDColumnNames.length; j++) {
		 * 
		 * verify("Click on "+sAdminMDColumnNames[j]+"",
		 * oGenericUtils.gfn_Click_On_Object("span", sAdminMDColumnNames[j]));
		 * 
		 * oGenericUtils.gfn_Verify_String_Object_Exist("(//span[text()='" +
		 * sAdminMDColumnNames[j]+
		 * "'])[2]/parent::div/parent::div/parent::div/parent::div/parent::div/parent::div"
		 * );
		 * 
		 * validateFieldPrasentPopUp();
		 * 
		 * switch (sAdminMDColumnNames[j]) { case "Medical Policy": sFiltertxt =
		 * "Bilateral Procedures Policy";
		 * 
		 * break; case "Topic": sFiltertxt =
		 * "Procedures that are Bilateral in Nature (Bilateral Indicator 2)";
		 * break;
		 * 
		 * default: Assert.assertTrue("Entered case value is not valid", false);
		 * 
		 * }
		 * 
		 * oSeleniumUtils.Enter_given_Text_Element(
		 * "//input[@id='filterTextSearch']", sFiltertxt);
		 * 
		 * verify("Click on Apply Filter Button",
		 * oGenericUtils.gfn_Click_On_Object("button", "Apply Filter"));
		 * 
		 * // oGenericUtils.gfn_Click_On_Object("button", "Apply Filter");
		 * 
		 * verify("Click on Cancel Button",
		 * oGenericUtils.gfn_Click_On_Object("Span", "Cancel"));
		 * 
		 * }
		 *//*
			 * verify("Click on Filters", oGenericUtils.gfn_Click_On_Object("a",
			 * "Filters"));
			 * 
			 * verify("Click on Clear All Filters",
			 * oGenericUtils.gfn_Click_On_Object("a", "Clear All Filters"));
			 */

		verify("Click on All Rule Radio Button",
				oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.AllRulesRadioBtn));

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oGenericUtils.gfn_Click_String_object_Xpath(
				"(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[1]");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		verify("Process Candidates is clicked", oGenericUtils.gfn_Click_On_Object("a", "Process Candidates"));

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		verify("Retrieve Candidates is clicked", oGenericUtils.gfn_Click_On_Object("a", "Retrieve Candidates"));

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oGenericUtils.gfn_Click_String_object_Xpath(
				"(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[1]");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		verify("Reassign Button is clicked in AdminScrub", oGenericUtils.gfn_Click_On_Object("a", "Reassign"));

		SeleniumUtils.defaultWait(6000);

		reassignUserInAdminScrub("Preliminary PO Review", "iht_ittest05");

		oGenericUtils.gfn_Click_String_object_Xpath(
				"(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[1]");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		verify("Reassign Button is clicked in AdminScrub", oGenericUtils.gfn_Click_On_Object("a", "Reassign"));

		reassignUserInAdminScrub("Final PO Review", "iht_ittest05");

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void reassignUserInAdminScrub(String sTask, String sUser) throws InterruptedException {

		oGenericUtils.gfn_Verify_Object_Exist("h5", "Re-Assign Rules");

		verify("Task Type Label is displayed", oGenericUtils.gfn_Verify_Object_Exist("span", "Task Type"));

		verify("MD Review Label is displayed", oGenericUtils.gfn_Verify_Object_Exist("span", "PO Review"));

		verify("Rule Count is displayed", oGenericUtils.gfn_Verify_Object_Exist("span", "Rule Count"));

		oGenericUtils.gfn_Verify_String_Object_Exist(
				"//span[text()='Rule Count']/parent::div/child::div/child::span[text()='1 ']");

		oGenericUtils.gfn_Verify_Object_Exist("span", "Task To Be Reassigned To ");

		oGenericUtils.gfn_Click_String_object_Xpath(
				"//span[text()='Task To Be Reassigned To ']/parent::div/child::div//input[@value='" + sTask + "']");

		verify("Reassign label is displayed", oGenericUtils.gfn_Verify_Object_Exist("span", "Reassign To "));

		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator("//select[@ng-model='reassignToUser']",
				sUser);

		verify("Reassign button is clicked", oGenericUtils.gfn_Click_On_Object("button", "Reassign"));

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		System.out.println(oSeleniumUtils
				.get_TextFrom_Locator("(//div[@class='ngCellText ui-grid-cell-contents ng-binding ng-scope'])[7]"));

		verify("User id is changed after reassign", sUser.equalsIgnoreCase(oSeleniumUtils
				.get_TextFrom_Locator("(//div[@class='ngCellText ui-grid-cell-contents ng-binding ng-scope'])[7]")));

		verify("Task is changed after reassign", sTask.equalsIgnoreCase(oSeleniumUtils
				.get_TextFrom_Locator("(//div[@class='ngCellText ui-grid-cell-contents ng-binding ng-scope'])[6]")));

		// getDriver().switchTo().defaultContent();

	}

	@Step
	public void validateAdminMDDashboard() throws InterruptedException {

		verify("Click on Dashboard", oGenericUtils.gfn_Click_On_Object("a", "Dashboard"));

		// SeleniumUtils.switchWindowUsingWindowCount(2, 2, getDriver());

		SeleniumUtils.switchWindowUsingWindowCount(2, 4, getDriver());

		boolean bln = oGenericUtils.gfn_Verify_Object_Exist("span", "Assignee");

		System.out.println(bln);

		verify("Assinee is displayed", oGenericUtils.gfn_Verify_Object_Exist("span", "Assignee"));

		verify("Medical Policy is displayed", oGenericUtils.gfn_Verify_Object_Exist("span", "Medical Policy"));

		verify("Topic is displayed", oGenericUtils.gfn_Verify_Object_Exist("span", "Topic"));

		verify("Rule Count is displayed", oGenericUtils.gfn_Verify_Object_Exist("span", "Rule Count"));

		verify("Refresh is displayed", oGenericUtils.gfn_Verify_Object_Exist("a", "Refresh"));

		oGenericUtils.gfn_Click_String_object_Xpath(
				"//div[@class='ui-grid-tree-base-row-header-buttons ui-grid-icon-plus-squared']");

		List<WebElement> sList = getDriver().findElements(By.xpath("//i[@class='ui-grid-icon-minus-squared']"));

		for (int i = 1; i <= sList.size(); i++) {

			oGenericUtils.gfn_Verify_String_Object_Exist("(//i[@class='ui-grid-icon-minus-squared'])[" + i + "]");

			System.out.println("Tested clopse functionality");
		}

		SeleniumUtils.switchWindowUsingWindowCount(2, 1, getDriver());

	}

	@Step
	public void validateRetainFilters() throws InterruptedException {

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.AdminScrubImageLoader);

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.AllRulesRadioBtn);

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.AdminScrubImageLoader);

		String[] sAdminMDColumnNames = { "Version", "IU Indicator" };

		for (int i = 0; i < sAdminMDColumnNames.length; i++) {

			oGenericUtils.gfn_Click_On_Object("span", sAdminMDColumnNames[i]);

			oGenericUtils.gfn_Verify_String_Object_Exist("(//span[text()='" + sAdminMDColumnNames[i]
					+ "'])[2]/parent::div/parent::div/parent::div/parent::div/parent::div/parent::div");

			// validateFieldPrasentPopUp();

			switch (sAdminMDColumnNames[i]) {

			case "Version":

				oGenericUtils.gfn_Verify_String_Object_Exist(
						"//span[@class='glyphicon glyphicon-home adminHome adminHomeHilight']");

				oSeleniumUtils.Enter_given_Text_Element("//input[@id='filterTextSearch']",
						oSeleniumUtils.get_TextFrom_Locator(
								"(//div[@class='ngCellText ui-grid-cell-contents ng-binding ng-scope'])[5]"));

				oGenericUtils.gfn_Click_On_Object("button", "Apply Filter");

				oGenericUtils.gfn_Click_On_Object("span", "Cancel");

				oGenericUtils.gfn_Verify_String_Object_Exist("//div[text()='E']/child::span//img");

				oGenericUtils.gfn_Click_On_Object("a", "Retrieve Candidates");

				oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.AdminScrubImageLoader);

				oGenericUtils.gfn_Verify_String_Object_Exist("//span[@class='glyphicon glyphicon-home adminHome']");

				oGenericUtils.gfn_Verify_String_Object_Exist("//div[text()='E']/child::span//img");

				oGenericUtils.gfn_Click_String_object_Xpath("//span[@class='glyphicon glyphicon-home adminHome']");

				oGenericUtils.gfn_Verify_String_Object_Exist(
						"//span[@class='glyphicon glyphicon-home adminHome adminHomeHilight']");

				break;
			case "IU Indicator":

				oGenericUtils.gfn_Verify_String_Object_Exist(
						"//span[@class='glyphicon glyphicon-home adminHome adminHomeHilight']");

				oSeleniumUtils.Enter_given_Text_Element("//input[@id='filterTextSearch']",
						oSeleniumUtils.get_TextFrom_Locator(
								"(//div[@class='ngCellText ui-grid-cell-contents ng-binding ng-scope'])[11]"));

				oGenericUtils.gfn_Click_On_Object("button", "Apply Filter");

				oGenericUtils.gfn_Click_On_Object("span", "Cancel");

				oGenericUtils.gfn_Verify_String_Object_Exist("//div[text()='K']/child::span//img");

				oGenericUtils.gfn_Click_On_Object("a", "Retrieve Candidates");

				oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.AdminScrubImageLoader);

				oGenericUtils.gfn_Verify_String_Object_Exist("//span[@class='glyphicon glyphicon-home adminHome']");

				oGenericUtils.gfn_Verify_String_Object_Exist("//div[text()='K']/child::span//img");

				oGenericUtils.gfn_Click_String_object_Xpath("//span[@class='glyphicon glyphicon-home adminHome']");

				oGenericUtils.gfn_Verify_String_Object_Exist(
						"//span[@class='glyphicon glyphicon-home adminHome adminHomeHilight']");
				break;

			default:
				Assert.assertTrue("Entered case value is not valid", false);

			}

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.AllRulesRadioBtn);

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.AdminScrubImageLoader);
		}

		getDriver().switchTo().defaultContent();
	}

	@Step
	public void validateAdminViewButtonsDisabled() throws InterruptedException {

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.AdminScrubImageLoader);

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.AllRulesRadioBtn);

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.AdminScrubImageLoader);

		String sTotalItems = oSeleniumUtils.get_TextFrom_Locator("//span[contains(text(),'Total')]");

		String sNoOFItemsHome = StringUtils.substringAfter(sTotalItems, ":").trim();

		oGenericUtils.gfn_Click_On_Object("a", "Admin View");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.AdminScrubImageLoader);

		String sTotalItemAdmin = oSeleniumUtils.get_TextFrom_Locator("//span[contains(text(),'Total')]");

		String sNoOFItemsAdmin = StringUtils.substringAfter(sTotalItemAdmin, ":").trim();

		verify("Total Items count displayed is not same for Home and Admin View" + "No of Items in Admin: "
				+ sNoOFItemsAdmin + "NoOFItemsHome" + sNoOFItemsHome, sNoOFItemsAdmin.equalsIgnoreCase(sNoOFItemsHome));

		/*
		 * oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.
		 * AllRulesRadioBtn);
		 * 
		 * oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.
		 * AdminScrubImageLoader);
		 * 
		 * oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.
		 * SelectAllCheckBoxAdminMDScrub);
		 * 
		 * Assert.assertFalse("Reassgin button is not disabled",
		 * oHomePage.Reassign_Btn.isCurrentlyEnabled());
		 */

	}

	@Step
	public void verifyColumnHeaderAndFilterPopupWhenAdminMDEntersZeroListValues() throws InterruptedException {

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.AdminScrubImageLoader);

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.AllRulesRadioBtn);

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.AdminScrubImageLoader);

		String sFiltertxt = "TestAuto123";

		String[] sAdminMDColumnNames = { "Review", "Comment", "Reviewer", "MR", "Version", "Task Type", "Assignee",
				"Library", "ARD", "Reference", "IU Indicator", "Medical Policy", "Topic", "Decision Point", "Group" };

		for (int i = 0; i < sAdminMDColumnNames.length; i++) {

			System.out.println(sAdminMDColumnNames[i]);

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.AdminScrubImageLoader);

			oGenericUtils.gfn_Click_On_Object("span", sAdminMDColumnNames[i]);

			oGenericUtils.gfn_Verify_String_Object_Exist("(//span[text()='" + sAdminMDColumnNames[i]
					+ "'])[2]/parent::div/parent::div/parent::div/parent::div/parent::div/parent::div");

			// validateFieldPrasentPopUp();

			oSeleniumUtils.Enter_given_Text_Element(oHomePage.AdminScrubFilterTextSearch, sFiltertxt);

			Assert.assertFalse("Apply filter button is not disabled", oHomePage.ApplyFilterButton.isCurrentlyEnabled());

			oGenericUtils.gfn_Click_On_Object("Span", "Cancel");

		}

	}

	@Step
	public void verifyFollowingLabelAndDataIsWithinThatColumn(String arg) throws InterruptedException {

		// oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.AdminScrubImageLoader);

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.AllRulesRadioBtn);

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.AdminScrubImageLoader);

		String[] sAdimMDScrubColumns = ProjectVariables.ColumnList;

		oHomePage.scrollToColumn("span", sAdimMDScrubColumns);

		oGenericUtils.gfn_Verify_Object_Exist("span", ProjectVariables.sColumns);

		String sFiltertxt = oSeleniumUtils.get_TextFrom_Locator(
				"(//div[@ng-bind-html='grid.appScope.wrapTextForMappingInfo(row.entity.mappingInfo)'])[1]//table//tr//td[1]");

		oSeleniumUtils.Doubleclick(
				"(//div[@ng-bind-html='grid.appScope.wrapTextForMappingInfo(row.entity.mappingInfo)'])[1]//table//tr");

		oGenericUtils.gfn_Verify_String_Object_Exist("(//span[text()='" + ProjectVariables.sColumns + "'])[2]");

		oSeleniumUtils.Enter_given_Text_Element(oHomePage.AdminPopCellSearchBox, sFiltertxt);

		oGenericUtils.gfn_Verify_String_Object_Exist(oHomePage.HighlightColor);

		oGenericUtils.gfn_Click_String_object_Xpath(StringUtils.replace(oHomePage.CloseCellPopup, "sValue",
				"ReviewCPT   Group        Mapped CPT           From-To                         CAT          Override "));

		oGenericUtils.gfn_Click_On_Object("span", ProjectVariables.sColumns);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oGenericUtils.gfn_Verify_String_Object_Exist("(//span[text()='" + ProjectVariables.sColumns + "'])[2]");

		oGenericUtils.gfn_Click_String_object_Xpath(
				"(//div[@class='ui-grid-selection-row-header-buttons ui-grid-icon-ok ng-scope'])[1]");

		oGenericUtils.gfn_Click_On_Object("button", "Apply Filter");

		oGenericUtils.gfn_Click_On_Object("Span", "Cancel");

		oGenericUtils.gfn_Verify_String_Object_Exist("//div[text()='U']/child::span//img");

		// getDriver().switchTo().defaultContent();

	}

	@Step
	public static boolean compareList(String string, String string2) {

		return string.toString().contentEquals(string2.toString()) ? true : false;
	}

	public void verifyGenerateSummariesEnabled() throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oSeleniumUtils.SwitchToFrame(oHomePage.frame);
		oGenericUtils.gfn_Click_On_Object("tab-heading", "Decisions");
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		String status_Config_Btn = getDriver().findElement(By.xpath(oHomePage.Generate_Summaries))
				.getAttribute("disabled");
		System.out.println(status_Config_Btn);

		if (status_Config_Btn == null) {
			verify("Generate summaires enabled sucessfully", true);
		} else {
			verify("Generate summaires is disabled", false);
		}

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Summaries"));
		oGenericUtils.gfn_Verify_Object_Exist("span", "Decision Summary");
		oGenericUtils.gfn_Verify_Object_Exist("span", "Configurations Summary");
		oGenericUtils.gfn_Verify_Object_Exist("span", "Impact Code List");

		getDriver().switchTo().defaultContent();

	}

	public void validateSystemProposalListItems() throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		List<WebElement> iDecesionList = getDriver().findElements(By.xpath(oHomePage.SystemDecisionSel));

		List<String> sFilterNameList = Arrays.asList(ProjectVariables.SystemDecesionList.split(";"));

		for (int k = 0; k < sFilterNameList.size(); k++) {

			System.out.println("Expected Column Value: " + sFilterNameList.get(k).toString());

			for (int i = 0; i < iDecesionList.size(); i++) {

				System.out.println("Rule Review Status: " + iDecesionList.get(i).getText().toString());

				if (iDecesionList.get(i).getText().contains(sFilterNameList.get(k))) {

					verify("Column Value Expected :" + sFilterNameList.get(k) + " and Actual is "
							+ iDecesionList.get(i).getText(), true);

				} else {

					verify("Column Value Expected :" + sFilterNameList.get(k) + " and Actual is "
							+ iDecesionList.get(i).getText(), false);

				}
			}
		}

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		getDriver().switchTo().defaultContent();

	}

	public void validateNoDecisionError() throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);
		oSeleniumUtils.Click_given_Locator(oHomePage.NoDecision);
		oGenericUtils.gfn_Verify_Object_Exist("p",
				" Final PO comments needs to be provided before performing no decisions action.");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Comments"));

		oGenericUtils.gfn_Click_On_Object("span", "Final PO Comments");
		oSeleniumUtils.Enter_given_Text_Element(
				StringUtil.replace(oHomePage.Final_MD_Comments, "sValue", "Final PO Comments"), "Automation test");
		oGenericUtils.gfn_Click_On_Object("label", "Save");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Decisions"));

		oGenericUtils.gfn_Verify_Object_Exist("h3", "No Decisions");
		// oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.NoDecision);
		oGenericUtils.gfn_Click_On_Object("button", "Yes");

		getDriver().switchTo().defaultContent();

	}

	public void validateCPTChangesInDB(String sRuleUpdate) throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		String sNewRuleFlag = "False";

		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Summaries"));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		if (oGenericUtils.gfn_Verify_Object_Exist(oHomePage.NewMidruleversion, 1) == true) {

			Serenity.setSessionVariable("MidRuleNewVersion")
					.to(oSeleniumUtils.get_TextFrom_Locator("//label[text()='New Rule Version: ']//..//label[2]"));

			System.out.println(Serenity.sessionVariableCalled("MidRuleNewVersion").toString());

			verify("New mid rule version is : " + Serenity.sessionVariableCalled("MidRuleNewVersion").toString(), true);

			sNewRuleFlag = "True";

			Serenity.setSessionVariable("NewRuleFlag").to(sNewRuleFlag);

		}

		if (oGenericUtils.gfn_Verify_Object_Exist(oHomePage.NewMidruleversion, 1) == true) {

			Serenity.setSessionVariable("MidRuleNewVersion")
					.to(oSeleniumUtils.get_TextFrom_Locator("//label[text()='New Rule Version: ']//..//label[2]"));

			System.out.println(Serenity.sessionVariableCalled("MidRuleNewVersion").toString());

			verify("New mid rule version is : " + Serenity.sessionVariableCalled("MidRuleNewVersion").toString(), true);

			sNewRuleFlag = "True";

			Serenity.setSessionVariable("NewRuleFlag").to(sNewRuleFlag);

		}

		oGenericUtils.gfn_Click_On_Object("span", "Configurations Summary");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		List<WebElement> ConfigSummaryList = getDriver().findElements(By.xpath(oHomePage.ConfigSumTable));
		System.out.println(ConfigSummaryList.size());

		for (int i = 1; i <= ConfigSummaryList.size(); i++) {

			String sConfigAction = getDriver()
					.findElement(By.xpath(oHomePage.GetDynamicXPathWithRowCol("CONFIGSUMMARY VALUES", i + 1, 1)))
					.getText();
			String sCPTFrom = getDriver()
					.findElement(By.xpath(oHomePage.GetDynamicXPathWithRowCol("CONFIGSUMMARY VALUES", i + 1, 2)))
					.getText();
			String sCPTTo = getDriver()
					.findElement(By.xpath(oHomePage.GetDynamicXPathWithRowCol("CONFIGSUMMARY VALUES", i + 1, 3)))
					.getText();
			String sMod1 = getDriver()
					.findElement(By.xpath(oHomePage.GetDynamicXPathWithRowCol("CONFIGSUMMARY VALUES", i + 1, 5)))
					.getText();
			String sMod2 = getDriver()
					.findElement(By.xpath(oHomePage.GetDynamicXPathWithRowCol("CONFIGSUMMARY VALUES", i + 1, 7)))
					.getText();
			String sPoslike = getDriver()
					.findElement(By.xpath(oHomePage.GetDynamicXPathWithRowCol("CONFIGSUMMARY VALUES", i + 1, 13)))
					.getText();
			String sAppDateFrom = getDriver()
					.findElement(By.xpath(oHomePage.GetDynamicXPathWithRowCol("CONFIGSUMMARY VALUES", i + 1, 14)))
					.getText();
			System.out.println(sAppDateFrom);
			String sAppDateTo = getDriver()
					.findElement(By.xpath(oHomePage.GetDynamicXPathWithRowCol("CONFIGSUMMARY VALUES", i + 1, 15)))
					.getText();

			boolean sResult = validateDBRuleValidation(sRuleUpdate, sConfigAction, sCPTFrom, sCPTTo, sMod1, sMod2,
					sPoslike, sAppDateFrom, sAppDateTo);

			verify(+i + "-column Value Expected :" + sCPTFrom + " and Actual is: " + sResult, sResult);

		}

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "QA"));

		getDriver().switchTo().defaultContent();

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

	}

	public boolean validateDBRuleValidation(String sRuleUpdate, String sConfigAction, String sCPTFrom, String sCPTTo,
			String sMod1, String sMod2, String sPoslike, String sAppDateFrom, String sAppDateTo) {

		String[] sRuleVersion;
		String sDateFrom = oGenericUtils.convertStringDate(sAppDateFrom);
		System.out.println("datefrom" + sDateFrom);

		String sDateTo = oGenericUtils.convertStringDate(sAppDateTo);
		System.out.println("datefrom" + sDateTo);
		// System.out.println("new flag:"
		// +Serenity.sessionVariableCalled("NewRuleFlag").toString());

		if (Serenity.sessionVariableCalled("NewRuleFlag") != null) {

			sRuleVersion = Serenity.sessionVariableCalled("MidRuleNewVersion").toString().split("\\.");
			System.out.println(Serenity.sessionVariableCalled("MidRuleNewVersion").toString());
		} else {
			sRuleVersion = Serenity.sessionVariableCalled("MidRuleVersion").toString().split("\\.");
			System.out.println(Serenity.sessionVariableCalled("MidRuleVersion").toString());

		}

		System.out.println("Midrule:  " + sRuleVersion[0]);

		System.out.println("Rule version :  " + sRuleVersion[1]);

		// Execute Query Before selecting Rule from My Tasks
		String sQuery = " select CPT_FROM from RULES.SUB_RULES_CPT where sub_rule_key in "
				+ "( select sub_Rule_key from rules.sub_rules where mid_rule_key ='" + sRuleVersion[0]
				+ "' and rule_version = '" + sRuleVersion[1] + "') " + "and cpt_from = '" + sCPTFrom
				+ "' and cpt_to = '" + sCPTTo + "' and DATE_FROM= TO_DATE('" + sDateFrom
				+ "', 'MM/DD/YYYY') and DATE_TO= TO_DATE('" + sDateTo + "', 'MM/DD/YYYY') and CPT_MOD1 = '" + sMod1
				+ "' and CPT_MOD1 = '" + sMod2 + "' and POS_LIKE = '" + sPoslike + "' ";

		System.out.println("Query: " + sQuery);

		verify("Executed Query " + sQuery, true);

		String sQueryResult = DBUtils.executeSQLQuery(sQuery);

		System.out.println("Query Result: " + sQueryResult);

		// boolean sStatus = sQueryResult.equalsIgnoreCase(sCPTFrom);

		// verify("Column Value Expected :" + sCPTFrom + " and Actual is " +
		// sQueryResult, sStatus);

		switch (sConfigAction) {
		case "ADD":
		case "CHG TO":
			switch (sRuleUpdate) {

			case "YES":
				if (sQueryResult.equalsIgnoreCase(sCPTFrom)) {
					verify("Displayed Records in Backend Database for Action After Update :" + sCPTFrom
							+ "Records Added sucessfully from Backend Database After Update" + sQueryResult, true);
				} else {
					verify("Displayed Records in Backend Database for Action After Update :" + sCPTFrom
							+ "Record not Added in Database after Update" + sQueryResult, false);
				}

				break;

			case "NO":
				if (sQueryResult.equals("")) {
					verify("Should not Display Records for Action Before Update:" + sCPTFrom
							+ "Records not Displayed in Backend Database before Rule Update" + sQueryResult, true);
				} else {
					verify("Should not Display Records for Action Before Update:" + sCPTFrom
							+ "Records Displayed in Backend Database before Rule Update" + sQueryResult, false);
				}

				break;
			}
			break;

		case "DEL":
		case "CHG FROM":
			switch (sRuleUpdate) {

			case "YES":
				if (sQueryResult.equals("")) {
					verify("Should not Display Records for Action Before Update:" + sCPTFrom
							+ "Records not Displayed in Backend Database before Rule Update" + sQueryResult, true);
				} else {
					verify("Should not Display Records for Action Before Update:" + sCPTFrom
							+ "Records Displayed in Backend Database before Rule Update" + sQueryResult, false);
				}

				break;

			case "NO":
				if (sQueryResult.equalsIgnoreCase(sCPTFrom)) {
					verify("Displayed Records in Backend Database for Action After Update :" + sCPTFrom
							+ "Records Added sucessfully from Backend Database After Update" + sQueryResult, true);
				} else {
					verify("Displayed Records in Backend Database for Action After Update :" + sCPTFrom
							+ "Record not Added in Database after Update" + sQueryResult, false);
				}

				break;
			}
			break;

		}

		return true;

	}

	public void returnRuleResponse(String sRuleFrom, String ButtonName) throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oSeleniumUtils.Click_given_Locator(oHomePage.ReturnRuleResponse);

		oGenericUtils.gfn_Verify_Object_Exist("h3", "Return Rule Response");

		oSeleniumUtils.Enter_given_Text_Element(oHomePage.CPMResponse, ProjectVariables.ReturnRuleComments);

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.SaveReturnRuleResponse);

		oGenericUtils.gfn_Verify_Object_Exist("h3", "WARNING");

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.StartReviewPoPYesBtn);

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		getDriver().switchTo().defaultContent();

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

	}

	@Step
	public void validateReturnReviewComments(String sReturnFrom, String ReturnTo) throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Comments"));

		oGenericUtils.gfn_Click_On_Object("span", "Return Review Comments");

		oGenericUtils.gfn_Verify_Object_Exist("span", "Return Review Comments");

		String sReturnFromTask = getDriver()
				.findElement(By.xpath(oHomePage.GetDynamicXPath("RETURN REVIEW COMMENTS", 2))).getText();

		String sReturnToTask = getDriver().findElement(By.xpath(oHomePage.GetDynamicXPath("RETURN REVIEW COMMENTS", 4)))
				.getText();

		String sReturnComments = getDriver()
				.findElement(By.xpath(oHomePage.GetDynamicXPath("RETURN REVIEW COMMENTS", 5))).getText();

		String sResponseComments = getDriver()
				.findElement(By.xpath(oHomePage.GetDynamicXPath("RETURN REVIEW COMMENTS", 6))).getText();

		if (sReturnFromTask.equalsIgnoreCase(sReturnFrom)) {

			verify("Expected Value is :" + sReturnFrom + "Acutal Return From Task is: " + sReturnFromTask, true);
		} else {
			verify("Expected Value is :" + sReturnFrom + "Acutal Return From Task is: " + sReturnFromTask, false);

		}

		if (sReturnToTask.equalsIgnoreCase(ReturnTo)) {

			verify("Expected Value is :" + ReturnTo + "Acutal Return From Task is: " + sReturnToTask, true);
		} else {
			verify("Expected Value is :" + ReturnTo + "Acutal Return From Task is: " + sReturnToTask, false);

		}

		if (sReturnComments.equalsIgnoreCase(ProjectVariables.ReturnComments)) {
			verify("Expected Value is :" + ProjectVariables.ReturnRuleComments + "Acutal Return From Task is: "
					+ sReturnComments, true);
		} else {
			verify("Expected Value is :" + ProjectVariables.ReturnRuleComments + "Acutal Return From Task is: "
					+ sReturnComments, false);

		}

		if (sResponseComments.indexOf("testing") >= 0) {
			verify("Expected Value is :" + "[iht_ittest01] testing" + "Acutal Return From Task is: "
					+ sResponseComments, true);
		} else {
			verify("Expected Value is :" + "[iht_ittest01] testing" + "Acutal Return From Task is: "
					+ sResponseComments, false);

		}

		getDriver().switchTo().defaultContent();

	}

	public void validateEditorialChangesInDB() {

		System.out.println(Serenity.sessionVariableCalled("MidRuleVersion").toString());
		String[] sRuleVersion = Serenity.sessionVariableCalled("MidRuleVersion").toString().split("\\.");

		System.out.println("Midrule:  " + sRuleVersion[0]);

		System.out.println("Rule version :  " + sRuleVersion[1]);

		// Execute Query Before selecting Rule from My Tasks
		String sQuery = "select SUB_RULE_DESC from rules.sub_rules " + "where mid_rule_key = '" + sRuleVersion[0]
				+ "'  and rule_version = '" + sRuleVersion[1] + "'";

		System.out.println("Query: " + sQuery);

		String sQueryResult = DBUtils.executeSQLQuery(sQuery);

		System.out.println("Query Result: " + sQueryResult);

		if (sQueryResult.equalsIgnoreCase(ProjectVariables.EditorialChangesDescripiton)) {
			verify("Validate Rule Decription in RMI After Update:" + sQueryResult + "Rule Description is matching",
					true);
		} else {
			verify("Rule Descritpion is not matching : Actual is : " + sQueryResult
					+ "Expected Record should display : " + ProjectVariables.EditorialChangesDescripiton, false);
		}
	}

	public boolean validateEditorialChangesInDB(String sColumnName) {

		List<String> sEditorialTabList = Arrays.asList(sColumnName.split(";"));

		for (int i = 0; i < sEditorialTabList.size(); i++) {

			System.out.println(Serenity.sessionVariableCalled("MidRuleVersion").toString());

			String[] sRuleVersion;

			if (Serenity.sessionVariableCalled("RetireRule-Yes") != null) {

				sRuleVersion = Serenity.sessionVariableCalled("MidRuleNewVersion").toString().split("\\.");

			} else {

				sRuleVersion = Serenity.sessionVariableCalled("MidRuleVersion").toString().split("\\.");
			}

			System.out.println("Midrule:  " + sRuleVersion[0]);

			System.out.println("Rule version :  " + sRuleVersion[1]);

			String sExpTabComments = null;

			switch (sEditorialTabList.get(i)) {

			case "SUB_RULE_DESC":
				sExpTabComments = ProjectVariables.EditorialChangesDescripiton;
				break;

			case "SUB_RULE_NOTES":
				sExpTabComments = ProjectVariables.EditorialChangesNotes;
				break;

			case "SUB_RULE_SCRIPT":
				sExpTabComments = ProjectVariables.EditorialChangesScript;
				break;

			case "SUB_RULE_RATIONALE":
				sExpTabComments = ProjectVariables.EditorialChangesRationale;
				break;

			case "REFERENCE":
				sExpTabComments = ProjectVariables.EditorialChangesReference;
				break;

			}

			// Execute Query Before selecting Rule from My Tasks
			String sQuery = "select " + sEditorialTabList.get(i) + " from rules.sub_rules " + "where mid_rule_key = '"
					+ sRuleVersion[0] + "'  and rule_version = '" + sRuleVersion[1] + "'";

			System.out.println("Query: " + sQuery);

			String sQueryResult = DBUtils.executeSQLQuery(sQuery);

			System.out.println("Query Result: " + sQueryResult);

			if (sQueryResult.trim().equalsIgnoreCase(sExpTabComments.trim())) {

				verify("Validate Rule Decription in RMI After Update:" + sQueryResult + "Rule Description is matching",
						true);
			} else {
				System.out.println("Row Number: " + i);
				verify("Rule Descritpion is not matching : Actual is : " + sQueryResult
						+ "Expected Record should display : " + sExpTabComments, false);
			}
		}

		return true;
	}

	@Step
	public void navigateToIUTasksAndGoToRuleInInstance(String sTaskTab, String sTaskName, String sInstancetype)
			throws InterruptedException {

		oHomePage.closeAllTabs();

		userNavigateToIUInstancesScreen(sTaskTab);

		switch (sTaskTab) {
		case "Admin PO Scrub":

			Assert.assertTrue("Work Queue Validation in My Task is not Displayed as expected", oSeleniumUtils
					.is_WebElement_Displayed(Serenity.sessionVariableCalled("TaskandInstnaceName").toString()));

			break;

		case "GroupTasks":

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.GetDynamicXPathWithString("GROUPTASK TASKTYPE",
					ProjectVariables.FinalMDInstance, sTaskName));

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			oHomePage.go_To_Rule(Serenity.sessionVariableCalled("MidRuleVersion").toString());

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			break;

		default:

			oMyTasksStepdef.clickTaskByInstanceName(sTaskName, sInstancetype);

			oHomePage.go_To_Rule(Serenity.sessionVariableCalled("MidRuleVersion").toString());

		}

	}

	@Step
	public void userClickonClaimTask() throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oGenericUtils.gfn_Click_String_object_Xpath((StringUtil.replace(oHomePage.SelectGridRuleChkbox, "sValue",
				Serenity.sessionVariableCalled("MidRuleVersion").toString())));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oGenericUtils.gfn_Click_On_Object("div", "Claim Tasks");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

	}

	@Step
	public void uploadFileInTestingReview() throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);
		oSeleniumUtils.Click_given_Locator(oHomePage.ChooseFile);

		getDriver().switchTo().defaultContent();

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		GenericUtils.attachFiles();

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);
		oGenericUtils.gfn_Click_On_Object("label", "Attach");

		oGenericUtils.gfn_Verify_Object_Exist("h3", "Success");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		// verify Brat Test Results
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oGenericUtils.gfn_Verify_String_Object_Exist(oHomePage.NamedSetsData);

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.BratTestGrid);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.Save_Brat);

		oGenericUtils.gfn_Verify_Object_Exist("h3", "Success");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		getDriver().switchTo().defaultContent();

	}

	@Step
	public void CPMReassignRule(String sReassignUserID) throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oGenericUtils.gfn_Click_String_object_Xpath(
				oHomePage.GetDynamicXPathWithString("CONTAINS LABEL", "Reassign Rule", ""));

		oGenericUtils.gfn_Verify_Object_Exist("h3", "CPM Reassign");

		oSeleniumUtils.Click_given_Locator(oHomePage.SelectCPMReassign);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils
				.Click_given_Locator((StringUtil.replace(oHomePage.SelectCPMReassignUser, "sValue", sReassignUserID)));

		oSeleniumUtils.Enter_given_Text_Element(oHomePage.ReasonReassign, "TestAutomation");

		oGenericUtils.gfn_Click_On_Object("button", "Reassign Rule");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		getDriver().switchTo().defaultContent();

	}

	public void ADDCodeinMD(String sCategoryCode, String sCode) throws InterruptedException {

		String sArrayCode = null;

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oGenericUtils.gfn_Click_On_Object("label", "Add Code");
		oGenericUtils.gfn_Verify_Object_Exist("h3", "Add Code");

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.StartNewCode);

		oGenericUtils.gfn_Click_On_Object("a", "Start New");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		int iYesBtn = getDriver()
				.findElements(
						By.xpath("//table[@id='Manual_Proposals_grid']//tr[2]//td[2]//select//option[text()='Yes']"))
				.size();

		if (iYesBtn > 0) {

			oSeleniumUtils.Click_given_Locator(
					"//table[@id='Manual_Proposals_grid']//tr[2]//td[2]//select//option[text()='Yes']");
		}

		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
				"//table[@id='Manual_Proposals_grid']//tr[2]//td[2]//select", "Yes");

		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
				oHomePage.GetDynamicXPathWithRowCol("MANUALPROP FIELDS", 2, 3), "Manual Add");

		oSeleniumUtils.Enter_given_Text_Element(("//table[@id='Manual_Proposals_grid']//tr[2]//td[4]//textarea"),
				"Test");

		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
				"//table[@id='Manual_Proposals_grid']//tr[2]//td[9]//select", sCategoryCode);

		// oSeleniumUtils.Click_given_Locator("//table[@id='Manual_Proposals_grid']//tr[2]//td[9]//select//option[text()='"
		// + sCategoryCode + "']");

		oSeleniumUtils.Enter_given_Text_Element(("//table[@id='Manual_Proposals_grid']//tr[2]//td[6]//input"), sCode);

		if (Serenity.sessionVariableCalled("POS CODE") != null) {

			if (Serenity.sessionVariableCalled("POS CODE").toString().contains("POS")) {

				String sPosCode = StringUtils.substringAfter(Serenity.sessionVariableCalled("POS CODE").toString(),
						"-");

				oSeleniumUtils.Enter_given_Text_Element(
						("//td[@aria-describedby='Manual_Proposals_grid_interpRuleCpt.posLike']//input"), sPosCode);
			}

		}

		if (Serenity.sessionVariableCalled("Override Flag") != null) {

			if (Serenity.sessionVariableCalled("Override Flag").toString().contains("OverrideFlag-Yes")) {

				verify("OVerride flag should be unchecked", !oSeleniumUtils.is_WebElement_Selected(
						"//td[@aria-describedby='Manual_Proposals_grid_interpRuleCpt.override']//input"));

				oSeleniumUtils.Click_given_Locator(
						"//td[@aria-describedby='Manual_Proposals_grid_interpRuleCpt.override']//input");

			}
		}

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oGenericUtils.gfn_Click_On_Object("label", "Save");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		String arr[] = { "0075T", "0009T", "0006T", "0007T", "0004T" };

		for (int i = 0; i < arr.length; i++) {

			sArrayCode = arr[i];

			int iErrorMsg = getDriver().findElements(By.xpath(StringUtil.replace(oHomePage.pTag, "sValue",
					" One or more proposals already exists. Please review duplicates and save."))).size();

			if (iErrorMsg > 0) {

				oGenericUtils.gfn_Click_On_Object("button", "Ok");
				oSeleniumUtils.Enter_given_Text_Element(("//table[@id='Manual_Proposals_grid']//tr[2]//td[6]//input"),
						sArrayCode);
				oGenericUtils.gfn_Click_On_Object("label", "Save");

			}

			int iMsg = getDriver().findElements(By.xpath(StringUtil.replace(oHomePage.h3Tag, "sValue", "Success")))
					.size();

			if (iMsg > 0) {
				break;
			}

		}

		oGenericUtils.gfn_Verify_Object_Exist("h3", "Success");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		String sCPTValue = oSeleniumUtils
				.get_TextFrom_Locator("//table[@id='Manual_Proposals_grid']//tr[2]//td[6]//input");

		Serenity.setSessionVariable("CPTValue").to(sCPTValue);
		Serenity.setSessionVariable("CategoryCode").to(sCategoryCode);
		Serenity.setSessionVariable("AddCodeMD").to(sCPTValue);

		System.out.println("CategoryCode: " + Serenity.sessionVariableCalled("CategoryCode").toString());
		System.out.println("Add Code in MD: " + Serenity.sessionVariableCalled("AddCodeMD").toString());

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void clickonGenerateSummariesValidateMessage(String sWorkQueue) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		if ((oSeleniumUtils.is_WebElement_Present(oHomePage.Generate_Summaries))) {
			oSeleniumUtils.Click_given_Locator(oHomePage.Generate_Summaries);
		} else {
			oGenericUtils.gfn_Click_On_Object("label", "Generate Summaries");
		}

		oGenericUtils.gfn_Verify_Object_Exist("p",
				" Billed With AND/Billed Without OR logic needs to be completed before generating summaries.");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void validateBOBWConfigLinkandSubmit() throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		int iBOBWConfigLink = getDriver()
				.findElements(By.xpath(StringUtil.replace(oHomePage.AnchorTag, "sValue", "BW And/BWO or Config Logic")))
				.size();

		if (iBOBWConfigLink > 0) {
			verify("BOBW Config Link is Exist ", true);
		} else {
			verify("BOBW Config Link is NOT Exist ", false);

		}

		oGenericUtils.gfn_Click_On_Object("a", "BW And/BWO or Config Logic");

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		int iBOBWScreen = getDriver()
				.findElements(By.xpath(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Submit"))).size();

		if (iBOBWScreen > 0) {
			verify("BOBW Screen is Displayed ", true);
		} else {
			verify("BOBW Screen is NOT Displayed ", false);

		}

		List<WebElement> iBOBWRadioBtnCount = getDriver()
				.findElements(By.xpath("//div[@class='col-md-12 table_data ng-scope']//label//input"));

		for (int i = 0; i < iBOBWRadioBtnCount.size(); i++) {

			// String status_Config_Btn =
			// getDriver().findElement(By.xpath(oHomePage.Config)).getAttribute("disabled");

			String sChkBtnStatus = iBOBWRadioBtnCount.get(i).getAttribute("disabled");

			if (sChkBtnStatus != "true") {

				iBOBWRadioBtnCount.get(i).click();

			}
			System.out.println(sChkBtnStatus);

		}

		oGenericUtils.gfn_Verify_Object_Exist("button", "Submit");
		oGenericUtils.gfn_Click_On_Object("button", "Submit");
		oGenericUtils.gfn_Verify_Object_Exist("h3", "Success");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void userModifyCode(String arg1, String arg2, String sDecision) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		switch (arg1) {

		case "PO MANUAL PROPOSALS":
			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
					oHomePage.GetDynamicXPathWithRowCol("MANUALPROP FIELDS", 2, 3), sDecision);
			SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
			oSeleniumUtils.Enter_given_Text_Element(("//table[@id='Manual_Proposals_grid']//tr[2]//td[4]//textarea"),
					"Auto Test");

			break;

		case "CPM MANUAL PROPOSALS":

			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
					"//table[@id='cpmManualProposalGrid']//tr[2]//td[4]//select", sDecision);

			break;

		case "CPM PROPOSALS":

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "CPM Decisions"));

			int oProposalsList = getDriver()
					.findElements(By
							.xpath("//table[@id='cpmProposalGrid']//tbody//tr//following-sibling::tr//input[@type='checkbox' and @role='checkbox']"))
					.size();

			if (oProposalsList > 0) {

				for (int i = 1; i <= oProposalsList; i++) {

					SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

					oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
							"//select[@id='" + i + "_cpmDecisionAction.decisionActionKey']", sDecision);

					oSeleniumUtils.Enter_given_Text_Element("//textarea[@id='" + i + "_cpmInterpComment.comments']",
							"Automation Testing");
				}

			} else {

				oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
						"//select[@id='1_cpmDecisionAction.decisionActionKey']", sDecision);

				oSeleniumUtils.Enter_given_Text_Element("//textarea[@id='1_cpmInterpComment.comments']",
						"Automation Testing");
			}

			break;
		}

		oGenericUtils.gfn_Click_On_Object("label", "Save");
		oGenericUtils.gfn_Verify_Object_Exist("h3", "Success");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		getDriver().switchTo().defaultContent();

	}

	@Step
	public void clickOnCPMValidateErrMsg(String arg1) {

		int iBOBWScreen = getDriver()
				.findElements(By.xpath(StringUtil.replace(oHomePage.pTag, "sValue",
						" Billed With AND/Billed Without OR logic needs to be completed before CPM Decision Complete.")))
				.size();

		if (iBOBWScreen > 0) {
			verify("BOBW CPM Error Screen is Displayed ", true);
		} else {
			verify("BOBW CPM Error Screen is NOT Displayed ", false);

		}

	}

	@Step
	public void validateUserBWBWOLink(String arg1, String arg2) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		int iBOBWConfigLink = getDriver()
				.findElements(By.xpath(StringUtil.replace(oHomePage.AnchorTag, "sValue", "BW And/BWO or Config Logic")))
				.size();

		switch (arg1) {

		case "CPM":

			if (iBOBWConfigLink > 0) {
				verify("BOBW Config Link is Exist ", true);
			} else {
				verify("BOBW Config Link is NOT Exist ", false);

			}

			oGenericUtils.gfn_Click_On_Object("a", "BW And/BWO or Config Logic");

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			int iBOBWScreen = getDriver()
					.findElements(By.xpath(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Submit"))).size();

			if (iBOBWScreen > 0) {
				verify("BOBW Screen is Displayed ", true);
			} else {
				verify("BOBW Screen is NOT Displayed ", false);

			}

			List<WebElement> iBOBWUser = getDriver()
					.findElements(By.xpath("//span[text()='(Updated By the User: iht_ittest01)']"));

			for (int i = 0; i < iBOBWUser.size(); i++) {

				if (iBOBWUser.get(i).getText().contains(arg2)) {
					verify("Updated user is displayed in BOBW Screen", true);
				} else {
					verify("Updated user is NOT displayed in BOBW Screen", false);

				}
			}

			oGenericUtils.gfn_Click_On_Object("button", "Cancel");
			break;

		case "NOLINK":

			WebElement iBOBWConfigLink2 = getDriver().findElement(
					By.xpath(StringUtil.replace(oHomePage.AnchorTag, "sValue", "BW And/BWO or Config Logic")));

			if (!(iBOBWConfigLink2.isDisplayed())) {

				verify("BOBW Config Link Should not displayed ", true);

			} else {
				verify("BOBW Config Link is Exist ", false);

			}

			break;

		}

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void clickonRequirePresentation(String arg1) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
		oSeleniumUtils.SwitchToFrame(oHomePage.frame);
		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "CPM Decisions"));
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		String sRPPayer = "";
		String sRequirePresentationPayer = "";
		switch (arg1) {

		case "StatusValidate":

			oSeleniumUtils
					.Click_given_Locator(StringUtil.replace(oHomePage.LabelTag, "sValue", "Requires Presentation"));
			oGenericUtils.gfn_Verify_Object_Exist("h4", "CPM: Change Requires Presentation Status");
			String status_Require_Presentation_Chkbox = getDriver()
					.findElement(By.xpath("(//table[@id='requirePresentationGrid'])//tr[2]"))
					.getAttribute("aria-selected");

			if (status_Require_Presentation_Chkbox != "true") {
				verify("Require Presentation status is checked", true);
			} else {
				verify("Require Presentation status is NOT checked", false);
			}
			oSeleniumUtils.Click_given_Locator(oHomePage.RequirePresentCancel);

			break;

		case "SELECT":
		case "UNSELECT":

			oSeleniumUtils
					.Click_given_Locator(StringUtil.replace(oHomePage.LabelTag, "sValue", "Requires Presentation"));
			oGenericUtils.gfn_Verify_Object_Exist("h4", "CPM: Change Requires Presentation Status");

			oSeleniumUtils.Click_given_WebElement(oHomePage.SelectRequirePresent1);

			sRequirePresentationPayer = getDriver()
					.findElement(By.xpath("((//table[@id='requirePresentationGrid']//tr)[2])//td[3]"))
					.getAttribute("title");

			System.out.print(sRequirePresentationPayer);

			Serenity.setSessionVariable("sRequirePresentationPayer").to(sRPPayer);

			oSeleniumUtils.Click_given_Locator(oHomePage.Save_Button);

			oGenericUtils.gfn_Verify_Object_Exist("p",
					"All the decisions recorded for the payer will be lost and sensitivities will be refreshed once a payer is set to Requires Presentation.");

			oSeleniumUtils.Click_given_Locator(oHomePage.Continue_Button);

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			break;

		case "SELECTFIRST":
		case "UNSELECTFIRST":

			oSeleniumUtils
					.Click_given_Locator(StringUtil.replace(oHomePage.LabelTag, "sValue", "Requires Presentation"));
			oGenericUtils.gfn_Verify_Object_Exist("h4", "CPM: Change Requires Presentation Status");

			oSeleniumUtils.Click_given_WebElement(oHomePage.SelectFirstRequirePresent);

			sRequirePresentationPayer = getDriver()
					.findElement(By.xpath("((//table[@id='requirePresentationGrid']//tr)[2])//td[3]"))
					.getAttribute("title");

			System.out.print(sRequirePresentationPayer);

			Serenity.setSessionVariable("sRequirePresentationPayer").to(sRPPayer);

			System.out.println(Serenity.sessionVariableCalled("sRequirePresentationPayer").toString());

			verify("Selected Payer is  :" + Serenity.sessionVariableCalled("sRequirePresentationPayer").toString(),
					true);

			// verify("Selected Rule is :" +
			// Serenity.sessionVariableCalled("MidRuleVersion").toString(),
			// true);

			oSeleniumUtils.Click_given_Locator(oHomePage.Save_Button);

			oGenericUtils.gfn_Verify_Object_Exist("p",
					"All the decisions recorded for the payer will be lost and sensitivities will be refreshed once a payer is set to Requires Presentation.");

			oSeleniumUtils.Click_given_Locator(oHomePage.Continue_Button);

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			break;

		case "UNSELECT2":

			oSeleniumUtils
					.Click_given_Locator(StringUtil.replace(oHomePage.LabelTag, "sValue", "Requires Presentation"));
			oGenericUtils.gfn_Verify_Object_Exist("h4", "CPM: Change Requires Presentation Status");

			oSeleniumUtils.Click_given_WebElement(oHomePage.SelectRequirePresent1);

			oSeleniumUtils.Click_given_Locator(oHomePage.Save_Button);

			oGenericUtils.gfn_Verify_Object_Exist("p",
					"All the decisions recorded for the payer will be lost and sensitivities will be refreshed once a payer is set to Requires Presentation.");

			oSeleniumUtils.Click_given_Locator(oHomePage.Continue_Button);

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			break;

		default:

			oGenericUtils.gfn_Click_On_Object("label", "Requires Presentation");
			oGenericUtils.gfn_Verify_Object_Exist("h4", "CPM: Change Requires Presentation Status");
			// oGenericUtils.gfn_Click_String_object_Xpath("(//table[@id='requirePresentationGrid']//input)[1]");
			oGenericUtils.gfn_Click_String_object_Xpath("//input[@id='cb_requirePresentationGrid']");

			Serenity.setSessionVariable("CPMReviewPayer")
					.to(oSeleniumUtils.get_TextFrom_Locator(oHomePage.RequirePresentReviewPayer));

			oSeleniumUtils.Click_given_Locator(oHomePage.RequirePresentSave);
			oGenericUtils.gfn_Verify_Object_Exist("p",
					"All the decisions recorded for the payer will be lost and sensitivities will be refreshed once a payer is set to Requires Presentation.");
			oSeleniumUtils.Click_given_Locator(oHomePage.Continue_Button);

			// oGenericUtils.gfn_Click_On_Object("button", "Ok");

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);
			// oGenericUtils.gfn_Click_On_Object("button", "Ok");

			break;
		}

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void validate_editorial_update_popup(String sTab) throws InterruptedException {

		oHomePage.Click_on_Editorial();

		List<String> sTabs = Arrays.asList(sTab.split(";"));

		for (int i = 0; i < sTabs.size(); i++) {

			if (sTabs.get(i).equalsIgnoreCase("New Rule Description")) {

				oGenericUtils.gfn_Click_On_Object("span", "Description");

			} else {
				oGenericUtils.gfn_Click_On_Object("span", sTabs.get(i));
			}

			validatePopUpTabsInEditorial(sTabs.get(i));

			perfromEditorialActions("Reset", sTabs.get(i));

			validatePopUpTabsInEditorial(sTabs.get(i));

			perfromEditorialActions("Refresh", sTabs.get(i));

			validatePopUpTabsInEditorial(sTabs.get(i));

			perfromEditorialActions("Cancel", sTabs.get(i));

		}

		oGenericUtils.gfn_Click_On_Object("span", "Description");

		validatePopUpTabsInEditorial("New Rule Description");

		oGenericUtils.gfn_Click_On_Object("label", "Update Resolved Description");

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		String sResolvedRuleDescription = oHomePage.ResolvedRuleDescription.getAttribute("value");

		int cResolvedRuleDescription = sResolvedRuleDescription.length();

		String sResolvedRuleCharacterCount = oSeleniumUtils.get_TextFrom_Locator(oHomePage.ResolvedRuleCharacterCount);

		int iResolvedRuleCharacterCount = Integer.parseInt(sResolvedRuleCharacterCount);

		verify("Resolved Rule Description char count " + iResolvedRuleCharacterCount
				+ " is matching with length of data present in resolved rule description" + cResolvedRuleDescription,
				iResolvedRuleCharacterCount == cResolvedRuleDescription);

		oSeleniumUtils.Click_given_Locator(
				StringUtil.replace(oHomePage.No_Edit_Required, "sValue", "No Editorial Changes Required"));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		verify("Confirmation Warning should be displayed", oSeleniumUtils.is_WebElement_Displayed(
				StringUtil.replace(oHomePage.pTag, "sValue", ProjectVariables.Editorial_MSG_NC_Required)));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.Click_given_WebElement(oHomePage.Confirm_Yes);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		verify("Confirmation Warning should be displayed", oSeleniumUtils.is_WebElement_Displayed(
				StringUtil.replace(oHomePage.pTag, "sValue", ProjectVariables.Editorial_MSG_Editorial_Saved)));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		WebElement oUnsetNoEditorialChangesRequiredbutton = getDriver().findElement(
				By.xpath(StringUtils.replace(oHomePage.LabelTag, "sValue", "Unset No Editorial Changes Required")));

		verify("Unset No Editorial Changes Required button", oUnsetNoEditorialChangesRequiredbutton.isDisplayed());

		getDriver().close();

		SeleniumUtils.switchWindowUsingWindowCount(3, 1, getDriver());

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void validatePopUpTabsInEditorial(String sValue) {

		WebElement oEditorialDescription = getDriver()
				.findElement(By.xpath(StringUtils.replace(oHomePage.EditDescriptioninEditorial, "sValue", sValue)));

		String sEditNewRuleDescription = oEditorialDescription.getAttribute("value");

		Serenity.setSessionVariable("EditNewRuleDescription").to(sEditNewRuleDescription.replaceAll("\\s", ""));

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.Enter_given_Text_Element(oEditorialDescription, "Test");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		WebElement oMarkupText = getDriver()
				.findElement(By.xpath(StringUtils.replace(oHomePage.MarkUpinEditorial, "sValue", sValue)));

		verify("Markup Text is displayed for " + sValue + ":-" + oMarkupText.getText().trim() + "Edit" + sValue + ":-"
				+ sEditNewRuleDescription.trim(),
				sEditNewRuleDescription.replaceAll("\\s", "")
						.equalsIgnoreCase(oMarkupText.getText().replaceAll("\\s", "")));

		Serenity.setSessionVariable("MarkupText").to(oMarkupText.getText().replaceAll("\\s", ""));

		WebElement oUpdatedText = getDriver()
				.findElement(By.xpath(StringUtils.replace(oHomePage.UpdatedTextinEditorial, "sValue", sValue)));

		verify("Updated Text is displayed for" + sValue,
				"Test".toString().equalsIgnoreCase(oUpdatedText.getText().trim()));

	}

	@Step
	public void perfromEditorialActions(String sAction, String sValue) throws InterruptedException {

		switch (sAction) {
		case "Refresh":

			oSeleniumUtils
					.Click_given_Locator(oHomePage.GetDynamicXPathWithString("EditorialActions", sValue, sAction));

			oGenericUtils.gfn_Click_On_Object("button", "Refresh Data");

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			verify("After refreshing same text appears in edit description" + sValue,
					Serenity.sessionVariableCalled("MarkupText").toString()
							.equalsIgnoreCase(Serenity.sessionVariableCalled("EditNewRuleDescription")));

			break;
		case "Reset":

			oSeleniumUtils
					.Click_given_Locator(oHomePage.GetDynamicXPathWithString("EditorialActions", sValue, sAction));

			oGenericUtils.gfn_Click_On_Object("button", "Reset Data");

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			verify("After Reseting same text appears in edit description" + sValue,
					Serenity.sessionVariableCalled("MarkupText").toString()
							.equalsIgnoreCase(Serenity.sessionVariableCalled("EditNewRuleDescription")));

			break;
		case "Cancel":

			oGenericUtils.gfn_Click_On_Object("label", "Cancel");

			oSeleniumUtils.Click_given_Locator(oHomePage.EditorialPopYesBtn);

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			verify("After cancel same text appears in edit description" + sValue,
					Serenity.sessionVariableCalled("MarkupText").toString()
							.equalsIgnoreCase(Serenity.sessionVariableCalled("EditNewRuleDescription")));

			break;

		default:
			Assert.assertTrue("No matching case", false);

		}

	}

	@Step
	public void user_navigate_to_group_task_IU_screen() {

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.GroupTaskTab);

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

	}

	@Step
	public void click_task_by_instance_name_in_group_tasks_tab(String sTask, String FilterValue)
			throws InterruptedException {

		Serenity.setSessionVariable("IUInstanceName").to(ProjectVariables.FinalMDInstance);

		List<WebElement> oTaskNames = getDriver().findElements(By.xpath("//tr[(td[2]='" + sTask + "')]//td[2]//div"));

		for (int i = 0; i < oTaskNames.size(); i++) {

			if (oTaskNames.get(i).getText().equalsIgnoreCase(sTask)) {

				WebElement oTaskName1 = getDriver()
						.findElement(By.xpath("//tr[(td[2]='" + sTask + "')][" + (i + 1) + "]//td[2]//div"));

				String sGroupTaskInstanceName = oSeleniumUtils
						.get_TextFrom_Locator("//tr[(td[2]='" + sTask + "')][" + (i + 1) + "]//td[3]//div");

				Serenity.setSessionVariable("Pool").to(sGroupTaskInstanceName);

				if (sGroupTaskInstanceName.equalsIgnoreCase(ProjectVariables.FinalMDInstance)) {

					oTaskName1.click();

					oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

					break;
				}

			}

		}

		if (FilterValue.equalsIgnoreCase("EditorialPool")) {

			applyActiveFilterMyTaskRuleWorkQueue("Assignees", FilterValue);

			String str = StringUtils.substringBefore(FilterValue, "Pool");

			applyActiveFilterMyTaskRuleWorkQueue("Task", str + " Review");

			String sRuleText = oSeleniumUtils.get_TextFrom_Locator(
					"(//div[@class='GEFT4QHBMHC'])[4]//tr[@class='GEFT4QHBK3C GEFT4QHBFN'][1]//td[@cellindex='1']//div");

			Serenity.setSessionVariable("PoolRuleText").to(sRuleText);

			WebElement sRuleCheckbox = getDriver().findElement(By.xpath(
					("(//div[@class='GEFT4QHBMHC'])[4]//tr[@class='GEFT4QHBK3C GEFT4QHBFN'][1]//td[@cellindex='0']//div[1]//div")));

			sRuleCheckbox.click();

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Click_On_Object("div", "Claim Tasks");

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			oHomePage.closeAllTabs();
		}

	}

	@Step
	public void validate_multiple_rule_id() throws InterruptedException {

		oHomePage.go_To_Rule(Serenity.sessionVariableCalled("MidRuleVersion"));
		// oHomePage.go_To_Rule(Serenity.sessionVariableCalled("PoolRuleText"));

		WebElement oRuleVersion = getDriver()
				.findElement(By.xpath("//div[text()='" + Serenity.sessionVariableCalled("MidRuleVersion") + "']"));

		/*
		 * WebElement oRuleVersion = getDriver()
		 * .findElement(By.xpath("//div[text()='" +
		 * Serenity.sessionVariableCalled("PoolRuleText") + "']"));
		 */
		verify("Rule Value is displayed", oRuleVersion.isDisplayed());

	}

	@Step
	public void validateCPMAndMDDecision() throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
		oSeleniumUtils.SwitchToFrame(oHomePage.frame);
		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "CPM Decisions"));

		String sCPMDecision = oSeleniumUtils
				.get_FirstSelected_Option_In_DropDown("(//table[@id='cpmProposalGrid'])//tr[2]//td[4]//select");

		System.out.println(sCPMDecision);

		String sMDRecommendation = oSeleniumUtils
				.get_TextFrom_Locator((oHomePage.GetDynamicXPath("CPM SYSTEMPROPOSAL", 7)));
		System.out.println(sMDRecommendation);

		Assert.assertTrue("Default value in Referenced Conditoned code",
				sMDRecommendation.equalsIgnoreCase(oSeleniumUtils.get_FirstSelected_Option_In_DropDown(
						"(//table[@id='cpmProposalGrid'])//tr[2]//td[4]//select")));

		if (sCPMDecision.equalsIgnoreCase(sMDRecommendation)) {
			verify("CPM Decisions and MD Recommendation should be same", true);
		} else {
			verify("CPM Decision and MD Recommendation are NOT SAME" + "Actual MD Recommendation is : "
					+ sMDRecommendation, false);

		}

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void validatePayerinCPMProposal(String sCase, String sDecision) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

		switch (sCase) {

		case "Payer CPM Proposals":
			oSeleniumUtils.SwitchToFrame(oHomePage.frame);
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "CPM Decisions"));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.CPMDecisionsLists, "sValue", "By Payer"));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			List<WebElement> iPayersCount = getDriver()
					.findElements(By.xpath("//td[@aria-describedby='cpmProposalGrid_1_t_payerShort']"));

			for (int i = 0; i < iPayersCount.size(); i++) {
				System.out.println(iPayersCount.get(i).getText());

				String sText = Serenity.sessionVariableCalled("sRPPayer");

				if (!(iPayersCount.get(i).getText().equals(sText))) {

					verify(sText + " Payer is not displayed ", true);

				} else {

					verify(Serenity.sessionVariableCalled("sRPPayer")
							+ " Payer is displayed which should not be displayed", false);

				}
			}

			oSeleniumUtils.Click_given_Locator("(//table[@id='cpmProposalGrid'])//tr[2]//td[5]//select");
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.CPMDecisionsLists, "sValue", "All"));
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.CPMSystemPropsalSave);
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			getDriver().switchTo().defaultContent();

			break;

		case "Presentation Warning":
			oSeleniumUtils.SwitchToFrame(oHomePage.frame);
			SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "CPM Summaries"));
			SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
			oGenericUtils.gfn_Verify_String_Object_Exist(oHomePage.CPMReviewPayerWarning);
			getDriver().switchTo().defaultContent();

			break;

		}

	}

	@Step
	public void selDifferentDecisionAtCPMPayerLevel() throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "CPM Decisions"));

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.CPMDecisionsLists, "sValue", "By Payer"));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		List<WebElement> iCPMDecisionCount = getDriver().findElements(
				By.xpath("//td[@aria-describedby='cpmProposalGrid_1_t_decisionAction.decisionActionKey']"));

		String arr[] = { "NO ACTION", "REMOVE" };

		for (int i = 0; i < iCPMDecisionCount.size(); i++) {

			System.out.println(iCPMDecisionCount.get(i).getText());

			iCPMDecisionCount.get(i).click();

			iCPMDecisionCount.get(i).click();

			int j = i + 1;

			String sRandomDecesion = arr[0];

			System.out.println((StringUtil.replace(oHomePage.GetDynamicXPath("CPM SYSTEMPROPOSAL2", 1), "sValue",
					sRandomDecesion)));

			oSeleniumUtils.Click_given_Locator(
					StringUtil.replace(oHomePage.GetDynamicXPath("CPM SYSTEMPROPOSAL2", j), "sValue", sRandomDecesion));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oSeleniumUtils.Enter_given_Text_Element(oHomePage.GetDynamicXPath("CPM PAYERSPEC COMMENTS", j),
					"Test Auto Comments");

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			arr[0] = arr[1];

		}

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.CPMSystemPropsalSave);

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		getDriver().switchTo().defaultContent();
	}

	@Step
	public void setCPMDecisionAsMDRecommend() throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "CPM Decisions"));

		String sMDRecommendation = oSeleniumUtils
				.get_TextFrom_Locator(oHomePage.GetDynamicXPath("CPM SYSTEMPROPOSAL", 7));

		System.out.println(sMDRecommendation);

		oSeleniumUtils.Click_given_Locator(oHomePage.GetDynamicXPath("CPM SYSTEMPROPOSAL", 4));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils
				.Click_given_Locator((StringUtil.replace(oHomePage.CPMDecisionList, "sValue", sMDRecommendation)));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		/*
		 * oSeleniumUtils.Click_given_Locator(oHomePage.GetDynamicXPath(
		 * "CPM SYSTEMPROPOSAL", 5));
		 * 
		 * SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		 * 
		 * oSeleniumUtils
		 * .Click_given_Locator((StringUtil.replace(oHomePage.CPMDecisionList,
		 * "sValue", "All")));
		 */

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.CPMDecisionsLists, "sValue", "All"));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.Enter_given_Text_Element("//textarea[@id='1_cpmInterpComment.comments']", "Test CPM Comments");

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.CPMSystemPropsalSave);

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		getDriver().switchTo().defaultContent();
	}

	@Step
	public void selectMultiPayerRuleNoAssignee(int sPayerQuantity) throws InterruptedException {

		List<WebElement> sRowCount = getDriver()
				.findElements(By.xpath("(//table[@class = 'GEFT4QHBI3C'])[last()]/tbody[2]/tr"));

		ArrayList<String> sArrPayers = new ArrayList<String>();

		for (int i = 0; i < sRowCount.size(); i++) {

			String sXPayer = oHomePage.GetDynamicXPath("RULE PAYERS", i + 1);
			String sPayer = getDriver().findElement(By.xpath(sXPayer)).getText();
			String[] ArrPayers = sPayer.split(",");

			if (ArrPayers.length > sPayerQuantity) {

				String sXRule = oHomePage.GetDynamicXPath("RULE VERSION", i + 1);
				String sRule = getDriver().findElement(By.xpath(sXRule)).getText();
				Serenity.setSessionVariable("MidRuleVersion").to(sRule);

				verify("MidRule Version is :" + Serenity.sessionVariableCalled("MidRuleVersion").toString(), true);
				System.out.println("Rule No : " + Serenity.sessionVariableCalled("MidRuleVersion").toString());

				for (int j = 0; j < ArrPayers.length; j++) {
					String sAllPayers = ArrPayers[j].trim().split(" ")[0];
					System.out.println(sAllPayers);

					sArrPayers.add(sAllPayers);
				}
				break;

			}
		}
	}

	@Step
	public void navigateToAdminRuleReviewApplyFilters(String sRole, String sInstanceType, String sColoumnName,
			String sColumnValue) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oHomePage.closeAllTabs();

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oGenericUtils.gfn_Click_On_Object("span", "Administration");

		oGenericUtils.gfn_Click_On_Object("a", "Interpretive Update Instances");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		clickInstanceinAdmin(sInstanceType);

		applyAdminActiveFilters(sColoumnName, sColumnValue);

	}

	@Step
	public void navigate_And_Validate_OVerviewReport() throws InterruptedException {

		oGenericUtils.gfn_Click_On_Object("div", "Overview");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		verify("Not Started Link displayed sucessfully", oGenericUtils.gfn_Verify_Object_Exist("a", "Not Started"));

		verify("Started Link displayed sucessfully", oGenericUtils.gfn_Verify_Object_Exist("a", "Started"));

		verify("Completed Link displayed sucessfully", oGenericUtils.gfn_Click_On_Object("a", "Completed"));

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		String oCount = oSeleniumUtils.get_TextFrom_Locator("//a[text()='Expand all']/following-sibling::div[1]");

		Serenity.setSessionVariable("TotalNoofRules").to(oCount);

		System.out.println(
				"Previous Count of Completed rules" + Serenity.sessionVariableCalled("TotalNoofRules").toString());

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void get_rule_from_overview_report() throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		verify("Not Started Link clicked sucessfully", oGenericUtils.gfn_Click_On_Object("a", "Not Started"));

		verify("Collape All Button is clicked",
				oSeleniumUtils.Click_given_Locator(StringUtils.replace(oHomePage.AnchorTag, "sValue", "Collapse all")));

		verify("Expand All Button is clicked",
				oSeleniumUtils.Click_given_Locator(StringUtils.replace(oHomePage.AnchorTag, "sValue", "Expand all")));

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		WebElement oele = getDriver().findElement(By.xpath("(//a[text()='Collapse all']/..//span)[7]"));

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		if (oele.isDisplayed()) {

			verify("ExpandReason clicked sucessfully",
					oGenericUtils.gfn_Click_String_object_Xpath("(//a[text()='Collapse all']/..//span)[7]"));

		}

		oGenericUtils.gfn_Verify_String_Object_Exist("//tr[@ng-repeat='node in node.details'][1]//td//button");

		String sRule = oSeleniumUtils.get_TextFrom_Locator("//tr[@ng-repeat='node in node.details'][1]//td//button");

		Serenity.setSessionVariable("MidRuleVersion").to(sRule);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void validate_rule_and_completed_count_in_overview_report() throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		verify("Completed Link clicked sucessfully", oGenericUtils.gfn_Click_On_Object("a", "Completed"));

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		String sCompletedCount = oSeleniumUtils
				.get_TextFrom_Locator("//a[text()='Expand all']/following-sibling::div[1]");

		int iPeviousCount = Integer.parseInt(Serenity.sessionVariableCalled("TotalNoofRules"));

		int iCompletedCount = Integer.parseInt(sCompletedCount);

		System.out.println("Prasent Completed Count" + iCompletedCount);

		if (iCompletedCount > iPeviousCount) {

			verify("Completed count is increased sucessfully", true);

		} else {

			verify("Completed count is not increased sucessfully", false);
		}
	}

	@Step
	public void select_rule_and_validate_ruleid_tab() throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		verify("Overview Rule clicked sucessfully",
				oGenericUtils.gfn_Click_String_object_Xpath("//tr[@ng-repeat='node in node.details'][1]//td//button"));

		System.out.println("selected RuleID Tab is displayed successfully"
				+ Serenity.sessionVariableCalled("MidRuleVersion").toString());

		getDriver().switchTo().defaultContent();

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		verify("selected RuleID Tab is displayed successfully",
				oGenericUtils.gfn_Verify_String_Object_Exist(
						(("//span[@class='x-tab-strip-inner']//span[@class='x-tab-strip-text  ' and text()='"
								+ Serenity.sessionVariableCalled("MidRuleVersion").toString() + "']"))));

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void validate_overview_ruleid_tab() throws InterruptedException {

		verify("selected RuleID Tab is displayed successfully",
				oGenericUtils.gfn_Verify_String_Object_Exist("//span[@class='x-tab-strip-text  ' and text()='"
						+ Serenity.sessionVariableCalled("MidRuleVersion").toString() + "']"));
	}

	@Step
	public void authorize_decisions_and_validate_warning_message() throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Summaries"));

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oGenericUtils.gfn_Click_String_object_Xpath(("//label[contains(text(),'Authorize Decisions')]"));

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		verify("Authorize Decisions Warning Message validation",
				oGenericUtils.gfn_Verify_Object_Exist("p", ProjectVariables.sPrelimMDWarningEditorialNotCompleted));

		verify("Yes Button is Clicked", oGenericUtils.gfn_Click_On_Object("button", "Yes"));

		verify("Ok button is Clicked", oGenericUtils.gfn_Click_On_Object("button", "Ok"));

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void retireRule() throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Summaries"));

		if (oGenericUtils.gfn_Verify_Object_Exist("span", "Retire Rule") == true) {

			oGenericUtils.gfn_Click_On_Object("span", "Retire Rule");

			oSeleniumUtils.select_Given_Value_From_DropDown(oHomePage.Retirerule_MDecision, "Do Not Retire Rule");

			oSeleniumUtils.Enter_given_Text_Element(oHomePage.MD_Comments, ProjectVariables.TestComments);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oGenericUtils.gfn_Click_On_Object("button", "Save");

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			oGenericUtils.gfn_Click_On_Object("span", "Retire Rule");

		}

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void validate_MD_Comments_No_Decision(String arg) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Comments"));

		if (oGenericUtils.gfn_Verify_Object_Exist("span", arg) == true) {

			oGenericUtils.gfn_Click_On_Object("span", arg);
		}

		oSeleniumUtils.Enter_given_Text_Element(StringUtil.replace(oHomePage.Final_MD_Comments, "sValue", arg),
				ProjectVariables.loMDTestComments);

		oGenericUtils.gfn_Click_On_Object("label", "Save");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Decisions"));

		oSeleniumUtils.Click_given_Locator(oHomePage.NoDecision);

		oGenericUtils.gfn_Click_On_Object("button", "Yes");

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void validate_cpm_decision_complete_alert(String sDecision) throws InterruptedException {

		String sAlert_Message = "PO has taken 'No Decision' on the rule. Please make sure you reviewed the PO comments on 'Comments' tab. Click 'Yes' to continue and 'No' to review PO comments.";

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "CPM Decisions"));

		oGenericUtils.gfn_Click_On_Object("label", "CPM Decision Complete");

		oGenericUtils.gfn_Verify_String_Object_Exist(oHomePage.MDAlertMessage);

		String sAlertMessge = oSeleniumUtils.get_TextFrom_Locator(oHomePage.MDAlertMessage);

		verify("Alert Message" + sAlert_Message,
				sAlert_Message.replaceAll("//s", "").equalsIgnoreCase(sAlertMessge.replaceAll("//s", "")));

		oGenericUtils.gfn_Click_On_Object("button", "No");

		boolean sCommentVal = validateMDCommentsInCPM(ProjectVariables.loMDTestComments);

		verify("MD Comments in CPM ", sCommentVal);

		getDriver().switchTo().defaultContent();

		userModifyCode("CPM PROPOSALS", "CpmAlert", sDecision);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oGenericUtils.gfn_Click_On_Object("label", "CPM Decision Complete");

		oGenericUtils.gfn_Click_On_Object("button", "Yes");

		oGenericUtils.gfn_Click_On_Object("span", "OK");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		getDriver().switchTo().defaultContent();

	}

	private boolean validateMDCommentsInCPM(String loMDTestComments) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Comments"));

		if (!oHomePage.FMDComments.isDisplayed()) {

			oGenericUtils.gfn_Click_On_Object("span", "Final PO Comments");

		}

		String sMdcomment = oHomePage.FMDComments.getAttribute("value");

		boolean bln = sMdcomment.trim().equalsIgnoreCase(ProjectVariables.loMDTestComments);

		verify("Md Comment is displayed", bln);

		return bln;
	}

	@Step
	public void verify_Modify_decisions_Tab() throws InterruptedException {

		verify("Verify Modify Decisions tab should not display",
				oGenericUtils.gfn_Verify_Object_Exist("label", "Modify Decisions"));

		String str = oHomePage.ModifyDecisions.getAttribute("disabled");

		verify("Authorize Decisions is Disabled", str.equalsIgnoreCase("disabled"));

	}

	@Step
	public void highlight_non_default_values_from_manual_proposals(String sProposalType, String sCategory,
			String sCPTCode, String sPOSCode, String sOverrideFlag) throws InterruptedException {

		String sArrayCode = null;

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oGenericUtils.gfn_Click_On_Object("label", "Add Code");

		switch (sProposalType) {

		case "START NEW":

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.StartNewCode);

			oGenericUtils.gfn_Click_On_Object("a", "Start New");

			oSeleniumUtils.Click_given_Locator(
					"//table[@id='Manual_Proposals_grid']//tr[2]//td[2]//select//option[text()='Yes']");

			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
					oHomePage.GetDynamicXPathWithRowCol("MANUALPROP FIELDS", 2, 3), "Manual Add");

			oSeleniumUtils.Enter_given_Text_Element(("//table[@id='Manual_Proposals_grid']//tr[2]//td[4]//textarea"),
					"Test");

			oSeleniumUtils.select_Given_Value_From_DropDown(oHomePage.ManualProposalCatgoryDropdwn, sCategory);

			oSeleniumUtils.Enter_given_Text_Element(("//table[@id='Manual_Proposals_grid']//tr[2]//td[6]//input"),
					sCPTCode);

			oSeleniumUtils.Enter_given_Text_Element(
					("//td[@aria-describedby='Manual_Proposals_grid_interpRuleCpt.posLike']//input"), sPOSCode);

			if (sOverrideFlag.equalsIgnoreCase("Yes")) {

				verify("OVerride flag should be unchecked", !oSeleniumUtils.is_WebElement_Selected(
						"//td[@aria-describedby='Manual_Proposals_grid_interpRuleCpt.override']//input"));

				oSeleniumUtils.Click_given_Locator(
						"//td[@aria-describedby='Manual_Proposals_grid_interpRuleCpt.override']//input");
			}

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			WebElement oColoumn = getDriver().findElement(
					By.xpath("//td[@aria-describedby='Manual_Proposals_grid_interpRuleCpt.dateTo']//input"));

			((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", oColoumn);

			oGenericUtils.gfn_Click_String_object_Xpath(
					"//td[@aria-describedby='Manual_Proposals_grid_interpRuleCpt.dateFrom']//input");

			oSeleniumUtils.Enter_given_Text_Element("//input[@id='FromDOSDate']", "01/08/1656");

			oGenericUtils.gfn_Click_On_Object("span", "Ok");

			oGenericUtils.gfn_Click_String_object_Xpath(
					"//td[@aria-describedby='Manual_Proposals_grid_interpRuleCpt.dateTo']//input");

			oSeleniumUtils.Enter_given_Text_Element("//input[@id='ToDOSDate']", "01/05/2015");

			oGenericUtils.gfn_Click_On_Object("span", "Ok");

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			WebElement oColoumn1 = getDriver()
					.findElement(By.xpath("//table[@id='Manual_Proposals_grid']//input[@name='interpRuleCpt.daysLo']"));

			((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", oColoumn1);

			verify("ICD flag should be unchecked", !oSeleniumUtils.is_WebElement_Selected(
					"//td[@aria-describedby='Manual_Proposals_grid_interpRuleCpt.invokeIcd']//input"));

			oSeleniumUtils.Click_given_Locator(
					"//td[@aria-describedby='Manual_Proposals_grid_interpRuleCpt.invokeIcd']//input");

			verify("calendar year should be unchecked",
					!oSeleniumUtils.is_WebElement_Selected("//input[@id='-1_interpRuleCpt.calendarYear']"));

			oSeleniumUtils.Click_given_Locator("//input[@id='-1_interpRuleCpt.calendarYear']");

			oSeleniumUtils.Enter_given_Text_Element(
					"//table[@id='Manual_Proposals_grid']//input[@name='interpRuleCpt.daysLo']", "15");

			oSeleniumUtils.Enter_given_Text_Element(
					"//table[@id='Manual_Proposals_grid']//input[@name='interpRuleCpt.daysHi']", "25");

			break;

		case "COPY CODE":

			break;

		default:
			Assert.assertTrue("Highlight_non_default_values_from_manual_proposals", false);

		}

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oGenericUtils.gfn_Click_On_Object("label", "Save");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		String arr[] = { "0075T", "0009T", "0006T", "0007T", "0004T" };

		for (int i = 0; i < arr.length; i++) {

			sArrayCode = arr[i];

			int iErrorMsg = getDriver().findElements(By.xpath(StringUtil.replace(oHomePage.pTag, "sValue",
					" One or more proposals already exists. Please review duplicates and save."))).size();

			if (iErrorMsg > 0) {

				oGenericUtils.gfn_Click_On_Object("button", "Ok");
				oSeleniumUtils.Enter_given_Text_Element(("//table[@id='Manual_Proposals_grid']//tr[2]//td[6]//input"),
						sArrayCode);
				oGenericUtils.gfn_Click_On_Object("label", "Save");

			}

			int iMsg = getDriver().findElements(By.xpath(StringUtil.replace(oHomePage.h3Tag, "sValue", "Success")))
					.size();

			if (iMsg > 0) {
				break;
			}

		}

		oGenericUtils.gfn_Verify_Object_Exist("h3", "Success");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		String[] str = ProjectVariables.HiglightedColumns;

		for (int i = 1; i <= str.length; i++) {

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			verify("check given coloumn is highlighted" + str[i - 1],
					oSeleniumUtils.is_WebElement_Displayed(
							"//table[@id='Decision_Summary_Manul_Proposals_grid']//tr[2]//td[contains(@style,'rgb')]["
									+ i + "]"));

		}

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void verify_highlighted_fields_from_summaries() throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Summaries"));

		oGenericUtils.gfn_Click_On_Object("span", "Decision Summary");

		String[] str = ProjectVariables.HiglightedColumns;

		for (int i = 1; i <= str.length; i++) {

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			verify("check given coloumn is highlighted" + str[i - 1],
					oSeleniumUtils.is_WebElement_Displayed(
							"//table[@id='Decision_Summary_Manul_Proposals_grid']//tr[2]//td[contains(@style,'rgb')]["
									+ i + "]"));

		}

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void get_total_rules_count_inRuleReviewWorkQueu() {

		String sRulesCount = oSeleniumUtils
				.get_TextFrom_Locator("(//div[contains(text(),'Displaying')])[position()=last()]");

		String sValue = StringUtils.substringAfter(sRulesCount, "of").trim();

		System.out.println(StringUtils.substringAfter(sRulesCount, "of").trim());

		Serenity.setSessionVariable("TotalCountInRuleReview").to(sValue);

	}

	@Step
	public void navigate_to_IU_report(String arg1) throws InterruptedException {

		List<String> sList = Arrays.asList(arg1.split(";"));

		if (sList.size() > 1) {

			for (int i = 0; i < sList.size(); i++) {

				oGenericUtils.gfn_Click_On_Object("div", "Report");

				SeleniumUtils.switchWindowUsingWindowCount(2, 2, getDriver());

				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

				oGenericUtils.gfn_Click_On_Object("div", sList.get(i));

				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

				SeleniumUtils.switchWindowUsingWindowCount(2, 1, getDriver());

			}

		} else {

			oGenericUtils.gfn_Click_On_Object("div", "Report");

			SeleniumUtils.switchWindowUsingWindowCount(2, 2, getDriver());

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Click_On_Object("div", arg1);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		}

	}

	@Step
	public void get_IU_Report_rule_count() throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.SummaryGroup1);

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.SummaryGroup2);

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.SummaryGroup3);

		oGenericUtils.gfn_Click_On_Object("div", "Search");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		String sTotalRuleCountInIUReport = oSeleniumUtils
				.get_TextFrom_Locator("//td[text()='Totals : ']/following-sibling::td[1]");

		Serenity.setSessionVariable("TotalRuleCountInIUReport").to(sTotalRuleCountInIUReport);

		System.out.println("TotalRuleCountInIUReport" + sTotalRuleCountInIUReport);

	}

	@Step
	public void validate_IU_Report_count_with_total_rules_count() throws InterruptedException {

		verify("IU Report count with total rules count in Rule Review Work Queue",
				Serenity.sessionVariableCalled("TotalCountInRuleReview").toString()
						.equalsIgnoreCase(Serenity.sessionVariableCalled("TotalRuleCountInIUReport").toString()));

	}

	@Step
	public void click_on_instance_with_status_from_Admin(String sStatus) throws InterruptedException {

		oSeleniumUtils.Click_given_Locator(StringUtils.replace(oHomePage.AdminScrubInstanceInAdmin, "sValue", sStatus));

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.Actions);

		oGenericUtils.gfn_Click_On_Object("span", "Rule Review");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

	}

	@Step
	public void select_rule_with_columnheader(String scolumn, String sHeaderValue) {

		List<WebElement> sRuleHeaderList = getDriver()
				.findElements(By.xpath("//td[@cellindex=12]//div[@class='GEFT4QHBE3C']"));

		for (int i = 0; i < sRuleHeaderList.size(); i++) {

			if (sRuleHeaderList.get(i).getText().equalsIgnoreCase(sHeaderValue)) {

				String str = oSeleniumUtils.get_TextFrom_Locator(
						"//td[@cellindex='1' and @class='GEFT4QHBC3C']//div[@class='GEFT4QHBE3C iht-HyperLinkText']["
								+ (i + 1) + "]");

				Serenity.setSessionVariable("MidRuleVersion").to(str);
			}

		}

	}

	@Step
	public void delete_manual_prop() throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		if (oGenericUtils
				.gfn_Click_String_object_Xpath("(//span[text()='Manual Proposals'])[position()=last()]") == true) {

			oGenericUtils.gfn_Click_String_object_Xpath("//input[@id='cb_Manual_Proposals_grid']");

			oGenericUtils.gfn_Click_On_Object("label", "Delete Selected");

			oGenericUtils.gfn_Click_On_Object("button", "Yes");

			oGenericUtils.gfn_Click_On_Object("label", "Save");

			oGenericUtils.gfn_Click_On_Object("button", "Ok");
		}

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void verify_run_type_column(String sRuntype) throws InterruptedException {

		oGenericUtils.gfn_Click_On_Object("div", "CPM Review Report");

		oGenericUtils.gfn_Click_On_Object("div", "Rule Review Report");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.SwitchToFrame("//iframe[@class='gwt-Frame']");

		oSeleniumUtils.Enter_given_Text_Element(oHomePage.RunTypeSearchBox, sRuntype);

		boolean bln = oSeleniumUtils.is_WebElement_Displayed(oHomePage.RunTypeValue);

		verify("VERIFY_RUN_TYPE_COLUMN" + sRuntype, bln);

		getDriver().switchTo().defaultContent();
	}

	@Step
	public void validate_in_IU_Reports(String arg1) throws InterruptedException {

		String[] sRuleReviewColumnsList = { "Run Type", "Mid Rule", "Version", "Type", "Medical Policy", "Topic", "DP",
				"Task", "Task Status", "Prelim PO", "Prelim Peer", "Final PO", "Final Peer", "CPM(s)", "Review Payers",
				"Editorial User (Edtiorials)", "Editorial User (Potential Conflicts)", "QA", "Config", "Testing" };

		oSeleniumUtils.SwitchToFrame("//iframe[@class='gwt-Frame']");

		scrollToColumn(arg1, "span", sRuleReviewColumnsList);

		getDriver().switchTo().defaultContent();
	}

	@Step
	public void show_rule_review_code(String arg1) throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oGenericUtils.gfn_Click_String_object_Xpath("//tab-heading[text()='Decisions']");

		boolean bln = !oSeleniumUtils.is_WebElement_Selected("//input[@id='showDescriptions']");

		verify("showDescriptions_MD should not be selected", bln);

		oGenericUtils.gfn_Click_String_object_Xpath("//input[@id='showDescriptions']");

		verify("showDescriptions_MD should not be selected", oSeleniumUtils.is_WebElement_Displayed(
				"//td[@class='wrapColumnText']//textarea[@class='form-control ng-pristine ng-valid']"));

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void close_IU_Report_window() {

		getDriver().close();

	}

	public void complete_all_QA_Reviews() throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oGenericUtils.gfn_Verify_String_Object_Exist(oHomePage.QAReviewSegment);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "QA"));

		List<WebElement> rows = getDriver().findElements(By.xpath(oHomePage.QA_Review_Table));

		String sReviewSegValue = null;

		for (int i = 2; i <= rows.size(); i++) {

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "QA"));

			String sVal = Integer.toString(i);

			sReviewSegValue = oSeleniumUtils
					.get_TextFrom_Locator(StringUtil.replace(oHomePage.QA_Review_Table_Item, "sValue", sVal));

			// if (!(sReviewSegValue == "Editorials")) {
			if (!(sReviewSegValue.equalsIgnoreCase("Editorials"))) {

				oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.QA_Review_Table_Item, "sValue", sVal));
				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			}

			switch (sReviewSegValue) {
			case "BW And/BWO Or Logic":
				oGenericUtils.gfn_Verify_Object_Exist("h3", "Billed With And/Billed Without Or Code Clarification");

				if (!(oHomePage.NewBilled_Radio_button.isSelected())) {

					oSeleniumUtils.Click_given_WebElement(oHomePage.NewBilled_Radio_button);
				}
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				oSeleniumUtils.Click_given_Locator(oHomePage.Complete_QA_Review_Sub);
				userClickOnReviewSuccessMsg(sReviewSegValue);
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

				break;

			case "Impact Code List":

				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

				int icount = getDriver().findElements(By.xpath(oHomePage.ImpactReviewCodeList)).size();

				if (icount > 0) {

					if (!(oHomePage.ImpactChkName.isSelected())) {
						oSeleniumUtils.highlightElement(oHomePage.ImpactReviewCodeAll);
						oSeleniumUtils.Click_given_WebElement(oHomePage.ImpactReviewCodeAll);
						SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
					} else {
						System.out.println("Radio button is not availble");
					}
				}

				oSeleniumUtils.highlightElement(oHomePage.Complete_QA_Review);
				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
				if (icount > 0) {
					oSeleniumUtils.Click_given_Locator(oHomePage.QAReview_Category);
				}
				oSeleniumUtils.highlightElement(oHomePage.Complete_QA_Review);
				oSeleniumUtils.highlightElement(oHomePage.Complete_QA_Review);
				oSeleniumUtils.Click_given_Locator(oHomePage.Complete_QA_Review);
				userClickOnReviewSuccessMsg(sReviewSegValue);

				oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "QA"));

				break;

			default:

				if (!(sReviewSegValue.equalsIgnoreCase("Editorials"))) {
					oSeleniumUtils.Click_given_Locator(oHomePage.Complete_QA_Review);
					userClickOnReviewSuccessMsg(sReviewSegValue);
					oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "QA"));
					break;
				}

			}

		}

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		// oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag,
		// "sValue", "QA"));

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void selectRuleFromDB(String InstanceType) {

		boolean sFlag = false;

		String sRuleQuery = "select MID_RULE_DOT_VERSION from "
				+ "IPDE.task_details t , IPDE.task_status_lkp tsl, IRDM.interp_rule_details ird,ipde.task_type_lkp ittl"
				+ "  where  tsl.task_status_key = t.task_status_key and ird.INTERP_RULE_KEY = reference_rule_id "
				+ "and t.TASK_TYPE_KEY = ittl.TASK_TYPE_KEY and  LIBRARY_STATUS_DESC='Custom' and ittl.TASK_TYPE_KEY='10' and TASK_STATUS_DESC ='Not Started'"
				+ " and reference_rule_id in "
				+ "(select INTERP_RULE_KEY from IRDM.interp_rule_details where interp_rule_key in"
				+ " (select interp_rule_key from IRDM.interp_rules where impact_key = "
				+ "(select impact_key from irdm.interp_impacts where update_instance_key ="
				+ "(select update_instance_key from IRDM.update_instances where update_instance_name = 'AutoInstance234'))))";

		System.out.println(sRuleQuery);

		String sRuleValue = DBUtils.executeSQLQuery(sRuleQuery);

		System.out.println(sRuleValue);

		if (sFlag == false) {

			verify("Selected Rules are Deactivated", false);
		}

	}

	@Step
	public void potentialConflictsReview(String ConflictType) throws Exception {

		navigateToAdminRuleReviewValidateColValues("Admin", "FINAL PO DEL", "Task", "Potential Conflicts Review");
		reassignTask("", "iht_ittest01");
		navigateToMyTasksAndGoToRuleInInstance("MyTasks", "Editorial Review Work Queue", "FINAL PO DEL");
		selectRule("RuleID");
		clickOnWorkQueue("Start Review");

		oGenericUtils.gfn_Click_String_object_Xpath(
				"//div[@class='ui-jqgrid-bdiv']//table[@id='potentialConflictGrid']//input[@type='radio' ][2]");
		oGenericUtils.gfn_Click_On_Object("label", "Save");
		oGenericUtils.gfn_Verify_Object_Exist("h3", "Information");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		oGenericUtils.gfn_Click_On_Object("label", "Conflict Review Completed");
		oGenericUtils.gfn_Verify_Object_Exist("h3", "Information");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		navigateToMyTasksAndGoToRuleInInstance("MyTasks", "PO Review Work Queue", "FINAL PO DEL");
		selectRule("RuleID");
		clickOnWorkQueue("Start Review");

	}

	@Step
	public void selectAEActions(String sInstance, String sActionType) throws Exception {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oHomePage.closeAllTabs();
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag, "sValue", "Group Tasks"));
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag, "sValue", "Administration"));
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		oSeleniumUtils.Click_given_Locator(
				StringUtil.replace(oHomePage.AnchorTag, "sValue", "Interpretive Update Instances"));

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(StringUtils.replace(oHomePage.AdminInstance, "sValue", sInstance));

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.Actions);

		oGenericUtils.gfn_Click_On_Object("span", sActionType);

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		int iAEOKBtn = getDriver().findElements(By.xpath("//div[text()='OK']")).size();

		if (iAEOKBtn > 0) {

			oGenericUtils.gfn_Click_On_Object("div", "OK");
		}

	}

	@Step
	public void completeAssignmentExceptions(String sInstanceType, int sheet_NO) throws Exception {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		int iAEOKBtn2 = getDriver().findElements(By.xpath("//div[text()='OK']")).size();

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		if (iAEOKBtn2 > 0) {

			oGenericUtils.gfn_Click_On_Object("div", "OK");
		}

		oGenericUtils.gfn_Click_On_Object("span", "Assignment Exceptions");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		int iAEOKBtn3 = getDriver().findElements(By.xpath("//div[text()='OK']")).size();

		if (iAEOKBtn3 > 0) {

			oGenericUtils.gfn_Click_On_Object("div", "OK");
		}

		int iTotalRows = ExcelUtils.GetRowCount(sheet_NO);
		System.out.println("Total Row Count :" + iTotalRows);

		for (int i = 1; i <= iTotalRows; i++) {

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.AEAddButton);
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			String sResource = ExcelUtils.getCellData(i, 0, sheet_NO);

			String sTask = ExcelUtils.getCellData(i, 1, sheet_NO);

			String sAssociatedTask = ExcelUtils.getCellData(i, 2, sheet_NO);

			String sAssociatedResource = ExcelUtils.getCellData(i, 3, sheet_NO);

			String sMedPolicyTitle = ExcelUtils.getCellData(i, 4, sheet_NO);

			String sTopicTitle1 = ExcelUtils.getCellData(i, 5, sheet_NO);

			String sTopicTitle = ExcelUtils.getCellData(i, 6, sheet_NO);

			String sDecisionPoint = ExcelUtils.getCellData(i, 7, sheet_NO);

			String sLibraryStatus = ExcelUtils.getCellData(i, 8, sheet_NO);

			System.out.println(sAssociatedTask);
			System.out.println(sAssociatedResource);

			List<WebElement> sAEListboxSize = getDriver().findElements(By.xpath("//div[@class='GEFT4QHBB1']"));

			sAEListboxSize.get(1).click();
			oSeleniumUtils.Enter_given_Text_Element(oHomePage.GetDynamicXPath("SELECT AE VALUES", 4), sResource);
			oSeleniumUtils.Click_given_Locator("(//div[@class='GEFT4QHBKO'])[3]//div//div[text()='" + sResource + "']");

			sAEListboxSize.get(2).click();
			oSeleniumUtils.Enter_given_Text_Element(oHomePage.GetDynamicXPath("SELECT AE VALUES", 5), sTask);
			oSeleniumUtils.Click_given_Locator("(//div[@class='GEFT4QHBKO'])[3]//div//div[text()='" + sTask + "']");

			sAEListboxSize.get(3).click();
			if (sAssociatedTask != null) {
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				oSeleniumUtils.Enter_given_Text_Element(oHomePage.GetDynamicXPath("SELECT AE VALUES", 6),
						sAssociatedTask);
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				oSeleniumUtils.Click_given_Locator(
						"(//div[@class='GEFT4QHBKO'])[3]//div//div[text()='" + sAssociatedTask + "']");
			}

			sAEListboxSize.get(4).click();
			if (sAssociatedResource != null) {
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				oSeleniumUtils.Enter_given_Text_Element(oHomePage.GetDynamicXPath("SELECT AE VALUES", 7),
						sAssociatedResource);
				oSeleniumUtils.Click_given_Locator(
						"(//div[@class='GEFT4QHBKO'])[3]//div//div[text()='" + sAssociatedResource + "']");
			}

			sAEListboxSize.get(5).click();
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			oSeleniumUtils.Enter_given_Text_Element(oHomePage.GetDynamicXPath("SELECT AE VALUES", 8), sMedPolicyTitle);
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			oSeleniumUtils
					.Click_given_Locator("(//div[@class='GEFT4QHBKO'])[3]//div//div[text()='" + sMedPolicyTitle + "']");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			if (sTopicTitle1 != null) {
				sAEListboxSize.get(6).click();
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				oSeleniumUtils.Enter_given_Text_Element(oHomePage.GetDynamicXPath("SELECT AE VALUES", 9), sTopicTitle1);
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				oSeleniumUtils.Click_given_Locator(
						"(//div[@class='GEFT4QHBKO'])[3]//div//div[text()='" + sTopicTitle1 + "']");
				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			}

			if (sTopicTitle != null) {
				sAEListboxSize.get(6).click();
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				oSeleniumUtils.Enter_given_Text_Element(oHomePage.GetDynamicXPath("SELECT AE VALUES", 9), sTopicTitle);
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				oSeleniumUtils
						.Click_given_Locator("(//div[@class='GEFT4QHBKO'])[3]//div//div[text()='" + sTopicTitle + "']");
				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			}

			if (sDecisionPoint != null) {
				sAEListboxSize.get(7).click();
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				oSeleniumUtils.Enter_given_Text_Element(oHomePage.GetDynamicXPath("SELECT AE VALUES", 10),
						sDecisionPoint);
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				oSeleniumUtils.Click_given_Locator(
						"(//div[@class='GEFT4QHBKO'])[3]//div//div[text()='" + sDecisionPoint + "']");
			}

			if (sLibraryStatus != null) {
				sAEListboxSize.get(10).click();
				oSeleniumUtils.Enter_given_Text_Element(oHomePage.GetDynamicXPath("SELECT AE VALUES", 13),
						sLibraryStatus);
				oSeleniumUtils.Click_given_Locator(
						"(//div[@class='GEFT4QHBKO'])[3]//div//div[text()='" + sLibraryStatus + "']");
			}

			System.out.print("Completed Row No" + i);
		}

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.AESaveButton);
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oGenericUtils.gfn_Verify_Object_Exist("div", "Are you sure you want to save the assignments?");
		oGenericUtils.gfn_Click_On_Object("div", "Yes");
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oGenericUtils.gfn_Verify_Object_Exist("div", "Administration - Interpretive Update Instances");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		oGenericUtils.gfn_Click_On_Object("div", "OK");

	}

	@Step
	public void vaidateAdminRuleReviewDB(String sInstanceType, String sColumnName, String sColumnValue) {

		String sQuery = null;
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		// Serenity.setSessionVariable("MidRuleVersion").to("14905.9");
		System.out.println(Serenity.sessionVariableCalled("MidRuleVersion").toString());
		// Serenity.setSessionVariable("IUInstanceName").to("ICD-22");
		System.out.println(Serenity.sessionVariableCalled("IUInstanceName").toString());

		switch (sColumnName) {

		case "Task":
			sQuery = "select TASK_TYPE_DESC from IPDE.TASK_TYPE_LKP where TASK_TYPE_KEY in"
					+ "(select TASK_TYPE_KEY from IPDE.task_details where   REFERENCE_RULE_ID in"
					+ "(select INTERP_RULE_KEY from IRDM.interp_rule_details where MID_RULE_DOT_VERSION = '"
					+ Serenity.sessionVariableCalled("MidRuleVersion").toString() + "' and INTERP_RULE_KEY in"
					+ "(select interp_rule_key from IRDM.interp_rules where impact_key ="
					+ "(select impact_key from irdm.interp_impacts where update_instance_key ="
					+ "(select update_instance_key from IRDM.update_instances where update_instance_name = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "')))))";

			System.out.println("Query: " + sQuery);
			break;

		case "Status":
			sQuery = "select TASK_STATUS_DESC from IPDE.TASK_STATUS_LKP where task_status_key in"
					+ "(select TASK_STATUS_KEY from IPDE.task_details where REFERENCE_RULE_ID in"
					+ "(select INTERP_RULE_KEY from IRDM.interp_rule_details where MID_RULE_DOT_VERSION = '"
					+ Serenity.sessionVariableCalled("MidRuleVersion").toString() + "' and INTERP_RULE_KEY in"
					+ "(select interp_rule_key from IRDM.interp_rules where impact_key in"
					+ "(select impact_key from irdm.interp_impacts where update_instance_key ="
					+ "(select update_instance_key from IRDM.update_instances where update_instance_name = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "')))))";

			System.out.println("Query: " + sQuery);
			break;

		case "Assignee": // changed from TASK_USER_KEY on 18 Jan 19
			sQuery = "select USER_NAME from IPDE.USERS where USER_KEY in"
					+ "(select TASK_USER_KEY  from IPDE.task_details where REFERENCE_RULE_ID in"
					+ "(select INTERP_RULE_KEY from IRDM.interp_rule_details where MID_RULE_DOT_VERSION = '"
					+ Serenity.sessionVariableCalled("MidRuleVersion").toString() + "' and INTERP_RULE_KEY in"
					+ "(select interp_rule_key from IRDM.interp_rules where impact_key in"
					+ "(select impact_key from irdm.interp_impacts where update_instance_key ="
					+ "(select update_instance_key from IRDM.update_instances where update_instance_name = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "')))))";

			System.out.println("Query: " + sQuery);
			break;
		}

		String sAdminRuleReviewDBVal = DBUtils.executeSQLQuery(sQuery);
		System.out.println("Query Status: " + sAdminRuleReviewDBVal);

		if (sAdminRuleReviewDBVal.equalsIgnoreCase(sColumnValue)) {

			verify("Rule Review Value is displayed Correctly: " + sAdminRuleReviewDBVal + "; Expected Value is: "
					+ sColumnValue, true);
		} else {
			verify("Actual Value is : " + sAdminRuleReviewDBVal + "; Expected Value is: " + sColumnValue, false);
			verify("Exeucted Query is : " + sQuery, false);

		}

	}

	@Step
	public void scrollToColumn(String sValue, String sTag, String[] sColumnList) throws InterruptedException {

		for (int i = 0; i < sColumnList.length; i++) {

			WebElement oColoumn = getDriver().findElement(By.xpath("//" + sTag + "[text()='" + sColumnList[i] + "']"));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			if (!oColoumn.isDisplayed()) {

				WebElement horizontal_scroll = oColoumn;

				((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);",
						horizontal_scroll);

			}

			verify("Column Values are displayed in" + sValue,
					oGenericUtils.gfn_Verify_Object_Exist("span", sColumnList[i]));

		}

	}

	@Step
	public void applyFiltersInMyTaskDB(String sTaskType, String sFilterValue) {

		String sQuery = null;

		String sFilterVal = null;

		String sType = StringUtils.substringBefore(sTaskType, "-");

		String TaskType = StringUtils.substringAfter(sTaskType, "-");

		Serenity.setSessionVariable("TaskType").to(sType);

		List<String> sFilterValueList = Arrays.asList(sFilterValue.split(";"));

		String sLibraryType = sFilterValueList.get(0);
		String sTaskStatus = sFilterValueList.get(1);
		String sTaskReview = sFilterValueList.get(2);
		String sARDStatus = sFilterValueList.get(3);

		String sRuleType = null;

		if (sFilterValueList.get(sFilterValueList.size() - 1).equalsIgnoreCase("Non Candidates")) {

			sRuleType = "AND IRD.LIBRARY_STATUS_DESC = '" + sLibraryType + "'" + "AND CANDIDATE_10=0";
		} else {
			

			sRuleType = "AND IRD.LIBRARY_STATUS_DESC = '" + sLibraryType + "'" + "AND CANDIDATE_10=-1";
		}

		int iFieldSize = sFilterValueList.size();
		System.out.println(iFieldSize);
		if (iFieldSize > 3) {
			sFilterVal = sFilterValueList.get(3);
		}

		switch (sType) {

		case "FINAL MD DEL MULTIPAYER1":

			sQuery = "select MID_RULE_DOT_VERSION from IRDM.interp_rule_details where PAYERS_4_RULE like '%,%' and LIBRARY_STATUS_DESC ='"
					+ sLibraryType + "' and ARD_EXISTS_YN='" + sARDStatus + "'and INTERP_RULE_KEY in"
					+ "(select REFERENCE_RULE_ID from IPDE.task_details where task_status_key in"
					+ "(select TASK_STATUS_KEY from IPDE.TASK_STATUS_LKP where TASK_STATUS_DESC ='" + sTaskStatus
					+ "')and TASK_TYPE_KEY in"
					+ "(select TASK_TYPE_KEY from IPDE.TASK_TYPE_LKP where TASK_TYPE_DESC ='Final PO Review')  and REFERENCE_RULE_ID in"
					+ "(select INTERP_RULE_KEY from IRDM.interp_rule_details IRD, rules.sub_rules RSB where IRD.MID_RULE_KEY=RSB.MID_RULE_KEY and IRD.RULE_VERSION=RSB.RULE_VERSION and DEACTIVATED_10 <> '-1' and INTERP_RULE_KEY in"
					+ "((select interp_rule_key from IRDM.interp_rules where impact_key in"
					+ "(select impact_key from irdm.interp_impacts where update_instance_key ="
					+ "(select update_instance_key from IRDM.update_instances where update_instance_name = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "'))))))";

			System.out.println("Query is : " + sQuery);

			break;

		case "FINAL MD DEL MULTIPAYER2":

			sQuery = "select MID_RULE_DOT_VERSION from IRDM.interp_rule_details where PAYERS_4_RULE like '%,%,%' and LIBRARY_STATUS_DESC ='"
					+ sLibraryType + "' and ARD_EXISTS_YN='" + sARDStatus + "'and INTERP_RULE_KEY in"
					+ "(select REFERENCE_RULE_ID from IPDE.task_details where task_status_key in"
					+ "(select TASK_STATUS_KEY from IPDE.TASK_STATUS_LKP where TASK_STATUS_DESC ='" + sTaskStatus
					+ "')and TASK_TYPE_KEY in"
					+ "(select TASK_TYPE_KEY from IPDE.TASK_TYPE_LKP where TASK_TYPE_DESC ='Final PO Review')  and REFERENCE_RULE_ID in"
					+ "(select INTERP_RULE_KEY from IRDM.interp_rule_details IRD, rules.sub_rules RSB where IRD.MID_RULE_KEY=RSB.MID_RULE_KEY and IRD.RULE_VERSION=RSB.RULE_VERSION and DEACTIVATED_10 <> '-1' and INTERP_RULE_KEY in"
					+ "((select interp_rule_key from IRDM.interp_rules where impact_key in"
					+ "(select impact_key from irdm.interp_impacts where update_instance_key ="
					+ "(select update_instance_key from IRDM.update_instances where update_instance_name = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "'))))))";

			System.out.println("Query is : " + sQuery);

			break;

		case "FINAL MD DEL MULTIPAYER2-Subsequent":

			sQuery = "select MID_RULE_DOT_VERSION from IRDM.interp_rule_details where PAYERS_4_RULE like '%,%,%' and LIBRARY_STATUS_DESC ='"
					+ sLibraryType + "' and ARD_EXISTS_YN='" + sARDStatus + "'and INTERP_RULE_KEY in"
					+ "(select REFERENCE_RULE_ID from IPDE.task_details where task_status_key in"
					+ "(select TASK_STATUS_KEY from IPDE.TASK_STATUS_LKP where TASK_STATUS_DESC ='" + sTaskStatus
					+ "')and TASK_TYPE_KEY in"
					+ "(select TASK_TYPE_KEY from IPDE.TASK_TYPE_LKP where TASK_TYPE_DESC ='Final PO Review')  and REFERENCE_RULE_ID in"
					+ "(select INTERP_RULE_KEY from IRDM.interp_rule_details IRD, rules.sub_rules RSB where IRD.MID_RULE_KEY=RSB.MID_RULE_KEY and IRD.RULE_VERSION=RSB.RULE_VERSION and DEACTIVATED_10 <> '-1' and INTERP_RULE_KEY in"
					+ "((select interp_rule_key from IRDM.interp_rules where impact_key in"
					+ "(select impact_key from irdm.interp_impacts where update_instance_key ="
					+ "(select update_instance_key from IRDM.update_instances where update_instance_name = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "') AND IMPACT SEQ=2)))))";

			System.out.println("Query is : " + sQuery);

			break;

		case "RULE HEADER":

			sQuery = "select MID_RULE_DOT_VERSION from IRDM.interp_rule_details where LIBRARY_STATUS_DESC ='"
					+ sLibraryType + "' and RULE_HEADER_KEY='" + sFilterVal + "' and  ARD_EXISTS_YN='" + sARDStatus
					+ "'and INTERP_RULE_KEY in"
					+ "(select REFERENCE_RULE_ID from IPDE.task_details where task_status_key in"
					+ "(select TASK_STATUS_KEY from IPDE.TASK_STATUS_LKP where TASK_STATUS_DESC ='" + sTaskStatus
					+ "')and TASK_TYPE_KEY in"
					+ "(select TASK_TYPE_KEY from IPDE.TASK_TYPE_LKP where TASK_TYPE_DESC ='Final PO Review')  and REFERENCE_RULE_ID in"
					+ "(select INTERP_RULE_KEY from IRDM.interp_rule_details IRD, rules.sub_rules RSB where IRD.MID_RULE_KEY=RSB.MID_RULE_KEY and IRD.RULE_VERSION=RSB.RULE_VERSION and DEACTIVATED_10 <> '-1' and INTERP_RULE_KEY in"
					+ "((select interp_rule_key from IRDM.interp_rules where impact_key in"
					+ "(select impact_key from irdm.interp_impacts where update_instance_key ="
					+ "(select update_instance_key from IRDM.update_instances where update_instance_name = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "'))))))";

			System.out.println("Query is : " + sQuery);

			break;

		case "DEACTIVATE RULE":

			sQuery = "select MID_RULE_DOT_VERSION from IRDM.interp_rule_details where LIBRARY_STATUS_DESC ='"
					+ sLibraryType + "' and ARD_EXISTS_YN='" + sARDStatus + "'and INTERP_RULE_KEY in"
					+ "(select REFERENCE_RULE_ID from IPDE.task_details where task_status_key in"
					+ "(select TASK_STATUS_KEY from IPDE.TASK_STATUS_LKP where TASK_STATUS_DESC ='" + sTaskStatus
					+ "')and TASK_TYPE_KEY in"
					+ "(select TASK_TYPE_KEY from IPDE.TASK_TYPE_LKP where TASK_TYPE_DESC ='Final PO Review')  and REFERENCE_RULE_ID in"
					+ "(select INTERP_RULE_KEY from IRDM.interp_rule_details IRD, rules.sub_rules RSB where IRD.MID_RULE_KEY=RSB.MID_RULE_KEY and IRD.RULE_VERSION=RSB.RULE_VERSION and DEACTIVATED_10 = '-1' and INTERP_RULE_KEY in"
					+ "((select interp_rule_key from IRDM.interp_rules where  impact_key in"
					+ "(select impact_key from irdm.interp_impacts where update_instance_key ="
					+ "(select update_instance_key from IRDM.update_instances where update_instance_name = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "'))))))";

		case "SINGLEPAYER":

			sQuery = "select MID_RULE_DOT_VERSION from IRDM.interp_rule_details where  PAYERS_4_RULE not like '%,%' and LIBRARY_STATUS_DESC ='"
					+ sLibraryType + "' and ARD_EXISTS_YN='" + sARDStatus + "'and INTERP_RULE_KEY in"
					+ "(select REFERENCE_RULE_ID from IPDE.task_details where task_status_key in"
					+ "(select TASK_STATUS_KEY from IPDE.TASK_STATUS_LKP where TASK_STATUS_DESC ='" + sTaskStatus
					+ "')and TASK_TYPE_KEY in" + "(select TASK_TYPE_KEY from IPDE.TASK_TYPE_LKP where TASK_TYPE_DESC ='"
					+ TaskType + "')  and REFERENCE_RULE_ID in"
					+ "(select INTERP_RULE_KEY from IRDM.interp_rule_details IRD, rules.sub_rules RSB where IRD.MID_RULE_KEY=RSB.MID_RULE_KEY and IRD.RULE_VERSION=RSB.RULE_VERSION and DEACTIVATED_10 <> '-1' and INTERP_RULE_KEY in"
					+ "((select interp_rule_key from IRDM.interp_rules where impact_key in"
					+ "(select impact_key from irdm.interp_impacts where update_instance_key ="
					+ "(select update_instance_key from IRDM.update_instances where update_instance_name = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "'))))))";

			System.out.println("Query is : " + sQuery);

			break;

		case "MD DEL MULTIPAYER2 Deny":

			sQuery = "select MID_RULE_DOT_VERSION from IRDM.interp_rule_details where PAYERS_4_RULE like '%,%,%' and RULE_HEADER_DESC='Deny Procedure'  and LIBRARY_STATUS_DESC ='"
					+ sLibraryType + "' and ARD_EXISTS_YN='" + sARDStatus + "'and INTERP_RULE_KEY in"
					+ "(select REFERENCE_RULE_ID from IPDE.task_details where task_status_key in"
					+ "(select TASK_STATUS_KEY from IPDE.TASK_STATUS_LKP where TASK_STATUS_DESC ='" + sTaskStatus
					+ "')and TASK_TYPE_KEY in"
					+ "(select TASK_TYPE_KEY from IPDE.TASK_TYPE_LKP where TASK_TYPE_DESC ='Final PO Review')  and REFERENCE_RULE_ID in"
					+ "(select INTERP_RULE_KEY from IRDM.interp_rule_details IRD, rules.sub_rules RSB where IRD.MID_RULE_KEY=RSB.MID_RULE_KEY and IRD.RULE_VERSION=RSB.RULE_VERSION and DEACTIVATED_10 <> '-1' and INTERP_RULE_KEY in"
					+ "((select interp_rule_key from IRDM.interp_rules where impact_key in"
					+ "(select impact_key from irdm.interp_impacts where update_instance_key ="
					+ "(select update_instance_key from IRDM.update_instances where update_instance_name = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "'))))))";

			break;

		case "Final MD ReviewSUBSEQUENTRUN":

			String sType1 = StringUtils.substringBefore(sTaskType, "SUBSEQUENTRUN");

			sQuery = "SELECT MID_RULE_DOT_VERSION FROM IRDM.interp_rule_details WHERE     LIBRARY_STATUS_DESC = '"
					+ sLibraryType
					+ "' AND ARD_EXISTS_YN = 'N' AND INTERP_RULE_KEY IN (SELECT REFERENCE_RULE_ID FROM IPDE.task_details WHERE     TASK_USER_KEY = '537' AND task_status_key IN (SELECT TASK_STATUS_KEY FROM IPDE.TASK_STATUS_LKP WHERE TASK_STATUS_DESC = '"
					+ sTaskStatus
					+ "') AND TASK_TYPE_KEY IN (SELECT TASK_TYPE_KEY FROM IPDE.TASK_TYPE_LKP WHERE TASK_TYPE_DESC = '"
					+ sType1
					+ "') AND REFERENCE_RULE_ID IN (SELECT INTERP_RULE_KEY FROM IRDM.interp_rule_details IRD, rules.sub_rules RSB WHERE     IRD.MID_RULE_KEY = RSB.MID_RULE_KEY AND IRD.RULE_VERSION = RSB.RULE_VERSION AND DEACTIVATED_10 <> '-1' AND INTERP_RULE_KEY IN ( (SELECT interp_rule_key FROM IRDM.interp_rules WHERE impact_key IN (SELECT impact_key FROM irdm.interp_impacts WHERE update_instance_key = (SELECT update_instance_key FROM IRDM.update_instances WHERE update_instance_name = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "') AND IMPACT_SEQ =2   )))))\r\n";

			break;

		case "CPMREVIEWMULTIPAYER":

			sQuery = "select MID_RULE_DOT_VERSION from IRDM.interp_rule_details where PAYERS_4_RULE like '%,%' and LIBRARY_STATUS_DESC ='"
					+ sLibraryType + "' and INTERP_RULE_KEY in"
					+ "(select REFERENCE_RULE_ID from IPDE.task_details where task_status_key in"
					+ "(select TASK_STATUS_KEY from IPDE.TASK_STATUS_LKP where TASK_STATUS_DESC ='" + sTaskStatus
					+ "')and TASK_TYPE_KEY in"
					+ "(select TASK_TYPE_KEY from IPDE.TASK_TYPE_LKP where TASK_TYPE_DESC ='CPM Review')  and REFERENCE_RULE_ID in"
					+ "(select INTERP_RULE_KEY from IRDM.interp_rule_details IRD, rules.sub_rules RSB where IRD.MID_RULE_KEY=RSB.MID_RULE_KEY and IRD.RULE_VERSION=RSB.RULE_VERSION and DEACTIVATED_10 <> '-1' and INTERP_RULE_KEY in"
					+ "((select interp_rule_key from IRDM.interp_rules where impact_key in"
					+ "(select impact_key from irdm.interp_impacts where update_instance_key ="
					+ "(select update_instance_key from IRDM.update_instances where update_instance_name = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "'))))))";

			System.out.println("Query is : " + sQuery);

			break;

		case "PROPOSALBASERULE":

			sQuery = "" + "WITH CTE " + "     AS (  SELECT IR.SUB_RULE_KEY, "
					+ "                  IRD.MID_RULE_DOT_VERSION, " + "                  IRD.LIBRARY_STATUS_DESC, "
					+ "                  IRD.ARD_EXISTS_YN, " + "                  TTL.TASK_TYPE_DESC, "
					+ "                  TSL.TASK_STATUS_DESC, " + "                  U.USER_ID, "
					+ "                  IRDM.CLIST (UGL.UPDATE_GROUP_NAME) PROPOSAL_TYPE " + "            "
					+ "             FROM IRDM.INTERP_RULES IR " + "                  JOIN IRDM.INTERP_RULE_DETAILS IRD "
					+ "                     ON IR.INTERP_RULE_KEY = IRD.INTERP_RULE_KEY "
					+ "                  JOIN IPDE.TASK_DETAILS TD "
					+ "                     ON TD.REFERENCE_RULE_ID = IR.INTERP_RULE_KEY "
					+ "                  JOIN IPDE.TASK_TYPE_LKP TTL "
					+ "                     ON TTL.TASK_TYPE_KEY = TD.TASK_TYPE_KEY "
					+ "                  JOIN IPDE.TASK_STATUS_LKP TSL "
					+ "                     ON TSL.TASK_STATUS_KEY = TD.TASK_STATUS_KEY "
					+ "                  JOIN IPDE.USERS U ON U.USER_KEY = TD.TASK_USER_KEY "
					+ "                  JOIN IRDM.INTERP_RULE_ICD IRI "
					+ "                     ON IRI.INTERP_RULE_KEY = IR.INTERP_RULE_KEY "
					+ "                  JOIN IRDM.INTERP_ICD_SOURCES IIS "
					+ "                     ON IIS.INTERP_RULE_ICD_KEY = IRI.INTERP_RULE_ICD_KEY "
					+ "                  JOIN IRDM.UPDATE_GROUP_LKP UGL "
					+ "                     ON UGL.UPDATE_GROUP_KEY = IRI.UPDATE_GROUP_KEY "
					+ "            WHERE     IR.IMPACT_KEY IN (SELECT IMPACT_KEY "
					+ "                                          FROM IRDM.INTERP_IMPACTS II "
					+ "                                               JOIN IRDM.UPDATE_INSTANCES I "
					+ "                                                  ON I.UPDATE_INSTANCE_KEY = "
					+ "                                                        II.UPDATE_INSTANCE_KEY "
					+ "                                         WHERE UPDATE_INSTANCE_NAME = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "') "
					+ "                  AND TTL.TASK_TYPE_DESC = '" + sTaskReview + "' "
					+ "                  AND TSL.TASK_STATUS_DESC = '" + sTaskStatus + "' "
					+ "                AND IRD.LIBRARY_STATUS_DESC ='" + sLibraryType + "' " + "         "
					+ "         GROUP BY IR.SUB_RULE_KEY, " + "                  IRD.MID_RULE_DOT_VERSION, "
					+ "                  IRD.LIBRARY_STATUS_DESC, " + "                  IRD.ARD_EXISTS_YN, "
					+ "                  TTL.TASK_TYPE_DESC, " + "                  TSL.TASK_STATUS_DESC, "
					+ "                 U.USER_ID) " + "SELECT MID_RULE_DOT_VERSION FROM CTE "
					+ "   where proposal_type ='" + sARDStatus + "'";
			break;

		case "MULTIPROPOSALS":

			sQuery = "" + "WITH CTE " + "     AS (  SELECT IR.SUB_RULE_KEY, "
					+ "                  IRD.MID_RULE_DOT_VERSION, " + "                  IRD.LIBRARY_STATUS_DESC, "
					+ "                  IRD.ARD_EXISTS_YN, " + "                  TTL.TASK_TYPE_DESC, "
					+ "                  TSL.TASK_STATUS_DESC, " + "                  U.USER_ID, "
					+ "                  IRDM.CLIST (UGL.UPDATE_GROUP_NAME) PROPOSAL_TYPE " + "            "
					+ "             FROM IRDM.INTERP_RULES IR " + "                  JOIN IRDM.INTERP_RULE_DETAILS IRD "
					+ "                     ON IR.INTERP_RULE_KEY = IRD.INTERP_RULE_KEY "
					+ "                  JOIN IPDE.TASK_DETAILS TD "
					+ "                     ON TD.REFERENCE_RULE_ID = IR.INTERP_RULE_KEY "
					+ "                  JOIN IPDE.TASK_TYPE_LKP TTL "
					+ "                     ON TTL.TASK_TYPE_KEY = TD.TASK_TYPE_KEY "
					+ "                  JOIN IPDE.TASK_STATUS_LKP TSL "
					+ "                     ON TSL.TASK_STATUS_KEY = TD.TASK_STATUS_KEY "
					+ "                  JOIN IPDE.USERS U ON U.USER_KEY = TD.TASK_USER_KEY "
					+ "                  JOIN IRDM.INTERP_RULE_ICD IRI "
					+ "                     ON IRI.INTERP_RULE_KEY = IR.INTERP_RULE_KEY "
					+ "                  JOIN IRDM.INTERP_ICD_SOURCES IIS "
					+ "                     ON IIS.INTERP_RULE_ICD_KEY = IRI.INTERP_RULE_ICD_KEY "
					+ "                  JOIN IRDM.UPDATE_GROUP_LKP UGL "
					+ "                     ON UGL.UPDATE_GROUP_KEY = IRI.UPDATE_GROUP_KEY "
					+ "            WHERE     IR.IMPACT_KEY IN (SELECT IMPACT_KEY "
					+ "                                          FROM IRDM.INTERP_IMPACTS II "
					+ "                                               JOIN IRDM.UPDATE_INSTANCES I "
					+ "                                                  ON I.UPDATE_INSTANCE_KEY = "
					+ "                                                        II.UPDATE_INSTANCE_KEY "
					+ "                                         WHERE UPDATE_INSTANCE_NAME = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "') "
					+ "                  AND TTL.TASK_TYPE_DESC = '" + sTaskReview + "' "
					+ "                  AND TSL.TASK_STATUS_DESC = '" + sTaskStatus + "' "
					+ "                AND IRD.LIBRARY_STATUS_DESC ='" + sLibraryType + "' " + "         "
					+ "         GROUP BY IR.SUB_RULE_KEY, " + "                  IRD.MID_RULE_DOT_VERSION, "
					+ "                  IRD.LIBRARY_STATUS_DESC, " + "                  IRD.ARD_EXISTS_YN, "
					+ "                  TTL.TASK_TYPE_DESC, " + "                  TSL.TASK_STATUS_DESC, "
					+ "                 U.USER_ID) " + "SELECT MID_RULE_DOT_VERSION FROM CTE " + "    WHERE 1=1 "
					+ "          AND INSTR(PROPOSAL_TYPE,'DEL') >0 " + "          AND INSTR(PROPOSAL_TYPE,'SIM') >0 "
					+ "          AND INSTR(PROPOSAL_TYPE,'REV') >0 ";
			break;
		case "ANYPROPOSALRULE":

			sQuery = "" + "WITH CTE " + "     AS (  SELECT IR.SUB_RULE_KEY, "
					+ "                  IRD.MID_RULE_DOT_VERSION, " + "                  IRD.LIBRARY_STATUS_DESC, "
					+ "                  IRD.ARD_EXISTS_YN, " + "                  TTL.TASK_TYPE_DESC, "
					+ "                  TSL.TASK_STATUS_DESC, " + "                  U.USER_ID, "
					+ "                  IRDM.CLIST (UGL.UPDATE_GROUP_NAME) PROPOSAL_TYPE " + "            "
					+ "             FROM IRDM.INTERP_RULES IR " + "                  JOIN IRDM.INTERP_RULE_DETAILS IRD "
					+ "                     ON IR.INTERP_RULE_KEY = IRD.INTERP_RULE_KEY "
					+ "                  JOIN IPDE.TASK_DETAILS TD "
					+ "                     ON TD.REFERENCE_RULE_ID = IR.INTERP_RULE_KEY "
					+ "                  JOIN IPDE.TASK_TYPE_LKP TTL "
					+ "                     ON TTL.TASK_TYPE_KEY = TD.TASK_TYPE_KEY "
					+ "                  JOIN IPDE.TASK_STATUS_LKP TSL "
					+ "                     ON TSL.TASK_STATUS_KEY = TD.TASK_STATUS_KEY "
					+ "                  JOIN IPDE.USERS U ON U.USER_KEY = TD.TASK_USER_KEY "
					+ "                  JOIN IRDM.INTERP_RULE_ICD IRI "
					+ "                     ON IRI.INTERP_RULE_KEY = IR.INTERP_RULE_KEY "
					+ "                  JOIN IRDM.INTERP_ICD_SOURCES IIS "
					+ "                     ON IIS.INTERP_RULE_ICD_KEY = IRI.INTERP_RULE_ICD_KEY "
					+ "                  JOIN IRDM.UPDATE_GROUP_LKP UGL "
					+ "                     ON UGL.UPDATE_GROUP_KEY = IRI.UPDATE_GROUP_KEY "
					+ "            WHERE     IR.IMPACT_KEY IN (SELECT IMPACT_KEY "
					+ "                                          FROM IRDM.INTERP_IMPACTS II "
					+ "                                               JOIN IRDM.UPDATE_INSTANCES I "
					+ "                                                  ON I.UPDATE_INSTANCE_KEY = "
					+ "                                                        II.UPDATE_INSTANCE_KEY "
					+ "                                         WHERE UPDATE_INSTANCE_NAME = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "') "
					+ "                  AND TTL.TASK_TYPE_DESC = '" + sTaskReview + "' "
					+ "                  AND TSL.TASK_STATUS_DESC = '" + sTaskStatus + "' "
					+ "                AND IRD.LIBRARY_STATUS_DESC ='" + sLibraryType + "' " + "         "
					+ "         GROUP BY IR.SUB_RULE_KEY, " + "                  IRD.MID_RULE_DOT_VERSION, "
					+ "                  IRD.LIBRARY_STATUS_DESC, " + "                  IRD.ARD_EXISTS_YN, "
					+ "                  TTL.TASK_TYPE_DESC, " + "                  TSL.TASK_STATUS_DESC, "
					+ "                 U.USER_ID) " + "SELECT MID_RULE_DOT_VERSION FROM CTE  where proposal_type = 'SIM' or proposal_type = 'REV' or proposal_type = 'DEL'";
			break;

		case "RETRIVEMULTIPAYERRULE":

			EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();

			Serenity.setSessionVariable("IUInstanceName").to(variables.getProperty("IU.FinalMDInstance"));

			sQuery = "WITH CTE  AS (  SELECT IR.SUB_RULE_KEY,  IRD.MID_RULE_DOT_VERSION,   IRD.LIBRARY_STATUS_DESC, PAYERS_4_RULE,IRD.ARD_EXISTS_YN, TTL.TASK_TYPE_DESC, TSL.TASK_STATUS_DESC, U.USER_ID,  IRDM.CLIST (UGL.UPDATE_GROUP_NAME) PROPOSAL_TYPE                          FROM IRDM.INTERP_RULES IR                   JOIN IRDM.INTERP_RULE_DETAILS IRD                      ON IR.INTERP_RULE_KEY = IRD.INTERP_RULE_KEY                   JOIN IPDE.TASK_DETAILS TD                      ON TD.REFERENCE_RULE_ID = IR.INTERP_RULE_KEY                   JOIN IPDE.TASK_TYPE_LKP TTL                      ON TTL.TASK_TYPE_KEY = TD.TASK_TYPE_KEY                   JOIN IPDE.TASK_STATUS_LKP TSL                      ON TSL.TASK_STATUS_KEY = TD.TASK_STATUS_KEY                   JOIN IPDE.USERS U ON U.USER_KEY = TD.TASK_USER_KEY                   JOIN IRDM.INTERP_RULE_ICD IRI                      ON IRI.INTERP_RULE_KEY = IR.INTERP_RULE_KEY                   JOIN IRDM.INTERP_ICD_SOURCES IIS                      ON IIS.INTERP_RULE_ICD_KEY = IRI.INTERP_RULE_ICD_KEY                   JOIN IRDM.UPDATE_GROUP_LKP UGL                      ON UGL.UPDATE_GROUP_KEY = IRI.UPDATE_GROUP_KEY             WHERE     IR.IMPACT_KEY IN (SELECT IMPACT_KEY                                           FROM IRDM.INTERP_IMPACTS II                                                JOIN IRDM.UPDATE_INSTANCES I                                                   ON I.UPDATE_INSTANCE_KEY =                                                         II.UPDATE_INSTANCE_KEY\r\n"
					+ "WHERE UPDATE_INSTANCE_NAME = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString().trim()
					+ "')  AND TTL.TASK_TYPE_DESC = '" + sTaskReview + "' AND TSL.TASK_STATUS_DESC = '" + sTaskStatus
					+ "'   AND IRD.LIBRARY_STATUS_DESC ='" + sLibraryType + "'\r\n"
					+ "          GROUP BY IR.SUB_RULE_KEY,   IRD.MID_RULE_DOT_VERSION,   IRD.LIBRARY_STATUS_DESC, IRD.ARD_EXISTS_YN,  TTL.TASK_TYPE_DESC, PAYERS_4_RULE,\r\n"
					+ "             TSL.TASK_STATUS_DESC,  U.USER_ID) SELECT MID_RULE_DOT_VERSION,PAYERS_4_RULE FROM CTE    where proposal_type ='"
					+ sARDStatus + "'   AND INSTR(PAYERS_4_RULE,',') > 0";
			break;

		case "RULESINGLEPAYER":
			sQuery = " WITH CTE  AS (  SELECT IR.SUB_RULE_KEY,  IRD.MID_RULE_DOT_VERSION,   IRD.LIBRARY_STATUS_DESC, PAYERS_4_RULE,IRD.ARD_EXISTS_YN, TTL.TASK_TYPE_DESC, TSL.TASK_STATUS_DESC, U.USER_ID,  IRDM.CLIST (UGL.UPDATE_GROUP_NAME) PROPOSAL_TYPE                          FROM IRDM.INTERP_RULES IR                   JOIN IRDM.INTERP_RULE_DETAILS IRD                      ON IR.INTERP_RULE_KEY = IRD.INTERP_RULE_KEY                   JOIN IPDE.TASK_DETAILS TD                      ON TD.REFERENCE_RULE_ID = IR.INTERP_RULE_KEY                   JOIN IPDE.TASK_TYPE_LKP TTL                      ON TTL.TASK_TYPE_KEY = TD.TASK_TYPE_KEY                   JOIN IPDE.TASK_STATUS_LKP TSL                      ON TSL.TASK_STATUS_KEY = TD.TASK_STATUS_KEY                   JOIN IPDE.USERS U ON U.USER_KEY = TD.TASK_USER_KEY                   JOIN IRDM.INTERP_RULE_ICD IRI                      ON IRI.INTERP_RULE_KEY = IR.INTERP_RULE_KEY                   JOIN IRDM.INTERP_ICD_SOURCES IIS                      ON IIS.INTERP_RULE_ICD_KEY = IRI.INTERP_RULE_ICD_KEY                   JOIN IRDM.UPDATE_GROUP_LKP UGL                      ON UGL.UPDATE_GROUP_KEY = IRI.UPDATE_GROUP_KEY             WHERE     IR.IMPACT_KEY IN (SELECT IMPACT_KEY                                           FROM IRDM.INTERP_IMPACTS II                                                JOIN IRDM.UPDATE_INSTANCES I                                                   ON I.UPDATE_INSTANCE_KEY =                                                         II.UPDATE_INSTANCE_KEY\r\n"
					+ "   WHERE UPDATE_INSTANCE_NAME = '" + Serenity.sessionVariableCalled("IUInstanceName").toString()
					+ "')  AND TTL.TASK_TYPE_DESC = '" + sTaskReview + "' AND TSL.TASK_STATUS_DESC = '" + sTaskStatus
					+ "'   AND IRD.LIBRARY_STATUS_DESC ='" + sLibraryType + "'"
					+ "    GROUP BY IR.SUB_RULE_KEY,   IRD.MID_RULE_DOT_VERSION,   IRD.LIBRARY_STATUS_DESC, IRD.ARD_EXISTS_YN,  TTL.TASK_TYPE_DESC, PAYERS_4_RULE,\r\n"
					+ "    TSL.TASK_STATUS_DESC,  U.USER_ID) SELECT MID_RULE_DOT_VERSION,PROPOSAL_TYPE,PAYERS_4_RULE FROM CTE    where INSTR(PAYERS_4_RULE,',') < 1 and PAYERS_4_RULE <> 'N/A'";
			break;
		// AND PROPOSAL_TYPE ='"+sARDStatus+"' OR PROPOSAL_TYPE ='DEL'
		default:
			sQuery = "" + "SELECT MID_RULE_DOT_VERSION " + "  FROM IRDM.INTERP_RULES IR "
					+ "       JOIN IRDM.INTERP_RULE_DETAILS IRD "
					+ "          ON IR.INTERP_RULE_KEY = IRD.INTERP_RULE_KEY "
					+ "       JOIN IPDE.TASK_DETAILS TD ON TD.REFERENCE_RULE_ID = IR.INTERP_RULE_KEY "
					+ "       JOIN IPDE.TASK_TYPE_LKP TTL ON TTL.TASK_TYPE_KEY = TD.TASK_TYPE_KEY "
					+ "       JOIN IPDE.TASK_STATUS_LKP TSL " + "          ON TSL.TASK_STATUS_KEY = TD.TASK_STATUS_KEY "
					+ "WHERE     IR.IMPACT_KEY IN (SELECT IMPACT_KEY "
					+ "                               FROM IRDM.INTERP_IMPACTS II "
					+ "                                   JOIN IRDM.UPDATE_INSTANCES I "
					+ "                                       ON I.UPDATE_INSTANCE_KEY = "
					+ "                                             II.UPDATE_INSTANCE_KEY "
					+ "                              WHERE UPDATE_INSTANCE_NAME = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "') "
					+ "       AND TTL.TASK_TYPE_DESC = '" + sTaskReview + "' " + "       AND TSL.TASK_STATUS_DESC = '"
					+ sTaskStatus + "' " + "       AND IRD.ARD_EXISTS_YN = '" + sARDStatus + "' " + sRuleType;

			break;

		}

		System.out.println(" " + sType + " Query is : " + sQuery);

		verify("" + sType + " Qurey:  " + sQuery, true);

		if (sTaskType.equalsIgnoreCase("FINAL MD DEL MULTIPAYER2")) {

			sTaskType = "Final PO Review";
		}

		System.out.println("Task Review : " + sTaskReview);

		// Serenity.setSessionVariable("TaskType").to(sTaskType);
		Serenity.setSessionVariable("TaskType").to(sTaskReview);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);

		// String sDBMidRuleVersion = DBUtils.executeSQLQuery(sQuery);
		// ============================================================================>

		ArrayList<String> sDBMidRuleVersion1 = DBUtils.executeSQLQueryMultipleRows(sQuery);

		Serenity.setSessionVariable("MidRuleVersion").to(
				sDBMidRuleVersion1.get(GenericUtils.generate_Random_Number_for_Given_Range(sDBMidRuleVersion1.size())));

		verify("Mid Rule Version is :  " + Serenity.sessionVariableCalled("MidRuleVersion").toString(), true);

		System.out.println("session rule:" + Serenity.sessionVariableCalled("MidRuleVersion").toString());

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

	}

	@Step
	public void complete_cpm_decision_complete(String sDecision) throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oGenericUtils.gfn_Click_On_Object("label", "CPM Decision Complete");

		oGenericUtils.gfn_Click_On_Object("span", "OK");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void assignCPMForDBRule() {

		ArrayList<String> sArrPayers = new ArrayList<String>();

		String sQuery = "select PAYERS_4_RULE from IRDM.interp_rule_details where   MID_RULE_DOT_VERSION='10409.15' and INTERP_RULE_KEY in"
				+ "(select interp_rule_key from IRDM.interp_rules where impact_key = "
				+ "(select impact_key from irdm.interp_impacts where update_instance_key ="
				+ "(select update_instance_key from IRDM.update_instances where update_instance_name = 'AutoInstanceE2E')))";

		System.out.println("Query is : " + sQuery);

		String sPayerList = DBUtils.executeSQLQuery(sQuery);
		System.out.println("Query Status: " + sPayerList);

		String[] ArrPayers = sPayerList.split(",");

		for (int i = 0; i < ArrPayers.length; i++) {
			String sAllPayers = ArrPayers[i].trim().split(" ")[0];
			System.out.println(sAllPayers);

			sArrPayers.add(sAllPayers);

		}
	}

	@Step
	public void retire_rule_validation_in_DB(String arg1, String sDosDate, String sRetired) {

		String sRule = Serenity.sessionVariableCalled("MidRuleVersion").toString();

		String[] sRuleID = sRule.split("\\.");

		String sQuery = "select DOS_TO,DEACTIVATED_10 from rules.sub_rules Where MID_RULE_KEY = '" + sRuleID[0]
				+ "' and RULE_VERSION = '" + sRuleID[1] + "'";

		ArrayList<String> sResult = DBUtils.executeSQLQueryMultipleRows(sQuery);

		System.out.println(sResult);

		System.out.println(sResult.get(0));

		System.out.println(sResult.get(0).replaceAll("-", "/"));

		if (sRetired.equalsIgnoreCase("YES")) {

			if (sDosDate.equalsIgnoreCase(sResult.get(0)) && sResult.get(1).equalsIgnoreCase("-1")) {

				verify("Rule Retired successfully", true);
			}

		} else {

			verify("Rule is not Retired", true);
		}

	}

	@Step
	public void validate_return_response_comments() throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Comments"));

		int iCommnetList = getDriver()
				.findElements(By.xpath("//td[@aria-describedby='returnReViewCommentsGrid_returnComments']")).size();

		if (iCommnetList > 0) {

			for (int j = 0; j < iCommnetList; j++) {

				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

				boolean bln = oGenericUtils.gfn_Verify_String_Object_Exist(
						("(//td[@aria-describedby='returnReViewCommentsGrid_returnComments'])[" + (j + 1) + "]"));

				verify("Response Comments are Updated Successfully", bln);
			}
		}

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void add_code_function_with_poscode(String sCategory, String sCPTCode, String sPOSCode, String sOverrideFlag)
			throws InterruptedException {

		Serenity.setSessionVariable("POS CODE").to(sPOSCode);

		Serenity.setSessionVariable("Override Flag").to(sOverrideFlag);

		ADDCodeinMD(sCategory, sCPTCode);

	}

	@Step
	public void validate_POS_Sensitivity(String sProposalTypes, String sFilter, String sIndicator, String sReason,
			String sComments) {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "CPM Decisions"));

		switch (sProposalTypes) {

		case "MANUAL PROPOSAL":

			break;

		case "MANUAL PROPOSAL FILTER VALIDATION":

			switch (sFilter) {

			case "BLANK":

				break;

			case "NOT BLANK":

				break;
			default:
				Assert.assertTrue("validate_POS_Sensitivity function case values are not matched" + sFilter, false);

			}

			break;
		case "MANUAL PROPOSAL NO SESTIVITI VALIDATION":

			break;

		default:
			Assert.assertTrue("validate_POS_Sensitivity function case values are not matched" + sProposalTypes, false);
		}

	}

	@Step
	public void validateReviewSegment(String sTask, String sReviewSegment, String sDisplay)
			throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", sTask));

		// oGenericUtils.gfn_Verify_String_Object_Exist(oHomePage.QAReviewSegment);

		// oSeleniumUtils.Click_given_Locator(
		// StringUtil.replace(oHomePage.AnchorTag, "sValue", "BW And/BWO or
		// Config Logic"));

		int i = getDriver().findElements(By.xpath(StringUtil.replace(oHomePage.AnchorTag, "sValue", sReviewSegment)))
				.size();
		System.out.println("Ivalue:" + i);

		switch (sDisplay) {

		case "YES":

			if (i > 0) {
				verify(sReviewSegment + " Should be displayed in " + sTask, true);
			} else {
				verify(sReviewSegment + " Not  displayed in " + sTask, false);
			}
			break;

		case "NO":
			if (i > 0) {
				verify(sReviewSegment + " Should be displayed in " + sTask, false);
			} else {
				verify(sReviewSegment + " Not  displayed in " + sTask, true);
			}
			break;
		}

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void updateQARuleNDReview(String sQAReview) throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		verify("Summaries button clicked",
				oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Summaries")));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		verify("QA button clicked",
				oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "QA")));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.highlightElement(oHomePage.Update_Rule);

		verify("Update Rule Button Clicked", oSeleniumUtils.Click_given_Locator(oHomePage.Update_Rule));

		verify("Rule Updated message popup is displayed",
				oGenericUtils.gfn_Verify_String_Object_Exist(oHomePage.Msg_Rule_Update));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		Assert.assertTrue("Rule updated Successfully",
				oSeleniumUtils.is_WebElement_Displayed(oHomePage.Msg_Rule_Update));

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Ok"));

		oSeleniumUtils.highlightElement(oHomePage.Stub_RMR_ID);

		Serenity.setSessionVariable("Stub_RMR_ID").to(oHomePage.Stub_RMR_ID.getText());

		if (Serenity.sessionVariableCalled("RetireRule-Yes") != null) {

			verify("New MidRule version is displayed in WorkQueue Page",
					oGenericUtils.gfn_Verify_String_Object_Exist(oHomePage.NewMidruleversion));

			String sNewMidRuleVersion = oSeleniumUtils.get_TextFrom_Locator(oHomePage.NewMidruleversion);

			Serenity.setSessionVariable("MidRuleNewVersion").to(sNewMidRuleVersion);

			verify("New MidRule Version" + sNewMidRuleVersion, true);
		}

		System.out.println(Serenity.sessionVariableCalled("Stub_RMR_ID").toString());

		getDriver().switchTo().defaultContent();

		if (sQAReview.equalsIgnoreCase("YES")) {

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oSeleniumUtils.SwitchToFrame(oHomePage.frame);

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Summaries"));

			oSeleniumUtils.highlightElement(oHomePage.QA_Review_Completed);

			oSeleniumUtils.Click_given_Locator(oHomePage.QA_Review_Completed);

			oGenericUtils.gfn_Verify_Object_Exist("p", " QA Review Completed");

			Assert.assertTrue("Rule updated Successfully", oSeleniumUtils
					.is_WebElement_Displayed(StringUtil.replace(oHomePage.pTag, "sValue", " QA Review Completed")));

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Ok"));

			getDriver().switchTo().defaultContent();

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);
		}

	}

	@Step
	public void clickOnQAReviewComplete(String sQAReview) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		oSeleniumUtils.SwitchToFrame(oHomePage.frame);
		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Summaries"));
		oSeleniumUtils.highlightElement(oHomePage.QA_Review_Completed);
		oSeleniumUtils.Click_given_Locator(oHomePage.QA_Review_Completed);
		oGenericUtils.gfn_Verify_Object_Exist("p", " QA Review Completed");
		Assert.assertTrue("Rule updated Successfully", oSeleniumUtils
				.is_WebElement_Displayed(StringUtil.replace(oHomePage.pTag, "sValue", " QA Review Completed")));
		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Ok"));
		getDriver().switchTo().defaultContent();

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

	}

	@Step
	public void validateRMIInstruction(String sInstruction) {

		int sStatus = 0;

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		System.out.println("Manual RMR ID: " + Serenity.sessionVariableCalled("Stub_RMR_ID").toString());

		String sQuery = "Select INSTRUCTIONS from AUTH_MASTER.WORK_ORDER_LOG where WORK_ORDER_CODE = '"
				+ Serenity.sessionVariableCalled("Stub_RMR_ID").toString() + "'";

		System.out.println("Query: " + sQuery);

		System.out.println("Add Code in MD " + Serenity.sessionVariableCalled("AddCodeMD").toString());
		System.out.println("CategoryCode in MD " + Serenity.sessionVariableCalled("CategoryCode").toString());

		String sManualRMRInstr = DBUtils.executeSQLQuery(sQuery);

		if (sManualRMRInstr.contains(Serenity.sessionVariableCalled("AddCodeMD").toString())) {
			sStatus = sStatus + 1;
		}

		if (sManualRMRInstr.contains(Serenity.sessionVariableCalled("CategoryCode").toString())) {

			sStatus = sStatus + 1;
		}

		if (sStatus == 2) {

			verify("  Actual Value is " + sManualRMRInstr, true);
		} else {
			verify(" Expected value contains :" + Serenity.sessionVariableCalled("CategoryCode").toString()
					+ Serenity.sessionVariableCalled("AddCodeMD").toString() + " and Actual is " + sManualRMRInstr,
					false);
		}

		System.out.println(sManualRMRInstr);

	}

	@Step
	public void ADDCodeRecodeLogic(String sCategoryCode, String sCPTCode, String sValidation)
			throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		String sArrayCode = null;

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oGenericUtils.gfn_Click_On_Object("label", "Add Code");

		oGenericUtils.gfn_Verify_Object_Exist("h3", "Add Code");

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.StartNewCode);

		oGenericUtils.gfn_Click_On_Object("a", "Start New");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		int iYesBtn = getDriver()
				.findElements(
						By.xpath("//table[@id='Manual_Proposals_grid']//tr[2]//td[2]//select//option[text()='Yes']"))
				.size();

		if (iYesBtn > 0) {

			oSeleniumUtils.Click_given_Locator(
					"//table[@id='Manual_Proposals_grid']//tr[2]//td[2]//select//option[text()='Yes']");
		}

		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
				oHomePage.GetDynamicXPathWithRowCol("MANUALPROP FIELDS", 2, 3), "Manual Add");

		oSeleniumUtils.Enter_given_Text_Element(("//table[@id='Manual_Proposals_grid']//tr[2]//td[4]//textarea"),
				"Test");

		oSeleniumUtils.Enter_given_Text_Element(("//table[@id='Manual_Proposals_grid']//tr[2]//td[6]//input"),
				sCPTCode);

		oSeleniumUtils.Click_given_Locator(
				"//table[@id='Manual_Proposals_grid']//tr[2]//td[9]//select//option[text()='" + sCategoryCode + "']");

		// oSeleniumUtils.Enter_given_Text_Element(("//table[@id='Manual_Proposals_grid']//tr[2]//td[6]//input"),sCode);

		// oSeleniumUtils.Enter_given_Text_Element(("//table[@id='Manual_Proposals_grid']//tr[2]//td[6]//input"),sCode);

		oGenericUtils.gfn_Click_On_Object("label", "Save");

		String arr[] = { "01230-01232", "01240-0124T", "01250-01260", "0126T-01270", "01270-01272" };

		for (int i = 0; i < arr.length; i++) {

			sArrayCode = arr[i];

			int iErrorMsg = getDriver().findElements(By.xpath(StringUtil.replace(oHomePage.pTag, "sValue",
					" Review code need to be entered with Code From and Code To for Re-code logic"))).size();

			if (iErrorMsg > 0) {

				oGenericUtils.gfn_Click_On_Object("button", "Ok");
				oSeleniumUtils.Enter_given_Text_Element(("//table[@id='Manual_Proposals_grid']//tr[2]//td[6]//input"),
						sArrayCode);
				oGenericUtils.gfn_Click_On_Object("label", "Save");

			}

			int iMsg = getDriver().findElements(By.xpath(StringUtil.replace(oHomePage.h3Tag, "sValue", "Success")))
					.size();

			if (iMsg > 0) {
				break;
			}

		}

		oGenericUtils.gfn_Verify_Object_Exist("h3", "Success");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void right_Panel_NewEdit_Edit(String strCase) {

	}

	@Step
	public void retriveNonCandidateRules(String strCase) throws InterruptedException {

		oGenericUtils.gfn_Click_On_Object("div", "Retrieve Non-Candidate Rules");

		int iNonCandidateRules = getDriver().findElements(By.xpath("//div[text()='Non Candidate Rules']")).size();

		if (iNonCandidateRules > 0) {

			oGenericUtils.gfn_Click_On_Object("div", "OK");
			verify("No Non Candidate Rules available for this instance", false);
		}

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		System.out.print(Serenity.sessionVariableCalled("AdminInstance").toString());

		String sQuery = "select MID_RULE_DOT_VERSION from IRDM.interp_rule_details where INTERP_RULE_KEY in"
				+ "(select INTERP_RULE_KEY from IRDM.interp_rule_details IRD, rules.sub_rules RSB where IRD.MID_RULE_KEY=RSB.MID_RULE_KEY and IRD.RULE_VERSION=RSB.RULE_VERSION and INTERP_RULE_KEY in"
				+ "((select interp_rule_key from IRDM.interp_rules where CANDIDATE_DEF_KEY<> 1 and impact_key = "
				+ "(select impact_key from irdm.interp_impacts where update_instance_key ="
				+ "(select update_instance_key from IRDM.update_instances where update_instance_name = '"
				+ Serenity.sessionVariableCalled("AdminInstance").toString() + "')))))";

		System.out.println(sQuery);
		String sDBNonCandRule = DBUtils.executeSQLQuery(sQuery);

		Serenity.setSessionVariable("MidRuleVersion").to(sDBNonCandRule);

		System.out.println("MidRuleVersion: " + Serenity.sessionVariableCalled("MidRuleVersion").toString());

		oGenericUtils.gfn_Verify_Object_Exist("div", Serenity.sessionVariableCalled("MidRuleVersion").toString());

		oHomePage.closeAllTabs();

		oGenericUtils.gfn_Click_On_Object("a", "Interpretive Update Instances");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		clickOnInstanceInAdminAndNavigateToRuleReviewPage("FINAL PO DEL");

		int iNoNonCandidateRules = getDriver()
				.findElements(By
						.xpath("//div[text()='" + Serenity.sessionVariableCalled("MidRuleVersion").toString() + "' ]"))
				.size();

		if (iNoNonCandidateRules > 0) {

			verify("Non Candidate Rules available for this instance", false);
		} else {
			verify("No Non Candidate Rules available for this instance", true);
		}

	}

	@Step
	public void checkDeactivatedRuleMessages() throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oSeleniumUtils.SwitchToFrame(oHomePage.frame);
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.StartReview);
		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		oGenericUtils.gfn_Verify_Object_Exist(oHomePage.DeactivateErrorMsg, "20");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void validateRationalComments(String arg1) throws InterruptedException {

		boolean sCommentVal = oHomePage.rationalComments(ProjectVariables.SystemProposalComments);

		verify("System Proposal Comments :  ", sCommentVal);
	}

	@Step
	public void getAndValidateSystemProposals(String arg1) {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		int iProposals = getDriver().findElements(By.xpath(oHomePage.NewRuleVersion)).size();

		// select[@id='1_decisionAction.decisionActionKey']//option

		String sModify = oSeleniumUtils.get_StringTextFrom_Locator(oHomePage.GetDynamicXPath("SYSTEM PROPOSAL VAL", 2));
		System.out.println(sModify);

		Assert.assertTrue("Modify option is displayed", sModify.equalsIgnoreCase("Modify"));

		String sNoAction = oSeleniumUtils
				.get_StringTextFrom_Locator(oHomePage.GetDynamicXPath("SYSTEM PROPOSAL VAL", 3));
		System.out.println(sNoAction);

		Assert.assertTrue("No Action option is displayed", sNoAction.equalsIgnoreCase("No Action"));

		String sRemove = oSeleniumUtils.get_StringTextFrom_Locator(oHomePage.GetDynamicXPath("SYSTEM PROPOSAL VAL", 4));
		System.out.println(sRemove);

		Assert.assertTrue("Remove option is displayed", sRemove.equalsIgnoreCase("Remove"));

		if (iProposals > 4) {

			String sNoDecision = oSeleniumUtils
					.get_StringTextFrom_Locator(oHomePage.GetDynamicXPath("SYSTEM PROPOSAL VAL", 5));
			System.out.println(sNoDecision);

			Assert.assertTrue("Remove option is displayed", sNoDecision.equalsIgnoreCase("No Decision"));
		}

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void getNewRuleVersion() {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		int iNewVersionExist = getDriver().findElements(By.xpath(oHomePage.NewRuleVersion)).size();

		if (iNewVersionExist > 0) {

			oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.NewRuleVersion);
			String sNewVersionRule = oSeleniumUtils.get_StringTextFrom_Locator(oHomePage.NewRuleVersion);
			Serenity.setSessionVariable("MidRuleVersion").to(sNewVersionRule);
			System.out.print("New Version Rule:" + sNewVersionRule);

			verify("New version Rule is created: " + sNewVersionRule, true);
		} else {
			verify("New version Rule is not created: ", false);
		}

		getDriver().switchTo().defaultContent();
	}

	@Step
	public void ruleRequest(String arg1) throws InterruptedException {

		oSeleniumUtils.Click_given_WebElement(oHomePage.ReAssignNavigationAdmin);

		int i = getDriver()
				.findElements(By.xpath(StringUtil.replace(oHomePage.DivTag, "sValue", "Rule Review Request"))).size();

		if (i > 0) {
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.DivTag, "sValue", "Rule Review Request"));
		} else {
			oGenericUtils.gfn_Click_On_Object("span", "Rule Review Request");
		}

		oGenericUtils.gfn_Verify_Object_Exist("div", "Request Rule Review");

		oSeleniumUtils.Enter_given_Text_Element(oHomePage.RuleProposal,
				Serenity.sessionVariableCalled("MidRuleVersion").toString());

		oGenericUtils.gfn_Click_On_Object("div", "Request Rule Review");

		oGenericUtils.gfn_Verify_Object_Exist(oHomePage.RuleReqMsg, "10");

		oGenericUtils.gfn_Click_On_Object("div", "OK");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

	}

	@Step

	public void navigateRuleReviewPage(String sInstanceType) throws InterruptedException {

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oHomePage.closeAllTabs();

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag, "sValue", "Group Tasks"));

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag, "sValue", "Administration"));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(
				StringUtil.replace(oHomePage.AnchorTag, "sValue", "Interpretive Update Instances"));

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		clickInstanceinAdmin(sInstanceType);
	}

	@Step
	public void modifyCPMSystemProposalError(String sMDRecomdations, String sDecision) throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
				oHomePage.GetDynamicXPathWithRowCol("MANUALPROP FIELDS", 2, 3), sDecision);
		oGenericUtils.gfn_Click_On_Object("label", "Save");
		oGenericUtils.gfn_Verify_Object_Exist("h3", "Success");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		oSeleniumUtils.Click_given_Locator((StringUtil.replace(oHomePage.LabelTag, "sValue", "CPM Decision Complete")));

		int iError = getDriver().findElements(By.xpath(StringUtil.replace(oHomePage.h3Tag, "sValue", "Error"))).size();

		if (iError > 0) {

			oGenericUtils.gfn_Verify_Object_Exist("p",
					" Billed With AND/Billed Without OR logic needs to be completed before CPM Decision Complete.");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");

		}

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void modifyCPMManualProposals(String sMDRecomdations, String sDecision) throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.sFrame);
		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
				"//table[@id='cpmManualProposalGrid']//tr[2]//td[4]//select", sDecision);
		oSeleniumUtils.Enter_given_Text_Element("//table[@id='cpmManualProposalGrid']//tr[2]//td[6]//textarea",
				ProjectVariables.CPMManualProposalComments);
		// oGenericUtils.gfn_Click_On_Object("label", "Save");
		oGenericUtils.gfn_Click_String_object_Xpath("//div[@ng-show='hasManualProposals']//label[text()='Save']");
		oGenericUtils.gfn_Verify_Object_Exist("h3", "Success");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		getDriver().switchTo().defaultContent();

	}

	@Step
	public void ValidateROLLUpSystem(String arg1) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		int iPosRollBtn = getDriver().findElements(By.xpath(oHomePage.PosRollBtn)).size();

		if (iPosRollBtn > 0) {

			verify("Rollup button is displayed ", true);
		} else {
			verify("Rollup button is not displayed ", false);
		}

		oSeleniumUtils.Click_given_Locator(oHomePage.PosRollBtn);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		List<WebElement> iMDRollDecisionCount = getDriver()
				.findElements(By.xpath("//table[@id='s_Data_Model_Decisions_grid_1']//tr//td[2]//select"));

		String arr[] = { "No Action", "Remove" };

		for (int i = 0; i < iMDRollDecisionCount.size(); i++) {

			System.out.println(iMDRollDecisionCount.get(i).getText());

			iMDRollDecisionCount.get(i).click();

			iMDRollDecisionCount.get(i).click();

			int j = i + 1;

			String sRandomDecesion = arr[0];

			System.out.print(sRandomDecesion);

			System.out.println(
					(StringUtil.replace(oHomePage.GetDynamicXPath("MD ROLLDECESION", j), "sValue", sRandomDecesion)));

			oSeleniumUtils.Click_given_Locator(
					StringUtil.replace(oHomePage.GetDynamicXPath("MD ROLLDECESION", j), "sValue", sRandomDecesion));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			System.out.print(oHomePage.GetDynamicXPath("CPM PAYERSPEC COMMENTS", j));

			oSeleniumUtils.Enter_given_Text_Element(oHomePage.GetDynamicXPath("CPM PAYERSPEC COMMENTS", j),
					"Test Auto Comments");

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			arr[0] = arr[1];

		}

		// oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.CPMSystemPropsalSave);

		// oGenericUtils.gfn_Click_On_Object("button", "Ok");

		getDriver().switchTo().defaultContent();
	}

	@Step
	public void GetVersionInfo() {

		oSeleniumUtils.Click_given_Locator(oHomePage.VersionLink);

		String sVersionInfo = getDriver().findElement(By.xpath(oHomePage.VersionInfo)).getText();

		System.out.println(sVersionInfo);

	}

	@Step
	public void validate_the_user_role_operation(String sUserName, String sOperation) throws InterruptedException {

		String sXpath = "//div[text()='" + sUserName + "']/..//following-sibling::td//div[text()='" + sUserName
				+ "']/..//following-sibling::td[2]//input";

		SeleniumUtils.scrollingToGivenElement(getDriver(), sXpath);

		boolean isChecked = getDriver().findElement(By.xpath(sXpath)).isSelected();

		System.out.println("fdsffffffffffffffff+++++++++++++++++++++" + isChecked);

		if (sOperation.equalsIgnoreCase("unCheck") && isChecked == true) {

			oGenericUtils.gfn_Click_String_object_Xpath(sXpath);

		} else if (sOperation.equalsIgnoreCase("Check") && isChecked == false) {

			oGenericUtils.gfn_Click_String_object_Xpath(sXpath);

		} else {

			verify("User Role for US is Already " + sOperation + "ed", true);
		}

		boolean bln = oSeleniumUtils.is_WebElement_Displayed(
				"(//div[@class='GEFT4QHBNM GEFT4QHBHM GEFT4QHBEN GEFT4QHBBN'])[3]//div[text()='Save']");

		System.out.println("Save value is returning" + bln);

		if (bln == true) {

			oGenericUtils.gfn_Click_On_Object("div", "Save");

			oGenericUtils.gfn_Click_On_Object("div", "Yes");

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		}

	}

	@Step
	public void retriveMidRulePayer() throws InterruptedException {

		System.out
				.println("Rule Version no in Multipayer" + Serenity.sessionVariableCalled("MidRuleVersion").toString());

		String sQuery = "select PAYERS_4_RULE from IRDM.interp_rule_details where   MID_RULE_DOT_VERSION='"
				+ Serenity.sessionVariableCalled("MidRuleVersion").toString() + "' and INTERP_RULE_KEY in"
				+ "(select interp_rule_key from IRDM.interp_rules where impact_key = "
				+ "(select impact_key from irdm.interp_impacts where update_instance_key ="
				+ "(select update_instance_key from IRDM.update_instances where update_instance_name ='"
				+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "')))";

		System.out.println("Query is : " + sQuery);

		String sPayer = DBUtils.executeSQLQuery(sQuery);

		Serenity.setSessionVariable("PayerShots").to(StringUtils.substringBefore(sPayer, "(").trim());

		System.out.println("Query Status: " + Serenity.sessionVariableCalled("PayerShots"));
	}

	@Step
	public void validate_rule_id() throws InterruptedException {

		oHomePage.go_To_Rule(Serenity.sessionVariableCalled("MidRuleVersion").toString());

		boolean bln = !oSeleniumUtils.is_WebElement_Displayed(StringUtils.replace(oHomePage.MyTaskRuleVersion, "sValue",
				Serenity.sessionVariableCalled("MidRuleVersion").toString()));

		verify("Rule version should not be displayed", bln);

	}

	public void cancel_Retire_Rule() {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Summaries"));

		String statusCancelRetireBtn = getDriver()
				.findElement(By.xpath("//label[contains(text(),'Cancel Retire Rule')]")).getAttribute("disabled");
		System.out.println(statusCancelRetireBtn);

		if (statusCancelRetireBtn == "true") {

			verify("Cancel Retire button is Disabled", false);
		} else {
			verify("Cancel Retire button is Enabled", true);

		}

		oSeleniumUtils.highlightElement(oHomePage.CancelRetireRule);

		oSeleniumUtils.Click_given_WebElement(oHomePage.CancelRetireRule);

		GenericUtils.clickOk();

		oGenericUtils.gfn_Click_String_object_Xpath(StringUtil.replace(oHomePage.ButtonTag, "sValue", "Ok"));

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void retireRule(String sCreateRuleVersion) throws InterruptedException {

		// SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Summaries"));

		String statusCancelRetireBtn = getDriver()
				.findElement(By.xpath("//label[contains(text(),'Cancel Retire Rule')]")).getAttribute("disabled");

		if (!(statusCancelRetireBtn == "true")) {

			verify("Cancel Retire button is Disabled", true);
		} else {
			verify("Cancel Retire button is enabled", false);

		}

		oSeleniumUtils.Click_given_WebElement(oHomePage.RetireRuleBtn);

		oGenericUtils.gfn_Click_On_Object("span", "Retire Rule");

		// oSeleniumUtils.select_Given_Value_From_DropDown(oHomePage.Retirerule_MDecision,
		// "Do Not Retire Rule");

		oSeleniumUtils.Enter_given_Text_Element(oHomePage.MD_Comments, ProjectVariables.TestComments);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		if (sCreateRuleVersion.equalsIgnoreCase("Yes")) {

			if (oSeleniumUtils.is_WebElement_Present(oHomePage.Retirerule_MDecision)) {

				oSeleniumUtils.select_Given_Value_From_DropDown(oHomePage.Retirerule_MDecision, "Retire Rule");

				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			}
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.CreateNewRuleVersion, "sValue", "-1"));
		}

		if (sCreateRuleVersion.equalsIgnoreCase("No")) {

			if (oSeleniumUtils.is_WebElement_Present(oHomePage.Retirerule_MDecision)) {

				oSeleniumUtils.select_Given_Value_From_DropDown(oHomePage.Retirerule_MDecision, "Do Not Retire Rule");

				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			}

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.CreateNewRuleVersion, "sValue", "0"));
		}

		oGenericUtils.gfn_Click_On_Object("button", "Save");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		oGenericUtils.gfn_Click_On_Object("span", "Retire Rule");

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void validateRetireRuleLink(String sDisplay) throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		String sCurrentTab = null;

		int iCurrentTab = getDriver()
				.findElements(By.xpath(StringUtils.replace(oHomePage.LabelTag, "sValue", "CPM System Proposals:")))
				.size();
		if (iCurrentTab > 0) {

			sCurrentTab = "CPM Summaries";
		}

		iCurrentTab = getDriver().findElements(By.xpath(StringUtils.replace(oHomePage.SpanTag, "sValue", "QA Review")))
				.size();
		if (iCurrentTab > 0) {

			sCurrentTab = "QA";
		}

		iCurrentTab = getDriver().findElements(By.xpath(StringUtils.replace(oHomePage.SpanTag, "sValue", "Editorial")))
				.size();

		if (iCurrentTab > 0) {

			sCurrentTab = "Editorial";
		}

		iCurrentTab = getDriver()
				.findElements(By.xpath(StringUtils.replace(oHomePage.SpanTag, "sValue", "Testing Review"))).size();
		if (iCurrentTab > 0) {

			sCurrentTab = "Test";
		}

		System.out.print(sCurrentTab);

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Summaries"));

		oGenericUtils.gfn_Verify_Object_Exist("span", "Configurations Summary");

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		switch (sDisplay) {

		case "NO":

			if (!(oSeleniumUtils
					.is_WebElement_Displayed(StringUtil.replace(oHomePage.SpanTag, "sValue", "Retire Rule")))) {

				verify("Retire Rule Button is Disabled", true);
			} else {

				verify("Retire Rule Button is Enabled", false);

			}
			break;

		case "YES":

			if (!(oSeleniumUtils
					.is_WebElement_Displayed(StringUtil.replace(oHomePage.SpanTag, "sValue", "Retire Rule")))) {

				verify("Retire Rule Button is Disabled", false);
			} else {

				verify("Retire Rule Button is Enabled", true);

			}
			break;
		}

		System.out.println(sCurrentTab);
		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", sCurrentTab));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void retireRuleValidationInDB(String sDosDate, String sRetired) throws Exception {

		// Serenity.setSessionVariable("MidRuleVersion").to("11616.3");
		String[] sRuleVersion = Serenity.sessionVariableCalled("MidRuleVersion").toString().split("\\.");

		String sQuery = "select DEACTIVATED_10 from rules.sub_rules Where MID_RULE_KEY = '" + sRuleVersion[0]
				+ "' and RULE_VERSION = '" + sRuleVersion[1] + "'";

		String sQuery2 = "select DOS_TO from rules.sub_rules Where MID_RULE_KEY = '" + sRuleVersion[0]
				+ "' and RULE_VERSION = '" + sRuleVersion[1] + "'";

		System.out.println(sQuery);

		String sResult = DBUtils.executeSQLQuery(sQuery);
		System.out.println("result1 " + sResult);

		String sResult2 = DBUtils.executeSQLQuery(sQuery2);

		String[] sResultDOS = sResult2.split(" ");

		System.out.println("results2 " + sResultDOS[0]);

		switch (sRetired) {

		case "YES":

			if (((sResult.equalsIgnoreCase("-1")) && ((sResultDOS[0].equalsIgnoreCase(sDosDate))))) {

				verify("Validate Retire Rule Status in RMI-YES :"
						+ Serenity.sessionVariableCalled("MidRuleVersion").toString()
						+ "Rule Retired successfully Validated in RMI", true);

			} else {
				verify("Validate Retire Rule Status in RMI-YES :"
						+ Serenity.sessionVariableCalled("MidRuleVersion").toString() + " is Rule not Retired", false);
			}

			break;

		case "NO":

			if (((sResult.equalsIgnoreCase("0")) && ((sResultDOS[0].equalsIgnoreCase(sDosDate))))) {

				verify("Validate Retire Rule Status in RMI-NO :"
						+ Serenity.sessionVariableCalled("MidRuleVersion").toString()
						+ "Rule is not Retired Validated in RMI", true);

			} else {
				verify("Validate Retire Rule Status in RMI-NO :"
						+ Serenity.sessionVariableCalled("MidRuleVersion").toString() + " is Rule Retired", false);
			}

			break;
		}

	}

	@Step
	public void validateCPMImpactCodeListGrid(String sTab) throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		switch (sTab) {

		case "Summaries":
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "Summaries"));
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag, "sValue", "Impact Code List"));
			int iImpactCodeGridData = getDriver()
					.findElements(By
							.xpath("//table[@id='impactCodeListGrid']//tr[@class='ui-widget-content jqgrow ui-row-ltr']"))
					.size();
			int iImpactCPTCode = getDriver()
					.findElements(By.xpath("//table[@id='impactCodeListGrid']//td[@title='0019T']")).size();
			if (iImpactCodeGridData > 0) {

				verify("Impact Code List Details are displayed in Summaries ", true);
				if (iImpactCPTCode > 0) {
					verify("CPT code is displayed in Summaries ", true);
				} else {
					verify("CPT code is NOT displayed in Summaries ", true);
				}

			} else {

				verify("Impact Code List Details are displayed in Summaries ", false);
			}
			break;

		case "CPM Summaries":
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag, "sValue", "CPM Summaries"));
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag, "sValue", "Impact Code List"));
			int iImpactCodeGridData2 = getDriver()
					.findElements(By
							.xpath("//table[@id='impactCodeListGrid']//tr[@class='ui-widget-content jqgrow ui-row-ltr']"))
					.size();
			int iImpactCPTCode2 = getDriver()
					.findElements(By.xpath("//table[@id='impactCodeListGrid']//td[@title='0019T']")).size();

			if (iImpactCodeGridData2 > 0) {

				verify("Impact Code List Details are displayed in CPM Summaries ", true);

				if (iImpactCPTCode2 > 0) {
					verify("CPT code is displayed in CPM Summaries ", true);
				} else {
					verify("CPT code is NOT displayed in CPM Summaries ", true);
				}

			} else {

				verify("Impact Code List Details are displayed in CPM Summaries ", false);
			}
			break;
		}

		// oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.TabheadingTag,
		// "sValue", "Modify Decisions"));

		oGenericUtils.gfn_Click_String_object_Xpath("//label[contains(text(),'Modify Decisions')]");
		oSeleniumUtils.Enter_given_Text_Element(
				"//table[@id='cpmProposalGrid']//textarea[@name='cpmInterpComment.comments']", "Test");
		oGenericUtils.gfn_Click_String_object_Xpath("//div[@ng-show='hasSystemProposals']//label[text()='Save']");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void validateCPTARD() throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		int iCPTARDLink = getDriver().findElements(By.xpath("//a[text()='CPT ARD']")).size();

		if (iCPTARDLink > 0) {
			verify("CPTARD Link is displayed ", true);
			oGenericUtils.gfn_Click_String_object_Xpath("//a[text()='CPT ARD']");
		} else {
			verify("CPTARD Link is NOT displayed ", false);

		}
		getDriver().switchTo().defaultContent();

		SeleniumUtils.switchWindowUsingWindowCount(2, 2, getDriver());

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

		oSeleniumUtils.Enter_given_Text_Element(oHomePage.BuilderARD_UserName, "iht_ittest01");
		oSeleniumUtils.Enter_given_Text_Element(oHomePage.BuilderARD_Password, "Ihealth123");
		oSeleniumUtils.Click_given_WebElement(oHomePage.Builder_SignInBtn);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oGenericUtils.gfn_Verify_String_Object_Exist("//div[@class=' x-form-field-wrap x-form-readonly x-component']");

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		SeleniumUtils.switchWindowUsingWindowCount(2, 1, getDriver());

	}

	@Step
	public void validateConfigPayerSpecificDecision() throws InterruptedException {

		oSeleniumUtils.SwitchToFrame(oHomePage.frame);

		oGenericUtils.gfn_Click_String_object_Xpath(
				StringUtil.replace(oHomePage.SpanTag, "sValue", "Configurations Summary"));

		int iConfigSummaryData = getDriver()
				.findElements(By.xpath(StringUtil.replace(oHomePage.SpanTag, "sValue",
						" No Configuration Summary data displayed because Payer specific decisions exists for the rule")))
				.size();

		if (iConfigSummaryData > 0) {
			verify("Configurations Summary message for payer specific Decision is displayed successfully ", true);
			oGenericUtils.gfn_Click_String_object_Xpath("//a[text()='CPT ARD']");
		} else {
			verify("Configurations Summary message for payer specific Decision is NOT displayed ", false);

		}

		getDriver().switchTo().defaultContent();

	}

	@Step
	public void setRequirePresentation() throws InterruptedException {

		oHomePage.go_To_Rule(Serenity.sessionVariableCalled("MidRuleVersion").toString());

		verify("MidRule Version is :" + Serenity.sessionVariableCalled("MidRuleVersion").toString(), true);
		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SelectRule, "sValue",
				Serenity.sessionVariableCalled("MidRuleVersion").toString()));
		oSeleniumUtils
				.Click_given_Locator((StringUtil.replace(oHomePage.DivTag, "sValue", "Set Requires Presentation")));
		oSeleniumUtils
				.Click_given_Locator((StringUtil.replace(oHomePage.DivTag, "sValue", "Set to Requires Presentation")));

	}

	@Step
	public void returnRequiresPresentationRule() throws InterruptedException {

		oSeleniumUtils.Click_given_WebElement(oHomePage.Col_Rule_Grid);

		oSeleniumUtils.Click_given_WebElement(oHomePage.ReAssignNavigationAdmin);

		int i = getDriver()
				.findElements(
						By.xpath(StringUtil.replace(oHomePage.DivTag, "sValue", "Return Requires Presentation Rule")))
				.size();

		if (i > 0) {
			oSeleniumUtils.Click_given_Locator(
					StringUtil.replace(oHomePage.DivTag, "sValue", "Return Requires Presentation Rule"));
		} else {
			oGenericUtils.gfn_Click_On_Object("span", "Return Requires Presentation Rule");
		}

		oGenericUtils.gfn_Verify_Object_Exist("div", "Return Requires Presentation Rule");

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ReturnRequirePresentRuleChk, "sValue",
				Serenity.sessionVariableCalled("MidRuleVersion").toString()));

		oGenericUtils.gfn_Click_On_Object("div", "Return Selected Rules for CPM Decision Review");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

	}

	@Step
	public void capturingAllFilterValues(String sWorkQueue) throws InterruptedException {

		List<String> sFilters = Arrays.asList(sWorkQueue.split(","));

		for (int i = 0; i < sFilters.size(); i++) {

			oSeleniumUtils.Click_given_Locator(
					StringUtil.replace(oHomePage.ActiveFiterDropDwnBtn, "sValue", sFilters.get(i).replace(":", "")));

			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);

			String[] sFilterValues = oSeleniumUtils.get_All_Text_from_Locator(oHomePage.FilterValuesInDropDown);

			String sValue = sFilters.get(i).replace(":", "");

			System.out.println("Filter List size" + sFilterValues.length);

			Serenity.setSessionVariable(sValue).to(sFilterValues);

			if (oSeleniumUtils.is_WebElement_Displayed(oHomePage.CloseIconFilterDropDown) == true) {

				oGenericUtils.gfn_Click_String_object_Xpath("//label[contains(text(),'" + sValue + "')]");

				// oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.CloseIconFilterDropDown);

				if (oSeleniumUtils.is_WebElement_Displayed(oHomePage.CloseIconFilterDropDown) == true) {

					oGenericUtils.gfn_Click_String_object_Xpath("//label[contains(text(),'" + sValue + "')]");
				}

			} else {

				oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.ActiveFiterDropDwnBtn, "sValue",
						sFilters.get(i).replace(":", "")));
			}

		}

	}

	@Step
	public void completeAssignments(String sTaskType, String sUser, String sOptional) throws Exception {

		oInstanceCreation.completeAssignments(sTaskType, sUser, sOptional);
	}

	/*
	 * public void the_user_should_be_displayed_with_the_comments_tab() {
	 * 
	 * 
	 * }
	 */

	@Step
	public void clickOnValidate(String arg1, String arg2) throws Exception {

		switch (arg1) {

		case "Review DC Assignments":
			SeleniumUtils.defaultWait(3000);

			oGenericUtils.gfn_Click_On_Object("span", "Decision Capture Assignments");

			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			Assert.assertTrue("Save is not Disabled in DC Assignments", !oSeleniumUtils.is_WebElement_Enabled(
					StringUtils.replace(oInstanceCreation.DCAssignmentsButtons, "sValue", " Save")));

			Assert.assertTrue("Cancel is not Disabled in DC Assignments", !oSeleniumUtils.is_WebElement_Enabled(
					StringUtils.replace(oInstanceCreation.DCAssignmentsButtons, "sValue", " Cancel")));

			Assert.assertTrue("Copy Global Assignments is Enabled in DC Assignments",
					oSeleniumUtils.is_WebElement_Enabled(StringUtils.replace(oInstanceCreation.DCAssignmentsButtons,
							"sValue", "Copy Global Assignments")));

			oGenericUtils.gfn_Click_String_object_Xpath(
					StringUtils.replace(oInstanceCreation.DCAssignmentsButtons, "sValue", "Copy Global Assignments"));

			oGenericUtils.gfn_Verify_Object_Exist("div", "Copy of Global Assignments is Complete");

			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			break;

		case "Re-Run Impact Analysis":
		case "Subsequent Rule Impact Analysis":
		case "Initial Rule Impact Analysis":		

			oGenericUtils.gfn_Click_On_Object("button", " Request Impact Analysis");
			
		    String sXpath="//label[text()='"+arg1+"']/preceding-sibling::input";
		   
		    if (arg1.equalsIgnoreCase("Initial Rule Impact Analysis")) {
				
		    	Assert.assertTrue("Initial Rule Impact Analysis is not selected ", oSeleniumUtils.is_WebElement_Selected(sXpath));

				oGenericUtils.gfn_Verify_String_Object_Exist("//label[text()='Subsequent Rule Impact Analysis']/preceding-sibling::input[@disabled]");

				oGenericUtils.gfn_Verify_String_Object_Exist("//label[text()='Re-Run Impact Analysis']/preceding-sibling::input[@disabled]");
			}

			if (arg2.equalsIgnoreCase("Impact Instance")) {
				
				oGenericUtils.gfn_Click_String_object_Xpath(sXpath);

				oGenericUtils.gfn_Click_On_Object("button", " Request Impact Analysis");

				oGenericUtils.gfn_Verify_String_Object_Exist(oInstanceCreation.ImpactMessesage);

				oGenericUtils.gfn_Click_String_object_Xpath("//a[@class='ui-menuitem-link ng-star-inserted']//span[text()='Interpretive Update Instances']");

				oGenericUtils.gfn_Click_On_Object("button", " Refresh");

				oGenericUtils.gfn_Click_On_Object("a", Serenity.sessionVariableCalled("NewInstanceName"));
			}

			SeleniumUtils.defaultWait(20000);

			break;
		
		case "Export to Excel":

			oGenericUtils.gfn_Click_On_Object("button", " Export to Excel");

			SeleniumUtils.defaultWait(8000);

			VerifyTheExcelFileDownload();

			break;

		case "ExportExcel in AdminScrub":

			oGenericUtils.gfn_Click_On_Object("a", "Export");

			SeleniumUtils.defaultWait(8000);

			VerifyTheCSVColumns();

			break;

		}

	}

	@Step
	public void VerifyTheExcelFileDownload() throws Exception {

		SeleniumUtils.defaultWait(5000);
		Date date = new Date();

		SimpleDateFormat strDate = new SimpleDateFormat("MM/dd/yyyy hh:mm");

		System.out.println(ProjectVariables.sTestDataFilePath);

		File file = new File(ProjectVariables.sTestDataFilePath);

		System.out.println("Latest file download" + strDate.format(file.lastModified()));

		String sSystemDate = strDate.format(date);

		System.out.println("System Date" + sSystemDate);

		String sDownloadDate = strDate.format(file.lastModified());
		SeleniumUtils.defaultWait(5000);
		boolean fileDownload = sDownloadDate.equalsIgnoreCase(sSystemDate);

		verify("File Downloaded successfully", fileDownload);

		verify("Deleted sheet is displayed as expected ", ("Deleted").equalsIgnoreCase(ExcelUtils.getSheetName(0)));
		verify("New sheet is displayed as expected ", ("New").equalsIgnoreCase(ExcelUtils.getSheetName(1)));
		verify("Revised sheet is displayed as expected ", ("Revised").equalsIgnoreCase(ExcelUtils.getSheetName(2)));
		verify("Similar sheet is displayed as expected ", ("Similar").equalsIgnoreCase(ExcelUtils.getSheetName(3)));

	}

	@Step
	public void should_be_viewing_the_count_at_bottom_right_corner_of_screen_with(String arg1, String arg2) {

		oAdminScrubPage.should_be_viewing_the_count_at_bottom_right_corner_of_screen_with(arg1, arg2);

	}

	public void should_be_viewing_the_count_below_the_with(String arg1, String arg2, String arg3) {

		oAdminScrubPage.should_be_viewing_the_count_below_the_with(arg1, arg2, arg3);

	}

	public void only_Processed_candidate_rules_should_be_considered() {

	}

	public void should_be_displayed_with_the_in_the_drill_down_format(String arg1) throws InterruptedException {
		oAdminScrubPage.should_be_displayed_with_the_in_the_drill_down_format(arg1);

	}

	public void user_switch_to_Page(String sPage) {

		switch (sPage) {
		case "Admin Scrub":
		case "Report Screen":

			SeleniumUtils.switchWindowUsingWindowCount(2, 3, getDriver());

			break;
		case "Task Mangement":

			SeleniumUtils.switchWindowUsingWindowCount(2, 2, getDriver());

			break;
		case "JBPM Home":

			// SeleniumUtils.switchWindowUsingWindowCountClose(2, 2,
			// getDriver());

			SeleniumUtils.switchWindowUsingWindowCount(2, 1, getDriver());

			break;
			
		
		}
		
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

	}

	@Step
	public void VerifyTheCSVColumns() throws Exception {

		String sName = null;

		File getLatestFile = GenericUtils.getLatestFilefromDir(ProjectVariables.sTestDataFilePath);

		String fileName = getLatestFile.getName();

		String sNewFileName = ProjectVariables.sTestDataFilePath + fileName;

		System.out.println("Sheet Name" + sNewFileName);

		verify("File Path: " + sNewFileName, true);

		try {

			FileReader fr = null;
			BufferedReader br = null;
			fr = new FileReader(sNewFileName);
			br = new BufferedReader(fr);
			String line = null;
			ArrayList<Object> myList = new ArrayList<Object>();
			line = br.readLine();
			while (line != null) {
				myList.add(line);
				line = br.readLine();
			}
			System.out.println(myList.get(0));

			List<String> sFilters = Arrays.asList(myList.get(0).toString().split(","));

			String arr[] = ProjectVariables.AdminCSV;

			for (int i = 0; i < sFilters.size(); i++) {

				if (sFilters.get(i).trim().equalsIgnoreCase(arr[i].trim())) {
					System.out.println("true " + arr[i]);
				} else {
					System.out.println("false" + arr[i]);
				}
				if (arr[i] == null) {
					System.out.println("loopbreak");
					break;
				}
			}

		} // try end
		catch (IOException ioex) {
			System.out.print(ioex);
		}
	} // file() method ending

	public boolean editorialWindow() {

		int iEditorialWarning = getDriver().findElements(By.xpath("//h4[text()='Editorial Update']")).size();

		if (iEditorialWarning > 0) {

			oSeleniumUtils.Click_given_Locator("//i[@class='fa fa-close']");
		}

		return true;

	}

	@Step

	public void userGoToRuleReivewAndValidateRule(String sRole, String sInstanceType, String sColoumnName,
			String sColumnValue) throws InterruptedException {

		boolean bflag = oAdminPage.goToActionsInAdminTab("");
		verify("Actions button is displayed in Admin Tab", bflag);

		userNavigateToIUInstancesScreen("Rule Review");

		oHomePage.go_To_Rule(Serenity.sessionVariableCalled("MidRuleVersion").toString());

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		validateRuleReviewColumnValues(sColoumnName, sColumnValue);

	}

	@Step
	public void goToRuleReviewInAdminTab(String sOperation, String sUser, String sInstanceType) throws Exception {

		/*
		 * // Serenity.setSessionVariable("MidRuleVersion").to("17057.1"); //
		 * Serenity.setSessionVariable("IUInstanceName").to("TestAutoInstance1")
		 * ; oGenericUtils.gfn_Click_String_object_Xpath(
		 * "//span[@class='tab-close ng-star-inserted']");
		 * SeleniumUtils.defaultWait(2000); boolean bflag =
		 * oAdminPage.goToActionsInAdminTab(""); verify(
		 * "Actions button is displayed in Admin Tab", bflag);
		 */

		userNavigateToIUInstancesScreen("ICD10-Admin");

		oAdminPage.clickInstaneInAdmin("Admin", sInstanceType, "Task", "");

		userNavigateToIUInstancesScreen("Rule Review");

		oHomePage.go_To_Rule(Serenity.sessionVariableCalled("MidRuleVersion").toString());

		// selectRule("RuleID-Admin");

		oGenericUtils.gfn_Click_String_object_Xpath("(//div[@class='ui-chkbox-box ui-widget ui-state-default'])[1]");

		// oGenericUtils.gfn_Click_String_object_Xpath("//a[text()='"+Serenity.sessionVariableCalled("MidRuleVersion").toString()+"']/parent::td/../td//p-tablecheckbox//div//div[2]");

		verify("MidRule Version is :" + Serenity.sessionVariableCalled("MidRuleVersion").toString(), true);
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oGenericUtils.gfn_Click_On_Object("button", "Reassign");
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		switch (sOperation) {
		case "Reassign":

			oGenericUtils.gfn_Verify_Object_Exist("th", "Re-Assign To");

			oGenericUtils.gfn_Verify_Object_Exist("button", "Submit Reassignments");

			oGenericUtils.gfn_Verify_Object_Exist("div", "Bulk Reassignment Errors");

			SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
					"//select[@class='selectpicker form-control show-tick']", sUser);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oGenericUtils.gfn_Click_On_Object("button", "Submit Reassignments");

			oGenericUtils.gfn_Click_On_Object("button", "Yes");

			verify("Confirm Messege is displayed", true);

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			verify("Decision Saved Sucessfully", true);

			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			oGenericUtils.gfn_Click_String_object_Xpath("//span[@class='tab-close ng-star-inserted']");

			break;

		case "CPMReassign":

			oGenericUtils.gfn_Verify_Object_Exist("div", "Reassign the selected rules to:");
			oGenericUtils.gfn_Click_String_object_Xpath("//span[@class='dropdown-down']");
			SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
			oSeleniumUtils.Enter_given_Text_Element("//input[@aria-label='multiselect-search']", sUser);
			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
			oSeleniumUtils.Click_given_Locator("//div[text()='" + sUser + "']");
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			oGenericUtils.gfn_Click_String_object_Xpath("//span[@class='dropdown-up']");
			oGenericUtils.gfn_Click_On_Object("button", "OK");
			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
//			oGenericUtils.gfn_Verify_Object_Exist("div", "Sucessfully reassigned.");
//			verify("Confirm Messege is displayed", true);
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			break;

		}

	}

	public void click_on_the_button(String arg1) throws InterruptedException {

		switch (arg1) {

		case "Apply Decision-Yes":

			verifyTheDecisionAndOperation(arg1,
					"Are you sure you want to apply the Bulk Decision to the selected rule proposals?");

			oGenericUtils.gfn_Verify_Object_Exist("div", "Saved Successfully!");

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			oGenericUtils.gfn_Click_String_object_Xpath(
					"(//span[contains(@class,'ui-chkbox-icon')])[2]/../../../../following-sibling::td//a");

			break;
		case "Apply Decision-No":

			verifyTheDecisionAndOperation(arg1,
					"Are you sure you want to apply the Bulk Decision to the selected rule proposals?");

			oGenericUtils.gfn_Verify_Object_Exist("button", StringUtils.substringBefore(arg1, "-"));

			break;
		case "Move Rules Back to Review-Yes":

			verifyTheDecisionAndOperation(arg1,
					"Continuing with the Retrieve Non-Candidate Rules function will include the selected rules in the Rule Review process. Continue?");

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			oGenericUtils.gfn_Click_String_object_Xpath(
					"//span[contains(text(),'" + ProjectVariables.NonCandiateRulesInstance + "')]");

			break;
		case "Move Rules Back to Review-No":

			verifyTheDecisionAndOperation(arg1,
					"Continuing with the Retrieve Non-Candidate Rules function will include the selected rules in the Rule Review process. Continue?");

			oGenericUtils.gfn_Verify_Object_Exist("button", StringUtils.substringBefore(arg1, "-"));

			break;
		case "Filters-Reset":

//			List<String> sFilterValueList = Arrays
//					.asList(Serenity.sessionVariableCalled("FilterValues").toString().split(";"));
			String arrProp[] = { "Topic", "Medical Policy","Decision","Proposal Type" }; 
			
			
			List<String> sFilterValueList = Arrays
					.asList();

			oGenericUtils.gfn_Click_On_Object("button", " Reset");

			oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			for (int i = 0; i < arrProp.length; i++) {
				String sArrayPropCode = arrProp[i];
				boolean bln = !oSeleniumUtils
						.is_WebElement_Displayed("//div[text()='" + sArrayPropCode + "']");

				verify("After clicking on Reset filter value is not displaying" + sArrayPropCode, bln);
			}

			break;

		default:
			oGenericUtils.gfn_Click_On_Object("button", arg1);
			break;
		}

		oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		SeleniumUtils.defaultWait(3000);
	}

	private void verifyTheDecisionAndOperation(String arg1, String sPromptMessage) throws InterruptedException {

		oGenericUtils.gfn_Click_On_Object("button", StringUtils.substringBefore(arg1, "-"));

		oGenericUtils.gfn_Verify_Object_Exist("div",
				"Are you sure you want to apply the Bulk Decision to the selected rule proposals?");

		oGenericUtils.gfn_Click_On_Object("button", StringUtils.substringAfter(arg1, "-"));

		oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

	}

	@Step
	public void selects_rules_for_BulkDecision(String sRuleType, String sProposalsType, String sCode) {

		oGenericUtils.gfn_Click_String_object_Xpath("(//span[contains(@class,'ui-chkbox-icon')])[2]/..");

		oGenericUtils.gfn_Click_String_object_Xpath("(//span[contains(@class,'ui-chkbox-icon')])[3]/..");

		String sPropCount1 = oSeleniumUtils.get_StringTextFrom_Locator(
				"(//span[contains(@class,'ui-chkbox-icon')])[2]/../../../../following-sibling::td[3]//span");

		String sPropCount2 = oSeleniumUtils.get_StringTextFrom_Locator(
				"(//span[contains(@class,'ui-chkbox-icon')])[3]/../../../../following-sibling::td[3]//span");

		int iPropCount1 = Integer.parseInt(sPropCount1);

		int iPropCount2 = Integer.parseInt(sPropCount2);

		Serenity.setSessionVariable("iPropCount1").to(iPropCount1);

		Serenity.setSessionVariable("iPropCount2").to(iPropCount2);

	}

	@Step
	public void can_select_the_from_the_Decision_list_drop_down(String arg1) throws InterruptedException {

		int iPropCountinPopup = Integer.parseInt(oSeleniumUtils
				.get_TextFrom_Locator("//label[text()='Proposal Count to be Impacted:']/../following-sibling::td")
				.trim());

		int iPropCount1 = Serenity.sessionVariableCalled("iPropCount1");

		int iPropCount2 = Serenity.sessionVariableCalled("iPropCount2");

		boolean bln = iPropCountinPopup == iPropCount1 + iPropCount2;

		System.out.println("Sum of Proposals" + bln);

		String sImpactedCodes = oSeleniumUtils
				.get_StringTextFrom_Locator("//label[text()='Rule Count to be Impacted:']/../following-sibling::td");

		boolean bln1 = sImpactedCodes.trim().equalsIgnoreCase("2");

		System.out.println("No fo Codes" + bln1);

		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
				"//label[text()='Decision for these Proposals:']/../following-sibling::td//select", "Add Code");

		oSeleniumUtils.Enter_given_Text_Element(
				"//label[text()='Enter New Rationale Comment']/../following-sibling::td//textarea", "Bulk Decisions");

		/*
		 * oGenericUtils.gfn_Verify_Object_Exist("div",
		 * "Are you sure you want to apply the Bulk Decision to the selected rule proposals?"
		 * );
		 * 
		 * oGenericUtils.gfn_Click_On_Object("button", "Yes");
		 * 
		 * oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		 * 
		 * oGenericUtils.gfn_Verify_Object_Exist("div", "Saved Successfully!");
		 * 
		 * oGenericUtils.gfn_Click_On_Object("button", "Ok");
		 * 
		 * oGenericUtils.gfn_Click_String_object_Xpath(
		 * "(//span[contains(@class,'ui-chkbox-icon')])[2]/../../../../following-sibling::td//a"
		 * );
		 * 
		 * oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		 * 
		 * SeleniumUtils.defaultWait(3000);
		 */

	}

	public void should_be_displayed_with_BulkDecision_Flag(String arg1, String arg2) throws InterruptedException {

		oGenericUtils.gfn_Click_String_object_Xpath(
				"(//span[contains(@class,'ui-chkbox-icon')])[2]/../../../../following-sibling::td//a");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		SeleniumUtils.defaultWait(3000);

		oGenericUtils.gfn_Verify_String_Object_Exist("//th[@ng-reflect-ng-switch='interpRuleCpt.cptCode']//input");

		oSeleniumUtils.Enter_given_Text_Element("//th[@ng-reflect-ng-switch='interpRuleCpt.cptCode']//input", "I48.11");

		System.out.println(oGenericUtils.gfn_Click_On_Object("td", " BULK DEC "));

	}

	public void should_be_able_to_view_and_select_multi_select_and_remove_the_decision() throws InterruptedException {

		int i = getDriver().findElements(By.xpath("//span[@class='ag-selection-checkbox']")).size();

		String sVal;

		Serenity.setSessionVariable("iVal").to(GenericUtils.generate_Random_Number_for_Given_Range(i));

		sVal = Serenity.sessionVariableCalled("iVal").toString();

		if (sVal.equalsIgnoreCase("0")) {

			sVal = "1";
		}

		oGenericUtils.gfn_Click_String_object_Xpath("(//span[@class='ag-selection-checkbox'])[" + sVal + "]");

		/*
		 * oSeleniumUtils.get_StringTextFrom_Locator(
		 * "(//span[@class='ag-selection-checkbox'])["+sVal+
		 * "]/../..//following-sibling::div[1]");
		 * 
		 * oSeleniumUtils.get_StringTextFrom_Locator(
		 * "(//span[@class='ag-selection-checkbox'])["+sVal+
		 * "]/../..//following-sibling::div[2]");
		 * 
		 * oSeleniumUtils.get_StringTextFrom_Locator(
		 * "(//span[@class='ag-selection-checkbox'])["+sVal+
		 * "]/../..//following-sibling::div[3]");
		 * 
		 * oSeleniumUtils.get_StringTextFrom_Locator(
		 * "(//span[@class='ag-selection-checkbox'])["+sVal+
		 * "]/../..//following-sibling::div[4]");
		 */

		String sRationalComments = oSeleniumUtils.get_StringTextFrom_Locator(
				"(//span[@class='ag-selection-checkbox'])[" + sVal + "]/../..//following-sibling::div[5]");

		oGenericUtils.gfn_Click_On_Object("button", "Remove Decision(s)");

		oGenericUtils.gfn_Verify_Object_Exist("div",
				"This will remove Bulk Decisions for proposals that have not started PO review. Are you sure?");

		oGenericUtils.gfn_Click_On_Object("button", "Yes");

		oHomePage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		oGenericUtils.gfn_Verify_Object_Exist("div", sRationalComments.trim());

	}

	@Step
	public void Select_Multi_select_the_rules_that_you_want_to_retrieve_back() throws InterruptedException {

		oGenericUtils.gfn_Click_On_Object("h4", "Select Non-Candidate rules to be retrieved for Rule Review");

		String[] sColumnHeaders = { "Rule Version", "Library Status", "Proposal Count", "Medical Policy", "Topic",
				"Decision Point", "Rule Header", "Claim Types", "Payers", "Proposals" };

		for (int i = 0; i < sColumnHeaders.length; i++) {

			oGenericUtils.gfn_Verify_Object_Exist("th", sColumnHeaders[i].trim());
		}

		oGenericUtils.gfn_Click_String_object_Xpath(
				"//th[text()='Rule Version']/../../../../../following::div//table//td[1]//p-tablecheckbox");

		String sRule = oSeleniumUtils
				.get_TextFrom_Locator("//th[text()='Rule Version']/../../../../../following::div//table//td[2]//span");

		Serenity.setSessionVariable("MidRuleVersion").to(sRule);
	}

	@Step
	public void checkRuleObsoletePayer(String sFlagValue) throws InterruptedException {

		ArrayList<String> sArrPayers = new ArrayList<String>();

		Serenity.setSessionVariable("PayerShots").to(sArrPayers);
		System.out
				.println("Rule Version no in Multipayer" + Serenity.sessionVariableCalled("MidRuleVersion").toString());

		String sQuery = "select PAYERS_4_RULE from IRDM.interp_rule_details where   MID_RULE_DOT_VERSION='"
				+ Serenity.sessionVariableCalled("MidRuleVersion").toString() + "' and INTERP_RULE_KEY in"
				+ "(select interp_rule_key from IRDM.interp_rules where impact_key in "
				+ "(select impact_key from irdm.interp_impacts where update_instance_key ="
				+ "(select update_instance_key from IRDM.update_instances where update_instance_name ='"
				+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "')))";

		System.out.println("Query is : " + sQuery);

		String sPayerList = DBUtils.executeSQLQuery(sQuery);
		System.out.println("Query Status: " + sPayerList);

		String[] ArrPayers = sPayerList.split(",");

		for (int i = 0; i < ArrPayers.length; i++) {
			String sAllPayers = ArrPayers[i].trim().split(" ")[0];
			System.out.println(sAllPayers);

			sArrPayers.add(sAllPayers);
		}

		oHomePage.userNavigateToAdministrationTab();

		oSeleniumUtils
				.Click_given_Locator(StringUtil.replace(oHomePage.AnchorTag, "sValue", "Assignment Configuration"));
		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		if ((oSeleniumUtils.is_WebElement_Present(StringUtil.replace(oHomePage.DivTag, "sValue", "OK")))) {
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.DivTag, "sValue", "OK"));
		}

		oGenericUtils.gfn_Click_On_Object("span", "CPM Payer Assignments");
		// oSeleniumUtils.Click_given_Locator(StringUtil.replace(oHomePage.SpanTag,
		// "sValue", "CPM Payer Assignments"));
		oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.Loading);
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		System.out.println("sPayerQuantity value :" + sFlagValue);

		oHomePage.checkObsoletePayer(sFlagValue, sArrPayers.get(0));

		oGenericUtils.gfn_Click_String_object_Xpath(oHomePage.CPM_Payer_Save);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		if ((oSeleniumUtils.is_WebElement_Present(oHomePage.CPM_Payer_Yes))) {
			oSeleniumUtils.Click_given_Locator(oHomePage.CPM_Payer_Yes);
			oHomePage.waitUntil_Loading_Dailog_Disappears(oHomePage.CPM_Saving_Load);
		}

		oHomePage.closeAllTabs();

	}
	
	@Step
	public void closeAllTabs(){
//		oHomePage.closeAllTabs();
	
		SeleniumUtils.switchWindowUsingWindowCountClose(2, 3, getDriver());
		SeleniumUtils.switchWindowUsingWindowCountClose(1, 2, getDriver());
//		SeleniumUtils.switchWindowUsingWindowCountClose(0, 1, getDriver());

	}
	
	@Step
	public void closeTabs(String arg1){

		if(arg1.equalsIgnoreCase("1")){
			SeleniumUtils.switchWindowUsingWindowCountClose(1, 2, getDriver());
		}
		
		if(arg1.equalsIgnoreCase("2")){
			SeleniumUtils.switchWindowUsingWindowCountClose(2, 3, getDriver());
			SeleniumUtils.switchWindowUsingWindowCountClose(1, 2, getDriver());
		}
		
		if(arg1.equalsIgnoreCase("CLOSETABS")){
			oCommonPage.closeAllOpendTabs2();
		}
		
		if(arg1.equalsIgnoreCase("CLOSETAB2")){
				
			List<WebElement> iCloseTabs = getDriver().findElements(By.xpath("//span[text()='x']"));
	
			for (int i = 1; i <=iCloseTabs.size(); i++) {

				oSeleniumUtils.Click_given_Locator("(//span[text()='x'])["+(i)+"]");
				SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			}
		}
		
	
		
//		SeleniumUtils.switchWindowUsingWindowCountClose(0, 1, getDriver());

	}

}
