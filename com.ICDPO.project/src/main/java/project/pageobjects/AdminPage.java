package project.pageobjects;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Verify;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;
import project.utilities.DBUtils;
import project.utilities.GenericUtils;
import project.utilities.ProjectVariables;
import project.utilities.SeleniumUtils;


public class AdminPage extends PageObject {

	// trinath..
	CommonPage oCommonPage;
	

	public String NewInstanceCreate = "//label[text()='sValue']/../following-sibling::td//input";

	public String UpdateType = "//label[text()='Update Type :']/../following-sibling::td//select";

	public String DescriptionTxt = "//label[text()='Description :']/../following-sibling::td//textarea";

	public String AdminDropdown = "//label[text()='sValue']/../following-sibling::td[1]//span//span[text()='Select']";

	public String AdminDropdownDeselection = "//label[text()='sValue']/../following-sibling::td[1]//span//span";

	public String AdminInstance = "//tr[(td[1]='sValue')]//div[@class='GEFT4QHBE3C iht-HyperLinkText']";

	public String ExportExcel = "//button[text()=' Export to Excel']";

	// public String Loading = "//div[@style='background-color: rgb(0, 100,
	// 0);']";

	@FindBy(xpath = "//div[@style='background-color: rgb(0, 100, 0);']")
	public WebElementFacade Loading;

	public String LoadingIcon = "//div[@style='background-color: rgb(0, 100, 0);']";

	GenericUtils oGenericUtils;

	SeleniumUtils oSeleniumUtils;

	public String createInstanceAdmin(String sNewInstancName, String sAdminOPS, String sAdminMD,
			String sOptionalParameter) throws InterruptedException, IOException {

		boolean blnFlg = false;
		
		String sPRMID = "";

		if (sNewInstancName.equalsIgnoreCase("Random")) {

			Serenity.setSessionVariable("NewInstanceName")
					.to("ICDPOAutoIV" + GenericUtils.generate_Random_Number_for_Given_Range(1000));

			System.out.println(Serenity.sessionVariableCalled("NewInstanceName").toString());

		} else {

			Serenity.setSessionVariable("NewInstanceName").to(sNewInstancName);
		}

		List<String> sAdminMDList = Arrays.asList(sAdminMD.split(";"));

		List<String> sAdminOPSList = Arrays.asList(sAdminOPS.split(";"));

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		// oGenericUtils.gfn_Click_On_Object("button", " Add");

		oGenericUtils.gfn_Click_String_object_Xpath(
				"(//span[text()='Interpretive Update Instances'])[2]/../../../../../..//button[contains(text(),'Add')]");

		SeleniumUtils.defaultWait(ProjectVariables.MIN_TIME_OUT);

		oSeleniumUtils.Enter_given_Text_Element(StringUtils.replace(NewInstanceCreate, "sValue", "Instance Name :"),
				Serenity.sessionVariableCalled("NewInstanceName"));

		oSeleniumUtils.Enter_given_Text_Element(DescriptionTxt, sNewInstancName);
		
				
		if(sOptionalParameter.equalsIgnoreCase("CREATEPRMID")){
            sPRMID = createPRMID("CREATEPRMID");
       }else{
            EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();
            sPRMID=variables.getProperty("PRMID");
       }

		
		oSeleniumUtils.Enter_given_Text_Element(StringUtils.replace(NewInstanceCreate, "sValue", "Library PRM :"),sPRMID);

		oSeleniumUtils.Enter_given_Text_Element(StringUtils.replace(NewInstanceCreate, "sValue", "Custom PRM :"),sPRMID);
				

		SeleniumUtils.defaultWait(3000);

		Calendar cal = Calendar.getInstance();

		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

		String dayOfMonthStr = String.valueOf(dayOfMonth);

		System.out.println(dayOfMonthStr);

		oGenericUtils.gfn_Click_String_object_Xpath("//button[@class='mat-icon-button']//span");

		oGenericUtils.gfn_Click_On_Object("div", dayOfMonthStr);

		oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(UpdateType, "ICD");

		select_AdminOps_And_AdminMD(sAdminOPSList, "Admin OPS :");

		select_AdminOps_And_AdminMD(sAdminMDList, "Admin PO :");

		if (sOptionalParameter.equalsIgnoreCase("Cancel-Yes")) {

			oGenericUtils.gfn_Click_On_Object("button", " Cancel");

			oGenericUtils.gfn_Verify_Object_Exist("h2", "Administartion - Interpretive Update Instances");

			oGenericUtils.gfn_Verify_Object_Exist("div", "Are you sure you want to cancel the current ADD process?");

			oGenericUtils.gfn_Click_On_Object("button", "Yes");

			oGenericUtils.gfn_Verify_Object_Exist("span", "Description");

		} else if (sOptionalParameter.equalsIgnoreCase("Cancel-No")) {

			oGenericUtils.gfn_Click_On_Object("button", " Cancel");

			oGenericUtils.gfn_Verify_Object_Exist("h2", "Administartion - Interpretive Update Instances");

			oGenericUtils.gfn_Verify_Object_Exist("div", "Are you sure you want to cancel the current ADD process?");

			oGenericUtils.gfn_Click_On_Object("button", "No");

			oGenericUtils.gfn_Verify_String_Object_Exist("//span[contains(text(),'iht_ittest01')]");

		} else {

			blnFlg = oGenericUtils.gfn_Click_On_Object("button", " Save");

			GenericUtils.Verify("Instance creation Save button is clicked", blnFlg);

			oCommonPage.waitUntil_Loading_Dailog_Disappears(Loading);

			SeleniumUtils.defaultWait(3000);

			blnFlg = oGenericUtils.gfn_Verify_Object_Exist("div", " The update instance is saved successfully. ");

			GenericUtils.Verify("Instance creation success message", blnFlg);

		}
		
		return sNewInstancName;

	}

