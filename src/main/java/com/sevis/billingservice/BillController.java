package com.sevis.billingservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillRepository repository;

    public BillController(BillRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Bill> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bill> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public List<Bill> getByUserId(@PathVariable Long userId) {
        return repository.findByUserId(userId);
    }

    @PostMapping
    public Bill create(@RequestBody Bill bill) {
        return repository.save(bill);
    }
}
