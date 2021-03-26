package project.feature.steps.definitions;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.jayway.jsonpath.Criteria;
import com.jayway.jsonpath.Filter;
//import com.jayway.restassured.response.Response;

import io.restassured.response.Response;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;
import project.pageobjects.AdminPage;
import project.pageobjects.CommonPage;
import project.utilities.DBUtils;
import project.utilities.GenericUtils;
import project.utilities.ProjectVariables;
import project.utilities.SeleniumUtils;

//trinath..
public class AdminStepdef extends ScenarioSteps{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String String = null;

	//trinath
	GenericUtils oGenericUtils;
	
	SeleniumUtils oSeleniumUtils;
		
    AdminPage oAdminPage;
    
    CommonPage oCommonPage;
    
	
	@Step
	public void createInstance(String sNewInstancName, String sAdminOPS, String sAdminMD,String sOptionalParameter) throws InterruptedException, IOException {

		String sInstance = oAdminPage.createInstanceAdmin(sNewInstancName, sAdminOPS, sAdminMD,sOptionalParameter);
			if (sInstance.length()>5){
			verify("InstanceName:--"  +sInstance , true);
			
			verify("Instance is created sucessfully " +Serenity.sessionVariableCalled("NewInstanceName").toString(), true);
		}else{
			verify("Instance is not created not sucessfully " +Serenity.sessionVariableCalled("NewInstanceName").toString(), false );
		}
		
	}
	
	@Step
	public void validateAdminRuleReviewValue(String sRole, String sInstanceType, String sColoumnName,String sColumnValue) throws InterruptedException {

		oAdminPage.clickInstaneInAdmin(sRole,sInstanceType,sColoumnName,sColumnValue);
		
		
	}
	

	@Step
	public void verify(String sDescription, boolean blnStatus) {

		GenericUtils.Verify(sDescription, blnStatus);
	}

	@Step
	public void user_Reassign_Tasks_to(String sTasks, String sUsers) throws InterruptedException {
		
		List<String> sTasksList = Arrays.asList(sTasks.split(";"));
		
		List<String> sUsersList = Arrays.asList(sUsers.split(";"));
		
		for (int i = 0; i < sTasksList.size(); i++) {
		
			oGenericUtils.gfn_Click_String_object_Xpath("//div[.='"+sTasksList.get(i)+"']/..//span");
			
			oGenericUtils.gfn_Click_On_Object("button", " Reassign");
						
			if (oSeleniumUtils.is_WebElement_Displayed("//h2[text()='Info']")==true) {
				
				oGenericUtils.gfn_Click_On_Object("button", "Ok");
				
			}else {
				
				oGenericUtils.gfn_Click_String_object_Xpath("//label[text()='Select User to ReAssign :']/../following-sibling::td");
				
				if (sUsersList.size()>1) {
					
					oSeleniumUtils.Enter_given_Text_Element("//li[contains(@class,'filter-textbox')]//input", sUsersList.get(i));
					
					oGenericUtils.gfn_Click_String_object_Xpath("(//div[text()='"+sUsersList.get(i)+"'])[position()=last()]");
					
				}else {
					
					//ListValuesInReassignDropdown(sTasksList.get(i));
					
                    oSeleniumUtils.Enter_given_Text_Element("//li[contains(@class,'filter-textbox')]//input", sUsers);
					
					oGenericUtils.gfn_Click_String_object_Xpath("(//div[text()='"+sUsers+"'])[position()=last()]");
				}
				
				
						
				oGenericUtils.gfn_Click_String_object_Xpath("//label[text()='Select User to ReAssign :']/../following-sibling::td");

				oGenericUtils.gfn_Click_On_Object("button", "OK");
				
				oCommonPage.waitUntil_Loading_Dailog_Disappears(oAdminPage.Loading);
				
				//SeleniumUtils.defaultWait(2000);
			}
			
			
		}
		
		oGenericUtils.gfn_Click_On_Object("span", Serenity.sessionVariableCalled("NewInstanceName"));
		
		oGenericUtils.gfn_Click_On_Object("button", " Actions");
		
		oGenericUtils.gfn_Click_On_Object("button", " Update Instance Assignments ");
	
	}

