Feature: Book Category
@db @US3
  #US:  As a data consumer, I want UI and DB book categories are match.
  Scenario: verify book categories with UI
    Given the user logged in as "librarian"
    When the user navigates to "Books" page
    And the user gets all book categories in webpage
    Then user should be able to see correct categories
@db @US4
  #US: As a data consumer, I want UI and DB book information are match.
  Scenario: Verify book information with db
    Given the user logged in as "librarian"
    And the user navigates to "Books" page
    When user searches for book "Clean Code"
    Then books information must match the database for "Clean Code"