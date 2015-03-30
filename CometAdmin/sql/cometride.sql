-- phpMyAdmin SQL Dump
-- version 4.0.9
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 30, 2015 at 05:25 AM
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
  `focus` varchar(40) NOT NULL,
  `waypoints` varchar(400) NOT NULL,
  PRIMARY KEY (`routeid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `routes`
--

INSERT INTO `routes` (`routeid`, `rname`, `color`, `focus`, `waypoints`) VALUES
(1, 'McDermott Library Route', 'Dark Blue', '', ''),
(2, 'University Commons Route', 'Purple', '', ''),
(3, 'University Village Phase 1 Route', 'Orange', '', ''),
(4, 'Rutford Avenue North-South Route', 'Green', '', '');

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
(1, 101, 'on-duty', 32.98617172241211, -96.75096893310547, '7-seater', 5, 3, 153),
(3, 201, 'off-duty', NULL, NULL, '9-seater', NULL, NULL, 0),
(4, 301, 'off-duty', NULL, NULL, '7-seater', NULL, NULL, 0),
(5, 401, 'off-duty', NULL, NULL, '7-seater', NULL, NULL, 0),
(7, 501, 'off-duty', NULL, NULL, '9-seater', NULL, NULL, 0);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=16 ;

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
(15, 'jim', '5e027396789a18c37aeda616e3d7991b', 'jim@gmail.com', 'driver', '2015-03-29 05:15:35', 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
