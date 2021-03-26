package project.pageobjects;

import java.util.Arrays;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.pages.PageObject;
import project.utilities.GenericUtils;
import project.utilities.ProjectVariables;
import project.utilities.SeleniumUtils;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import junit.framework.Assert;

public class AdminScrubPage extends PageObject{
	//trinath..
	GenericUtils oGenericUtils;
	SeleniumUtils oSeleniumUtils;
	CommonPage oCommonPage;
	 
	@FindBy(xpath = "//div[@class='imageLoader']")
	public WebElementFacade AdminScrubImageLoader;
	
	@FindBy(xpath = "//span[text()='Cancel']")
	public WebElementFacade CancelButton;
	
	public String NightView="(//div[@style='background-color: rgb(66, 66, 66); color: rgb(255, 255, 255); border: 1px solid rgb(51, 125, 189);'])[2]";
	
	public String UnscrubbedRadioBtn = "//input[@type='radio' and @value='all']";
	
	private String SpanTagContains="//span[contains(text(),'sValue')]";
	 
	public String SelectAllCheckBoxUnscrubbed = "(//span[text()='Review']/parent::div/parent::div/parent::div//input)[position()=last()]";
	
	public String SelectAllCheckBoxAdminMDScrub = "//span[text()='Review']/parent::div/parent::div/parent::div//input";
	
	public String AllRulesRadioBtn = "//input[@type='radio' and @value='allUserRules']";
	
	public void user_selects_and_clicks_on_the_button(String sButton) throws InterruptedException {
		
		oGenericUtils.gfn_Click_On_Object("a", "Night View");
		
	}


	public boolean system_should_change_the_view_of_this_screen(String sValue) throws InterruptedException {
		
		boolean bln=false;
		if (sValue.equalsIgnoreCase("Night view")) {
			
			bln= oGenericUtils.gfn_Verify_String_Object_Exist(NightView);
			
		}else {
			bln= !oSeleniumUtils.is_WebElement_Displayed(NightView);
			
		}
		return bln;
		
		
	}


	public void the_user_should_be_able_to_view_the_following_and(String sColumnHeader, String sButton) throws InterruptedException {
		
		String sColumns="Review, Comment, Reviewer, MR, Version, Task Type, Assignee, Library, ARD, Reference, IU Indicator, Medical Policy, Topic, Decision Point, Group, Review ICD, ICD, From-To, CAT, Override, ReviewICD   Group        Mapped ICD           From-To                         CAT          Override ,Sim Mappings,Sub Rule Description Resolved, Sub Rule Description Unresolved, Sub Rule Notes, Sub Rule script, Sub Rule Rationale, Payers, Claim Types";
		
		List<String> oButtonList=Arrays.asList(sButton.split(";"));
		
		List<String> oColumnList=Arrays.asList(sColumns.split(","));
		
		if (oButtonList.size()>1) {
			
			for (int i = 0; i < oButtonList.size(); i++) {
				
				oGenericUtils.gfn_Verify_Object_Exist("a", oButtonList.get(i));
				
				System.out.println(oButtonList.get(i));
				
			}
			
		}
		
		for (int i = 0; i < oColumnList.size(); i++) {
			
			boolean bln= oSeleniumUtils.is_WebElement_Displayed(StringUtils.replace(SpanTagContains, "sValue", oColumnList.get(i).trim()));
			
			System.out.println(oColumnList.get(i)+bln);
			
			if (oColumnList.get(i).equalsIgnoreCase("CAT")) {
				
				SeleniumUtils.defaultWait(3000);
				
				SeleniumUtils.scrollingToGivenElement(getDriver(), "//span[text()='Sim Mappings']");
				
			}
			
		}
		
	}
	
	public void user_selects_the_rule_in_AdminScrub(String arg1) throws InterruptedException {
		
		oGenericUtils.gfn_Click_String_object_Xpath(AllRulesRadioBtn);
		
		oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);
		
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		
		oGenericUtils.gfn_Click_String_object_Xpath("(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[1]");
		
