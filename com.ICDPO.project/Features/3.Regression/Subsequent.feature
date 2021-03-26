#Author: your.email@your.domain.com
@Subsequent @Regression
Feature: Subsequent

  @PCA-4471
  Scenario Outline: Ability to 'Reset' the searched filter criteria - Workqueue
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen
    #Then validate Admin rule review value "Admin" "TestAutoIV162" "Task" ""
    #And user navigate to IU "Rule Review" Instances Screen
    #And Apply filters in My Task Rule Work Queue "<FilterName>" and "<FilterValue>"

    Examples: 
      | FilterName                   | FilterValue                                  |
      | Task Status;Tasks;Impact Run | Not Started;Final PO Review;Subsequent Run 1 |

  @PCA-4471Sc01
  Scenario Outline: Ability to 'Reset' the searched filter criteria - Workqueue
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen
    #Then validate Admin rule review value "Admin" "TestAutoIV162" "Task" ""
    #And user navigate to IU "Rule Review" Instances Screen
    #And Apply filters in My Task Rule Work Queue "<FilterName>" and "<FilterValue>"

    Examples: 
      | FilterName                   | FilterValue                             |
      | Task Status;Tasks;Impact Run | Not Started;Final PO Review;Initial Run |

  @PCA-14722 @PCA-5510 @PCA-8597
  Scenario Outline: Creation Instance-->Intial Run-->Admin MD Scrub-->MD Review WorkQueue-No Change Required-->Subsequent Run-->Admin MD Scrub-->MD Review WorkQueu
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
    #And user navigate to IU "MyTasks" Instances Screen
    #When user click on "Review PO Assignments" with "<NewInstanceName>"
    #Then complete "Review PO Assignments" and assign "iht_ittest01" to perform ""
    And user navigate to IU "MyTasks" Instances Screen
    When user click on "Review Obsolete Payers" with "<NewInstanceName>"
    Then complete "Review Obsolete Payers" and assign "iht_ittest01" to perform ""
    And user switch to "Task Mangement" Page
    Then complete "Review UI Assignments" and assign "iht_ittest01" to perform "Cancel-Yes"
    Then complete "Review UI Assignments" and assign "iht_ittest01" to perform ""
    #Then complete "Review DC Assignments" and assign "Primary PO,Final PO" to perform "Cancel-Yes"
    Then complete "Review DC Assignments" and assign "Final PO" to perform "Cancel-Yes"
    Then complete "Review DC Assignments" and assign "iht_ittest01" to perform ""
    
    And user navigate to IU "ICD10-Admin" Instances Screen
    When user load Same Sim data through Database "G43.A1"
    Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
    Then click on "Initial Rule Impact Analysis" and validate "Impact Instance"
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
    And click on "Start Review"
    And set "No Change Required" in MD Review and click on ""
    And user navigate to IU "ICD10-Admin" Instances Screen
    When user load Same Sim data through Database "Q44.7"
    Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
    Then click on "Subsequent Rule Impact Analysis" and validate "Impact Instance"
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
