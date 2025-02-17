package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductServiceImpl service;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("A Product");
        product.setProductQuantity(100);
    }

    @Test
    void testCreate() {
        service.create(product);

        verify(repository, times(1)).create(product);
    }

    @Test
    void testUpdate() {
        when(repository.update(product)).thenReturn(product);

        Product updatedProduct = service.update(product);

        verify(repository, times(1)).update(product);
        assertThat(updatedProduct).isEqualTo(product);
    }

    @Test
    void testDelete() {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";
        service.delete(productId);

        verify(repository, times(1)).delete(productId);
    }

    @Test
    void testFindAll() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        product1.setProductName("Product 1");
        product1.setProductQuantity(111);

        Product product2 = new Product();
        product2.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd8");
        product2.setProductName("Product 2");
        product2.setProductQuantity(222);

        List<Product> productList = new ArrayList<>();
        productList.add(product1);
        productList.add(product2);
        Iterator<Product> mockedIterator = productList.iterator();

        when(repository.findAll()).thenReturn(mockedIterator);

        List<Product> result = service.findAll();

        verify(repository, times(1)).findAll();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(product1, product2);
    }

    @Test
    void testFindByIdPositive() {
        when(repository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(product);

        Product found = service.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        verify(repository, times(1)).findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertThat(found).isNotNull();
        assertThat(found.getProductId()).isEqualTo("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertThat(found.getProductName()).isEqualTo("A Product");
    }

    @Test
    void testFindByIdNegative() {
        when(repository.findById(anyString())).thenReturn(null);

        Product found = service.findById("11111111-1111-1111-1111-111111111111");

        verify(repository, times(1)).findById("11111111-1111-1111-1111-111111111111");
        assertThat(found).isNull();
    }
}