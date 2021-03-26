package project.feature.steps.definitions;

import java.util.ArrayList;

import org.junit.Assert;

import cucumber.api.java.en.Then;
import freemarker.template.utility.StringUtil;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import project.pageobjects.AdminPage;
import project.pageobjects.CommonPage;
import project.pageobjects.MyTasksPage;
import project.utilities.GenericUtils;
import project.utilities.ProjectVariables;
import project.utilities.SeleniumUtils;

//trinath..

public class MyTasksStepdef extends ScenarioSteps {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// trinath

	MyTasksPage oMyTasksPage;

	GenericUtils oGenericUtils;

	SeleniumUtils oSeleniumUtils;

	AdminPage oAdminPage;

	CommonPage oCommonPage;

	@Step
	public void clickTaskByInstanceName(String sTaskName, String sInstancetype) throws InterruptedException {

		EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();

		String myCustomProperty = null;

		String sInstance = null;

		Serenity.setSessionVariable("InstanceType").to(sInstancetype);

		boolean blnFlg = false;

		switch (sInstancetype.trim()) {

		case "FINAL PO DEL":
		case "FINAL MD DEL":
			myCustomProperty = variables.getProperty("IU.FinalMDInstance");
			sInstance = myCustomProperty;
			verify("Final MD Del Instance Name: " + sInstance, true);
			break;
		case "FINAL MD DEL-Specific":
			sInstance = ProjectVariables.FinalMDInstanceSpecific;
			verify("Final MD Del Instance Name: " + sInstance, true);
			break;

		case "FINAL MD SIM":
			sInstance = ProjectVariables.FinalMDInstance;
			break;

		case "PRELIM PO DEL":
		case "PRELIM MD DEL":
			myCustomProperty = variables.getProperty("IU.PrilimMDInstance");
			sInstance = myCustomProperty;
			verify("Prelim MD Del Instance Name: " + sInstance, true);
			break;

		case "Admin Scrub":
			myCustomProperty = variables.getProperty("IU.AdminScrubInstance");
			sInstance = myCustomProperty;
			verify("Admin Scrub Instance Name: " + sInstance, true);
			break;
		case "Pool":
			sInstance = Serenity.sessionVariableCalled("Pool").toString();
			break;

		case "AE PRELIM MD DEL":
		case "AE PRELIM PO DEL":
			sInstance = ProjectVariables.AEPrelimInstance;
			break;

		case "LFINAL MD DEL":
			sInstance = ProjectVariables.LfinalMDInstance;
			break;

		case "BWO INSTANCE":
			sInstance = ProjectVariables.BWOInstance;
			break;
		case "Non Candiate Rules-Instance":

			sInstance = ProjectVariables.NonCandiateRulesInstance;

			break;

		default:
			// Assert.assertTrue("Instance Name not matched for function
			// click_Task_By_Instance_Name", false);
			break;
		}
		Serenity.setSessionVariable("InstanceName").to(sInstance);
		String sTaskType = null;

		String strInstance = null;

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		if (sInstancetype.contains("Random")) {

			sTaskType = StringUtil.replace(oMyTasksPage.TaskandInstance, "sTask", sTaskName);

			// Serenity.setSessionVariable("NewInstanceName").to("TestAutoIV895");

			sInstance = Serenity.sessionVariableCalled("NewInstanceName");

			strInstance = StringUtil.replace(sTaskType, "sInstance", sInstance);

			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			oGenericUtils.gfn_Click_On_Object("button", " Refresh");

			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		} else {

			sTaskType = StringUtil.replace(oMyTasksPage.TaskandInstance, "sTask", sTaskName);

			strInstance = StringUtil.replace(sTaskType, "sInstance", sInstance);

		}

		Serenity.setSessionVariable("TaskandInstnaceName").to(strInstance);

		Serenity.setSessionVariable("IUInstanceName").to(sInstance);

		// System.out.println("Instance Name " +
		// Serenity.sessionVariableCalled("IUInstanceName").toString());

		int i = 0;

		while (oSeleniumUtils.is_WebElement_Displayed(strInstance) == false && i != 10) {

			oGenericUtils.gfn_Click_String_object_Xpath("(//button[text()='Next'])[1 or 2]");

			i = i + 1;
		}

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		blnFlg = oGenericUtils.gfn_Click_String_object_Xpath(strInstance);

		verify(sTaskName + " is clicked in MyTask Page" + "Locator used: " + strInstance, blnFlg);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		// SeleniumUtils.defaultWait(3000);

		if (sInstancetype.equalsIgnoreCase("Admin Scrub")) {

			SeleniumUtils.switchWindowUsingWindowCount(3, 3, getDriver());

		}

		/*
		 * if (sTaskName.equalsIgnoreCase("MD Review Work Queue")) {
		 * 
		 * verify(
		 * "Rules count is displaying at the side of pagination in Individual Task"
		 * , oGenericUtils.gfn_Verify_String_Object_Exist(
		 * "(//div[contains(text(),'Displaying 1')])[1]")); }
		 */
	}

	@Step
	public void verify(String sDescription, boolean blnStatus) {

		GenericUtils.Verify(sDescription, blnStatus);
	}

	@Step
	public void validateListofRulesInMDReview(String arg1) {

		ArrayList<String> oList = oMyTasksPage.validateListofRulesInMDReview(arg1);
		if (oList.size() > 0) {
			for (int i = 0; i < oList.size(); i++) {
				verify("Rules are not matched with DB Rules: Those Rules are" + oList.get(i), false);
			}
		} else {
			verify("Rules matched with DB Rules Count ", true);
		}

	}

	@Step
	public void clickTaskOnCompactInstance(String sTaskName, String sInstancetype) throws InterruptedException {

		

		String sInstance = null;

		boolean blnFlg = false;

		switch (sInstancetype.trim()) {

		case "FINAL MD COMPACT":
			sInstance = ProjectVariables.MDCompactInstance;
			break;

		}
		Serenity.setSessionVariable("InstanceName").to(sInstance);
		String sTaskType = null;

		String strInstance = null;

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		if (sInstancetype.contains("Random")) {

			sTaskType = StringUtil.replace(oMyTasksPage.TaskandInstance, "sTask", sTaskName);

			// Serenity.setSessionVariable("NewInstanceName").to("TestAutoIV895");

			sInstance = Serenity.sessionVariableCalled("NewInstanceName");

			strInstance = StringUtil.replace(sTaskType, "sInstance", sInstance);

			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			oGenericUtils.gfn_Click_On_Object("button", " Refresh");

			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		} else {

			sTaskType = StringUtil.replace(oMyTasksPage.TaskandInstance, "sTask", sTaskName);

			strInstance = StringUtil.replace(sTaskType, "sInstance", sInstance);

		}

		Serenity.setSessionVariable("TaskandInstnaceName").to(strInstance);

		Serenity.setSessionVariable("IUInstanceName").to(sInstance);

		// System.out.println("Instance Name " +
		// Serenity.sessionVariableCalled("IUInstanceName").toString());

		int i = 0;

		while (oSeleniumUtils.is_WebElement_Displayed(strInstance) == false && i != 10) {

			oGenericUtils.gfn_Click_String_object_Xpath("(//button[text()='Next'])[1 or 2]");

			i = i + 1;
		}

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		blnFlg = oGenericUtils.gfn_Click_String_object_Xpath(strInstance);

		verify(sTaskName + " is clicked in MyTask Page" + "Locator used: " + strInstance, blnFlg);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		if (sInstancetype.equalsIgnoreCase("Admin Scrub")) {

			SeleniumUtils.switchWindowUsingWindowCount(3, 3, getDriver());

		}

	}

}
