package com.educa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lecciones")
public class Leccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_leccion")
    private Long idLeccion;

    @Column(name = "titulo", nullable = false, length = 100)
    private String titulo;

    @Column(name = "contenido", columnDefinition = "TEXT")
    private String contenido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso", nullable = false)
    private Curso curso;

    public Leccion() {}

    public Long getIdLeccion() { return idLeccion; }
    public void setIdLeccion(Long idLeccion) { this.idLeccion = idLeccion; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
}