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
import org.mockito.Mockito;

import static org.example.model.constant.Constants.CONNECTION_EXCEPTION_MESSAGE;
import static org.example.model.constant.Constants.REQUEST_EXCEPTION_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.*;

class BankServiceTestImpl implements BankServiceTest {

    private final BankService bankService = Mockito.mock(BankServiceImpl.class);

    @Test
    public void findByIdPositiveTest() {
        BankResponseDto target = getBankResponseDto();
        when(bankService.findById(1L)).thenReturn(target);
        BankResponseDto result = bankService.findById(target.getId());
        assertEquals(target.getId(), result.getId());
        assertEquals(target.getName(), result.getName());
    }

    @Test
    public void findByIdNegativeTest() {
        when(bankService.findById(anyLong())).thenThrow(new ConnectionException(CONNECTION_EXCEPTION_MESSAGE));
        var exception = Assertions.assertThrows(Exception.class, () -> {
            bankService.findById(Long.MAX_VALUE);
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));
    }

    @Test
    public void createPositiveTest() {
        BankResponseDto target = getBankResponseDto();
        BankCreateDto bankCreateDto = getBankCreateDto();
        when(bankService.create(bankCreateDto)).thenReturn(target);
        BankResponseDto actualResponse = bankService.create(bankCreateDto);
        Assertions.assertNotNull(actualResponse.getId());
        Assertions.assertEquals(bankCreateDto.name(), actualResponse.getName());
    }

    @Test
    public void createNegativeTest() {
        BankCreateDto target = new BankCreateDto(null);
        doThrow(RequestException.class).when(bankService).create(target);
        var exception = Assertions.assertThrows(Exception.class,
                () -> bankService.create(target));
        Assertions.assertEquals(exception.getClass(), RequestException.class);
    }

    @Test
    public void updatePositiveTest() {
        BankCreateDto bankCreateDto = getBankCreateDto();
        BankResponseDto saved = getBankResponseDto();
        when(bankService.create(bankCreateDto)).thenReturn(saved);
        BankUpdateDto user = getBankUpdateDto();
        BankResponseDto updated = new BankResponseDto();
        updated.setId(saved.getId());
        updated.setName("bank_update_name");
        when(bankService.update(user)).thenReturn(updated);
        Assertions.assertNotEquals(saved.getName(), updated.getName());
    }

    @Test
    public void updateNegativeTest() {
        BankUpdateDto bankUpdateDto = new BankUpdateDto();
        bankUpdateDto.setId(null);
        bankUpdateDto.setName(null);
        when(bankService.update(bankUpdateDto)).thenThrow(new RequestException(REQUEST_EXCEPTION_MESSAGE));
        RequestException exception = Assertions.assertThrows(RequestException.class, () -> {
            bankService.update(bankUpdateDto);
        });
        Assertions.assertTrue(exception.getMessage().startsWith(REQUEST_EXCEPTION_MESSAGE));
    }

    @Test
    public void deleteTest() {
        BankCreateDto bankCreateDto = getBankCreateDto();
        BankResponseDto saved = getBankResponseDto();
        doReturn(saved).when(bankService).create(bankCreateDto);
        doNothing().when(bankService).delete(saved.getId());
        doThrow(new ConnectionException(CONNECTION_EXCEPTION_MESSAGE)).when(bankService).findById(saved.getId());
        ConnectionException exception = Assertions.assertThrows(ConnectionException.class, () -> {
            bankService.findById(saved.getId());
        });
        Assertions.assertTrue(exception.getMessage().startsWith(CONNECTION_EXCEPTION_MESSAGE));
    }

    private BankResponseDto getBankResponseDto() {
        BankResponseDto bankResponseDto = new BankResponseDto();
        bankResponseDto.setId(1L);
        bankResponseDto.setName("bank_1");
        return bankResponseDto;
    }

    private BankCreateDto getBankCreateDto() {
        return new BankCreateDto("bank_1");
    }

    private BankUpdateDto getBankUpdateDto() {
        BankUpdateDto bankUpdateDto = new BankUpdateDto();
        bankUpdateDto.setId(1L);
        bankUpdateDto.setName("bank_1");
        return bankUpdateDto;
    }
}