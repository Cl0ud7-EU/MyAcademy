-- MySQL dump 10.13  Distrib 5.7.28, for Linux (x86_64)
--
-- Host: remotemysql.com    Database: JTw8pRhybP
-- ------------------------------------------------------
-- Server version	8.0.13-4

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `administrador`
--

DROP TABLE IF EXISTS `administrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrador` (
  `dni` int(11) NOT NULL,
  `nombre_administrador` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `contraseña` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`dni`),
  UNIQUE KEY `nombre_administrador_UNIQUE` (`nombre_administrador`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrador`
--

LOCK TABLES `administrador` WRITE;
/*!40000 ALTER TABLE `administrador` DISABLE KEYS */;
/*!40000 ALTER TABLE `administrador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fichaEvaluacion`
--

DROP TABLE IF EXISTS `fichaEvaluacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fichaEvaluacion` (
  `id_ficha` int(11) NOT NULL AUTO_INCREMENT,
  `dni_alumno` varchar(9) COLLATE utf8_unicode_ci NOT NULL,
  `readingAndUseOfEnglish` double NOT NULL,
  `writing` double NOT NULL,
  `listening` double NOT NULL,
  `speaking` double NOT NULL,
  `media` double NOT NULL,
  `apuntesProfesor` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `fecha` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id_ficha`),
  KEY `dni_alumno` (`dni_alumno`) USING BTREE,
  CONSTRAINT `dni_ficha` FOREIGN KEY (`dni_alumno`) REFERENCES `usuario` (`dni`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fichaEvaluacion`
--

LOCK TABLES `fichaEvaluacion` WRITE;
/*!40000 ALTER TABLE `fichaEvaluacion` DISABLE KEYS */;
INSERT INTO `fichaEvaluacion` VALUES (2,'45140487j',6.5,5.5,6.5,6.5,6.25,'Hola Adri eres un genio','2020-01-14 17:58:35'),(4,'75640633M',7.5,4.5,1,5,4.5,'Saturnino necesita mejorar el listening de personas con diferentes acentos','2020-01-14 20:22:08'),(5,'46130578K',10,10,10,10,10,'Juan es un jefazo','2020-01-14 20:31:04');
/*!40000 ALTER TABLE `fichaEvaluacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `grupos`
--

DROP TABLE IF EXISTS `grupos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `grupos` (
  `id_grupo` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `dni_profesor` varchar(9) COLLATE utf8_unicode_ci DEFAULT NULL,
  `hora_inicio` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `dia` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `duracion` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_grupo`),
  KEY `dni_profesor_idx` (`dni_profesor`),
  CONSTRAINT `dni_grupos` FOREIGN KEY (`dni_profesor`) REFERENCES `usuario` (`dni`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `grupos`
--

LOCK TABLES `grupos` WRITE;
/*!40000 ALTER TABLE `grupos` DISABLE KEYS */;
INSERT INTO `grupos` VALUES ('ingles 1','12345678P','16:00','Lunes','60m'),('ingles 2','12345678P',NULL,NULL,NULL),('ingles 3','28829966J',NULL,NULL,NULL);
/*!40000 ALTER TABLE `grupos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `log` (
  `id_log` int(11) NOT NULL,
  `dni_usuario` varchar(9) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ultima_conexion` date DEFAULT NULL,
  `hora_ultima_conexion` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `dia` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_log`),
  KEY `dni_log` (`dni_usuario`),
  CONSTRAINT `dni_log` FOREIGN KEY (`dni_usuario`) REFERENCES `usuario` (`dni`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago`
--

DROP TABLE IF EXISTS `pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pago` (
  `id_pago` int(11) NOT NULL AUTO_INCREMENT,
  `cantidad` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `fecha` date NOT NULL,
  `dni_alumno` varchar(9) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id_pago`),
  KEY `dni_pago` (`dni_alumno`),
  CONSTRAINT `dni_pago` FOREIGN KEY (`dni_alumno`) REFERENCES `usuario` (`dni`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago`
--

LOCK TABLES `pago` WRITE;
/*!40000 ALTER TABLE `pago` DISABLE KEYS */;
INSERT INTO `pago` VALUES (1,'75','2010-01-20','45140487j'),(2,'90','2026-12-19','75640633M'),(3,'54.95','2024-01-20','46130578K'),(4,'27','2020-01-20','46130578K'),(5,'30','2015-03-20','45140487j'),(6,'55','2015-03-20','46130578K'),(7,'23','2015-03-20','75640633M'),(8,'50','2014-01-20','46130578K');
/*!40000 ALTER TABLE `pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test`
--

DROP TABLE IF EXISTS `test`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test` (
  `id_test` int(11) NOT NULL,
  `dni_profesor` varchar(9) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id_test`),
  KEY `dni_test` (`dni_profesor`),
  CONSTRAINT `dni_test` FOREIGN KEY (`dni_profesor`) REFERENCES `usuario` (`dni`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test`
--

LOCK TABLES `test` WRITE;
/*!40000 ALTER TABLE `test` DISABLE KEYS */;
/*!40000 ALTER TABLE `test` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test2`
--

DROP TABLE IF EXISTS `test2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_Grupo` varchar(25) COLLATE utf8_unicode_ci NOT NULL,
  `preguntas` varchar(6000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `respuestas` varchar(6000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `correctas` varchar(6000) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test2`
--

LOCK TABLES `test2` WRITE;
/*!40000 ALTER TABLE `test2` DISABLE KEYS */;
INSERT INTO `test2` VALUES (1,'ingles 1','b||b||c||d||e','a||b||c||a||b||c||a||b||c||a||b||c||a||b||c||','1||2||1||3||1'),(2,'ingles 2','b||b||c||d||e','a||b||c||a||b||c||a||b||c||a||b||c||a||b||c||','1||2||1||3||1'),(3,'ingles 3','b||b||c||d||e','a||b||c||a||b||c||a||b||c||a||b||c||a||b||c||','1||2||1||3||1'),(5,'ingles 1','b¬b¬c¬d¬e','b¬b¬c¬b¬b¬c¬b¬b¬c¬b¬b¬c¬b¬b¬c','b¬b¬c¬d¬e'),(6,'ingles 1','Cuando fue 1+1¬pregunta 2¬pregunta 3¬Pregunta4¬Pregunta 5','La respuesta es el fantastico ralph¬resp1¬resp2¬resp3¬resp1¬resp2¬resp3¬resp1¬resp2¬resp3¬resp1¬resp2¬resp3','3¬2¬2¬2¬1');
/*!40000 ALTER TABLE `test2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `testRealizado2`
--

DROP TABLE IF EXISTS `testRealizado2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `testRealizado2` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_alumno` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `respuestas` varchar(30) COLLATE utf8_unicode_ci NOT NULL,
  `id_test` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `testRealizado2`
--

LOCK TABLES `testRealizado2` WRITE;
/*!40000 ALTER TABLE `testRealizado2` DISABLE KEYS */;
INSERT INTO `testRealizado2` VALUES (1,'45140487j','1,2,1,3,4',1);
/*!40000 ALTER TABLE `testRealizado2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_realizado`
--

DROP TABLE IF EXISTS `test_realizado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `test_realizado` (
  `id_test_realizado` int(11) NOT NULL,
  `id_test` int(11) NOT NULL,
  `dni_alumno` varchar(9) COLLATE utf8_unicode_ci NOT NULL,
  `respuesta1` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `respuesta2` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `respuesta3` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `nota` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id_test_realizado`),
  KEY `id_test_id_test_realizado` (`id_test`),
  KEY `dni_test_realizado` (`dni_alumno`),
  CONSTRAINT `dni_test_realizado` FOREIGN KEY (`dni_alumno`) REFERENCES `usuario` (`dni`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_test_id_test_realizado` FOREIGN KEY (`id_test`) REFERENCES `test` (`id_test`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_realizado`
--

LOCK TABLES `test_realizado` WRITE;
/*!40000 ALTER TABLE `test_realizado` DISABLE KEYS */;
/*!40000 ALTER TABLE `test_realizado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `dni` varchar(9) COLLATE utf8_unicode_ci NOT NULL,
  `tipo_usuario` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nombre` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `apellidos` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `sexo` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_grupo` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `nombre_usuario` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `contraseña` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `telefono` varchar(9) COLLATE utf8_unicode_ci DEFAULT NULL,
  `evaluacion` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`dni`),
  UNIQUE KEY `nombre_usuario_UNIQUE` (`nombre_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES ('12345678P','Profesor','Profesor','molon','Femenino',NULL,'rootp','root','profesormolon@topotamadre.com',NULL,'Sin nota'),('12356789J','Alumno','pepe','jose','Masculino',NULL,'pepe12356789J','root','NULL',NULL,'Sin nota'),('28829966J','Profesor','Jabe','Nitez','Femenino',NULL,'Ja69696969','elfitnessmeapasiona','jabenitez@hotmail.com',NULL,'Sin nota'),('45140387j','admin','Pepe','Viyuela',NULL,NULL,'admin','admin','','0',''),('45140487j','Alumno','Adrian','gil miranda','Masculino','ingles 1','roota','root',NULL,'684089110','Sin nota'),('46130578K','Alumno','Juan','tonto lava','Masculino','ingles 2','Juan@estudiantes','pepe',NULL,'666555777','Sin nota'),('75640633M','Alumno','Saturnino','perez gomez','Masculino','ingles 3','Saturnino75640633M','root','null',NULL,'Sin nota');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-14 23:14:08