	private void ListValuesInReassignDropdown(String string) {
			
		List<WebElement> oListMD=getDriver().findElements(By.xpath("//li[contains(@class,'multiselect-item-checkbox')]//div"));
		
		ArrayList<String> oUIList=new ArrayList<String>();
		
		for (int i = 0; i < oListMD.size(); i++) {
			
			oUIList.add(oListMD.get(i).getText());
			
		}
		
		switch (string) {
	
		case "Review PO Assignments":
			
			compareTwoLists(oUIList,restServices("mdOpsRole"),string+"Serivice");
			
			String sQuery="select DISTINCT(USER_NAME) from ipde.users where user_name not like '%Pool%' and user_key in(select distinct(user_key) from ipde.user_roles where role_key in (select role_key from ipde.role_lkp where role_desc = 'MD OPS'))";
			
			ArrayList<String> omdOpsRole=DBUtils.db_GetAllColumnValues(sQuery, "USER_NAME");
			
			compareTwoLists(oUIList,omdOpsRole,string+"DB+UI");
		
			break;
		case "Review CPM Assignments":

		case "Review Obsolete Payers":
			
			compareTwoLists(oUIList,restServices("clientOpsRole"),string+"Serivice");
		
			String sQuery1="select DISTINCT(USER_NAME) from ipde.users where user_name not like '%Pool%' and user_key in(select distinct(user_key) from ipde.user_roles where role_key in (select role_key from ipde.role_lkp where role_desc = 'CLIENT OPS'))";
			
			ArrayList<String> sClientOpsList=DBUtils.db_GetAllColumnValues(sQuery1, "USER_NAME");
			
			compareTwoLists(oUIList,sClientOpsList,string+"DB+UI");
				
			break;
		case "Review UI Assignments":

			compareTwoLists(oUIList,restServices("adminOpsRole"),string+"Serivice");
					
            String sQuery2="select DISTINCT(USER_NAME) from ipde.users where user_name not like '%Pool%' and user_key in(select distinct(user_key) from ipde.user_roles where role_key in (select role_key from ipde.role_lkp where role_desc = 'ADMIN OPS'))";
            
			ArrayList<String> sAdminOpsRole=DBUtils.db_GetAllColumnValues(sQuery2, "USER_NAME");
			
			compareTwoLists(oUIList,sAdminOpsRole,string+"DB+UI");
			
			break;
		case "Review DC Assignments":
			
			compareTwoLists(oUIList,restServices("adminMdRole"),string+"Serivice");
			
            String sQuery3="select DISTINCT(USER_NAME) from ipde.users where user_name not like '%Pool%' and user_key in(select distinct(user_key) from ipde.user_roles where role_key in (select role_key from ipde.role_lkp where role_desc = 'ADMIN MD'))";
            
			ArrayList<String> sAdminMdRoleList=DBUtils.db_GetAllColumnValues(sQuery3, "USER_NAME");
			
			compareTwoLists(oUIList,sAdminMdRoleList,string+"DB+UI");
			
			break;

		default:
			break;
		}

		
	}

	private void compareTwoLists(ArrayList<String> sUIList,ArrayList<String> sServiceList,String sValue) {
		
		   
        Collections.sort(sUIList);
        Collections.sort(sServiceList);
         
      
        boolean isEqual = sUIList.equals(sServiceList);
        System.out.println(isEqual+sValue);
		
	}

	@Step
	public void the_user_had_navigated_to_an_already_created_Update_Instance() {
		
		oAdminPage.the_user_had_navigated_to_an_already_created_Update_Instance();
		
		
	}

	@Step
	public void the_user_had_updated_field_value_as(String arg1, String arg2) throws InterruptedException {
		
		oAdminPage.the_user_had_updated_field_value_as(arg1,arg2);
		
	}

	@Step
	public void the_user_clicks_on_button(String arg1) throws InterruptedException {
		oAdminPage.the_user_clicks_on_button(arg1);
		
	}

	@Step
	public void all_the_updates_on_should_be_saved_successfully_in_DB(String arg1, String arg2) throws Exception {
		oAdminPage.all_the_updates_on_should_be_saved_successfully_in_DB(arg1,arg2);
		
	}


	public ArrayList<String> restServices(String sRole) {
		
		Response sResposeBody = SerenityRest.given().get("https://qapolicy.cotiviti.com/jbpm-builder/rs/iuservice/getAllUserRoles");

		Filter filter = Filter.filter(Criteria.where(sRole).is("true"));

		ArrayList<Object> svale = sResposeBody.jsonPath().get();

		ArrayList<String> oListUsers=new ArrayList();
		
		for (int i = 0; i < svale.size(); i++) {
			
			HashMap jsonOb = (HashMap) svale.get(i);
			
			Object sval = jsonOb.get(sRole);

			if (sval.equals(true)) {
				
				Object svadl = jsonOb.get("userName");

				//System.out.println(svadl.toString());
				
				oListUsers.add(svadl.toString());
			}
			
			
		}
		
		return oListUsers;
	}

	

