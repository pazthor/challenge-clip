package com.example.clip.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReport {
    @JsonProperty("user_name")
    private String userId;
    @JsonProperty("payments_sum")
    private BigDecimal totalPayments;
    @JsonProperty("new_payments")
    private BigDecimal newPayments;
    @JsonProperty("new_payments_amount")
    private BigDecimal totalAmountPayments;

}