	    String sRule=oSeleniumUtils.get_TextFrom_Locator("(//div[@class='ngCellText ui-grid-cell-contents ng-binding ng-scope'])[4]");
		
		Serenity.setSessionVariable("NonCandidateRule").to(sRule);

		String sVersion=oSeleniumUtils.get_TextFrom_Locator("(//div[@class='ngCellText ui-grid-cell-contents ng-binding ng-scope'])[5]");
		
		Serenity.setSessionVariable("NonCandidateRuleVersion").to(sVersion);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		
		switch (arg1) {
		case "Process Candidates":

			oGenericUtils.gfn_Click_On_Object("a", arg1);
			
			break;
		case "Process Non Candidates":

			oGenericUtils.gfn_Click_On_Object("a", arg1);
			break;
		case "Process Non Candidates-RemainingRules":
	

			oSeleniumUtils.Click_given_Locator(SelectAllCheckBoxAdminMDScrub);
			
			oGenericUtils.gfn_Click_String_object_Xpath("(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[1]");
			
			oGenericUtils.gfn_Click_On_Object("a", "Process Non Candidates");
			
			break;	
		case "Process Candidates-All Rules":	
			
			oGenericUtils.gfn_Click_String_object_Xpath(AllRulesRadioBtn);
			
			oSeleniumUtils.Click_given_Locator(SelectAllCheckBoxAdminMDScrub);
			
			oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);
			
			oGenericUtils.gfn_Click_On_Object("a", "Process Candidates");
			break;