	public void user_retrives_table_data_of_from_Same_Sim_file_and_Validate_with_DB(String sCodeType, String sInstance) {
		
		switch (sCodeType) {
		
		case "Deleted":
		
			validateAllCodeType("1520",sCodeType,"","2");
			
			break;
		case "Revised":
			
			validateAllCodeType("1540",sCodeType,"","3");
			
			break;
			
		case "New":
			
			validateAllCodeType("1540",sCodeType,"","1");
			
			break;

		default:
			
		}
		
		
	}

	private void validateAllCodeType(String sIntanceKey,String sCodeType,String sInstanceName,String sGroupKey) {

		ArrayList<String> ICDCodeListDB;
		ArrayList<String> descriptionDB;
		ArrayList<String> commentsDB;
		ArrayList<String> dvKeyDB;
		ArrayList<String> endDate;
		ArrayList<String> description;
		ArrayList<String> dvKey;
		ArrayList<String> comments;
		ArrayList<String> id;
		ArrayList<String> terminatedDate = null;
		
		String sQuery = "select DV_KEY, ICD_CODE,DESCRIPTION,NEW_END_DATE,COMMENTS from IRDM.ICD_UPDATES where update_instance_key ='"+sIntanceKey+"' and update_group_key ='"+sGroupKey+"'";

		System.out.println("Query"+sQuery);
		
		ICDCodeListDB = DBUtils.db_GetAllColumnValues(sQuery, "ICD_CODE");

		descriptionDB = DBUtils.db_GetAllColumnValues(sQuery, "DESCRIPTION");

		commentsDB = DBUtils.db_GetAllColumnValues(sQuery, "COMMENTS");

		dvKeyDB = DBUtils.db_GetAllColumnValues(sQuery, "DV_KEY");

		endDate = DBUtils.db_GetAllColumnValues(sQuery, "NEW_END_DATE");
			
		String sServiceUrl="https://qapolicy.cotiviti.com/jbpm-suite/sameSimAnalysis/getIcd10"+sCodeType+"CodeCrossReference?instanceKey="+sIntanceKey+"";
				
		System.out.println("ServiceUrl"+sServiceUrl);
		
		description = restServices("description", sServiceUrl);

		dvKey = restServices("dvKey", sServiceUrl);

		comments = restServices("comments", sServiceUrl);

		id = restServices("id", sServiceUrl);
		
		//String terminatedDate=null;
		
		if (sCodeType.equalsIgnoreCase("Revised") || sCodeType.equalsIgnoreCase("New")) {
		
		}else {
			
			terminatedDate = restServices("terminatedDate", sServiceUrl);
		}

		for (int i = 0; i < dvKey.size(); i++) {
		
			boolean bln;
			
			if (sCodeType.equalsIgnoreCase("Revised") || sCodeType.equalsIgnoreCase("New") ) {
				
			}else {
				
				String str= GenericUtils.ConvertEpochtoDate(terminatedDate.get(i));
				
				bln=str.equalsIgnoreCase(endDate.get(i));
				
				System.out.println("Date"+bln);
				
			}

			
			bln=id.get(i).equalsIgnoreCase(ICDCodeListDB.get(i));
			
			System.out.println("id"+bln);
			
			bln=description.get(i).equalsIgnoreCase(descriptionDB.get(i));
			
			System.out.println("description"+bln);
			
			bln=comments.get(i).equalsIgnoreCase(commentsDB.get(i));
			
			System.out.println("comments"+bln);
			
			bln= dvKey.get(i).equalsIgnoreCase(dvKeyDB.get(i));
			
			System.out.println("dvkey"+bln);
			
		}
		
		String sSummary="select count(update_instance_key) from IRDM.ICD_UPDATES where update_instance_key ='"+sIntanceKey+"' and update_group_key ='"+sGroupKey+"'";
		
		System.out.println("sSummary"+sSummary);
		
		String sDBCount=DBUtils.executeSQLQuery(sSummary);
		
		String sSummaryService="https://qapolicy.cotiviti.com/jbpm-suite/sameSimAnalysis/getCodeSummary?instanceKey="+sIntanceKey+"&codeGroup="+sCodeType+"";
		
		ArrayList<String> slist=restServices("totalCount",sSummaryService);
		
		System.out.println("slist"+slist.get(0));
		
		slist.get(0).equalsIgnoreCase(sDBCount);

	}

