package com.educa.controller;

import com.educa.model.Curso;
import com.educa.model.Inscripcion;
import com.educa.model.Usuario;
import com.educa.dao.CursoDAO;
import com.educa.dao.InscripcionDAO;
import com.educa.dao.UsuarioDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // <-- No olvides importar HttpSession
import java.io.IOException;
import java.util.List;

@WebServlet(name = "HomeServlet", urlPatterns = {"/home", ""})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CursoDAO cursoDAO = new CursoDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        InscripcionDAO inscripcionDAO = new InscripcionDAO();
        
        List<Curso> listaCursos = cursoDAO.listarTodos();
        List<Usuario> listaUsuarios = usuarioDAO.listarTodos();

        List<Inscripcion> listaInscripciones = inscripcionDAO.listarInscripciones();
        request.setAttribute("inscripciones", listaInscripciones);

        
        // 1. OBTENER EL USUARIO DE LA SESIÓN ACTUAL
        HttpSession session = request.getSession();
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        
    
        // 2. ENVIARLO A LA VISTA (JSP)
        request.setAttribute("usuarioLogueado", usuarioLogueado);
        
        request.setAttribute("cursos", listaCursos);
        request.setAttribute("usuarios", listaUsuarios);
        request.setAttribute("totalCursos", (listaCursos != null) ? listaCursos.size() : 0);
        request.setAttribute("totalUsuarios", (listaUsuarios != null) ? listaUsuarios.size() : 0);
        request.setAttribute("totalLecciones", 45);
        
        request.getRequestDispatcher("/WEB-INF/index.jsp").forward(request, response);
    }
}