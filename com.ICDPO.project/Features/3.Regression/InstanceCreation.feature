#Author: trinath.kumar@cotiviti.com
@Instance @Regression
Feature: Instance Creation validations
  Creating the new instance for Rules

  @InstanceWithOutRequest @Sanity @PCA-3384Demo @PCA-3742 @PCA-3412 @PCA-3372 @PCA-3454 @PCA-3375 @PCA-3455 @PCA-3412 @PCA-3714 @PCA-3727 @PCA-3733 @PCA-3742 @PCA-3711 @PCA-3646 @PCA-3970 @PCA-3605 @PCA-4167 @PCA-3949 @PCA-3718 @PCA-3375 @PCA-3404
  Scenario Outline: Creation Instance-->Admin MD Scrub-->MD Review WorkQueue
    PCA-3727 PCA 2529_Complete 'Review CPM assignment"
    PCA-3714 PCA 2519_Ability to Complete "DC Assignments review"
    PCA-3742 PCA 2534_ICD 10 :MD Assignments-- Review & navigate Complete Obsolete Payer Review

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
    Then click on "Request Impact Analysis" and validate "Impact Instance"
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
    When user click on "PO Review Work Queue" for "FINAL MD DEL-Random" Instance
    And select "RuleID-Random"

    Examples: 
      | NewInstanceName | Admin OPS    | Admin MD     | GlobalTasks                                                         | Users        | Tasks                                       |
      | Random          | iht_ittest01 | iht_ittest01 | Review Obsolete Payers;Review PO Assignments;Review CPM Assignments | iht_ittest01 | Review UI Assignments;Review DC Assignments |

  @PCA-3392 @PCA-3393
  Scenario: Create Instance-Cancel without any changes
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen
    And create instance with "Random" and "iht_ittest01" "iht_ittest01" "Cancel-Yes"
    And create instance with "Random" and "iht_ittest01" "iht_ittest01" "Cancel-No"

  #*************************************************************************************************************************************************************************************************************************************************************************************
  @PCA-3730
  Scenario Outline: PCA 2586_Ability to Edit an Already created Update Instance
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen

    #Then validate Admin rule review value "Admin" "PRELIM MD DEL" "Task" ""
    #And The user had updated "<Update Instance Fields>" field value as "<Description>"
    #And The user clicks on "Save" button
    #And All the updates on "<Update Instance Fields>" should be saved successfully in DB "<Query>"
    Examples: 
      | Update Instance Fields     |
      | Effective Date;Description |

  #*************************************************************************************************************************************************************************************************************************************************************************************
  @PCA-4024Test @PCA-4099
  Scenario Outline: PCA 2662_Ability to view data Delete tab in Same/Sim mapping
    PCA 2767_ICD 10 : Translation of Same-Sim document: Ability to view 'Revised' tab

    Given user retrives table data of "Deleted" from Same Sim file and Validate with DB for "<Instance>"
    And user retrives table data of "Revised" from Same Sim file and Validate with DB for "<Instance>"
    And user retrives table data of "New" from Same Sim file and Validate with DB for "<Instance>"

    Examples: 
      | NewInstanceName |
      | TestAutoIV641       |

  @PCA-3952 @Pass @PCA-4099
  Scenario Outline: PCA 2660_ICD 10 : Ability to view Same Sim Mappings in IU Application
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen
    Then validate Admin rule review value "Admin" "FINAL PO DEL" "Task" ""
    And user navigate to IU "Same/Sim Mapping" Instances Screen
    And user can able to see the following "<Tabs>"

    Examples: 
      | Tabs                        |
      | Deleted;New;Revised;Similar |

  @PCA-4507
  Scenario Outline: PCA 2661_ Ability to Export Same Sim Mappingss
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen

    And user navigate to IU "ICD10-Admin" Instances Screen
    Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
    And user navigate to IU "Same/Sim Mapping" Instances Screen
    Then click on "Export to Excel" and validate "Global Assignments"
    Examples: 
      | NewInstanceName | Tabs    |
      | TestAutoIV70     | Revised |

  @PCA-3428
  Scenario Outline: PCA 2912_ Impact analysis for ICD(Intial Rune) : Idnetification of Rules
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen

    #Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
    #Then click on "Request Impact Analysis" and validate "Global Assignments"
    Examples: 
      | NewInstanceName | Tabs    |
      | TestAutoIV659   | Revised |

  @PCA-4507
  Scenario Outline: PCA 2661_ Ability to Export Same Sim Mappings
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen

    #Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
    #And user navigate to IU "Same/Sim Mapping" Instances Screen
    #Then click on "Export to Excel" and validate "Global Assignments"
    Examples: 
      | NewInstanceName | Tabs    |
      | ICD-22          | Revised |

  @PCA-3428
  Scenario Outline: PCA 2912_ Impact analysis for ICD(Intial Rune) : Idnetification of Rules
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen

    #Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
    #Then click on "Request Impact Analysis" and validate "Global Assignments"
    Examples: 
      | NewInstanceName | Tabs    |
      | TestAutoIV659   | Revised |

  @PCA-3419
  Scenario Outline: PCA 3209_Ability to export the Same Sim Data to load in tables
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen

    #Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
    #When user load Same Sim data through Database "G43.A1"
    #Then click on "Request Impact Analysis" and validate "Impact Instance"
    Examples: 
      | NewInstanceName | Tabs    |
      | TestAutoIV988   | Revised |

  @PCA-4094
  Scenario: PCA 3866_Validation of pending 'Proposal Generation' scenarios
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    When user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    When user click on "Admin PO Scrub Review" for "Admin Scrub" Instance
    And user click on "GroupFilter" Option as ""
    Then validate pending 'Proposal Generation'
    And user clicks on the "Admin View" button
    Then validate sorting and filtering functionality

  @PCA-5096
  Scenario Outline: Ability to 'Release' the rules for work queue - Multiple Admin MDs
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen
    And create instance with "<NewInstanceName>" and "<Admin OPS>" "<Admin MD>" "Operations"
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
    Then complete "Review UI Assignments" and assign "iht_ittest01" to perform "Cancel-Yes"
    Then complete "Review UI Assignments" and assign "iht_ittest01" to perform ""
    Then complete "Review DC Assignments" and assign "Primary PO,Final PO" to perform "Cancel-Yes"
    Then complete "Review DC Assignments" and assign "iht_ittest01" to perform ""
    And user navigate to IU "ICD10-Admin" Instances Screen
    When user load Same Sim data through Database "G43.A1"
    Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
    Then click on "Request Impact Analysis" and validate "Impact Instance"
    And user navigate to IU "ICD10-MyTasks" Instances Screen
    #When user click on "Admin PO Scrub Review" for "Admin Scrub-Random" Instance
    #And user switch to "Admin Scrub" Page
    #And user clicks on the "Admin View" button
    #And assign with three different AdminMDs Users to the rules in Admin MD scrub
    #And user clicks on the "Release" button
    #And user switch to "Task Mangement" Page
    #And user navigate to IU "ICD10-Admin" Instances Screen
    #Then validate Admin rule review value "Admin" "<NewInstanceName>" "Task" ""
    #And user navigate to IU "View All Tasks" Instances Screen
    #Then Admin MDs user which we assigned should be displayed

    #Given user logs into "QA" with "iht_ittest02" into Interpretive Update Application
    #And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    #And user navigate to IU "ICD10-MyTasks" Instances Screen
    #When user click on "Admin PO Scrub Review" for "Admin Scrub-Random" Instance
    #Given user logs into "QA" with "iht_ittest03" into Interpretive Update Application
    #And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    #And user navigate to IU "ICD10-MyTasks" Instances Screen
    #When user click on "Admin PO Scrub Review" for "Admin Scrub-Random" Instance
    Examples: 
      | NewInstanceName | Admin OPS    | Admin MD     | GlobalTasks                                                         | Users        | Tasks                                       |
      | Random          | iht_ittest01 | iht_ittest01 | Review Obsolete Payers;Review PO Assignments;Review CPM Assignments | iht_ittest01 | Review UI Assignments;Review DC Assignments |

  @PCA-1111
  Scenario Outline: PCA-1111-Create Instance for Filing
    Given user logs into "QA" with "iht_ittest01" into Interpretive Update Application
    And user navigate to IU "ICD-Interpretive Rule Update" Instances Screen
    And user navigate to IU "ICD10-Admin" Instances Screen
    And create instance with "<NewInstanceName>" and "<Admin OPS>" "<Admin MD>" "Operations"
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
    Then complete "Review UI Assignments" and assign "iht_ittest01" to perform "Cancel-Yes"
    Then complete "Review UI Assignments" and assign "iht_ittest01" to perform ""
    Then complete "Review DC Assignments" and assign "Primary PO,Final PO" to perform "Cancel-Yes"
    Then complete "Review DC Assignments" and assign "iht_ittest01" to perform ""
    And user navigate to IU "ICD10-Admin" Instances Screen

    Examples: 
      | NewInstanceName | Admin OPS    | Admin MD     | GlobalTasks                                                         | Users        | Tasks                                       |
      | Random          | iht_ittest01 | iht_ittest01 | Review Obsolete Payers;Review MD Assignments;Review CPM Assignments | iht_ittest01 | Review UI Assignments;Review DC Assignments |