	public void select_AdminOps_And_AdminMD(List<String> sList, String sValue) throws InterruptedException {

		oGenericUtils.gfn_Click_String_object_Xpath(StringUtils.replace(AdminDropdown, "sValue", sValue));

		for (int i = 0; i < sList.size(); i++) {

			oGenericUtils.gfn_Click_On_Object("div", sList.get(i));

			if (i == 3) {

				oGenericUtils.gfn_Verify_String_Object_Exist("//span[contains(text(),'+')]");

			}

		}

		oGenericUtils.gfn_Click_String_object_Xpath(StringUtils.replace(AdminDropdownDeselection, "sValue", sValue));
	}

	public void the_user_had_navigated_to_an_already_created_Update_Instance() {

		int iInstanceNames = getDriver().findElements(By.xpath("//div[@col-id='instanceName']//a")).size();

		Serenity.setSessionVariable("AlreadyCreatedInstanceName")
				.to(GenericUtils.generate_Random_Number_for_Given_Range(iInstanceNames));

		if (Serenity.sessionVariableCalled("AlreadyCreatedInstanceName").equals(0)) {

			Serenity.setSessionVariable("AlreadyCreatedInstanceName").to(1);
		}

		Serenity.setSessionVariable("CreatedInstanceName")
				.to(oSeleniumUtils.get_All_Text_from_Locator("(//div[@col-id='instanceName']//a)["
						+ Serenity.sessionVariableCalled("AlreadyCreatedInstanceName") + "]"));

		oGenericUtils.gfn_Click_String_object_Xpath("(//div[@col-id='instanceName']//a)["
				+ Serenity.sessionVariableCalled("AlreadyCreatedInstanceName") + "]");

	}

