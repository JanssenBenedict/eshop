package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class PaymentRepository {
    private List<Payment> paymentData = new ArrayList<>();

    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        return null;
    }

    public void setStatus(Payment payment, String status) {
    }

    public Payment getPayment(String id) {
        return null;
    }

    public List<Payment> getAllPayments() {
        return null;
    }
}