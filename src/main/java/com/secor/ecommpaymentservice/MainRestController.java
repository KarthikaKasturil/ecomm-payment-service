package com.secor.ecommpaymentservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payments")
public class MainRestController {

    private final Logger LOG = LoggerFactory.getLogger(MainRestController.class);

    @Autowired
    private PaymentRepository paymentRepository;

    @GetMapping
    public List<Payment> getAllPayments() {
        LOG.info("getAllPayments");
        return paymentRepository.findAll();
    }

    @GetMapping("/{paymentId}")
    public Payment getPaymentByPaymentId(@PathVariable String paymentId) {
        LOG.info("getPaymentByPaymentId({})", paymentId);
        return paymentRepository.findByPaymentId(paymentId);
    }

    @GetMapping("/customer/{customerId}")
    public Payment getAllPaymentsByCustomerId(@PathVariable String customerId) {
        LOG.info("getAllPaymentsByCustomerId({})", customerId);
        return paymentRepository.findAllByCustomerId(customerId);
    }

    @GetMapping("/order/{orderId}")
    public Payment getPaymentByOrderId(@PathVariable String orderId) {
        LOG.info("getPaymentByOrderId({})", orderId);
        return paymentRepository.findByOrderId(orderId);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPayment(@RequestBody Payment payment) {
        LOG.info("createPayment({})", payment);
        Payment existing = paymentRepository.findByOrderId(payment.getOrderId());
        LOG.info("Payment exists: {}", existing != null);
        if (existing != null) {
            return ResponseEntity.status(400).body("Payment for this order already exists");
        }
        payment.setStatus("PENDING");
        payment.setPaymentDate(LocalDateTime.now());
        payment.setUpdatedAt(LocalDateTime.now());
        Payment savedPayment = paymentRepository.save(payment);
        return ResponseEntity.ok("Payment created for order " + savedPayment.getOrderId());
    }

    @PutMapping("/update/payment/status")
    public ResponseEntity<?> updatePaymentStatus(@RequestBody PaymentStatus status) {
        LOG.info("updatePaymentStatus({})", status);
        Payment payment = paymentRepository.findByOrderId(status.getOrderId());
        if (payment != null) {
           payment.setStatus(payment.getStatus());
           payment.setUpdatedAt(LocalDateTime.now());
           Payment updatedPayment = paymentRepository.save(payment);
              return ResponseEntity.ok("Payment status updated to " + updatedPayment.getStatus() + " for order " + updatedPayment.getOrderId());
        }
        else{
            return ResponseEntity.status(400).body("Payment not found for order " + status.getOrderId());
        }
    }

}