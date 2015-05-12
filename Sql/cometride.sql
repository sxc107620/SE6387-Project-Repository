-- phpMyAdmin SQL Dump
-- version 4.0.9
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 11, 2015 at 09:51 PM
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
-- Table structure for table `interests`
--

CREATE TABLE IF NOT EXISTS `interests` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `routeid` int(11) NOT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=24 ;

--
-- Dumping data for table `interests`
--

INSERT INTO `interests` (`id`, `routeid`, `latitude`, `longitude`) VALUES
(1, 7, 32.9887492, -96.7732458),
(2, 7, 32.9887879, -96.7732846),
(3, 7, 32.9887879, -96.7732846),
(4, 7, 32.988759699999996, -96.773265),
(5, 7, 32.98876, -96.77363000000001),
(6, 7, 0, 0),
(7, 7, 32.98876, -96.77363000000001),
(8, 7, 32.98876, -96.77363000000001),
(9, 7, 32.98876, -96.77363000000001),
(10, 7, 32.98876, -96.77363000000001),
(11, 7, 32.98876, -96.77363000000001),
(12, 7, 32.98876, -96.77363000000001),
(13, 7, 32.98876, -96.77363000000001),
(14, 45, 32.989520000000006, -96.77314000000001),
(15, 7, 32.98876, -96.77363000000001),
(16, 45, 32.989520000000006, -96.77314000000001),
(17, 7, 32.98876, -96.77363000000001),
(18, 45, 32.989520000000006, -96.77314000000001),
(19, 7, 32.98876, -96.77363000000001),
(20, 7, 32.98876, -96.77363000000001),
(21, 7, 32.98876, -96.77363000000001),
(22, 1, 32.988060000000004, -96.74672000000001),
(23, 5, 32.988020000000006, -96.75095);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `routes`
--

INSERT INTO `routes` (`routeid`, `rname`, `color`, `savepoints`, `lines`, `curves`) VALUES
(1, 'McDermott Library Route', '#0039a6', 'uoihErunmQuMjCgBeIuH}FlDdK~IhGp@eE', '', 'goihEpunmQwBAeDAWF_Bf@[@QmAS_AWg@??w@sAu@j@s@oAQa@Oi@KmAESEGOE_@@m@?E?m@Cg@HDD?^C^?PFTR`@r@rA??R^RNr@tAtAxCHBNAl@e@??r@k@@UKk@O]c@q@Y]`@[V\\l@fA??Vv@N`ABz@?BENGNCFMl@AjC?D??@DJD~A?DC??@oA?UDKTMBc@?O??Ci@UiAx@WVGdD@zB@'),
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=232 ;

--
-- Dumping data for table `shuttles`
--