	public ArrayList<String> restServices(String sRole, String sValue) {

		Response sResposeBody = SerenityRest.given().get(sValue);

		Filter filter = Filter.filter(Criteria.where(sRole).is("true"));

		ArrayList<Object> svale = sResposeBody.jsonPath().get();

		ArrayList<String> oListUsers = new ArrayList();

		for (int i = 0; i < svale.size(); i++) {

			HashMap jsonOb = (HashMap) svale.get(i);

			Object sval = jsonOb.get(sRole);

			oListUsers.add(sval.toString());

		}

		return oListUsers;
	}

	@Step
	public void user_can_able_to_see_the_followingTabs(String sTabs) throws InterruptedException {
		
		Assert.assertTrue("Expot Excel button is in disabled mode", oSeleniumUtils.is_WebElement_Enabled(oAdminPage.ExportExcel));
	
		HashMap<String, String> olist=new HashMap<String, String>();
		
		olist.put("Revised", "DV Key;REV Code;New Code Description;Old Code Description;MD Comments;Load For Review");
		
		olist.put("Deleted", "DV Key;DEL Code;Description;Term Date;Comments;Load For Review");
		
		olist.put("New", "DV Key;NEW Code;Description;Effective Date;SIMILAR Codes;Comments;Load For Review");
		
		olist.put("Similar", "DV Key;SIM Code;Description;NEW Code(s);NEW Descriptions");
		
		HashMap<String, String> oSummary=new HashMap<String, String>();
		
		oSummary.put("Revised", "3");
		
		oSummary.put("Deleted", "1");
		
		oSummary.put("New", "2");
		
		List<String> oList=Arrays.asList(sTabs.split(";"));
		
		for (int i = 0; i < oList.size(); i++) {
			
			SeleniumUtils.defaultWait(3000);
			
			oGenericUtils.gfn_Click_On_Object("Span", oList.get(i));
			
			if (oList.get(i).equalsIgnoreCase("Similar")) {

			} else {

				oGenericUtils.gfn_Verify_String_Object_Exist("(//mat-panel-title[text()= ' Summary '])["+oSummary.get(oList.get(i))+"]");
				
			}

			List<String> oColumnHeaders=Arrays.asList(olist.get(oList.get(i)).split(";"));
			
			for (int j = 0; j < oColumnHeaders.size(); j++) {
				
				oGenericUtils.gfn_Verify_Object_Exist("span",oColumnHeaders.get(j));
			}
			
		}
	}
	
	    
	@Step
    public void loadSimDataInDB(String arg1) throws InterruptedException{
                    
     /*if (Serenity.sessionVariableCalled("NewInstanceName")!= null){
                Serenity.setSessionVariable("NewInstanceName").to(arg1);
           }*/
		
          String sSubRuleKey = DBUtils.insertSQLQuery(oCommonPage
                    .getDynamicQuery("DATALOADQUERY1", arg1, Serenity.sessionVariableCalled("NewInstanceName")).trim());
          SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
          
          /*oGenericUtils.gfn_Click_String_object_Xpath(("//a[@class='ui-menuitem-link ng-star-inserted']//span[text()='Interpretive Update Instances']"));
          SeleniumUtils.defaultWait(ProjectVariables.MIN_THREAD_WAIT);
          oGenericUtils.gfn_Click_On_Object("button", " Refresh");
          SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);
          oGenericUtils.gfn_Click_On_Object("a",Serenity.sessionVariableCalled("NewInstanceName"));*/
                    
                    
    }

	public void admin_MDs_user_which_we_assigned_should_be_displayed() throws InterruptedException {
		
		oAdminPage.admin_MDs_user_which_we_assigned_should_be_displayed();
		
	}
	
