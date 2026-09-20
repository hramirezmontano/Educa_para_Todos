package com.educa.dao;

import com.educa.model.Usuario;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;
import org.mindrot.jbcrypt.BCrypt; 

public class UsuarioDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("EducaParaTodosPU");

    // Método para listar solo los usuarios ACTIVOS (Filtrado por borrado lógico)
    public List<Usuario> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u WHERE u.estado = 'ACTIVO'", Usuario.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    // Método opcional por si necesitas ver absolutamente todos (activos e inactivos)
    public List<Usuario> listarTodosSinFiltro() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
        } finally {
            em.close();
        }
    }

    // Método para guardar un nuevo usuario

    public void guardar(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            
            // Asegurar que nazca activo por defecto si viene vacío
            if (usuario.getEstado() == null || usuario.getEstado().isEmpty()) {
                usuario.setEstado("ACTIVO");
            }
            
            // BLINDAJE DE SEGURIDAD: Si la contraseña no está vacía y no está ya hasheada (no empieza con $2a$)
            if (usuario.getPassword() != null && !usuario.getPassword().startsWith("$2a$")) {
                String passwordSeguro = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
                usuario.setPassword(passwordSeguro);
            }

            em.persist(usuario);
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


    // Método para buscar usuario por ID
    public Usuario buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    // Método para actualizar un usuario existente (Requerido por UsuarioUpdateServlet)
    public void actualizar(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(usuario);
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

    // Método de Borrado Lógico por ID (Requerido por UsuarioDeleteServlet)
    public void eliminar(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Usuario usuario = em.find(Usuario.class, id);
            if (usuario != null) {
                // Borrado lógico: cambiamos estado a INACTIVO en vez de em.remove()
                usuario.setEstado("INACTIVO");
                em.merge(usuario);
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

    // Operación UPDATE Masiva requerida por UsuarioServlet
    public int borradoLogicoUsuariosMasivo(LocalDate fechaCorte) {
        EntityManager em = emf.createEntityManager();
        int actualizados = 0;
        try {
            em.getTransaction().begin();
            String jpql = "UPDATE Usuario u SET u.estado = 'INACTIVO' WHERE u.fechaRegistro < :fechaCorte";
            actualizados = em.createQuery(jpql)
                           .setParameter("fechaCorte", fechaCorte)
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

    // UPDATE Masivo general de Usuarios por fecha de registro y estado
    public int actualizarEstadoUsuariosPorFecha(LocalDate fechaCorte, String nuevoEstado) {
        EntityManager em = emf.createEntityManager();
        int actualizados = 0;
        try {
            em.getTransaction().begin();
            String jpql = "UPDATE Usuario u SET u.estado = :estado WHERE u.fechaRegistro < :fechaCorte";
            actualizados = em.createQuery(jpql)
                           .setParameter("estado", nuevoEstado)
                           .setParameter("fechaCorte", fechaCorte)
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

    // DELETE Masivo de Usuarios por estado y fecha (Se mantiene por si se requiere borrado físico masivo en lotes antiguos)
    public int eliminarUsuariosPorEstadoYFecha(String estado, LocalDate fechaLimite) {
        EntityManager em = emf.createEntityManager();
        int eliminados = 0;
        try {
            em.getTransaction().begin();
            String jpql = "DELETE FROM Usuario u WHERE u.estado = :estado AND u.fechaRegistro < :fechaLimite";
            eliminados = em.createQuery(jpql)
                           .setParameter("estado", estado)
                           .setParameter("fechaLimite", fechaLimite)
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

    // Registrar Usuario (Alias por compatibilidad)
    public void registrarUsuario(Usuario usuario) {
        guardar(usuario);
    }
}