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

@WebServlet("/UsuarioDeleteServlet")
public class UsuarioDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr != null) {
            Long id = Long.parseLong(idStr);
            
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("EducaParaTodosPU");
            EntityManager em = emf.createEntityManager();
            
            try {
                em.getTransaction().begin();
                com.educa.model.Usuario usuario = em.find(com.educa.model.Usuario.class, id);
                if (usuario != null) {
                    // CAMBIO CLAVE: Borrado lógico en lugar de físico (em.remove)
                    usuario.setEstado("INACTIVO"); // O "ELIMINADO" según lo que maneje tu entidad
                    em.merge(usuario);
                }
                em.getTransaction().commit();
            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                e.printStackTrace();
            } finally {
                em.close();
                emf.close();
            }
        }
        // Redirige de regreso al panel principal
        response.sendRedirect("home");
    }
}