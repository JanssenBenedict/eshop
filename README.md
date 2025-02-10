Name: Janssen Benedict
Class: Pemrograman Lanjut A
NPM: 2306152102

# Reflection 1

I have successfully implemented two new features using Spring Boot and applied the appropriate clean code principles and secure coding practices to my code.

## Applied Clean Code Principles

1. Meaningful Names: I have made sure to avoid using variable, class, and method names that don't properly indicate their true purposes. I chose names that are pronounceable and easy to comprehend. For example, each class' name accurately represents their purpose, such as ProductController, ProductRepository, ProductService, and others. The methods also use meaningful names that properly show their intent, like createProductPage, productListPage, editProductPost, and more.
2. Function: Each method or function created does only one thing well. I also made sure their names are short and correspond to their specific purposes. For example, the ProductService and ProductServiceImpl classes have separate methods for creating a product (create), updating a product's properties (update), deleting a product (delete), finding all products (findAll), and finding a specific product through its ID (findById). Each method does one specific thing and they do it well.
3. Objects and Data Structure: Certain properties or variables, such as the properties in the Product class or model, are kept private, so they won't be directly exposed to other users.

## Applied Secure Coding Practices

1. Output Data Encoding: The HTML templates I made utilize Thymeleaf, hence they automatically escape inputs from users properly and prevent certain vulnerabilities, such as XSS attacks.