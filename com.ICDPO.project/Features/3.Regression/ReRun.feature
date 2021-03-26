#Author: trinath.kumar@cotiviti.com
@ReRun @Regression
Feature: Re-Run Impact Analysis

  @PCA-345
  Scenario Outline: Creation Instance-->Intial Run-->Admin MD Scrub-->Re Run-->Admin MD Scrub-->MD Review WorkQueu
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
    Then complete "Review UI Assignments" and assign "iht_ittest01" to perform ""
    Then complete "Review DC Assignments" and assign "iht_ittest01" to perform ""
    And user navigate to IU "ICD10-Admin" Instances Screen
    When user load Same Sim data through Database "G43.A1"
    Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
    Then click on "Initial Rule Impact Analysis" and validate "Impact Instance"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub-Random" Instance
    And user switch to "Admin Scrub" Page
    Then retrive "All Rules" count in Admin MD Scrub
    And user switch to "Task Mangement" Page
    And user navigate to IU "ICD10-Admin" Instances Screen
    When user load Same Sim data through Database "Q41.9"
    Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
    Then click on "Re-Run Impact Analysis" and validate "Impact Instance"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub-Random" Instance
    And user switch to "Admin Scrub" Page
    Then retrived rule count in "Re-Run" should be greater than "Intial Run" in Admin MD Scrub

    Examples: 
      | NewInstanceName | Admin OPS    | Admin MD     | GlobalTasks                                                         | Users        | Tasks                                       |
      | Random          | iht_ittest01 | iht_ittest01 | Review Obsolete Payers;Review PO Assignments;Review CPM Assignments | iht_ittest01 | Review UI Assignments;Review DC Assignments |
