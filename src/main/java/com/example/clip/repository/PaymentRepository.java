package com.example.clip.repository;

import com.example.clip.model.Payment;
import com.example.clip.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByStatusIs( Status status);
    List<Payment> findByUserIdIs( String userId);
}
