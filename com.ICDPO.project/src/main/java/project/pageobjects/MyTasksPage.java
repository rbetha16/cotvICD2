package project.pageobjects;

import java.util.ArrayList;

import org.junit.Assert;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.pages.PageObject;
import project.utilities.DBUtils;
import project.pageobjects.CommonPage;
import project.utilities.SeleniumUtils;

import project.utilities.ProjectVariables;


public class MyTasksPage extends PageObject {

	//trinath..
	
	CommonPage oCommonPage;
	SeleniumUtils oSeleniumUtils;
	DBUtils oDBUtils;
	
	public String TaskandInstance="//a[text()='sTask']/..//following::div[1]//span[text()='sInstance']/../..//preceding-sibling::div//child::a";
	
	public ArrayList<String> validateListofRulesInMDReview(String arg1){
		
		int iRulesCount = getDriver().findElements(By.xpath("//tr[@class='ui-selectable-row ng-star-inserted']//span[text()='iht_ittest01']/../..//td[5]//span[text()='Final PO Review']/../..//td[2]//a")).size();
		
//		String[] sRuleID= oSeleniumUtils.get_All_Text_from_Locator("//tr[@class='ui-selectable-row ng-star-inserted']//span[text()='iht_ittest01']/../..//td[5]//span[text()='Final MD Review']/../..//td[2]//a");
		String[] sRuleID= oSeleniumUtils.get_All_Text_from_Locator("//tr[@class='ui-selectable-row ng-star-inserted']//td[2]//a");
		
		ArrayList<String> oList=new ArrayList<String>();
		System.out.println("Rules Lenght:"  +sRuleID.length);
		
		for (int i = 0; i < sRuleID.length; i++) {
			System.out.print("Rule : " +sRuleID[i]);
			oList.add(sRuleID[i]);
		}
		
//		ArrayList<String> dbList = oDBUtils.db_GetAllColumnValues(oCommonPage.getDynamicQuery("RULESCOUNT",ProjectVariables.LfinalMDInstance,arg1),"MID_RULE_DOT_VERSION");
//		ArrayList<String> dbList = oDBUtils.db_GetAllColumnValues("SELECT  IRD.MID_RULE_DOT_VERSION FROM IRDM.INTERP_RULES IR JOIN IRDM.INTERP_RULE_DETAILS IRD ON IR.INTERP_RULE_KEY = IRD.INTERP_RULE_KEY JOIN IPDE.TASK_DETAILS TD ON TD.REFERENCE_RULE_ID = IR.INTERP_RULE_KEY JOIN IPDE.TASK_TYPE_LKP TTL ON TTL.TASK_TYPE_KEY = TD.TASK_TYPE_KEY JOIN IPDE.TASK_STATUS_LKP TSL ON TSL.TASK_STATUS_KEY = TD.TASK_STATUS_KEY JOIN IPDE.USERS U ON U.USER_KEY = TD.TASK_USER_KEY WHERE IR.IMPACT_KEY IN (SELECT IMPACT_KEY FROM IRDM.INTERP_IMPACTS II JOIN IRDM.UPDATE_INSTANCES I ON I.UPDATE_INSTANCE_KEY = II.UPDATE_INSTANCE_KEY WHERE UPDATE_INSTANCE_NAME =  'TestAutoIV576') AND TTL.TASK_TYPE_DESC =  'Final MD Review' AND IR.CANDIDATE_10 = '-1' AND U.USER_ID = 'iht_ittest01'","MID_RULE_DOT_VERSION");
		
		
		 // BuildMyString.com generated code. Please enjoy your string responsibly.

		String sb = "SELECT DISTINCT IRD.MID_RULE_DOT_VERSION\r\n" +
		"   FROM IRDM.INTERP_RULES IR\r\n" +
		"       JOIN IRDM.INTERP_RULE_DETAILS IRD\r\n" +
		"          ON IR.INTERP_RULE_KEY = IRD.INTERP_RULE_KEY\r\n" +
		"       JOIN IPDE.TASK_DETAILS TD ON TD.REFERENCE_RULE_ID = IR.INTERP_RULE_KEY\r\n" +
		"       JOIN IPDE.TASK_TYPE_LKP TTL ON TTL.TASK_TYPE_KEY = TD.TASK_TYPE_KEY\r\n" +
		"       JOIN IPDE.TASK_STATUS_LKP TSL\r\n" +
		"          ON TSL.TASK_STATUS_KEY = TD.TASK_STATUS_KEY\r\n" +
		"       JOIN IPDE.USERS U ON U.USER_KEY = TD.TASK_USER_KEY\r\n" +
		"       JOIN IRDM.INTERP_RULE_ICD IRI\r\n" +
		"         ON IRI.INTERP_RULE_KEY = IR.INTERP_RULE_KEY\r\n" +
		"       JOIN IRDM.INTERP_ICD_SOURCES IIS\r\n" +
		"         ON IIS.INTERP_RULE_ICD_KEY = IRI.INTERP_RULE_ICD_KEY\r\n" +
		"       JOIN IRDM.UPDATE_GROUP_LKP UGL\r\n" +
		"         ON UGL.UPDATE_GROUP_KEY = IRI.UPDATE_GROUP_KEY\r\n" +
		"       LEFT JOIN IRDM.SUB_RULES_REF_PRO SRRP\r\n" +
		"         ON IR.INTERP_RULE_KEY = SRRP.INTERP_RULE_KEY\r\n" +
		"       JOIN IPDE.EDITORIAL_STATUS_LKP ESL\r\n" +
		"         ON ESL.EDITORIAL_STATUS_KEY  = TD.EDITORIAL_STATUS_KEY\r\n" +
		"WHERE     IR.IMPACT_KEY IN (SELECT IMPACT_KEY\r\n" +
		"                               FROM IRDM.INTERP_IMPACTS II\r\n" +
		"                                    JOIN IRDM.UPDATE_INSTANCES I\r\n" +
		"                                       ON I.UPDATE_INSTANCE_KEY =\r\n" +
		"                                             II.UPDATE_INSTANCE_KEY\r\n" +
		"                              WHERE UPDATE_INSTANCE_NAME = '" + Serenity.sessionVariableCalled("IUInstanceName").toString() + "')\r\n" +
		"                              AND U.USER_NAME in ('iht_ittest01')\r\n" +
		"                              AND TSL.TASK_STATUS_DESC in ('Started','Not Started')\r\n" +
		"                              AND ((TTL.TASK_TYPE_DESC LIKE ('Editorial Review%')))";
		
		ArrayList<String> dbList = oDBUtils.db_GetAllColumnValues(sb,"MID_RULE_DOT_VERSION");

		System.out.println("Size: " +dbList.size());

		ArrayList<String> oDBList=new ArrayList<String>();
		ArrayList<String> oErrorList = new ArrayList<String>();

		for (int i = 0; i < dbList.size(); i++) {
			System.out.print("Rule : " +dbList.get(i));
			oDBList.addAll(dbList);
		}
		String sValue ="NoVal";
		
		
		
//		System.out.println("OListsize  " +oList.size());
		for (int i = 0; i < dbList.size(); i++) {
		
//			if (oList.contains(oDBList.get(i))){
					String sDBValue = dbList.get(i);
					String sAppValue = oList.get(i);
					if (sDBValue.contains(sAppValue)){
						sValue="Val";
					}
					
					if(sValue=="NoVal"){
						oErrorList.add(dbList.get(i));
					}
			}
		
		
		
		
				
//			}else{
//				System.out.println("failed : " +oDBList.get(i));
//				oErrorList.add(oDBList.get(i));
//				
//			}
		
		
		
				
		return oErrorList;
		
		
	}

}
