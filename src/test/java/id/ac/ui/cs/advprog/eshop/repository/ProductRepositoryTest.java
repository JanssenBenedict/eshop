package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindNonExistentProduct() {
        Product result = productRepository.findById("11111111-1111-1111-1111-111111111111");
        assertNull(result);
    }

    @Test
    void testEditProductPositive() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Some Object");
        product.setProductQuantity(1000);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Other Object");
        updatedProduct.setProductQuantity(10000);

        Product result = productRepository.update(updatedProduct);
        assertNotNull(result);
        assertEquals("Other Object", result.getProductName());
        assertEquals(10000, result.getProductQuantity());
    }

    @Test
    void testEditProductNegative() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("11111111-1111-1111-1111-111111111111");
        updatedProduct.setProductName("Unmade Object");
        updatedProduct.setProductQuantity(1);

        Product result = productRepository.update(updatedProduct);
        assertNull(result);
    }

    @Test
    void testDeleteProductPositive() {
        Product product = new Product();
        product.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product.setProductName("Misc. Object");
        product.setProductQuantity(1);
        productRepository.create(product);

        productRepository.delete("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        assertNull(productRepository.findById("a0f9de46-90b1-437d-a0bf-d0821dde9096"));
    }

    @Test
    void testDeleteProductNegative() {
        assertDoesNotThrow(() -> productRepository.delete("11111111-1111-1111-1111-111111111111"));
    }

    @Test
    void testCreateProductWithNullOrEmptyName() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertThrows(IllegalArgumentException.class, () -> product.setProductName(null));
        assertThrows(IllegalArgumentException.class, () -> product.setProductName("  "));
    }

    @Test
    void testCreateProductWithZeroOrNegativeQuantity() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("A Product");
        assertThrows(IllegalArgumentException.class, () -> product.setProductQuantity(-10));
    }

    @Test
    void testEditProductWithNullOrEmptyName() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("A Product");
        product.setProductQuantity(100);
        productRepository.create(product);

        assertThrows(IllegalArgumentException.class, () -> product.setProductName(null));
        assertThrows(IllegalArgumentException.class, () -> product.setProductName("  "));
    }

    @Test
    void testEditProductWithZeroOrNegativeQuantity() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("A Product");
        product.setProductQuantity(100);
        productRepository.create(product);

        assertThrows(IllegalArgumentException.class, () -> product.setProductQuantity(0));
    }
}