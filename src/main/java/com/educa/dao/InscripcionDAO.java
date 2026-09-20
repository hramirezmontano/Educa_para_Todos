package com.educa.dao;

import com.educa.model.Inscripcion;
import com.educa.model.Usuario;
import com.educa.model.Curso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class InscripcionDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("EducaParaTodosPU");

    public void registrarInscripcion(Inscripcion inscripcion) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();

        Long idUsuario = inscripcion.getUsuario().getIdUsuario();
        Long idCurso = inscripcion.getCurso().getIdCurso();

        Usuario usuarioManaged = em.find(Usuario.class, idUsuario);
        Curso cursoManaged = em.find(Curso.class, idCurso);

        if (usuarioManaged != null && cursoManaged != null) {
            inscripcion.setUsuario(usuarioManaged);
            inscripcion.setCurso(cursoManaged);
            
            em.persist(inscripcion); // Inserta una nueva inscripción con ID autoincremental
        }

        em.getTransaction().commit();
    } catch (Exception e) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        e.printStackTrace();
    } finally {
        em.close();
    }
}


public void actualizarInscripcion(Long idInscripcion, Long nuevoIdCurso, String nuevoEstado) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            
            // 1. Buscar la inscripción existente
            Inscripcion inscripcion = em.find(Inscripcion.class, idInscripcion);
            
            if (inscripcion != null) {
                // 2. Si se seleccionó un nuevo curso, buscarlo y actualizarlo
                if (nuevoIdCurso != null) {
                    Curso curso = em.find(Curso.class, nuevoIdCurso);
                    if (curso != null) {
                        inscripcion.setCurso(curso);
                    }
                }
                
                // 3. Actualizar el estado si viene especificado
                if (nuevoEstado != null && !nuevoEstado.isEmpty()) {
                    inscripcion.setEstado(nuevoEstado);
                }
                
                em.merge(inscripcion);
            }
            
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }


public List<Inscripcion> listarInscripciones() {
    EntityManager em = JPAUtil.getEntityManager();
    try {
        System.out.println("🔍 EJECUTANDO CONSULTA DE INSCRIPCIONES CON JOIN FETCH...");
        List<Inscripcion> lista = em.createQuery(
            "SELECT i FROM Inscripcion i JOIN FETCH i.usuario JOIN FETCH i.curso", 
            Inscripcion.class
        ).getResultList();
        
        System.out.println("✅ INSCRIPCIONES ENCONTRADAS: " + (lista != null ? lista.size() : 0));
        
        // --- LÍNEAS DE DEPURACIÓN NUEVAS ---
        for (Inscripcion ins : lista) {
            System.out.println("--- DEBUG ENTIDAD ---");
            System.out.println("ID Inscripción: " + ins.getIdInscripcion());
            System.out.println("Usuario objeto: " + ins.getUsuario());
            System.out.println("Usuario nombre: " + (ins.getUsuario() != null ? ins.getUsuario().getNombre() : "ES NULL"));
            System.out.println("Curso objeto: " + ins.getCurso());
            System.out.println("Curso título: " + (ins.getCurso() != null ? ins.getCurso().getTitulo() : "ES NULL"));
        }
        // ----------------------------------

        return lista;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    } finally {
        em.close();
    }
}

public void eliminarInscripcion(Long idInscripcion) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Inscripcion inscripcion = em.find(Inscripcion.class, idInscripcion);
            if (inscripcion != null) {
                em.remove(inscripcion);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}