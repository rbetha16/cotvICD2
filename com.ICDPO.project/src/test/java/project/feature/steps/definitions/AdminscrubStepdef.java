package project.feature.steps.definitions;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.By;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import project.pageobjects.AdminScrubPage;
import project.pageobjects.CommonPage;
import project.utilities.DBQueries;
import project.utilities.DBUtils;
import project.utilities.GenericUtils;
import project.utilities.ProjectVariables;
import project.utilities.SeleniumUtils;

//trinath..
public class AdminscrubStepdef extends ScenarioSteps {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// trinath
	AdminScrubPage oAdminScrubPage;

	GenericUtils oGenericUtils;

	SeleniumUtils oSeleniumUtils;

	CommonPage oCommonPage;

	@Step
	public void should_be_viewing_the_count_at_bottom_right_corner_of_screen_with(String arg1, String arg2) {

		oAdminScrubPage.should_be_viewing_the_count_at_bottom_right_corner_of_screen_with(arg1, arg2);

	}

	@Step
	public void should_be_viewing_the_count_below_the_with(String arg1, String arg2, String arg3) {

		oAdminScrubPage.should_be_viewing_the_count_below_the_with(arg1, arg2, arg3);

	}

	@Step
	public void only_Processed_candidate_rules_should_be_considered() {

	}

	@Step
	public void should_be_displayed_with_the_in_the_drill_down_format(String arg1) throws InterruptedException {
		oAdminScrubPage.should_be_displayed_with_the_in_the_drill_down_format(arg1);

	}

	@Step
	public void user_should_be_displayed_with(String arg1) throws InterruptedException {

		oGenericUtils.gfn_Verify_String_Object_Exist(
				"//div[text()='" + Serenity.sessionVariableCalled("NonCandidateRule").toString() + "' or '"
						+ Serenity.sessionVariableCalled("NonCandidateRuleVersion").toString() + "']");

		String sTotalItemsAllRules = oSeleniumUtils.get_TextFrom_Locator("//span[contains(text(),'Total')]");

		String sNoOFItemsAllRules = StringUtils.substringAfter(sTotalItemsAllRules, ":").trim();

		System.out.println(sNoOFItemsAllRules);

		Serenity.setSessionVariable(arg1).to(sNoOFItemsAllRules);

		List<WebElement> oAssigneelist = getDriver()
				.findElements(By.xpath("//div[@ng-bind-html='row.entity.taskUserName']"));

		List<WebElement> oMPlist = getDriver()
				.findElements(By.xpath("//div[@ng-bind-html='row.entity.medicalPolicy']"));

		List<WebElement> oTopiclist = getDriver().findElements(By.xpath("//div[@ng-bind-html='row.entity.topic']"));

	}

	@Step
	public void user_enters_the_comments_and_click_on_the_button() throws InterruptedException {

		oSeleniumUtils.Enter_given_Text_Element("//textarea[@id='modalProcessTextArea']", "Autotest");

		oGenericUtils.gfn_Click_On_Object("a", "Apply");

		SeleniumUtils.defaultWait(3000);

	}

	@Step
	public void user_selects_the_rule_in_AdminScrub(String arg1) throws InterruptedException {

		// verify("Click on All Rule Radio
		// Button",oGenericUtils.gfn_Click_String_object_Xpath(oAdminScrubPage.UnscrubbedRadioBtn));

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oAdminScrubPage.user_selects_the_rule_in_AdminScrub(arg1);

	}

	@Step
	public void user_selects_and_clicks_on_the_button(String sButton) throws InterruptedException {

		oAdminScrubPage.user_selects_and_clicks_on_the_button(sButton);

	}

	@Step
	public void the_system_should_change_the_view_of_this_screen_to(String sValue) throws InterruptedException {

		verify(sValue + "is Dispalayed", oAdminScrubPage.system_should_change_the_view_of_this_screen(sValue));

	}

	@Step
	public void the_user_should_be_able_to_view_the_following_and(String sColumns, String sButton)
			throws InterruptedException {
		oAdminScrubPage.the_user_should_be_able_to_view_the_following_and(sColumns, sButton);

	}

