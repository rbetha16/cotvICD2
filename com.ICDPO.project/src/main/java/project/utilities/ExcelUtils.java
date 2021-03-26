package project.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import net.serenitybdd.core.Serenity;

import org.apache.poi.ss.usermodel.Cell;

//trinath


public class ExcelUtils {

	@SuppressWarnings("resource")
	public static int SetCellDataXlsm(String sFeaturename , String sTcName, String sTcStatus, String sPath) throws Exception {

        int rowcount;
        int rowNum = 0;
        try {
               
               int iTcNum = 0;
               int iFeatureName = 1;
               int iTcName = 2;
               int iTcStatus = 3;
               int iTcTime = 4;                                                           
               String path = sPath;
               
              DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String sDate = dateFormat.format(date).toString();
        
        File src = new File(path);
               FileInputStream finput = new FileInputStream(src);
               XSSFWorkbook workbook = new XSSFWorkbook(finput);
               XSSFSheet sheet = workbook.getSheetAt(0);                   
               rowcount = sheet.getPhysicalNumberOfRows();
               
               int iRowCount = rowcount;
               rowNum = iRowCount+1;
               String sTcNo = "TC_"+(iRowCount-9);

               XSSFRow row = sheet.getRow(0);          
               row = sheet.getRow(rowNum - 1);
               if (row == null)
                     row = sheet.createRow(rowNum - 1);
               
               sheet.autoSizeColumn(iTcNum);
               XSSFCell sTcNumCell = row.getCell(iTcNum);
               if (sTcNumCell == null)
                     sTcNumCell = row.createCell(iTcNum);
               sTcNumCell.setCellValue(sTcNo);
               
               sheet.autoSizeColumn(iFeatureName);
               XSSFCell sFeatureCell = row.getCell(iFeatureName);
               if (sFeatureCell == null)
                     sFeatureCell = row.createCell(iFeatureName);
               sFeatureCell.setCellValue(sFeaturename);
               
               sheet.autoSizeColumn(iTcName);
               XSSFCell sTsNamecell = row.getCell(iTcName);
               if (sTsNamecell == null)
                     sTsNamecell = row.createCell(iTcName);
               sTsNamecell.setCellValue(sTcName);

               sheet.autoSizeColumn(iTcStatus);
               XSSFCell sTcStatusCell = row.getCell(iTcStatus);
               if (sTcStatusCell == null)
                     sTcStatusCell = row.createCell(iTcStatus);
               sTcStatusCell.setCellValue(sTcStatus);
        
               sheet.autoSizeColumn(iTcTime);
               XSSFCell sTcDateCell = row.getCell(iTcTime);
               if (sTcDateCell == null)
                     sTcDateCell = row.createCell(iTcTime);
               sTcDateCell.setCellValue(sDate);
               
               FileOutputStream fileOut = new FileOutputStream(path);
               workbook.write(fileOut);
               
               fileOut.close();
               
        } catch (Exception e) {
               e.printStackTrace();
        }
        
        return rowNum;
        
 }
 
//###############################################################################################################
 

	@SuppressWarnings("resource")
	public static boolean SetPath(int rowNum, int colName, String data, String sPath) {

        try {

               String path = sPath;
               FileInputStream fis = new FileInputStream(path);
               XSSFWorkbook workbook = new XSSFWorkbook(fis);
               if (rowNum <= 0)
                     return false;
               XSSFSheet sheet = workbook.getSheetAt(0);
               XSSFRow row = sheet.getRow(0);
               int colNum = colName;
               sheet.autoSizeColumn(colNum);
               row = sheet.getRow(rowNum - 1);
               if (row == null)
                     row = sheet.createRow(rowNum - 1);
               XSSFCell cell = row.getCell(colNum);
               if (cell == null)
                     cell = row.createCell(colNum);
               cell.setCellValue(data);
               FileOutputStream fileOut = new FileOutputStream(path);
               workbook.write(fileOut);
               fileOut.close();

        } catch (Exception e) {
               e.printStackTrace();
               return false;
        }
        return true;
 }