	@Step
    public void newInstanceCreationErrorMesseges(String sErrMsg, String xPath, String sItem) throws InterruptedException{
          
   oGenericUtils.gfn_Click_On_Object("button", " Add");
   oGenericUtils.gfn_Click_On_Object("button", " Save");
   
    Assert.assertTrue("Validate the Error message when Instance Name is empty",oSeleniumUtils.is_WebElement_Present(oCommonPage.GetDynamicXPathWithString("INSTANCE ERRORS", "Instance Name :", "*Instance Name is a required Field")));
   Assert.assertTrue("Validate the Error message when Description is empty",oSeleniumUtils.is_WebElement_Present(oCommonPage.GetDynamicXPathWithString("INSTANCE ERRORS", "Description :", "*Description is a required Field")));
   Assert.assertTrue("Validate the Error message when Admin OPS is empty",oSeleniumUtils.is_WebElement_Present(oCommonPage.GetDynamicXPathWithString("INSTANCE ERRORS", "Admin OPS :", " *Admin Ops is a required field")));
   Assert.assertTrue("Validate the Error message when Effective Name is empty",oSeleniumUtils.is_WebElement_Present("//td//label[text()='Effective Date :']/parent::td/parent::tr//td[4]//mat-error[text()='*Effective Date is a required Field']"));
   Assert.assertTrue("Validate the Error message when Update Type Name is empty",oSeleniumUtils.is_WebElement_Present(oCommonPage.GetDynamicXPathWithString("INSTANCE ERRORS", "Update Type :", "*Update Type is a required Field")));
   Assert.assertTrue("Validate the Error message when Library PRM Name is empty",oSeleniumUtils.is_WebElement_Present(oCommonPage.GetDynamicXPathWithString("INSTANCE ERRORS", "Library PRM :", "*Library PRM is a required Field")));
   Assert.assertTrue("Validate the Error message when Custom PRM is empty",oSeleniumUtils.is_WebElement_Present(oCommonPage.GetDynamicXPathWithString("INSTANCE ERRORS", "Custom PRM :", "*Custom PRM is a required Field")));
   Assert.assertTrue("Validate the Error message when Admin OPS is empty",oSeleniumUtils.is_WebElement_Present(oCommonPage.GetDynamicXPathWithString("INSTANCE ERRORS", "Admin PO :", " *Admin Ops is a required field")));
   
    
    //enter values
   oSeleniumUtils.Enter_given_Text_Element(StringUtils.replace(oAdminPage.NewInstanceCreate, "sValue", "Instance Name :"), "Random123");
          oSeleniumUtils.Enter_given_Text_Element(oAdminPage.DescriptionTxt, "Random123");
    oSeleniumUtils.Enter_given_Text_Element(StringUtils.replace(oAdminPage.NewInstanceCreate, "sValue", "Library PRM :"),ProjectVariables.LibCustomPRM);
    oSeleniumUtils.Enter_given_Text_Element(StringUtils.replace(oAdminPage.NewInstanceCreate, "sValue", "Custom PRM :"),ProjectVariables.LibCustomPRM);

          SeleniumUtils.defaultWait(3000);
          Calendar cal = Calendar.getInstance();
          int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
          String dayOfMonthStr = String.valueOf(dayOfMonth);
          System.out.println(dayOfMonthStr);
          
          oGenericUtils.gfn_Click_String_object_Xpath("//button[@class='mat-icon-button']//span");
          oGenericUtils.gfn_Click_On_Object("div", dayOfMonthStr);
          oSeleniumUtils.select_Given_Value_From_DropDown_Using_StringLocator(oAdminPage.UpdateType, "ICD");
          
          oGenericUtils.gfn_Click_On_Object("button", " Save");
          
          Assert.assertTrue("Validate the Error message when Admin MD is empty",oSeleniumUtils.is_WebElement_Present(oCommonPage.GetDynamicXPathWithString("INSTANCE ERRORS", "Admin PO :", " *Admin Ops is a required field")));
          Assert.assertTrue("Validate the Error message when Admin OPS is empty",oSeleniumUtils.is_WebElement_Present(oCommonPage.GetDynamicXPathWithString("INSTANCE ERRORS", "Admin OPS :", " *Admin Ops is a required field")));
          
          oGenericUtils.gfn_Click_String_object_Xpath(StringUtils.replace(oAdminPage.AdminDropdown, "sValue", "Admin PO :"));
          oGenericUtils.gfn_Click_On_Object("div","iht_ittest01");
          
          oGenericUtils.gfn_Click_On_Object("button", " Save");
          
          Assert.assertTrue("Validate the Error message when Admin OPS is empty",oSeleniumUtils.is_WebElement_Present(oCommonPage.GetDynamicXPathWithString("INSTANCE ERRORS", "Admin OPS :", " *Admin Ops is a required field")));
            
    }
	
	@Step
	public void validate_RequirePresentation(String sButton, String sPage) throws InterruptedException {
		
		oAdminPage.validate_RequirePresentation(sButton,sPage);
		
		
	}
	
	
	
		
		
		
		
		

		

	





}
