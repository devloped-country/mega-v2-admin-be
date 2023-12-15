package com.app.mega.repository;

import com.app.mega.entity.Admin;
import com.app.mega.entity.Payment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaPaymentRepository extends JpaRepository<Payment, Long> {
//    Optional<Payment> findByOrderId(String orderId);
//
//    Optional<Payment> findByPaymentKeyAndUser_Email(String paymentKey, String email);
//
//    Slice<Payment> findAllByUser_Email(String email, Pageable pageable);

//    Optional<Payment> saveByInstitutionId(String paymentKey, String orderId, Long amount, Long month, Admin admin);

}