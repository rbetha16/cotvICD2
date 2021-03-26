#Author: trinath.kumar@cotiviti.com
@QASmoke
Feature: ICD-IU QA Smoke

  @QASmoke1
  Scenario Outline: QA BASIC SMOKE VALIDATION1
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen
    And create instance with "<NewInstanceName>" and "<Admin OPS>" "<Admin MD>" "<Operations>"
    #And user navigate to IU "ICD10-MyTasks" Instances Screen
    #When user click on "MD Review Work Queue" for "FINAL MD DEL" Instance

    Examples: 
      | NewInstanceName | Admin OPS    | Admin MD     | Operations |
      | Random          | iht_ittest01 | iht_ittest01 | Cancel-Yes |

@QASmoke2
  Scenario Outline: QA BASIC SMOKE VALIDATION2
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen

    Examples: 
      | NewInstanceName | Admin OPS    | Admin MD     | Operations |
      | Random          | iht_ittest01 | iht_ittest01 | Cancel-Yes |

@QASmoke3
  Scenario Outline: QA BASIC SMOKE VALIDATION3
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen
    
    Examples: 
      | NewInstanceName | Admin OPS    | Admin MD     | Operations |
      | Random          | iht_ittest01 | iht_ittest01 | Cancel-Yes |

  @CreateDELInstance
  Scenario Outline: CreateDELInstance and Release all Rules and validate MD Review WorkQueue
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
    When user click on "Review MD Assignments" with "<NewInstanceName>"
    Then complete "Review MD Assignments" and assign "iht_ittest01" to perform ""
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
    When user click on "Admin MD Scrub Review" for "Admin Scrub-Random" Instance
    And user switch to "Admin Scrub" Page
    When user selects the rule and click on the on the "Process Candidates-All Rules" button
    And user clicks on the "Release" button
    And user switch to "Task Mangement" Page
    When user click on "MD Review Work Queue" for "FINAL MD DEL-Random" Instance

    Examples: 
      | NewInstanceName | Admin OPS    | Admin MD     | GlobalTasks                                                         | Users        | Tasks                                       |
      | Random          | iht_ittest01 | iht_ittest01 | Review Obsolete Payers;Review MD Assignments;Review CPM Assignments | iht_ittest01 | Review UI Assignments;Review DC Assignments |
