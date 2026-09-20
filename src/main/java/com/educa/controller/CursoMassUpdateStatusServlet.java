package com.educa.controller;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "CursoMassUpdateStatusServlet", urlPatterns = {"/CursoMassUpdateStatusServlet"})
public class CursoMassUpdateStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
            // 1. Capturar los parámetros usando los nombres exactos del formulario JSP
        String popularidadStr = request.getParameter("popularidad_max"); 
        String estadoStr = request.getParameter("nuevo_estado");             

        EntityManagerFactory emf = (EntityManagerFactory) getServletContext().getAttribute("emf");
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("EducaParaTodosPU");
        }

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            int popMax = Integer.parseInt(popularidadStr);
            
            // 2. Si se selecciona "INACTIVO" o "SUSPENDIDO", el campo activo pasa a ser false (0)
            // (Ajusta esta lógica si consideras que alguno de ellos debería ser true)
            boolean nuevoActivo = "ACTIVO".equalsIgnoreCase(estadoStr);
            
            tx.begin();
            
            // 3. Consulta JPQL actualizada para modificar 'c.activo'
            String jpql = "UPDATE Curso c SET c.activo = :activo WHERE c.popularidad <= :popMax";
            
            int actualizados = em.createQuery(jpql)
                                .setParameter("activo", nuevoActivo)
                                .setParameter("popMax", popMax)
                                .executeUpdate();
                                
            tx.commit();
            
            response.sendRedirect(request.getContextPath() + "/home?mensaje=Se actualizaron " + actualizados + " cursos correctamente.");
            
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/home?error=Error en actualización masiva: " + e.getMessage());
        } finally {
            em.close();
        }


    }
}