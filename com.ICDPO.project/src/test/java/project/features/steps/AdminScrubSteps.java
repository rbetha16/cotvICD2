package project.features.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import project.feature.steps.definitions.AdminscrubStepdef;

public class AdminScrubSteps {
	//trinath..
	@Steps
	AdminscrubStepdef oAdminscrubStepdef;
	
	@When("^should be viewing the \"([^\"]*)\" count at bottom right corner of screen with \"([^\"]*)\"$")
	   public void should_be_viewing_the_count_at_bottom_right_corner_of_screen_with(String arg1, String arg2) throws Throwable {
	       
		oAdminscrubStepdef.should_be_viewing_the_count_at_bottom_right_corner_of_screen_with(arg1,arg2);
	   }

	   @When("^should be viewing the \"([^\"]*)\" count below the \"([^\"]*)\" with \"([^\"]*)\"$")
	   public void should_be_viewing_the_count_below_the_with(String arg1, String arg2, String arg3) throws Throwable {
	       
		   oAdminscrubStepdef.should_be_viewing_the_count_below_the_with(arg1,arg2,arg3);
	   }
	   

		@Then("^should be displayed with the \"([^\"]*)\" in the drill down format$")
		public void should_be_displayed_with_the_in_the_drill_down_format(String arg1) throws Throwable {
			oAdminscrubStepdef.should_be_displayed_with_the_in_the_drill_down_format(arg1);
		}

		@Then("^only “Processed candidate rules” should be considered$")
		public void only_Processed_candidate_rules_should_be_considered() throws Throwable {
			oAdminscrubStepdef.only_Processed_candidate_rules_should_be_considered();
		}
		
		@When("^user Processed the some rules as non candidates /candidates$")
		public void user_Processed_the_some_rules_as_non_candidates_candidates() throws Throwable {
		    /*// Write code here that turns the phrase above into concrete actions
		    throw new PendingException();*/
		}

		@Then("^the “Review” should be changed to “NO”/”Yes” for that rules$")
		public void the_Review_should_be_changed_to_NO_Yes_for_that_rules() throws Throwable {
		    /*// Write code here that turns the phrase above into concrete actions
		    throw new PendingException();*/
		}
		
		@Then("^user should be displayed with \"([^\"]*)\"$")
		public void user_should_be_displayed_with(String arg1) throws Throwable {
			oAdminscrubStepdef.user_should_be_displayed_with(arg1);
		}
		
		@When("^user enters the comments and click on the ok button$")
		public void user_enters_the_comments_and_click_on_the_ok_button() throws Throwable {
			oAdminscrubStepdef.user_enters_the_comments_and_click_on_the_button();
		}

		@When("^user selects the rule and click on the on the \"([^\"]*)\" button$")
		public void user_selects_the_rule_and_click_on_the_on_the_button(String arg1) throws Throwable {
			oAdminscrubStepdef.user_selects_the_rule_in_AdminScrub(arg1);
		}
		
		@Given("^user selects \"([^\"]*)\" and clicks on the button$")
		public void user_selects_and_clicks_on_the_button(String arg1) throws Throwable {
		   
			oAdminscrubStepdef.user_selects_and_clicks_on_the_button(arg1);
		}

		@Then("^the system should change the view of this screen to \"([^\"]*)\"$")
		public void the_system_should_change_the_view_of_this_screen_to(String arg1) throws Throwable {
		   
			oAdminscrubStepdef.the_system_should_change_the_view_of_this_screen_to(arg1);
		}
		
		@When("^the user should be able to view the following \"([^\"]*)\" and \"([^\"]*)\"$")
		public void the_user_should_be_able_to_view_the_following_and(String sColumns, String sButton) throws Throwable {
			oAdminscrubStepdef.the_user_should_be_able_to_view_the_following_and(sColumns,sButton);
		}
		
		@When("^user clicks on the \"([^\"]*)\" button$")
		public void user_clicks_on_the_button(String arg1) throws Throwable {
			oAdminscrubStepdef.user_clicks_on_the_button(arg1);
		}
		
		@Then("^validate sorting and filtering functionality$")
		public void validate_sorting_and_filtering_functionality() throws Throwable {
			oAdminscrubStepdef.clickColumnAndValidateFilterFunctionality();
		}
		
		
		@Then("^select all rows drag column header$")
		public void select_all_rows_drag_column_header() throws Throwable {
			oAdminscrubStepdef.selectallRowsDragColumnHeader();
		}
		

		@When("^user click on \"([^\"]*)\" Option as \"([^\"]*)\"$")
	    public void user_click_on_Option_as(String arg1, String arg2) throws Throwable {
	          
			oAdminscrubStepdef.userClickOnOption(arg1,arg2);
	        
	    }
		
		@When("^assign with three different AdminMDs Users to the rules in Admin MD scrub$")
		public void assign_with_three_different_AdminMDs_Users_to_the_rules_in_Admin_MD_scrub() throws Throwable {
		   
			oAdminscrubStepdef.assign_with_three_different_AdminMDs_Users_to_the_rules_in_Admin_MD_scrub();
		}
		
		@Then("^retrive \"([^\"]*)\" count in Admin MD Scrub$")
		public void retrive_count_in_Admin_MD_Scrub(String arg1) {
		    
			oAdminscrubStepdef.retrive_count_in_Admin_MD_Scrub(arg1);
		}

		@Then("^retrived rule count in \"([^\"]*)\" should be greater than \"([^\"]*)\" in Admin MD Scrub$")
		public void retrived_rule_count_in_should_be_greater_than_in_Admin_MD_Scrub(String arg1, String arg2) {
			oAdminscrubStepdef.retrived_rule_count_in_should_be_greater_than_in_Admin_MD_Scrub(arg1,arg2);
		}
		
		

	     @Given("^user selects two rules as non candidates and remaining as process candidates$")
	     public void user_selects_two_rules_as_non_candidates_and_remaining_as_process_candidates() throws InterruptedException {
	           oAdminscrubStepdef.user_selects_two_rules_as_non_candidates_and_remaining_as_process_candidates();
	           }
	           
       @When("^filter with medical policies \"([^\"]*)\" and Reassign reviewer \"([^\"]*)\" capture rules count$")
       public void filter_with_medical_policies_and_Reassign_reviewer_capture_rules_count(String arg1,String sMedicalPolicy) throws InterruptedException {
       oAdminscrubStepdef.filter_with_medical_policies_and_Reassign_reviewer_capture_rules_count(arg1,sMedicalPolicy);
       } 
       
      
       
       @Then("^validate Direct Release button \"([^\"]*)\" for \"([^\"]*)\"$")
       public void validate_Direct_Release_button_for(String arg1, String arg2) throws InterruptedException {
    	   
    	   oAdminscrubStepdef.validateDirectReleaseBtnStatus(arg1,arg2);
    	   
       }


}
