CREATE DATABASE  IF NOT EXISTS `tingwode` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `tingwode`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: tingwode
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
-- Table structure for table `album_favorites`
--

DROP TABLE IF EXISTS `album_favorites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `album_favorites` (
  `user_id` varchar(40) NOT NULL DEFAULT '',
  `album_id` varchar(40) NOT NULL DEFAULT '',
  PRIMARY KEY (`user_id`,`album_id`),
  KEY `ae_albumid_FK` (`album_id`),
  CONSTRAINT `ae_albumid_FK` FOREIGN KEY (`album_id`) REFERENCES `albums` (`id`),
  CONSTRAINT `ae_userid_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album_favorites`
--

LOCK TABLES `album_favorites` WRITE;
/*!40000 ALTER TABLE `album_favorites` DISABLE KEYS */;
/*!40000 ALTER TABLE `album_favorites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `album_like`
--

DROP TABLE IF EXISTS `album_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `album_like` (
  `user_id` varchar(40) NOT NULL DEFAULT '',
  `album_id` varchar(40) NOT NULL DEFAULT '',
  PRIMARY KEY (`user_id`,`album_id`),
  KEY `ap_albumid_FK` (`album_id`),
  CONSTRAINT `ap_albumid_FK` FOREIGN KEY (`album_id`) REFERENCES `albums` (`id`),
  CONSTRAINT `ap_userid_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album_like`
--

LOCK TABLES `album_like` WRITE;
/*!40000 ALTER TABLE `album_like` DISABLE KEYS */;
/*!40000 ALTER TABLE `album_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `album_voice_relation`
--

DROP TABLE IF EXISTS `album_voice_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `album_voice_relation` (
  `album_id` varchar(40) NOT NULL DEFAULT '',
  `voice_id` varchar(40) NOT NULL DEFAULT '',
  PRIMARY KEY (`album_id`,`voice_id`),
  KEY `avrel_voiceid_FK` (`voice_id`),
  CONSTRAINT `avrel_albumid_FK` FOREIGN KEY (`album_id`) REFERENCES `albums` (`id`),
  CONSTRAINT `avrel_voiceid_FK` FOREIGN KEY (`voice_id`) REFERENCES `voices` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `album_voice_relation`
--

LOCK TABLES `album_voice_relation` WRITE;
/*!40000 ALTER TABLE `album_voice_relation` DISABLE KEYS */;
INSERT INTO `album_voice_relation` VALUES ('2c91b90546bd5f360146bdf50ce10005','2c91b90546bd5f360146bd78b5440000'),('2c91b90546bd5f360146bdf50ce10005','2c91b90546bd5f360146bd992c0a0001'),('2c91b90546bd5f360146bdf50ce10005','2c91b90546bd5f360146bd9dc38a0002'),('2c91b90546bd5f360146bdf50ce10005','2c91b90546bd5f360146bdb6f4760003'),('2c91b90546bd5f360146bdf50ce10005','2c91b90546bd5f360146bdebee650004'),('2c91b90546c790fc0146c7cdb60c0006','2c91b90546c790fc0146c7acf0910000'),('2c91b90546c790fc0146c7cdb60c0006','2c91b90546c790fc0146c7b1a5a10001'),('2c91b90546c790fc0146c7cdb60c0006','2c91b90546c790fc0146c7b4cda20002'),('2c91b90546c790fc0146c7cdb60c0006','2c91b90546c790fc0146c7b72ae60003'),('2c91b90546c790fc0146c7cdb60c0006','2c91b90546c790fc0146c7bd83e10004'),('2c91b90546c790fc0146c7cdb60c0006','2c91b90546c790fc0146c7c74be70005');
/*!40000 ALTER TABLE `album_voice_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `albums`
--

