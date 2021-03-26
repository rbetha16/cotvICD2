
package project.features;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;


@RunWith(CucumberWithSerenity.class)




@CucumberOptions( plugin = {"pretty", "html:target/cucumber-html-report"},features="Features",tags = {"@PCA-21621"},monochrome = true)



public class TestRunner {
	
	@BeforeClass
    public static void killExcel() throws Exception{   
	  
    }

	
	@AfterClass
    public static void fn_SendMail() throws Exception{
		
     
       
    }
}

