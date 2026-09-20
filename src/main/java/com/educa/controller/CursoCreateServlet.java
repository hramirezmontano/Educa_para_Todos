package com.educa.controller;

import com.educa.dao.CursoDAO;
import com.educa.model.Curso;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/CursoCreateServlet")
public class CursoCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CursoDAO cursoDAO = new CursoDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Recibir los datos del formulario JSP
        String titulo = request.getParameter("titulo");
        String tema = request.getParameter("tema");
        String nivelDificultad = request.getParameter("nivelDificultad");

        // 2. Crear el objeto Curso y asignar valores
        Curso nuevoCurso = new Curso();
        nuevoCurso.setTitulo(titulo);
        nuevoCurso.setTema(tema);
        nuevoCurso.setNivelDificultad(nivelDificultad);
        nuevoCurso.setFechaCreacion(LocalDate.now());
        nuevoCurso.setActivo(true);
        nuevoCurso.setPopularidad(0);

        // 3. Guardar en la base de datos usando el DAO y Hibernate
        try {
            cursoDAO.crearCurso(nuevoCurso);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 4. Redirigir de vuelta al home para ver el listado actualizado
        response.sendRedirect("home");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("home");
    }
}