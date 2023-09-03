package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.dto.request.AccountCreateDto;
import org.example.model.dto.response.AccountResponseDto;
import org.example.model.dto.response.AccountUpdateDto;
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
public class AccountController extends HttpServlet {
    private final AccountService accountService = new AccountServiceImpl();

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String id = request.getParameter("id");
            AccountResponseDto object = accountService.findById(Long.valueOf(id));
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(object);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("Account not found");
        }
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
            AccountCreateDto accountCreateDto = mapper.readValue(jb.toString(), AccountCreateDto.class);
            AccountResponseDto accountResponseDto = accountService.create(accountCreateDto);
            String json = mapper.writeValueAsString(accountResponseDto);
            PrintWriter out = response.getWriter();
            out.print(json);
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("Account created error!");
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
            AccountUpdateDto accountUpdateDto = mapper.readValue(jb.toString(), AccountUpdateDto.class);
            accountUpdateDto.setId(Long.valueOf(id));
            AccountResponseDto accountResponseDto = accountService.update(accountUpdateDto);
            String json = mapper.writeValueAsString(accountResponseDto);
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

            accountService.delete(Long.valueOf(id));

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter writer = response.getWriter();
            writer.println("Account is deleted");

        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("Account deleted error!");
        }
    }
}
