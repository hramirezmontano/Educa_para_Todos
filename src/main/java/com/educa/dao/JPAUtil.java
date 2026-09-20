package com.educa.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.Map;

public class JPAUtil {
    // Vinculamos directamente con el nombre exacto de tu <persistence-unit name="EducaParaTodosPU">
    private static final EntityManagerFactory emf = buildEntityManagerFactory();

    private static EntityManagerFactory buildEntityManagerFactory() {
        try {
            // Primero busca -DDb_Password (propiedad de la JVM),
            // y si no existe, busca una variable de entorno con el mismo nombre.
            String password = System.getProperty("Db_Password");
            if (password == null || password.isEmpty()) {
                password = System.getenv("Db_Password");
            }

            if (password == null || password.isEmpty()) {
                throw new IllegalStateException(
                    "No se encontró Db_Password. Defínela como -DDb_Password=... en la JVM de Tomcat " +
                    "o como variable de entorno.");
            }

            Map<String, String> props = new HashMap<>();
            props.put("jakarta.persistence.jdbc.password", password);

            return Persistence.createEntityManagerFactory("EducaParaTodosPU", props);
        } catch (Throwable ex) {
            System.err.println("Error crítico al inicializar EntityManagerFactory: " + ex.getMessage());
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}