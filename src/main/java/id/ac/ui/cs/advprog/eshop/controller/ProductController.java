package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        try {
            productValidation(product);
            service.create(product);
            return "redirect:list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "createProduct";
        }
    }

    @GetMapping("/edit/{productId}")
    public String editProductPage(@PathVariable String productId, Model model) {
        Product product = service.findById(productId);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The product cannot be found");
        }
        model.addAttribute("product", product);
        return "editProduct";
    }

    @PostMapping("/edit")
    public String editProductPost(@ModelAttribute Product product, Model model) {
        try {
            productValidation(product);
            service.update(product);
            return "redirect:/product/list";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "editProduct";
        }
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }

    @GetMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable String productId, Model model) {
        Product product = service.findById(productId);
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The product cannot be found");
        }
        service.delete(productId);
        return "redirect:/product/list";
    }

    private void productValidation(Product product) {

        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("The name of the product cannot be empty or null");
        }

        if (product.getProductQuantity() < 1) {
            throw new IllegalArgumentException("The product quantity cannot be negative or zero");
        }
    }
}