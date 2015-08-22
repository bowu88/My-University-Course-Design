CREATE DATABASE  IF NOT EXISTS `asylum_system` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `asylum_system`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: asylum_system
-- ------------------------------------------------------
-- Server version	5.6.14

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
-- Table structure for table `asylums`
--

DROP TABLE IF EXISTS `asylums`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `asylums` (
  `id` varchar(40) NOT NULL,
  `name` varchar(100) NOT NULL,
  `address` varchar(100) DEFAULT NULL,
  `classid` varchar(20) DEFAULT NULL,
  `area` double DEFAULT NULL,
  `people` int(11) DEFAULT NULL,
  `img` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `x` double DEFAULT NULL,
  `y` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `asylums`
--

LOCK TABLES `asylums` WRITE;
/*!40000 ALTER TABLE `asylums` DISABLE KEYS */;
INSERT INTO `asylums` VALUES ('0372faf7-a5d2-4e40-a58b-8ac1c1b9cfc2','莆田二十八中','东庄镇','Ⅲ类',0.7,1300,NULL,NULL,119.03785,25.25355),('1ab9d65d-21ae-42bc-85d4-d4b1e53ee790','仙游县实小','党校路','Ⅲ类',0.8,600,NULL,NULL,118.68908,25.36553),('24a13022-4f62-4061-911a-65d8f2a862b0','仙游一中','解放东路','Ⅲ类',1.8,3000,NULL,NULL,118.69728,25.36801),('2a95df29-a512-4931-aa95-95b99909c8ae','仙游县第一道德中学','兰溪公园旁','Ⅲ类',0.8,1000,NULL,NULL,118.68723,25.357),('2b932b23-7826-4b80-9baf-1b88156b4db3','莆田科技校','城厢区华亭','Ⅲ类',0.4,2000,NULL,NULL,118.94445,25.39194),('32f3436a-9186-42cc-bb37-32f4645cd95b','涵江区体育场','新涵街中段西侧','Ⅲ类',1,3000,NULL,NULL,119.123219,25.47678),('3624435a-83f8-4121-a009-3b8365234292','莆田市政广场','市政府广场','Ⅰ类',2.3,12000,NULL,NULL,119.00316,25.45511),('3c388d97-5e40-4b56-b1bc-311996176b32','莆田六中体育场','涵三路西侧','Ⅲ类',1,2000,NULL,NULL,119.11276,25.45579),('423fc260-aa90-42e8-8094-aae4dfc243d6','莆田十七中','涵江区苍林路西侧','Ⅲ类',0.4,1000,NULL,NULL,119.10051,25.46224),('43efc9e8-82e9-477a-b441-a59bd04325ef','莆田五中','城厢区政府旁','Ⅱ类',0.4,2000,NULL,NULL,118.98952,25.42364),('45c71d29-2cc9-4983-b6ee-8022daf02cd1','莆田四中','荔城区延寿北路','Ⅲ类',2.2,11000,NULL,NULL,119.02757,25.45411),('52bfaf1f-d5ad-4be6-9503-20226f925251','北岸山亭中心小学','山亭镇','Ⅲ类',0.2,300,NULL,NULL,119.131641,25.162495),('7de0e486-1c57-4ce0-8646-30d7cfc4ba88','仙游县体育中心','八二五南路','Ⅱ类',2.2,6000,NULL,NULL,118.68844,25.34897),('81ac4b6b-06c9-4401-83d0-e55f531e3789','莆田十五中','荔城区西天尾','Ⅲ类',0.37,1800,NULL,NULL,119.048,25.4896),('858fbd47-b9e3-4977-b369-74fc347ffb2c','莆田工艺美术城','高速公路莆田出口旁','Ⅱ类',0.64,2200,NULL,NULL,119.06309,25.37765),('97db45a3-f938-4603-a1fe-1aa28a4a831f','湄州岛天后广场','天后宫','Ⅲ类',0.2,300,NULL,NULL,119.14055,25.09047),('9e173a41-853c-4e57-a5eb-816a95d1df7f','莆田三中','学园北路','Ⅲ类',0.2,500,NULL,NULL,119.01807,25.44881),('9f4206ff-0518-4931-81e3-0d4ca1dd7625','涵江区人民公园','人民街北段东侧','Ⅱ类',2,5000,NULL,NULL,119.11751,25.47442),('b5f4b2c0-a5e6-4f0b-b201-510c4dd485f0','秀屿中央公园','秀屿区政府广场','Ⅰ类',2,600,NULL,NULL,119.09913,25.31775),('c09757f2-8592-40bc-9035-b1a398cc718a','秀屿区第十一中学','埭头镇','Ⅲ类',0.3,800,NULL,NULL,119.243206,25.259526),('c996d672-6850-4fe8-9444-f6abc23851fb','北高岱峰中学',' 北高镇岱峰村','Ⅲ类 ',1.08,5500,NULL,NULL,119.161879,25.347951),('dd681505-7948-463d-9bf6-44391f6c1878','莆田文献中学','石室路边','Ⅲ类',4.6,500,NULL,NULL,118.99753,25.44056),('ea0a5a85-cea2-46eb-a33b-9c7680ab5367','莆田八中','荔城区黄石镇','Ⅲ类',0.11,600,NULL,NULL,119.08028,25.38146),('f798109d-fb1c-48bc-80c4-0a3561b7fc35','秀屿区南日中学','南日镇','Ⅲ类',0.6,1300,NULL,NULL,119.47028,25.22104),('f9c671c9-c9f8-43f2-af01-85b9c04d4404','莆田一中','学园南街','Ⅲ类',0.4,2000,NULL,NULL,119.012712,25.422803);
/*!40000 ALTER TABLE `asylums` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `earthquake`
--

DROP TABLE IF EXISTS `earthquake`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `earthquake` (
  `id` varchar(40) NOT NULL,
  `location` varchar(100) DEFAULT NULL,
  `time` varchar(100) DEFAULT NULL,
  `magnitude` double DEFAULT NULL,
  `x` double DEFAULT NULL,
  `y` double DEFAULT NULL,
  `depth` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `earthquake`
--

LOCK TABLES `earthquake` WRITE;
/*!40000 ALTER TABLE `earthquake` DISABLE KEYS */;
INSERT INTO `earthquake` VALUES ('2012-01-19 18:45:47','福建省莆田市仙游县、福州市永泰县交界','2012-01-19 18:45:47',2,118.72,25.67,12),('2012-04-15 11:57:01','福建省莆田市仙游县、福州市永泰县交界','2012-04-15 11:57:01',3.6,118.71,25.66,10),('2012-04-15 12:02:02','福建省莆田市仙游县、福州市永泰县交界','2012-04-15 12:02:02',2.6,118.72,25.67,11),('2012-11-25 07:48:49','福建省莆田市仙游县','2012-11-25 07:48:49',3.2,118.748,25.321,7.612),('2013-08-03 02:43:55','福建省莆田市仙游县','2013-08-03 02:43:55',3.5,118.75,25.65,5),('2013-08-03 02:43:56','福建省莆田市仙游县','2013-08-03 02:43:56',3.5,118.8,25.6,4),('2013-08-09 13:37:13','福建省莆田市仙游县','2013-08-09 13:37:13',2.2,118.8,25.6,7),('2013-08-09 13:38:40','福建省莆田市仙游县','2013-08-09 13:38:40',3.2,118.75,25.63,9),('2013-08-09 13:38:41','福建省莆田市仙游县','2013-08-09 13:38:41',2.6,118.8,25.6,6),('2013-08-19 17:36:19','福建省莆田市仙游县','2013-08-19 17:36:19',3.3,118.8,25.6,11),('2013-08-23 05:02:01','福建省莆田市仙游县','2013-08-23 05:02:01',4,118.8,25.6,8),('2013-09-04 06:23:26','福建省莆田市仙游县、福州市永泰县交界','2013-09-04 06:23:26',4.8,118.8,25.6,10),('2013-10-30 01:50:11','福建省莆田市仙游县、福州市永泰县交界','2013-10-30 01:50:11',4.3,118.8,25.6,11),('2014-03-14 19:55:27','福建省莆田市仙游县','2014-03-14 19:55:27',3.4,118.75,25.64,10),('2014-03-14 20:03:03','福建省莆田市仙游县','2014-03-14 20:03:03',3.3,118.8,25.6,11),('2014-07-19 01:57:31','福建省莆田市仙游县','2014-07-19 01:57:31',3,118.74,25.62,7),('2014-07-19 02:02:07','福建省莆田市仙游县','2014-07-19 02:02:07',2.6,118.7,25.6,11);
/*!40000 ALTER TABLE `earthquake` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-09-08 19:14:45
