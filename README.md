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
During the process of completing the exercise, I have encountered quality issues. One of these issues, that severely impacted my program, was the fact that I didn't state the names of the Thymeleaf HTML templates in their proper case-sensitive forms inside the HomeController class, the ProductController class, the HomeController unit test suite class, and the ProductControllerTest unit test suite class. As a result, during the Continuous Integration process, GitHub Actions ran my controller unit tests through Gradle and declared that they failed due to the HTML templates not being detected.
In order to fix this issue, my strategy was to modify each mention of the Thymeleaf HTML templates on those controller classes and unit test suites to state their proper case-sensitive names (for example, "createProduct" was changed to "CreateProduct", "homePage" was changed to "HomePage", and so on).
Another one of these

Other than that, through the JaCoCo test reports, I initially saw that my code coverage percentage was only 33%. I realized that this issue would result in a large portion of my code being left untested and unverified.
To fix this issue, I added more unit test suites that would properly test the functionalities of every aspect of the program, whether it would be the controller classes, the model class, the repository class, or even the service class. After successfully creating more unit test suites and updating already existing unit test suites to include more test cases, I have achieved 100% code coverage.

### Current Implementation of CI/CD Workflows
The project has already implemented CI/CD Workflows properly, following the principles stated in the module. For Continuous Integration aspect, I utilized GitHub actions to run tests on the GitHub repository after every single push to a single branch (ci.yml). I have also utilized OSSF Scorecard (scorecard.yml) and SonarCloud (sonarcloud.yml) in order to analyze my code and detect certain issues that might arise. For the Continuous Deployment aspect, I utilized Koyeb as the platform to deploy the program. Utilizing Koyeb's API access tokens, I was able to connect Koyeb to my GitHub repository, and now Koyeb properly deploys the web service build of the eshop program each time a push is made to the GitHub repository.


# Module 3 - OO Principles & Software Maintainability

## Reflection

### Explain what principles you apply to your project!
1. Single Responsibility Principle (SRP): This principle states that a class or module must only have one responsibility or functionality that it must commit to in order to enhance readability and maintainability. For this eshop project, I have implemented this principle by separating two specific controller classes, ProductController and CarController. Originally, the CarController class was in the ProductController.java file, extending the ProductController class. However, due to the CarController class having its own responsibilities that are separate from ProductController, I decided to remove the CarController class' extension towards the ProductController class and place it in a new file called CarController.java. This way, it is made obvious that CarController is a separate controller class that handles the functionalities on the Car web pages. This is different from the ProductController class, that handles functionalities on the Product web pages.
2. Interface Segregation Principle (ISP): This principle states that interfaces that are large and contain overly complex method combinations should be divided into smaller and more focused interfaces instead. I have applied this principle through the use of the CarService and ProductService interfaces. They contain methods that indicate certain functionalities and responsibilities that are appropriate to those specific services. Thus, the implementation of said interfaces would become much more apparent and comprehensible.
3. Dependency Inversion Principle (DIP): This principle states that high-level modules shouldn't depend on low-level modules and that they both should depend on abstraction (abstract classes and interfaces) instead. For this project, I have applied said principle when making it so that the ProductController class would declare the productService property with the ProductService interface, instead of the ProductServiceImpl class. I also made it so that the CarController class would declare the carService property with the CarService interface, instead of the CarServiceImpl class. These changes properly align with this principle. 

### Explain the advantages of applying SOLID principles to your project with examples.
1. Enhanced code readability: By following and applying SOLID principles into my eshop project, the code I have written became easier to understand and comprehend. For example, I can now tell that ProductController and CarController are controller classes for Product pages and Car pages respectively. Whereas before, the CarController class was put into the same file as the ProductController class, making it less obvious as to what the classes in ProductController.java file handle.
2. Better maintainability and testability: After applying SOLID principles into the project, classes now maintain their proper single-responsibility nature due to them being uncoupled, making testing easier and more manageable because changes made to one section won't directly affect other sections. For example, changes to the ProductController class won't affect CarController due to them being properly uncoupled.
3. Improved scalability and flexibility: Applying SOLID principles into my project helped improve code scalability and flexibility. This became apparent when I made sure that certain modules depend on interfaces instead of their implementation classes. For example, the carService property declared on the CarController class utilizes the CarService interface. This will be helpful if another implementation class of CarService must be made in the future and applied, because then this could be done without changing the existing code.

