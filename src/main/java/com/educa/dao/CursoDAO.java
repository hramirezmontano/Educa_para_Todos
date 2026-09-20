package com.educa.dao;

import com.educa.model.Curso;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class CursoDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("EducaParaTodosPU");

    // Método para buscar curso por ID
    public Curso buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Curso.class, id);
        } finally {
            em.close();
        }
    }

    // 1. Búsqueda avanzada con JPQL por filtros (Tema, Nivel, Popularidad mínima)
    public List<Curso> buscarCursosFiltros(String tema, String nivelDificultad, Integer popularidadMinima) {
        EntityManager em = emf.createEntityManager();
        try {
            StringBuilder jpql = new StringBuilder("SELECT c FROM Curso c WHERE 1=1");
            
            if (tema != null && !tema.trim().isEmpty()) {
                jpql.append(" AND c.tema LIKE :tema");
            }
            if (nivelDificultad != null && !nivelDificultad.trim().isEmpty()) {
                jpql.append(" AND c.nivelDificultad = :nivel");
            }
            if (popularidadMinima != null) {
                jpql.append(" AND c.popularidad >= :popularidad");
            }
            
            TypedQuery<Curso> query = em.createQuery(jpql.toString(), Curso.class);
            
            if (tema != null && !tema.trim().isEmpty()) {
                query.setParameter("tema", "%" + tema + "%");
            }
            if (nivelDificultad != null && !nivelDificultad.trim().isEmpty()) {
                query.setParameter("nivel", nivelDificultad);
            }
            if (popularidadMinima != null) {
                query.setParameter("popularidad", popularidadMinima);
            }
            
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // 2. Operación UPDATE Masiva JPQL
    public int actualizarCursosMasivoPorFecha(LocalDate fechaLimite, String nuevoNivel) {
        EntityManager em = emf.createEntityManager();
        int actualizados = 0;
        try {
            em.getTransaction().begin();
            String jpql = "UPDATE Curso c SET c.nivelDificultad = :nuevoNivel WHERE c.fechaCreacion < :fechaLimite";
            actualizados = em.createQuery(jpql)
                             .setParameter("nuevoNivel", nuevoNivel)
                             .setParameter("fechaLimite", fechaLimite)
                             .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        return actualizados;
    }

    // 3. Operación DELETE Masiva JPQL
    public int eliminarCursosBajaPopularidad(int popularidadMaxima) {
        EntityManager em = emf.createEntityManager();
        int eliminados = 0;
        try {
            em.getTransaction().begin();
            String jpql = "DELETE FROM Curso c WHERE c.popularidad <= :popMax";
            eliminados = em.createQuery(jpql)
                           .setParameter("popMax", popularidadMaxima)
                           .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        return eliminados;
    }

public List<Curso> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Curso> query = em.createQuery("SELECT c FROM Curso c", Curso.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    } 

// Método para guardar un nuevo curso
    public void crearCurso(Curso curso) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(curso);
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

    public void eliminarPorId(Long id) {
    EntityManager em = emf.createEntityManager();
    try {
        em.getTransaction().begin();
        Curso curso = em.find(Curso.class, id);
        if (curso != null) {
            em.remove(curso);
        }
        em.getTransaction().commit();
    } catch (Exception e) {
        if (em.getTransaction().isActive()) em.getTransaction().rollback();
        e.printStackTrace();
    } finally {
        em.close();
    }
}

// Método para actualizar un curso existente
    public void actualizar(Curso curso) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(curso);
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

