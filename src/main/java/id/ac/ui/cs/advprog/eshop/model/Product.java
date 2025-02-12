package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

import java.util.UUID;

@Getter @Setter
public class Product {
    private String productId;

    @NotBlank(message = "The name of the product is needed")
    private String productName;

    @Min(value = 1, message = "The product quantity cannot be negative or zero")
    private int productQuantity;

    public Product() {
        this.productId = UUID.randomUUID().toString();
    }
}