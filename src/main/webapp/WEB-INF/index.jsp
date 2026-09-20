<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.educa.model.Curso" %>
<%@ page import="com.educa.model.Usuario" %>
<%@ page import="com.educa.model.Inscripcion" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Educa para Todos - Panel CRUD Académico</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

    <div class="container">
        <h1>Plataforma Académica - Educa para Todos</h1>
        <p>Panel de Control y Gestión Integral</p>

        <!-- Navegación por Pestañas -->
        <div class="nav-tabs">
            <button id="tab-dashboard" class="active-tab" onclick="cambiarVista('dashboard')">📊 Dashboard</button>
            <button id="tab-crear" onclick="cambiarVista('crear-curso')">➕ Registrar Curso</button>
            <button id="tab-cursos-lista" onclick="cambiarVista('cursos-lista')">📚 Lista-Modifica/Elimina Curso</button>
            <button id="tab-usuario-register" onclick="cambiarVista('usuario-register')">👤 Registrar Usuario</button>
            <button id="tab-usuarios-lista" onclick="cambiarVista('usuarios-lista')">👥 Lista-Modifica/Elimina Usuario</button>
            <button id="tab-inscripcion" onclick="cambiarVista('inscripcion')">🔗 Asignar Curso</button>
            <button id="tab-gestionar-inscripcion" onclick="cambiarVista('gestionar-inscripcion')">⚙️ Gestionar Inscripciones</button>
            <button id="tab-busqueda" onclick="cambiarVista('busqueda-cursos')">🔍 Buscar Cursos</button>
            <button id="tab-masivos" onclick="cambiarVista('masivos')">🛠️ Operaciones y Filtros</button>
        </div>

        <!-- VISTA 1: DASHBOARD (Resumen y Estadísticas) -->
        <section id="dashboard" class="section-panel active-section">
            <div class="stats">
                <div class="card">
                    <h3><%= request.getAttribute("totalCursos") != null ? request.getAttribute("totalCursos") : 0 %></h3>
                    <p>Cursos Activos</p>
                </div>
                <div class="card card-orange">
                    <h3><%= request.getAttribute("totalUsuarios") != null ? request.getAttribute("totalUsuarios") : 0 %></h3>
                    <p>Usuarios Registrados</p>
                </div>
            </div>
            <div class="panel-box">
                <h2>Bienvenido al Panel de Control</h2>
                <p>Utiliza las pestañas superiores para navegar entre las diferentes opciones de gestión académica de la plataforma.</p>
            </div>
        </section>

        <!-- VISTA: REGISTRAR CURSO -->
        <section id="crear-curso" class="section-panel" style="display: none;">
            <div class="panel-box">
                <h2>Registrar Nuevo Curso</h2>
                <form action="${pageContext.request.contextPath}/CursoCreateServlet" method="POST">
                    <div class="form-group">
                        <label for="titulo">Título del Curso:</label>
                        <input type="text" id="titulo" name="titulo" placeholder="Ej. Hibernate 6 Avanzado" required>
                    </div>
                    <div class="form-group">
                        <label for="tema">Tema:</label>
                        <input type="text" id="tema" name="tema" placeholder="Ej. Base de Datos" required>
                    </div>
                    <div class="form-group">
                        <label for="nivel">Nivel de Dificultad:</label>
                        <select id="nivel" name="nivelDificultad">
                            <option value="Basico">Básico</option>
                            <option value="Intermedio">Intermedio</option>
                            <option value="Avanzado">Avanzado</option>
                        </select>
                    </div>
                    <button type="submit" class="btn-submit">Guardar Curso en BD</button>
                </form>
            </div>
        </section>

        <!-- VISTA DINÁMICA: LISTAR, MODIFICAR Y ELIMINAR CURSOS -->
        <section id="cursos-lista" class="section-panel" style="display: none;">
            <div class="panel-box">
                <h2>Gestión de Cursos (Lista, Modifica y Elimina)</h2>
                <p>Visualiza el directorio de cursos registrados en el sistema, modifica sus datos o elimínalos si es necesario.</p>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Título del Curso</th>
                            <th>Tema</th>
                            <th>Nivel Dificultad</th>
                            <th>Popularidad</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
