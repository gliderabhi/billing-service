package com.sevis.billingservice.service;

import com.sevis.billingservice.dto.request.BillRequest;
import com.sevis.billingservice.dto.response.BillResponse;
import com.sevis.billingservice.model.mapper.BillMapper;
import com.sevis.billingservice.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;

    // Short-TTL cache (see CacheConfig): bills carry amount/status, so this is
    // still a financial-state read — cached only to smooth read bursts, not
    // to persist stale data for long.
    @Cacheable(value = "billsAll", sync = true)
    public List<BillResponse> getAll() {
        return billRepository.findAll()
                .stream()
                .map(BillMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "billById", key = "#id", sync = true)
    public Optional<BillResponse> getById(Long id) {
        return billRepository.findById(id)
                .map(BillMapper::toResponse);
    }

    @Cacheable(value = "billsByUser", key = "#userId", sync = true)
    public List<BillResponse> getByUserId(Long userId) {
        return billRepository.findByUserId(userId)
                .stream()
                .map(BillMapper::toResponse)
                .collect(Collectors.toList());
    }

    // A new bill can't already be present in billById (it doesn't have an id
    // yet), so only the list-shaped reads need invalidating.
    @Caching(evict = {
            @CacheEvict(value = "billsAll", allEntries = true),
            @CacheEvict(value = "billsByUser", allEntries = true)
    })
    public BillResponse create(BillRequest request) {
        return BillMapper.toResponse(billRepository.save(BillMapper.toEntity(request)));
    }
}
