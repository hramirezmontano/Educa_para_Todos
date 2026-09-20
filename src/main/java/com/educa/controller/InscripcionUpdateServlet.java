package com.educa.controller;

import com.educa.model.Inscripcion;
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

@WebServlet("/InscripcionUpdateServlet")
public class InscripcionUpdateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 1. Recoger los parámetros del formulario JSP
        String idInscripcionStr = request.getParameter("id_inscripcion");
        String cursoIdStr = request.getParameter("nuevo_id_curso");
        String estado = request.getParameter("estado");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EducaParaTodosPU");        
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // 2. Convertir el ID y buscar la inscripción existente
            Long idInscripcion = Long.parseLong(idInscripcionStr);
            Inscripcion inscripcion = em.find(Inscripcion.class, idInscripcion);

            if (inscripcion != null) {
                // 3. Actualizar el estado si viene informado
                if (estado != null && !estado.isEmpty()) {
                    inscripcion.setEstado(estado);
                }

                // 4. Actualizar el curso si se seleccionó uno nuevo
                if (cursoIdStr != null && !cursoIdStr.isEmpty()) {
                    Long cursoId = Long.parseLong(cursoIdStr);
                    Curso nuevoCurso = em.find(Curso.class, cursoId);
                    if (nuevoCurso != null) {
                        inscripcion.setCurso(nuevoCurso);
                    }
                }

                em.merge(inscripcion);
                em.getTransaction().commit();
            }

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }

        // 5. Redirigir de vuelta al panel principal (HomeServlet)
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/home"));
    }
}