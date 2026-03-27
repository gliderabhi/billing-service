package com.sevis.billingservice.dto.response;

import com.sevis.billingservice.model.Bill;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BillResponse {

    private final Long id;
    private final Long userId;
    private final Long orderId;
    private final double amount;
    private final String status;
    private final LocalDateTime createdAt;

    public BillResponse(Bill bill) {
        this.id = bill.getId();
        this.userId = bill.getUserId();
        this.orderId = bill.getOrderId();
        this.amount = bill.getAmount();
        this.status = bill.getStatus();
        this.createdAt = bill.getCreatedAt();
    }
}
