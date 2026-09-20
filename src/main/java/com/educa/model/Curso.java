package com.educa.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    private Long idCurso;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "tema", length = 100)
    private String tema;

    @Column(name = "nivel_dificultad", length = 50)
    private String nivelDificultad;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @Column(name = "nivel", length = 50)
    private String nivel;

    @Column(name = "popularidad")
    private Integer popularidad = 0;

    // Campo agregado para soportar la eliminación lógica o estado del curso
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    // Relación Uno a Muchos con Leccion
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Leccion> lecciones;

    // Relación Uno a Muchos con Inscripcion
    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Inscripcion> inscripciones;

    public Curso() {}

    // Constructor útil para altas rápidas
    public Curso(String titulo, String tema, String nivelDificultad) {
        this.titulo = titulo;
        this.tema = tema;
        this.nivelDificultad = nivelDificultad;
        this.fechaCreacion = LocalDate.now();
        this.popularidad = 0;
        this.activo = true;
    }

    public String getEstado() {
        if (this.activo == null) return "INACTIVO";
        return this.activo ? "ACTIVO" : "INACTIVO";
    }

    public void setEstado(String estado) {
        if (estado != null) {
            // Si mandan ACTIVO es true, cualquier otro valor (INACTIVO, SUSPENDIDO) es false
            this.activo = estado.equalsIgnoreCase("ACTIVO");
        }
    }

    // Getters y Setters
    public Long getIdCurso() { return idCurso; }
    public void setIdCurso(Long idCurso) { this.idCurso = idCurso; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }

    public String getNivelDificultad() { return nivelDificultad; }
    public void setNivelDificultad(String nivelDificultad) { this.nivelDificultad = nivelDificultad; }

    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public Integer getPopularidad() { return popularidad; }
    public void setPopularidad(Integer popularidad) { this.popularidad = popularidad; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public List<Leccion> getLecciones() { return lecciones; }
    public void setLecciones(List<Leccion> lecciones) { this.lecciones = lecciones; }

    public List<Inscripcion> getInscripciones() { return inscripciones; }
    public void setInscripciones(List<Inscripcion> inscripciones) { this.inscripciones = inscripciones; }
}