 	@SuppressWarnings("resource")
	public static int GetSheetCount() throws Exception {
		int sheetcount;
		try {
			File src = new File(ProjectVariables.sTestDataFilePath);
			FileInputStream finput = new FileInputStream(src);
			XSSFWorkbook workbook = new XSSFWorkbook(finput);
			sheetcount = workbook.getNumberOfSheets();
		} catch (Exception e) {
			throw (e);
		}

		System.out.println("sheet Count:=" + sheetcount);
		return sheetcount;

	}

	//###############################################################################################################
	
	@SuppressWarnings("resource")
	public static int GetRowCount(int sheet_No) throws Exception {

		int rowcount;
		try {

			// Open the Excel file
			File src = new File(ProjectVariables.sTestDataFilePath);
			FileInputStream finput = new FileInputStream(src);
			XSSFWorkbook workbook = new XSSFWorkbook(finput);

			XSSFSheet ExcelWSheet = workbook.getSheetAt(sheet_No);

			rowcount = ExcelWSheet.getLastRowNum();

		} catch (Exception e) {

			throw (e);

		}

		System.out.println("Row Count:=" + rowcount);
		return rowcount;
	}

	//###############################################################################################################
	
	@SuppressWarnings("resource")
	public static int GetColumnCount(int sheet_No) throws Exception {

		int columncount;
		try {

			// Open the Excel file

			File src = new File(ProjectVariables.sTestDataFilePath);
			FileInputStream finput = new FileInputStream(src);
			XSSFWorkbook workbook = new XSSFWorkbook(finput);

			XSSFSheet ExcelWSheet = workbook.getSheetAt(sheet_No);
			XSSFRow row = ExcelWSheet.getRow(0);
			columncount = row.getLastCellNum();

		} catch (Exception e) {

			throw (e);

		}

		System.out.println("Column Count:=" + columncount);
		return columncount;
	}

	//###############################################################################################################
	//////////////////////////////////////////////
	@SuppressWarnings({ "deprecation", "resource" })
	public static String getCellData(int RowNum, int ColNum, int sheet_No) throws Exception {
		String cellText = null;

		try {

			File src = new File(ProjectVariables.sTestDataFilePath);
			FileInputStream finput = new FileInputStream(src);
			XSSFWorkbook workbook = new XSSFWorkbook(finput);
			XSSFSheet sheet = workbook.getSheetAt(sheet_No);
			XSSFCell cell = sheet.getRow(RowNum).getCell(ColNum);

			if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				cellText = cell.getStringCellValue();
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC || cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
				cellText = String.valueOf(cell.getRawValue());
			} else if (cell.getCellType() == Cell.CELL_TYPE_BLANK)
				cellText = null;
			else
				cellText = null;
		}

		catch (Exception e) {
			// e.printStackTrace();
			System.out.println("row " + RowNum + " or column " + ColNum + " does not exist  in xlsx");
			cellText = null;
		}

