@login
Feature: Login feature

  Background:
    Given I am on the login page

  @librarian
  Scenario: Login as a librarian
    When I login as a librarian
    Then dashboard should be displayed

  @student
  Scenario: Login as a student
    When I login as a student
    Then books should be displayed

    #Login with parameters
  @librarianParam
  Scenario: Login as librarian 22
    When I enter username "librarian22@library"
    And I enter password "libraryUser"
    And click the sign in button
    And there should be 86 users
    Then dashboard should be displayed
      #number can be whatever you have there

  @db
  Scenario Outline: Login with valid credentials <email>
    Given the user logged in  "<email>" and "<password>"
    When user gets username from user fields
    Then the username should be same with database
    Examples:
      | email               | password    |
      | librarian22@library | libraryUser |
      | librarian27@library | libraryUser |
      | student5@library    | libraryUser |
      | student6@library    | libraryUser |