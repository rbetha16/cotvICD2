#Author: your.email@your.domain.comm
@Regression @PreliminaryReview
Feature: Preliminary PO Review
  I want to use this template for my feature file

  @PCA-5843 @R238Demo4659
  
  Scenario Outline: PCA 3527 MD Review queue Apply Bulk Decisions to Proposals
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "PRELIM PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "MULTIPROPOSALS" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    When user enter Bulk Decision and Validate

    Examples: 
      | FilterValue |

  @PCA-3536
  Scenario Outline: PCA-3536 Ability to view 'Rule Description' "<MDReview>"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "Final PO Review" and "<FilterValue>"
    And select "RuleID"
    Then Validate the "Rule Description" with DB

    Examples: 
      | FilterValue                          |
      | Custom;Not Started;Final PO Review;N |

  @PCA-3525 @PCA-4491 @PCA-5133
  Scenario Outline: PCA-4491 Add Code and delete code using Start New Code on the rule "<MDReview>"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<MDReview>" Instance
    And Apply filters in My Task and Retrive Rule in DB "<Review>" and "<FilterValue>"
    And select "RuleID"
    Then Validate the "Decisions" with DB

    Examples: 
      | FilterValue                            | MDReview      | Review                |
      | Custom;Started;Preliminary PO Review;N | PRELIM PO DEL | Preliminary PO Review |

  @PCA-5009 @PCA-5136
  Scenario Outline: PCA-5009 Ability to Add Another Code to the Manual Grid "<MDReview>"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    #When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    #And user navigate to IU "ICD10-MyTasks" Instances Screen
    #When user click on "PO Review Work Queue" for "<MDReview>" Instance
    #And Apply filters in My Task and Retrive Rule in DB "<Review>" and "<FilterValue>"
    #And select "RuleID"
    #Then Add another code from add code with "D81.3"

    Examples: 
      | FilterValue                            | MDReview      | Review                |
      | Custom;Started;Preliminary PO Review;N | PRELIM PO DEL | Preliminary PO Review |

  @PCA-5132
  Scenario Outline: PCA-5132 Add Code using Copy an existing Code on the rule "<MDReview>"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<MDReview>" Instance
    And Apply filters in My Task and Retrive Rule in DB "<Review>" and "<FilterValue>"
    And select "RuleID"
    Then Copy Code from existing rule with "D81.3"

    Examples: 
      | FilterValue                            | MDReview      | Review                |
      | Custom;Started;Preliminary PO Review;N | PRELIM PO DEL | Preliminary PO Review |

  @PCA-5135
  Scenario Outline: PCA-5135 Ability to select 'Modify' Decision Type - Final or Prelim MD
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "PRELIM PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"

    #And click on "Start Review"
    #And select System Proposal "REV""ModifyDecisionWarning"
    Examples: 
      | FilterValue                                  |
      | Custom;Not Started;Preliminary PO Review;REV |

  @PCA-5127 @R239Demo
  Scenario Outline: PCA-5127 REV ICD Rule Review Final MD Ability to view Retire Rules Summary
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "PRELIM PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "PROPOSALBASERULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "REV""No Action"
    And click on "Generate Summaries"
    Then validate Retire Rule Secion "NORetireRule" and ""
    And select System Proposal "Modify Decisions;REV""Remove"
    And click on "Generate Summaries"
    Then validate Retire Rule Secion "SystemDeterminRetire" and ""

    Examples: 
      | FilterValue                                  |
      | Custom;Not Started;Preliminary PO Review;REV |

  @PCA-5127B @R239Demo
  Scenario Outline: PCA-5127 SIM ICD Rule Review Final MD Ability to view Retire Rules Summary
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "PRELIM PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "PROPOSALBASERULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal "SIM""No Action"
    And click on "Generate Summaries"
    Then validate Retire Rule Secion "MDDeterminRetire" and ""

    Examples: 
      | FilterValue                                  |
      | Custom;Not Started;Preliminary PO Review;SIM |

  @PCA-4639
  Scenario Outline: PCA-4639 Validate the code range in manual grid Final PO Review
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<MDReview>" Instance
    And Apply filters in My Task and Retrive Rule in DB "<Review>" and "<FilterValue>"
    And select "RuleID"
    Then Valiadate the Range of code"011.63-011.66"

    Examples: 
      | FilterValue       | MDReview     | Review          |
      | Custom;Started;Final PO Review;N | FINAL PO DEL | Final PO Review |

  @PCA-4643SC2 @Demo238
  Scenario Outline: PCA-4643 should not viewing "No Change Required" butto
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "PRELIM PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And should not viewing "No Change Required" button

    Examples: 
      | FilterValue                                |
      | Custom;Not Started;Preliminary PO Review;N |

  @PCA-4642SC01 @Demo238 @ReRun
  Scenario Outline: PCA-4642 SC01 ICD Rule Review (MDs) Ability to select 'No Decision' on Rule Level "<FilterValue>"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "PRELIM PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And set "No Decision-Alert" in MD Review and click on ""
    And user enter "Preliminary Review" comments
    And set "No Decision" in MD Review and click on "No"
    And set "No Decision" in MD Review and click on ""
    And user navigate to IU "ICD10-Admin" Instances Screen
    Then validate Admin rule review value "Admin" "PRELIM PO DEL" "Task" ""
    And user navigate to IU "Rule Review" Instances Screen
    And select "RuleID"
    #And verify comments entered in "Preliminary Review"
    Then validate Admin rule review value in DB "PRELIM PO DEL" "Task" "Final PO Review After Preliminary"

    #And verify comments entered in "Final MD-Empty"
    Examples: 
      | FilterValue                                |
      | Custom;Not Started;Preliminary PO Review;N |

  @PCA-3534 @Demo238 @ReRun
  Scenario Outline: PCA 3534 MD Review queue  Ability to view MD review task "<Instance Type>" "<Task Type>"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<Instance Type>" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And Select System Proposal "" "No Decision-Displayed"

    Examples: 
      | FilterValue                                 | Instance Type | Task Type             |
      | Custom;Not Started;Preliminary PO Review;N  | PRELIM PO DEL | Preliminary PO Review |
      | Library;Not Started;Preliminary PO Review;N | PRELIM PO DEL | Preliminary PO Review |

  @PCA-5007Sc01
  Scenario Outline: PCA 3506 MD Review queue  Ability to view MD review task "<Task Type>"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<Instance Type>" Instance
    #And Apply filters in My Task and Retrive Rule in DB "<Task Type>" and "<FilterValue>"
     And Apply filters in My Task and Retrive Rule in DB "PROPOSALBASERULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal """Modify"

    Examples: 
      | FilterValue                                | Task Type             | Instance Type |
      | Custom;Not Started;Preliminary PO Review;REV | Preliminary PO Review | PRELIM PO DEL |

  #| Library;Not Started;Preliminary PO Review;N | Preliminary PO Review | PRELIM PO DEL |
  @PCA-6025Prilim @Demo238
  Scenario Outline: PCA-6025 Ability to raise 'Rule Review Request'
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<Instance Type>" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "Rule Review Request-InvalidInput"
    And select "Rule Review Request"

    Examples: 
      | FilterValue                                | Instance Type |
      | Custom;Not Started;Preliminary PO Review;N | PRELIM PO DEL |

  @PCA-6025SC02 @Demo238
  Scenario Outline: PCA-6025 Ability to raise 'Rule Review Request'
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<Instance Type>" Instance
    And Apply filters in My Task and Retrive Rule in DB "<Task Type>" and "<FilterValue>"
    And select "Rule Review Request-InvalidInput"
    And select "Rule Review Request-Non Candidates"

    Examples: 
      | FilterValue                                               | Instance Type | Task Type             |
      | Custom;Not Started;Preliminary PO Review;N;Non Candidates | PRELIM PO DEL | Preliminary PO Review |

  @PCA-6025SC03 @Demo238 @ReRun
  Scenario Outline: PCA-6025 Ability to raise 'Rule Review Request'
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen
    Then validate Admin rule review value "Admin" "<Instance Type>" "Task" ""
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And user navigate to IU "Rule Review" Instances Screen
    And select "Rule Review Request-InvalidInput"
    And select "Rule Review Request"

    Examples: 
      | FilterValue                                | Instance Type | Task Type             |
      | Custom;Not Started;Preliminary PO Review;N | PRELIM PO DEL | Preliminary PO Review |
      | Custom;Not Started;Final PO Review;N       | FINAL PO DEL  | Final PO Review       |

  @PCA-6025SC04 @Demo238
  Scenario Outline: PCA-6025 Ability to raise 'Rule Review Request'
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen
    Then validate Admin rule review value "Admin" "<Instance Type>" "Task" ""
    And Apply filters in My Task and Retrive Rule in DB "<Task Type>" and "<FilterValue>"
    And user navigate to IU "Rule Review" Instances Screen
    And select "Rule Review Request-InvalidInput"
    And select "Rule Review Request-Non Candidates"

    Examples: 
      | FilterValue                                               | Instance Type | Task Type             |
      | Custom;Not Started;Preliminary PO Review;N;Non Candidates | PRELIM PO DEL | Preliminary PO Review |
      | Custom;Not Started;Final PO Review;N;Non Candidates       | FINAL PO DEL  | Final PO Review       |

  @PCA-5951SC01 @Demo238
  Scenario Outline: PCA-5951 Ability to view 'REV' under 'Actions' "<Instance Type>"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
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
      | FilterValue                                  | Instance Type |
      | Custom;Not Started;Preliminary PO Review;REV | PRELIM PO DEL |

  @PCA-5996 @Demo238
  Scenario Outline: PCA-5996 Ability to Copy/Paste a decision or a Rationale Comment "<Instance Type>" "<Task Type>"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<Instance Type>" Instance
    And Apply filters in My Task and Retrive Rule in DB "<Task Type>" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And click on "Copy Decision" in WorkQueue

    Examples: 
      | FilterValue                          | Task Type       | Instance Type |
      | Custom;Not Started;Final PO Review;N | Final PO Review | FINAL PO DEL  |

  @PCA-5996Sc01 @Demo238
  Scenario Outline: PCA-5996 Ability to Copy/Paste a decision or a Rationale Comment "<Instance Type>" "<Task Type>"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "<Instance Type>" Instance
    And Apply filters in My Task and Retrive Rule in DB "PROPOSALBASERULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And click on "Copy Decision" in WorkQueue

    Examples: 
      | FilterValue                                  | Task Type             | Instance Type |
      | Custom;Not Started;Preliminary PO Review;SIM | Preliminary PO Review | PRELIM PO DEL |

  @PCA-5403 @Demo238
  Scenario Outline: PCA-5403 Ability to Validate 'BW And/BWO OR' logic hyperlink
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "PROPOSALBASERULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    Then adds a code with "Billed With (And);Billed Without (Or)" Category without Override flag

    Examples: 
      | FilterValue                            |
      | Custom;Not Started;Final PO Review;SIM |

  @PCA-4800 @Demo238 @ReRun
  Scenario Outline: PCA-4800 Ability to navigate back from Summaries tab to Modify a decision "<FilterValue>"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "PRELIM PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "ANYPROPOSALRULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And select System Proposal """No Action"
    And click on "Generate Summaries"
    And click on "Modify Decisions"
    And select System Proposal """No Decision"
    And click on "Generate Summaries"

    Examples: 
      | FilterValue                                |
      | Custom;Not Started;Preliminary PO Review;N |

  @PCA-4808 @Demo238
  Scenario Outline: PCA-4808 Ability to view "BW AND" under "Actions"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen

    #And user navigate to IU "ICD10-MyTasks" Instances Screen
    #When user click on "PO Review Work Queue" for "BWO INSTANCE" Instance
    #And user entered RuleNo "<RuleNo>"
    #Then validate "<BWO Criteria>" hyperlink under Actions column
    Examples: 
      | RuleNo  | BWO Criteria |
      | 17830.5 | BW_AND       |

  @PCA-3526 @Demo238
  Scenario Outline: PCA-3526 ICD Rule Review Ability to take "Logical Decision- Remove Code" on the rule proposal "<FilterValue>"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "PROPOSALBASERULE" and "<FilterValue>"
    And select "RuleID"
    And click on "Start Review"
    And retrive "SIM" code from System Proposals
    And click on "Remove Code" button on WorkQueue
    Then user should be able to enter the code that they want to remove in the pop up

    Examples: 
      | FilterValue                            |
      | Custom;Not Started;Final PO Review;SIM |

  @PCA-4999 @Demo238
  Scenario Outline: PCA-4999 Ability to view ICD ARD link "<Instance Type>" "<Task Type>"
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "FINAL PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "Final PO Review" and "<FilterValue>"
    And select "RuleID"
    Then the ICD ARD link appears beside the Potential Conflict button when the rule is defined with an ARD.
    And user can click on the link and review the ARD setup for the rule.

    Examples: 
      | FilterValue                          |
      | Custom;Not Started;Final PO Review;Y |

  @PCA-4804SC01
  Scenario Outline: PCA-4804 Ability to view INFO link under Actions "<FilterValue>"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "PRELIM PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "<Task Type>" and "<FilterValue>"
    And select "RuleID"
    When clicks on "INFO" action link

    Examples: 
      | FilterValue                                 | Task Type             | Instance Type |
      | Custom;Not Started;Preliminary PO Review;N  | Preliminary PO Review | PRELIM PO DEL |
      | Library;Not Started;Preliminary PO Review;N | Preliminary PO Review | PRELIM PO DEL |

  @PCA-4805SC01
  Scenario Outline: PCA-4805 Ability to view EXISTS link under Actions "<FilterValue>"
    Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "PO Review Work Queue" for "PRELIM PO DEL" Instance
    And Apply filters in My Task and Retrive Rule in DB "PROPOSALBASERULE" and "<FilterValue>"
    And select "RuleID"
    When clicks on "EXISTS" action link

    Examples: 
      | FilterValue                                   | Task Type             | Instance Type |
      | Custom;Not Started;Preliminary PO Review;SIM  | Preliminary PO Review | PRELIM PO DEL |
      | Library;Not Started;Preliminary PO Review;SIM | Preliminary PO Review | PRELIM PO DEL |
