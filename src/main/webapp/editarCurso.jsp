<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.educa.model.Curso" %>
<%
    Curso curso = (Curso) request.getAttribute("curso");
    if (curso == null) {
        response.sendRedirect("home");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Modificar Curso - Educa Para Todos</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f4f7f6; margin: 0; padding: 40px; }
        .container { max-width: 500px; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 10px rgba(0,0,0,0.1); margin: auto; }
        h2 { color: #333; margin-bottom: 20px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; font-weight: bold; margin-bottom: 5px; color: #555; }
        input[type="text"], input[type="number"], select { width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        .btn-container { margin-top: 20px; display: flex; justify-content: space-between; }
        .btn { padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; text-decoration: none; color: white; font-weight: bold; }
        .btn-primary { background-color: #337ab7; }
        .btn-secondary { background-color: #d9534f; }
    </style>
</head>
<body>

<div class="container">
    <h2>Modificar Curso</h2>
    <form action="editarCurso" method="POST">
        <!-- ID oculto para saber qué curso estamos actualizando -->
        <input type="hidden" name="idCurso" value="<%= curso.getIdCurso() %>">

        <div class="form-group">
            <label for="titulo">Título del Curso:</label>
            <input type="text" id="titulo" name="titulo" value="<%= curso.getTitulo() %>" required>
        </div>

        <div class="form-group">
            <label for="tema">Tema:</label>
            <input type="text" id="tema" name="tema" value="<%= curso.getTema() %>" required>
        </div>

        <div class="form-group">
            <label for="nivelDificultad">Nivel de Dificultad:</label>
            <select id="nivelDificultad" name="nivelDificultad">
                <option value="Básico" <%= "Básico".equals(curso.getNivelDificultad()) ? "selected" : "" %>>Básico</option>
                <option value="Intermedio" <%= "Intermedio".equals(curso.getNivelDificultad()) ? "selected" : "" %>>Intermedio</option>
                <option value="Avanzado" <%= "Avanzado".equals(curso.getNivelDificultad()) ? "selected" : "" %>>Avanzado</option>
            </select>
        </div>

        <div class="form-group">
            <label for="popularidad">Popularidad (Puntos):</label>
            <input type="number" id="popularidad" name="popularidad" value="<%= curso.getPopularidad() %>" required>
        </div>

        <div class="btn-container">
            <button type="submit" class="btn btn-primary">Guardar Cambios</button>
            <a href="home" class="btn btn-secondary">Cancelar</a>
        </div>
    </form>
</div>

</body>
</html>