	@Step
	public void user_clicks_on_the_button(String arg1) throws InterruptedException {

		oAdminScrubPage.click_On_button_AdminScrubPage(arg1);
	}

	@Step
	public void clickColumnAndValidateFilterFunctionality() throws InterruptedException {

		oAdminScrubPage.clickColumnAndValidateFilterFunctionality();

	}

	@Step
	public void verify(String sDescription, boolean blnStatus) {

		GenericUtils.Verify(sDescription, blnStatus);
	}

	public void selectallRowsDragColumnHeader() throws InterruptedException {
		oAdminScrubPage.selectallRowsDragColumnHeader();
	}

	@Step
	public void userClickOnOption(String arg1, String arg2) throws InterruptedException {

		oGenericUtils.gfn_Click_On_Object("span", "Group");
		oGenericUtils.gfn_Click_String_object_Xpath(
				"(//div[@class='ui-grid-selection-row-header-buttons ui-grid-icon-ok ng-scope'])[2]");
		oGenericUtils.gfn_Click_On_Object("button", "Apply Filter");
		SeleniumUtils.defaultWait(3000);
		oGenericUtils.gfn_Click_On_Object("span", "Cancel");

		String sAdminMDScrubRule = getDriver()
				.findElement(By.xpath(oAdminScrubPage.getDynamicXPathAdminMD("MidRule", 1))).getText();

		String sAdminMDScrubRuleVersion = getDriver()
				.findElement(By.xpath(oAdminScrubPage.getDynamicXPathAdminMD("RuleVersion", 1))).getText();

		String sAdminMDProposalType = getDriver()
				.findElement(By.xpath(oAdminScrubPage.getDynamicXPathAdminMD("RuleVersion", 1))).getText();

		String sAdminMDScrubReviewCode = getDriver()
				.findElement(By.xpath(oAdminScrubPage.getDynamicXPathAdminMD("RuleVersion", 1))).getText();

		String sAdminMDScrubSameSimCode = getDriver()
				.findElement(By.xpath(oAdminScrubPage.getDynamicXPathAdminMD("RuleVersion", 1))).getText();

		String sAdminMDScrubFromTo = getDriver()
				.findElement(By.xpath(oAdminScrubPage.getDynamicXPathAdminMD("RuleVersion", 1))).getText();

		String sAdminMDScrubCATDesc = getDriver()
				.findElement(By.xpath(oAdminScrubPage.getDynamicXPathAdminMD("RuleVersion", 1))).getText();

		String sSubRuleKey = DBUtils.executeSQLQuery(
				oCommonPage.getDynamicQuery("Sub Rule Key", sAdminMDScrubRule, sAdminMDScrubRuleVersion).trim());

		String sUpdateInstanceKey = DBUtils.executeSQLQuery(
				oCommonPage.getDynamicQuery("Update Instance Key", ProjectVariables.AdminScrubInstance, "").trim());

		String sProposalResult = DBUtils
				.executeSQLQuery(oCommonPage.getDynamicQuery("PROPOSAL QUERY", sUpdateInstanceKey, sSubRuleKey).trim());

		System.out.println("proposal" + sProposalResult);

		verify("SubRule Key is displayed properly " + sSubRuleKey.trim().equalsIgnoreCase(sProposalResult.trim()),
				true);

	}

	public void assign_with_three_different_AdminMDs_Users_to_the_rules_in_Admin_MD_scrub()
			throws InterruptedException {

		oAdminScrubPage.assign_with_three_different_AdminMDs_Users_to_the_rules_in_Admin_MD_scrub();
	}

	public void retrive_count_in_Admin_MD_Scrub(String arg1) {

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oCommonPage.AdminScrubImageLoader);
		/*
		 * oGenericUtils.gfn_Click_String_object_Xpath(oCommonPage.
		 * AllRulesRadioBtn);
		 * 
		 * oCommonPage.waitUntil_Loading_Dailog_Disappears(oCommonPage.
		 * AdminScrubImageLoader);
		 */

		SeleniumUtils.scrollingToGivenElement(getDriver(), "//span[contains(text(),'Total')]");

		String sTotalItems = oSeleniumUtils.get_TextFrom_Locator("//span[contains(text(),'Total')]");

