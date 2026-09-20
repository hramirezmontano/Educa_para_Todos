package com.educa.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet("/UsuarioMassUpdateServlet")
public class UsuarioMassUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fechaCorteStr = request.getParameter("fecha_corte");
        String nuevoEstado = request.getParameter("nuevo_estado");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EducaParaTodosPU");
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            
            LocalDate fechaCorte = LocalDate.parse(fechaCorteStr);
            
            // Sentencia JPQL de actualización masiva
            String jpql = "UPDATE Usuario u SET u.estado = :estado WHERE u.fechaRegistro < :fechaCorte";
            
            int actualizados = em.createQuery(jpql)
                .setParameter("estado", nuevoEstado)
                .setParameter("fechaCorte", fechaCorte)
                .executeUpdate();
        
System.out.println("Usuarios actualizados masivamente: " + actualizados);
            
            em.getTransaction().commit();
            
            // Redirigimos de vuelta al index indicando la pestaña masivos
            response.sendRedirect("home?tab=masivos");

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en actualización masiva: " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }
    }
}