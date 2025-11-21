-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: mutualdb
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `afiliados`
--

DROP TABLE IF EXISTS `afiliados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `afiliados` (
  `NroTarjeta` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `dni` int NOT NULL,
  `domicilio` varchar(255) DEFAULT NULL,
  `sexo` char(1) DEFAULT NULL,
  `fecNac` date DEFAULT NULL,
  `fecIngMut` date DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `estadoAfi` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`NroTarjeta`),
  UNIQUE KEY `dni` (`dni`),
  UNIQUE KEY `mail` (`mail`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `afiliados`
--

LOCK TABLES `afiliados` WRITE;
/*!40000 ALTER TABLE `afiliados` DISABLE KEYS */;
INSERT INTO `afiliados` VALUES (3,'Juan','Perez','1132456789',45673299,'Chubut','M','1990-10-01','2023-10-10','juanperez@gmail.com',1),(5,'Lucia','Avila','12345342',45678900,'Belgrano 1912','F','1990-12-11','2016-02-22','lucia@gmail.com',0),(17,'Brunela Sofia','Gomez','3854328908',45857219,'obispo trejo','F','1991-11-12','2010-11-12','bgsofi26@gmail.com',1),(19,'Maira','Suarez','+543856786788',23456789,'San Martin 234','F','1989-11-15','2025-07-10','maira@gmail.com',1),(20,'Valeria','Herrera','+543856788908',23678908,'Av Solis 145','F','1988-05-01','2012-11-15','valeria@gmail.com',1),(21,'Silvia','Rivas','+543856789087',29345678,'Independencia 1400','F','2004-10-03','2025-05-31','Silvia@gmail.com',1),(22,'Mariana','Torres','+543854547890',34567890,'Córdoba 125','F','2015-09-30','2025-10-06','mariana@gmail.com',1),(23,'Sofia ','Gomez','+543844468726',45857666,'San Martin 189','F','2002-02-01','2025-10-31','usuariogomez502@gmail.com',1);
/*!40000 ALTER TABLE `afiliados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bonosemitidos`
--

DROP TABLE IF EXISTS `bonosemitidos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bonosemitidos` (
  `idBonos` int NOT NULL AUTO_INCREMENT,
  `IdComercios` int NOT NULL,
  `NroTarjeta` int NOT NULL,
  `fecEmision` date NOT NULL,
  `fecVencimiento` date NOT NULL,
  PRIMARY KEY (`idBonos`),
  KEY `fk_bonos_comercios` (`IdComercios`),
  KEY `fk_bonos_afiliados` (`NroTarjeta`),
  CONSTRAINT `fk_bonos_afiliados` FOREIGN KEY (`NroTarjeta`) REFERENCES `afiliados` (`NroTarjeta`) ON UPDATE CASCADE,
  CONSTRAINT `fk_bonos_comercios` FOREIGN KEY (`IdComercios`) REFERENCES `comercios` (`IdComercios`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bonosemitidos`
--

LOCK TABLES `bonosemitidos` WRITE;
/*!40000 ALTER TABLE `bonosemitidos` DISABLE KEYS */;
INSERT INTO `bonosemitidos` VALUES (2,1,3,'2025-10-31','2025-10-31'),(3,1,5,'2025-10-31','2025-10-31'),(19,2,17,'2025-11-13','2025-12-13'),(20,1,17,'2025-11-13','2025-12-13'),(23,15,19,'2025-11-16','2025-12-16'),(24,13,17,'2025-11-16','2025-12-16'),(25,17,21,'2025-11-16','2025-12-16'),(26,15,22,'2025-11-16','2025-12-16'),(27,18,20,'2025-11-16','2025-12-16'),(28,2,21,'2025-11-16','2025-12-16'),(29,16,22,'2025-11-16','2025-12-16'),(30,1,21,'2025-11-16','2025-12-16'),(31,17,19,'2025-11-16','2025-12-16'),(32,16,17,'2025-11-16','2025-12-16'),(33,17,17,'2025-11-16','2025-12-16'),(34,18,17,'2025-11-16','2025-12-16'),(35,19,17,'2025-11-16','2025-12-16'),(36,16,23,'2025-11-17','2025-12-17'),(37,18,23,'2025-11-17','2025-12-17'),(38,2,23,'2025-11-17','2025-12-17'),(39,13,23,'2025-11-17','2025-12-17'),(40,1,22,'2025-11-17','2025-12-17'),(41,2,19,'2025-11-17','2025-12-17'),(42,17,3,'2025-11-21','2025-12-21');
/*!40000 ALTER TABLE `bonosemitidos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comercios`
--

DROP TABLE IF EXISTS `comercios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comercios` (
  `IdComercios` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `responsable` varchar(255) DEFAULT NULL,
  `TELEFONO` varchar(20) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `descuento` int NOT NULL,
  `fecIniAct` date NOT NULL,
  `estadoCom` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`IdComercios`),
  UNIQUE KEY `mail` (`mail`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comercios`
--

LOCK TABLES `comercios` WRITE;
/*!40000 ALTER TABLE `comercios` DISABLE KEYS */;
INSERT INTO `comercios` VALUES (1,'Estetica siempre bella','Av. Siempre Viva 123','Lucia Perez','384446872','esteticasb@gmail.com',10,'2025-10-04',1),(2,'Pelqueria el Siglo','Av. Belgrano 123','Jose Perez','123456789','peluqueriaeliglo@gmail.com',15,'2025-10-27',1),(13,'Pilates ','San Juan 123','Micaela ','384452986','micaelapilates@gmail.com',10,'2023-09-22',1),(14,'pilates mym','gorriti 123','martina perez','3844529867','martina@gmail.com',10,'2023-09-22',1),(15,'yoga & spa','belgrano 1800','Marcela Juarez','3856789001','marcela@gmail.com',10,'2025-07-02',1),(16,'Optica visión perfecta','San Juan 234','Lic. Marina Lopez','3856789098','optica@gmail.com',25,'2009-06-10',1),(17,'Farmacia La Esquina','Avellaneda 223','Dr. Carlos Mendez','387567890','farmacialaesquina@gmail.com',15,'2000-07-19',1),(18,'Odontología Sonrisas','Pedro Leon Gallo 129','Dra. Julieta Herrera ','3856789007','odjulietah@gmail.com',15,'2017-04-04',1),(19,'Pilates y Bienestar','Pellegrini 254','Lorena Castillo','3854567890','lorenac@gmail.com',10,'2021-10-05',1);
/*!40000 ALTER TABLE `comercios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-11-21 18:29:39
