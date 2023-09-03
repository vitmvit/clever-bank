package org.example.service;

import org.example.exeption.FileWriteException;
import org.example.model.dto.Receipt;

import java.io.FileWriter;
import java.io.IOException;

public class ReceiptService {

    public void createReceipt(Receipt receipt) {
        String fileName = "check/receipt_" + receipt.getNumber() + ".txt";
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write("Номер чека: " + receipt.getNumber() + "\n");
            writer.write("Тип транзакции: " + receipt.getTypeTransaction() + "\n");
            writer.write("Банк получателя: " + receipt.getRecipientBankName() + "\n");
            writer.write("Банк отправителя: " + receipt.getSenderBankName() + "\n");
            writer.write("Счет получателя: " + receipt.getRecipientAccountId() + "\n");
            writer.write("Счет отправителя: " + receipt.getSenderAccountId() + "\n");
            writer.write("Сумма: " + receipt.getSum());
            writer.flush();
        } catch (IOException ex) {
            throw new FileWriteException("Error create receipt", ex);
        }
    }
}
