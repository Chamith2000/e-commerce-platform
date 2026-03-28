package com.ecommerce.paymentservice.service;

import com.ecommerce.paymentservice.dto.PaymentDTO;
import com.ecommerce.paymentservice.model.Payment;
import com.ecommerce.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public List<Payment> getPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    public Payment processPayment(PaymentDTO dto) {
        Payment payment = new Payment();
        payment.setOrderId(dto.getOrderId());
        payment.setUserId(dto.getUserId());
        payment.setAmount(dto.getAmount());
        payment.setPaymentMethod(Payment.PaymentMethod.valueOf(dto.getPaymentMethod().toUpperCase()));
        payment.setTransactionReference("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        // Simulate payment processing logic
        if (dto.getPaymentMethod().equalsIgnoreCase("CASH_ON_DELIVERY")) {
            payment.setStatus(Payment.Status.PENDING);
        } else {
            // Simulate 90% success rate for card/online payments
            boolean success = Math.random() > 0.1;
            payment.setStatus(success ? Payment.Status.SUCCESS : Payment.Status.FAILED);
            if (!success) {
                payment.setFailureReason("Payment declined by bank");
            }
        }

        return paymentRepository.save(payment);
    }

    public Payment refundPayment(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
        if (payment.getStatus() != Payment.Status.SUCCESS) {
            throw new RuntimeException("Only successful payments can be refunded");
        }
        payment.setStatus(Payment.Status.REFUNDED);
        return paymentRepository.save(payment);
    }
}
