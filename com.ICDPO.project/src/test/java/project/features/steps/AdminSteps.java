package project.features.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import project.feature.steps.definitions.AdminStepdef;

public class AdminSteps {
	//trinath..
	@Steps
	AdminStepdef  oAdminStepdef;
	
	@Given("^create instance with \"([^\"]*)\" and \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
	public void create_instance_with_and(String arg1, String arg2, String arg3,String arg4) throws Throwable {
		
		oAdminStepdef.createInstance(arg1, arg2, arg3,arg4);
	}
	
	@Then("^validate Admin rule review value \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\" \"([^\"]*)\"$")
	public void validateAdminrulereviewvalue(String sRole, String sRuleReviewID, String sColoumnName,String sExpectedvalue) throws Throwable {
		oAdminStepdef.validateAdminRuleReviewValue(sRole, sRuleReviewID, sColoumnName, sExpectedvalue);

	}
	
	@Then("^user Reassign Tasks \"([^\"]*)\" to \"([^\"]*)\"$")
	public void user_Reassign_Tasks_to(String sTasks, String sUsers) throws Throwable {
	  
		oAdminStepdef.user_Reassign_Tasks_to(sTasks,sUsers);
	}

	@Then("^The user had navigated to an already created Update Instance$")
	public void the_user_had_navigated_to_an_already_created_Update_Instance() throws Throwable {
	   
		oAdminStepdef.the_user_had_navigated_to_an_already_created_Update_Instance();
	}

	@Then("^The user had updated \"([^\"]*)\" field value as \"([^\"]*)\"$")
	public void the_user_had_updated_field_value_as(String arg1, String arg2) throws Throwable {
		oAdminStepdef.the_user_had_updated_field_value_as(arg1,arg2);
	}

	@Then("^The user clicks on \"([^\"]*)\" button$")
	public void the_user_clicks_on_button(String arg1) throws Throwable {
		oAdminStepdef.the_user_clicks_on_button(arg1);
	}

	@Then("^All the updates on \"([^\"]*)\" should be saved successfully in DB \"([^\"]*)\"$")
	public void all_the_updates_on_should_be_saved_successfully_in_DB(String arg1, String arg2) throws Throwable {
		oAdminStepdef.all_the_updates_on_should_be_saved_successfully_in_DB(arg1,arg2);
	}
	
	@Given("^user retrives table data of \"([^\"]*)\" from Same Sim file and Validate with DB for \"([^\"]*)\"$")
	public void user_retrives_table_data_of_from_Same_Sim_file_and_Validate_with_DB_for(String arg1, String arg2) throws Throwable {
		oAdminStepdef.user_retrives_table_data_of_from_Same_Sim_file_and_Validate_with_DB(arg1,arg2);
	}
	
	@Then("^user can able to see the following \"([^\"]*)\"$")
	public void user_can_able_to_see_the_following(String arg1) throws Throwable {
		oAdminStepdef.user_can_able_to_see_the_followingTabs(arg1);
	}


    @Then("^validate pending 'Proposal Generation'$")
    public void validate_pending_Proposal_Generation() throws Throwable {
       
        
    }
    
    @When("^user load Same Sim data through Database \"([^\"]*)\"$")
    public void user_load_Same_Sim_data_through_Database(String arg1) throws Throwable {
       
          oAdminStepdef.loadSimDataInDB(arg1);
    }

    @Then("^Admin MDs user which we assigned should be displayed$")
    public void admin_MDs_user_which_we_assigned_should_be_displayed() throws Throwable {
    	oAdminStepdef.admin_MDs_user_which_we_assigned_should_be_displayed();
    }

    @Then("^validate \"([^\"]*)\" in \"([^\"]*)\"$")
	public void validate(String arg1, String arg2) throws InterruptedException {
    	oAdminStepdef.validate_RequirePresentation(arg1,arg2);
	}

	

}
