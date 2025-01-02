-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: testmysql
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date_of_birth` datetime(6) DEFAULT NULL,
  `age` int NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `business_title` varchar(255) DEFAULT NULL,
  `citizenship_status` varchar(255) DEFAULT NULL,
  `city_of_birth` varchar(255) DEFAULT NULL,
  `country_of_birth` varchar(255) DEFAULT NULL,
  `email_company` varchar(255) DEFAULT NULL,
  `ethnicty` varchar(255) DEFAULT NULL,
  `gender` bit(1) DEFAULT NULL,
  `hire_date` datetime(6) DEFAULT NULL,
  `job` varchar(255) DEFAULT NULL,
  `job_profile` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `marital` varchar(255) DEFAULT NULL,
  `employee_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `point` int NOT NULL,
  `primary_nationality` varchar(255) DEFAULT NULL,
  `region_of_birth` varchar(255) DEFAULT NULL,
  `religion` varchar(255) DEFAULT NULL,
  `time_type` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `organization_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2rna2w2imtuuk9f2et8yllawk` (`organization_id`),
  CONSTRAINT `FK2rna2w2imtuuk9f2et8yllawk` FOREIGN KEY (`organization_id`) REFERENCES `organization` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'2024-12-11 00:00:00.000000',12,'avatar','business_title','citizenship_status','city_of_birth','country_of_birth','dung@gmail.com','ethnicty',_binary '','2024-12-11 00:00:00.000000','job','job_profile','HCM','yes','dung','$2a$10$iy4h8J464wc2V2PKaHHqCup9HBJKWrTJFdQo/bwSJNLCwJlQMq532',100,'primary_nationality','region_of_birth','religion','time_type','Manager',1),(2,'2024-12-11 00:00:00.000000',21,'avatar','business_title','citizenship_status','city_of_birth','country_of_birth','duy@gmail.com','ethnicty',_binary '','2024-12-11 00:00:00.000000','job','job_profile','HCM','yes','duy','$2a$10$N3AO8ZUDDqjBn86RshP.3ObFnDtwOEFTBaTIwEDNAwNwB2ca8i16W',100,'primary_nationality','region_of_birth','religion','time_type','Manager',2),(3,'2024-12-11 00:00:00.000000',12,'avatar','business_title','citizenship_status','city_of_birth','country_of_birth','duy1@gmail.com','ethnicty',_binary '','2024-12-11 00:00:00.000000','job','job_profile','HCM','yes','duy1','$2a$10$v/D8OChiaXYEFV5C2dmseu3Gy0cvoChhf7YyLeG.5JvjoDMFkC0XC',20,'primary_nationality','region_of_birth','religion','time_type','HR',2),(4,'2024-12-11 00:00:00.000000',12,'avatar','business_title','citizenship_status','city_of_birth','country_of_birth','emp1@gmail.com','ethnicty',_binary '\0','2024-12-11 00:00:00.000000','job','job_profile','HCM','yes','emp1','$2a$10$/fStTn9K8LnryCC9jgBZaeLIoir3C4erTZTD8R8vPcQRcT29DnCFe',1,'primary_nationality','region_of_birth','religion','time_type','Employee',2),(5,'2024-12-11 00:00:00.000000',111,'avatar','business_title','citizenship_status','city_of_birth','country_of_birth','emp2@gmail.com','ethnicty',_binary '\0','2024-12-11 00:00:00.000000','job','job_profile','HCM','yes','emp2','$2a$10$WatANUZecGzrvWF.zAgOSOTz3DF132YJjKMR0UjQ/.DpLu/Sk0XSK',1,'primary_nationality','region_of_birth','religion','time_type','Employee',2),(6,'2024-12-11 00:00:00.000000',1,'avatar','business_title','citizenship_status','city_of_birth','country_of_birth','emp3@gmail.com','ethnicty',_binary '\0','2024-12-11 00:00:00.000000','job','job_profile','HCM','yes','emp3','$2a$10$vfa9qqMZ9LuIiITJuwAQN.qeIpgCq8fMDZFnYUHljp8Cel1upmyCG',2,'primary_nationality','region_of_birth','religion','time_type','Employee',1),(7,NULL,0,NULL,NULL,NULL,NULL,NULL,'null@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'empnull','$2a$10$Z2KJk5ZkSchtYYP0D3eGTudMqHvGcF7Po5CGd47AZanDC7ZkOsEGm',0,NULL,NULL,NULL,NULL,'Employee',1),(8,NULL,0,NULL,NULL,NULL,NULL,NULL,'null1@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'empnull1','$2a$10$PhXXMneHVdcgjTuxA5R1huNCAv6sWGo.faf2e/bhxKEZvUl4m9uJS',0,NULL,NULL,NULL,NULL,'Employee',1),(9,NULL,0,NULL,NULL,NULL,NULL,NULL,'null11@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'empnull11','$2a$10$PXscM7LyzvHkq.al2AZG3eJnsXcdIiWvCwczLCRVhS432rIhNBFyq',0,NULL,NULL,NULL,NULL,'Employee',1);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-04 13:48:19
