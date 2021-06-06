/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.17 : Database - stock_manager
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`stock_manager` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `stock_manager`;

/*Table structure for table `goods` */

DROP TABLE IF EXISTS `goods`;

CREATE TABLE `goods` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `goodsname` varchar(50) NOT NULL,
  `goodsstyle` varchar(50) NOT NULL,
  `goodsnumber` int(11) NOT NULL,
  `storageID` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `goods` */

insert  into `goods`(`id`,`goodsname`,`goodsstyle`,`goodsnumber`,`storageID`) values (14,'apple','fruit',123,'java');

/*Table structure for table `storage` */

DROP TABLE IF EXISTS `storage`;

CREATE TABLE `storage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `storagename` varchar(50) NOT NULL,
  `storagestyle` varchar(50) NOT NULL,
  `storageID` varchar(100) NOT NULL,
  `seller` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`,`storageID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `storage` */

insert  into `storage`(`id`,`storagename`,`storagestyle`,`storageID`,`seller`) values (12,'blue','aa','400','d'),(13,'343','32325','325','java'),(14,'milk','caw','400','java'),(16,'rtrt','fefe','200','d');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `userpwd` varchar(50) NOT NULL,
  `integrate` varchar(100) DEFAULT NULL,
  `flag` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

/*Data for the table `users` */

insert  into `users`(`id`,`username`,`userpwd`,`integrate`,`flag`) values (1,'admin','1','1000',2),(2,'java','1','8600',1),(4,'d','123','300',1),(8,'xiaoming','1','10000',1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