		int iRuleCount = Integer.parseInt(StringUtils.substringAfter(sTotalItems, ":").trim());

		System.out.println("Intail Run Count" + iRuleCount);

		Serenity.setSessionVariable("iRuleCount").to(iRuleCount);

		getDriver().close();

	}

	public void retrived_rule_count_in_should_be_greater_than_in_Admin_MD_Scrub(String arg1, String arg2) {

		oCommonPage.waitUntil_Loading_Dailog_Disappears(oCommonPage.AdminScrubImageLoader);

		/*
		 * oGenericUtils.gfn_Click_String_object_Xpath(oCommonPage.
		 * AllRulesRadioBtn);
		 * 
		 * oCommonPage.waitUntil_Loading_Dailog_Disappears(oCommonPage.
		 * AdminScrubImageLoader);
		 */

		SeleniumUtils.scrollingToGivenElement(getDriver(), "//span[contains(text(),'Total')]");

		String sTotalItems = oSeleniumUtils.get_TextFrom_Locator("//span[contains(text(),'Total')]");

		int iReRunCount = Integer.parseInt(StringUtils.substringAfter(sTotalItems, ":").trim());

		System.out.println("ReRun count" + iReRunCount);

		int iRuleCount = Serenity.sessionVariableCalled("iRuleCount");

		boolean bln = iReRunCount > iRuleCount;

		GenericUtils.Verify(
				"Rules are not greater after loading the data in Rerun" + iReRunCount + "Intial" + iRuleCount, bln);

	}

	@Step
	public void user_selects_two_rules_as_non_candidates_and_remaining_as_process_candidates()
			throws InterruptedException {

		oAdminScrubPage.user_selects_two_rules_as_non_candidates_and_remaining_as_process_candidates();

	}

	@Step
	public void filter_with_medical_policies_and_Reassign_reviewer_capture_rules_count(String sMedicalPolicy,
			String sUser) throws InterruptedException {

		oAdminScrubPage.filter_with_medical_policies_and_Reassign_reviewer_capture_rules_count(sMedicalPolicy, sUser);

	}

	@Step
	public void validateDirectReleaseBtnStatus(String arg1, String arg2) throws InterruptedException {

		
		switch (arg2) {
		case "Process Non Candidates":

			verify("Direct Release button is Enabled",
					oAdminScrubPage.StatusBtnDirectRelease().equalsIgnoreCase("enabled"));

			oGenericUtils.gfn_Click_String_object_Xpath(
					"(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[1]");
			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			WebElement e = getDriver().findElement(By.xpath("//a[text()='Direct Release']"));
			(new Actions(getDriver())).moveToElement(e).click().build().perform();
			// SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			// verify("Displayed 'Need to select row(s) to process' Messege "
			// ,oGenericUtils.gfn_Click_String_object_Xpath("//div[text()='Need
			// to select row(s) to process.']"));
			verify("Displayed  'Please select only Candidate Rules' Messege ",
					oGenericUtils.gfn_Click_String_object_Xpath("//div[text()='Please select only Candidate Rules']"));
			break;
		case "Process Candidates":

			verify("Direct Release button is Enabled",
					oAdminScrubPage.StatusBtnDirectRelease().equalsIgnoreCase("enabled"));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Click_String_object_Xpath(
					"(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[1]");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			verify("Mouse Over on Direct Release Button ", oAdminScrubPage.mouseOverDirectRelease());
			if (getDriver().findElements(By.xpath("//div[text()='Please select only Candidate Rules']")).size() > 0) {
				verify("Messege Should not be shown", false);
			}
			break;

		case "Unscrubbed Rules":

			verify("Direct Release button is  Enabled ",
					oAdminScrubPage.StatusBtnDirectRelease().equalsIgnoreCase("enabled"));
			verify("clicked on Direct Release button ", oGenericUtils.gfn_Click_On_Object("a", "Direct Release"));
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			verify("Displayed  'Need to select row(s) to process' Messege ",
					oGenericUtils.gfn_Click_String_object_Xpath("//div[text()='Need to select row(s) to process.']"));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			verify("selected Rule ", oGenericUtils.gfn_Click_String_object_Xpath(
					"(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[1]"));
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			verify("click on Direct Release button ", oGenericUtils.gfn_Click_On_Object("a", "Direct Release"));
			verify("Displayed  'Please select only Candidate Rules' Messege ",
					oGenericUtils.gfn_Click_String_object_Xpath("//div[text()='Please select only Candidate Rules']"));

			break;

		case "Admin View":
			oGenericUtils.gfn_Click_On_Object("a", "Admin View");
			oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(oAdminScrubPage.AdminScrubImageLoader);
			verify("Direct Release button is  disabled ",
					oAdminScrubPage.StatusBtnDirectRelease().equalsIgnoreCase("disabled"));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			oGenericUtils.gfn_Click_String_object_Xpath(
					"(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[1]");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			verify("Direct Release button is  disabled ",
					oAdminScrubPage.StatusBtnDirectRelease().equalsIgnoreCase("disabled"));
			

			break;
		case "Candidates Non Candidates":
			verify("User Clicked on AllRules ",
					oGenericUtils.gfn_Click_String_object_Xpath(oAdminScrubPage.AllRulesRadioBtn));
			oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(oAdminScrubPage.AdminScrubImageLoader);
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			oGenericUtils.gfn_Click_String_object_Xpath(
					"(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[1]");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			oGenericUtils.gfn_Click_String_object_Xpath(
					"(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[2]");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			verify("clicked on Direct Release button ", oGenericUtils.gfn_Click_On_Object("a", "Direct Release"));
			verify("Displayed  'Please select only Candidate Rules' Messege ",
					oGenericUtils.gfn_Click_String_object_Xpath("//div[text()='Please select only Candidate Rules']"));

			break;
			
		case "Direct Release":

			verify("Direct Release button is Enabled",
					oAdminScrubPage.StatusBtnDirectRelease().equalsIgnoreCase("enabled"));

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Click_String_object_Xpath(
					"(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[1]");
			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);
			
			String sRule=oSeleniumUtils.get_TextFrom_Locator("(//div[@class='ngCellText ui-grid-cell-contents ng-binding ng-scope'])[4]");
			Serenity.setSessionVariable("NonCandidateRule").to(sRule);

			String sVersion=oSeleniumUtils.get_TextFrom_Locator("(//div[@class='ngCellText ui-grid-cell-contents ng-binding ng-scope'])[5]");
			Serenity.setSessionVariable("NonCandidateRuleVersion").to(sVersion);
			
			String sMidRuleVersion =sRule+"."+sVersion;
			System.out.println(sMidRuleVersion);
			
			String sTaskType=oSeleniumUtils.get_TextFrom_Locator("(//div[@class='ngCellText ui-grid-cell-contents ng-binding ng-scope'])[6]");
			Serenity.setSessionVariable("TaskType").to(sTaskType);

						
			

			verify("Mouse Over on Direct Release Button ", oAdminScrubPage.mouseOverDirectRelease());
			if (getDriver().findElements(By.xpath("//div[text()='Please select only Candidate Rules']")).size() > 0) {
				verify("Messege Should not be shown", false);
			}
			verify("clicked on Direct Release button ", oGenericUtils.gfn_Click_On_Object("a", "Direct Release"));
			String sUIMsg = oSeleniumUtils.get_TextFrom_Locator("//div[contains(text(),'Directly Released rules ')]");
			
			verify("Displayed Messege 'Directly Released rules..' ",  oSeleniumUtils.get_TextFrom_Locator("//div[contains(text(),'Directly Released rules ')]").trim().contains("Directly Released rules shall be routed to Rule Review process"));
			oGenericUtils.gfn_Click_On_Object("button", "Yes");
			oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(oAdminScrubPage.AdminScrubImageLoader);
				
			String sDBTaskValue = DBUtils.executeSQLQuery(DBQueries.sQueryTaskTypeAndStatus("TTL.TASK_TYPE_DESC",Serenity.sessionVariableCalled("InstanceName").toString(),sMidRuleVersion));
			
			verify("Rule is Directly Released to " + sTaskType , sDBTaskValue.trim().equalsIgnoreCase(sTaskType.trim()));
			
			break;
		}

	}

}
