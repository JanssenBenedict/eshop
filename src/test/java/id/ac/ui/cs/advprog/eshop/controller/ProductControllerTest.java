package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("A Product");
        product.setProductQuantity(100);
    }

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProductPostPositive() throws Exception {
        mockMvc.perform(post("/product/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("productName", "Another Product")
                        .param("productQuantity", "1000")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(service, times(1)).create(Mockito.any(Product.class));
    }

    @Test
    void testCreateProductPostNegative() throws Exception {
        mockMvc.perform(post("/product/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("productName", "")
                        .param("productQuantity", "0")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("createProduct"))
                .andExpect(model().attributeHasFieldErrors("product", "productName", "productQuantity"));

        verify(service, never()).create(Mockito.any(Product.class));
    }

    @Test
    void testEditProductPageProductFound() throws Exception {
        given(service.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).willReturn(product);

        mockMvc.perform(get("/product/edit/eb558e9f-1c39-460e-8860-71af6af63bd6"))
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeExists("product"))
                .andExpect(model().attribute("product", product));
    }

    @Test
    void testEditProductPageProductNotFound() throws Exception {
        given(service.findById("11111111-1111-1111-1111-111111111111")).willReturn(null);

        mockMvc.perform(get("/product/edit/11111111-1111-1111-1111-111111111111"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void testEditProductPostPositive() throws Exception {
        given(service.update(Mockito.any(Product.class))).willReturn(product);

        mockMvc.perform(post("/product/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("productId", "eb558e9f-1c39-460e-8860-71af6af63bd6")
                        .param("productName", "A Better Name")
                        .param("productQuantity", "101")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(service).update(productCaptor.capture());

        Product captured = productCaptor.getValue();
        org.assertj.core.api.Assertions.assertThat(captured.getProductId()).isEqualTo("eb558e9f-1c39-460e-8860-71af6af63bd6");
        org.assertj.core.api.Assertions.assertThat(captured.getProductName()).isEqualTo("A Better Name");
        org.assertj.core.api.Assertions.assertThat(captured.getProductQuantity()).isEqualTo(101);
    }

    @Test
    void testEditProductPostNegative() throws Exception {
        mockMvc.perform(post("/product/edit")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("productId", "11111111-1111-1111-1111-111111111111")
                        .param("productName", "")
                        .param("productQuantity", "0")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("editProduct"))
                .andExpect(model().attributeHasFieldErrors("product", "productName", "productQuantity"));

        verify(service, never()).update(Mockito.any(Product.class));
    }

    @Test
    void testDeleteProductFound() throws Exception {
        given(service.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).willReturn(product);

        mockMvc.perform(get("/product/delete/eb558e9f-1c39-460e-8860-71af6af63bd6"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(service, times(1)).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testDeleteProductNotFound() throws Exception {
        given(service.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).willReturn(null);

        mockMvc.perform(get("/product/delete/eb558e9f-1c39-460e-8860-71af6af63bd6"))
                .andExpect(status().is4xxClientError());

        verify(service, never()).delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
    }

    @Test
    void testProductListPage() throws Exception {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd7");
        product1.setProductName("First Product");
        product1.setProductQuantity(111);

        Product product2 = new Product();
        product2.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd8");
        product2.setProductName("Second Product");
        product2.setProductQuantity(222);

        given(service.findAll()).willReturn(Arrays.asList(product1, product2));

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attributeExists("products"))
                .andExpect(model().attribute("products", hasSize(2)))
                .andExpect(model().attribute("products", is(Arrays.asList(product1, product2))));
    }
}
