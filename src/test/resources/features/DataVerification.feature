@US1
Feature: As a data consumer, I want the user information are stored in mySql DB correctly in users table.

  @db
  Scenario: verify users have unique IDs
    When Execute query to get all IDs from users
    Then verify all users have unique ID

  @db
  Scenario: verify users table columns
    When Execute query to get all columns
    Then verify the below columns are listed in result
      | id            |
      | full_name     |
      | email         |
      | password      |
      | user_group_id |
      | image         |
      | extra_data    |
      | status        |
      | is_admin      |
      | start_date    |
      | end_date      |
      | address       |

    @US5 @db
  #US: As a data consumer, I want to know genre of books are being borrowed the most
  Scenario: verify the the common book genre that’s being borrowed
    When I execute query to find most popular book genre
    Then I verify "Humor" is the most popular book genre.