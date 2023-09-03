package org.example.service.impl;

import org.example.converter.AccountConverter;
import org.example.converter.TransactionConverter;
import org.example.exeption.RequestException;
import org.example.model.constant.TransactionType;
import org.example.model.dto.Receipt;
import org.example.model.dto.request.AccountCreateDto;
import org.example.model.dto.request.MoneyOperationDto;
import org.example.model.dto.response.AccountResponseDto;
import org.example.model.dto.response.AccountUpdateDto;
import org.example.model.entity.Account;
import org.example.model.entity.Transaction;
import org.example.repository.AccountRepository;
import org.example.repository.TransactionRepository;
import org.example.repository.impl.AccountRepositoryImpl;
import org.example.repository.impl.TransactionRepositoryImpl;
import org.example.service.AccountService;
import org.example.service.ReceiptService;
import org.example.util.EntityUtils;

import java.util.Date;

public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository = new AccountRepositoryImpl();
    private final TransactionRepository transactionRepository = new TransactionRepositoryImpl();
    private final ReceiptService receiptService = new ReceiptService();
    private final AccountConverter accountConverter = new AccountConverter();
    private final TransactionConverter transactionConverter = new TransactionConverter();

    @Override
    public AccountResponseDto findById(Long id) {
        Account account = accountRepository.findById(id);
        return accountConverter.convert(account);
    }

    @Override
    public AccountResponseDto create(AccountCreateDto accountCreateDto) {
        if (EntityUtils.isEmpty(accountCreateDto.bankId()) || EntityUtils.isEmpty(accountCreateDto.userId())) {
            throw new RequestException("String is empty");
        }
        Account account = accountConverter.convert(accountCreateDto);
        Account saved = accountRepository.create(account);
        return accountConverter.convert(saved);
    }

    @Override
    public AccountResponseDto update(AccountUpdateDto accountUpdateDto) {
        if (EntityUtils.isEmpty(accountUpdateDto.getBankId()) || EntityUtils.isEmpty(accountUpdateDto.getUserId())) {
            throw new RequestException("String is empty");
        }
        Account account = accountConverter.convert(accountUpdateDto);
        Account saved = accountRepository.update(account);
        return accountConverter.convert(saved);
    }

    @Override
    public void delete(Long id) {
        accountRepository.delete(id);
    }

    @Override
    public void moneyAdd(MoneyOperationDto moneyOperationDto) {
        Receipt receipt = accountRepository.moneyAdd(transactionConverter.convert(moneyOperationDto));
        Transaction transaction = transactionCreate(TransactionType.ADD, moneyOperationDto);
        receipt.setNumber(transaction.getId());
        receiptService.createReceipt(receipt);
    }

    @Override
    public void moneyRemove(MoneyOperationDto moneyOperationDto) {
        Receipt receipt = accountRepository.moneyRemove(transactionConverter.convert(moneyOperationDto));
        Transaction transaction = transactionCreate(TransactionType.REMOVE, moneyOperationDto);
        receipt.setNumber(transaction.getId());
        receiptService.createReceipt(receipt);
    }

    @Override
    public void moneyTransfer(MoneyOperationDto moneyOperationDto) {
        Receipt receipt = accountRepository.moneyTransfer(transactionConverter.convert(moneyOperationDto));
        Transaction transaction = transactionCreate(TransactionType.TRANSFER, moneyOperationDto);
        receipt.setNumber(transaction.getId());
        receiptService.createReceipt(receipt);
    }

    private Transaction transactionCreate(TransactionType type, MoneyOperationDto moneyOperationDto) {
        Transaction transaction = new Transaction();
        transaction.setType(type);
        transaction.setSenderAccountId(moneyOperationDto.senderAccountId());
        transaction.setRecipientAccountId(moneyOperationDto.recipientAccountId());
        transaction.setBankId(moneyOperationDto.bankId());
        transaction.setDateTransaction(new Date());
        transaction.setSum(moneyOperationDto.sum());
        return transactionRepository.create(transaction);
    }
}
