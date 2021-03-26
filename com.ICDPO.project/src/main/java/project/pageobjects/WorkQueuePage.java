package project.pageobjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import freemarker.template.utility.StringUtil;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;

import project.utilities.DBQueries;
import project.utilities.DBUtils;
import project.utilities.GenericUtils;
import project.utilities.ProjectVariables;
import project.utilities.SeleniumUtils;
import project.pageobjects.CommonPage;

//trinath..
public class WorkQueuePage extends PageObject {

	SeleniumUtils oSeleniumUtils;
	GenericUtils oGenericUtils;
	CommonPage oCommonPage;
	AdminPage oAdminPage;
	DBUtils oDBUtils;

	public String ProposalType = "//th[@ng-reflect-ng-switch='proposalType']//input";
	public String BulkDecisionBtn = "//button[text()='Bulk Decision']";
	public String BulkDecisionText = "//h4[text()='Bulk Decision']";
	public String ProposalTypeBox = "(//select[@class='ng-pristine ng-valid ng-touched']//option)[3]";
	public String DecisionBox = "//b[text()='Decision']/..//..//select[@class='ng-untouched ng-pristine ng-valid']//option";
	public String BulkDecisionComments = "//textarea[@id='bulkDecisionRationalCommentTxtId']";
	public String SelAllProposalChk = "//input[@class='modalCheckBox ng-untouched ng-pristine ng-valid']";
	public String EnterInBulkDecision = "//button[text()='Enter']";
	public String CloseBulkDecision = "//i[@class='fa fa-close']";
	public String chkResolved = "//label[text()='Resolved Description']/../..//input[@type='checkbox']";
	public String txtClaimTypes = "//label[text()='Claim Types:']/../..//input";
	public String txtTask = "//label[text()='Task:']/../..//input";
	public String txtNotes = "//label[text()='Notes:']/../..//textarea";
	public String txtReference = "//label[text()='Reference:']/../..//textarea";
	public String txtDesc = "//label[text()='Description:']/../..//textarea";
	public String rdoStartNew = "//input[@value='startNew']";
	public String rdoCopyAnother = "//input[@value='copyAnother']";
	public String chkSelect = "//h5[text()=' Manual Proposals ']/../../..//div/div/div/p-table/div/div/table/thead/tr/th[1]/p-tableheadercheckbox/div/div[2]";
	public String ddlInstance = "//h5[text()=' Manual Proposals ']/../../..//div/div/div/p-table/div/div/table/tbody/tr/td[2]/select[not(@disabled)]";
	public String ddlDecision = "//h5[text()=' Manual Proposals ']/../../..//div/div/div/p-table/div/div/table/tbody/tr/td[3]/select[not(@disabled)]";
	public String txtComments = "//h5[text()=' Manual Proposals ']/../../..//div/div/div/p-table/div/div/table/tbody/tr/td[4]/textarea[not(@disabled)]";
	public String txtCode = "//h5[text()=' Manual Proposals ']/../../..//div/div/div/p-table/div/div/table/tbody/tr/td[6]/input[not(@disabled)]";
	public String ddlCategory = "//h5[text()=' Manual Proposals ']/../../..//div/div/div/p-table/div/div/table/tbody/tr/td[7]/select[not(@disabled)]";
	public String ddlDcat = "//h5[text()=' Manual Proposals ']/../../..//div/div/div/p-table/div/div/table/tbody/tr/td[11]/select[not(@disabled)]";
	public String btnDisabled = "//button[text()='Delete Selected'][(@disabled)]";
	public String btnSave = "//button[text()='Add Another Code']/../..//button[text()='Save']";
	public String chkSystemprp = "(//h5[text()=' System Proposals ']/../../..//div/div/div/p-table/div/div/table/tbody/tr/td[1]/../..//div/div[2])[1]";
	public String RetireRuleMDDecision = "//select[@id='ddlMDDecision']";
	public String RetireRuleMDComments = "//div[text()='PO Comments:']/..//div//textarea";
	public String AuthorizationWarning = "//div[contains(text(),'Selecting Authorize Decisions completes the ')]";
	public String txtRational = "//*[@id='cdk-accordion-child-5']/div/div/p-table/div/div/table/thead/tr[2]/th[3]/input";
	public String InterpretiveRuleUpdate = "//span[@class='tab-close ng-star-inserted']";
	public String ClaimTask = "//button//i[@class='fa fa-refresh']";
	public String MyTaskICD = "//span[@class='ui-tabview-title ng-star-inserted' and text()='My Tasks']";
	// public String ConflictNotes ="(//div[contains(@class,'ag-row ag-row-even
	// ag-row-level-0 ag-row-position-absolute ag-row-first ag-row-last
	// ag-row-focus')]//div[@col-id='conflictNotes'])[1]";
	public String ConflictNotes = "(//div[@role='gridcell' and @col-id='conflictNotes'])[1]";
	public String CurrentAgeFilter = "//div[text()='sValue']/parent::div//div[3]//select[contains(@class,'selectpicker form-control show-tick ng-')]";
	public String NewAgeFilter = "//div[text()='sValue']/parent::div//div[2]//select[contains(@class,'selectpicker form-control show-tick ng-')]";
	public String NewMAXAgeFilter = "//div[text()='sValue']/parent::div//div[4]//select[contains(@class,'selectpicker form-control show-tick ng-')]";
	public String txtEditorial = "//div//label[text()='arg']/../..//textarea";
	public String mrqText = "//label[text()='arg']/../..//ins";
	public String EditoialButton = "//div//label[text()='arg1']/../..//button[text()='arg2']";
	public String closeEditorial = "//h4[text()='Editorial Update']/../..//i[@class='fa fa-close']";
	public String decisionSummaryTab = "//h5[text()=' Decision Summary ']";
	public String sysSummaryTab = "//h5[text()=' System Proposals ']";
	public String ManualSummaryTab = "//h5[text()=' Manual Proposals ']";
	public String sysDecision = "//h5[text()=' System Proposals ']/../../..//div//div/div[2]/p-table/div/div[1]/table/tbody/tr/td[2]/select";
	public String sysComments = "//h5[text()=' System Proposals ']/../../..//div//div/div[2]/p-table/div/div[1]/table/tbody/tr/td[3]/textarea";
	public String sysPraposal = "//h5[text()=' System Proposals ']/../../..//div//div/div[2]/p-table/div/div[1]/table/tbody/tr/td[8]";
	private String sBuilderARD_QA_URL;
	public String ReturnRuleInDialog = "//mat-dialog-actions[@class='mat-dialog-actions']//button[text()='Return Rule']";
	public String ReturnRuleCommnets = "//textarea[@id='ruleReturnComments']";

	@FindBy(xpath = "//label[text()='User Name :']/../..//input[@type='text']")
	WebElementFacade BuilderARD_UserName;

	@FindBy(xpath = "//label[text()='Password :']/../..//input[@type='password']")
	WebElementFacade BuilderARD_Password;

	@FindBy(xpath = "//button[text()='Sign In']")
	public WebElementFacade SignIn_Btn;

	public String EditorialComments = "//a[text()='arg']/../../../..//td/textarea";
	public String EditorialReviewStatus = "//a[text()='arg']/../../../..//td[@ng-reflect-ng-switch='taskStatusDesc']/span";
	public String btnCompleteDecision = "//h5[text()=' arg ']/../../..//button[text()=' Complete Editorial Review ']";
	public String btnCompleteQA = "//h5[text()=' arg ']/../../..//button[text()=' Complete QA Review ']";
	public String btnConfigSumm = "//h5[text()=' Configurations Summary ']/../../..//button[text()=' Complete Editorial Review ']";
	public String lblConfigSummary = "//h5[text()=' Configurations Summary ']";
	public String txtCnfgSummary = "//label[text()='Additional Config Instructions: ']/../../div//textarea";
	public String btnCnfgSummary = "//input[@value='Save']";
	public String btnCPMRetire = "//button[text()='Retire Rule' and not(@disabled)]";
	public String btnCPMConfig = "//button[text()='Config' and not(@disabled)]";

	// *****************************************************REUSABLES
	// FUNCTIONS********************************************************

	public boolean enterMDBulkDecision(String sProposalType, String sDecisionType) throws InterruptedException {

		oGenericUtils.gfn_Click_String_object_Xpath(BulkDecisionBtn);
//		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
//		oGenericUtils.gfn_Click_String_object_Xpath(BulkDecisionText);
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
		// oGenericUtils.gfn_Click_String_object_Xpath("//select[@class='ng-pristine
		// ng-valid ng-touched']");
		// oGenericUtils.gfn_Click_String_object_Xpath("//select[@class='ng-pristine
		// ng-valid ng-touched']/option[@value='"+sProposalType+"']");
		// oGenericUtils.gfn_Click_String_object_Xpath("//b[text()='Decision']/..//..//select[@class='ng-untouched
		// ng-pristine ng-valid']");
		// oGenericUtils.gfn_Click_String_object_Xpath("//b[text()='Decision']/..//..//select[@class='ng-untouched
		// ng-pristine
		// ng-valid']/option[contains(text(),'"+sDecisionType+"')]");
		Serenity.setSessionVariable("SystemBulkDecision").to(sDecisionType);
		oSeleniumUtils.Enter_given_Text_Element(BulkDecisionComments, "Text" + java.time.LocalTime.now());
		oSeleniumUtils.Click_given_Locator(SelAllProposalChk);
		oSeleniumUtils.Click_given_Locator(EnterInBulkDecision);

		int i = getDriver().findElements(By.xpath("//h4[text()='Alert!']")).size();
		if (i > 0) {
			GenericUtils.Verify("Alert Messeage is displayed", true);
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
		}

		oSeleniumUtils.Click_given_Locator(CloseBulkDecision);
		boolean bFlag = true;
		return bFlag;

	}

	public boolean validateSysPropBasedBulkDecision(String sProposalType) {

		boolean bValDecisionSel = false;
		oSeleniumUtils.Enter_given_Text_Element(ProposalType, sProposalType);
		SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);

		oCommonPage.GetDynamicXPathWithRowCol("SYSTEMPROPOSAL", 1, 2);
		String pList = Serenity.sessionVariableCalled("SystemBulkDecision").toString();
		ArrayList<String> sMidRuleList = new ArrayList<String>();

		List<WebElement> iProposals = getDriver().findElements(By.xpath(
				"(//h5[text()=' System Proposals ']/parent::span/following::div//div//tbody[@class='ui-table-tbody'])[1]//tr"));

