#Author: your.email@your.domain.comm
@Regression @QAReview
Feature: QA Review

  @CompleteQAReview
  Scenario Outline: Complete the QA Review <Changes>
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "PROPOSALBASERULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And Add another code from add code with "ADDCODE"
    And select System Proposal "All""No Action"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Do Not Retire Rule"
    Then Verify Assigned user and claim the rule
    Given user logs into "QA" with "SERENITY" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Editorial Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete Editorial review with "<Changes>"
    Then Verify Assigned user and claim the rule
    Given user logs into "QA" with "SERENITY" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete QA review with "<Changes>"

    Examples: 
      | FiterName        | FilterValue                             | Changes                       |
      | FilterTaskStatus | Library;Not Started;Final PO Review;SIM | No Editorial Changes Required |

  @PCA-5005 @PCA-5024 @PCA-5025 @R240Demo
  Scenario Outline: PCA-5024 5005 5025 ICD Interpretive Rule Update (QAs) Ability to Return a Library Rule to MD/Editorial
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "All""No Action"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Retire Rule-a"
    Then Verify Assigned user and claim the rule
    Given user logs into "QA" with "SERENITY" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Editorial Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete Editorial review with "<Changes>"
    Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user Return Rule from "QA" to "PO"
    Then validate Rule Status with DataBase "Final PO Review - QA Return;Not Started;iht_ittest01"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "QA" to "PO"
    When user click on Return Rule Response "QA"
    Then validate Rule Status with DataBase "QA Review - Final PO Response;Not Started;iht_ittest01"
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "QA" to "PO"
    When user Return Rule from "QA" to "Editorial"
    Then validate Rule Status with DataBase "Editorial Review - QA Return;Not Started;iht_ittest01"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Editorial Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "QA" to "PO;Editorial"
    When user click on Return Rule Response "QA"
    Then validate Rule Status with DataBase "QA Review - Editorial Response;Not Started;iht_ittest01"
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "QA" to "PO;Editorial"

    Examples: 
      | FiterName        | FilterValue                             |
      | FilterTaskStatus | Library;Not Started;Final PO Review;REV |

  @PCA-5020 @R240Demo
  Scenario: PCA-5020 (QAs) ICD Interpretive Rule Update Ability to handle 'Deactivated/Disabled/Retired' Rule scenarios
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    When user Update Rule "DEACTIVATERMRIUPD","" using Lotus Services
    And select "RuleID"
    Then click on Start Review and Validate "RULEDEACTIVATED"

  @PCA-24353
  Scenario Outline: PCA-24353 Rule routing is not happening properly to the  Editorial user
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "All""No Action"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Retire Rule-a"
    Then Verify Assigned user and claim the rule
    Given user logs into "QA" with "SERENITY" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Editorial Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete Editorial review with "<Changes>"
    Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user Return Rule from "QA" to "PO"
    Then validate Rule Status with DataBase "Final PO Review - QA Return;Not Started;iht_ittest01"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "Modify Decisions;No Action""Remove"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Do Not Retire Rule"
    Then Validate the Rule Task "Not Started"
    Then validate Admin rule review value in DB "<Instance Type>" "Task" "QA Review"
    Then validate Admin rule review value in DB "<Instance Type>" "Status" "Not Started"
    Then validate Admin rule review value in DB "<Instance Type>" "Assignee" "iht_ittest01"

    Examples: 
      | FiterName        | FilterValue                             |
      | FilterTaskStatus | Library;Not Started;Final PO Review;REV |
