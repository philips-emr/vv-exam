Feature: Books API Test

  Scenario: List books
    Given that I have the Books API URI
    When I make a GET request to list the books
    Then the answer must contain a list of books

  Scenario: Add a new book
    Given that I have the Books API URI
    And a new book with the necessary information
    When I make a POST request to add the book
    Then the response must return the code 200

  Scenario: Search book by ID
    Given that I have the Books API URI
    And an existing book ID 42
    When I make a GET request to fetch the book by ID
    Then the answer must contain the corresponding book information

  Scenario: Update book by ID
    Given that I have the Books API URI
    And an existing book ID 42
    And a new book with updated information
    When I make a PUT request to update the book
    Then the response must return the code 200
    And the response must contain the updated book information

  Scenario: Delete book by ID
    Given that I have the Books API URI
    And an existing book ID 200
    When I make a DELETE request to delete the book
    Then the response must return the code 200

  Scenario: Search book by invalid ID - Error
    Given that I have the Books API URI
    When I make a GET request to fetch the book with invalid ID
    Then the response must return the code 404
    And the response must contain an error message 404, 'Not Found'

  Scenario: Add book with invalid publication date - Error
    Given that I have the Books API URI
    When I add a new book with invalid publication date
    Then the response must return the code 400
    And the response must contain an error message 400, 'One or more validation errors occurred.'



#CENARIO COM ERRO DE CONVERTER pageCount
#  Scenario Outline: Update book by ID
#    Given that I have the Books API URI
#    And an existing book ID 42
#    And a new book with updated information
#      | title      | description      | pageCount      | excerpt      |
#      | <newTitle> | <newDescription> | <newPageCount> | <newExcerpt> |
#    When I make a PUT request to update the book
#    Then the response must return the code 200
#    And the response must contain the updated book information
#      | title      | description      | pageCount      | excerpt      |
#      | <newTitle> | <newDescription> | <newPageCount> | <newExcerpt> |
#
#    Examples:
#      | bookId | newTitle    | newDescription        | newPageCount | newExcerpt    | expectedStatusCode |
#      | 1      | New Title 1 | Updated Description 1 | 0            | New Excerpt 1 | 200                |
#      | 2      | New Title 2 | Updated Description 2 | 0            | New Excerpt 2 | 200                |