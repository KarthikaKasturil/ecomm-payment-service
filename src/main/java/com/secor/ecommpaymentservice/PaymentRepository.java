package com.secor.ecommpaymentservice;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment, String>
{
    Payment findByPaymentId(String paymentId);
    Payment findAllByCustomerId(String customerId);
    Payment findByOrderId(String orderId);
}
