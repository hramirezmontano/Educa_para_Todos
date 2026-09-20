package com.educa.controller;

import com.educa.dao.CursoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EliminarCursoServlet", urlPatterns = {"/eliminarCurso"})
public class EliminarCursoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                Long id = Long.parseLong(idParam);
                CursoDAO cursoDAO = new CursoDAO();
                cursoDAO.eliminarPorId(id);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        // Redirige de vuelta al home/panel principal
        response.sendRedirect("home");
    }
}