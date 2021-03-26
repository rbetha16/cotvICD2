import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
strict = true,
features = {"H:/git/com.ICD-IU241.project/Features/3.Regression/AdminScrub.feature:30"},
plugin = {"json:H:/git/com.ICD-IU241.project/target/cucumber-parallel/1.json"},
monochrome = true)

public class Parallel01IT {
}