	public void the_user_had_updated_field_value_as(String sFields, String arg2) throws InterruptedException {

		SeleniumUtils.defaultWait(3000);

		List<String> oFieldsList = Arrays.asList(sFields.split(";"));

		for (int i = 0; i < oFieldsList.size(); i++) {

			switch (oFieldsList.get(i)) {

			case "Effective Date":

				Calendar cal = Calendar.getInstance();

				int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

				if (dayOfMonth > 27) {

					dayOfMonth = 26;
				}
				String dayOfMonthStr = String.valueOf(dayOfMonth + 1);

				System.out.println(dayOfMonthStr);

				Serenity.setSessionVariable("DateModified").to(dayOfMonthStr);

				oGenericUtils.gfn_Click_String_object_Xpath("//button[@class='mat-icon-button']//span");

				oGenericUtils.gfn_Click_On_Object("div", dayOfMonthStr);

				String sEditedDate = oSeleniumUtils
						.get_TextFrom_Locator(StringUtils.replace(NewInstanceCreate, "sValue", "Effective Date :"));

				Serenity.setSessionVariable("EditedDate").to(sEditedDate);

				break;

			case "Description":

				Serenity.setSessionVariable("UpdatedDescription").to(
						"Edited Description after Creation" + GenericUtils.generate_Random_Number_for_Given_Range(100));

				oSeleniumUtils.Enter_given_Text_Element(DescriptionTxt,
						Serenity.sessionVariableCalled("UpdatedDescription"));

				break;
			/*
			 * case "Admin OPS": case "Admin MD": String
			 * sAdminOPS="iht_ittest03";
			 * 
			 * //String sAdminMD="iht_ittest03";
			 * 
			 * oGenericUtils.gfn_Click_String_object_Xpath("//label[text()='"+
			 * oFieldsList.get(i)+
			 * " :']/../following-sibling::td[1]//div[text()='Select All']");
			 * 
			 * oGenericUtils.gfn_Click_String_object_Xpath("//label[text()='"+
			 * oFieldsList.get(i)+
			 * " :']/../following-sibling::td[1]//div[text()='UnSelect All']");
			 * 
			 * List<String> sAdminOPSList = Arrays.asList(sAdminOPS.split(";"));
			 * 
			 * select_AdminOps_And_AdminMD(sAdminOPSList,""+oFieldsList.get(i)+
			 * " :");
			 * 
			 * break;
			 */

			case "Library PRM":
			case "Custom PRM":

				String sExistingPr = oSeleniumUtils.get_TextFrom_Locator("//input[@formcontrolname='libraryPRM']");

				List<String> oPRlist = Arrays.asList(ProjectVariables.LibCustomPRMEdited.split(";"));

				if (oFieldsList.get(i).equalsIgnoreCase("Library PRM")) {

					for (int j = 0; j < oPRlist.size(); j++) {

						if (!sExistingPr.trim().equals(oPRlist.get(j).trim())) {

							Serenity.setSessionVariable("New PR").to(oPRlist.get(j).trim());

							oSeleniumUtils.Enter_given_Text_Element(
									StringUtils.replace(NewInstanceCreate, "sValue", "Library PRM :"),
									oPRlist.get(j).trim());

							break;
						}

					}
				} else {
					oSeleniumUtils.Enter_given_Text_Element(
							StringUtils.replace(NewInstanceCreate, "sValue", "Custom PRM :"),
							Serenity.sessionVariableCalled("New PR").toString());
				}

				break;
			default:
				break;
			}
		}

	}

	public void the_user_clicks_on_button(String sButton) throws InterruptedException {

		switch (sButton) {
		case "Save":

			oGenericUtils.gfn_Click_On_Object("button", " Save");

			oCommonPage.waitUntil_Loading_Dailog_Disappears(Loading);

			break;

		default:
			break;
		}

	}

