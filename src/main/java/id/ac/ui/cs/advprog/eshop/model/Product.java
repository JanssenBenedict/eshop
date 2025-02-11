package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

import java.util.UUID;

@Getter @Setter
public class Product {
    private String productId;
    private String productName;
    private int productQuantity;

    public Product() {
        this.productId = UUID.randomUUID().toString();
    }

    public void setProductName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("The name of the product cannot be empty or null");
        }
        this.productName = productName;
    }

    public void setProductQuantity(int productQuantity) {
        if (productQuantity < 1) {
            throw new IllegalArgumentException("The product quantity cannot be negative or zero");
        }
        this.productQuantity = productQuantity;
    }
}