INSERT INTO `shuttles` (`id`, `number`, `status`, `latitude`, `longitude`, `type`, `capacity`, `routeid`, `totalpassengers`) VALUES
(1, 101, 'on-duty', 32.985801213321, -96.74598634243, '7-seater', 0, 1, 153),
(3, 201, 'on-duty', 32.986863136939, -96.745970249176, '9-seater', 9, 1, 94),
(4, 301, 'on-duty', 32.987617949879, -96.750973910093, '7-seater', 4, 2, 197),
(5, 401, 'on-duty', 32.990628138178, -96.751360148191, '7-seater', 7, 2, 308),
(7, 501, 'on-duty', 32.98541648784, -96.75423681736, '9-seater', 2, 3, 458),
(8, 601, 'on-duty', 32.988390754652, -96.754255592823, '7-seater', 6, 4, 154),
(9, 701, 'on-duty', 32.985632474281, -96.75137758255, '9-seater', 0, 4, 752),
(139, 725, 'on-duty', 32.991517901385, -96.750916242599, '7-seater', 2, 5, 0),
(140, 726, 'on-duty', 32.988963323257, -96.750967204571, '7-seater', 1, 2, 0),
(141, 727, 'on-duty', 32.98567747139, -96.751385629177, '7-seater', 1, 4, 0),
(142, 728, 'on-duty', 32.985533480561, -96.749432981014, '9-seater', 1, 4, 0),
(143, 729, 'on-duty', 32.988549364401, -96.750971227884, '7-seater', 4, 2, 0),
(144, 730, 'on-duty', 32.992710237508, -96.752192974091, '9-seater', 5, 5, 0),
(145, 731, 'on-duty', 32.981312645059, -96.750631928444, '7-seater', 3, 5, 0),
(146, 732, 'on-duty', 32.986460417409, -96.751019507647, '9-seater', 0, 2, 0),
(147, 733, 'on-duty', 32.987704567344, -96.755022704601, '7-seater', 2, 4, 0),
(148, 734, 'on-duty', 32.988397504008, -96.754872500896, '9-seater', 0, 4, 0),
(149, 735, 'on-duty', 32.989353657681, -96.751010119915, '9-seater', 3, 5, 0),
(150, 736, 'on-duty', 32.985974451732, -96.751061081886, '9-seater', 6, 5, 0),
(151, 737, 'on-duty', 32.987484086356, -96.753713786602, '7-seater', 1, 4, 0),
(152, 738, 'on-duty', 32.984883269052, -96.755269467831, '9-seater', 5, 3, 0),
(153, 739, 'on-duty', 32.989949842376, -96.750956475735, '7-seater', 1, 2, 0),
(154, 740, 'on-duty', 32.989436898805, -96.743765473366, '9-seater', 7, 1, 0),
(155, 741, 'on-duty', 32.984671780539, -96.754075884819, '9-seater', 5, 3, 0),
(156, 742, 'on-duty', 32.990628138178, -96.751397699118, '7-seater', 6, 2, 0),
(157, 743, 'on-duty', 32.988304137859, -96.751004755497, '9-seater', 0, 2, 0),
(158, 744, 'on-duty', 32.990700129466, -96.753233671188, '9-seater', 1, 2, 0),
(159, 745, 'on-duty', 32.985625724713, -96.75263017416, '9-seater', 1, 3, 0),
(160, 746, 'on-duty', 32.988199522659, -96.755009293556, '7-seater', 4, 4, 0),
(161, 747, 'on-duty', 32.98546373492, -96.755239963531, '7-seater', 4, 3, 0),
(162, 748, 'on-duty', 32.980439653897, -96.75102353096, '9-seater', 5, 5, 0),
(163, 749, 'on-duty', 32.985639223849, -96.753547489643, '7-seater', 1, 4, 0),
(164, 750, 'on-duty', 32.985000262479, -96.753979325294, '7-seater', 7, 3, 0),
(165, 751, 'on-duty', 32.991220939694, -96.750921607018, '9-seater', 0, 5, 0),
(166, 752, 'on-duty', 32.985956452952, -96.750975251198, '9-seater', 1, 5, 0),
(167, 753, 'on-duty', 32.988368256792, -96.74546867609, '9-seater', 4, 1, 0),
(168, 754, 'on-duty', 32.988788965823, -96.751003414392, '7-seater', 0, 2, 0),
(169, 755, 'on-duty', 32.983740325101, -96.754378974438, '7-seater', 4, 3, 0),
(170, 756, 'on-duty', 32.988977946768, -96.744602322578, '9-seater', 3, 1, 0),
(171, 757, 'on-duty', 32.985645973416, -96.75399273634, '9-seater', 6, 4, 0),
(172, 758, 'on-duty', 32.988874457253, -96.744792759418, '7-seater', 0, 1, 0),
(173, 759, 'on-duty', 32.985690970518, -96.754094660282, '9-seater', 8, 3, 0),
(174, 760, 'on-duty', 32.986975628099, -96.75101146102, '9-seater', 8, 2, 0),
(175, 761, 'on-duty', 32.986460417409, -96.751019507647, '9-seater', 7, 2, 0),
(176, 762, 'on-duty', 32.98567747139, -96.751581430435, '9-seater', 2, 4, 0),
(177, 763, 'on-duty', 32.98553573042, -96.749905049801, '9-seater', 1, 4, 0),
(178, 764, 'on-duty', 32.987315350533, -96.746871471405, '9-seater', 6, 1, 0),
(179, 765, 'on-duty', 32.986176937756, -96.746026575565, '9-seater', 2, 1, 0),
(180, 766, 'on-duty', 32.985630224425, -96.751914024353, '9-seater', 9, 4, 0),
(181, 767, 'on-duty', 32.991220939694, -96.750921607018, '7-seater', 0, 5, 0),
(182, 768, 'on-duty', 32.984962014645, -96.75096988678, '7-seater', 2, 5, 0),
(183, 769, 'on-duty', 32.984154306509, -96.755771040916, '9-seater', 5, 3, 0),
(184, 770, 'on-duty', 0, 0, '9-seater', 9, 5, 0),
(185, 771, 'on-duty', 32.985000262479, -96.753979325294, '7-seater', 6, 3, 0),
(186, 772, 'on-duty', 32.98461778339, -96.754102706909, '7-seater', 6, 3, 0),
(187, 773, 'on-duty', 32.983711076341, -96.754655241966, '7-seater', 0, 3, 0),
(188, 774, 'on-duty', 32.987949795552, -96.751055717468, '7-seater', 0, 5, 0),
(189, 775, 'on-duty', 32.985274746451, -96.754089295864, '7-seater', 4, 3, 0),
(190, 776, 'on-duty', 32.988102781612, -96.747142374516, '7-seater', 5, 1, 0),
(191, 777, 'on-duty', 32.98802403882, -96.745455265045, '9-seater', 3, 1, 0),
(192, 778, 'on-duty', 32.98566397226, -96.750138401985, '7-seater', 7, 3, 0),
(193, 779, 'on-duty', 32.987432340738, -96.746120452881, '9-seater', 1, 1, 0),
(194, 780, 'on-duty', 32.990871108541, -96.752592623234, '9-seater', 5, 2, 0),
(195, 781, 'on-duty', 32.985544729853, -96.749679744244, '7-seater', 3, 2, 0),
(196, 782, 'on-duty', 32.980439653897, -96.750701665878, '7-seater', 5, 5, 0),
(197, 783, 'on-duty', 32.985684220954, -96.752976179123, '7-seater', 4, 3, 0),
(198, 784, 'on-duty', 32.989173676605, -96.743824481964, '7-seater', 0, 1, 0),
(199, 785, 'on-duty', 32.986351300349, -96.751024872065, '9-seater', 3, 2, 0),
(200, 786, 'on-duty', 32.985274746451, -96.754089295864, '9-seater', 1, 3, 0),
(201, 787, 'on-duty', 32.987565079436, -96.747668087482, '7-seater', 6, 1, 0),
(202, 788, 'on-duty', 32.987484086356, -96.755020022392, '7-seater', 6, 4, 0),
(203, 789, 'on-duty', 32.988051036356, -96.745828092098, '7-seater', 6, 1, 0),
(204, 790, 'on-duty', 32.985623474857, -96.753005683422, '7-seater', 5, 3, 0),
(205, 791, 'on-duty', 32.988102781612, -96.747142374516, '9-seater', 4, 1, 0),
(206, 792, 'on-duty', 32.987085869297, -96.754939556122, '9-seater', 0, 4, 0),
(207, 793, 'on-duty', 32.985679721245, -96.753906905651, '7-seater', 4, 3, 0),
(208, 794, 'on-duty', 32.985526730986, -96.749618053436, '7-seater', 0, 3, 0),
(209, 795, 'on-duty', 32.987090368935, -96.754344105721, '7-seater', 5, 4, 0),
(210, 796, 'on-duty', 32.985652722983, -96.749698519707, '7-seater', 5, 2, 0),
(211, 797, 'on-duty', 32.985616725288, -96.750822365284, '9-seater', 4, 3, 0),
(212, 798, 'on-duty', 32.985634724137, -96.753163933754, '7-seater', 2, 4, 0),
(213, 799, 'on-duty', 32.985623474857, -96.751559972763, '9-seater', 4, 3, 0),
(214, 800, 'on-duty', 32.986818140434, -96.753912270069, '7-seater', 3, 4, 0),
(215, 801, 'on-duty', 32.984305049257, -96.75554305315, '7-seater', 6, 3, 0),
(216, 802, 'on-duty', 32.985632474281, -96.752133965492, '7-seater', 5, 4, 0),
(217, 803, 'on-duty', 32.985634724137, -96.754108071327, '7-seater', 7, 3, 0),
(218, 804, 'on-duty', 32.983783073271, -96.751082539558, '7-seater', 2, 5, 0),
(219, 805, 'on-duty', 32.990154569021, -96.744151711464, '7-seater', 1, 1, 0),
(220, 806, 'on-duty', 32.987315350533, -96.746871471405, '7-seater', 5, 1, 0),
(221, 807, 'on-duty', 32.985688720664, -96.755266785622, '7-seater', 4, 3, 0),
(222, 808, 'on-duty', 32.985693220373, -96.753153204918, '7-seater', 3, 3, 0),
(223, 809, 'on-duty', 32.988818212901, -96.751039624214, '9-seater', 6, 5, 0),
(224, 810, 'on-duty', 32.987607825754, -96.750959157944, '7-seater', 5, 5, 0),
(225, 811, 'on-duty', 32.988262516773, -96.745661795139, '9-seater', 8, 1, 0),
(226, 812, 'on-duty', 32.990721501869, -96.750992685556, '9-seater', 0, 2, 0),
(227, 813, 'on-duty', 32.984746026564, -96.754051744938, '7-seater', 7, 3, 0),
(228, 814, 'on-duty', 32.987261355002, -96.75094306469, '7-seater', 5, 5, 0),
(229, 815, 'on-duty', 32.985632474281, -96.751294434071, '7-seater', 5, 4, 0),
(230, 816, 'on-duty', 32.985679721245, -96.752828657627, '9-seater', 7, 4, 0),
(231, 817, 'on-duty', 32.98567747139, -96.751581430435, '7-seater', 3, 4, 0);

