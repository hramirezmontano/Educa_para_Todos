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

@WebServlet("/CursoCargarServlet")
public class CursoCargarServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        
        if (idStr != null && !idStr.isEmpty()) {
            Long id = Long.parseLong(idStr);
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("EducaParaTodosPU");
            EntityManager em = emf.createEntityManager();
            
            try {
                Curso curso = em.find(Curso.class, id);
                // Guardamos el curso encontrado en la request
                request.setAttribute("cursoEditar", curso);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
                emf.close();
            }
        }
        
        // Redirigimos al home/index manteniendo la pestaña de edición activa
        request.getRequestDispatcher("/WEB-INF/index.jsp?tab=editar-curso").forward(request, response);    }
}