		default:
			break;
		}
		
	/*	SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

	    oGenericUtils.gfn_Click_On_Object("a", "Retrieve Non Candidates");
		
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);*/

		

		
	}

    @Step
	public void should_be_viewing_the_count_at_bottom_right_corner_of_screen_with(String arg1, String arg2) {
		
		String sTotalItems = oSeleniumUtils.get_TextFrom_Locator("//span[contains(text(),'Total')]");

		String sNoOFItems = StringUtils.substringAfter(sTotalItems, ":").trim();

		System.out.println(sNoOFItems);

		oSeleniumUtils.Click_given_Locator(SelectAllCheckBoxUnscrubbed);

		String sSelectedRows = oSeleniumUtils.get_TextFrom_Locator("//div[contains(text(),'Selected Row(s)')]");

		String sRows = StringUtils.substringAfter(sSelectedRows, ":").trim();

		System.out.println(sRows);
		
		System.out.println("Only Unscrubbed Rules Rows count should match after selecting checkbox in adminscrub"+sNoOFItems.equalsIgnoreCase(sRows));

				
	}

    @Step
	public void should_be_viewing_the_count_below_the_with(String arg1, String arg2, String arg3) {
		
    	oGenericUtils.gfn_Click_String_object_Xpath(AllRulesRadioBtn);
    	
    	oCommonPage.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);

		String sTotalItemsAllRules = oSeleniumUtils.get_TextFrom_Locator("//span[contains(text(),'Total')]");

		String sNoOFItemsAllRules = StringUtils.substringAfter(sTotalItemsAllRules, ":").trim();

		System.out.println(sNoOFItemsAllRules);

		oSeleniumUtils.Click_given_Locator(SelectAllCheckBoxAdminMDScrub);

		String sSelectedRowsAllRules = oSeleniumUtils.get_TextFrom_Locator("//div[contains(text(),'Selected Row(s)')]");

		String sRowsAllRules = StringUtils.substringAfter(sSelectedRowsAllRules, ":").trim();

		System.out.println(sRowsAllRules);

		//verify("All Rules Rows count should match after selecting checkbox inadminscrub",sNoOFItems.equalsIgnoreCase(sRows));
		
		System.out.println("All Rules Rows count should match after selecting checkbox inadminscrub"+sNoOFItemsAllRules.equalsIgnoreCase(sRowsAllRules));

		oSeleniumUtils.Click_given_Locator(SelectAllCheckBoxAdminMDScrub);

		boolean bln = !oSeleniumUtils.is_WebElement_Selected(SelectAllCheckBoxAdminMDScrub);

		System.out.println(bln);

		if (bln == true) {

			String sSelectedRows1 = oSeleniumUtils.get_TextFrom_Locator("//div[contains(text(),'Selected Row(s)')]");

			String sRows1 = StringUtils.substringAfter(sSelectedRows1, ":").trim();
			
			boolean blflg="0".equalsIgnoreCase(sRows1.trim());
			
			//verify("All rules Rows count was matched after selecting unchecking the checkbox in adminscrub",blflg);
			
			System.out.println("All rules Rows count was matched after selecting unchecking the checkbox in adminscrub"+blflg);
		}

	}


	public void should_be_displayed_with_the_in_the_drill_down_format(String arg1) throws InterruptedException {
		
		List<String> oColumnList=Arrays.asList(arg1.split(";"));
		
		for (int i = 0; i < oColumnList.size(); i++) {
			
			oGenericUtils.gfn_Verify_Object_Exist("span", oColumnList.get(i));
		}
		
		oGenericUtils.gfn_Click_String_object_Xpath("//div[@class='ui-grid-tree-base-row-header-buttons ui-grid-icon-plus-squared']");
		
		oGenericUtils.gfn_Verify_String_Object_Exist("//div[@class='ui-grid-tree-base-row-header-buttons ui-grid-icon-minus-squared']");
		
		List<WebElement> olist=getDriver().findElements(By.xpath("//i[@class='ui-grid-icon-minus-squared']"));
		
		for (int i = 0; i < olist.size(); i++) {
			
            oGenericUtils.gfn_Verify_String_Object_Exist("//i[@class='ui-grid-icon-minus-squared']["+(i+1)+"]");
		}
		
		
	}


	public void click_On_button_AdminScrubPage(String arg1) throws InterruptedException {

		if (arg1.equalsIgnoreCase("All Rules Radio")) {

			oGenericUtils.gfn_Click_String_object_Xpath(AllRulesRadioBtn);

			oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);

		} else if (arg1.equalsIgnoreCase("Only Unscrubbed Rules Radio"))

		{

			oGenericUtils.gfn_Click_String_object_Xpath(UnscrubbedRadioBtn);

			oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);
			
		}else {
			
			if (arg1.equalsIgnoreCase("Release")) {
				
				int i =0;
				
					 while (oSeleniumUtils.is_WebElement_Displayed("//a[text()='Release' and @disabled='disabled' ]") & i!=50 ){
						
						SeleniumUtils.defaultWait(1000);
						 
						oGenericUtils.gfn_Click_String_object_Xpath(UnscrubbedRadioBtn);
						 
						oGenericUtils.gfn_Click_String_object_Xpath(AllRulesRadioBtn);
						
						i =i+1;
						
						 if (i==50) {
								
							 System.out.println("Release button is not enabled");
							 
							 GenericUtils.Verify("Release button is enabled"+"Wait Time: "+(i*2)+"Secs", false);
							 
						 }
						 
					 }
					 	 
				}

			oGenericUtils.gfn_Click_On_Object("a", arg1);
		}

				
		oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);

		if (arg1.equalsIgnoreCase("Dashboard")) {

			SeleniumUtils.switchWindowUsingWindowCount(4 ,3, getDriver());

		}	

		SeleniumUtils.defaultWait(3000);

	}
	
	@Step
	public void applyFilter(String sWebElementList, String sColumnName, String sFilterImgColumn)
			throws InterruptedException {

		String sCellTextXpath = "(//div[@ng-bind-html='row.entity." + sWebElementList + "'])";

		System.out.println(sCellTextXpath);

		List<WebElement> sWebList = getDriver()
				.findElements(By.xpath("(//div[@ng-bind-html='row.entity." + sWebElementList + "'])"));

		for (int j = 1; j <= sWebList.size(); j++) {

			System.out.println(oSeleniumUtils.get_TextFrom_Locator(sCellTextXpath + "[" + j + "]"));

			oSeleniumUtils.Enter_given_Text_Element("//input[@id='filterTextSearch']",
					oSeleniumUtils.get_TextFrom_Locator(sCellTextXpath + "[" + j + "]"));

			verify("Cell value is not displayed",
					oSeleniumUtils.is_WebElement_Displayed(sCellTextXpath + "[" + j + "]"));

			oGenericUtils.gfn_Click_On_Object("button", "Apply Filter");

			oGenericUtils.gfn_Click_On_Object("span", "Cancel");

			oGenericUtils.gfn_Verify_String_Object_Exist("//div[text()='" + sFilterImgColumn + "']/child::span//img");

			oGenericUtils.gfn_Click_On_Object("a", "Filters");

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Click_On_Object("a", "Clear All Filters");

			oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);

			oGenericUtils.gfn_Click_On_Object("span", sColumnName);

			if (CancelButton.isVisible()) {

				oGenericUtils.gfn_Click_On_Object("span", "Cancel");

				break;
			}

		}
	}


	public void clickColumnAndValidateFilterFunctionality() throws InterruptedException {
		
		oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);

		oGenericUtils.gfn_Click_String_object_Xpath(AllRulesRadioBtn);

		oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);

		String[] sAdminMDColumnNames = { "Reviewer", "MR", "Medical Policy", "IU Indicator", "Version", "Library","ARD" };

		for (int i = 0; i < sAdminMDColumnNames.length; i++) {

			System.out.println(sAdminMDColumnNames[i]);

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oGenericUtils.gfn_Click_On_Object("span", sAdminMDColumnNames[i]);

			oGenericUtils.gfn_Verify_String_Object_Exist("(//span[text()='" + sAdminMDColumnNames[i]
					+ "'])[2]/parent::div/parent::div/parent::div/parent::div/parent::div/parent::div");

			validateFieldPrasentPopUp();

			switch (sAdminMDColumnNames[i]) {

			case "Version":
				applyFilter("version", sAdminMDColumnNames[i], "E");
				break;
			case "Library":
				applyFilter("libraryStatus", sAdminMDColumnNames[i], "H");

				break;
			case "ARD":
				applyFilter("ard", sAdminMDColumnNames[i], "I");
				break;
			case "IU Indicator":
				applyFilter("industryUpdate", sAdminMDColumnNames[i], "K");
				break;
			case "Medical Policy":
				applyFilter("medicalPolicy", sAdminMDColumnNames[i], "L");

				break;
			case "Reviewer":

				applyFilter("adminMDReviewer", sAdminMDColumnNames[i], "C");

				break;

			case "MR":
				applyFilter("mainRule", sAdminMDColumnNames[i], "D");

				break;

			default:
				verify("Entered case value is not valid", false);

			}

		}
		getDriver().switchTo().defaultContent();
	}
	
	@Step
	public void validateFieldPrasentPopUp() throws InterruptedException {

		verify("Sort Ascending label is displayed", oGenericUtils.gfn_Verify_String_Object_Exist("//a[contains(text(),'Sort Ascending')]"));
		
		verify("Sort Descending label is displayed", oGenericUtils.gfn_Verify_String_Object_Exist("//a[contains(text(),'Sort Descending')]"));

		verify("Filter Text Search label is displayed", oGenericUtils.gfn_Verify_String_Object_Exist("//input[@id='filterTextSearch']"));

		verify("Contains label is displayed", oGenericUtils.gfn_Verify_String_Object_Exist("//label[contains(text(),'Contains')]"));

        verify("Does Not Contain label is displayed", oGenericUtils.gfn_Verify_String_Object_Exist("//label[contains(text(),'Does Not Contain')]"));		

        verify("Contains Only label is displayed", oGenericUtils.gfn_Verify_String_Object_Exist("//label[contains(text(),'Contains Only')]"));
        
        verify("Excludes Only label is displayed", oGenericUtils.gfn_Verify_String_Object_Exist("//label[contains(text(),'Excludes Only')]"));
        
        verify("Cancel label is displayed", oGenericUtils.gfn_Verify_Object_Exist("Span", "Cancel"));
		
        verify("Clear Filter label is displayed", oGenericUtils.gfn_Verify_Object_Exist("Span", "Clear Filter"));
		
		// oGenericUtils.gfn_Verify_Object_Exist("button", "Apply Filter");

	}

	@Step
	public void verify(String sDescription, boolean blnStatus) {

		GenericUtils.Verify(sDescription, blnStatus);
	}
	
	@Step
	public void selectallRowsDragColumnHeader() throws InterruptedException {

		oGenericUtils.gfn_Verify_Object_Exist("a", "Process Candidates");
		
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		Actions act = new Actions(getDriver());

		oSeleniumUtils.highlightElement("//div[@class='ui-grid-cell-contents headerLetter column_5']");

		// find element which we need to drag
		WebElement drag = getDriver()
				.findElement(By.xpath("//div[@class='ui-grid-cell-contents headerLetter column_5']"));

		String sdragColumnValue = oSeleniumUtils
				.get_TextFrom_Locator("//div[@class='ui-grid-cell-contents headerLetter column_5']");

		oSeleniumUtils.highlightElement("//div[@class='ui-grid-cell-contents headerLetter column_9']");

		// find element which we need to drop
		WebElement drop = getDriver()
				.findElement(By.xpath("//div[@class='ui-grid-cell-contents headerLetter column_9']"));

		// this will drag element to destination
		act.dragAndDrop(drag, drop).build().perform();

		String sdropColumnValue = oSeleniumUtils
				.get_TextFrom_Locator("//div[@class='ui-grid-cell-contents headerLetter column_9']");

		verify("Column is dragged and dropped", sdragColumnValue.equalsIgnoreCase(sdropColumnValue));

		
	}

	public String getDynamicXPathAdminMD(String sXpath, int sVal) {

        String sFormattedXpath = null;

        switch (sXpath) {

        case "MidRule":
             sFormattedXpath = "//div[@class='ui-grid-viewport ng-isolate-scope']/child::div/child::div[" + sVal+ "]/div/div[5]//descendant::div";
             break;
        case "RuleVersion":
             sFormattedXpath = "//div[@class='ui-grid-viewport ng-isolate-scope']/child::div/child::div[" + sVal+ "]/div/div[6]//descendant::div";
             break;
        case "Sub Rule Description - Resolved":
             sFormattedXpath = "(//div[@class='ngCellText ui-grid-cell-contents ng-binding ng-scope'] [@ng-bind='row.entity.subRuleDescResolved'])["+ sVal + "]";
             break;
        case "Sub Rule Description - Unresolved":
             sFormattedXpath = "(//div[@class='ngCellText ui-grid-cell-contents ng-binding ng-scope'] [@ng-bind='row.entity.subRuleDescUnresolved'])["+ sVal + "]";
             break;
        case "Sub Rule Rationale":
             sFormattedXpath = "(//div[@class='ngCellText ui-grid-cell-contents ng-binding ng-scope'] [@ng-bind='row.entity.subRuleRationale'])["+ sVal + "]";
             break;
        case "Sub Rule Notes":
             sFormattedXpath = "(//div[@class='ngCellText ui-grid-cell-contents ng-binding ng-scope'] [@ng-bind='row.entity.subRuleNotes'])["+ sVal + "]";
             break;
        case "Sub Rule Script":
             sFormattedXpath = "(//div[@class='ngCellText ui-grid-cell-contents ng-binding ng-scope'] [@ng-bind='row.entity.subRuleScript'])["+ sVal + "]";
             break;
        case "ProposalType":
             sFormattedXpath = "//div[@class='ui-grid-viewport ng-isolate-scope']/child::div/child::div[" + sVal+ "]/div/div[16]//descendant::div";
             break;
        case "ReviewCode":
             sFormattedXpath = "//div[@class='ui-grid-viewport ng-isolate-scope']/child::div/child::div[" + sVal+ "]/div/div[17]//descendant::div";
             break;
        case "SameSimCode":
             sFormattedXpath = "//div[@class='ui-grid-viewport ng-isolate-scope']/child::div/child::div[" + sVal+ "]/div/div[18]//descendant::div";
             break;
        case "FromTo":
             sFormattedXpath = "//div[@class='ui-grid-viewport ng-isolate-scope']/child::div/child::div[" + sVal+ "]/div/div[19]//descendant::div";
             break;
             

        }

        return sFormattedXpath;
  }


	public void assign_with_three_different_AdminMDs_Users_to_the_rules_in_Admin_MD_scrub() throws InterruptedException {
		
        oGenericUtils.gfn_Click_String_object_Xpath(AllRulesRadioBtn);
		
		oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);
		
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		
		oGenericUtils.gfn_Click_On_Object("a", "Admin View");
		
		oGenericUtils.gfn_Click_String_object_Xpath("(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[1]");
		
		oGenericUtils.gfn_Click_On_Object("a", "Reassign");
		
		oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);
		
		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator("//select[@ng-model='reassignToAdminReviewer']", "iht_ittest02");
		
		oGenericUtils.gfn_Click_String_object_Xpath("(//button[text()='Reassign'])[2]");
		
		oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);
		
        oGenericUtils.gfn_Click_String_object_Xpath("(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[2]");
		
		oGenericUtils.gfn_Click_On_Object("a", "Reassign");
		
		oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);
		
		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator("//select[@ng-model='reassignToAdminReviewer']", "iht_ittest03");
		
		oGenericUtils.gfn_Click_String_object_Xpath("(//button[text()='Reassign'])[2]");
		
		oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);
		
		
	    /*String sRule=oSeleniumUtils.get_TextFrom_Locator("(//div[@class='ngCellText ui-grid-cell-contents ng-binding ng-scope'])[4]");
		
		Serenity.setSessionVariable("NonCandidateRule").to(sRule);

		String sVersion=oSeleniumUtils.get_TextFrom_Locator("(//div[@class='ngCellText ui-grid-cell-contents ng-binding ng-scope'])[5]");
		
		Serenity.setSessionVariable("NonCandidateRuleVersion").to(sVersion);*/

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
		
		 oGenericUtils.gfn_Click_String_object_Xpath(AllRulesRadioBtn);
		 
		 oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);
	
		oSeleniumUtils.Click_given_Locator(SelectAllCheckBoxAdminMDScrub);
			
		//oGenericUtils.gfn_Click_String_object_Xpath("(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[1]");
			
		oGenericUtils.gfn_Click_On_Object("a", "Process Candidates");

		
	}
	
	public void filter_with_medical_policies_and_Reassign_reviewer_capture_rules_count(java.lang.String sMedicalPolicy,String sUser) throws InterruptedException {

        oGenericUtils.gfn_Click_On_Object("span", "Medical Policy");
           
           
           
           String sMedicalPolicy1=null;
           
           if (sMedicalPolicy.equalsIgnoreCase("MP01")) {
        	   
        	  oGenericUtils.gfn_Click_String_object_Xpath("(//div[@ng-click='selectButtonClick(row, $event)'])[2]");
              sMedicalPolicy1=oSeleniumUtils.get_TextFrom_Locator("((//span[text()='Medical Policy'])[2]/parent::div/parent::div/parent::div/parent::div/parent::div/parent::div/../../../..//div[@ng-style='Viewport.rowStyle(rowRenderIndex)']/child::div/child::div/child::div)[2]");
                
           }else {
        	  oGenericUtils.gfn_Click_String_object_Xpath("(//div[@ng-click='selectButtonClick(row, $event)'])[3]");
              sMedicalPolicy1=oSeleniumUtils.get_TextFrom_Locator("((//span[text()='Medical Policy'])[2]/parent::div/parent::div/parent::div/parent::div/parent::div/parent::div/../../../..//div[@ng-style='Viewport.rowStyle(rowRenderIndex)']/child::div/child::div/child::div)[3]");
           }
           
       
        oGenericUtils.gfn_Click_On_Object("button", "Apply Filter");

           oGenericUtils.gfn_Click_On_Object("span", "Cancel");
           
           oGenericUtils.gfn_Click_String_object_Xpath(SelectAllCheckBoxAdminMDScrub);
           
           String sSelectedRows=null;
           
           if (sMedicalPolicy.equalsIgnoreCase("MP01")) {
                
           sSelectedRows=StringUtils.substringAfter(oSeleniumUtils.get_TextFrom_Locator("//div[contains(text(),'Selected Row(s):')]"), ":").trim();
                
                Serenity.setSessionVariable("MPCount1").to(sSelectedRows);
                
           }else{
                
           sSelectedRows=StringUtils.substringAfter(oSeleniumUtils.get_TextFrom_Locator("//div[contains(text(),'Selected Row(s):')]"), ":").trim();
                
                Serenity.setSessionVariable("MPCount2").to(sSelectedRows);
           }
                     
           verify("Reassign Button is clicked in AdminScrub", oGenericUtils.gfn_Click_On_Object("a", "Reassign"));
           
          oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);
           
     oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator("//select[@ng-model='reassignToAdminReviewer']", sUser);
           
     oGenericUtils.gfn_Click_String_object_Xpath("(//button[text()='Reassign'])[2]");
           
          oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);
           
     oGenericUtils.gfn_Verify_String_Object_Exist("//div[text()='L']/child::span//img");

           oGenericUtils.gfn_Click_On_Object("a", "Filters");

           SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

           oGenericUtils.gfn_Click_On_Object("a", "Clear All Filters");

          oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);

           
           
           
           
     }

    @Step
     public void user_selects_two_rules_as_non_candidates_and_remaining_as_process_candidates() throws InterruptedException {
           
    oGenericUtils.gfn_Click_String_object_Xpath(AllRulesRadioBtn);
           
          oSeleniumUtils.waitUntil_Loading_Dailog_Disappears(AdminScrubImageLoader);
           
           SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
    
           oGenericUtils.gfn_Click_String_object_Xpath("(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[1]");
           
           oGenericUtils.gfn_Click_String_object_Xpath("(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[2]");
           
           oGenericUtils.gfn_Click_On_Object("a", "Process Non Candidates");
           
           SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
     oSeleniumUtils.Enter_given_Text_Element("//textarea[@id='modalProcessTextArea']", "Autotest");
     
     System.out.println();

           oGenericUtils.gfn_Click_On_Object("a", "Apply");
           
           SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
           
           oSeleniumUtils.Click_given_Locator(SelectAllCheckBoxAdminMDScrub);
           
           SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
           
           oGenericUtils.gfn_Click_String_object_Xpath("(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[1]");
           
           oGenericUtils.gfn_Click_String_object_Xpath("(//input[@ng-model='grid.appScope.rowsList[grid.appScope.findRowIndex(row)].checked'])[2]");
           
           SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
           
           oGenericUtils.gfn_Click_On_Object("a", "Process Candidates");
           
     }
     
    
    public boolean mouseOverDirectRelease(){

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);
			WebElement e = getDriver().findElement(By.xpath("//a[text()='Direct Release']"));
			(new Actions(getDriver())).moveToElement(e).perform();
			return true;
    }
    
    public String StatusBtnDirectRelease(){
    	String btnStatus=null;
    	if(getDriver().findElements(By.xpath("//a[text()='Direct Release' and @disabled='disabled' ]")).size()>0){
    		btnStatus="disabled";
    	}else{
    		btnStatus="enabled";
    	}
    		
    	return btnStatus;
    }
    
   
	

}
