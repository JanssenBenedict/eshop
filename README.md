- Name: Janssen Benedict
- Class: Pemrograman Lanjut A
- NPM: 2306152102

# Reflection 1

I have successfully implemented two new features using Spring Boot and applied the appropriate clean code principles and secure coding practices to my code.

## Applied Clean Code Principles

1. Meaningful Names: I have made sure to avoid using variable, class, and method names that don't properly indicate their true purposes. I chose names that are pronounceable and easy to comprehend. For example, each class' name accurately represents their purpose, such as ProductController, ProductRepository, ProductService, and others. The methods also use meaningful names that properly show their intent, like createProductPage, productListPage, editProductPost, and more.
2. Function: Each method or function created does only one thing well. I also made sure their names are short and correspond to their specific purposes. For example, the ProductService and ProductServiceImpl classes have separate methods for creating a product (create), updating a product's properties (update), deleting a product (delete), finding all products (findAll), and finding a specific product through its ID (findById). Each method does one specific thing and they do it well.
3. Objects and Data Structure: Certain properties or variables, such as the properties in the Product class or model, are kept private, so they won't be directly exposed to other users. I have also utilized data structures in the code, such as the productData list on the ProductRepository class to store all the created Product objects.
4. Error Handling: I have implemented proper error handling through the usage of Jakarta Bean Validation in the Product model class and the ProductController class. I have also made sure that the HTML templates properly display messages to the users regarding what error occurred due to their inputs.
5. Comments: As of right now, due to the simplistic code structure and implementation, as well as the variables, classes, and methods already using meaningful names that already describe their purposes well, comments are currently excluded from this current tutorial.

## Applied Secure Coding Practices

1. Output Data Encoding: The HTML templates I made utilize Thymeleaf, hence they automatically escape outputs properly and prevent certain vulnerabilities, such as XSS (cross-site scripting) attacks.
2. Input Data Validation: The input data has already been properly validated in order to not encounter certain vulnerabilities, such as XSS (cross-site scripting) attacks or SQL code injections.

# Reflection 2

I have successfully implemented unit tests and functional tests that are used to validate certain actions in the program, such as creating products, editing product properties, deleting products, and properly accessing certain pages along with validating their contents.

## My Thoughts after Unit Test Creation
1. After writing my own unit tests, I feel more confident in my code's integrity, as said code has passed the unit tests that I have created.
2. The amount of unit tests there should be in a class depends on the amount of functionalities provided by said class. Each functionality should have at least one proper unit test, which can be divided into a positive test case and a negative test case whenever possible.
3. To make sure that our unit tests are enough to verify our program, we must create tests for many aspects of the program, testing individual functions and seeing if they can handle both positive and negative test cases.
4. If my code has 100% coverage, it does not necessarily mean that my code has no bugs or errors, but it does provide a good indication on the scope of my code that has been tested.

## Creating a New Functional Test Suite
If I were to create a new functional test suite that verifies the number of items in the product list through a new Java class similar to the prior functional test suites with the same setup procedures and instance variables, it would result in unnecessary code duplication.
The code of the new functional test suite will indeed reduce the code quality due to the duplication of the code making my program repetitive or redundant.
To resolve this, the duplication of my code must be eliminated. Instead, I can write a new base functional test suite with the same setup procedures and instance variables, then have the functional test suits that utilize those procedures and variables extend that base Java class.