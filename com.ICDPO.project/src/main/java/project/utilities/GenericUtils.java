package project.utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.net.MalformedURLException;

import net.serenitybdd.core.Serenity;
import net.serenitybdd.core.pages.PageObject;

public class GenericUtils extends PageObject {
	
	SeleniumUtils oSeleniumUtils;

	public static String getDate_given_Format() {
		// String element = DA_PROJ_OR.LAST_SEARCH_TIME;
		String sExpectedTime = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
		String[] words = sExpectedTime.split("\\s");
		System.out.println("System Date-->:- " + words[0]);

		String sExpectedDate = words[0];

		return sExpectedDate;
	}

	public static int generate_Random_Number_for_Given_Range(int range) {
		Random rand = new Random();
		int value = rand.nextInt(range);
		return value;
		
	}

	/*
	 * The idea is that 1 + nextInt(2) shall always give 1 or 2. You then
	 * multiply it by 10000 to satisfy your requirement and then add a number
	 * between [0..9999].
	 */

	public static int GetRandomNumber() {
		Random r = new Random(System.currentTimeMillis());
		return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
	}

	public static String decode(String value) throws Exception {
		byte[] decodedValue = null;
		try {
			decodedValue = Base64.getDecoder().decode(value);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return new String(decodedValue, StandardCharsets.UTF_8);
	}

	/*public static void Verify(String sDescription, boolean blnStatus) {

		if (blnStatus) {
			Assert.assertTrue("Validation Successful for " + sDescription, blnStatus);
		} else {
			Assert.assertTrue("Validation not Successful for " + sDescription, blnStatus);
		}

	}*/

	public boolean gfn_Verify_Object_Exist(String sTagName, String sText) throws InterruptedException {

		boolean blnFlag = false;
		int iTimer = 0;
		String strXpath = "//" + sTagName + "[text()='" + sText + "']";
		
		System.out.println("Locator   "+strXpath);

		try {
			do {

				List<WebElement> sList = getDriver().findElements(By.xpath(strXpath));

				if (sList.size() > 0) {
					for (int i = 0; i < sList.size(); i++) {
						if (sList.get(i).isDisplayed()) {
							oSeleniumUtils.highlightElement(sList.get(i));
							blnFlag = true;
							break;
						}
					}
				}

				if (!blnFlag) {
					Thread.sleep(ProjectVariables.MID_TIME_OUT);
				}
				iTimer = iTimer + 1;

			} while ((blnFlag != true) && (iTimer != ProjectVariables.TimerCount));

			if (blnFlag != true) {
				System.out.println(sText + " object not found");
//				Assert.assertTrue(sText+" object not found", false);
				blnFlag = false;
			}

		} catch (Exception e) {
			System.err.println(e);
		}

		return blnFlag;

	}

	public boolean gfn_Click_On_Object(String sTagName, String sText) throws InterruptedException {

		boolean blnFlag = false;
		int iTimer = 0;
		String strXpath = "//" + sTagName + "[text()= '" + sText + "']";

		try {
			do {

				List<WebElement> sList = getDriver().findElements(By.xpath(strXpath));

				if (sList.size() > 0) {
					for (int i = 0; i < sList.size(); i++) {
						if (sList.get(i).isDisplayed() && sList.get(i).isEnabled()) {
							oSeleniumUtils.highlightElement(sList.get(i));
							sList.get(i).click();
							blnFlag = true;
							break;
						}
					}
				}

				if (!blnFlag) {
					Thread.sleep(ProjectVariables.MID_TIME_OUT);
				}
				iTimer = iTimer + 1;
			} while ((blnFlag != true) && (iTimer != ProjectVariables.TimerCount));

			if (blnFlag != true) {
				System.out.println(sText + " object not found");
				blnFlag = false;
//				Assert.assertTrue(sText+" object not found", blnFlag);
			}

		} catch (Exception e) {
			System.err.println(e);

		}

		return blnFlag;

	}

	public boolean gfn_Click_String_object_Xpath(String sXpath) {

		boolean blnFlag = false;
		int iTimer = 0;
		try {
			do {

				List<WebElement> sList = getDriver().findElements(By.xpath(sXpath));

				if (sList.size() > 0) {
					for (int i = 0; i < sList.size(); i++) {
						if (sList.get(i).isDisplayed()) {
							oSeleniumUtils.highlightElement(sList.get(i));
							sList.get(i).click();
							blnFlag = true;
							break;
						}
					}
				}

				if (!blnFlag) {
					Thread.sleep(ProjectVariables.MID_TIME_OUT);
				}
				iTimer = iTimer + 1;

			} while ((blnFlag != true) && (iTimer != ProjectVariables.TimerCount));

			if (blnFlag != true) {
				System.out.println("Object not clicked "+ sXpath);
//				Assert.assertTrue(sXpath + " object not found", false);
				blnFlag = false;
			}

		} catch (Exception e) {
			System.err.println(e);

		}

		return blnFlag;

	}

	public boolean gfn_Verify_String_Object_Exist(String strXpath) throws InterruptedException {

		boolean blnFlag = false;
		int iTimer = 0;
		// String strXpath = "//"+sTagName+"[text()='"+sText+"']";

		try {
			do {

				List<WebElement> sList = getDriver().findElements(By.xpath(strXpath));

				if (sList.size() > 0) {
					for (int i = 0; i < sList.size(); i++) {
						if (sList.get(i).isDisplayed()) {
							oSeleniumUtils.highlightElement(sList.get(i));
							blnFlag = true;
							break;
						}
					}
				}

				if (!blnFlag) {
					Thread.sleep(ProjectVariables.MID_SLEEP);
				}
				iTimer = iTimer + 1;

			} while ((blnFlag != true) && (iTimer != ProjectVariables.TimerCount));

			if (blnFlag != true) {
				System.out.println(strXpath + " object not found");
				blnFlag = false;
//				Verify(strXpath+" object not found", false);
			}

		} catch (Exception e) {
			System.err.println(e);

		}

		return blnFlag;

	}

	public boolean gfn_Verify_Object_Exist(String strXpath, int sTime) throws InterruptedException {

		boolean blnFlag = false;
		int iTimer = 0;

		try {
			do {

				List<WebElement> sList = getDriver().findElements(By.xpath(strXpath));

				if (sList.size() > 0) {
					for (int i = 0; i < sList.size(); i++) {
						if (sList.get(i).isDisplayed()) {
							oSeleniumUtils.highlightElement(sList.get(i));
							blnFlag = true;
							break;
						}
					}
				}

				if (!blnFlag) {
					Thread.sleep(ProjectVariables.MID_SLEEP);
				}
				iTimer = iTimer + 1;

			} while ((blnFlag != true) && (iTimer != sTime));

			if (blnFlag != true) {
				System.out.println(strXpath + " object not found");
				blnFlag = false;
			}

		} catch (Exception e) {
			System.err.println(e);
		}

		return blnFlag;

	}

	public static String Get_SystemDate_for_the_given_format(String format) {

		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		String TodaysDate = dateFormat.format(date);
		System.out.println("Today's Date:" + TodaysDate); // 2016/11/16

		return TodaysDate;

	}

	public String convertStringDate(String dateInString) {

		String date_Temp = null;
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy");

		try {

			Date date = formatter.parse(dateInString);
			date_Temp = (formatter.format(date));
			System.out.println(formatter.format(date));

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date_Temp.toString();
	}

	@SuppressWarnings("unused")
	public static void attachFiles() {

		String sDirectory = System.getProperty("user.dir");
		String sDriverPath = sDirectory + "\\src\\test\\resources\\TestFiles";
		String sExcelPath = sDirectory + "\\src\\test\\resources\\TestFiles\\BRATReuslts.xlsx";

		File sFile = new File(sExcelPath);
	
		System.out.println("File Path:- " + sFile.getAbsolutePath());
		StringSelection stringSelection = new StringSelection(sFile.getAbsolutePath());
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

		Robot robot;
		try {
			robot = new Robot();
			SeleniumUtils.defaultWait(1000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);

			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

	}
	
	public  void attachFiles(String arg1) {
	     int LastElementVal = getDriver().findElements(By.xpath("//input[@type ='file']")).size();
		System.out.println("Working Directory = " +  System.getProperty("user.dir"));
		
		String sDirectory = System.getProperty("user.dir");
		String sExcelPath = sDirectory + "//src//test//resources//TestFiles//"+arg1;
//		String sExcelPath = sDirectory + "\\src\\test\\resources\\TestFiles\\"+arg1;
		System.out.println("File Path" +sExcelPath);
				
	    String sXpath = "(//input[@type ='file'])["+LastElementVal+"]";
		WebElement uploadfield = getDriver().findElement(By.xpath(sXpath));
		upload(sExcelPath).to(uploadfield);

		SeleniumUtils.defaultWait(ProjectVariables.MID_SLEEP);

	}

	public static void attachDocument(String sFilePath) {

		File sFile = new File(sFilePath);
		System.out.println("File Path:- " + sFile.getAbsolutePath());
		StringSelection stringSelection = new StringSelection(sFile.getAbsolutePath());
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);

		Robot robot;
		try {
			robot = new Robot();
			SeleniumUtils.defaultWait(1000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);

			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);

			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

	}
	
	
	
	

	public static void gfn_Writedata_toPropertyfile(String sKey, String sValue, String sFile) throws IOException {

		File configFile = new File(sFile);
		FileInputStream in = new FileInputStream(configFile);
		Properties props = new Properties();
		props.load(in);
		in.close();
		FileOutputStream out = new FileOutputStream(configFile);
		props.setProperty(sKey, sValue);
		props.store(out, null);
		out.close();
	}

	// Set value in Edit Feild
	public boolean SET_VALUE(By sObjectType, String sInputVal) {
		boolean blnResult = false;
		int iTimer = 0;

		try {
			do {
				List<WebElement> sTxtElement = getDriver().findElements(sObjectType);
				// if size greater than zero
				if (sTxtElement.size() > 0) {

					getDriver().findElement(sObjectType).sendKeys(sInputVal);
					Thread.sleep(1000);
					blnResult = true;
					// oCPStepsDef.Validate("Value enterted
					// sucessfully:="+sInputVal,"PASSED");
					Validate("Value enterted sucessfully:=" + sInputVal, "PASSED");

				}

				iTimer = iTimer + 1;
				// Thread.sleep(1000);

			} while ((blnResult != true) && (iTimer != 60));

			if (blnResult != true) {
				Validate("Object not found:=" + sObjectType, "FAILED");
				getDriver().quit();
			}
		} catch (Exception e) {
			Validate("Object not clicked Successfully , Failed due to :=" + e.getMessage(), "FAILED");
		}
		return blnResult;
	}

	public void Validate(String StepDetails, String sStatus) {

		try {
			if (sStatus.equalsIgnoreCase("PASSED")) {
				System.out.println(StepDetails);
				Assert.assertTrue(sStatus, true);
			} else {
				System.out.println(StepDetails);
				Assert.assertTrue(sStatus, false);
			}
		} catch (Exception e) {
			Validate("Object not clicked Successfully , Failed due to :=" + e.getMessage(), "FAILED");
		}
	}

	public boolean CLICK_LINK_XPATH(String sLINK_XPATH) {
		boolean blnResult = false;
		int iTimer = 0;

		try {
			do {
				List<WebElement> sList = getDriver().findElements(By.xpath(sLINK_XPATH));
				if (sList.size() > 0) {

					// System.out.println("Link found");
					getDriver().findElement(By.xpath(sLINK_XPATH)).click();
					Validate("Link clicked sucessfully:=" + sLINK_XPATH, "PASSED");
					Thread.sleep(1000);
					blnResult = true;
				}
				iTimer = iTimer + 1;
			} while ((blnResult != true) && (iTimer != 60));
			// If flag false
			if (blnResult != true) {
				Validate("Object not found:=" + sLINK_XPATH, "FAILED");
				getDriver().quit();
			}

		} catch (Exception e) {
			Validate("Object not clicked Successfully , Failed due to :=" + e.getMessage(), "FAILED");
		}

		return blnResult;
	}

	public boolean CLICK_BUTTON(By sObjectType) {
		boolean blnResult = false;
		int iTimer = 0;
		try {
			List<WebElement> sList = getDriver().findElements(sObjectType);
			do {
				if (sList.size() > 0) {

					getDriver().findElement(sObjectType).click();
					blnResult = true;
					Thread.sleep(2000);
					Validate("Button clicked sucessfully:=" + sObjectType, "PASSED");
				}
				iTimer = iTimer + 1;
			} while ((blnResult != true) && (iTimer != 60));
			// If flag false
			if (blnResult != true) {
				Validate("Object not found:=" + sObjectType, "FAILED");
				getDriver().quit();
			}

		} catch (Exception e) {
			Validate("Object not clicked Successfully , Failed due to :=" + e.getMessage(), "FAILED");
		}

		return blnResult;
	}

	public static void clickOk() {

		Robot robot;
		try {
			robot = new Robot();
			SeleniumUtils.defaultWait(1000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);

		} catch (AWTException e) {
			e.printStackTrace();
		}
		SeleniumUtils.defaultWait(ProjectVariables.MID_TIME_OUT);

	}

	public static void savingResultsInFolder() {

		String sDirectory = System.getProperty("user.dir");

		LocalDateTime now = LocalDateTime.now();

		System.out.println("Before Formatting: " + now);

		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		String formatDateTime = now.format(format);

		String sModified = formatDateTime.replaceAll(":", "-");

		String ts = sModified.replaceAll("\\s", "IST");

		String sName = "H:\\DecRelease2018Day1" + "\\" + ts;

		File file = new File(sName);

		if (!file.exists()) {

			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}

			String sSourceDir = sDirectory + "\\target";

			File source = new File(sSourceDir);

			File dest = new File(sName);
			try {
				FileUtils.copyDirectory(source, dest);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String ConvertEpochtoDate(String sData) {

		Date date = new Date(Long.valueOf(sData));
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		format.setTimeZone(TimeZone.getTimeZone("GMT"));
		String formatted = format.format(date);
		// System.out.println(formatted);

		return formatted;
	}

	public static File getLatestFilefromDir(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}

	public static String ConvertEpochtoDate(String sData, String Requiredformat) {
		// TimeFormat Example==> dd/MM/yyyy h:mm:ss a
		Date date = new Date(sData);
		DateFormat format = new SimpleDateFormat(Requiredformat);
		// format.setTimeZone(TimeZone.getTimeZone("GMT"));
		String formatted = format.format(date);
		// System.out.println(formatted);

		return formatted;
	}

	public static void gfn_ReportEvent(String StepDetails, String sStatus) {

		switch (sStatus.toUpperCase()) {

		case "PASSED":
			// this.scenario =scenario;
			// scenario.write(StepDetails);
			System.out.println(StepDetails);
			Assert.assertTrue(StepDetails + sStatus, true);
			break;
		case "FAILED":
			System.out.println(StepDetails);
			Assert.assertTrue(StepDetails + sStatus, false);
			break;

		}
	}

	// *****************************************SWITCH_TO_BROWSER***********************************************************
	public static WebDriver gfn_Switch_Browser(WebDriver driver, int sWindowCount) throws InterruptedException {

		WebDriver oDriver = null;

		Set<String> NoofTabs = driver.getWindowHandles();
		for (String winHandle : NoofTabs) {
			if (sWindowCount > 1) {

				oDriver = driver.switchTo().window(winHandle);
				Thread.sleep(1000);
				// System.out.println(driver.getTitle());

			} else {
				oDriver = driver.switchTo().window(winHandle);
				Thread.sleep(1000);
				System.out.println(driver.getTitle());
				break;
			}
		}
		return oDriver;
	}

	// public static String postWSDLRequest(String sURL,String sXMLRequest)
	// throws IOException{
	// String sServiceReponse = null;
	//// sURL =
	// "http://domtst/IT/RMR/RMRV3WS.nsf/RMRWebServicesIUPD?OpenWebService";
	// URL obj = new URL(sURL);
	// HttpURLConnection huc=(HttpURLConnection) obj.openConnection();
	// huc.setRequestMethod("POST");
	// huc.setDoOutput(true);
	// OutputStream os = huc.getOutputStream();
	// os.write(sXMLRequest.getBytes());
	// os.flush();
	// os.close();
	//
	// // For POST only - END
	// int iResponseCode = huc.getResponseCode();
	// String sResponseMsg = huc.getResponseMessage();
	// System.out.println("POST Response Code :: " + iResponseCode);
	// System.out.println("Stub ud :: " + sResponseMsg);
	//
	// if (iResponseCode == HttpURLConnection.HTTP_OK) { //success
	// BufferedReader inputMsg = new BufferedReader(new
	// InputStreamReader(huc.getInputStream()));
	// String inputLine;
	// StringBuffer response = new StringBuffer();
	//
	// while ((inputLine = inputMsg.readLine()) != null) {
	// response.append(inputLine);
	// }
	// inputMsg.close();
	//
	// // print result
	// System.out.println(response.toString());
	// sServiceReponse = response.toString();
	// } else {
	// System.out.println("POST request not worked");
	// }
	//
	// return sServiceReponse;
	// }
	//

	public static String getTextFromTwoStrings(String sInputResponse, String sTextFrom, String sTextTo) {

		String result = "";
		result = sInputResponse.substring(sInputResponse.indexOf(sTextFrom) + sTextFrom.length(),
				sInputResponse.length());
		result = result.substring(0, result.indexOf(sTextTo));
		System.out.println("Result " + result);

		return result;

	}

	public static String sendPostRequest(String sURL, String sRequestedXML) throws IOException {
		String sResponse = "";
		URL obj = new URL(sURL);
		HttpURLConnection huc = (HttpURLConnection) obj.openConnection();

		huc.setRequestMethod("POST");
		huc.setDoOutput(true);

		OutputStream os = huc.getOutputStream();
		os.write(sRequestedXML.getBytes());
		os.flush();
		os.close();
		int responseCode = huc.getResponseCode();
		String sStubID = huc.getResponseMessage();
		System.out.println("POST Response Code :: " + responseCode);
		System.out.println("Stub ud :: " + sStubID);

		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(huc.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				System.out.println("inputline: " + inputLine);

				response.append(inputLine);
			}
			in.close();

			System.out.println(response.toString());
			sResponse = response.toString();

		} else {
			System.out.println("POST request not worked");
		}

		return sResponse;

	}
	
	public static String sendGetRequest(String sURL, String sRequestedXML) throws IOException {
		String sResponse = "";
		URL obj = new URL(sURL);
		HttpURLConnection huc = (HttpURLConnection) obj.openConnection();

		huc.setRequestMethod("GET");
		huc.setDoOutput(true);
		
//		OutputStream os = huc.getOutputStream();
//		os.write(sRequestedXML.getBytes());
//		os.flush();
//		os.close();
		int responseCode = huc.getResponseCode();
		String sStubID = huc.getResponseMessage();
		System.out.println("POST Response Code :: " + responseCode);
		System.out.println("Stub ud :: " + sStubID);
		
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(huc.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				System.out.println("inputline: " + inputLine);

				response.append(inputLine);
			}
			in.close();

			System.out.println(response.toString());
			sResponse = response.toString();

		} else {
			System.out.println("POST request not worked");
		}

		return sResponse;

	}

	public static String Get_Required_Date_For_Given_String(String sFormatType) {

		String[] sDate = sFormatType.split("/");

		for (int i = 0; i <= 2; i++) {
			String sCurrentDate = sDate[i].trim();

			switch (sCurrentDate) {

			case "1":
				sDate[i] = "01";
				break;
			case "2":
				sDate[i] = "02";
				break;
			case "3":
				sDate[i] = "03";
				break;
			case "4":
				sDate[i] = "04";
				break;
			case "5":
				sDate[i] = "05";
				break;
			case "6":
				sDate[i] = "06";
				break;
			case "7":
				sDate[i] = "07";
				break;
			case "8":
				sDate[i] = "08";
				break;
			case "9":
				sDate[i] = "09";
				break;
			}
		}
		System.out.println("UI Date:" + sDate[0] + "/" + sDate[1] + "/" + sDate[2]);
		return sDate[0] + "/" + sDate[1] + "/" + sDate[2];
	}

	public static String getDate_Format(String dt) {

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String d_temp = null;
		Date dTemp;
		try {
			dTemp = formatter.parse(dt);
			formatter = new SimpleDateFormat("MM/dd/yyyy");

			d_temp = formatter.format(dTemp);
			System.out.println("val : " + d_temp);
		} catch (ParseException ex) {

		}

		return d_temp.toString();

	}

	public static String dateCalculate(String dateString, String dateFormat, int days) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat s = new SimpleDateFormat(dateFormat);
		try {
			cal.setTime(s.parse(dateString));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("C Date::" + Calendar.DATE);
		cal.add(Calendar.DATE, -days);
		return s.format(cal.getTime());
	}

	// ################################################################################################################################################################
	
	public static void Verify(String StepDetails, String sStatus) {

		if (sStatus.equalsIgnoreCase("PASSED")) {
			System.out.println(StepDetails);
			Serenity.recordReportData().withTitle(StepDetails).andContents(sStatus);
			Assert.assertTrue(StepDetails, true);
		} else {
			Serenity.recordReportData().withTitle(StepDetails).andContents(sStatus);
			Serenity.takeScreenshot();
			System.out.println(StepDetails);
			Assert.assertTrue(StepDetails, false);

		}
		
	}

	// ################################################################################################################################################################

	public static void Verify(String StepDetails, boolean sStatus) {

		if (sStatus) {
			System.out.println(StepDetails);
			Serenity.recordReportData().withTitle(StepDetails).andContents("Passed");
			Assert.assertTrue(StepDetails, true);
		} else {
			System.out.println(StepDetails);
			Serenity.recordReportData().withTitle(StepDetails).andContents("Failed");
			Serenity.takeScreenshot();
			if (StepDetails.contains("is not")) {
				
				Assert.assertTrue(StepDetails.replace("is not", "is"), false);
				
			}else {
				
				Assert.assertTrue(StepDetails.replace("is", "is not"), false);
			}
			

		}
	}
	
	public static boolean stringContainsNumber( String s )
	{
	    return Pattern.compile( "[0-9]" ).matcher( s ).find();
	}
	
	public static String getRequiredDateForGivenString(String sFormatType) {

		String[] sDate = sFormatType.split("-");

		for (int i = 0; i <= 2; i++) {
			String sCurrentDate = sDate[i].trim();

			switch (sCurrentDate) {

			case "1":
				sDate[i] = "01";
				break;
			case "2":
				sDate[i] = "02";
				break;
			case "3":
				sDate[i] = "03";
				break;
			case "4":
				sDate[i] = "04";
				break;
			case "5":
				sDate[i] = "05";
				break;
			case "6":
				sDate[i] = "06";
				break;
			case "7":
				sDate[i] = "07";
				break;
			case "8":
				sDate[i] = "08";
				break;
			case "9":
				sDate[i] = "09";
				break;
			}
		}
//		System.out.println("UI Date:" + sDate[0] + "/" + sDate[1] + "/" + sDate[2]);
//		return sDate[0] + "/" + sDate[1] + "/" + sDate[2];
	
		String[] sDateTime = sDate[2].split("\\.");
		String[] sTime = sDateTime[0].split(" ");
		return sDate[1] + "/" + sTime[0] + "/" + sDate[0] + " " +sTime[1];
		
	}
	
	
	
	

	// ################################################################################################################################################################
}
