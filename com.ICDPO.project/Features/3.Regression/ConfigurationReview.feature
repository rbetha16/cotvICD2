#Author: your.email@your.domain.comm
@Regression @Configuration
Feature: Configuration Review

  @PCA-5033 @PCA-5034 @R242Demo
  Scenario Outline: PCA-5034 ICD Interpretive Rule Update (Config): Return Rule – QA Response
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "All""No Action"
    And Modify Editorial comments with "No Editorial Changes" in "Only Final PO Review"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Retire Rule-a"
    Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete QA review with "<Changes>"
    Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Configuration Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user Return Rule from "Config" to "QA"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "Config" to "QA"
    When user click on Return Rule Response "Config"
    Then validate Rule Status with DataBase "Configuration Review - QA Response;Not Started;iht_ittest01"
    When user click on "Configuration Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "Config" to "QA"
    Then validate Rule Response comments to "Config" from ""

    Examples: 
      | FiterName        | FilterValue                             |
      | FilterTaskStatus | Library;Not Started;Final PO Review;REV |

  @PCA-5035 @R242Demo
  Scenario Outline: PCA-5035 ICD Interpretive Rule Update (Config) Return Rule Response – Config Response to QA
  									PCA-25228 PROD Allow Config user to generate Change Log with QA-Config Return Task
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "All""No Action"
    And Modify Editorial comments with "No Editorial Changes" in "Only Final PO Review"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Retire Rule-a"
    Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete QA review with "<Changes>"
    Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Configuration Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    And complete all Config Reviews
    Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user Return Rule from "QA" to "Config"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Configuration Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate "Generate Change Log" button
    Then validate comments section as Rule return from "QA" to "Config"
    When user click on Return Rule Response "QA"
    Then validate Rule Status with DataBase "QA Review - Configuration Response;Not Started;iht_ittest01"
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "QA" to "Config"
    Then validate Rule Response comments to "QA" from ""

    Examples: 
      | FiterName        | FilterValue                             |
      | FilterTaskStatus | Library;Not Started;Final PO Review;REV |

  @PCA-5036 @5032 @14596
  Scenario Outline: Ability to Complete Configuration & Generate Work Order
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "PROPOSALBASERULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "SIM""No Action"
    And Modify Editorial comments with "No Editorial Changes" in "Only Final PO Review"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Retire Rule-a"
    Then user navigate to Rule Review in AdminTab "CPMReassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user Complete CPM Decesion "Agree"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Do Not Retire Rule"
    Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete QA review with "<Changes>"
    Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest02" for "FINAL PO DEL"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Configuration Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    And complete all Config Reviews
    Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    And Complete Post Additional Configuration

    Examples: 
      | FilterValue                            |
      | Custom;Not Started;Final PO Review;SIM |

  @PCA-3545 @14741
  Scenario Outline: Ability to Cancel Retire Rules
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "All""No Action"
    And click on "Generate Summaries"
    And validate Retire Rule and Cancel Retire Rule

    Examples: 
      | FilterValue                            |
      | Custom;Not Started;Final PO Review;SIM |

  @PCA-5030
  Scenario Outline: PCA-5030 Ability to view Config Assignment in Group Taks
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "All""No Action"
    And Modify Editorial comments with "No Editorial Changes" in "Only Final PO Review"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Retire Rule-a"
    Then user navigate to Rule Review in AdminTab "CPMReassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user Complete CPM Decesion "Agree"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Do Not Retire Rule"
    Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete QA review with "<Changes>"
    Then Verify Assigned user and claim the rule

    Examples: 
      | FilterValue                            |
      | Custom;Not Started;Final PO Review;SIM |


  @PCA-3511 @PCA-14860
  Scenario Outline: Ability to 'Reset' the searched filter criteria - Workqueue
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task Rule Work Queue "<FilterName>" and "<FilterValue>"
    Then click on the "Filters-Reset" button

    Examples: 
      | FilterName                                      | FilterValue                                |
      | Proposal Types;Task Status;Tasks;Library Status | Similar;Not Started;Final PO Review;Library |

  @PCA-14861
  Scenario Outline: Ability to 'Reset' the searched filter criteria - Rule Review
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen
    Then validate Admin rule review value "Admin" "FINAL PO DEL" "Task" ""
    #And user navigate to IU "Rule Review" Instances Screen
    #And Apply filters in My Task Rule Work Queue "<FilterName>" and "<FilterValue>"
    #Then click on the "Filters-Reset" button

    Examples: 
      | FilterName                                      | FilterValue                                |
      | Proposal Types;Task Status;Tasks;Library Status | Similar;Not Started;Final PO Review;Custom |
      
      
      
      
      
