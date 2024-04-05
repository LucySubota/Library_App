@US2
Feature: As a librarian, I want to know borrowed books number
  @db
  Scenario: verify the total amount of borrowed books
    Given the user logged in as "librarian"
    When the librarian gets borrowed books number
    Then borrowed books number information must match with DB
