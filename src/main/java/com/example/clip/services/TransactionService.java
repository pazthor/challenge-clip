package com.example.clip.services;

import com.example.clip.model.Payment;
import com.example.clip.model.Status;
import com.example.clip.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    private final PaymentRepository paymentRepository;

    @Autowired
    public TransactionService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getListUsersWithPaymentSaved() {

        return paymentRepository.findByStatusIs(Status.NEW);
    }

    public void saveNewTransaction(Payment payment){
        payment.setStatus(Status.NEW);
        paymentRepository.save(payment);
    }
}
