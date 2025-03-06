package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

@Getter
public class Payment {
    private String id;
    private PaymentMethod method;
    private PaymentStatus status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(String method, Map<String, String> paymentData, Order order) {
        this.id = UUID.randomUUID().toString();
        this.setMethod(method);
        this.setPaymentData(paymentData);

        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        this.order = order;
    }

    public void setMethod(String method) {
        if (method == null || !PaymentMethod.contains(method)) {
            throw new IllegalArgumentException("Invalid payment method");
        }
        this.method = PaymentMethod.valueOf(method);
    }

    public void setPaymentData(Map<String, String> paymentData) {
        if (paymentData == null) {
            throw new IllegalArgumentException("Payment data cannot be null");
        }

        if (this.method == PaymentMethod.VOUCHER) {
            String voucherCode = paymentData.get("voucherCode");
            if (voucherCode == null || voucherCode.length() != 16
                    || !voucherCode.startsWith("ESHOP")) {
                this.status = PaymentStatus.REJECTED;
            } else {
                int counter = 0;
                for (int i = 0; i < voucherCode.length(); i++) {
                    if (Character.isDigit(voucherCode.charAt(i))) {
                        counter++;
                    }
                }
                if (counter == 8) {
                    this.status = PaymentStatus.PENDING;
                } else {
                    this.status = PaymentStatus.REJECTED;
                }
            }
        } else if (this.method == PaymentMethod.BANK_TRANSFER) {
            String bankName = paymentData.get("bankName");
            String referenceCode = paymentData.get("referenceCode");

            if (bankName == null || bankName.isEmpty()
                    || referenceCode == null || referenceCode.isEmpty()) {
                this.status = PaymentStatus.REJECTED;
            } else {
                this.status = PaymentStatus.PENDING;
            }
        }
        this.paymentData = paymentData;
    }

    public void setStatus(String status) {
        if (status == null || !PaymentStatus.contains(status)) {
            throw new IllegalArgumentException("Invalid payment status");
        }
        this.status = PaymentStatus.valueOf(status);
    }
}