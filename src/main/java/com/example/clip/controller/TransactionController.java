package com.example.clip.controller;


import javax.persistence.PersistenceException;

import com.example.clip.model.Payment;
import com.example.clip.model.PaymentDisbursement;
import com.example.clip.model.UserReport;
import com.example.clip.request.PaymentRequest;
import com.example.clip.services.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/clip")
public class TransactionController {
    @Autowired
    TransactionService transactionService;


    @RequestMapping(value = "/createPayload", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody PaymentRequest paymentRequest) {

        Payment payment = new Payment();
        payment.setAmount(paymentRequest.getAmount());
        payment.setUserId(paymentRequest.getUserId());

        try {
            transactionService.saveNewTransaction(payment);
            log.info("Payload Created Successfully");
            return ResponseEntity.status(HttpStatus.OK).build();

        } catch (PersistenceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @RequestMapping(value = "/getUsersPaymentSaved", method = RequestMethod.GET)
    public ResponseEntity getUsers() {

        try {
            List<String> userWithPayments = transactionService.getListUsersWithPaymentSaved();
            log.info("Users with Payments loaded Successfully");
            return ResponseEntity.ok(userWithPayments);

        } catch (PersistenceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @RequestMapping(value = "/disbursementProcess", method = RequestMethod.GET)
    public ResponseEntity disbursementProcess() {

        try {
            List<PaymentDisbursement> userWithPayments = transactionService.getListUsersWithDisbursementProcess();
            log.info("Users with Disbursement process Successfully");
            return ResponseEntity.ok(userWithPayments);

        } catch (PersistenceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @RequestMapping(value = "/getReport", method = RequestMethod.GET)
    public ResponseEntity getReportByUser( @RequestParam String userId) {

        try {
            UserReport report = transactionService.getReportByUser(userId);
            log.info("User Report Created Successfully");
            return ResponseEntity.ok(report);

        } catch (PersistenceException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