<tbody>
        <% 
            List<Curso> listaCursosGestion = (List<Curso>) request.getAttribute("cursos");
            if (listaCursosGestion != null && !listaCursosGestion.isEmpty()) {
                for (Curso c : listaCursosGestion) {
        %>
                <tr>
                    <td><%= c.getIdCurso() %></td>
                    <td><%= c.getTitulo() %></td>
                    <td><%= c.getTema() %></td>
                    <td><%= c.getNivelDificultad() %></td>
                    <td>
                        <span class="badge" style="background-color: #5cb85c; color: white; padding: 4px 8px; border-radius: 4px;">
                            <%= c.getPopularidad() %> pts
                        </span>
                    </td>
                    <td>
                        <% if (c.getActivo()) { %>
                            <span style="background-color: #d4edda; color: #155724; padding: 4px 8px; border-radius: 4px; font-weight: bold; font-size: 0.9em;">ACTIVO</span>
                        <% } else { %>
                            <span style="background-color: #f8d7da; color: #721c24; padding: 4px 8px; border-radius: 4px; font-weight: bold; font-size: 0.9em;">INACTIVO</span>
                        <% } %>
                    </td>
                    <td>
                        <button type="button" class="btn-edit" 
                            style="background:#f0ad4e; color:white; border:none; padding:5px 10px; border-radius:4px; cursor:pointer;"
                            onclick="cargarDatosEdicionCurso('<%= c.getIdCurso() %>', '<%= c.getTitulo() %>', '<%= c.getTema() %>', '<%= c.getNivelDificultad() %>', '<%= c.getPopularidad() %>', '<%= c.getActivo() %>')">
                            Modificar
                        </button>
                        
                        <form action="${pageContext.request.contextPath}/CursoDeleteServlet" method="POST" style="display:inline;">
                            <input type="hidden" name="id" value="<%= c.getIdCurso() %>">
                            <button type="submit" class="btn-delete" style="background:#d9534f; color:white; border:none; padding:5px 10px; border-radius:4px; cursor:pointer;" onclick="return confirm('¿Seguro que deseas eliminar este curso?');">Eliminar</button>
                        </form>
                    </td>
                </tr>
        <% 
                }
            } else { 
        %>
                <tr>
                    <td colspan="7" style="text-align:center; padding: 20px;">
                        No hay cursos registrados.
                    </td>
                </tr>
        <% } %>
    </tbody>

                </table>
            </div>
        </section>

        <!-- VISTA: MODIFICAR CURSO -->
        <section id="curso-update" class="section-panel" style="display: none;">
            <div class="panel-box">
                <h2>Modificar Curso</h2>
            <form action="${pageContext.request.contextPath}/CursoUpdateServlet" method="POST">
                <!-- ID oculto del curso -->
                <input type="hidden" id="edit_id_curso" name="id">

                <div class="form-group">
                    <label for="edit_titulo">Título del Curso:</label>
                    <input type="text" id="edit_titulo" name="titulo" required>
                </div>

                <div class="form-group">
                    <label for="edit_tema">Tema:</label>
                    <input type="text" id="edit_tema" name="tema" required>
                </div>

                <div class="form-group">
                    <label for="edit_nivel">Nivel de Dificultad:</label>
                    <select id="edit_nivel" name="nivelDificultad">
                        <option value="Basico">Básico</option>
                        <option value="Intermedio">Intermedio</option>
                        <option value="Avanzado">Avanzado</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="edit_popularidad">Popularidad:</label>
                    <input type="number" id="edit_popularidad" name="popularidad">
                </div>

                <!-- AQUÍ ESTÁ EL SELECT DEL ESTADO QUE FALTABA -->
                <div class="form-group">
                    <label for="edit_activo">Estado del Curso:</label>
                    <select id="edit_activo" name="activo">
                        <option value="ACTIVO">ACTIVO</option>
                        <option value="INACTIVO">INACTIVO</option>
                    </select>
                </div>

                <button type="submit" class="btn-submit">Guardar Cambios</button>
            </form>

            </div>
        </section>

        <!-- VISTA: REGISTRAR USUARIO -->
        <section id="usuario-register" class="section-panel" style="display: none;">
            <div class="panel-box">
                <h2>Registrar Nuevo Usuario</h2>
                <form action="${pageContext.request.contextPath}/UsuarioRegisterServlet" method="POST">
                    <div class="form-group">
                        <label>Nombre Completo:</label>
                        <input type="text" name="nombre" required>
                    </div>
                    <div class="form-group">
                        <label>Correo Electrónico:</label>
                        <input type="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label>Contraseña:</label>
                        <input type="password" name="password" required>
                    </div>
                    <div class="form-group">
                        <label>Rol:</label>
                        <select name="rol">
                            <option value="ESTUDIANTE">Estudiante</option>
                            <option value="PROFESOR">Profesor</option>
                            <option value="ADMIN">Administrador</option>
                        </select>
                    </div>
                    <button type="submit" class="btn-submit">Guardar Usuario en BD</button>
                </form>
            </div>
        </section>

        <!-- VISTA DINÁMICA: LISTAR, MODIFICAR Y ELIMINAR USUARIOS -->
        <section id="usuarios-lista" class="section-panel" style="display: none;">
            <div class="panel-box">
                <h2>Gestión de Usuarios (Lista, Modifica y Elimina)</h2>
                <p>Visualiza el directorio de usuarios registrados en el sistema, modifica sus datos o elimina cuentas si es necesario.</p>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Email</th>
                            <th>Rol</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            List<Usuario> listaUsuarios = (List<Usuario>) request.getAttribute("usuarios");
                            if (listaUsuarios != null && !listaUsuarios.isEmpty()) {
                                for (Usuario u : listaUsuarios) {
                        %>
                                    <tr>
                                        <td><%= u.getIdUsuario() %></td>
                                        <td><%= u.getNombre() %></td>
                                        <td><%= u.getEmail() %></td>
                                        <td><%= u.getRol() %></td>
                                        <td>
                                            <span class="badge" style="background-color: #5cb85c;">
                                                <%= u.getEstado() %>
                                            </span>
                                        </td>
                                        <td>
                                            <button type="button" class="btn-edit" 
                                                style="background:#f0ad4e; color:white; border:none; padding:5px 10px; border-radius:4px; cursor:pointer;"
                                                onclick="cargarDatosEdicion('<%= u.getIdUsuario() %>', '<%= u.getNombre() %>', '<%= u.getEmail() %>', '<%= u.getRol() %>', '<%= u.getEstado() %>')">
                                                Modificar
                                            </button>

                                            <form action="${pageContext.request.contextPath}/UsuarioDeleteServlet" method="POST" style="display:inline;">
                                                <input type="hidden" name="id" value="<%= u.getIdUsuario() %>">
                                                <button type="submit" class="btn-delete" style="background:#d9534f; color:white; border:none; padding:5px 10px; border-radius:4px; cursor:pointer;" onclick="return confirm('¿Seguro que deseas eliminar este usuario?');">Eliminar</button>
                                            </form>
                                        </td>
                                    </tr>
                        <% 
                                }
                            } else { 
                        %>
                            <tr>
                                <td colspan="6" style="text-align:center; padding: 20px;">
                                    No hay usuarios registrados o debes ingresar a través de la ruta del Servlet: 
                                    <a href="home"><strong>/home</strong></a>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </section>

        <!-- VISTA: MODIFICAR / ACTUALIZAR USUARIO -->
        <section id="usuario-update" class="section-panel" style="display: none;">
            <div class="panel-box">
                <h2>Actualizar Datos de Usuario</h2>
                <p>Modifica la información o el estado de un usuario existente en la base de datos.</p>
                <form action="${pageContext.request.contextPath}/UsuarioUpdateServlet" method="POST">
                    <div class="form-group">
                        <label for="id_usuario_edit">ID del Usuario a Modificar:</label>
                        <input type="number" id="id_usuario_edit" name="id" placeholder="Ej. 1" required readonly style="background-color: #e9ecef;">
                    </div>
                    <div class="form-group">
                        <label for="edit_nombre">Nuevo Nombre Completo:</label>
                        <input type="text" id="edit_nombre" name="nombre" placeholder="Nombre actualizado">
                    </div>
                    <div class="form-group">
                        <label for="edit_email">Nuevo Correo Electrónico:</label>
                        <input type="email" id="edit_email" name="email" placeholder="correo@educa.com">
                    </div>
                    <div class="form-group">
                        <label for="edit_rol">Rol del Usuario:</label>
                        <select id="edit_rol" name="rol">
                            <option value="ESTUDIANTE">Estudiante</option>
                            <option value="PROFESOR">Profesor</option>
                            <option value="ADMIN">Administrador</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="edit_estado">Estado de Cuenta:</label>
                        <select id="edit_estado" name="estado">
                            <option value="ACTIVO">ACTIVO</option>
                            <option value="INACTIVO">INACTIVO</option>
                            <option value="SUSPENDIDO">SUSPENDIDO</option>
                        </select>
                    </div>
                    <button type="submit" class="btn-submit">Guardar Cambios de Usuario</button>
                </form>
            </div>
        </section>

        <!-- VISTA: ASIGNAR CURSO -->
        <section id="inscripcion" class="section-panel" style="display: none;">
            <div class="panel-box">
                <h2>Asignar Curso a Usuario (Inscripción)</h2>
                <form action="${pageContext.request.contextPath}/InscripcionServlet" method="POST">
                    <div class="form-group">
                        <label for="id_usuario">Seleccionar Usuario:</label>
                        <select id="id_usuario" name="id_usuario" required>
                            <option value="" selected disabled>Elige un usuario...</option>
                            <% 
                                List<Usuario> listaUsersInsc = (List<Usuario>) request.getAttribute("usuarios");
                                if (listaUsersInsc != null) {
                                    for (Usuario u : listaUsersInsc) {
                            %>
                                        <option value="<%= u.getIdUsuario() %>"><%= u.getNombre() %> (<%= u.getEmail() %>)</option>
                            <% 
                                    }
                                } 
                            %>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="id_curso">Seleccionar Curso:</label>
                        <select id="id_curso" name="id_curso" required>
                            <option value="" selected disabled>Elige un curso activo...</option>
                            <% 
                                List<Curso> listaCursosInsc = (List<Curso>) request.getAttribute("cursos");
                                if (listaCursosInsc != null) {
                                    for (Curso c : listaCursosInsc) {
                            %>
                                        <option value="<%= c.getIdCurso() %>"><%= c.getTitulo() %></option>
                            <% 
                                    }
                                } 
                            %>
                        </select>
                    </div>

                    <div class="form-group">
                        <label for="fecha_inscripcion">Fecha de Inscripción:</label>
                        <input type="date" id="fecha_inscripcion" name="fecha_inscripcion" required>
                    </div>
                    
                    <button type="submit" class="btn-submit">Registrar Inscripción</button>
                </form>
            </div>

            <!-- Listado de Cursos Asignados (Abajo) -->
            <div class="panel-box" style="margin-top: 20px;">
                <h2>📚 Listado de Cursos Asignados</h2>
                <div class="table-responsive" style="margin-top: 15px;">
                    <table class="table table-striped table-hover align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>ID Inscripción</th>
                                <th>Estudiante</th>
                                <th>Curso Asignado</th>
                                <th>Fecha</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty inscripciones}">
                                    <c:forEach var="ins" items="${inscripciones}">
                                        <tr>
                                            <td>${ins.idInscripcion}</td>
                                            <td>${ins.usuario != null ? ins.usuario.nombre : 'Sin usuario'}</td>
                                            <td>${ins.curso != null ? ins.curso.titulo : 'Sin curso'}</td>
                                            <td>${ins.fechaInscripcion}</td>
                                            <td>${ins.estado}</td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="5" class="text-center text-muted py-3" style="text-align: center;">
                                            No hay inscripciones registradas todavía.
                                        </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>

        <!-- VISTA: GESTIONAR INSCRIPCIÓN -->
        <section id="gestionar-inscripcion" class="section-panel" style="display: none;">
            <div class="panel-box">
                <h2>Modificar Inscripción o Cambiar de Curso</h2>
                <form action="InscripcionUpdateServlet" method="POST">
                    <div class="form-group">
                        <label for="id_inscripcion_edit">ID de Inscripción a Modificar:</label>
                        <input type="number" id="id_inscripcion_edit" name="id_inscripcion" value="1" placeholder="Ej. 1" required>                    
                    </div>

                    <div class="form-group">
                        <label for="nuevo_id_curso">Nuevo Curso Asignado:</label>
                        <select id="nuevo_id_curso" name="nuevo_id_curso" required>
                            <option value="" selected disabled>Selecciona el nuevo curso...</option>
                            <% 
                                List<Curso> listaCursosGest = (List<Curso>) request.getAttribute("cursos");
                                if (listaCursosGest != null) {
                                    for (Curso c : listaCursosGest) {
                            %>
                                        <option value="<%= c.getIdCurso() %>"><%= c.getTitulo() %></option>
                            <% 
                                    }
                                } 
                            %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="nuevo_estado">Estado de la Matrícula:</label>
                        <select id="nuevo_estado" name="nuevo_estado">
                            <option value="ACTIVO">ACTIVO</option>
                            <option value="SUSPENDIDO">SUSPENDIDO</option>
                            <option value="COMPLETADO">COMPLETADO</option>
                        </select>
                    </div>
                    <button type="submit" class="btn-submit">Actualizar Inscripción</button>
                </form>
            </div>

        <div style="margin-top: 30px;">
            <h3>Listado de Inscripciones Registradas</h3>
            <table border="1" style="width:100%; border-collapse: collapse; margin-top: 10px;">
                <thead>
                    <tr style="background-color: #343a40; color: white; text-align: left;">
                        <th style="padding: 10px;">ID</th>
                        <th style="padding: 10px;">Usuario / Estudiante</th>
                        <th style="padding: 10px;">Curso Asignado</th>
                        <th style="padding: 10px;">Estado</th>
                        <th style="padding: 10px; text-align: center;">Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<Inscripcion> listaInscripciones = (List<Inscripcion>) request.getAttribute("inscripciones");
                        if (listaInscripciones != null && !listaInscripciones.isEmpty()) {
                            for (Inscripcion ins : listaInscripciones) {
                                String idUsr = (ins.getUsuario() != null) ? String.valueOf(ins.getUsuario().getIdUsuario()) : "";
                                String idCur = (ins.getCurso() != null) ? String.valueOf(ins.getCurso().getIdCurso()) : "";
                                String nombreUsr = (ins.getUsuario() != null) ? ins.getUsuario().getNombre() : "Sin Usuario";                              
                                String tituloCur = (ins.getCurso() != null) ? ins.getCurso().getTitulo() : "Sin Curso";
                    %>
                            <tr>
                                <td style="padding: 8px;"><%= ins.getIdInscripcion() %></td>
                                <td style="padding: 8px;"><%= nombreUsr %></td>
                                <td style="padding: 8px;"><%= tituloCur %></td>
                                <td style="padding: 8px;"><%= ins.getEstado() %></td>
                                
                                <td style="padding: 8px; text-align: center;">
                                    <button type="button" class="btn-edit" 
                                        style="background:#f0ad4e; color:white; border:none; padding:5px 10px; border-radius:4px; cursor:pointer;"
                                        data-id="<%= ins.getIdInscripcion() %>"
                                        data-usuario="<%= idUsr %>"
                                        data-curso="<%= idCur %>"
                                        data-estado="<%= ins.getEstado() %>"
                                        onclick="cargarDatosEdicionInscripcionDesdeDataset(this)">
                                        Modificar
                                    </button>
                                    
                                    <form action="${pageContext.request.contextPath}/InscripcionDeleteServlet" method="POST" style="display:inline; margin-left: 5px;">
                                        <input type="hidden" name="id" value="<%= ins.getIdInscripcion() %>">
                                        <button type="submit" class="btn-delete" style="background:#d9534f; color:white; border:none; padding:5px 10px; border-radius:4px; cursor:pointer;" onclick="return confirm('¿Seguro que deseas eliminar esta inscripción?');">Eliminar</button>
                                    </form>
                                </td>



                            </tr>
                    <% 
                            }
                        } else { 
                    %>
                            <tr>
                                <td colspan="5" style="text-align:center; padding: 20px;">
                                    No hay inscripciones registradas.
                                </td>
                            </tr>
                    <% } %>
                </tbody>
            </table>
        </div>

        </section>

        <!-- VISTA: BÚSQUEDA Y FILTRADO AVANZADO DE CURSOS -->
        <section id="busqueda-cursos" class="section-panel" style="display: none;">
            <div class="panel-box">
                <h2>Búsqueda de Cursos por Filtros</h2>
            <form action="CursoSearchServlet" method="GET">
                <div class="form-group">
                    <label for="filtro_tema">Tema del Curso:</label>
                    <select id="filtro_tema" name="tema">
                        <option value="">-- Todos los temas --</option>
                        <option value="Programacion" ${param.tema == 'Programacion' ? 'selected' : ''}>Programación</option>
                        <option value="Base de Datos" ${param.tema == 'Base de Datos' ? 'selected' : ''}>Base de Datos</option>
                        <option value="Desarrollo Web" ${param.tema == 'Desarrollo Web' ? 'selected' : ''}>Desarrollo Web</option>
                        <option value="Redes" ${param.tema == 'Redes' ? 'selected' : ''}>Redes</option>
                        <!-- Agrega aquí los demás temas que maneje tu base de datos -->
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="filtro_nivel">Nivel de Dificultad:</label>
                    <select id="filtro_nivel" name="nivel">
                        <option value="">-- Todos los niveles --</option>
                        <option value="Basico" ${param.nivel == 'Basico' ? 'selected' : ''}>Básico</option>
                        <option value="Intermedio" ${param.nivel == 'Intermedio' ? 'selected' : ''}>Intermedio</option>
                        <option value="Avanzado" ${param.nivel == 'Avanzado' ? 'selected' : ''}>Avanzado</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="filtro_pop">Popularidad Mínima (Ptos):</label>
                    <input type="number" id="filtro_pop" name="popularidad" value="${param.popularidad}" placeholder="Ej. 80">
                </div>
                
                <button type="submit" class="btn-submit">Filtrar Cursos</button>
            </form>                

            </div>

            <!-- Resultados de la Búsqueda -->
            <div class="panel-box" style="margin-top: 20px;">
                <h2>🔍 Resultados de la Búsqueda</h2>
                <div class="table-responsive" style="margin-top: 15px;">
                    <table>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Título del Curso</th>
                                <th>Tema</th>
                                <th>Nivel Dificultad</th>
                                <th>Popularidad</th>
                                <th>Estado</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty cursosFiltrados}">
                                    <c:forEach var="c" items="${cursosFiltrados}">
                                        <tr>
                                            <td>${c.idCurso}</td>
                                            <td>${c.titulo}</td>
                                            <td>${c.tema}</td>
                                            <td>${c.nivelDificultad}</td>
                                            <td>
                                                <span class="badge" style="background-color: #5cb85c;">
                                                    ${c.popularidad} pts
                                                </span>
                                            </td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${c.activo}">
                                                        <span style="color: #28a745; font-weight: bold;">ACTIVO</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span style="color: #dc3545; font-weight: bold;">INACTIVO</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="6" style="text-align:center; padding: 20px; color: #666;">
                                            Realiza una búsqueda para ver los resultados aquí.
                                        </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>
        </section>

        <!-- VISTA: OPERACIONES MASIVAS Y FILTROS -->
        <section id="masivos" class="section-panel" style="display: none;">
            <div class="panel-box">
                <h2>Gestión Avanzada y Masiva</h2>
                <hr style="margin: 20px 0; border: 0; border-top: 1px solid #ddd;">
                
                <h3>Actualizar Estado de Usuarios por Fecha</h3>
                <form action="UsuarioMassUpdateServlet" method="POST">
                    <div class="form-group">
                        <label for="fecha_corte_usu">Registrados antes de:</label>
                        <input type="date" id="fecha_corte_usu" name="fecha_corte" required>
                    </div>
                    <div class="form-group">
                        <label for="nuevo_estado_usu">Nuevo Estado:</label>
                        <select id="nuevo_estado_usu" name="nuevo_estado">
                            <option value="INACTIVO">INACTIVO</option>
                            <option value="SUSPENDIDO">SUSPENDIDO</option>
                        </select>
                    </div>
                    <button type="submit" class="btn-submit">Ejecutar Update Masivo Usuarios</button>
                </form>

                <hr style="margin: 20px 0; border: 0; border-top: 1px solid #ddd;">
                
                <h3>Inactivar Cursos por Baja Popularidad</h3>
                <form action="CursoMassUpdateStatusServlet" method="POST">
                    <div class="form-group">
                        <label for="pop_max">Popularidad menor o igual a:</label>
                        <input type="number" id="pop_max" name="popularidad_max" placeholder="Ej. 5" required>
                    </div>
                    <div class="form-group">
                        <label for="nuevo_estado_curso">Nuevo Estado del Curso:</label>
                        <select id="nuevo_estado_curso" name="nuevo_estado">
                            <option value="INACTIVO">INACTIVO</option>
                            <option value="SUSPENDIDO">SUSPENDIDO</option>
                        </select>
                    </div>
                    <button type="submit" class="btn-submit" style="background-color: #f0ad4e;">Ejecutar Inactivación Masiva de Cursos</button>
                </form>
            </div>
        </section>
    </div>

    <script>
        function cambiarVista(idSeccion) {
            // Ocultar todas las secciones
            const secciones = document.querySelectorAll('.section-panel');
            secciones.forEach(sec => sec.style.display = 'none');

            // Quitar la clase activa de todos los botones
            const botones = document.querySelectorAll('.nav-tabs button');
            botones.forEach(btn => btn.classList.remove('active-tab'));

            // Mostrar la sección seleccionada
            const seccionActiva = document.getElementById(idSeccion);
            if (seccionActiva) {
                seccionActiva.style.display = 'block';
            }

            event.target.classList.add('active-tab');
        }

        function cargarDatosEdicion(id, nombre, email, rol, estado) {
            cambiarVista('usuario-update');
            document.getElementById('id_usuario_edit').value = id;
            document.getElementById('edit_nombre').value = nombre;
            document.getElementById('edit_email').value = email;
            document.getElementById('edit_rol').value = rol;
            document.getElementById('edit_estado').value = estado;
        }

        function cargarDatosEdicionCurso(id, titulo, tema, nivel, popularidad, activo) {
                // 1. Mostrar la sección de edición
                const vistaEditar = document.getElementById('curso-update');
                if (vistaEditar) {
                    document.querySelectorAll('.section-panel').forEach(sec => sec.style.display = 'none');
                    vistaEditar.style.display = 'block';
                }

                // 2. Rellenar los inputs
                if(document.getElementById('edit_id_curso')) document.getElementById('edit_id_curso').value = id;
                if(document.getElementById('edit_titulo')) document.getElementById('edit_titulo').value = titulo;
                if(document.getElementById('edit_tema')) document.getElementById('edit_tema').value = tema;
                if(document.getElementById('edit_nivel')) document.getElementById('edit_nivel').value = nivel;
                if(document.getElementById('edit_popularidad')) document.getElementById('edit_popularidad').value = popularidad;

                // 3. Evaluar el estado de forma infalible
                const campoActivo = document.getElementById('edit_activo');
                if(campoActivo) {
                    let valorActivoStr = String(activo).trim().toLowerCase();
                    if (valorActivoStr === 'false' || valorActivoStr === 'inactivo' || valorActivoStr === '0') {
                        campoActivo.value = 'INACTIVO';
                    } else {
                        campoActivo.value = 'ACTIVO';
                    }
                }
            }

            function cargarDatosEdicionInscripcion(idInscp, idUsr, idCur, estado) {
                // Rellenar el campo oculto o visible del ID de inscripción
                document.getElementById('edit_id_inscripcion').value = idInscp;
                
                // Seleccionar automáticamente el usuario y curso en los selects
                document.getElementById('edit_usuario').value = idUsr;
                document.getElementById('edit_curso').value = idCur;
                document.getElementById('edit_estado_inscripcion').value = estado;
                
                // Opcional: desplazar la página suavemente hacia arriba donde está el formulario
                window.scrollTo({ top: 0, behavior: 'smooth' });
            }

            function cargarDatosEdicionInscripcionDesdeDataset(button) {
                const idInscp = button.getAttribute('data-id');
                const idCur = button.getAttribute('data-curso');
                const estado = button.getAttribute('data-estado');

                // Usamos los IDs exactos que están definidos en tu HTML de arriba
                document.getElementById('id_inscripcion_edit').value = idInscp;
                document.getElementById('nuevo_id_curso').value = idCur;
                document.getElementById('nuevo_estado').value = estado;

                // Desplaza la vista suavemente hacia arriba para ver el formulario lleno
                window.scrollTo({ top: 0, behavior: 'smooth' });
            }
</script>

<script>
    window.addEventListener('DOMContentLoaded', (event) => {
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.has('tema') || urlParams.has('nivel') || urlParams.has('popularidad')) {
            cambiarVista('busqueda-cursos');
        }
    });
</script>


</body>
</html>