	public void all_the_updates_on_should_be_saved_successfully_in_DB(String sFields, String arg2) throws Exception {

		EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();

		String sQuery = "select * from irdm.update_instances where update_instance_name = '"
				+ variables.getProperty("IU.PrilimMDInstance") + "'";

		// String sQuery= "select * from irdm.update_instances where
		// update_instance_name =
		// '"+Serenity.sessionVariableCalled("NewInstanceName").toString()+"'";

		String sUpdatedField = null;

		boolean bln;

		List<String> oFieldsList = Arrays.asList(sFields.split(";"));

		for (int i = 0; i < oFieldsList.size(); i++) {

			switch (oFieldsList.get(i)) {

			case "Effective Date":

				sUpdatedField = DBUtils.db_GetFirstValueforColumn(sQuery, "EFFECTIVE_DATE");

				SimpleDateFormat formatter = new SimpleDateFormat("MM/DD/yyyy");

				String strDate = formatter.format(sUpdatedField);

				System.out.println(strDate);

				bln = sUpdatedField.equalsIgnoreCase(Serenity.sessionVariableCalled("EditedDate"));

				// GenericUtils.Verify("Effective Date"+"IU
				// :"+Serenity.sessionVariableCalled("EditedDate")+"DB
				// Date:"+sUpdatedField, bln);

				System.out.println("Effective Date" + bln);

				break;

			case "Description":

				sUpdatedField = DBUtils.db_GetFirstValueforColumn(sQuery, "UPDATE_INSTANCE_DESC");

				bln = sUpdatedField.equalsIgnoreCase(Serenity.sessionVariableCalled("UpdatedDescription"));

				GenericUtils.Verify("Description" + "IU :" + Serenity.sessionVariableCalled("UpdatedDescription")
						+ "DB Description:" + sUpdatedField, bln);

				System.out.println("Description" + bln);

				break;
			case "Library PRM":
			case "Custom PRM":

				if (oFieldsList.get(i).equalsIgnoreCase("Library PRM")) {

					sUpdatedField = DBUtils.db_GetFirstValueforColumn(sQuery, "LIBRARY_PROJECT_CODE");

				} else {

					sUpdatedField = DBUtils.db_GetFirstValueforColumn(sQuery, "CUSTOM_PROJECT_CODE");
				}

				bln = sUpdatedField.equalsIgnoreCase(Serenity.sessionVariableCalled("New PR").toString());

				GenericUtils.Verify(
						"PRM" + "IU :" + Serenity.sessionVariableCalled("New PR") + "DB PRM:" + sUpdatedField, bln);

				System.out.println("PRM" + bln);

				break;

			default:

			}
		}

	}

	public void admin_MDs_user_which_we_assigned_should_be_displayed() throws InterruptedException {

		oGenericUtils.gfn_Verify_String_Object_Exist("//div[.='Admin PO Scrub Review']/./..//div[.='iht_ittest02']");

		oGenericUtils.gfn_Verify_String_Object_Exist("//div[.='Admin PO Scrub Review']/./..//div[.='iht_ittest03']");
	}

	@Step
	public boolean goToActionsInAdminTab(String arg1) throws InterruptedException {

		oCommonPage.waitUntil_Loading_Dailog_Disappears(Loading);

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oGenericUtils.gfn_Click_On_Object("span", "Administration");

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oGenericUtils.gfn_Click_On_Object("span", "Interpretive Update Instances");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(Loading);

		oSeleniumUtils.Enter_given_Text_Element("(//input[@ref='eColumnFloatingFilter'])[1]",
				Serenity.sessionVariableCalled("IUInstanceName").toString());

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		oGenericUtils.gfn_Click_On_Object("a", Serenity.sessionVariableCalled("IUInstanceName").toString());

		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

		// oGenericUtils.gfn_Verify_Object_Exist("//button[text()='Actions']",
		// 2);

		return true;

	}

	public void clickInstaneInAdmin(String sRole, String sInstanceType, String sColoumnName, String sColumnValue)
			throws InterruptedException {

		oCommonPage.waitUntil_Loading_Dailog_Disappears(Loading);

		oGenericUtils.gfn_Click_On_Object("button", " Refresh");

		oCommonPage.waitUntil_Loading_Dailog_Disappears(Loading);

		if (sColumnValue.contains("Status")) {

			oSeleniumUtils.Enter_given_Text_Element("(//input[@ref='eColumnFloatingFilter'])[4]",
					StringUtils.substringAfter(sColumnValue, "-"));

			oGenericUtils.gfn_Click_String_object_Xpath("//div[@col-id='instanceName']//a");

		} else {

			clickInstanceinAdmin(sInstanceType, sColumnValue);

			Serenity.setSessionVariable("Role").to(sRole);

			oCommonPage.waitUntil_Loading_Dailog_Disappears(Loading);
		}

	}

