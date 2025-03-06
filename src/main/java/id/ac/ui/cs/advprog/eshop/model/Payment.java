package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(String method, Map<String, String> paymentData, Order order) {
        this.id = UUID.randomUUID().toString();
        this.setMethod(method);
        this.setPaymentData(paymentData);

        if (order == null) {
            throw new IllegalArgumentException();
        } else {
            this.order = order;
        }
    }

    public void setMethod(String method) {
        String[] methodList = {"VOUCHER", "BANK_TRANSFER"};

        if (Arrays.asList(methodList).contains(method)) {
            this.method = method;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setPaymentData(Map<String, String> paymentData) {
        if (paymentData == null) {
            throw new IllegalArgumentException("Payment data cannot be null");
        }

        if ("VOUCHER".equals(this.method)) {
            String voucherCode = paymentData.get("voucherCode");
            if (voucherCode == null || voucherCode.length() != 16 ||
                    !(voucherCode.startsWith("ESHOP"))) {
                this.status = "REJECTED";
            } else {
                int counter = 0;
                for (int i = 0; i < voucherCode.length(); i++) {
                    if (Character.isDigit(voucherCode.charAt(i))) {
                        counter++;
                    }
                }
                if (counter == 8) {
                    this.status = "PENDING";
                } else {
                    this.status = "REJECTED";
                }
            }
        } else if ("BANK_TRANSFER".equals(this.method)) {
            String bankName = paymentData.get("bankName");
            String referenceCode = paymentData.get("referenceCode");

            if (bankName == null || bankName.isEmpty() ||
                    referenceCode == null || referenceCode.isEmpty()) {
                this.status = "REJECTED";
            } else {
                this.status = "PENDING";
            }
        }
        this.paymentData = paymentData;
    }

    public void setStatus(String status) {
        String[] statusList = {"PENDING", "SUCCESS", "REJECTED"};

        if (Arrays.asList(statusList).contains(status)) {
            this.status = status;
        } else {
            throw new IllegalArgumentException();
        }
    }
}