package project.feature.steps.definitions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import project.utilities.DBQueries;

import freemarker.template.utility.StringUtil;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import project.pageobjects.AdminPage;
import project.pageobjects.CommonPage;
import project.pageobjects.MyTasksPage;
import project.pageobjects.WorkQueuePage;
import project.utilities.GenericUtils;
import project.utilities.ProjectVariables;
import project.utilities.SeleniumUtils;
import project.utilities.DBQueries;
import project.utilities.DBUtils;

public class WorkQueueStepDef extends ScenarioSteps {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	GenericUtils oGenericUtils;
	SeleniumUtils oSeleniumUtils;
	AdminPage oAdminPage;
	CommonPage oCommonPage;
	WorkQueuePage oWorkQueuePage;
	CommonStepDef oCommonStepDef;
	MyTasksPage oMyTasksPage;

	// trinath..
	@Step
	public void getAndValidateSystemProposals(String arg1, String sProposalType, String arg3) {

		String[] arr = null;

		switch (sProposalType) {

		case "DEL":

			arr = ProjectVariables.DELProp;

			break;

		case "SIM":

			arr = ProjectVariables.SIMProp;

			break;
		}

		oSeleniumUtils.Enter_given_Text_Element(oWorkQueuePage.ProposalType, sProposalType);

		ArrayList<String> sMidRuleList = new ArrayList<String>();

		List<WebElement> iProposals = getDriver().findElements(By.xpath(
				"(//h5[text()=' System Proposals ']/parent::span/following::div//div//tbody[@class='ui-table-tbody'])[1]//tr[1]/td[2]/select/option"));

		System.out.println("size " + iProposals.size());
		for (int i = 0; i < iProposals.size(); i++) {

			System.out.println("Options are " + iProposals.get(i).getText());

			String pList = arr[i];
			System.out.println("Proposal Types" + pList);

			if (iProposals.get(i).getText().trim().equalsIgnoreCase(arr[i].trim())) {

				GenericUtils.Verify("", true);
			} else {
				GenericUtils.Verify("", false);

			}

		}

	}

	@Step
	public void validate_Rule_Description_with_DB(String arg1) throws InterruptedException {
		boolean bflag = oWorkQueuePage.ValidateData_with_DB(arg1);
		GenericUtils.Verify("Validation of the daetails with DB", bflag);

	}

	@Step
	public void add_another_code_from_add_code_with(String arg1) throws InterruptedException {
		GenericUtils.Verify("Add code from add another code", oWorkQueuePage.fnAddAnotherCode(arg1));
	}

	@Step
	public void copy_Code_from_existing_rule_with(String arg1) throws InterruptedException {
		GenericUtils.Verify("Add code from copy from existing rule", oWorkQueuePage.fnCopyCodefromExisting(arg1));
	}

	@Step
	public void enterMDBulkDecisionAndValidate() throws InterruptedException {

		String arrProp[] = { "SIM", "DEL" };
		String arrDecision[] = { "No Decision", "Remove" };
		String sArrayPropCode = null;

		for (int i = 0; i < arrProp.length; i++) {
			sArrayPropCode = arrProp[i];
			String sArrayDecisionCode = arrDecision[i];
			boolean bFlag = oWorkQueuePage.enterMDBulkDecision(sArrayPropCode, sArrayDecisionCode);
			GenericUtils.Verify("Entered Bulk Decision Properly ", bFlag);

			boolean bValDecisionSel = oWorkQueuePage.validateSysPropBasedBulkDecision(sArrayPropCode);
			GenericUtils.Verify("Decision selection Properly", bValDecisionSel);
		}

	}

	@Step
	public void verify_the_manual_praposal_grid() throws InterruptedException {
		GenericUtils.Verify("Verify the manual praposal grid", oWorkQueuePage.fnAddAnotherCode("VERIFYMANUALGRID"));
	}

	@Step
	public void ValidatetheCodeRange(String sCodeRange) throws InterruptedException {
		String[] Codes = sCodeRange.split("-");
		String[] codeRangeArray = new String[] { "Test-" + Codes[1], Codes[0] + "-Test", Codes[1] + "-" + Codes[0],
				sCodeRange };
		String[] sMessage = new String[] { "The ICD Code span is not valid one.", "ICD To Code is not valid",
				"The ICD Code span is not valid one.", "Successfully saved manual proposals." };
		boolean flag = false;
		if (getDriver().findElements(By.xpath(oWorkQueuePage.chkSelect)).size() > 0) {
			oGenericUtils.gfn_Click_String_object_Xpath(oWorkQueuePage.chkSelect);
			oGenericUtils.gfn_Click_On_Object("button", "Delete Selected");
			oGenericUtils.gfn_Verify_Object_Exist("div", "Are you sure you want to delete selected rows?");
			oGenericUtils.gfn_Click_On_Object("button", "Yes");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
			oGenericUtils.gfn_Click_String_object_Xpath(oWorkQueuePage.btnSave);
			SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
		}

		for (int i = 0; i < codeRangeArray.length; i++) {

			if (i != 0)
				flag = true;
			oWorkQueuePage.fnAddCode(codeRangeArray[i], flag, "Billed Without (And)");
			GenericUtils.Verify("Code Range-" + codeRangeArray[i] + sMessage,
					oWorkQueuePage.fnSaveManualGrid(sMessage[i]));

		}
	}

	public void user_enter_comments(String sReview) throws InterruptedException {

		oGenericUtils.gfn_Click_On_Object("span", "Comments");

		Serenity.setSessionVariable("Comments").to(sReview);

		oSeleniumUtils.Enter_given_Text_Element("//h5[.='" + sReview + " Comments']/../../..//textarea", sReview);

		oGenericUtils.gfn_Click_On_Object("button", "Save");

		oGenericUtils.gfn_Verify_Object_Exist("h4", "Information");

		oGenericUtils.gfn_Verify_Object_Exist("div", "Successfully saved review comments.");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
	}

	@Step
	public void verify_comments_entered_in(String sReview) throws InterruptedException {

		oGenericUtils.gfn_Click_On_Object("span", "Comments");

		String str = StringUtils.substringBefore(sReview, "-");

		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

		WebElement oele = getDriver().findElement(By.xpath("//h5[text()='" + str + " Comments']/..//../..//textarea"));

		boolean bln;

		String sTextEntered = null;

		if (sReview.contains("Empty")) {

			bln = oele.getAttribute("value").equalsIgnoreCase("");

		} else {

			// Serenity.setSessionVariable("Comments").to("Preliminary Review");

			sTextEntered = Serenity.sessionVariableCalled("Comments").toString();

			bln = oele.getAttribute("value").trim().equalsIgnoreCase(sTextEntered.trim());
		}

		GenericUtils.Verify("Comments text is not matched" + "UI text" + oele.getAttribute("value")
				+ "Text we entered comment sections" + sTextEntered, bln);

	}

	@Step
	public void selectSystemProposal(String sProposalType, String sDecision) throws InterruptedException {

		boolean bVal = oWorkQueuePage.selectSystemProposal(sProposalType, sDecision);
		GenericUtils.Verify("System Proposal Selected sucessfully", bVal);

	}

	@Step
	public void completeAuthroizationInMDReview(String arg1) throws InterruptedException {

		oWorkQueuePage.completeAuthroizationInMDReview(arg1);

	}

	@Step
	public void validateRationaleComments(String sReviewType) {

		SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);
		// Serenity.setSessionVariable("MDRationalComments").to("test");
		// String sRationalComments =
		// Serenity.sessionVariableCalled("MDRationalComments").toString();
		List<WebElement> iProposals = getDriver().findElements(By.xpath(
				"(//h5[text()=' System Proposals ']/parent::span/following::div//div//tbody[@class='ui-table-tbody'])[1]//tr"));

