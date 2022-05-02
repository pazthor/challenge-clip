package com.example.clip.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequest {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank(message = "userId must be not empty")
    String userId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @NotBlank(message = "amount must be not empty")
    BigDecimal amount;
}
