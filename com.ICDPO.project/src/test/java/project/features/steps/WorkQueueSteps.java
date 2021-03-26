
package project.features.steps;

import java.io.IOException;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

import project.feature.steps.definitions.WorkQueueStepDef;

//trinath..
public class WorkQueueSteps {
	// trinath
	@Steps
	WorkQueueStepDef oWorkQueueStepDef;

	@Then("^validate \"([^\"]*)\" for \"([^\"]*)\" and \"([^\"]*)\"$")
	public void validate_for_and(String arg1, String arg2, String arg3) throws Throwable {

		oWorkQueueStepDef.getAndValidateSystemProposals(arg1, arg2, arg3);
	}

	@When("^user enter Bulk Decision and Validate$")
	public void user_enter_Bulk_Decision_and_Validate() throws Throwable {

		oWorkQueueStepDef.enterMDBulkDecisionAndValidate();

	}

	@Then("^Validate the \"([^\"]*)\" with DB$")
	public void validate_the_with_DB(String arg1) throws Throwable {
		oWorkQueueStepDef.validate_Rule_Description_with_DB(arg1);
	}

	@Then("^Add another code from add code with \"([^\"]*)\"$")
	public void add_another_code_from_add_code_with(String arg1) throws Throwable {
		oWorkQueueStepDef.add_another_code_from_add_code_with(arg1);

	}

	@Then("^Copy Code from existing rule with \"([^\"]*)\"$")
	public void copy_Code_from_existing_rule_with(String arg1) throws Throwable {
		oWorkQueueStepDef.copy_Code_from_existing_rule_with(arg1);
	}


    @When("^should be viewing \"([^\"]*)\" textbox with \"([^\"]*)\"$")
    public void should_be_viewing_textbox_with(String arg1, String arg2) throws Throwable {
    	oWorkQueueStepDef.should_be_viewing_textbox_with_Description(arg1,arg2);
    }
    
    @When("^user update Rule Editorial through Services$")
    public void user_update_Rule_Editorial_through_Services() throws Throwable {
        
       oWorkQueueStepDef.ruleEditorialUpdateService();
    }


    @Then("^click on Start Review and Validate Potential Conflict$")
    public void click_on_Start_Review_and_Validate_Potential_Conflict() throws Throwable {
    	
    	oWorkQueueStepDef.clickStartRevieandValidatePotentialConflict();
        
    }

    @Then("^Click on \"([^\"]*)\" from Group Tasks$")
    public void click_on_from_Group_Tasks(String arg1) throws Throwable {
    	
    	oWorkQueueStepDef.clickFromGroupTasks(arg1);
        
    }

    @Then("^adds a code with \"([^\"]*)\" Category without Override flag$")
    public void adds_a_code_with_Category_without_Override_flag(String arg1) throws Throwable {
    	oWorkQueueStepDef.adds_a_code_with_Category_without_Override_flag(arg1);
    }
    
    @When("^click on \"([^\"]*)\" button on WorkQueue$")
    public void click_on_button_on_WorkQueue(String arg1) throws Throwable {
    	oWorkQueueStepDef.click_on_button_on_WorkQueue(arg1);
    }
    
    @When("^retrive \"([^\"]*)\" code from System Proposals$")
    public void retrive_code_from_System_Proposals(String sProposalType) throws Throwable {
    	oWorkQueueStepDef.retrive_code_from_System_Proposals(sProposalType);
    }
    
    @Then("^user should be able to enter the code that they want to remove in the pop up$")
    public void user_should_be_able_to_enter_the_code_that_they_want_to_remove_in_the_pop_up() throws Throwable {
    	oWorkQueueStepDef.user_should_be_able_to_enter_the_code_that_they_want_to_remove_in_the_pop_up();
    }
    
    @Then("^the ICD ARD link appears beside the Potential Conflict button when the rule is defined with an ARD\\.$")
    public void the_ICD_ARD_link_appears_beside_the_Potential_Conflict_button_when_the_rule_is_defined_with_an_ARD() throws Throwable {
    	oWorkQueueStepDef.the_ICD_ARD_link_appears_beside_the_Potential_Conflict_button_when_the_rule_is_defined_with_an_ARD();
    }