		System.out.println("size " + iProposals.size());
		for (int i = 0; i < iProposals.size(); i++) {
			int j = i + 1;
			List<WebElement> iRationalComments = getDriver()
					.findElements(By.xpath(oCommonPage.GetDynamicXPathWithRowCol("MDRATIONALCOMMENTS", j, 3)));
			System.out.println("Options are " + iRationalComments.get(0).getAttribute("value"));

			System.out.println("Proposal Types" + ProjectVariables.SystemProposalComments);

			if (iRationalComments.get(0).getAttribute("value").trim()
					.equalsIgnoreCase(ProjectVariables.SystemProposalComments.trim())) {
				GenericUtils.Verify("Comments are displayed Properly", true);
			} else {
				GenericUtils.Verify("Comments are not displayed Properly", false);
			}
		} // end loop

	}

	@Step
	public void validateActionsColumn(String arg1) throws InterruptedException {

		oGenericUtils.gfn_Click_On_Object("a", arg1);

		HashMap<String, String> omap = new HashMap<>();

		omap.put("BWO_OR", "Billed Without Or");

		omap.put("BW_AND", "Billed With And");

		verify(omap.get(arg1) + " for Rule screen is displayed",
				oSeleniumUtils.is_WebElement_Displayed("//h4[text()='" + omap.get(arg1) + " Records for Rule']"));

		verify(omap.get(arg1) + "Records are displayed", oSeleniumUtils
				.is_WebElement_Displayed("(//table[@class='table table-striped']//tr[@class='ng-star-inserted'])[1]"));

	}

	@Step
	public void validateSysPropBasedBulkDecision(String sProposalType, String sDecision) {

		oSeleniumUtils.Enter_given_Text_Element(oWorkQueuePage.ProposalType, sProposalType);
		SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);
		oCommonPage.GetDynamicXPathWithRowCol("SYSTEMPROPOSAL", 1, 2);
		// String pList =
		// Serenity.sessionVariableCalled("SystemBulkDecision").toString();
		ArrayList<String> sMidRuleList = new ArrayList<String>();

		List<WebElement> iProposals = getDriver().findElements(By.xpath(
				"(//h5[text()=' System Proposals ']/parent::span/following::div//div//tbody[@class='ui-table-tbody'])[1]//tr"));

		System.out.println("size " + iProposals.size());
		for (int i = 0; i < iProposals.size(); i++) {
			int j = i + 1;
			List<WebElement> iSelProposals = getDriver()
					.findElements(By.xpath(oCommonPage.GetDynamicXPathWithRowCol("SYSTEMPROPOSALROWS", j, 2)));
			System.out.println("Options are " + iSelProposals.get(0).getAttribute("value"));

			switch (sDecision) {
			case "Remove":
				sDecision = "2";
				break;
			case "Modify":
				sDecision = "3";
				break;
			case "No Action":
				sDecision = "4";
				break;
			case "No Decision":
				sDecision = "6";
				break;
			}

			System.out.println("Proposal Types" + sDecision);

			if (iSelProposals.get(0).getAttribute("value").trim().equalsIgnoreCase(sDecision.trim())) {

				GenericUtils.Verify("", true);

			} else {
				GenericUtils.Verify("Bulk Decision is selected wrongly ", false);

			}
		} // end loop

	}

	@Step
	public void should_not_viewing_button(String sValue) throws InterruptedException {

		boolean bln;

		if (sValue.equalsIgnoreCase("No Decision-Enabled")) {

			bln = !oSeleniumUtils.is_WebElement_Enabled("//button[text()='No Decision']");

			verify("No Decisions button is enabled", bln);

		} else {

			bln = !oGenericUtils.gfn_Verify_Object_Exist("button", sValue);

			verify(sValue + "is not displayed", bln);
		}

	}

	@Step
	public void setNoChangeRequiredInMDReview(String sOperation, String sValue) throws InterruptedException {

		oWorkQueuePage.setNoChangeRequiredInMDReview(sOperation, sValue);

	}

	@Step
	public void verify(String sDescription, boolean blnStatus) {

		GenericUtils.Verify(sDescription, blnStatus);
	}

	public void user_update_the_bulk_Decisions() throws InterruptedException {
		if (getDriver().findElements(By.xpath("//button[text()='Start Review'][not(@disabled)]")).size() > 0) {
			oGenericUtils.gfn_Click_String_object_Xpath("//button[text()='Start Review'][not(@disabled)]");
			oGenericUtils.gfn_Click_On_Object("button", "Yes");
		}
		if (getDriver().findElements(By.xpath("//button[text()='Modify Decisions'][not(@disabled)]")).size() > 0) {
			oGenericUtils.gfn_Click_String_object_Xpath("//button[text()='Modify Decisions'][not(@disabled)]");
		}

		oWorkQueuePage.fnAddCode("D55.0", false, "Billed Without (And)");
		oGenericUtils.CLICK_LINK_XPATH(oWorkQueuePage.btnSave);
		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		SeleniumUtils.scrollingToGivenElement(getDriver(), oWorkQueuePage.BulkDecisionBtn);
		oGenericUtils.gfn_Click_String_object_Xpath(oWorkQueuePage.BulkDecisionBtn);
		oGenericUtils.gfn_Click_String_object_Xpath(oWorkQueuePage.BulkDecisionText);
		List<WebElement> options = getDriver()
				.findElements(By.xpath("//select[@class='ng-pristine ng-valid ng-touched']/option"));
		String arrDecision[] = new String[options.size() - 1];
		for (int i = 1; i < options.size(); i++) {
			arrDecision[i - 1] = options.get(i).getText();
			String text = options.get(i).getText();
			System.out.println(text);
		}
		oSeleniumUtils.Click_given_Locator(oWorkQueuePage.CloseBulkDecision);
		for (int i = 0; i < arrDecision.length; i++) {

			boolean bFlag = oWorkQueuePage.enterMDBulkDecision(arrDecision[i], "No Action");
			GenericUtils.Verify("Entered Bulk Decision Properly ", bFlag);
		}
		GenericUtils.Verify("updated manual praposals", oWorkQueuePage.fnUpdateManualPraposals());
	}

	@Step
	public void clicks_on_action_link(String arg) throws InterruptedException {

		SeleniumUtils.defaultWait(7000);

		String sRevCode = oSeleniumUtils
				.get_StringTextFrom_Locator("//a[text()='" + arg + "']/../..//following-sibling::td[2]");

		Serenity.setSessionVariable("" + arg + " Code").to(sRevCode);

		oGenericUtils.gfn_Click_On_Object("a", arg);

		SeleniumUtils.defaultWait(7000);

		if (arg.equalsIgnoreCase("REV")) {

			oGenericUtils.gfn_Verify_Object_Exist("h4", "Revised Code Description - Code: " + sRevCode + "");

		} else if (arg.equalsIgnoreCase("INFO")) {

			SeleniumUtils.defaultWait(7000);

			oGenericUtils.gfn_Verify_Object_Exist("h6", "Code: " + sRevCode.trim() + "");

		} else {

			oGenericUtils.gfn_Verify_Object_Exist("h4", "  Code Exists - Code " + sRevCode.trim() + "");

			oGenericUtils.gfn_Verify_Object_Exist("label",
					" Select record(s) where the code should be removed, enter Rationale Comment, then click on \'Generate Remove Code Proposals\' to create manual proposals to remove the code.");

			oGenericUtils.gfn_Click_String_object_Xpath("//div[text()='Rationale Comment: ']/..//textarea");

			oGenericUtils.gfn_Verify_String_Object_Exist("//span[text()=' " + sRevCode.trim()
					+ " ']/../p-treetabletoggler/following-sibling::p-treetablecheckbox");

		}

	}

	@Step
	public void should_be_viewing_textbox_with_Description(String sDescriptionType, String sDescriptionTypeQuery)
			throws InterruptedException {

		String sInstance = Serenity.sessionVariableCalled("IUInstanceName").toString();

		String sRevCode = Serenity.sessionVariableCalled("REV Code").toString();

		String sUIDescp = null;

		String sDBDescp = null;

		if (sDescriptionType.equalsIgnoreCase("Previous code Description")) {

			oGenericUtils.gfn_Verify_Object_Exist("b", "Previous Code Description");

			sUIDescp = oSeleniumUtils.get_TextFrom_Locator("//b[text()='Previous Code Description']/..//div");

			sDBDescp = DBUtils.executeSQLQuery(
					"select OLD_DESCRIPTION from irdm.icd_updates where update_instance_key in (select update_instance_key from IRDM.UPDATE_INSTANCES where update_instance_name = '"
							+ sInstance + "') and icd_code = '" + sRevCode + "'");

		} else {

			oGenericUtils.gfn_Verify_Object_Exist("b", "Current Code Description");

			sUIDescp = oSeleniumUtils.get_TextFrom_Locator("//b[text()='Current Code Description']/..//div");

			sDBDescp = DBUtils.executeSQLQuery(
					"select DESCRIPTION from irdm.icd_updates where update_instance_key in (select update_instance_key from IRDM.UPDATE_INSTANCES where update_instance_name = '"
							+ sInstance + "') and icd_code = '" + sRevCode + "'");

		}

		verify("" + sDescriptionType + " is matched with DB Description" + "UI" + sUIDescp + "DB" + sDBDescp,
				sUIDescp.equalsIgnoreCase(sDBDescp));

		oGenericUtils.gfn_Verify_Object_Exist("b", "Description Diff:");

		oGenericUtils.gfn_Verify_String_Object_Exist("//div[@class='descriptionDiff']//del");

	}

	@Step
	public void click_on_in_WorkQueue(String arg1) throws InterruptedException {

		oWorkQueuePage.click_on_in_WorkQueue(arg1);

	}

	@Step
	public void ruleEditorialUpdateService() throws IOException {

		String sSubRuleKey = DBUtils.executeSQLQuery(oCommonPage.getDynamicQuery("UPDATERULE2",
				Serenity.sessionVariableCalled("IUInstanceName").toString(), "Preliminary PO Review"));
		System.out.println("SubRuleKey: " + sSubRuleKey);
		String sRequestedPRMIDXML = oWorkQueuePage.createRequestXML("CREATERMRSTUBIUPD", "HSHZ-051412024417",
				sSubRuleKey);
		String sPRMIDResponse = GenericUtils.sendPostRequest(ProjectVariables.sLotusRMRURL, sRequestedPRMIDXML);
		String sPRMID = GenericUtils.getTextFromTwoStrings(sPRMIDResponse, "<REQID>", "</REQID>");
		System.out.println("Text: " + sPRMID);

		String sRequestedEDITORIALXML = oWorkQueuePage.createRequestXML("EDITORIALUPDATERMRIUPD", sPRMID, "");
		String sSuccessResponse = GenericUtils.sendPostRequest(ProjectVariables.sLotusRMRURL, sRequestedEDITORIALXML);
		String sSuccessMsg = GenericUtils.getTextFromTwoStrings(sSuccessResponse, "<SUCCESSFLAG>", "</SUCCESSFLAG>");
		System.out.println("Msg: " + sSuccessMsg);

		String sMidRuleVersion = DBUtils.executeSQLQuery("SELECT IRD.MID_RULE_DOT_VERSION\r\n" + "     \r\n"
				+ "  FROM IRDM.INTERP_RULES IR\r\n" + "       JOIN IRDM.INTERP_RULE_DETAILS IRD\r\n"
				+ "          ON IR.INTERP_RULE_KEY = IRD.INTERP_RULE_KEY\r\n"
				+ "       JOIN IPDE.TASK_DETAILS TD ON TD.REFERENCE_RULE_ID = IR.INTERP_RULE_KEY\r\n"
				+ "       JOIN IPDE.TASK_TYPE_LKP TTL ON TTL.TASK_TYPE_KEY = TD.TASK_TYPE_KEY\r\n"
				+ "       JOIN IPDE.TASK_STATUS_LKP TSL\r\n"
				+ "          ON TSL.TASK_STATUS_KEY = TD.TASK_STATUS_KEY\r\n"
				+ "WHERE     IR.IMPACT_KEY IN (SELECT IMPACT_KEY\r\n"
				+ "                               FROM IRDM.INTERP_IMPACTS II\r\n"
				+ "                                   JOIN IRDM.UPDATE_INSTANCES I\r\n"
				+ "                                       ON I.UPDATE_INSTANCE_KEY =\r\n"
				+ "                                             II.UPDATE_INSTANCE_KEY\r\n"
				+ "                              WHERE UPDATE_INSTANCE_NAME = '"
				+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "')\r\n"
				+ "       AND IR.SUB_RULE_KEY = '" + sSubRuleKey + "'");
		System.out.println("MiddleRule Version: " + sMidRuleVersion);

		Serenity.setSessionVariable("MidRuleVersion").to(sMidRuleVersion);

	}

	@Step
	public void clickStartRevieandValidatePotentialConflict() throws InterruptedException {

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		oGenericUtils.gfn_Click_On_Object("button", "Start Review");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		// oGenericUtils.gfn_Verify_Object_Exist("h4", "Confirm");

		int i = getDriver().findElements(By.xpath("//h4[text()='Potential Conflict Found']")).size();

		if (i > 0) {
			verify("Potential Conflict window is displayed", true);
			oGenericUtils.gfn_Verify_Object_Exist(
					"//div[contains(text(),'Potential Conflict is identified on the rule and the rule will be assigned to Editorial User for review. Click')]",
					0);
			verify("Displayed Messege:: "
					+ "Potential Conflict is identified on the rule and the rule will be assigned to Editorial User for review. Click...",
					true);
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		} else {
			verify("Rule does not have Potential Conflict", false);
		}
	}

	public void adds_a_code_with_Category_without_Override_flag(String arg1) throws InterruptedException {

		verify("Billed With (And);Billed Without (Or) Pop validation",
				oWorkQueuePage.adds_a_code_with_Category_without_Override_flag(arg1));

	}

	public void click_on_button_on_WorkQueue(String arg1) throws InterruptedException {
		oWorkQueuePage.click_on_button_on_WorkQueue(arg1);

	}

	public void retrive_code_from_System_Proposals(String sProposalType) throws InterruptedException {
		oWorkQueuePage.retrive_code_from_System_Proposals(sProposalType);

	}

	@Step
	public void user_should_be_able_to_enter_the_code_that_they_want_to_remove_in_the_pop_up()
			throws InterruptedException {

		verify("Validations in Remove Popup",
				oWorkQueuePage.user_should_be_able_to_enter_the_code_that_they_want_to_remove_in_the_pop_up());

	}

	@Step
	public void the_ICD_ARD_link_appears_beside_the_Potential_Conflict_button_when_the_rule_is_defined_with_an_ARD()
			throws InterruptedException {
		oWorkQueuePage
				.the_ICD_ARD_link_appears_beside_the_Potential_Conflict_button_when_the_rule_is_defined_with_an_ARD();

	}

	@Step
	public void user_can_click_on_the_link_and_review_the_ARD_setup_for_the_rule() throws Exception {
		oWorkQueuePage.user_can_click_on_the_link_and_review_the_ARD_setup_for_the_rule();

	}

	@Step
	public void clickFromGroupTasks(String arg1) throws InterruptedException {

		oGenericUtils.gfn_Click_On_Object("span", "Interpretive Rule Update Tasks");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		if (arg1.equalsIgnoreCase(arg1)) {
			oGenericUtils.gfn_Click_On_Object("span", "Group Tasks");
			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
			oGenericUtils.gfn_Click_String_object_Xpath(
					"//span[text()='" + Serenity.sessionVariableCalled("IUInstanceName").toString()
							+ "']/../../..//a[text()='" + arg1 + "']");
			verify("Rule is moved to " + arg1 + " Pool", true);
			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		} else {
			verify("Rule is not moved to " + arg1 + " Pool", false);
		}
	}

	public void selectRuleAndClaimTask() throws InterruptedException {

		// Serenity.setSessionVariable("MidRuleVersion").to("10974.11");

		if (!(oSeleniumUtils.is_WebElement_Displayed(StringUtils.replace(oCommonPage.AnchorTag, "sValue",
				Serenity.sessionVariableCalled("MidRuleVersion").toString())))) {
			oCommonPage.go_To_Rule(Serenity.sessionVariableCalled("MidRuleVersion").toString());
			verify("MidRule Version is Displayed :" + Serenity.sessionVariableCalled("MidRuleVersion").toString(),
					true);
		}
		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
		// click on check box of the Rule
		System.out.println("//span[text()='" + Serenity.sessionVariableCalled("MidRuleVersion").toString()
				+ "']/parent::td/../td//p-tablecheckbox//div//div[2]/span");
		oSeleniumUtils
				.Click_given_Locator("//span[text()='" + Serenity.sessionVariableCalled("MidRuleVersion").toString()
						+ "']/parent::td/../td//p-tablecheckbox//div//div[2]");
		// oGenericUtils.gfn_Click_String_object_Xpath("//a[text()='"+Serenity.sessionVariableCalled("MidRuleVersion").toString()+"']/parent::td/../td//p-tablecheckbox//div//div[2]");
		oGenericUtils.gfn_Click_String_object_Xpath(oWorkQueuePage.ClaimTask);
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		oGenericUtils.gfn_Click_On_Object("span", "Interpretive Rule Update Tasks");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		oGenericUtils.gfn_Click_String_object_Xpath(oWorkQueuePage.MyTaskICD);
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

	}

	@Step
	public void completeConflictReview(String arg1) throws InterruptedException {

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		oGenericUtils.gfn_Click_On_Object("button", "Start Review");
		int i = getDriver()
				.findElements(By.xpath("//div[text()='Please confirm you wish to start Potential Conflicts Review.']"))
				.size();

		if (i > 0) {
			verify("Please confirm you wish to start Potential Conflicts Review.' is displayed", true);
			oGenericUtils.gfn_Click_On_Object("button", "Yes");
		} else {
			verify("Not Displayed Popup window", false);
		}
		SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);

		oGenericUtils.gfn_Click_String_object_Xpath(oWorkQueuePage.ConflictNotes);
		oGenericUtils.gfn_Click_String_object_Xpath(oWorkQueuePage.ConflictNotes);
		oSeleniumUtils.Doubleclick(oWorkQueuePage.ConflictNotes);
		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
		oSeleniumUtils.Enter_given_Text_Element("//div[@class='ag-large-textarea']/textarea", "PO Comments");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);
		oGenericUtils.gfn_Click_String_object_Xpath("(//input[@id='" + arg1 + "'])[1]");
		oGenericUtils.gfn_Click_On_Object("button", "Save");

		oGenericUtils.gfn_Verify_Object_Exist("div", "Successfully saved potential conflicts.");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		oGenericUtils.gfn_Click_On_Object("button", "Conflict Review Completed");

		oGenericUtils.gfn_Verify_Object_Exist("div",
				"Successfully completed conflict review. The rule moved to previous review process successfully.");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		oGenericUtils.gfn_Click_On_Object("span", "Interpretive Rule Update Tasks");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

	}

	public void validateRetireRuleSection(String sRetireType, String arg2) throws InterruptedException {
		if (!(sRetireType.equalsIgnoreCase("NORetireRule"))) {

			String sReqQuery = "";
			String sColumnName = null;
			oGenericUtils.gfn_Click_On_Object("span", "Summaries");

			if (sRetireType.equalsIgnoreCase("MDDeterminRetire")) {
				SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
				verify("Retire button is displayed and Click on Retire button",
						oGenericUtils.gfn_Click_On_Object("button", " Retire Rule "));
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			}

			verify("Retire Link is displayed and Click on Retire Rule Link"
					+ oGenericUtils.gfn_Click_String_object_Xpath("//h5[contains(text(),'Retire Rule ')]"), true);
			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			if (sRetireType.equalsIgnoreCase("SystemDeterminRetire")) {
				verify("Displayed Retire Rule Sumaary - System Determined" + oSeleniumUtils
						.is_WebElement_Displayed("//label[text()='Retire Rule Summary - PO Determined']"), true);
				verify("Displayed Retire Rule Sumaary - System Determined"
						+ oSeleniumUtils.is_WebElement_Displayed(oWorkQueuePage.RetireRuleMDDecision), true);
				// select Do Not Retire Rule Option
				oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(oWorkQueuePage.RetireRuleMDDecision,
						"Do Not Retire Rule");
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				oSeleniumUtils.Enter_given_Text_Element(oWorkQueuePage.RetireRuleMDComments,
						ProjectVariables.TestComments);
				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
				verify("MD Comments field should be displayed and editable", true);
				oGenericUtils.gfn_Verify_Object_Exist("button", "Save");
				verify("Save button is displayed ", true);
				int i = getDriver()
						.findElements(By.xpath("//input[@ng-reflect-value='0']/../../../../../div[@hidden='']")).size();
				if (i > 0) {
					verify("Create new version Radio button is not displayed ", true);
				} else {
					verify(" displayed Create new version Radio button is not displayed ", false);
				}
				// select Retire Rule Option
				oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(oWorkQueuePage.RetireRuleMDDecision,
						"Retire Rule");
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			} else {
				verify("MD Decision list box should not be displayed",
						(!oSeleniumUtils.is_WebElement_Displayed(oWorkQueuePage.RetireRuleMDDecision)));
				verify("Displayed Retire Rule Sumaary - System Determined" + oSeleniumUtils
						.is_WebElement_Displayed("//label[text()='Retire Rule Summary - PO Determined']"), true);
			}

			// Create New Version is No
			oSeleniumUtils.Click_given_Locator("//input[@ng-reflect-value='0']");
			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
			int i = getDriver().findElements(By.xpath("//div[@hidden='']//label[text()='New Rule Version Data:']"))
					.size();
			if (i > 0) {
				verify("Not displayed New Rule Version Data Section ", true);
			} else {
				verify("Not displayed New Rule Version Data Section ", false);
			}

			int j = getDriver().findElements(By.xpath("//div[@hidden='']//div//div[text()='New MIN Age Filter:']"))
					.size();
			if (j > 0) {
				verify("Not displayed Age filter Section ", true);
			} else {
				verify("It displayed Age filter Section ", false);
			}

			// Create New Version is Yes
			oSeleniumUtils.Click_given_Locator("//input[@ng-reflect-value='-1']");
			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
			oSeleniumUtils.Enter_given_Text_Element(oWorkQueuePage.RetireRuleMDComments, ProjectVariables.TestComments);
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			verify("MD Comments field should be displayed and editable", true);
			oGenericUtils.gfn_Verify_Object_Exist("button", "Save");
			verify("Save button is displayed ", true);
			oGenericUtils.gfn_Verify_Object_Exist("//input[@ng-reflect-value='0']", 0);
			verify("Create new version Radio button is displayed ", true);
			oGenericUtils.gfn_Verify_Object_Exist("//label[text()='Review Rule Version Data:']", 0);
			verify("Review Rule Version Data is displayed", true);
			// String sText1 =
			// oSeleniumUtils.get_TextFrom_Locator("(//th[contains(text(),'Current
			// DOS
			// From')]/parent::tr/parent::thead/following::tbody//tr)[1]//td[1]");

			String sQuery = " select sColumnName from rules.sub_rules where sub_rule_key = ( SELECT IR.SUB_RULE_KEY\r\n"
					+ "  FROM IRDM.INTERP_RULES IR\r\n" + "       JOIN IRDM.INTERP_RULE_DETAILS IRD\r\n"
					+ "          ON IR.INTERP_RULE_KEY = IRD.INTERP_RULE_KEY\r\n"
					+ "       JOIN IPDE.TASK_DETAILS TD ON TD.REFERENCE_RULE_ID = IR.INTERP_RULE_KEY\r\n"
					+ "       JOIN IPDE.TASK_TYPE_LKP TTL ON TTL.TASK_TYPE_KEY = TD.TASK_TYPE_KEY\r\n"
					+ "       JOIN IPDE.TASK_STATUS_LKP TSL\r\n"
					+ "          ON TSL.TASK_STATUS_KEY = TD.TASK_STATUS_KEY\r\n"
					+ "WHERE     IR.IMPACT_KEY IN (SELECT IMPACT_KEY\r\n"
					+ "                               FROM IRDM.INTERP_IMPACTS II JOIN IRDM.UPDATE_INSTANCES I ON I.UPDATE_INSTANCE_KEY = II.UPDATE_INSTANCE_KEY WHERE UPDATE_INSTANCE_NAME = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "') \r\n"
					+ "       AND IRD.MID_RULE_DOT_VERSION = '"
					+ Serenity.sessionVariableCalled("MidRuleVersion").toString() + "')\r\n";
			String sInstanceDateQuery = "select Effective_date from IRDM.UPDATE_INSTANCES where update_instance_name = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "'";
			System.out.println(sQuery);

			String sCurrentDosFromValue = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("Current DOS From", 1));
			String sCurrentDosToValue = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("Current DOS To", 2));
			String sUpdatedDosFromValue = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("Updated DOS From", 3));
			String sUpdatedDosToValue = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("Updated DOS To", 4));
			String sSourcesDosFromValue = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("Source DOS From", 1));
			String sSourcesDosToValue = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("Source DOS To", 2));
			String sNewDosFromValue = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("New DOS From", 3));
			String sNewDosToValue = oSeleniumUtils.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("New DOS To", 4));
			System.out.println(sCurrentDosFromValue);

			sReqQuery = StringUtils.replace(sQuery, "sColumnName", "DOS_FROM");
			System.out.println("Query: " + sReqQuery);
			String sDBCurrentDosFromValue = DBUtils.executeSQLQuery(sReqQuery);
			System.out.println("DB Value " + sDBCurrentDosFromValue);
			boolean bCurrentDosFromStatus = GenericUtils.getDate_Format(sDBCurrentDosFromValue)
					.equalsIgnoreCase(GenericUtils.Get_Required_Date_For_Given_String(sCurrentDosFromValue));
			verify("Current DOS From is dispalyed properly " + "DBValue:: " + sDBCurrentDosFromValue,
					bCurrentDosFromStatus);

			sReqQuery = StringUtils.replace(sQuery, "sColumnName", "DOS_TO");
			System.out.println("Query: " + sReqQuery);
			String sDBCurrentDosToValue = DBUtils.executeSQLQuery(sReqQuery);
			boolean bCurrentDosToStatus = GenericUtils.getDate_Format(sDBCurrentDosToValue)
					.equalsIgnoreCase(GenericUtils.Get_Required_Date_For_Given_String(sCurrentDosToValue));
			verify("Current DOS From is dispalyed properly " + "DBValue:: " + sDBCurrentDosFromValue,
					bCurrentDosToStatus);
			// updated dos from
			boolean bUpdatedDosFromStatus = GenericUtils.getDate_Format(sDBCurrentDosFromValue)
					.equalsIgnoreCase(GenericUtils.Get_Required_Date_For_Given_String(sUpdatedDosFromValue));
			verify("Current DOS From is dispalyed properly " + "DBValue:: " + sUpdatedDosFromValue,
					bUpdatedDosFromStatus);

			// updated dos To
			String sDBInstanceDate = DBUtils.executeSQLQuery(sInstanceDateQuery);
			String sConvertDBInstanceDate = GenericUtils.getDate_Format(sDBInstanceDate);
			String sUpdatedDBDosToVal = GenericUtils.dateCalculate(sConvertDBInstanceDate, "MM/dd/yyyy", 1);
			boolean bUpdatedDosToStatus = sUpdatedDBDosToVal.trim().equalsIgnoreCase(sUpdatedDosToValue.trim());
			verify("Current DOS To is dispalyed properly " + "DBValue:: " + sUpdatedDBDosToVal + " UI Value:: "
					+ sUpdatedDosToValue, bUpdatedDosToStatus);

			// Source Dos from
			boolean bSourceDosFromStatus = GenericUtils.getDate_Format(sDBCurrentDosFromValue)
					.equalsIgnoreCase(GenericUtils.Get_Required_Date_For_Given_String(sSourcesDosFromValue));
			verify("Current DOS From is dispalyed properly " + "DBValue:: " + sSourcesDosFromValue,
					bSourceDosFromStatus);

			// Source Dos To
			boolean bSourceDosToStatus = GenericUtils.getDate_Format(sDBCurrentDosToValue)
					.equalsIgnoreCase(GenericUtils.Get_Required_Date_For_Given_String(sSourcesDosToValue));
			verify("Current DOS To is dispalyed properly " + "DBValue:: " + sDBCurrentDosToValue + " UI Value::"
					+ sSourcesDosToValue, bSourceDosToStatus);

			// New Dos from
			boolean bNewDosFromStatus = GenericUtils.getDate_Format(sDBInstanceDate)
					.equalsIgnoreCase(GenericUtils.Get_Required_Date_For_Given_String(sNewDosFromValue));
			verify("Current DOS From is dispalyed properly " + sNewDosFromValue, bNewDosFromStatus);

			// New Dos To
			boolean bNewDosFToStatus = GenericUtils.getDate_Format(sDBCurrentDosToValue)
					.equalsIgnoreCase(GenericUtils.Get_Required_Date_For_Given_String(sNewDosToValue));
			verify("Current DOS From is dispalyed properly " + sNewDosFromValue, bNewDosFToStatus);

			verify("Current Max Age Filter ListBox is displayed" + oSeleniumUtils.is_WebElement_Displayed(
					StringUtils.replace(oWorkQueuePage.CurrentAgeFilter, "sValue", "Current MAX Age Filter:")), true);
			verify("Current Min Age Filter ListBox is displayed" + oSeleniumUtils.is_WebElement_Displayed(
					StringUtils.replace(oWorkQueuePage.CurrentAgeFilter, "sValue", "Current MIN Age Filter:")), true);
			verify("Current New MIN Age Filter: ListBox is displayed" + oSeleniumUtils.is_WebElement_Displayed(
					StringUtils.replace(oWorkQueuePage.NewAgeFilter, "sValue", "New MAX Age Filter:")), true);
			verify("Current New MIN Age Filter: ListBox is displayed" + oSeleniumUtils.is_WebElement_Displayed(
					StringUtils.replace(oWorkQueuePage.NewAgeFilter, "sValue", "New MIN Age Filter:")), true);

			oSeleniumUtils.Enter_given_Text_Element(oCommonPage.GetDynamicXPath("Updated DOS To", 4),
					ProjectVariables.UpdatedDOSTo);

			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
					StringUtils.replace(oWorkQueuePage.NewAgeFilter, "sValue", "New MIN Age Filter:"),
					"1 - Minimum Age Filter Override");
			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
					StringUtils.replace(oWorkQueuePage.NewMAXAgeFilter, "sValue", "New MAX Age Filter:"),
					"6 - Less than or equal to 28 Days");

			String sNewUpdatedDosToValue = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("Updated DOS To", 4));
			String sChangedDosFromValue = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("New DOS From", 3));

			oGenericUtils.gfn_Click_On_Object("button", "Save");
			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			// verify update Date of Service TO

			// sNewDosFromValue =
			// oSeleniumUtils.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("New
			// DOS From", 3));

			String sNewUpdatedDosToValueLess = GenericUtils.dateCalculate(sChangedDosFromValue, "MM/dd/yyyy", 1);
			boolean bStatus = sNewUpdatedDosToValueLess.trim().equalsIgnoreCase(sNewUpdatedDosToValue.trim());
			verify("New Dos from value after changing Updated DoS To " + "ExpectedValue:: " + sChangedDosFromValue,
					bStatus);
		} else {
			verify("Retire Link is should not be displayed",
					(!oSeleniumUtils.is_WebElement_Displayed(("//h5[contains(text(),'Retire Rule ')]"))));
			oGenericUtils.gfn_Click_On_Object("span", "Decisions");
			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		} // end if loop

	}

	public void validate_the_Editorial_details_in(String arg1) throws InterruptedException {
		GenericUtils.Verify("Valiadtion of " + arg1, oWorkQueuePage.fnValidateEditorial(arg1));
	}

	@Step
	public void modify_Editorial_comments_with_in(String sAction, String sReviewType) throws InterruptedException {
		oWorkQueuePage.fnModifyEditorialComments(sAction, sReviewType);
	}

	@Step
	public void Vaslidate_Decision_Summary() throws InterruptedException {
		oGenericUtils.CLICK_LINK_XPATH(oWorkQueuePage.decisionSummaryTab);
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		oGenericUtils.CLICK_LINK_XPATH(oWorkQueuePage.sysSummaryTab);
		oGenericUtils.gfn_Verify_String_Object_Exist(oWorkQueuePage.sysDecision);
		oGenericUtils.gfn_Verify_String_Object_Exist(oWorkQueuePage.sysComments);
		oGenericUtils.gfn_Verify_String_Object_Exist(oWorkQueuePage.sysPraposal);
		oGenericUtils.CLICK_LINK_XPATH(oWorkQueuePage.ManualSummaryTab);
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
	}

	@Step
	public void validateDBTaskTypeAndStatus(String sColumnValues) throws InterruptedException {

		List<String> sColumnValueList = Arrays.asList(sColumnValues.split(";"));
		if (sColumnValueList.get(0).length() > 0) {
			String sTaskType = sColumnValueList.get(0);
			String sTaskTypeDBValue = DBUtils.executeSQLQuery(DBQueries.sQueryTaskTypeAndStatus("TTL.TASK_TYPE_DESC",
					Serenity.sessionVariableCalled("IUInstanceName").toString(),
					Serenity.sessionVariableCalled("MidRuleVersion").toString()));
			verify(Serenity.sessionVariableCalled("MidRuleVersion").toString() + "  Expected Task is : " + sTaskType
					+ " Acutal Task is : " + sTaskTypeDBValue, sTaskTypeDBValue.trim().equalsIgnoreCase(sTaskType));
		}

		if (sColumnValueList.get(1).length() > 0) {
			String sTaskStatus = sColumnValueList.get(1);
			String sTaskStatusDBValue = DBUtils.executeSQLQuery(DBQueries.sQueryTaskTypeAndStatus(
					"TSL.TASK_STATUS_DESC", Serenity.sessionVariableCalled("IUInstanceName").toString(),
					Serenity.sessionVariableCalled("MidRuleVersion").toString()));
			verify(Serenity.sessionVariableCalled("MidRuleVersion").toString() + "  Expected TaskStatus is : "
					+ sTaskStatus + " Acutal TaskStatus is : " + sTaskStatusDBValue,
					sTaskStatusDBValue.trim().equalsIgnoreCase(sTaskStatus));
		}

		if (sColumnValueList.get(2).length() > 0) {
			String sUser = sColumnValueList.get(2);
			String sTaskUserDBValue = DBUtils.executeSQLQuery(DBQueries.sQueryTaskTypeAndStatus("U.USER_ID",
					Serenity.sessionVariableCalled("IUInstanceName").toString(),
					Serenity.sessionVariableCalled("MidRuleVersion").toString()));
			verify(Serenity.sessionVariableCalled("MidRuleVersion").toString() + "  Expected User is : " + sUser
					+ " Acutal User is : " + sTaskUserDBValue, sTaskUserDBValue.trim().equalsIgnoreCase(sUser));
		}

		oGenericUtils.gfn_Click_On_Object("span", "Interpretive Rule Update Tasks");

		int i = getDriver().findElements(By.xpath("//div[@style='background-color: rgb(0, 100, 0);']")).size();

		if (i > 0) {
			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		}

	}

	@Step
	public void returnRule(String sFrom, String sTo) throws InterruptedException {

		oWorkQueuePage.editorialWindow();

		oSeleniumUtils.Click_given_Locator(StringUtils.replace(oCommonPage.ButtonTag, "sValue", "Return Rule"));
		SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);

		// oGenericUtils.gfn_Verify_Object_Exist(oWorkQueuePage.ReturnRuleInDialog,
		// "5");

		if (sFrom.trim().equalsIgnoreCase("Config")) {
			sFrom = "Configuration";
		}

		if (sTo.trim().equalsIgnoreCase("Config")) {
			sTo = "Configuration";
		}
		oGenericUtils.gfn_Click_String_object_Xpath("//label[text()='" + sFrom
				+ " Return To:']//parent::div//div[@class='mat-select-trigger']//div[@class='mat-select-arrow-wrapper']//div[@class='mat-select-arrow']");
		// oSeleniumUtils.Click_given_Locator("//label[text()='"+sFrom+" Return
		// To:']//parent::div//div[@class='mat-select-trigger']//div[@class='mat-select-arrow-wrapper']//div[@class='mat-select-arrow']");

		// verify("MD option is
		// displayed",oSeleniumUtils.is_WebElement_Displayed("//span[text()='"+sTo+"']"));
		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
		// oSeleniumUtils.Click_given_Locator("//span[text()='"+sTo+"']");

		oSeleniumUtils.Click_given_Locator("//mat-option[contains(@class,'mat')]//span[text()='" + sTo + "']");

		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
		oSeleniumUtils.Enter_given_Text_Element(oWorkQueuePage.ReturnRuleCommnets, "Return to " + sTo);
		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		// oSeleniumUtils.Click_given_Locator(oWorkQueuePage.ReturnRuleCommnets);

		// oSeleniumUtils.Click_given_Locator("(//button[text()='Cancel'])[2]");
		//
		// oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		// oGenericUtils.gfn_Verify_Object_Exist(oWorkQueuePage.ReturnRuleInDialog,
		// "5");

		// oSeleniumUtils.Click_given_Locator("//label[text()='"+sFrom+" Return
		// To:']//parent::div//div[@class='mat-select-trigger']//div[@class='mat-select-arrow-wrapper']//div[@class='mat-select-arrow']");

		// verify("MD option is
		// displayed",oSeleniumUtils.is_WebElement_Displayed("//mat-option[@class='mat-option
		// ng-star-inserted mat-selected mat-active']//span[text()='MD']"));

		// oSeleniumUtils.Click_given_Locator("//mat-option[contains(@class,'mat-option
		// ng-star-inse')]//span[text()='"+sTo+"']");

		// oSeleniumUtils.Enter_given_Text_Element(oWorkQueuePage.ReturnRuleCommnets,
		// "Return to "+sTo);

		// SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		oSeleniumUtils.Click_given_Locator(oWorkQueuePage.ReturnRuleCommnets);

		oSeleniumUtils.Click_given_Locator(oWorkQueuePage.ReturnRuleInDialog);

		// verify("Dispalyed ",
		// oSeleniumUtils.is_WebElement_Displayed(StringUtils.replace(oCommonPage.DivTag,
		// "sValue", "Rule is being returned to MD for clarification of
		// Editorial concern. Continue?")));
		oGenericUtils.gfn_Click_String_object_Xpath(StringUtils.replace(oCommonPage.ButtonTag, "sValue", "Yes"));
		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

		// boolean sReturnFlag
		// =oGenericUtils.gfn_Click_String_object_Xpath(StringUtils.replace(oCommonPage.DivTag,
		// "sValue", "Rule Returned successfully."));
		// verify("Rule is Returned " +sTo+ "Sucessfully " , sReturnFlag);

		verify("Rule is Returned sucessfully ", true);
		oSeleniumUtils.Click_given_Locator(StringUtils.replace(oCommonPage.ButtonTag, "sValue", "Ok"));

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		oGenericUtils.gfn_Click_On_Object("span", "Interpretive Rule Update Tasks");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

	}

	@Step
	public void returnRuleResponse(String arg1) throws InterruptedException {

		oSeleniumUtils.Click_given_Locator(StringUtil.replace(oCommonPage.ButtonTag, "sValue", "Return Rule Response"));
		oSeleniumUtils.Enter_given_Text_Element("//textarea[@id='comments']", "Rule Response to " + arg1);
		if (arg1.equalsIgnoreCase("Config")) {
			// arg1="Configuration";
			// oGenericUtils.gfn_Click_On_Object("button", "Return Response To
			// "+arg1);
			verify("Clicked on Return Response ", oGenericUtils
					.gfn_Click_String_object_Xpath("//button[contains(text(),'Return Response To " + arg1 + "')]"));

		} else {
			oGenericUtils.gfn_Click_On_Object("button", "Return Response To " + arg1);
		}

		oGenericUtils.gfn_Click_On_Object("button", "Yes");
		// verify("Scucess Messege is : " +"Successfully saved."+"Rule Moved to
		// " +arg1+ " Review",
		// oSeleniumUtils.is_WebElement_Displayed(StringUtils.replace(oCommonPage.DivTag,
		// "sValue", "Successfully saved.")));
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		oGenericUtils.gfn_Click_On_Object("span", "Interpretive Rule Update Tasks");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
	}

	@Step
	public void validateRetrunReviewComments(String sReturnFrom, String sReturnTo) throws InterruptedException {
		oWorkQueuePage.editorialWindow();
		String sReturnComments1 = "";
		String sReturnComments2 = "";
		if (sReturnTo.equalsIgnoreCase("PO")) {
			sReturnComments1 = "Return to PO";
		} else if (sReturnTo.equalsIgnoreCase("QA")) {
			sReturnComments1 = "Return to QA";
		} else if (sReturnTo.equalsIgnoreCase("CPM")) {
			sReturnComments1 = "Return to CPM";
		} else if (sReturnTo.equalsIgnoreCase("Config")) {
			sReturnComments1 = "Return to Configuration";
		} else if (sReturnTo.equalsIgnoreCase("Testing")) {
			sReturnComments1 = "Return to Testing";
		} else if (sReturnTo.trim().equalsIgnoreCase(("PO;Editorial").trim())) {
			sReturnComments1 = "Return to PO";
			sReturnComments2 = "Return to Editorial";
		} else {
			sReturnComments1 = "Return to Editorial";
		}
		oGenericUtils.gfn_Click_On_Object("span", "Comments");
		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
		String sText = oSeleniumUtils.get_StringTextFrom_Locator(
				StringUtil.replace(oCommonPage.GetDynamicXPath("RETURN REVIEW TABLE", 1), "sValue", "returnTo"));
		System.out.println(sText);
		verify("Return To displayed correctly",
				"iht_ittest01".equalsIgnoreCase(oSeleniumUtils.get_StringTextFrom_Locator(StringUtil
						.replace(oCommonPage.GetDynamicXPath("RETURN REVIEW TABLE", 1), "sValue", "returnTo"))));
		verify("Return By displayed correctly",
				"iht_ittest01".equalsIgnoreCase(oSeleniumUtils.get_StringTextFrom_Locator(StringUtil
						.replace(oCommonPage.GetDynamicXPath("RETURN REVIEW TABLE", 1), "sValue", "returnBy"))));
		if (sReturnFrom.equalsIgnoreCase("Config")) {
			sReturnFrom = "Config";
			verify("Return From Task displayed correctly",
					oSeleniumUtils
							.get_StringTextFrom_Locator(StringUtil.replace(
									oCommonPage.GetDynamicXPath("RETURN REVIEW TABLE", 1), "sValue", "returnFromTask"))
							.contains(sReturnFrom));
		} else {
			verify("Return From Task displayed correctly",
					sReturnFrom.equalsIgnoreCase(oSeleniumUtils.get_StringTextFrom_Locator(StringUtil.replace(
							oCommonPage.GetDynamicXPath("RETURN REVIEW TABLE", 1), "sValue", "returnFromTask"))));
		}

		System.out.println("returnto " + oSeleniumUtils.get_StringTextFrom_Locator(
				StringUtil.replace(oCommonPage.GetDynamicXPath("RETURN REVIEW TABLE", 1), "sValue", "returnToTask")));
		System.out.println("Xpath:" + (StringUtil.replace(oCommonPage.GetDynamicXPath("RETURN REVIEW TABLE", 1),
				"sValue", "returnToTask")));
		String sReturnToTaskText = oSeleniumUtils.get_StringTextFrom_Locator(
				StringUtil.replace(oCommonPage.GetDynamicXPath("RETURN REVIEW TABLE", 1), "sValue", "returnToTask"));
		/*
		 * if(sReturnToTaskText.indexOf(sReturnTo)>1){ verify(
		 * "Retrun To Task displayed correctly " +"and text is :"
		 * +sReturnToTaskText, true); }else{ verify(
		 * "Retrun To Task NOT Displayed correctly" +"and text is :"
		 * +sReturnToTaskText, false); }
		 */

		int iReturnComments = getDriver()
				.findElements(By.xpath("//div[@col-id='returnComments']//span//span[@ref='eValue']")).size();
		if (iReturnComments > 1) {
			// verify("Retrun Comments displayed correctly in Line
			// 1",sReturnComments1.trim().equalsIgnoreCase(oSeleniumUtils.get_StringTextFrom_Locator(StringUtil.replace(oCommonPage.GetDynamicXPath("RETURN
			// REVIEW TABLE",1),"sValue","returnComments")).trim()));
			verify("Retrun Comments displayed correctly in Line 2",
					sReturnComments2.trim()
							.equalsIgnoreCase(oSeleniumUtils.get_StringTextFrom_Locator(StringUtil.replace(
									oCommonPage.GetDynamicXPath("RETURN REVIEW TABLE", 2), "sValue", "returnComments"))
									.trim()));
		} else {
			verify("Retrun Comments displayed correctly",
					sReturnComments1.equalsIgnoreCase(oSeleniumUtils.get_StringTextFrom_Locator(StringUtil.replace(
							oCommonPage.GetDynamicXPath("RETURN REVIEW TABLE", 1), "sValue", "returnComments"))));
		}

		// verify("Response Comments displayed correctly","[iht_ittest01] MD
		// Rule Response to
		// Editorial".equalsIgnoreCase(oSeleniumUtils.get_StringTextFrom_Locator(StringUtil.replace(oCommonPage.GetDynamicXPath("RETURN
		// REVIEW TABLE",1),"sValue","responseComments"))));

	}

	@Step
	public void completeCPMReview(String sAgreePayer) throws InterruptedException {

		if (getDriver().findElements(By.xpath("//button[text()='Config']")).size() > 0) {

			oGenericUtils.gfn_Click_String_object_Xpath("//button[text()='Config']");

			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			if (getDriver().findElements(By.xpath("//h4[text()='CPM Additional Configuration Approval']")).size() > 0) {
				verify("CPM Additional Configuration Approval is displayed", true);
				oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
						"//th[text()=' Payer']/../following::tr//select", sAgreePayer);
				oSeleniumUtils.Enter_given_Text_Element("//th[text()=' Payer']/../following::tr//textarea",
						"CPM Comments");

			}
		}

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oGenericUtils.gfn_Click_String_object_Xpath(
				"//label[text()='PO Recommended Additional Configuration:']/../../../../../..//button[.='CPM Decision Complete']");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		if (getDriver().findElements(By.xpath("//button[text()='Yes']")).size() > 0) {

			oGenericUtils.gfn_Click_On_Object("button", "Yes");

			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
			// verify("CPM Decisions completed
			// sucessfully",oSeleniumUtils.is_WebElement_Displayed("//div[text()='CPM
			// Decisions completed successfully']"));
		}

		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		verify("Sucessfully Saved Config Comments", true);
		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

		oGenericUtils.gfn_Click_On_Object("button", "CPM Decision Complete");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		if (getDriver().findElements(By.xpath("//button[text()='Yes']")).size() > 0) {
			oGenericUtils.gfn_Click_On_Object("button", "Yes");

			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			// verify("CPM Decisions completed
			// sucessfully",oSeleniumUtils.is_WebElement_Displayed("//div[text()='CPM
			// Decisions completed successfully']"));
		}

		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		verify("Sucessfully Saved Config Comments", true);
	}

	@Step
	public void verify_Assigned_user_and_claim_the_rule() throws InterruptedException {
		/*
		 * Serenity.setSessionVariable("MidRuleVersion").to("10974.11");
		 * Serenity.setSessionVariable("IUInstanceName").to("TestAutoIV360");
		 */
		String sMidRule = Serenity.sessionVariableCalled("MidRuleVersion").toString();
		String sInstance = Serenity.sessionVariableCalled("IUInstanceName").toString();
		Serenity.setSessionVariable("IUInstanceName").to(sInstance);
		String sDBQuery = DBQueries.sQuery_Rule_Status(sInstance, sMidRule, "U.USER_ID");
		// String sRefRuleId = DBUtils.executeSQLQuery(sDBQuery);
		// sDBQuery = DBQueries.sQuery_Rule_Status(sRefRuleId);
		String sUser = DBUtils.executeSQLQuery(sDBQuery);
		if (sUser.isEmpty() || sUser.contains("Pool") || sUser.contains("PROD")) {
			SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
			SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
			SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
			// oCommonStepDef.userNavigateToIUInstancesScreen("ICD-Interpretive
			// Rule Update");

			// if
			// (!(oSeleniumUtils.is_WebElement_Displayed(StringUtils.replace(oCommonPage.AnchorTag,
			// "sValue",
			// Serenity.sessionVariableCalled("MidRuleVersion").toString())))) {
			// oCommonPage.go_To_Rule(Serenity.sessionVariableCalled("MidRuleVersion").toString());
			// verify("MidRule Version is Displayed :" +
			// Serenity.sessionVariableCalled("MidRuleVersion").toString(),
			// true);
			// }
			oGenericUtils.gfn_Click_On_Object("span", "Interpretive Rule Update Tasks");
			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
			oGenericUtils.gfn_Click_On_Object("span", "Group Tasks");
			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
			if (sUser.equals("QAPool"))
				clickTaskByInstanceName("QA Review Work Queue");
			else if (sUser.equals("TestingPool"))
				clickTaskByInstanceName("Testing Review Work Queue");
			else if (sUser.equals("ConfigPool"))
				clickTaskByInstanceName("Configuration Review Work Queue");
			else
				clickTaskByInstanceName("Editorial Review Work Queue");
			// click on check box of the Rule
			System.out.println("//span[text()='" + Serenity.sessionVariableCalled("MidRuleVersion").toString()
					+ "']/parent::td/../td//p-tablecheckbox//div//div[2]/span");
			oSeleniumUtils
					.Click_given_Locator("//span[text()='" + Serenity.sessionVariableCalled("MidRuleVersion").toString()
							+ "']/parent::td/../td//p-tablecheckbox//div//div[2]");
			// oGenericUtils.gfn_Click_String_object_Xpath("//a[text()='"+Serenity.sessionVariableCalled("MidRuleVersion").toString()+"']/parent::td/../td//p-tablecheckbox//div//div[2]");
			oGenericUtils.gfn_Click_String_object_Xpath("//button[text()=' Claim Task']");
			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
			oGenericUtils.gfn_Click_On_Object("span", "Interpretive Rule Update Tasks");
			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
			oGenericUtils.gfn_Click_String_object_Xpath("(//span[text()='My Tasks'])[2]");
			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
			Serenity.setSessionVariable("User").to("iht_ittest01");
		} else {
			Serenity.setSessionVariable("User").to(sUser);
		}
	}

	@Step
	private void clickTaskByInstanceName(String sTaskName) throws InterruptedException {

		String sInstance = null;

		EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();

		sInstance = variables.getProperty("IU.FinalMDInstance");

		verify("Final MD Del Instance Name: " + sInstance, true);

		String sTaskType = null;

		String strInstance = null;

		SeleniumUtils.defaultWait(4000);

		oGenericUtils.gfn_Click_On_Object("button", " Refresh");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		sTaskType = StringUtil.replace(oMyTasksPage.TaskandInstance, "sTask", sTaskName);

		strInstance = StringUtil.replace(sTaskType, "sInstance", sInstance);

		Serenity.setSessionVariable("TaskandInstnaceName").to(strInstance);

		Serenity.setSessionVariable("IUInstanceName").to(sInstance);

		// System.out.println("Instance Name " +
		// Serenity.sessionVariableCalled("IUInstanceName").toString());

		int i = 0;

		while (oSeleniumUtils.is_WebElement_Displayed(strInstance) == false && i != 10) {

			oGenericUtils.gfn_Click_String_object_Xpath("(//button[text()='Next'])[1 or 2]");

			i = i + 1;
		}

		oGenericUtils.gfn_Click_String_object_Xpath(strInstance);

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

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
	public void complete_Editorial_Review(String sDecision) throws InterruptedException {
		GenericUtils.Verify("Complete editorial ", oWorkQueuePage.CompleteEditorialReview(sDecision));

	}

	@Step
	public void verifySystemProposalDescription() throws InterruptedException {
		GenericUtils.Verify("System proposal full Description ", oWorkQueuePage.verifySystemProposalDescription());
	}

	@Step

	public void ruleUpdateService(String sUpdateType, String arg2) throws IOException {

		String sMidRuleVersion = "";
		String sLotusUser = "";
		if (sUpdateType.equalsIgnoreCase("LOGICALRMRIUPD") || sUpdateType.equalsIgnoreCase("DEACTIVATERULE")) {
			EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
			sMidRuleVersion = variables.getProperty("IU.MidRuleVersion");
			verify("Input Rule MidRule Version is :  " + sMidRuleVersion, true);
			System.out.println("Retrived Rule" + sMidRuleVersion);
		}

		if (sUpdateType.equalsIgnoreCase("LOGICALRMRIUPD")) {
			EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
			sLotusUser = variables.getProperty("IU.LotusUser");

			sLotusUser = sLotusUser.toUpperCase();

			verify("User Name is :  " + sLotusUser, true);
			Serenity.setSessionVariable("LotusUserName").to(sLotusUser);
			System.out.println("Retrived Rule" + sLotusUser);
			Serenity.setSessionVariable("LOGICALSTUB").to("LOGICALSTUBREQ");
		}

		String sSubRuleKey = "";
		if (sUpdateType.equalsIgnoreCase("LOGICALRMRIUPD") || sUpdateType.equalsIgnoreCase("DEACTIVATERULE")) {
			String[] sRuleVersion = sMidRuleVersion.split("\\.");
			String sQuery = "SELECT sub_rule_key FROM  RULES.SUB_RULES where mid_rule_key ='" + sRuleVersion[0]
					+ "' and rule_version= '" + sRuleVersion[1] + "'";
			sSubRuleKey = DBUtils.executeSQLQuery(sQuery);
		} else {
			sSubRuleKey = DBUtils.executeSQLQuery(oCommonPage.getDynamicQuery("UPDATERULE",
					Serenity.sessionVariableCalled("IUInstanceName").toString(), "Final PO Review"));
			System.out.println("SubRuleKey: " + sSubRuleKey);
		}
		String sCase = "";
		if (sUpdateType.equalsIgnoreCase("LOGICALRMRIUPD")) {
			sCase = "CREATERMRSTUBIUPDFORLOGICALRMR";
		} else {
			sCase = "CREATERMRSTUBIUPD";
		}

		String sRequestedPRMIDXML = oWorkQueuePage.createRequestXML(sCase, "HSHZ-051412024417", sSubRuleKey);
		String sPRMIDResponse = GenericUtils.sendPostRequest(ProjectVariables.sLotusRMRURL, sRequestedPRMIDXML);
		String sPRMID = GenericUtils.getTextFromTwoStrings(sPRMIDResponse, "<REQID>", "</REQID>");
		verify("Work order code genernated sucessfully :: WORK ORDER CODE :: " + sPRMID, true);
		System.out.println("Text: " + sPRMID);
		String sRequestedEDITORIALXML = "";
		// Create Request XML for Change
		switch (sUpdateType) {
		case "DEACTIVATERMRIUPD":
			sRequestedEDITORIALXML = oWorkQueuePage.createRequestXML("DEACTIVATERMRIUPD", sPRMID, "");
			break;
		case "DEACTIVATERULE":
			sRequestedEDITORIALXML = oWorkQueuePage.createRequestXML("DEACTIVATERULE", sPRMID, "");
			break;
		case "LOGICALRMRIUPD":
			sRequestedEDITORIALXML = oWorkQueuePage.createRequestXML("LOGICALRMRIUPD", sPRMID, sSubRuleKey);
			break;

		}

		String sSuccessResponse = GenericUtils.sendPostRequest(ProjectVariables.sLotusRMRURL, sRequestedEDITORIALXML);
		String sSuccessMsg = GenericUtils.getTextFromTwoStrings(sSuccessResponse, "<SUCCESSFLAG>", "</SUCCESSFLAG>");
		System.out.println("Msg: " + sSuccessMsg);
		verify(sUpdateType + " Post Request is Sucessfull", sSuccessMsg.equalsIgnoreCase("1"));

		if (sUpdateType.equalsIgnoreCase("DEACTIVATERULE")) {
			String[] sRuleVersion = sMidRuleVersion.split("\\.");
			String sDeactivateQuery = "select DEACTIVATED_10 from rules.sub_rules where mid_rule_key ='"
					+ sRuleVersion[0] + "' and rule_version ='" + sRuleVersion[1] + "'";
			String sDeactivateStatus = DBUtils.executeSQLQuery(sDeactivateQuery);
			verify("Rule No:: " + sMidRuleVersion + "is Deactivated and DB Status is :: " + sDeactivateStatus,
					sDeactivateStatus.equalsIgnoreCase("-1"));
		}

		if (sUpdateType.equalsIgnoreCase("LOGICALRMRIUPD")) {

			String sChangeLogQuery = "select work_order_code from auth_master.change_log where work_order_code = '"
					+ sPRMID + "' and sub_rule_key = '" + sSubRuleKey + "' and  rownum = 1";
			String sChangeLogStatus = DBUtils.executeSQLQuery(sChangeLogQuery);
			verify("ChangeLog Generated Sucessfully  and WORK_ORDER_CODE:: " + sPRMID,
					sChangeLogStatus.equalsIgnoreCase(sPRMID));
		}
		if (sUpdateType.equalsIgnoreCase("LOGICALRMRIUPD") || sUpdateType.equalsIgnoreCase("DEACTIVATERULE")) {

			System.out.println("PASS ");
		} else {
			String sMidRule = DBUtils.executeSQLQuery("SELECT IRD.MID_RULE_DOT_VERSION\r\n" + "     \r\n"
					+ "  FROM IRDM.INTERP_RULES IR\r\n" + "       JOIN IRDM.INTERP_RULE_DETAILS IRD\r\n"
					+ "          ON IR.INTERP_RULE_KEY = IRD.INTERP_RULE_KEY\r\n"
					+ "       JOIN IPDE.TASK_DETAILS TD ON TD.REFERENCE_RULE_ID = IR.INTERP_RULE_KEY\r\n"
					+ "       JOIN IPDE.TASK_TYPE_LKP TTL ON TTL.TASK_TYPE_KEY = TD.TASK_TYPE_KEY\r\n"
					+ "       JOIN IPDE.TASK_STATUS_LKP TSL\r\n"
					+ "          ON TSL.TASK_STATUS_KEY = TD.TASK_STATUS_KEY\r\n"
					+ "WHERE     IR.IMPACT_KEY IN (SELECT IMPACT_KEY\r\n"
					+ "                               FROM IRDM.INTERP_IMPACTS II\r\n"
					+ "                                   JOIN IRDM.UPDATE_INSTANCES I\r\n"
					+ "                                       ON I.UPDATE_INSTANCE_KEY =\r\n"
					+ "                                             II.UPDATE_INSTANCE_KEY\r\n"
					+ "                              WHERE UPDATE_INSTANCE_NAME = '"
					+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "')\r\n"
					+ "       AND IR.SUB_RULE_KEY = '" + sSubRuleKey + "'");
			System.out.println("MiddleRule Version: " + sMidRule);
			Serenity.setSessionVariable("MidRuleVersion").to(sMidRule);
		}
	}

	@Step
	public void clickStartReviewandValidate(String sMessegeType) throws InterruptedException {

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		oGenericUtils.gfn_Click_On_Object("button", "Start Review");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		int i = getDriver()
				.findElements(By
						.xpath("//div[text()='Custom rule has been deactivated. Rule status will be set to DEACTIVATED. Decision Capture is not allowed for the deactivated rules.']"))
				.size();

		if (i > 0) {

			verify("Fule is Deactivated Messege:: "
					+ "Custom rule has been deactivated. Rule status will be set to DEACTIVATED. Decision Capture is not allowed for the deactivated rules.']",
					true);
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		} else {
			verify("Rule is not deactivated", false);
		}
	}

	@Step
	public void selectByPayerInCPM(String sCPMDecision, String sMDDecision) throws InterruptedException {

		List<String> sDecValue = Arrays.asList(sCPMDecision.split(";"));
		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
				"(//tbody[@class='ui-table-tbody'])[1]//tr[1]//td[3]//select", "By Payer");
		List<WebElement> CPMManualRows = getDriver()
				.findElements(By.xpath("(//tbody[@class='ui-table-tbody'])[2]//tr"));

		for (int i = 0; i < CPMManualRows.size(); i++) {

			System.out.println(sDecValue.get(0));
			System.out.println(oCommonPage.GetDynamicXPathWithRowCol("BYPAYERCOMMENTS", i + 1, 6));
			if (i > 0) {
				oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
						oCommonPage.GetDynamicXPathWithRowCol("BYPAYERLISTBOX", i + 1, 2), sDecValue.get(1));
			} else {
				oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
						oCommonPage.GetDynamicXPathWithRowCol("BYPAYERLISTBOX", i + 1, 2), sDecValue.get(0));
			}
			oSeleniumUtils.Enter_given_Text_Element(oCommonPage.GetDynamicXPathWithRowCol("BYPAYERTEXTTBOX", i + 1, 4),
					"CPM Comments");

			String sMDDecText = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPathWithRowCol("BYPAYER", i + 1, 5));
			verify("MDDecision is displayed properly" + "Actual:" + sMDDecText + "Expected:" + sMDDecision,
					sMDDecision.trim().equalsIgnoreCase(sMDDecText.trim()));

			String sMDComments = oSeleniumUtils
					.get_TextBy_Attribute(oCommonPage.GetDynamicXPathWithRowCol("BYPAYERCOMMENTS", i + 1, 6));
			verify("MDComments are displayed properly" + "Actual:" + sMDComments + "Expected:"
					+ ProjectVariables.SystemProposalComments,
					ProjectVariables.SystemProposalComments.trim().equalsIgnoreCase(sMDComments.trim()));
			System.out.println("comments " + sMDComments);

		}

		oGenericUtils.gfn_Click_On_Object("button", "Save");
		verify("Sucessfully saved proposal decisions", true);
		oGenericUtils.gfn_Click_On_Object("button", "Ok");

	}

	@Step
	public void validateConfigSummaryAndWarning() throws InterruptedException {

		verify("Payer Specifici Decisions Warning is displayed", oSeleniumUtils
				.is_WebElement_Displayed("//div[text()=' WARNING!! Payer Specific Decisions Exist for this Rule ']"));
		oGenericUtils.gfn_Click_On_Object("h5", " Configurations Summary ");
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		verify("No Configuration Summary data displayed", oSeleniumUtils.is_WebElement_Displayed(
				"//label[text()=' No Configuration Summary data displayed because Payer specific decisions exists for the rule']"));

	}

	public void validateRuleReviewColumnValues(String arg1, String sColumnName, String sColumnValues)
			throws InterruptedException {
		// Gherkin format Ex: "Task;Status;Assginee" "CPM
		// Review;Status;Assignee"
		// Serenity.setSessionVariable("MidRuleVersion").to("11773.11");
		// Serenity.setSessionVariable("IUInstanceName").to("ICD Instance");
		List<String> sColName = Arrays.asList(sColumnName.split(";"));
		List<String> sColVal = Arrays.asList(sColumnValues.split(";"));
		String sAssigneeColVal = "";
		String sTaskColVal = "";
		String sStatus = "";
		oGenericUtils.gfn_Click_String_object_Xpath("//span[@class='tab-close ng-star-inserted']");
		SeleniumUtils.defaultWait(2000);
		boolean bflag = oAdminPage.goToActionsInAdminTab("");
		verify("Actions button is displayed in Admin Tab", bflag);

		oGenericUtils.gfn_Click_On_Object("button", " Actions");
		oGenericUtils.gfn_Click_On_Object("button", " Rule Review ");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		verify("Rule Review is displayed sucessfully", true);

		oCommonPage.go_To_Rule(Serenity.sessionVariableCalled("MidRuleVersion").toString());

		for (int i = 0; i < sColName.size(); i++) {

			switch (sColName.get(i)) {
			case "Task":
				sTaskColVal = oSeleniumUtils
						.get_StringTextFrom_Locator(oCommonPage.GetDynamicXPath("RULEREVIEW COLVALUE", 5));
				if (sTaskColVal.equalsIgnoreCase(sColVal.get(i))) {
					verify(sColName.get(i) + " Value Expected :" + sColVal.get(i) + " and Actual is " + sTaskColVal,
							true);
				} else {
					verify(sColName.get(i) + " Value Expected :" + sColVal.get(i) + " and Actual is " + sTaskColVal,
							false);
				}
				break;
			case "Status":
				sStatus = oSeleniumUtils
						.get_StringTextFrom_Locator(oCommonPage.GetDynamicXPath("RULEREVIEW COLVALUE", 6));
				if (sStatus.equalsIgnoreCase(sColVal.get(i))) {
					verify(sColName.get(i) + " Value Expected :" + sColVal.get(i) + " and Actual is " + sStatus, true);
				} else {
					verify(sColName.get(i) + " Value Expected :" + sColVal.get(i) + " and Actual is " + sStatus, false);
				}
				break;
			case "Assginee":
				sAssigneeColVal = oSeleniumUtils
						.get_StringTextFrom_Locator(oCommonPage.GetDynamicXPath("RULEREVIEW COLVALUE", 8));
				if (sAssigneeColVal.equalsIgnoreCase(sColVal.get(i))) {
					verify(sColName.get(i) + " Value Expected :" + sColVal.get(i) + " and Actual is " + sAssigneeColVal,
							true);
				} else {
					verify(sColName.get(i) + " Value Expected :" + sColVal.get(i) + " and Actual is " + sAssigneeColVal,
							false);
				}
				break;
			}
		}
	}

	public void complete_the_Review(String sReview) throws InterruptedException {
		GenericUtils.Verify("Completed the review", oWorkQueuePage.complete_the_Review(sReview));
	}

	@Step
	public void validate_the_Rule_Task(String sStatus) throws InterruptedException {

		// GenericUtils.Verify("verify rule status",
		// oWorkQueuePage.validateRuleStatus(Status));

		String sMidRule = Serenity.sessionVariableCalled("MidRuleVersion").toString();
		String sInstance = Serenity.sessionVariableCalled("IUInstanceName").toString();
		Serenity.setSessionVariable("IUInstanceName").to(sInstance);
		String sDBQuery = DBQueries.sQuery_Rule_Status(sInstance, sMidRule, "TASK_STATUS_DESC");
		String sUIstatus = DBUtils.executeSQLQuery(sDBQuery);
		if (sStatus.equals(sUIstatus)) {
			verify("Rule Status is : " + sUIstatus + " Expected :  " + sStatus, true);
		} else {
			verify("Rule Status is : " + sUIstatus + " Expected :  " + sStatus, true);
		}
	}

	@Step
	public void validateRuleResponseComments(String arg1, String arg2) {

		int iResponseComments = getDriver()
				.findElements(By.xpath("//div[@col-id='responseComments']//span//span[@ref='eValue']")).size();
		String sSearchResponseComments = "Response to " + arg1;

		String sSearchResponseComments2 = "Rule Response to " + arg2;
		String sText = oSeleniumUtils.get_StringTextFrom_Locator(StringUtil
				.replace(oCommonPage.GetDynamicXPath("RETURN REVIEW TABLE", 1), "sValue", "responseComments"));

		System.out.println("vALUE " + (sText.indexOf(sSearchResponseComments)));

		System.out.println("Passed");

		if (iResponseComments > 1) {
			String sText2 = oSeleniumUtils.get_StringTextFrom_Locator(StringUtil
					.replace(oCommonPage.GetDynamicXPath("RETURN REVIEW TABLE", 2), "sValue", "responseComments"));
			if (sText.trim().indexOf(sSearchResponseComments.trim()) > 1) {
				verify("Response Comments displayed correctly in Line 1" + "  Expected:: " + sSearchResponseComments
						+ "Actual:: " + sText, true);
			} else {
				verify("Response Comments not displayed correctly in Line 1" + "  Expected:: " + sSearchResponseComments
						+ "Actual:: " + sText, false);
			}
			if (sText2.trim().indexOf(sSearchResponseComments2.trim()) > 1) {
				verify("Response Comments displayed correctly in Line 2" + "  Expected:: " + sSearchResponseComments2
						+ "Actual:: " + sText2, true);
			} else {
				verify("Response Comments not displayed correctly in Line 2" + "  Expected:: "
						+ sSearchResponseComments2 + "Actual:: " + sText2, false);
			}

		} else {
			if (sText.trim().indexOf(sSearchResponseComments.trim()) > 1) {
				verify("Response Comments displayed correctly in Line 1" + "  Expected:: " + sSearchResponseComments
						+ "Actual:: " + sText, true);
			} else {
				verify("Response Comments not displayed correctly in Line 1" + "  Expected:: " + sSearchResponseComments
						+ "Actual:: " + sText, false);
			}
		}

	}

	@Step
	public void should_be_displayed_with_Full_description_along_with_code_under_Sim_Codes_and_Review_codes()
			throws InterruptedException {

		oWorkQueuePage.should_be_displayed_with_Full_description_along_with_code_under_Sim_Codes_and_Review_codes();

	}

	@Step
	public void verify_rule_Status_in_DB_for_User(String sStatus, String sUser) throws Exception {

		oWorkQueuePage.verify_rule_Status_in_DB_for_User(sStatus, sUser);

	}

	@Step
	public void complete_QA_Review(String sDecision) throws InterruptedException {
		GenericUtils.Verify("Complete QA Review ", oWorkQueuePage.CompleteQAReview(sDecision));

	}

	@Step
	public void complete_Testing_Review(String sDecision) throws InterruptedException {
		GenericUtils.Verify("Complete Testing Review ", oWorkQueuePage.CompleteTestingReview(sDecision));

	}

	public void completePostAdditionalConfig() throws InterruptedException {

		oSeleniumUtils.Enter_given_Text_Element("//a[text()='Post Additional Configuration']/../../../..//td/textarea",
				"Automating testing");

		oGenericUtils.gfn_Click_On_Object("button", "Save");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);

		oGenericUtils.gfn_Verify_Object_Exist("div", " QA review comments successfully saved. ");

		oGenericUtils.gfn_Click_On_Object("a", "Post Additional Configuration");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		oGenericUtils.gfn_Click_On_Object("button", " Complete QA Review Post Configuration ");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		oGenericUtils.gfn_Verify_Object_Exist("div", "Review completed successfully.");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		String sText = oSeleniumUtils.get_StringTextFrom_Locator(
				"//a[text()='Post Additional Configuration']/../../../..//td[@ng-reflect-ng-switch='taskStatusDesc']/span");

		boolean bln = sText.equalsIgnoreCase("Completed");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oGenericUtils.gfn_Click_On_Object("button", "Additional Config QA Completed");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		oGenericUtils.gfn_Verify_Object_Exist("div", "QA Review Completed");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

	}

	public void completeConfigReview() throws Exception {

		oSeleniumUtils.Enter_given_Text_Element("//a[text()='Additional Configuration']/../../../..//td/textarea",
				"Automating testing");

		oGenericUtils.gfn_Click_On_Object("button", "Save");

		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);

		oGenericUtils.gfn_Verify_Object_Exist("div", " Config review comments successfully saved. ");

		oGenericUtils.gfn_Click_On_Object("a", "Additional Configuration");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		oGenericUtils.gfn_Click_On_Object("button", " Complete Config Review ");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		oGenericUtils.gfn_Verify_Object_Exist("div", "Review completed successfully.");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		String sText = oSeleniumUtils.get_StringTextFrom_Locator(
				"//a[text()='Additional Configuration']/../../../..//td[@ng-reflect-ng-switch='taskStatusDesc']/span");

		sText.equalsIgnoreCase("Completed");

		String sStubRMRID = oSeleniumUtils
				.get_StringTextFrom_Locator("//label[text()='Stub RMR ID: ']/following-sibling::label");

		oGenericUtils.gfn_Click_On_Object("button", "Generate Change Log");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		oGenericUtils.gfn_Verify_Object_Exist("div", "Created Successfully.");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		String sSubRuleKey = "SELECT IR.SUB_RULE_KEY   FROM IRDM.INTERP_RULES IR        JOIN IRDM.INTERP_RULE_DETAILS IRD           ON IR.INTERP_RULE_KEY = IRD.INTERP_RULE_KEY        JOIN IPDE.TASK_DETAILS TD ON TD.REFERENCE_RULE_ID = IR.INTERP_RULE_KEY\r\n"
				+ "            JOIN IPDE.TASK_TYPE_LKP TTL ON TTL.TASK_TYPE_KEY = TD.TASK_TYPE_KEY        JOIN IPDE.TASK_STATUS_LKP TSL           ON TSL.TASK_STATUS_KEY = TD.TASK_STATUS_KEY WHERE     IR.IMPACT_KEY IN (SELECT IMPACT_KEY   \r\n"
				+ "                                         FROM IRDM.INTERP_IMPACTS II                                    JOIN IRDM.UPDATE_INSTANCES I                                        ON I.UPDATE_INSTANCE_KEY =    \r\n"
				+ "                                                                                   II.UPDATE_INSTANCE_KEY                               WHERE UPDATE_INSTANCE_NAME =  '"
				+ Serenity.sessionVariableCalled("IUInstanceName").toString() + "')   AND  MID_RULE_DOT_VERSION='"
				+ Serenity.sessionVariableCalled("MidRuleVersion").toString() + "'  \r\n";

		System.out.println("SubRule Key Query:   " + sSubRuleKey);

		String sChangeLogQuery = "select * from auth_master.change_log where work_order_code = '" + sStubRMRID.trim()
				+ "' and username = 'IHT_ITTEST02' AND  SUB_RULE_KEY = '" + DBUtils.executeSQLQuery(sSubRuleKey) + "'";

		System.out.println("ChangeLogQuery      " + sChangeLogQuery);

		String sUser = DBUtils.db_GetFirstValueforColumn(sChangeLogQuery, "USERNAME");

		boolean bln = "iht_ittest02".equalsIgnoreCase(sUser);

		System.out.println("Change log userid   " + bln);

		oGenericUtils.gfn_Click_On_Object("button", "Config Complete");

		oGenericUtils.gfn_Verify_Object_Exist("div",
				"Rule is being set to Configuration Completed and will be reassigned to QA for review. Continue?");

		oGenericUtils.gfn_Click_On_Object("button", "Yes");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		oGenericUtils.gfn_Verify_Object_Exist("div", "Configuration review completed successfully.");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

	}

	@Step
	public void retireRuleAndCancelRetireRuleValidation() throws InterruptedException {

		oWorkQueuePage.retireRuleAndCancelRetireRuleValidation();

	}

	@Step

	public void uploadFileInTestingReview() throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		String[] str = { "ICDAttachFiles.docx", "BRATReuslts.xlsx" };

		for (int j = 0; j < str.length; j++) {
			Serenity.setSessionVariable("AttachmentName").to(str[j]);
			String sFileName = str[j];
			oGenericUtils.attachFiles(sFileName);
			oGenericUtils.gfn_Click_On_Object("button", "Attach");
			// oGenericUtils.gfn_Verify_Object_Exist("h4", "Success");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
		}
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		oGenericUtils.gfn_Verify_String_Object_Exist(oCommonPage.GetDynamicXPath("BRAT RESULTS TEXTBOX", 1));
		List<WebElement> eChkBox = getDriver().findElements(By.xpath(
				"(//div[@class='ui-table-wrapper ng-star-inserted'])[2]//div[@class='ui-chkbox-box ui-widget ui-state-default']"));
		// List<WebElement> eTextbox =
		// getDriver().findElements(By.xpath("(//div[@class='ui-table-wrapper
		// ng-star-inserted'])[2]//textarea[@class='ng-untouched ng-pristine
		// ng-valid ng-star-inserted']"));
		System.out.println("chk box size " + eChkBox.size());
		for (int i = 1; i < eChkBox.size(); i++) {
			oSeleniumUtils.Enter_given_Text_Element(oCommonPage.GetDynamicXPath("BRAT RESULTS TEXTBOX", i), "");
			oSeleniumUtils.Enter_given_Text_Element(oCommonPage.GetDynamicXPath("BRAT RESULTS TEXTBOX", i),
					ProjectVariables.BratResultsComments);
		}
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		oGenericUtils.gfn_Click_String_object_Xpath("//button[text()='Delete']/..//button[text()='Save']");
		oGenericUtils.gfn_Verify_Object_Exist("h4", "Success");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		oGenericUtils.gfn_Click_On_Object("span", "QA");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		oGenericUtils.gfn_Click_On_Object("span", "Test");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.highlightElement(oCommonPage.Stub_RMR_ID);
		Serenity.setSessionVariable("Stub_RMR_ID").to(oCommonPage.Stub_RMR_ID.getText());
		System.out.println(Serenity.sessionVariableCalled("Stub_RMR_ID").toString());

		// Validate attachments name and comments
		for (int k = 1; k < eChkBox.size(); k++) {

			boolean bTXTFlag = oGenericUtils.gfn_Verify_String_Object_Exist(
					StringUtil.replace(oCommonPage.GetDynamicXPath("BRAT RESULTS TEXTBOX", k), "sValue",
							Serenity.sessionVariableCalled("Stub_RMR_ID").toString()));
			verify(" Attachments are dispalyed in Line  " + k, bTXTFlag);

			boolean bCOMMENTSFlag = oGenericUtils.gfn_Verify_String_Object_Exist(StringUtil.replace(
					oCommonPage.GetDynamicXPath("BRAT COMMENTS", k), "sValue", ProjectVariables.BratResultsComments));
			verify(" Comments are dispalyed in Line  " + k + " Comments are : " + ProjectVariables.BratResultsComments,
					bCOMMENTSFlag);

		}

	}

	public void userDeleteAttachments() throws InterruptedException {

		// Delete attachments and Save
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		oGenericUtils.gfn_Click_String_object_Xpath(oCommonPage.GetDynamicXPath("BRAT RESULTS CHKBOX", 1));
		oGenericUtils.gfn_Click_On_Object("button", "Delete");
		oGenericUtils.gfn_Verify_Object_Exist("h4", "Success");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		verify("Test Results sucessfully Deleted ", true);

		oGenericUtils.gfn_Click_On_Object("span", "QA");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		oGenericUtils.gfn_Click_On_Object("span", "Test");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		List<WebElement> sFileName = getDriver().findElements(
				By.xpath("(//div[@class='ui-table-wrapper ng-star-inserted'])[2]//span[@class='ng-star-inserted']//a"));
		if (sFileName.size() > 0) {
			verify(" Attachments are dispalyed ", false);
		} else {
			verify("Expected :: Attachments are not  dispalyed ", true);
		}
	}

	@Step
	public void validate_the_dropdown_values_with_DB(String sColumn, String sReview, String sSearch)
			throws InterruptedException {

		oWorkQueuePage.validate_the_dropdown_values_with_DB(sColumn, sReview, sSearch);

	}

	@Step
	public void validateFilterRuleswithDB(String sColumn, String sReview) throws InterruptedException {

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		String sInstance = Serenity.sessionVariableCalled("IUInstanceName").toString();
		String sDBQuery = DBQueries.queryForDropdownList("", sInstance, sReview, sColumn);
		String sDBRulesCount = DBUtils.executeSQLQuery(sDBQuery);

		String sText = oSeleniumUtils.get_StringTextFrom_Locator("//span[contains(text(),'Showing')]");
		String[] sShowCount = sText.trim().split(" ");
		System.out.println("vALUE " + sShowCount[3]);
		GenericUtils.Verify("Record count" + " UI Count: " + sShowCount[3] + "DB Count: " + sDBRulesCount,
				String.valueOf(sDBRulesCount).equals(sShowCount[3]));

	}

	@Step
	public void validatePayerFilterRulesCount(String sColumn, String sReview, String arg3) throws InterruptedException {

		String sInstance = Serenity.sessionVariableCalled("IUInstanceName").toString();
		String sDBQuery = DBQueries.queryForDropdownList("", sInstance, arg3, sColumn);
		String sDBRuleValue = DBUtils.executeSQLQuery(sDBQuery);

		String[] sPayerSplit = sDBRuleValue.split(" ");

		System.out.println(sPayerSplit[0]);

		WebElement e = getDriver().findElement(By.xpath("//mat-label[text()='Priority']/../../label"));
		(new Actions(getDriver())).moveToElement(e).click().build().perform();
		oSeleniumUtils.highlightElement("//mat-label[text()='" + arg3 + "']/../../label");
		WebElement f = getDriver().findElement(By.xpath("//mat-label[text()='" + arg3 + "']/../../label"));
		(new Actions(getDriver())).moveToElement(f).click().build().perform();
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		verify("clicked on dropdown",
				oGenericUtils.gfn_Click_String_object_Xpath("(//div[@class='mat-select-arrow'])[13]"));
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		oGenericUtils
				.gfn_Click_String_object_Xpath("//span[text()='" + sPayerSplit[0].trim() + "']/../mat-pseudo-checkbox");

		WebElement g = getDriver().findElement(By.xpath("//button[text()='ApplyFilter']"));
		(new Actions(getDriver())).moveToElement(g).click().build().perform();

		verify("clicked on Apply Filter",
				oGenericUtils.gfn_Click_String_object_Xpath("//button[text()='ApplyFilter']"));
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		String sDBRuleCountQuery = DBQueries.queryForDropdownList("PAYERRULECOUNT", sInstance, sReview, sColumn);
		String sDBRuleCount = DBUtils.executeSQLQuery(sDBRuleCountQuery);
		String sText = oSeleniumUtils.get_StringTextFrom_Locator("//span[contains(text(),'Showing')]");
		String[] sShowCount = sText.trim().split(" ");
		System.out.println("vALUE " + sShowCount[3]);
		GenericUtils.Verify("Record count" + " UI Count: " + sShowCount[3] + "  DB Count: " + sDBRuleCount,
				String.valueOf(sDBRuleCount).equals(sShowCount[3]));

	}

	@Step

	public void createPRMID(String sUpdateType) throws IOException {

		EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();

		switch (sUpdateType) {
		case "CREATEPRMID":

			String sLotusUser = variables.getProperty("IU.LotusUser");

			Serenity.setSessionVariable("LotusUserName").to(sLotusUser);
			System.out.println("Lotus User is::  " + sLotusUser);
			Serenity.setSessionVariable("LOGICALSTUB").to("LOGICALSTUBREQ");

			String sPRMID = oWorkQueuePage.createPRMID(sUpdateType);
			System.out.println("Text: " + sPRMID);
			verify("User " + sLotusUser + " Created Sucessfully and PRM ID is:: " + sPRMID, true);
			break;

		case "GETPRSTATUS":

			String sLotusPRMID = variables.getProperty("IU.LotusPRMID");
			Serenity.setSessionVariable("LotusPRID").to(sLotusPRMID.trim());
			String sPRMIDStatus = oWorkQueuePage.createPRMID(sUpdateType);
			System.out.println("Text: " + sPRMIDStatus);
			if (sPRMIDStatus.equalsIgnoreCase("2")) {
				sPRMIDStatus = "In Analysis";
			}
			if (sPRMIDStatus.equalsIgnoreCase("3")) {
				sPRMIDStatus = "In Progress";
			}
			if (sPRMIDStatus.equalsIgnoreCase("4")) {
				sPRMIDStatus = "In Testing";
			}
			if (sPRMIDStatus.equalsIgnoreCase("5")) {
				sPRMIDStatus = "Pended";
			}
			if (sPRMIDStatus.equalsIgnoreCase("6")) {
				sPRMIDStatus = "Awating Approval";
			}
			if (sPRMIDStatus.equalsIgnoreCase("1")) {
				sPRMIDStatus = "New";
			}
			if (sPRMIDStatus.equalsIgnoreCase("7")) {
				sPRMIDStatus = "Completed";
			}
			if (sPRMIDStatus.equalsIgnoreCase("8")) {
				sPRMIDStatus = "Rejected";
			}
			verify("PRM ID " + sLotusPRMID + " Status is :: " + sPRMIDStatus, true);
			break;
		}

	}

	public void selectReportTab(String arg1) {

		verify("Clicked on Dashboard ", oGenericUtils.gfn_Click_String_object_Xpath("//*[text()=' Dashboard']"));

		int i = getDriver().findElements(By.xpath("//button[text()='Yes']")).size();

		if (i > 0) {
			oGenericUtils.gfn_Click_String_object_Xpath("//button[text()='Yes']");
		}

		switch (arg1) {
		case "Scrub Report":
			int j = getDriver().findElements(By.xpath("//*[contains(text(),'" + arg1 + "')]")).size();
			if (j > 0) {
				oGenericUtils.gfn_Click_String_object_Xpath("//*[contains(text(),'" + arg1 + "')]");
				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			}
			break;

		case " User Name Task Report":
			int k = getDriver().findElements(By.xpath("//*[contains(text(),'" + arg1 + "')]")).size();
			if (k == 0) {
				oGenericUtils.gfn_Click_String_object_Xpath("//*[contains(text(),'Status Tracker')]");
				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			}
			oGenericUtils.gfn_Click_String_object_Xpath("//*[contains(text(),'" + arg1 + "')]");
			break;
		case " Rule History Report":
			int l = getDriver().findElements(By.xpath("//*[contains(text(),'" + arg1 + "')]")).size();
			if (l == 0) {
				oGenericUtils.gfn_Click_String_object_Xpath("//*[contains(text(),'Status Tracker')]");
				SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			}
			oGenericUtils.gfn_Click_String_object_Xpath("//*[contains(text(),'" + arg1 + "')]");
			break;
		}

	}

	public void selectReportCategeory(String sSelectReportType, String sRunType, String sSearchRule) {

		verify(sSelectReportType + " is Clicked ",
				(oGenericUtils.gfn_Click_String_object_Xpath("//*[contains(text(),'" + sSelectReportType + "')]")));

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		if (sSearchRule.equalsIgnoreCase("Validate Rules")) {
			ArrayList<String> sMidRuleList = new ArrayList<String>();

			List<WebElement> iRulesCount = getDriver().findElements(By.xpath(
					"//ng-multiselect-dropdown[@formcontrolname='searchRule']//li[@class='multiselect-item-checkbox ng-star-inserted']//div"));
			System.out.println("Rules Count " + iRulesCount.size());
			oGenericUtils.gfn_Click_String_object_Xpath("//span[text()='Search Rule']");
			for (int i = 2; i <= iRulesCount.size(); i++) {
				String sMidRule = oSeleniumUtils
						.get_StringTextFrom_Locator(oCommonPage.GetDynamicXPath("SEARCHRULE RULES", i));
				sMidRuleList.add(sMidRule.trim());
			}
			ArrayList<String> sDBRuleList = DBUtils.db_GetAllColumnValues(
					DBQueries.sQueryGetAllRulesforInstance("MID_RULE_DOT_VERSION",
							Serenity.sessionVariableCalled("IUInstanceName").toString(), "Manual RMR"),
					"MID_RULE_DOT_VERSION");

			Collections.sort(sDBRuleList);
			Collections.sort(sMidRuleList);

			verify("List of Rules in DB " + sDBRuleList, true);
			verify("List of Rules from IU Report " + sMidRuleList, true);
			verify("Rules compare is equal  " + sMidRuleList, sDBRuleList.equals(sMidRuleList));

		}

	}

	public void validateSearchRule(String sReviewType, String sTaskStatus) throws InterruptedException {
		String sRuleSearchFlag = "False";
		String sMidRule = "";
		if (sReviewType.equalsIgnoreCase("ManualRMRReport")) {
			sMidRule = DBUtils.executeSQLQuery(DBQueries.sQueryGetAllRulesonTaskStatus("MID_RULE_DOT_VERSION",
					Serenity.sessionVariableCalled("IUInstanceName").toString(), sTaskStatus));
		} else {
			sMidRule = DBUtils.executeSQLQuery(
					DBQueries.sQueryGetRetriveDetails("MID_RULE_DOT_VERSION", sReviewType, sTaskStatus));
		}
		if (!(sReviewType.equalsIgnoreCase("ManualRMRReport"))) {
			// Serenity.setSessionVariable("IUInstanceName").to("TestAutoIV508");

			if (!(sMidRule.isEmpty())) {
				oGenericUtils.gfn_Click_String_object_Xpath("//span[text()='Search Rule']");
				WebElement e = getDriver().findElement(By.xpath("//div[text()='" + sMidRule + "']"));
				(new Actions(getDriver())).moveToElement(e).click().build().perform();
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
				verify("Generate Report button is Clicked", oWorkQueuePage.clickGenerateReport());
				sRuleSearchFlag = "True";
			} else {
				verify("No Rules for " + sReviewType + " " + sTaskStatus + " Query ::  "
						+ DBQueries.sQueryGetRetriveDetails("MID_RULE_DOT_VERSION", sReviewType, sTaskStatus), true);
			}
		}

		if (sRuleSearchFlag.equalsIgnoreCase("True") || sReviewType.equalsIgnoreCase("ManualRMRReport")) {

			int i = 0;
			String sUIRMRValue = null;
			String sUIAssign = null;
			if (sReviewType.equalsIgnoreCase("ManualRMRReport")) {
				i = 1;
				String sUIRMRValu2e = oSeleniumUtils
						.get_TextFrom_Locator("(//table[@class='ui-table-scrollable-body-table']//tr//td[1])[1]");
				sUIRMRValue = oSeleniumUtils.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("REPORT FIELDS", i));

			}
			String sUIMidValue = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("REPORT FIELDS", i + 1));
			String sUIVerValue = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("REPORT FIELDS", i + 2));
			String sUIRunType = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("REPORT FIELDS", i + 3));
			String sUILibraryType = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("REPORT FIELDS", i + 4));
			String sUIMedPolicy = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("REPORT FIELDS", i + 5));
			String sUITopicValue = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("REPORT FIELDS", i + 6));
			String sUIDPValue = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("REPORT FIELDS", i + 7));
			String sUITaskValue = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("REPORT FIELDS", i + 8));
			String sUITaskStatus = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("REPORT FIELDS", i + 9));
			if (!(sReviewType.equalsIgnoreCase("ManualRMRReport"))) {
				sUIAssign = oSeleniumUtils.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("REPORT FIELDS", i + 10));
			}
			String sDBRMRCode = null;
			if (sReviewType.equalsIgnoreCase("ManualRMRReport")) {
				sDBRMRCode = DBUtils.executeSQLQuery(DBQueries.sQueryTaskTypeAndStatus("WORK_ORDER_CODE",
						Serenity.sessionVariableCalled("IUInstanceName").toString(), sMidRule));
			}
			String sDBRunType = DBUtils.executeSQLQuery(DBQueries.sQueryGetImpactDesc("IMPACT_DESC"));
			String sDBLibraryType = DBUtils.executeSQLQuery(DBQueries.sQueryGetRuleDetails("LIBRARY_STATUS_DESC",
					Serenity.sessionVariableCalled("IUInstanceName").toString(), sMidRule));
			String sDBMedPolicy = DBUtils.executeSQLQuery(DBQueries.sQueryGetRuleDetails("MED_POL_TITLE",
					Serenity.sessionVariableCalled("IUInstanceName").toString(), sMidRule));
			String sDBTopicValue = DBUtils.executeSQLQuery(DBQueries.sQueryGetRuleDetails("TOPIC_TITLE",
					Serenity.sessionVariableCalled("IUInstanceName").toString(), sMidRule));
			String sDBDPValue = DBUtils.executeSQLQuery(DBQueries.sQueryGetRuleDetails("DP_DESC",
					Serenity.sessionVariableCalled("IUInstanceName").toString(), sMidRule));
			String sDBTaskValue = DBUtils.executeSQLQuery(DBQueries.sQueryTaskTypeAndStatus("TTL.TASK_TYPE_DESC",
					Serenity.sessionVariableCalled("IUInstanceName").toString(), sMidRule));
			String sDBTaskStatus = DBUtils.executeSQLQuery(DBQueries.sQueryTaskTypeAndStatus("TASK_STATUS_DESC",
					Serenity.sessionVariableCalled("IUInstanceName").toString(), sMidRule));
			String sDBAssign = DBUtils.executeSQLQuery(DBQueries.sQueryTaskTypeAndStatus("U.USER_ID",
					Serenity.sessionVariableCalled("IUInstanceName").toString(), sMidRule));

			String[] sRuleVersion = sMidRule.split("\\.");
			// verify("UI Mid value Matched with DB " + "Expected:: "
			// +sRuleVersion[0] + " Actual:: " +sUIMidValue + " MidRule "
			// +sMidRule,sRuleVersion[0].trim().equalsIgnoreCase(sUIMidValue.trim()));
			// verify("UI Version value Matched with DB " + "Expected:: "
			// +sRuleVersion[1] + " Actual:: " +sUIVerValue+ " MidRule "
			// +sMidRule,sRuleVersion[1].trim().equalsIgnoreCase(sUIVerValue.trim()));

			verify("UI  Run Type value Matched with DB " + "Expected::  " + sDBRunType + " Actual:: " + sUIRunType
					+ " MidRule  " + sMidRule, sUIRunType.trim().equalsIgnoreCase(sDBRunType.trim()));
			verify("UI  Library Type Matched with DB " + "Expected::  " + sDBLibraryType + " Actual:: " + sUILibraryType
					+ " MidRule  " + sMidRule, sUILibraryType.trim().equalsIgnoreCase(sDBLibraryType.trim()));
			verify("UI Med Policy value Matched with DB " + "Expected::  " + sDBMedPolicy + " Actual:: " + sUIMedPolicy
					+ " MidRule  " + sMidRule, sUIMedPolicy.trim().equalsIgnoreCase(sDBMedPolicy.trim()));
			verify("UI  Topic value Matched with DB " + "Expected::  " + sDBTopicValue + " Actual:: " + sUITopicValue
					+ " MidRule  " + sMidRule,
					removeWhiteSpaces(sUITopicValue).equalsIgnoreCase(removeWhiteSpaces(sDBTopicValue)));
			verify("UI DP value Matched with DB " + "Expected::  " + sDBDPValue + " Actual:: " + sUIDPValue
					+ " MidRule  " + sMidRule, sUIDPValue.trim().equalsIgnoreCase(sDBDPValue.trim()));

			verify("UI Task value Matched with DB " + "Expected::  " + sDBTaskValue + " Actual:: " + sUITaskValue
					+ " MidRule  " + sMidRule, sUITaskValue.trim().equalsIgnoreCase(sDBTaskValue.trim()));
			verify("UI Task Status  Matched with DB " + "Expected::  " + sDBTaskStatus + " Actual:: " + sUITaskStatus
					+ " MidRule  " + sMidRule, sUITaskStatus.trim().equalsIgnoreCase(sDBTaskStatus.trim()));
			if (!(sReviewType.equalsIgnoreCase("ManualRMRReport"))) {
				verify("UI Assign value Matched with DB " + "Expected::  " + sDBAssign + " Actual:: " + sUIAssign
						+ " MidRule  " + sMidRule, sUIAssign.trim().equalsIgnoreCase(sDBAssign.trim()));
			}
			if (sReviewType.equalsIgnoreCase("ManualRMRReport")) {
				verify("UI RMR Code is Matched with DB " + "Expected::  " + sDBRMRCode + " Actual:: " + sUIRMRValue
						+ " MidRule  " + sMidRule, sUIRMRValue.trim().equalsIgnoreCase(sDBRMRCode.trim()));
			}
		}

	}

	public static String removeWhiteSpaces(String sText) {

		sText = sText.replaceAll("\\s", "");
		return sText;

	}

	public void validateSelectedReport(String sReportType) throws InterruptedException {

		verify("Generate Report button is Clicked", oWorkQueuePage.clickGenerateReport());

		ArrayList<String> sMidRuleList = new ArrayList<String>();

		List<WebElement> iMidRule = getDriver()
				.findElements(By.xpath("//table[@class='ui-table-scrollable-body-table']//tr//td[2]"));
		List<WebElement> iMidVersion = getDriver()
				.findElements(By.xpath("//table[@class='ui-table-scrollable-body-table']//tr//td[3]"));

		for (int i = 0; i < iMidRule.size(); i++) {
			String MidRuleVersion = iMidRule.get(i).getText() + "." + iMidVersion.get(i).getText();
			// System.out.println("I Rule "+MidRuleVersion);
			sMidRuleList.add(MidRuleVersion);
		}

		ArrayList<String> sDBRuleList = DBUtils.db_GetAllColumnValues(
				DBQueries.sQueryGetAllRulesonTaskStatus("MID_RULE_DOT_VERSION",
						Serenity.sessionVariableCalled("IUInstanceName").toString(), "Manual RMR"),
				"MID_RULE_DOT_VERSION");

		Collections.sort(sDBRuleList);
		Collections.sort(sMidRuleList);

		verify("List of Rules in DB " + sDBRuleList, true);
		verify("List of Rules from IU Report " + sMidRuleList, true);
		verify("Rules compare is equal  " + sMidRuleList, sDBRuleList.equals(sMidRuleList));

	}

	public void selectReportCategeoryType(String sSelectReportType, String sRunType, String sSearchRule, String arg4)
			throws InterruptedException {

		Serenity.setSessionVariable("Run Type").to(sRunType);

		// oGenericUtils.gfn_Click_String_object_Xpath("//span[contains(text(),'Run')]//a");

		// selectValuesDropDown("Select Report",sSelectReportType);

		// selectValuesDropDown("Run Type",sRunType);

		// selectValuesDropDown(StringUtils.substringBefore(sSearchRule,
		// "-"),StringUtils.substringAfter(sSearchRule, "-"));
		//
		// verify("Generate Report button is clicked",
		// oGenericUtils.gfn_Click_On_Object("button", "Generate Report"));
		//
		// SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		switch (sSelectReportType) {
		case "User Name Task Report":
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			// oGenericUtils.gfn_Click_String_object_Xpath("(//label[text()='   Run
			// Type']//following::ng-multiselect-dropdown)[1]");
			oGenericUtils.gfn_Click_String_object_Xpath("//a[text()='x']");
			oGenericUtils.gfn_Click_String_object_Xpath("//span[text()='Select']");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			verify("Selected Run Type ", oWorkQueuePage.selectText(sRunType));

			oGenericUtils.gfn_Click_String_object_Xpath("//span[text()='Select User']");
			verify("Selected Policy Owner ", oWorkQueuePage.selectText(sSearchRule));

			break;
		case "Scrub Report":

			oGenericUtils.gfn_Click_String_object_Xpath(
					"//label[text()='Policy Name']/parent::td/..//td[2]//span[text()='Select']");

			verify("Selected CMS Coverage Policies ", oWorkQueuePage.selectText(sSearchRule));
			Serenity.setSessionVariable("PolicyName").to(sSearchRule);
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Click_String_object_Xpath(
					"//label[contains(text(),'Scrub Reviewer')]/following::td//span[text()='Select']");
			verify("Selected Policy Owner ", oWorkQueuePage.selectText(arg4));
			Serenity.setSessionVariable("User").to(arg4);

			break;

		}
		WebElement e = getDriver().findElement(By.xpath("//button[text()='Generate Report']"));
		(new Actions(getDriver())).moveToElement(e).click().build().perform();
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

	}

	private void selectValuesDropDown(String sFilter, String sValue) throws InterruptedException {

		String sXpath = null;

		if (sFilter.equalsIgnoreCase("Select Report") || sFilter.equalsIgnoreCase("User Name")) {

			// sXpath="//label[contains(text(),'"+sFilter+"')]/../following-sibling::td//span[@class='dropdown-btn']";
			sXpath = "//*[contains(text(),'" + sFilter + "')]";

		} else {

			// sXpath="//label[contains(text(),'"+sFilter+"')]/../../following-sibling::td//span[@class='dropdown-btn']";
			sXpath = "//*[contains(text(),'" + sFilter + "')]";
		}

		oGenericUtils.gfn_Click_String_object_Xpath(sXpath);

		oGenericUtils.gfn_Click_On_Object("div", sValue.trim());

		oGenericUtils.gfn_Click_String_object_Xpath(sXpath);

	}

	@Step
	public void validate_Reports(String arg1) throws Exception {

		switch (arg1) {
		case "Drug and Biological Policy":
		case "Healthplan Policy":

			String sScrubReview = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 1));
			String sPolicyName = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 2));
			String sPreScrubLibraryCount = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 3));
			String sPostScrubLibraryCount = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 4));
			String sPreScrubCustomCount = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 5));
			String sPostScrubCustomCount = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 6));
			String sRunType = oSeleniumUtils
					.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 7));

			verify("Scrub Reviwer is Shown Properly " + "Expected: "
					+ Serenity.sessionVariableCalled("PolicyName").toString() + " Actual: " + sPolicyName,
					Serenity.sessionVariableCalled("PolicyName").toString().trim()
							.equalsIgnoreCase(sPolicyName.trim()));
			verify("Scrub Reviewer is Shown Properly " + "Expected: "
					+ Serenity.sessionVariableCalled("User").toString().trim() + " Actual: " + sScrubReview,
					Serenity.sessionVariableCalled("User").toString().trim().equalsIgnoreCase(sScrubReview.trim()));

			verify("Run Type is Shown Properly " + "Expected: " + Serenity.sessionVariableCalled("Run Type").toString()
					+ " Actual: " + sRunType,
					Serenity.sessionVariableCalled("Run Type").toString().trim().equalsIgnoreCase(sRunType.trim()));

			if (arg1.equalsIgnoreCase("Drug and Biological Policy")) {
				verify("Pre Scrub Custom Count is Shown Properly " + "Expected: "
						+ ProjectVariables.DrugPolicyPreScrubCount + " Actual: " + sPreScrubCustomCount,
						ProjectVariables.DrugPolicyPreScrubCount.equalsIgnoreCase(sPreScrubCustomCount));

				verify("Pre Scrub Custom Count is Shown Properly " + "Expected: "
						+ ProjectVariables.DrugPolicyPostScrubCount + " Actual: " + sPostScrubCustomCount,
						ProjectVariables.DrugPolicyPostScrubCount.equalsIgnoreCase(sPostScrubCustomCount.trim()));
			}

			if (arg1.equalsIgnoreCase("Healthplan Policy")) {
				verify("Pre Scrub Custom Count is Shown Properly " + "Expected: "
						+ ProjectVariables.HealthPolicyPreScrubCount + " Actual: " + sPreScrubCustomCount,
						ProjectVariables.HealthPolicyPreScrubCount.equalsIgnoreCase(sPreScrubCustomCount));

				verify("Pre Scrub Custom Count is Shown Properly " + "Expected: "
						+ ProjectVariables.HealthPolicyPostScrubCount + " Actual: " + sPostScrubCustomCount,
						ProjectVariables.HealthPolicyPostScrubCount.equalsIgnoreCase(sPostScrubCustomCount.trim()));
			}

			break;
		case "Username Search Report":

			ArrayList<Float> sRules = new ArrayList<Float>();

			ArrayList<String> sUserList = new ArrayList<String>();

			oGenericUtils.gfn_Click_String_object_Xpath(
					"//span[@class='ui-dropdown-trigger-icon ui-clickable pi pi-chevron-down']");

			// oGenericUtils.gfn_Click_String_object_Xpath("(//p-dropdownitem//li)[position()=last()]");

			List<WebElement> oRules = getDriver()
					.findElements(By.xpath("((//tbody[@class='ui-table-tbody'])[2]//tr//td)"));

			String sTypeFlag = null;

			if (Serenity.sessionVariableCalled("Run Type").toString().equalsIgnoreCase("Initial Run")) {

				sTypeFlag = "1";
			} else {

				sTypeFlag = "2";
			}

			String sb = "SELECT\r\n" + "       IRD.MID_RULE_DOT_VERSION,\r\n" + "           U.USER_NAME USER_NAME,\r\n"
					+ "                TSL.TASK_STATUS_DESC,\r\n" + "                IM.IMPACT_DESC,\r\n"
					+ "                TT.TASK_TYPE_DESC\r\n" + "         \r\n"
					+ "        FROM IRDM.UPDATE_INSTANCES UI,\r\n" + "       IRDM.INTERP_IMPACTS IM,\r\n"
					+ "       IRDM.INTERP_RULES IR,\r\n" + "       IRDM.INTERP_RULE_DETAILS IRD,\r\n"
					+ "   IPDE.USERS U,\r\n" + "   IPDE.TASK_DETAILS TD,\r\n" + "   IPDE.TASK_TYPE_LKP TT,\r\n"
					+ "   IPDE.TASK_STATUS_LKP tsl\r\n" + "      \r\n"
					+ "WHERE     UI.UPDATE_INSTANCE_KEY = IM.UPDATE_INSTANCE_KEY\r\n"
					+ "       AND IR.IMPACT_KEY = IM.IMPACT_KEY\r\n"
					+ "       AND IR.INTERP_RULE_KEY = IRD.INTERP_RULE_KEY\r\n"
					+ "       and IRD.INTERP_RULE_KEY = TD.REFERENCE_RULE_ID\r\n"
					+ "     and U.USER_KEY = TD.TASK_USER_KEY\r\n" + "     and TT.TASK_TYPE_KEY = TD.TASK_TYPE_KEY\r\n"
					+ "     and TSL.TASK_STATUS_KEY = TD.TASK_STATUS_KEY\r\n"
					+ "       AND UI.UPDATE_INSTANCE_NAME = 'TestAutoIV174'\r\n" + "       and IR.CANDIDATE_10 = -1\r\n"
					+ "       and impact_seq= " + sTypeFlag + "\r\n";

			for (int j = 3, k = 4, l = 1; j < oRules.size(); j++, k++, l++) {

				oSeleniumUtils.get_TextFrom_Locator("((//tbody[@class='ui-table-tbody'])[2]//tr//td)[" + (j) + "]");

				String strRule = oSeleniumUtils
						.get_TextFrom_Locator("((//tbody[@class='ui-table-tbody'])[2]//tr//td)[" + (j) + "]") + ".";

				String strRuleVersion = oSeleniumUtils
						.get_TextFrom_Locator("((//tbody[@class='ui-table-tbody'])[2]//tr//td)[" + (k) + "]");

				String sUsers = oSeleniumUtils
						.get_TextFrom_Locator("((//tbody[@class='ui-table-tbody'])[2]//tr//td)[" + (l) + "]");

				String sVal = strRule + strRuleVersion;

				float iVal = Float.parseFloat(sVal);

				sRules.add(iVal);
				sUserList.add(sUsers);

				j = j + 9;
				k = k + 9;
				l = l + 9;

			}

			ArrayList<String> olist = DBUtils.db_GetAllColumnValues(sb, "MID_RULE_DOT_VERSION");

			ArrayList<String> oDBUserlist = DBUtils.db_GetAllColumnValues(sb, "USER_NAME");

			List<Float> sortedUIList = sRules.stream().sorted().collect(Collectors.toList());

			verify("UI RULES" + sortedUIList, true);

			verify("DB RULES" + olist, true);

			verify("DB USER NAME list" + oDBUserlist, true);

			verify("UI USER NAME list" + sUserList, true);

			for (int j = 0; j < olist.size(); j++) {

				olist.get(j).equalsIgnoreCase(sortedUIList.get(j).toString());

			}

			verify("Reset Button is clicked", oGenericUtils.gfn_Click_On_Object("button", "Reset"));

			break;
		case "Rule History Report":