### Explain the disadvantages of not applying SOLID principles to your project with examples.
1. Debugging process would become even more difficult: Without applying any SOLID principles into my project, certain classes wouldn't maintain their single-responsibility nature and could have multiple responsibilities. This could be a massive issue that would make testing and debugging harder. For example, had I kept CarController as an extension of ProductController and an issue comes to light, it would be less obvious as to where it came from, as it could come from the CarController or even the ProductController.
2. Decreased scalability and flexibility: If I don't apply SOLD principles into my project, I'd have decreased scalability and flexibility. I wouldn't have applied proper interface segregation, resulting in large interfaces with a lot of unnecessary methods. For example, had I not properly separated the CarService and ProductService interfaces, the implementation classes would have to implement certain methods that are completely irrelevant to it.
3. Code may become too tightly coupled: Not applying SOLID principles to the project would result in the code not properly being uncoupled, thus changes to one section of the program's code could impact another section in unexpected ways. For example, if the CarController class directly depended on the CarServiceImpl class instead of the CarService interface, any direct modifications to the CarServiceImpl class could negatively impact the CarController class.


# Module 4 - Test-Driven Development & Refactoring

## Reflection

### Reflect based on Percival (2017) proposed self-reflective questions, whether this TDD flow is useful enough for you or not.
During this tutorial, I properly implemented a Test-Driven Development (TDD) workflow where I wrote tests for the bits of functionality I needed to add (RED), then wrote the functional code needed for said tests to pass (GREEN), and finally refactored the code when necessary to create a better code structure (REFACTOR). I feel like said TDD flow was very useful for me and helped me find a better and more efficient development structure.
1. Correctness: Applying Test-Driven Development helped me ensure correctness by allowing me to write various test suites that cover several areas of the functionality in order to make sure that the code functions properly. Writing these tests before the actual code implementation actually helped a lot with efficiency and proper clarification, as I can spot any mistakes made on my program based on the test case results and fix it accordingly in order to achieve the expected behavior.
2. Maintainability: Utilizing Test-Driven Development helped me improve maintainability significantly, this was done via proper refactoring. For example, replacing the hardcoded order status strings with the OrderStatus enum ensured better logic and more manageable maintenance when needed. Aside from that, creating the test suites before the proper code implementation also helped with improving maintainability due to future modifications now being more manageable as well.
3. Productive Workflow: Implementing the Test-Driven Development workflow helped me to develop the project in more efficient and productive ways. The process involved creating the tests first, then implementing the code, doing proper refactoring afterward, and repeating that process until every aspect has been completed. This process was very helpful in providing a clear path for how the development cycle for this eshop project should function.

### Reflect on whether your tests have successfully followed F.I.R.S.T. principle or not.
During this tutorial, I personally think that I have successfully followed the F.I.R.S.T. principles for the tests done in my eshop project.
1. Fast: The tests in this project ran smoothly and can be executed quickly without disrupting the workflow established. This was done by properly utilizing mocking to separate or isolate our unit tests from dependencies yet to be included.
2. Isolated/Independent: I have made sure that the unit tests in this project are isolated, they do not depend on one another nor do they directly change the state of certain functionalities. I have also made sure to mock the dependencies and apply @BeforeEach annotation on the test suites' setUp method to establish the required data before each test case begins running.
3. Repeatable: Every single test in this eshop project is able to run repeatedly with a consistent result (pass) every single time, so this principle has been applied correctly.
4. Self-Validating: This principle dictates that tests in this project must be self-validating. I have applied strict and clear assertions in each test case across many different test suites that will explicitly inform whether the test has failed or passed.
5. Thorough/Timely: The tests in this project have covered all happy paths and unhappy paths. Each requirement was understood, the methods and properties in need of implementing were fully realized, and the necessary happy and unhappy tests were created.