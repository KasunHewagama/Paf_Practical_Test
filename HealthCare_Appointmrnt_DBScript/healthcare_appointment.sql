-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: healthcare
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `appointment` (
  `appointmentId` varchar(6) NOT NULL,
  `patientId` char(6) NOT NULL,
  `patientName` varchar(45) NOT NULL,
  `phone` char(10) NOT NULL,
  `doctorName` varchar(45) NOT NULL,
  `specialization` varchar(45) NOT NULL,
  `hospitalId` char(6) NOT NULL,
  `hospitalName` varchar(45) NOT NULL,
  `appointmentDate` varchar(15) NOT NULL,
  `appointmentTime` time NOT NULL,
  `lastUpdateDate` varchar(15) DEFAULT NULL,
  `lastUpdateTime` time DEFAULT NULL,
  `appointmentStatus` varchar(15) NOT NULL,
  PRIMARY KEY (`appointmentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES ('APP1','p00001','Pasan Anuradha','0112000000','Dr W C Perera','Counsellor','h00001','Nawaloka','2020-04-10','20:25:00','2020-05-22','16:14:43','Completed'),('APP2','p00002','Dasun Madawala','0112224568','Dr R P Silva','Children Dentist','h00002','Sachitra Hospital','2020-04-20','20:05:00','2020-04-23','20:48:06','Completed'),('APP3','p00003','Oshan Silva','0123456789','Dr K C K  Alwis','Cardiologist','h00002','Hemas','2020-05-07','06:20:00','2020-04-24','23:07:31','Active'),('APP4','p00004','Sithum Alwis','0123456789','Dr L  Mohotte','Ayurveda','h00003','Appolo','2020-05-07','06:20:00','2020-04-25','21:16:04','Active'),('APP5','p00005','Hemantha Perera','0761625365','Dr S M  Wijesiri','Psychologist','h00008','Arogya Hospital','2020-05-07','20:05:00','2020-04-30','10:45:14','Active'),('APP6','p00006','Charitha Bandara','0785625365','Dr Kosala De Silva','Special Surgeon','h00001','Nawaloka','2020-05-10','08:10:00','2020-05-06','16:13:49','Pending'),('APP7','p00007','Pathum Sameera','0123456789','Dr K K Dais','Cardiologist','h00002','Hemas','2020-05-22','13:06:00','2020-05-06','17:03:58','Pending');
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-05-06 17:24:15
