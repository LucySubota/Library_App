Feature: Books feature

  @db @US3 @ui
  #US:  As a data consumer, I want UI and DB book categories are match.
  Scenario: verify book categories with UI
    Given the user logged in as "librarian"
    When the user navigates to "Books" page
    And the user gets all book categories in webpage
    Then user should be able to see correct categories

  @db @US4 @ui
  #US: As a data consumer, I want UI and DB book information are match.
  Scenario: Verify book information with db
    Given the user logged in as "librarian"
    And the user navigates to "Books" page
    When user searches for book "Clean Code"
    Then books information must match the database for "Clean Code"

  @db @US6 @ui
  #US: As a librarian, I should be able to add new book into library
  Scenario Outline: Verify added book is matching with DB
    Given the user logged in as "librarian"
    And the user navigates to "Books" page
    When the librarian click to add book
    And the librarian enter book name "<Book Name>"
    When the librarian enter ISBN "<ISBN>"
    And the librarian enter year "<Year>"
    When the librarian enter author "<Author>"
    And the librarian choose the book category "<Book Category>"
    And the librarian click to save changes
    Then verify "The book has been created." message is displayed
    And verify "<Book Name>" information must match with DB
    Examples:
      | Book Name      | ISBN     | Year | Author          | Book Category        |
      | Hello, family! | 09112500 | 2021 | Robert Parson   | Drama                |
      | I knowJava     | 10112666 | 2021 | Ashley Smith    | Action and Adventure |
      | Cydeo is Cool! | 11112777 | 2006 | Daniel Connelly | Short Story          |

  @db @US7 @ui
  #US: As a students, I should be able to borrow book
  Scenario: Student borrow new book
    Given the user logged in as "student"
    When the user navigates to "Books" page
    And the user searches book name called "The Mermaids Singing"
    When the user clicks Borrow Book
    Then verify that book is shown in "Borrowing Books” page
    And verify logged student has same book in database