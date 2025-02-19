- Name: Janssen Benedict
- Class: Pemrograman Lanjut A
- NPM: 2306152102

### Deployment Link
Web URL (Koyeb): special-gerianne-janssenbenedict-f01eda63.koyeb.app/

# Module 1 - Coding Standards

## Reflection 1

I have successfully implemented two new features using Spring Boot and applied the appropriate clean code principles and secure coding practices to my code.

### Applied Clean Code Principles

1. Meaningful Names: I have made sure to avoid using variable, class, and method names that don't properly indicate their true purposes. I chose names that are pronounceable and easy to comprehend. For example, each class' name accurately represents their purpose, such as ProductController, ProductRepository, ProductService, and others. The methods also use meaningful names that properly show their intent, like createProductPage, productListPage, editProductPost, and more.
2. Function: Each method or function created does only one thing well. I also made sure their names are short and correspond to their specific purposes. For example, the ProductService and ProductServiceImpl classes have separate methods for creating a product (create), updating a product's properties (update), deleting a product (delete), finding all products (findAll), and finding a specific product through its ID (findById). Each method does one specific thing and they do it well.
3. Objects and Data Structure: Certain properties or variables, such as the properties in the Product class or model, are kept private, so they won't be directly exposed to other users. I have also utilized data structures in the code, such as the productData list on the ProductRepository class to store all the created Product objects.
4. Error Handling: I have implemented proper error handling through the usage of Jakarta Bean Validation in the Product model class and the ProductController class. I have also made sure that the HTML templates properly display messages to the users regarding what error occurred due to their inputs.
5. Comments: As of right now, due to the simplistic code structure and implementation, as well as the variables, classes, and methods already using meaningful names that already describe their purposes well, comments are currently excluded from this current tutorial.

### Applied Secure Coding Practices

1. Output Data Encoding: The HTML templates I made utilize Thymeleaf, hence they automatically escape outputs properly and prevent certain vulnerabilities, such as XSS (cross-site scripting) attacks.
2. Input Data Validation: The input data has already been properly validated in order to not encounter certain vulnerabilities, such as XSS (cross-site scripting) attacks or SQL code injections.

## Reflection 2

I have successfully implemented unit tests and functional tests that are used to validate certain actions in the program, such as creating products, editing product properties, deleting products, and properly accessing certain pages along with validating their contents.

### My Thoughts after Unit Test Creation
1. After writing my own unit tests, I feel more confident in my code's integrity, as said code has passed the unit tests that I have created.
2. The amount of unit tests there should be in a class depends on the amount of functionalities provided by said class. Each functionality should have at least one proper unit test, which can be divided into a positive test case and a negative test case whenever possible.
3. To make sure that our unit tests are enough to verify our program, we must create tests for many aspects of the program, testing individual functions and seeing if they can handle both positive and negative test cases.
4. If my code has 100% coverage, it does not necessarily mean that my code has no bugs or errors, but it does provide a good indication on the scope of my code that has been tested.

### Creating a New Functional Test Suite
If I were to create a new functional test suite that verifies the number of items in the product list through a new Java class similar to the prior functional test suites with the same setup procedures and instance variables, it would result in unnecessary code duplication.
The code of the new functional test suite will indeed reduce the code quality due to the duplication of the code making my program repetitive or redundant.
To resolve this, the duplication of my code must be eliminated. Instead, I can write a new base functional test suite with the same setup procedures and instance variables, then have the functional test suits that utilize those procedures and variables extend that base Java class.

# Module 2 - CI/CD & DevOps

## Reflection

### Code Quality Issues I Fixed during the Exercise and My Strategies on Fixing Them
During the process of completing the exercise, I encountered a few code quality issues. One of these issues was the fact that I didn't state the names of the Thymeleaf HTML templates in their proper case-sensitive forms inside the HomeController class, the ProductController class, the HomeController unit test suite class, and the ProductControllerTest unit test suite class. As a result, during the Continuous Integration process, GitHub Actions ran my controller unit tests through Gradle and declared that they failed due to the HTML templates not being detected.
In order to fix this issue, my strategy was to modify each mention of the Thymeleaf HTML templates on those controller classes and unit test suites to state their proper case-sensitive names (for example, "createProduct" was changed to "CreateProduct", "homePage" was changed to "HomePage", and so on).

Other than that, through the JaCoCo test reports, I initially saw that my code coverage percentage was only 33%. I realized that this issue would result in a large portion of my code being left untested and unverified.
To fix this issue, I added more unit test suites that would properly test the functionalities of every aspect of the program, whether it would be the controller classes, the model class, the repository class, or even the service class. After successfully creating more unit test suites and updating already existing unit test suites to include more test cases, I have achieved 100% code coverage.

### Current Implementation of CI/CD Workflows
The project has already implemented CI/CD Workflows properly, following the principles stated in the module. For Continuous Integration aspect, I utilized GitHub actions to run tests on the GitHub repository after every single push to a single branch (ci.yml). I have also utilized OSSF Scorecard (scorecard.yml) and SonarCloud (sonarcloud.yml) in order to analyze my code and detect certain issues that might arise. For the Continuous Deployment aspect, I utilized Koyeb as the platform to deploy the program. Utilizing Koyeb's API access tokens, I was able to connect Koyeb to my GitHub repository, and now Koyeb properly deploys the web service build of the eshop program each time a push is made to the GitHub repository.