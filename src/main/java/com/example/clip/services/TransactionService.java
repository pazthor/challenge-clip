package com.example.clip.services;

import com.example.clip.model.Payment;
import com.example.clip.model.PaymentDisbursement;
import com.example.clip.model.Status;
import com.example.clip.model.UserReport;
import com.example.clip.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private final PaymentRepository paymentRepository;
    static final double DISCOUNT_DISBURSEMENT=0.035;

    @Autowired
    public TransactionService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public List<Payment> getListUsersWithPaymentSaved() {

        return paymentRepository.findByStatusIs(Status.NEW);
    }

    public List<PaymentDisbursement> getListUsersWithDisbursementProcess() {
        List<Payment> payments =  paymentRepository.findByStatusIs(Status.NEW);
        List<Payment> paymentsUpdated = payments.stream().peek(p -> p.setStatus(Status.PROCESSED)).collect(Collectors.toList());
        paymentRepository.saveAll(paymentsUpdated);

        List<PaymentDisbursement> result = disbursementProcess(payments);

        return result;
    }

    public void saveNewTransaction(Payment payment){
        payment.setStatus(Status.NEW);
        paymentRepository.save(payment);
    }

    public List<PaymentDisbursement> disbursementProcess(List<Payment> listPayments){
        List<PaymentDisbursement> result = listPayments.stream().map(p ->{
            PaymentDisbursement pD = new PaymentDisbursement();
            BigDecimal disbursement = getDisbursement(p.getAmount());
            pD.setDisbursement(disbursement);
            pD.setPayment(p.getAmount());
            pD.setUserId(p.getUserId());

            return pD;
        } ).collect(Collectors.toList());

        return result;
    }

    public BigDecimal getDisbursement(BigDecimal payment){
        BigDecimal discount = payment.multiply( BigDecimal.valueOf(DISCOUNT_DISBURSEMENT));
        BigDecimal result = payment.subtract(discount);

        return result;
    }

    public UserReport getReportByUser(String userId){
        List<Payment> payments = paymentRepository.findByUserIdIs(userId);

        return createReport(userId, payments);

    }

    public UserReport createReport(String userId, List<Payment> listPayments){
        int totalAllPayments = listPayments.size();
        long totalNewPayments =  listPayments.stream().filter(x -> x.getStatus().equals(Status.NEW)).count();
        List<BigDecimal> l = listPayments.stream().map(Payment::getAmount).collect(Collectors.toList());
        BigDecimal totalAmount = l.stream().reduce(BigDecimal.ZERO, BigDecimal::add);

        UserReport result = new UserReport();
        result.setUserId(userId);
        result.setNewPayments(BigDecimal.valueOf(totalNewPayments));
        result.setTotalPayments(BigDecimal.valueOf(totalAllPayments));
        result.setTotalAmountPayments(totalAmount);








        return result;
    }

}
