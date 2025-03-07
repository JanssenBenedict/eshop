package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentMethod;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    List<Payment> payments = new ArrayList<>();
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

        Payment voucherCodePayment = new Payment(
                PaymentMethod.VOUCHER.getValue(), voucherCodePaymentData, order);
        Payment bankTransferPayment = new Payment(
                PaymentMethod.BANK_TRANSFER.getValue(), bankTransferPaymentData, order);

        payments.add(voucherCodePayment);
        payments.add(bankTransferPayment);
    }

    @Test
    public void testAddPaymentWithVoucherCode() {
        Payment payment = payments.getFirst();
        doReturn(payment).when(paymentRepository).addPayment(order, PaymentMethod.VOUCHER.getValue(), voucherCodePaymentData);

        Payment actualPayment = paymentService.addPayment(order, PaymentMethod.VOUCHER.getValue(), voucherCodePaymentData);

        verify(paymentRepository, times(1))
                .addPayment(order, PaymentMethod.VOUCHER.getValue(), voucherCodePaymentData);
        assertEquals(payment, actualPayment);
    }

    @Test
    public void testAddPaymentWithBankTransfer() {
        Payment payment = payments.getLast();
        doReturn(payment).when(paymentRepository).addPayment(order, PaymentMethod.BANK_TRANSFER.getValue(), bankTransferPaymentData);

        Payment actualPayment = paymentService.addPayment(order, PaymentMethod.BANK_TRANSFER.getValue(), bankTransferPaymentData);

        verify(paymentRepository, times(1))
                .addPayment(order, PaymentMethod.BANK_TRANSFER.getValue(), bankTransferPaymentData);
        assertEquals(payment, actualPayment);
    }

    @Test
    void testCreatePaymentInvalidMethod() {
        Map<String, String> miscPaymentData = new HashMap<>();

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.addPayment(order, "IDK", miscPaymentData);
        });
    }

    @Test
    void testCreatePaymentNullOrder() {
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.addPayment(null, PaymentMethod.VOUCHER.getValue(), voucherCodePaymentData);
        });
    }

    @Test
    void testSetVoucherCodePaymentStatus() {
        Payment payment = payments.getFirst();
        doReturn(payment).when(paymentRepository).addPayment(order, PaymentMethod.VOUCHER.getValue(), voucherCodePaymentData);

        Payment actualPayment = paymentService.addPayment(order, PaymentMethod.VOUCHER.getValue(), voucherCodePaymentData);

        assertEquals(actualPayment.getStatus(), PaymentStatus.PENDING.getValue());

        Payment modifiedPayment = new Payment(PaymentMethod.VOUCHER.getValue(), voucherCodePaymentData, order);

        modifiedPayment.setStatus(PaymentStatus.SUCCESS.getValue());
        modifiedPayment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        doReturn(modifiedPayment).when(paymentRepository).setStatus(actualPayment, PaymentStatus.SUCCESS.getValue());

        Payment modifiedActualPayment = paymentService.setStatus(actualPayment, PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), modifiedActualPayment.getStatus());
        assertEquals(PaymentMethod.VOUCHER.getValue(), modifiedActualPayment.getMethod());
        assertEquals(OrderStatus.SUCCESS.getValue(), modifiedActualPayment.getOrder().getStatus());

        modifiedPayment.setStatus(PaymentStatus.REJECTED.getValue());
        modifiedPayment.getOrder().setStatus(OrderStatus.FAILED.getValue());
        doReturn(modifiedPayment).when(paymentRepository).setStatus(actualPayment, PaymentStatus.REJECTED.getValue());

        modifiedActualPayment = paymentService.setStatus(actualPayment, PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), modifiedActualPayment.getStatus());
        assertEquals(PaymentMethod.VOUCHER.getValue(), modifiedActualPayment.getMethod());
        assertEquals(OrderStatus.FAILED.getValue(), modifiedActualPayment.getOrder().getStatus());
    }

    @Test
    void testSetBankTransferPaymentStatus() {
        Payment payment = payments.getLast();
        doReturn(payment).when(paymentRepository).addPayment(order, PaymentMethod.BANK_TRANSFER.getValue(), bankTransferPaymentData);

        Payment actualPayment = paymentService.addPayment(order, PaymentMethod.BANK_TRANSFER.getValue(), bankTransferPaymentData);

        assertEquals(actualPayment.getStatus(), PaymentStatus.PENDING.getValue());

        Payment modifiedPayment = new Payment(PaymentMethod.BANK_TRANSFER.getValue(), bankTransferPaymentData, order);

        modifiedPayment.setStatus(PaymentStatus.SUCCESS.getValue());
        modifiedPayment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        doReturn(modifiedPayment).when(paymentRepository).setStatus(actualPayment, PaymentStatus.SUCCESS.getValue());

        Payment modifiedActualPayment = paymentService.setStatus(actualPayment, PaymentStatus.SUCCESS.getValue());
        assertEquals(PaymentStatus.SUCCESS.getValue(), modifiedActualPayment.getStatus());
        assertEquals(PaymentMethod.BANK_TRANSFER.getValue(), modifiedActualPayment.getMethod());
        assertEquals(OrderStatus.SUCCESS.getValue(), modifiedActualPayment.getOrder().getStatus());

        modifiedPayment.setStatus(PaymentStatus.REJECTED.getValue());
        modifiedPayment.getOrder().setStatus(OrderStatus.FAILED.getValue());
        doReturn(modifiedPayment).when(paymentRepository).setStatus(actualPayment, PaymentStatus.REJECTED.getValue());

        modifiedActualPayment = paymentService.setStatus(actualPayment, PaymentStatus.REJECTED.getValue());
        assertEquals(PaymentStatus.REJECTED.getValue(), modifiedActualPayment.getStatus());
        assertEquals(PaymentMethod.BANK_TRANSFER.getValue(), modifiedActualPayment.getMethod());
        assertEquals(OrderStatus.FAILED.getValue(), modifiedActualPayment.getOrder().getStatus());
    }

    @Test
    void testSetInvalidVoucherCodePaymentStatus() {
        Payment payment = payments.getFirst();

        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
        assertEquals(PaymentMethod.VOUCHER.getValue(), payment.getMethod());

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payment, "IDK1");
        });
    }

    @Test
    void testSetInvalidBankTransferPaymentStatus() {
        Payment payment = payments.getLast();

        assertEquals(PaymentStatus.PENDING.getValue(), payment.getStatus());
        assertEquals(PaymentMethod.BANK_TRANSFER.getValue(), payment.getMethod());

        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payment, "IDK2");
        });
    }

    @Test
    void testGetPaymentFound() {
        Payment payment = payments.getFirst();
        doReturn(payment).when(paymentRepository).addPayment(order, PaymentMethod.VOUCHER.getValue(), voucherCodePaymentData);
        doReturn(payment).when(paymentRepository).getPayment(payment.getId());

        Payment wantedPayment = paymentService.addPayment(order, PaymentMethod.VOUCHER.getValue(), voucherCodePaymentData);

        assertEquals(wantedPayment, paymentService.getPayment(wantedPayment.getId()));
    }

    @Test
    void testGetPaymentNotFound() {
        doReturn(null).when(paymentRepository).getPayment("randomInvalidID");

        assertNull(paymentService.getPayment("randomInvalidID"));
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).getAllPayments();

        List<Payment> allPayments = paymentService.getAllPayments();

        assertEquals(2, allPayments.size());
        assertEquals(allPayments, payments);
    }
}