    @Then("^user can click on the link and review the ARD setup for the rule\\.$")
    public void user_can_click_on_the_link_and_review_the_ARD_setup_for_the_rule() throws Throwable {
    	oWorkQueueStepDef.user_can_click_on_the_link_and_review_the_ARD_setup_for_the_rule();
    }
    
	@When("^user Select and Claim the Rule from Editorial Pool$")
	public void user_Select_and_Claim_the_Rule_from_Editorial_Pool() throws Throwable {
		
		oWorkQueueStepDef.selectRuleAndClaimTask();
	    
	}
	
	@When("^user Complete Conflict Review \"([^\"]*)\"$")
	public void user_Complete_Conflict_Review(String arg1) throws Throwable {
	  
		oWorkQueueStepDef.completeConflictReview(arg1);
	
	}
	
	@Then("^validate Retire Rule Secion \"([^\"]*)\" and \"([^\"]*)\"$")
	public void validate_Retire_Rule_Secion_and(String arg1, String arg2) throws Throwable {
	   
		oWorkQueueStepDef.validateRetireRuleSection(arg1,arg2);
	  
	}


	@Then("^Verify the manual praposal grid$")
	public void verify_the_manual_praposal_grid() throws Throwable {
		oWorkQueueStepDef.verify_the_manual_praposal_grid();
	}

	@When("^user enter \"([^\"]*)\" comments$")
	public void user_enter_comments(String sReview) throws Throwable {

		oWorkQueueStepDef.user_enter_comments(sReview);
	}

	@Then("^verify comments entered in \"([^\"]*)\"$")
	public void verify_comments_entered_in(String arg1) throws Throwable {

		oWorkQueueStepDef.verify_comments_entered_in(arg1);
	}

	@When("^select System Proposal \"([^\"]*)\"\"([^\"]*)\"$")
	public void select_System_Proposal(String sProposalType, String sDecision) throws Throwable {

		oWorkQueueStepDef.selectSystemProposal(sProposalType, sDecision);
	}

	@When("^user complete the Authorization Decision with \"([^\"]*)\"$")
	public void user_complete_the_Authorization_Decision_with(String arg1) throws Throwable {

		oWorkQueueStepDef.completeAuthroizationInMDReview(arg1);

	}

	@Then("^validate Rationale Comments in \"([^\"]*)\"$")
	public void validate_Rationale_Comments_in(String sReviewType) throws Throwable {

		oWorkQueueStepDef.validateRationaleComments(sReviewType);

	}

	@Then("^validate \"([^\"]*)\" hyperlink under Actions column$")
	public void validate_hyperlink_under_Actions_column(String arg1) throws Throwable {

		oWorkQueueStepDef.validateActionsColumn(arg1);

	}

	@Then("^validate System Proposal \"([^\"]*)\"\"([^\"]*)\"$")
	public void validate_System_Proposal(String arg1, String arg2) throws Throwable {
		oWorkQueueStepDef.validateSysPropBasedBulkDecision(arg1, arg2);

	}

	@When("^should not viewing \"([^\"]*)\" button$")
	public void should_not_viewing_button(String arg1) throws Throwable {
		oWorkQueueStepDef.should_not_viewing_button(arg1);
	}

	@Given("^set \"([^\"]*)\" in MD Review and click on \"([^\"]*)\"$")
	public void set_no_change_required_in_MD_Review(String arg1, String sValue) throws Throwable {
		oWorkQueueStepDef.setNoChangeRequiredInMDReview(arg1, sValue);
	}

	@Then("^Valiadate the Range of code\"([^\"]*)\"$")
	public void valiadate_the_Range_of_code(String arg1) throws Throwable {
		oWorkQueueStepDef.ValidatetheCodeRange(arg1);
	}

	@When("^user update the bulk Decisions$")
	public void user_update_the_bulk_Decisions() throws Throwable {
		oWorkQueueStepDef.user_update_the_bulk_Decisions();
	}

	@Given("^click on \"([^\"]*)\" in WorkQueue$")
	public void click_on_in_WorkQueue(String arg1) throws Throwable {
		oWorkQueueStepDef.click_on_in_WorkQueue(arg1);
	}

