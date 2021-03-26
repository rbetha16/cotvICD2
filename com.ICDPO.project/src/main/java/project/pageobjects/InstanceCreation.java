package project.pageobjects;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import project.utilities.GenericUtils;
import project.utilities.ProjectVariables;
import project.utilities.SeleniumUtils;

public class InstanceCreation extends PageObject {
	
	//trinath..
	GenericUtils oGenericUtils;
	
	CommonPage  oCommonPage;
	
	AdminPage oAdminPage;
	SeleniumUtils oSeleniumUtils;
	
	
	@FindBy(xpath = "//div[text()='Complete PO Assignment Review']")
	public WebElementFacade CompleteMDAssignmentReview;
	
	public String BulkAssign = "//p-tabpanel[@header='Decision Capture Assignments']//button[text()=' Bulk Assign']";
    
    public String GlobalAssignments = "//p-tabpanel[@header='Decision Capture Assignments']//button[text()='Copy Global Assignments']";
    
    public String DCSave = "//p-tabpanel[@header='Decision Capture Assignments']//button[text()=' Save']";
    
    public String DCCancel = "//p-tabpanel[@header='Decision Capture Assignments']//button[text()=' Cancel']";
    
    public String DCAssignmentsButtons = "//p-tabpanel[@header='Decision Capture Assignments']//button[text()='sValue']";
    
    public String TaskandInstance="//span[text()='sInstance']/../../..//a[text()='sTask']";
    