		System.out.println("size " + iProposals.size());
		for (int i = 0; i < iProposals.size(); i++) {
			int j = i + 1;
			List<WebElement> iSelProposals = getDriver()
					.findElements(By.xpath(oCommonPage.GetDynamicXPathWithRowCol("SYSTEMPROPOSALROWS", j, 2)));
			System.out.println("Options are " + iSelProposals.get(0).getAttribute("value"));

			switch (pList) {
			case "Remove":
				pList = "2";
				break;
			case "Modify":
				pList = "3";
				break;
			case "No Action":
				pList = "4";
				break;
			case "No Decision":
				pList = "6";
				break;
			}

			System.out.println("Proposal Types" + pList);

			if (iSelProposals.get(0).getAttribute("value").trim().equalsIgnoreCase(pList.trim())) {

				GenericUtils.Verify("", true);
				bValDecisionSel = true;
			} else {
				GenericUtils.Verify("Bulk Decision is selected wrongly ", false);

			}
		} // end loop
		return bValDecisionSel;
	}

	public boolean ValidateData_with_DB(String arg1) throws InterruptedException {
		if (arg1.equals("Decisions")) {
			oGenericUtils.gfn_Click_On_Object("span", "Decisions");
			if (getDriver().findElements(By.xpath("//button[text()='Modify Decisions'][not(@disabled)]")).size() > 0) {
				oGenericUtils.gfn_Click_String_object_Xpath("//button[text()='Modify Decisions'][not(@disabled)]");
			}
			oGenericUtils.gfn_Click_On_Object("button", "Add Code");
			oGenericUtils.gfn_Verify_Object_Exist("h4", "Add Code");
			oGenericUtils.gfn_Click_String_object_Xpath(rdoStartNew);
			oGenericUtils.gfn_Click_On_Object("button", "Start New");
			oGenericUtils.gfn_Click_String_object_Xpath(chkSelect);
			oGenericUtils.gfn_Click_On_Object("button", "Delete Selected");
			oGenericUtils.gfn_Verify_Object_Exist("div", "Are you sure you want to delete selected rows?");
			oGenericUtils.gfn_Click_On_Object("button", "Yes");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
			fnAddCode("D81.3", false, "Billed Without (And)");
			fnSaveManualGrid("Successfully saved manual proposals.");
			oGenericUtils.gfn_Click_String_object_Xpath(chkSelect);
			oGenericUtils.gfn_Click_On_Object("button", "Delete Selected");
			oGenericUtils.gfn_Verify_Object_Exist("div", "Are you sure you want to delete selected rows?");
			oGenericUtils.gfn_Click_On_Object("button", "No");
			oGenericUtils.gfn_Click_On_Object("button", "Delete Selected");
			oGenericUtils.gfn_Verify_Object_Exist("div", "Are you sure you want to delete selected rows?");
			oGenericUtils.gfn_Click_On_Object("button", "Yes");
			SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
			oGenericUtils.gfn_Verify_String_Object_Exist(btnDisabled);
			fnSaveManualGrid("Successfully saved manual proposals.");
			fnAddCode("Test", false, "Billed Without (And)");
			fnSaveManualGrid("ICD Code is not valid");
			return true;
		}
		if (arg1.equals("Rule Description")) {
			oGenericUtils.gfn_Click_On_Object("button", "Show Rule Description");
			WebElement oElement = getDriver().findElement(By.xpath(txtTask));
			String sText = oElement.getAttribute("value");
			oGenericUtils.gfn_Verify_Object_Exist("label", sText);
		} else {
			oGenericUtils.gfn_Click_On_Object("button", "Open in New Window");
			SeleniumUtils.switchWindowUsingWindowCount(2, 3, getDriver());
			oGenericUtils.gfn_Verify_Object_Exist("label", "Task:");
		}
		String sMidRule = StringUtils.substringBefore(Serenity.sessionVariableCalled("MidRuleVersion").toString(), ".")
				.trim();
		String sVersion = StringUtils.substringAfter(Serenity.sessionVariableCalled("MidRuleVersion").toString(), ".")
				.trim();
		String[] strDBCoulumnsArray = new String[] { "REFERENCE", "SUB_RULE_NOTES", "CLAIM_TYPES_4_RULE",
				"SUB_RULE_DESC", "SUB_RULE_DESC_RESOLVED" };
		String[] strLocatorArray = new String[] { txtReference, txtNotes, txtClaimTypes, txtDesc, txtDesc };
		for (int i = 0; i < strDBCoulumnsArray.length; i++) {
			if (strDBCoulumnsArray[i] == "SUB_RULE_DESC_RESOLVED")
				oGenericUtils.CLICK_LINK_XPATH(chkResolved);
			String sDBQuery = DBQueries.sQuery_Rule_Description(strDBCoulumnsArray[i], sMidRule, sVersion);
			String sDBData = DBUtils.executeSQLQuery(sDBQuery);
			WebElement oElement = getDriver().findElement(By.xpath(strLocatorArray[i]));
			String lblText = oElement.getAttribute("value");
			if (lblText.trim().equals(sDBData.trim())) {
				GenericUtils.gfn_ReportEvent("DB value is  " + sDBData + " and UI value is " + lblText + " for "
						+ strDBCoulumnsArray[i] + " Field are same", "Passed");
			} else {
				GenericUtils.gfn_ReportEvent("DB value is " + sDBData + " and UI value is " + lblText + " for "
						+ strDBCoulumnsArray[i] + " Field are not same", "Failed");
			}

		}
		return true;
	}

	public void fnAddCode(String sCode, boolean isAnother, String CategoryCode) throws InterruptedException {
		if (!isAnother) {
			oGenericUtils.gfn_Click_On_Object("button", "Add Code");
			oGenericUtils.gfn_Verify_Object_Exist("h4", "Add Code");
			oGenericUtils.gfn_Click_String_object_Xpath(rdoStartNew);
			oGenericUtils.gfn_Click_On_Object("button", "Start New");
		}

		SeleniumUtils.defaultWait(3000);
		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(ddlInstance, "Yes");
		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(ddlDecision, " Manual Add");
		oSeleniumUtils.Enter_given_Text_Element(txtComments, "Test Comments");
		oSeleniumUtils.Enter_given_Text_Element(txtCode, sCode);
		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(ddlCategory, CategoryCode);

		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(ddlDcat, "PRIMARY");

	}

	public boolean fnAddAnotherCode(String sCode) throws InterruptedException {
		if (getDriver().findElements(By.xpath("//button[text()='Modify Decisions'][not(@disabled)]")).size() > 0) {
			oGenericUtils.gfn_Click_String_object_Xpath("//button[text()='Modify Decisions'][not(@disabled)]");
		}
		oGenericUtils.gfn_Click_On_Object("button", "Add Code");
		oGenericUtils.gfn_Verify_Object_Exist("h4", "Add Code");
		oGenericUtils.gfn_Click_String_object_Xpath(rdoStartNew);
		oGenericUtils.gfn_Click_On_Object("button", "Start New");
		if (sCode.toUpperCase().equals("VERIFYMANUALGRID")) {
			oGenericUtils.gfn_Verify_String_Object_Exist(ddlInstance);
			oGenericUtils.gfn_Verify_String_Object_Exist(ddlDecision);
			oGenericUtils.gfn_Verify_String_Object_Exist(txtComments);
			oGenericUtils.gfn_Verify_String_Object_Exist(txtCode);
			oGenericUtils.gfn_Verify_String_Object_Exist(ddlCategory);
			oGenericUtils.gfn_Verify_String_Object_Exist(ddlDcat);
			fnSaveManualGrid("Associated With Update Instance: Field is required.");
			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(ddlInstance, "Yes");
			// fnSaveManualGrid("Decision: Field is required.");
			// oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(ddlDecision,
			// " Manual Add");
			fnSaveManualGrid("Rationale Comments: Field is required.");
			oSeleniumUtils.Enter_given_Text_Element(txtComments, "Test Comments");
			fnSaveManualGrid("Review Code: Field is required.");
			oSeleniumUtils.Enter_given_Text_Element(txtCode, "Test");
			fnSaveManualGrid("Category: Field is required.");
			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(ddlCategory, "Billed Without (And)");
			fnSaveManualGrid("Diag Category: Field is required.");
			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(ddlDcat, "PRIMARY");
			fnSaveManualGrid("ICD Code is not valid");
			return true;
		} else if (sCode.toUpperCase().equals("ADDCODE")) {
			fnAddCode("D55.1", true, "Billed Without (And)");
			fnSaveManualGrid("Successfully saved manual proposals.");
			return true;
		}
		oGenericUtils.gfn_Click_String_object_Xpath(chkSelect);
		oGenericUtils.gfn_Click_On_Object("button", "Delete Selected");
		oGenericUtils.gfn_Verify_Object_Exist("div", "Are you sure you want to delete selected rows?");
		oGenericUtils.gfn_Click_On_Object("button", "Yes");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		oGenericUtils.gfn_Click_On_Object("button", "Add Code");
		oGenericUtils.gfn_Verify_Object_Exist("h4", "Add Code");
		oGenericUtils.gfn_Click_String_object_Xpath(rdoStartNew);
		oGenericUtils.gfn_Click_On_Object("button", "Start New");
		oGenericUtils.gfn_Click_On_Object("button", "Add Another Code");
		oGenericUtils.gfn_Verify_Object_Exist("h4", "Alert");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		fnAddCode(sCode, true, "Billed Without (And)");
		oGenericUtils.gfn_Click_On_Object("button", "Add Another Code");
		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator("(" + ddlInstance + ")[2]", "Yes");
		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator("(" + ddlDecision + ")[2]", " Manual Add");
		oSeleniumUtils.Enter_given_Text_Element("(" + txtComments + ")[2]", "Test Comments");
		oSeleniumUtils.Enter_given_Text_Element("(" + txtCode + ")[2]", "D55.1");
		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator("(" + ddlCategory + ")[2]",
				"Billed Without (And)");
		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator("(" + ddlDcat + ")[2]", "PRIMARY");
		fnSaveManualGrid("Successfully saved manual proposals.");

		return true;

	}

	public boolean fnCopyCodefromExisting(String sCode) throws InterruptedException {
		oGenericUtils.gfn_Click_On_Object("span", "Decisions");
		oGenericUtils.gfn_Click_String_object_Xpath(chkSystemprp);
		if (getDriver().findElements(By.xpath("//button[text()='Modify Decisions'][not(@disabled)]")).size() > 0) {
			oGenericUtils.gfn_Click_String_object_Xpath("//button[text()='Modify Decisions'][not(@disabled)]");
		}
		oGenericUtils.gfn_Click_On_Object("button", "Add Code");
		oGenericUtils.gfn_Verify_Object_Exist("h4", "Add Code");
		oGenericUtils.gfn_Click_String_object_Xpath(rdoCopyAnother);
		oGenericUtils.gfn_Click_On_Object("button", "Copy Code");
		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(ddlInstance, "Yes");
		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(ddlDecision, " Manual Add");
		oSeleniumUtils.Enter_given_Text_Element(txtComments, "Test Comments");
		oSeleniumUtils.Enter_given_Text_Element(txtCode, sCode);
		fnSaveManualGrid("Successfully saved manual proposals.");
		oGenericUtils.gfn_Click_String_object_Xpath(chkSelect);
		oGenericUtils.gfn_Click_On_Object("button", "Delete Selected");
		oGenericUtils.gfn_Verify_Object_Exist("div", "Are you sure you want to delete selected rows?");
		oGenericUtils.gfn_Click_On_Object("button", "Yes");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		fnSaveManualGrid("Successfully saved manual proposals.");
		return true;
	}

	public boolean fnSaveManualGrid(String sMessage) throws InterruptedException {
		oGenericUtils.CLICK_LINK_XPATH(btnSave);
		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
		AdminPage oAdminPage = this.switchToPage(AdminPage.class);
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		oGenericUtils.gfn_Verify_Object_Exist("div", sMessage);
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		return true;
	}

	public boolean fnUpdateManualPraposals() throws InterruptedException {
		oGenericUtils.gfn_Click_On_Object("button", "Save");
		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
		oGenericUtils.gfn_Verify_Object_Exist("div", " Successfully saved proposal decisions. ");
		oGenericUtils.gfn_Click_On_Object("button", "Generate Summaries");
		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
		SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
		oGenericUtils.gfn_Verify_Object_Exist("div", "Summaries generated successfully.");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
		oGenericUtils.gfn_Click_On_Object("button", " Modify Decisions ");
		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(ddlInstance, "Yes");
		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(ddlDecision, " Manual Add");
		oSeleniumUtils.Enter_given_Text_Element(txtComments, "Test Comments" + java.time.LocalTime.now());
		// oSeleniumUtils.Enter_given_Text_Element(txtCode, sCode);
		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(ddlCategory, "Billed Without (And)");
		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(ddlDcat, "PRIMARY");
		oGenericUtils.gfn_Verify_Object_Exist("button", "Save");
		oGenericUtils.CLICK_LINK_XPATH("//label[text()='Rule Version: ']");
		fnSaveManualGrid("Successfully saved manual proposals.");
		return true;
	}

	@Step
	public boolean selectSystemProposal(String sProposalType, String sDecision) throws InterruptedException {

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		ArrayList<String> oList = new ArrayList<String>();
		if (sProposalType.equalsIgnoreCase("All")) {
			if (getDriver().findElements(By.xpath("//button[text()='Modify Decisions'][not(@disabled)]")).size() > 0) {
				oGenericUtils.gfn_Click_String_object_Xpath("//button[text()='Modify Decisions'][not(@disabled)]");
			}

			SeleniumUtils.scrollingToGivenElement(getDriver(), BulkDecisionBtn);
			oGenericUtils.gfn_Click_String_object_Xpath(BulkDecisionBtn);
			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
			oGenericUtils.gfn_Verify_String_Object_Exist(BulkDecisionText);
			List<WebElement> options = getDriver()
					.findElements(By.xpath("//b[text()='Proposal Type']/../..//select//option"));
			String arrDecision[] = new String[options.size() - 1];
			for (int i = 1; i < options.size(); i++) {
				arrDecision[i - 1] = options.get(i).getText();
				String text = options.get(i).getText();
				System.out.println(text);
			}

			oSeleniumUtils.Click_given_Locator(CloseBulkDecision);

			for (int i = 0; i < arrDecision.length; i++) {

				boolean bFlag = enterMDBulkDecision(arrDecision[i], sDecision);
				GenericUtils.Verify("Entered Bulk Decision Properly ", bFlag);
			}
		} else {

			String arrayProposaltype[] = sProposalType.split(";");

			int arraySize = arrayProposaltype.length;

			if (arraySize > 1) {
				oGenericUtils.gfn_Click_On_Object("button", "Modify Decisions");
				SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
				oSeleniumUtils.Enter_given_Text_Element(ProposalType, arrayProposaltype[1]);
			} else {
				oSeleniumUtils.Enter_given_Text_Element(ProposalType, sProposalType);
			}

			SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);

			String[] sDropDownList = oSeleniumUtils
					.get_All_Text_from_Locator("(//h5[.=' System Proposals ']/../../..//select)//option");

			for (int i = 0; i < sDropDownList.length; i++) {

				oList.add(sDropDownList[i]);
			}
		}

		switch (sDecision) {

		case "No Decision-Not Displayed":

			boolean bln = !oList.contains("No Decision");

			Assert.assertTrue("No Decision Value is displayed", bln);

			break;
		case "No Decision-Displayed":

			boolean bln1 = oList.contains("No Decision");

			Assert.assertTrue("No Decision Value is not displayed", bln1);

			break;
		case "Add":
		case "No Action":
		case "Remove":
		case "No Decision":

			SystemProposals(sDecision);

			if (oSeleniumUtils.is_WebElement_Displayed("//a[text()='2']") == true) {

				oGenericUtils.gfn_Click_On_Object("a", "2");

				SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);

				SystemProposals(sDecision);

			} else if (oSeleniumUtils.is_WebElement_Displayed("//a[text()='3']") == true) {

				oGenericUtils.gfn_Click_On_Object("a", "3");

				SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);

				SystemProposals(sDecision);
			}

			break;

		case "Modify":

			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(oCommonPage.SystemProposalDecision,
					sDecision);

			oSeleniumUtils.Enter_given_Text_Element(oCommonPage.SystemProposalCommments,
					ProjectVariables.SystemProposalComments);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oGenericUtils.gfn_Click_String_object_Xpath(oCommonPage.System_Proposal_DOS);

			oGenericUtils.gfn_Click_String_object_Xpath("(//div[text()=' From Date : ']//mat-form-field//button)[2]");

			Serenity.setSessionVariable("To Date")
					.to(String.valueOf(GenericUtils.generate_Random_Number_for_Given_Range(20) + 1));

			oGenericUtils.gfn_Click_On_Object("div", Serenity.sessionVariableCalled("To Date").toString());

			String sText = oSeleniumUtils
					.get_TextFrom_Locator("(//div[text()=' From Date : ']//mat-form-field//input)[1]");

			String sText1 = oSeleniumUtils
					.get_TextFrom_Locator("(//div[text()=' From Date : ']//mat-form-field//input)[2]");

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			boolean bln12 = oGenericUtils.gfn_Verify_String_Object_Exist(sText + "-" + sText1);

			System.out.println("Modified Date updated" + (sText + "-" + sText1) + bln12);

			break;

		case "ModifywithoutDate":

			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(oCommonPage.SystemProposalDecision,
					ProjectVariables.Decision);

			oSeleniumUtils.Enter_given_Text_Element("//textarea[@id='1_interpComment.comments']",
					ProjectVariables.SystemProposalComments);

			break;

		case "ModifyDecisionWarning":

			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(oCommonPage.SystemProposalDecision,
					"Modify");

			oGenericUtils.gfn_Click_On_Object("button", "Save");

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			Assert.assertTrue("Please Modify the DOS value Warning is dispalyed", oSeleniumUtils
					.is_WebElement_Displayed("//div[contains(text(),'Please Modify the DOS value for Review Code')]"));

			oGenericUtils.gfn_Verify_Object_Exist("h4", "Error");

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			oSeleniumUtils.Enter_given_Text_Element(oCommonPage.SystemProposalCommments,
					ProjectVariables.SystemProposalComments);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oSeleniumUtils.Enter_given_Text_Element(oCommonPage.System_Proposal_DOS,
					ProjectVariables.SystemProposalDOS);

			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oCommonPage.SpanTag, "sValue", "Ok"));

			break;

		}

		oGenericUtils.gfn_Click_String_object_Xpath("//div[@class='col-md-offset-9']//button[text()='Save']");
		// oGenericUtils.gfn_Click_On_Object("button", "Save");

		SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);

		// oGenericUtils.gfn_Verify_Object_Exist("div", " Successfully saved proposal
		// decisions. ");
		// Assert.assertTrue("Decision Successfully Saved",
		// oSeleniumUtils.is_WebElement_Displayed(
		// StringUtil.replace(oCommonPage.DivTag, "sValue", " Successfully saved
		// proposal decisions. ")));

		// boolean bln=oGenericUtils.gfn_Verify_String_Object_Exist(("//div[text()='All
		// entered decisions are saved successfully. There are no modified
		// decisions/rational comments to save.']"));

		String sEle = "//div[text()='All entered decisions are saved successfully. There are no modified decisions/rational comments to save.']";
		if (getDriver().findElements(By.xpath(sEle)).size() > 0) {

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

		} else {

			boolean blnFlg = oGenericUtils.gfn_Verify_Object_Exist("div", " Successfully saved proposal decisions. ");

			GenericUtils.Verify("Decision is Successfully Saved", blnFlg);
		}

		return true;

	}

	private void SystemProposals(String sDecision) {

		List<WebElement> iMidRuleCount1 = getDriver()
				.findElements(By.xpath("//h5[.=' System Proposals ']/../../..//select"));

		System.out.println(iMidRuleCount1.size());

		for (int i = 1; i <= iMidRuleCount1.size(); i++) {

			int j = i;
			String sXpath = "(//h5[.=' System Proposals ']/../../..//select)" + "[" + i + "]";
			System.out.println(sXpath);
			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(sXpath, sDecision);
			oSeleniumUtils.Enter_given_Text_Element(oCommonPage.SystemProposalCommments + "[" + j + "]",
					ProjectVariables.SystemProposalComments);

		}

	}

	public void selectModifyDecisionForProposal() throws InterruptedException {

		int iProposalRows = getDriver().findElements(By.xpath(
				"(//h5[text()=' System Proposals ']/parent::span/following::div//div//tbody[@class='ui-table-tbody'])[1]//tr"))
				.size();

		if (iProposalRows > 1) {

			for (int i = 1; i < iProposalRows; i++) {

				// oCommonPage.GetDynamicXPathWithRowCol("GetDynamicXPathWithRowCol",
				// sCol, sRow);
				oGenericUtils.gfn_Click_String_object_Xpath(
						"(//h5[text()=' System Proposals ']/parent::span/following::div//div//tbody[@class='ui-table-tbody'])[1]//tr[i]/td[1]");
				oSeleniumUtils.Enter_given_Text_Element(
						"(//h5[text()=' System Proposals ']/parent::span/following::div//div//tbody[@class='ui-table-tbody'])[1]//tr[i]/td[3]",
						"Automation Comments");
				oGenericUtils.gfn_Click_On_Object("button", "Save");

			}

		}
	}

	public void setNoChangeRequiredInMDReview(String sOperation, String sValue) throws InterruptedException {

		boolean blnFlg = false;

		switch (sOperation) {

		case "No Change Required":

			blnFlg = oGenericUtils.gfn_Click_On_Object("button", "No Change Required");

			GenericUtils.Verify("No Change Required Button is Clicked in Rule Review WorkQueue", blnFlg);

			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			/*
			 * GenericUtils.
			 * Verify("No Change Required Button is Clicked in Rule Review WorkQueue",
			 * oGenericUtils.gfn_Click_On_Object("h4", "No Change Required"));
			 */

			Serenity.setSessionVariable("Comments").to(sOperation);

			blnFlg = oGenericUtils.gfn_Verify_String_Object_Exist("//b[text()='Comment:']/following-sibling::textarea");

			if (blnFlg != true) {

				blnFlg = oGenericUtils.gfn_Click_On_Object("button", "No Change Required");

				GenericUtils.Verify("No Change Required Button is Clicked in Rule Review WorkQueue", blnFlg);
			}

			blnFlg = oSeleniumUtils.Enter_given_Text_Element("//b[text()='Comment:']/following-sibling::textarea",
					sOperation);

			GenericUtils.Verify("Text is entered in No change Required Popup", blnFlg);

			if (sValue.equalsIgnoreCase("Cancel")) {

				oGenericUtils.gfn_Click_String_object_Xpath("//b[text()='Comment:']/..//button[.='Cancel']");

				/*
				 * GenericUtils.
				 * Verify("No Change Required Button is Clicked in Rule Review WorkQueue",
				 * oGenericUtils.gfn_Click_On_Object("h2", "No Change Required"));
				 */

			} else {

				blnFlg = oGenericUtils.gfn_Click_On_Object("button", "Set No Change Required");

				GenericUtils.Verify("Set button is Clicked on Popup window", blnFlg);
			}

			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			break;
		case "Unset No Change Required":

			blnFlg = oGenericUtils.gfn_Click_On_Object("button", "Unset No Change Required");

			GenericUtils.Verify("Unset No Change Required Button is Clicked in Rule Review WorkQueue Page", blnFlg);

			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			blnFlg = oGenericUtils.gfn_Verify_Object_Exist("div", "Successfully done Unset No Change Required.");

			GenericUtils.Verify(
					"After Clicking Unset No Change Required in Review WorkQueue Page Popup Text Expected: Successfully done Unset No Change Required.",
					blnFlg);

			blnFlg = oGenericUtils.gfn_Click_On_Object("button", "Ok");

			GenericUtils.Verify("Ok button is clicked on the Popup", blnFlg);

			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			break;
		case "No Decision":

			oGenericUtils.gfn_Click_On_Object("button", sOperation);

			oGenericUtils.gfn_Verify_Object_Exist("h4", "No Decisions");

			// String sText =
			// oSeleniumUtils.get_TextFrom_Locator("//div[text()=' This option
			// will automatically complete the ']");

			// System.out.println(sText);

			if (sValue.equalsIgnoreCase("No")) {

				oGenericUtils.gfn_Verify_Object_Exist("button", sOperation);

			} else {

				oGenericUtils.gfn_Click_On_Object("button", "Yes");

				oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			}

			break;
		case "No Decision-FinalMDLib":

			boolean bln = !oSeleniumUtils.is_WebElement_Enabled("//button[text()='No Decision']");

			GenericUtils.Verify("No Decisions button is enabled", bln);

			break;
		case "No Decision-Alert":

			oGenericUtils.gfn_Click_On_Object("button", "No Decision");

			oGenericUtils.gfn_Verify_Object_Exist("*", "Error");

			if (Serenity.sessionVariableCalled("TaskType").toString().equalsIgnoreCase("Final PO Review")) {

				oGenericUtils.gfn_Verify_Object_Exist("div",
						"Final comments needs to be provided before performing no decisions action.");

			} else {

				oGenericUtils.gfn_Verify_Object_Exist("div",
						"Preliminary comments needs to be provided before performing no decisions action.");
			}

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			break;

		default:
			break;
		}

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

	}

	public void completeAuthroizationInMDReview(String arg1) throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		String sTaskTypeDBValue = DBUtils.executeSQLQuery(DBQueries.sQueryTaskTypeAndStatus("TTL.TASK_TYPE_DESC",
				Serenity.sessionVariableCalled("IUInstanceName").toString(),
				Serenity.sessionVariableCalled("MidRuleVersion").toString()));
		if (sTaskTypeDBValue.trim().equalsIgnoreCase("CPM Review") || arg1.equals("CPM")) {
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oCommonPage.SpanTag, "sValue", "CPM Summaries"));
		} else {
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oCommonPage.SpanTag, "sValue", "Summaries"));
		}
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		if (arg1.equals("Retire Rule-a")) {
			oSeleniumUtils.Click_given_Locator(lblConfigSummary);
			oSeleniumUtils.Enter_given_Text_Element(txtCnfgSummary, "Test Summary");
			oSeleniumUtils.Click_given_Locator(btnCnfgSummary);
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			oGenericUtils.gfn_Verify_Object_Exist("div", "Configuration Summary  Saved Successfully.");
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		}

		oGenericUtils.gfn_Click_String_object_Xpath(("//button[contains(text(),'Authorize Decisions')]"));

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		int iEditorialWarning = getDriver()
				.findElements(By.xpath(StringUtils.replace(oCommonPage.h3Tag, "sValue", "Editorial changes observed")))
				.size();

		if (iEditorialWarning > 0) {

			oGenericUtils.gfn_Click_On_Object("button", "No");
			oGenericUtils.gfn_Click_String_object_Xpath(("//label[contains(text(),'Authorize Decisions')]"));

		}

		List<WebElement> sList = getDriver().findElements(By.xpath(oCommonPage.RetireRule_Error));

		if (sList.size() > 0) {

			oGenericUtils.gfn_Verify_Object_Exist("div", "Please Review Retire Rule before Authorizing Decisions");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			retireRule(arg1);
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			oGenericUtils.gfn_Click_String_object_Xpath(("//button[contains(text(),'Authorize Decisions')]"));

		}

		oGenericUtils.gfn_Verify_Object_Exist("button", "Yes");

		oGenericUtils.gfn_Click_On_Object("button", "Yes");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

	}

	public void retireRule(String arg1) throws InterruptedException {

		switch (arg1) {
		case "Do Not Retire Rule":

			oGenericUtils.gfn_Click_String_object_Xpath("//h5[contains(text(),'Retire Rule ')]");

			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator("//select[@id='ddlMDDecision']",
					"Do Not Retire Rule");

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			oSeleniumUtils.Enter_given_Text_Element(RetireRuleMDComments, ProjectVariables.TestComments);
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oCommonPage.ButtonTag, "sValue", "Save"));
			oGenericUtils.gfn_Verify_Object_Exist("button", "Ok");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oCommonPage.ButtonTag, "sValue", "Ok"));
			break;
		case "Retire Rule-a":

			oGenericUtils.gfn_Click_String_object_Xpath("//h5[contains(text(),'Retire Rule ')]");
			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);
			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator("//select[@id='ddlMDDecision']",
					"Retire Rule");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			oSeleniumUtils.Enter_given_Text_Element(RetireRuleMDComments, ProjectVariables.TestComments);
			oGenericUtils.gfn_Click_String_object_Xpath("//label[text()=' No ']");
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oCommonPage.ButtonTag, "sValue", "Save"));
			oGenericUtils.gfn_Verify_Object_Exist("button", "Ok");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			oSeleniumUtils.Click_given_Locator(StringUtil.replace(oCommonPage.ButtonTag, "sValue", "Ok"));
			break;
		}

	}

	public boolean validateRationaleComments(String sRationalComments) {

		boolean bValRationalComments = false;
		// oSeleniumUtils.Enter_given_Text_Element(ProposalType, sProposalType);
		SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);

		// oCommonPage.GetDynamicXPathWithRowCol("MDRATIONALCOMMENTS", 1, 2);
		// String pList =
		// Serenity.sessionVariableCalled("SystemBulkDecision").toString();
		ArrayList<String> sMidRuleList = new ArrayList<String>();

		List<WebElement> iProposals = getDriver().findElements(By.xpath(
				"(//h5[text()=' System Proposals ']/parent::span/following::div//div//tbody[@class='ui-table-tbody'])[1]//tr"));

		System.out.println("size " + iProposals.size());
		for (int i = 0; i < iProposals.size(); i++) {
			int j = i + 1;
			List<WebElement> iRationalComments = getDriver()
					.findElements(By.xpath(oCommonPage.GetDynamicXPathWithRowCol("SYSTEMPROPOSALROWS", j, 3)));
			System.out.println("Options are " + iRationalComments.get(0).getAttribute("value"));

			System.out.println("Proposal Types" + sRationalComments);

			if (iRationalComments.get(0).getAttribute("value").trim().equalsIgnoreCase(sRationalComments.trim())) {

				GenericUtils.Verify("", true);
				bValRationalComments = true;
			} else {
				GenericUtils.Verify("Bulk Decision is selected wrongly ", false);

			}
		} // end loop
		return bValRationalComments;
	}

	public String createRequestXML(String sRequestType, String sProjectID, String sSubRuleKey) {

		String sCurrentDate = GenericUtils.getDate_given_Format();
		String sRequestedXML = "";
		String sSubRuleDesc = "";
		String sRuleScript = "";
		String sRationale = "";
		String sNotes = "";
		String sRef = "";

		if (sRequestType.equalsIgnoreCase("LOGICALRMRIUPD")) {
			// String sQuery = "SELECT 'sValue' FROM RULES.SUB_RULES where mid_rule_key
			// ='6153' and rule_version= '1'";
			// sub_rule_key,
			// sub_rule_desc,sub_rule_script,sub_rule_rationale,sub_rule_notes,reference,
			// dos_from, dos_to
			String sQuery = "SELECT sValue FROM  RULES.SUB_RULES where sub_rule_key ='" + sSubRuleKey + "'  ";
			// String sQuery = oCommonPage.getDynamicQuery("SUBRULEDETAILS",sSubRuleKey,"");

			sSubRuleDesc = DBUtils.executeSQLQuery(StringUtils.replace(sQuery, "sValue", "sub_rule_desc"));
			sRuleScript = DBUtils.executeSQLQuery(StringUtils.replace(sQuery, "sValue", "sub_rule_script"));
			sRationale = DBUtils.executeSQLQuery(StringUtils.replace(sQuery, "sValue", "sub_rule_rationale"));
			sNotes = DBUtils.executeSQLQuery(StringUtils.replace(sQuery, "sValue", "sub_rule_notes"));
			sRef = DBUtils.executeSQLQuery(StringUtils.replace(sQuery, "sValue", "reference"));
		}

		String sLotusUser = "";
		if (sRequestType.equalsIgnoreCase("CREATERMRSTUBIUPDFORLOGICALRMR")) {
			sLotusUser = Serenity.sessionVariableCalled("LotusUserName").toString();
		} else {
			sLotusUser = "IHT_ITTEST04";
		}

		if (sRequestType.equalsIgnoreCase("CREATEPRMID")) {
			sLotusUser = Serenity.sessionVariableCalled("LotusUserName").toString();
			// }else{
			// sLotusUser="icpmtest37";
		}

		switch (sRequestType) {

		case "CREATERMRSTUBIUPD":

			sRequestedXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:com.ihealth.ws.iupd\">\r\n"
					+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>\r\n" + "      <urn:CREATERMRSTUBIUPD>\r\n"
					+ "         <CREATEDBY>" + sLotusUser + "</CREATEDBY>\r\n" + "         <PRID>" + sProjectID
					+ "</PRID>\r\n" + "         <SUBRULEKEY>" + sSubRuleKey + "</SUBRULEKEY>\r\n"
					+ "         <REQFIGAROSWITCH>No</REQFIGAROSWITCH>\r\n" + "         <ARDRULE>No</ARDRULE>\r\n"
					+ "         <DUEDATE>02/20/2021</DUEDATE>\r\n" + "         <REQUESTTYPEKEY>2</REQUESTTYPEKEY>\r\n"
					+ "         <DESCRIPTION>sample test</DESCRIPTION>\r\n"
					+ "         <PURPOSE>sample test</PURPOSE>\r\n" + "         <CHANGESOURCE>14</CHANGESOURCE>\r\n"
					+ "         <COMMENTS>sample comments</COMMENTS>\r\n" + "      </urn:CREATERMRSTUBIUPD>\r\n"
					+ "   </soapenv:Body>\r\n" + "</soapenv:Envelope>";

			break;

		case "EDITORIALUPDATERMRIUPD":

			sRequestedXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:com.ihealth.ws.iupd\">\r\n"
					+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>\r\n" + "      <urn:EDITORIALUPDATERMRIUPD>\r\n"
					+ "         <ID>" + sProjectID + "</ID>\r\n" + "         <ICMCLIENTS>N/A</ICMCLIENTS>\r\n"
					+ "         <ICMOCLIENTS>N/A</ICMOCLIENTS>\r\n" + "         <SUBRULEDESC>ABC desc</SUBRULEDESC>\r\n"
					+ "         <SCRIPT>script</SCRIPT>\r\n" + "         <RATIONALE>rationale</RATIONALE>\r\n"
					+ "         <NOTES>notes</NOTES>\r\n" + "         <REFDETAILS>referwnce</REFDETAILS>\r\n"
					+ "         <DOSFROM>01/01/1753</DOSFROM>\r\n" + "         <DOSTO>12/31/9999</DOSTO>\r\n"
					+ "         <CHANGECOMM>Updated through Post services</CHANGECOMM>\r\n"
					+ "         <SWITCHTYPE>4</SWITCHTYPE>\r\n" + "      </urn:EDITORIALUPDATERMRIUPD>\r\n"
					+ "   </soapenv:Body>\r\n" + "</soapenv:Envelope>";

			break;

		case "DEACTIVATERMRIUPD":

			sRequestedXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:com.ihealth.ws.iupd\">\r\n"
					+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>\r\n" + "      <urn:DEACTIVATERMRIUPD>\r\n"
					+ "         <ID>" + sProjectID + "</ID>\r\n" + "         <ICMCLIENTS>N/A</ICMCLIENTS>\r\n"
					+ "         <ICMOCLIENTS>N/A</ICMOCLIENTS>\r\n" + "         <NOTES>Subrule desc</NOTES>\r\n"
					+ "         <REFDETAILS>referwnce</REFDETAILS>\r\n" + "         <DOSFROM>01/01/1763</DOSFROM>\r\n"
					+ "         <DOSTO>12/31/9999</DOSTO>\r\n"
					+ "         <CHANGECOMM>Deactivated through post service</CHANGECOMM>\r\n"
					+ "         <SWITCHTYPE>4</SWITCHTYPE>\r\n" + "      </urn:DEACTIVATERMRIUPD>\r\n"
					+ "   </soapenv:Body>\r\n" + "</soapenv:Envelope>";
			break;

			
		case "CREATEPRMID":
			sRequestedXML  = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:com.cotiviti.ws.prm\">" +
					"   <soapenv:Header/>" +
					"   <soapenv:Body>" +
					"      <urn:CREATEPROJECTREQUEST>" +
					"         <USERID>" + sLotusUser + "</USERID>" +
					"         <CREATORNAME>Raveendra</CREATORNAME>" +
					"         <CATEGORY>Industry Update</CATEGORY>" +
					"         <FIGAROYN></FIGAROYN>" +
					"         <SOURCEREQ>KK Test</SOURCEREQ>" +
					"         <TARGETREL></TARGETREL>" +
					"         <REQUESTDATE>" + sCurrentDate + "</REQUESTDATE>" +
					"         <DCD>01/15/2022</DCD>" +
					"         <PAYERS>ALL</PAYERS>" +
					"         <TITLE>Auto Test</TITLE>" +
					"         <DESCRIPTION>Create IU PRM ID</DESCRIPTION>" +
					"      </urn:CREATEPROJECTREQUEST>" +
					"   </soapenv:Body>" +
					"</soapenv:Envelope>";
			break;

		case "LOGICALRMRIUPD":

			sRequestedXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:com.ihealth.ws.iupd\">"
					+ "   <soapenv:Header/>" + "   <soapenv:Body>" + "      <urn:LOGICALRMRIUPD>" + "         <ID>"
					+ sProjectID + "</ID>" + "         <ICMCLIENTS>N/A</ICMCLIENTS>"
					+ "         <ICMOCLIENTS>N/A</ICMOCLIENTS>" + "         <SUBRULEDESC><![CDATA[" + sSubRuleDesc
					+ "]]></SUBRULEDESC>" + "         <SCRIPT><![CDATA[" + sRuleScript + "]]></SCRIPT>"
					+ "         <RATIONALE><![CDATA[" + sRationale + "]]></RATIONALE>" + "         <NOTES><![CDATA["
					+ sNotes + "]]></NOTES>" + "         <REFDETAILS><![CDATA[" + sRef + "]]></REFDETAILS>"
					+ "         <DOSFROM>01/01/1763</DOSFROM>" + "         <DOSTO>12/31/9999</DOSTO>"
					+ "         <CHANGECOMM>upatedthrservice</CHANGECOMM>" + "         <SWITCHTYPE>3</SWITCHTYPE>"
					+ "      </urn:LOGICALRMRIUPD>" + "   </soapenv:Body>" + "</soapenv:Envelope>";

			break;

		case "DEACTIVATERULE":

			sRequestedXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:com.ihealth.ws.iupd\">\r\n"
					+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>\r\n" + "      <urn:DEACTIVATERMRIUPD>\r\n"
					+ "         <ID>" + sProjectID + "</ID>\r\n" + "         <ICMCLIENTS>N/A</ICMCLIENTS>\r\n"
					+ "         <ICMOCLIENTS>N/A</ICMOCLIENTS>\r\n" + "         <NOTES><![CDATA[" + sNotes
					+ "]]></NOTES>" + "         <REFDETAILS>referwnce</REFDETAILS>\r\n"
					+ "         <DOSFROM>01/01/1763</DOSFROM>\r\n" + "         <DOSTO>12/31/9999</DOSTO>\r\n"
					+ "         <CHANGECOMM>Deactivated through post service</CHANGECOMM>\r\n"
					+ "         <SWITCHTYPE>4</SWITCHTYPE>\r\n" + "      </urn:DEACTIVATERMRIUPD>\r\n"
					+ "   </soapenv:Body>\r\n" + "</soapenv:Envelope>";
			break;

		case "CREATERMRSTUBIUPDFORLOGICALRMR":

			sRequestedXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:com.ihealth.ws.iupd\">\r\n"
					+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>\r\n" + "      <urn:CREATERMRSTUBIUPD>\r\n"
					+ "         <CREATEDBY>" + sLotusUser + "</CREATEDBY>\r\n" + "         <PRID>" + sProjectID
					+ "</PRID>\r\n" + "         <SUBRULEKEY>" + sSubRuleKey + "</SUBRULEKEY>\r\n"
					+ "         <REQFIGAROSWITCH>No</REQFIGAROSWITCH>\r\n" + "         <ARDRULE>No</ARDRULE>\r\n"
					+ "         <DUEDATE>02/20/2020</DUEDATE>\r\n" + "         <REQUESTTYPEKEY>2</REQUESTTYPEKEY>\r\n"
					+ "         <DESCRIPTION>sample test</DESCRIPTION>\r\n"
					+ "         <PURPOSE>sample test</PURPOSE>\r\n" + "         <CHANGESOURCE>14</CHANGESOURCE>\r\n"
					+ "         <COMMENTS>sample comments</COMMENTS>\r\n" + "      </urn:CREATERMRSTUBIUPD>\r\n"
					+ "   </soapenv:Body>\r\n" + "</soapenv:Envelope>";

			break;

		case "GETPRSTATUS":

			String sLotusPRMID = Serenity.sessionVariableCalled("LotusPRID").toString().trim();
			sRequestedXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:com.cotiviti.ws.cdm\">"
					+ "   <soapenv:Header/>" + "   <soapenv:Body>" + "      <urn:GETPRSTATUS>" + "         <SID>"
					+ sLotusPRMID.trim() + "</SID>" + "      </urn:GETPRSTATUS>" + "   </soapenv:Body>"
					+ "</soapenv:Envelope>";
			break;

		}

		System.out.println("Requested XML ::" + sRequestedXML);

		return sRequestedXML;
	}

	public boolean fnValidateEditorial(String sTab) throws InterruptedException {
		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
		oGenericUtils.gfn_Click_On_Object("button", "Editorials");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		oGenericUtils.gfn_Verify_Object_Exist("h4", "Editorial Update");
		String sMidRule = StringUtils.substringBefore(Serenity.sessionVariableCalled("MidRuleVersion").toString(), ".")
				.trim();
		String sVersion = StringUtils.substringAfter(Serenity.sessionVariableCalled("MidRuleVersion").toString(), ".")
				.trim();
		switch (sTab) {
		case "Description":
			oGenericUtils.gfn_Click_On_Object("span", "Description");
			String UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", "Rule Description:"));
			String txtVal = UIText;
			fnRefreshData(false);
			SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
			oSeleniumUtils.Enter_given_Text_Element(txtEditorial.replace("arg", "Edit New Rule Description:"),
					txtVal + "Test");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
			String mrq = oSeleniumUtils.get_TextFrom_Locator(mrqText.replace("arg", "New Rule Description:"));
			SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
			GenericUtils.Verify("MarkUp", mrq.equalsIgnoreCase("Test"));
			System.out.println("Mark up text:-" + mrq);
			oGenericUtils.gfn_Click_On_Object("button", "Update Resolved Description");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
			UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", "Edit New Rule Description:"));
			System.out.println("Edit New Rule Description:-" + UIText);
			String resovedUIText = oSeleniumUtils
					.get_TextBy_Attribute(txtEditorial.replace("arg", "Resolved Rule Description:"));
			// GenericUtils.Verify("Update Resolved Description",
			// UIText.equalsIgnoreCase(resovedUIText));
			String DescCount = oSeleniumUtils
					.get_TextBy_Attribute("//div//label[text()='Resolved Rule Character Count:']/../..//input");
			GenericUtils.Verify("Character Count:", resovedUIText.length() == Integer.parseInt(DescCount));
			UIText = UIText.substring(0, UIText.length() - 10);
			oSeleniumUtils.Enter_given_Text_Element(txtEditorial.replace("arg", "Edit New Rule Description:"), UIText);
			oGenericUtils.CLICK_LINK_XPATH("//h4[text()='Editorial Update']/../..//button[text()='Save']");
			SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
			oGenericUtils.gfn_Verify_Object_Exist("div", "Successfully saved editorial updates");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", "Edit New Rule Description:"));
			oSeleniumUtils.Enter_given_Text_Element(txtEditorial.replace("arg", "Edit New Rule Description:"),
					UIText + "Test");
			ResetEditorialData(UIText);
			fnRefreshData(true);
			oGenericUtils.CLICK_LINK_XPATH(closeEditorial);
			break;
		case "Notes":
			oGenericUtils.gfn_Click_On_Object("span", "Notes");
			String[] sXPATHVar = { "Notes:", "Edit Notes:", "Updated Notes:" };
			ValidateTab(sMidRule, sVersion, "SUB_RULE_NOTES", sXPATHVar);
			break;
		case "Script":
			oGenericUtils.gfn_Click_On_Object("span", "Script");
			String[] sScriptVar = { "Script:", "Edit Script:", "Updated Script:" };
			ValidateTab(sMidRule, sVersion, "SUB_RULE_SCRIPT", sScriptVar);
			break;
		case "Rationale":
			oGenericUtils.gfn_Click_On_Object("span", "Rationale");
			String[] sRationalVar = { "Rationale:", "Edit Rationale:", "Updated Rationale:" };
			ValidateTab(sMidRule, sVersion, "SUB_RULE_RATIONALE", sRationalVar);
			break;
		case "Reference":
			oGenericUtils.gfn_Click_On_Object("span", "Reference");
			String[] sReferenceVar = { "Reference:", "Edit Reference:", "Updated Reference:" };
			ValidateTab(sMidRule, sVersion, "REFERENCE", sReferenceVar);
			break;
		}
		return true;
	}

	private void ValidateTab(String sMidRule, String sVersion, String DbColumn, String[] sXPATHVar)
			throws InterruptedException {
		String sDBQuery = DBQueries.sQuery_Rule_Description(DbColumn, sMidRule, sVersion);
		String sDBData = DBUtils.executeSQLQuery(sDBQuery);
		String UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", sXPATHVar[0]));
		System.out.println(sXPATHVar[0] + UIText);
		GenericUtils.Verify(sXPATHVar[0], UIText.trim().equalsIgnoreCase(sDBData.trim()));
		UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", sXPATHVar[1]));
		System.out.println(sXPATHVar[1] + UIText);
		GenericUtils.Verify(sXPATHVar[1], UIText.trim().equalsIgnoreCase(sDBData.trim()));
		oSeleniumUtils.Enter_given_Text_Element(txtEditorial.replace("arg", sXPATHVar[1]), UIText + "Test");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		String mrq = oSeleniumUtils.get_TextFrom_Locator(mrqText.replace("arg", sXPATHVar[2]));
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		GenericUtils.Verify("MarkUp", mrq.equalsIgnoreCase("Test"));
		System.out.println("Mark up text:-" + mrq);
		oGenericUtils.CLICK_LINK_XPATH("//h4[text()='Editorial Update']/../..//button[text()='Save']");
		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
		oGenericUtils.gfn_Verify_Object_Exist("div", "Successfully saved editorial updates");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", sXPATHVar[1]));
		String UITextNew = UIText.substring(0, UIText.length() - 10);
		oSeleniumUtils.Enter_given_Text_Element(txtEditorial.replace("arg", sXPATHVar[1]), UITextNew);

		// Reset
		oGenericUtils.CLICK_LINK_XPATH(EditoialButton.replace("arg1", sXPATHVar[1]).replace("arg2", "Reset"));
		oGenericUtils.CLICK_LINK_XPATH("//h4[text()='Confirmation']/../..//button[text()='Cancel']");
		oGenericUtils.CLICK_LINK_XPATH(EditoialButton.replace("arg1", sXPATHVar[1]).replace("arg2", "Reset"));
		oGenericUtils.CLICK_LINK_XPATH("//h4[text()='Confirmation']/../..//button[text()='Reset Data ']");
		String UITextReset = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", sXPATHVar[1]));
		GenericUtils.Verify("Edit Notes:", UIText.trim().equalsIgnoreCase(UITextReset.trim()));
		// Refresh
		oGenericUtils.CLICK_LINK_XPATH(EditoialButton.replace("arg1", sXPATHVar[1]).replace("arg2", "Refresh"));
		oGenericUtils.CLICK_LINK_XPATH("//h4[text()='Confirmation']/../..//button[text()='Cancel']");
		oGenericUtils.CLICK_LINK_XPATH(EditoialButton.replace("arg1", sXPATHVar[1]).replace("arg2", "Refresh"));
		oGenericUtils.CLICK_LINK_XPATH("//h4[text()='Confirmation']/../..//button[text()='Refresh Data']");
		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
		oGenericUtils.gfn_Verify_Object_Exist("div", "Successfully saved editorial updates");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		sDBQuery = DBQueries.sQuery_Rule_Description(DbColumn, sMidRule, sVersion);
		sDBData = DBUtils.executeSQLQuery(sDBQuery);
		UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", sXPATHVar[0]));
		System.out.println(sXPATHVar[0] + UIText);
		GenericUtils.Verify(sXPATHVar[0], UIText.trim().equalsIgnoreCase(sDBData.trim()));
		UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", sXPATHVar[1]));
		System.out.println(sXPATHVar[1] + UIText);
		GenericUtils.Verify(sXPATHVar[1], UIText.trim().equalsIgnoreCase(sDBData.trim()));
		oGenericUtils.CLICK_LINK_XPATH(closeEditorial);

	}

	private void ResetEditorialData(String sText) {
		oGenericUtils.CLICK_LINK_XPATH(
				EditoialButton.replace("arg1", "Edit New Rule Description:").replace("arg2", "Reset"));
		oGenericUtils.CLICK_LINK_XPATH("//h4[text()='Confirmation']/../..//button[text()='Cancel']");
		oGenericUtils.CLICK_LINK_XPATH(
				EditoialButton.replace("arg1", "Edit New Rule Description:").replace("arg2", "Reset"));
		oGenericUtils.CLICK_LINK_XPATH("//h4[text()='Confirmation']/../..//button[text()='Reset Data ']");
		String UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", "Edit New Rule Description:"));
		GenericUtils.Verify("Resolved Rule Description:", UIText.trim().equalsIgnoreCase(sText.trim()));
	}

	private void fnRefreshData(boolean isRefresh) throws InterruptedException {
		String sMidRule = StringUtils.substringBefore(Serenity.sessionVariableCalled("MidRuleVersion").toString(), ".")
				.trim();
		String sVersion = StringUtils.substringAfter(Serenity.sessionVariableCalled("MidRuleVersion").toString(), ".")
				.trim();
		if (isRefresh) {
			oGenericUtils.CLICK_LINK_XPATH(
					EditoialButton.replace("arg1", "Edit New Rule Description:").replace("arg2", "Refresh"));
			oGenericUtils.CLICK_LINK_XPATH("//h4[text()='Confirmation']/../..//button[text()='Cancel']");
			oGenericUtils.CLICK_LINK_XPATH(
					EditoialButton.replace("arg1", "Edit New Rule Description:").replace("arg2", "Refresh"));
			oGenericUtils.CLICK_LINK_XPATH("//h4[text()='Confirmation']/../..//button[text()='Refresh Data']");
			SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
			oGenericUtils.gfn_Verify_Object_Exist("div", "Successfully saved editorial updates");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
		}
		String sDBQuery = DBQueries.sQuery_Rule_Description("SUB_RULE_DESC", sMidRule, sVersion);
		String sDBData = DBUtils.executeSQLQuery(sDBQuery);
		String UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", "Rule Description:"));
		System.out.println("Rule Description:-" + UIText);
		GenericUtils.Verify("Rule Descritption:", UIText.trim().equalsIgnoreCase(sDBData.trim()));
		UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", "Edit New Rule Description:"));
		System.out.println("Edit New Rule Description:-" + UIText);
		GenericUtils.Verify("Edit New Rule Description:", UIText.trim().equalsIgnoreCase(sDBData.trim()));
		sDBQuery = DBQueries.sQuery_Rule_Description("SUB_RULE_DESC_RESOLVED", sMidRule, sVersion);
		sDBData = DBUtils.executeSQLQuery(sDBQuery);
		oGenericUtils.gfn_Click_On_Object("button", "Update Resolved Description");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		String resovedUIText = oSeleniumUtils
				.get_TextBy_Attribute(txtEditorial.replace("arg", "Resolved Rule Description:"));
		System.out.println("Resolved Rule Description:-" + resovedUIText);
		GenericUtils.Verify("Resolved Rule Description:", resovedUIText.trim().equalsIgnoreCase(sDBData.trim()));
		String DescCount = oSeleniumUtils
				.get_TextBy_Attribute("//div//label[text()='Resolved Rule Character Count:']/../..//input");
		GenericUtils.Verify("Character Count:", resovedUIText.length() == Integer.parseInt(DescCount));
	}

	public boolean fnModifyEditorialComments(String sAction, String sReviewType) throws InterruptedException {

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		String sMidRule = Serenity.sessionVariableCalled("MidRuleVersion").toString();
		String sInstance = Serenity.sessionVariableCalled("IUInstanceName").toString();
		String sDBQuery = DBQueries.sQuery_RuleDetails(sInstance, sMidRule, "IRD.INTERP_RULE_KEY");
		String sRefRuleId = DBUtils.executeSQLQuery(sDBQuery);
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		oGenericUtils.gfn_Click_On_Object("button", "Editorials");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		oGenericUtils.gfn_Verify_Object_Exist("h4", "Editorial Update");
		switch (sAction) {
		case "No Editorial Changes":
			if (sReviewType.equalsIgnoreCase("Preliminary PO Review")) {
				noEditorialChangesRequired(sRefRuleId);
			} else if (sReviewType.equalsIgnoreCase("Only Final PO Review")) {
				noEditorialChangesRequired(sRefRuleId);

			} else {
				oGenericUtils.gfn_Verify_Object_Exist("button", "Unset No Editorial Changes Required");
				oGenericUtils.gfn_Click_On_Object("button", "Complete Editorials");
				SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
				oGenericUtils.gfn_Click_On_Object("button", "Ok");
				sDBQuery = DBQueries.sQuery_Editorial_Status(sRefRuleId);
				String sRuleStatus = DBUtils.executeSQLQuery(sDBQuery);
				GenericUtils.Verify("Rule Status:", sRuleStatus.trim().equalsIgnoreCase("Completed"));
				oGenericUtils.CLICK_LINK_XPATH(closeEditorial);
			}
			break;
		case "Edit values":

			String sRule = StringUtils.substringBefore(Serenity.sessionVariableCalled("MidRuleVersion").toString(), ".")
					.trim();
			String sVersion = StringUtils
					.substringAfter(Serenity.sessionVariableCalled("MidRuleVersion").toString(), ".").trim();
			if (sReviewType.equalsIgnoreCase("Preliminary PO Review")) {
				saveEditorialComments(sRefRuleId, sReviewType);
				oGenericUtils.gfn_Click_On_Object("button", "Complete Editorials");
				SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
				oGenericUtils.gfn_Click_On_Object("button", "Ok");
				sDBQuery = DBQueries.sQuery_Editorial_Status(sRefRuleId);
				String sRuleStatus = DBUtils.executeSQLQuery(sDBQuery);
				GenericUtils.Verify("Rule Status:", sRuleStatus.trim().equalsIgnoreCase("Review Pending"));
				oGenericUtils.CLICK_LINK_XPATH(closeEditorial);

			} else if (sReviewType.equalsIgnoreCase("Only Final PO Review")) {
				String sEditText = "";
				editFinalMDEditorila(sRefRuleId, sRule, sVersion, sEditText, sReviewType);
			} else {
				String sEditText = "Test";
				editFinalMDEditorila(sRefRuleId, sRule, sVersion, sEditText, sReviewType);
			}
			break;
		}
		return true;
	}

	private void noEditorialChangesRequired(String sRefRuleId) throws InterruptedException {
		String sDBQuery;
		oGenericUtils.gfn_Click_On_Object("button", "No Editorial Changes Required");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		oGenericUtils.gfn_Verify_Object_Exist("div",
				"The No Change Required action loses all Editorial changes. Are you sure you want to continue?");
		oGenericUtils.gfn_Click_On_Object("button", "No");
		oGenericUtils.gfn_Click_On_Object("button", "No Editorial Changes Required");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		oGenericUtils.gfn_Click_On_Object("button", "Yes");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		// oGenericUtils.gfn_Verify_Object_Exist("div",
		// "Successfully saved editorial status:No Change Recommended");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		// oGenericUtils.gfn_Verify_Object_Exist("button", "Unset No Editorial
		// Changes Required");
		sDBQuery = DBQueries.sQuery_Editorial_Status(sRefRuleId);
		String sRuleStatus = DBUtils.executeSQLQuery(sDBQuery);
		// GenericUtils.Verify("Rule Status:",
		// sRuleStatus.trim().equalsIgnoreCase("No Change Required"));
		oGenericUtils.CLICK_LINK_XPATH(closeEditorial);
	}

	private void editFinalMDEditorila(String sRefRuleId, String sRule, String sVersion, String sEditText,
			String sReviewType) throws InterruptedException {
		String sDBQuery;
		oGenericUtils.gfn_Click_On_Object("span", "Description");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		String UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", "Edit New Rule Description:"));
		sDBQuery = DBQueries.sQuery_Rule_Description("SUB_RULE_DESC", sRule, sVersion);
		String sDBData = DBUtils.executeSQLQuery(sDBQuery);
		GenericUtils.Verify("Rule Status:", UIText.trim().equalsIgnoreCase(sDBData + sEditText));

		oGenericUtils.gfn_Click_On_Object("span", "Notes");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", "Edit Notes:"));
		sDBQuery = DBQueries.sQuery_Rule_Description("SUB_RULE_NOTES", sRule, sVersion);
		sDBData = DBUtils.executeSQLQuery(sDBQuery);
		GenericUtils.Verify("Rule Status:", UIText.trim().equalsIgnoreCase(sDBData + sEditText));
		oGenericUtils.gfn_Click_On_Object("span", "Script");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", "Edit Script:"));
		sDBQuery = DBQueries.sQuery_Rule_Description("SUB_RULE_SCRIPT", sRule, sVersion);
		sDBData = DBUtils.executeSQLQuery(sDBQuery);
		GenericUtils.Verify("Rule Status:", UIText.trim().equalsIgnoreCase(sDBData + sEditText));
		oGenericUtils.gfn_Click_On_Object("span", "Rationale");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", "Edit Rationale:"));
		sDBQuery = DBQueries.sQuery_Rule_Description("SUB_RULE_RATIONALE", sRule, sVersion);
		sDBData = DBUtils.executeSQLQuery(sDBQuery);
		GenericUtils.Verify("Rule Status:", UIText.trim().equalsIgnoreCase(sDBData + sEditText));
		oGenericUtils.gfn_Click_On_Object("span", "Reference");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", "Edit Reference:"));
		sDBQuery = DBQueries.sQuery_Rule_Description("REFERENCE", sRule, sVersion);
		sDBData = DBUtils.executeSQLQuery(sDBQuery);
		GenericUtils.Verify("Rule Status:", UIText.trim().equalsIgnoreCase(sDBData + sEditText));
		if (!sReviewType.equalsIgnoreCase("Only Final PO Review")) {
			oGenericUtils.gfn_Click_On_Object("button", "Approve Editorials");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
			oGenericUtils.gfn_Verify_Object_Exist("div", "Successfully saved editorial status:Approved");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			sDBQuery = DBQueries.sQuery_Editorial_Status(sRefRuleId);
			String sRuleStatus = DBUtils.executeSQLQuery(sDBQuery);
			GenericUtils.Verify("Rule Status:", sRuleStatus.trim().equalsIgnoreCase("Approved"));
		}
		saveEditorialComments(sRefRuleId, sReviewType);
		oGenericUtils.gfn_Click_On_Object("button", "Complete Editorials");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		sDBQuery = DBQueries.sQuery_Editorial_Status(sRefRuleId);
		String sRuleStatus = DBUtils.executeSQLQuery(sDBQuery);
		GenericUtils.Verify("Rule Status:", sRuleStatus.trim().equalsIgnoreCase("Completed"));
		oGenericUtils.CLICK_LINK_XPATH(closeEditorial);
	}

	private void saveEditorialComments(String sRefRuleId, String sReviewType) throws InterruptedException {
		oGenericUtils.gfn_Click_On_Object("span", "Description");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		String UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", "Rule Description:"));
		oSeleniumUtils.Enter_given_Text_Element(txtEditorial.replace("arg", "Edit New Rule Description:"),
				UIText + "Test");
		oGenericUtils.gfn_Click_On_Object("span", "Notes");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", "Edit Notes:"));
		oSeleniumUtils.Enter_given_Text_Element(txtEditorial.replace("arg", "Edit Notes:"), UIText + "Test");
		oGenericUtils.gfn_Click_On_Object("span", "Script");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", "Edit Script:"));
		oSeleniumUtils.Enter_given_Text_Element(txtEditorial.replace("arg", "Edit Script:"), UIText + "Test");
		oGenericUtils.gfn_Click_On_Object("span", "Rationale");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", "Edit Rationale:"));
		oSeleniumUtils.Enter_given_Text_Element(txtEditorial.replace("arg", "Edit Rationale:"), UIText + "Test");
		oGenericUtils.gfn_Click_On_Object("span", "Reference");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		UIText = oSeleniumUtils.get_TextBy_Attribute(txtEditorial.replace("arg", "Edit Reference:"));
		oSeleniumUtils.Enter_given_Text_Element(txtEditorial.replace("arg", "Edit Reference:"), UIText + "Test");
		oGenericUtils.CLICK_LINK_XPATH("//h4[text()='Editorial Update']/../..//button[text()='Save']");
		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
		oGenericUtils.gfn_Verify_Object_Exist("div", "Successfully saved editorial updates");
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		String sDBQuery = DBQueries.sQuery_Editorial_Status(sRefRuleId);
		String sRuleStatus = DBUtils.executeSQLQuery(sDBQuery);
		GenericUtils.Verify("Rule Status:", sRuleStatus.trim().equalsIgnoreCase("Reviewed"));

	}

	public boolean adds_a_code_with_Category_without_Override_flag(String arg1) throws InterruptedException {

		String[] str = { " Billed With (And) ", "Billed Without (Or)" };

		// List<String> oList=Arrays.asList(arg1.split(";"));

		for (int j = 0; j < str.length; j++) {

			Serenity.setSessionVariable("Category").to(str[j]);

			fnAddCode("D81.3", false, Serenity.sessionVariableCalled("Category"));
		}

		oGenericUtils.gfn_Click_String_object_Xpath(
				"//button[text()='Add Another Code']/following-sibling::button[text()='Save']");

		oGenericUtils.gfn_Verify_Object_Exist("div", "Successfully saved manual proposals.");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		GenericUtils.Verify("BW And/BWO or Config Logic link above system proposals",
				oGenericUtils.gfn_Click_On_Object("a", "BW And/BWO or Config Logic"));

		GenericUtils.Verify("Billed With And/Billed Without Or Code Clarification pop up",
				oGenericUtils.gfn_Verify_Object_Exist("h4", "Billed With And/Billed Without Or Code Clarification"));

		oGenericUtils.gfn_Verify_Object_Exist("b",
				"Codes are being added as Billed With And conditions as Non-Overrides. Clarify whether each code is to be included in an existing condition or added as a new additional condition.");

		oGenericUtils.gfn_Verify_Object_Exist("b",
				"Codes are being added as Billed WithOut Or conditions as Non-Overrides. Clarify whether each code is to be included in an existing condition or added as a new additional condition.");

		oGenericUtils.gfn_Verify_Object_Exist("td", "D81.3");

		oGenericUtils.gfn_Verify_String_Object_Exist("(//td[text()='D81.3'])[2]");

		/*
		 * oGenericUtils.gfn_Click_On_Object("button", "Submit");
		 * 
		 * oGenericUtils.gfn_Verify_Object_Exist("h4",
		 * "Error - No Review Codes Selection");
		 * 
		 * oGenericUtils.gfn_Verify_Object_Exist("div",
		 * "Please select all the review codes.");
		 * 
		 * oGenericUtils.gfn_Click_On_Object("button", "Ok");
		 */

		oGenericUtils.gfn_Click_String_object_Xpath("//label[contains(text(),'New Billed With And Condition')]//input");

		oGenericUtils
				.gfn_Click_String_object_Xpath("//label[contains(text(),'New Billed Without Or Condition')]//input");

		oGenericUtils.gfn_Click_On_Object("button", "Submit");

		oGenericUtils.gfn_Verify_Object_Exist("h4", "Success");

		oGenericUtils.gfn_Verify_Object_Exist("div", "Saved Successfully.");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		oGenericUtils.gfn_Click_On_Object("a", "BW And/BWO or Config Logic");

		oGenericUtils.gfn_Verify_String_Object_Exist(
				"//label[contains(text(),'New Billed With And Condition')]//input/following-sibling::span[.='(Updated By the User: iht_ittest01)']");

		oGenericUtils.gfn_Verify_String_Object_Exist(
				"//label[contains(text(),'New Billed Without Or Condition')]//input/following-sibling::span[.='(Updated By the User: iht_ittest01)']");

		oGenericUtils.gfn_Click_On_Object("button", "Submit");

		oGenericUtils.gfn_Verify_Object_Exist("h4", "Success");

		oGenericUtils.gfn_Verify_Object_Exist("div", "Saved Successfully.");

		boolean bln = oGenericUtils.gfn_Click_On_Object("button", "Ok");

		return bln;

	}

	public void click_on_button_on_WorkQueue(String sButton) throws InterruptedException {

		oGenericUtils.gfn_Click_On_Object("button", sButton);

	}

	public void retrive_code_from_System_Proposals(String sProposalType) throws InterruptedException {

		SeleniumUtils.defaultWait(3000);

		oGenericUtils.gfn_Verify_String_Object_Exist("//th[@ng-reflect-ng-switch='proposalType']//input");

		oSeleniumUtils.Enter_given_Text_Element("//th[@ng-reflect-ng-switch='proposalType']//input", sProposalType);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);

		String sSIMCodes = oSeleniumUtils
				.get_TextFrom_Locator("((//td[text()=' SIM '])[1]/following-sibling::td//span)[2]");

		Serenity.setSessionVariable("SIMCode").to(sSIMCodes);

		String sDiagCode = oSeleniumUtils.get_TextFrom_Locator("(//td[text()=' SIM '])[1]/following::td[4]");

		String sReviewCode = oSeleniumUtils.get_TextFrom_Locator("(//td[text()=' SIM '])[1]/following::td[2]");

		// Serenity.setSessionVariable("SIMCode").to(StringUtils.substringBefore(sDiagCode,
		// "-"));

		Serenity.setSessionVariable("DiagCode").to(sDiagCode);

		Serenity.setSessionVariable("ReviewCode").to(sReviewCode);

		String sCategory = oSeleniumUtils.get_TextFrom_Locator("(//td[text()=' SIM '])[1]/following::td[5]");

		Serenity.setSessionVariable("Category").to(sCategory);

		/*
		 * String sSIMCodes1=oSeleniumUtils.get_TextFrom_Locator(
		 * "((//td[text()=' SIM '])[2]/following-sibling::td//span)[1]");
		 * 
		 * Serenity.setSessionVariable("SIMCode1").to(sSIMCodes1);
		 * 
		 * String sDiagCode1=oSeleniumUtils.get_TextFrom_Locator(
		 * "(//td[text()=' SIM '])[2]/following::td[4]");
		 * 
		 * Serenity.setSessionVariable("DiagCode1").to(sDiagCode1);
		 * 
		 * String sCategory1=oSeleniumUtils.get_TextFrom_Locator(
		 * "(//td[text()=' SIM '])[2]/following::td[5]");
		 * 
		 * Serenity.setSessionVariable("Category1").to(sCategory1);
		 */

		List<WebElement> olist = getDriver().findElements(
				By.xpath("//h5[.=' Manual Proposals ']/./../../..//select[@ng-reflect-is-disabled='true']"));

		if (olist.size() > 0) {

			oGenericUtils.gfn_Click_String_object_Xpath(
					"//th[text()='Associated with Update Instance']/preceding-sibling::th//p-tableheadercheckbox");

			oGenericUtils.gfn_Click_On_Object("button", "Delete Selected");

			oGenericUtils.gfn_Verify_Object_Exist("div", "Are you sure you want to delete selected rows?");

			oGenericUtils.gfn_Click_On_Object("button", "Yes");

			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			oGenericUtils.gfn_Click_String_object_Xpath(
					"//button[text()='Add Another Code']/following-sibling::button[text()='Save']");

			oGenericUtils.gfn_Verify_Object_Exist("div", "Successfully saved manual proposals.");

			oGenericUtils.gfn_Click_On_Object("button", "Ok");
		}

	}

	public boolean user_should_be_able_to_enter_the_code_that_they_want_to_remove_in_the_pop_up()
			throws InterruptedException {

		boolean bln = false;

		bln = enter_Data_GenerateRemove(Serenity.sessionVariableCalled("SIMCode"),
				Serenity.sessionVariableCalled("Category"), Serenity.sessionVariableCalled("DiagCode"), "Related");

		bln = enter_Data_GenerateRemove(Serenity.sessionVariableCalled("SIMCode"),
				Serenity.sessionVariableCalled("Category"), Serenity.sessionVariableCalled("DiagCode"), "Not Related");

		oGenericUtils.gfn_Click_String_object_Xpath("//h4[.='Generate Remove Code Proposal']/..//i");

		SeleniumUtils.defaultWait(5000);

		SeleniumUtils.scrollingToGivenElement(getDriver(),
				"//h5[.=' Manual Proposals ']/./../../..//select[@ng-reflect-is-disabled='true']");

		verify_Fields_DisabledMode("select");

		verify_Fields_DisabledMode("textarea");

		verify_Fields_DisabledMode("input");

		return bln;
	}

	private boolean enter_Data_GenerateRemove(Object sessionVariableCalled, Object sessionVariableCalled2,
			Object sessionVariableCalled3, String string) throws InterruptedException {

		oSeleniumUtils.Enter_given_Text_Element(
				"//div[text()='Enter the Code to be removed and click search :']/..//input",
				sessionVariableCalled.toString());

		oGenericUtils.gfn_Click_On_Object("button", " Search ");

		oSeleniumUtils.Enter_given_Text_Element("//div[text()='Rationale Comment: ']/following-sibling::div//textarea",
				"AutoTest" + string);

		oGenericUtils.gfn_Click_String_object_Xpath("//label[text()=' " + string + " to Update ']/..//input");

		oGenericUtils.gfn_Click_String_object_Xpath("//span[text()=' " + sessionVariableCalled + " ']/..//i");

		oGenericUtils.gfn_Click_String_object_Xpath("//span[text()=' " + sessionVariableCalled2 + " ']/..//i");

		if (!sessionVariableCalled3.toString().contains("-")) {

			sessionVariableCalled3 = Serenity.sessionVariableCalled("ReviewCode").toString() + "-"
					+ Serenity.sessionVariableCalled("ReviewCode").toString();
		}

		String sXpath = "//span[contains(text(),'" + sessionVariableCalled3 + "')]/..//p-treetablecheckbox";

		System.out.println(sXpath);

		oGenericUtils.gfn_Click_String_object_Xpath(sXpath);

		boolean bln = oGenericUtils.gfn_Click_On_Object("button", "Generate Remove Code Proposals");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		oGenericUtils.gfn_Verify_Object_Exist("h4", "Success");

		oGenericUtils.gfn_Verify_Object_Exist("div", "Manual proposal is created successfully");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		return bln;

	}

	private void verify_Fields_DisabledMode(String sTag) throws InterruptedException {

		List<WebElement> olist = getDriver().findElements(
				By.xpath("//h5[.=' Manual Proposals ']/./../../..//" + sTag + "[@ng-reflect-is-disabled='true']"));

		for (int i = 0; i < olist.size(); i++) {

			oGenericUtils.gfn_Verify_String_Object_Exist("(//h5[.=' Manual Proposals ']/./../../..//" + sTag
					+ "[@ng-reflect-is-disabled='true'])[" + (i + 1) + "]");
		}

	}

	public void the_ICD_ARD_link_appears_beside_the_Potential_Conflict_button_when_the_rule_is_defined_with_an_ARD()
			throws InterruptedException {

		SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);

		boolean bln = oGenericUtils.gfn_Click_On_Object("a", "ICD ARD ");

		GenericUtils.Verify("ICD ARD LINK is cliced", bln);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);

	}

	public boolean user_can_click_on_the_link_and_review_the_ARD_setup_for_the_rule() throws Exception {

		SeleniumUtils.switchWindowUsingWindowCount(2, 3, getDriver());

		SeleniumUtils oSeleniumUtils = this.switchToPage(SeleniumUtils.class);

		oSeleniumUtils.highlightElement(BuilderARD_UserName);

		BuilderARD_UserName.withTimeoutOf(ProjectVariables.MIN_TIME_OUT, TimeUnit.SECONDS).waitUntilVisible().clear();

		BuilderARD_UserName.waitUntilVisible().sendKeys(ProjectVariables.USER_NAME);

		// Enter Password
		oSeleniumUtils.highlightElement(BuilderARD_Password);
		BuilderARD_Password.waitUntilVisible().clear();

		BuilderARD_Password.waitUntilVisible().sendKeys(GenericUtils.decode(ProjectVariables.PASSWORD));

		oSeleniumUtils.Click_given_WebElement(SignIn_Btn);

		boolean bln = oGenericUtils.gfn_Verify_String_Object_Exist(
				"//span[contains(text(),'" + Serenity.sessionVariableCalled("MidRuleVersion").toString() + "')]");

		GenericUtils.Verify("Rule version is dispalyed in Builder ARD APP", bln);

		return true;

	}

	public void click_on_in_WorkQueue(String sButton) throws InterruptedException {

		boolean blnFlg = false;

		switch (sButton) {
		case "Copy Decision":

			oSeleniumUtils.Enter_given_Text_Element("//th[@ng-reflect-ng-switch='proposalType']//input", "SIM");

			boolean bln1 = oSeleniumUtils.is_WebElement_Displayed(
					"//div[text()='All entered decisions are saved successfully. There are no modified decisions/rational comments to save.']");

			if (bln1 == true) {

				oGenericUtils.gfn_Click_On_Object("button", "Ok");
			}

			oSeleniumUtils.Enter_given_Text_Element(oCommonPage.SystemProposalCommments, "Copy Decision");

			oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(oCommonPage.SystemProposalDecision,
					"No Action");

			SeleniumUtils.defaultWait(4000);

			oGenericUtils.gfn_Click_String_object_Xpath(
					"(//div[contains(@class,'ui-chkbox-box ui-widget ui-state-default')])[2]/../..");

			boolean bln = oSeleniumUtils.is_WebElement_Selected(
					"(//div[contains(@class,'ui-chkbox-box ui-widget ui-state-default')])[2]/../..");

			if (bln == false) {

				oSeleniumUtils.Click_given_Locator(
						"(//div[contains(@class,'ui-chkbox-box ui-widget ui-state-default')])[2]/../..");

			}

			blnFlg = oGenericUtils.gfn_Click_On_Object("button", sButton);

			GenericUtils.Verify("Copy Decisions is clicked", blnFlg);

			blnFlg = oGenericUtils.gfn_Verify_Object_Exist("h4", "Copy Decision");

			GenericUtils.Verify("Copy Decision Popup header label is displayed", blnFlg);

			blnFlg = oGenericUtils.gfn_Verify_Object_Exist("label", "Copy Decisions:");

			GenericUtils.Verify("Copy Decision: label is displayed", blnFlg);

			blnFlg = oSeleniumUtils.is_WebElement_Selected("//span[text()='Copy Decision']/preceding-sibling::input");

			GenericUtils.Verify("Copy Descision is selected", blnFlg);

			blnFlg = oSeleniumUtils
					.is_WebElement_Selected("//span[text()='Copy Rationale Comment']/preceding-sibling::input");

			GenericUtils.Verify("Copy Rationale Comment is selected", blnFlg);

			blnFlg = !oSeleniumUtils.is_WebElement_Selected(
					"//span[text()='Select all proposals for the selected Proposal Type']/preceding-sibling::input");

			GenericUtils.Verify("Select all proposals for the selected Proposal Type not selected", blnFlg);

			oGenericUtils.gfn_Click_String_object_Xpath("//span[text()='Copy Decision']/preceding-sibling::input");

			oGenericUtils
					.gfn_Click_String_object_Xpath("//span[text()='Copy Rationale Comment']/preceding-sibling::input");

			oGenericUtils.gfn_Click_String_object_Xpath(
					"//span[text()='Select all proposals for the selected Proposal Type']/preceding-sibling::input");

			GenericUtils.Verify("Copy Descision not selected",
					!oSeleniumUtils.is_WebElement_Selected("//span[text()='Copy Decision']/preceding-sibling::input"));

			GenericUtils.Verify("Copy Rationale Comment not selected", !oSeleniumUtils
					.is_WebElement_Selected("//span[text()='Copy Rationale Comment']/preceding-sibling::input"));

			GenericUtils.Verify("Select all proposals for the selected Proposal Type is selected",
					oSeleniumUtils.is_WebElement_Selected(
							"//span[text()='Select all proposals for the selected Proposal Type']/preceding-sibling::input"));

			String sPopText = " Please select a single proposal from the list to copy and click \"Copy Decision\". Then select the proposals on the list where the copied proposal will be pasted and click \"Paste Decision\". ";

			System.out.println(sPopText);

			String sUIText = oSeleniumUtils
					.get_TextFrom_Locator("//h4[text()='Copy Decision']/../..//mat-dialog-content");

			System.out.println(sUIText);

			GenericUtils.Verify("", sPopText.trim().equalsIgnoreCase(sUIText.trim()));

			oGenericUtils.gfn_Click_String_object_Xpath("//span[text()='Copy Decision']/preceding-sibling::input");

			oGenericUtils
					.gfn_Click_String_object_Xpath("//span[text()='Copy Rationale Comment']/preceding-sibling::input");

			oGenericUtils.gfn_Click_String_object_Xpath(
					"//h4[text()='Copy Decision']//../..//button[text()='Copy Decision']");

			blnFlg = oGenericUtils.gfn_Click_On_Object("button", "Paste Decision");

			GenericUtils.Verify("Paste Decisions is clicked", blnFlg);

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

			SeleniumUtils.defaultWait(7000);

			oGenericUtils.gfn_Verify_String_Object_Exist(
					"((//h5[.=' System Proposals ']/../../..//textarea[@ng-reflect-model='Copy Decision']))[2]");

			break;

		default:
			break;
		}

	}

	public boolean editorialWindow() {

		int iEditorialWarning = getDriver().findElements(By.xpath("//h4[text()='Editorial Update']")).size();

		if (iEditorialWarning > 0) {

			oSeleniumUtils.Click_given_Locator(CloseBulkDecision);
		}

		return true;

	}

	public boolean CompleteEditorialReview(String sDecision) throws InterruptedException {
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);
		if (getDriver().findElements(By.xpath(closeEditorial)).size() > 0)
			oGenericUtils.CLICK_LINK_XPATH(closeEditorial);
		SeleniumUtils.defaultWait(1000);
		List<WebElement> sList = getDriver()
				.findElements(By.xpath("//th[text()=' Editorial Review Segment ']/../../..//td/../..//a"));
		String arrDecision[] = new String[sList.size()];
		for (int i = 1; i <= sList.size(); i++) {
			arrDecision[i - 1] = oSeleniumUtils.get_StringTextFrom_Locator(
					"(//th[text()=' Editorial Review Segment ']/../../..//td/../..//a)[" + i + "]");
		}
		for (int i = 0; i < arrDecision.length; i++) {
			if (!arrDecision[i].trim().equals("Editorials")) {
				oSeleniumUtils.Enter_given_Text_Element(EditorialComments.replace("arg", arrDecision[i]), "Test");
				oGenericUtils.gfn_Click_On_Object("button", "Save");
				SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
				oGenericUtils.gfn_Verify_Object_Exist("div", " Editorial review comments successfully saved. ");
				oGenericUtils.gfn_Click_On_Object("button", "Save");
				oGenericUtils.gfn_Verify_Object_Exist("div", " Please select at least one row to save. ");
				String sStatus = oSeleniumUtils
						.get_StringTextFrom_Locator(EditorialReviewStatus.replace("arg", arrDecision[i]));
				if (sStatus.equals("Not Completed")) {
					String Sval = arrDecision[i];
					oGenericUtils.gfn_Click_On_Object("a", arrDecision[i]);
					if (Sval.trim().equals("Additional Configuration"))
						Sval = "Configuration Summary";
					if (arrDecision[i].trim().equals("Impact Code List"))
						oGenericUtils
								.gfn_Click_String_object_Xpath("//th[text()=' Review Code']//p-tableheadercheckbox");
					SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
					if (Sval.trim().equals("Configuration Summary")) {
						if (getDriver().findElements(By.xpath("//button[text()=' Complete Editorial Review ']"))
								.size() == 1)
							oGenericUtils
									.gfn_Click_String_object_Xpath("//button[text()=' Complete Editorial Review ']");
						else {
							oGenericUtils.gfn_Click_String_object_Xpath(btnCompleteDecision
									.replace("arg",
											Sval.replace("PO ", "").replace("Configuration", "Configurations")
													.replace("CPM ", ""))
									.replace("Additional Configuration", "Configurations Summary"));
						}
					} else {

						oGenericUtils.gfn_Click_String_object_Xpath(btnCompleteDecision
								.replace("arg",
										Sval.replace("PO ", "").replace("Configuration", "Configurations")
												.replace("CPM ", ""))
								.replace("Additional Configuration", "Configurations Summary"));
					}
					SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
					oGenericUtils.gfn_Click_On_Object("button", "Ok");
					SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
					sStatus = oSeleniumUtils
							.get_StringTextFrom_Locator(EditorialReviewStatus.replace("arg", arrDecision[i]));
					GenericUtils.Verify(arrDecision[i], sStatus.trim().equalsIgnoreCase("Completed"));
				} else
					GenericUtils.Verify(arrDecision[i], sStatus.trim().equalsIgnoreCase("Completed"));
			}
		}

		oGenericUtils.gfn_Click_On_Object("a", "Editorials");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		if (sDecision.trim().equals("No Editorial Changes Required")) {
			oGenericUtils.gfn_Click_On_Object("button", sDecision);
			SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
			oGenericUtils.gfn_Click_On_Object("button", "Yes");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		}
		oGenericUtils.gfn_Click_On_Object("button", "Complete Editorials");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		oGenericUtils.gfn_Click_On_Object("button", "Yes");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		return true;
	}

	public boolean verifySystemProposalDescription() throws InterruptedException {
		String sICDCode = oSeleniumUtils
				.get_TextFrom_Locator("(//th[text()=' Review Code ']/../../tr/../..//td)[11]/span");
		String sDBQuery = DBQueries.sQuery_ICDCodeDesc(sICDCode);
		String sDesc = DBUtils.executeSQLQuery(sDBQuery);
		oGenericUtils.gfn_Click_String_object_Xpath("//b[text()='Show Descriptions']/../input");
		GenericUtils.Verify("ICD Full Description",
				oSeleniumUtils.get_TextFrom_Locator("(//th[text()=' Review Code ']/../../tr/../..//td)[11]/span").trim()
						.equalsIgnoreCase("[" + sICDCode + "] " + sDesc));
		return true;
	}

	public boolean complete_the_Review(String sReview) throws InterruptedException {
		if (sReview.equals("ADDCODE")) {
			oGenericUtils.gfn_Verify_String_Object_Exist("(//textarea[@ng-reflect-model='Test Comments'])[1]");
			oGenericUtils.gfn_Verify_String_Object_Exist("(//span[text()='D55.1'])[1]");
			oGenericUtils.gfn_Verify_String_Object_Exist("(//span[text()='PRIMARY'])[1]");
		} else if (sReview.equals("COMPLETE")) {
			SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
			oGenericUtils.gfn_Click_On_Object("button", "CPM Decision Complete");
			oGenericUtils.gfn_Verify_Object_Exist("div",
					"Please select CPM Decision for all proposals. CPM Decision should not be Blank or No Decision.");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			List<WebElement> iMidRuleCount = getDriver()
					.findElements(By.xpath("//h5[.=' CPM System Proposals ']/../../..//select"));
			System.out.println(iMidRuleCount.size());
			for (int i = 1; i <= iMidRuleCount.size(); i += 2) {
				String sXpath = "(//h5[.=' CPM System Proposals ']/../../..//select)" + "[" + i + "]";
				System.out.println(sXpath);
				oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(sXpath, "No Action");
				oSeleniumUtils.Enter_given_Text_Element(
						"(//h5[.=' CPM System Proposals ']/../../..//textarea)" + "[" + i + "]",
						ProjectVariables.SystemProposalComments);

			}
			oGenericUtils.gfn_Click_String_object_Xpath("(//button[text()='Save'])[1]");
			oGenericUtils.gfn_Verify_Object_Exist("div", "Successfully saved proposal decisions.");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			for (int i = 1; i <= iMidRuleCount.size(); i += 2) {
				String sXpath = "(//h5[.=' CPM System Proposals ']/../../..//select)" + "[" + i + "]";
				System.out.println(sXpath);
				oGenericUtils.gfn_Verify_String_Object_Exist(sXpath);
				oGenericUtils.gfn_Verify_String_Object_Exist(
						"(//h5[.=' CPM System Proposals ']/../../..//textarea)" + "[" + i + "]");

			}
		} else if (sReview.equals("RMR")) {
			oGenericUtils.gfn_Click_String_object_Xpath(btnCPMConfig);
			SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
			oGenericUtils.gfn_Click_String_object_Xpath("//i[@class='pi pi-chevron-right']");
			List<WebElement> sPayers = getDriver().findElements(
					By.xpath("//h5[text()=' Additional Configuration ']/../../..//select[not(@disabled)]"));
			for (int i = 1; i <= sPayers.size(); i++) {
				String sXpath = "(//h5[text()=' Additional Configuration ']/../../..//select[not(@disabled)])" + "[" + i
						+ "]";
				if (i == 1)
					oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(sXpath, "Agree");
				else
					oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(sXpath, "Disagree");
				oSeleniumUtils.Enter_given_Text_Element(
						"(//th[text()='CPM Decision']/../../..//textarea[not(@disabled)])" + "[" + i + "]", "Test");
			}
			oGenericUtils.gfn_Click_String_object_Xpath("(//button[text()='CPM Decision Complete'])[2]");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
			oGenericUtils.gfn_Click_On_Object("button", "CPM Decision Complete");
			oGenericUtils.gfn_Click_On_Object("button", "Yes");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
		} else {
			if (getDriver().findElements(By.xpath(btnCPMConfig)).size() > 0) {
				oGenericUtils.gfn_Click_String_object_Xpath(btnCPMConfig);
				SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
				oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
						"//th[text()='CPM Decision']/../../..//select", "Agree");
				oSeleniumUtils.Enter_given_Text_Element("//th[text()='CPM Decision']/../../..//textarea", "Test");
				oGenericUtils.gfn_Click_String_object_Xpath("(//button[text()='CPM Decision Complete'])[2]");
				oGenericUtils.gfn_Click_On_Object("button", "Ok");
			}
			if (getDriver().findElements(By.xpath(btnCPMRetire)).size() > 0) {
				oGenericUtils.gfn_Click_String_object_Xpath(btnCPMRetire);
				SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
				oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(
						"(//select[@id='ddlMDDecision'])[1]", "AGREE");
				oGenericUtils.gfn_Click_String_object_Xpath(
						"//div[text()='CPM Decision:']/../../..//button[text()='CPM Decision Complete']");
				oGenericUtils.gfn_Click_On_Object("button", "Ok");
				SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
			}
			oGenericUtils.gfn_Click_On_Object("button", "CPM Decision Complete");
			oGenericUtils.gfn_Click_On_Object("button", "Yes");
			oGenericUtils.gfn_Click_On_Object("button", "Ok");
		}
		return true;
	}

	public boolean validateRuleStatus(String sStatus) throws InterruptedException {
		String sMidRule = Serenity.sessionVariableCalled("MidRuleVersion").toString();
		String sInstance = Serenity.sessionVariableCalled("IUInstanceName").toString();
		Serenity.setSessionVariable("IUInstanceName").to(sInstance);
		String sDBQuery = DBQueries.sQuery_Rule_Status(sInstance, sMidRule, "TASK_STATUS_DESC");
		String sUIstatus = DBUtils.executeSQLQuery(sDBQuery);
		return sStatus.equals(sUIstatus);
	}

	public boolean should_be_displayed_with_Full_description_along_with_code_under_Sim_Codes_and_Review_codes()
			throws InterruptedException {

		boolean blnFlg = false;

		oGenericUtils.gfn_Verify_String_Object_Exist("(//th[text()=' Review Code ']/../../tr/../..//td)[8]/span");

		String sICDCode = oSeleniumUtils
				.get_TextFrom_Locator("(//th[text()=' Review Code ']/../../tr/../..//td)[8]/span");

		String sDBQuery = DBQueries.sQuery_ICDCodeDesc(sICDCode);

		String sDesc = DBUtils.executeSQLQuery(sDBQuery);

		SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);

		blnFlg = oGenericUtils.gfn_Click_String_object_Xpath("//label[text()='Show Descriptions']/../input");

		SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);

		String sICDCodeDescription = oSeleniumUtils
				.get_TextFrom_Locator("(//th[text()=' Review Code ']/../../tr/../..//td)[8]/span");

		GenericUtils.Verify("ICD Full Description",
				sICDCodeDescription.trim().equalsIgnoreCase("[" + sICDCode + "] " + sDesc));

		return blnFlg;

	}

	public void verify_rule_Status_in_DB_for_User(String sStatus, String sUser) throws Exception {

		String sQuery = "    SELECT DISTINCT U.USER_ID,PAYERS_4_RULE,TASK_TYPE_DESC,TASK_STATUS_DESC,U.USER_ID FROM IRDM.INTERP_RULES IR JOIN IRDM.INTERP_RULE_DETAILS IRD ON IR.INTERP_RULE_KEY = IRD.INTERP_RULE_KEY JOIN IPDE.TASK_DETAILS TD ON TD.REFERENCE_RULE_ID = IR.INTERP_RULE_KEY\r\n"
				+ "     LEFT JOIN IPDE.CPM_TASK_DETAILS CTD ON TD.TASK_DETAIL_KEY = CTD.TASK_DETAIL_KEY JOIN IPDE.TASK_TYPE_LKP TTL ON TTL.TASK_TYPE_KEY = CASE WHEN TD.TASK_TYPE_KEY = 16 THEN CTD.TASK_TYPE_KEY  ELSE TD.TASK_TYPE_KEY \r\n"
				+ "     END JOIN IPDE.TASK_STATUS_LKP TSL ON TSL.TASK_STATUS_KEY = CASE WHEN TD.TASK_TYPE_KEY = 16 THEN CTD.TASK_STATUS_KEY ELSE TD.TASK_STATUS_KEY END  JOIN IPDE.USERS U  ON U.USER_KEY = CASE WHEN TD.TASK_TYPE_KEY = 16 \r\n"
				+ "     THEN CTD.USER_KEY ELSE TD.TASK_USER_KEY END WHERE IR.IMPACT_KEY IN (SELECT IMPACT_KEY FROM IRDM.INTERP_IMPACTS II JOIN IRDM.UPDATE_INSTANCES I ON I.UPDATE_INSTANCE_KEY = II.UPDATE_INSTANCE_KEY\r\n"
				+ "    WHERE UPDATE_INSTANCE_NAME = '" + Serenity.sessionVariableCalled("IUInstanceName").toString()
				+ "') AND IRD.MID_RULE_DOT_VERSION  ='" + Serenity.sessionVariableCalled("MidRuleVersion").toString()
				+ "' AND U.USER_ID='" + sUser + "'  \r\n";

		DBUtils.db_GetFirstValueforColumn(sQuery, "TASK_STATUS_DESC");

		System.out.println(DBUtils.db_GetFirstValueforColumn(sQuery, "TASK_STATUS_DESC"));

		GenericUtils.Verify("DB Value is matched",
				sStatus.trim().equalsIgnoreCase(DBUtils.db_GetFirstValueforColumn(sQuery, "TASK_STATUS_DESC").trim()));
	}

	public boolean CompleteTestingReview(String sDecision) throws InterruptedException {

		List<WebElement> sList = getDriver()
				.findElements(By.xpath("//th[text()=' Review Segment ']/../../..//td/../..//a"));

		String arrDecision[] = new String[sList.size()];
		for (int i = 1; i <= sList.size(); i++) {
			arrDecision[i - 1] = oSeleniumUtils
					.get_StringTextFrom_Locator("(//th[text()=' Review Segment ']/../../..//td/../..//a)[" + i + "]");
		}
		for (int i = 0; i < arrDecision.length; i++) {
			if (!arrDecision[i].trim().equals("Editorials")) {
				oSeleniumUtils.Enter_given_Text_Element(EditorialComments.replace("arg", arrDecision[i]), "Test t");
				oGenericUtils.gfn_Click_On_Object("button", "Save");
				SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
				oGenericUtils.gfn_Click_On_Object("button", "Ok");
				oGenericUtils.gfn_Click_On_Object("button", "Save");
				oGenericUtils.gfn_Verify_Object_Exist("div", " Please select at least one row to save. ");
				String sStatus = oSeleniumUtils
						.get_StringTextFrom_Locator(EditorialReviewStatus.replace("arg", arrDecision[i]));
				if (sStatus.equals("Not Completed")) {
					String Sval = arrDecision[i];
					oGenericUtils.gfn_Click_On_Object("a", arrDecision[i]);
					if (Sval.trim().equals("Additional Configuration"))
						Sval = "Configuration Summary";
					if (arrDecision[i].trim().equals("Impact Code List"))
						oGenericUtils
								.gfn_Click_String_object_Xpath("//th[text()=' Review Code']//p-tableheadercheckbox");
					SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
					if (Sval.trim().equals("Configuration Summary")) {
						if (getDriver().findElements(By.xpath("//button[text()=' Complete Test Review ']")).size() == 1)
							oGenericUtils.gfn_Click_String_object_Xpath("//button[text()=' Complete Test Review ']");
						else {
							oGenericUtils.gfn_Click_String_object_Xpath(btnCompleteDecision
									.replace("arg",
											Sval.replace("PO ", "").replace("Configuration", "Configurations")
													.replace("CPM ", ""))
									.replace("Additional Configuration", "Configurations Summary"));
						}
					} else {

						oGenericUtils.gfn_Click_String_object_Xpath(btnCompleteQA.replace("QA", "Test")
								.replace("arg",
										Sval.replace("PO ", "").replace("Configuration", "Configurations")
												.replace("CPM ", ""))
								.replace("Additional Configuration", "Configurations Summary"));
					}
					SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
					oGenericUtils.gfn_Click_On_Object("button", "Ok");
					SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
					sStatus = oSeleniumUtils
							.get_StringTextFrom_Locator(EditorialReviewStatus.replace("arg", arrDecision[i]));
					GenericUtils.Verify(arrDecision[i], sStatus.trim().equalsIgnoreCase("Completed"));
				} else
					GenericUtils.Verify(arrDecision[i], sStatus.trim().equalsIgnoreCase("Completed"));
			}
		}

		oGenericUtils.gfn_Click_On_Object("a", "Editorials");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		oGenericUtils.gfn_Click_On_Object("button", "Complete Testing Review");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		if (getDriver().findElements(By.xpath(closeEditorial)).size() > 0)
			oGenericUtils.CLICK_LINK_XPATH(closeEditorial);
		String sCode = oSeleniumUtils.get_StringTextFrom_Locator("//label[text()='Stub RMR ID: ']/following::label");
		oGenericUtils.gfn_Click_On_Object("button", "Testing Complete");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		oSeleniumUtils.Enter_given_Text_Element("//label[text()='Testing Comments:']/../textarea", "Testing comments");
		oGenericUtils.gfn_Click_On_Object("button", "Testing Completed");
		oGenericUtils.gfn_Click_On_Object("button", "Continue");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);
		fnValidateDetailswithDB(sCode);
		return true;
	}

	public boolean CompleteQAReview(String sDecision) throws InterruptedException {
		List<WebElement> sList = getDriver()
				.findElements(By.xpath("//th[text()=' Review Segment ']/../../..//td/../..//a"));

		String arrDecision[] = new String[sList.size()];
		for (int i = 1; i <= sList.size(); i++) {
			arrDecision[i - 1] = oSeleniumUtils
					.get_StringTextFrom_Locator("(//th[text()=' Review Segment ']/../../..//td/../..//a)[" + i + "]");
		}
		for (int i = 0; i < arrDecision.length; i++) {
			if (!arrDecision[i].trim().equals("Editorials")) {
				oSeleniumUtils.Enter_given_Text_Element(EditorialComments.replace("arg", arrDecision[i]), "Test t");
				oGenericUtils.gfn_Click_On_Object("button", "Save");
				SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
				oGenericUtils.gfn_Verify_Object_Exist("div", " QA review comments successfully saved. ");
				oGenericUtils.gfn_Click_On_Object("button", "Save");
				oGenericUtils.gfn_Verify_Object_Exist("div", " Please select at least one row to save. ");
				String sStatus = oSeleniumUtils
						.get_StringTextFrom_Locator(EditorialReviewStatus.replace("arg", arrDecision[i]));
				if (sStatus.equals("Not Completed")) {
					String Sval = arrDecision[i];
					oGenericUtils.gfn_Click_On_Object("a", arrDecision[i]);
					if (Sval.trim().equals("Additional Configuration"))
						Sval = "Configuration Summary";
					if (arrDecision[i].trim().equals("Impact Code List"))
						oGenericUtils
								.gfn_Click_String_object_Xpath("//th[text()=' Review Code']//p-tableheadercheckbox");
					SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
					if (Sval.trim().equals("Configuration Summary")) {
						if (getDriver().findElements(By.xpath("//button[text()=' Complete QA Review ']")).size() == 1)
							oGenericUtils.gfn_Click_String_object_Xpath("//button[text()=' Complete QA Review ']");
						else {
							oGenericUtils.gfn_Click_String_object_Xpath(btnCompleteDecision
									.replace("arg",
											Sval.replace("PO ", "").replace("Configuration", "Configurations")
													.replace("CPM ", ""))
									.replace("Additional Configuration", "Configurations Summary"));
						}
					} else {

						oGenericUtils.gfn_Click_String_object_Xpath(btnCompleteQA
								.replace("arg",
										Sval.replace("PO ", "").replace("Configuration", "Configurations")
												.replace("CPM ", ""))
								.replace("Additional Configuration", "Configurations Summary"));
					}
					SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
					oGenericUtils.gfn_Click_On_Object("button", "Ok");
					SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
					sStatus = oSeleniumUtils
							.get_StringTextFrom_Locator(EditorialReviewStatus.replace("arg", arrDecision[i]));
					GenericUtils.Verify(arrDecision[i], sStatus.trim().equalsIgnoreCase("Completed"));
				} else
					GenericUtils.Verify(arrDecision[i], sStatus.trim().equalsIgnoreCase("Completed"));
			}
		}

		oGenericUtils.gfn_Click_On_Object("a", "Editorials");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		oGenericUtils.gfn_Click_On_Object("button", "Complete QA Review");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		if (getDriver().findElements(By.xpath(closeEditorial)).size() > 0)
			oGenericUtils.CLICK_LINK_XPATH(closeEditorial);
		oGenericUtils.gfn_Click_On_Object("button", "Update Rule");
		AdminPage oAdminPage = this.switchToPage(AdminPage.class);
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		oGenericUtils.gfn_Click_On_Object("button", "QA Review Completed");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		oGenericUtils.gfn_Click_On_Object("button", "Ok");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		return true;
	}

	private void fnValidateDetailswithDB(String sCode) {
		String sMidRule = StringUtils.substringBefore(Serenity.sessionVariableCalled("MidRuleVersion").toString(), ".")
				.trim();
		String sVersion = StringUtils.substringAfter(Serenity.sessionVariableCalled("MidRuleVersion").toString(), ".")
				.trim();
		String sDBQuery = DBQueries.sQuery_TestingRule_Details(sMidRule, sVersion, "TEST_DESC");
		String sTestDec = DBUtils.executeSQLQuery(sDBQuery);
		String sUserID = Serenity.sessionVariableCalled("User").toString();
		GenericUtils.Verify("Test Desc", sTestDec.trim().equalsIgnoreCase(sUserID + " - Testing comments"));
		sDBQuery = DBQueries.sQuery_TestingRule_Details(sMidRule, sVersion, "USERNAME");
		String sUserName = DBUtils.executeSQLQuery(sDBQuery);
		GenericUtils.Verify("User Name", sUserName.trim().equalsIgnoreCase("USER_NOTES"));
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);

		// String sCode =
		// oSeleniumUtils.get_StringTextFrom_Locator("//label[text()='Stub RMR ID:
		// ']/following::label");

		// sDBQuery = DBQueries.sQuery_RMR_Status(sCode.trim(), "WORK_STATUS_KEY");
		// String sStatus=DBUtils.executeSQLQuery(sDBQuery);
		// GenericUtils.Verify("User Name", sStatus.trim().equalsIgnoreCase("7"));

	}

	public boolean retireRuleAndCancelRetireRuleValidation() throws InterruptedException {

		boolean blnFlg = false;

		GenericUtils.Verify("Summaries button is clicked", oGenericUtils.gfn_Click_On_Object("span", "Summaries"));

		GenericUtils.Verify("Retire Rule button is displayed",
				oGenericUtils.gfn_Verify_Object_Exist("button", " Retire Rule "));

		blnFlg = oSeleniumUtils.is_WebElement_Enabled("//button[text()=' Retire Rule ']");

		GenericUtils.Verify("Retire Rule button is enabled", blnFlg);

		GenericUtils.Verify("Cancel Retire Rule button is disabled",
				oGenericUtils.gfn_Verify_String_Object_Exist("//button[text()=' Cancel Retire Rule ' and @disabled]"));

		GenericUtils.Verify("Retire Rule button is clicked",
				oGenericUtils.gfn_Click_On_Object("button", " Retire Rule "));

		GenericUtils.Verify("Retire Rule button is clicked under configuration",
				oGenericUtils.gfn_Click_On_Object("h5", " Retire Rule "));

		oGenericUtils.gfn_Click_String_object_Xpath("//label[text()=' No ']");

		oSeleniumUtils.Enter_given_Text_Element(RetireRuleMDComments, ProjectVariables.TestComments);

		oGenericUtils.gfn_Click_On_Object("button", "Save");

		oGenericUtils.gfn_Verify_Object_Exist("button", "Ok");

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		blnFlg = oSeleniumUtils.is_WebElement_Enabled("//button[text()=' Cancel Retire Rule ']");

		GenericUtils.Verify("Cancel Retire Rule button is enabled", blnFlg);

		GenericUtils.Verify("Cancel Retire Rule button is clicked",
				oGenericUtils.gfn_Click_On_Object("button", " Cancel Retire Rule "));

		oGenericUtils.gfn_Click_On_Object("button", "Yes");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.LoadingIcon);

		oGenericUtils.gfn_Click_On_Object("button", "Ok");

		GenericUtils.Verify("Summaries button is clicked", oGenericUtils.gfn_Click_On_Object("span", "Summaries"));

		blnFlg = !oSeleniumUtils.is_WebElement_Displayed("//h5[text()= ' Retire Rule ']");

		GenericUtils.Verify("Retire Rule button is not displayed under configuration", blnFlg);

		blnFlg = oSeleniumUtils.is_WebElement_Enabled("//button[text()=' Retire Rule ']");

		GenericUtils.Verify("Retire Rule button is enabled", blnFlg);

		GenericUtils.Verify("Cancel Retire Rule button is disabled",
				oGenericUtils.gfn_Verify_String_Object_Exist("//button[text()=' Cancel Retire Rule ' and @disabled]"));

		//
		return blnFlg;
	}

	public boolean validate_the_dropdown_values_with_DB(String sColumn, String sReview, String sSearch)
			throws InterruptedException {
		String sInstance = Serenity.sessionVariableCalled("InstanceName").toString();
		String sQueryCase = "";
		String sDBQuery = DBQueries.queryForDropdownList(sQueryCase, sInstance, sReview, sColumn);
		ArrayList<String> dropdownList = DBUtils.executeSQLQueryMultipleRows(sDBQuery);
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		// oGenericUtils.gfn_Click_String_object_Xpath("//mat-label[text()='Medical
		// Policies']/../../label");
		WebElement e = getDriver().findElement(By.xpath("//mat-label[text()='" + sSearch + "']/../../label"));

		(new Actions(getDriver())).moveToElement(e).click().build().perform();
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		if (sColumn.equals("IRI.ICD_CODE")) {
			oGenericUtils.gfn_Click_String_object_Xpath(
					"//mat-label[text()='Codes']/parent::label/parent::span/parent::div//mat-select//div[@class='mat-select-arrow']");
		}

		if (sColumn.equals("UGL.UPDATE_GROUP_NAME")) {
			oGenericUtils.gfn_Verify_String_Object_Exist("//span[text()='Deleted' and @class='mat-option-text']");
			oGenericUtils.gfn_Click_String_object_Xpath("//span[text()='Deleted']/../mat-pseudo-checkbox");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
			String xpath1 = "//button[text()='ApplyFilter']";

			WebElement el = getDriver().findElement(By.xpath(xpath1));

			(new Actions(getDriver())).moveToElement(el).click().build().perform();
			oGenericUtils.gfn_Click_On_Object("button", "ApplyFilter");
			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
			// oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading );
			return true;
		}
		// for(int i=0;i<dropdownList.size();i++){
		// oGenericUtils.gfn_Verify_String_Object_Exist("//span[text()='"+dropdownList.get(i).trim()+"'
		// and @class='mat-option-text']" );
		// }
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		// if(sColumn.equals("IRI.ICD_CODE") || sColumn.equals("TTL.TASK_TYPE_DESC") ||
		// sColumn.equals("TASK_STATUS_DESC")){
		if (sColumn.equals("IRI.ICD_CODE")) {
			oGenericUtils.gfn_Click_String_object_Xpath(
					"//span[text()='" + dropdownList.get(1).trim() + "']/../mat-pseudo-checkbox");
		} else {
			oGenericUtils.gfn_Click_String_object_Xpath(
					"//span[text()='" + dropdownList.get(0).trim() + "']/../mat-pseudo-checkbox");
		}

		if (sColumn.equals("IRD.MED_POL_TITLE"))
			Serenity.setSessionVariable("IRD.MED_POL_TITLE").to(dropdownList.get(0).trim());
		if (sColumn.equals("IRD.TOPIC_TITLE"))
			Serenity.setSessionVariable("IRD.TOPIC_TITLE").to(dropdownList.get(0).trim());
		// SeleniumUtils.defaultWait(ProjectVariables.MIN_SLEEP);
		String xpath1 = "//button[text()='ApplyFilter']";

		WebElement el = getDriver().findElement(By.xpath(xpath1));

		(new Actions(getDriver())).moveToElement(el).click().build().perform();
		oGenericUtils.gfn_Click_On_Object("button", "ApplyFilter");
		oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

		int count = 0;

		count = oSeleniumUtils
				.get_Matching_WebElement_count("//td/..//span[text()='" + dropdownList.get(0).trim() + "']");

		String sDBCountQuery = null;
		String sDBRulesCount = null;
		System.out.println("int count " + count);
		String sText = oSeleniumUtils.get_StringTextFrom_Locator("//span[contains(text(),'Showing')]");
		String[] sShowCount = sText.trim().split(" ");
		System.out.println("vALUE " + sShowCount[3]);
		if (sColumn.equals("IRI.ICD_CODE")) {
			sDBCountQuery = DBQueries.queryForDropdownList("", dropdownList.get(1), sReview, "ICDCODECOUNT");
			sDBRulesCount = DBUtils.executeSQLQuery(sDBCountQuery);
		}
		if (sColumn.equals("IRI.ICD_CODE")) {
			GenericUtils.Verify(
					"Record Matched with Database" + "UI Count:: " + sShowCount[3] + "DB Count:: " + sDBRulesCount,
					String.valueOf(sDBRulesCount).equals(sShowCount[3]));
		} else {
			GenericUtils.Verify("Record count", String.valueOf(count).equals(sShowCount[3]));
		}

		return true;
	}

	public String createPRMID(String sUpdateType) throws IOException {

		String sPRMID = "";
		String sRequestedPRMIDXML = createRequestXML(sUpdateType, "", "");
		System.out.println("XML ::" + sRequestedPRMIDXML);
		String sPRMIDResponse = GenericUtils.sendPostRequest(ProjectVariables.sLotusPRMURL, sRequestedPRMIDXML);

		if (sUpdateType.equalsIgnoreCase("GETPRSTATUS")) {
			sPRMID = GenericUtils.getTextFromTwoStrings(sPRMIDResponse, "<PRSTATUS>", "</PRSTATUS>");
			System.out.println("Text: " + sPRMID);
		} else {
			sPRMID = GenericUtils.getTextFromTwoStrings(sPRMIDResponse, "<REQID>", "</REQID>");
			System.out.println("Text: " + sPRMID);

		}

		return sPRMID;
	}

	public boolean clickGenerateReport() throws InterruptedException {

		String xpath1 = "//button[text()='Generate Report']";

		WebElement el = getDriver().findElement(By.xpath(xpath1));

		(new Actions(getDriver())).moveToElement(el).click().build().perform();
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		boolean sflag = oGenericUtils.gfn_Click_On_Object("button", "Generate Report");
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		return sflag;

	}

	public boolean selectText(String arg1) throws InterruptedException {

		WebElement e = getDriver().findElement(By.xpath("//div[text()='" + arg1 + "']"));
		(new Actions(getDriver())).moveToElement(e).click().build().perform();
		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
		return true;

	}

}
