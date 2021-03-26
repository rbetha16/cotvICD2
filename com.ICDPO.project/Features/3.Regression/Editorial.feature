#Author: your.email@your.domain.comm
@Regression @EditorialReview
Feature: Editorial Review
  I want to use this template for my feature file

  @PCA-4634 @PCA-4632 @PCA-4627 @PCA-4623 @PCA-4622 @PCA-4620 @R239Demo
  Scenario: PCA-4634 (Editorial) Potential Conflict (Editorial) Ability to select Conflict Review Completed
    			PCA-4632 (Editorial) Potential Conflict (Editorial): Ability to select 'Conflict Identified ' as 'No'
    			PCA-4627 (Editorial) Potential Conflict: (UI) Ability to select Potential Conflict

    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "PRELIM PO DEL" Instance
    When user update Rule Editorial through Services
    And select "RuleID"
    Then click on Start Review and Validate Potential Conflict
    And Click on "Editorial Review Work Queue" from Group Tasks
    And user Select and Claim the Rule from Editorial Pool
    When user click on "Editorial Review Work Queue" for "PRELIM PO DEL" Instance
    And select "RuleID-Admin"
    When user Complete Conflict Review "no"
    When user click on "PO Review Work Queue" for "PRELIM PO DEL" Instance
    And select "RuleID"

  @Editorial93
  Scenario Outline: Validate the Editorialcommetns "<MDReview>"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<MDReview>" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    Then Validate the Editorial details in"Description"
    Then Validate the Editorial details in"Notes"
    Then Validate the Editorial details in"Script"
    Then Validate the Editorial details in"Rationale"
    Then Validate the Editorial details in"Reference"

    Examples: 
      | FiterName        | FilterValue                              | MDReview      |
      | FilterTaskStatus | Custom;Started;Preliminary PO Review;REV | PRELIM PO DEL |

  @Editorial1
  Scenario Outline: Edit values and verify in final MD
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "PRELIM PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And Modify Editorial comments with "<Changes>" in "Preliminary PO Review"
    And Add another code from add code with "ADDCODE"
    And select System Proposal "All""No Action"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Do Not Retire Rule"
    Then Verify Assigned user and claim the rule
    Given user logs into "QA" with "SERENITY" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "PRELIM PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    And Modify Editorial comments with "<Changes>" in "Final Review"

    Examples: 
      | FiterName        | FilterValue                                  | Changes              |
      | FilterTaskStatus | Custom;Not Started;Preliminary PO Review;DEL | No Editorial Changes |
      | FilterTaskStatus | Custom;Not Started;Preliminary PO Review;DEL | Edit values          |

  @Editorial2
  Scenario Outline: Validate editorial in final MD
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And Modify Editorial comments with "<Changes>" in "Only Final PO Review"

    Examples: 
      | FiterName        | FilterValue                            | Changes              |
      | FilterTaskStatus | Custom;Not Started;Final PO Review;REV | No Editorial Changes |
      | FilterTaskStatus | Custom;Not Started;Final PO Review;REV | Edit values          |

  @EditorialReview @PCA-4683
  Scenario Outline: Complete the editorial review <Changes>
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
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

    Examples: 
      | FiterName        | FilterValue                             | Changes              |
      | FilterTaskStatus | Library;Not Started;Final PO Review;DEL | No Editorial Changes |

  @PCA-5006 @R240Demo
  Scenario Outline: PCA-5006 ICD Interpretive Rule Update (Editorial)  Ability to return a Library rule to MD with a question
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
    When user Return Rule from "Editorial" to "PO"
    Then validate Rule Status with DataBase "Final PO Review - Editorial Return;Not Started;iht_ittest01"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "Editorial" to "PO"
    When user click on Return Rule Response "Editorial"
    Then validate Rule Status with DataBase "Editorial Review - Final PO Response;Not Started;iht_ittest01"
    When user click on "Editorial Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "Editorial" to "PO"

    Examples: 
      | FiterName        | FilterValue                             |
      | FilterTaskStatus | Library;Not Started;Final PO Review;REV |

  @PCA-5013 @PCA-8130 @R241Demo
  Scenario Outline: PCA-5013 ICD Interpretive Rule Update (Editorial)  Ability to return a Custom rule to CPM with a question
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "RULESINGLEPAYER" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "All""No Action"
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
    When user click on "Editorial Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user Return Rule from "Editorial" to "CPM"
    Then validate Rule Status with DataBase "CPM Review - Editorial Return;Not Started;iht_ittest01"
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "Editorial" to "CPM"
    When user click on Return Rule Response "Editorial"
    Then validate Rule Status with DataBase "Editorial Review - CPM Response;Not Started;iht_ittest01"
    When user click on "Editorial Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "Editorial" to "CPM"
    Then validate Rule Response comments to "Editorial" from ""

    Examples: 
      | FiterName        | FilterValue                            |
      | FilterTaskStatus | Custom;Not Started;Final PO Review;REV |

  @PCA-5014 @R240Demo
  Scenario Outline: PCA-5014 ICD Interpretive Rule Update (Editorial) Editorial Response to QA for Return Rule
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
    Then user navigate to Rule Review in AdminTab "Reassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
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
    When user Return Rule from "QA" to "Editorial"
    Then validate Rule Status with DataBase "Editorial Review - QA Return;Not Started;iht_ittest01"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Editorial Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "QA" to "Editorial"
    When user click on Return Rule Response "QA"
    Then validate Rule Status with DataBase "QA Review - Editorial Response;Not Started;iht_ittest01"
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "QA" to "Editorial"

    Examples: 
      | FiterName        | FilterValue                             |
      | FilterTaskStatus | Library;Not Started;Final PO Review;REV |

  @PCA-5181
  Scenario Outline: Complete the editorial review for custom <Changes>
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
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
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    And complete the Review"CPM"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "CPM"
    Then Verify Assigned user and claim the rule
    Given user logs into "QA" with "SERENITY" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Editorial Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then Complete Editorial review with "<Changes>"

    Examples: 
      | FiterName        | FilterValue                            | Changes              |
      | FilterTaskStatus | Custom;Not Started;Final PO Review;DEL | No Editorial Changes |
   
  
