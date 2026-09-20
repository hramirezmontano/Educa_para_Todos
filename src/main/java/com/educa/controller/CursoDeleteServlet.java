package com.educa.controller;

import com.educa.dao.CursoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CursoDeleteServlet")
public class CursoDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CursoDAO cursoDAO = new CursoDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            try {
                Long id = Long.parseLong(idStr);
                cursoDAO.eliminarPorId(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("home");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}