package com.educa.controller;

import com.educa.model.Curso;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cursos")
public class CursoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Lista simulada en memoria para evitar llamadas a JPA y librerías faltantes
        List<Curso> listaSimulada = new ArrayList<>();
        
        Curso c1 = new Curso();
        c1.setIdCurso(1L);
        c1.setTitulo("Introducción a Java EE 10 y Jakarta");
        c1.setDescripcion("Aprende los fundamentos de Jakarta EE, Servlets y despliegue en Tomcat.");
        c1.setTema("Programación");
        c1.setNivelDificultad("Principiante");
        c1.setPopularidad(95);
        listaSimulada.add(c1);

        Curso c2 = new Curso();
        c2.setIdCurso(2L);
        c2.setTitulo("Bases de Datos con JPA y Hibernate 6");
        c2.setDescripcion("Dominando el mapeo objeto-relacional y consultas avanzadas JPQL.");
        c2.setTema("Base de Datos");
        c2.setNivelDificultad("Intermedio");
        c2.setPopularidad(88);
        listaSimulada.add(c2);

        // Enviamos la lista a la vista JSP
        request.setAttribute("cursos", listaSimulada);
        request.getRequestDispatcher("/cursos.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}