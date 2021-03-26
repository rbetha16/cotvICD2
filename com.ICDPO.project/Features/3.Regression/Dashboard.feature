#Author: your.email@your.domain.comm
@Regression @Dashboard
Feature: IU Dashboard

  @StatusTracking1
  Scenario: Rule Search Report
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Reports" Instances Screen
    When user navigate to IU Report Screen "Rule Search Report"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "Final PO Review" for "Not Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "QA Review" for "Not Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "Final PO Review" for "Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "CPM Review" for "Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "CPM Review" for "Not Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "Editorial Review" for "Not Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "Testing Review" for "Not Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "Configuration Review" for "Not Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "CPM Review - Testing Return" for "Not Started"

  @StatusTracking2
  Scenario: Rule Search Report2
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Reports" Instances Screen
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "QA Review - CPM Response" for "Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "Final PO Review - Configuration Return" for "Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "QA Review - Config Return" for "Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "QA Review - Configuration Response" for "Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "Final PO Review - QA Return" for "Not Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "Testing Review - CPM Response" for "Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "QA Review - Editorial Response" for "Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "Editorial Review - CPM Response" for "Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "Configuration Review - Final PO Response" for "Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "Configuration Review - QA Response" for "Started"
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Rule Search Report" RunType "Intial Run" "Search Rule"
    Then validate Search Rule "Potential Conflicts Review" for "Not Started"

  @StatusTracking3
  Scenario: Rule Search Report3
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Reports" Instances Screen
    When user navigate to IU Report Screen "Status Tracker"
    And user select Report "Manual RMR Report" RunType "Intial Run" "Search Rule"
    Then user validate "Manual RMR Report" and respective columns
    Then validate Search Rule "ManualRMRReport" for "Manual RMR"

  #Preliminary PO Review
  #Preliminary PO Peer Review
  #Final PO Review
  #Final PO Peer Review
  #Editorial Review
  #QA Review
  #Testing Review
  #Configuration Review
  #CPM Review
  #CPM Review - QA Return
  #CPM Review - Editorial Return
  #Editorial Review - QA Return
  #Editorial Review - CPM Response
  #QA Review - Editorial Response
  #QA Review - Configuration Complete
  #QA Review - CPM Response
  #QA Review - Config Return
  #QA Review - Testing Return
  #Configuration Review - QA Response
  #Testing Review - QA Response
  #Configuration Review - QA Return
  #QA Review - Post Configuration
  #QA Review - Configuration Response
  #Potential Conflicts Review
  #Editorial Review - Testing Return
  #CPM Review - Testing Return
  #Final PO Review - Testing Return
  #Testing Review - Editorial Response
  #Testing Review - CPM Response
  #Testing Review - Final PO Response
  #Final PO Review - Configuration Return
  #Configuration Review - Final PO Response
  #Final PO Review After Preliminary
  #Final PO Review - Editorial Return
  #Final PO Review - QA Return
  #Editorial Review - Final PO Response
  #QA Review - Final PO Response
  @UsernameSearchReport
  Scenario: validating Username Search Report
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Reports" Instances Screen
    When user navigate to IU Report Screen " User Name Task Report"
    And user select Report "User Name Task Report" RunType "Initial Run" "iht_ittest01" ""
    Then validate "Username Search Report"

  #When user navigate to IU Report Screen " User Name Task Report"
  #And user select Report "User Name Task Report" RunType "Subsequent Run 1" "iht_ittest01" ""
  #Then validate "Username Search Report"
  @StatusTracking4
  Scenario: Rule Search Report4
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen

  #And create instance with "<NewInstanceName>" and "<Admin OPS>" "<Admin MD>" "Operations"
  #And user navigate to IU "ICD10-Admin" Instances Screen
  #Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
  #And user navigate to IU "View All Tasks" Instances Screen
  #And user Reassign Tasks "<GlobalTasks>" to "<Users>"
  #And user switch to "JBPM Home" Page
  #And user navigate to IU "MyTasks" Instances Screen
  #When user click on "Review PO Assignments" with "<NewInstanceName>"
  #Then complete "Review PO Assignments" and assign "iht_ittest01" to perform ""
  #And user navigate to IU "MyTasks" Instances Screen
  #When user click on "Review Obsolete Payers" with "<NewInstanceName>"
  #Then complete "Review Obsolete Payers" and assign "iht_ittest01" to perform ""
  #And user switch to "Task Mangement" Page
  #Then complete "Review UI Assignments" and assign "iht_ittest01" to perform "Cancel-Yes"
  #Then complete "Review UI Assignments" and assign "iht_ittest01" to perform ""
  #Then complete "Review DC Assignments" and assign "Primary PO,Final PO" to perform "Cancel-Yes"
  #Then complete "Review DC Assignments" and assign "iht_ittest01" to perform ""
  #And user navigate to IU "ICD10-Admin" Instances Screen
  #When user load Same Sim data through Database "G43.A1"
  #Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
  #Then click on "Request Impact Analysis" and validate "Impact Instance"
  #And user navigate to IU "ICD10-MyTasks" Instances Screen
  #When user click on "Admin PO Scrub Review" for "Admin Scrub-Random" Instance
  #When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance
  #And user switch to "Admin Scrub" Page
  #And user clicks on the "Admin View" button
  #And filter with medical policies "MP01" and Reassign reviewer "iht_ittest02" capture rules count
  #And filter with medical policies "MP02" and Reassign reviewer "iht_ittest03" capture rules count
  #And user selects two rules as non candidates and remaining as process candidates
  #And user clicks on the "Release" button
  #When user close Tabs "1"
  #Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
  #When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
  #And user navigate to IU "ICD10-MyTasks" Instances Screen
  #When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance
  #And user selects two rules as non candidates and remaining as process candidates
  #And user clicks on the "Release" button
  #And user switch to "Task Mangement" Page
  #And user navigate to IU "ICD10-ScrubReport" Instances Screen
  #When user navigate to IU Report Screen "Scrub Report"
  #And user select Report "Scrub Report" RunType "Initial Run" "Drug and Biological Policy" "iht_ittest02"
  #Then validate "Drug and Biological Policy"
  #When user close Tabs "2"
  #Given user logs into "QA" with "iht_ittest03" into Interpretive Update Application
  #When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
  #And user navigate to IU "ICD10-MyTasks" Instances Screen
  #When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance
  #And user selects two rules as non candidates and remaining as process candidates
  #And user clicks on the "Release" button`
  #And user navigate to IU "ICD10-ScrubReport" Instances Screen
  #When user navigate to IU Report Screen "Scrub Report"
  #And user select Report "Scrub Report" RunType "Initial Run" "Healthplan Policy" "iht_ittest03"
  #Then validate "Healthplan Policy"
  @StatusTracking5
  Scenario Outline: WorkProgress Report for CustomRule
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Reports" Instances Screen
    When user navigate to IU Report Screen "Status Tracker"
    Then user validate Role "CUSTOMRULE" and  TaskType "RULEREVIEW" and Status "Pending"
    And user switch to "Task Mangement" Page
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "All""No Action"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Retire Rule-a"
    And user switch to "Report Screen" Page
    Then user validate Role "CUSTOMRULE" and  TaskType "RULEREVIEW" and Status "Validate"
    Then user validate Role "CUSTOMRULE" and  TaskType "CPM" and Status "Pending"
    And user switch to "Task Mangement" Page
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user Complete CPM Decesion "Agree"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Do Not Retire Rule"
    And user switch to "Report Screen" Page
    Then user validate Role "CUSTOMRULE" and  TaskType "CPM" and Status "Validate"
    Then user validate Role "CUSTOMRULE" and  TaskType "Editorial Team" and Status "Pending"
    And user switch to "Task Mangement" Page
    Then Verify Assigned user and claim the rule
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Editorial Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete Editorial review with "<Changes>"
    And user switch to "Report Screen" Page
    Then user validate Role "CUSTOMRULE" and  TaskType "Editorial Team" and Status "Validate"

    Examples: 
      | FiterName        | FilterValue                            |
      | FilterTaskStatus | Custom;Not Started;Final PO Review;REV |

  @StatusTracking6
  Scenario Outline: WorkProgress Report for LibraryRule
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Reports" Instances Screen
    When user navigate to IU Report Screen "Status Tracker"
    Then user validate Role "Policy Owner" and  TaskType "RULEREVIEW" and Status "Pending"
    And user switch to "Task Mangement" Page
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And Modify Editorial comments with "Edit values" in "Only Final PO Review"
    And select System Proposal "All""No Action"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Retire Rule-a"
    And user switch to "Report Screen" Page
    Then user validate Role "Policy Owner" and  TaskType "RULEREVIEW" and Status "Validate"
    Then user validate Role "Policy Owner" and  TaskType "QA Team" and Status "Pending"
    And user switch to "Task Mangement" Page
    Then Verify Assigned user and claim the rule
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete QA review with "<Changes>"
    And user switch to "Report Screen" Page
    Then user validate Role "Policy Owner" and  TaskType "QA Team" and Status "Validate"
    Then user validate Role "Policy Owner" and  TaskType "Configuration Team" and Status "Pending"
    And user switch to "Task Mangement" Page
    Then Verify Assigned user and claim the rule
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Configuration Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    And complete all Config Reviews
    And user switch to "Report Screen" Page
    Then user validate Role "Policy Owner" and  TaskType "Configuration Team" and Status "Validate"
    Then user validate Role "Policy Owner" and  TaskType "QA Team" and Status "Pending"
    And user switch to "Task Mangement" Page
    Then Verify Assigned user and claim the rule
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    And Complete Post Additional Configuration
    And user switch to "Report Screen" Page
    Then user validate Role "Policy Owner" and  TaskType "QA Team" and Status "Validate"
    Then user validate Role "Policy Owner" and  TaskType "Testing  Team" and Status "Pending"
    And user switch to "Task Mangement" Page
    Then Verify Assigned user and claim the rule
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete Testing review with "<Changes>"
    Then user validate Role "Policy Owner" and  TaskType "Testing Team" and Status "Validate"

    Examples: 
      | FiterName        | FilterValue                             |
      | FilterTaskStatus | Library;Not Started;Final PO Review;REV |

  
  @PCA-25753 @PCA-25758 @PCA-25842 @PCA-25764 
   Scenario Outline: PCA-25753 Ability to select Rule History Report from left navigation pane
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
     And Modify Editorial comments with "No Editorial Changes" in "Only Final PO Review"
    And select System Proposal "All""No Action"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Retire Rule-a"
    Then user navigate to Rule Review in AdminTab "CPMReassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user close Tabs "CLOSETAB2"
    And user navigate to IU "ICD10-Reports" Instances Screen
    When user navigate to IU Report Screen " Rule History Report"
    Then validate Rule History Report "CPM Review","Not Started","Assigned to CPM",""
    And user switch to "Task Mangement" Page
    And user close Tabs "CLOSETABS"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user Complete CPM Decesion "Agree"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Do Not Retire Rule"
    Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user switch to "Report Screen" Page
    When user navigate to IU Report Screen " Rule History Report"
    Then validate Rule History Report "QA Review","Not Started","Assigned to QA",""
    And user switch to "Task Mangement" Page
    And user close Tabs "CLOSETABS"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete QA review with "<Changes>"
    Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user switch to "Report Screen" Page
    When user navigate to IU Report Screen " Rule History Report"
    Then validate Rule History Report "Configuration Review","Not Started","Assigned to Config",""

    Examples: 
      | FiterName        | FilterValue                             |
      | FilterTaskStatus | Custom;Not Started;Final PO Review;SIM |

  @PCA-25763
  Scenario Outline: PCA-25763 Rule History Report: Ability to generate report and view the report in the grid 
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "PROPOSALBASERULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "REV""Remove"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Do Not Retire Rule"
    Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user close Tabs "CLOSETAB2"
    And user navigate to IU "ICD10-Reports" Instances Screen
    When user navigate to IU Report Screen " Rule History Report"
    Then validate Rule History Report "Editorial Review","Not Started","Assigned to Editorial",""
    And user switch to "Task Mangement" Page
    And user close Tabs "CLOSETABS"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Editorial Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete Editorial review with "<Changes>"
    Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user switch to "Report Screen" Page
    When user navigate to IU Report Screen " Rule History Report"
    Then validate Rule History Report "QA Review","Not Started","Assigned to QA",""
    And user switch to "Task Mangement" Page
    And user close Tabs "CLOSETABS"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete QA review with "<Changes>"
    Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user switch to "Report Screen" Page
    When user navigate to IU Report Screen " Rule History Report"
    Then validate Rule History Report "Testing Review","Not Started","Assigned to Testing",""

    Examples: 
      | FiterName        | FilterValue                             |
      | FilterTaskStatus | Library;Not Started;Final PO Review;REV |
