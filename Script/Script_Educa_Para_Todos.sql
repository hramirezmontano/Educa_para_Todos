CREATE DATABASE  IF NOT EXISTS `educa_para_todos` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `educa_para_todos`;
-- MySQL dump 10.13  Distrib 8.0.46, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: educa_para_todos
-- ------------------------------------------------------
-- Server version	8.0.46

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cursos`
--

DROP TABLE IF EXISTS `cursos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cursos` (
  `id_curso` bigint NOT NULL AUTO_INCREMENT,
  `titulo` varchar(150) NOT NULL,
  `tema` varchar(50) NOT NULL,
  `nivel_dificultad` varchar(20) NOT NULL,
  `popularidad` int DEFAULT '0',
  `fecha_creacion` date NOT NULL,
  `activo` bit(1) NOT NULL,
  PRIMARY KEY (`id_curso`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cursos`
--

LOCK TABLES `cursos` WRITE;
/*!40000 ALTER TABLE `cursos` DISABLE KEYS */;
INSERT INTO `cursos` VALUES (1,'Introducción a Java 17','Programación ','Básico',95,'2026-03-01',_binary ''),(2,'Jakarta EE 10 Avanzado','Backend','Avanzado',88,'2026-03-05',_binary ''),(3,'Fundamentos de Bases de Datos Relacionales','Base de Datos','Básico',75,'2026-03-10',_binary ''),(4,'Hibernate 6 y Mapeo Objeto-Relacional','Persistencia','Intermedio',90,'2026-03-12',_binary ''),(7,'Cobol','Programacion','Basico',2,'2026-09-15',_binary ''),(8,'Python 3.4','Programacion','Avanzado',90,'2026-09-16',_binary ''),(9,'Unity','Diseño','Intermedio',46,'2026-09-19',_binary '');
/*!40000 ALTER TABLE `cursos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inscripciones`
--

DROP TABLE IF EXISTS `inscripciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inscripciones` (
  `id_inscripcion` bigint NOT NULL AUTO_INCREMENT,
  `id_usuario` bigint NOT NULL,
  `id_curso` bigint NOT NULL,
  `fecha_inscripcion` date DEFAULT NULL,
  `estado` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_inscripcion`),
  KEY `fk_usuario` (`id_usuario`),
  KEY `fk_curso` (`id_curso`),
  CONSTRAINT `fk_curso` FOREIGN KEY (`id_curso`) REFERENCES `cursos` (`id_curso`),
  CONSTRAINT `fk_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inscripciones`
--

LOCK TABLES `inscripciones` WRITE;
/*!40000 ALTER TABLE `inscripciones` DISABLE KEYS */;
INSERT INTO `inscripciones` VALUES (1,1,8,'2026-09-16','ACTIVO'),(3,7,9,'2026-09-19','ACTIVO');
/*!40000 ALTER TABLE `inscripciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lecciones`
--

DROP TABLE IF EXISTS `lecciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lecciones` (
  `id_leccion` bigint NOT NULL AUTO_INCREMENT,
  `titulo` varchar(150) NOT NULL,
  `orden` int NOT NULL,
  `id_curso` bigint NOT NULL,
  PRIMARY KEY (`id_leccion`),
  KEY `lecciones_ibfk_1` (`id_curso`),
  CONSTRAINT `lecciones_ibfk_1` FOREIGN KEY (`id_curso`) REFERENCES `cursos` (`id_curso`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lecciones`
--

LOCK TABLES `lecciones` WRITE;
/*!40000 ALTER TABLE `lecciones` DISABLE KEYS */;
INSERT INTO `lecciones` VALUES (1,'Configuración del Entorno JDK 17',1,1),(2,'Estructura de un Servlet',1,2),(3,'Consultas JPQL Avanzadas',2,2),(4,'Normalización de Tablas',1,3);
/*!40000 ALTER TABLE `lecciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuarios`
--

DROP TABLE IF EXISTS `usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuarios` (
  `id_usuario` bigint NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `rol` varchar(20) DEFAULT 'ESTUDIANTE',
  `fecha_registro` date NOT NULL,
  `estado` varchar(20) DEFAULT 'ACTIVO',
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuarios`
--

LOCK TABLES `usuarios` WRITE;
/*!40000 ALTER TABLE `usuarios` DISABLE KEYS */;
INSERT INTO `usuarios` VALUES (1,'Carlos Pérez','carlos.perez@educa.com','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','ESTUDIANTE','2026-03-01','ACTIVO'),(2,'Ana Gómez','ana.gomez@educa.com','$2a$10$7v1X4N1K6jE5L2h8v5yMZOa9Gq3z1F4B6c8D0E2F4G6H8I0J2K4L6','ESTUDIANTE','2026-03-03','ACTIVO'),(3,'Juan Soto','juan.soto@educa.com','$$2a$10$8w9Z5xY2uV1tS3rQ4pP6oO7nN9mM0lL1kK3jJ5hH7gG9fE1dC3bA5','ESTUDIANTE','2026-03-05','ACTIVO'),(5,'Raquel Aguilar Herrera','Raquel.aguilar@cruzycia.cl','$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy','ESTUDIANTE','2026-09-15','ACTIVO'),(6,'Matias','matias@123.cl','$2a$10$y6K9vL2mP4qR7sT1uW3xZ5aB8dE0fG2hI4jK6lN8oP0qR2sT4uV6wX','ESTUDIANTE','2026-09-16','INACTIVO'),(7,'Matias Ramirez Aguilar','matiasIngnacio@gmail.com','$2a$10$3mN5pQ7sT9uV1wX2yZ4aB6dE8fG0hI2jK4lN6oP8qR0sT2uV4wX6yZ','ESTUDIANTE','2026-09-19','ACTIVO'),(11,'Camila Ramirez','cramirez@gmail.com','$2a$10$DhmCGGPRHxOsljzgRsU0UOsjQBRU7q1aCzCKBnJxjAns16KXYWzoO','ESTUDIANTE','2026-09-20','ACTIVO'),(12,'Andrea Ramirez','Aramirez123@Gamil.com','$2a$10$3Hexur/ps68DAKnhbVxh.e4BBND8jw.VfP09PUyDfFH.3Vj9.pg2G','ESTUDIANTE','2026-09-20','ACTIVO');
/*!40000 ALTER TABLE `usuarios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'educa_para_todos'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-20 12:42:07
