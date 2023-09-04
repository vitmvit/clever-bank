package org.example.service.impl;

import org.example.converter.BankConverter;
import org.example.exeption.RequestException;
import org.example.model.dto.request.BankCreateDto;
import org.example.model.dto.response.BankResponseDto;
import org.example.model.dto.response.BankUpdateDto;
import org.example.model.entity.Bank;
import org.example.repository.BankRepository;
import org.example.repository.impl.BankRepositoryImpl;
import org.example.service.BankService;
import org.example.util.StringUtils;

import static org.example.model.constant.Constants.REQUEST_EXCEPTION_MESSAGE;

public class BankServiceImpl implements BankService {

    private final BankRepository bankRepository = new BankRepositoryImpl();
    private final BankConverter bankConverter = new BankConverter();

    @Override
    public BankResponseDto findById(Long id) {
        Bank bank = bankRepository.findById(id);
        return bankConverter.convert(bank);
    }

    @Override
    public BankResponseDto create(BankCreateDto bankCreateDto) {
        if (StringUtils.isEmpty(bankCreateDto.name())) {
            throw new RequestException(REQUEST_EXCEPTION_MESSAGE);
        }
        Bank bank = bankConverter.convert(bankCreateDto);
        Bank saved = bankRepository.create(bank);
        return bankConverter.convert(saved);
    }

    @Override
    public BankResponseDto update(BankUpdateDto bankUpdateDto) {
        if (StringUtils.isEmpty(bankUpdateDto.getName())) {
            throw new RequestException(REQUEST_EXCEPTION_MESSAGE);
        }
        Bank bank = bankConverter.convert(bankUpdateDto);
        Bank saved = bankRepository.update(bank);
        return bankConverter.convert(saved);
    }

    @Override
    public void delete(Long id) {
        bankRepository.delete(id);
    }
}
