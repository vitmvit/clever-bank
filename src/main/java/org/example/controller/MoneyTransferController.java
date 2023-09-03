package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.dto.request.MoneyOperationDto;
import org.example.service.AccountService;
import org.example.service.impl.AccountServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet
public class MoneyTransferController extends HttpServlet {
    private final AccountService accountService = new AccountServiceImpl();

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            StringBuffer jb = new StringBuffer();
            String line = null;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();
            MoneyOperationDto bankCreateDto = mapper.readValue(jb.toString(), MoneyOperationDto.class);
            accountService.moneyTransfer(bankCreateDto);
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("Money transfer error!");
        }
    }
}
