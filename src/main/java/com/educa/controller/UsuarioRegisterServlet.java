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
import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/UsuarioRegisterServlet")
public class UsuarioRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordHasheada = BCrypt.hashpw(password, BCrypt.gensalt());


        String rol = request.getParameter("rol");

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(passwordHasheada);
        nuevoUsuario.setRol(rol != null ? rol : "ESTUDIANTE");
        nuevoUsuario.setFechaRegistro(LocalDate.now());
        nuevoUsuario.setEstado("ACTIVO");

        try {
            usuarioDAO.registrarUsuario(nuevoUsuario);
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("home");
    }
}