	public void clickInstanceinAdmin(String sInstancetype, String sColumnValue) throws InterruptedException {

		boolean blnFlg = false;

		EnvironmentVariables variables = SystemEnvironmentVariables.createEnvironmentVariables();

		// Serenity.setSessionVariable("AdminInstance").to(sInstancetype);

		if (sInstancetype.equalsIgnoreCase("Random")) {

		} else {

			Serenity.setSessionVariable("NewInstanceName").to(sInstancetype);
		}

		HashMap<String, String> hm = new HashMap<String, String>();

		hm.put("FINAL PO DEL", variables.getProperty("IU.FinalMDInstance"));

		hm.put("PRELIM PO DEL", variables.getProperty("IU.PrilimMDInstance"));

		hm.put("FINAL MD DEL-Subsequent", ProjectVariables.FinalMDInstanceSubsequent);

		hm.put("AE PRELIM MD DEL", ProjectVariables.AEPrelimInstance);

		hm.put("All Review Types", ProjectVariables.AllReviewTypesInstance);

		hm.put("PR MODIFY DEL", ProjectVariables.PRMODIFY);

		hm.put("Non Candiate Rules-Instance", ProjectVariables.NonCandiateRulesInstance);

		GenericUtils.Verify(sInstancetype + hm.get(sInstancetype), true);

		// Serenity.setSessionVariable("NewInstanceName").to(hm.get(sInstancetype));

		Serenity.setSessionVariable("IUInstanceName").to(hm.get(sInstancetype));

		if (sInstancetype.contains("DEL") || sInstancetype.contains("Non Candiate Rules-Instance")) {

			oSeleniumUtils.Enter_given_Text_Element("//input[@class='ag-floating-filter-input']",
					hm.get(sInstancetype));

			blnFlg = oGenericUtils.gfn_Click_On_Object("a", hm.get(sInstancetype));

			GenericUtils.Verify("Instacne name " + hm.get(sInstancetype) + " is clicked", blnFlg);

		} else if (sInstancetype.equalsIgnoreCase("Random")) {

			SeleniumUtils.defaultWait(7000);

			// oGenericUtils.gfn_Verify_Object_Exist("a",
			// Serenity.sessionVariableCalled("NewInstanceName"));

			oSeleniumUtils.Enter_given_Text_Element("//input[@class='ag-floating-filter-input']",
					Serenity.sessionVariableCalled("NewInstanceName"));

			if (sColumnValue.equalsIgnoreCase("Status-SameSim")) {

				oGenericUtils.gfn_Verify_Object_Exist("span", "In Same/Sim Analysis");

			} else {

				oGenericUtils.gfn_Click_On_Object("a", Serenity.sessionVariableCalled("NewInstanceName"));
			}

		} else {

			oSeleniumUtils.Enter_given_Text_Element("//input[@class='ag-floating-filter-input']", sInstancetype);

			if (sColumnValue.equalsIgnoreCase("Status-SameSim")) {

				oGenericUtils.gfn_Verify_Object_Exist("span", "In Same/Sim Analysis");
			} else {

				oGenericUtils.gfn_Click_On_Object("a", sInstancetype);
			}

		}

	}

	public void validate_RequirePresentation(String sButton, String sPage) throws InterruptedException {

		String sRule = Serenity.sessionVariableCalled("MidRuleVersion");

		if (sButton.equalsIgnoreCase("Set Requires Presentation")) {

			System.out.println("//button[contains(text(),'" + sButton + "'])");

			oGenericUtils.gfn_Click_String_object_Xpath("//button[contains(text(),'" + sButton + "')]");

			oGenericUtils.gfn_Verify_Object_Exist("b",
					"//b[text()='Selected rules will be set to Requires Presentation status for all review payers assigned to this CPM. Rules will be removed from the review queue and no changes will be made to the rules at this time. A rule can be returned to CPM Review for CPM decision capture by using the Return Requires Presentation Rule function.Continue? ']");

			oGenericUtils.gfn_Click_On_Object("button", "Cancel");

			oGenericUtils.gfn_Click_String_object_Xpath("//button[contains(text(),'" + sButton + "')]");

			oGenericUtils.gfn_Click_On_Object("button", "Set To Require Presentation");

			oGenericUtils.gfn_Verify_Object_Exist("div", "you have successfully updated the selected rules.");

			oGenericUtils.gfn_Click_On_Object("button", "Ok");

		} else {

			oGenericUtils.gfn_Click_String_object_Xpath("//button[contains(text(),'" + sButton + "')]");

			oGenericUtils.gfn_Click_On_Object("button", "Cancel");

			oGenericUtils.gfn_Click_String_object_Xpath("//button[contains(text(),'" + sButton + "')]");

			SeleniumUtils.defaultWait(4000);

			oGenericUtils.gfn_Click_String_object_Xpath(
					"//b[text()='Select rule(s) to return for CPM Decision Review: ']/ancestor::mat-dialog-container//span[contains(text(),'"
							+ sRule + "')]/../preceding-sibling::td//div//child::div[2]");

			oGenericUtils.gfn_Click_On_Object("button", "Return Selected Rules For CPM Decision Review");

			oGenericUtils.gfn_Verify_Object_Exist("div", "you have successfully returned 1 rule.");

			oGenericUtils.gfn_Click_On_Object("button", "Ok");
		}

	}
	
