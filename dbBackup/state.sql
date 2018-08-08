/*
SQLyog Ultimate v8.32 
MySQL - 5.7.20-log 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

create table `state` (
	`id` double ,
	`sName` varchar (150),
	`population` varchar (150),
	`runningTimes` double ,
	`aveIncome` double ,
	`area` float ,
	`goodness` float ,
	`compactness` float ,
	`racialFairness` float ,
	`patisanFairness` float 
); 
insert into `state` (`id`, `sName`, `population`, `runningTimes`, `aveIncome`, `area`, `goodness`, `compactness`, `racialFairness`, `patisanFairness`) values('1','NH','1334168','182','70936','9349','1','1','1','1');
insert into `state` (`id`, `sName`, `population`, `runningTimes`, `aveIncome`, `area`, `goodness`, `compactness`, `racialFairness`, `patisanFairness`) values('2','CO','5684203','21','52334','104185','2','2','2','2');
insert into `state` (`id`, `sName`, `population`, `runningTimes`, `aveIncome`, `area`, `goodness`, `compactness`, `racialFairness`, `patisanFairness`) values('3','SC','5088916','34','65685','32020','3','3','3','3');
