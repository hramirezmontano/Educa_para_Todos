package com.educa.controller;

import com.educa.dao.InscripcionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/InscripcionDeleteServlet")
public class InscripcionDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private InscripcionDAO inscripcionDao;

    @Override
    public void init() {
        inscripcionDao = new InscripcionDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            inscripcionDao.eliminarInscripcion(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect(request.getContextPath() + "/home");
    }
}