package com.sevis.billingservice.service;

import com.sevis.billingservice.dto.request.BillRequest;
import com.sevis.billingservice.dto.response.BillResponse;
import com.sevis.billingservice.model.mapper.BillMapper;
import com.sevis.billingservice.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillService {

    private final BillRepository billRepository;

    public List<BillResponse> getAll() {
        return billRepository.findAll()
                .stream()
                .map(BillMapper::toResponse)
                .collect(Collectors.toList());
    }

    public Optional<BillResponse> getById(Long id) {
        return billRepository.findById(id)
                .map(BillMapper::toResponse);
    }

    public List<BillResponse> getByUserId(Long userId) {
        return billRepository.findByUserId(userId)
                .stream()
                .map(BillMapper::toResponse)
                .collect(Collectors.toList());
    }

    public BillResponse create(BillRequest request) {
        return BillMapper.toResponse(billRepository.save(BillMapper.toEntity(request)));
    }
}
