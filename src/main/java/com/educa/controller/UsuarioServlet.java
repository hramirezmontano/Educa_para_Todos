package com.educa.controller;

import com.educa.dao.UsuarioDAO;
import com.educa.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import org.mindrot.jbcrypt.BCrypt; // 1. Importamos BCrypt

@WebServlet("/usuarios")
public class UsuarioServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Redirige al index para evitar el error 405 si se accede por GET
        resp.sendRedirect("index.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String accion = req.getParameter("accion");

        if ("borradoLogicoMasivo".equals(accion)) {
            String fechaStr = req.getParameter("fechaCorte");
            LocalDate fecha = LocalDate.parse(fechaStr);

            int desactivados = usuarioDAO.borradoLogicoUsuariosMasivo(fecha);
            resp.getWriter().write("Se desactivaron lógicamente " + desactivados + " usuarios.");
        } else {
            String nombre = req.getParameter("nombre");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            
            // 2. Ciframos la contraseña antes de asignarla al modelo
            String passwordHasheada = BCrypt.hashpw(password, BCrypt.gensalt());

            Usuario u = new Usuario();
            u.setNombre(nombre);
            u.setEmail(email);
            u.setPassword(passwordHasheada); // 3. Guardamos el hash seguro
            u.setRol("ESTUDIANTE");
            u.setEstado("ACTIVO");
            u.setFechaRegistro(LocalDate.now());

            usuarioDAO.guardar(u);
            resp.sendRedirect("cursos");
        }
    }
}