##Author: trinath.kumar@cotiviti.com
@Regression @AdminScrub @Regression

Feature: AdminScrub

  @PCA-4416Demo
  Scenario: PCA 3501_ICD 10 : Ability to provide 'Filters'
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance
    Then validate sorting and filtering functionality

  @PCA-4424Demo @Failure
  Scenario: PCA 3503_ICD 10 : ICD Admin MD Scrub : Ability to select 'All Rules' or 'Uncurbed Rules'
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance

  #Then select all rows drag column header
  @AdminScrubTC3156 @PCA-4514Demo @Failure
  Scenario: PCA 3495_ICD 10 : Ability to 'Reassign' rules in "Admin MD Review" task from "Admin View"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance

  #Then validate Reassign functionality in Admin MD
  @AdminScrubTC3210
  Scenario: Admin MD_TC3210_Dashboard view of rule assignments on Admin MD
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance
    Then validate Admin MD Dashboard

  @AdminScrubTC3918
  Scenario: Admin MD_TC3918_Ability to retain filters on returning to Home Page
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance
    Then validate retain filters

  @PCA-4517 @Failure
  Scenario: Admin MD Scrub Ability to Reassign rules in Admin MD Review  task from Admin View
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance

  #Then validate Admin view buttons disabled
  @AdminScrubTC4353
  Scenario: Admin MD_TC4353_Disable Apply filter option when there no value to filter
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance
    Then verify column header and filter popup when AdminMD enters zero list values then Apply Filter button is Disabled

  @PCA-4339Demo @Failure
  Scenario Outline: Admin MD Scrub (UI) View the list of impacted rules
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance
    And the user should be able to view the following "<Columns>" and "<Buttons>"
    Then verify following Label, and data is within that column of "ReviewCPT, Group, Mapped CPT, From-To, CAT, Override"

    Examples: 
      | Columns                                                                         | Buttons                                                                                                                                                           |
      | Review;Comment;Version;MR;Task Type;Assignee;Library;ARD;Reference;IU Indicator | Export;Process Candidates;Process Non Candidates;Filters;Hide/Unhide;Retrieve Non Candidates;Retrieve Candidates;Reassign;Night View;Admin View;Release;Dashboard |

  @PCA-4357Demo
  Scenario: Ability to view the Admin Scrub screen in 'Night View'
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance
    And user selects "Night Mode button" and clicks on the button
    Then the system should change the view of this screen to "Night view"
    And user selects "Night Mode button" and clicks on the button
    Then the system should change the view of this screen to "Normal view"

  @PCA-4342 @PCA-4528 @PCA-4604Demo
  Scenario: Admin MD Scrub Retrieve Non Candidate rules
    PCA 3491 Ability to 'Process Non Candidates' for rule review
    PCA 3502 Ability to add 'Comments' to the rules

    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance
    When user selects the rule and click on the on the "Process Non Candidates" button
    And user enters the comments and click on the ok button
    And user clicks on the "Retrieve Non Candidates" button
    Then user should be displayed with "Non candidates rules"

  @PCA-4361Demo
  Scenario: Admin MD Scrub Ability to Retrieve 'Candidate' Rules
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance
    When user selects the rule and click on the on the "Process Candidates" button
    And user clicks on the "Retrieve Candidates" button
    Then user should be displayed with "Candidates rules"

  @PCA-4524Demo
  Scenario Outline: Admin MD Scrub Ability to view the screen in 'Admin View'
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance
    And user clicks on the "Admin View" button
    And the user should be able to view the following "<Columns>" and "<Buttons>"
    And should be viewing the "Total Items" count at bottom right corner of screen with "Total rules in review count"
    And should be viewing the "Selected Row(s)" count below the "Total Items" with "Total selected Record count"

    Examples: 
      | Columns                                                                                                                                                                                                                                                                                                                                                                                          |
      | Review, Comment, Reviewer, MR, Version, Task Type, Assignee, Library, ARD, Reference, IU Indicator, Medical Policy, Topic, Decision Point, Group, Review ICD, ICD, From-To, CAT, Override, ReviewICD-Group-Mapped ICD-From/To-CAT-Override-Sim Mappings-Sub Rule Description Resolved, Sub Rule Description Unresolved, Sub Rule Notes, Sub Rule script, Sub Rule Rationale, Payers, Claim Types |

  @PCA-4691 @Failure
  Scenario: Admin MD Scrub Ability to view the 'Dashboard'
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance
    When user selects the rule and click on the on the "Process Candidates" button
    And user clicks on the "Retrieve Candidates" button
    Then user should be displayed with "Candidates rules"
    And user clicks on the "All Rules Radio" button
    And user clicks on the "Dashboard" button
    Then should be displayed with the "Assignee;Medical Policy;Topic;Rule Count" in the drill down format
    And only “Processed candidate rules” should be considered
    And user clicks on the "Only Unscrubbed Rules Radio" button
    And user switch to "Admin MD Scrub" Page
    And user switch to "Task Mangement" Page
    When user Processed the some rules as non candidates /candidates
    Then the “Review” should be changed to “NO”/”Yes” for that rules

  @PCA-4707
  Scenario: Admin MD Scrub Ability to 'Export' list of rules
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance

  #Then click on "ExportExcel in AdminScrub" and validate "Global Assignments"
  @PCA-19915
  Scenario: PCA-19915 IU-ICD Ability to select 'Direct Release' only Candidate Rules
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance
    When user selects the rule and click on the on the "Process Non Candidates" button
    And user enters the comments and click on the ok button
    And user clicks on the "Retrieve Non Candidates" button
    Then validate Direct Release button "status" for "Process Non Candidates"
    When user selects the rule and click on the on the "Process Candidates" button
    And user clicks on the "Retrieve Candidates" button
    Then validate Direct Release button "status" for "Process Candidates"
    And user clicks on the "Only Unscrubbed Rules Radio" button
    Then validate Direct Release button "status" for "Unscrubbed Rules"
    Then validate Direct Release button "status" for "Admin View"
    Then validate Direct Release button "status" for "Candidates Non Candidates"

  @PCA-19917
  Scenario: PCA-19917 Ability to validate if the Directly Released rules have routed to respective users
  					PCA-19913 Ability to get an alert before releasing the rules directly
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance
    When user selects the rule and click on the on the "Process Candidates" button
    And user clicks on the "Retrieve Candidates" button
    Then validate Direct Release button "status" for "Direct Release"
    
    
    
