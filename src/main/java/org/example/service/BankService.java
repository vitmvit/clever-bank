package org.example.service;

import org.example.model.dto.request.BankCreateDto;
import org.example.model.dto.response.BankResponseDto;
import org.example.model.dto.response.BankUpdateDto;

public interface BankService {

    BankResponseDto findById(Long id);

    BankResponseDto create(BankCreateDto bankCreateDto);

    BankResponseDto update(BankUpdateDto bankUpdateDto);

    void delete(Long id);
}
