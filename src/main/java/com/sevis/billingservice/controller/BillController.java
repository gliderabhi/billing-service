package com.sevis.billingservice.controller;

import com.sevis.billingservice.dto.request.BillRequest;
import com.sevis.billingservice.dto.response.BillResponse;
import com.sevis.billingservice.service.BillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
@RequiredArgsConstructor
public class BillController {

    private final BillService billService;

    @GetMapping
    public List<BillResponse> getAll() {
        return billService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> getById(@PathVariable Long id) {
        return billService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<BillResponse> getByUserId(@PathVariable Long userId) {
        return billService.getByUserId(userId);
    }

    @PostMapping
    public BillResponse create(@Valid @RequestBody BillRequest request) {
        return billService.create(request);
    }
}
