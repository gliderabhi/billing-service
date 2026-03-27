package com.sevis.billingservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BillRequest {

    @NotNull
    private Long userId;

    @NotNull
    private Long orderId;

    @NotNull
    private Double amount;

    @NotBlank
    private String status;
}
