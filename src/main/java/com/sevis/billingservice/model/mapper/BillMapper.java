package com.sevis.billingservice.model.mapper;

import com.sevis.billingservice.dto.request.BillRequest;
import com.sevis.billingservice.dto.response.BillResponse;
import com.sevis.billingservice.model.Bill;

public class BillMapper {

    private BillMapper() {}

    public static Bill toEntity(BillRequest request) {
        Bill bill = new Bill();
        bill.setUserId(request.getUserId());
        bill.setOrderId(request.getOrderId());
        bill.setAmount(request.getAmount());
        bill.setStatus(request.getStatus());
        return bill;
    }

    public static BillResponse toResponse(Bill bill) {
        return new BillResponse(bill);
    }
}