	@When("^clicks on \"([^\"]*)\" action link$")
	public void clicks_on_action_link(String arg1) throws Throwable {
		oWorkQueueStepDef.clicks_on_action_link(arg1);
	}

	

	@Then("^Validate the Editorial details in\"([^\"]*)\"$")
	public void validate_the_Editorial_details_in(String arg1) throws Throwable {
		oWorkQueueStepDef.validate_the_Editorial_details_in(arg1);
	}

	@When("^Modify Editorial comments with \"([^\"]*)\" in \"([^\"]*)\"$")
	public void modify_Editorial_comments_with_in(String sAction, String sReviewType) throws Throwable {
		oWorkQueueStepDef.modify_Editorial_comments_with_in(sAction, sReviewType);
	}
	
	@Then("^Validate Descision Summary$")
	public void validate_Descision_Summary() throws Throwable {
		oWorkQueueStepDef.Vaslidate_Decision_Summary();
	}
	
	@Then("^validate Rule Status with DataBase \"([^\"]*)\"$")
	public void validate_Rule_Status_with_DataBase(String arg1) throws Throwable {
		oWorkQueueStepDef.validateDBTaskTypeAndStatus(arg1);
	}
	
	@When("^user Return Rule from \"([^\"]*)\" to \"([^\"]*)\"$")
	public void user_Return_Rule_from_to(String arg1, String arg2) throws Throwable {
		
		oWorkQueueStepDef.returnRule(arg1, arg2);
	   
	}
	
	@Then("^validate comments section as Rule return from \"([^\"]*)\" to \"([^\"]*)\"$")
	public void validate_comments_section_as_Rule_return_from_to(String arg1, String arg2) throws Throwable {
		oWorkQueueStepDef.validateRetrunReviewComments(arg1,arg2);
	}
	
	@When("^user click on Return Rule Response \"([^\"]*)\"$")
	public void user_click_on_Return_Rule_Response(String arg1) throws Throwable {
	   
		oWorkQueueStepDef.returnRuleResponse(arg1);
	}
	
	
	@When("^user Complete CPM Decesion \"([^\"]*)\"$")
	public void user_Complete_CPM_Decesion(String sAgreePayer) throws Throwable {
		oWorkQueueStepDef.completeCPMReview(sAgreePayer);
	}

	@Then("^Verify Assigned user and claim the rule$")
	public void verify_Assigned_user_and_claim_the_rule() throws Throwable {
		oWorkQueueStepDef.verify_Assigned_user_and_claim_the_rule();
	}
		
	@Then("^Complete Editorial review with \"([^\"]*)\"$")
	public void complete_Editorial_review_with(String arg1) throws Throwable {
		oWorkQueueStepDef.complete_Editorial_Review(arg1);
	}
	
	@Then("^Validate the full description of System generated proposals$")
	public void validate_the_full_description_of_System_generated_proposals() throws Throwable {
		
		oWorkQueueStepDef.verifySystemProposalDescription();

	}
	

	@When("^user \"([^\"]*)\" Rule using Lotus Services$")
	public void user_Rule_using_Lotus_Services(String arg1,String arg2) throws IOException {
	   
		oWorkQueueStepDef.ruleUpdateService(arg1,arg2);
	}

	@Then("^click on Start Review and Validate \"([^\"]*)\"$")
	public void click_on_Start_Review_and_Validate(String arg1) throws InterruptedException {
		oWorkQueueStepDef.clickStartReviewandValidate(arg1);	   
	}
	
	@When("^user select ByPayer in CPM Decision as \"([^\"]*)\" and validate MD Decision \"([^\"]*)\"$")
	public void user_select_ByPayer_in_CPM_Decision_as_and_validate_MD_Decision(String arg1, String arg2) throws InterruptedException {
	    
		oWorkQueueStepDef.selectByPayerInCPM(arg1,arg2);
	}
	
	@Then("^validate ConfigSummary and Warning$")
	public void validate_ConfigSummary_and_Warning() throws InterruptedException {
	   
		oWorkQueueStepDef.validateConfigSummaryAndWarning();
	}
	
