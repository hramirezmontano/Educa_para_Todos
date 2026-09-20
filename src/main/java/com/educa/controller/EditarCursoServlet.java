package com.educa.controller;

import com.educa.dao.CursoDAO;
import com.educa.model.Curso;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "EditarCursoServlet", urlPatterns = {"/editarCurso"})
public class EditarCursoServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                Long id = Long.parseLong(idParam);
                CursoDAO cursoDAO = new CursoDAO();
                Curso curso = cursoDAO.buscarPorId(id);
                
                if (curso != null) {
                    request.setAttribute("curso", curso);
                    // Redirige a una vista o modal de edición (ej: editarCurso.jsp)
                    request.getRequestDispatcher("/editarCurso.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("home");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(request.getParameter("idCurso"));
            String titulo = request.getParameter("titulo");
            String tema = request.getParameter("tema");
            String nivelDificultad = request.getParameter("nivelDificultad");
            int popularidad = Integer.parseInt(request.getParameter("popularidad"));

            CursoDAO cursoDAO = new CursoDAO();
            Curso curso = cursoDAO.buscarPorId(id);
            
            if (curso != null) {
                curso.setTitulo(titulo);
                curso.setTema(tema);
                curso.setNivelDificultad(nivelDificultad);
                curso.setPopularidad(popularidad);
                
                cursoDAO.actualizar(curso);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("home");
    }
}