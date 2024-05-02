@api_sprint
Feature: As a librarian, I want to retrieve all users

  @us01
  Scenario: Retrieve all users from the API endpoint

    Given I logged Library api as a "librarian"
    And Accept header is "application/json"
    When I send GET request to "/get_all_users" endpoint
    Then status code should be 200
    And Response Content type is "application/json; charset=utf-8"
    And Each "id" field should not be null
    And Each "name" field should not be null

   ##User Story:  As a user, I want to search for a specific user by their id
   #        so that I can quickly find the information I need.
  @us02
  Scenario: Retrieve single user
    Given I logged Library api as a "librarian"
    And Accept header is "application/json"
    And Path param is "1"
    When I send GET request to "/get_user_by_id/{id}" endpoint
    Then status code should be 200
    And Response Content type is "application/json; charset=utf-8"
    And "id" field should be same with path param
    And following fields should not be null
      | full_name |
      | email     |
      | password  |

     ##User Story:As a librarian, I want to create a new book
  @us03
       Scenario: Create a new book API
         Given I logged Library api as a "librarian"
         And Accept header is "application/json"
         And Request Content Type header is "application/x-www-form-urlencoded"
         And I create a random "book" as request body
         When I send POST request to "/add_book" endpoint
         Then status code should be 200
         And Response Content type is "application/json; charset=utf-8"
         And the field value for "message" path should be equal to "The book has been created."
         And "book_id" field should not be null
  @us03.1 @db @ui
       Scenario: Create a new book all layers
         Given I logged Library api as a "librarian"
         And Accept header is "application/json"
         And Request Content Type header is "application/x-www-form-urlencoded"
         And I create a random "book" as request body
         And I logged in Library UI as "librarian"
         And I navigate to "Books" page
         When I send POST request to "/add_book" endpoint
         Then status code should be 200
         And Response Content type is "application/json; charset=utf-8"
         And the field value for "message" path should be equal to "The book has been created."
         And "book_id" field should not be null
         And UI, Database and API created book information must match

   ##User Story: As a librarian, I want to create a new user
  @us04
  Scenario: Create a new user API
    Given I logged Library api as a "librarian"
    And Accept header is "application/json"
    And Request Content Type header is "application/x-www-form-urlencoded"
    And I create a random "user" as request body
    When I send POST request to "/add_user" endpoint
    Then status code should be 200
    And Response Content type is "application/json; charset=utf-8"
    And the field value for "message" path should be equal to "The user has been created."
    And "user_id" field should not be null
  @us04.1 @db @ui
  Scenario: Create a new user all layers
    Given I logged Library api as a "librarian"
    And Accept header is "application/json"
    And Request Content Type header is "application/x-www-form-urlencoded"
    And I create a random "user" as request body
    When I send POST request to "/add_user" endpoint
    Then status code should be 200
    And Response Content type is "application/json; charset=utf-8"
    And the field value for "message" path should be equal to "The user has been created."
    And "user_id" field should not be null
    And created user information should match with Database
    And created user should be able to login Library UI
    And created user name should appear in Dashboard Page

   ##User Story: As a user, I want to view my own user information using the API
   # so that I can see what information is stored about me
  @us05
  Scenario Outline: Decode User
    Given I logged Library api with credentials "<email>" and "<password>"
    And Accept header is "application/json"
    And Request Content Type header is "application/x-www-form-urlencoded"
    And I send token information as request body
    When I send POST request to "/decode" endpoint
    Then status code should be 200
    And Response Content type is "application/json; charset=utf-8"
    And the field value for "user_group_id" path should be equal to "<user_group_id>"
    And the field value for "email" path should be equal to "<email>"
    And "full_name" field should not be null
    And "id" field should not be null


    Examples:
      | email                | password    | user_group_id |
      | student5@library     | libraryUser | 3             |
      | librarian10@library  | libraryUser | 2             |
      | student10@library    | libraryUser | 3             |
      | librarian13@library  | libraryUser | 2             |