    public String ImpactMessesage ="//div[contains(text(),'Impact requested successfully for instance:')]";


	
	public void completeAssignments(String sTaskType, String sUser,String sOptional) throws Exception {

		boolean blnFlg=false;
		
		switch (sTaskType) {

		case "Review DC Assignments":
			
			SeleniumUtils.defaultWait(3000);
			
			oGenericUtils.gfn_Click_On_Object("span", "Decision Capture Assignments");
			
			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
			
			/*blnFlg=!oSeleniumUtils.is_WebElement_Enabled("(//button[text()=' Save'])[2]");
			
			Assert.assertTrue("Save is not disabled in DC Assignments",blnFlg);
			
			blnFlg=!oSeleniumUtils.is_WebElement_Enabled("(//button[text()=' Cancel'])[2]");
			
			Assert.assertTrue("Cancel is not disabled in DC Assignments",blnFlg);*/
		
			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			if (oSeleniumUtils.is_WebElement_Displayed("//h2[text()='Info']")==true) {
				
				//oGenericUtils.gfn_Verify_Object_Exist("h2", "Info");

				oGenericUtils.gfn_Click_On_Object("button", "Ok");
			}
			
			oGenericUtils.gfn_Click_String_object_Xpath("((//th[.=' Medical Policy '])[2]/..//div)[3]");
			
			oGenericUtils.gfn_Click_String_object_Xpath("(//button[.=' Bulk Reassign'])[2]");

			if (sUser.equals("ASSIGNMENT EXCEPTIONS")) {

				oSeleniumUtils.Enter_given_Text_Element(StringUtils.replace(oCommonPage.DecisionCapturePopUP, "sValue", "Preliminary Review:"),"iht_ittest01");

				oSeleniumUtils.Enter_given_Text_Element(StringUtils.replace(oCommonPage.DecisionCapturePopUP, "sValue", "Preliminary Peer Review:"),"iht_ittest02");

				oSeleniumUtils.Enter_given_Text_Element(StringUtils.replace(oCommonPage.DecisionCapturePopUP, "sValue", "Final Review:"),
						"iht_ittest03");

				oSeleniumUtils.Enter_given_Text_Element(StringUtils.replace(oCommonPage.DecisionCapturePopUP, "sValue", "Final Peer Review:"),
						"iht_ittest04");

				oSeleniumUtils.Enter_given_Text_Element(
						StringUtils.replace(oCommonPage.DecisionCapturePopUP, "sValue", "Editorial:"), "iht_ittest05");

				oSeleniumUtils.Enter_given_Text_Element(
						StringUtils.replace(oCommonPage.DecisionCapturePopUP, "sValue", "QA:"), "iht_ittest06");

				oSeleniumUtils.Enter_given_Text_Element(
						StringUtils.replace(oCommonPage.DecisionCapturePopUP, "sValue", "Configuration:"),
						"iht_ittest07");

				oSeleniumUtils.Enter_given_Text_Element(
						StringUtils.replace(oCommonPage.DecisionCapturePopUP, "sValue", "Test:"), "iht_ittest08");

				SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			} else {

				String [] sReviews={"Preliminary Review :","Final Review","Editorial","QA","Configuration","Test"};
				
				//String[]  sValues={"N/A","iht_ittest01","EditorialPool","QAPool","ConfigPool","TestingPool"};
				
				String[] sValues;
				
				if (sUser.contains("Primary PO")) {

					sValues = new String[] { "iht_ittest01", "iht_ittest02", "EditorialPool", "QAPool", "ConfigPool","TestingPool" };

				} else {

					sValues = new String[] { "N/A", "iht_ittest01", "EditorialPool", "QAPool", "ConfigPool","TestingPool" };
				}
				
				for (int i = 0; i < sReviews.length; i++) {
					
					oGenericUtils.gfn_Click_String_object_Xpath("//label[text()='"+sReviews[i]+"']/../..//span");
					
					oSeleniumUtils.Enter_given_Text_Element("(//input[@placeholder='Search'])["+(i+1)+"]", sValues[i]);
					
					if (sValues[i].equalsIgnoreCase("iht_ittest01") && !sUser.contains("Primary PO") || sValues[i].equalsIgnoreCase("iht_ittest02") ) {
						
						oGenericUtils.gfn_Click_String_object_Xpath("(//li//div[.='"+sValues[i]+"'])[2]");
						
					}else {
						
						oGenericUtils.gfn_Click_String_object_Xpath("(//li//div[.='"+sValues[i]+"'])[1]");
					}
					
					oGenericUtils.gfn_Click_String_object_Xpath("//label[text()='"+sReviews[i]+"']/../..//span");
				}
				
				/*if (sUser.contains("Primary PO")) {

					oGenericUtils.gfn_Click_String_object_Xpath("//label[text()='Preliminary Review :']/../..//span");

					oSeleniumUtils.Enter_given_Text_Element("(//input[@placeholder='Search'])[1]", "iht_ittest01");
					
					oGenericUtils.gfn_Click_String_object_Xpath("(//li//div[.='iht_ittest01'])[1]");

					oGenericUtils.gfn_Click_String_object_Xpath("//label[text()='Preliminary Review :']/../..//span");

				}*/
			}
			
			oGenericUtils.gfn_Click_On_Object("button", "OK");

			if (sOptional.equalsIgnoreCase("Cancel-Yes")) {
				
				oGenericUtils.gfn_Click_String_object_Xpath("(//button[text()=' Save'])[2]");
				
				oGenericUtils.gfn_Verify_Object_Exist("div", " Final Reviewer should not be same as Preliminary Reviewer ");

				oGenericUtils.gfn_Click_On_Object("button", " Cancel");

				oGenericUtils.gfn_Click_On_Object("div", "Are you sure you want to cancel the changes?");

				oGenericUtils.gfn_Click_On_Object("button", "Yes");
				
				oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
				
			} else {

				oGenericUtils.gfn_Click_On_Object("button", " Cancel");

				oGenericUtils.gfn_Click_On_Object("div", "Are you sure you want to cancel the changes?");

				oGenericUtils.gfn_Click_On_Object("button", "No");

				oGenericUtils.gfn_Click_On_Object("button", " Save");

				//oGenericUtils.gfn_Click_On_Object("button", "Yes");

				//oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
				
				 oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
		            
				//SeleniumUtils.defaultWait(3000);

				// oGenericUtils.gfn_Verify_Object_Exist("div", " Successfully
				// saved decision capture assignments for instance:
				// "+Serenity.sessionVariableCalled("NewInstanceName").toString()+"
				// ");

				if (oSeleniumUtils.is_WebElement_Enabled("//button[text()=' Complete DC Assignment Review']") == true) {

					oGenericUtils.gfn_Click_On_Object("button", " Complete DC Assignment Review");

					oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
					
					oGenericUtils.gfn_Click_On_Object("button", "Ok");
				}

				/*SeleniumUtils.switchWindowUsingWindowCount(3, 1, getDriver());

				SeleniumUtils.defaultWait(3000);*/
		            
			}

            //oGenericUtils.gfn_Click_On_Object("button", " Save");
            
           
			//oGenericUtils.gfn_Click_On_Object("button", "Yes");

			break;

		case "Review UI Assignments":
			
			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			//if (oSeleniumUtils.is_WebElement_Displayed("//h4[text()='Info']")==true) {
				
				//oGenericUtils.gfn_Verify_Object_Exist("h2", "Info");

				oGenericUtils.gfn_Click_On_Object("button", "Ok");
			//}
			
            oGenericUtils.gfn_Click_String_object_Xpath("((//th[.=' Medical Policy '])[1]/..//div)[3]");
			
			oGenericUtils.gfn_Click_On_Object("button", " Bulk Reassign");
			
			oGenericUtils.gfn_Click_String_object_Xpath("//label[.='Impact Analysis Rule Review :']/../..//td[2]");
		
			oSeleniumUtils.Enter_given_Text_Element("//input[@placeholder='Search']", "iht_ittest01");
			
			oGenericUtils.gfn_Click_String_object_Xpath("//li//div[.='iht_ittest01']");
			
			Assert.assertTrue("Impact Analysis Ruel Review Button", oGenericUtils.gfn_Click_String_object_Xpath("//label[.='Impact Analysis Rule Review :']/../..//td[2]"));
			
			oGenericUtils.gfn_Click_On_Object("button", "OK");
			
			int oEle = getDriver().findElements(By.xpath("//td[@ng-reflect-ng-switch='userName']")).size();

			Serenity.setSessionVariable("iNum").to(GenericUtils.generate_Random_Number_for_Given_Range(oEle));

			if (Serenity.sessionVariableCalled("iNum").equals(0)) {

				Serenity.setSessionVariable("iNum").to("1");

			}
		
			System.out.println(Serenity.sessionVariableCalled("iNum").toString());
			
			String sDefaultUser=oSeleniumUtils.get_TextFrom_Locator("(//td[@ng-reflect-ng-switch='userName'])["+Serenity.sessionVariableCalled("iNum")+"]");
			
			oGenericUtils.gfn_Click_String_object_Xpath("(//td[@ng-reflect-ng-switch='userName'])["+Serenity.sessionVariableCalled("iNum")+"]");
			
			oGenericUtils.gfn_Click_String_object_Xpath("(//td[@ng-reflect-ng-switch='userName'])["+Serenity.sessionVariableCalled("iNum")+"]");
			
			int oUserList=getDriver().findElements(By.xpath("//li[@role='option' and contains(@class,'ui-dropdown-item ui-corner-all')]//span")).size();
			
			Serenity.setSessionVariable("iUsers").to(GenericUtils.generate_Random_Number_for_Given_Range(oUserList));
			
			if (Serenity.sessionVariableCalled("iUsers").equals(0)) {

				Serenity.setSessionVariable("iUsers").to("1");

			}
			
			String sSelectedValue=oSeleniumUtils.get_StringTextFrom_Locator("(//li[@role='option' and contains(@class,'ui-dropdown-item ui-corner-all')]//span)["+Serenity.sessionVariableCalled("iUsers")+"]");
			
			oGenericUtils.gfn_Click_String_object_Xpath("(//li[@role='option' and contains(@class,'ui-dropdown-item ui-corner-all')]//span)["+Serenity.sessionVariableCalled("iUsers")+"]");
			
			String sValueAfterSelection= oSeleniumUtils.get_StringTextFrom_Locator("(//td[@ng-reflect-ng-switch='userName'])["+Serenity.sessionVariableCalled("iNum")+"]");
			
			boolean bln= sSelectedValue.trim().equalsIgnoreCase(sValueAfterSelection.trim());
			
		    System.out.println("asdfasdfasdfdsaf"+bln);
			
		    if (sOptional.equalsIgnoreCase("Cancel-Yes")) {
				
		    	oGenericUtils.gfn_Click_On_Object("button", " Cancel");
		    	
		    	oGenericUtils.gfn_Click_On_Object("div", "Are you sure you want to cancel the changes?");
		    	
		    	oGenericUtils.gfn_Click_On_Object("button", "Yes");
		   
		    	oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.LoadingIcon);
		    	
		    	SeleniumUtils.defaultWait(6000);
		   
		    	String sAfterSelection= oSeleniumUtils.get_TextFrom_Locator("(//td[@ng-reflect-ng-switch='userName'])["+Serenity.sessionVariableCalled("iNum")+"]");
		    	
		    	boolean blnUser=sDefaultUser.equalsIgnoreCase(sAfterSelection);
		    	
		    	Assert.assertTrue("Value is selected"+"Default Value:"+sDefaultUser+"Value After Selection:"+sAfterSelection, blnUser);
		    	
			}else {
			
				oGenericUtils.gfn_Click_On_Object("button", " Cancel");

				oGenericUtils.gfn_Click_On_Object("div", "Are you sure you want to cancel the changes?");

				oGenericUtils.gfn_Click_On_Object("button", "No");
		    	
				oGenericUtils.gfn_Click_On_Object("button", " Save");
				
				oGenericUtils.gfn_Click_On_Object("button", "Yes");
				
                String sAfterSelection= oSeleniumUtils.get_TextFrom_Locator("(//td[@ng-reflect-ng-switch='userName'])["+Serenity.sessionVariableCalled("iNum")+"]");
		    	
                boolean blnUser=!sDefaultUser.equalsIgnoreCase(sSelectedValue);
                
                if (sSelectedValue.equalsIgnoreCase(sDefaultUser)) {
					
                	 boolean blnUser1=sDefaultUser.equalsIgnoreCase(sAfterSelection);
                	 
                	Assert.assertTrue("Value is selected"+"Default Value:"+sDefaultUser+"Value After Selection:"+sAfterSelection, blnUser1);
				}else {
					
					Assert.assertTrue("Value is selected"+"Default Value:"+sDefaultUser+"Value After Selection:"+sAfterSelection, blnUser);
				}
		    	
		    	
		    	
				
				Assert.assertTrue("Impact Analysis Rule review Save message is displayed", oGenericUtils.gfn_Verify_String_Object_Exist("//div[contains(text(),' Successfully saved rule review assignments for instance')]"));
				
				oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
				
				if (oSeleniumUtils.is_WebElement_Enabled("//button[text()=' Complete UI Assignment Review']")==true) {
					
					oGenericUtils.gfn_Click_On_Object("button", " Complete UI Assignment Review");
				}
				
			}
		    
			//SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			//oGenericUtils.gfn_Verify_Object_Exist("div", "Complete UI Assignment Review");

			
			
			
			
			oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);

