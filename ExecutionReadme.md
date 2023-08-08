## Run Instructions

1. Automated API Testing Project with Java, Cucumber and RestAssured
   - Execution of Tests:
      Use a build tool like Maven to run the tests. In the terminal, go to your project directory and run:
      `mvn test`

## About the project

1. Performed 7 tests on the "Books" APIs:

   - List books (List all books with GET method).

   - Add a new book (Adds a new book with the POST method).

   - Search book by ID (Search a book by a specific ID with the GET/id method).

   - Update book by ID (Updates a certain book by ID with the PUT/id method).

   - Delete book by ID (Deletes a book record by ID with the DELETE/id method) // NOTE: The delete method is not working on the request, it gives success 200 but does not delete).

   - Search book by Invalid ID - Error (Search for an inexistent book id with the GET/id method).

   - Add book with invalid publication date - Error (Tries to change a book with invalid publication date, with the PUT/id method).

   - Note: I had performed a delete test with id without registration, but the method was successful even without registration, so I removed the test.