package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class DeleteProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest(ChromeDriver driver) {
        baseUrl = String.format("%s:%d/product/create", testBaseUrl, serverPort);

        driver.get(baseUrl);
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.sendKeys("Product To Be Deleted");
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.sendKeys("5555");
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        baseUrl = String.format("%s:%d/product/list", testBaseUrl, serverPort);
    }

    @Test
    void deleteProduct_isSuccessful(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);

        boolean isProductNamePresent = driver.getPageSource().contains("Product To Be Deleted");
        boolean isProductQuantityPresent = driver.getPageSource().contains("5555");

        assertTrue(isProductNamePresent);
        assertTrue(isProductQuantityPresent);

        WebElement deleteButton = driver.findElement(By.cssSelector("a.btn-danger"));
        deleteButton.click();

        driver.get(String.format("%s:%d/product/list", testBaseUrl, serverPort));
        isProductNamePresent = driver.getPageSource().contains("Product To Be Deleted");
        isProductQuantityPresent = driver.getPageSource().contains("5555");

        assertFalse(isProductNamePresent);
        assertFalse(isProductQuantityPresent);
    }
}