			break;

		case "Review Obsolete Payers":

			SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

			oCommonPage.waitUntil_Loading_Dailog_Disappears(oCommonPage.Loading);

			oGenericUtils.gfn_Verify_Object_Exist("div", "Review Tasks");

			oGenericUtils.gfn_Click_On_Object("div", "OK");

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oGenericUtils.gfn_Click_On_Object("div", "Complete CPM Assignment Review");

			oGenericUtils.gfn_Verify_Object_Exist("div", "Complete Task");

			oGenericUtils.gfn_Click_String_object_Xpath(oCommonPage.ClaimTask);

			oGenericUtils.gfn_Click_On_Object("div", "OK");

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oGenericUtils.gfn_Click_On_Object("div", "Complete Obsolete Payer Review");

			oGenericUtils.gfn_Verify_Object_Exist("div", "Complete Task");

			oGenericUtils.gfn_Click_String_object_Xpath(oCommonPage.ClaimTask);

			oGenericUtils.gfn_Click_On_Object("div", "OK");

			oCommonPage.closeAllTabs();

			break;
		case "Review PO Assignments":

			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			oCommonPage.waitUntil_Loading_Dailog_Disappears(oCommonPage.Loading);

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oGenericUtils.gfn_Verify_Object_Exist("div", "Review Tasks");

			oGenericUtils.gfn_Click_On_Object("div", "OK");

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			oGenericUtils.gfn_Verify_Object_Exist("div", "Complete PO Assignment Review");

			oGenericUtils.gfn_Click_On_Object("div", "Complete PO Assignment Review");

			oGenericUtils.gfn_Verify_Object_Exist("div", "Complete Task");

			oGenericUtils.gfn_Click_String_object_Xpath(oCommonPage.ClaimTask);

			oGenericUtils.gfn_Click_On_Object("div", "OK");

			oCommonPage.closeAllTabs();

			break;

		case "Assignment Exceptions":

			int sSheetNo = 0;

			SeleniumUtils.defaultWait(ProjectVariables.MAX_TIME_OUT);

			oCommonPage.waitUntil_Loading_Dailog_Disappears(oCommonPage.Loading);

			if (sUser.equals("0")) {

				sSheetNo = 0;
			} else {
				sSheetNo = 1;
			}

			SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

			//completeAssignmentExceptions("", sSheetNo);

			oCommonPage.closeAllTabs();

			break;

		default:
			Assert.assertTrue("Case value is not matched for completeAssignments()", false);

		}

	}

	

}