DROP TABLE IF EXISTS `albums`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `albums` (
  `id` varchar(40) NOT NULL,
  `user_id` varchar(40) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `albums`
--

LOCK TABLES `albums` WRITE;
/*!40000 ALTER TABLE `albums` DISABLE KEYS */;
INSERT INTO `albums` VALUES ('2c91b90546bd5f360146bdf50ce10005','2c91b90546bd1bd60146bd1d055a0000','世界杯主题曲（1998-2014）','2014-06-21 10:24:51','本专辑收录了1998年到2014年各届世界杯的主题曲'),('2c91b90546c790fc0146c7cdb60c0006','2c91b90546bd1bd60146bd1d055a0000','音为你','2014-06-23 08:18:05','最初的想法');
/*!40000 ALTER TABLE `albums` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `follow`
--

DROP TABLE IF EXISTS `follow`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `follow` (
  `following_id` varchar(40) NOT NULL DEFAULT '',
  `followed_id` varchar(40) NOT NULL DEFAULT '',
  PRIMARY KEY (`following_id`,`followed_id`),
  KEY `follow_followedid_FK` (`followed_id`),
  CONSTRAINT `follow_followedid_FK` FOREIGN KEY (`followed_id`) REFERENCES `users` (`id`),
  CONSTRAINT `follow_followingid_FK` FOREIGN KEY (`following_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `follow`
--

LOCK TABLES `follow` WRITE;
/*!40000 ALTER TABLE `follow` DISABLE KEYS */;
/*!40000 ALTER TABLE `follow` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` varchar(40) NOT NULL,
  `username` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(20) NOT NULL,
  `gender` varchar(4) DEFAULT NULL,
  `photo_url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('2c91b90546bd1bd60146bd1d055a0000','向阳','501902960@qq.com','123','男',NULL),('2c91b90546c23f3c0146c28d03790000','安','ad@test.com','123','男','安_faceImage.jpg');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voice_favorites`
--

DROP TABLE IF EXISTS `voice_favorites`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voice_favorites` (
  `user_id` varchar(40) NOT NULL DEFAULT '',
  `voice_id` varchar(40) NOT NULL DEFAULT '',
  PRIMARY KEY (`user_id`,`voice_id`),
  KEY `ve_voiceid_FK` (`voice_id`),
  CONSTRAINT `ve_userid_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `ve_voiceid_FK` FOREIGN KEY (`voice_id`) REFERENCES `voices` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voice_favorites`
--

LOCK TABLES `voice_favorites` WRITE;
/*!40000 ALTER TABLE `voice_favorites` DISABLE KEYS */;
/*!40000 ALTER TABLE `voice_favorites` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voice_like`
--

DROP TABLE IF EXISTS `voice_like`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voice_like` (
  `user_id` varchar(40) NOT NULL DEFAULT '',
  `voice_id` varchar(40) NOT NULL DEFAULT '',
  PRIMARY KEY (`user_id`,`voice_id`),
  KEY `vp_voiceid_FK` (`voice_id`),
  CONSTRAINT `vp_userid_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `vp_voiceid_FK` FOREIGN KEY (`voice_id`) REFERENCES `voices` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voice_like`
--

LOCK TABLES `voice_like` WRITE;
/*!40000 ALTER TABLE `voice_like` DISABLE KEYS */;
/*!40000 ALTER TABLE `voice_like` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `voices`
--

DROP TABLE IF EXISTS `voices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `voices` (
  `id` varchar(40) NOT NULL,
  `user_id` varchar(40) DEFAULT NULL,
  `title` varchar(100) NOT NULL,
  `tag` varchar(100) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `picture_url` varchar(100) DEFAULT NULL,
  `audio_url` varchar(100) NOT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `play_count` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `voices_userid_FK` (`user_id`),
  CONSTRAINT `voices_userid_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `voices`
--

LOCK TABLES `voices` WRITE;
/*!40000 ALTER TABLE `voices` DISABLE KEYS */;
INSERT INTO `voices` VALUES ('2c91b90546bd1bd60146bd1effb70001','2c91b90546bd1bd60146bd1d055a0000','Anthem','足球','国际足联公平竞赛曲','/WEB-INF/upload/picture/向阳_Anthem_201111413266995.jpg','/WEB-INF/upload/audio/向阳_Anthem_Fifa_Anthem.mp3',26.041282,119.147254,'2014-06-21 06:31:03',0),('2c91b90546bd313b0146bd3640fb0000','2c91b90546bd1bd60146bd1d055a0000','时间都去哪啦','歌曲','流行歌曲','/WEB-INF/upload/picture/向阳_时间都去哪啦_1-13111121044Q56.png','/WEB-INF/upload/audio/向阳_时间都去哪啦_14385500158400128.mp3',26.036607,119.216531,'2014-06-21 06:56:28',0),('2c91b90546bd5f360146bd78b5440000','2c91b90546bd1bd60146bd1d055a0000','the Cup of Life','世界杯 足球','《生命之杯》1998年法国世界杯主题曲','/WEB-INF/upload/picture/向阳_the Cup of Life_法国夺冠.jpg','/WEB-INF/upload/audio/向阳_the Cup of Life_The Cup Of Life.mp3',48.874632,2.37573,'2014-06-21 08:09:03',0),('2c91b90546bd5f360146bd992c0a0001','2c91b90546bd1bd60146bd1d055a0000','Boom','足球 世界杯','2002年韩日世界杯主题曲','/WEB-INF/upload/picture/向阳_Boom_02巴西夺冠.jpg','/WEB-INF/upload/audio/向阳_Boom_Boom.mp3',35.172912,129.170475,'2014-06-21 08:44:30',0),('2c91b90546bd5f360146bd9dc38a0002','2c91b90546bd1bd60146bd1d055a0000','the Time of Our Life','足球 世界杯','2006年德国世界杯主题曲','/WEB-INF/upload/picture/向阳_the Time of Our Life_06意大利夺冠.jpg','/WEB-INF/upload/audio/向阳_the Time of Our Life_The time of our lives.mp3',48.012851,11.758927,'2014-06-21 08:49:31',0),('2c91b90546bd5f360146bdb6f4760003','2c91b90546bd1bd60146bd1d055a0000','Waka Waka','世界杯 足球','2010南非世界杯主题曲','/WEB-INF/upload/picture/向阳_Waka Waka_10西班牙夺冠.jpg','/WEB-INF/upload/audio/向阳_Waka Waka_Waka Waka (This Time For Africa) (K-Mix).mp3',-27.241324,26.81309,'2014-06-21 09:17:02',0),('2c91b90546bd5f360146bdebee650004','2c91b90546bd1bd60146bd1d055a0000','We Are One','世界杯 足球','2014年巴西世界杯主题曲','/WEB-INF/upload/picture/向阳_We Are One_201406171521552718.gif','/WEB-INF/upload/audio/向阳_We Are One_We Are One.mp3',-22.93073,-43.198973,'2014-06-21 10:14:54',0),('2c91b90546c790fc0146c7acf0910000','2c91b90546bd1bd60146bd1d055a0000','boss在拉萨','音为你','在拉萨的boss','/WEB-INF/upload/picture/向阳_boss在拉萨_1.jpg','/WEB-INF/upload/audio/向阳_boss在拉萨_黄小鸡_缩混.mp3',29.6454,91.079447,'2014-06-23 07:42:18',0),('2c91b90546c790fc0146c7b1a5a10001','2c91b90546bd1bd60146bd1d055a0000','琛哥在青海湖','音为你 青海湖','在青海湖的琛哥',NULL,'/WEB-INF/upload/audio/向阳_琛哥在青海湖_琛哥_缩混.mp3',36.644443,100.382155,'2014-06-23 07:47:26',0),('2c91b90546c790fc0146c7b4cda20002','2c91b90546bd1bd60146bd1d055a0000','桂林在成都','音为你','在成都的桂林',NULL,'/WEB-INF/upload/audio/向阳_桂林在成都_刘桂林 四川.mp3',30.660428,104.041492,'2014-06-23 07:50:53',0),('2c91b90546c790fc0146c7b72ae60003','2c91b90546bd1bd60146bd1d055a0000','正太在武汉','音为你 武汉','在武汉的正太',NULL,'/WEB-INF/upload/audio/向阳_正太在武汉_杨敬杰_缩混.mp3',30.635075,114.260903,'2014-06-23 07:53:28',0),('2c91b90546c790fc0146c7bd83e10004','2c91b90546bd1bd60146bd1d055a0000','凯悦在南京','音为你 南京','南京南京',NULL,'/WEB-INF/upload/audio/向阳_凯悦在南京_黄凯悦.mp3',32.079386,118.854478,'2014-06-23 08:00:24',0),('2c91b90546c790fc0146c7c74be70005','2c91b90546bd1bd60146bd1d055a0000','向阳在上海','音为你 上海','上海滩',NULL,'/WEB-INF/upload/audio/向阳_向阳在上海_向阳_缩混.mp3',31.588796,121.106926,'2014-06-23 08:11:05',0),('2c91b90546c7ea660146c7eb76790000','2c91b90546bd1bd60146bd1d055a0000','输入','测试','输入语句',NULL,'/WEB-INF/upload/audio/向阳_输入_杨敬杰_缩混.mp3',26.001282,119.204746,'2014-06-23 08:50:36',0);
/*!40000 ALTER TABLE `voices` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-06-23 17:44:36
