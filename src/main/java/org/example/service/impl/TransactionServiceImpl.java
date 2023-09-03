package org.example.service.impl;

import org.example.converter.TransactionConverter;
import org.example.exeption.RequestException;
import org.example.model.dto.request.MoneyOperationDto;
import org.example.model.dto.response.TransactionResponseDto;
import org.example.model.dto.response.TransactionUpdateDto;
import org.example.model.entity.Transaction;
import org.example.repository.TransactionRepository;
import org.example.repository.impl.TransactionRepositoryImpl;
import org.example.service.TransactionService;
import org.example.util.EntityUtils;

import java.util.Date;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository = new TransactionRepositoryImpl();
    private final TransactionConverter transactionConverter = new TransactionConverter();

    @Override
    public TransactionResponseDto findById(Long id) {
        Transaction transaction = transactionRepository.findById(id);
        return transactionConverter.convert(transaction);
    }

    @Override
    public List<TransactionResponseDto> getAllTransactionsByUserId(Long id) {
        List<Transaction> responseDtoList = transactionRepository.getAllTransactionsByUserId(id);
        return transactionConverter.convert(responseDtoList);
    }

    @Override
    public List<TransactionResponseDto> getAllTransactionsByDate(Date date) {
        List<Transaction> responseDtoList = transactionRepository.getAllTransactionsByDate(date);
        return transactionConverter.convert(responseDtoList);
    }

    @Override
    public TransactionResponseDto create(MoneyOperationDto moneyOperationDto) {
        if (EntityUtils.isEmpty(moneyOperationDto.bankId()) || EntityUtils.isEmpty(moneyOperationDto.recipientAccountId()) || EntityUtils.isEmpty(moneyOperationDto.senderAccountId())) {
            throw new RequestException("String is empty");
        }
        Transaction transaction = transactionConverter.convert(moneyOperationDto);
        Transaction saved = transactionRepository.create(transaction);
        return transactionConverter.convert(saved);
    }

    @Override
    public TransactionResponseDto update(TransactionUpdateDto transactionUpdateDto) {
        if (EntityUtils.isEmpty(transactionUpdateDto.getBankId()) || EntityUtils.isEmpty(transactionUpdateDto.getRecipientAccountId()) || EntityUtils.isEmpty(transactionUpdateDto.getSenderAccountId())) {
            throw new RequestException("String is empty");
        }
        Transaction transaction = transactionConverter.convert(transactionUpdateDto);
        Transaction saved = transactionRepository.update(transaction);
        return transactionConverter.convert(saved);
    }

    @Override
    public void delete(Long id) {
        transactionRepository.delete(id);
    }
}
