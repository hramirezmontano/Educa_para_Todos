package com.educa.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "inscripciones")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscripcion")
    private Long idInscripcion;

    @ManyToOne(fetch = FetchType.EAGER) // <-- Agrega fetch = FetchType.EAGER
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    @ManyToOne(fetch = FetchType.EAGER) // <-- Agrega fetch = FetchType.EAGER
    @JoinColumn(name = "id_curso")
    private Curso curso;

    @Column(name = "fecha_inscripcion")
    private LocalDate fechaInscripcion;

    private String estado;

    public Inscripcion() {}

    public Long getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(Long idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}