package org.example.service;

import org.example.model.dto.request.MoneyOperationDto;
import org.example.model.dto.response.TransactionResponseDto;
import org.example.model.dto.response.TransactionUpdateDto;

import java.util.Date;
import java.util.List;

public interface TransactionService {

    TransactionResponseDto findById(Long id);

    List<TransactionResponseDto> getAllTransactionsByUserId(Long id);

    List<TransactionResponseDto> getAllTransactionsByDate(Date date);

    TransactionResponseDto create(MoneyOperationDto transaction);

    TransactionResponseDto update(TransactionUpdateDto transactionUpdateDto);

    void delete(Long id);
}
