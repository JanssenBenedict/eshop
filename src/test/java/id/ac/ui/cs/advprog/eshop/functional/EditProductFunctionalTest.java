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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class EditProductFunctionalTest {

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
        nameInput.sendKeys("A Product");
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.sendKeys("100");
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        baseUrl = String.format("%s:%d/product/list", testBaseUrl, serverPort);
    }

    @Test
    void pageTitle_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);
        WebElement editButton = driver.findElement(By.cssSelector("a.btn-warning"));
        editButton.click();
        String pageTitle = driver.getTitle();

        assertEquals("Edit Product", pageTitle);
    }

    @Test
    void titleMessage_editProductPage_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);
        WebElement editButton = driver.findElement(By.cssSelector("a.btn-warning"));
        editButton.click();
        String headerText = driver.findElement(By.tagName("h3")).getText();

        assertEquals("Edit Product", headerText);
    }

    @Test
    void editProduct_isSuccessful(ChromeDriver driver) throws Exception {
        driver.get(baseUrl);
        WebElement editButton = driver.findElement(By.cssSelector("a.btn-warning"));
        editButton.click();

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys("A Product Again");

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys("200");

        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        assertEquals(testBaseUrl + ":" + serverPort + "/product/list", currentUrl);
    }
}