-- --------------------------------------------------------

--
-- Table structure for table `statistics_driver_time`
--

CREATE TABLE IF NOT EXISTS `statistics_driver_time` (
  `id` int(11) NOT NULL,
  `date` date NOT NULL,
  `starttime` time DEFAULT NULL,
  `endtime` time DEFAULT NULL,
  `totaltime` bigint(20) NOT NULL DEFAULT '0',
  `username` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `statistics_driver_time`
--

INSERT INTO `statistics_driver_time` (`id`, `date`, `starttime`, `endtime`, `totaltime`, `username`) VALUES
(12, '2015-01-10', '19:11:03', '19:12:26', 83000, 'percy'),
(13, '2015-04-14', '19:11:03', '19:12:26', 83000, 'jack'),
(14, '2015-04-14', '19:11:04', '19:12:27', 83000, 'bill'),
(15, '2015-02-11', '19:11:04', '19:12:27', 83000, 'percy'),
(16, '2015-04-14', '19:11:05', '19:12:28', 83000, 'jim'),
(17, '2015-03-19', '19:13:03', '19:19:07', 364000, 'percy'),
(18, '2015-04-14', '19:13:03', '19:19:08', 365000, 'bill'),
(19, '2015-04-14', '19:13:04', '19:19:08', 364000, 'jim'),
(20, '2015-04-20', '19:22:05', '21:07:13', 6308000, 'percy'),
(51, '2015-02-03', '21:31:00', '21:38:33', 453000, 'percy'),
(52, '2015-03-13', '21:39:25', '21:40:48', 83000, 'percy');

-- --------------------------------------------------------

--
-- Table structure for table `statistics_passengers`
--

CREATE TABLE IF NOT EXISTS `statistics_passengers` (
  `id` int(11) NOT NULL,
  `routeid` int(11) DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `totalpassengers` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `statistics_passengers`
--

INSERT INTO `statistics_passengers` (`id`, `routeid`, `timestamp`, `totalpassengers`) VALUES
(17, 3, '2015-04-14 19:28:11', 23),
(18, 1, '2015-04-14 19:28:12', 48),
(19, 2, '2015-04-14 19:28:12', 47),
(20, 4, '2015-04-14 19:28:13', 21),
(21, 5, '2015-04-14 19:28:13', 22),
(22, 3, '2015-04-14 19:34:57', 6),
(23, 1, '2015-04-14 19:34:57', 16),
(24, 2, '2015-04-14 19:34:58', 14),
(25, 4, '2015-04-14 19:34:59', 2),
(26, 5, '2015-04-14 19:34:59', 4),
(27, 3, '2015-04-14 19:42:05', 6),
(28, 1, '2015-04-14 19:42:05', 16),
(29, 2, '2015-04-14 19:42:06', 14),
(30, 4, '2015-04-14 19:42:07', 2),
(31, 5, '2015-04-14 19:42:07', 4),
(32, 3, '2015-04-14 19:47:08', 3),
(33, 1, '2015-04-14 19:47:08', 8),
(34, 2, '2015-04-14 19:47:09', 7),
(35, 4, '2015-04-14 19:47:09', 1),
(36, 5, '2015-04-14 19:47:10', 2),
(37, 3, '2015-04-14 19:56:36', 3),
(38, 1, '2015-04-14 19:56:37', 8),
(39, 2, '2015-04-14 19:56:37', 7),
(40, 4, '2015-04-14 19:56:38', 1),
(41, 5, '2015-04-14 19:56:38', 2),
(42, 3, '2015-04-14 20:04:26', 3),
(43, 1, '2015-04-14 20:04:27', 8),
(44, 2, '2015-04-14 20:04:27', 7),
(45, 4, '2015-04-14 20:04:28', 1),
(46, 5, '2015-04-14 20:04:29', 2),
(47, 3, '2015-04-14 20:12:44', 3),
(48, 1, '2015-04-14 20:12:45', 8),
(49, 2, '2015-04-14 20:12:45', 7),
(50, 4, '2015-04-14 20:12:46', 1),
(51, 5, '2015-04-14 20:12:47', 2),
(52, 3, '2015-04-14 20:17:56', 3),
(53, 1, '2015-04-14 20:17:56', 8),
(54, 2, '2015-04-14 20:17:57', 7),
(55, 4, '2015-04-14 20:17:58', 1),
(56, 5, '2015-04-14 20:17:58', 2),
(57, 3, '2015-04-14 21:44:08', 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `uid` int(100) NOT NULL AUTO_INCREMENT,
  `uname` varchar(20) NOT NULL,
  `upass` varchar(64) NOT NULL,
  `email` varchar(40) NOT NULL,
  `type` varchar(10) NOT NULL,
  `joindate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `attempts` int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`uid`, `uname`, `upass`, `email`, `type`, `joindate`, `attempts`) VALUES
(1, 'admin', '21232f297a57a5a743894a0e4a801fc3', 'dannymatthew@gmail.com', 'superadmin', '2015-03-27 23:12:31', 0),
(2, 'danny', 'b7bee6b36bd35b773132d4e3a74c2bb5', '10dannymatthew@gmail.com', 'admin', '2015-03-28 19:12:31', 0),
(4, 'bob', '9f9d51bc70ef21ca5c14f307980a29d8', 'bob@gmail.com', 'driver', '2015-03-28 19:12:31', 0),
(6, 'eric', '29988429c481f219b8c5ba8c071440e1', 'eric@gmail.com', 'admin', '2015-03-29 02:03:53', 0),
(7, 'beny', 'ce86a5a6d59742542f38d0641bb44d16', 'beny@gmail.com', 'admin', '2015-03-29 02:04:46', 0),
(12, 'jack', '4ff9fc6e4e5d5f590c4f2134a8cc96d1', 'jack@gmail.com', 'driver', '2015-03-29 04:47:13', 0),
(13, 'bill', '89c246298be2b6113fb10ba80f3c6956', 'bill@gmail.com', 'driver', '2015-03-29 04:47:36', 0),
(14, 'percy', '22af645d1859cb5ca6da0c484f1f37ea', 'percy@gmail.com', 'driver', '2015-03-29 04:48:14', 0),
(15, 'jim', '5e027396789a18c37aeda616e3d7991b', 'jim@gmail.com', 'driver', '2015-03-29 05:15:35', 0),
(16, 'fiona', '98855d4cedc4019a607176b532f1a051', 'fiona@gmail.com', 'admin', '2015-04-01 02:41:21', 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
