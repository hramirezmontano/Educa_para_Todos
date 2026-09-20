package com.educa.controller;

import com.educa.model.Curso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/CursoUpdateServlet")
public class CursoUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Configurar codificación para evitar problemas con tildes o eñes
        request.setCharacterEncoding("UTF-8");
        
        // 1. Recibir los parámetros del formulario de edición de curso
        String idStr = request.getParameter("id");
        String titulo = request.getParameter("titulo");
        String tema = request.getParameter("tema");
        String nivelDificultad = request.getParameter("nivelDificultad");
        String popularidadStr = request.getParameter("popularidad");
        String estado = request.getParameter("activo");        
        
        if (idStr != null && !idStr.isEmpty()) {
            Long id = Long.parseLong(idStr);
            
            // 2. Conectarse a la BD usando tu EntityManagerFactory con el nombre correcto
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("EducaParaTodosPU");
            EntityManager em = emf.createEntityManager();
            
            try {
                em.getTransaction().begin();
                Curso curso = em.find(Curso.class, id);
                if (curso != null) {
                    curso.setTitulo(titulo);
                    curso.setTema(tema);
                    curso.setNivelDificultad(nivelDificultad);
                    
                    if (popularidadStr != null && !popularidadStr.isEmpty()) {
                        curso.setPopularidad(Integer.parseInt(popularidadStr));
                    }
                    
                    if (estado != null && !estado.isEmpty()) {
                        curso.setEstado(estado);
                    }
                    
                    em.merge(curso); // Actualiza el curso en la base de datos
                }
                em.getTransaction().commit();
            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                e.printStackTrace();
            } finally {
                if (em != null && em.isOpen()) em.close();
                if (emf != null && emf.isOpen()) emf.close();
            }
        }
        
        // 3. Redirigir de vuelta a la sección de lista de cursos conservando la pestaña
        response.sendRedirect(request.getContextPath() + "/home?tab=lista-cursos");
    }
}