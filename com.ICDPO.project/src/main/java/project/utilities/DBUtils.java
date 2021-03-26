package project.utilities;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public  class DBUtils {	
	//trinath
	 
	public static String executeSQLQuery(String sqlQuery)
	{
		//int rows = 0;
		String queryResultValue= "";
		
	
	try {
        		
        	Class.forName(ProjectVariables.DB_DRIVER_NAME);
        	
        	Connection con = DriverManager.getConnection(ProjectVariables.DB_CONNECTION_URL,ProjectVariables.DB_USERNAME,ProjectVariables.DB_PASSWORD);
        	
        	  if(con!=null) {
                  System.out.println("Connected to the Database...");
              }else {
                  System.out.println("Database connection failed ");
                 
              }
        	
        	  Statement st = con.createStatement();	        		
        	  ResultSet rs =st.executeQuery(sqlQuery);    
        	  
        	int i=  rs.getFetchSize();
        	System.out.println(i);
        
            while(rs.next())
            {
               queryResultValue = rs.getString(1).toString(); 
               
                             
            } 
            
            System.out.println("DB Result: "+queryResultValue);
            
            con.close();
            
        }catch (SQLException e) {
            e.printStackTrace();}
        
        catch (NullPointerException err) {
            System.out.println("No Records obtained for this specific query");
           // err.printStackTrace();       
            }        
		
	     catch (ClassNotFoundException e1) {     		  		
				e1.printStackTrace();	}
       
	 
	 return queryResultValue;
        
	}   
	
	
public static  ArrayList<String> executeSQLQueryMultipleRows(String sqlQuery)
	{
		//int rows = 0;
		//String queryResultValue= "";
		ArrayList<String> resultList = new ArrayList<String>();
	
	try {
        		
        	Class.forName(ProjectVariables.DB_DRIVER_NAME);
        	Connection con = DriverManager.getConnection(ProjectVariables.DB_CONNECTION_URL,ProjectVariables.DB_USERNAME,ProjectVariables.DB_PASSWORD);
        	
        	  if(con!=null) {
                  System.out.println("Connected to the Database...");
              }else {
                  System.out.println("Database connection failed ");
              }
        	
        	  
        	 String result;
        	  
        	  Statement st = con.createStatement();	        		
        	  ResultSet rs =st.executeQuery(sqlQuery);  
        	  
        	 
        	  while (rs.next()) {
        	          result = rs.getString(1).toString();
        	          resultList.add(result);
        	      }
        	
        	           
            //System.out.println("DB Result: "+queryResultValue);
            
            con.close();
            
        }catch (SQLException e) {
            e.printStackTrace();}
        
        catch (NullPointerException err) {
            System.out.println("No Records obtained for this specific query");
            err.printStackTrace();              }        
		
	     catch (ClassNotFoundException e1) {     		  		
				e1.printStackTrace();	}
       
	 
	  return resultList;
          
	}   
	
public static String GET_VALID_VALUE_IN_DB(String first,String second){
		
		String sQuery = "select ICD_CODE from MDM_STAGE.DX_MAP_STAGE where rownum < "+first+" minus select ICD_CODE from MDM_STAGE.DX_MAP_STAGE where rownum < "+second;
		 
		
		String sResult = executeSQLQuery(sQuery);
		System.out.println("sResult++++++++++++++++++++"+sResult);
		
		if(!(sResult.equals(" "))){
			System.out.println(" is  Successfully updated in the DataBase");
		}else{
			System.out.println("is not Successfully updated in the DataBase");
			
		}
		
		return sResult;
	}
	


	public static String db_GetFirstValueforColumn(String sQuery, String dbColumn) throws Exception {

		Connection conn = null;
		String sVal = "";

		try {

			Class.forName("oracle.jdbc.OracleDriver");

			conn = DriverManager.getConnection(ProjectVariables.DB_CONNECTION_URL, ProjectVariables.DB_USERNAME,
					ProjectVariables.DB_PASSWORD);

			Statement stmt = conn.createStatement();
			stmt.setQueryTimeout(10);
			ResultSet rs = stmt.executeQuery(sQuery);

			while (rs.next()) {
				sVal = rs.getString(dbColumn);
			}

			System.out.println("Stored Data in DB:" + " " + sVal);

			if (conn != null) {
				conn.close();
			}

		} catch (Exception e) {
			System.out.println("Exception " + e.getMessage());
		}

		finally {
			try {
				if (conn != null)
					conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return sVal;

	}
	
	public static String insertSQLQuery(String sqlQuery)
	{
		Connection con = null;
		String queryResultValue= "";
		
	
	try {
        		
        	Class.forName(ProjectVariables.DB_DRIVER_NAME);
        	 con = DriverManager.getConnection(ProjectVariables.DB_CONNECTION_URL,ProjectVariables.DB_USERNAME,ProjectVariables.DB_PASSWORD);
        	
        	  if(con!=null) {
                  System.out.println("Connected to the Database...");
              }else {
                  System.out.println("Database connection failed ");
              }
        	
        	  Statement st = con.createStatement();	        		
        	  //ResultSet rs =st.executeQuery(sqlQuery);    
        	  
        	  st.executeUpdate(sqlQuery);
        	  con.setAutoCommit(false);
              con.commit();
        	  System.out.println("inserted data successfully");

        }catch (SQLException e) {
            e.printStackTrace();}
        
        catch (NullPointerException err) {
            System.out.println("No Records obtained for this specific query");
            err.printStackTrace();              }        
		
	     catch (ClassNotFoundException e1) {     		  		
				e1.printStackTrace();	}
	
		finally {
			try {
				if (con != null)
					con.close();
	
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
       
	 
	 return queryResultValue;
        
	}   
	
	public static ArrayList<String> db_GetAllColumnValues(String sqlQuery, String sColumn) {
        
        Connection con=null;
        ArrayList<String> resultList = new ArrayList<String>();
        String result;

        try {

               Class.forName(ProjectVariables.DB_DRIVER_NAME);
               con = DriverManager.getConnection(ProjectVariables.DB_CONNECTION_URL,
                            ProjectVariables.DB_USERNAME, ProjectVariables.DB_PASSWORD);

               if (con != null) {
                     System.out.println("Connected to the Database...");
                     } else {
                     System.out.println("Database connection failed ");
               }

               Statement st = con.createStatement();
               st.setQueryTimeout(10);
               ResultSet rs = st.executeQuery(sqlQuery);
               ResultSetMetaData rsmd = rs.getMetaData();
               

               int iColCount = rsmd.getColumnCount();
               System.out.println("Column"+iColCount);
               
               while (rs.next()) {
                     try{
                            result = rs.getString(sColumn).toString();
                            System.out.println("Column data "+result);
                            resultList.add(result);          
                     }catch(NullPointerException err){
                            resultList.add("");
                     }
               }

               con.close();

        } catch (SQLException e) {
               e.getMessage();
        }

        catch (NullPointerException err) {
               System.out.println("No Records obtained for this specific query");
               err.getMessage();
        }

        catch (ClassNotFoundException e1) {
               e1.getMessage();
        }

        finally{
          try {
             if(con != null)
               con.close();
               
            }catch(SQLException e)  {           
               e.getMessage();         
           } 
        }
        
        return resultList;

 }

	
	public static void DBLockTest() throws Exception {
		
		String sHtml;
        String sb1 = null;
        
        String sb = "select SID,status,machine,LOGON_TIME,PREV_EXEC_START from v$session where machine like '%eap%%' and status ='SNIPED'";
        
        String sNoOfUsersCount = "select count(SID)from v$session where machine like '%eap%%' and status ='SNIPED'";
        
        String str=DBUtils.executeSQLQuery(sNoOfUsersCount);
        
        sb1 = "<!DOCTYPE html>\r\n" +
                     "<html>\r\n" +
                     "<head>\r\n" +
                     "<style>\r\n" +
                     "table, th, td {\r\n" +
                     "    border: 1px solid black;\r\n" +
                     "    border-collapse: collapse;\r\n" +
                     "}\r\n" +
                     "th, td {\r\n" +
                     "    padding: 10px;\r\n" +
                     "}\r\n" +
                     "th {\r\n" +
                     "    text-align: left;\r\n" +
                     "}\r\n" +
                     "</style>\r\n" +
                     "</head>\r\n" +
                     "<body>\r\n" +
                     "<h2>TOTAL SNIPED CONNECTIONS :"+str+"</h2>\r\n" +
                     "<p>Records found related to Sniped Connections</p>\r\n" +
                     "<table style=\"width:30%\">\r\n" +
                     "  <tr>\r\n" +
                     "    <th>SID</th> \r\n" + 
                     "    <th>STATUS</th>\r\n" +
                     "    <th>MACHINE</th>\r\n" +
                     "    <th>LOGON_TIME(EST)</th>\r\n" +
                     "     <th>PREV_EXEC_START(EST)</th>\r\n" +
                     "  </tr>\r\n";
        
      ArrayList<String> str1=DBUtils.db_GetAllColumnValues(sb, "SID");
        
        ArrayList<String> str2=DBUtils.db_GetAllColumnValues(sb, "status");
        
        ArrayList<String> str3=DBUtils.db_GetAllColumnValues(sb,"machine");
        
        ArrayList<String> str4=DBUtils.db_GetAllColumnValues(sb,"LOGON_TIME");
        
        ArrayList<String> str5=DBUtils.db_GetAllColumnValues(sb,"PREV_EXEC_START");
        
        
        if (str.equalsIgnoreCase("0")) {
        
              sHtml ="<!DOCTYPE html>\r\n" +
                                   "<html>\r\n" +
                                   "<head>\r\n" +
                                   "<style>\r\n" +
                                   "table, th, td {\r\n" +
                                   "    border: 1px solid black;\r\n" +
                                   "    border-collapse: collapse;\r\n" +
                                   "}\r\n" +
                                   "th, td {\r\n" +
                                   "    padding: 10px;\r\n" +
                                   "}\r\n" +
                                   "th {\r\n" +
                                   "    text-align: left;\r\n" +
                                   "}\r\n" +
                                   "</style>\r\n" +
                                   "</head>\r\n" +
                                   "<body>\r\n" +
                                   "<h2>SNIPED CONNECTIONS IN VPMTST1</h2>\r\n" +
                                   "<p>No Records found related to Sniped Connections</p>\r\n" +
                                   "<table style=\"width:30%\">\r\n" +
                                   "  <tr>\r\n" +
                                   "    <th>SID</th> \r\n" + 
                                   "    <th>STATUS</th>\r\n" +
                                   "    <th>MACHINE</th>\r\n" +
                                   "    <th>LOGON_TIME(EST)</th>\r\n" +
                                   "     <th>PREV_EXEC_START(EST)</th>\r\n" +
                                   "  </tr>\r\n"+"<tr>\r\n" +
                                   "    <th></th> \r\n" +    
                                   "    <th></th>\r\n" +
                                   "    <th></th>\r\n" +
                                   "    <th></th>\r\n" +
                                   "     <th></th>\r\n" +
                                   "  </tr>\r\n"+"</table>\r\n" +
                                                 "</body>\r\n" +
                                                 "</html>\r\n";      
//              System.out.println("No Records found++++++"+sHtml);
              
        }else {
              
              for (int i = 0; i <str1.size(); i++) {
                     
                     String strUpd="  <tr>\r\n" +
                                   "    <td>"+str1.get(i)+"</td>\r\n" +
                                   "    <td>"+str2.get(i)+"</td>\r\n" +
                                   "    <td>"+str3.get(i)+"</td>\r\n" +
                                   "    <td>"+str4.get(i)+"</td>\r\n" +
                                   "    <td>"+str5.get(i)+"</td>\r\n" +
                                   "  </tr>\r\n" ;
                     
              sb1=sb1.concat(strUpd);
        
              }
              
              sHtml=sb1+"</table>\r\n" +
                            "</body>\r\n" +
                            "</html>\r\n";             

//              System.out.println(sHtml);
              
        }
              
        SendSnipConEmail(sHtml);
        
        }
        
    
 // For Sending a mail with attachments and HTML Report
        public static void SendSnipConEmail(String sHtml) {

                                   // For Sending the Mail-----------
              try {

                     // Get system properties
                     Properties properties = System.getProperties();

                     // Setup mail server
                     properties.setProperty("mail.smtp.host", "ihtmail.ihealthtechnologies.com");
                     Session session = Session.getInstance(properties, null);
                     Message msg = new MimeMessage(session);
                     msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
                     msg.addHeader("format", "flowed");
                     msg.addHeader("Content-Transfer-Encoding", "8bit");
                     msg.setFrom(new InternetAddress("donotreply@cotiviti.com", "SNIPED CONNECTIONS"));
                     msg.setSubject("SNIPED CONNECTIONS");
                     Multipart multipart = new MimeMultipart();

                     // Creating the attachment part
                     BodyPart attachmentBodyPart = new MimeBodyPart();
                     String sDirectory = System.getProperty("user.dir");
                     String sDriverPath = sDirectory + "\\target\\site\\serenity";
                     File file = new File(sDriverPath);
                     File[] files = file.listFiles();

                     // Create the HTML Part
                     BodyPart htmlBodyPart = new MimeBodyPart();
                     StringBuilder sb = new StringBuilder(sHtml);
                     System.out.println(sb);
                     htmlBodyPart.setContent(sb.toString(), "text/html; charset=utf-8");
                     multipart.addBodyPart(htmlBodyPart);
                     msg.setContent(multipart);
                     msg.setSentDate(new Date());
                     String UserName = null;
                    // UserName = "Srinivasu.Gangam@cotiviti.com,I&VAutomationTeam@cotiviti.com,Chandrasekhar.Mallela@cotiviti.com,Venkat.Vadlamudi@cotiviti.com,Anuraag.Guptaa@cotiviti.com,Swetha.Podugu@cotiviti.com,TESLAInternal@cotiviti.com,RDASupport@cotiviti.com";
                     UserName ="raveendra.betha@cotiviti.com";
                     msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(UserName, false));

                     msg.setContent(multipart);

                     // To Send the mail
                     Transport.send(msg);

                     System.out.println("Mail sent successfully");

              } catch (Exception e) {

                     e.printStackTrace();

              }

        }

        

}