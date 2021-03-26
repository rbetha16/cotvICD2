package project.features.steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import project.feature.steps.definitions.MyTasksStepdef;
//trinath..
public class MyTasksSteps {
	//trinath
	@Steps
	MyTasksStepdef oMyTasksStepdef;
	
	@When("^user click on \"([^\"]*)\" for \"([^\"]*)\" Instance$")
	public void userclickonforInstance(String sTaskName, String sInstanceName) throws Throwable {
		oMyTasksStepdef.clickTaskByInstanceName(sTaskName, sInstanceName);
	}
	
	@Then("^validate list of rules under MD Review Workqueue \"([^\"]*)\"$")
	public void validate_list_of_rules_under_MD_Review_Workqueue(String arg1) throws Throwable {
	    
		oMyTasksStepdef.validateListofRulesInMDReview(arg1);
	   
	}
	
	 @When("^user click \"([^\"]*)\" for \"([^\"]*)\" Instance$")
     public void user_click_for_Instance(String arg1, String arg2) throws InterruptedException {
        
		 oMyTasksStepdef.clickTaskOnCompactInstance(arg1,arg2);
     }

}