		return cellText;
	}

	//###############################################################################################################
	// This method is to write in the Excel cell, Row num and Col num are the
	// parameters

	@SuppressWarnings("resource")
	public static boolean SetCellData(int rowNum, int colName, String data, int sheet_No) {

		try {

			String path = ProjectVariables.sTestDataFilePath;
			FileInputStream fis = new FileInputStream(path);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			if (rowNum <= 0)
				return false;
			XSSFSheet sheet = workbook.getSheetAt(sheet_No);
			XSSFRow row = sheet.getRow(0);
			int colNum = colName;
			sheet.autoSizeColumn(colNum);
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);
			XSSFCell cell = row.getCell(colNum);
			if (cell == null)
				cell = row.createCell(colNum);
			cell.setCellValue(data);
			FileOutputStream fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//###############################################################################################################
	
	
	public static int SetCellDataXlsm(String sFeaturename , String sTcName, String sTcStatus,String sReasonCode,String sReason,String sPath) throws Exception {

		int rowcount;
		int rowNum = 0;
		try {
			
			int iTcNum = 0;
			int iFeatureName = 1;
			int iTcName = 2;
			int iTcStatus = 3;
			int iTcTime = 4;
			int iTcReasonCode= 5;
			int iTcReason=6;
			String path = sPath;
			
		 	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	    	Date date = new Date();
	    	String sDate = dateFormat.format(date).toString();
	    	
	    	File src = new File(path);
			FileInputStream finput = new FileInputStream(src);
			XSSFWorkbook workbook = new XSSFWorkbook(finput);
			XSSFSheet sheet = workbook.getSheetAt(0);			
			rowcount = sheet.getPhysicalNumberOfRows();
			
			int iRowCount = rowcount;
			rowNum = iRowCount+1;
			String sTcNo = "TC_"+(iRowCount-9);

			XSSFRow row = sheet.getRow(0);		
			row = sheet.getRow(rowNum - 1);
			if (row == null)
				row = sheet.createRow(rowNum - 1);
			
			sheet.autoSizeColumn(iTcNum);
			XSSFCell sTcNumCell = row.getCell(iTcNum);
			if (sTcNumCell == null)
				sTcNumCell = row.createCell(iTcNum);
			sTcNumCell.setCellValue(sTcNo);
			
			sheet.autoSizeColumn(iFeatureName);
			XSSFCell sFeatureCell = row.getCell(iFeatureName);
			if (sFeatureCell == null)
				sFeatureCell = row.createCell(iFeatureName);
			sFeatureCell.setCellValue(sFeaturename);
			
			sheet.autoSizeColumn(iTcName);
			XSSFCell sTsNamecell = row.getCell(iTcName);
			if (sTsNamecell == null)
				sTsNamecell = row.createCell(iTcName);
			sTsNamecell.setCellValue(sTcName);

			sheet.autoSizeColumn(iTcStatus);
			XSSFCell sTcStatusCell = row.getCell(iTcStatus);
			if (sTcStatusCell == null)
				sTcStatusCell = row.createCell(iTcStatus);
			sTcStatusCell.setCellValue(sTcStatus);
		
			sheet.autoSizeColumn(iTcTime);
			XSSFCell sTcDateCell = row.getCell(iTcTime);
			if (sTcDateCell == null)
				sTcDateCell = row.createCell(iTcTime);
			sTcDateCell.setCellValue(sDate);
			
			sheet.autoSizeColumn(iTcReasonCode);
			XSSFCell sTcReasonCodeCell = row.getCell(iTcReasonCode);
			if (sTcReasonCodeCell == null)
				sTcReasonCodeCell = row.createCell(iTcReasonCode);
			sTcReasonCodeCell.setCellValue(sReasonCode);
			
			sheet.autoSizeColumn(iTcReason);
			XSSFCell sTcReasonCell = row.getCell(iTcReason);
			if (sTcReasonCell == null)
				sTcReasonCell = row.createCell(iTcReason);
			sTcReasonCell.setCellValue(sReason);
			
			FileOutputStream fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			
			fileOut.close();
			
		} catch (Exception e) {
			e.getMessage();
		}
		
		return rowNum;
		
	}
	
	public static  String  getSheetName(int sheetNO){
        
        String sName=null;
             
        File getLatestFile = GenericUtils.getLatestFilefromDir(ProjectVariables.sTestExcelFilePath);
        
        String fileName = getLatestFile.getName();
        
        String sNewFileName=ProjectVariables.sTestExcelFilePath+fileName;
        
        //String sSheetName=ExcelUtils.GetSheetName(1,sNewFileName);
        
        System.out.println("Sheet Name"+sNewFileName);
        
        Serenity.setSessionVariable("ExcelFileName").to(sNewFileName);
        

      FileInputStream fileInputStream = null;
      try {
          fileInputStream = new FileInputStream(sNewFileName);

          XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

          // for each sheet in the workbook
          //for (int i = 0; i < workbook.getNumberOfSheets(); i++) {

              //System.out.println("Sheet name: " + workbook.getSheetName(sheetNO));
              sName=workbook.getSheetName(sheetNO);
          //}

      } catch (IOException e) {
          e.printStackTrace();
      } finally {
          if (fileInputStream != null) {
              try {
                  fileInputStream.close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
        return sName;
  }




	

 
}
