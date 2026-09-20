package com.educa.controller;

import com.educa.model.Curso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/CursoSearchServlet")
public class CursoSearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tema = request.getParameter("tema");
        String nivel = request.getParameter("nivel");
        String popularidadStr = request.getParameter("popularidad");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EducaParaTodosPU");
        EntityManager em = emf.createEntityManager();

        try {
            // Construcción dinámica de la consulta JPQL
            StringBuilder jpql = new StringBuilder("SELECT c FROM Curso c WHERE 1=1");

            if (tema != null && !tema.trim().isEmpty()) {
                jpql.append(" AND LOWER(c.tema) LIKE LOWER(:tema)");
            }
            if (nivel != null && !nivel.trim().isEmpty()) {
                jpql.append(" AND c.nivelDificultad = :nivel");
            }
            if (popularidadStr != null && !popularidadStr.trim().isEmpty()) {
                jpql.append(" AND c.popularidad >= :popularidad");
            }

            TypedQuery<Curso> query = em.createQuery(jpql.toString(), Curso.class);

            // Asignación de parámetros si fueron enviados
            if (tema != null && !tema.trim().isEmpty()) {
                query.setParameter("tema", "%" + tema.trim() + "%");
            }
            if (nivel != null && !nivel.trim().isEmpty()) {
                query.setParameter("nivel", nivel);
            }
            if (popularidadStr != null && !popularidadStr.trim().isEmpty()) {
                query.setParameter("popularidad", Integer.parseInt(popularidadStr));
            }

            List<Curso> resultados = query.getResultList();

            // Pasamos los resultados y el estado de la pestaña activa a la vista
            request.setAttribute("cursosFiltrados", resultados);
            request.setAttribute("totalCursos", em.createQuery("SELECT COUNT(c) FROM Curso c", Long.class).getSingleResult());
            request.setAttribute("totalUsuarios", em.createQuery("SELECT COUNT(u) FROM Usuario u", Long.class).getSingleResult());
            
            // Redirigimos al JSP principal indicando que abra la pestaña de búsqueda
            request.getRequestDispatcher("/WEB-INF/index.jsp?tab=busqueda-cursos").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al filtrar los cursos: " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }
    }
}