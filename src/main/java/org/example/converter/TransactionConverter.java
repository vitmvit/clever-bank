package org.example.converter;

import org.example.model.dto.request.MoneyOperationDto;
import org.example.model.dto.response.TransactionResponseDto;
import org.example.model.dto.response.TransactionUpdateDto;
import org.example.model.entity.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionConverter {

    public Transaction convert(MoneyOperationDto source) {
        if (source == null) {
            return null;
        }
        Transaction target = new Transaction();
        target.setBankId(source.bankId());
        target.setSenderAccountId(source.senderAccountId());
        target.setRecipientAccountId(source.recipientAccountId());
        target.setDateTransaction(source.dateTransaction());
        target.setSum(source.sum());
        return target;
    }

    public Transaction convert(TransactionUpdateDto source) {
        if (source == null) {
            return null;
        }
        Transaction target = new Transaction();
        target.setId(source.getId());
        target.setBankId(source.getBankId());
        target.setSenderAccountId(source.getSenderAccountId());
        target.setRecipientAccountId(source.getRecipientAccountId());
        target.setDateTransaction(source.getDateTransaction());
        target.setSum(source.getSum());
        return target;
    }

    public TransactionResponseDto convert(Transaction source) {
        if (source == null) {
            return null;
        }
        TransactionResponseDto target = new TransactionResponseDto();
        target.setId(source.getId());
        target.setBankId(source.getBankId());
        target.setSenderAccountId(source.getSenderAccountId());
        target.setRecipientAccountId(source.getRecipientAccountId());
        target.setDateTransaction(source.getDateTransaction());
        target.setSum(source.getSum());
        return target;
    }

    public List<TransactionResponseDto> convert(List<Transaction> source) {
        if (source == null) {
            return null;
        }
        List<TransactionResponseDto> target = new ArrayList<>(source.size());
        for (Transaction transaction : source) {
            target.add(convert(transaction));
        }
        return target;
    }
}
