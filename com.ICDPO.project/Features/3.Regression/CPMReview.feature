#Author: your.email@your.domain.comm
@Regression @CPMReview
Feature: CPM Review
  I want to use this template for my feature fil

  @PCA-5378
  Scenario Outline: (Custom )Create Manual RMR if multiple CPMs give different decisions on rule
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And Apply filters in My Task and Retrive Rule in DB "RETRIVEMULTIPAYERRULE" and "<FilterValue>"
    When user select Rule with Multiple Payers "ONECPMPAYERSDB"
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "SIM""No Action"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Retire Rule-a"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user Complete CPM Decesion "Agree"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Do Not Retire Rule"
    Then Verify Assigned user and claim the rule
    Given user logs into "QA" with "SERENITY" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user Complete CPM Decesion "Disagree"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Do Not Retire Rule"
    Then Validate the Rule Task "Manual RMR"

    Examples: 
      | FilterValue                            |
      | Custom;Not Started;Final PO Review;SIM |

    @PCA-5366 @PCA-8382
  Scenario Outline: PCA-5366 Ability for CPM to view 'Full Descriptions' of codeds
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
    Then user navigate to Rule Review in AdminTab "CPMReassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then should be displayed with Full description along with code under Sim Codes and Review codes

    Examples: 
      | FilterValue                            |
      | Custom;Not Started;Final PO Review;SIM |


  @PCA-5361 @PCA-8482
  Scenario Outline: Rule with Iht_ittest01(CPM1) with "Not started" status and Ith_ittest02(cpm 2) with "Not started" status
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And Apply filters in My Task and Retrive Rule in DB "RETRIVEMULTIPAYERRULE" and "<FilterValue>"
    When user select Rule with Multiple Payers "ONECPMPAYERSDB"
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "All""No Action"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Retire Rule-a"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    Then user navigate to Rule Review in AdminTab "CPMReassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And verify rule Status "Not Started" in DB for User "iht_ittest01"

    Examples: 
      | FilterValue                            |
      | Custom;Not Started;Final PO Review;SIM |


    @PCA-5361 @PCA-8483
  Scenario Outline: Rule with Iht_ittest01(CPM1) with "started" status and Ith_ittest02(cpm 2) with "Not started" status
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And Apply filters in My Task and Retrive Rule in DB "RETRIVEMULTIPAYERRULE" and "<FilterValue>"
    When user select Rule with Multiple Payers "ONECPMPAYERSDB"
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "SIM""No Action"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Retire Rule-a"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    Then user navigate to Rule Review in AdminTab "CPMReassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And verify rule Status "Started" in DB for User "iht_ittest01"

    Examples: 
      | FilterValue                            |
      | Custom;Not Started;Final PO Review;SIM |


  @PCA-8484
  Scenario Outline: PCA-5361 Rule with Iht_ittest01(CPM1) with "Not started" status and Ith_ittest02(cpm 2) with "Started" status
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And Apply filters in My Task and Retrive Rule in DB "RETRIVEMULTIPAYERRULE" and "<FilterValue>"
    When user select Rule with Multiple Payers "ONECPMPAYERSDB"
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "SIM""No Action"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Retire Rule-a"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    Then user navigate to Rule Review in AdminTab "CPMReassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And verify rule Status "Not Started" in DB for User "iht_ittest01"

    Examples: 
      | FilterValue                            |
      | Custom;Not Started;Final PO Review;SIM |


  @PCA-8485
  Scenario Outline: PCA-5361 Rule with Iht_ittest01(CPM1) with "started" status and Ith_ittest02(cpm 2) with "started" status
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And Apply filters in My Task and Retrive Rule in DB "RETRIVEMULTIPAYERRULE" and "<FilterValue>"
    When user select Rule with Multiple Payers "ONECPMPAYERSDB"
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "SIM""No Action"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Retire Rule-a"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then user navigate to Rule Review in AdminTab "CPMReassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And verify rule Status "Started" in DB for User "iht_ittest01"

    Examples: 
      | FilterValue                            |
      | Custom;Not Started;Final PO Review;SIM |


  @PCA-5358
  Scenario Outline: Ability for CPM to Agree or Disagree to retire rule
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
    When user complete the Authorization Decision with "Retire Rule-a"
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

    Examples: 
      | FiterName        | FilterValue                            | Changes              |
      | FilterTaskStatus | Custom;Not Started;Final PO Review;REV | No Editorial Changes |

  @PCA-5328
  Scenario Outline: PCA-5328 Ability to capture manual proposals Add Code
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "PROPOSALBASERULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And Add another code from add code with "ADDCODE"
    And select System Proposal "All""Remove"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Retire Rule-a"
    Then Verify Assigned user and claim the rule
    Given user logs into "QA" with "SERENITY" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    And complete the Review"ADDCODE"

    Examples: 
      | FiterName        | FilterValue                            | Changes              |
      | FilterTaskStatus | Custom;Not Started;Final PO Review;REV | No Editorial Changes |

  @PCA-5323 @PCA-5334
  Scenario Outline: Ability to select CPM descision complete
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "PROPOSALBASERULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And Add another code from add code with "ADDCODE"
    And select System Proposal "All""No Decision"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Do Not Retire Rule"
    Then Verify Assigned user and claim the rule
    Given user logs into "QA" with "SERENITY" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    And complete the Review"COMPLETE"

    Examples: 
      | FiterName        | FilterValue                            | Changes              |
      | FilterTaskStatus | Custom;Not Started;Final PO Review;REV | No Editorial Changes |

  @PCA-5375
  Scenario Outline: Ability to generate manual RMR for additional config
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "RETRIVEMULTIPAYERRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And Add another code from add code with "ADDCODE"
    And select System Proposal "All""No Action"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Retire Rule-a"
    Then Verify Assigned user and claim the rule
    Given user logs into "QA" with "SERENITY" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    And complete the Review"RMR"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "CPM"
    Then Validate the Rule Task "Manual RMR"

    Examples: 
      | FiterName        | FilterValue                            | Changes              |
      | FilterTaskStatus | Custom;Not Started;Final PO Review;REV | No Editorial Changes |

  @PCA-5379 @R241Demo
  Scenario Outline: PCA-5379 (CPM) ICD Rule Review (Manual RMR) (Custom Rule )Create Manual RMR if Obsolete Payer on the rule
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
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "MyTasks" Instances Screen
    When user select the Rule Payers and make Obsolete "ON"
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user Complete CPM Decesion "Agree"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "CPM"
    Then Validate the Rule Task "Manual RMR"

    Examples: 
      | FiterName        | FilterValue                            |
      | FilterTaskStatus | Custom;Not Started;Final PO Review;REV |

  @PCA-5362 @R241Demo***
  Scenario Outline: PCA-5362 ICD Rule Review (CPMs)  Ability for CPM to reply to Returned Rule from QA
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "RULESINGLEPAYER" and "<FilterValue>"
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
    When user Return Rule from "QA" to "CPM"
    Then validate Rule Status with DataBase "CPM Review - QA Return;Not Started;iht_ittest01"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "QA" to "CPM"
    When user click on Return Rule Response "QA"
    Then validate Rule Status with DataBase "QA Review - CPM Response;Not Started;iht_ittest01"
    When user click on "QA Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate comments section as Rule return from "QA" to "CPM"
    Then validate Rule Response comments to "QA" from ""

    Examples: 
      | FiterName        | FilterValue                            |
      | FilterTaskStatus | Custom;Not Started;Final PO Review;REV |

  @PCA-5326 @PCA-8138 @R241Demo
  Scenario Outline: PCA-5326 ICD Rule Review (CPMs) Ability to select 'Payer Level' selection
    							PCA-5327 ICD Rule Review (CPMs) Ability to capture CPM Comments

    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "RETRIVEMULTIPAYERRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "REV""Remove"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Do Not Retire Rule"
    Then user navigate to Rule Review in AdminTab "CPMReassign" the Rule to "iht_ittest01" for "FINAL PO DEL"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user select ByPayer in CPM Decision as "Remove;No Action" and validate MD Decision "Remove"
    When user Complete CPM Decesion "Agree"
    And click on "Generate Summaries"
    Then validate ConfigSummary and Warning
    When user complete the Authorization Decision with "Do Not Retire Rule"
    Then validate Admin rule review value in AdminTab "FINAL PO DEL" "Task;Status" "CPM Review;Manual RMR"

    Examples: 
      | FiterName        | FilterValue                            |
      | FilterTaskStatus | Custom;Not Started;Final PO Review;REV |

  @PCA-14963
  Scenario Outline: Ability to select Set Require presentation in Rule Review
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "SIM""No Action"
    And click on "Generate Summaries"
    When user complete the Authorization Decision with "Retire Rule-a"
    And user navigate to IU "ICD10-Admin" Instances Screen
    Then validate Admin rule review value "Admin" "FINAL PO DEL" "Task" ""
    And user navigate to IU "Rule Review" Instances Screen
    And select "First-RuleID"
    And validate "Set Requires Presentation" in ""
    And validate "Unset Requires Presentation" in ""

    Examples: 
      | FilterValue                            |
      | Custom;Not Started;Final PO Review;SIM |
      
      
      
      @PCA-21621
  Scenario Outline: PCA-21621 Ability to capture Copy Decisions & Rationale Comments (UI)
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "CPM Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    When user Click on CPM BulkDecision "All" Proposal Type "SIM,DEL" and Decision "No Decision,Remove"
    Examples: 
      | FilterValue                           | Task Type       | Instance Type |
      | Custom;Not Started;Final PO Review;N  | Final PO Review | FINAL PO DEL  |
   
      
       
