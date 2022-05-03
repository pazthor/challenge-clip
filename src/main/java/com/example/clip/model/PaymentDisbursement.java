package com.example.clip.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PaymentDisbursement {
    private BigDecimal disbursement;
    private BigDecimal payment;
    private String userId;
}
