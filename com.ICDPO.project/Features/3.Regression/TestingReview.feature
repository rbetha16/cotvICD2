#Author: your.email@your.domain.comm
@Regression @TestingReview
Feature: Testing Review

  @PCA-5040 @R242Demo
  Scenario Outline: PCA-5400 ICD Rule Review (Testing) Return a rule
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
    Then Complete QA review with "<Changes>"
    Then Verify Assigned user and claim the rule
    Given user logs into "QA" with "SERENITY" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Testing Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user Return Rule from "Testing" to "QA"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "Testing" to "QA"
    When user click on Return Rule Response "Testing"
    Then validate Rule Status with DataBase "Testing Review - QA Response;Not Started;iht_ittest01"
    When user click on "Testing Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "Testing" to "QA"
    Then validate Rule Response comments to "Testing" from ""
    When user Return Rule from "Testing" to "Editorial"
    Then validate Rule Status with DataBase "Editorial Review - Testing Return;Not Started;iht_ittest01"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Editorial Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "Testing" to "Editorial"
    When user click on Return Rule Response "Testing"
    Then validate Rule Status with DataBase "Testing Review - Editorial Response;Not Started;iht_ittest01"
    When user click on "Testing Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "Testing" to "Editorial"
    Then validate Rule Response comments to "Testing" from ""

    Examples: 
      | FiterName        | FilterValue                             |
      | FilterTaskStatus | Library;Not Started;Final PO Review;REV |

  @PCA-5400 @R242Demo
  Scenario Outline: PCA-5400 ICD Interpretive Rule Update (Testing) Ability for Testing to Return Rule to QA
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "PROPOSALBASERULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "REV""Remove"
    And Modify Editorial comments with "No Editorial Changes" in "Only Final PO Review"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Do Not Retire Rule"
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
    Given user logs into "QA" with "SERENITY" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Testing Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user Return Rule from "Testing" to "CPM"
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "Testing" to "CPM"
    When user click on Return Rule Response "Testing"
    Then validate Rule Status with DataBase "Testing Review - CPM Response;Not Started;iht_ittest01"
    When user click on "Testing Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "Testing" to "CPM"
    Then validate Rule Response comments to "Testing" from ""

    Examples: 
      | FiterName        | FilterValue                            |
      | FilterTaskStatus | Custom;Not Started;Final PO Review;REV |

  @PCA-5041
  Scenario Outline: ICD Interpretive Rule Update (Testing) Ability to Complete All Testing Tasks
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
    Then Verify Assigned user and claim the rule
    Given user logs into "QA" with "SERENITY" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Testing Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete Testing review with "<Changes>"

    Examples: 
      | FiterName        | FilterValue                             | Changes                       |
      | FilterTaskStatus | Library;Not Started;Final PO Review;SIM | No Editorial Changes Required |

  @PCA-5037 @R242Demo
  Scenario Outline: PCA-5037 ICD Interpretive Update (Testing)  Ability to view 'Testing Review Work queue' Assignment
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And Modify Editorial comments with "Edit values" in "Only Final PO Review"
    And select System Proposal "All""No Action"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Do Not Retire Rule"
    Then Verify Assigned user and claim the rule
    Given user logs into "QA" with "SERENITY" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete QA review with "<Changes>"
    Then validate Rule Status with DataBase "QA Review;Completed;iht_ittest01"

    Examples: 
      | FiterName        | FilterValue                             | Changes                       |
      | FilterTaskStatus | Library;Not Started;Final PO Review;SIM | No Editorial Changes Required |

  @PCA-5396 @R242Demo
  Scenario Outline: PCA-5396 ICD Rule Review (Testing)  Ability to upload BRAT test results in IU
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
    Then Complete QA review with "<Changes>"
    Then Verify Assigned user and claim the rule
    Given user logs into "QA" with "SERENITY" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Testing Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user upload the BRAT test results and Validate
    Then user Delete the attachments and Validate

    Examples: 
      | FiterName        | FilterValue                             |
      | FilterTaskStatus | Library;Not Started;Final PO Review;REV |

  @PCA-14854 @R244Demo
  Scenario Outline: ICD Rule Review (Testing) Return a rule to MD
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And Modify Editorial comments with "Edit values" in "Only Final PO Review"
    And select System Proposal """Modify"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Do Not Retire Rule"
    Then Verify Assigned user and claim the rule
    Given user logs into "QA" with "SERENITY" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    #When user click on "Editorial Review Work Queue" for "FINAL PO DEL" Instance
    #And select "RuleID"
    #And click on "Start Review"
    #Then Complete Editorial review with "<Changes>"
    #Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    #And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete QA review with "<Changes>"
    Then Verify Assigned user and claim the rule
    Given user logs into "QA" with "SERENITY" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Testing Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user Return Rule from "Testing" to "PO"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "Testing" to "PO"
    When user click on Return Rule Response "Testing"
    Then validate Rule Status with DataBase "Testing Review - Final PO Response;Not Started;iht_ittest01"

    Examples: 
      | FiterName        | FilterValue                             |
      | FilterTaskStatus | Library;Not Started;Final PO Review;DEL |

  @PCA-14859 @R244Demo
  Scenario Outline: PCA 5033 ICD Interpretive Rule Update (Config) Config Return Rule to MD
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
    When user Return Rule from "Config" to "PO"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "Config" to "PO"
    When user click on Return Rule Response "Config"
    Then validate Rule Status with DataBase "Configuration Review - Final PO Response;Not Started;iht_ittest01"
    When user click on "Configuration Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "Config" to "PO"
    Then validate Rule Response comments to "Config" from ""

    Examples: 
      | FiterName        | FilterValue                             |
      | FilterTaskStatus | Library;Not Started;Final PO Review;REV |

 
