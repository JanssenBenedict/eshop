package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class PaymentTest {
    private List<Product> products;
    private Order order;
    private Map<String, String> voucherCodePaymentData;
    private Map<String, String> bankTransferPaymentData;

    @BeforeEach
    void setUp() {
        this.voucherCodePaymentData = new HashMap<>();
        this.voucherCodePaymentData.put("voucherCode", "ESHOP1234ABC5678");
        this.bankTransferPaymentData = new HashMap<>();
        this.bankTransferPaymentData.put("bankName", "Random Bank");
        this.bankTransferPaymentData.put("referenceCode", "123456789");

        this.products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);

        Product product2 = new Product();
        product2.setProductId("a2c62328-4a37-4664-83c7-f32db8620155");
        product2.setProductName("Sabun Cap Usep");
        product2.setProductQuantity(1);

        this.products.add(product1);
        this.products.add(product2);

        this.order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                this.products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testCreatePaymentWithNoOrder() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("VOUCHER", this.voucherCodePaymentData, null);
        });
    }

    @Test
    void testCreatePaymentWithNoPaymentData() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("VOUCHER", null, this.order);
        });
    }

    @Test
    void testCreatePaymentWithInvalidMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment("IDK", this.voucherCodePaymentData, this.order);
        });
    }

    @Test
    void testCreatePaymentWithInvalidVoucherCodePaymentData() {
        Map<String, String> lessThan16CharsPaymentData = new HashMap<>();
        Map<String, String> moreThan16CharsPaymentData = new HashMap<>();
        Map<String, String> noESHOPAtStartPaymentData = new HashMap<>();
        Map<String, String> no8NumericalCharsPaymentData = new HashMap<>();

        lessThan16CharsPaymentData.put("voucherCode", "ESHOP123ABC4567");
        moreThan16CharsPaymentData.put("voucherCode", "ESHOP123ABC456789");
        noESHOPAtStartPaymentData.put("voucherCode", "ABCDE123ABC45678");
        no8NumericalCharsPaymentData.put("voucherCode", "ESHOPABCDEFGHIJK");

        Payment lessThan16CharsPayment = new Payment(
                "VOUCHER", lessThan16CharsPaymentData, this.order);
        Payment moreThan16CharsPayment = new Payment(
                "VOUCHER", moreThan16CharsPaymentData, this.order);
        Payment noESHOPAtStartPayment = new Payment(
                "VOUCHER", noESHOPAtStartPaymentData, this.order);
        Payment no8NumericalCharsPayment = new Payment(
                "VOUCHER", no8NumericalCharsPaymentData, this.order);

        assertEquals("REJECTED", lessThan16CharsPayment.getStatus());
        assertEquals("REJECTED", moreThan16CharsPayment.getStatus());
        assertEquals("REJECTED", noESHOPAtStartPayment.getStatus());
        assertEquals("REJECTED", no8NumericalCharsPayment.getStatus());
    }

    @Test
    void testCreatePaymentWithInvalidBankTransferPaymentData() {
        Map<String, String> nullBankNamePaymentData = new HashMap<>();
        Map<String, String> nullReferenceCodePaymentData = new HashMap<>();
        Map<String, String> emptyBankNamePaymentData = new HashMap<>();
        Map<String, String> emptyReferenceCodePaymentData = new HashMap<>();

        nullBankNamePaymentData.put("bankName", null);
        nullReferenceCodePaymentData.put("referenceCode", null);
        emptyBankNamePaymentData.put("bankName", "");
        emptyReferenceCodePaymentData.put("referenceCode", "");

        Payment nullBankNamePayment = new Payment(
                "BANK_TRANSFER", nullBankNamePaymentData, this.order);
        Payment nullReferenceCodePayment = new Payment(
                "BANK_TRANSFER", nullReferenceCodePaymentData, this.order);
        Payment emptyBankNamePayment = new Payment(
                "BANK_TRANSFER", emptyBankNamePaymentData, this.order);
        Payment emptyReferenceCodePayment = new Payment(
                "BANK_TRANSFER", emptyReferenceCodePaymentData, this.order);

        assertEquals("REJECTED", nullBankNamePayment.getStatus());
        assertEquals("REJECTED", nullReferenceCodePayment.getStatus());
        assertEquals("REJECTED", emptyBankNamePayment.getStatus());
        assertEquals("REJECTED", emptyReferenceCodePayment.getStatus());
    }

    @Test
    void testCreateVoucherCodePaymentPending() {
        Payment payment = new Payment(
                "VOUCHER", this.voucherCodePaymentData, this.order);

        assertNotNull(payment.getId(), "Payment ID cannot be null");
        assertEquals("PENDING", payment.getStatus());
        assertSame(this.voucherCodePaymentData, payment.getPaymentData());
        assertSame(this.order, payment.getOrder());
    }

    @Test
    void testCreateBankTransferPaymentPending() {
        Payment payment = new Payment(
                "BANK_TRANSFER", this.bankTransferPaymentData, this.order);

        assertNotNull(payment.getId(), "Payment ID cannot be null");
        assertEquals("PENDING", payment.getStatus());
        assertSame(this.bankTransferPaymentData, payment.getPaymentData());
        assertSame(this.order, payment.getOrder());
    }

    @Test
    void testSetInvalidPaymentStatus() {
        Payment payment = new Payment(
                "VOUCHER", this.voucherCodePaymentData, this.order);

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("IDK");
        });
    }
}