	@Then("^validate Admin rule review value in AdminTab \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
	public void validate_Admin_rule_review_value_in_AdminTab(String arg1, String arg2, String arg3) throws InterruptedException {
		
		oWorkQueueStepDef.validateRuleReviewColumnValues(arg1,arg2,arg3);
	   
	}
	
	@Then("^complete the Review\"([^\"]*)\"$")
	public void complete_the_Review(String arg1) throws Throwable {
	    
		oWorkQueueStepDef.complete_the_Review(arg1);
	}
	
	@Then("^Validate the Rule Task \"([^\"]*)\"$")
	public void validate_the_Rule_Task(String arg1) throws Throwable {
	    
		oWorkQueueStepDef.validate_the_Rule_Task(arg1);
	}
	

	@Then("^validate Rule Response comments to \"([^\"]*)\" from \"([^\"]*)\"$")
	public void validate_Rule_Response_comments_to_from(String arg1, String arg2) {
   
		oWorkQueueStepDef.validateRuleResponseComments(arg1,arg2);
	}

	@Then("^should be displayed with Full description along with code under Sim Codes and Review codes$")
	public void should_be_displayed_with_Full_description_along_with_code_under_Sim_Codes_and_Review_codes() throws InterruptedException {
		oWorkQueueStepDef.should_be_displayed_with_Full_description_along_with_code_under_Sim_Codes_and_Review_codes();
	}
	
	@When("^verify rule Status \"([^\"]*)\" in DB for User \"([^\"]*)\"$")
	public void verify_rule_Status_in_DB_for_User(String arg1, String arg2) throws Exception {
		oWorkQueueStepDef.verify_rule_Status_in_DB_for_User(arg1,arg2);
	}

	
	@Then("^Complete QA review with \"([^\"]*)\"$")
	public void complete_QA_review_with(String arg1) throws Throwable{
	    // Write code here that turns the phrase above into concrete actions
		oWorkQueueStepDef.complete_QA_Review(arg1);
	}
	
	@Then("^Complete Testing review with \"([^\"]*)\"$")
	public void complete_Testing_review_with(String arg1) throws Throwable{
	    // Write code here that turns the phrase above into concrete actions
		oWorkQueueStepDef.complete_Testing_Review(arg1);

	}

	@When("^complete all Config Reviews$")
	public void complete_all_Config_Reviews() throws Throwable {
		
		oWorkQueueStepDef.completeConfigReview();
	   
	}

	@Then("^Complete Post Additional Configuration$")
	public void complete_Post_Additional_Configuration() throws Throwable {
		
		oWorkQueueStepDef.completePostAdditionalConfig();
	   
	}
	
	@When("^validate Retire Rule and Cancel Retire Rule$")
	public void validate_Retire_Rule_and_Cancel_Retire_Rule() throws InterruptedException {
		oWorkQueueStepDef.retireRuleAndCancelRetireRuleValidation();
	}
	
	

	@When("^user upload the BRAT test results and Validate$")
	public void user_upload_the_BRAT_test_results_and_Validate() throws InterruptedException {
	    
		oWorkQueueStepDef.uploadFileInTestingReview();
	}

	@Then("^user Delete the attachments and Validate$")
	public void user_Delete_the_attachments_and_Validate() throws InterruptedException {
	    // Write code here that turns the phrase above into concrete actions
		oWorkQueueStepDef.userDeleteAttachments();
	}
	
		
	@Then("^Validate the dropdown values with DB \"([^\"]*)\" for \"([^\"]*)\" with \"([^\"]*)\"$")
	public void validate_the_dropdown_values_with_DB_for_with(String arg1, String arg2, String arg3) throws InterruptedException {
	    // Write code here that turns the phrase above into concrete actions
		  oWorkQueueStepDef.validate_the_dropdown_values_with_DB(arg1,arg2, arg3);
	}
	
	@Then("^Validate the Rules count with DB \"([^\"]*)\" and Column name \"([^\"]*)\"$")
	public void validate_the_Rules_count_with_DB_and_Column_name(String arg1, String arg2) throws InterruptedException {
	    
		oWorkQueueStepDef.validateFilterRuleswithDB(arg1,arg2);
	}
	
	@Then("^Validate the dropdown values with DataBase \"([^\"]*)\" for \"([^\"]*)\" with \"([^\"]*)\"$")
	public void validate_the_dropdown_values_with_DataBase_for_with(String arg1, String arg2, String arg3) throws InterruptedException {
	   
		oWorkQueueStepDef.validatePayerFilterRulesCount(arg1,arg2,arg3);
	    
	}
	
	@When("^user create PRM ID through Service \"([^\"]*)\"$")
	public void user_create_PRM_ID_through_Service(String arg1) throws IOException {
	    
		oWorkQueueStepDef.createPRMID(arg1);
	    
	}
	
	@When("^user Update Rule \"([^\"]*)\",\"([^\"]*)\" using Lotus Services$")
	public void user_Update_Rule_using_Lotus_Services(String sUpdateType, String arg2) throws IOException {
		
		oWorkQueueStepDef.ruleUpdateService(sUpdateType, arg2);
	}
	
	@When("^user navigate to IU Report Screen \"([^\"]*)\"$")
	public void user_navigate_to_IU_Report_Screen(String arg1) {
	    
		oWorkQueueStepDef.selectReportTab( arg1);
	   
	}

	@When("^user select Report \"([^\"]*)\" RunType \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
	public void user_select_Report_RunType(String arg1, String arg2, String arg3, String arg4) throws InterruptedException {
	  
		oWorkQueueStepDef.selectReportCategeoryType(arg1, arg2,arg3,arg4);
	}

	@Then("^validate Search Rule \"([^\"]*)\" for \"([^\"]*)\"$")
	public void validate_Search_Rule_for(String arg1, String arg2) throws InterruptedException {
		
		oWorkQueueStepDef.validateSearchRule(arg1, arg2);
	    
	}
	
	@Given("^user select Report \"([^\"]*)\" RunType \"([^\"]*)\" \"([^\"]*)\"$")
	public void user_select_Report_RunType(String arg1, String arg2, String arg3) {
		oWorkQueueStepDef.selectReportCategeory(arg1,arg2,arg3);
	}
	
	
	
	 @Then("^user validate \"([^\"]*)\" and respective columns$")
     public void user_validate_and_respective_columns(String arg1) throws InterruptedException {
        
           oWorkQueueStepDef.validateSelectedReport(arg1);
         
     }
     
     @Then("^validate \"([^\"]*)\"$")
     public void validate_Reports(String arg1) throws Exception {
           
           oWorkQueueStepDef.validate_Reports(arg1);
         
     }
     
     @Then("^user validate Role \"([^\"]*)\" and  TaskType \"([^\"]*)\" and Status \"([^\"]*)\"$")
     public void user_validate_Role_and_TaskType_and_Status(String arg1, String arg2, String arg3) throws InterruptedException {
         
    	 oWorkQueueStepDef.validateWorkProgressReport(arg1,arg2,arg3);
         
     }
     
     @Then("^Validate Selected dropdown values with DB \"([^\"]*)\" for \"([^\"]*)\" with \"([^\"]*)\"$")
     public void validate_Selected_dropdown_values_with_DB_for_with(String sColumn, String sReview, String sSearch) throws InterruptedException {
    	 
    	 oWorkQueueStepDef.validateSelectedDropdownValuesUsingDB(sColumn, sReview, sSearch);
         
     }
     
     @Then("^validate \"([^\"]*)\" button$")
     public void validate_button(String arg1) {
    	 oWorkQueueStepDef.validateButton(arg1);
         
     }
     
     @Then("^validate Rule History Report \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$")
     public void validate_Rule_History_Report(String arg1, String arg2, String arg3, String arg4) {
         
    	 oWorkQueueStepDef.validateRuleHistoryReport(arg1,arg2,arg3,arg4);
     }
     
     @When("^user Click on CPM BulkDecision \"([^\"]*)\" Proposal Type \"([^\"]*)\" and Decision \"([^\"]*)\"$")
     public void user_Click_on_CPM_BulkDecision_Proposal_Type_and_Decision(String arg1, String arg2, String arg3) throws InterruptedException {
    	 
    	 oWorkQueueStepDef.enterCPMBulkDecisionAndValidate(arg1,arg2,arg3);
       
     }
    
     
     


	
}
