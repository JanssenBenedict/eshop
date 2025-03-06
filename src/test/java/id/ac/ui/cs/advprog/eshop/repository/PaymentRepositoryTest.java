package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

class PaymentRepositoryTest {

    PaymentRepository paymentRepository;
    List<Payment> payments;
    List<Product> products;
    Order order;
    Map<String, String> voucherCodePaymentData;
    Map<String, String> bankTransferPaymentData;

    @BeforeEach
    void setUp() {
        this.paymentRepository = new PaymentRepository();

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
    void testAddPayment() {
        paymentRepository.addPayment(order, PaymentMethod.VOUCHER.getValue(), voucherCodePaymentData);
        Payment addedPayment = paymentRepository.getAllPayments().get(0);

        assertEquals(PaymentMethod.VOUCHER.getValue(), addedPayment.getMethod());
        assertSame(order, addedPayment.getOrder());
        assertSame(voucherCodePaymentData, addedPayment.getPaymentData());
        assertEquals(PaymentStatus.PENDING.getValue(), addedPayment.getStatus());
    }

    @Test
    void testAddPaymentWithInvalidMethod() {
        Map<String, String> miscPaymentData = new HashMap<>();

        assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.addPayment(order, "IDK", miscPaymentData);
        });
    }

    @Test
    void testVoucherCodePaymentSetSuccessStatus() {
        Payment payment = paymentRepository.addPayment(order, PaymentMethod.VOUCHER.getValue(), voucherCodePaymentData);
        paymentRepository.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), payment.getOrder().getStatus());
    }

    @Test
    void testVoucherCodePaymentSetRejectedStatus() {
        Payment payment = paymentRepository.addPayment(order, PaymentMethod.VOUCHER.getValue(), voucherCodePaymentData);
        paymentRepository.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), payment.getOrder().getStatus());
    }

    @Test
    void testBankTransferPaymentSetSuccessStatus() {
        Payment payment = paymentRepository.addPayment(order, PaymentMethod.BANK_TRANSFER.getValue(), bankTransferPaymentData);
        paymentRepository.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), payment.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), payment.getOrder().getStatus());
    }

    @Test
    void testBankTransferPaymentSetRejectedStatus() {
        Payment payment = paymentRepository.addPayment(order, PaymentMethod.BANK_TRANSFER.getValue(), bankTransferPaymentData);
        paymentRepository.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), payment.getStatus());
        assertEquals(OrderStatus.FAILED.getValue(), payment.getOrder().getStatus());
    }

    @Test
    void testSetInvalidPaymentStatus() {
        Payment payment = paymentRepository.addPayment(order, PaymentMethod.VOUCHER.getValue(), voucherCodePaymentData);

        assertThrows(IllegalArgumentException.class, () -> {
            paymentRepository.setStatus(payment, "IDK");
        });
    }

    @Test
    void testGetPayment() {
        Payment payment = paymentRepository.addPayment(order, PaymentMethod.VOUCHER.getValue(), voucherCodePaymentData);

        assertSame(payment, paymentRepository.getPayment(payment.getId()));
    }

    @Test
    void testGetAllPayments() {
        Payment payment1 = paymentRepository.addPayment(order, PaymentMethod.VOUCHER.getValue(), voucherCodePaymentData);
        Payment payment2 = paymentRepository.addPayment(order, PaymentMethod.BANK_TRANSFER.getValue(), bankTransferPaymentData);

        assertSame(payment1, paymentRepository.getPayment(payment1.getId()));
        assertSame(payment2, paymentRepository.getPayment(payment2.getId()));
    }

    @Test
    void testAddInvalidPayments() {
        Map<String, String> nullBankNamePaymentData = new HashMap<>();
        Map<String, String> nullReferenceCodePaymentData = new HashMap<>();
        Map<String, String> emptyBankNamePaymentData = new HashMap<>();
        Map<String, String> emptyReferenceCodePaymentData = new HashMap<>();

        nullBankNamePaymentData.put("bankName", null);
        nullReferenceCodePaymentData.put("referenceCode", null);
        emptyBankNamePaymentData.put("bankName", "");
        emptyReferenceCodePaymentData.put("referenceCode", "");

        Payment nullBankNamePayment = paymentRepository.addPayment(
                order, PaymentMethod.BANK_TRANSFER.getValue(), nullBankNamePaymentData);
        Payment nullReferenceCodePayment = paymentRepository.addPayment(
                order, PaymentMethod.BANK_TRANSFER.getValue(), nullReferenceCodePaymentData);
        Payment emptyBankNamePayment = paymentRepository.addPayment(
                order, PaymentMethod.BANK_TRANSFER.getValue(), emptyBankNamePaymentData);
        Payment emptyReferenceCodePayment = paymentRepository.addPayment(
                order, PaymentMethod.BANK_TRANSFER.getValue(), emptyReferenceCodePaymentData);

        Map<String, String> lessThan16CharsPaymentData = new HashMap<>();
        Map<String, String> moreThan16CharsPaymentData = new HashMap<>();
        Map<String, String> noESHOPAtStartPaymentData = new HashMap<>();
        Map<String, String> no8NumericalCharsPaymentData = new HashMap<>();

        lessThan16CharsPaymentData.put("voucherCode", "ESHOP123ABC4567");
        moreThan16CharsPaymentData.put("voucherCode", "ESHOP123ABC456789");
        noESHOPAtStartPaymentData.put("voucherCode", "ABCDE123ABC45678");
        no8NumericalCharsPaymentData.put("voucherCode", "ESHOPABCDEFGHIJK");

        Payment lessThan16CharsPayment = paymentRepository.addPayment(
                order, PaymentMethod.VOUCHER.getValue(), lessThan16CharsPaymentData);
        Payment moreThan16CharsPayment = paymentRepository.addPayment(
                order, PaymentMethod.VOUCHER.getValue(), moreThan16CharsPaymentData);
        Payment noESHOPAtStartPayment = paymentRepository.addPayment(
                order, PaymentMethod.VOUCHER.getValue(), noESHOPAtStartPaymentData);
        Payment no8NumericalCharsPayment = paymentRepository.addPayment(
                order, PaymentMethod.VOUCHER.getValue(), no8NumericalCharsPaymentData);

        boolean rejectedStatuses = true;
        for (Payment payment : paymentRepository.getAllPayments()) {
            if (!(payment.getStatus().equals(PaymentStatus.REJECTED.getValue()))) {
                rejectedStatuses = false;
                break;
            }
        }
        assertTrue(rejectedStatuses);
    }
}