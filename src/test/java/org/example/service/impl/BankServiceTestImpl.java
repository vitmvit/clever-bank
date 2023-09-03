package org.example.service.impl;

import org.example.exeption.ConnectionException;
import org.example.exeption.RequestException;
import org.example.model.dto.request.BankCreateDto;
import org.example.model.dto.response.BankResponseDto;
import org.example.model.dto.response.BankUpdateDto;
import org.example.service.BankService;
import org.example.service.BankServiceTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BankServiceTestImpl implements BankServiceTest {

    private final BankService bankService = new BankServiceImpl();

    @Test
    public void findByIdPositive() {
        BankResponseDto bankResponseDto = bankService.create(getBank());
        Assertions.assertNotNull(bankService.findById(bankResponseDto.getId()));
    }

    @Test
    public void findByIdNegative() {
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            bankService.findById(Long.MAX_VALUE);
        });
        Assertions.assertTrue(exception.getMessage().startsWith("Connection is lost"));
    }

    @Test
    public void createPositive() {
        BankCreateDto bankCreateDto = getBank();
        BankResponseDto bankResponseDto = bankService.create(bankCreateDto);
        Assertions.assertNotNull(bankResponseDto.getId());
        Assertions.assertEquals(bankCreateDto.name(), bankResponseDto.getName());
    }

    @Test
    public void createNegative() {
        BankCreateDto bankCreateDto = new BankCreateDto(null);
        var exception = Assertions.assertThrows(Exception.class,
                () -> bankService.create(bankCreateDto));
        Assertions.assertEquals(exception.getClass(), RequestException.class);
    }

    @Test
    public void updatePositive() {
        BankCreateDto accountCreateDto = getBank();

        BankResponseDto saved = bankService.create(accountCreateDto);

        BankUpdateDto bank = new BankUpdateDto();
        bank.setId(saved.getId());
        bank.setName("Name");

        BankResponseDto updated = bankService.update(bank);
        Assertions.assertNotEquals(saved.getName(), updated.getName());
    }

    @Test
    public void updateNegative() {
        BankUpdateDto bank = new BankUpdateDto();
        bank.setId(null);
        bank.setName(null);

        RequestException exception = Assertions.assertThrows(RequestException.class, () -> {
            bankService.update(bank);
        });
        Assertions.assertTrue(exception.getMessage().startsWith("String is empty"));
    }

    @Test
    public void delete() {
        BankResponseDto saved = bankService.create(getBank());
        bankService.delete(saved.getId());
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            bankService.findById(saved.getId());
        });
        Assertions.assertTrue(exception.getMessage().startsWith("Connection is lost"));
    }

    private BankCreateDto getBank() {
        return new BankCreateDto("bank_1");
    }
}