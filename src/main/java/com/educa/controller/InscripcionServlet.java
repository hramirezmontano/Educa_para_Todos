package com.educa.controller;

import com.educa.dao.InscripcionDAO;
import com.educa.model.Inscripcion;
import com.educa.model.Usuario;
import com.educa.model.Curso;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/InscripcionServlet")
public class InscripcionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private InscripcionDAO inscripcionDAO = new InscripcionDAO();

    // 1. Añadimos el doGet para listar las inscripciones si se le llama directamente
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
        List<Inscripcion> inscripciones = inscripcionDAO.listarInscripciones();
        request.setAttribute("inscripciones", inscripciones); // <-- Verifica que la clave sea exactamente "inscripciones"
        request.getRequestDispatcher("index.jsp").forward(request, response); 
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Capturar los valores enviados desde el formulario HTML
        String idUsuarioStr = request.getParameter("id_usuario");
        String idCursoStr = request.getParameter("id_curso");
        String fechaStr = request.getParameter("fecha_inscripcion");

        if (idUsuarioStr != null && idCursoStr != null && fechaStr != null) {
            Long idUsuario = Long.parseLong(idUsuarioStr);
            Long idCurso = Long.parseLong(idCursoStr);
            LocalDate fechaInscripcion = LocalDate.parse(fechaStr);

            // 2. Crear instancias de referencia con los IDs capturados
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(idUsuario);

            Curso curso = new Curso();
            curso.setIdCurso(idCurso);

            // 3. Construir el objeto Inscripcion
            Inscripcion inscripcion = new Inscripcion();
            inscripcion.setUsuario(usuario);
            inscripcion.setCurso(curso);
            inscripcion.setFechaInscripcion(fechaInscripcion);
            inscripcion.setEstado("ACTIVO");

            // 4. Persistir a través del DAO
            inscripcionDAO.registrarInscripcion(inscripcion);
        }

        // 5. Redireccionar de vuelta al panel principal
        response.sendRedirect(request.getContextPath() + "/home?tab=inscripcion");
    }
}