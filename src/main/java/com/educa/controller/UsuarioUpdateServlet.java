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

@WebServlet("/UsuarioUpdateServlet")
public class UsuarioUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String rol = request.getParameter("rol");
        String estado = request.getParameter("estado");

        if (idStr != null && !idStr.isEmpty()) {
            Long id = Long.parseLong(idStr);
            
            // RECUERDA: Cambiar "TuUnidadDePersistencia" por el nombre real de tu persistence.xml
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("EducaParaTodosPU");
            EntityManager em = emf.createEntityManager();
            
            try {
                em.getTransaction().begin();
                com.educa.model.Usuario usuario = em.find(com.educa.model.Usuario.class, id);
                if (usuario != null) {
                    if (nombre != null && !nombre.isEmpty()) usuario.setNombre(nombre);
                    if (email != null && !email.isEmpty()) usuario.setEmail(email);
                    if (rol != null && !rol.isEmpty()) usuario.setRol(rol);
                    if (estado != null && !estado.isEmpty()) usuario.setEstado(estado);
                    
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
        
        // Redirigir de regreso al panel manteniendo la pestaña de actualización o listado de usuarios
        response.sendRedirect(request.getContextPath() + "/home?tab=usuarios-lista");
    }
}