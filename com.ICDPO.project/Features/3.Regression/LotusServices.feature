@LotusNotes
Feature: Lotus Notes Services

  @CreatePRMID
  Scenario: Create PRM ID through Service
    When user create PRM ID through Service "CREATEPRMID"
    
     @GetPRStatus
  Scenario: Get Status of PRM ID through Service
    When user create PRM ID through Service "GETPRSTATUS"

  @CreateLogicalRMRID
  Scenario: Create LogicalRMRID through Service
    When user Update Rule "LOGICALRMRIUPD","" using Lotus Services

  @DeactivateRule
  Scenario: Deactivate Rule Using Lotus Notes Service
    When user Update Rule "DEACTIVATERULE","" using Lotus Services

  #@CreateDELInstance
  #Scenario Outline: CreateDELInstance and Release all Rules and validate MD Review WorkQueuee
    #Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    #And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    #And user navigate to IU "ICD10-Admin" Instances Screen
    #And create instance with "<NewInstanceName>" and "<Admin OPS>" "<Admin MD>" "CREATEPRMID"
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
    #Then complete "Review UI Assignments" and assign "iht_ittest01" to perform ""
    #Then complete "Review DC Assignments" and assign "iht_ittest01" to perform ""
    #And user navigate to IU "ICD10-Admin" Instances Screen
    #When user load Same Sim data through Database "G43.A1"
    #Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
    #Then click on "Request Impact Analysis" and validate "Impact Instance"
    #And user navigate to IU "ICD10-MyTasks" Instances Screen
    #When user click on "Admin PO Scrub Review" for "Admin Scrub-Random" Instance
    #And user switch to "Admin Scrub" Page
    #When user selects the rule and click on the on the "Process Candidates-All Rules" button
    #And user clicks on the "Release" button
    #And user switch to "Task Mangement" Page
    #When user click on "PO Review Work Queue" for "FINAL PO DEL-Random" Instance
#
    #Examples: 
      #| NewInstanceName | Admin OPS    | Admin MD     | GlobalTasks                                                         | Users        | Tasks                                       |
      #| Random          | iht_ittest01 | iht_ittest01 | Review Obsolete Payers;Review PO Assignments;Review CPM Assignments | iht_ittest01 | Review UI Assignments;Review DC Assignments |

  @CreatePRMndInstance
  Scenario Outline: Create Custom PRM ID and Del Instance with Single Rule
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen
    And create instance with "<NewInstanceName>" and "<Admin OPS>" "<Admin MD>" "CREATEPRMID"
    And user navigate to IU "ICD10-Admin" Instances Screen
    Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
    And user navigate to IU "View All Tasks" Instances Screen
    And user Reassign Tasks "<GlobalTasks>" to "<Users>"
    And user switch to "JBPM Home" Page
    And user navigate to IU "MyTasks" Instances Screen
    When user click on "Review PO Assignments" with "<NewInstanceName>"
    Then complete "Review PO Assignments" and assign "iht_ittest01" to perform ""
    And user navigate to IU "MyTasks" Instances Screen
    When user click on "Review Obsolete Payers" with "<NewInstanceName>"
    Then complete "Review Obsolete Payers" and assign "iht_ittest01" to perform ""
    And user switch to "Task Mangement" Page
    Then complete "Review UI Assignments" and assign "iht_ittest01" to perform "Cancel-Yes"
    Then complete "Review UI Assignments" and assign "iht_ittest01" to perform ""
    Then complete "Review DC Assignments" and assign "Primary PO,Final PO" to perform "Cancel-Yes"
    Then complete "Review DC Assignments" and assign "iht_ittest01" to perform ""
    And user navigate to IU "ICD10-Admin" Instances Screen
    When user load Same Sim data through Database "G43.A1"
    Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
    Then click on "Request Impact Analysis" and validate "Impact Instance"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub-Random" Instance
    And user switch to "Admin Scrub" Page
    When user selects the rule and click on the on the "Process Candidates" button
    And user clicks on the "Retrieve Candidates" button
    Then user should be displayed with "Candidates rules"
    When user selects the rule and click on the on the "Process Non Candidates-RemainingRules" button
    And user enters the comments and click on the ok button
    And user clicks on the "Release" button
    And user switch to "Task Mangement" Page
    When user click on "PO Review Work Queue" for "FINAL PO DEL-Random" Instance
    And select "RuleID-Random"

    Examples: 
      | NewInstanceName | Admin OPS    | Admin MD     | GlobalTasks                                                         | Users        | Tasks                                       |
      | Random          | iht_ittest01 | iht_ittest01 | Review Obsolete Payers;Review PO Assignments;Review CPM Assignments | iht_ittest01 | Review UI Assignments;Review DC Assignments |
      
      
      @CreateWithManualPRM
  Scenario Outline: Create Instance Passing PRM From Jenkins
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen
    And create instance with "<NewInstanceName>" and "<Admin OPS>" "<Admin MD>" "Operations"
    And user navigate to IU "ICD10-Admin" Instances Screen
    Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
    And user navigate to IU "View All Tasks" Instances Screen
    And user Reassign Tasks "<GlobalTasks>" to "<Users>"
    And user switch to "JBPM Home" Page
    And user navigate to IU "MyTasks" Instances Screen
    When user click on "Review PO Assignments" with "<NewInstanceName>"
    Then complete "Review PO Assignments" and assign "iht_ittest01" to perform ""
    And user navigate to IU "MyTasks" Instances Screen
    When user click on "Review Obsolete Payers" with "<NewInstanceName>"
    Then complete "Review Obsolete Payers" and assign "iht_ittest01" to perform ""
    And user switch to "Task Mangement" Page
    Then complete "Review UI Assignments" and assign "iht_ittest01" to perform ""
    Then complete "Review DC Assignments" and assign "iht_ittest01" to perform ""
    And user navigate to IU "ICD10-Admin" Instances Screen
    When user load Same Sim data through Database "G43.A1"
    Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
    Then click on "Request Impact Analysis" and validate "Impact Instance"
    And user navigate to IU "ICD10-MyTasks" Instances Screen

    #When user click on "Admin PO Scrub Review" for "Admin Scrub-Random" Instance
    #And user switch to "Admin Scrub" Page
    #When user selects the rule and click on the on the "Process Candidates-All Rules" button
    #And user clicks on the "Release" button
    #And user switch to "Task Mangement" Page
    #When user click on "PO Review Work Queue" for "FINAL PO DEL-Random" Instance
    Examples: 
      | NewInstanceName | Admin OPS    | Admin MD     | GlobalTasks                                                         | Users        | Tasks                                       |
      | Random          | iht_ittest01 | iht_ittest01 | Review Obsolete Payers;Review PO Assignments;Review CPM Assignments | iht_ittest01 | Review UI Assignments;Review DC Assignments |
      
