#Author: your.email@your.domain.comm
@Regression @MDReview
Feature: MD Review
  I want to use this template for my feature file

  @PCA-4450 @R238Demo3525
  
  Scenario Outline: PCA-4450 ICD Rule review Ability to validate list of rules under MD Review Workqueue
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Editorial Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "Editorial Review" and "<FilterValue>"
    Then validate list of rules under MD Review Workqueue "Editorial Review"

    Examples: 
      | FiterName        | FilterValue                           |
      | FilterTaskStatus | Custom;Not Started;Editorial Review;N |

  @PCA-3525 @PCA-4491 @PCA-5133
  Scenario Outline: PCA-3525 Add Code and delete code using Start New Code on the rule "<MDReview>"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<MDReview>" Instance
    And Apply filters in My Task and Retrive Rule in DB "<Review>" and "<FilterValue>"
    And select "RuleID"
    Then Validate the "Decisions" with DB

    Examples: 
      | FilterValue                      | MDReview     | Review          |
      | Custom;Started;Final PO Review;N | FINAL PO DEL | Final PO Review |

  @DecisionSummary
  Scenario Outline: Ability to view Decision summary"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And Add another code from add code with "ADDCODE"
    And select System Proposal "All""No Action"
    And click on "Generate Summaries"
    Then Validate Descision Summary

    Examples: 
      | FilterValue                        |
      | Custom;Started;Final PO Review;DEL |

  @PCA-3547
  Scenario Outline: PCA-3547 Ability to 'Show Full Descriptions' for system generated proposals
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    Then Validate the full description of System generated proposals

    Examples: 
      | FilterValue                             | Changes              |
      | Library;Not Started;Final PO Review;DEL | No Editorial Changes |

  @PCA-5127DEL @R239Demo
  Scenario Outline: PCA-5127 DEL ICD Rule Review Final MD Ability to view Retire Rules Summary
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "PROPOSALBASERULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "REV""Remove"
    And click on "Generate Summaries"
    Then validate Retire Rule Secion "SystemDeterminRetire" and ""

    Examples: 
      | FilterValue                            |
      | Custom;Not Started;Final PO Review;REV |

  @PCA-3524 @PCA-4659 @R238Demo
  Scenario Outline: PCA-4659 ICD Rule Review for MDs: Ability for Final MD to 'Modify' Prelim MD's decision
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "PRELIM PO DEL" Instance
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
    When user click on "PO Review Work Queue" for "PRELIM PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    Then validate Rationale Comments in "Final PO Review"
    And select System Proposal "Modify Decisions;REV""No Decision"
    Then validate System Proposal "REV""No Decision"

    Examples: 
      | FilterValue                                  |
      | Custom;Not Started;Preliminary PO Review;REV |

  #@PCA-4807 @R238Demo @RequiredBWOInstance
  #Scenario Outline: PCA-4807 ICD Rule Review (MDs) Ability to view BWO Or under Actions
  #Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
  #When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
  #And user navigate to IU "ICD10-MyTasks" Instances Screen
  #When user click on "PO Review Work Queue" for "BWO INSTANCE" Instance
  #
  #Examples:
  #| FiterName        | RuleNo   | BWO Criteria   |
  #| FilterTaskStatus | 12137.13 | Billed with OR |
  
  @PCA-4492
  Scenario Outline: PCA-4492 Ability to updated the manual praposal grid Final PO Review
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
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And select "RuleID"
    And click on "Start Review"
    When user update the bulk Decisions

    Examples: 
      | FilterValue                            |
      | Custom;Not Started;Final PO Review;REV |

  @PCA-6025Final @Demo238
  Scenario Outline: PCA-6025 Ability to raise 'Rule Review Request'
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<Instance Type>" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "Rule Review Request-InvalidInput"
    And select "Rule Review Request"

    Examples: 
      | FilterValue                          | Instance Type |
      | Custom;Not Started;Final PO Review;N | FINAL PO DEL  |

  @PCA-6025SC01 @Demo238
  Scenario Outline: PCA-6025 Ability to raise 'Rule Review Request'
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<Instance Type>" Instance
    And Apply filters in My Task and Retrive Rule in DB "<Task Type>" and "<FilterValue>"
    And select "Rule Review Request-InvalidInput"
    And select "Rule Review Request-Non Candidates"

    Examples: 
      | FilterValue                                         | Instance Type | Task Type       |
      | Custom;Not Started;Final PO Review;N;Non Candidates | FINAL PO DEL  | Final PO Review |

  @PCA-5009 @PCA-5136
  Scenario Outline: PCA-5009 Ability to Add Another Code to the Manual Grid Final PO Review
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<MDReview>" Instance
    And Apply filters in My Task and Retrive Rule in DB "<Review>" and "<FilterValue>"
    And select "RuleID"
    Then Add another code from add code with "D81.3"

    Examples: 
      | FilterValue                      | MDReview     | Review          |
      | Custom;Started;Final PO Review;N | FINAL PO DEL | Final PO Review |

  @PCA-3538
  Scenario Outline: PCA-3538 Ability to view the manual praposal grid Final PO Review
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<MDReview>" Instance
    And Apply filters in My Task and Retrive Rule in DB "<Review>" and "<FilterValue>"
    And select "RuleID"
    Then Verify the manual praposal grid

    Examples: 
      | FilterValue                      | MDReview     | Review          |
      | Custom;Started;Final PO Review;N | FINAL PO DEL | Final PO Review |

  @PCA-4643SC1 @Demo238 @ReRun
  Scenario Outline: PCA-4643 SC1 ICD Rule Review (MDs)Ability to select 'No Change' required on Rule "<Instance Type>" "<Task Type>"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<Instance Type>" Instance
    And Apply filters in My Task and Retrive Rule in DB "<Task Type>" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And set "No Change Required" in MD Review and click on "Cancel"
    And set "No Change Required" in MD Review and click on ""
    Then validate Admin rule review value in DB "<Instance Type>" "Status" "No Change Required"
    And user navigate to IU "ICD10-Admin" Instances Screen
    Then validate Admin rule review value "Admin" "<Instance Type>" "Task" ""
    And user navigate to IU "Rule Review" Instances Screen
    And select "RuleID"
    And verify comments entered in "Final PO"
    And set "Unset No Change Required" in MD Review and click on ""
    Then validate Admin rule review value in DB "<Instance Type>" "Status" "Started"

    Examples: 
      | FilterValue                          | Instance Type | Task Type       |
      | Custom;Not Started;Final PO Review;N | FINAL PO DEL  | Final PO Review |

  @PCA-4642SC04 @Demo238
  Scenario Outline: 4642 SC04 ICD Rule Review (MDs) : Ability to select 'No Decision' on Rule Level
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "Final PO Review" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And should not viewing "No Decision-Enabled" button

    Examples: 
      | FilterValue                           |
      | Library;Not Started;Final PO Review;N |

  @PCA-3534SC1 @Demo238
  Scenario Outline: PCA-3534 Ability to select 'No Decision' on Proposal "<Instance Type>" "<Task Type>"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<Instance Type>" Instance
    #And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And Apply filters in My Task and Retrive Rule in DB "<Instance Type>" and "<FilterValue>"
    And select "RuleID"
    #And click on "Start Review"
    And Select System Proposal "" "No Decision-Displayed"

    Examples: 
      | FilterValue                           | Instance Type | Task Type       |
      #| Custom;Not Started;Final PO Review;N  | FINAL PO DEL  | Final PO Review |
      | Library;Not Started;Preliminary PO Review;N | PRELIM PO DEL  | Preliminary PO Review |

  @PCA-4800SC01
  Scenario Outline: PCA-4800 Ability to navigate back from Summaries tab to Modify a decision for Final MD "<FilterValue>"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal """No Action"
    And click on "Generate Summaries"
    And click on "Modify Decisions"
    And select System Proposal """No Decision"
    And click on "Generate Summaries"

    Examples: 
      | FilterValue                          |
      | Custom;Not Started;Final PO Review;N |

  @PCA-5007
  Scenario Outline: PCA 3506 MD Review queue  Ability to view MD review task "<Task Type>"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<Instance Type>" Instance
    And Apply filters in My Task and Retrive Rule in DB "<Task Type>" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal """Modify"

    Examples: 
      | FilterValue                          | Task Type       | Instance Type |
      | Custom;Not Started;Final PO Review;N | Final PO Review | FINAL PO DEL  |

  @PCA-6025 @Demo238
  Scenario Outline: PCA-6025 Ability to raise 'Rule Review Request'
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<Instance Type>" Instance
    And Apply filters in My Task and Retrive Rule in DB "<Task Type>" and "<FilterValue>"
    And select "Rule Review Request-InvalidInput"
    And select "Rule Review Request"

    Examples: 
      | FilterValue                          | Instance Type | Task Type       |
      | Custom;Not Started;Final PO Review;N | FINAL PO DEL  | Final PO Review |

  @PCA-4805
  Scenario Outline: PCA-4805 Ability to view EXISTS link under Actions "<FilterValue>"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "PROPOSALBASERULE" and "<FilterValue>"
    And select "RuleID"
    When clicks on "EXISTS" action link

    Examples: 
      | FilterValue                             | Task Type       | Instance Type |
      | Custom;Not Started;Final PO Review;SIM  | Final PO Review | FINAL PO DEL  |
      | Library;Not Started;Final PO Review;SIM | Final PO Review | FINAL PO DEL  |

  @PCA-3513 @Local
  Scenario Outline: PCA-3513 Ability to take 'Bulk Decision' on the set of rules
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
#
    #And Apply filters in My Task Rule Work Queue "<FilterName>" and "<FilterValue>"
    #When selects the "Rules" with "Same Proposal" and "Codes"
    #When click on the " Bulk Decision" button
    #And can select the "<Decision for these Proposals:>" from the list drop down
    #When click on the "Apply Decision-No" button
    #When click on the "Apply Decision-Yes" button
    #And should be displayed with "Bulk Dec" under "<flags>" columns on Rule
    Examples: 
      | FilterName                       | FilterValue                |
      | Proposal Types;Codes;Task Status | Similar;D81.31;Not Started |

  @PCA-6120 @Local
  Scenario Outline: PCA-6120 ICD Rule Review(MDs): Ability to navigate back from Summaries tab to Modify a decision
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "PRELIM PO DEL" Instance

    #And Apply filters in My Task Rule Work Queue "<FilterName>" and "<FilterValue>"
    Examples: 
      | FilterName                       | FilterValue                |
      | Proposal Types;Codes;Task Status | Similar;I48.11;Not Started |

  @PCA-61201 @Local
  Scenario Outline: PCA-61201 ICD Rule Review(MDs): Ability to navigate back from Summaries tab to Modify a decision
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen
    Then validate Admin rule review value "Admin" "PRELIM PO DEL" "Task" ""
    And user navigate to IU "Rule Review" Instances Screen

    # And Apply filters in My Task Rule Work Queue "<FilterName>" and "<FilterValue>"
    Examples: 
      | FilterName                             | FilterValue                                      |
      | Proposal Types;Codes;Task Status;Tasks | Similar;I48.11;Not Started;Preliminary PO Review |

  @PCA-4804
  Scenario Outline: PCA-4804 Ability to view INFO link under Actions "<FilterValue>"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "<Task Type>" and "<FilterValue>"
    And select "RuleID"
    When clicks on "INFO" action link

    Examples: 
      | FilterValue                           | Task Type       | Instance Type |
      | Custom;Not Started;Final PO Review;N  | Final PO Review | FINAL PO DEL  |
      | Library;Not Started;Final PO Review;N | Final PO Review | FINAL PO DEL  |

  @PCA-3514 @Local
  Scenario Outline: PCA-3514 Ability to 'Remove Bulk Decision' to the set of rules
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance

    And Apply filters in My Task Rule Work Queue "<FilterName>" and "<FilterValue>"
    Then click on the "Remove Bulk Decision" button
    Then should be able to view and select/multi select and remove the decision
    Examples: 
      | FilterName                       | FilterValue                |
      | Proposal Types;Codes;Task Status | Similar;I48.11;Not Started |

  @PCA-3512 @Local
  Scenario: PCA-3512 Ability to 'Retrieve Non Candidate Rules' in the review
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application

  When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
  And user navigate to IU "ICD10-MyTasks" Instances Screen
  When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
  Then click on the "Retrieve Non-Candidate Rules" button
  And then Select/Multi select the rules that you want to retrieve back
  Then click on the "Move Rules Back to Review-No" button
  Then click on the "Move Rules Back to Review-Yes" button
  And select "RuleID"
  
  @PCA-3512Sc1 @Local
  Scenario: Ability to 'Retrieve Non Candidate Rules' in the review
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application

  @PCA-4467 @PCA-4466 @PCA-4463
  Scenario Outline: PCA-4467 ICD Rule Review for MDs Ability to 'Search' for rules using 'Task ''Task Status''ICD code' and search criteria
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    Then Validate the dropdown values with DB "<DB Column>" for "Final PO Review" with "<UI Column>"

    #Then click on the "Filters-Reset" button
    Examples: 
      | FilterName  | FilterValue | DB Column          | UI Column   |
      | Task Status | Not Started | IRI.ICD_CODE       | Codes       |
      #| Task Status | Not Started | TTL.TASK_TYPE_DESC | Tasks       |
      #| Task Status | Not Started | TASK_STATUS_DESC   | Task Status |

  @PCA-4470
  Scenario Outline: PCA-4470 ICD Rule Review for MDs Ability to 'Search' for rules using 'Rules with ARD' search criteria
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task Rule Work Queue "<FilterName>" and "<FilterValue>"
    Then Validate the Rules count with DB "<DB Column>" and Column name "<UI Column>"

    Examples: 
      | FilterName      | FilterValue | DB Column         | UI Column       |
      | Rules With ARDs | Restrict To | IRD.ARD_EXISTS_YN | Rules With ARDs |

  @PCA-4462 @PCA-4461 @PCA-4460 @PCA-4459 @PCA-4458 @PCA-4456
  Scenario Outline: PCA-4462 ICD Rule Review for MDs: Ability to 'Search' for rules using 'Proposal Type' search criteria
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click "PO Review Work Queue" for "FINAL MD COMPACT" Instance
    
    Then Validate Selected dropdown values with DB "IRD.MED_POL_TITLE" for "Final PO Review" with "Medical Policies"
    Then click on the "Filters-Reset" button
    Then Validate Selected dropdown values with DB "IRD.TOPIC_TITLE" for "Final PO Review" with "Topics"
    Then click on the "Filters-Reset" button
    Then Validate Selected dropdown values with DB "IRD.DP_DESC" for "Final PO Review" with "Decision Points"
    Then click on the "Filters-Reset" button
    Then Validate Selected dropdown values with DB "UGL.UPDATE_GROUP_NAME" for "Final PO Review" with "Proposal Types"
    Then click on the "Filters-Reset" button
        

    Examples: 
      | FilterName                                      | FilterValue                                |
      | Proposal Types;Task Status;Tasks;Library Status | Similar;Not Started;Final PO Review;Custom |

  @PCA-4468
  Scenario Outline: PCA-4468 ICD Rule Review for MDs: Ability to 'Search' for rules using 'Payers' search criteria
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance

    Then Validate the dropdown values with DataBase "<DB Column>" for "Final PO Review" with "<UI Column>"
    Then click on the "Filters-Reset" button
    Examples: 
      | FilterName  | FilterValue | DB Column     | UI Column |
      | Task Status | Not Started | PAYERS_4_RULE | Payers    |

  @PCA-5951
  Scenario Outline: PCA-5951 Ability to view 'REV' under 'Actions' "<Instance Type>"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<Instance Type>" Instance
    And Apply filters in My Task and Retrive Rule in DB "PROPOSALBASERULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    When clicks on "REV" action link
    And should be viewing "Previous code Description" textbox with "<Old Description QUERY>"
    And should be viewing "Current code Description" textbox with "<New Description QUERY>"

    Examples: 
      | FilterValue                            | Instance Type |
      | Custom;Not Started;Final PO Review;REV | FINAL PO DEL  |
