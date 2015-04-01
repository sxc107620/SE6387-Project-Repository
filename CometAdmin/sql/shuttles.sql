-- phpMyAdmin SQL Dump
-- version 4.0.9
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Mar 31, 2015 at 10:08 PM
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

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
