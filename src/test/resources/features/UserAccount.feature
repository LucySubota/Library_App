Feature: User account tests
  As a user, I should be able to login with different users using their correct emails and passwords.
  when I login, I should be able to see username in the account username section.

  @all_accounts @ui
  Scenario Outline: Verify user information <email>
    Given I am on the login page
    When I login using "<email>" and "<password>"
    Then account holder name should be "<name>"
#TEST DATA
    @students
    Examples:
      | email             | password    | name            |
      | student27@library | libraryUser | Test Student 27 |
      | student28@library | libraryUser | Test Student 28 |
      | student29@library | libraryUser | Test Student 29 |
      | student30@library | libraryUser | Test Student 30 |

    @librarians
    Examples:
      | email               | password    | name              |
      | librarian13@library | libraryUser | Test Librarian 13 |
      | librarian14@library | libraryUser | Test Librarian 14 |
      | librarian15@library | libraryUser | Test Librarian 15 |
      | librarian16@library | libraryUser | Test Librarian 16 |
      | librarian17@library | libraryUser | Test Librarian 17 |