//			validateRuleHistoryReport("Final PO Review","Not Started","Assigned to Final PO");
			
			break;
		}

	}

	@Step
	public void validatePolicyWiseReport(String arg1, String arg2) {

		String sScrubReview = oSeleniumUtils
				.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 1));
		String sPolicyName = oSeleniumUtils.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 2));
		String sPreScrubLibraryCount = oSeleniumUtils
				.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 3));
		String sPostScrubLibraryCount = oSeleniumUtils
				.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 4));
		String sPreScrubCustomCount = oSeleniumUtils
				.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 5));
		String sPostScrubCustomCount = oSeleniumUtils
				.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 6));
		String sRunType = oSeleniumUtils.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 7));

	}

	@Step
	public void validateWorkProgressReport(String arg1, String arg2, String arg3) throws InterruptedException {

		String sStageFlag = "";
		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
		oGenericUtils.gfn_Click_On_Object("button", "Refresh");
		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

		switch (arg1) {
		case "CUSTOMRULE":
			if (!(arg3.equalsIgnoreCase("Validate"))) {// pending
				// Retrive PreRule Review Value
				if (arg2.equalsIgnoreCase("RULEREVIEW")) {
					Serenity.setSessionVariable("vCustPendingValue").to(oSeleniumUtils
							.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("WORKPROGRESS RULEREVIEW", 4)));
					Serenity.setSessionVariable("vCustCompletedValue").to(oSeleniumUtils
							.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("WORKPROGRESS RULEREVIEW", 8)));

				} else {
					Serenity.setSessionVariable("vCustPendingValue").to(oSeleniumUtils.get_TextFrom_Locator(
							oCommonPage.GetDynamicXPathWithCase("WORKPROGRESS ROLE", " " + arg2 + " ", 5, 0)));
					Serenity.setSessionVariable("vCustCompletedValue").to(oSeleniumUtils.get_TextFrom_Locator(
							oCommonPage.GetDynamicXPathWithCase("WORKPROGRESS ROLE", " " + arg2 + " ", 9, 0)));

				}
			}
			if (arg3.equalsIgnoreCase("Validate") && arg2.equalsIgnoreCase("RULEREVIEW")) {
				Serenity.setSessionVariable("vPostCustPendingValue").to(
						oSeleniumUtils.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("WORKPROGRESS RULEREVIEW", 4)));
				Serenity.setSessionVariable("vPostCustCompletedValue").to(
						oSeleniumUtils.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("WORKPROGRESS RULEREVIEW", 8)));
			} else {
				sStageFlag = "II";
			}
			if (arg3.equalsIgnoreCase("Validate") && sStageFlag.equalsIgnoreCase("II")) {
				Serenity.setSessionVariable("vPostCustPendingValue").to(oSeleniumUtils.get_TextFrom_Locator(
						oCommonPage.GetDynamicXPathWithCase("WORKPROGRESS ROLE", " " + arg2 + " ", 5, 0)));
				Serenity.setSessionVariable("vPostCustCompletedValue").to(oSeleniumUtils.get_TextFrom_Locator(
						oCommonPage.GetDynamicXPathWithCase("WORKPROGRESS ROLE", " " + arg2 + " ", 9, 0)));
			}
			break;

		default:
			if (!(arg3.equalsIgnoreCase("Validate"))) {
				// Retrive PreRule Review Value
				if (arg2.equalsIgnoreCase("RULEREVIEW")) {
					Serenity.setSessionVariable("vCustPendingValue").to(oSeleniumUtils
							.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("WORKPROGRESS RULEREVIEW", 2)));
					Serenity.setSessionVariable("vCustCompletedValue").to(oSeleniumUtils
							.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("WORKPROGRESS RULEREVIEW", 6)));

				} else {
					Serenity.setSessionVariable("vCustPendingValue").to(oSeleniumUtils.get_TextFrom_Locator(
							oCommonPage.GetDynamicXPathWithCase("WORKPROGRESS ROLE", " " + arg2 + " ", 3, 0)));
					Serenity.setSessionVariable("vCustCompletedValue").to(oSeleniumUtils.get_TextFrom_Locator(
							oCommonPage.GetDynamicXPathWithCase("WORKPROGRESS ROLE", " " + arg2 + " ", 7, 0)));

				}
			}
			if (arg3.equalsIgnoreCase("Validate") && arg2.equalsIgnoreCase("RULEREVIEW")) {
				Serenity.setSessionVariable("vPostCustPendingValue").to(
						oSeleniumUtils.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("WORKPROGRESS RULEREVIEW", 2)));
				Serenity.setSessionVariable("vPostCustCompletedValue").to(
						oSeleniumUtils.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("WORKPROGRESS RULEREVIEW", 6)));
			} else {
				sStageFlag = "II";
			}
			if (arg3.equalsIgnoreCase("Validate") && sStageFlag.equalsIgnoreCase("II")) {
				Serenity.setSessionVariable("vPostCustPendingValue").to(oSeleniumUtils.get_TextFrom_Locator(
						oCommonPage.GetDynamicXPathWithCase("WORKPROGRESS ROLE", " " + arg2 + " ", 3, 0)));
				Serenity.setSessionVariable("vPostCustCompletedValue").to(oSeleniumUtils.get_TextFrom_Locator(
						oCommonPage.GetDynamicXPathWithCase("WORKPROGRESS ROLE", " " + arg2 + " ", 7, 0)));
			}

			break;
		}

		if (arg3.equalsIgnoreCase("Validate")) {

			String vPreCustPendingValue = Serenity.sessionVariableCalled("vCustPendingValue").toString();

			String vPreCompletePendingValue = Serenity.sessionVariableCalled("vCustCompletedValue").toString();

			int iPostCustPendingValue = Integer
					.parseInt(Serenity.sessionVariableCalled("vPostCustPendingValue").toString());
			int iPostCompletePendingValue = Integer
					.parseInt(Serenity.sessionVariableCalled("vPostCustCompletedValue").toString());

			String vPostCustPendingValue = Integer.toString(iPostCustPendingValue + 1);
			String vPostCompletePendingValue = Integer.toString(iPostCompletePendingValue - 1);

			System.out.println(vPostCustPendingValue);
			System.out.println(vPostCompletePendingValue);

			verify("Rule deducted from Pending Section of  " + arg2 + " Pre Orders Count: " + vPreCustPendingValue
					+ " Post Orders Count " + vPostCustPendingValue,
					vPreCustPendingValue.equalsIgnoreCase(vPostCustPendingValue));
			verify("Rule deducted from Completed Secion of " + arg2 + " Pre Orders Count: " + vPreCompletePendingValue
					+ " Post Orders Count " + vPostCompletePendingValue,
					vPreCompletePendingValue.equalsIgnoreCase(vPostCompletePendingValue));

		}

	}

	@Step
	public void validateSelectedDropdownValuesUsingDB(String sColumn, String sReview, String sSearch)
			throws InterruptedException {

		String sInstance = Serenity.sessionVariableCalled("InstanceName").toString();
		String sQueryCase = "";
		String sDBQuery = DBQueries.queryForDropdownList(sQueryCase, sInstance, sReview, sColumn);
		ArrayList<String> dropdownList = DBUtils.executeSQLQueryMultipleRows(sDBQuery);

		WebElement e = getDriver().findElement(By.xpath("//mat-label[text()='" + sSearch + "']/../../label"));

		(new Actions(getDriver())).moveToElement(e).click().build().perform();
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		if (sColumn.equals("UGL.UPDATE_GROUP_NAME")) {
			if (dropdownList.get(0).equalsIgnoreCase("SIM")) {
				oGenericUtils.gfn_Click_String_object_Xpath("//span[text()='Similar']/../mat-pseudo-checkbox");
			}
			if (dropdownList.get(0).equalsIgnoreCase("REV")) {
				oGenericUtils.gfn_Click_String_object_Xpath("//span[text()='Revised']/../mat-pseudo-checkbox");
			}

		} else {
			oGenericUtils.gfn_Click_String_object_Xpath(
					"//span[text()='" + dropdownList.get(0).trim() + "']/../mat-pseudo-checkbox");
		}
		System.out.println(dropdownList.get(0));
		Serenity.setSessionVariable(sColumn).to(dropdownList.get(0).trim());

		String xpath1 = "//button[text()='ApplyFilter']";
		WebElement el = getDriver().findElement(By.xpath(xpath1));
		(new Actions(getDriver())).moveToElement(el).click().build().perform();
		oGenericUtils.gfn_Click_On_Object("button", "ApplyFilter");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		System.out.println("Title" + Serenity.sessionVariableCalled(sColumn).toString());
		int iUICount = 0;
		if (sColumn.equals("UGL.UPDATE_GROUP_NAME")) {
			iUICount = oSeleniumUtils.get_Matching_WebElement_count("//td/..//span[text()='Initial Run']");
		} else {
			iUICount = oSeleniumUtils
					.get_Matching_WebElement_count("//td/..//span[text()='" + dropdownList.get(0).trim() + "']");
		}
		System.out.println("int count " + iUICount);
		String sqlQuery = DBQueries.sQueryCountMedTopic(sColumn, sInstance,
				Serenity.sessionVariableCalled(sColumn).toString());
		System.out.println("Q1 :" + sqlQuery);
		String sText = DBUtils.executeSQLQuery(sqlQuery);
		System.out.println("vALUE " + sText);

		verify("Record Matched with Database" + " UI Count:: " + iUICount + "  DB Count:: " + sText,
				String.valueOf(iUICount).equals(sText));

		oGenericUtils.gfn_Click_On_Object("button", " Reset");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		String sButtonName = null;
		switch (sColumn) {
		case "IRD.MED_POL_TITLE":
			sButtonName = "Medical Policy";
			break;
		case "IRD.TOPIC_TITLE":
			sButtonName = "Topic";
			break;
		case "IRD.DP_DESC":
			sButtonName = "Decision";
			break;
		case "UGL.UPDATE_GROUP_NAME":
			sButtonName = "Proposal Type";
			break;
		}

		boolean bln = !oSeleniumUtils.is_WebElement_Displayed("//div[text()='" + sButtonName + "']");

		verify("After clicking on Reset filter " + sButtonName + " is not displaying" + sButtonName, bln);

	}
	
	@Step
	public void validateButton(String sButtonName){
		
//		Assert.assertFalse("Apply filter button is not disabled", oHomePage.ApplyFilterButton.isCurrentlyEnabled());
				verify("Generate Change log button is enabled ",oCommonPage.btnGenerateChangeLog.isCurrentlyEnabled());
		
		
	}
	
	@Step
	public void validateRuleHistoryReport(String sTaskType, String sTaskStatus, String sReportColumnStatus, String arg4) {
		int sColNum =0;
		String sDBAssignedQuery ="";
		oSeleniumUtils.Enter_given_Text_Element("(//table[@class='ui-table-scrollable-header-table']//input[@class='ng-star-inserted'])[3]", Serenity.sessionVariableCalled("MidRuleVersion").toString());
		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
		
//		String sRunType = oSeleniumUtils
//				.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 1));
//		String sMidRuleVersion = oSeleniumUtils.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 3));
//		String sCurrentTask = oSeleniumUtils
//				.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 6));
//		String sCurrentTaskStatus = oSeleniumUtils
//				.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 7));
				
		switch (sReportColumnStatus) {
		case "Assigned to Final PO":
			sColNum=11;
			break;
		case "Final PO Review Completed":
			sColNum=13;
			break;
		case "Assigned to CPM":
			sColNum=14;
			break;
		case "CPM Review Completed":
			sColNum=16;
			break;
		case "Assigned to Editorial":
			sColNum=17;
			break;
		case "Editorials Completed":
			sColNum=19;
			break;
		case "Assigned to QA":
			sColNum=23;
			break;
		case "QA Review Completed":
			sColNum=25;
			break;
		case "Assigned to Config":
			sColNum=26;
			break;
		case "Config Review Completed":
			sColNum=28;
			break;
		case "Assigned to Testing":
			sColNum=29;
			break;
		case "Testing Review Completed":
			sColNum=31;
			break;
		}
		
		String sAssignedDate = oSeleniumUtils
				.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", sColNum));
		
		
		
		System.out.println("appdate " +sAssignedDate);
		String sFinalPO = oSeleniumUtils
				.get_TextFrom_Locator(oCommonPage.GetDynamicXPath("POLICYREPORT COLUMN", 12));
		
		
		
//		String sqlQuery = DBQueries.sQueryRuleHistory("MID_RULE_DOT_VERSION",sTaskType,sTaskStatus,Serenity.sessionVariableCalled("IUInstanceName").toString());
//		System.out.println("Q1 :" + sqlQuery);
//		String sText = DBUtils.executeSQLQuery(sqlQuery);
//		System.out.println("vALUE " + sText);
		
		if(sTaskType.equalsIgnoreCase("CPM Review")){
			sDBAssignedQuery = DBQueries.sQueryCPMRuleHistory("Tdh.HIST_END",sTaskType,sTaskStatus,Serenity.sessionVariableCalled("IUInstanceName").toString());
		}else {
			sDBAssignedQuery = DBQueries.sQueryRuleHistory("Tdh.HIST_END",sTaskType,sTaskStatus,Serenity.sessionVariableCalled("IUInstanceName").toString());	
		}
			
			System.out.println("DB query " +sDBAssignedQuery);
	//		String sDBAssignedDate = DBUtils.executeSQLQuery(sDBAssignedQuery);
			ArrayList<String> dbRowValue = DBUtils.executeSQLQueryMultipleRows(sDBAssignedQuery);
			System.out.println("One"+dbRowValue.get(0));
	//		System.out.println("Two "+dbRowValue.get(1));
			
			System.out.println("****LApp date " +GenericUtils.Get_Required_Date_For_Given_String(sAssignedDate));
			System.out.println("****LDB date " +GenericUtils.getRequiredDateForGivenString(dbRowValue.get(0)));
					
			boolean bCurrentDosToStatus = GenericUtils.getRequiredDateForGivenString(dbRowValue.get(0))
					.equalsIgnoreCase(GenericUtils.Get_Required_Date_For_Given_String(sAssignedDate));
			verify("Current DOS From is dispalyed properly " + "DBValue:: " + GenericUtils.Get_Required_Date_For_Given_String(sAssignedDate),
					bCurrentDosToStatus);
		
		
		
	}
	
	
	@Step
	public void enterCPMBulkDecisionAndValidate(String sDecisionType, String sProposalList, String sDecisionList) throws InterruptedException {

		
		String arrProp[] = { "SIM", "DEL" };
		String arrDecision[] = { "No Decision", "Remove" };
		String sArrayPropCode = null;
		String sMsgType="";

		for (int i = 0; i < arrProp.length; i++) {
			sArrayPropCode = arrProp[i];
			String sArrayDecisionCode = arrDecision[i];
			enterCPMBulkDecision(sArrayPropCode, sArrayDecisionCode,"NoRational");
			GenericUtils.Verify("Entered Bulk Decision Properly ", true);
			
		}
		
		String arrProp2[] = { "DEL"};
		String arrDecision2[] = { "Modify"};
		String sArrayPropCode2 = null;
		

		for (int i = 0; i < arrProp2.length; i++) {
			sArrayPropCode = arrProp2[i];
			String sArrayDecisionCode = arrDecision2[i];
			enterCPMBulkDecision(sArrayPropCode, sArrayDecisionCode,"NoRational");
			GenericUtils.Verify("Entered Bulk Decision Properly ", true);
			
		}
		
		String arrProp3[] = { "SIM", "DEL" };
		String arrDecision3[] = { "No Decision", "Remove" };
		String sArrayPropCode3 = null;
		

		for (int i = 0; i < arrProp.length; i++) {
			sArrayPropCode = arrProp[i];
			String sArrayDecisionCode = arrDecision[i];
			enterCPMBulkDecision(sArrayPropCode, sArrayDecisionCode,"Sucess");
			GenericUtils.Verify("Entered Bulk Decision Properly ", true);
			
		}


	}
	
	public void enterCPMBulkDecision(String sProposalType, String sDecisionType,String sMsgType) throws InterruptedException {

		oGenericUtils.gfn_Click_String_object_Xpath(oWorkQueuePage.BulkDecisionBtn);
		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
		oGenericUtils.gfn_Click_String_object_Xpath(oWorkQueuePage.BulkDecisionText);
		oSeleniumUtils.select_Given_Value_From_DropDown(
				getDriver().findElement(By.xpath("(//b[text()='Proposal Type']/../../../div//select)[1]")), sProposalType);
		if (sProposalType.trim().equals("SIM"))
			oSeleniumUtils.select_Given_Value_From_DropDown(
					getDriver().findElement(By.xpath(
							"//b[text()='Decision']/..//..//select[@class='ng-untouched ng-pristine ng-valid']")),
					sDecisionType);
		else
			oSeleniumUtils.select_Given_Value_From_DropDown(
					getDriver().findElement(By.xpath("//b[text()='Decision']/..//..//select")), sDecisionType);
		
		Serenity.setSessionVariable("SystemBulkDecision").to(sDecisionType);
		if(sMsgType.equalsIgnoreCase("NoRational")) {
			oSeleniumUtils.Enter_given_Text_Element(oWorkQueuePage.BulkDecisionComments, "");
		}else {
		oSeleniumUtils.Enter_given_Text_Element(oWorkQueuePage.BulkDecisionComments, "Text" + java.time.LocalTime.now());
		}
		oSeleniumUtils.Click_given_Locator(oWorkQueuePage.SelAllProposalChk);
		oSeleniumUtils.Click_given_Locator(oWorkQueuePage.EnterInBulkDecision);
		
		switch (sMsgType) {
		case "NoRational":
			
			verify("Rational comments cannot be blank is displayed",
					oSeleniumUtils.is_WebElement_Displayed("//div[text()='Rational comments cannot be blank']"));
		
			break;
			
		case "NoModify":
			
			verify("Modify Decision is not available for the Review code')] is displayed",
					oSeleniumUtils.is_WebElement_Displayed("//b[contains(text(),'Modify Decision is not available for the Review code')]"));
		
			break;
			
		case "Sucess":
			
			verify("Modify Decision is not available for the Review code')] is displayed",
					oSeleniumUtils.is_WebElement_Displayed("//div[contains(text(),'Successfully applied Bulk Decision for')]"));
		
			break;
			
			
	
		}

		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		oSeleniumUtils.Click_given_Locator(oWorkQueuePage.SelAllProposalChk);
	
	

	}

	
	

}
