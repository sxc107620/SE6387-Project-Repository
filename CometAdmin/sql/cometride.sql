-- phpMyAdmin SQL Dump
-- version 4.0.9
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Apr 01, 2015 at 11:37 PM
-- Server version: 5.6.14
-- PHP Version: 5.5.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `cometride`
--

-- --------------------------------------------------------

--
-- Table structure for table `routes`
--

CREATE TABLE IF NOT EXISTS `routes` (
  `routeid` int(11) NOT NULL AUTO_INCREMENT,
  `rname` varchar(50) NOT NULL,
  `color` varchar(10) NOT NULL,
  `savepoints` varchar(40) NOT NULL,
  `lines` varchar(400) NOT NULL,
  `curves` varchar(400) NOT NULL,
  PRIMARY KEY (`routeid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Dumping data for table `routes`
--

INSERT INTO `routes` (`routeid`, `rname`, `color`, `savepoints`, `lines`, `curves`) VALUES
(1, 'McDermott Library Route', '#0039a6', 'uoihErunmQuMjCgBeIuH}FlDdK~IhGp@eE', 'e~ihEzynmQMX?xCnBFDcBDM', 'moihEpunmQeDCuB?eAZ??WJc@HM?UFAZAFO`@?JjBdAJCHEBc@Cy@UiAe@N[@QmAEY??Me@oA{Bq@f@CBs@oAQa@Oi@KmAESEGOE_@@m@?s@CG@??_@FDD?^C^?PFTR`@f@~@b@v@B@V^Vd@r@zAn@rABDLBHClA_ARQBOCQOi@_@s@e@o@^[TZ??^l@f@rATbB'),
(2, 'University Commons Route', '#773dbc', 'yoihEdmomQ@hFoIPaS@kFbOjEkB', '', 'goihEvjomQA|GA`@G?k@@??qKCyKCqC?AjE???b@BVA~@mBA??sAA??O@EFAJ?z@A~A@VDDb@@??xBB??p@?DcG?YBUDkB@qA??Ag@hA@bD?bPBrA@~BA@{CDA??FKB_A?sA??OA'),
(3, 'University Village Phase 1 Route', '#ff33ff', 'qoihEbmomQAbF?~S?~D|DJbEvBkCsI', '', 'goihEvjomQEnc@??\\?JEJOJOB@PBVJFB??f@RTB`@?b@NFHNX@B??\\f@h@TPDHADM?O???sC@uA???]II_@Mm@Mq@EWEOG??y@UYAWHOFWVORe@KEA@{R@o@?yBHEFQ?]???kBKA'),
(4, 'University Village Phase 1 Route 2', '#ff9900', 'qoihEbmomQAbFKtPBnDaGmDmGjF', '', 'goihEvjomQAjKAlKAlAG?{AAqBA}A?eF?CxF??`B@??zC?A{EAQ???MN?R??L?Pd@?ZBHDP?^An@Eb@AHR?hBAN??d@??eD?C@gP@{CLMBK???gCOA'),
(5, 'Rutford Avenue North-South Route', '#005710', 'soihEftomQoMD_OCcHJnp@VrPT', 'gyhhEzuomQIg@gFCsAD?a@_A??f@', 'cyhhExuomQdD@nBAZAJG@A??AoDCGGAuB???S@CH?\\@jA?h@cD?K???aKHqGEi@?O???uLAyKC}KA?B@pHC?kC?A??}A??lC@');

-- --------------------------------------------------------

--
-- Table structure for table `shuttles`
--

CREATE TABLE IF NOT EXISTS `shuttles` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `number` int(5) NOT NULL,
  `status` varchar(8) NOT NULL DEFAULT 'off-duty',
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `type` varchar(30) NOT NULL,
  `capacity` int(11) DEFAULT NULL,
  `routeid` int(11) DEFAULT NULL,
  `totalpassengers` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Dumping data for table `shuttles`
--

INSERT INTO `shuttles` (`id`, `number`, `status`, `latitude`, `longitude`, `type`, `capacity`, `routeid`, `totalpassengers`) VALUES
(1, 101, 'on-duty', 32.9852758713839, -96.75412009935826, '7-seater', 5, 3, 153),
(3, 201, 'on-duty', 32.987860928527766, -96.74627195578068, '9-seater', 0, 1, 94),
(4, 301, 'on-duty', 32.98948864324784, -96.7510019056499, '7-seater', 7, 2, 197),
(5, 401, 'on-duty', 32.987665195779485, -96.75374729325995, '7-seater', 2, 4, 308),
(7, 501, 'on-duty', 32.98980248389205, -96.75098459701985, '9-seater', 5, 5, 458);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `uid` int(100) NOT NULL AUTO_INCREMENT,
  `uname` varchar(20) NOT NULL,
  `upass` varchar(64) NOT NULL,
  `email` varchar(40) NOT NULL,
  `type` varchar(6) NOT NULL,
  `joindate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `attempts` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`uid`, `uname`, `upass`, `email`, `type`, `joindate`, `attempts`) VALUES
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'dannymatthew@gmail.com', 'admin', '2015-03-27 23:12:31', 0),
(2, 'danny', 'b7bee6b36bd35b773132d4e3a74c2bb5', '10dannymatthew@gmail.com', 'admin', '2015-03-28 19:12:31', 0),
(4, 'bob', '9f9d51bc70ef21ca5c14f307980a29d8', 'bob@gmail.com', 'driver', '2015-03-28 19:12:31', 0),
(6, 'eric', '29988429c481f219b8c5ba8c071440e1', 'eric@gmail.com', 'admin', '2015-03-29 02:03:53', 0),
(7, 'beny', 'ce86a5a6d59742542f38d0641bb44d16', 'beny@gmail.com', 'admin', '2015-03-29 02:04:46', 0),
(10, 'celric', '8db0b87b6d4f68e3313e9344b5139b8c', 'celric@gmail.com', 'admin', '2015-03-29 03:01:40', 0),
(12, 'jack', '4ff9fc6e4e5d5f590c4f2134a8cc96d1', 'jack@gmail.com', 'driver', '2015-03-29 04:47:13', 0),
(13, 'bill', 'e8375d7cd983efcbf956da5937050ffc', 'bill@gmail.com', 'driver', '2015-03-29 04:47:36', 0),
(14, 'percy', '13aef0865dcae54b2e4db0067e3fd5c6', 'percy@gmail.com', 'driver', '2015-03-29 04:48:14', 0),
(15, 'jim', '5e027396789a18c37aeda616e3d7991b', 'jim@gmail.com', 'driver', '2015-03-29 05:15:35', 0),
(16, 'fiona', '98855d4cedc4019a607176b532f1a051', 'fiona@gmail.com', 'admin', '2015-04-01 02:41:21', 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
