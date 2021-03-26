package project.features;


import java.text.DateFormat;
//import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import project.utilities.DBQueries;
import project.utilities.DBUtils;
import project.utilities.GenericUtils;
import project.utilities.ProjectVariables;
import project.utilities.SeleniumUtils;

public class Sample {
	
	
	
public static void  main(String args[]){
	
	for (int i = 1; i < 2; i++) {
		
		System.out.println("i value : " +i);

	}
	
//	String sDate = GenericUtils.getDate_given_Format() ;
//	String sDate = GenericUtils.Get_Required_Date_For_Given_String("2020-09-22 03:56:11.20915");
//	System.out.println(GenericUtils.getRequiredDateForGivenString("2020-09-22 03:56:11.20915"));
	
	
	
	
	
	
//	String arg2 = "1234.67";
//	String[] sRuleVersion = arg2.split(".");
	
	
//	String Text ="Minor Surgery:  10-Day Procedures ";
	
//	String Text ="Config";
//		
//	String Text2= "Config";
//	
//	
//	System.out.println(Text2.contains(Text));
//	
//	if (Text2.contains(Text)==true){
//		System.out.println("pass");
//	}else{
//		System.out.println("fail");
//	}
	
	
	
//String sText = "DOMT-BPKF7K-R";
//if(sText.length()<14){
//	System.out.println("pass");
//}else{
//	System.out.println("fail");
//}
//System.out.println(sText.length());

	
	
	
	
	
//	
//	List<String> sRuleVersion = Arrays.asList(arg2.trim().split("\\."));
//	System.out.println(sRuleVersion.size());
//	System.out.println(sRuleVersion.get(0));
//	System.out.println(sRuleVersion.get(1));
//	
//	DBQueries.sQuerygetRuleDetails("","","");
//	ArrayList<String> ArrDBValues = DBUtils.db_GetAllColumnValues(DBQueries.sQuerygetRuleDetails("","",""), "sub_rule_desc");
//	System.out.println("size::" +ArrDBValues.size());
//	for (int j = 0;j <ArrDBValues.size();j++ ){
//		
//		String sDBValue = ArrDBValues.get(j);
//	}
	
//	GenericUtils GenericUtils = null;
//	
//	String sDate = GenericUtils.getDate_given_Format() ;
//	
//	String sDate2= GenericUtils.dateCalculate(sDate, "MM/dd/yyyy", -7);
//		
//	System.out.println(sDate2)	;
	
	
//	public static void  main(String args[]){
//		
//		String sText = "Rule Response to Config";
//		String sSearch = "Response to Config";
//		
//		
//		System.out.println("vALUE " +(sText.indexOf(sSearch)));
//		
//		if(sText.trim().indexOf(sSearch.trim())>1){
//			System.out.println("Passed");
//		}
		
		
		
//		System.out.println( "try" +dateCalculate("01/20/2020", "MM/dd/yyyy", 2));
		
//		String sInstanceDate = DBUtils.executeSQLQuery(sInstanceDateQuery);
//		String sUpdatedDosToVal = GenericUtils.dateCalculate(sInstanceDate, "MM/dd/yyyy", 1);
//		
//		//updated dos To	
//		boolean bUpdatedDosToStatus = GenericUtils.getDate_Format(sDBCurrentDosToValue)
//				.equalsIgnoreCase(GenericUtils.Get_Required_Date_For_Given_String(sUpdatedDosToVal));	
//			verify("Current DOS From is dispalyed properly " + "DBValue:: " +sUpdatedDosToValue, bUpdatedDosToStatus);
		
		
	}
	
	
//	public static void dateConvert(String dt, int iDays){
////		 dt= "2020-11-11";
//		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar cal = Calendar.getInstance();
//		String d_temp = null;
//		Date dTemp;
//		try {
//			dTemp = formatter.parse(dt);
//			formatter = new SimpleDateFormat("MM/dd/yyyy");
//			d_temp = formatter.format(dTemp);
//			System.out.println("val : " + d_temp);
////			cal.add(Calendar.DATE, -1);
//			cal.add(formatter.parse(d_temp).getDate(), -2);
//			formatter.format(cal.getTime());
////			String dDateActual = dateCalculate("d_temp","MM/dd/yyyy",iDays);
//			System.out.println("D Actual date"+formatter.format(cal.getTime()));
//		} catch (ParseException ex) {
//
//		}
//	}
	
	public static String dateCalculate(String dateString, String dateFormat, int days) {
	    Calendar cal = Calendar.getInstance();
	    SimpleDateFormat s = new SimpleDateFormat(dateFormat);
	    try {
	        cal.setTime(s.parse(dateString));
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    System.out.println("C Date::" +Calendar.DATE);
	    cal.add(Calendar.DATE, -days);
	    return s.format(cal.getTime());
	}
	
public static String removeWhiteSpaces(String sText) {
		
		sText=sText.replaceAll("\\s", "");
		return sText;
			
		}
	
	

}
