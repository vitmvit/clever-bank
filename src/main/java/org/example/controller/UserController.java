package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.dto.request.UserCreateDto;
import org.example.model.dto.response.UserResponseDto;
import org.example.model.dto.response.UserUpdateDto;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet
public class UserController extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String id = request.getParameter("id");
            UserResponseDto object = userService.findById(Long.valueOf(id));
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(object);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(json);
            out.flush();
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("User not found");
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
            UserCreateDto userCreateDto = mapper.readValue(jb.toString(), UserCreateDto.class);
            UserResponseDto userResponseDto = userService.create(userCreateDto);
            String json = mapper.writeValueAsString(userResponseDto);
            PrintWriter out = response.getWriter();
            out.print(json);
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("User created error!");
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
            UserUpdateDto userUpdateDto = mapper.readValue(jb.toString(), UserUpdateDto.class);
            userUpdateDto.setId(Long.valueOf(id));
            UserResponseDto accountResponseDto = userService.update(userUpdateDto);
            String json = mapper.writeValueAsString(accountResponseDto);
            PrintWriter out = response.getWriter();
            out.print(json);
        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("User updated error!");
        }
    }

    //    http://localhost:8080/api/users?id=45
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String id = request.getParameter("id");

            userService.delete(Long.valueOf(id));

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter writer = response.getWriter();
            writer.println("User is deleted");

        } catch (Exception e) {
            PrintWriter out = response.getWriter();
            out.print("User deleted error!");
        }
    }
}