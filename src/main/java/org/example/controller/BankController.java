package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.dto.request.BankCreateDto;
import org.example.model.dto.response.BankResponseDto;
import org.example.model.dto.response.BankUpdateDto;
import org.example.service.BankService;
import org.example.service.impl.BankServiceImpl;

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
public class BankController extends HttpServlet {

    private final BankService bankService = new BankServiceImpl();

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String id = request.getParameter("id");
            BankResponseDto object = bankService.findById(Long.valueOf(id));
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(object);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("Bank not found");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();
            BankCreateDto bankCreateDto = mapper.readValue(stringBuffer.toString(), BankCreateDto.class);
            BankResponseDto bankResponseDto = bankService.create(bankCreateDto);
            String json = mapper.writeValueAsString(bankResponseDto);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(json);
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("Bank creation error!");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        try {
            StringBuffer stringBuffer = new StringBuffer();
            String line;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                stringBuffer.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();
            BankUpdateDto bankUpdateDto = mapper.readValue(stringBuffer.toString(), BankUpdateDto.class);
            bankUpdateDto.setId(Long.valueOf(id));
            BankResponseDto accountResponseDto = bankService.update(bankUpdateDto);
            String json = mapper.writeValueAsString(accountResponseDto);
            PrintWriter out = response.getWriter();
            out.print(json);
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("Bank update error");
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String id = request.getParameter("id");
            bankService.delete(Long.valueOf(id));
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.println("Bank is deleted");
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("Bank deletion error!");
        }
    }
}