	public String createPRMID(String sUpdateType) throws IOException {
		
		String sRequestedPRMIDXML = createRequestXML(sUpdateType, "","");
		String sPRMIDResponse = GenericUtils.sendPostRequest(ProjectVariables.sLotusPRMURL, sRequestedPRMIDXML);
		String sPRMID = GenericUtils.getTextFromTwoStrings(sPRMIDResponse, "<REQID>", "</REQID>");
		System.out.println("Text: " + sPRMID);
		return sPRMID;
	}
	
	public String createRequestXML(String sRequestType, String sProjectID, String sSubRuleKey) {

		String sCurrentDate = GenericUtils.getDate_given_Format();
		String sRequestedXML = "";
		switch (sRequestType) {

				case "CREATERMRSTUBIUPD":
		
					sRequestedXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:com.ihealth.ws.iupd\">\r\n"
							+ "   <soapenv:Header/>\r\n" + "   <soapenv:Body>\r\n" + "      <urn:CREATERMRSTUBIUPD>\r\n"
							+ "         <CREATEDBY>IHT_ITTEST04</CREATEDBY>\r\n" + "         <PRID>" + sProjectID
							+ "</PRID>\r\n" + "         <SUBRULEKEY>" + sSubRuleKey + "</SUBRULEKEY>\r\n"
							+ "         <REQFIGAROSWITCH>No</REQFIGAROSWITCH>\r\n" + "         <ARDRULE>No</ARDRULE>\r\n"
							+ "         <DUEDATE>02/20/2020</DUEDATE>\r\n" + "         <REQUESTTYPEKEY>2</REQUESTTYPEKEY>\r\n"
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
					
					sRequestedXML = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:com.ihealth.ws.iupd\">\r\n" +
							"   <soapenv:Header/>\r\n" +
							"   <soapenv:Body>\r\n" +
							"      <urn:DEACTIVATERMRIUPD>\r\n" +
							"         <ID>" + sProjectID + "</ID>\r\n" +
							"         <ICMCLIENTS>N/A</ICMCLIENTS>\r\n" +
							"         <ICMOCLIENTS>N/A</ICMOCLIENTS>\r\n" +
							"         <NOTES>Subrule desc</NOTES>\r\n" +
							"         <REFDETAILS>referwnce</REFDETAILS>\r\n" +
							"         <DOSFROM>01/01/1763</DOSFROM>\r\n" +
							"         <DOSTO>12/31/9999</DOSTO>\r\n" +
							"         <CHANGECOMM>Deactivated through post service</CHANGECOMM>\r\n" +
							"         <SWITCHTYPE>4</SWITCHTYPE>\r\n" +
							"      </urn:DEACTIVATERMRIUPD>\r\n" +
							"   </soapenv:Body>\r\n" +
							"</soapenv:Envelope>";
					break;
					
				case "CREATEPRMID":
					sRequestedXML  = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:com.cotiviti.ws.prm\">" +
							"   <soapenv:Header/>" +
							"   <soapenv:Body>" +
							"      <urn:CREATEPROJECTREQUEST>" +
							"         <USERID>icpmtest37</USERID>" +
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

		}
		
		System.out.println("Requested XML ::" + sRequestedXML);

		return sRequestedXML;
	}

}
