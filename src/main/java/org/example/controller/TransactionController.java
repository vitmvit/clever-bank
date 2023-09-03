package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.dto.response.TransactionResponseDto;
import org.example.model.dto.response.TransactionUpdateDto;
import org.example.service.TransactionService;
import org.example.service.impl.TransactionServiceImpl;

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
public class TransactionController extends HttpServlet {

    private final TransactionService transactionService = new TransactionServiceImpl();

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String id = request.getParameter("id");
            TransactionResponseDto object = transactionService.findById(Long.valueOf(id));
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(object);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("Transaction not found");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        try {
            StringBuffer jb = new StringBuffer();
            String line = null;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
            ObjectMapper mapper = new ObjectMapper();
            TransactionUpdateDto transactionUpdateDto = mapper.readValue(jb.toString(), TransactionUpdateDto.class);
            transactionUpdateDto.setId(Long.valueOf(id));
            TransactionResponseDto transactionResponseDto = transactionService.update(transactionUpdateDto);
            String json = mapper.writeValueAsString(transactionResponseDto);
            PrintWriter out = response.getWriter();
            out.print(json);
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("Account updated error!");
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");

            transactionService.delete(Long.valueOf(id));

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter writer = response.getWriter();
            writer.println("Transaction is deleted");

        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("Transaction deleted error!");
        }
    }
}
