package com.educa.controller;

import com.educa.dao.CursoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CursoMassDeleteServlet")
public class CursoMassDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CursoDAO cursoDAO = new CursoDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String popMaxStr = request.getParameter("popularidad_max");

            if (popMaxStr != null && !popMaxStr.isEmpty()) {
                int popularidadMaxima = Integer.parseInt(popMaxStr);
                
                // Ejecutar la operación DELETE masiva en el DAO
                int eliminados = cursoDAO.eliminarCursosBajaPopularidad(popularidadMaxima);
                System.out.println("Cursos eliminados masivamente: " + eliminados);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Redireccionar de vuelta al panel principal
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);    }
}